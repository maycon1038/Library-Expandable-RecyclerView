package com.android.msm.searchable;

import android.content.Context;
import android.database.Cursor;

import com.android.msm.searchable.adapters.ExpandableJsonAdapter;
import com.android.msm.searchable.adapters.RecyclerAdapter;
import com.android.msm.searchable.interfaces.JsonConvert;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.HashMap;


public class AdapterUtil implements JsonConvert {

    private static boolean isConfigExpandableAdapter = false;
    private static boolean isConfigRecycleAdapter = false;
    private static HashMap<String, AdapterUtil> instances = new HashMap<String, AdapterUtil>();
    ArrayList<Integer> listID;
    ArrayList<String> ItensDatabase;
    private String name;
    private Context context;
    private com.android.msm.searchable.interfaces.Adapters myFilter;
    private RecyclerAdapter adapteRecycler;
    private ExpandableJsonAdapter adapterExpandable;
    private int idLayout;
    private Cursor cursor;
    private JsonArray json;
    private int idLayoutGroup, idLayoutChild;
    private ArrayList<Integer> listIdsGrup, listIdsfilho;
    private ArrayList<String> itensGrupo, itensChild;

    private AdapterUtil(Context context, String name) {
        this.context = context = context.getApplicationContext();
        this.name = name;

    }

    public AdapterUtil(int idLayout, ArrayList<Integer> listID, ArrayList<String> itensDatabase) {
        this.listID = listID;
        ItensDatabase = itensDatabase;
        this.idLayout = idLayout;
    }

    public static AdapterUtil with(Context context) {
        return getDefault(context);
    }

    private static AdapterUtil getDefault(Context context) {
        return getInstance(context, context.getClass().getSimpleName());
    }

    private static AdapterUtil getInstance(Context context, String name) {
        if (context == null) {
            throw new NullPointerException("O Context não pode ser null");
        } else {
            AdapterUtil instance = instances.get(name);
            if (instance == null)
                instances.put(name, instance = new AdapterUtil(context, name));
            return instance;
        }
    }

    public AdapterUtil setJson(JsonArray json) {
        this.json = json;
        return getDefault(context);
    }


    public AdapterUtil setCursor(Cursor cursor) {
        JsonUtil json = new JsonUtil();
        json.setCursor(cursor);
        json.Convert(this);
        return getDefault(context);
    }

  public AdapterUtil setObjects(Object object) {
      JsonUtil json = new JsonUtil();
      json.setObj(object);
      json.Convert(this);
        return getDefault(context);
    }
    public AdapterUtil setObjects(ArrayList<Object>  objects) {
        JsonUtil json = new JsonUtil();
        json.setListObj(objects);
        json.Convert(this);
        return getDefault(context);
    }

    public AdapterUtil configRecycleViewAdapter(int idLayout, ArrayList<Integer> listID, ArrayList<String> itensDatabase) {
        this.listID = listID;
        ItensDatabase = itensDatabase;
        this.idLayout = idLayout;
        isConfigRecycleAdapter = true;
        return getDefault(context);
    }

    public AdapterUtil configExpandableAdapter(int idLayoutGroup, int idLayoutChild,
                                               ArrayList<Integer> listIdsGrup, ArrayList<Integer> listIdsfilho,
                                               ArrayList<String> itensGrupo, ArrayList<String> itensChild) {
        this.idLayoutGroup = idLayoutGroup;
        this.idLayoutChild = idLayoutChild;
        this.listIdsGrup = new ArrayList<Integer>();
        this.listIdsGrup = listIdsGrup;
        this.listIdsfilho = new ArrayList<Integer>();
        this.listIdsfilho = listIdsfilho;
        this.itensGrupo = new ArrayList<String>();
        this.itensGrupo = itensGrupo;
        this.itensChild = new ArrayList<String>();
        this.itensChild = itensChild;
        isConfigExpandableAdapter = true;
        return getDefault(context);
    }

    public AdapterUtil start(com.android.msm.searchable.interfaces.Adapters callback) {
        AdapterUtil ini = getDefault(context);
        ini.setFilterCallback(callback);
        return ini;
    }



    private void setFilterCallback(com.android.msm.searchable.interfaces.Adapters callback) {
        this.myFilter = callback;
        if (isConfigExpandableAdapter) {
            adapterExpandable = new ExpandableJsonAdapter(context,json,listIdsGrup,
                    listIdsfilho,idLayoutChild,idLayoutGroup,itensChild,itensGrupo);
            myFilter.seAdapter(adapterExpandable);
        } else if(isConfigRecycleAdapter){
            adapteRecycler = new RecyclerAdapter(context, idLayout, json, ItensDatabase, listID);
            myFilter.seAdapter(adapteRecycler);
        }

    }

    @Override
    public void asJsonArray(JsonArray jsonArray) {
        json = jsonArray;
    }
}
