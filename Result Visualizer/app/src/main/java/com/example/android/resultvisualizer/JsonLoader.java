package com.example.android.resultvisualizer;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.resultvisualizer.Utilities.NotificationUtils;

import org.json.JSONObject;

import static com.example.android.resultvisualizer.Utilities.JsonUtils.jsonObjFromFile;

public class JsonLoader extends AsyncTaskLoader<JSONObject> {

    private String rn;

    private boolean bool;

    JsonLoader(Context context, String s, boolean b) {
        super(context);
        rn = s;
        bool = b;
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

    @Override
    public void deliverResult(JSONObject data) {
        super.deliverResult(data);
        if (data.optJSONObject(rn) != null && bool)
            NotificationUtils.notification(getContext(), rn);
    }
}
