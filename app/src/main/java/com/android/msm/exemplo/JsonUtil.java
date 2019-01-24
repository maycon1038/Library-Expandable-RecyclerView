package com.android.msm.exemplo;

import android.database.Cursor;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {


    //para convet para gson
    public static JsonArray convertlist(ArrayList<animais> list) {

        JsonArray jsonArray = new JsonArray();
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        for(animais d : list) {
            JsonElement ret = parser.parse(gson.toJson(d));
            jsonArray.add(ret);
        }
        Log.d("MainActivity 1 ", jsonArray.toString());
        return jsonArray;

    }
    //para fazer intereto para gson
    public static  List<animais> parseJSON(String jsonString) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<animais>>(){}.getType();
        List<animais> contactList = gson.fromJson(jsonString, type);
        return contactList;
    }


    public static  JSONArray cur2Json(Cursor cursor) {

        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        rowObject.put(cursor.getColumnName(i),
                                cursor.getString(i));
                    } catch (Exception e) {
                        Log.d("MainActivity ", e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }

        cursor.close();
        return resultSet;

    }
}
