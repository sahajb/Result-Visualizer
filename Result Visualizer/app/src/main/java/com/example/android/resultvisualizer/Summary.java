package com.example.android.resultvisualizer;

import org.json.JSONObject;

public class Summary {

    private JSONObject object;

    private int position;

    public Summary(JSONObject jsonObject, int i) {
        object = jsonObject;
        position = i;
    }

    public int getPosition() {
        return position;
    }

    public JSONObject getObject() {
        return object;
    }

}
