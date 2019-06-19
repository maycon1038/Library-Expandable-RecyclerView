package com.android.msm.recycleviewexpandable.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import static com.android.msm.recycleviewexpandable.Util.Tag;


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


        this.groupTextView = new ArrayList<Integer>();
        this.groupTextView = groupTextView;
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
        JsonObject parentRow = (JsonObject)getGroup(groupPosition);
        LayoutInflater layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = layoutInflater.inflate(groupLayout, null);
        int position = 0;
        for ( int view :groupTextView ) {
            ((TextView) v.findViewById(view)).setText(
                    String.valueOf(parentRow.get(itensGroup.get(position++))).replace("\"", "")
            );

        }

        return v;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View v, ViewGroup parent) {
        JsonObject childRow = (JsonObject) getChild(groupPosition, childPosition);
        LayoutInflater layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = layoutInflater.inflate(childLayout, null);
        int position = 0;
        for ( int view :childTextView ) {
            ((TextView) v.findViewById(view)).setText(
                    String.valueOf(childRow.get(itensChild.get(position++))).replace("\"", ""));
        }



        return v;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void filterData(String query) {
        query = query.toLowerCase();
        groupLinhaList = new JsonArray();

        if (query.isEmpty()) {
            groupLinhaList.addAll(originalList);
        } else {
            JsonArray newGroupList = new JsonArray();
            //groupLinhaList.get(groupPosition).getAsJsonArray().get(0).getAsJsonObject()
            //groupLinhaList.get(groupPosition).getAsJsonArray().get(1).getAsJsonArray().get(childPosition).getAsJsonObject()
            for(JsonElement jsonElement : originalList) {
                JsonObject jsonObject = jsonElement.getAsJsonArray().get(0).getAsJsonObject();
                JsonArray jsonQuery = jsonElement.getAsJsonArray().get(1).getAsJsonArray();
                JsonArray newChildList = new JsonArray();
                for (JsonElement childRow : jsonQuery) {
                    for (String value : itensChild) {
                        if (String.valueOf(childRow.getAsJsonObject().get(value)).replace("\"", "").toLowerCase().contains(query)) {

                            newChildList.add(childRow);
                        }
                    }
                }
                if (newChildList.size() > 0) {
                    newGroupList.add(jsonObject);
                    newGroupList.add(newChildList);
                    Tag(context, " groupLinhaList Teste" + groupLinhaList.toString());
                }
            }
            if(newGroupList.size() >0){
                groupLinhaList.add(newGroupList);
            }

        } // end else

        notifyDataSetChanged();
    }

}
