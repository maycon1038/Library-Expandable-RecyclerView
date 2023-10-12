package com.example.expandablerecyclerview;

import android.content.Context;
import android.database.Cursor;

import com.example.expandablerecyclerview.adapters.ExpandableJsonAdapter;
import com.example.expandablerecyclerview.adapters.RecyclerAdapter;
import com.example.expandablerecyclerview.interfaces.AdapterExpandable;
import com.example.expandablerecyclerview.interfaces.AdapterRecycleView;
import com.example.expandablerecyclerview.interfaces.JsonConvert;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.HashMap;


public class AdapterUtil implements JsonConvert {

    private static HashMap<String, AdapterUtil> instances = new HashMap<String, AdapterUtil>();
    ArrayList<Integer> listID;
    private String name;
    private Context context;
    private AdapterRecycleView myFilterRecycleView;
    private AdapterExpandable myFilterExpandable;
    private RecyclerAdapter adapteRecycler;
    private ExpandableJsonAdapter adapterExpandable;
    private int idLayout;
    private JsonArray json;
    private int idLayoutGroup, idLayoutChild;
    private ArrayList<Integer> listIdsGrup, listIdsfilho;
    private ArrayList<String> itensGrupo, itensChild;
    private Integer idCheckBox;
    private Integer idCProg;
    private Integer idImgView;
    private Integer idRating;
    private Integer idYoutube;
    private AdapterUtil(Context context, String name) {
        this.context = context;
        this.name = name;

    }

    public AdapterUtil(int idLayout, ArrayList<Integer> listID, ArrayList<String> itensDatabase) {
        this.listID = listID;
        this.idLayout = idLayout;
    }

    public static AdapterUtil with(Context context) {
        return getDefault(context);
    }

    private static AdapterUtil getDefault(Context context) {
        return getInstance(context, "AdapterUtil");
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

    public AdapterUtil setObjects(ArrayList<Object> objects, Object object) {
        JsonUtil.setListObjs(objects, object).Convert(this);
        return getDefault(context);
    }

    public AdapterUtil setCursor(Cursor cursor) {
        JsonUtil.setCursor(cursor).Convert(this);
        return getDefault(context);
    }

    public AdapterUtil setObjects(Object object) {
        JsonUtil.setObj(object).Convert(this);
        return getDefault(context);
    }

    public AdapterUtil setObjects(ArrayList<Object> objects) {
        JsonUtil.setListObj(objects).Convert(this);
        return getDefault(context);
    }

    public AdapterUtil configRecycleViewAdapter(int idLayout, ArrayList<Integer> listID) {
        this.listID = listID;
        this.idLayout = idLayout;
        return getDefault(context);
    }

    public AdapterUtil configRecycleViewAdapter(int idLayout, ArrayList<Integer> listID,
                                                Integer idCheckBox, Integer idCProg, Integer idImgView, Integer idRating) {
        this.listID = listID;
        this.idLayout = idLayout;
        this.idCheckBox = idCheckBox;
        this.idCProg = idCProg;
        this.idCProg = idCProg;
        this.idImgView = idImgView;
        this.idRating = idRating;
        return getDefault(context);
    }



    public AdapterUtil configRecycleViewAdapter(int idLayout, ArrayList<Integer> listID,
                                                Integer idCheckBox, Integer idCProg, Integer idImgView) {
        this.listID = listID;
        this.idLayout = idLayout;
        this.idCheckBox = idCheckBox;
        this.idCProg = idCProg;
        this.idImgView = idImgView;
        return getDefault(context);
    }


    public AdapterUtil configExpandableAdapter(int idLayoutGroup, int idLayoutChild,
                                               ArrayList<Integer> listIdsGrup, ArrayList<Integer> listIdsfilho) {
        this.idLayoutGroup = idLayoutGroup;
        this.idLayoutChild = idLayoutChild;
        this.listIdsGrup = new ArrayList<Integer>();
        this.listIdsGrup = listIdsGrup;
        this.listIdsfilho = new ArrayList<Integer>();
        this.listIdsfilho = listIdsfilho;
        return getDefault(context);
    }


    public AdapterUtil startRecycleViewAdapter(AdapterRecycleView callback) {
        AdapterUtil ini = getDefault(context);
        if (idYoutube == null) {
            ini.setRecycleViewAdapterCallback(callback);
        } else {
            ini.setmRecycleViewAdapterCallback(callback);
        }

        return ini;
    }


    public AdapterUtil startExpandableAdapter(AdapterExpandable callback) {
        AdapterUtil ini = getDefault(context);
        ini.setExpandableAdapterCallback(callback);
        return ini;
    }


    private void setExpandableAdapterCallback(AdapterExpandable callback) {
        this.myFilterExpandable = callback;
        adapterExpandable = new ExpandableJsonAdapter(context, json, listIdsGrup,
                listIdsfilho, idLayoutChild, idLayoutGroup);
        myFilterExpandable.setExpandableAdapter(adapterExpandable);

    }

    private void setRecycleViewAdapterCallback(AdapterRecycleView callback) {
        this.myFilterRecycleView = callback;

        adapteRecycler = new RecyclerAdapter(context, idLayout, json, listID, idCheckBox, idCProg, idImgView, idRating);
        myFilterRecycleView.setRecyclerAdapter(adapteRecycler);

    }

    private void setmRecycleViewAdapterCallback(AdapterRecycleView callback) {
        this.myFilterRecycleView = callback;
        adapteRecycler = new RecyclerAdapter(context, idLayout, json, listID, idCheckBox, idCProg, idImgView, idRating, idYoutube);
        myFilterRecycleView.setRecyclerAdapter(adapteRecycler);


    }


    @Override
    public void asJsonArray(JsonArray jsonArray) {
        this.json = jsonArray;
    }


}
