package com.example.lab3_ex3;

public class Phone {
    private int icon;
    private String name;
    private boolean checked;

    public Phone(int icon, String name, boolean checked) {
        this.icon = icon;
        this.name = name;
        this.checked = checked;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
