package com.android.msm.recycleviewexpandable.interfaces;

import android.widget.CheckBox;
import android.widget.ImageView;

import com.google.gson.JsonArray;

import at.grabner.circleprogress.CircleProgressView;


public interface RecyclerViewOnCheckBox {

    public void OnCheckBox(CheckBox checkBox, JsonArray json, int position);
}
