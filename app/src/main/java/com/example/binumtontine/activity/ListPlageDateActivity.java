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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListPlageDateActivity extends AppCompatActivity implements SERVER_ADDRESS {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_EAV_ID = "ev_numero";
    private static final String KEY_CTP_ID = "CTP_numero";
    private static final String KEY_EAV_LIBELLE = "ev_libelle";
    private static final String KEY_CTP_LIBELLE = "CTP_libelle";
    private static final String KEY_CAISSE_ID = "ev_caisse_id";
    public static boolean  IS_TO_CREATE_OR_TO_UPDATE = false;

    private ArrayList<HashMap<String, String>> movieList;
    private ListView movieListView;
    private ProgressDialog pDialog;
    private String eavId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_produit_eav);
/* begin */
      //  setContentView(R.layout.activity_movie_listing);
        Intent intent = getIntent();
        eavId = intent.getStringExtra(KEY_EAV_ID);
        movieListView = (ListView) findViewById(R.id.movieList);
        new ListPlageDateActivity.FetchMoviesAsyncTask().execute();

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
                Intent i = new Intent(ListPlageDateActivity.this, PlageData.class);
                startActivityForResult(i, 20);


            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
private void loadPlageForCreate(){
    //        HttpJsonParser httpJsonParser = new HttpJsonParser();
//        Map<String, String> httpParams = new HashMap<>();
//        httpParams.put(KEY_CAISSE_ID, String.valueOf(MyData.CAISSE_ID));
//        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
//                BASE_URL + "fetch_all_eav.php", "GET", httpParams);




    try {
        //int success = jsonObject.getInt(KEY_SUCCESS);
        int success = 1;
        //  JSONArray movies;
        if (success == 1) {
            movieList = new ArrayList<>();
            // movies = jsonObject.getJSONArray(KEY_DATA);
            //Parcours la liste des plages du produit
            for (int i = 0; i < CreateProduitEAV.plageDataList.size(); i++) {
                // JSONObject movie = movies.getJSONObject(i);
                Integer movieId = i;

                String movieName = CreateProduitEAV.plageDataList.get(i).getPdValDe()+
                        " - "+CreateProduitEAV.plageDataList.get(i).getPdValA()+
                        " ( "+CreateProduitEAV.plageDataList.get(i).getPdValTaux()+" )";
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(KEY_EAV_ID, movieId.toString());
                map.put(KEY_EAV_LIBELLE, movieName);
                movieList.add(map);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
private void loadPlageForCreateTxIntDecouvCC(){
    //        HttpJsonParser httpJsonParser = new HttpJsonParser();
//        Map<String, String> httpParams = new HashMap<>();
//        httpParams.put(KEY_CAISSE_ID, String.valueOf(MyData.CAISSE_ID));
//        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
//                BASE_URL + "fetch_all_eav.php", "GET", httpParams);




    try {
        //int success = jsonObject.getInt(KEY_SUCCESS);
        int success = 1;
        //  JSONArray movies;
        if (success == 1) {
            movieList = new ArrayList<>();
            // movies = jsonObject.getJSONArray(KEY_DATA);
            //Parcours la liste des plages du produit
            for (int i = 0; i < CreateProduitCpteCourant.plageDataListCTP.size(); i++) {
                // JSONObject movie = movies.getJSONObject(i);
                Integer movieId = i;

                String movieName = CreateProduitCpteCourant.plageDataListCTP.get(i).getPdValDe()+
                        " - "+CreateProduitCpteCourant.plageDataListCTP.get(i).getPdValA()+
                        " ( "+CreateProduitCpteCourant.plageDataListCTP.get(i).getPdValTaux()+" )";
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(KEY_CTP_ID, movieId.toString());
                map.put(KEY_CTP_LIBELLE, movieName);
                movieList.add(map);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
private void loadPlageForUpdate(){
            HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String, String> httpParams = new HashMap<>();
        httpParams.put(KEY_CAISSE_ID, eavId);
        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                BASE_URL + "fetch_all_tiv_eav.php", "GET", httpParams);




    try {
        int success = jsonObject.getInt(KEY_SUCCESS);

          JSONArray movies;
        if (success == 1) {
            movieList = new ArrayList<>();
             movies = jsonObject.getJSONArray(KEY_DATA);
            //Parcours la liste des plages du produit
            for (int i = 0; i < movies.length(); i++) {
                 JSONObject movie = movies.getJSONObject(i);
                Integer PdNumero = movie.getInt(KEY_EAV_ID);
                String PdTypeData = movie.getString(KEY_EAV_LIBELLE);
//                String PdNature = movie.getString(KEY_EAV_LIBELLE);
//                String PdValTaux = movie.getString(KEY_EAV_LIBELLE);
//                String PdValDe = movie.getString(KEY_EAV_LIBELLE);
//                String PdValA = movie.getString(KEY_EAV_LIBELLE);
//                String PdBase = movie.getString(KEY_EAV_LIBELLE);
//                String PdProduit = movie.getString(KEY_EAV_LIBELLE);

//                ModelPlageData maPlage = new ModelPlageData(PdNumero+"",PdTypeData,PdNature,
//                        PdValTaux,PdValDe,PdValA,PdBase,PdProduit);
//                UpdateEAV.plageDataListCTP.add(maPlage);

//                String concatenateData = CreateProduitEAV.plageDataListCTP.get(i).getPdValDe()+
//                        " - "+CreateProduitEAV.plageDataListCTP.get(i).getPdValA()+
//                        " ( "+CreateProduitEAV.plageDataListCTP.get(i).getPdValTaux()+" )";
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(KEY_EAV_ID, PdNumero.toString());
                map.put(KEY_EAV_LIBELLE, PdTypeData);
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
private class FetchMoviesAsyncTask extends AsyncTask<String, String, String> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Display progress bar
        pDialog = new ProgressDialog(ListPlageDateActivity.this);
        pDialog.setMessage("Chargement des plages. Veuillez patienter...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
//        HttpJsonParser httpJsonParser = new HttpJsonParser();
//        Map<String, String> httpParams = new HashMap<>();
//        httpParams.put(KEY_CAISSE_ID, String.valueOf(MyData.CAISSE_ID));
//        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
//                BASE_URL + "fetch_all_eav.php", "GET", httpParams);

if (IS_TO_CREATE_OR_TO_UPDATE){
    loadPlageForCreate();
}else loadPlageForUpdate();

//
//        try {
//            //int success = jsonObject.getInt(KEY_SUCCESS);
//            int success = 1;
//          //  JSONArray movies;
//            if (success == 1) {
//                movieList = new ArrayList<>();
//               // movies = jsonObject.getJSONArray(KEY_DATA);
//                //Parcours la liste des plages du produit
//                for (int i = 0; i < CreateProduitEAV.plageDataListCTP.size(); i++) {
//                   // JSONObject movie = movies.getJSONObject(i);
//                    Integer movieId = i;
//
//                    String movieName = CreateProduitEAV.plageDataListCTP.get(i).getPdValDe()+
//                            " - "+CreateProduitEAV.plageDataListCTP.get(i).getPdValA()+
//                            " ( "+CreateProduitEAV.plageDataListCTP.get(i).getPdValTaux()+" )";
//                    HashMap<String, String> map = new HashMap<String, String>();
//                    map.put(KEY_EAV_ID, movieId.toString());
//                    map.put(KEY_EAV_LIBELLE, movieName);
//                    movieList.add(map);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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
 * Fetches the list of movies from the server
 */
private class FetchListTxIntDecouvAsyncTask extends AsyncTask<String, String, String> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Display progress bar
        pDialog = new ProgressDialog(ListPlageDateActivity.this);
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
//        HttpJsonParser httpJsonParser = new HttpJsonParser();
//        Map<String, String> httpParams = new HashMap<>();
//        httpParams.put(KEY_CAISSE_ID, String.valueOf(MyData.CAISSE_ID));
//        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
//                BASE_URL + "fetch_all_eav.php", "GET", httpParams);

if (IS_TO_CREATE_OR_TO_UPDATE){
    loadPlageForCreateTxIntDecouvCC();
}else loadPlageForUpdate();


        return null;
    }

    protected void onPostExecute(String result) {
        pDialog.dismiss();
        runOnUiThread(new Runnable() {
            public void run() {
                populateMovieListPlageTxIntDecouv();
            }
        });
    }

}

    /**
     * Updating parsed JSON data into ListView
     * */
    private void populateMovieList() {
        ListAdapter adapter = new SimpleAdapter(
                ListPlageDateActivity.this, movieList,
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
                            PlageData.class);
                    if (IS_TO_CREATE_OR_TO_UPDATE){
                        PlageData.what_to_do=2;
                    }else{
                        PlageData.what_to_do=3;
                    }

                    intent.putExtra(KEY_EAV_ID, movieId);
                    startActivityForResult(intent, 20);

                } else {
                    Toast.makeText(ListPlageDateActivity.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }


            }
        });

    }
    /**
     * Updating parsed JSON data into ListView
     * */
    private void populateMovieListPlageTxIntDecouv() {
        ListAdapter adapter = new SimpleAdapter(
                ListPlageDateActivity.this, movieList,
                R.layout.list_item, new String[]{KEY_CTP_ID,
                KEY_CTP_LIBELLE},
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
                            PlageData.class);
                    if (IS_TO_CREATE_OR_TO_UPDATE){
                        PlageData.what_to_do=2;
                    }else{
                        PlageData.what_to_do=3;
                    }

                    intent.putExtra(KEY_CTP_ID, movieId);
                    startActivityForResult(intent, 20);

                } else {
                    Toast.makeText(ListPlageDateActivity.this,
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
            PlageData.what_to_do=0;
        }
    }

}


