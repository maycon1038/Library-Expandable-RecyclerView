package com.android.msm.searchable.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.android.msm.searchable.interfaces.RecyclerViewOnClickListenerHack;

import java.util.ArrayList;



public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    public static CursorAdapter mCursorAdapter;
    static ArrayList<Integer> listID;
    private static RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private static ObjectAnimator mAnimator;
    ArrayList<String> nameItensDatabase;
    private Context mContext;
    private LayoutInflater mInflater;


    public MyRecyclerAdapter(Context context, final int idLayout, Cursor c, ArrayList<String> itensDatabase, ArrayList<Integer> list_id) {
        mContext = context;
        listID = list_id;
        nameItensDatabase = itensDatabase;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCursorAdapter = new CursorAdapter(mContext, c, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                View view = mInflater.inflate(idLayout, parent, false);
                ViewHolder holder = new ViewHolder(view);
                view.setTag(holder);
                return view;
            }

            @Override
            public void bindView(View view, Context context, Cursor curso) {
                ViewHolder holder = (ViewHolder) view.getTag();
                int position = 0;
                for (String name : nameItensDatabase) {
                    String nome = curso.getString(curso.getColumnIndex(name));
                    holder.lisTextView.get(position++).setText(nome);
                }

            }
        };
    }

    public static CursorAdapter getmCursorAdapter() {
        return mCursorAdapter;
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
        mRecyclerViewOnClickListenerHack = r;
    }

    @Override
    public int getItemCount() {
        return mCursorAdapter.getCount();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Passing the binding operation to cursor loader
        mCursorAdapter.getCursor().moveToPosition(position);
        //EDITED: added this line as suggested in the comments below, thanks :)
        mCursorAdapter.bindView(holder.itemView, mContext, mCursorAdapter.getCursor());

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Passing the inflater job to the cursor-adapter
        View v = mCursorAdapter.newView(mContext, mCursorAdapter.getCursor(), parent);
        return new ViewHolder(v);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        ArrayList<TextView> lisTextView = new ArrayList<>();

        public ViewHolder(View itemView) {
            super(itemView);
            for (int id : listID) {
                lisTextView.add((TextView) itemView.findViewById(id));
            }
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mRecyclerViewOnClickListenerHack != null) {
                onEditStarted(v);
                mRecyclerViewOnClickListenerHack.onClickListener(v, getmCursorAdapter(), getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mRecyclerViewOnClickListenerHack != null) {
                mRecyclerViewOnClickListenerHack.onLongPressClickListener(v, getmCursorAdapter(), getAdapterPosition());
            }
            return true;
        }
    }

    private static void onEditStarted(View v) {

        if (mAnimator != null) {
            mAnimator.cancel();
        }

        mAnimator = ObjectAnimator.ofFloat(v, View.ALPHA, 0.5f,1.0f);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.setDuration(200);
        mAnimator.start();
    }

}