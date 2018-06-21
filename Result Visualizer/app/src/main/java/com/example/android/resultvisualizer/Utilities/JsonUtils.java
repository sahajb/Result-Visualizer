package com.example.android.resultvisualizer.Utilities;

import android.content.Context;
import android.util.Log;

import com.example.android.resultvisualizer.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class JsonUtils {

    private static JSONObject obj = null;

    private static JSONObject buildJsonStr(Context context) {
        if (obj == null) {
            String ret = "";
            try {
                InputStream inputStream = context.getResources().openRawResource(R.raw.fy);
                if (inputStream != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String receiveString;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((receiveString = bufferedReader.readLine()) != null) {
                        stringBuilder.append(receiveString);
                    }
                    inputStream.close();
                    ret = stringBuilder.toString();
                }
            } catch (FileNotFoundException e) {
                Log.e("readFromFile", "File not found: " + e.toString());
            } catch (IOException e) {
                Log.e("readFromFile", "Can not read file: " + e.toString());
            }
            try {
                obj = new JSONObject(ret);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    public static JSONObject jsonObjFromFile(Context context) {
        return buildJsonStr(context);
    }

}