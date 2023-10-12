package com.example.expandablerecyclerview.interfaces;

import android.widget.ImageView;

import com.google.gson.JsonArray;

import at.grabner.circleprogress.CircleProgressView;


public interface RecyclerViewOnCircleProgressView {

    public void OnCircleProgressoView(CircleProgressView circleProgressView, ImageView imageView, JsonArray json, int position);
}
