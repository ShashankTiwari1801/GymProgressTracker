package com.example.gymprogresstracker.util;

import android.content.Context;

//import com.example.gymprogresstracker.JSONSorter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

public class JSONHelper {
    Context context;

    public JSONObject parseJSONFile(String fileName){
        JSONObject jsonObject;

        try {
            InputStream inputStream = context.getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            reader.close();
            jsonObject = new JSONObject(jsonString.toString());
            for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                String s = it.next();
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }

        return jsonObject;
    }
    public JSONHelper(Context context){
        this.context = context;
    }
}
