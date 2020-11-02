/* package com.example.binumtontine.activity;

import eav.os.Bundle;

import com.example.binumtontine.R;
import com.google.eav.material.floatingactionbutton.FloatingActionButton;
import com.google.eav.material.snackbar.Snackbar;
import com.google.eav.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import eav.view.Menu;
import eav.view.MenuInflater;
import eav.view.MenuItem;
import eav.view.View;

import com.example.binumtontine.activity.ui.main.SectionsPagerAdapter;

public class CaisseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caisse);





        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ajouter une caisse", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }
}*/

package com.example.binumtontine.activity.parametreGenerauxOF;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.binumtontine.R;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TypeMembreActivity extends AppCompatActivity implements SERVER_ADDRESS {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";

    private static final String KEY_TYPE_MEMBRE_ID = "TmNumero";
    private static final String KEY_TYPE_MEMBRE_DENOMINATION = "TmLibelle";

    private static final String KEY_EXTRA_ACTION_TO_AFFECT = "ACTION_TO_AFFECT"; //to get intent.extra
    private Boolean action_to_affect=false;

    private ArrayList<HashMap<String, String>> caisseList;
    private ListView caisseListView;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_type_membre);

        /* begin */
        //  setContentView(R.layout.activity_movie_listing);
        caisseListView = (ListView) findViewById(R.id.typeMembreList);
        new TypeMembreActivity.FetchListCaisseAsyncTask().execute();

        /* end*/

        Toolbar toolbar = findViewById(R.id.toolbar_list_type_membre);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Types de membres");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab_list_type_membre);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ajouter un type de membre", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent i = new Intent(TypeMembreActivity.this, CreateTypeMembreOf.class);
                i.putExtra(KEY_EXTRA_ACTION_TO_AFFECT, action_to_affect);// to know if it's for update or not
                startActivityForResult(i,20);



            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
/*
    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, ParamProduitEAVFragment.class);
        startActivity(i);

        /*switch (v.getId()){
            case R.id.fab_produitEAV :
                AppCompatActivity activityMajOf = (AppCompatActivity) v.getContext();
                Fragment paramProduitEAV = new ParamProduitEAVFragment();
                activityMajOf.getSupportFragmentManager().beginTransaction().replace(((ViewGroup)getView().getParent()).getId(), paramProduitEAV).addToBackStack(null).commit();

                break;
        }*/


    /**
     * Fetches the list of movies from the server
     */
    private class FetchListCaisseAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(TypeMembreActivity.this);
            pDialog.setMessage("Loading types membres. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_type_membre.php", "GET", null);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray movies;
                if (success == 1) {
                    caisseList = new ArrayList<>();
                    movies = jsonObject.getJSONArray(KEY_DATA);
                    //Iterate through the response and populate movies list
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject caisse = movies.getJSONObject(i);
                        Integer caisseId = caisse.getInt(KEY_TYPE_MEMBRE_ID);
                        String caisseDenomination = caisse.getString(KEY_TYPE_MEMBRE_DENOMINATION);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_TYPE_MEMBRE_ID, caisseId.toString());
                        map.put(KEY_TYPE_MEMBRE_DENOMINATION, caisseDenomination);
                        caisseList.add(map);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    populateMovieList();
                }
            });
        }

    }

    /**
     * Updating parsed JSON data into ListView
     * */
    private void populateMovieList() {
        ListAdapter adapter = new SimpleAdapter(
                TypeMembreActivity.this, caisseList,
                R.layout.list_item, new String[]{KEY_TYPE_MEMBRE_ID,
                KEY_TYPE_MEMBRE_DENOMINATION},
                new int[]{R.id.movieId, R.id.movieName});
        // updating listview
        caisseListView.setAdapter(adapter);
        //Call MovieUpdateDeleteActivity when a movie is clicked
        caisseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Check for network connectivity
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    String movieId = ((TextView) view.findViewById(R.id.movieId))
                            .getText().toString();
                    Intent intent = new Intent(getApplicationContext(),
                            CreateTypeMembreOf.class);
                    intent.putExtra(KEY_EXTRA_ACTION_TO_AFFECT, !action_to_affect);// to know if it's for update or not
                    intent.putExtra(KEY_TYPE_MEMBRE_ID, movieId);
                    startActivityForResult(intent, 20);

                } else {
                    Toast.makeText(TypeMembreActivity.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 20) {
            // If the result code is 20 that means that
            // the user has deleted/updated the movie.
            // So refresh the movie listing
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

}


