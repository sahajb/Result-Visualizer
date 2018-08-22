package com.example.android.resultvisualizer;

import org.json.JSONObject;

public class Summary {

    private JSONObject object;

    private int position;

    Summary(JSONObject jsonObject, int i) {
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
