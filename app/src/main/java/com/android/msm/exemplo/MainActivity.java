package com.android.msm.exemplo;

import static com.android.msm.recycleviewexpandable.Util.Tag;
import static com.android.msm.recycleviewexpandable.Util.carregarCircleProgressView;
import static com.msm.themes.ThemeUtil.getModeNightFromPreferences;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.msm.recycleviewexpandable.AdapterUtil;
import com.android.msm.recycleviewexpandable.DividerItemDecoration;
import com.android.msm.recycleviewexpandable.JsonUtil;
import com.android.msm.recycleviewexpandable.adapters.RecyclerAdapter;
import com.android.msm.recycleviewexpandable.interfaces.AdapterRecycleView;
import com.android.msm.recycleviewexpandable.interfaces.JsonConvert;
import com.android.msm.recycleviewexpandable.interfaces.RecyclerViewCardView;
import com.android.msm.recycleviewexpandable.interfaces.RecyclerViewOnCheckBox;
import com.android.msm.recycleviewexpandable.interfaces.RecyclerViewOnCircleProgressView;
import com.android.msm.recycleviewexpandable.interfaces.RecyclerViewOnClickListener;
import com.android.msm.recycleviewexpandable.interfaces.RecyclerViewOnListTextView;
import com.android.msm.recycleviewexpandable.interfaces.RecyclerViewOnRatingBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.msm.themes.BaseActivity;
import com.msm.themes.ThemeUtil;

import java.util.ArrayList;
import java.util.Objects;

import at.grabner.circleprogress.CircleProgressView;


public class MainActivity extends BaseActivity implements AdapterRecycleView, RecyclerViewOnClickListener,
	RecyclerViewOnCheckBox, ActionMode.Callback, RecyclerViewOnRatingBar, RecyclerViewOnListTextView, RecyclerViewOnCircleProgressView, RecyclerViewCardView {

	animaisDAO dao = new animaisDAO(this);
	ActionMode mActionMode;
	private JsonArray jsonGroup = new JsonArray();
	private ArrayList<Integer> listID;
	private RecyclerView mRecyclerView;
	private Toolbar mToolbar;
	private RecyclerAdapter myAdapter;
	private int checkedCount = 0;
	private Integer cProg;
	private Integer ImgVeiw;
	private boolean thema;

	private ArrayList<animaisVO> listVo;
	private ArrayList<animaisVO> FistListVo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ThemeUtil.setMyTheme(this, ThemeUtil.THEME_GREEN);
		setContentView(R.layout.activity_searchable);
		thema = getModeNightFromPreferences(MainActivity.this);
		mToolbar = findViewById(R.id.toolbar);
		mToolbar.setTitle("Cães");
		setSupportActionBar(mToolbar);
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);


		((FloatingActionButton) findViewById(R.id.fab)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (FistListVo.size() < 10) {
					FistListVo.add(listVo.get(FistListVo.size()));
					JsonUtil.setObj(FistListVo.get(FistListVo.size() - 1) ).Convert(new JsonConvert() {
						@Override
						public void asJsonArray(JsonArray jsonArray) {
							new MaterialDialog.Builder(MainActivity.this)
								.title(FistListVo.get(FistListVo.size() - 1 ).getRaca())
								.positiveText("Ok")
								.content(jsonArray.toString()).show();

							jsonGroup.add(jsonArray.get(0));
							myAdapter.setFilterJsonArray(jsonGroup);
						}
					});



				}
			}
		});

		listVo = new ArrayList<>();
		FistListVo = new ArrayList<>();
		dao.deleteTudo();
		animaisVO vo = null;
		{
			vo = new animaisVO();
			vo.setRankig(1);
			vo.setEspecie("Cão");
			vo.setRaca("Border Collie");
			vo.setImg("https://www.infoescola.com/wp-content/uploads/2010/08/border-collie_593634296.jpg");
			dao.insert(vo);
			listVo.add(vo);
			FistListVo.add(vo);
			vo = new animaisVO();
			vo.setRankig(2);
			vo.setEspecie("Cão");
			vo.setRaca("Poodle");
			vo.setImg("http://portalmelhoresamigos.com.br/wp-content/uploads/2015/11/poodle_cachorro.png");
			dao.insert(vo);
			listVo.add(vo);
			FistListVo.add(vo);
			vo = new animaisVO();
			vo.setRankig(3);
			vo.setEspecie("Cão");
			vo.setRaca("Pastor Alemão");
			vo.setImg("https://tudosobrecachorros.com.br/wp-content/uploads/pastor-alemao-01.jpg");
			dao.insert(vo);
			listVo.add(vo);
			vo = new animaisVO();
			vo.setRankig(4);
			vo.setEspecie("Cão");
			vo.setRaca("Golden Retriever");
			vo.setImg("https://img.quizur.com/f/img5cc8647699a3f3.43367462.jpg?lastEdited=1556636800");
			dao.insert(vo);
			listVo.add(vo);
			vo = new animaisVO();
			vo.setRankig(5);
			vo.setEspecie("Cão");
			vo.setRaca("Doberman");
			vo.setImg("https://love.doghero.com.br/wp-content/uploads/2018/03/shutterstock_75891091-768x510.jpg");
			dao.insert(vo);
			listVo.add(vo);
			vo = new animaisVO();
			vo.setRankig(6);
			vo.setEspecie("Cão");
			vo.setRaca("Pastro de Shetland");
			vo.setImg("https://upload.wikimedia.org/wikipedia/commons/6/62/ShetlandShpdogBlue2_wb.jpg");
			dao.insert(vo);
			listVo.add(vo);
			vo = new animaisVO();
			vo.setRankig(7);
			vo.setEspecie("Cão");
			vo.setRaca("Labrador Retriever");
			vo.setImg("http://gatoecachorro.com/wp-content/uploads/2015/08/Labrador-Retriever.png");
			dao.insert(vo);
			listVo.add(vo);
			vo = new animaisVO();
			vo.setRankig(8);
			vo.setEspecie("Cão");
			vo.setRaca("Papillon");
			vo.setImg("https://cdn.britannica.com/72/8172-050-B6BC0AC1.jpg");
			dao.insert(vo);
			listVo.add(vo);
			vo = new animaisVO();
			vo.setRankig(9);
			vo.setEspecie("Cão");
			vo.setRaca("Rottveiler");
			vo.setImg("https://cdn.diferenca.com/imagens/rottweiler-alemao-cke.jpg");
			dao.insert(vo);
			listVo.add(vo);
			vo = new animaisVO();
			vo.setRankig(1);
			vo.setEspecie("Cão");
			vo.setRaca("Austrian Cattle Dog");
			vo.setImg("https://vetsmart-parsefiles.s3.amazonaws.com/abd60f9c32a113fb7cfc09e6fc82963a_breed.jpg");
			dao.insert(vo);
			listVo.add(vo);


		}

		listID = new ArrayList<>();
		listID.add(R.id.tv_id);
		listID.add(R.id.tv_name);

		cProg = R.id.circleView_img_obj;
		ImgVeiw = R.id.imageObj;

		findViewById(R.id.btn_themaModo).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ThemeUtil.setMode(MainActivity.this, !getModeNightFromPreferences(MainActivity.this));
				recreate();
			}
		});

		findViewById(R.id.btn_start_expandable).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, ExpandableActivity.class));
			}
		});
		mRecyclerView = findViewById(R.id.rv_list);
		mRecyclerView.setHasFixedSize(true);

		jsonGroup = new JsonArray();
		JsonElement element =  new Gson().toJsonTree(FistListVo, new TypeToken<ArrayList<animaisVO>>() { }.getType());
		jsonGroup.addAll(element.getAsJsonArray());

		AdapterUtil.with(MainActivity.this).setObjects(new ArrayList<Object>(FistListVo)).
				configRecycleViewAdapter(R.layout.itens, listID, R.id.idCheckedView, cProg, ImgVeiw, R.id.ratingBar).
		  startRecycleViewAdapter(MainActivity.this);

	/*	JsonUtil.setCursor(dao.buscarTudo()).Convert(new JsonConvert() {
			@Override
			public void asJsonArray(JsonArray jsonArray) {
				Tag(MainActivity.this, new Throwable() +" jsonArray  " +  jsonArray.toString());
			}
		});

		JsonUtil.setListObj(new ArrayList<Object>(FistListVo)).Convert(new JsonConvert() {
			@Override
			public void asJsonArray(JsonArray jsonArray) {
				Tag(MainActivity.this, new Throwable() + " Object  " + jsonArray.toString());
			}
		});*/


		LinearLayoutManager llm = new LinearLayoutManager(this);
		llm.setOrientation(RecyclerView.VERTICAL);
		mRecyclerView.setLayoutManager(llm);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		//     SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		MenuItem item = menu.findItem(R.id.action_searchable_activity);
		SearchView searchView = (SearchView) item.getActionView();
		//  searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		searchView.setQueryHint(getResources().getString(R.string.search_hint));
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String s) {

				//String q = it.getStringExtra(SearchManager.QUERY);
				SearchRecentSuggestions searchRecentSuggestions = new SearchRecentSuggestions(MainActivity.this,
					SearchableProvider.AUTHORITY,
					SearchableProvider.MODE);
				searchRecentSuggestions.saveRecentQuery(s, null);
				myAdapter.filterData(s);
				return false;
			}

			@Override
			public boolean onQueryTextChange(String s) {
				myAdapter.filterData(s);
				return false;
			}
		});
		searchView.setOnCloseListener(new SearchView.OnCloseListener() {
			@Override
			public boolean onClose() {
				myAdapter.filterData(null);
				return false;
			}
		});
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

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void setRecyclerAdapter(RecyclerAdapter adapteRecycler) {

		myAdapter = adapteRecycler;
		mRecyclerView.setAdapter(myAdapter);
		myAdapter.setCardViewId(R.id.cardview);

		mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext()));
		myAdapter.setRecyclerViewOnClickListenerJson(this);
		myAdapter.setmRecyclerViewCheckBox(this);
		myAdapter.setmRecyclerViewRatingBar(this);
		myAdapter.setmRecyclerViewListTextView(this);
		myAdapter.setRecyclerViewProgressView(this);
		myAdapter.setmRecyclerViewCardView(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		boolean consumed = (mActionMode != null);
		if (consumed) {
			mActionMode.finish();
			mActionMode = null;
		}

	}

	@Override
	public void onClickListener(View view, JsonArray json, int position) {
		Log.d("JsonResultOnclick", json.get(position).getAsJsonObject().toString());

		if (mActionMode == null) {

			Toast.makeText(this, "Onclick...." +
				json.get(position).getAsJsonObject().toString(), Toast.LENGTH_SHORT).show();
		} else {

			if (json.get(position).getAsJsonObject().get("checked").getAsBoolean()) {

				json.get(position).getAsJsonObject().addProperty("checked", false);
			} else {
				json.get(position).getAsJsonObject().addProperty("checked", true);

			}
			myAdapter.setFilterJsonArray(json);

		}
		jsonGroup = json;

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
		jsonGroup = json;
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


			for (int i = 0; i < jsonGroup.size(); i++) {
				Log.d(" checked ", jsonGroup.get(i).getAsJsonObject().toString());
				while (jsonGroup.size() > 0 && jsonGroup.get(i).getAsJsonObject().get("checked").getAsBoolean()) {
					jsonGroup.remove(i);
				}
			}

			myAdapter.setFilterJsonArray(jsonGroup);

		} else if (item.getItemId() == R.id.acao_star) {

			for (int i = 0; i < jsonGroup.size(); i++) {
				if (jsonGroup.get(i).getAsJsonObject().get("checked").getAsBoolean()) {
					if (jsonGroup.get(i).getAsJsonObject().get("ratingBar").getAsFloat() > 0) {
						jsonGroup.get(i).getAsJsonObject().addProperty("ratingBar", 0.0);
					} else {
						jsonGroup.get(i).getAsJsonObject().addProperty("ratingBar", 1.0);
					}
				}
			}
			myAdapter.setFilterJsonArray(jsonGroup);
		} else if (item.getItemId() == R.id.action_checkable) {
			if (item.isChecked()) {
				item.setChecked(false);
				for (int i = 0; i < jsonGroup.size(); i++) {
					if (jsonGroup.get(i).getAsJsonObject().get("checked").getAsBoolean()) {
						jsonGroup.get(i).getAsJsonObject().addProperty("checked", false);
					}
				}

			} else {
				item.setChecked(true);
				for (int i = 0; i < jsonGroup.size(); i++) {
					if (!jsonGroup.get(i).getAsJsonObject().get("checked").getAsBoolean()) {
						jsonGroup.get(i).getAsJsonObject().addProperty("checked", true);
					}
				}

			}
			myAdapter.setFilterJsonArray(jsonGroup);
		}

		return true;
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {

		mActionMode = null;
		for (int i = 0; i < jsonGroup.size(); i++) {
			if (jsonGroup.get(i).getAsJsonObject().get("checked").getAsBoolean()) {
				jsonGroup.get(i).getAsJsonObject().addProperty("checked", false);
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
		} else {
			checkBox.setVisibility(View.GONE);
		}


	}

	private void atualizarTitulo(JsonArray json) {
		checkedCount = 0;
		for (int i = 0; i < json.size(); i++) {
			if (jsonGroup.get(i).getAsJsonObject().get("checked").getAsBoolean()) {
				checkedCount++;
			}

		}
		if (checkedCount > 0) {
			mActionMode.setTitle(String.valueOf(checkedCount));
		} else {
			mActionMode.setTitle("Selecionar Itens");

		}


	}

	@Override
	public void OnCheckBox(RatingBar ratingBar, JsonArray json, int position) {

		ratingBar.setRating(json.get(position).getAsJsonObject().get("ratingBar").getAsFloat());

	}

	@Override
	public void OnLisTextView(ArrayList<TextView> lisTextView, JsonArray json, int position) {

		lisTextView.get(0).setText(String.valueOf(position + 1));
		lisTextView.get(1).setText(json.get(position).getAsJsonObject().get("raca").getAsString());

		lisTextView.get(0).setTextAppearance(this, R.style.Text_Body2);
		lisTextView.get(1).setTextAppearance(this, R.style.Text_Body2);

	}

	@Override
	public void OnCircleProgressoView(CircleProgressView circleProgressView, final ImageView imageView, JsonArray json, int position) {

		JsonObject jsonObject = json.get(position).getAsJsonObject();

		if (jsonObject.has("img")) {
			imageView.setImageResource(R.drawable.ic_baseline_broken_image_24);
			final CircleProgressView progressor = carregarCircleProgressView(circleProgressView);
			Ion.with(this).load(jsonObject.get("img").getAsString())
				.progressHandler(new ProgressCallback() {
					@Override
					public void onProgress(long downloaded, long total) {
						double progress = (100 * downloaded) / total;
						progressor.setValue((int) progress);
					}
				})
				.asBitmap().setCallback(new FutureCallback<Bitmap>() {
				@Override
				public void onCompleted(Exception e, Bitmap result) {
					progressor.setVisibility(View.GONE);
					if (result != null) {
						Log.d("ResultJson ", " Sucesso " + jsonObject.get("img").getAsString());
						Bitmap miniPhotoUserBitmap = Bitmap.createScaledBitmap(result, 300, 150, true);
						imageView.setImageBitmap(miniPhotoUserBitmap);

					} else {
						Log.d("ResultJson ", " erro " + jsonObject.get("img").getAsString());
					}
				}

			});
		}else{
			imageView.setImageResource(R.drawable.ic_baseline_image_24);
		}


	}


	@Override
	public void OnCardView(CardView cardView) {

		        if(getModeNightFromPreferences( this) ){
					cardView.setBackgroundColor(Color.parseColor("#FF424242"));
				}else{
					cardView.setBackgroundColor(Color.parseColor("#FFFFFF"));
				}
	//	 cardView.setCardBackgroundColor(R.attr.materialCardViewStyle);

	}
}
