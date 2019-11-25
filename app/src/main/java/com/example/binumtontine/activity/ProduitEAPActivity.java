/*package com.example.binumtontine.activity;

import eav.content.Intent;
import eav.os.Bundle;
import eav.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.binumtontine.R;
import com.google.eav.material.floatingactionbutton.FloatingActionButton;
import com.google.eav.material.snackbar.Snackbar;

public class ProduitEAPActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit_eap);
        Toolbar toolbar = findViewById(R.id.toolbar_produitEAP);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab_produitEAP);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ajouter un produit EAP", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent i = new Intent(ProduitEAPActivity.this, CreateProduitEAP.class);
                startActivity(i);


            }
        });
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
        }* /

    }
*/

package com.example.binumtontine.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.example.binumtontine.remotemysqlconnection.MovieUpdateDeleteActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.binumtontine.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProduitEAPActivity extends AppCompatActivity  {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_MOVIE_ID = "ep_numero";
    private static final String KEY_MOVIE_NAME = "ep_libelle";
   /* private static final String KEY_MOVIE_ID = "cx_numero";
    private static final String KEY_MOVIE_NAME = "cx_localite";*/
    //private static final String BASE_URL = "http://192.168.1.102/binumTontine/";
    private static final String BASE_URL = "http://binumt.diff-itc.net/binumTontine/";
    private ArrayList<HashMap<String, String>> movieList;
    private ListView movieListView;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit_eap);
        /* begin */
        //  setContentView(R.layout.activity_movie_listing);
        movieListView = (ListView) findViewById(R.id.movieList_eap);
        new ProduitEAPActivity.FetchMoviesAsyncTask().execute();

        /* end*/

        Toolbar toolbar = findViewById(R.id.toolbar_produitEAP);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab_produitEAP);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ajouter un produit EAP", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //  Intent i = new Intent(ProduitEAVActivity.this, AddMovieActivity.class);
                Intent i = new Intent(ProduitEAPActivity.this, CreateProduitEAP.class);
                startActivity(i);


            }
        });
    }



    /**
     * Fetches the list of movies from the server
     */
    private class FetchMoviesAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(ProduitEAPActivity.this);
            pDialog.setMessage("Loading EAP. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_eap.php", "GET", null);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray movies;
                if (success == 1) {
                    movieList = new ArrayList<>();
                    movies = jsonObject.getJSONArray(KEY_DATA);
                    //Iterate through the response and populate movies list
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject movie = movies.getJSONObject(i);
                        Integer movieId = movie.getInt(KEY_MOVIE_ID);
                        String movieName = movie.getString(KEY_MOVIE_NAME);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_MOVIE_ID, movieId.toString());
                        map.put(KEY_MOVIE_NAME, movieName);
                        movieList.add(map);
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
                ProduitEAPActivity.this, movieList,
                R.layout.list_item, new String[]{KEY_MOVIE_ID,
                KEY_MOVIE_NAME},
                new int[]{R.id.movieId, R.id.movieName});
        // updating listview
        movieListView.setAdapter(adapter);
        //Call MovieUpdateDeleteActivity when a movie is clicked
        movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Check for network connectivity
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    String movieId = ((TextView) view.findViewById(R.id.movieId))
                            .getText().toString();
                    Intent intent = new Intent(getApplicationContext(),
                            MovieUpdateDeleteActivity.class);
                    intent.putExtra(KEY_MOVIE_ID, movieId);
                    startActivityForResult(intent, 20);

                } else {
                    Toast.makeText(ProduitEAPActivity.this,
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




