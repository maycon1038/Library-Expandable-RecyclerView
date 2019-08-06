package com.android.msm.recycleviewexpandable.interfaces;

import android.view.View;
import android.widget.CheckBox;

import com.google.gson.JsonArray;

public interface RecyclerViewOnClickListener {
    public void onClickListener(View view, JsonArray json, int position);
    public void onLongPressClickListener(View view,  JsonArray json, int position);
}
