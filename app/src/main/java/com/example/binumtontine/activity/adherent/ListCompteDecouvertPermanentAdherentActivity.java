package com.example.binumtontine.activity.adherent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.binumtontine.R;
import com.example.binumtontine.adapter.CustomAdapterListDecouvertAdherent;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.HttpJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class ListCompteDecouvertPermanentAdherentActivity extends AppCompatActivity implements SERVER_ADDRESS {

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
    private List<ComptesAdherent> listComptesAdherent;


    /*BEGIN*/
    private static final String KEY_DATA_EAV = "EAV";
    private static final String KEY_DATA_DECOUVERT = "DF";
    private static final String KEY_DATA_COMPTE_COURANT = "CC";
    private static final String KEY_DATA_EAT = "EAT";
    private static final String KEY_DATA_EAP = "EAP";
    private static final String KEY_DATA_DEMANDE_CREDIT = "DC";
    private static final String KEY_COMPTE_ID = "Numero";
    private static final String KEY_LIBELLE_PRODUIT = "Libelle";
    private static final String KEY_DfStatut = "DfStatut";
    private static final String KEY_NUM_DOSSIER_COMPTE = "NumDossier";
    private static final String KEY_MONTANT_COMPTE = "MtSolde";
    private static final String KEY_MONTANT_ACCORDE_COMPTE = "DfMtAccordeCom";
    private static final String KEY_MONTANT_DEMANDE_DECOUV = "DfMtDecouv";
    private static final String KEY_DATE_H_CREE = "DateHCree";
    private static final String KEY_TAUX = "Taux";
    private static final String KEY_TYPE_COMPTE = "TypeCompte";
    private static final String KEY_ADHERENT = "ADHERENT";
//    public static Adherent adherent;
    public static ComptesAdherent adherent;


    private static final String KEY_ADHERENT_ID = "IpMembre";
    private static final String KEY_TYPE_OPERATION = "TypeOperation";

    private String adherentId;

    private ArrayList<HashMap<String, String>> compteAdherentList;
    private ListView compteAdherentListView;


    private NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
    private String CgDevise ="A";
    /*END*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_compte_adherent_new);
        //Toolbar toolbar = findViewById(R.id.toolbar_list_adherent);
        Toolbar toolbar = findViewById(R.id.toolbar_list_adherent);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setTitle(MyData.GUICHET_NAME) ;

        //initializing views
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listAdherent = new ArrayList<>();
        listComptesAdherent = new ArrayList<>();

        //loading listAdherent view item with this function
        //loadRecyclerViewItem();
        //new ListCompteAdherentActivity_New.FetchAdherentGuichetAsyncTask().execute();

        defaultFormat.setCurrency(Currency.getInstance("Fcf"));

        Intent intent = getIntent();
        adherentId = intent.getStringExtra(KEY_ADHERENT_ID);
        Bundle bundle = intent.getExtras();
        adherent = (ComptesAdherent) bundle.getSerializable(KEY_ADHERENT);
        getSupportActionBar().setTitle("Decouv Permanent");
        getSupportActionBar().setSubtitle(ListCompteAdherentActivity_New.adherent.getAdNom()+" "+ListCompteAdherentActivity_New.adherent.getAdPrenom());
        new FetchListDecouvertAdherentAsyncTask().execute();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /* To manage Menu*/
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home_decouvert_permanent_adherent, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:

                return true;
            case R.id.action_decouvert_permanent_add:
//                Intent i = new Intent(ListCompteDecouvertPermanentAdherentActivity.this, OperationDecouvertPermanent.class);
                Intent i = new Intent(ListCompteDecouvertPermanentAdherentActivity.this, OperationCompteCourant.class);
                i.putExtra(KEY_TYPE_OPERATION, "P");
                startActivityForResult(i,20);


                return true;



            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void loadRecyclerViewItem() {
        //you can fetch the data from server or some apis

        adapter = new CustomAdapterListDecouvertAdherent(listComptesAdherent, this);
        recyclerView.setAdapter(adapter);

    }
    /**
     * Fetches the list of accounts adherent from the server
     */
    private class FetchListDecouvertAdherentAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(ListCompteDecouvertPermanentAdherentActivity.this);
            pDialog.setMessage("Chargement des découverts permanents. Patientez...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_ADHERENT_ID, adherentId);
            httpParams.put(KEY_TYPE_OPERATION, "P");
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_decouvert_by_adherent.php", "GET", httpParams);
            Log.e("Response: ", "> **********" + adherentId);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray movies;
                if (success == 1) {
                    compteAdherentList = new ArrayList<>();
                    Log.e("Response: ", "> **********test covid19 " + adherentId);

//                    //list Decouvert Permanent
                    movies = jsonObject.getJSONArray(KEY_DATA_DECOUVERT);
                    //Iterate through the response and populate movies list
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject compte = movies.getJSONObject(i);
                        Integer compteId = compte.getInt(KEY_COMPTE_ID);
                        String compteDetail = compte.getString(KEY_DfStatut);
                        String compteNumDossier = compte.getString(KEY_NUM_DOSSIER_COMPTE);
                        String compteMontant = compte.getString(KEY_MONTANT_DEMANDE_DECOUV);
                        String compteDateCreation = compte.getString(KEY_DATE_H_CREE);

                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_COMPTE_ID, compteId.toString());
                        map.put(KEY_DfStatut, compteDetail);
                        map.put(KEY_NUM_DOSSIER_COMPTE, compteNumDossier);
                        map.put(KEY_MONTANT_DEMANDE_DECOUV, defaultFormat.format(parseDouble(compteMontant))+CgDevise);
                        map.put(KEY_DATE_H_CREE, compteDateCreation.substring(0,10));
                        map.put(KEY_TYPE_COMPTE, "DECOUVERT PERMANENT");
                        compteAdherentList.add(map);
                        ComptesAdherent mesComptes = new ComptesAdherent(compteId,compteDetail,compteNumDossier,
                                compteDateCreation.substring(0,10),defaultFormat.format(parseDouble(compteMontant))+CgDevise,
                                "P","");
                        listComptesAdherent.add(mesComptes);
                        Log.e("Response: ", "> **********compteNumDossier" + compteNumDossier);
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
                    //populateGuichetList();
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


}
