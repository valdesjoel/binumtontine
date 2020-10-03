package com.example.binumtontine.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.binumtontine.R;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PreParametrageOFActivity extends AppCompatActivity implements SERVER_ADDRESS {

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
    //new data
    private static final String KEY_PoAppellationMemAdhAss = "PoAppellationMemAdhAss";
    private static final String KEY_PoPeriodDefTxIntCCour = "PoPeriodDefTxIntCCour";
    private static final String KEY_PoPeriodDefTxIntCred = "PoPeriodDefTxIntCred";
    private static final String KEY_PoOrdreRembDecouv = "PoOrdreRembDecouv";
    private static final String KEY_PoOrdreRembCredit = "PoOrdreRembCredit";
    private static final String KEY_PoTxIntMinAutorDecouv = "PoTxIntMinAutorDecouv";
    private static final String KEY_PoTxIntMaxAutorDecouv = "PoTxIntMaxAutorDecouv";
    private static final String KEY_PoTxIntMinAutorAvceSpec = "PoTxIntMinAutorAvceSpec";
    private static final String KEY_PoTxIntMaxAutorAvceSpec = "PoTxIntMaxAutorAvceSpec";
    private static final String KEY_PoTxIntMinAutorDecouvPerm = "PoTxIntMinAutorDecouvPerm";
    private static final String KEY_PoTxIntMaxAutorDecouvPerm = "PoTxIntMaxAutorDecouvPerm";
    private static final String KEY_PoTxIntMinAutorCredit = "PoTxIntMinAutorCredit";
    private static final String KEY_PoTxIntMaxAutorCredit = "PoTxIntMaxAutorCredit";
    private static final String KEY_PoNbMoisMaxDecouvHorsBilan = "PoNbMoisMaxDecouvHorsBilan";
    private static final String KEY_PoNbMoisMaxCreditHorsBilan = "PoNbMoisMaxCreditHorsBilan";
    private static final String KEY_PoIsMultiCpteCourByMemb = "PoIsMultiCpteCourByMemb";

    private static String STRING_EMPTY = "";

    private EditText PoAppellationMemAdhAss,
            PoPeriodDefTxIntCCour,
            PoPeriodDefTxIntCred,
            PoOrdreRembDecouv,
            PoOrdreRembCredit,
            PoTxIntMinAutorDecouv,
            PoTxIntMaxAutorDecouv,
            PoTxIntMinAutorAvceSpec,
            PoTxIntMaxAutorAvceSpec,
            PoTxIntMinAutorDecouvPerm,
            PoTxIntMaxAutorDecouvPerm,
            PoTxIntMinAutorCredit,
            PoTxIntMaxAutorCredit,
            PoNbMoisMaxDecouvHorsBilan,
            PoNbMoisMaxCreditHorsBilan;
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

    private String txtDav;
    private String txtDat;
    private String txtDap;
    private String txtEav;
    private String txtEat;
    private String txtEap;

    private Button btnPreParam;
    private Button annulerButton;
    private int success;
    private ProgressDialog pDialog;
    private ProgressDialog pDialogUpdatePreParametrageProduit;
    private String st_PoAppellationMemAdhAss;
    private String st_PoPeriodDefTxIntCCour;
    private String st_PoPeriodDefTxIntCred;
    private String st_PoOrdreRembDecouv;
    private String st_PoOrdreRembCredit;
    private String st_PoTxIntMinAutorDecouv;
    private String st_PoTxIntMaxAutorDecouv;
    private String st_PoTxIntMinAutorAvceSpec;
    private String st_PoTxIntMaxAutorAvceSpec;
    private String st_PoTxIntMinAutorDecouvPerm;
    private String st_PoTxIntMaxAutorDecouvPerm;
    private String st_PoTxIntMinAutorCredit;
    private String st_PoTxIntMaxAutorCredit;
    private String st_PoNbMoisMaxDecouvHorsBilan;
    private String st_PoNbMoisMaxCreditHorsBilan;
    private Boolean bool_PoIsMultiCpteCourByMemb;
    private RadioButton PoAppellationMemAdhAss_rbMembre;
    private RadioButton PoAppellationMemAdhAss_rbAdherent;
    private RadioButton PoAppellationMemAdhAss_rbAssocie;
    private RadioButton PoPeriodDefTxIntCCour_rbJour;
    private RadioButton PoPeriodDefTxIntCCour_rbMois;
    private RadioButton PoPeriodDefTxIntCCour_rbTrimestre;
    private RadioButton PoPeriodDefTxIntCCour_rbAnnee;
    private RadioButton PoPeriodDefTxIntCred_rbJourCr;
    private RadioButton PoPeriodDefTxIntCred_rbMoisCr;
    private RadioButton PoPeriodDefTxIntCred_rbTrimestreCr;
    private RadioButton PoPeriodDefTxIntCred_rbAnneeCr;
    private RadioButton PoOrdreRembDecouv_rbChoix1;
    private RadioButton PoOrdreRembDecouv_rbChoix2;
    private RadioButton PoOrdreRembCredit_rbChoix1Cr;
    private RadioButton PoOrdreRembCredit_rbChoix2Cr;
    private SwitchCompat PoIsMultiCpteCourByMemb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_parametrage_of);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_preParam_Produit);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setToolbarTitle();

        Intent intent = getIntent();

        txtDav = getString(R.string.produit_dav);
        txtDat = getString(R.string.produit_dat);
        txtDap = getString(R.string.produit_dap);

        txtEav = getString(R.string.produit_eav);
        txtEat = getString(R.string.produit_eat);
        txtEap = getString(R.string.produit_eap);

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


        PoAppellationMemAdhAss_rbMembre = (RadioButton) findViewById(R.id.rbMembre);
        PoAppellationMemAdhAss_rbAdherent = (RadioButton) findViewById(R.id.rbAdherent);
        PoAppellationMemAdhAss_rbAssocie = (RadioButton) findViewById(R.id.rbAssocie);

        PoPeriodDefTxIntCCour_rbJour = (RadioButton) findViewById(R.id.rbJour);
        PoPeriodDefTxIntCCour_rbMois = (RadioButton) findViewById(R.id.rbMois);
        PoPeriodDefTxIntCCour_rbTrimestre = (RadioButton) findViewById(R.id.rbTrimestre);
        PoPeriodDefTxIntCCour_rbAnnee = (RadioButton) findViewById(R.id.rbAnnee);

        PoPeriodDefTxIntCred_rbJourCr = (RadioButton) findViewById(R.id.rbJourCr);
        PoPeriodDefTxIntCred_rbMoisCr = (RadioButton) findViewById(R.id.rbMoisCr);
        PoPeriodDefTxIntCred_rbTrimestreCr = (RadioButton) findViewById(R.id.rbTrimestreCr);
        PoPeriodDefTxIntCred_rbAnneeCr = (RadioButton) findViewById(R.id.rbAnneeCr);

        PoOrdreRembDecouv_rbChoix1 = (RadioButton) findViewById(R.id.rbChoix1);
        PoOrdreRembDecouv_rbChoix2 = (RadioButton) findViewById(R.id.rbChoix2);

        PoOrdreRembCredit_rbChoix1Cr = (RadioButton) findViewById(R.id.rbChoix1Cr);
        PoOrdreRembCredit_rbChoix2Cr = (RadioButton) findViewById(R.id.rbChoix2Cr);

        PoTxIntMinAutorDecouv = (EditText) findViewById(R.id.input_txt_PoTxIntMinAutorDecouv);
        PoTxIntMaxAutorDecouv = (EditText) findViewById(R.id.input_txt_PoTxIntMaxAutorDecouv);
        PoTxIntMinAutorAvceSpec = (EditText) findViewById(R.id.input_txt_PoTxIntMinAutorAvceSpec);
        PoTxIntMaxAutorAvceSpec = (EditText) findViewById(R.id.input_txt_PoTxIntMaxAutorAvceSpec);
        PoTxIntMinAutorDecouvPerm = (EditText) findViewById(R.id.input_txt_PoTxIntMinAutorDecouvPerm);
        PoTxIntMaxAutorDecouvPerm = (EditText) findViewById(R.id.input_txt_PoTxIntMaxAutorDecouvPerm);
        PoTxIntMinAutorCredit = (EditText) findViewById(R.id.input_txt_PoTxIntMinAutorCredit);
        PoTxIntMaxAutorCredit = (EditText) findViewById(R.id.input_txt_PoTxIntMaxAutorCredit);
        PoNbMoisMaxDecouvHorsBilan = (EditText) findViewById(R.id.input_txt_PoNbMoisMaxDecouvHorsBilan);
        PoNbMoisMaxCreditHorsBilan = (EditText) findViewById(R.id.input_txt_PoNbMoisMaxCreditHorsBilan);
        PoIsMultiCpteCourByMemb = (SwitchCompat) findViewById(R.id.SwitchPoIsMultiCpteCourByMemb);
        avertissement();
        new FetchPreParametrageDetailsAsyncTask().execute();

        btnPreParam = (Button)findViewById(R.id.btn_save_PreParamOf);
        annulerButton = (Button)findViewById(R.id.btn_clean);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnPreParam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    updateUser();

                } else {
                    Toast.makeText(PreParametrageOFActivity.this,
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
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Préparamétrage du réseau:");

    }
    public void avertissement() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("AVERTISSEMENT !")
                .setMessage("Veuillez porter attention sur la nature des mises à jours " +
                        "des paramètres de la plate forme, " +
                        "qui peuvent avoir des incidences fortes sur " +
                        "le fonctionnement local ou global du système et des produits." +
                        "\n Etes-vous sûr de vouloir faire des modifications ?")
                .setNegativeButton("Non", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setPositiveButton("Oui", null)
                .show();
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
                eav.setText(txtEav);
                eat.setText(txtEat);
                eap.setText(txtEap);
                str = checked1?"Appellation Epargne Selected":"Appellation Epargne Deselected";
                break;
            case R.id.rbDepot:
                eav.setText(txtDav);
                eat.setText(txtDat);
                eap.setText(txtDap);
                str = checked1?"Appellation Dépôt Selected":"Appellation Dépôt Deselected";
                break;
            case R.id.rbMembre:
                st_PoAppellationMemAdhAss = "M";
                break;
            case R.id.rbAdherent:
                st_PoAppellationMemAdhAss = "A";
                break;
            case R.id.rbAssocie:
                st_PoAppellationMemAdhAss = "S";
                break;
            case R.id.rbJour:
                st_PoPeriodDefTxIntCCour = "J";
                break;
            case R.id.rbMois:
                st_PoPeriodDefTxIntCCour = "M";
                break;
            case R.id.rbTrimestre:
                st_PoPeriodDefTxIntCCour = "T";
                break;
            case R.id.rbAnnee:
                st_PoPeriodDefTxIntCCour = "A";
                break;
            case R.id.rbJourCr:
                st_PoPeriodDefTxIntCred = "J";
                break;
            case R.id.rbMoisCr:
                st_PoPeriodDefTxIntCred = "M";
                break;
            case R.id.rbTrimestreCr:
                st_PoPeriodDefTxIntCred = "T";
                break;
            case R.id.rbAnneeCr:
                st_PoPeriodDefTxIntCred = "A";
                break;
            case R.id.rbChoix1:
                st_PoOrdreRembDecouv = "1";
                break;
            case R.id.rbChoix2:
                st_PoOrdreRembDecouv = "2";
                break;
            case R.id.rbChoix1Cr:
                st_PoOrdreRembCredit = "1";
                break;
            case R.id.rbChoix2Cr:
                st_PoOrdreRembCredit = "2";
                break;
        }

//        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }
    /**
     * Fetches single preParamProduit details from the server
     */
    private class FetchPreParametrageDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(PreParametrageOFActivity.this);
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
                    bool_PoIsMultiCpteCourByMemb = Boolean.parseBoolean(preParamProduit.getString(KEY_PoIsMultiCpteCourByMemb));

                    st_PoAppellationMemAdhAss = preParamProduit.getString(KEY_PoAppellationMemAdhAss);
                    st_PoPeriodDefTxIntCCour = preParamProduit.getString(KEY_PoPeriodDefTxIntCCour);
                    st_PoPeriodDefTxIntCred = preParamProduit.getString(KEY_PoPeriodDefTxIntCred);
                    st_PoOrdreRembDecouv = preParamProduit.getString(KEY_PoOrdreRembDecouv);
                    st_PoOrdreRembCredit = preParamProduit.getString(KEY_PoOrdreRembCredit);
                    st_PoTxIntMinAutorDecouv = preParamProduit.getString(KEY_PoTxIntMinAutorDecouv);
                    st_PoTxIntMaxAutorDecouv = preParamProduit.getString(KEY_PoTxIntMaxAutorDecouv);
                    st_PoTxIntMinAutorAvceSpec = preParamProduit.getString(KEY_PoTxIntMinAutorAvceSpec);
                    st_PoTxIntMaxAutorAvceSpec = preParamProduit.getString(KEY_PoTxIntMaxAutorAvceSpec);
                    st_PoTxIntMinAutorDecouvPerm = preParamProduit.getString(KEY_PoTxIntMinAutorDecouvPerm);
                    st_PoTxIntMaxAutorDecouvPerm = preParamProduit.getString(KEY_PoTxIntMaxAutorDecouvPerm);
                    st_PoTxIntMinAutorCredit = preParamProduit.getString(KEY_PoTxIntMinAutorCredit);
                    st_PoTxIntMaxAutorCredit = preParamProduit.getString(KEY_PoTxIntMaxAutorCredit);
                    st_PoNbMoisMaxDecouvHorsBilan = preParamProduit.getString(KEY_PoNbMoisMaxDecouvHorsBilan);
                    st_PoNbMoisMaxCreditHorsBilan = preParamProduit.getString(KEY_PoNbMoisMaxCreditHorsBilan);


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
                    if (!preAppellationProduitEpargne){
                        eav.setText(txtDav);
                        eat.setText(txtDat);
                        eap.setText(txtDap);
                    }

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
                    PoIsMultiCpteCourByMemb.setChecked(bool_PoIsMultiCpteCourByMemb);

                    if (st_PoAppellationMemAdhAss.equals("M")){
                        PoAppellationMemAdhAss_rbMembre.setChecked(true);
                    }else if (st_PoAppellationMemAdhAss.equals("A")){
                        PoAppellationMemAdhAss_rbAdherent.setChecked(true);
                    }else if (st_PoAppellationMemAdhAss.equals("S")){
                        PoAppellationMemAdhAss_rbAssocie.setChecked(true);
                    }

                    if (st_PoPeriodDefTxIntCCour.equals("J")){
                        PoPeriodDefTxIntCCour_rbJour.setChecked(true);
                    }else if (st_PoPeriodDefTxIntCCour.equals("M")){
                        PoPeriodDefTxIntCCour_rbMois.setChecked(true);
                    }else if (st_PoPeriodDefTxIntCCour.equals("T")){
                        PoPeriodDefTxIntCCour_rbTrimestre.setChecked(true);
                    }else if (st_PoPeriodDefTxIntCCour.equals("A")){
                        PoPeriodDefTxIntCCour_rbAnnee.setChecked(true);
                    }

                    if (st_PoPeriodDefTxIntCred.equals("J")){
                        PoPeriodDefTxIntCred_rbJourCr.setChecked(true);
                    }else if (st_PoPeriodDefTxIntCred.equals("M")){
                        PoPeriodDefTxIntCred_rbMoisCr.setChecked(true);
                    }else if (st_PoPeriodDefTxIntCred.equals("T")){
                        PoPeriodDefTxIntCred_rbTrimestreCr.setChecked(true);
                    }else if (st_PoPeriodDefTxIntCred.equals("A")){
                        PoPeriodDefTxIntCred_rbAnneeCr.setChecked(true);
                    }

                    if (st_PoOrdreRembDecouv.equals("1")){
                        PoOrdreRembDecouv_rbChoix1.setChecked(true);
                    }else if (st_PoOrdreRembDecouv.equals("2")){
                        PoOrdreRembDecouv_rbChoix2.setChecked(true);
                    }
                    if (st_PoOrdreRembCredit.equals("1")){
                        PoOrdreRembCredit_rbChoix1Cr.setChecked(true);
                    }else if (st_PoOrdreRembCredit.equals("2")){
                        PoOrdreRembCredit_rbChoix2Cr.setChecked(true);
                    }

//                    PoAppellationMemAdhAss.setText(st_PoAppellationMemAdhAss);
//                    PoPeriodDefTxIntCCour.setText(st_PoPeriodDefTxIntCCour);
//                    PoPeriodDefTxIntCred.setText(st_PoPeriodDefTxIntCred);
//                    PoOrdreRembDecouv.setText(st_PoOrdreRembDecouv);
//                    PoOrdreRembCredit.setText(st_PoOrdreRembCredit);
                    PoTxIntMinAutorDecouv.setText(st_PoTxIntMinAutorDecouv);
                    PoTxIntMaxAutorDecouv.setText(st_PoTxIntMaxAutorDecouv);
                    PoTxIntMinAutorAvceSpec.setText(st_PoTxIntMinAutorAvceSpec);
                    PoTxIntMaxAutorAvceSpec.setText(st_PoTxIntMaxAutorAvceSpec);
                    PoTxIntMinAutorDecouvPerm.setText(st_PoTxIntMinAutorDecouvPerm);
                    PoTxIntMaxAutorDecouvPerm.setText(st_PoTxIntMaxAutorDecouvPerm);
                    PoTxIntMinAutorCredit.setText(st_PoTxIntMinAutorCredit);
                    PoTxIntMaxAutorCredit.setText(st_PoTxIntMaxAutorCredit);
                    PoNbMoisMaxDecouvHorsBilan.setText(st_PoNbMoisMaxDecouvHorsBilan);
                    PoNbMoisMaxCreditHorsBilan.setText(st_PoNbMoisMaxCreditHorsBilan);

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

//                st_PoAppellationMemAdhAss = PoAppellationMemAdhAss.getText().toString();
//                st_PoPeriodDefTxIntCCour = PoPeriodDefTxIntCCour.getText().toString();
//                st_PoPeriodDefTxIntCred = PoPeriodDefTxIntCred.getText().toString();
//                st_PoOrdreRembDecouv = PoOrdreRembDecouv.getText().toString();
//                st_PoOrdreRembCredit = PoOrdreRembCredit.getText().toString();
                st_PoTxIntMinAutorDecouv = PoTxIntMinAutorDecouv.getText().toString();
                st_PoTxIntMaxAutorDecouv = PoTxIntMaxAutorDecouv.getText().toString();
                st_PoTxIntMinAutorAvceSpec = PoTxIntMinAutorAvceSpec.getText().toString();
                st_PoTxIntMaxAutorAvceSpec = PoTxIntMaxAutorAvceSpec.getText().toString();
                st_PoTxIntMinAutorDecouvPerm = PoTxIntMinAutorDecouvPerm.getText().toString();
                st_PoTxIntMaxAutorDecouvPerm = PoTxIntMaxAutorDecouvPerm.getText().toString();
                st_PoTxIntMinAutorCredit = PoTxIntMinAutorCredit.getText().toString();
                st_PoTxIntMaxAutorCredit = PoTxIntMaxAutorCredit.getText().toString();
                st_PoNbMoisMaxDecouvHorsBilan = PoNbMoisMaxDecouvHorsBilan.getText().toString();
                st_PoNbMoisMaxCreditHorsBilan = PoNbMoisMaxCreditHorsBilan.getText().toString();
                bool_PoIsMultiCpteCourByMemb = PoIsMultiCpteCourByMemb.isChecked();

                new PreParametrageOFActivity.UpdateMovieAsyncTask().execute();

    }

    /**
     * AsyncTask for updating a preParam details
     */

    private class UpdateMovieAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(PreParametrageOFActivity.this);
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

            httpParams.put(KEY_PoAppellationMemAdhAss, st_PoAppellationMemAdhAss);
            httpParams.put(KEY_PoPeriodDefTxIntCCour, st_PoPeriodDefTxIntCCour);
            httpParams.put(KEY_PoPeriodDefTxIntCred, st_PoPeriodDefTxIntCred);
            httpParams.put(KEY_PoOrdreRembDecouv, st_PoOrdreRembDecouv);
            httpParams.put(KEY_PoOrdreRembCredit, st_PoOrdreRembCredit);
            httpParams.put(KEY_PoTxIntMinAutorDecouv, st_PoTxIntMinAutorDecouv);
            httpParams.put(KEY_PoTxIntMaxAutorDecouv, st_PoTxIntMaxAutorDecouv);
            httpParams.put(KEY_PoTxIntMinAutorAvceSpec, st_PoTxIntMinAutorAvceSpec);
            httpParams.put(KEY_PoTxIntMaxAutorAvceSpec, st_PoTxIntMaxAutorAvceSpec);
            httpParams.put(KEY_PoTxIntMinAutorDecouvPerm, st_PoTxIntMinAutorDecouvPerm);
            httpParams.put(KEY_PoTxIntMaxAutorDecouvPerm, st_PoTxIntMaxAutorDecouvPerm);
            httpParams.put(KEY_PoTxIntMinAutorCredit, st_PoTxIntMinAutorCredit);
            httpParams.put(KEY_PoTxIntMaxAutorCredit, st_PoTxIntMaxAutorCredit);
            httpParams.put(KEY_PoNbMoisMaxDecouvHorsBilan, st_PoNbMoisMaxDecouvHorsBilan);
            httpParams.put(KEY_PoNbMoisMaxCreditHorsBilan, st_PoNbMoisMaxCreditHorsBilan);
            httpParams.put(KEY_PoIsMultiCpteCourByMemb, String.valueOf(bool_PoIsMultiCpteCourByMemb));

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
                        Toast.makeText(PreParametrageOFActivity.this,
                                "Pre Parametres Updated", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(PreParametrageOFActivity.this,
                                "Some error occurred while updating Pre Parametres",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}

