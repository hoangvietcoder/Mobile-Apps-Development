package com.example.lab3_ex2;

public class Item {

    private String title;
    private String buttonRemove;

    public Item(String title, String buttonRemove) {
        this.title = title;
        this.buttonRemove = buttonRemove;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getButtonRemove() {
        return buttonRemove;
    }

    public void setButtonRemove(String buttonRemove) {
        this.buttonRemove = buttonRemove;
    }
}
