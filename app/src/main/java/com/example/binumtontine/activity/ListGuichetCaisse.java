package com.example.binumtontine.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import com.example.binumtontine.activity.parametrageGuichet.ListWorkflowGuichet;
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

public class ListGuichetCaisse extends AppCompatActivity implements SERVER_ADDRESS {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_GUICHET_ID = "gx_numero";
    private static final String KEY_GUICHET_DENOMINATION = "gx_denomination";
    private static final String KEY_CAISSE_ID = "cx_numero";
    private static final String KEY_CAISSE_DENOMINATION = "cx_denomination";

    private ArrayList<HashMap<String, String>> movieList;
    private ListView movieListView;
    private ProgressDialog pDialog;
    private String sens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_list_guichets_caisses);
/* begin */
      //  setContentView(R.layout.activity_movie_listing);

        Intent intent = getIntent();
        sens = intent.getStringExtra("sens");
        movieListView = (ListView) findViewById(R.id.movieList);
        new ListGuichetCaisse.FetchMoviesAsyncTask().execute();

        /* end*/

        Toolbar toolbar = findViewById(R.id.toolbar_produitEAV);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Guichet Adhérent") ;


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab_produitEAV);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ajouter un produit EAV", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
              //  Intent i = new Intent(ProduitEAVActivity.this, AddMovieActivity.class);
                Intent i = new Intent(ListGuichetCaisse.this, CreateProduitEAV.class);
                startActivityForResult(i, 20);


            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
/**
 * Fetches the list of movies from the server
 */
private class FetchMoviesAsyncTask extends AsyncTask<String, String, String> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Display progress bar
        pDialog = new ProgressDialog(ListGuichetCaisse.this);
        pDialog.setMessage("Loading list's guichets. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String, String> httpParams = new HashMap<>();
//        httpParams.put(KEY_CAISSE_ID, String.valueOf(MyData.CAISSE_ID));
        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                BASE_URL + "fetch_all_guichets_caisses.php", "GET", httpParams);
        try {
            int success = jsonObject.getInt(KEY_SUCCESS);
            Log.e("jsonObject", jsonObject+"");
            JSONArray movies;
            if (success == 1) {
                movieList = new ArrayList<>();
                movies = jsonObject.getJSONArray(KEY_DATA);
                //Iterate through the response and populate movies list
                for (int i = 0; i < movies.length(); i++) {
                    JSONObject movie = movies.getJSONObject(i);
                    Integer gx_numero = movie.getInt(KEY_GUICHET_ID);
                    String cx_denomination = movie.getString(KEY_CAISSE_DENOMINATION);
                    String gx_denomination = movie.getString(KEY_GUICHET_DENOMINATION);

//                    HashMap<String, String> map = new HashMap<String, String>();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(KEY_GUICHET_ID, gx_numero.toString());
                    map.put(KEY_CAISSE_DENOMINATION, cx_denomination);
                    map.put(KEY_GUICHET_DENOMINATION, gx_denomination);

//                    map.put(KEY_GUICHET_DENOMINATION, gx_denomination+i+1);
                    movieList.add(map);
                    Log.e("fetch_all_guichets_caisses><<<<<<  ",gx_numero+"  "+cx_denomination+"  "+gx_denomination);
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
                ListGuichetCaisse.this, movieList,
                R.layout.list_item_guichets_caisses, new String[]{KEY_GUICHET_ID,
                 KEY_CAISSE_DENOMINATION,KEY_GUICHET_DENOMINATION},
                new int[]{R.id.movieId, R.id.movieName, R.id.movieNameDetails});
     /*   TextView cx = ((TextView) findViewById(R.id.movieNameDetails));
        cx.setVisibility(View.VISIBLE);*/
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

                    Toast.makeText(ListGuichetCaisse.this,
                            guichetDenomination+" "+movieId,
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),
                            ListNomPrenomAdherentTransfert.class);
                    intent.putExtra(KEY_GUICHET_ID, movieId);
                    intent.putExtra("sens", sens);
                    startActivityForResult(intent, 20);
                    finish();

                } else {
                    Toast.makeText(ListGuichetCaisse.this,
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


