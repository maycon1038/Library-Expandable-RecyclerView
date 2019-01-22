package com.android.msm.searchable;

import android.content.Context;
import android.database.Cursor;

import com.android.msm.searchable.adapters.ExpandableJsonAdapter;
import com.android.msm.searchable.adapters.RecyclerAdapter;
import com.android.msm.searchable.interfaces.JsonConvert;
import com.android.msm.searchable.interfaces.adapter;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.HashMap;


public class Adapters implements JsonConvert {

    private static boolean isConfigExpandableAdapter = false;
    private static boolean isConfigRecycleAdapter = false;
    private static HashMap<String, Adapters> instances = new HashMap<String, Adapters>();
    ArrayList<Integer> listID;
    ArrayList<String> ItensDatabase;
    private String name;
    private Context context;
    private adapter myFilter;
    private RecyclerAdapter adapteRecycler;
    private ExpandableJsonAdapter adapterExpandable;
    private int idLayout;
    private Cursor cursor;
    private JsonArray json;
    private int idLayoutGroup, idLayoutChild;
    private ArrayList<Integer> listIdsGrup, listIdsfilho;
    private ArrayList<String> itensGrupo, itensChild;

    private Adapters(Context context, String name) {
        this.context = context = context.getApplicationContext();
        this.name = name;

    }

    public Adapters(int idLayout, ArrayList<Integer> listID, ArrayList<String> itensDatabase) {
        this.listID = listID;
        ItensDatabase = itensDatabase;
        this.idLayout = idLayout;
    }

    public static Adapters with(Context context) {
        return getDefault(context);
    }

    private static Adapters getDefault(Context context) {
        return getInstance(context, "Adapters");
    }

    private static Adapters getInstance(Context context, String name) {
        if (context == null) {
            throw new NullPointerException("O Context não pode ser null");
        } else {
            Adapters instance = instances.get(name);
            if (instance == null)
                instances.put(name, instance = new Adapters(context, name));
            return instance;
        }
    }

    public Adapters setJson(JsonArray json) {
        this.json = json;
        return getDefault(context);
    }


    public Adapters setCursor(Cursor cursor) {
        JsonUtil.setCursor(cursor).Convert(this);
        return getDefault(context);
    }

  public Adapters setObjects( Object object) {
        JsonUtil.setObj(object).Convert(this);
        return getDefault(context);
    }
    public Adapters setObjects(ArrayList<Object>  objects) {
        JsonUtil.setListObj(objects).Convert(this);
        return getDefault(context);
    }

    public Adapters configRecycleViewAdapter(int idLayout, ArrayList<Integer> listID, ArrayList<String> itensDatabase) {
        this.listID = listID;
        ItensDatabase = itensDatabase;
        this.idLayout = idLayout;
        isConfigRecycleAdapter = true;
        return getDefault(context);
    }

    public Adapters configExpandableAdapter(int idLayoutGroup, int idLayoutChild,
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

    public Adapters start(adapter callback) {
        Adapters ini = getDefault(context);
        ini.setFilterCallback(callback);
        return ini;
    }



    private void setFilterCallback(adapter callback) {
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
