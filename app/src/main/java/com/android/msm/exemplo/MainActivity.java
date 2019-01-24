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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.msm.recycleviewexpandable.AdapterUtil;
import com.android.msm.recycleviewexpandable.adapters.ExpandableJsonAdapter;
import com.android.msm.recycleviewexpandable.adapters.RecyclerAdapter;
import com.android.msm.recycleviewexpandable.interfaces.Adapters;
import com.android.msm.recycleviewexpandable.interfaces.RecyclerViewOnClickListener;
import com.google.gson.JsonArray;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Adapters, RecyclerViewOnClickListener {

    animaisDAO dao = new animaisDAO(this);
    private ArrayList<Integer> listID;
    private ArrayList<String> ItensDatabase;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private RecyclerAdapter myAdapter;

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

        Cursor cursor = dao.buscarTudo();
        AdapterUtil.with(this).configRecycleViewAdapter(R.layout.itens, listID, ItensDatabase).
                setCursor(cursor).start(this);


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
    public void seAdapter(RecyclerAdapter adapteRecycler) {
        myAdapter = adapteRecycler;
        mRecyclerView.setAdapter(myAdapter);
        myAdapter.setRecyclerViewOnClickListenerJson(this);
    }

    @Override
    public void seAdapter(ExpandableJsonAdapter adapter) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        hendleSearch();
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
        Toast.makeText(this, "Onclick...." +
                json.get(position).getAsJsonObject().get("raca").toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongPressClickListener(View view, JsonArray json, int position) {

    }
}