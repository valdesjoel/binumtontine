package com.example.binumtontine.activity.parametrageGuichet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.example.binumtontine.activity.UpdateEAV;
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

public class ProduitEAVGuichetActivity extends AppCompatActivity implements SERVER_ADDRESS {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_EAV_ID = "ev_numero";
    private static final String KEY_EXTRA_ACTION_TO_AFFECT = "ACTION_TO_AFFECT"; //to push intent.extra
    private static final String KEY_EAV_LIBELLE = "ev_libelle";
    private static final String KEY_CAISSE_ID = "ev_caisse_id";
    private static final String KEY_GX_NUMERO = "ev_gx_numero";

    private ArrayList<HashMap<String, String>> movieList;
    private ListView movieListView;
    private ProgressDialog pDialog;
    private Boolean action_to_affect=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_produit_eav);
/* begin */
      //  setContentView(R.layout.activity_movie_listing);
        movieListView = (ListView) findViewById(R.id.movieList);
        new ProduitEAVGuichetActivity.FetchMoviesAsyncTask().execute();


        /* end*/

        Toolbar toolbar = findViewById(R.id.toolbar_produitEAV);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Produits du guichet") ;
        //this.setCheckedItem(R.id.nav_new);


        FloatingActionButton fab = findViewById(R.id.fab_produitEAV);
        fab.hide(); //to hide FloatingActionButton

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ajouter un produit EAV", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
              //  Intent i = new Intent(ProduitEAVActivity.this, AddMovieActivity.class);
                Intent i = new Intent(ProduitEAVGuichetActivity.this, CreateProduitEAV.class);
                startActivityForResult(i, 20);


            }
        });
    }

    /* To manage Menu*/
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_produit_guichet, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_all:
               // new ProduitEAVGuichetActivity.FetchMoviesAsyncTask().execute();
               // startActivity(new Intent(this, About.class));
                return true;
            case R.id.action_to_affect:
                action_to_affect = false;
                new ProduitEAVGuichetActivity.FetchMoviesAsyncTask().execute();
               // startActivity(new Intent(this, Help.class));
                return true;
                case R.id.action_already_affect:
                    action_to_affect = true;
                     new ProduitEAVGuichetActivity.FetchMoviesAsyncTask().execute();
               // startActivity(new Intent(this, Help.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


/**
 * Fetches the list of movies from the server
 */
private class FetchMoviesAsyncTask extends AsyncTask<String, String, String> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Display progress bar
        pDialog = new ProgressDialog(ProduitEAVGuichetActivity.this);
        pDialog.setMessage("Loading EAV. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_CAISSE_ID, String.valueOf(MyData.CAISSE_ID));
            httpParams.put(KEY_GX_NUMERO, String.valueOf(MyData.GUICHET_ID));
            JSONObject jsonObject =(action_to_affect)? httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_eav_by_guichet.php", "GET", httpParams): httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_eav_not_affect_on_guichet.php", "GET", httpParams);


        try {
            int success = jsonObject.getInt(KEY_SUCCESS);
            JSONArray movies;
            if (success == 1) {
                movieList = new ArrayList<>();
                movies = jsonObject.getJSONArray(KEY_DATA);
                //Iterate through the response and populate movies list
                for (int i = 0; i < movies.length(); i++) {
                    JSONObject movie = movies.getJSONObject(i);
                    Integer movieId = movie.getInt(KEY_EAV_ID);
                    String movieName = movie.getString(KEY_EAV_LIBELLE);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(KEY_EAV_ID, movieId.toString());
                    map.put(KEY_EAV_LIBELLE, movieName);
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
                ProduitEAVGuichetActivity.this, movieList,
                R.layout.list_item, new String[]{KEY_EAV_ID,
                KEY_EAV_LIBELLE},
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
                            UpdateEAVForGuichet.class);
                    intent.putExtra(KEY_EAV_ID, movieId);
                    intent.putExtra(KEY_EXTRA_ACTION_TO_AFFECT, !action_to_affect);

                  //  intent.putExtra(KEY_GX_NUMERO, MyData.GUICHET_ID);

                    startActivityForResult(intent, 20);

                } else {
                    Toast.makeText(ProduitEAVGuichetActivity.this,
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


