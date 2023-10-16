package com.android.msm.recycleviewexpandable.interfaces;

import android.widget.TextView;

import com.google.gson.JsonArray;

import java.util.ArrayList;


public interface RecyclerViewOnListTextView {

    public void OnLisTextView(ArrayList<TextView> lisTextView, JsonArray json, int position);
}
