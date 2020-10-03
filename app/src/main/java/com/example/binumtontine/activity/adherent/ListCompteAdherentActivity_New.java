package com.example.binumtontine.activity.adherent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.binumtontine.R;
import com.example.binumtontine.adapter.CustomAdapterListAdherent;
import com.example.binumtontine.adapter.CustomAdapterListComptesAdherent;
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

public class ListCompteAdherentActivity_New extends AppCompatActivity implements SERVER_ADDRESS {

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
    private static final String KEY_DATA_COMPTE_COURANT = "CC";
    private static final String KEY_DATA_EAT = "EAT";
    private static final String KEY_DATA_EAP = "EAP";
    private static final String KEY_DATA_DEMANDE_CREDIT = "DC";
    private static final String KEY_COMPTE_ID = "Numero";
    private static final String KEY_LIBELLE_PRODUIT = "Libelle";
    private static final String KEY_NUM_DOSSIER_COMPTE = "NumDossier";
    private static final String KEY_MONTANT_COMPTE = "MtSolde";
    private static final String KEY_DATE_H_CREE = "DateHCree";
    private static final String KEY_TAUX = "Taux";
    private static final String KEY_TYPE_COMPTE = "TypeCompte";
    private static final String KEY_ADHERENT = "ADHERENT";
    public static Adherent adherent;


    private static final String KEY_ADHERENT_ID = "IpMembre";

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

        //initializing views
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listAdherent = new ArrayList<>();
        listComptesAdherent = new ArrayList<>();


        defaultFormat.setCurrency(Currency.getInstance("Fcf"));

        Intent intent = getIntent();
        adherentId = intent.getStringExtra(KEY_ADHERENT_ID);
        Bundle bundle = intent.getExtras();
        adherent = (Adherent) bundle.getSerializable(KEY_ADHERENT);
        getSupportActionBar().setTitle(adherent.getAdNom()+"\n"+adherent.getAdPrenom());
        new ListCompteAdherentActivity_New.FetchListCompteAdherentAsyncTask().execute();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void loadRecyclerViewItem() {
        //you can fetch the data from server or some apis

        adapter = new CustomAdapterListComptesAdherent(listComptesAdherent, this);
        recyclerView.setAdapter(adapter);

    }
    /**
     * Fetches the list of accounts adherent from the server
     */
    private class FetchListCompteAdherentAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(ListCompteAdherentActivity_New.this);
            pDialog.setMessage("Chargement des comptes. Patientez...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_ADHERENT_ID, adherentId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_comptes_by_adherent.php", "GET", httpParams);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray movies;
                if (success == 1) {
                    compteAdherentList = new ArrayList<>();
                    //list EAV
                    movies = jsonObject.getJSONArray(KEY_DATA_EAV);
                    //Iterate through the response and populate movies list
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject compte = movies.getJSONObject(i);
                        Integer compteId = compte.getInt(KEY_COMPTE_ID);
                        String compteDetail = compte.getString(KEY_LIBELLE_PRODUIT);
                        String compteNumDossier = compte.getString(KEY_NUM_DOSSIER_COMPTE);
                        String compteMontant = compte.getString(KEY_MONTANT_COMPTE);
                        String compteDateCreation = compte.getString(KEY_DATE_H_CREE);

                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_COMPTE_ID, compteId.toString());
                        map.put(KEY_LIBELLE_PRODUIT, compteDetail);
                        map.put(KEY_NUM_DOSSIER_COMPTE, compteNumDossier);
                        map.put(KEY_MONTANT_COMPTE, defaultFormat.format(parseDouble(compteMontant))+CgDevise);
                        map.put(KEY_DATE_H_CREE, compteDateCreation.substring(0,10));
                        map.put(KEY_TYPE_COMPTE, "EAV");
                        compteAdherentList.add(map);
                        ComptesAdherent mesComptes = new ComptesAdherent(compteId,compteDetail,compteNumDossier,
                                compteDateCreation.substring(0,10),defaultFormat.format(parseDouble(compteMontant))+CgDevise,
                                "EAV","");
                        listComptesAdherent.add(mesComptes);
                    }
                    //list Compte courant
                    movies = jsonObject.getJSONArray(KEY_DATA_COMPTE_COURANT);
                    //Iterate through the response and populate movies list
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject compte = movies.getJSONObject(i);
                        Integer compteId = compte.getInt(KEY_COMPTE_ID);
                        String compteDetail = compte.getString(KEY_LIBELLE_PRODUIT);
                        String compteNumDossier = compte.getString(KEY_NUM_DOSSIER_COMPTE);
                        String compteMontant = compte.getString(KEY_MONTANT_COMPTE);
                        String compteDateCreation = compte.getString(KEY_DATE_H_CREE);

                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_COMPTE_ID, compteId.toString());
                        map.put(KEY_LIBELLE_PRODUIT, compteDetail);
                        map.put(KEY_NUM_DOSSIER_COMPTE, compteNumDossier);
                        map.put(KEY_MONTANT_COMPTE, defaultFormat.format(parseDouble(compteMontant))+CgDevise);
                        map.put(KEY_DATE_H_CREE, compteDateCreation.substring(0,10));
                        map.put(KEY_TYPE_COMPTE, "COMPTE COURANT");
                        compteAdherentList.add(map);
                        ComptesAdherent mesComptes = new ComptesAdherent(compteId,compteDetail,compteNumDossier,
                                compteDateCreation.substring(0,10),defaultFormat.format(parseDouble(compteMontant))+CgDevise,
                                "COMPTE COURANT","");
                        listComptesAdherent.add(mesComptes);
                    }
                    //list EAT
                    movies = jsonObject.getJSONArray(KEY_DATA_EAT);
                    //Iterate through the response and populate movies list
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject compte = movies.getJSONObject(i);
                        Integer compteId = compte.getInt(KEY_COMPTE_ID);
                        String compteDetail = compte.getString(KEY_LIBELLE_PRODUIT);
                        String compteNumDossier = compte.getString(KEY_NUM_DOSSIER_COMPTE);
                        String compteMontant = compte.getString(KEY_MONTANT_COMPTE);
                        String compteDateCreation = compte.getString(KEY_DATE_H_CREE);
                        String compteTaux = compte.getString(KEY_TAUX);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_COMPTE_ID, compteId.toString());
                        map.put(KEY_LIBELLE_PRODUIT, compteDetail);
                        map.put(KEY_NUM_DOSSIER_COMPTE, compteNumDossier);
                        map.put(KEY_MONTANT_COMPTE, defaultFormat.format(parseDouble(compteMontant))+CgDevise);
                        map.put(KEY_DATE_H_CREE, compteDateCreation.substring(0,10));
                        map.put(KEY_TAUX, compteTaux);
                        map.put(KEY_TYPE_COMPTE, "EAT");
                        compteAdherentList.add(map);
                        ComptesAdherent mesComptes = new ComptesAdherent(compteId,compteDetail,compteNumDossier,
                                compteDateCreation.substring(0,10),defaultFormat.format(parseDouble(compteMontant))+CgDevise,
                                "EAT",compteTaux);
                        listComptesAdherent.add(mesComptes);
                    }
                    //list EAP
                    movies = jsonObject.getJSONArray(KEY_DATA_EAP);
                    //Iterate through the response and populate movies list
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject compte = movies.getJSONObject(i);
                        Integer compteId = compte.getInt(KEY_COMPTE_ID);
                        String compteDetail = compte.getString(KEY_LIBELLE_PRODUIT);
                        String compteNumDossier = compte.getString(KEY_NUM_DOSSIER_COMPTE);
                        String compteMontant = compte.getString(KEY_MONTANT_COMPTE);
                        String compteDateCreation = compte.getString(KEY_DATE_H_CREE);
                        String compteTaux = compte.getString(KEY_TAUX);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_COMPTE_ID, compteId.toString());
                        map.put(KEY_LIBELLE_PRODUIT, compteDetail);
                        map.put(KEY_NUM_DOSSIER_COMPTE, compteNumDossier);
                        map.put(KEY_MONTANT_COMPTE, defaultFormat.format(parseDouble(compteMontant))+CgDevise);
                        map.put(KEY_DATE_H_CREE, compteDateCreation.substring(0,10));
                        map.put(KEY_TAUX, compteTaux);
                        map.put(KEY_TYPE_COMPTE, "EAP");
                        compteAdherentList.add(map);

                        ComptesAdherent mesComptes = new ComptesAdherent(compteId,compteDetail,compteNumDossier,
                                compteDateCreation.substring(0,10),defaultFormat.format(parseDouble(compteMontant))+CgDevise,
                                "EAP",compteTaux);
                        listComptesAdherent.add(mesComptes);
                    }
                    //list Demande de crédit
                    movies = jsonObject.getJSONArray(KEY_DATA_DEMANDE_CREDIT);
                    //Iterate through the response and populate movies list
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject compte = movies.getJSONObject(i);
                        Integer compteId = compte.getInt(KEY_COMPTE_ID);
                        String compteDetail = compte.getString(KEY_LIBELLE_PRODUIT);
                        String compteNumDossier = compte.getString(KEY_NUM_DOSSIER_COMPTE);
                        String compteMontant = compte.getString(KEY_MONTANT_COMPTE);
                        String compteDateCreation = compte.getString(KEY_DATE_H_CREE);
                        String compteTaux = compte.getString(KEY_TAUX);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_COMPTE_ID, compteId.toString());
                        map.put(KEY_LIBELLE_PRODUIT, compteDetail);
                        map.put(KEY_NUM_DOSSIER_COMPTE, compteNumDossier);
                        map.put(KEY_MONTANT_COMPTE, defaultFormat.format(parseDouble(compteMontant))+CgDevise);
                        map.put(KEY_DATE_H_CREE, compteDateCreation.substring(0,10));
                        map.put(KEY_TAUX, compteTaux);
                        map.put(KEY_TYPE_COMPTE, "CREDIT");
                        compteAdherentList.add(map);

                        ComptesAdherent mesComptes = new ComptesAdherent(compteId,compteDetail,compteNumDossier,
                                compteDateCreation.substring(0,10),defaultFormat.format(parseDouble(compteMontant))+CgDevise,
                                "CREDIT",compteTaux);
                        listComptesAdherent.add(mesComptes);
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
