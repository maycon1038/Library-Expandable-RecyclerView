package com.example.expandablerecyclerview.adapters;

import static com.example.expandablerecyclerview.Util.Tag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.expandablerecyclerview.interfaces.mExpandableChildViewOnListTextView;
import com.example.expandablerecyclerview.interfaces.mExpandableGroupViewOnListTextView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class ExpandableJsonAdapter extends BaseExpandableListAdapter {

    private Context context;
    private JsonArray jsonArray, newJsonArray;
    private ArrayList<Integer> groupTextView, childTextView;
    private int childLayout, groupLayout;
    private mExpandableGroupViewOnListTextView mGroupViewListTextView;
    private mExpandableChildViewOnListTextView mChildViewListTextView;

    public ExpandableJsonAdapter(Context context, JsonArray jsonArray,
                                 ArrayList<Integer> groupTextView, ArrayList<Integer> childTextView,
                                 int childLayout, int groupLayout) {
        this.context = context;
        this.newJsonArray = new JsonArray();
        this.newJsonArray.addAll(jsonArray);
        this.jsonArray = new JsonArray();
        this.jsonArray.addAll(jsonArray);


        this.groupTextView = new ArrayList<Integer>();
        this.groupTextView = groupTextView;
        this.childTextView = new ArrayList<Integer>();
        this.childTextView = childTextView;
        this.groupLayout = groupLayout;
        this.childLayout = childLayout;

    }


    @Override
    public int getGroupCount() {
        Tag(context, " getGroupCount " + newJsonArray.size());
        return newJsonArray.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Tag(context, " getChildrenCount " + newJsonArray.get(groupPosition).getAsJsonArray().get(1).getAsJsonArray().size());
        return newJsonArray.get(groupPosition).getAsJsonArray().get(1).getAsJsonArray().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        Tag(context, " getGroup " + newJsonArray.get(groupPosition).getAsJsonArray());
        return newJsonArray.get(groupPosition).getAsJsonArray().get(0).getAsJsonObject();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        Tag(context, " getChild " + newJsonArray.get(groupPosition).getAsJsonArray()
                .get(1).getAsJsonArray().get(childPosition).getAsJsonObject());
        return newJsonArray.get(groupPosition).getAsJsonArray().get(1).getAsJsonArray().get(childPosition).getAsJsonObject();
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
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = layoutInflater.inflate(groupLayout, null);

        ArrayList<TextView> listTextView = new ArrayList<>();

        for (int view : groupTextView) {
            TextView tv = v.findViewById(view);
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

        for (int view : childTextView) {
            TextView tv = v.findViewById(view);
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
        query = query.toLowerCase();
        newJsonArray = new JsonArray();

        if (query.isEmpty()) {
            newJsonArray.addAll(jsonArray);
        } else {
            JsonArray newGroupList = new JsonArray();

            for (JsonElement jsonElement : jsonArray) {
                JsonObject jsonObject = jsonElement.getAsJsonArray().get(0).getAsJsonObject();
                JsonArray jsonQuery = jsonElement.getAsJsonArray().get(1).getAsJsonArray();
                JsonArray newChildList = new JsonArray();
                for (JsonElement childRow : jsonQuery) {


                    JsonObject jsonChildObject = childRow.getAsJsonObject();
                    Set<Map.Entry<String, JsonElement>> jsr = jsonChildObject.entrySet();

                    Iterator<Map.Entry<String, JsonElement>> jsi = jsr.iterator();

                    for (int i = 0; i < jsonChildObject.size(); i++) {
                        if (jsi.hasNext()) {

                            Map.Entry<String, JsonElement> r = jsi.next();

                            if (r.getValue().toString().replace("\"", "").toLowerCase().contains(query)) {

                                newChildList.add(childRow);
                                break;

                            }

                        }

                    }
                }
                if (newChildList.size() > 0) {
                    newGroupList.add(jsonObject);
                    newGroupList.add(newChildList);
                    Tag(context, " groupLinhaList Teste" + newChildList.toString());
                }
            }
            if (newGroupList.size() > 0) {
                newJsonArray.add(newGroupList);
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
