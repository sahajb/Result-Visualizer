package com.example.android.resultvisualizer;

import org.json.JSONObject;

public class Stats {

    private JSONObject object;

    private int position;

    private int clr;

    private int cr;

    private int sub;

    Stats(JSONObject jsonObject, int i, int cl, int c, int s) {
        object = jsonObject;
        position = i;
        clr = cl;
        cr = c;
        sub = s;
    }

    public int getPosition() {
        return position;
    }

    public JSONObject getObject() {
        return object;
    }

    public int getClr() {
        return clr;
    }

    public String getCr() {
        return String.valueOf(cr);
    }

    public int getSub() {
        return sub;
    }
}
