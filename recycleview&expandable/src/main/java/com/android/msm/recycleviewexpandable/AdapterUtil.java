package com.android.msm.recycleviewexpandable;

import android.content.Context;
import android.database.Cursor;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.android.msm.recycleviewexpandable.adapters.ExpandableJsonAdapter;
import com.android.msm.recycleviewexpandable.adapters.RecyclerAdapter;
import com.android.msm.recycleviewexpandable.interfaces.AdapterExpandable;
import com.android.msm.recycleviewexpandable.interfaces.AdapterRecycleView;
import com.android.msm.recycleviewexpandable.interfaces.JsonConvert;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.HashMap;


public class AdapterUtil implements JsonConvert {

    private static HashMap<String, AdapterUtil> instances = new HashMap<String, AdapterUtil>();
    ArrayList<Integer> listID;
    ArrayList<String> ItensDatabase;
    private String name;
    private Context context;
    private com.android.msm.recycleviewexpandable.interfaces.AdapterRecycleView myFilterRecycleView;
    private com.android.msm.recycleviewexpandable.interfaces.AdapterExpandable myFilterExpandable;
    private RecyclerAdapter adapteRecycler;
    private ExpandableJsonAdapter adapterExpandable;
    private int idLayout;
    private JsonArray json;
    private int idLayoutGroup, idLayoutChild;
    private ArrayList<Integer> listIdsGrup, listIdsfilho;
    private ArrayList<String> itensGrupo, itensChild;
    private ArrayList<String> nameItensDatabaseImgs;
    private ArrayList<Integer> listIDImg;
    private Integer idCheckBox;
    private Integer idCProg;
    private  Integer idImgView;
    private Integer idRating;

    private AdapterUtil(Context context, String name) {
        this.context = context;
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

    public AdapterUtil configRecycleViewAdapter(int idLayout, ArrayList<Integer> listID, ArrayList<String> itensDatabase) {
        this.listID = listID;
        ItensDatabase = itensDatabase;
        this.idLayout = idLayout;
        return getDefault(context);
    }
    public AdapterUtil configRecycleViewAdapter(int idLayout, ArrayList<Integer> listID, ArrayList<String> itensDatabase,
                                                Integer idCheckBox, Integer idCProg, Integer idImgView, Integer idRating) {
        this.listID = listID;
        ItensDatabase = itensDatabase;
        this.idLayout = idLayout;
        this.idCheckBox = idCheckBox;
        this.idCProg = idCProg;
        this.idCProg = idCProg;
        this.idImgView = idImgView;
        this.idRating = idRating;
        return getDefault(context);
    }
    public AdapterUtil configRecycleViewAdapter(int idLayout, ArrayList<Integer> listID, ArrayList<String> itensDatabase,
                                                Integer idCheckBox, Integer idCProg, Integer idImgView) {
        this.listID = listID;
        ItensDatabase = itensDatabase;
        this.idLayout = idLayout;
        this.idCheckBox = idCheckBox;
        this.idCProg = idCProg;
        this.idCProg = idCProg;
        this.idImgView = idImgView;
        return getDefault(context);
    }

    public AdapterUtil configRecycleViewAdapter(int idLayout, ArrayList<Integer> listID, ArrayList<String> itensDatabase,
                                                ArrayList<String> itensDatabaseImg, ArrayList<Integer> listIdsImg) {
        this.listID = listID;
        ItensDatabase = itensDatabase;
        this.idLayout = idLayout;
        this.nameItensDatabaseImgs = itensDatabaseImg;
        this.listIDImg = listIdsImg;
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
        return getDefault(context);
    }


    public AdapterUtil startRecycleViewAdapter(AdapterRecycleView callback) {
        AdapterUtil ini = getDefault(context);
        ini.setRecycleViewAdapterCallback(callback);
        return ini;
    }
    public AdapterUtil startExpandableAdapter(AdapterExpandable callback) {
        AdapterUtil ini = getDefault(context);
        ini.setExpandableAdapterCallback(callback);
        return ini;
    }


    private void setExpandableAdapterCallback(AdapterExpandable callback) {
        this.myFilterExpandable= callback;
            adapterExpandable = new ExpandableJsonAdapter(context, json, listIdsGrup,
            listIdsfilho, idLayoutChild, idLayoutGroup, itensChild, itensGrupo);
        myFilterExpandable.setExpandableAdapter(adapterExpandable);

    }
    private void setRecycleViewAdapterCallback(AdapterRecycleView callback) {
        this.myFilterRecycleView = callback;

        adapteRecycler = new RecyclerAdapter(context, idLayout, json, listID, idCheckBox,idCProg, idImgView,idRating);
        myFilterRecycleView.setRecyclerAdapter(adapteRecycler);



    }

    @Override
    public void asJsonArray(JsonArray jsonArray) {
        this.json = jsonArray;
    }
}
