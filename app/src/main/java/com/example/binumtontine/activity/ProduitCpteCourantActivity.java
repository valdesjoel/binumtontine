package com.example.binumtontine.activity;

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

public class ProduitCpteCourantActivity extends AppCompatActivity implements SERVER_ADDRESS {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_CPTE_COURANT_ID = "CcNumero";
    private static final String KEY_CPTE_COURANT_LIBELLE = "CcLibelle";

    private ArrayList<HashMap<String, String>> cpteCourantList;
    private ListView cpteCourantListView;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_produit_cpte_courant);
/* begin */
      //  setContentView(R.layout.activity_movie_listing);
        cpteCourantListView = (ListView) findViewById(R.id.movieList);
        new FetchCpteCourantAsyncTask().execute();

        /* end*/

        Toolbar toolbar = findViewById(R.id.toolbar_produitCpteCourant);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab_produitCpteCourant);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ajouter un produit Compte courant", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
              //  Intent i = new Intent(ProduitEAVActivity.this, AddMovieActivity.class);
                Intent i = new Intent(ProduitCpteCourantActivity.this, CreateProduitCpteCourant.class);
               // startActivity(i);
                startActivityForResult(i, 20);


            }
        });
    }



/**
 * Fetches the list of cpteCourant from the server
 */
private class FetchCpteCourantAsyncTask extends AsyncTask<String, String, String> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Display progress bar
        pDialog = new ProgressDialog(ProduitCpteCourantActivity.this);
        pDialog.setMessage("Loading Compte courant. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                BASE_URL + "fetch_all_cpte_courant.php", "GET", null);
        try {
            int success = jsonObject.getInt(KEY_SUCCESS);
            JSONArray movies;
            if (success == 1) {
                cpteCourantList = new ArrayList<>();
                movies = jsonObject.getJSONArray(KEY_DATA);
                //Iterate through the response and populate movies list
                for (int i = 0; i < movies.length(); i++) {
                    JSONObject movie = movies.getJSONObject(i);
                    Integer movieId = movie.getInt(KEY_CPTE_COURANT_ID);
                    String movieName = movie.getString(KEY_CPTE_COURANT_LIBELLE);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(KEY_CPTE_COURANT_ID, movieId.toString());
                    map.put(KEY_CPTE_COURANT_LIBELLE, movieName);
                    cpteCourantList.add(map);
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
                ProduitCpteCourantActivity.this, cpteCourantList,
                R.layout.list_item, new String[]{KEY_CPTE_COURANT_ID,
                KEY_CPTE_COURANT_LIBELLE},
                new int[]{R.id.movieId, R.id.movieName});
        // updating listview
        cpteCourantListView.setAdapter(adapter);
        //Call MovieUpdateDeleteActivity when a movie is clicked
        cpteCourantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Check for network connectivity
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    String movieId = ((TextView) view.findViewById(R.id.movieId))
                            .getText().toString();
                    Intent intent = new Intent(getApplicationContext(),
                            UpdateEAV.class);
                    intent.putExtra(KEY_CPTE_COURANT_ID, movieId);
                    startActivityForResult(intent, 20);

                } else {
                    Toast.makeText(ProduitCpteCourantActivity.this,
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


