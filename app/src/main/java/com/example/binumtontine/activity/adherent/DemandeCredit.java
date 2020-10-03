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
import android.app.assist.AssistStructure;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class DemandeCredit extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    /*Begin*/
         //to fetchProduitList by caisse and guichet ID
    private static final String KEY_CREDIT_CAISSE_ID = "CrCaisseId";
    private static final String KEY_GX_NUMERO = "CrGuichetId";
    private static final String KEY_EV_GUICHET_ID = "ev_gx_numero";
    /* end*/
    private static final String KEY_CR_ID = "CrNumero";
    private static final String KEY_CR_LIBELLE = "CrLibelle";

    private static final String KEY_DC_CREDIT = "DcCredit";
    private static final String KEY_DC_OBJET = "DcObjet";
    private static final String KEY_DC_MEMBRE = "DcMembre";
    private static final String KEY_DC_GUICHET = "DcGuichet";
    private static final String KEY_DC_NUM_DOSSIER = "DcNumDoss";
    private static final String KEY_DC_DESCRIPTION = "DcDescription";
    private static final String KEY_DC_MT_DMDE = "DcMtDmde";
    private static final String KEY_DC_DUREE_MOIS = "DcDureeMois";
    private static final String KEY_DC_TAUX_INTER = "DcTauxInter";
    private static final String KEY_DC_CUMUL_DETTE_ENCOURS = "DcCumulDetteEncours";
    private static final String KEY_DC_NAT_ACTIV = "DcNatActiv";
    private static final String KEY_DC_MT_SOLDE_EPARGNE = "DcMtSoldeEpargne";
    private static final String KEY_DC_FREQ_REVENUS = "DcFreqRevenus";
    private static final String KEY_DC_MT_REVENUS_FIXE = "DcMtRevenusFixe";
    private static final String KEY_DC_MT_AUTRE_REVENUS = "DcMtAutreRevenus";

    private static final String KEY_DC_USER_CREE = "DcUserCree";
    private static final String KEY_ADHERENT_NUM_DOSSIER = "CvNumDossier";

    /*Param for get extra*/
    private static final String KEY_ADHERENT_ID = "IpMembre";
    private static final String KEY_ADHERENT_NOM = "AdNom";
    private static final String KEY_ADHERENT_PRENOM = "AdPrenom";
    private static final String KEY_ADHERENT_NUM_MANUEL = "AdNumManuel";
    private static final String KEY_ADHERENT_CODE = "AdCode";
    private static final String KEY_CAISSE_ID = "OcCaisse";
    private static final String KEY_OC_NUMERO = "OcNumero";
    private static final String KEY_OC_LIBELLE = "OcLibelle";


    private static String STRING_EMPTY = "";

    private EditText DcDescriptionEditText;
    private EditText DcDureeMoisEditText;
    private EditText DcTauxInterEditText;
    private EditText DcCreditAnterieur1EditText;
    private EditText DcCreditAnterieur2EditText;
    private EditText DcCreditAnterieur3EditText;
    private EditText DcCumulDetteEncoursEditText;
    private EditText DcNatActivEditText;
    private EditText DcMtMoyEpargneEditText;
    private EditText DcMtSoldeEpargneEditText;
    private EditText DcFreqRevenusEditText;
    private EditText DcMtRevenusFixeEditText;
    private EditText DcMtAutreRevenusEditText;
    private EditText DcMtDmdeEditText;
    private EditText DcNumDossEditText;


    private String adherentId;
    private String dcMtDmde;
    private String adNom;
    private String adPrenom;
    private String adNumManuel;
    private String adCode;
    private String dcNumDossier;

    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Category> creditList;
    List<Integer> creditListID = new ArrayList<Integer>();
    private ArrayList<Category> objetCreditList;
    List<Integer> objetCreditListID = new ArrayList<Integer>();
    private int creditID;
    private Spinner spinnerListCredit;
    private int objetCreditID;
    private Spinner spinnerListObjetCredit;
    private TextView tvAdherentNom;
    private TextView tvAdherentNumManuel;
    private TextView tvAdherentCode;
    private TextView tvAdherentNumDossier;
    /*end manage*/

    private Button addButton;
    private Button annulerButton;
    private int success;
    private ProgressDialog pDialog;
    private ProgressDialog pDialogFetchProduitCreditList;
    private String dcMtAutreRevenus;
    private String dcMtRevenusFixe;
    private String dcFreqRevenus;
    private String dcMtSoldeEpargne;
    private String dcMtMoyEpargne;
    private String dcNatActiv;
    private String dcCumulDetteEncours;
    private String dcCreditAnterieur3;
    private String dcCreditAnterieur2;
    private String dcCreditAnterieur1;
    private String dcDureeMois;
    private String dcDescription;
    private String dcTauxInter;
    private RadioButton rb_avalisteCreditNon;
    private RadioButton rb_avalisteCreditOui;
    private LinearLayout tv_plage_CrTypTxInter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_demande_credit_adherent);


        Intent intent = getIntent();
        adherentId = intent.getStringExtra(KEY_ADHERENT_ID);
        adNom = intent.getStringExtra(KEY_ADHERENT_NOM);
        adPrenom = intent.getStringExtra(KEY_ADHERENT_PRENOM);
        adNumManuel = intent.getStringExtra(KEY_ADHERENT_NUM_MANUEL);
        adCode = intent.getStringExtra(KEY_ADHERENT_CODE);
        //dcNumDossier = intent.getStringExtra(KEY_ADHERENT_NUM_DOSSIER);

        DcDescriptionEditText = (EditText) findViewById(R.id.input_txt_DcDescription);
        DcDureeMoisEditText = (EditText) findViewById(R.id.input_txt_DcDureeMois);
        DcTauxInterEditText = (EditText) findViewById(R.id.input_txt_DcTauxInter);
        DcCreditAnterieur1EditText = (EditText) findViewById(R.id.input_txt_DcCreditAnterieur1);
        DcCreditAnterieur2EditText = (EditText) findViewById(R.id.input_txt_DcCreditAnterieur2);
        DcCreditAnterieur3EditText = (EditText) findViewById(R.id.input_txt_DcCreditAnterieur3);
        DcCumulDetteEncoursEditText = (EditText) findViewById(R.id.input_txt_DcCumulDetteEncours);
        DcNatActivEditText = (EditText) findViewById(R.id.input_txt_DcNatActiv);
        DcMtMoyEpargneEditText = (EditText) findViewById(R.id.input_txt_DcMtMoyEpargne);
        DcMtSoldeEpargneEditText = (EditText) findViewById(R.id.input_txt_DcMtSoldeEpargne);
        DcFreqRevenusEditText = (EditText) findViewById(R.id.input_txt_DcFreqRevenus);
        DcMtRevenusFixeEditText = (EditText) findViewById(R.id.input_txt_DcMtRevenusFixe);
        DcMtAutreRevenusEditText = (EditText) findViewById(R.id.input_txt_DcMtAutreRevenus);
        DcMtDmdeEditText = (EditText) findViewById(R.id.input_txt_DcMtDmde);
        DcNumDossEditText = (EditText) findViewById(R.id.input_txt_DcNumDoss);
        rb_avalisteCreditOui = (RadioButton) findViewById(R.id.rb_avaliste_credit_oui);
        rb_avalisteCreditNon = (RadioButton) findViewById(R.id.rb_avaliste_credit_non);
        tv_plage_CrTypTxInter = (LinearLayout) findViewById(R.id.tv_avaliste_credit_plage);
        onRadioButtonClicked(rb_avalisteCreditNon);


        spinnerListCredit = (Spinner) findViewById(R.id.spn_list_credit);
        spinnerListObjetCredit = (Spinner) findViewById(R.id.spn_list_objet_credit);
        tvAdherentNom = (TextView) findViewById(R.id.tv_nom_adherent);
        tvAdherentNom.setText(adNom+"\n"+adPrenom);
        tvAdherentNumManuel = (TextView) findViewById(R.id.tv_num_manuel_adherent);
        tvAdherentNumManuel.setText(adNumManuel);
        tvAdherentCode = (TextView) findViewById(R.id.tv_code_adherent);
        tvAdherentCode.setText(adCode);
       /* tvAdherentNumDossier = (TextView) findViewById(R.id.tv_num_dossier_adherent);
        tvAdherentNumDossier.setText(dcNumDossier);*/

        creditList = new ArrayList<Category>();
        objetCreditList = new ArrayList<Category>();
        // spinner item select listener
        spinnerListCredit.setOnItemSelectedListener(DemandeCredit.this);
        spinnerListObjetCredit.setOnItemSelectedListener(DemandeCredit.this);
        new GetProduitCreditList().execute();
        new FetchObjetCreditAsyncTask().execute();
//        tv_plage_CrTypTxInter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
//                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Avalistes";
////                    ListPlageDataTICActivity.IS_TO_CREATE_OR_TO_UPDATE = true;
////                    Intent i = new Intent(DemandeCredit.this, ListPlageDataTICActivity.class);
////                    startActivityForResult(i,20);
//
//                } else {
//                    Toast.makeText(DemandeCredit.this,
//                            "Unable to connect to internet",
//                            Toast.LENGTH_LONG).show();
//
//                }
//
//            }
//        });
        addButton = (Button) findViewById(R.id.btn_save_demande_credit);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(DemandeCredit.this,
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
                    Toast.makeText(DemandeCredit.this,
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

            case R.id.rb_avaliste_credit_oui:
                if (rb_avalisteCreditOui.isChecked()) {
                    tv_plage_CrTypTxInter.setVisibility(View.VISIBLE);
                    //str = checked1?"Nature frais fixe":"";

                }
                break;
            case R.id.rb_avaliste_credit_non:
                if (rb_avalisteCreditNon.isChecked()) {
                    tv_plage_CrTypTxInter.setVisibility(View.GONE);
                    // str = checked1?"Nature frais taux":"";

                }

                break;


        }
        // Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    /**
     * Adding Credit spinner data
     * */
    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();

        //tvCaisse.setText("");

        for (int i = 0; i < creditList.size(); i++) {
            lables.add(creditList.get(i).getName());//recupère les noms
            creditListID.add(creditList.get(i).getId()); //recupère les Id
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(DemandeCredit.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerListCredit.setAdapter(spinnerAdapter);
    }
    /**
     * Adding Objet Credit spinner data
     * */
    private void populateObjetCreditSpinner() {
        List<String> lables = new ArrayList<String>();

        //tvCaisse.setText("");

        for (int i = 0; i < objetCreditList.size(); i++) {
            lables.add(objetCreditList.get(i).getName());//recupère les noms
            objetCreditListID.add(objetCreditList.get(i).getId()); //recupère les Id
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(DemandeCredit.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerListObjetCredit.setAdapter(spinnerAdapter);
    }

    /**
     * Async task to get all food categories
     * */
    private class GetProduitCreditList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogFetchProduitCreditList = new ProgressDialog(DemandeCredit.this);
            pDialogFetchProduitCreditList.setMessage("Fetching produits's list..");
            pDialogFetchProduitCreditList.setCancelable(false);
            pDialogFetchProduitCreditList.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair(KEY_CREDIT_CAISSE_ID, String.valueOf(MyData.CAISSE_ID)));
            httpParams.add(new BasicNameValuePair(KEY_GX_NUMERO, String.valueOf(MyData.GUICHET_ID)));
//            httpParams.add(new BasicNameValuePair(KEY_EV_GUICHET_ID, String.valueOf(MyData.GUICHET_ID)));
//            httpParams.put(KEY_GX_NUMERO, String.valueOf(MyData.GUICHET_ID));
//            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_credit_by_caisse.php", ServiceHandler.GET, httpParams);
            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_credit_by_guichet.php", ServiceHandler.GET, httpParams);
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
                            Category cat = new Category(catObj.getInt(KEY_CR_ID),
                                    catObj.getString(KEY_CR_LIBELLE));
                            creditList.add(cat);
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
            if (pDialogFetchProduitCreditList.isShowing())
                pDialogFetchProduitCreditList.dismiss();
            populateSpinner();
        }

    }
    /**
     * Async task to get all objet credit categories
     * */
    private class FetchObjetCreditAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DemandeCredit.this);
            pDialog.setMessage("Fetching objets's list..");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair(KEY_CAISSE_ID, String.valueOf(MyData.CAISSE_ID)));
//            httpParams.add(new BasicNameValuePair(KEY_EV_GUICHET_ID, String.valueOf(MyData.GUICHET_ID)));
            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_objet_credit_by_caisse.php", ServiceHandler.GET, httpParams);

//            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_credit_by_guichet.php", ServiceHandler.GET, httpParams);
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
                            Category cat = new Category(catObj.getInt(KEY_OC_NUMERO),
                                    catObj.getString(KEY_OC_LIBELLE));
                            objetCreditList.add(cat);
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
            if (pDialog.isShowing())
                pDialog.dismiss();
            populateObjetCreditSpinner();
        }

    }



    /**
     * Async task to get all adherents on guichet
     * */
    private class GetProduitAvalisteList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogFetchProduitCreditList = new ProgressDialog(DemandeCredit.this);
            pDialogFetchProduitCreditList.setMessage("Fetching produits's list..");
            pDialogFetchProduitCreditList.setCancelable(false);
            pDialogFetchProduitCreditList.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair(KEY_CREDIT_CAISSE_ID, String.valueOf(MyData.CAISSE_ID)));
            httpParams.add(new BasicNameValuePair(KEY_EV_GUICHET_ID, String.valueOf(MyData.GUICHET_ID)));
            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_credit_by_caisse.php", ServiceHandler.GET, httpParams);
//            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_credit_by_guichet.php", ServiceHandler.GET, httpParams);
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
                            Category cat = new Category(catObj.getInt(KEY_CR_ID),
                                    catObj.getString(KEY_CR_LIBELLE));
                            creditList.add(cat);
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
            if (pDialogFetchProduitCreditList.isShowing())
                pDialogFetchProduitCreditList.dismiss();
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


        int idSpinner = parent.getId();

        switch (idSpinner)
        {
            case R.id.spn_list_credit:
                // your stuff here
                creditID = creditListID.get(position);//pour recuperer l'ID du produit crédit selectionné
                break;
            case R.id.spn_list_objet_credit:
                // your stuff here
                objetCreditID = objetCreditListID.get(position);//pour recuperer l'ID de l'objet crédit selectionné
                break;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }


    /**
     * Checks whether all files are filled. If so then calls AddDcAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addEavAdherent() {
        if (!STRING_EMPTY.equals(DcMtDmdeEditText.getText().toString()) &&
            !STRING_EMPTY.equals(DcNumDossEditText.getText().toString()) &&
                creditID !=0 && objetCreditID !=0 ) {



                dcNumDossier = DcNumDossEditText.getText().toString();
            dcDescription = DcDescriptionEditText.getText().toString();
            dcMtDmde = DcMtDmdeEditText.getText().toString();
            dcDureeMois = DcDureeMoisEditText.getText().toString();
            dcTauxInter = DcTauxInterEditText.getText().toString();
            dcCreditAnterieur1 = DcCreditAnterieur1EditText.getText().toString();
            dcCreditAnterieur2 = DcCreditAnterieur2EditText.getText().toString();
            dcCreditAnterieur3 = DcCreditAnterieur3EditText.getText().toString();
            dcCumulDetteEncours = DcCumulDetteEncoursEditText.getText().toString();
            dcNatActiv = DcNatActivEditText.getText().toString();
            dcMtMoyEpargne = DcMtMoyEpargneEditText.getText().toString();
            dcMtSoldeEpargne = DcMtSoldeEpargneEditText.getText().toString();
            dcFreqRevenus = DcFreqRevenusEditText.getText().toString();
            dcMtRevenusFixe = DcMtRevenusFixeEditText.getText().toString();
            dcMtAutreRevenus = DcMtAutreRevenusEditText.getText().toString();

                new AddDcAsyncTask().execute();


        } else {
            Toast.makeText(DemandeCredit.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for adding a compte eav
     */
    private class AddDcAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(DemandeCredit.this);
            pDialog.setMessage("Adding demande crédit. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
           // httpParams.put(KEY_CR_ID, uxGuichetId);

            httpParams.put(KEY_DC_MEMBRE, adherentId);
            httpParams.put(KEY_DC_CREDIT, String.valueOf(creditID));
            httpParams.put(KEY_DC_OBJET, String.valueOf(objetCreditID));
            httpParams.put(KEY_DC_NUM_DOSSIER, dcNumDossier);
            httpParams.put(KEY_DC_DESCRIPTION, dcDescription);
            httpParams.put(KEY_DC_MT_DMDE, dcMtDmde);
            httpParams.put(KEY_DC_DUREE_MOIS, dcDureeMois);
            httpParams.put(KEY_DC_TAUX_INTER, dcTauxInter);
            httpParams.put(KEY_DC_CUMUL_DETTE_ENCOURS, dcCumulDetteEncours);
            httpParams.put(KEY_DC_NAT_ACTIV, dcNatActiv);
            httpParams.put(KEY_DC_MT_SOLDE_EPARGNE, dcMtSoldeEpargne);
            httpParams.put(KEY_DC_FREQ_REVENUS, dcFreqRevenus);
            httpParams.put(KEY_DC_MT_REVENUS_FIXE, dcMtRevenusFixe);
            httpParams.put(KEY_DC_MT_AUTRE_REVENUS, dcMtAutreRevenus);



            httpParams.put(KEY_DC_GUICHET, String.valueOf(MyData.GUICHET_ID));


            httpParams.put(KEY_DC_USER_CREE, String.valueOf(MyData.USER_ID));

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_demande_credit.php", "POST", httpParams);
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
                        Toast.makeText(DemandeCredit.this,
                                "Demande créée avec succès", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(DemandeCredit.this,
                                "Some error occurred while adding Demande",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}