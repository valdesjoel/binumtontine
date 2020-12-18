/*package com.example.binumtontine.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import eav.os.Bundle;

import com.example.binumtontine.R;

public class CreateProduitEAV extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_param_produit_eav);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_produit_eav);
        setSupportActionBar(toolbar);
        setToolbarTitle();
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Ajout d'un produit EAV");

    }
}*/
package com.example.binumtontine.activity.adherent;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.modele.CompteCourant;
import com.example.binumtontine.activity.ServiceHandler;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateCpteCourant extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    /*Begin*/
         //to fetchProduitList by caisse and guichet ID
    private static final String KEY_EV_CAISSE_ID = "ev_caisse_id";
    private static final String KEY_EV_GUICHET_ID = "ev_gx_numero";
    private static final String KEY_CC_CAISSE_NUMERO = "CcCaisseId";
    private static final String KEY_GX_NUMERO = "CcGuichetId";
    /* end*/
    private static final String KEY_CPTE_COURANT_ID = "CcNumero";
    private static final String KEY_CPTE_COURANT_LIBELLE = "CcLibelle";
    private static final String KEY_CPTE_COURANT_CcIsDecouvOn = "CcIsDecouvOn";
    private static final String KEY_CPTE_COURANT_CcMtMaxDecouv = "CcMtMaxDecouv";
    private static final String KEY_CPTE_COURANT_CcIsMaxDecouvNeg = "CcIsMaxDecouvNeg";

    private static final String KEY_CPTE_COURANT_ID_1 = "CcNumero";
    private static final String KEY_CPTE_COURANT_LIBELLE_1 = "CcLibelle";

    private static final String KEY_CV_PRODUIT = "CcaProduit";
    private static final String KEY_CV_MEMBRE = "CcaMembre";
    private static final String KEY_CV_GUICHET = "CcaGuichet";
    private static final String KEY_CV_NUM_DOSSIER = "CcaNumDossier";
    private static final String KEY_CV_MT_SOLDE = "CcaMtSolde";
    private static final String KEY_CcaMtPlafondDecouvNeg = "CcaMtPlafondDecouvNeg";
    private static final String KEY_CV_USER_CREE = "CcaUserCree";
    private static final String KEY_ADHERENT_NUM_DOSSIER = "CcaNumDossier";

    /*Param for get extra*/
    private static final String KEY_ADHERENT_ID = "IpMembre";
    private static final String KEY_ADHERENT_NOM = "AdNom";
    private static final String KEY_ADHERENT_PRENOM = "AdPrenom";
    private static final String KEY_ADHERENT_NUM_MANUEL = "AdNumManuel";
    private static final String KEY_ADHERENT_CODE = "AdCode";



    private static String STRING_EMPTY = "";

    private EditText EavDepotMinEditText;
    private EditText CcaMtMaxDecouvEditText;
    private EditText NumDossierEditText;


    private String adherentId;
    private String eavDepotMin;
    private String adNom;
    private String adPrenom;
    private String adNumManuel;
    private String adCode;
    private String adNumDossier;

    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<CompteCourant> cpteCourantList;
    List<Integer> cpteCourantListID = new ArrayList<Integer>();
    List<Double> listCcMtMaxDecouv = new ArrayList<Double>();
    List<String> listCcIsDecouvOn = new ArrayList<String>();
    List<String> listCcIsMaxDecouvNeg = new ArrayList<String>();
    private int eavID;
    private Double CcMtMaxDecouv;
    private String CcIsDecouvOn;
    private String CcIsMaxDecouvNeg;
    private Spinner spinnerListCC;
    private TextView tvAdherentNom;
    private TextView tvAdherentNumManuel;
    private TextView tvAdherentCode;
    private TextView tvAdherentNumDossier;
    /*end manage*/

    private Button addButton;
    private Button annulerButton;
    private int success;
    private ProgressDialog pDialog;
    private ProgressDialog pDialogFetchProduitEavList;
    private TextView tv_title_cc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_eav_adherent);


        Intent intent = getIntent();
        adherentId = intent.getStringExtra(KEY_ADHERENT_ID);
        adNom = intent.getStringExtra(KEY_ADHERENT_NOM);
        adPrenom = intent.getStringExtra(KEY_ADHERENT_PRENOM);
        adNumManuel = intent.getStringExtra(KEY_ADHERENT_NUM_MANUEL);
        adCode = intent.getStringExtra(KEY_ADHERENT_CODE);
        //adNumDossier = intent.getStringExtra(KEY_ADHERENT_NUM_DOSSIER);

        EavDepotMinEditText = (EditText) findViewById(R.id.input_txt_depot_min);
        CcaMtMaxDecouvEditText = (EditText) findViewById(R.id.input_txt_CcaMtMaxDecouv);
        NumDossierEditText = (EditText) findViewById(R.id.input_txt_num_dossier_adherent);


        tv_title_cc = (TextView) findViewById(R.id.tv_title_eav);
        tv_title_cc.setText("Produit compte courant");
        spinnerListCC = (Spinner) findViewById(R.id.spn_list_eav);
        tvAdherentNom = (TextView) findViewById(R.id.tv_nom_adherent);
        tvAdherentNom.setText(adNom+"\n"+adPrenom);
        tvAdherentNumManuel = (TextView) findViewById(R.id.tv_num_manuel_adherent);
        tvAdherentNumManuel.setText(adNumManuel);
        tvAdherentCode = (TextView) findViewById(R.id.tv_code_adherent);
        tvAdherentCode.setText(adCode);
       /* tvAdherentNumDossier = (TextView) findViewById(R.id.tv_num_dossier_adherent);
        tvAdherentNumDossier.setText(adNumDossier);*/

        cpteCourantList = new ArrayList<CompteCourant>();
        // spinner item select listener
        spinnerListCC.setOnItemSelectedListener(CreateCpteCourant.this);
        new GetProduitEAVList().execute();

        addButton = (Button) findViewById(R.id.btn_save_compte_eav);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(CreateCpteCourant.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addEavAdherent();
                } else {
                    Toast.makeText(CreateCpteCourant.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });


    }


    /**
     * Adding spinner data
     * */
    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();

        //tvCaisse.setText("");

        for (int i = 0; i < cpteCourantList.size(); i++) {
            lables.add(cpteCourantList.get(i).getCcLibelle());//recupère les noms
            cpteCourantListID.add(cpteCourantList.get(i).getCcNumero()); //recupère les Id
            listCcMtMaxDecouv.add(cpteCourantList.get(i).getCcMtMaxDecouv()); //recupère les Id
            listCcIsDecouvOn.add(cpteCourantList.get(i).getCcIsDecouvOn()); //recupère l'état d'ctivation des découverts
            listCcIsMaxDecouvNeg.add(cpteCourantList.get(i).getCcIsMaxDecouvNeg()); //recupère l'état d'ctivation des découverts
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(CreateCpteCourant.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerListCC.setAdapter(spinnerAdapter);
    }

    /**
     * Async task to get all food categories
     * */
    private class GetProduitEAVList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogFetchProduitEavList = new ProgressDialog(CreateCpteCourant.this);
            pDialogFetchProduitEavList.setMessage("Fetching produits's list..");
            pDialogFetchProduitEavList.setCancelable(false);
            pDialogFetchProduitEavList.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair(KEY_CC_CAISSE_NUMERO, String.valueOf(MyData.CAISSE_ID)));
            httpParams.add(new BasicNameValuePair(KEY_GX_NUMERO, String.valueOf(MyData.GUICHET_ID)));
//            httpParams.add(new BasicNameValuePair(KEY_EV_GUICHET_ID, String.valueOf(MyData.GUICHET_ID)));
//            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_eav_by_guichet.php", ServiceHandler.GET, httpParams);
//            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_cpte_courant.php", ServiceHandler.GET, httpParams);
            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_cpte_courant_by_guichet.php", ServiceHandler.GET, httpParams);
           // String json = jsonParser.makeServiceCall(URL_GUICHETS, ServiceHandler.GET);



            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj
                                .getJSONArray(KEY_DATA);

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            CompteCourant cat = new CompteCourant(catObj.getInt(KEY_CPTE_COURANT_ID),
                                    catObj.getString(KEY_CPTE_COURANT_LIBELLE),
                                    catObj.getString(KEY_CPTE_COURANT_CcIsDecouvOn),
                                    catObj.getDouble(KEY_CPTE_COURANT_CcMtMaxDecouv),
                                    catObj.getString(KEY_CPTE_COURANT_CcIsMaxDecouvNeg));
                            cpteCourantList.add(cat);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialogFetchProduitEavList.isShowing())
                pDialogFetchProduitEavList.dismiss();
            populateSpinner();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
     /*   Toast.makeText(
                getApplicationContext(),
                parent.getItemAtPosition(position).toString() + " Selected" ,
                Toast.LENGTH_LONG).show();
        */
        eavID = cpteCourantListID.get(position);//pour recuperer l'ID du guichet selectionnée
        CcMtMaxDecouv = listCcMtMaxDecouv.get(position);//pour recuperer le montant max decouvert du produit cc selectionné
        CcIsDecouvOn = listCcIsDecouvOn.get(position);//pour recuperer le CcIsDecouvOn du produit selectionné
        CcIsMaxDecouvNeg = listCcIsMaxDecouvNeg.get(position);//pour recuperer le CcIsDecouvOn du produit selectionné
        CcaMtMaxDecouvEditText.setText(CcMtMaxDecouv+"");

        if (Boolean.parseBoolean(CcIsDecouvOn)){
            CcaMtMaxDecouvEditText.setVisibility(View.VISIBLE);
            if (Boolean.parseBoolean(CcIsMaxDecouvNeg)){
                CcaMtMaxDecouvEditText.setFocusable(true);
                CcaMtMaxDecouvEditText.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
                CcaMtMaxDecouvEditText.setClickable(true); // user navigates with wheel and selects widget
            }else{
                CcaMtMaxDecouvEditText.setFocusable(false);
                CcaMtMaxDecouvEditText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                CcaMtMaxDecouvEditText.setClickable(false); // user navigates with wheel and selects widget
            }
        }else{
            CcaMtMaxDecouvEditText.setVisibility(View.GONE);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }


    /**
     * Checks whether all files are filled. If so then calls AddCpteCourantAdherentAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addEavAdherent() {
        if (!STRING_EMPTY.equals(EavDepotMinEditText.getText().toString()) &&
            !STRING_EMPTY.equals(NumDossierEditText.getText().toString()) &&
                eavID !=0 ) {

                eavDepotMin = EavDepotMinEditText.getText().toString();
//            CcMtMaxDecouv = CcaMtMaxDecouvEditText.getText().toString();
                adNumDossier = NumDossierEditText.getText().toString();

                new AddCpteCourantAdherentAsyncTask().execute();


        } else {
            Toast.makeText(CreateCpteCourant.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for adding a compte eav
     */
    private class AddCpteCourantAdherentAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(CreateCpteCourant.this);
            pDialog.setMessage("Adding compte courant. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
           // httpParams.put(KEY_CPTE_COURANT_ID, uxGuichetId);

            httpParams.put(KEY_CV_MEMBRE, adherentId);
            httpParams.put(KEY_CV_PRODUIT, String.valueOf(eavID));
            httpParams.put(KEY_CV_GUICHET, String.valueOf(MyData.GUICHET_ID));
            httpParams.put(KEY_CV_NUM_DOSSIER, adNumDossier);
            httpParams.put(KEY_CV_MT_SOLDE, eavDepotMin );
            httpParams.put(KEY_CcaMtPlafondDecouvNeg, CcaMtMaxDecouvEditText.getText().toString() );
            httpParams.put(KEY_CV_USER_CREE, String.valueOf(MyData.USER_ID));

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_compte_courant_adherent_new.php", "POST", httpParams);
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
                        Toast.makeText(CreateCpteCourant.this,
                                "Compte créé avec succès", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CreateCpteCourant.this,
                                "Some error occurred while adding Compte",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}