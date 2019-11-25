package com.example.binumtontine.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.binumtontine.R;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PrivacyPolicyActivity extends AppCompatActivity {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";

    private static final String KEY_PRE_PARAM_ID = "pre_numero";
    private static final String KEY_PRE_PARAM_EAV = "pre_eav";
    private static final String KEY_PRE_PARAM_EAT = "pre_eat";
    private static final String KEY_PRE_PARAM_EAP = "pre_eap";
    private static final String KEY_PRE_PARAM_CC = "pre_compte_courant";
    private static final String KEY_PRE_PARAM_CREDIT = "pre_credit";
    private static final String KEY_PRE_PARAM_TRANSFERT_CLASS = "pre_transfert_classique";
    private static final String KEY_PRE_PARAM_TRANSFERT_MTN = "pre_transfert_mtn";
    private static final String KEY_PRE_PARAM_TRANSFERT_ORANGE = "pre_transfert_orange";
    private static final String KEY_PRE_PARAM_TRANSFERT_EU = "pre_transfert_eu";
    private static final String KEY_PRE_PARAM_OPERATION_EXTERNE = "pre_operation_externe";
    private static final String KEY_PRE_PARAM_APPELLATION_PRODUIT = "pre_appellation_produit";

    //private static final String BASE_URL = "http://192.168.1.102/binumTontine/";
    private static final String BASE_URL = "http://binumt.diff-itc.net/binumTontine/";

    private static String STRING_EMPTY = "";

    private CheckBox eav, eap, eat, cptecourant, credit, transfert_classique,
            transfert_to_mtn,transfert_to_orange, transfert_eu, op_externe ;
    //private CheckBox appellationProduitEpargne;
    private RadioButton appellationProduitEpargne;
    private RadioButton appellationProduitDepot;

    private String preParamId="1"; //car on a un seul enregistrement un BD donc son ID est connu

    private Boolean preEAV;
    private Boolean preEAT;
    private Boolean preEAP;
    private Boolean preCpteCourant;
    private Boolean preCredit;
    private Boolean preTransfertClassique;
    private Boolean preTransfertMTN;
    private Boolean preTransfertOrange;
    private Boolean preTransfertEU;
    private Boolean preOperationExterne;
    private Boolean preAppellationProduitEpargne;
    private Boolean preAppellationProduitDepot;

    private Button btnPreParam;
    private int success;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_preParam_Produit);
        setSupportActionBar(toolbar);
        setToolbarTitle();

        Intent intent = getIntent();

        eav = (CheckBox)findViewById(R.id.eavCheckBox);
        eap = (CheckBox)findViewById(R.id.eapCheckBox);
        eat = (CheckBox)findViewById(R.id.eatCheckBox);
        cptecourant = (CheckBox)findViewById(R.id.cptecourantCheckBox);
        credit = (CheckBox)findViewById(R.id.creditCheckBox);
        transfert_classique = (CheckBox)findViewById(R.id.checkBoxTransfert_classique);
        transfert_to_mtn = (CheckBox)findViewById(R.id.checkBoxTransfert_to_mtn);
        transfert_to_orange = (CheckBox)findViewById(R.id.checkBoxTransfert_to_orange);
        transfert_eu = (CheckBox)findViewById(R.id.checkBoxTransfert_to_eu);
        op_externe = (CheckBox)findViewById(R.id.chkOp_ext);
        //appellationProduitEpargne = (CheckBox)findViewById(R.id.chkAppellation_produit);
        appellationProduitEpargne = (RadioButton) findViewById(R.id.rbEpargne);
        appellationProduitDepot = (RadioButton) findViewById(R.id.rbDepot);

        new PrivacyPolicyActivity.FetchMovieDetailsAsyncTask().execute();

        btnPreParam = (Button)findViewById(R.id.btn_save_PreParamOf);
        btnPreParam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    updateUser();

                } else {
                    Toast.makeText(PrivacyPolicyActivity.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
           /* public void onClick(View v) {
                String result = "Selected Courses";
                if(eav.isChecked()){
                    result += "\nAndroid";
                }
                if(eap.isChecked()){
                    result += "\nAngularJS";
                }
                if(eat.isChecked()){
                    result += "\nJava";
                }
                if(cptecourant.isChecked()){
                    result += "\nPython";
                }
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            } */
        });
       }
    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Préparamétrage du réseau:");

    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.eavCheckBox:
                str = checked?"Produit EAV Selected":"Produit EAV Deselected";
                break;
            case R.id.eapCheckBox:
                str = checked?"Produit EAP Selected":"Produit EAP Deselected";
                break;
            case R.id.eatCheckBox:
                str = checked?"Produit EAT Selected":"Produit EAT Deselected";
                break;
            case R.id.cptecourantCheckBox:
                str = checked?"Produit Compte Courant Selected":"Produit Compte Courant Deselected";
                break;
            case R.id.creditCheckBox:
                str = checked?"Produit Crédit Selected":"Produit Crédit Deselected";
                break;
            case R.id.checkBoxTransfert_classique:
                str = checked?"Produit Transfert Classique Selected":"Produit Transfert Classique Deselected";
                break;
            case R.id.checkBoxTransfert_to_mtn:
                str = checked?"Produit Transfert vers MTN Selected":"Produit Transfert vers MTN Deselected";
                break;
            case R.id.checkBoxTransfert_to_orange:
                str = checked?"Produit Transfert vers ORANGE Selected":"Produit Transfert vers ORANGE Deselected";
                break;
            case R.id.checkBoxTransfert_to_eu:
                str = checked?"Produit Transfert vers EU Selected":"Produit Transfert vers EU Deselected";
                break;
            case R.id.chkOp_ext:
                str = checked?"Opérations externes Selected":"Opérations externes Deselected";
                break;
           /* case R.id.chkAppellation_produit:
                str = checked?"Appellation Produits Selected":"Appellation Produits Deselected";
                break;*/

        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }
    public void onRadioButtonClicked(View view) {
        boolean checked1 = ((RadioButton) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.rbEpargne:
                str = checked1?"Appellation Epargne Selected":"Appellation Epargne Deselected";
                break;
            case R.id.rbDepot:
                str = checked1?"Appellation Dépôt Selected":"Appellation Dépôt Deselected";
                break;
        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }
    /**
     * Fetches single preParamProduit details from the server
     */
    private class FetchMovieDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(PrivacyPolicyActivity.this);
            pDialog.setMessage("Loading Pre Paramètres Details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_PRE_PARAM_ID, preParamId);

          //  HttpJsonParser httpJsonParser = new HttpJsonParser();
            /*JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_user.php", "GET", null);*/
            /*JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_pre_parametrage_details.php", "GET", null);*/


            try {
                JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                        BASE_URL + "get_pre_parametrage_details.php", "GET", httpParams);
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject preParamProduit;
                if (success == 1) {
                    //Parse the JSON response
                    preParamProduit = jsonObject.getJSONObject(KEY_DATA);
                    preEAV = Boolean.parseBoolean(preParamProduit.getString(KEY_PRE_PARAM_EAV));
                    preEAT = Boolean.parseBoolean(preParamProduit.getString(KEY_PRE_PARAM_EAT));
                    preEAP = Boolean.parseBoolean(preParamProduit.getString(KEY_PRE_PARAM_EAP));
                    preCpteCourant = Boolean.parseBoolean(preParamProduit.getString(KEY_PRE_PARAM_CC));
                    preCredit = Boolean.parseBoolean(preParamProduit.getString(KEY_PRE_PARAM_CREDIT));
                    preTransfertClassique = Boolean.parseBoolean(preParamProduit.getString(KEY_PRE_PARAM_TRANSFERT_CLASS));
                    preTransfertMTN = Boolean.parseBoolean(preParamProduit.getString(KEY_PRE_PARAM_TRANSFERT_MTN));
                    preTransfertOrange = Boolean.parseBoolean(preParamProduit.getString(KEY_PRE_PARAM_TRANSFERT_ORANGE));
                    preTransfertEU = Boolean.parseBoolean(preParamProduit.getString(KEY_PRE_PARAM_TRANSFERT_EU));
                    preOperationExterne = Boolean.parseBoolean(preParamProduit.getString(KEY_PRE_PARAM_OPERATION_EXTERNE));
                    preAppellationProduitEpargne = Boolean.parseBoolean(preParamProduit.getString(KEY_PRE_PARAM_APPELLATION_PRODUIT));

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
                    //Populate the Edit Texts once the network activity is finished executing
                    eav.setChecked(preEAV);
                    eat.setChecked(preEAT);
                    eap.setChecked(preEAP);
                    cptecourant.setChecked(preCpteCourant);
                    credit.setChecked(preCredit);
                    transfert_classique.setChecked(preTransfertClassique);
                    transfert_to_mtn.setChecked(preTransfertMTN);
                    transfert_to_orange.setChecked(preTransfertOrange);
                    transfert_eu.setChecked(preTransfertEU);
                    op_externe.setChecked(preOperationExterne);
                    appellationProduitEpargne.setChecked(preAppellationProduitEpargne);
                    appellationProduitDepot.setChecked(!preAppellationProduitEpargne);

                }
            });
        }


    }

    /**
     * Checks whether all files are filled. If so then calls UpdateMovieAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void updateUser() {

                preEAV = eav.isChecked();
                preEAT = eat.isChecked();
                preEAP = eap.isChecked();
                preCpteCourant = cptecourant.isChecked();
                preCredit = credit.isChecked();
                preTransfertClassique = transfert_classique.isChecked();
                preTransfertMTN = transfert_to_mtn.isChecked();
                preTransfertOrange = transfert_to_orange.isChecked();
                preTransfertEU = transfert_eu.isChecked();
                preOperationExterne = op_externe.isChecked();
                preAppellationProduitEpargne = appellationProduitEpargne.isChecked();

               /* uxCaisseId = uxCaisseIdSpinner.getText().toString();
                uxProfil = uxProfilEditText.getText().toString();
                uxNom = uxNomEditText.getText().toString();
                uxPrenom = uxPrenomEditText.getText().toString();
                uxAdresse = uxAdresseEditText.getText().toString();
                uxTel1 = uxTel1EditText.getText().toString();
                uxTel2 = uxTel2EditText.getText().toString();
                uxTel3 = uxTel3EditText.getText().toString();
                uxLogin = uxLoginEditText.getText().toString();
                uxPassword = uxPasswordEditText.getText().toString();
                //uxConfirmPassword = uxConfirmPasswordEditText.getText().toString();
                uxEmail = uxEmailEditText.getText().toString();*/
                new PrivacyPolicyActivity.UpdateMovieAsyncTask().execute();

    }

    /**
     * AsyncTask for updating a preParam details
     */

    private class UpdateMovieAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(PrivacyPolicyActivity.this);
            pDialog.setMessage("Setting Pré-Paramètres produits. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_PRE_PARAM_ID, preParamId);
            httpParams.put(KEY_PRE_PARAM_EAV, preEAV.toString());
            httpParams.put(KEY_PRE_PARAM_EAT, preEAT.toString());
            httpParams.put(KEY_PRE_PARAM_EAP, preEAP.toString());
            httpParams.put(KEY_PRE_PARAM_CC, preCpteCourant.toString());
            httpParams.put(KEY_PRE_PARAM_CREDIT, preCredit.toString());
            httpParams.put(KEY_PRE_PARAM_TRANSFERT_CLASS, preTransfertClassique.toString());
            httpParams.put(KEY_PRE_PARAM_TRANSFERT_MTN, preTransfertMTN.toString());
            httpParams.put(KEY_PRE_PARAM_TRANSFERT_ORANGE, preTransfertOrange.toString());
            httpParams.put(KEY_PRE_PARAM_TRANSFERT_EU, preTransfertEU.toString());
            httpParams.put(KEY_PRE_PARAM_OPERATION_EXTERNE, preOperationExterne.toString());
            httpParams.put(KEY_PRE_PARAM_APPELLATION_PRODUIT, preAppellationProduitEpargne.toString());

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "update_pre_parametrage.php", "POST", httpParams);
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
                        Toast.makeText(PrivacyPolicyActivity.this,
                                "Pre Parametres Updated", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(PrivacyPolicyActivity.this,
                                "Some error occurred while updating Pre Parametres",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}

