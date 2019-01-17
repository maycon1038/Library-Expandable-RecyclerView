package com.android.msm.searchable.adapters;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.android.msm.searchable.interfaces.RecyclerViewOnClickListenerCursor;
import com.android.msm.searchable.interfaces.RecyclerViewOnClickListenerJson;
import com.google.gson.JsonArray;

import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    public static CursorAdapter mCursorAdapter;
    static ArrayList<Integer> listID;
    private static RecyclerViewOnClickListenerCursor mRecyclerViewOnClickListenerCursor;
    private static RecyclerViewOnClickListenerJson mRecyclerViewOnClickListenerJson;
    private static ObjectAnimator mAnimator;




    private ArrayList<String> nameItensDatabase;
    private Context mContext;
    private LayoutInflater mInflater;
    public static  JsonArray jsonArray;
    private int IdLayout;


    public RecyclerAdapter(Context context, final int idLayout, Cursor c, ArrayList<String> itensDatabase, ArrayList<Integer> list_id) {
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

    public RecyclerAdapter(Context c,  final int idLayout,JsonArray jsonArray, ArrayList<String> itensDatabase, ArrayList<Integer> list_id) {
        mContext = c;
        this.jsonArray = jsonArray;
        listID = list_id;
        IdLayout = idLayout;
        nameItensDatabase = itensDatabase;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static CursorAdapter getmCursorAdapter() {
        return mCursorAdapter;
    }

    public static JsonArray getmJsonArray() {
        return jsonArray;
    }

    private static void onEditStarted(View v) {

        if (mAnimator != null) {
            mAnimator.cancel();
        }

        mAnimator = ObjectAnimator.ofFloat(v, View.ALPHA, 0.5f, 1.0f);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.setDuration(200);
        mAnimator.start();
    }

    public void setRecyclerViewOnClickListenerCursor(RecyclerViewOnClickListenerCursor r) {
        mRecyclerViewOnClickListenerCursor = r;
    }

    public void setRecyclerViewOnClickListenerJson(RecyclerViewOnClickListenerJson r) {
        mRecyclerViewOnClickListenerJson = r;
    }

    @Override
    public int getItemCount() {

        if(mCursorAdapter != null){
            return mCursorAdapter.getCount();
        }else{
            return jsonArray.size();
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Passing the binding operation to cursor loader
        if(mCursorAdapter != null){
            mCursorAdapter.getCursor().moveToPosition(position);
            //EDITED: added this line as suggested in the comments below, thanks :)
            mCursorAdapter.bindView(holder.itemView, mContext, mCursorAdapter.getCursor());
        }else if(!jsonArray.isJsonNull()){
            int index = 0;
            for (TextView v : holder.lisTextView) {
                v.setText(
   String.valueOf(jsonArray.get(position).getAsJsonObject().get(nameItensDatabase.get(index++))).replace("\"", ""));


            }
        }


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Passing the inflater job to the cursor-adapter
          if(mCursorAdapter != null) {
              View v = mCursorAdapter.newView(mContext, mCursorAdapter.getCursor(), parent);

              return new ViewHolder(v);
          }else{
              View view = mInflater.inflate(IdLayout, parent, false);
              ViewHolder holder = new ViewHolder(view);
              return holder;
          }
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
            if (mRecyclerViewOnClickListenerCursor != null) {
                onEditStarted(v);
                mRecyclerViewOnClickListenerCursor.onClickListener(v, getmCursorAdapter(), getAdapterPosition());
            }
            if (mRecyclerViewOnClickListenerJson != null) {
                onEditStarted(v);
                mRecyclerViewOnClickListenerJson.onClickListener(v, getmJsonArray(), getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mRecyclerViewOnClickListenerCursor != null) {
                mRecyclerViewOnClickListenerCursor.onLongPressClickListener(v, getmCursorAdapter(), getAdapterPosition());
            }
            return true;
        }
    }

}