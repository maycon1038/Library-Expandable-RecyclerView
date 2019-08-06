package com.android.msm.recycleviewexpandable.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.msm.recycleviewexpandable.interfaces.RecyclerViewOnCheckBox;
import com.android.msm.recycleviewexpandable.interfaces.RecyclerViewOnCircleProgressView;
import com.android.msm.recycleviewexpandable.interfaces.RecyclerViewOnClickListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.ArrayList;

import at.grabner.circleprogress.CircleProgressView;

import static com.android.msm.recycleviewexpandable.Util.Tag;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


    private ArrayList<Integer> listID;
    private ArrayList<Integer> listIDImg;
    private  RecyclerViewOnClickListener mRecyclerViewOnClickListener;
    private RecyclerViewOnCircleProgressView mRecyclerViewProgressView;
    private RecyclerViewOnCheckBox mRecyclerViewCheckBox;
    private ArrayList<String> nameItensDatabase;
    private ArrayList<String> nameItensDatabaseImgs;
    private Context mContext;
    private LayoutInflater mInflater;
    private JsonArray jsonArray;
    private JsonArray groupLinhaList;
    private int IdLayout;
    private Integer idCheckBox;
    private Integer idCProg;
    private  Integer idImgView;
    private CircleProgressView cpView;
    private ImageView mImgView;
    private CheckBox mcheckBox;
    ArrayList<TextView> lisTextView;


    public ArrayList<TextView> getLisTextView() {
        return lisTextView;
    }



    public ArrayList<ImageView> getLisImgView() {
        return lisImgView;
    }


    ArrayList<ImageView> lisImgView;

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

    public RecyclerAdapter(Context c, final int idLayout, JsonArray jsonArray, ArrayList<String> itensDatabase, ArrayList<Integer> list_id,
                           ArrayList<String> itensDatabaseImg, ArrayList<Integer> listIdsImg) {
        mContext = c;
        this.jsonArray = new JsonArray();
        this.jsonArray.addAll(jsonArray);
        this.groupLinhaList = new JsonArray();
        this.groupLinhaList.addAll(jsonArray);
        listID = list_id;
        IdLayout = idLayout;
        listIDImg = listIdsImg;
        nameItensDatabase = itensDatabase;
        nameItensDatabaseImgs = itensDatabaseImg;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public RecyclerAdapter(Context c, final int idLayout, JsonArray jsonArray, ArrayList<String> itensDatabase, ArrayList<Integer> list_id,
                           Integer idCheckBox, Integer idCProg, Integer idImgView) {
        mContext = c;
        this.jsonArray = new JsonArray();
        this.jsonArray.addAll(jsonArray);
        this.groupLinhaList = new JsonArray();
        this.groupLinhaList.addAll(jsonArray);
        listID = list_id;
        IdLayout = idLayout;
        nameItensDatabase = itensDatabase;
        this.idCheckBox = idCheckBox;
        this.idCProg = idCProg;
        this.idImgView = idImgView;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


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

    public void setFilterJsonArray(JsonArray json) {

        groupLinhaList = new JsonArray();

        if (json != null && !json.isJsonNull()) {
            groupLinhaList.addAll(json);
            notifyDataSetChanged();
        }
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
        this.cpView  = holder.ciProView;
        this.mImgView = holder.mImgView;
        this.lisTextView  = holder.lisTextView;
        this.mcheckBox  = holder.mCheckBox;

        if (!groupLinhaList.isJsonNull()) {
            int  index = 0;
            for (TextView v : holder.lisTextView) {
                v.setText(String.valueOf(groupLinhaList.get(position).getAsJsonObject().get(nameItensDatabase.get(index++))).replace("\"", ""));

            }

            if(cpView != null && mImgView != null){

                if (mRecyclerViewProgressView != null) {
                    mRecyclerViewProgressView.OnCircleProgressoView(cpView, mImgView,  getmJsonArray(), position);
                }
            }

            if(idCheckBox != null){

                if (mRecyclerViewCheckBox != null) {
                    mRecyclerViewCheckBox.OnCheckBox(mcheckBox,  getmJsonArray(), position);
                }
            }

        }
    }

    public void setRecyclerViewProgressView(RecyclerViewOnCircleProgressView r) {
        mRecyclerViewProgressView = r;
    }

    public void setmRecyclerViewCheckBox(RecyclerViewOnCheckBox r) {
         mRecyclerViewCheckBox  = r;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Passing the inflater job to the cursor-adapter
        View view = mInflater.inflate(IdLayout, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        ArrayList<TextView> lisTextView = new ArrayList<>();
        public CircleProgressView ciProView;
        public ImageView mImgView;
        public CheckBox mCheckBox;



        public ViewHolder(View itemView) {
            super(itemView);
            for (int id : listID) {
                lisTextView.add((TextView) itemView.findViewById(id));
            }
            if(idCProg != null && idCProg > 0) {
                ciProView = itemView.findViewById(idCProg);

            }

            if(idImgView != null && idImgView > 0) {
                mImgView  = itemView.findViewById(idImgView);

            }

            if(idCheckBox != null && idCheckBox > 0){
                mCheckBox = itemView.findViewById(idCheckBox);
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