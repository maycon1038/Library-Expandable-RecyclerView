package com.example.expandablerecyclerview.interfaces;

import android.widget.CheckBox;

import com.google.gson.JsonArray;
public interface RecyclerViewOnCheckBox {

    public void OnCheckBox(CheckBox checkBox, JsonArray json, int position);
}
