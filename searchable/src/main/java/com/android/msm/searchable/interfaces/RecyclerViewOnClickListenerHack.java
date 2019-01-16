package com.android.msm.searchable.interfaces;

import android.view.View;
import android.widget.CursorAdapter;

public interface RecyclerViewOnClickListenerHack {
    public void onClickListener(View view, CursorAdapter cursorAdapter, int position);
    public void onLongPressClickListener(View view, CursorAdapter cursorAdapter, int position);
}
