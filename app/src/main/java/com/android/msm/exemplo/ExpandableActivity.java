package com.android.msm.exemplo;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.msm.recycleviewexpandable.AdapterUtil;
import com.android.msm.recycleviewexpandable.JsonUtil;
import com.android.msm.recycleviewexpandable.adapters.ExpandableJsonAdapter;
import com.android.msm.recycleviewexpandable.interfaces.AdapterExpandable;
import com.android.msm.recycleviewexpandable.interfaces.JsonConvert;
import com.android.msm.recycleviewexpandable.interfaces.mExpandableChildViewOnListTextView;
import com.android.msm.recycleviewexpandable.interfaces.mExpandableGroupViewOnListTextView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.msm.themes.BaseActivity;

import java.util.ArrayList;

import static com.msm.themes.util.Util.ProgressNotCancel;

public class ExpandableActivity extends BaseActivity
		implements  JsonConvert, AdapterExpandable, mExpandableGroupViewOnListTextView, mExpandableChildViewOnListTextView, SearchView.OnQueryTextListener, SearchView.OnCloseListener {

	animaisDAO dao = new animaisDAO(this);
	MaterialDialog pg;
	private android.widget.SearchView searchView;
	private ExpandableListView myExpandableListView;
	private JsonArray groupist;
	private MenuItem searchItem;
	private Dialog dialog;
	private int CodigoMenu;
	private ArrayList<Integer> listChildID;
	private ArrayList<Integer> listGroupID;
	private ArrayList<String> ItensChild;
	private ArrayList<String> ItensGroup;
	private ExpandableJsonAdapter listAdapter;
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expandable);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);


		dialog = new Dialog(this, R.style.Theme_Dialog_Translucent);
		dialog.setCancelable(false);
		dialog.setTitle("Processando...");

		pg = ProgressNotCancel(this);
		ItensChild = new ArrayList<>();
		listGroupID = new ArrayList<>();
		listGroupID.add(R.id.pai_text);

		listChildID = new ArrayList<>();
		listChildID.add(R.id.filho_text1);
		listChildID.add(R.id.filho_text2);
		listChildID.add(R.id.filho_text3);

		if (dao.buscarTudo() == null) {
			animaisVO vo = new animaisVO();
			vo.setRankig(1);
			vo.setEspecie("Cão");
			vo.setRaca("Border Collie");
			vo.setImg("https://www.infoescola.com/wp-content/uploads/2010/08/border-collie_593634296.jpg");
			dao.insert(vo);
			vo.setRankig(2);
			vo.setEspecie("Cão");
			vo.setRaca("Poodle");
			vo.setImg("http://portalmelhoresamigos.com.br/wp-content/uploads/2015/11/poodle_cachorro.png");
			dao.insert(vo);
			vo.setRankig(3);
			vo.setEspecie("Cão");
			vo.setRaca("Pastor Alemão");
			vo.setImg("https://tudosobrecachorros.com.br/wp-content/uploads/pastor-alemao-01.jpg");
			dao.insert(vo);
			vo.setRankig(4);
			vo.setEspecie("Cão");
			vo.setRaca("Golden Retriever");
			vo.setImg("https://img.quizur.com/f/img5cc8647699a3f3.43367462.jpg?lastEdited=1556636800");
			dao.insert(vo);
			vo.setRankig(5);
			vo.setEspecie("Cão");
			vo.setRaca("Doberman");
			vo.setImg("https://love.doghero.com.br/wp-content/uploads/2018/03/shutterstock_75891091-768x510.jpg");
			dao.insert(vo);
			vo.setRankig(6);
			vo.setEspecie("Cão");
			vo.setRaca("Pastro de Shetland");
			vo.setImg("https://upload.wikimedia.org/wikipedia/commons/6/62/ShetlandShpdogBlue2_wb.jpg");
			dao.insert(vo);
			vo.setRankig(7);
			vo.setEspecie("Cão");
			vo.setRaca("Labrador Retriever");
			vo.setImg("http://gatoecachorro.com/wp-content/uploads/2015/08/Labrador-Retriever.png");
			dao.insert(vo);
			vo.setRankig(8);
			vo.setEspecie("Cão");
			vo.setRaca("Papillon");
			vo.setImg("https://cdn.britannica.com/72/8172-050-B6BC0AC1.jpg");
			dao.insert(vo);
			vo.setRankig(9);
			vo.setEspecie("Cão");
			vo.setRaca("Rottveiler");
			vo.setImg("https://cdn.diferenca.com/imagens/rottweiler-alemao-cke.jpg");
			dao.insert(vo);
			vo.setRankig(1);
			vo.setEspecie("Cão");
			vo.setRaca("Austrian Cattle Dog");
			vo.setImg("https://vetsmart-parsefiles.s3.amazonaws.com/abd60f9c32a113fb7cfc09e6fc82963a_breed.jpg");
			dao.insert(vo);
		}

		myExpandableListView = findViewById(R.id.expandableListView_search);
		getJsonGroup();

	}

	private void getJsonGroup() {

		 Cursor cursor = dao.buscarTudo();
		ArrayList<Object> resultSet = new ArrayList<>();

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			int totalColumn = cursor.getColumnCount();
			JsonObject rowObject = new JsonObject();
			rowObject.addProperty("checked", false);
			rowObject.addProperty("ratingBar", 0.0);
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
		JsonUtil  jsUtil = new  JsonUtil();
		jsUtil.setListObjs(resultSet, new animais(1,"Cao")).Convert(this);
		cursor.close();
	}

	private void expandAll() {
		int count = listAdapter.getGroupCount();
		for (int i = 0; i < count; i++) {
			myExpandableListView.expandGroup(i);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		getMenuInflater().inflate(R.menu.menu_expandable, menu);
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		android.widget.SearchView searchView;
		MenuItem item = menu.findItem(R.id.action_search);
		searchView = (android.widget.SearchView) item.getActionView();
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		searchView.setQueryHint(getResources().getString(R.string.search_hint));
		searchView.setIconifiedByDefault(false);
		searchView.setOnQueryTextListener(this);
		searchView.setOnCloseListener(this);
		searchView.requestFocus();

		return true;
	}

	@Override
	public boolean onClose() {
		expandAll();
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		listAdapter.filterData(query);
		expandAll();
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		listAdapter.filterData(newText);
		expandAll();
		return false;
	}

	@Override
	public void setExpandableAdapter(ExpandableJsonAdapter adapter) {

		listAdapter = adapter;
		myExpandableListView.invalidate();
		myExpandableListView.setAdapter(listAdapter);
		listAdapter.setmChildViewListTextView(this);
		listAdapter.setmGroupViewListTextView(this);
		listAdapter.notifyDataSetChanged();

	}

	@Override
	public void asJsonArray(JsonArray jsonArray) {

		 JsonArray jsArray2 = new JsonArray();
		  jsArray2.add(jsonArray);
		AdapterUtil.with(this).configExpandableAdapter(R.layout.pai_layout,
				R.layout.model_tree_itens, listGroupID, listChildID).setJson(jsArray2).startExpandableAdapter(this);

	}


	@Override
	public void OnChildLisTextView(ArrayList<TextView> lisTextViewChild, JsonObject json) {
		lisTextViewChild.get(0).setTextAppearance(this,R.style.Text_Body1);
		lisTextViewChild.get(0).setText(json.get("ranking").getAsString());
		lisTextViewChild.get(1).setTextAppearance(this,R.style.Text_Body1);
		lisTextViewChild.get(1).setText(json.get("raca").getAsString());

		Log.d("testeJson ", " OnChildLisTextView " + json.toString());

	}

	@Override
	public void OnGroupLisTextView(ArrayList<TextView> lisTextViewGroup, JsonObject json) {
		lisTextViewGroup.get(0).setTextAppearance(this,R.style.Text_Body2);
		lisTextViewGroup.get(0).setText(json.get("raca").getAsString());

		Log.d("testeJson ", " OnGroupLisTextView " + json.toString());

	}


}
