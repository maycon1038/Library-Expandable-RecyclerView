package com.android.msm.exemplo;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
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
import com.android.msm.recycleviewexpandable.adapters.RecyclerAdapter;
import com.android.msm.recycleviewexpandable.interfaces.AdapterRecycleView;
import com.android.msm.recycleviewexpandable.interfaces.RecyclerViewOnClickListener;
import com.google.gson.JsonArray;

import java.util.ArrayList;


public class SearchableActivity extends AppCompatActivity
        implements RecyclerViewOnClickListener,
        AdapterRecycleView {
    animaisDAO dao = new animaisDAO(this);
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private ArrayList<Integer> listID;
    private ArrayList<String> ItensDatabase;
    private RecyclerAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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


        AdapterUtil.with(this).configRecycleViewAdapter(R.layout.itens, listID).
                setCursor(dao.buscarTudo()).startRecycleViewAdapter(this);
        hendleSearch(getIntent());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        hendleSearch(intent);
    }

    public void hendleSearch(Intent intent) {
        if (Intent.ACTION_SEARCH.equalsIgnoreCase(intent.getAction())) {
            String q = intent.getStringExtra(SearchManager.QUERY);

            myAdapter.filterData(q);

            SearchRecentSuggestions searchRecentSuggestions = new SearchRecentSuggestions(this,
                    SearchableProvider.AUTHORITY,
                    SearchableProvider.MODE);
            searchRecentSuggestions.saveRecentQuery(q, null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_searchable_activity, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView;
        MenuItem item = menu.findItem(R.id.action_searchable_activity);
        searchView = (SearchView) item.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        return true;


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

        return true;
    }


    @Override
    public void onClickListener(View view, JsonArray json, int position) {
        //trabalhe no seu metodo

    }

    @Override
    public void onLongPressClickListener(View view, JsonArray json, int position) {

    }

    @Override
    public void setRecyclerAdapter(RecyclerAdapter adapter) {
        myAdapter = adapter;
        mRecyclerView.setAdapter(myAdapter);
        myAdapter.setRecyclerViewOnClickListenerJson(this);
    }
}
