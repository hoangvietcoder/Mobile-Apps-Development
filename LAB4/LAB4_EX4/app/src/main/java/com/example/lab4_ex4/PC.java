package com.example.lab4_ex4;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PC {

    private String label;
    private boolean on;

    public void changMode() {
        this.on = !this.on;
    }

    public PC(String label, boolean on) {
        this.label = label;
        this.on = on;
    }

    public PC(int id) {
        this.label = String.format(Locale.US,"PC %d", id);
        this.on = false;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public static List<PC> generate(int numOfPC) {
        if (numOfPC <= 0 || numOfPC > 200) {
            throw new InvalidParameterException("Number of PC must in [1, 200]");
        }

        List<PC> data = new ArrayList<>();

        for (int i = 1; i <= numOfPC; i++) {
            data.add(new PC(i));
        }

        return data;
    }
}
