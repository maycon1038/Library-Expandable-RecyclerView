package com.android.msm.searchable;

import android.content.Context;
import android.database.Cursor;

import com.android.msm.searchable.adapters.MyRecyclerAdapter;
import com.android.msm.searchable.interfaces.MyFilter;

import java.util.ArrayList;


public class SearchableFilter {

    private Context ctx;
    private MyFilter myFilter;
    private MyRecyclerAdapter adapteRecycler;
    ArrayList<Integer> listID;
    ArrayList<String> ItensDatabase;
    private int idLayout;
    private Cursor cursor;

    public SearchableFilter(Context ctx, int idLayout, ArrayList<Integer> listID, ArrayList<String> itensDatabase) {
        this.ctx = ctx;
        this.listID = listID;
        ItensDatabase = itensDatabase;
        this.idLayout = idLayout;
    }

    public SearchableFilter init(MyFilter callback, Cursor cursor) {
        SearchableFilter ini = new SearchableFilter(ctx,idLayout,listID,ItensDatabase);
        ini.setCursor(cursor);
        ini.setFilterCallback(callback);
        return  ini;
    }

    private void setFilterCallback(MyFilter callback) {
        this.myFilter = callback;
        adapteRecycler = new MyRecyclerAdapter(ctx,idLayout,cursor, ItensDatabase, listID);
        myFilter.filter(adapteRecycler);
    }

    private void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }



    }
