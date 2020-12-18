package com.example.binumtontine.activity.parametreGenerauxCx;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListEtapeDemandeCreditCx extends AppCompatActivity implements SERVER_ADDRESS {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";

    private static final String KEY_ED_NUMERO = "EdNumero";
    private static final String KEY_TX_TYPE_MEMBRE = "TxTypMembre";
    private static final String KEY_TX_NEW_LIBELLE = "TxNewLibelle";
    private static final String KEY_EXTRA_ACTION_TO_AFFECT = "ACTION_TO_AFFECT"; //to push intent.extra
    private static final String KEY_ED_LIBELLE = "EdLibelle";
    private static final String KEY_OC_DETAILS = "OcDetails";
    private static final String KEY_CAISSE_ID = "EdCaisse";


    private ArrayList<HashMap<String, String>> movieList;
    private ListView movieListView;
    private ProgressDialog pDialog;
    private Boolean action_to_affect=false;
    private String EdNumero ="";
    private int success;
    //private EditText taskEditText = new EditText(TypeMembreCxActivity.this);
    private String EdLibelle;
    private String detailsObjetCredit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_list_etapes_demande_credit);
/* begin */
      //  setContentView(R.layout.activity_movie_listing);
        movieListView = (ListView) findViewById(R.id.movieList);
        new FetchProduitEAVAsyncTask().execute();


        /* end*/

        Toolbar toolbar = findViewById(R.id.toolbar_produitEAV);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Etapes à affecter") ;


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab_produitEAV);
        fab.hide();
//        if (!action_to_affect){
//            getSupportActionBar().setTitle("Types de membres à affecter") ;
//        }else{
//            getSupportActionBar().setTitle("Types de membres déjà affectés") ;
//        }
        //this.setCheckedItem(R.id.nav_new);



    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void showAddItemDialog(Context c) {
        final EditText taskEditText = new EditText(c);
        taskEditText.setText(EdLibelle);
        final EditText detailsObjetCreditEditText = new EditText(c);
        taskEditText.setText(detailsObjetCredit);
        //taskEditText = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Affectation d'un objet de crédit")
                .setMessage("Renommer cet objet de crédit")
                .setView(taskEditText)
                .setPositiveButton("Affecter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //String EdLibelle = String.valueOf(taskEditText.getText());
                        EdLibelle = String.valueOf(taskEditText.getText());
                        detailsObjetCredit = String.valueOf(detailsObjetCreditEditText.getText());
                        new AffectOrDeleteMovieAsyncTask().execute();
                    }
                })
                .setNegativeButton("Annuler", null)
                .create();
        dialog.show();
    }

    /* To manage Menu*/
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_etape_credit_cx, menu);
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

                    getSupportActionBar().setTitle("Etapes à affecter") ;



                new FetchProduitEAVAsyncTask().execute();
               // startActivity(new Intent(this, Help.class));
                return true;
                case R.id.action_already_affect:
                    action_to_affect = true;
                    getSupportActionBar().setTitle("Etapes déjà affectés") ;
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
        pDialog = new ProgressDialog(ListEtapeDemandeCreditCx.this);
        pDialog.setMessage("Chargement des  étapes de demande de crédit. SVP Veuillez patienter...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {


        HttpJsonParser httpJsonParser = new HttpJsonParser();
        Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_CAISSE_ID, String.valueOf(MyData.CAISSE_ID));
            JSONObject jsonObject =(action_to_affect)? httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_etapes_demande_credit_by_caisse.php", "GET", httpParams): httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_etapes_demande_credit_not_affect_on_caisse.php", "GET", httpParams);
        Log.e("httpParams",httpParams+"");
        Log.e("jsonObject",jsonObject+"");

        try {
            int success = jsonObject.getInt(KEY_SUCCESS);
            JSONArray movies;
            if (success == 1) {
                movieList = new ArrayList<>();
                movies = jsonObject.getJSONArray(KEY_DATA);
                //Iterate through the response and populate movies list
                for (int i = 0; i < movies.length(); i++) {
                    JSONObject movie = movies.getJSONObject(i);
                    Integer movieId = movie.getInt(KEY_ED_NUMERO);
                    String movieName = MyData.normalizeSymbolsAndAccents(movie.getString(KEY_ED_LIBELLE));
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(KEY_ED_NUMERO, movieId.toString());
                    map.put(KEY_ED_LIBELLE, movieName);
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
                ListEtapeDemandeCreditCx.this, movieList,
                R.layout.list_item, new String[]{KEY_ED_NUMERO,
                KEY_ED_LIBELLE},
                new int[]{R.id.movieId, R.id.movieName});
        // updating listview
        movieListView.setAdapter(adapter);
        //Call MovieUpdateDeleteActivity when a movie is clicked
        movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Check for network connectivity
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    EdNumero = ((TextView) view.findViewById(R.id.movieId))
                            .getText().toString();
                  EdLibelle = ((TextView) view.findViewById(R.id.movieName))
                            .getText().toString();

                   // taskEditText.setText(EdLibelle);
                    Intent intent = new Intent(getApplicationContext(),
                            AffectEtapeDemandeCreditCx.class);
                    intent.putExtra(KEY_ED_NUMERO, EdNumero);
                    intent.putExtra(KEY_EXTRA_ACTION_TO_AFFECT, !action_to_affect);
                    if (!action_to_affect){
//                        confirmAffectation();
                        startActivityForResult(intent, 20);
                    }else{
                        confirmDelete();
                    }

                  //  intent.putExtra(KEY_GX_NUMERO, MyData.GUICHET_ID);

//                    startActivityForResult(intent, 20);

                } else {
                    Toast.makeText(ListEtapeDemandeCreditCx.this,
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
                ListEtapeDemandeCreditCx.this);
        alertDialogBuilder.setMessage("Êtes-vous sûr de vouloir supprimer cette Etape de crédit ?");
        alertDialogBuilder.setPositiveButton("Supprimer",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                            //If the user confirms deletion, execute AffectOrDeleteMovieAsyncTask
                            new AffectOrDeleteMovieAsyncTask().execute();
                            new DeleteObjetCreditAsyncTask().execute();
                        } else {
                            Toast.makeText(ListEtapeDemandeCreditCx.this,
                                    "Impossible de se connecter à Internet",
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                });

        alertDialogBuilder.setNegativeButton("Cancel", null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }   /**
     * Displays an alert dialogue to confirm the deletion
     */
    private void confirmAffectation() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                ListEtapeDemandeCreditCx.this);
        alertDialogBuilder.setMessage("Êtes-vous sûr de vouloir affecter cet objet de crédit à cette caisse ?");
        alertDialogBuilder.setPositiveButton("Affecter",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                            //If the user confirms deletion, execute AffectOrDeleteMovieAsyncTask
                            showAddItemDialog(ListEtapeDemandeCreditCx.this);
                            //new TypeMembreCxActivity.AffectOrDeleteMovieAsyncTask().execute();
                        } else {
                            Toast.makeText(ListEtapeDemandeCreditCx.this,
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
    private class AffectOrDeleteMovieAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(ListEtapeDemandeCreditCx.this);
            if (!action_to_affect){
                pDialog.setMessage("Affectation de l'étape de demande de crédit à la caisse. Veuillez patienter...");
            }else{
                pDialog.setMessage("Suppression de l'étape de demande de crédit. SVP veuillez patienter...");
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
            httpParams.put(KEY_TX_TYPE_MEMBRE, EdNumero);
            httpParams.put(KEY_TX_NEW_LIBELLE, EdLibelle);
            httpParams.put(KEY_CAISSE_ID, String.valueOf(MyData.CAISSE_ID));


            JSONObject jsonObject =(!action_to_affect)?
                    httpJsonParser.makeHttpRequest(
                            BASE_URL + "assign_type_membre_cx.php", "POST", httpParams)
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
                            Toast.makeText(ListEtapeDemandeCreditCx.this,
                                    "Objet de crédit"+" affecté à la caisse "+MyData.CAISSE_NAME, Toast.LENGTH_LONG).show();
                        }else{

                            Toast.makeText(ListEtapeDemandeCreditCx.this,
                                    "Objet de crédit supprimé sur la caisse "+MyData.CAISSE_NAME, Toast.LENGTH_LONG).show();
                        }
                        Intent i = getIntent();
                        //send result code 20 to notify about movie deletion
                        setResult(20, i);
                        finish();
                        startActivity(i);

                    } else {
                        Toast.makeText(ListEtapeDemandeCreditCx.this,

                                        "Une erreur s'est produite lors de l'opération sur l'objet crédit",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    /**
     * AsyncTask to delete a guichet
     */
    private class DeleteObjetCreditAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(ListEtapeDemandeCreditCx.this);
            pDialog.setMessage("Suppression de l'étape de demande de Crédit. Veuillez patienter...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Set guichet_id parameter in request
            httpParams.put(KEY_ED_NUMERO, EdNumero);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "delete_etape_demande_credit.php", "POST", httpParams);
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
                        Toast.makeText(ListEtapeDemandeCreditCx.this,
                                "Etape Demande Crédit Supprimé", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie deletion
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(ListEtapeDemandeCreditCx.this,
                                "Some error occurred while deleting Etape Demande Crédit",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }


}


