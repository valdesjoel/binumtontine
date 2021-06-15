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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.binumtontine.JRSpinner;
import com.example.binumtontine.R;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.google.android.material.textfield.TextInputLayout;

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


    private static final String KEY_PoBordOperGen = "PoBordOperGen";
    private static final String KEY_PoPrefDepot = "PoPrefDepot";
    private static final String KEY_PoPrefRetrait = "PoPrefRetrait";
    private static final String KEY_PoSuffDepot = "PoSuffDepot";
    private static final String KEY_PoSuffRetrait = "PoSuffRetrait";
    private static final String KEY_PoSuffDecouv = "PoSuffDecouv";
    private static final String KEY_PoSuffAvceSpec = "PoSuffAvceSpec";
    private static final String KEY_PoSuffDecouvPerm = "PoSuffDecouvPerm";
    private static final String KEY_PoSuffCredit = "PoSuffCredit";
    private static final String KEY_PoSuffPartSoc = "PoSuffPartSoc";
    private static final String KEY_PoSuffFondSolid = "PoSuffFondSolid";
    private static final String KEY_PoSuffProduit = "PoSuffProduit";
    private static final String KEY_PoSuffCharge = "PoSuffCharge";
    private static final String KEY_PoSuffTransfert = "PoSuffTransfert";

    private static final String KEY_PoIsTransfIntOn = "PoIsTransfIntOn";
    private static final String KEY_PoCodTransfInt = "PoCodTransfInt";
    private static final String KEY_PoLibelTransfInt = "PoLibelTransfInt";
    private static final String KEY_PoNatureFraisTransfInt = "PoNatureFraisTransfInt";
    private static final String KEY_PoValTauxFrais = "PoValTauxFrais";
    private static final String KEY_PoBaseFrais = "PoBaseFrais";
    private static final String KEY_PoIsTaxe1On = "PoIsTaxe1On";
    private static final String KEY_PoCodTaxe1 = "PoCodTaxe1";
    private static final String KEY_PoLibelTaxe1 = "PoLibelTaxe1";
    private static final String KEY_PoNatureTaxe1 = "PoNatureTaxe1";
    private static final String KEY_PoValTauxTaxe1 = "PoValTauxTaxe1";
    private static final String KEY_PoBaseTaxe1 = "PoBaseTaxe1";
    private static final String KEY_PoIsTaxe2On = "PoIsTaxe2On";
    private static final String KEY_PoCodTaxe2 = "PoCodTaxe2";
    private static final String KEY_PoLibelTaxe2 = "PoLibelTaxe2";
    private static final String KEY_PoNatureTaxe2 = "PoNatureTaxe2";
    private static final String KEY_PoValTauxTaxe2 = "PoValTauxTaxe2";
    private static final String KEY_PoBaseTaxe2 = "PoBaseTaxe2";
    private static final String KEY_PoIsTaxe3On = "PoIsTaxe3On";
    private static final String KEY_PoCodTaxe3 = "PoCodTaxe3";
    private static final String KEY_PoLibelTaxe3 = "PoLibelTaxe3";
    private static final String KEY_PoNatureTaxe3 = "PoNatureTaxe3";
    private static final String KEY_PoValTauxTaxe3 = "PoValTauxTaxe3";
    private static final String KEY_PoBaseTaxe3 = "PoBaseTaxe3";
    private static final String KEY_PoIsTVAOn = "PoIsTVAOn";
    private static final String KEY_PoTxTVA = "PoTxTVA";

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
            PoNbMoisMaxCreditHorsBilan,
            PoPrefDepot,
            PoPrefRetrait,
            PoSuffDepot,
            PoSuffRetrait,
            PoSuffDecouv,
            PoSuffAvceSpec,
            PoSuffDecouvPerm,
            PoSuffCredit,
            PoSuffPartSoc,
            PoSuffFondSolid,
            PoSuffProduit,
            PoSuffCharge,
            PoSuffTransfert;

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

    private String st_PoBordOperGen,
                    st_PoPrefDepot,
    st_PoPrefRetrait,
    st_PoSuffDepot,
    st_PoSuffRetrait,
    st_PoSuffDecouv,
    st_PoSuffAvceSpec,
    st_PoSuffDecouvPerm,
    st_PoSuffCredit,
    st_PoSuffPartSoc,
    st_PoSuffFondSolid,
    st_PoSuffProduit,
    st_PoSuffCharge,
    st_PoSuffTransfert,
            st_PoIsTransfIntOn,
            st_PoCodTransfInt,
            st_PoLibelTransfInt,
            st_PoNatureFraisTransfInt,
            st_PoValTauxFrais,
            st_PoBaseFrais,
            st_PoIsTaxe1On,
            st_PoCodTaxe1,
            st_PoLibelTaxe1,
            st_PoNatureTaxe1,
            st_PoValTauxTaxe1,
            st_PoBaseTaxe1,
            st_PoIsTaxe2On,
            st_PoCodTaxe2,
            st_PoLibelTaxe2,
            st_PoNatureTaxe2,
            st_PoValTauxTaxe2,
            st_PoBaseTaxe2,
            st_PoIsTaxe3On,
            st_PoCodTaxe3,
            st_PoLibelTaxe3,
            st_PoNatureTaxe3,
            st_PoValTauxTaxe3,
            st_PoBaseTaxe3,
            st_PoIsTVAOn,
            st_PoTxTVA;


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
    private RadioButton rbPoBordOperGenBordereau;
    private RadioButton rbPoBordOperGenOperation;
//    Transfert
    private SwitchCompat PoIsTransfIntOn;
    private LinearLayout LL_PoIsTransfIntOn;
    private EditText PoCodTransfInt;
    private EditText PoLibelTransfInt;
    private RadioButton rbPoNatureFraisTransfIntFixe;
    private RadioButton rbPoNatureFraisTransfIntTaux;
    private RadioButton rbPoNatureFraisTransfIntPlage;
    private EditText PoValTauxFrais;
    private JRSpinner PoBaseFrais;
    private TextView tv_plage_TT0_transfert;
    private TextInputLayout input_layout_PoValTauxFrais;
    private TextInputLayout input_layout_PoBaseFrais;
    private TextInputLayout input_layout_PoBaseTaxe1;
    private TextInputLayout input_layout_PoBaseTaxe2;
    private TextInputLayout input_layout_PoBaseTaxe3;
//    Taxe1
    private SwitchCompat PoIsTaxe1On;
    private LinearLayout LL_PoIsTaxe1On;
    private EditText PoCodTaxe1;
    private EditText PoLibelTaxe1;
    private RadioButton rbPoNatureTaxe1Fixe;
    private RadioButton rbPoNatureTaxe1Taux;
    private RadioButton rbPoNatureTaxe1Plage;
    private EditText PoValTauxTaxe1;
    private JRSpinner PoBaseTaxe1;
    private TextView tv_plage_TT1_transfert;
    private TextInputLayout input_layout_PoValTauxTaxe1;
    //Taxe2
    private SwitchCompat PoIsTaxe2On;
    private LinearLayout LL_PoIsTaxe2On;
    private EditText PoCodTaxe2;
    private EditText PoLibelTaxe2;
    private RadioButton rbPoNatureTaxe2Fixe;
    private RadioButton rbPoNatureTaxe2Taux;
    private RadioButton rbPoNatureTaxe2Plage;
    private EditText PoValTauxTaxe2;
    private JRSpinner PoBaseTaxe2;
    private TextView tv_plage_TT2_transfert;
    private TextInputLayout input_layout_PoValTauxTaxe2;
    //Taxe3
    private SwitchCompat PoIsTaxe3On;
    private LinearLayout LL_PoIsTaxe3On;
    private EditText PoCodTaxe3;
    private EditText PoLibelTaxe3;
    private RadioButton rbPoNatureTaxe3Fixe;
    private RadioButton rbPoNatureTaxe3Taux;
    private RadioButton rbPoNatureTaxe3Plage;
    private EditText PoValTauxTaxe3;
    private JRSpinner PoBaseTaxe3;
    private TextView tv_plage_TT3_transfert;
    private TextInputLayout input_layout_PoValTauxTaxe3;
    //TVA

    private SwitchCompat PoIsTVAOn;
    private LinearLayout LL_PoIsTVAOn;
//    private CurrencyEditText etPoTxTVA;
    private EditText etPoTxTVA;

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


        rbPoBordOperGenBordereau = (RadioButton) findViewById(R.id.rbPoBordOperGenBordereau);
        rbPoBordOperGenOperation = (RadioButton) findViewById(R.id.rbPoBordOperGenOperation);
        PoPrefDepot = (EditText) findViewById(R.id.input_PoPrefDepot);
        PoPrefRetrait = (EditText) findViewById(R.id.input_PoPrefRetrait);
        PoSuffDepot = (EditText) findViewById(R.id.input_PoSuffDepot);
        PoSuffRetrait = (EditText) findViewById(R.id.input_PoSuffRetrait);
        PoSuffDecouv = (EditText) findViewById(R.id.input_PoSuffDecouv);
        PoSuffAvceSpec = (EditText) findViewById(R.id.input_PoSuffAvceSpec);
        PoSuffDecouvPerm = (EditText) findViewById(R.id.input_PoSuffDecouvPerm);
        PoSuffCredit = (EditText) findViewById(R.id.input_PoSuffCredit);
        PoSuffPartSoc = (EditText) findViewById(R.id.input_PoSuffPartSoc);
        PoSuffFondSolid = (EditText) findViewById(R.id.input_PoSuffFondSolid);
        PoSuffProduit = (EditText) findViewById(R.id.input_PoSuffProduit);
        PoSuffCharge = (EditText) findViewById(R.id.input_PoSuffCharge);
        PoSuffTransfert = (EditText) findViewById(R.id.input_PoSuffTransfert);


        PoIsTransfIntOn = (SwitchCompat) findViewById(R.id.SwitchPoIsTransfIntOn);
        LL_PoIsTransfIntOn = (LinearLayout) findViewById(R.id.LL_PoIsTransfIntOn);
        PoCodTransfInt = (EditText) findViewById(R.id.input_PoCodTransfInt);
        PoLibelTransfInt = (EditText) findViewById(R.id.input_PoLibelTransfInt);
        rbPoNatureFraisTransfIntFixe = (RadioButton) findViewById(R.id.rbPoNatureFraisTransfIntFixe);
        rbPoNatureFraisTransfIntTaux = (RadioButton) findViewById(R.id.rbPoNatureFraisTransfIntTaux);
        rbPoNatureFraisTransfIntPlage = (RadioButton) findViewById(R.id.rbPoNatureFraisTransfIntPlage);
        PoValTauxFrais = (EditText) findViewById(R.id.input_txt_PoValTauxFrais);
        input_layout_PoValTauxFrais = (TextInputLayout) findViewById(R.id.input_layout_PoValTauxFrais);
        input_layout_PoBaseFrais = (TextInputLayout) findViewById(R.id.input_layout_PoBaseFrais);
        input_layout_PoBaseTaxe1 = (TextInputLayout) findViewById(R.id.input_layout_PoBaseTaxe1);
        input_layout_PoBaseTaxe2 = (TextInputLayout) findViewById(R.id.input_layout_PoBaseTaxe2);
        input_layout_PoBaseTaxe3 = (TextInputLayout) findViewById(R.id.input_layout_PoBaseTaxe3);
        PoBaseFrais = (JRSpinner)findViewById(R.id.spn_my_spinner_PoBaseFrais);
        PoBaseFrais.setItems(getResources().getStringArray(R.array.array_PoBaseFrais)); //this is important, you must set it to set the item list
        PoBaseFrais.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        PoBaseFrais.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically

        PoBaseFrais.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position
                //  cxLocalite = mySpinnerLocalite.getText().toString();
                // Log.d("iddddddd***",caisseLocalite);
            }
        });
        tv_plage_TT0_transfert = (TextView) findViewById(R.id.tv_plage_TT0_transfert);
        //Taxe1
        PoIsTaxe1On = (SwitchCompat) findViewById(R.id.SwitchPoIsTaxe1On);
        LL_PoIsTaxe1On = (LinearLayout) findViewById(R.id.LL_PoIsTaxe1On);
        PoCodTaxe1 = (EditText) findViewById(R.id.input_PoCodTaxe1);
        PoLibelTaxe1 = (EditText) findViewById(R.id.input_PoLibelTaxe1);
        rbPoNatureTaxe1Fixe = (RadioButton) findViewById(R.id.rbPoNatureTaxe1Fixe);
        rbPoNatureTaxe1Taux = (RadioButton) findViewById(R.id.rbPoNatureTaxe1Taux);
        rbPoNatureTaxe1Plage = (RadioButton) findViewById(R.id.rbPoNatureTaxe1Plage);
        PoValTauxTaxe1 = (EditText) findViewById(R.id.input_txt_PoValTauxTaxe1);
        input_layout_PoValTauxTaxe1 = (TextInputLayout) findViewById(R.id.input_layout_PoValTauxTaxe1);
        PoBaseTaxe1 = (JRSpinner)findViewById(R.id.spn_my_spinner_PoBaseTaxe1);
        PoBaseTaxe1.setItems(getResources().getStringArray(R.array.array_PoBaseFrais)); //this is important, you must set it to set the item list
        PoBaseTaxe1.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        PoBaseTaxe1.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically

        PoBaseTaxe1.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });
        tv_plage_TT1_transfert = (TextView) findViewById(R.id.tv_plage_TT1_transfert);
        //Taxe2
        PoIsTaxe2On = (SwitchCompat) findViewById(R.id.SwitchPoIsTaxe2On);
        LL_PoIsTaxe2On = (LinearLayout) findViewById(R.id.LL_PoIsTaxe2On);
        PoCodTaxe2 = (EditText) findViewById(R.id.input_PoCodTaxe2);
        PoLibelTaxe2 = (EditText) findViewById(R.id.input_PoLibelTaxe2);
        rbPoNatureTaxe2Fixe = (RadioButton) findViewById(R.id.rbPoNatureTaxe2Fixe);
        rbPoNatureTaxe2Taux = (RadioButton) findViewById(R.id.rbPoNatureTaxe2Taux);
        rbPoNatureTaxe2Plage = (RadioButton) findViewById(R.id.rbPoNatureTaxe2Plage);
        PoValTauxTaxe2 = (EditText) findViewById(R.id.input_txt_PoValTauxTaxe2);
        input_layout_PoValTauxTaxe2 = (TextInputLayout) findViewById(R.id.input_layout_PoValTauxTaxe2);
        PoBaseTaxe2 = (JRSpinner)findViewById(R.id.spn_my_spinner_PoBaseTaxe2);
        PoBaseTaxe2.setItems(getResources().getStringArray(R.array.array_PoBaseFrais)); //this is important, you must set it to set the item list
        PoBaseTaxe2.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        PoBaseTaxe2.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically

        PoBaseTaxe2.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });
        tv_plage_TT2_transfert = (TextView) findViewById(R.id.tv_plage_TT2_transfert);
        //Taxe3
        PoIsTaxe3On = (SwitchCompat) findViewById(R.id.SwitchPoIsTaxe3On);
        LL_PoIsTaxe3On = (LinearLayout) findViewById(R.id.LL_PoIsTaxe3On);

        PoCodTaxe3 = (EditText) findViewById(R.id.input_PoCodTaxe3);
        PoLibelTaxe3 = (EditText) findViewById(R.id.input_PoLibelTaxe3);
        rbPoNatureTaxe3Fixe = (RadioButton) findViewById(R.id.rbPoNatureTaxe3Fixe);
        rbPoNatureTaxe3Taux = (RadioButton) findViewById(R.id.rbPoNatureTaxe3Taux);
        rbPoNatureTaxe3Plage = (RadioButton) findViewById(R.id.rbPoNatureTaxe3Plage);
        PoValTauxTaxe3 = (EditText) findViewById(R.id.input_txt_PoValTauxTaxe3);
        input_layout_PoValTauxTaxe3 = (TextInputLayout) findViewById(R.id.input_layout_PoValTauxTaxe3);
        PoBaseTaxe3 = (JRSpinner)findViewById(R.id.spn_my_spinner_PoBaseTaxe3);
        PoBaseTaxe3.setItems(getResources().getStringArray(R.array.array_PoBaseFrais)); //this is important, you must set it to set the item list
        PoBaseTaxe3.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        PoBaseTaxe3.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically

        PoBaseTaxe3.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });
        tv_plage_TT3_transfert = (TextView) findViewById(R.id.tv_plage_TT3_transfert);

        PoIsTVAOn = (SwitchCompat) findViewById(R.id.SwitchPoIsTVAOn);
        LL_PoIsTVAOn = (LinearLayout) findViewById(R.id.LL_PoIsTVAOn);
        etPoTxTVA =  findViewById(R.id.etPoTxTVA);
//        TTO
        tv_plage_TT0_transfert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Frais de transfert";
                    ListPlageTT0.IS_TO_CREATE_OR_TO_UPDATE = false;
                    Intent i = new Intent(PreParametrageOFActivity.this,ListPlageTT0.class);

                    i.putExtra(KEY_PRE_PARAM_ID, preParamId);
                    startActivityForResult(i, 20);

                } else {
                    Toast.makeText(PreParametrageOFActivity.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
//        TT1
        tv_plage_TT1_transfert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Frais de taxe 1";
                    ListPlageTT1.IS_TO_CREATE_OR_TO_UPDATE = false;
                    Intent i = new Intent(PreParametrageOFActivity.this,ListPlageTT1.class);

                    i.putExtra(KEY_PRE_PARAM_ID, preParamId);
                    startActivityForResult(i, 20);

                } else {
                    Toast.makeText(PreParametrageOFActivity.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
//        TT2
        tv_plage_TT2_transfert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Frais de taxe 2";
                    ListPlageTT2.IS_TO_CREATE_OR_TO_UPDATE = false;
                    Intent i = new Intent(PreParametrageOFActivity.this,ListPlageTT2.class);

                    i.putExtra(KEY_PRE_PARAM_ID, preParamId);
                    startActivityForResult(i, 20);

                } else {
                    Toast.makeText(PreParametrageOFActivity.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
//        TT3
        tv_plage_TT3_transfert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Frais de taxe 3";
                    ListPlageTT3.IS_TO_CREATE_OR_TO_UPDATE = false;
                    Intent i = new Intent(PreParametrageOFActivity.this,ListPlageTT3.class);

                    i.putExtra(KEY_PRE_PARAM_ID, preParamId);
                    startActivityForResult(i, 20);

                } else {
                    Toast.makeText(PreParametrageOFActivity.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

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
            case R.id.rbPoBordOperGenBordereau:
                st_PoBordOperGen = "B";
                break;
            case R.id.rbPoBordOperGenOperation:
                st_PoBordOperGen = "O";
                break;
//                Transfert
            case R.id.rbPoNatureFraisTransfIntFixe:
                st_PoNatureFraisTransfInt = "F";
                input_layout_PoValTauxFrais.setVisibility(View.VISIBLE);
                input_layout_PoValTauxFrais.setHint("Montant en FCFA");
                input_layout_PoBaseFrais.setVisibility(View.GONE);
                tv_plage_TT0_transfert.setVisibility(View.GONE);
                break;
            case R.id.rbPoNatureFraisTransfIntTaux:
                st_PoNatureFraisTransfInt = "T";

                input_layout_PoValTauxFrais.setVisibility(View.VISIBLE);
                input_layout_PoValTauxFrais.setHint("Taux en %");
                input_layout_PoBaseFrais.setVisibility(View.VISIBLE);
                tv_plage_TT0_transfert.setVisibility(View.GONE);
                break;
            case R.id.rbPoNatureFraisTransfIntPlage:
                st_PoNatureFraisTransfInt = "P";

                input_layout_PoValTauxFrais.setVisibility(View.GONE);
                input_layout_PoBaseFrais.setVisibility(View.GONE);
                tv_plage_TT0_transfert.setVisibility(View.VISIBLE);
                break;
//                Taxe1
            case R.id.rbPoNatureTaxe1Fixe:
                st_PoNatureTaxe1 = "F";
                input_layout_PoValTauxTaxe1.setVisibility(View.VISIBLE);
                input_layout_PoValTauxTaxe1.setHint("Montant en FCFA");
                input_layout_PoBaseTaxe1.setVisibility(View.GONE);
                tv_plage_TT1_transfert.setVisibility(View.GONE);
                break;
            case R.id.rbPoNatureTaxe1Taux:
                st_PoNatureTaxe1 = "T";

                input_layout_PoValTauxTaxe1.setVisibility(View.VISIBLE);
                input_layout_PoValTauxTaxe1.setHint("Taux en %");
                input_layout_PoBaseTaxe1.setVisibility(View.VISIBLE);
                tv_plage_TT1_transfert.setVisibility(View.GONE);
                break;
            case R.id.rbPoNatureTaxe1Plage:
                st_PoNatureTaxe1 = "P";

                input_layout_PoValTauxTaxe1.setVisibility(View.GONE);
                input_layout_PoBaseTaxe1.setVisibility(View.GONE);
                tv_plage_TT1_transfert.setVisibility(View.VISIBLE);
                break;
//                Taxe2
            case R.id.rbPoNatureTaxe2Fixe:
                st_PoNatureTaxe2 = "F";

                input_layout_PoValTauxTaxe2.setVisibility(View.VISIBLE);
                input_layout_PoValTauxTaxe2.setHint("Montant en FCFA");
                input_layout_PoBaseTaxe2.setVisibility(View.GONE);
                tv_plage_TT2_transfert.setVisibility(View.GONE);
                break;
            case R.id.rbPoNatureTaxe2Taux:
                st_PoNatureTaxe2 = "T";

                input_layout_PoValTauxTaxe2.setVisibility(View.VISIBLE);
                input_layout_PoValTauxTaxe2.setHint("Taux en %");
                input_layout_PoBaseTaxe2.setVisibility(View.VISIBLE);
                tv_plage_TT2_transfert.setVisibility(View.GONE);
                break;
            case R.id.rbPoNatureTaxe2Plage:
                st_PoNatureTaxe2 = "P";

                input_layout_PoValTauxTaxe2.setVisibility(View.GONE);
                input_layout_PoBaseTaxe2.setVisibility(View.GONE);
                tv_plage_TT2_transfert.setVisibility(View.VISIBLE);
                break;
//                Taxe3
            case R.id.rbPoNatureTaxe3Fixe:
                st_PoNatureTaxe3 = "F";

                input_layout_PoValTauxTaxe3.setVisibility(View.VISIBLE);
                input_layout_PoValTauxTaxe3.setHint("Montant en FCFA");
                input_layout_PoBaseTaxe3.setVisibility(View.GONE);
                tv_plage_TT3_transfert.setVisibility(View.GONE);
                break;
            case R.id.rbPoNatureTaxe3Taux:
                st_PoNatureTaxe3 = "T";

                input_layout_PoValTauxTaxe3.setVisibility(View.VISIBLE);
                input_layout_PoValTauxTaxe3.setHint("Taux en %");
                input_layout_PoBaseTaxe3.setVisibility(View.VISIBLE);
                tv_plage_TT3_transfert.setVisibility(View.GONE);
                break;
            case R.id.rbPoNatureTaxe3Plage:
                st_PoNatureTaxe3 = "P";

                input_layout_PoValTauxTaxe3.setVisibility(View.GONE);
                input_layout_PoBaseTaxe3.setVisibility(View.GONE);
                tv_plage_TT3_transfert.setVisibility(View.VISIBLE);
                break;
        }

//        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    public void onSwitchButtonClicked(View view) {
//        boolean checked1 = ((Switch) view).isChecked();
//        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.SwitchPoIsTransfIntOn:
                if (PoIsTransfIntOn.isChecked()){

                    LL_PoIsTransfIntOn.setVisibility(View.VISIBLE);
                }else{
                    LL_PoIsTransfIntOn.setVisibility(View.GONE);
                }
                break;
            case R.id.SwitchPoIsTaxe1On:
                if (PoIsTaxe1On.isChecked()){

                    LL_PoIsTaxe1On.setVisibility(View.VISIBLE);
                }else{
                    LL_PoIsTaxe1On.setVisibility(View.GONE);
                }
                break;
            case R.id.SwitchPoIsTaxe2On:
                if (PoIsTaxe2On.isChecked()){

                    LL_PoIsTaxe2On.setVisibility(View.VISIBLE);
                }else{
                    LL_PoIsTaxe2On.setVisibility(View.GONE);
                }
                break;
            case R.id.SwitchPoIsTaxe3On:
                if (PoIsTaxe3On.isChecked()){

                    LL_PoIsTaxe3On.setVisibility(View.VISIBLE);
                }else{
                    LL_PoIsTaxe3On.setVisibility(View.GONE);
                }
                break;
            case R.id.SwitchPoIsTVAOn:
                if (PoIsTVAOn.isChecked()){

                    LL_PoIsTVAOn.setVisibility(View.VISIBLE);
                }else{
                    LL_PoIsTVAOn.setVisibility(View.GONE);
                }
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


            try {
                JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                        BASE_URL + "get_pre_parametrage_details.php", "GET", httpParams);
                Log.e("jsonObject ", String.valueOf(jsonObject));
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



                    st_PoBordOperGen = preParamProduit.getString(KEY_PoBordOperGen);
                    st_PoPrefDepot = preParamProduit.getString(KEY_PoPrefDepot);
                    st_PoPrefRetrait = preParamProduit.getString(KEY_PoPrefRetrait);
                    st_PoSuffDepot = preParamProduit.getString(KEY_PoSuffDepot);
                    st_PoSuffRetrait = preParamProduit.getString(KEY_PoSuffRetrait);
                    st_PoSuffDecouv = preParamProduit.getString(KEY_PoSuffDecouv);
                    st_PoSuffAvceSpec = preParamProduit.getString(KEY_PoSuffAvceSpec);
                    st_PoSuffDecouvPerm = preParamProduit.getString(KEY_PoSuffDecouvPerm);
                    st_PoSuffCredit = preParamProduit.getString(KEY_PoSuffCredit);
                    st_PoSuffPartSoc = preParamProduit.getString(KEY_PoSuffPartSoc);
                    st_PoSuffFondSolid = preParamProduit.getString(KEY_PoSuffFondSolid);
                    st_PoSuffProduit = preParamProduit.getString(KEY_PoSuffProduit);
                    st_PoSuffCharge = preParamProduit.getString(KEY_PoSuffCharge);
                    st_PoSuffTransfert = preParamProduit.getString(KEY_PoSuffTransfert);

                    st_PoIsTransfIntOn = preParamProduit.getString(KEY_PoIsTransfIntOn);
                    st_PoCodTransfInt = preParamProduit.getString(KEY_PoCodTransfInt);
                    st_PoLibelTransfInt = preParamProduit.getString(KEY_PoLibelTransfInt);
                    st_PoNatureFraisTransfInt = preParamProduit.getString(KEY_PoNatureFraisTransfInt);
                    st_PoValTauxFrais = preParamProduit.getString(KEY_PoValTauxFrais);
                    st_PoBaseFrais = decodeBaseFraisTransfert(preParamProduit.getString(KEY_PoBaseFrais));
                    st_PoIsTaxe1On = preParamProduit.getString(KEY_PoIsTaxe1On);
                    st_PoCodTaxe1 = preParamProduit.getString(KEY_PoCodTaxe1);
                    st_PoLibelTaxe1 = preParamProduit.getString(KEY_PoLibelTaxe1);
                    st_PoNatureTaxe1 = preParamProduit.getString(KEY_PoNatureTaxe1);
                    st_PoValTauxTaxe1 = preParamProduit.getString(KEY_PoValTauxTaxe1);
                    st_PoBaseTaxe1 = decodeBaseFraisTransfert(preParamProduit.getString(KEY_PoBaseTaxe1));
                    st_PoIsTaxe2On = preParamProduit.getString(KEY_PoIsTaxe2On);
                    st_PoCodTaxe2 = preParamProduit.getString(KEY_PoCodTaxe2);
                    st_PoLibelTaxe2 = preParamProduit.getString(KEY_PoLibelTaxe2);
                    st_PoNatureTaxe2 = preParamProduit.getString(KEY_PoNatureTaxe2);
                    st_PoValTauxTaxe2 = preParamProduit.getString(KEY_PoValTauxTaxe2);
                    st_PoBaseTaxe2 = decodeBaseFraisTransfert(preParamProduit.getString(KEY_PoBaseTaxe2));
                    st_PoIsTaxe3On = preParamProduit.getString(KEY_PoIsTaxe3On);
                    st_PoCodTaxe3 = preParamProduit.getString(KEY_PoCodTaxe3);
                    st_PoLibelTaxe3 = preParamProduit.getString(KEY_PoLibelTaxe3);
                    st_PoNatureTaxe3 = preParamProduit.getString(KEY_PoNatureTaxe3);
                    st_PoValTauxTaxe3 = preParamProduit.getString(KEY_PoValTauxTaxe3);
                    st_PoBaseTaxe3 = decodeBaseFraisTransfert(preParamProduit.getString(KEY_PoBaseTaxe3));

                    st_PoIsTVAOn = preParamProduit.getString(KEY_PoIsTVAOn);
                    st_PoTxTVA = preParamProduit.getString(KEY_PoTxTVA);



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
                    try {
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

                        if (st_PoBordOperGen.equals("B")){
                            rbPoBordOperGenBordereau.setChecked(true);
                        }else{
                            rbPoBordOperGenOperation.setChecked(true);
                        }
                        PoPrefDepot.setText(st_PoPrefDepot);
                        PoPrefRetrait.setText(st_PoPrefRetrait);
                        PoSuffDepot.setText(st_PoSuffDepot);
                        PoSuffRetrait.setText(st_PoSuffRetrait);
                        PoSuffDecouv.setText(st_PoSuffDecouv);
                        PoSuffAvceSpec.setText(st_PoSuffAvceSpec);
                        PoSuffDecouvPerm.setText(st_PoSuffDecouvPerm);
                        PoSuffCredit.setText(st_PoSuffCredit);
                        PoSuffPartSoc.setText(st_PoSuffPartSoc);
                        PoSuffFondSolid.setText(st_PoSuffFondSolid);
                        PoSuffProduit.setText(st_PoSuffProduit);
                        PoSuffCharge.setText(st_PoSuffCharge);
                        PoSuffTransfert.setText(st_PoSuffTransfert);

                        //Transfert
                        PoCodTransfInt.setText(st_PoCodTransfInt);
                        PoLibelTransfInt.setText(st_PoLibelTransfInt);
                        if (st_PoNatureFraisTransfInt.equals("F")){
                            rbPoNatureFraisTransfIntFixe.setChecked(true);
                            onRadioButtonClicked(rbPoNatureFraisTransfIntFixe);
                        }else if (st_PoNatureFraisTransfInt.equals("T")){
                            rbPoNatureFraisTransfIntTaux.setChecked(true);
                            onRadioButtonClicked(rbPoNatureFraisTransfIntTaux);
                        }else{
                            rbPoNatureFraisTransfIntPlage.setChecked(true);
                            onRadioButtonClicked(rbPoNatureFraisTransfIntPlage);
                        }

                        if (st_PoIsTransfIntOn.equals("Y")){
                            PoIsTransfIntOn.setChecked(true);
                        }else{
                            PoIsTransfIntOn.setChecked(false);
                        }
                        onSwitchButtonClicked(PoIsTransfIntOn);

                        PoValTauxFrais.setText(st_PoValTauxFrais);
                        PoBaseFrais.setText(st_PoBaseFrais);
                        //Taxe1
                        PoCodTaxe1.setText(st_PoCodTaxe1);
                        PoLibelTaxe1.setText(st_PoLibelTaxe1);
                        if (st_PoNatureTaxe1.equals("F")){
                            rbPoNatureTaxe1Fixe.setChecked(true);
                            onRadioButtonClicked(rbPoNatureTaxe1Fixe);
                        }else if (st_PoNatureTaxe1.equals("T")){
                            rbPoNatureTaxe1Taux.setChecked(true);
                            onRadioButtonClicked(rbPoNatureTaxe1Taux);
                        }else{
                            rbPoNatureTaxe1Plage.setChecked(true);
                            onRadioButtonClicked(rbPoNatureTaxe1Plage);
                        }

                        if (st_PoIsTaxe1On.equals("Y")){
                            PoIsTaxe1On.setChecked(true);

                        }else{
                            PoIsTaxe1On.setChecked(false);
                        }
                        onSwitchButtonClicked(PoIsTaxe1On);
                        PoValTauxTaxe1.setText(st_PoValTauxTaxe1);
                        PoBaseTaxe1.setText(st_PoBaseTaxe1);
                        //Taxe2
                        PoCodTaxe2.setText(st_PoCodTaxe2);
                        PoLibelTaxe2.setText(st_PoLibelTaxe2);
                        if (st_PoNatureTaxe2.equals("F")){
                            rbPoNatureTaxe2Fixe.setChecked(true);
                            onRadioButtonClicked(rbPoNatureTaxe2Fixe);
                        }else if (st_PoNatureTaxe2.equals("T")){
                            rbPoNatureTaxe2Taux.setChecked(true);
                            onRadioButtonClicked(rbPoNatureTaxe2Taux);
                        }else{
                            rbPoNatureTaxe2Plage.setChecked(true);
                            onRadioButtonClicked(rbPoNatureTaxe2Plage);
                        }

                        if (st_PoIsTaxe2On.equals("Y")){
                            PoIsTaxe2On.setChecked(true);
                        }else{
                            PoIsTaxe2On.setChecked(false);
                        }
                        onSwitchButtonClicked(PoIsTaxe2On);
                        PoValTauxTaxe2.setText(st_PoValTauxTaxe2);
                        PoBaseTaxe2.setText(st_PoBaseTaxe2);
                        //Taxe3
                        PoCodTaxe3.setText(st_PoCodTaxe3);
                        PoLibelTaxe3.setText(st_PoLibelTaxe3);
                        if (st_PoNatureTaxe3.equals("F")){
                            rbPoNatureTaxe3Fixe.setChecked(true);
                            onRadioButtonClicked(rbPoNatureTaxe3Fixe);
                        }else if (st_PoNatureTaxe3.equals("T")){
                            rbPoNatureTaxe3Taux.setChecked(true);
                            onRadioButtonClicked(rbPoNatureTaxe3Taux);
                        }else{
                            rbPoNatureTaxe3Plage.setChecked(true);
                            onRadioButtonClicked(rbPoNatureTaxe3Plage);
                        }

                        if (st_PoIsTaxe3On.equals("Y")){
                            PoIsTaxe3On.setChecked(true);
                        }else{
                            PoIsTaxe3On.setChecked(false);
                        }
                        onSwitchButtonClicked(PoIsTaxe3On);
                        PoValTauxTaxe3.setText(st_PoValTauxTaxe3);
                        PoBaseTaxe3.setText(st_PoBaseTaxe3);

                        if (st_PoIsTVAOn.equals("Y")){
                            PoIsTVAOn.setChecked(true);
                        }else{
                            PoIsTVAOn.setChecked(false);
                        }
                        onSwitchButtonClicked(PoIsTVAOn);
                        etPoTxTVA.setText(st_PoTxTVA);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

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

        st_PoPrefDepot = PoPrefDepot.getText().toString();
        st_PoPrefRetrait = PoPrefRetrait.getText().toString();
        st_PoSuffDepot = PoSuffDepot.getText().toString();
        st_PoSuffRetrait = PoSuffRetrait.getText().toString();
        st_PoSuffDecouv = PoSuffDecouv.getText().toString();
        st_PoSuffAvceSpec = PoSuffAvceSpec.getText().toString();
        st_PoSuffDecouvPerm = PoSuffDecouvPerm.getText().toString();
        st_PoSuffCredit = PoSuffCredit.getText().toString();
        st_PoSuffPartSoc = PoSuffPartSoc.getText().toString();
        st_PoSuffFondSolid = PoSuffFondSolid.getText().toString();
        st_PoSuffProduit = PoSuffProduit.getText().toString();
        st_PoSuffCharge = PoSuffCharge.getText().toString();
        st_PoSuffTransfert = PoSuffTransfert.getText().toString();

//        Transfert
        if (PoIsTransfIntOn.isChecked()){
            st_PoIsTransfIntOn = "Y";
        }else{
            st_PoIsTransfIntOn = "N";
        }
        st_PoCodTransfInt = PoCodTransfInt.getText().toString().trim();
        st_PoLibelTransfInt = PoLibelTransfInt.getText().toString().trim();
        st_PoValTauxFrais = PoValTauxFrais.getText().toString().trim();
        st_PoBaseFrais = encodeBaseFraisTransfert(PoBaseFrais.getText().toString().trim());
//        Taxe 1
        if (PoIsTaxe1On.isChecked()){
            st_PoIsTaxe1On = "Y";
        }else{
            st_PoIsTaxe1On = "N";
        }

        st_PoCodTaxe1 = PoCodTaxe1.getText().toString().trim();
        st_PoLibelTaxe1 = PoLibelTaxe1.getText().toString().trim();
        st_PoValTauxTaxe1 = PoValTauxTaxe1.getText().toString().trim();
//        Taxe 2
        if (PoIsTaxe2On.isChecked()){
            st_PoIsTaxe2On = "Y";
        }else{
            st_PoIsTaxe2On = "N";
        }
        st_PoCodTaxe2 = PoCodTaxe2.getText().toString().trim();
        st_PoLibelTaxe2 = PoLibelTaxe2.getText().toString().trim();
        st_PoValTauxTaxe2 = PoValTauxTaxe2.getText().toString().trim();
        st_PoBaseTaxe2 = encodeBaseFraisTransfert(PoBaseTaxe2.getText().toString().trim());
//        Taxe 3
        if (PoIsTaxe3On.isChecked()){
            st_PoIsTaxe3On = "Y";
        }else{
            st_PoIsTaxe3On = "N";
        }
        st_PoCodTaxe3 = PoCodTaxe3.getText().toString().trim();
        st_PoLibelTaxe3 = PoLibelTaxe3.getText().toString().trim();
        st_PoValTauxTaxe3 = PoValTauxTaxe3.getText().toString().trim();
        st_PoBaseTaxe3 = encodeBaseFraisTransfert(PoBaseTaxe3.getText().toString().trim());
        //        TVA
        if (PoIsTVAOn.isChecked()){
            st_PoIsTVAOn = "Y";
        }else{
            st_PoIsTVAOn = "N";
        }
        st_PoTxTVA = etPoTxTVA.getText().toString().trim();

        new PreParametrageOFActivity.UpdateMovieAsyncTask().execute();

    }

    public static String encodeBaseFraisTransfert(String decode){
        String encode="";
        if (decode.equals("1- Montant du transfert")){
            encode = "MDT";
        }
        return encode;
    }
    public static String decodeBaseFraisTransfert(String encode){
        String decode="";
        if (encode.equals("MDT")){
            decode = "1- Montant du transfert";
        }
        return decode;
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


            httpParams.put(KEY_PoBordOperGen, st_PoBordOperGen);
            httpParams.put(KEY_PoPrefDepot, st_PoPrefDepot);
            httpParams.put(KEY_PoPrefRetrait, st_PoPrefRetrait);
            httpParams.put(KEY_PoSuffDepot, st_PoSuffDepot);
            httpParams.put(KEY_PoSuffRetrait, st_PoSuffRetrait);
            httpParams.put(KEY_PoSuffDecouv, st_PoSuffDecouv);
            httpParams.put(KEY_PoSuffAvceSpec, st_PoSuffAvceSpec);
            httpParams.put(KEY_PoSuffDecouvPerm, st_PoSuffDecouvPerm);
            httpParams.put(KEY_PoSuffCredit, st_PoSuffCredit);
            httpParams.put(KEY_PoSuffPartSoc, st_PoSuffPartSoc);
            httpParams.put(KEY_PoSuffFondSolid, st_PoSuffFondSolid);
            httpParams.put(KEY_PoSuffProduit, st_PoSuffProduit);
            httpParams.put(KEY_PoSuffCharge, st_PoSuffCharge);
            httpParams.put(KEY_PoSuffTransfert, st_PoSuffTransfert);
//            Transfert
            httpParams.put(KEY_PoIsTransfIntOn, st_PoIsTransfIntOn);
            httpParams.put(KEY_PoCodTransfInt, st_PoCodTransfInt);
            httpParams.put(KEY_PoLibelTransfInt, st_PoLibelTransfInt);
            httpParams.put(KEY_PoNatureFraisTransfInt, st_PoNatureFraisTransfInt);
            httpParams.put(KEY_PoValTauxFrais, st_PoValTauxFrais);
            httpParams.put(KEY_PoBaseFrais, st_PoBaseFrais);
//            Taxe1
            httpParams.put(KEY_PoIsTaxe1On, st_PoIsTaxe1On);
            httpParams.put(KEY_PoCodTaxe1, st_PoCodTaxe1);
            httpParams.put(KEY_PoLibelTaxe1, st_PoLibelTaxe1);
            httpParams.put(KEY_PoNatureTaxe1, st_PoNatureTaxe1);
            httpParams.put(KEY_PoValTauxTaxe1, st_PoValTauxTaxe1);
            httpParams.put(KEY_PoBaseTaxe1, st_PoBaseTaxe1);
            //Taxe2
            httpParams.put(KEY_PoIsTaxe2On, st_PoIsTaxe2On);
            httpParams.put(KEY_PoCodTaxe2, st_PoCodTaxe2);
            httpParams.put(KEY_PoLibelTaxe2, st_PoLibelTaxe2);
            httpParams.put(KEY_PoNatureTaxe2, st_PoNatureTaxe2);
            httpParams.put(KEY_PoValTauxTaxe2, st_PoValTauxTaxe2);
            httpParams.put(KEY_PoBaseTaxe2, st_PoBaseTaxe2);

            //Taxe3
            httpParams.put(KEY_PoIsTaxe3On, st_PoIsTaxe3On);
            httpParams.put(KEY_PoCodTaxe3, st_PoCodTaxe3);
            httpParams.put(KEY_PoLibelTaxe3, st_PoLibelTaxe3);
            httpParams.put(KEY_PoNatureTaxe3, st_PoNatureTaxe3);
            httpParams.put(KEY_PoValTauxTaxe3, st_PoValTauxTaxe3);
            httpParams.put(KEY_PoBaseTaxe3, st_PoBaseTaxe3);

            //TVA
            httpParams.put(KEY_PoIsTVAOn, st_PoIsTVAOn);
            httpParams.put(KEY_PoTxTVA, st_PoTxTVA);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "update_pre_parametrage.php", "POST", httpParams);
            try {
//                Log.e("update_pre_parametrage", jsonObject+"");
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

