/* package com.example.binumtontine.activity;

import eav.os.Bundle;

import com.example.binumtontine.R;
import com.google.eav.material.floatingactionbutton.FloatingActionButton;
import com.google.eav.material.snackbar.Snackbar;
import com.google.eav.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import eav.view.Menu;
import eav.view.MenuInflater;
import eav.view.MenuItem;
import eav.view.View;

import com.example.binumtontine.activity.ui.main.SectionsPagerAdapter;

public class CaisseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caisse);





        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ajouter une caisse", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }
}*/

package com.example.binumtontine.activity.adherent;

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
import com.example.binumtontine.activity.CreateGuichet;
import com.example.binumtontine.activity.UpdateGuichet;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class ListCompteAdherentActivity extends AppCompatActivity implements SERVER_ADDRESS {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA_EAV = "EAV";
    private static final String KEY_DATA_EAT = "EAT";
    private static final String KEY_DATA_EAP = "EAP";
    private static final String KEY_COMPTE_ID = "Numero";
    private static final String KEY_LIBELLE_PRODUIT = "Libelle";
    private static final String KEY_NUM_DOSSIER_COMPTE = "NumDossier";
    private static final String KEY_MONTANT_COMPTE = "MtSolde";
    private static final String KEY_DATE_H_CREE = "DateHCree";
    private static final String KEY_TAUX = "Taux";
    private static final String KEY_TYPE_COMPTE = "TypeCompte";
    private static final String KEY_ADHERENT = "ADHERENT";
    private Adherent adherent;


    private static final String KEY_ADHERENT_ID = "IpMembre";

    private String adherentId;

    private ArrayList<HashMap<String, String>> compteAdherentList;
    private ListView compteAdherentListView;
    private ProgressDialog pDialog;


    private NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
    private String CgDevise ="A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_compte_adherent);
        defaultFormat.setCurrency(Currency.getInstance("Fcf"));

        Intent intent = getIntent();
        adherentId = intent.getStringExtra(KEY_ADHERENT_ID);
        Bundle bundle = intent.getExtras();
        adherent = (Adherent) bundle.getSerializable(KEY_ADHERENT);
        /* begin */
        compteAdherentListView = (ListView) findViewById(R.id.compteAdherentList);
        new FetchListCompteAdherentAsyncTask().execute();

        /* end*/


        Toolbar toolbar = findViewById(R.id.toolbar_list_guichet);
        setSupportActionBar(toolbar);
        setToolbarTitle();

        FloatingActionButton fab = findViewById(R.id.fab_list_compte);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ajouter un compte", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //  Intent i = new Intent(ProduitEAVActivity.this, AddMovieActivity.class);
                Intent i = new Intent(ListCompteAdherentActivity.this, CreateGuichet.class);
                startActivityForResult(i, 20);
               // startActivity(i);



            }
        });
    }
    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Comptes:"+" "+adherent.getAdNom()+" "+adherent.getAdPrenom());

    }



    /**
     * Fetches the list of movies from the server
     */
    private class FetchListCompteAdherentAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(ListCompteAdherentActivity.this);
            pDialog.setMessage("Loading comptes. Please wait...");
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
                    populateGuichetList();
                }
            });
        }

    }

    /**
     * Updating parsed JSON data into ListView
     * */
    private void populateGuichetList() {

        ListAdapter adapter = new SimpleAdapter(
                ListCompteAdherentActivity.this, compteAdherentList,
                R.layout.list_item_compte_adherent, new String[]{KEY_COMPTE_ID,
                KEY_LIBELLE_PRODUIT,KEY_NUM_DOSSIER_COMPTE,KEY_DATE_H_CREE,KEY_MONTANT_COMPTE},
                new int[]{R.id.compteId, R.id.libelleProduit,R.id.compteNumDossier,R.id.compteDateCreation,R.id.compteMontant});
        // updating listview
        compteAdherentListView.setAdapter(adapter);
        //Call MovieUpdateDeleteActivity when a movie is clicked
        compteAdherentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Check for network connectivity
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    String compteId = ((TextView) view.findViewById(R.id.compteId))
                            .getText().toString();
                    String compteMontant = ((TextView) view.findViewById(R.id.compteMontant))
                            .getText().toString();
                    String compteLibelleProduit = ((TextView) view.findViewById(R.id.libelleProduit))
                            .getText().toString();
                    String compteDateCreation = ((TextView) view.findViewById(R.id.compteDateCreation))
                            .getText().toString();
                    String typeCompte = compteAdherentList.get(i).get(KEY_TYPE_COMPTE);
                    String compteTaux = compteAdherentList.get(i).get(KEY_TAUX);
                    Intent intent;
                    if (typeCompte.equals("EAV")){
                        intent = new Intent(getApplicationContext(),
                                OperationEAV.class);
                    }else if (typeCompte.equals("EAT")){
                        intent = new Intent(getApplicationContext(),
                                OperationEAT.class);
                    }else{
                        intent = new Intent(getApplicationContext(),
                                OperationEAP.class);
                    }

                    intent.putExtra(KEY_COMPTE_ID, compteId);
                    intent.putExtra(KEY_MONTANT_COMPTE, compteMontant);
                    intent.putExtra(KEY_LIBELLE_PRODUIT, compteLibelleProduit);
                    intent.putExtra(KEY_DATE_H_CREE, compteDateCreation);
                    intent.putExtra(KEY_TAUX, compteTaux);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(KEY_ADHERENT, (Serializable) adherent);
                    // bundle.putSerializable(KEY_ADHERENT, adherent);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 20);

                } else {
                    Toast.makeText(ListCompteAdherentActivity.this,
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


