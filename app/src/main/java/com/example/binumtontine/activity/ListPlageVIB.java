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
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class ListPlageVIB extends AppCompatActivity implements SERVER_ADDRESS {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_TAS_ID = "Numero";
    private static final String KEY_TAS_LIBELLE = "Libelle";
    private static final String KEY_EAV_ID = "ev_numero"; // Idem pour tous les eav
    private static final String KEY_PD_TYPE_DATA = "PdTypeData"; //nouveau
    private static final String KEY_Produit_ID = "ProduitId"; //nouveau 15/10/2020
    public static boolean  IS_TO_CREATE_OR_TO_UPDATE = false;

    private ArrayList<HashMap<String, String>> movieList;
    private ListView movieListView;
    private ProgressDialog pDialog;
    private String eavId;
    public static String pdTypeData="VIB"; //nouveau
    public static NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_produit_eav);
/* begin */
      //  setContentView(R.layout.activity_movie_listing);
        defaultFormat.setCurrency(Currency.getInstance("xaf"));
        Intent intent = getIntent();
        eavId = intent.getStringExtra(KEY_EAV_ID); //Utiliser pour les mises à jours
        movieListView = (ListView) findViewById(R.id.movieList);
        new FetchListTxIntAvancSpecAsyncTask().execute();

        /* end*/

        Toolbar toolbar = findViewById(R.id.toolbar_produitEAV);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Plage "+MyData.TYPE_DE_FRAIS_PLAGE_DATA);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab_produitEAV);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ajouter une plage", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
              //  Intent i = new Intent(ProduitEAVActivity.this, AddMovieActivity.class);
                Intent i = new Intent(ListPlageVIB.this, PlageDataVIB.class);
                i.putExtra(KEY_Produit_ID, eavId);
                startActivityForResult(i, 20);


            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

private void loadPlageForCreateTxIntAvancSpec(){

    try {
        //int success = jsonObject.getInt(KEY_SUCCESS);
        int success = 1;
        //  JSONArray movies;
        if (success == 1) {
            movieList = new ArrayList<>();
            // movies = jsonObject.getJSONArray(KEY_DATA);
            //Parcours la liste des plages du produit
                for (int i = 0; i < CreateProduitEAV.plageDataListVIB.size(); i++) {
                // JSONObject movie = movies.getJSONObject(i);
                Integer movieId = i;
                String movieName ="";
                if (CreateProduitEAV.plageDataListVIB.get(i).getPdNature().equals("F")){
                    movieName = "De:"+CreateProduitEAV.plageDataListVIB.get(i).getPdValDe()+
                            " A - "+ CreateProduitEAV.plageDataListVIB.get(i).getPdValA()+
                            " ( "+ defaultFormat.format(parseDouble(CreateProduitEAV.plageDataListVIB.get(i).getPdValTaux()))+" )";
                }else{
                    movieName = "De:"+CreateProduitEAV.plageDataListVIB.get(i).getPdValDe()+
                            " A - "+ CreateProduitEAV.plageDataListVIB.get(i).getPdValA()+
                            " ( "+ parseDouble(CreateProduitEAV.plageDataListVIB.get(i).getPdValTaux())+" %)";

                }


                HashMap<String, String> map = new HashMap<String, String>();
                map.put(KEY_TAS_ID, movieId.toString());
                map.put(KEY_TAS_LIBELLE, movieName);
                movieList.add(map);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

}
private void loadPlageForUpdateTAS(){
            HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String, String> httpParams = new HashMap<>();
        httpParams.put(KEY_EAV_ID, eavId);
    httpParams.put(KEY_PD_TYPE_DATA, pdTypeData); //nouveau
        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                BASE_URL + "fetch_all_plage_eav.php", "GET", httpParams);




    try {
        int success = jsonObject.getInt(KEY_SUCCESS);

          JSONArray movies;
        if (success == 1) {
            movieList = new ArrayList<>();
             movies = jsonObject.getJSONArray(KEY_DATA);
            //Parcours la liste des plages du produit
            for (int i = 0; i < movies.length(); i++) {
                 JSONObject movie = movies.getJSONObject(i);
                Integer PdNumero = movie.getInt(KEY_TAS_ID);
                String PdTypeData = movie.getString(KEY_TAS_LIBELLE);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(KEY_TAS_ID, PdNumero.toString());
                map.put(KEY_TAS_LIBELLE, PdTypeData);
                movieList.add(map);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
/**
 * Fetches the list of movies from the server
 */
private class FetchListTxIntAvancSpecAsyncTask extends AsyncTask<String, String, String> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Display progress bar
        pDialog = new ProgressDialog(ListPlageVIB.this);
        pDialog.setMessage("Chargement des plages. Veuillez patienter...");
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // Progress Dialog Max Value
//        pDialog.setMax(100);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {

if (IS_TO_CREATE_OR_TO_UPDATE){
    loadPlageForCreateTxIntAvancSpec();
}else loadPlageForUpdateTAS();


        return null;
    }

    protected void onPostExecute(String result) {
        pDialog.dismiss();
        runOnUiThread(new Runnable() {
            public void run() {
                populateMovieListPlageTxIntAvanSpec();
            }
        });
    }

}

    /**
     * Updating parsed JSON data into ListView
     * */
    private void populateMovieListPlageTxIntAvanSpec() {
        ListAdapter adapter = new SimpleAdapter(
                ListPlageVIB.this, movieList,
                R.layout.list_item, new String[]{KEY_TAS_ID,
                KEY_TAS_LIBELLE},
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
                            PlageDataVIB.class);
                    if (IS_TO_CREATE_OR_TO_UPDATE){
                        PlageDataVIB.what_to_do=2;  //Pour la mise à jour d'un produit en cours d'enregistrement
                    }else{
                        PlageDataVIB.what_to_do=3; //Pour la mise à jour d'un produit déjà enregistré
                    }

                    intent.putExtra(KEY_TAS_ID, movieId);
                    startActivityForResult(intent, 20);

                } else {
                    Toast.makeText(ListPlageVIB.this,
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
            PlageDataVIB.what_to_do=0;
        }
    }

}


