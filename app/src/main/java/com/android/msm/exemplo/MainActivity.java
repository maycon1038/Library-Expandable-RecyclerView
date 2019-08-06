package com.android.msm.exemplo;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.msm.recycleviewexpandable.AdapterUtil;
import com.android.msm.recycleviewexpandable.adapters.RecyclerAdapter;
import com.android.msm.recycleviewexpandable.interfaces.AdapterRecycleView;
import com.android.msm.recycleviewexpandable.interfaces.RecyclerViewOnCheckBox;
import com.android.msm.recycleviewexpandable.interfaces.RecyclerViewOnClickListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AdapterRecycleView, RecyclerViewOnClickListener, RecyclerViewOnCheckBox, ActionMode.Callback {

    animaisDAO dao = new animaisDAO(this);
    private ArrayList<Integer> listID;
    private ArrayList<String> ItensDatabase;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private RecyclerAdapter myAdapter;
    ActionMode mActionMode;
    private static JsonArray jsonGroup = new JsonArray();
    private int checkedCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        mToolbar.setTitle("Cães");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        if (dao.buscarTudo() == null) {
            animaisVO vo = new animaisVO();
            vo.setRankig(1);
            vo.setEspecie("Cão");
            vo.setRaca("Border Collie");
            dao.insert(vo);
            vo.setRankig(2);
            vo.setEspecie("Cão");
            vo.setRaca("Poodle");
            dao.insert(vo);
            vo.setRankig(3);
            vo.setEspecie("Cão");
            vo.setRaca("Pastor Alemão");
            dao.insert(vo);
            vo.setRankig(4);
            vo.setEspecie("Cão");
            vo.setRaca("Golden Retriever");
            dao.insert(vo);
            vo.setRankig(5);
            vo.setEspecie("Cão");
            vo.setRaca("Doberman");
            dao.insert(vo);
            vo.setRankig(6);
            vo.setEspecie("Cão");
            vo.setRaca("Pastro de Shetland");
            dao.insert(vo);
            vo.setRankig(7);
            vo.setEspecie("Cão");
            vo.setRaca("Labrador Retriever");
            dao.insert(vo);
            vo.setRankig(8);
            vo.setEspecie("Cão");
            vo.setRaca("Papillon");
            dao.insert(vo);
            vo.setRankig(9);
            vo.setEspecie("Cão");
            vo.setRaca("Rottveiler");
            dao.insert(vo);
            vo.setRankig(1);
            vo.setEspecie("Cão");
            vo.setRaca("Austrian Cattle Dog");
            dao.insert(vo);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        listID = new ArrayList<>();
        ItensDatabase = new ArrayList<>();
        listID.add(R.id.tv_id);
        ItensDatabase.add("ranking");
        listID.add(R.id.tv_name);
        ItensDatabase.add("raca");


        if(jsonGroup.size() >= 1){
            AdapterUtil.with(this).configRecycleViewAdapter(R.layout.itens, listID, ItensDatabase, R.id.idCheckedView,null, null).
                    setJson(jsonGroup).startRecycleViewAdapter(this);
        }else{
            Cursor cursor = dao.buscarTudo();
            if(cursor != null && cursor.getCount() >= 1){
                jsonGroup = getJsonGroup(cursor);
                AdapterUtil.with(this).configRecycleViewAdapter(R.layout.itens, listID, ItensDatabase, R.id.idCheckedView,null, null).
                        setJson(jsonGroup).startRecycleViewAdapter(this);
            }else{
                Toast.makeText(this, "nenhum dado encontrado", Toast.LENGTH_SHORT).show();
            }

        }




    }

    private JsonArray getJsonGroup(Cursor cursor) {

        JsonArray resultSet = new JsonArray();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int totalColumn = cursor.getColumnCount();
            JsonObject rowObject = new JsonObject();
            rowObject.addProperty("checked", false);
            rowObject.addProperty("img", "getFile");
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    String columnName = cursor.getColumnName(i);
                    String value = (cursor.getString(i) == null) ? "" : cursor.getString(i);
                    try {
                      rowObject.addProperty(columnName, value);
                    } catch (Exception e) {
                        Log.d(" cursorToJson ", e.getMessage());
                    }

                }
            }
            resultSet.add(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        return resultSet;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem item = menu.findItem(R.id.action_searchable_activity);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        return true;
    }

    public void hendleSearch() {
        //    filter.initJson(this,convertlist( getListAnimais()));


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.action_delete) {
            SearchRecentSuggestions searchRecentSuggestions = new SearchRecentSuggestions(this,
                    SearchableProvider.AUTHORITY,
                    SearchableProvider.MODE);
            searchRecentSuggestions.clearHistory();
            Toast.makeText(this, "Cookies removidos", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setRecyclerAdapter(RecyclerAdapter adapteRecycler) {
        myAdapter = adapteRecycler;
        mRecyclerView.setAdapter(myAdapter);
        myAdapter.setRecyclerViewOnClickListenerJson(this);
        myAdapter.setmRecyclerViewCheckBox(this);
    }




    @Override
    protected void onResume() {
        super.onResume();
        hendleSearch();
        boolean consumed = (mActionMode != null);
        //  ImageView checkBox = (ImageView)view.findViewById(R.id.checkBox);
        if (consumed) {
            mActionMode.finish();
            mActionMode = null;
        }
    }

    public ArrayList<animais> getListAnimais() {
        ArrayList<animais> list = new ArrayList<>();
        animaisDAO dao = new animaisDAO(getBaseContext());
        Cursor cl = dao.buscarTudo();  // buscando o curso de todas lotacoes
        while (cl.moveToNext()) {
            list.add(
                    new animais(cl.getInt(1),
                            cl.getString(3)));   //calcula a distancia  e adiciona na lista

        }
        cl.close();

        return list;
    }

    @Override
    public void onClickListener(View view, JsonArray json, int position) {
        Log.d("JsonResult", json.get(position).getAsJsonObject().toString());

        if (mActionMode == null) {

            Toast.makeText(this, "Onclick...." +
                    json.get(position).getAsJsonObject().toString(), Toast.LENGTH_SHORT).show();
        }else{

            if(json.get(position).getAsJsonObject().get("checked").getAsBoolean()){

                json.get(position).getAsJsonObject().addProperty("checked", false);
            }else{
                json.get(position).getAsJsonObject().addProperty("checked", true);

            }
            myAdapter.setFilterJsonArray(json);
            Toast.makeText(this, "Onclick.... position " + position + " "+
                    json.get(position).getAsJsonObject().get("checked").toString(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onLongPressClickListener(View view, JsonArray json, int position) {

        boolean consumed = (mActionMode == null);
        //  ImageView checkBox = (ImageView)view.findViewById(R.id.checkBox);
        if (consumed) {

            iniciarModoExclusao();
            json.get(position).getAsJsonObject().addProperty("checked", true);
            myAdapter.setFilterJsonArray(json);
            // lv.setItemChecked(position, true);
            //  atualizarItensMarcados(lv, position, checkBox);
        }

    }

    private void iniciarModoExclusao() {
            mActionMode = startActionMode(this);
            //   updateChecked(position,view);
            mActionMode.setTitle("Selecionar Itens");


    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.menu_delete_list, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        if (item.getItemId() == R.id.acao_delete) {

            Toast.makeText(this, "Delete Itens", Toast.LENGTH_SHORT).show();

            return true;
        }else  if (item.getItemId() == R.id.action_checkable) {
            if (item.isChecked()) {
                item.setChecked(false);
                for (int i = 0; i < jsonGroup.size(); i++) {
                    if(jsonGroup.get(i).getAsJsonObject().get("checked").getAsBoolean()){
                        jsonGroup.get(i).getAsJsonObject().addProperty("checked",false);
                    }
                }

            }else{
                item.setChecked(true);
                for (int i = 0; i < jsonGroup.size(); i++) {
                    if(!jsonGroup.get(i).getAsJsonObject().get("checked").getAsBoolean()){
                        jsonGroup.get(i).getAsJsonObject().addProperty("checked",true);
                    }
                }

            }
            myAdapter.setFilterJsonArray(jsonGroup);

            return true;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

        mActionMode = null;
        for (int i = 0; i < jsonGroup.size(); i++) {
          if(jsonGroup.get(i).getAsJsonObject().get("checked").getAsBoolean()){
             jsonGroup.get(i).getAsJsonObject().addProperty("checked",false);
          }
        }
        checkedCount = 0;
        myAdapter.setFilterJsonArray(jsonGroup);

    }

    @Override
    public void OnCheckBox(CheckBox checkBox, JsonArray json, int position) {

        if (mActionMode != null) {
                checkBox.setVisibility(View.VISIBLE);
                checkBox.setChecked(json.get(position).getAsJsonObject().get("checked").getAsBoolean());
            atualizarTitulo(json);
        }else{
            checkBox.setVisibility(View.GONE);
        }


    }

    private void atualizarTitulo(JsonArray json) {
        checkedCount = 0;
        for (int i = 0; i < json.size(); i++) {
            if(jsonGroup.get(i).getAsJsonObject().get("checked").getAsBoolean()){
                checkedCount++;
            }

        }
        if (checkedCount > 0) {
            mActionMode.setTitle(String.valueOf(checkedCount));
        } else {
            mActionMode.setTitle("Selecionar Itens");

        }


    }



}
