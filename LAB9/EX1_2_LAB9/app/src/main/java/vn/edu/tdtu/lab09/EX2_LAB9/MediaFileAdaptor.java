package vn.edu.tdtu.lab09.EX2_LAB9;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import vn.edu.tdtu.lab09.R;

public class MediaFileAdaptor extends RecyclerView.Adapter<MediaFileAdaptor.MyViewHolder> {

  int mPosition;
  private final Context context;
  private List<MediaFile> mMediaList;

  public MediaFileAdaptor(Context context) {
    this.context = context;
  }

  @NonNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(context)
        .inflate(R.layout.exercise02_list_row, viewGroup, false);

    return new MyViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {
    MediaFile df = mMediaList.get(position);
    myViewHolder.name.setText(df.getName());

  }

  @Override
  public int getItemCount() {
    if (mMediaList == null) {
      return 0;
    }
    return mMediaList.size();

  }

  public void setData(List<MediaFile> downloadedFileList) {
    mMediaList = downloadedFileList;
    notifyDataSetChanged();
  }

  public List<MediaFile> getEvents() {
    return mMediaList;
  }

  public void setPosition(int position) {
    mPosition = position;
  }

  class MyViewHolder extends RecyclerView.ViewHolder {

    TextView name;

    MyViewHolder(@NonNull final View itemView) {
      super(itemView);
      name = itemView.findViewById(R.id.tv_name);

      itemView.setOnClickListener(v -> {
        Intent newActivity = new Intent(context, PlayActivity.class);
        newActivity.putExtra("vMusicName", mMediaList.get(getAdapterPosition()).getName());
        newActivity.putExtra("vMusicPath", mMediaList.get(getAdapterPosition()).getPath());
        context.startActivity(newActivity);
      });
    }
  }
}
