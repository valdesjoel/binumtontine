package com.example.binumtontine.activity.parametrageGuichet;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.CreateProduitEAV;
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

public class TypeMembreGxActivity extends AppCompatActivity implements SERVER_ADDRESS {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";

    private static final String KEY_EAV_ID = "TmNumero";
    private static final String KEY_TX_ID = "TxNumero";
    private static final String KEY_TX_TYPE_MEMBRE = "TxTypMembre";
    private static final String KEY_TX_NEW_LIBELLE = "TxNewLibelle";
    private static final String KEY_EXTRA_ACTION_TO_AFFECT = "ACTION_TO_AFFECT"; //to push intent.extra
    private static final String KEY_EAV_LIBELLE = "TmLibelle";
    private static final String KEY_CAISSE_ID = "TxCaisse";
    private static final String KEY_GUICHET_ID = "TxGuichet";


    private ArrayList<HashMap<String, String>> movieList;
    private ListView movieListView;
    private ProgressDialog pDialog;
    private Boolean action_to_affect=false;
    private String tmNumero="";
    private int success;
    //private EditText taskEditText = new EditText(TypeMembreCxActivity.this);
    private String task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_type_membre_cx);
/* begin */
      //  setContentView(R.layout.activity_movie_listing);
        movieListView = (ListView) findViewById(R.id.movieList);
        new FetchProduitEAVAsyncTask().execute();


        /* end*/

        Toolbar toolbar = findViewById(R.id.toolbar_type_membre_cx);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Membres à affecter") ;
//        if (!action_to_affect){
//            getSupportActionBar().setTitle("Types de membres à affecter") ;
//        }else{
//            getSupportActionBar().setTitle("Types de membres déjà affectés") ;
//        }
        //this.setCheckedItem(R.id.nav_new);


        FloatingActionButton fab = findViewById(R.id.fab_type_membre_cx);
        fab.hide(); //to hide FloatingActionButton

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ajouter un produit EAV", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
              //  Intent i = new Intent(ProduitEAVActivity.this, AddMovieActivity.class);
                Intent i = new Intent(TypeMembreGxActivity.this, CreateProduitEAV.class);
                startActivityForResult(i, 20);


            }
        });
    }
    private void showAddItemDialog(Context c) {
        final EditText taskEditText = new EditText(c);
        taskEditText.setText(task);
        //taskEditText = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Affectation d'un type de membre")
                .setMessage("Renommer ce type de membre")
                .setView(taskEditText)
                .setPositiveButton("Affecter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //String task = String.valueOf(taskEditText.getText());
                        task = String.valueOf(taskEditText.getText());
                        new TypeMembreGxActivity.DeleteMovieAsyncTask().execute();
                    }
                })
                .setNegativeButton("Annuler", null)
                .create();
        dialog.show();
    }

    /* To manage Menu*/
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_type_membre_gx, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_all:
               // new ProduitEAVGuichetActivity.FetchProduitEAVAsyncTask().execute();
               // startActivity(new Intent(this, About.class));
                return true;
            case R.id.action_to_affect:
                action_to_affect = false;

                    getSupportActionBar().setTitle("Membres à affecter") ;



                new FetchProduitEAVAsyncTask().execute();
               // startActivity(new Intent(this, Help.class));
                return true;
                case R.id.action_already_affect:
                    action_to_affect = true;
                    getSupportActionBar().setTitle("Membres déjà affectés") ;
                     new FetchProduitEAVAsyncTask().execute();
               // startActivity(new Intent(this, Help.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


/**
 * Fetches the list of movies from the server
 */
private class FetchProduitEAVAsyncTask extends AsyncTask<String, String, String> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Display progress bar
        pDialog = new ProgressDialog(TypeMembreGxActivity.this);
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
            httpParams.put(KEY_GUICHET_ID, String.valueOf(MyData.GUICHET_ID));
            JSONObject jsonObject =(action_to_affect)? httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_type_membre_by_guichet.php", "GET", httpParams): httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_type_membre_not_affect_on_guichet.php", "GET", httpParams);


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
                TypeMembreGxActivity.this, movieList,
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
                    tmNumero = ((TextView) view.findViewById(R.id.movieId))
                            .getText().toString();
                  task   = ((TextView) view.findViewById(R.id.movieName))
                            .getText().toString();
                   // taskEditText.setText(task);
                    Intent intent = new Intent(getApplicationContext(),
                            UpdateEAVForGuichet.class);
                    intent.putExtra(KEY_EAV_ID, tmNumero);
                    intent.putExtra(KEY_EXTRA_ACTION_TO_AFFECT, !action_to_affect);
                    if (!action_to_affect){
                        confirmAffectation();
                    }else{
                        confirmDelete();
                    }

                  //  intent.putExtra(KEY_GX_NUMERO, MyData.GUICHET_ID);

                    //startActivityForResult(intent, 20);

                } else {
                    Toast.makeText(TypeMembreGxActivity.this,
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



    /**
     * Displays an alert dialogue to confirm the deletion
     */
    private void confirmDelete() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                TypeMembreGxActivity.this);
        alertDialogBuilder.setMessage("Êtes-vous sûr de vouloir désaffecter ce type de membre ?");
        alertDialogBuilder.setPositiveButton("Désaffecter",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                            //If the user confirms deletion, execute DeleteMovieAsyncTask
                            new TypeMembreGxActivity.DeleteMovieAsyncTask().execute();
                        } else {
                            Toast.makeText(TypeMembreGxActivity.this,
                                    "Impossible de se connecter à Internet",
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                });

        alertDialogBuilder.setNegativeButton("Annuler", null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }   /**
     * Displays an alert dialogue to confirm the deletion
     */
    private void confirmAffectation() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                TypeMembreGxActivity.this);
        alertDialogBuilder.setMessage("Êtes-vous sûr de vouloir affecter ce type de membre à la cette caisse ?");
        alertDialogBuilder.setPositiveButton("Affecter",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                            //If the user confirms deletion, execute DeleteMovieAsyncTask
                            showAddItemDialog(TypeMembreGxActivity.this);
                            //new TypeMembreCxActivity.DeleteMovieAsyncTask().execute();
                        } else {
                            Toast.makeText(TypeMembreGxActivity.this,
                                    "Impossible de se connecter à Internet",
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                });

        alertDialogBuilder.setNegativeButton("Annuler", null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * AsyncTask to delete or affect type membre on caisse
     */
    private class DeleteMovieAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(TypeMembreGxActivity.this);
            if (!action_to_affect){
                pDialog.setMessage("Affectation du type de membre au guichet. Veuillez patienter...");
            }else{
                pDialog.setMessage("Suppression du type de membre. SVP veuillez patienter...");
            }
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Set movie_id parameter in request
            httpParams.put(KEY_TX_TYPE_MEMBRE, tmNumero);
            httpParams.put(KEY_TX_ID, tmNumero);
            httpParams.put(KEY_TX_NEW_LIBELLE, task);
            httpParams.put(KEY_CAISSE_ID, String.valueOf(MyData.CAISSE_ID));
            httpParams.put(KEY_GUICHET_ID, String.valueOf(MyData.GUICHET_ID));


            JSONObject jsonObject =(!action_to_affect)?
                    httpJsonParser.makeHttpRequest(
                            BASE_URL + "assign_type_membre_gx.php", "POST", httpParams)
                    :
                    httpJsonParser.makeHttpRequest(
                            BASE_URL + "delete_type_membre_cx.php", "POST", httpParams);

            try {
                success = jsonObject.getInt(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        if (!action_to_affect){
                            Toast.makeText(TypeMembreGxActivity.this,
                                    "Type membre"+" affecté au guichet "+MyData.GUICHET_NAME, Toast.LENGTH_LONG).show();
                        }else{

                            Toast.makeText(TypeMembreGxActivity.this,
                                    "Type membre supprimé sur le guichet "+MyData.GUICHET_NAME, Toast.LENGTH_LONG).show();
                        }
                        Intent i = getIntent();
                        //send result code 20 to notify about movie deletion
                        setResult(20, i);
                        finish();
                        startActivity(i);

                    } else {
                        Toast.makeText(TypeMembreGxActivity.this,

                                        "Une erreur s'est produite lors de l'opération sur le type de membre",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }


}


