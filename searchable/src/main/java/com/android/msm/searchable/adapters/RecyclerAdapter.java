package com.android.msm.searchable.adapters;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.android.msm.searchable.interfaces.RecyclerViewOnClickListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import static com.android.msm.searchable.Util.Tag;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    public static CursorAdapter mCursorAdapter;
    static ArrayList<Integer> listID;
    private static RecyclerViewOnClickListener mRecyclerViewOnClickListener;
    private static ObjectAnimator mAnimator;


    private ArrayList<String> nameItensDatabase;
    private Context mContext;
    private LayoutInflater mInflater;
    private JsonArray jsonArray;
    private JsonArray groupLinhaList;
    private int IdLayout;

    public RecyclerAdapter(Context c, final int idLayout, JsonArray jsonArray, ArrayList<String> itensDatabase, ArrayList<Integer> list_id) {
        mContext = c;
        this.jsonArray = new JsonArray();
        this.jsonArray.addAll(jsonArray);
        this.groupLinhaList = new JsonArray();
        this.groupLinhaList.addAll(jsonArray);
        listID = list_id;
        IdLayout = idLayout;
        nameItensDatabase = itensDatabase;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    /*public RecyclerAdapter(Context context, final int idLayout, Cursor c, ArrayList<String> itensDatabase, ArrayList<Integer> list_id) {
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
    }*/

    private static void onEditStarted(View v) {

        if (mAnimator != null) {
            mAnimator.cancel();
        }

        mAnimator = ObjectAnimator.ofFloat(v, View.ALPHA, 0.5f, 1.0f);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.setDuration(200);
        mAnimator.start();
    }

    /*private  CursorAdapter getmCursorAdapter() {
        return mCursorAdapter;
    }*/

    public void filterData(String query) {
        query = query.toLowerCase();
        groupLinhaList = new JsonArray();

        if (query.isEmpty()) {
            groupLinhaList.addAll(jsonArray);
        } else {
            JsonArray newGroupList = new JsonArray();
            //String.valueOf(jsonArray.get(position).getAsJsonObject().get(nameItensDatabase.get(index++))).replace("\"", ""));

            for (JsonElement jsonElement : jsonArray) {
                //jsonArray.get(position).getAsJsonObject()
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                for (String value : nameItensDatabase) {
                    if (String.valueOf(jsonObject.get(value)).replace("\"", "").toLowerCase().contains(query)) {

                        newGroupList.add(jsonObject);
                    }
                }
            }
            if (newGroupList.size() > 0) {
                groupLinhaList.addAll(newGroupList);
                Tag(mContext, " groupLinhaList Teste" + groupLinhaList.toString());
            }

        } // end else

        notifyDataSetChanged();
    }

    private JsonArray getmJsonArray() {
        return groupLinhaList;
    }

    public void setRecyclerViewOnClickListenerJson(RecyclerViewOnClickListener r) {
        mRecyclerViewOnClickListener = r;
    }

    @Override
    public int getItemCount() {


            return groupLinhaList.size();

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Passing the binding operation to cursor loader
        if (!groupLinhaList.isJsonNull()) {
            int index = 0;
            for (TextView v : holder.lisTextView) {
                v.setText(
                        String.valueOf(groupLinhaList.get(position).getAsJsonObject().get(nameItensDatabase.get(index++))).replace("\"", ""));


            }
        }


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Passing the inflater job to the cursor-adapter
        View view = mInflater.inflate(IdLayout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

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

            if (mRecyclerViewOnClickListener != null) {
                mRecyclerViewOnClickListener.onClickListener(v, getmJsonArray(), getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mRecyclerViewOnClickListener != null) {
                mRecyclerViewOnClickListener.onLongPressClickListener(v, getmJsonArray(), getAdapterPosition());
            }
            return true;
        }
    }

}