package com.android.msm.searchable;

import android.database.Cursor;
import android.util.Log;


import com.android.msm.searchable.interfaces.JsonConvert;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;

public class JsonUtil {

    private static Cursor cursor;
    private static Object obj;
    private static ArrayList<Object> listObj;
    private static HashMap<String, JsonUtil> instances = new HashMap<String, JsonUtil>();
    private String name;
    private JsonConvert json;

    private JsonUtil(String name) {
        this.name = name;
    }

    public static JsonUtil with() {
        return getDefault();
    }


    private static JsonUtil getDefault() {
        return getInstance("JsonUtil");
    }


    private static JsonUtil getInstance(String name) {
        JsonUtil instance = instances.get(name);
        if (instance == null)
            instances.put(name, instance = new JsonUtil(name));
        return instance;
    }

    public static JsonUtil setListObj(ArrayList<Object> listObj) {
        if (listObj == null) {
            throw new NullPointerException("O ArrayList<Object> não pode ser null");
        } else {
            JsonUtil.listObj = listObj;
            return with();
        }

    }

    public static JsonUtil setListObjs(ArrayList<Object> listObj, Object obj) {
        if (listObj == null) {
            throw new NullPointerException("O ArrayList<Object> e o Object não pode ser null");
        } else {
            JsonUtil.listObj = listObj;
            JsonUtil.obj = obj;
            return with();
        }

    }

    public static JsonUtil setCursor(Cursor cursor) {
        if (cursor == null) {
            throw new NullPointerException("O Cursor não pode ser null");
        } else {
            JsonUtil.cursor = cursor;
            return with();
        }

    }


    private static JsonArray convertArrayListObjectToJsonArray(ArrayList<Object> list) {
        JsonArray jsonArray = new JsonArray();
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();

        for (Object d : list) {
            JsonElement ret = parser.parse(gson.toJson(d));
            jsonArray.add(ret);
        }
        return jsonArray;
    }

    public static JsonArray cursorToJson(Cursor cursor) {
        JsonArray resultSet = new JsonArray();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int totalColumn = cursor.getColumnCount();
            JsonObject rowObject = new JsonObject();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        rowObject.addProperty(cursor.getColumnName(i), cursor.getString(i));
                    } catch (Exception e) {
                        Log.d(" cursorToJson ", e.getMessage());
                    }
                }
            }
            resultSet.add(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        return resultSet;

    }

    public  static JsonUtil setObj(Object obj) {
        if (obj == null) {
            throw new NullPointerException("O Object não pode ser null");
        } else {
            JsonUtil.obj = obj;
            return with();
        }
    }

    public JsonUtil Convert(JsonConvert callback) {
        with().setCallback(callback);
        return with();
    }

    private void setCallback(JsonConvert j) {
        json = j;
        if (json != null) {
            json.asJsonArray(getArrayJson());
        }

    }

    private JsonArray getArrayJson() {

        if (obj != null && listObj != null) {
            return convertObjects(obj, listObj);
        } else if (obj != null) {
            return convertObject(obj);
        } else if (listObj != null) {
            return convertObjects(listObj);
        } else if (cursor != null) {
            return cursorToJson(cursor);
        }
        return null;

    }

    private JsonArray convertObjects(Object list0, ArrayList<Object> list1) {


        JsonArray jsonArray = new JsonArray();
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonElement je = parser.parse(gson.toJson(list0));
        jsonArray.add(je);
        jsonArray.add(convertArrayListObjectToJsonArray(list1));
        return jsonArray;

    }

    private JsonArray convertObjects(ArrayList<Object> list) {

        JsonArray jsonArray = new JsonArray();
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();

        for (Object d : list) {
            JsonElement ret = parser.parse(gson.toJson(d));
            jsonArray.add(ret);
        }
        return jsonArray;

    }

    private JsonArray convertObject(Object list0) {


        JsonArray jsonArray = new JsonArray();
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonElement je = parser.parse(gson.toJson(list0));
        jsonArray.add(je);
        return jsonArray;

    }


}
