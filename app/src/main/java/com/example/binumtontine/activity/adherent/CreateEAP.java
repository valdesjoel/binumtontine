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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.Category;
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

public class CreateEAP extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    /*Begin*/
         //to fetchProduitList by caisse and guichet ID
    private static final String KEY_EP_CAISSE_ID = "EpCaisseId";
    private static final String KEY_EP_GUICHET_ID = "EpGuichetId";
    /* end*/
    private static final String KEY_EAP_ID = "EpNumero";
    private static final String KEY_EAP_LIBELLE = "EpLibelle";
    private static final String KEY_EAP_TAUX = "EpValTxInter";

    private static final String KEY_CP_PRODUIT = "CpProduit";
    private static final String KEY_CP_MEMBRE = "CpMembre";
    private static final String KEY_CP_GUICHET = "CpGuichet";
    private static final String KEY_CP_NUM_DOSSIER = "CpNumDossier";
    private static final String KEY_CP_DEPOT_MIN = "CpDepotMin";
    private static final String KEY_CP_TAUX = "CpTaux";
    private static final String KEY_CP_NBRE_UNITE = "CpNbUnites";
    private static final String KEY_CP_USER_CREE = "CpUserCree";
    private static final String KEY_CP_MOD_RENOUV = "CpModRenouv";
    //private static final String KEY_ADHERENT_NUM_DOSSIER = "CpNumDossier";

    /*Param for get extra*/
    private static final String KEY_ADHERENT_ID = "IpMembre";
    private static final String KEY_ADHERENT_NOM = "AdNom";
    private static final String KEY_ADHERENT_PRENOM = "AdPrenom";
    private static final String KEY_ADHERENT_NUM_MANUEL = "AdNumManuel";
    private static final String KEY_ADHERENT_CODE = "AdCode";



    private static String STRING_EMPTY = "";

    private EditText EapDepotMinEditText;
    private EditText EapTauxEditText;
    private EditText NumDossierEditText;


    private String adherentId;
    private String eapDepotMin;
    private String eapTaux="";
    private String adNom;
    private String adPrenom;
    private String adNumManuel;
    private String adCode;
    private String adNumDossier;
    private String eapNbreUnite;
    private String eapCtModRenouv;

    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Category> eapList;
    List<Integer> eapListID = new ArrayList<Integer>();
    List<String> eapListTaux = new ArrayList<String>();
    private int eapID;
    private Spinner spinnerListEAP;
    private Spinner spinnerNbreUnite;
    private TextView tvAdherentNom;
    private TextView tvAdherentNumManuel;
    private TextView tvAdherentCode;
    private RadioButton rb_transfert_vers_eav;
    private RadioButton rb_renouveller;
    private RadioButton rb_retirer;
    private TextView tvAdherentNumDossier;
    /*end manage*/

    private Button addButton;
    private Button annulerButton;
    private int success;
    private ProgressDialog pDialog;
    private ProgressDialog pDialogFetchProduitEapList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_eap_adherent);


        Intent intent = getIntent();
        adherentId = intent.getStringExtra(KEY_ADHERENT_ID);
        adNom = intent.getStringExtra(KEY_ADHERENT_NOM);
        adPrenom = intent.getStringExtra(KEY_ADHERENT_PRENOM);
        adNumManuel = intent.getStringExtra(KEY_ADHERENT_NUM_MANUEL);
        adCode = intent.getStringExtra(KEY_ADHERENT_CODE);
        //adNumDossier = intent.getStringExtra(KEY_ADHERENT_NUM_DOSSIER);

        EapDepotMinEditText = (EditText) findViewById(R.id.input_txt_montant_mise);
        EapTauxEditText = (EditText) findViewById(R.id.input_txt_taux);
        EapTauxEditText.setText(eapTaux+" %");
        NumDossierEditText = (EditText) findViewById(R.id.input_txt_num_dossier_adherent);
        rb_transfert_vers_eav = (RadioButton) findViewById(R.id.rb_CtModRenouv_transfert_vers_eav);
        rb_renouveller = (RadioButton) findViewById(R.id.rb_CtModRenouv_renouveller);
        rb_retirer = (RadioButton) findViewById(R.id.rb_CtModRenouv_retirer);


        spinnerListEAP = (Spinner) findViewById(R.id.spn_list_eap);
        spinnerNbreUnite = (Spinner) findViewById(R.id.spn_list_duree);
        tvAdherentNom = (TextView) findViewById(R.id.tv_nom_adherent);
        tvAdherentNom.setText(adNom+"\n"+adPrenom);
        tvAdherentNumManuel = (TextView) findViewById(R.id.tv_num_manuel_adherent);
        tvAdherentNumManuel.setText(adNumManuel);
        tvAdherentCode = (TextView) findViewById(R.id.tv_code_adherent);
        tvAdherentCode.setText(adCode);
       /* tvAdherentNumDossier = (TextView) findViewById(R.id.tv_num_dossier_adherent);
        tvAdherentNumDossier.setText(adNumDossier);*/

        eapList = new ArrayList<Category>();
        // spinner item select listener
        spinnerListEAP.setOnItemSelectedListener(CreateEAP.this);
        new GetProduitEATList().execute();

        addButton = (Button) findViewById(R.id.btn_save_compte_eap);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(CreateEAP.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addEatAdherent();
                } else {
                    Toast.makeText(CreateEAP.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });


    }

    /**
     * To manage Radio button
     * @param view
     */
    public void onRadioButtonClicked(View view) {
        boolean checked1 = ((RadioButton) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.rb_CtModRenouv_transfert_vers_eav:
                if (rb_transfert_vers_eav.isChecked()) {
                    eapCtModRenouv = "T";
                    //str = checked1?"Nature frais fixe":"";

                }
                break;
            case R.id.rb_CtModRenouv_renouveller:
                if (rb_renouveller.isChecked()) {
                    eapCtModRenouv = "R";
                   // str = checked1?"Nature frais taux":"";

                }
                break;
            case R.id.rb_CtModRenouv_retirer:
                if (rb_retirer.isChecked()) {
                    eapCtModRenouv = "E";
                    //str = checked1?"Ce frais est obligatoire":"";

                }

                break;


        }
       // Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }


    /**
     * Adding spinner data
     * */
    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();

        //tvCaisse.setText("");

        for (int i = 0; i < eapList.size(); i++) {
            lables.add(eapList.get(i).getName());//recupère les noms
            eapListID.add(eapList.get(i).getId()); //recupère les Id
            eapListTaux.add(eapList.get(i).getTaux()); //recupère les Taux
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(CreateEAP.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerListEAP.setAdapter(spinnerAdapter);
    }

    /**
     * Async task to get all food categories
     * */
    private class GetProduitEATList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogFetchProduitEapList = new ProgressDialog(CreateEAP.this);
            pDialogFetchProduitEapList.setMessage("Fetching produits's list..");
            pDialogFetchProduitEapList.setCancelable(false);
            pDialogFetchProduitEapList.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair(KEY_EP_CAISSE_ID, String.valueOf(MyData.CAISSE_ID)));
            httpParams.add(new BasicNameValuePair(KEY_EP_GUICHET_ID, String.valueOf(MyData.GUICHET_ID)));
            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_eap_by_guichet.php", ServiceHandler.GET, httpParams);
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
                            Category cat = new Category(catObj.getInt(KEY_EAP_ID),
                                    catObj.getString(KEY_EAP_LIBELLE), catObj.getString(KEY_EAP_TAUX));
                            eapList.add(cat);
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
            if (pDialogFetchProduitEapList.isShowing())
                pDialogFetchProduitEapList.dismiss();
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
        eapID = eapListID.get(position);//pour recuperer l'ID du guichet selectionnée
        //eapTaux = eapListTaux.get(position);//pour recuperer le taux du produit selectionnée
        EapTauxEditText.setText(eapList.get(position).getTaux()+" %");
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }


    /**
     * Checks whether all files are filled. If so then calls AddEatAdherentAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addEatAdherent() {
        if (!STRING_EMPTY.equals(EapDepotMinEditText.getText().toString()) &&
            !STRING_EMPTY.equals(NumDossierEditText.getText().toString()) &&
            !STRING_EMPTY.equals(EapTauxEditText.getText().toString()) &&
                eapID !=0 ) {


                eapDepotMin = EapDepotMinEditText.getText().toString();
                adNumDossier = NumDossierEditText.getText().toString();
            eapTaux = (EapTauxEditText.getText().toString()).replace(" %","");
                eapNbreUnite = spinnerNbreUnite.getSelectedItem().toString();
         /*   if (rb_transfert_vers_eav.isChecked()) {
                eapCtModRenouv = "T";
                //str = checked1?"Nature frais fixe":"";

            }else  if (rb_renouveller.isChecked()) {
                eapCtModRenouv = "R";
                // str = checked1?"Nature frais taux":"";

            } else {
                eapCtModRenouv = "R";
                // str = checked1?"Nature frais taux":"";

            }*/

                new AddEatAdherentAsyncTask().execute();


        } else {
            Toast.makeText(CreateEAP.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for adding a compte eav
     */
    private class AddEatAdherentAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(CreateEAP.this);
            pDialog.setMessage("Adding compte à terme. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
           // httpParams.put(KEY_EAP_ID, uxGuichetId);

            httpParams.put(KEY_CP_MEMBRE, adherentId);
            httpParams.put(KEY_CP_PRODUIT, String.valueOf(eapID));
            httpParams.put(KEY_CP_GUICHET, String.valueOf(MyData.GUICHET_ID));
            httpParams.put(KEY_CP_NUM_DOSSIER, adNumDossier);
            httpParams.put(KEY_CP_DEPOT_MIN, eapDepotMin);
            httpParams.put(KEY_CP_NBRE_UNITE, eapNbreUnite);
            httpParams.put(KEY_CP_USER_CREE, String.valueOf(MyData.USER_ID));
            httpParams.put(KEY_CP_TAUX, eapTaux);
            httpParams.put(KEY_CP_MOD_RENOUV, eapCtModRenouv);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_eap_adherent.php", "POST", httpParams);
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
                        Toast.makeText(CreateEAP.this,
                                "Compte créé avec succès", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CreateEAP.this,
                                "Some error occurred while adding Compte",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}