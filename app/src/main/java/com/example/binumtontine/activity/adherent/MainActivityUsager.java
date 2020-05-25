package com.example.binumtontine.activity.adherent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.binumtontine.R;
import com.example.binumtontine.adapter.CustomAdapterListAdherent;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivityUsager extends AppCompatActivity implements SERVER_ADDRESS {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";

    private static final String KEY_AD_AdNumero = "AdNumero";
    private static final String KEY_AD_AdCode = "AdCode";
    private static final String KEY_AD_AdNumManuel = "AdNumManuel";
    private static final String KEY_AD_AdNom = "AdNom";
    private static final String KEY_AD_AdPrenom = "AdPrenom";
    private static final String KEY_AD_AdDateNaiss = "AdDateNaiss";
    private static final String KEY_AD_AdLieuNaiss = "AdLieuNaiss";
    private static final String KEY_AD_AdSexe = "AdSexe";
    private static final String KEY_AD_AdNationalite = "AdNationalite";
    private static final String KEY_AD_AdSitFam = "AdSitFam";
    private static final String KEY_AD_AdNbreEnfACh = "AdNbreEnfACh";
    private static final String KEY_AD_AdTel1 = "AdTel1";
    private static final String KEY_AD_AdTel2 = "AdTel2";
    private static final String KEY_AD_AdTel3 = "AdTel3";
    private static final String KEY_AD_AdEMail = "AdEMail";
    private static final String KEY_AD_AdProfess = "AdProfess";

    private static final String KEY_AD_AdDomicile = "AdDomicile";
    private static final String KEY_AD_AdLieuTrav = "AdLieuTrav";
    private static final String KEY_AD_AdActivitePr = "AdActivitePr";
    private static final String KEY_AD_AdTypCarteID = "AdTypCarteID";
    private static final String KEY_AD_AdNumCarteID = "AdNumCarteID";
    private static final String KEY_AD_AdValideDu = "AdValideDu";
    private static final String KEY_AD_AdValideAu = "AdValideAu";
    private static final String KEY_AD_AdTypHabite = "AdTypHabite";
    private static final String KEY_AD_AdEstParti = "AdEstParti";
    private static final String KEY_AD_AdPartiLe = "AdPartiLe";
    private static final String KEY_AD_AdRemplacePar = "AdRemplacePar";
    private static final String KEY_AD_NBRE_COMPTE = "AdNbreCompte";
    //private static final String KEY_AD_NBRE_COMPTE = "AdNbreCompte";

    private static final String KEY_AD_GUICHET = "AdGuichet";

    //recyclerview objects
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ProgressDialog pDialog;
    //model object for our listAdherent data
    private ArrayList<HashMap<String, String>> fraisList = new ArrayList<>();
    //private List<MyList> listAdherent;
    private List<Adherent> listAdherent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_adherent);
        //Toolbar toolbar = findViewById(R.id.toolbar_list_adherent);
        Toolbar toolbar = findViewById(R.id.toolbar_list_adherent);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(MyData.GUICHET_NAME) ;

        //initializing views
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listAdherent = new ArrayList<>();

        //loading listAdherent view item with this function
        //loadRecyclerViewItem();
        new MainActivityUsager.FetchAdherentGuichetAsyncTask().execute();
    }

//    boolean doubleBackToExitPressedOnce = false;
//
//    @Override
//    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            return;
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "SVP veuillez cliquer deux fois pour sortir !", Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce=false;
//            }
//        }, 2000);
//    }
@Override
public void onBackPressed() {
    new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Quitter l'application")
            .setMessage("Etes-vous sûr de vouloir vous déconnecter ?")
            .setPositiveButton("Oui", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }

            })
            .setNegativeButton("Non", null)
            .show();
}
    /* To manage Menu*/
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home_adherent, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // new ProduitEAVGuichetActivity.FetchMoviesAsyncTask().execute();

                return true;
            case R.id.action_person_add:
                Intent i = new Intent(MainActivityUsager.this, CreateAdherent.class);
                startActivityForResult(i,20);
                // new ProduitEAVGuichetActivity.FetchMoviesAsyncTask().execute();
                // startActivity(new Intent(this, About.class));
                return true;
            case R.id.action_situation_guichet:
                //action_to_affect = false;
                //new ProduitEAVGuichetActivity.FetchMoviesAsyncTask().execute();
                // startActivity(new Intent(this, Help.class));
                return true;
            case R.id.action_brouillard_de_caisse:
                //action_to_affect = true;
                //new ProduitEAVGuichetActivity.FetchMoviesAsyncTask().execute();
                // startActivity(new Intent(this, Help.class));
                return true;
                case R.id.action_cloturer_la_journee:
               // action_to_affect = true;
             //   new ProduitEAVGuichetActivity.FetchMoviesAsyncTask().execute();
                // startActivity(new Intent(this, Help.class));
                return true;
                case R.id.action_operation_externe:
              //  action_to_affect = true;
                //new ProduitEAVGuichetActivity.FetchMoviesAsyncTask().execute();
                // startActivity(new Intent(this, Help.class));
                return true;
                case R.id.action_cotisations_annuelle:
              //  action_to_affect = true;
                //new ProduitEAVGuichetActivity.FetchMoviesAsyncTask().execute();
                // startActivity(new Intent(this, Help.class));
                return true;
                case R.id.action_jour_anterieur:
               // action_to_affect = true;
                //new ProduitEAVGuichetActivity.FetchMoviesAsyncTask().execute();
                // startActivity(new Intent(this, Help.class));
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void loadRecyclerViewItem() {
        //you can fetch the data from server or some apis
        //for this tutorial I am adding some dummy data directly
     /*   for (int i = 1; i <= 5; i++) {
            MyList myList = new MyList(
                    "Dummy Heading " + i,
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi molestie nisi dui."
            );
            listAdherent.add(myList);
        }
        */

        adapter = new CustomAdapterListAdherent(listAdherent, this);
        recyclerView.setAdapter(adapter);
        //Call MovieUpdateDeleteActivity when a movie is clicked
       /* recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Check for network connectivity
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    String movieId = ((TextView) view.findViewById(R.id.movieId))
                            .getText().toString();
                    Intent intent = new Intent(getApplicationContext(),
                            UpdateCaisse.class);
                    intent.putExtra(KEY_CAISSE_ID, movieId);
                    startActivityForResult(intent, 20);

                } else {
                    Toast.makeText(CaisseActivity.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }


            }
        }); */
    }

    /**
     * Fetches the listAdherent of movies from the server
     */
    public  class FetchAdherentGuichetAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(MainActivityUsager.this);
            pDialog.setMessage("Loading adherents. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_AD_GUICHET, String.valueOf(MyData.GUICHET_ID));
            //httpParams.put(KEY_GUICHET_ID, String.valueOf(MyData.GUICHET_ID));
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_adherents.php", "GET", httpParams);
            //creating Arraylist
            //List<String> fruitList = new ArrayList<>();
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray movies;
                if (success == 1) {
                    //fraisList = new ArrayList<>();
                    movies = jsonObject.getJSONArray(KEY_DATA);

                    //Iterate through the response and populate movies listAdherent
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject guichet = movies.getJSONObject(i);
                        String adherentID = guichet.getString(KEY_AD_AdNumero);
                        String adherentCode = guichet.getString(KEY_AD_AdCode);
                        String adherentNumManuel = guichet.getString(KEY_AD_AdNumManuel);
                        String adherentNom = guichet.getString(KEY_AD_AdNom);
                        String adherentPrenom = guichet.getString(KEY_AD_AdPrenom);
                        String adherentDateNaiss = guichet.getString(KEY_AD_AdDateNaiss);
                        String adherentLieuNaiss = guichet.getString(KEY_AD_AdLieuNaiss);
                        String adherentSexe = guichet.getString(KEY_AD_AdSexe);
                        String adherentNationalite = guichet.getString(KEY_AD_AdNationalite);
                        String adherentSituaFamiliale = guichet.getString(KEY_AD_AdSitFam);
                        String adherentNbreEnfant = guichet.getString(KEY_AD_AdNbreEnfACh);
                        String adherentTel1 = guichet.getString(KEY_AD_AdTel1);
                        String adherentTel2 = guichet.getString(KEY_AD_AdTel2);
                        String adherentTel3 = guichet.getString(KEY_AD_AdTel3);
                        String adherentEmail = guichet.getString(KEY_AD_AdEMail);
                        String adherentProfession = guichet.getString(KEY_AD_AdProfess);
                        String adherentDomicile = guichet.getString(KEY_AD_AdDomicile);
                        String adherentLieuTravail = guichet.getString(KEY_AD_AdLieuTrav);
                        String adherentActivitePrincipale = guichet.getString(KEY_AD_AdActivitePr);
                        String adherentTypeCarteID = guichet.getString(KEY_AD_AdTypCarteID);
                        String adherentNumCarteID = guichet.getString(KEY_AD_AdNumCarteID);
                        String adherentValideDu = guichet.getString(KEY_AD_AdValideDu);
                        String adherentValideAu = guichet.getString(KEY_AD_AdValideAu);
                        String adherentTypHabite = guichet.getString(KEY_AD_AdTypHabite);
                        String adherentEstParti = guichet.getString(KEY_AD_AdEstParti);
                        String adherentPartiLe = guichet.getString(KEY_AD_AdPartiLe);
                        String adherentRemplacePar = guichet.getString(KEY_AD_AdRemplacePar);
                        String adherentNbreCompte = guichet.getString(KEY_AD_NBRE_COMPTE);
                        //MyList myList = new MyList(
                        Adherent myList = new Adherent(
                                adherentID ,
                                adherentCode,
                                adherentNumManuel,
                                adherentNom ,
                                adherentPrenom,
                                adherentDateNaiss,
                                adherentLieuNaiss,
                                adherentSexe,
                                adherentNationalite,
                                adherentSituaFamiliale,
                                adherentNbreEnfant,
                                adherentTel1,
                                adherentTel2,
                                adherentTel3,
                                adherentEmail,
                                adherentProfession,
                                adherentDomicile,
                                adherentLieuTravail,
                                adherentActivitePrincipale,
                                adherentTypeCarteID,
                                adherentNumCarteID,
                                adherentValideDu,
                                adherentValideAu,
                                adherentTypHabite,
                                adherentEstParti,
                                adherentPartiLe,
                                adherentRemplacePar,
                                MyData.GUICHET_ID,
                                adherentNbreCompte

                        );
                      /*  Integer guichetId = guichet.getInt(KEY_FC_NUMERO);
                        String guichetDenomination = guichet.getString(KEY_FC_NEW_LIBELLE);
                        String guichetDenomination = guichet.getString(KEY_FC_NEW_LIBELLE);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_FC_NUMERO, guichetId.toString());
                        map.put(KEY_FC_NEW_LIBELLE, guichetDenomination);*/
                        //adding String Objects to fruitsList ArrayList
                        // MyData.fruitList.add(guichetDenomination);
                       /* fraisList.add(map);*/
                        listAdherent.add(myList);
                    }
                    // Log.d("convert to array","coucouuuuuuuuuuuuuuuuu"+fruitList.size());
                    //String[] item = fruitList.toArray(new String[fruitList.size()]);
                    // Log.d("*********item****",item.length+"");
                    //MyData.animallist = item;

                    // Log.d("********animallist",MyData.animallist.length+"");
                    //Log.d("********animallist",MyData.animallist[0]+"");
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
                   // populateGuichetList();
                    loadRecyclerViewItem();
                }
            });
        }



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
     * Updating parsed JSON data into ListView
     * */
    private void populateGuichetList() {
      /*  ListAdapter adapter = new SimpleAdapter(
                GetFraisAdherent.this, fraisList,
                R.layout.list_item, new String[]{KEY_FC_NUMERO,
                KEY_FC_NEW_LIBELLE},
                new int[]{R.id.movieId, R.id.movieName});
        // updating listview
        lv.setAdapter(adapter); */
        //lv.setAdapter(customAdapterListViewCheckbox);
        //Call MovieUpdateDeleteActivity when a movie is clicked
      /*  lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Check for network connectivity
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    String guichetId = ((TextView) view.findViewById(R.id.movieId))
                            .getText().toString();
                    Intent intent = new Intent(getApplicationContext(),
                            UpdateGuichet.class);
                    intent.putExtra(KEY_GUICHET_ID, guichetId);
                    startActivityForResult(intent, 20);

                } else {
                    Toast.makeText(GetPieceAdherent.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }


            }
        }); */

    }


}
