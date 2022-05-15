package vn.edu.tdtu.lab09.EX1_LAB9;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import vn.edu.tdtu.lab09.R;

public class DownloadFileAdaptor extends RecyclerView.Adapter<DownloadFileAdaptor.MyViewHolder> {

  private final Handler handler = new Handler();
  int mPosition;
  private final Context context;
  private List<DownloadFile> mDownloadedFileList;

  public DownloadFileAdaptor(Context context) {
    this.context = context;
  }

  @NonNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(context)
       .inflate(R.layout.exercise01_list_row, viewGroup, false);

    return new MyViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {
    DownloadFile df = mDownloadedFileList.get(position);
    myViewHolder.name.setText(df.getName());
    myViewHolder.size.setText(df.getSize());
    myViewHolder.status.setText(df.getStatus());

    myViewHolder.imageThumb.setPadding(10, 10, 10, 10);
    myViewHolder.imageThumb.setScaleType(ImageView.ScaleType.CENTER_CROP);
    if (df.getImageThumBitmap() != null) {
      myViewHolder.imageThumb.setImageBitmap(df.getImageThumBitmap());
    } else {
      myViewHolder.imageThumb.setImageResource(android.R.drawable.ic_menu_report_image);
    }

    myViewHolder.btnView.setEnabled(false);
    myViewHolder.btnView.setOnClickListener(v -> viewImageSDCard(position));

    myViewHolder.btnDownload.setOnClickListener(v -> {
      // Download
      myViewHolder.btnDownload.setEnabled(false);
      myViewHolder.btnDownload.setTextColor(Color.GRAY);

      startDownload(position, myViewHolder);
      myViewHolder.status.setEnabled(true);
    });
  }

  @Override
  public int getItemCount() {
    if (mDownloadedFileList == null) {
      return 0;
    }
    return mDownloadedFileList.size();

  }

  public void setData(List<DownloadFile> downloadedFileList) {
    mDownloadedFileList = downloadedFileList;
    notifyDataSetChanged();
  }

  public List<DownloadFile> getEvents() {
    return mDownloadedFileList;
  }

  public void setPosition(int position) {
    mPosition = position;
  }

  static class MyViewHolder extends RecyclerView.ViewHolder {

    TextView name;
    TextView size;
    TextView status;
    ImageView imageThumb;
    ProgressBar progressBar;
    Button btnView;
    Button btnDownload;

    MyViewHolder(@NonNull final View itemView) {
      super(itemView);
      name = itemView.findViewById(R.id.tv_name);
      size = itemView.findViewById(R.id.tv_size);
      status = itemView.findViewById(R.id.tv_status);
      imageThumb = itemView.findViewById(R.id.imgv_thumb);
      progressBar = itemView.findViewById(R.id.progressBar);
      btnView = itemView.findViewById(R.id.btnView);
      btnDownload = itemView.findViewById(R.id.btnDownload);

      itemView.setOnClickListener(v -> {

      });
    }
  }

  // View Image from SD Card
  public void viewImageSDCard(int position) {
    final Builder imageDialog = new Builder(context);

    View layout = LayoutInflater.from(context)
        .inflate(R.layout.exercise01_custom_fullimage_dialog, null);

    ImageView image = layout.findViewById(R.id.fullimage);

    String urlDownload = mDownloadedFileList.get(position).getDownloadPath();

    // Get File Name from URL
    String fileName = urlDownload.substring(urlDownload.lastIndexOf('/') + 1);

    String strPath = "/mnt/sdcard/" + fileName;
    Bitmap bm = BitmapFactory.decodeFile(strPath); // Path from SDCard
    image.setScaleType(ImageView.ScaleType.CENTER_CROP);

    image.setImageBitmap(bm);

    String strName = mDownloadedFileList.get(position).getName();
    imageDialog.setIcon(android.R.drawable.btn_star_big_on);
    imageDialog.setTitle("View : " + strName);
    imageDialog.setView(layout);
    imageDialog.setPositiveButton(android.R.string.ok,
            (dialog, which) -> dialog.dismiss());
    imageDialog.create();
    imageDialog.show();
  }

  public void startDownload(final int position, final MyViewHolder myViewHolder) {

    Runnable runnable = new Runnable() {
      int currentStatus = 0;

      public void run() {

        String urlDownload = mDownloadedFileList.get(position).getDownloadPath();
        int count = 0;
        try {

          URL url = new URL(urlDownload);
          URLConnection conexion = url.openConnection();
          conexion.connect();

          final int lenghtOfFile = conexion.getContentLength();
          Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

          InputStream input = new BufferedInputStream(url.openStream());

          // Get File Name from URL
          String fileName = urlDownload
              .substring(urlDownload.lastIndexOf('/') + 1, urlDownload.length());

          OutputStream output = new FileOutputStream("/mnt/sdcard/" + fileName);

          byte data[] = new byte[1024];
          long total = 0;

          while ((count = input.read(data)) != -1) {
            total += count;
            currentStatus = (int) ((total * 100) / lenghtOfFile);
            output.write(data, 0, count);

            // Update ProgressBar
            handler.post(new Runnable() {
              public void run() {
                updateStatus(position, currentStatus, myViewHolder, lenghtOfFile);
              }
            });

          }

          output.flush();
          output.close();
          input.close();

        } catch (Exception e) {
        }


      }
    };
    new Thread(runnable).start();
  }

  private void updateStatus(int index, int currentStatus, MyViewHolder myViewHolder, int lenghtOfFile) {
    // Update ProgressBar
    myViewHolder.progressBar.setProgress(currentStatus);

    // Update Text to ColStatus
    myViewHolder.status.setText("Complete");
    myViewHolder.status.setText("Load : " + String.valueOf(currentStatus) + "%");

    // Enabled Button View
    if (currentStatus >= 100) {
      myViewHolder.status.setEnabled(true);
      myViewHolder.status.setText("Complete");
      myViewHolder.status.setTextColor(Color.GREEN);

      double fileSizeMegabytes = (lenghtOfFile / 1024);
      myViewHolder.size.setText("Size: " + fileSizeMegabytes + "MB");

      myViewHolder.btnView.setEnabled(true);
    }
  }
}
