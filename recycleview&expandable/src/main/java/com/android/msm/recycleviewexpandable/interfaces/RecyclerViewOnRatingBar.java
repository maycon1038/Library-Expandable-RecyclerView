package com.example.expandablerecyclerview.interfaces;

import android.widget.RatingBar;

import com.google.gson.JsonArray;


public interface RecyclerViewOnRatingBar {

    public void OnCheckBox(RatingBar ratingBar, JsonArray json, int position);
}
