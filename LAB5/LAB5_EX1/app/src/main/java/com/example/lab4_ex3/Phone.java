package com.example.lab4_ex3;

public class Phone {

    private int icon;
    private String title;
    private boolean checked;

    public Phone(int icon, String title, boolean checked) {
        this.icon = icon;
        this.title = title;
        this.checked = checked;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
