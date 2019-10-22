package com.android.msm.recycleviewexpandable.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.android.msm.recycleviewexpandable.R;
import com.android.msm.recycleviewexpandable.interfaces.mExpandableChildViewOnListTextView;
import com.android.msm.recycleviewexpandable.interfaces.mExpandableGroupViewOnListTextView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.android.msm.recycleviewexpandable.Util.Tag;


public class ExpandableJsonAdapter extends BaseExpandableListAdapter {

    private Context context;
    private JsonArray originalList, groupLinhaList;
    private ArrayList<Integer> groupTextView, childTextView;
    private int childLayout, groupLayout;
    private mExpandableGroupViewOnListTextView mGroupViewListTextView;
    private mExpandableChildViewOnListTextView mChildViewListTextView;
    private int themeText1, themeText2;
    public ExpandableJsonAdapter(Context context, int themeText1, int themeText2, JsonArray originalList,
                                 ArrayList<Integer> groupTextView, ArrayList<Integer> childTextView,
                                 int childLayout, int groupLayout) {
        this.context = context;
        this.themeText1 = themeText1;
        this.themeText2 = themeText2;
        this.groupLinhaList = new JsonArray();
        this.groupLinhaList.addAll(originalList);
        this.originalList = new JsonArray();
        this.originalList.addAll(originalList);


        this.groupTextView = new ArrayList<Integer>();
        this.groupTextView = groupTextView;
        this.childTextView = new ArrayList<Integer>();
        this.childTextView = childTextView;
        this.groupLayout = groupLayout;
        this.childLayout = childLayout;

    }


    @Override
    public int getGroupCount() {
        Tag(context, " getGroupCount " + groupLinhaList.size());
        return groupLinhaList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Tag(context, " getChildrenCount " +groupLinhaList.get(groupPosition).getAsJsonArray().get(1).getAsJsonArray().size());
        return groupLinhaList.get(groupPosition).getAsJsonArray().get(1).getAsJsonArray().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        Tag(context, " getGroup " + groupLinhaList.get(groupPosition).getAsJsonArray());
        return groupLinhaList.get(groupPosition).getAsJsonArray().get(0).getAsJsonObject();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        Tag(context, " getChild " +groupLinhaList.get(groupPosition).getAsJsonArray()
                .get(1).getAsJsonArray().get(childPosition).getAsJsonObject());
        return groupLinhaList.get(groupPosition).getAsJsonArray().get(1).getAsJsonArray().get(childPosition).getAsJsonObject();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View v, ViewGroup parent) {
        JsonObject parentRow = (JsonObject) getGroup(groupPosition);
        LayoutInflater layoutInflater = (LayoutInflater)  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = layoutInflater.inflate(groupLayout, null);

        ArrayList<TextView> listTextView = new ArrayList<>();

        for ( int view : groupTextView) {

            TextView  tv = v.findViewById(view);
            tv.setTextAppearance(context,themeText1);

            listTextView.add(tv);
        }


      /*  int position = 0;
        for ( int view :groupTextView ) {
            ((TextView) v.findViewById(view)).setText(
                    String.valueOf(parentRow.get(itensGroup.get(position++))).replace("\"", "")
            );

        }*/

        if (groupTextView != null) {

            if (mGroupViewListTextView != null) {
                mGroupViewListTextView.OnGroupLisTextView(listTextView, parentRow);
            }
        }

        return v;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View v, ViewGroup parent) {
        JsonObject childRow = (JsonObject) getChild(groupPosition, childPosition);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = layoutInflater.inflate(childLayout, null);

        ArrayList<TextView> listTextView = new ArrayList<>();

        for ( int view : childTextView) {

            TextView  tv = v.findViewById(view);
            tv.setTextAppearance(context, themeText2);

            listTextView.add(tv);
        }


        if (childTextView != null) {

            if (mChildViewListTextView != null) {
                mChildViewListTextView.OnChildLisTextView(listTextView, childRow);
            }
        }



     /*   for ( int view :childTextView ) {
            ((TextView) v.findViewById(view)).setText(
                    String.valueOf(childRow.get(itensChild.get(position++))).replace("\"", ""));
        }
*/


        return v;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void filterData(String query) {

        if(query != null){
            query = query.toLowerCase();
        }

        groupLinhaList = new JsonArray();

        if (query == null || query.isEmpty()) {
            groupLinhaList.addAll(originalList);
        } else {
            JsonArray newGroupList = new JsonArray();
            //String.valueOf(jsonArray.get(position).getAsJsonObject().get(nameItensDatabase.get(index++))).replace("\"", ""));

            for (JsonElement jsonElement : originalList) {
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
                Tag(context, " groupLinhaList Teste" + groupLinhaList.toString());
            }

        } // end else

        notifyDataSetChanged();
    }


    public void setmGroupViewListTextView(mExpandableGroupViewOnListTextView l) {
        mGroupViewListTextView = l;
    }


    public void setmChildViewListTextView(mExpandableChildViewOnListTextView l) {
        mChildViewListTextView = l;
    }

}
