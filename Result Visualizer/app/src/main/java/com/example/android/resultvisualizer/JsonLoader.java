package com.example.android.resultvisualizer;

import android.content.AsyncTaskLoader;
import android.content.Context;

import org.json.JSONObject;

import static com.example.android.resultvisualizer.Utilities.JsonUtils.jsonObjFromFile;

public class JsonLoader extends AsyncTaskLoader<JSONObject> {

    private String rn;

    public JsonLoader(Context context, String s) {
        super(context);
        rn = s;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public JSONObject loadInBackground() {
        JSONObject jsonObject = jsonObjFromFile();
        if (jsonObject != null)
            return jsonObject;
        else
            return new JSONObject();
    }

}
