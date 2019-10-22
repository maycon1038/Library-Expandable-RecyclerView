package com.android.msm.recycleviewexpandable.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.msm.recycleviewexpandable.interfaces.RecyclerViewOnCheckBox;
import com.android.msm.recycleviewexpandable.interfaces.RecyclerViewOnCircleProgressView;
import com.android.msm.recycleviewexpandable.interfaces.RecyclerViewOnClickListener;
import com.android.msm.recycleviewexpandable.interfaces.RecyclerViewOnListTextView;
import com.android.msm.recycleviewexpandable.interfaces.RecyclerViewOnRatingBar;
import com.android.msm.recycleviewexpandable.interfaces.RecyclerViewYoutube;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import at.grabner.circleprogress.CircleProgressView;

import static com.android.msm.recycleviewexpandable.Util.Tag;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


    ArrayList<TextView> lisTextView;
    private Integer idRating;
    private ArrayList<Integer> listID;
    private RecyclerViewOnClickListener mRecyclerViewOnClickListener;
    private RecyclerViewOnCircleProgressView mRecyclerViewProgressView;
    private RecyclerViewOnCheckBox mRecyclerViewCheckBox;
    private RecyclerViewOnRatingBar mRecyclerViewRatingBar;
    private RecyclerViewYoutube  mRecyclerViewYouTube;
    private RecyclerViewOnListTextView mRecyclerViewListTextView;
    private Context mContext;
    private LayoutInflater mInflater;
    private JsonArray jsonArray;
    private JsonArray groupLinhaList;
    private int IdLayout;
    private Integer idCheckBox;
    private Integer idCProg;
    private Integer idImgView;
    private CircleProgressView cpView;
    private ImageView mImgView;
    private CheckBox mcheckBox;
    private RatingBar mRatingBar;
    private YouTubePlayerView mYouTubePlayerView;
    private int themeText1, themeText2;
    private Integer iDyouTube;
    public RecyclerAdapter(Context c,int themeText1, int themeText2,  int idLayout, JsonArray jsonArray,  ArrayList<Integer> list_id) {
        mContext = c;
        this.themeText1 = themeText1;
        this.themeText2 = themeText2;
        this.jsonArray = new JsonArray();
        this.jsonArray.addAll(jsonArray);
        this.groupLinhaList = new JsonArray();
        this.groupLinhaList.addAll(jsonArray);
        listID = list_id;
        IdLayout = idLayout;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    public RecyclerAdapter(Context c,int themeText1, int themeText2,  int idLayout, JsonArray jsonArray,  ArrayList<Integer> list_id,
                           Integer idCheckBox, Integer idCProg, Integer idImgView, Integer idRating) {
        mContext = c;
        this.themeText1 = themeText1;
        this.themeText2 = themeText2;
        this.jsonArray = new JsonArray();
        this.jsonArray.addAll(jsonArray);
        this.groupLinhaList = new JsonArray();
        this.groupLinhaList.addAll(jsonArray);
        listID = list_id;
        IdLayout = idLayout;
        this.idCheckBox = idCheckBox;
        this.idCProg = idCProg;
        this.idImgView = idImgView;
        this.idRating = idRating;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    public RecyclerAdapter(Context c,int themeText1, int themeText2,  int idLayout, JsonArray jsonArray,  ArrayList<Integer> list_id,
                           Integer idCheckBox, Integer idCProg, Integer idImgView, Integer idRating, Integer iDyouTube) {
        mContext = c;
        this.themeText1 = themeText1;
        this.themeText2 = themeText2;
        this.jsonArray = new JsonArray();
        this.jsonArray.addAll(jsonArray);
        this.groupLinhaList = new JsonArray();
        this.groupLinhaList.addAll(jsonArray);
        listID = list_id;
        IdLayout = idLayout;
        this.idCheckBox = idCheckBox;
        this.idCProg = idCProg;
        this.idImgView = idImgView;
        this.idRating = idRating;
        this.iDyouTube = iDyouTube;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void filterData(String query) {

          if(query != null){
              query = query.toLowerCase();
          }

        groupLinhaList = new JsonArray();

        if (query == null || query.isEmpty()) {
            groupLinhaList.addAll(jsonArray);
        } else {
            JsonArray newGroupList = new JsonArray();
            //String.valueOf(jsonArray.get(position).getAsJsonObject().get(nameItensDatabase.get(index++))).replace("\"", ""));

            for (JsonElement jsonElement : jsonArray) {
                //jsonArray.get(position).getAsJsonObject()
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                Log.d("JsonKey ", jsonObject.toString());

                Set<Map.Entry<String, JsonElement>> jsr = jsonObject.entrySet();

                Iterator<Map.Entry<String, JsonElement>> jsi = jsr.iterator();

                for (int i = 0; i < jsonObject.size(); i++) {
                    if(jsi.hasNext()){
                        Map.Entry<String, JsonElement> r = jsi.next();
                        Log.d("JsonKey1 ", r.getValue().toString() + " q " + query);
                    if (r.getValue().toString().replace("\"", "").toLowerCase().contains(query)) {
                        Log.d("JsonKey2 ", jsonObject.toString());
                        newGroupList.add(jsonObject);
                    }

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
        this.cpView = holder.ciProView;
        this.mImgView = holder.mImgView;
        this.lisTextView = holder.lisTextView;
        this.mcheckBox = holder.mCheckBox;
        this.mRatingBar = holder.mRatingBar;
        this.mYouTubePlayerView = holder.mYouTubePlayerView;

        if (!groupLinhaList.isJsonNull()) {

            if (cpView != null && mImgView != null) {

                if (mRecyclerViewProgressView != null) {
                    mRecyclerViewProgressView.OnCircleProgressoView(cpView, mImgView, getmJsonArray(), position);
                }
            }

            if (mcheckBox != null) {

                if (mRecyclerViewCheckBox != null) {
                    mRecyclerViewCheckBox.OnCheckBox(mcheckBox, getmJsonArray(), position);
                }
            }
            if (mRatingBar != null) {

                if (mRecyclerViewRatingBar != null) {
                    mRecyclerViewRatingBar.OnCheckBox(mRatingBar, getmJsonArray(), position);
                }
            }
            if (mYouTubePlayerView != null) {

                if (mRecyclerViewYouTube != null) {
                    mRecyclerViewYouTube.OnYouTubePlayerView(mYouTubePlayerView, getmJsonArray(), position);
                }
            }

            if (lisTextView != null) {

                if (mRecyclerViewListTextView != null) {
                    mRecyclerViewListTextView.OnLisTextView(lisTextView, getmJsonArray(), position);
                }
            }

        }
    }

    public void setRecyclerViewProgressView(RecyclerViewOnCircleProgressView r) {
        mRecyclerViewProgressView = r;
    }

    public void setmRecyclerViewYouTube(RecyclerViewYoutube y) {
        mRecyclerViewYouTube = y;
    }

    public void setmRecyclerViewListTextView(RecyclerViewOnListTextView l) {
        mRecyclerViewListTextView = l;
    }

    public void setmRecyclerViewRatingBar(RecyclerViewOnRatingBar r) {
        mRecyclerViewRatingBar = r;
    }

    public void setmRecyclerViewCheckBox(RecyclerViewOnCheckBox r) {
        mRecyclerViewCheckBox = r;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Passing the inflater job to the cursor-adapter
        View view = mInflater.inflate(IdLayout, parent, false);
        return new ViewHolder(view);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public RatingBar mRatingBar;
        public CircleProgressView ciProView;
        public YouTubePlayerView mYouTubePlayerView;
        public ImageView mImgView;
        public CheckBox mCheckBox;
        ArrayList<TextView> lisTextView = new ArrayList<>();


        public ViewHolder(View itemView) {
            super(itemView);
            for (int id : listID) {
                TextView  tv =   itemView.findViewById(id);
                tv.setTextAppearance(mContext,themeText1);
                lisTextView.add(tv);
            }
            if (idCProg != null && idCProg > 0) {
                ciProView = itemView.findViewById(idCProg);

            }

            if (iDyouTube != null && iDyouTube > 0) {
                mYouTubePlayerView = itemView.findViewById(iDyouTube);

            }

            if (idImgView != null && idImgView > 0) {
                mImgView = itemView.findViewById(idImgView);

            }

            if (idCheckBox != null && idCheckBox > 0) {
                mCheckBox = itemView.findViewById(idCheckBox);
            }
            if (idRating != null && idRating > 0) {
                mRatingBar = itemView.findViewById(idRating);
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