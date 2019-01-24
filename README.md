# Library-Searchable
Este projeto contém uma library que facilita a implementação do Searchable, RecyclerView e database SQlite 

<img src="captura.gif" height="400" width="300" title="SampleRangeDatePicker">

Step 1. Adicione-o em sua raiz build.gradle no final dos repositórios: 

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Adicione as dependências de dependência

	dependencies {
	        implementation 'com.github.maycon1038:Library-Expandable-RecyclerView:2.0'
	}
Step 3. Crie o seu provider exemplo

    public class SearchableProvider extends SearchRecentSuggestionsProvider {
    public static final String AUTHORITY = "SeuPacote.SearchableProvider";
    public static final int MODE = DATABASE_MODE_QUERIES;
    public SearchableProvider(){
        setupSuggestions( AUTHORITY, MODE );
    } 
    }
Step 4. Em res> xml crie o arquivo searchable.xml  conforme exemplo abaixo

     <?xml version="1.0" encoding="utf-8"?>
     <searchable
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:label="@string/app_name"
    android:hint="@string/search_hint"
    android:voiceSearchMode="showVoiceSearchButton|launchRecognizer"
    android:searchSuggestAuthority="SeuPacote.SearchableProvider"
    android:searchSuggestSelection=" ?">
    </searchable>
Step 4. Crie a Classe SearchableActivity e  implements Adapters, RecyclerViewOnClickListener  conforme exemplo

    public class SearchableActivity extends AppCompatActivity
        implements RecyclerViewOnClickListener,
        Adapters {
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


        AdapterUtil.with(this).configRecycleViewAdapter(R.layout.itens, listID, ItensDatabase).
                setCursor(dao.buscarTudo()).start(this);
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
    public void seAdapter(RecyclerAdapter adapter) {
        myAdapter = adapter;
        mRecyclerView.setAdapter(myAdapter);
        myAdapter.setRecyclerViewOnClickListenerJson(this);
    }

    @Override
    public void seAdapter(ExpandableJsonAdapter adapter) {


    }


    @Override
    public void onClickListener(View view, JsonArray json, int position) {
        //trabalhe no seu metodo

    }

    @Override
    public void onLongPressClickListener(View view, JsonArray json, int position) {

    }
}


Step 5. implemente na sua MainActivity conforme exemplo
   
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

Step 5. finalmente adicione o provider no seu manifest r conforme exemplo

     <application
       ........

        <meta-data
            android:name="android.app.searchable"
            android:resource="@xml/searchable" />

        <provider
            android:name="SeuPacote.SearchableProvider"
            android:authorities="SeuPacote.SearchableProvider" />
            
            <activity
            android:name=".MainActivity"
            android:label="@string/app_name"
            android:theme="@style/AppTheme.NoActionBar">
           ....
adicione na sua activity
          
           <meta-data
                android:name="android.app.default_searchable"
                android:value="com.android.msm.mylibrarysearchable.SearchableActivity"/>
        </activity>
            declare o seu SearchableActivity
             <activity
            android:name="pmam.com.br.icheckpm.unidade_pmam.SearchableActivity"
            android:label="@string/title_activity_transition"
            android:launchMode="singleTop"
            android:theme="@style/AppTheme.NoActionBar"
            android:windowSoftInputMode="adjustPan|adjustNothing">
            <intent-filter>
                <action android:name="android.intent.action.SEARCH" />
            </intent-filter>

            <meta-data
                android:name="android.app.searchable"
                android:resource="@xml/searchable" />
        </activity>
           

  
