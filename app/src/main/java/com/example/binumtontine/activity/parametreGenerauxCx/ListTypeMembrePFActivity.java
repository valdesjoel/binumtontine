package com.example.binumtontine.activity.parametreGenerauxCx;

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
import com.example.binumtontine.activity.CreateProduitEAV;
import com.example.binumtontine.activity.parametrageGuichet.ParamGuichetActivity;
import com.example.binumtontine.controleur.MyData;
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
import java.util.Map;

public class ListTypeMembrePFActivity extends AppCompatActivity implements SERVER_ADDRESS {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_GUICHET_ID = "TmNumero";
    private static final String KEY_GUICHET_DENOMINATION = "TmLibelle";
    private static final String KEY_CAISSE_ID = "TxCaisse";

    private ArrayList<HashMap<String, String>> movieList;
    private ListView movieListView;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_list_guichets);
/* begin */
      //  setContentView(R.layout.activity_movie_listing);
        movieListView = (ListView) findViewById(R.id.movieList);
        new ListTypeMembrePFActivity.FetchMoviesAsyncTask().execute();

        /* end*/

        Toolbar toolbar = findViewById(R.id.toolbar_produitEAV);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sélectionner un type de membre") ;

        FloatingActionButton fab = findViewById(R.id.fab_produitEAV);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ajouter un produit EAV", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
              //  Intent i = new Intent(ProduitEAVActivity.this, AddMovieActivity.class);
                Intent i = new Intent(ListTypeMembrePFActivity.this, CreateProduitEAV.class);
                startActivityForResult(i, 20);


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
        pDialog = new ProgressDialog(ListTypeMembrePFActivity.this);
        pDialog.setMessage("Chargement des types de membres. Veuillez patienter...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String, String> httpParams = new HashMap<>();
        httpParams.put(KEY_CAISSE_ID, String.valueOf(MyData.CAISSE_ID));
        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                BASE_URL + "fetch_all_type_membre_by_caisse.php", "GET", httpParams);
        try {
            int success = jsonObject.getInt(KEY_SUCCESS);
            JSONArray movies;
            if (success == 1) {
                movieList = new ArrayList<>();
                movies = jsonObject.getJSONArray(KEY_DATA);
                //Iterate through the response and populate movies list
                for (int i = 0; i < movies.length(); i++) {
                    JSONObject movie = movies.getJSONObject(i);
                    Integer movieId = movie.getInt(KEY_GUICHET_ID);
                    String movieName = movie.getString(KEY_GUICHET_DENOMINATION);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(KEY_GUICHET_ID, movieId.toString());
                    map.put(KEY_GUICHET_DENOMINATION, movieName);
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
                ListTypeMembrePFActivity.this, movieList,
                R.layout.list_item, new String[]{KEY_GUICHET_ID,
                KEY_GUICHET_DENOMINATION},
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
                    String guichetDenomination = ((TextView) view.findViewById(R.id.movieName))
                            .getText().toString();
                    MyData.TYPE_MEMBRE_ID = Integer.parseInt(movieId);
                    MyData.TYPE_MEMBRE_NAME = guichetDenomination; //a revoir si c'est mieux de faire un intent.putExtra
                    Intent intent = new Intent(getApplicationContext(),
                            ParametreGenerauxCxActivity.class);
                    intent.putExtra(KEY_GUICHET_ID, movieId);
                    startActivityForResult(intent, 20);

                } else {
                    Toast.makeText(ListTypeMembrePFActivity.this,
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


