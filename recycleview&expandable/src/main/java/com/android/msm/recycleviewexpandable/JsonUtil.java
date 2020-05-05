package com.android.msm.recycleviewexpandable;

import android.database.Cursor;
import android.util.Log;


import com.android.msm.recycleviewexpandable.interfaces.JsonConvert;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;

public class JsonUtil {

    private  Cursor cursor;
    private  Object obj;
    private  ArrayList<Object> listObj;
    private static HashMap<String, JsonUtil> instances = new HashMap<String, JsonUtil>();
    private String name;
    private JsonConvert json;

    public JsonUtil(String name) {
        this.name = name;
    }
	public JsonUtil() {
		this.name = "JsonUtil";
	}

    public JsonUtil with() {
        return getDefault();
    }


    private JsonUtil getDefault() {
        return getInstance("JsonUtil");
    }


    private  JsonUtil getInstance(String name) {
        JsonUtil instance = instances.get(name);
        if (instance == null)
            instances.put(name, instance = new JsonUtil(name));
        return instance;
    }

    public  JsonUtil setListObj(ArrayList<Object> listObj) {
        if (listObj == null) {
            throw new NullPointerException("O ArrayList<Object> não pode ser null");
        } else {
            this.listObj = listObj;
            return with();
        }

    }

    public  JsonUtil setListObjs(ArrayList<Object> listObj, Object obj) {
        if (listObj == null) {
            throw new NullPointerException("O ArrayList<Object> e o Object não pode ser null");
        } else {
			this.listObj = listObj;
			this.obj = obj;
            return with();
        }

    }

    public   JsonUtil setCursor(Cursor cursor) {
        if (cursor == null) {
            throw new NullPointerException("O Cursor não pode ser null");
        } else {
			this.cursor = cursor;
            return with();
        }

    }


    private   JsonArray convertArrayListObjectToJsonArray(ArrayList<Object> list) {
        JsonArray jsonArray = new JsonArray();
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();

        for (Object d : list) {
            JsonElement ret = parser.parse(gson.toJson(d));
            jsonArray.add(ret);
        }
        return jsonArray;
    }

    public   JsonArray cursorToJson(Cursor cursor) {
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
		this.cursor = null;
        return resultSet;

    }

    public   ArrayList<Object> cursorToListObjs(Cursor cursor) {
        ArrayList<Object>  resultSet = new ArrayList<>() ;
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
		this.cursor = null;
        return resultSet;

    }

    public    JsonUtil setObj(Object obj) {
        if (obj == null) {
            throw new NullPointerException("O Object não pode ser null");
        } else {
			this.obj = obj;
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
        listObj =  null;
        obj = null;
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
        listObj =  null;
        return jsonArray;

    }

    private JsonArray convertObject(Object list0) {


        JsonArray jsonArray = new JsonArray();
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonElement je = parser.parse(gson.toJson(list0));
        jsonArray.add(je);
        obj = null;
        return jsonArray;

    }


}
