package com.example.binumtontine.activity.parametreGenerauxCx.membreComiteCredit;

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
import com.example.binumtontine.activity.parametreGenerauxCx.comiteDeCredit.CreateComiteCredit;
import com.example.binumtontine.activity.parametreGenerauxCx.comiteDeCredit.UpdateComiteCredit;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.example.binumtontine.modele.ComiteCredit;
import com.example.binumtontine.modele.MembreComiteCredit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListMembreComiteCredit extends AppCompatActivity implements SERVER_ADDRESS {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";

    private ArrayList<HashMap<String, String>> movieList;
    private ListView movieListView;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_list_membre_comite_credit);
        movieListView = (ListView) findViewById(R.id.movieList);
        new FetchComiteCreditAsyncTask().execute();

        Toolbar toolbar = findViewById(R.id.toolbar_produitEAV);
        setSupportActionBar(toolbar);
//        getActionBar().setTitle("OBJET CREDIT");
        getSupportActionBar().setTitle("LISTE DES MEMBRES");
        getSupportActionBar().setSubtitle("Du comité de crédit");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab_produitEAV);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ajouter un membre au comité de crédit", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent i = new Intent(ListMembreComiteCredit.this, CreateMembreComiteCredit.class);
                startActivityForResult(i,20);
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

/**
 * Fetches the list of comite Credit from the server
 */
private class FetchComiteCreditAsyncTask extends AsyncTask<String, String, String> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Display progress bar
        pDialog = new ProgressDialog(ListMembreComiteCredit.this);
        pDialog.setMessage("Chargement des  comités de crédit. SVP Veuillez patienter...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String, String> httpParams = new HashMap<>();
        httpParams.put(ComiteCredit.KEY_CmCaisse, String.valueOf(MyData.CAISSE_ID));
        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                BASE_URL + "fetch_all_membre_comite_credit.php", "GET", httpParams);
        try {
            int success = jsonObject.getInt(KEY_SUCCESS);
            JSONArray movies;
            if (success == 1) {
                movieList = new ArrayList<>();
                movies = jsonObject.getJSONArray(KEY_DATA);
                //Iterate through the response and populate movies list
                for (int i = 0; i < movies.length(); i++) {
                    JSONObject movie = movies.getJSONObject(i);
                    Integer movieId = movie.getInt(MembreComiteCredit.KEY_McNumero);
                    String movieMcNom = movie.getString(MembreComiteCredit.KEY_McNom);
                    String movieMcPrenom = movie.getString(MembreComiteCredit.KEY_McPrenom);
                    String movieName = movieMcNom.concat(" ").concat(movieMcPrenom);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(MembreComiteCredit.KEY_McNumero, movieId.toString());
                    map.put(MembreComiteCredit.KEY_McNom, movieName);
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
                ListMembreComiteCredit.this, movieList,
                R.layout.list_item, new String[]{MembreComiteCredit.KEY_McNumero,
                MembreComiteCredit.KEY_McNom},
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
                            UpdateComiteCredit.class);
                    intent.putExtra(ComiteCredit.KEY_CmNumero, movieId);
//                    startActivityForResult(intent, 20);

                } else {
                    Toast.makeText(ListMembreComiteCredit.this,
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


