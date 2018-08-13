package com.example.android.resultvisualizer.Utilities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public final class JsonUtils {

    private JsonUtils() {
    }

    private static JSONObject obj = null;

    private static final String URL = "https://gist.githubusercontent.com/sahajb/b5d41ea86f03c13b4d2658238fe5d5de/raw/ec6bd2b9bd56907264850d00f419ef5374d0df68/ts1.json";

    private static JSONObject buildJsonStr() {
        if (obj == null) {
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(createUrl(URL));
            } catch (IOException e) {
                Log.e("buildJsonStr", "Problem making the HTTP request.", e);
            }
            try {
                obj = new JSONObject(jsonResponse);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("createUrl", "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null)
            return jsonResponse;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("makeHttpRequest", "Error response code: " + urlConnection.getResponseCode());
                return "";
            }
        } catch (IOException e) {
            return "";
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static JSONObject jsonObjFromFile() {
        return buildJsonStr();
    }

    public static boolean jsonValid() {
        return (obj != null) && (obj.length() != 0);
    }

}