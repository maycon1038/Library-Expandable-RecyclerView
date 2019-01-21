package com.android.msm.searchable.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.android.msm.searchable.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import static com.android.msm.searchable.Util.Tag;


public class ExpandableJsonAdapter extends BaseExpandableListAdapter {

    private Context context;
    private JsonArray originalList, groupLinhaList;
    private ArrayList<Integer> groupTextView, childTextView;
    private int childLayout, groupLayout;
     private  ArrayList<String> itensChild;
    private  ArrayList<String> itensGroup;


    public ExpandableJsonAdapter(Context context, JsonArray originalList,
                                 ArrayList<Integer> groupTextView, ArrayList<Integer> childTextView,
                                 int childLayout, int groupLayout, ArrayList<String> itensChild, ArrayList<String> itensGroup) {
        this.context = context;
        this.groupLinhaList = new JsonArray();
        this.groupLinhaList.addAll(originalList);
        this.originalList = new JsonArray();
        this.originalList.addAll(originalList);
        this.groupTextView = groupTextView;
        this.groupTextView = new ArrayList<Integer>();
        this.childTextView = new ArrayList<Integer>();
        this.childTextView = childTextView;
        this.groupLayout = groupLayout;
        this.childLayout = childLayout;
        this.itensChild = new ArrayList<String>();
        this.itensChild = itensChild;
        this.itensGroup = new ArrayList<String>();
        this.itensGroup = itensGroup;

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
      //  ParentRow parentRow = (ParentRow) getGroup(groupPosition);
        // re = groupLinhaList.get(groupPosition).getAsJsonObject().get("title").getAsString();
        JsonObject parentRow = (JsonObject)getGroup(groupPosition);
        if (v == null) {
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(groupLayout, null);

            int position = 0;
            for ( int view :groupTextView ) {
                ((TextView) v.findViewById(view)).setText(parentRow.get(itensGroup.get(position++)).getAsString());
            }
        }


        return v;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View v, ViewGroup parent) {
       JsonObject childRow = (JsonObject) getChild(groupPosition, childPosition);
        LayoutInflater layoutInflater = (LayoutInflater)
                context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        v = layoutInflater.inflate(childLayout, null);
        int position = 0;
        for ( int view :childTextView ) {
            ((TextView) v.findViewById(view)).setText(childRow.get(itensChild.get(position++)).getAsString());
        }

//nro_radio


        return v;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void filterData(String query) {
      /*  query = query.toLowerCase();
        groupLinhaList = null;

        if (query.isEmpty()) {
            groupLinhaList.addAll(originalList);
        } else {
            int y = 0, j = 0;
            for (JsonArray parentRow : originalList) {
                JsonArray childList = parentRow.get(y).getAsJsonArray();
                JsonArray newList = new JsonArray();

                for (JsonElement childRow : childList) {
                    if (childRow.getAsJsonObject().get("teste").toString().toLowerCase().contains(query)
                            || childRow.getAsJsonObject().get("teste2").toString().toLowerCase().contains(query)
                            || childRow.getAsJsonObject().get("teste3").toString().toLowerCase().contains(query)) {
                        newList.add(childRow);
                    }
                }
                if (newList.size() > 0) {
                  *//*  ParentRow nParentRow = new ParentRow(parentRow.getName(), newList);
                    groupLinhaList.add(nParentRow);*//*
                }
            } // end or (com.example.user.searchviewexpandablelistChildview.ParentRow parentRow : originalList)
        } // end else

        notifyDataSetChanged();*/
    }


}
