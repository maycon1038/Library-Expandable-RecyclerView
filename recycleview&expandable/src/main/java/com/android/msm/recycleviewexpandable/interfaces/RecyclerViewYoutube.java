package com.android.msm.recycleviewexpandable.interfaces;

import android.widget.RatingBar;

import com.google.gson.JsonArray;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;


public interface RecyclerViewYoutube {

    public void OnYouTubePlayerView(YouTubePlayerView youTubePlayerView, JsonArray json, int position);
}
