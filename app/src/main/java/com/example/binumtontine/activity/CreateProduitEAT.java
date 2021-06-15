
package com.example.binumtontine.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.binumtontine.JRSpinner;
import com.example.binumtontine.R;
import com.example.binumtontine.activity.adherent.ModelPlageData;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.example.binumtontine.modele.ProduitEAT;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.binumtontine.controleur.MyData.alreadyUpperCase;

public class CreateProduitEAT extends AppCompatActivity implements SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";

    private static final String KEY_EAT_CODE = "EtCode";
    private static final String KEY_EAT_LIBELLE = "EtLibelle";
    private static final String KEY_EAT_MT_MIN_MISE = "EtMtMinMise";
    private static final String KEY_EAT_MT_MAX_MISE = "EtMtMaxMise";
    private static final String KEY_EAT_DUREE_MIN = "EtDureeMin";
    private static final String KEY_EAT_DUREE_MAX = "EtDureeMax";
    private static final String KEY_EAT_NATURE_PAS = "EtNaturePas";
    private static final String KEY_EAT_NBRE_UNITE_PAS = "EtNbreUnitePas";
    private static final String KEY_EAT_TYPE_TX_INTER = "EtTypTxInter";
    private static final String KEY_EAT_VAL_TX_INTER = "EtValTxInter";
    private static final String KEY_EAT_BaseTxInter = "EtBaseTxInter";

    private static final String ET_IS_TX_INT_NEG = "EtIsTxIntNeg";
    private static final String ET_IS_PRISE_INT_MISE_ON = "EtIsPriseIntMiseOn";
    private static final String ET_IS_PENAL_RUP_ANT = "EtIsPenalRupAnt";
    private static final String ET_NATURE_RUP_AN = "EtNatureRupAn";
    private static final String ET_VAL_TX_MT_RUPTURE = "EtValTxMtRupture";
    private static final String ET_BASE_TX_PENAL = "EtBaseTxPenal";

    private static final String ET_IS_EPAR_RETIRE_FIN = "EtIsEparRetireFin";
    private static final String ET_IS_EPAR_TRANSF_FIN = "EtIsEparTransfFin";
    private static final String ET_IS_ONLY_TOT_TRANSF = "EtIsOnlyTotTransf";
    private static final String ET_IS_EPAR_RENOUV_FIN = "EtIsEparRenouvFin";
    private static final String ET_ACTION_DEF_A_TERME = "EtActionDefATerme";
    private static final String ET_IS_MULTI_EAT_ON = "EtIsMultiEATOn";
    private static final String ET_IS_INTER_DUS_RUP_ANT = "EtIsInterDusRupAnt";
    private static final String ET_IS_NEW_TX_INT_RUP_ANT = "EtIsNewTxIntRupAnt";
    private static final String ET_TYP_NEW_TX_INT_RUP_ANT = "EtTypNewTxIntRupAnt";
    private static final String ET_VAL_TX_INT_PENAL = "EtValTxIntPenal";
    private static final String ET_BASE_TX_INT_PENAL = "EtBaseTxIntPenal";

    private static final String KEY_EtIsPenalDeblocageRupAnt = "EtIsPenalDeblocageRupAnt";
    private static final String KEY_EtNaturePenalDeblocage = "EtNaturePenalDeblocage";
    private static final String KEY_EtValTxMtPenaliteDeblocage = "EtValTxMtPenaliteDeblocage";
    private static final String KEY_EtBaseTxMtPenalDeblocage = "EtBaseTxMtPenalDeblocage";

    private static final String ET_TX_INT_PENAL_NEG = "EtTxIntPenalNeg";
    private static final String ET_CAISSE_ID = "EtCaisseId";
    private static final String KEY_EtIsTVAOn = "EtIsTVAOn";




    private static String STRING_EMPTY = "";
    private EditText EtCode_ET;
    private EditText EtLibelleET;
    private EditText EtMtMinMise_ET;
    private EditText EtMtMaxMise_ET;
    private EditText EtDureeMin_ET;
    private EditText EtDureeMax_ET;
    private RadioButton rbEtNaturePasFixe;
    private RadioButton rbEtNaturePasSaut;
    private EditText EtNbreUnitePas_ET;
    private RadioButton rbEtTypTxInterFixe;
    private RadioButton rbEtTypTxInterTaux;
    private RadioButton rbEtTypTxInterPlage;
    private EditText EtValTxInter_ET;
//    private EditText EtPlageTxInterValeur_ET;

    private Switch EtIsTxIntNeg_SW;
    private Switch EtIsPriseIntMiseOn_SW;
    private Switch EtIsPenalRupAnt_SW;
    private RadioButton rbEtNatureRupAnTaux;
    private RadioButton rbEtNatureRupAnMontant;
    private RadioButton rbEtNatureRupAnPlage;
    private EditText EtValTxMtRupture_ET;
    private JRSpinner EtBaseTxPenal_ET; //pour gérer le spinner contenant les bases du EtBaseTxPenal

    private Switch EtIsEparRetireFin_SW;
    private Switch EtIsEparTransfFin_SW;
    private Switch EtIsOnlyTotTransf_SW;
    private Switch EtIsEparRenouvFin_SW;
//    private Switch EtActionDefATerme_SW;

    private RadioButton rb_CtModRenouv_transfert_vers_eav;
    private RadioButton rb_CtModRenouv_renouveller;
    private RadioButton rb_CtModRenouv_retirer;
    private RadioButton rb_CtModRenouv_ras;
    private Switch EtIsMultiEATOn_SW;
    private Switch EtIsInterDusRupAnt_SW;
    private Switch EtIsNewTxIntRupAnt_SW;
    private RadioButton rbEtTypNewTxIntRupAntFixe;
    private RadioButton rbEtTypNewTxIntRupAntTaux;
    private RadioButton rbEtTypNewTxIntRupAntPlage;
    private RadioButton rbEtTypNewTxIntRupAntPlageMois;
    private EditText EtValTxIntPenal_ET;
    private JRSpinner EtBaseTxIntPenal_ET;
    private Switch EtTxIntPenalNeg_SW;


    private String EtCode;
    private String EtLibelle;
    private String EtMtMinMise;
    private String EtMtMaxMise;
    private String EtDureeMin;
    private String EtDureeMax;

    private String EtNaturePas;
    private String EtNbreUnitePas;
    private String EtTypTxInter;
    private String EtValTxInter;
    private String EtBaseTxInter;
    private String EtIsTxIntNeg ;
    private String EtIsPriseIntMiseOn;
    private String EtIsPenalRupAnt ;
    private String EtNatureRupAn ;
    private String EtValTxMtRupture;
    private String EtBaseTxPenal ;
    private String EtIsEparRetireFin;
    private String EtIsEparTransfFin;
    private String EtIsOnlyTotTransf;
    private String EtIsEparRenouvFin;
    private String EtActionDefATerme;
    private String EtIsMultiEATOn ;
    private String EtIsInterDusRupAnt;
    private String EtIsNewTxIntRupAnt ;
    private String EtTypNewTxIntRupAnt;
    private String EtValTxIntPenal;
    private String EtBaseTxIntPenal ;
    private String EtTxIntPenalNeg ;



    private LinearLayout LL_EtNatureRupAn;
    private LinearLayout LL_EtTypNewTxIntRupAnt;


    private TextInputLayout input_layout_EtBaseTxInter;
    private JRSpinner mySpinnerEtBaseTxInter; //pour gérer le spinner contenant les bases du Tx Int avance spéciale

    private Button addButton;
    private Button deleteButton;
    private Button cancelButton;
    private int success;
    private ProgressDialog pDialog;


    private TextView tv_header_produit;
    private TextView tv_plageEtValTxInter;
    private TextView tv_plage_EtValTxMtRupture;
    private TextView tv_plage_EtValTxIntPenal;

    public static ArrayList<ModelPlageData> plageDataListTIT = new ArrayList<ModelPlageData>(); //to manage plageData TIT
    public static ArrayList<ModelPlageData> plageDataListTPT = new ArrayList<ModelPlageData>(); //to manage plageData TPT
    public static ArrayList<ModelPlageData> plageDataListRAT = new ArrayList<ModelPlageData>(); //to manage plageData RAT
    public static ArrayList<ModelPlageData> plageDataListDBT = new ArrayList<ModelPlageData>(); //to manage plageData DBT

    //#TIT
    private static final String KEY_EAT_PLAGE_TX_INTER_DEBUT = "EtTitDebut";
    private static final String KEY_EAT_PLAGE_TX_INTER_FIN = "EtTitFin";
    private static final String KEY_EAT_PLAGE_TX_INTER_VALEUR = "EtTitValeur";
    private static final String KEY_EAT_PLAGE_TX_INTER_BASE = "EtTitBase";
    private static final String KEY_EAT_PLAGE_TX_INTER_NATURE = "EtTitNature";

    private String tabPlageDebutTIT ="";
    private String tabPlageFinTIT ="";
    private String tabPlageValeurTIT ="";
    private String tabPlageBaseTIT ="";
    private String tabPlageNatureTIT ="";

    //#TPT
    private static final String KEY_EAT_PLAGE_TPT_DEBUT = "EtTptDebut";
    private static final String KEY_EAT_PLAGE_TPT_FIN = "EtTptFin";
    private static final String KEY_EAT_PLAGE_TPT_VALEUR = "EtTptValeur";
    private static final String KEY_EAT_PLAGE_TPT_BASE = "EtTptBase";
    private static final String KEY_EAT_PLAGE_TPT_NATURE = "EtTptNature";

    private String tabPlageDebutTPT ="";
    private String tabPlageFinTPT ="";
    private String tabPlageValeurTPT ="";
    private String tabPlageBaseTPT ="";
    private String tabPlageNatureTPT ="";

    //#RAT
    private static final String KEY_EAT_PLAGE_RAT_DEBUT = "EtRatDebut";
    private static final String KEY_EAT_PLAGE_RAT_FIN = "EtRatFin";
    private static final String KEY_EAT_PLAGE_RAT_VALEUR = "EtRatValeur";
    private static final String KEY_EAT_PLAGE_RAT_BASE = "EtRatBase";
    private static final String KEY_EAT_PLAGE_RAT_NATURE = "EtRatNature";

    private String tabPlageDebutRAT ="";
    private String tabPlageFinRAT ="";
    private String tabPlageValeurRAT ="";
    private String tabPlageBaseRAT ="";
    private String tabPlageNatureRAT ="";
    //START 06/11/2020

    //#DBT
    private static final String KEY_EAT_PLAGE_DBT_DEBUT = "EtDbtDebut";
    private static final String KEY_EAT_PLAGE_DBT_FIN = "EtDbtFin";
    private static final String KEY_EAT_PLAGE_DBT_VALEUR = "EtDbtValeur";
    private static final String KEY_EAT_PLAGE_DBT_BASE = "EtDbtBase";
    private static final String KEY_EAT_PLAGE_DBT_NATURE = "EtDbtNature";

    private String tabPlageDebutDBT ="";
    private String tabPlageFinDBT ="";
    private String tabPlageValeurDBT ="";
    private String tabPlageBaseDBT ="";
    private String tabPlageNatureDBT ="";

    private Switch EtIsPenalDeblocageRupAnt_SW;
    private RadioButton rbEtNaturePenalDeblocageTaux;
    private RadioButton rbEtNaturePenalDeblocageMontant;
    private RadioButton rbEtNaturePenalDeblocagePlage;
    private EditText EtValTxMtPenaliteDeblocage_ET;
    private JRSpinner EtBaseTxMtPenalDeblocage_JR;
    private TextInputLayout input_layout_EtBaseTxPenalDeblocage;
    private LinearLayout ll_EtNaturePenalDeblocage;
    private String EtNaturePenalDeblocage;
    private TextView tv_plageEtValTxMtPenaliteDeblocage;
    private String EtValTxMtPenaliteDeblocage;
    private String EtIsPenalDeblocageRupAnt;
    private String EtBaseTxMtPenalDeblocage;

    private SwitchCompat EtIsTVAOn;
    private String st_EtIsTVAOn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat);

        tv_header_produit = (TextView) findViewById(R.id.header_produit);
        tv_header_produit.setText("Produit EAT\n"+"Caisse: "+ MyData.CAISSE_NAME);

        LL_EtNatureRupAn = (LinearLayout)findViewById(R.id.ll_EtNatureRupAn);
        LL_EtTypNewTxIntRupAnt = (LinearLayout)findViewById(R.id.ll_EtTypNewTxIntRupAnt);

        EtCode_ET = (EditText) findViewById(R.id.input_txt_Code_EAT);
        alreadyUpperCase(EtCode_ET);
        EtLibelleET = (EditText) findViewById(R.id.input_txt_LibelleEAT);
        EtMtMinMise_ET = (EditText) findViewById(R.id.input_txt_MinMtMiseEAT);
        EtMtMaxMise_ET = (EditText) findViewById(R.id.input_txt_MaxMtMiseEAT);
        EtDureeMin_ET = (EditText) findViewById(R.id.input_txt_DureeMinEAT);
        EtDureeMax_ET = (EditText) findViewById(R.id.input_txt_DureeMaxEAT);

        rbEtNaturePasFixe = (RadioButton) findViewById(R.id.rb_EtNaturePasFixe);
        rbEtNaturePasSaut = (RadioButton) findViewById(R.id.rb_EtNaturePasSaut);
        EtNbreUnitePas_ET = (EditText) findViewById(R.id.input_txt_NbreUnitePasEAT);
        rbEtTypTxInterFixe = (RadioButton) findViewById(R.id.rbEtTypTxInterFixe);
        rbEtTypTxInterTaux = (RadioButton) findViewById(R.id.rbEtTypTxInterTaux);
        rbEtTypTxInterPlage = (RadioButton) findViewById(R.id.rbEtTypTxInterPlage);
        EtValTxInter_ET = (EditText) findViewById(R.id.input_txt_ValeurTauxInteretEAT);

        EtIsTxIntNeg_SW = (Switch) findViewById(R.id.SwitchTauxInteretNegocieEAT);
        EtIsPriseIntMiseOn_SW = (Switch) findViewById(R.id.SwitchPrendreInteretDesLaMiseEAT);
        EtIsPenalRupAnt_SW = (Switch) findViewById(R.id.SwitchPenaliteEnCasDeRuptureEAT);

        rbEtNatureRupAnTaux = (RadioButton) findViewById(R.id.rb_EtNatureRupAnTaux);
        rbEtNatureRupAnMontant = (RadioButton) findViewById(R.id.rb_EtNatureRupAnMontant);
        rbEtNatureRupAnPlage = (RadioButton) findViewById(R.id.rb_EtNatureRupAnPlage);

        EtValTxMtRupture_ET = (EditText) findViewById(R.id.input_txt_ValTxMtRuptureEAT);

        /*Base EtBaseTxPenal_ET debut*/
        EtBaseTxPenal_ET = (JRSpinner) findViewById(R.id.input_txt_BaseTxPenalEAT);
        EtBaseTxPenal_ET.setItems(getResources().getStringArray(R.array.array_EtBaseTxPenal)); //this is important, you must set it to set the item list
        EtBaseTxPenal_ET.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        EtBaseTxPenal_ET.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        EtBaseTxPenal_ET.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });
        /*Base EtBaseTxPenal_ET fin*/


        EtIsEparRetireFin_SW = (Switch) findViewById(R.id.SwitchPossibiliteRetirerEAT);
        EtIsEparTransfFin_SW = (Switch) findViewById(R.id.SwitchPossibiliteTransfererVersEAT_EAV);
        EtIsOnlyTotTransf_SW = (Switch) findViewById(R.id.SwitchTransfererTotaliteVersEAT_EAV);
        EtIsEparRenouvFin_SW = (Switch) findViewById(R.id.SwitchPossibiliteRenouvelerEAT);

        rb_CtModRenouv_transfert_vers_eav = (RadioButton) findViewById(R.id.rb_CtModRenouv_transfert_vers_eav); //29_a
        rb_CtModRenouv_renouveller = (RadioButton) findViewById(R.id.rb_CtModRenouv_renouveller); //29_b
        rb_CtModRenouv_retirer = (RadioButton) findViewById(R.id.rb_CtModRenouv_retirer); //29_c
        rb_CtModRenouv_ras = (RadioButton) findViewById(R.id.rb_CtModRenouv_ras); //29_d
        EtIsMultiEATOn_SW = (Switch) findViewById(R.id.SwitchMultiEAT);
        EtIsInterDusRupAnt_SW = (Switch) findViewById(R.id.SwitchInteretDusRuptureEAP);
        EtIsNewTxIntRupAnt_SW = (Switch) findViewById(R.id.SwitchDefinirNouveauTxInteretEAT);
        rbEtTypNewTxIntRupAntFixe = (RadioButton) findViewById(R.id.rbEtTypeNouveauTxInteretRuptureEATFixe);
        rbEtTypNewTxIntRupAntTaux = (RadioButton) findViewById(R.id.rbEtTypeNouveauTxInteretRuptureEATTaux);
        rbEtTypNewTxIntRupAntPlage = (RadioButton) findViewById(R.id.rbEtTypeNouveauTxInteretRuptureEATPlage);
        rbEtTypNewTxIntRupAntPlageMois = (RadioButton) findViewById(R.id.rbEtTypeNouveauTxInteretRuptureEATPlageMois);
        EtValTxIntPenal_ET = (EditText) findViewById(R.id.txtValeurNewTxInteretRuptureEAT);

        /*Base EtBaseTxIntPenal_ET debut*/
        EtBaseTxIntPenal_ET = (JRSpinner) findViewById(R.id.txtBaseNewTxInteretEAT);
        EtBaseTxIntPenal_ET.setItems(getResources().getStringArray(R.array.array_EtBaseTxIntPenal)); //this is important, you must set it to set the item list
        EtBaseTxIntPenal_ET.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        EtBaseTxIntPenal_ET.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        EtBaseTxIntPenal_ET.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });
        /*Base EtBaseTxIntPenal_ET fin*/

        EtTxIntPenalNeg_SW = (Switch) findViewById(R.id.SwitchTxNewInteretNegociableEAT);

        mySpinnerEtBaseTxInter = (JRSpinner)findViewById(R.id.spn_my_spinner_base_EtBaseTxInter);
        /*Base EtBaseTxInter debut*/
        mySpinnerEtBaseTxInter.setItems(getResources().getStringArray(R.array.array_EtBaseTxInter)); //this is important, you must set it to set the item list
        mySpinnerEtBaseTxInter.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        mySpinnerEtBaseTxInter.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        mySpinnerEtBaseTxInter.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });
        /*Base EtBaseTxInter fin*/

        input_layout_EtBaseTxInter = (TextInputLayout) findViewById(R.id.input_layout_EtBaseTxInter);

        //START 06/11/2020
        EtIsPenalDeblocageRupAnt_SW = (Switch) findViewById(R.id.SwitchPenaliteDeblocageEnCasDeRuptureEAT); //39
        ll_EtNaturePenalDeblocage = (LinearLayout) findViewById(R.id.ll_EtNaturePenalDeblocage);
        tv_plageEtValTxMtPenaliteDeblocage = (TextView) findViewById(R.id.tv_plageEtValTxMtPenaliteDeblocage);

        rbEtNaturePenalDeblocageTaux = (RadioButton) findViewById(R.id.rbEtNaturePenalDeblocageTaux); //40_a
        rbEtNaturePenalDeblocageMontant = (RadioButton) findViewById(R.id.rbEtNaturePenalDeblocageMontant); //40_b
        rbEtNaturePenalDeblocagePlage = (RadioButton) findViewById(R.id.rbEtNaturePenalDeblocagePlage); //40_c
        EtValTxMtPenaliteDeblocage_ET = (EditText) findViewById(R.id.txtEtValTxMtPenaliteDeblocage); //41


        /*Base EtBaseTxMtPenalDeblocage_JR debut*/
        EtBaseTxMtPenalDeblocage_JR = (JRSpinner) findViewById(R.id.input_txt_EtBaseTxMtPenalDeblocage); //44
        input_layout_EtBaseTxPenalDeblocage = (TextInputLayout) findViewById(R.id.input_layout_EtBaseTxMtPenalDeblocage);


        EtBaseTxMtPenalDeblocage_JR.setItems(getResources().getStringArray(R.array.array_EtBaseTxMtPenalDeblocage)); //this is important, you must set it to set the item list
        EtBaseTxMtPenalDeblocage_JR.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        EtBaseTxMtPenalDeblocage_JR.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        EtBaseTxMtPenalDeblocage_JR.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });
        /*Base EpBaseTxMtPenalDeblocage_ET fin*/
        onRadioButtonClicked(rb_CtModRenouv_transfert_vers_eav);
        onRadioButtonClicked(rbEtNaturePenalDeblocageMontant);
        onSwitchButtonClicked(EtIsPenalDeblocageRupAnt_SW);
        //FIN 06/11/2020

        EtIsTVAOn = (SwitchCompat) findViewById(R.id.SwitchEtIsTVAOn);

        tv_plageEtValTxInter = (TextView) findViewById(R.id.tv_plage_EtValTxInter);
        //TIT
        tv_plageEtValTxInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux d'intérêt";
                    ListPlageTIT.IS_TO_CREATE_OR_TO_UPDATE = true;
//                    Intent i = new Intent(CreateProduitEAT.this,ListPlageDataTASActivity.class);
                    Intent i = new Intent(CreateProduitEAT.this, ListPlageTIT.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(CreateProduitEAT.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        tv_plage_EtValTxMtRupture = (TextView) findViewById(R.id.tv_plage_EtValTxMtRupture);
        //TPT
        tv_plage_EtValTxMtRupture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux de pénalité";
                    ListPlageTPT.IS_TO_CREATE_OR_TO_UPDATE = true;
                    Intent i = new Intent(CreateProduitEAT.this,ListPlageTPT.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(CreateProduitEAT.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        tv_plage_EtValTxIntPenal = (TextView) findViewById(R.id.tv_plage_EtValTxIntPenal);
//RAT
        tv_plage_EtValTxIntPenal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Nouveau Taux d'intérêt";
                    ListPlageRAT.IS_TO_CREATE_OR_TO_UPDATE = true;
                    Intent i = new Intent(CreateProduitEAT.this,ListPlageRAT.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(CreateProduitEAT.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        //DBT
        tv_plageEtValTxMtPenaliteDeblocage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux pén débloc";
                    ListPlageDBT.IS_TO_CREATE_OR_TO_UPDATE = true;
                    Intent i = new Intent(CreateProduitEAT.this,ListPlageDBT.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(CreateProduitEAT.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

        onRadioButtonClicked(rbEtNaturePasFixe);
        onRadioButtonClicked(rbEtTypTxInterFixe);
        onRadioButtonClicked(rbEtNatureRupAnTaux);
        onRadioButtonClicked(rbEtTypNewTxIntRupAntFixe);

        onSwitchButtonClicked(EtIsPenalRupAnt_SW);
        onSwitchButtonClicked(EtIsNewTxIntRupAnt_SW);

       deleteButton = (Button) findViewById(R.id.btn_delete_eat);
        deleteButton.setVisibility(View.GONE);
        addButton = (Button) findViewById(R.id.btn_save_eat);
        cancelButton = (Button) findViewById(R.id.btn_clean);
        //cirLoginButton
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
//                    Intent i = new Intent(CreateProduitEAT.this, PlageData.class);
//                    startActivityForResult(i,20);
                    finish();
                } else {
                    Toast.makeText(CreateProduitEAT.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addEAT();
                } else {
                    Toast.makeText(CreateProduitEAT.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

    }
    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Ajout d'un produit EAT");

    }


    public void onSwitchButtonClicked(View view) {
       /* boolean checked1 = ((Switch) view).isChecked();
        String str="";*/

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.SwitchPenaliteDeblocageEnCasDeRuptureEAT:
                if (EtIsPenalDeblocageRupAnt_SW.isChecked()) {
//                    str = checked1?"Pénalité en cas de non respect des échéances activée":"Pénalité en cas de non respect des échéances désactivée";
                    ll_EtNaturePenalDeblocage.setVisibility(View.VISIBLE);
//                    onRadioButtonClicked(rbEtNaturePenalDeblocageTaux);
//                    EtValTxMtPenaliteDeblocage_ET.setVisibility(View.VISIBLE);



                }else{
                    ll_EtNaturePenalDeblocage.setVisibility(View.GONE);
//                    EtValTxMtPenaliteDeblocage_ET.setVisibility(View.GONE);
                }

                break;

            case R.id.SwitchPenaliteEnCasDeRuptureEAT:
                if (EtIsPenalRupAnt_SW.isChecked()) {
                   // str = checked1?"Pénalité en cas de rupture anticipéé activée":"Pénalité en cas de rupture anticipéé désactivée";

                    LL_EtNatureRupAn.setVisibility(View.VISIBLE);

                    EtValTxMtRupture_ET.setVisibility(View.VISIBLE);
                    //ll_EtValTxMtRupture.setVisibility(View.VISIBLE);
                    EtBaseTxPenal_ET.setVisibility(View.VISIBLE);

                }else{
                    LL_EtNatureRupAn.setVisibility(View.GONE);

                    EtValTxMtRupture_ET.setVisibility(View.GONE);
                    EtBaseTxPenal_ET.setVisibility(View.GONE);
                }

                break;
            case R.id.SwitchDefinirNouveauTxInteretEAT:
                if (EtIsNewTxIntRupAnt_SW.isChecked()) {
                    //str = checked1?"Nouveau taux d'interêt activé":"Nouveau taux d'interêt désactivé";

                    LL_EtTypNewTxIntRupAnt.setVisibility(View.VISIBLE);

                    EtValTxIntPenal_ET.setVisibility(View.VISIBLE);
                    //ll_EtValTxMtRupture.setVisibility(View.VISIBLE);
                    EtBaseTxIntPenal_ET.setVisibility(View.VISIBLE);
                }else{
                    LL_EtTypNewTxIntRupAnt.setVisibility(View.GONE);


                    EtValTxIntPenal_ET.setVisibility(View.GONE);
                    EtBaseTxIntPenal_ET.setVisibility(View.GONE);
                    tv_plage_EtValTxIntPenal.setVisibility(View.GONE);
                }

                break;

        }
        //Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();

    }
    public void onRadioButtonClicked(View view) {
        boolean checked1 = ((RadioButton) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.rb_CtModRenouv_transfert_vers_eav:
                if (rb_CtModRenouv_transfert_vers_eav.isChecked()) {
                    EtActionDefATerme ="TCV";
                }
                break;
            case R.id.rb_CtModRenouv_renouveller:
                if (rb_CtModRenouv_renouveller.isChecked()) {
                    EtActionDefATerme ="RAE";
                }
                break;
            case R.id.rb_CtModRenouv_retirer:
                if (rb_CtModRenouv_retirer.isChecked()) {
                    EtActionDefATerme ="CTR";
                }
                break;
            case R.id.rb_CtModRenouv_ras:
                if (rb_CtModRenouv_ras.isChecked()) {
                    EtActionDefATerme ="RAS";
                }
                break;

            case R.id.rbEtNaturePenalDeblocageMontant:
                if (rbEtNaturePenalDeblocageMontant.isChecked()) {
                    EtNaturePenalDeblocage="F";
                    EtValTxMtPenaliteDeblocage_ET.setVisibility(View.VISIBLE);
//                    EpBaseTxMtPenal_ET.setVisibility(View.GONE);
                    input_layout_EtBaseTxPenalDeblocage.setVisibility(View.GONE);
                    tv_plageEtValTxMtPenaliteDeblocage.setVisibility(View.GONE);

                }/*else{
                    EpValTxMtPenalite_ET.setVisibility(View.GONE);
                }*/
                break;
            case R.id.rbEtNaturePenalDeblocageTaux:
                if (rbEtNaturePenalDeblocageTaux.isChecked()) {
                    EtNaturePenalDeblocage="T";
                    EtValTxMtPenaliteDeblocage_ET.setVisibility(View.VISIBLE);
//                    EpBaseTxMtPenal_ET.setVisibility(View.VISIBLE);
                    input_layout_EtBaseTxPenalDeblocage.setVisibility(View.VISIBLE);
                    tv_plageEtValTxMtPenaliteDeblocage.setVisibility(View.GONE);
                }/*else{
                    EpValTxMtPenalite_ET.setVisibility(View.GONE);
                    EpBaseTxMtPenal_ET.setVisibility(View.GONE);
                }*/
                break;
            case R.id.rbEtNaturePenalDeblocagePlage:
                if (rbEtNaturePenalDeblocagePlage.isChecked()) {
                    EtNaturePenalDeblocage="P";
                    EtValTxMtPenaliteDeblocage_ET.setVisibility(View.GONE);
//                    EpBaseTxMtPenal_ET.setVisibility(View.GONE);
                    input_layout_EtBaseTxPenalDeblocage.setVisibility(View.GONE);
                    tv_plageEtValTxMtPenaliteDeblocage.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.rb_EtNaturePasFixe:
                if (rbEtNaturePasFixe.isChecked()) {

                    EtNaturePas ="F";

                }

                break;
            case R.id.rb_EtNaturePasSaut:
                if (rbEtNaturePasSaut.isChecked()){
                    EtNaturePas ="S";

        }


                break;
            case R.id.rbEtTypTxInterFixe:
                if (rbEtTypTxInterFixe.isChecked()) {
                    EtTypTxInter ="F";
                    EtValTxInter_ET.setHint("Montant fixe");
                    EtValTxInter_ET.setVisibility(View.VISIBLE);
                    tv_plageEtValTxInter.setVisibility(View.GONE);
                    input_layout_EtBaseTxInter.setVisibility(View.GONE);
                }
                break;
            case R.id.rbEtTypTxInterTaux:
                if (rbEtTypTxInterTaux.isChecked()) {
                    EtTypTxInter ="T";
                    EtValTxInter_ET.setHint("Valeur du taux d'interêt");
                    EtValTxInter_ET.setVisibility(View.VISIBLE);
                    input_layout_EtBaseTxInter.setVisibility(View.VISIBLE);
                    tv_plageEtValTxInter.setVisibility(View.GONE);

                }
                break;
            case R.id.rbEtTypTxInterPlage:
                if (rbEtTypTxInterPlage.isChecked()) {
                    EtTypTxInter ="P";
                    tv_plageEtValTxInter.setVisibility(View.VISIBLE);

                    EtValTxInter_ET.setVisibility(View.GONE);
                    input_layout_EtBaseTxInter.setVisibility(View.GONE);


                }
                break;
            case R.id.rb_EtNatureRupAnTaux:
                if (rbEtNatureRupAnTaux.isChecked()) {
                    EtNatureRupAn="T";
                    EtValTxMtRupture_ET.setVisibility(View.VISIBLE);
                    EtBaseTxPenal_ET.setVisibility(View.VISIBLE);
                    tv_plage_EtValTxMtRupture.setVisibility(View.GONE);

                }
                break;
            case R.id.rb_EtNatureRupAnMontant:
                if (rbEtNatureRupAnMontant.isChecked()) {
                    EtNatureRupAn="M";
                    EtValTxMtRupture_ET.setVisibility(View.VISIBLE);
                    EtBaseTxPenal_ET.setVisibility(View.GONE);
                    tv_plage_EtValTxMtRupture.setVisibility(View.GONE);
                }
                break;
            case R.id.rb_EtNatureRupAnPlage:
                if (rbEtNatureRupAnPlage.isChecked()) {
                    EtNatureRupAn="P";

                    EtValTxMtRupture_ET.setVisibility(View.GONE);
                    EtBaseTxPenal_ET.setVisibility(View.GONE);
                    tv_plage_EtValTxMtRupture.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rbEtTypeNouveauTxInteretRuptureEATFixe:
                if (rbEtTypNewTxIntRupAntFixe.isChecked()) {
                    EtTypNewTxIntRupAnt = "F";
                    EtValTxIntPenal_ET.setVisibility(View.VISIBLE);
                    EtBaseTxIntPenal_ET.setVisibility(View.GONE);
                    tv_plage_EtValTxIntPenal.setVisibility(View.GONE);
                }
                break;
            case R.id.rbEtTypeNouveauTxInteretRuptureEATTaux:
                if (rbEtTypNewTxIntRupAntTaux.isChecked()) {
                    EtTypNewTxIntRupAnt = "T";
                    EtValTxIntPenal_ET.setVisibility(View.VISIBLE);
                    EtBaseTxIntPenal_ET.setVisibility(View.VISIBLE);
                    tv_plage_EtValTxIntPenal.setVisibility(View.GONE);
                }
                break;
            case R.id.rbEtTypeNouveauTxInteretRuptureEATPlage:
                if (rbEtTypNewTxIntRupAntPlage.isChecked()) {
                    EtTypNewTxIntRupAnt = "P";
                    EtValTxIntPenal_ET.setVisibility(View.GONE);
                    EtBaseTxIntPenal_ET.setVisibility(View.GONE);
                    tv_plage_EtValTxIntPenal.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rbEtTypeNouveauTxInteretRuptureEATPlageMois:
                if (rbEtTypNewTxIntRupAntPlageMois.isChecked()) {
                    EtTypNewTxIntRupAnt = "M";
                    EtValTxIntPenal_ET.setVisibility(View.GONE);
                    EtBaseTxIntPenal_ET.setVisibility(View.GONE);
                    tv_plage_EtValTxIntPenal.setVisibility(View.VISIBLE);
                }
                break;
        }
       // Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }



    /**
     * Checks whether all files are filled. If so then calls AddEATAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addEAT() {


if (!STRING_EMPTY.equals(EtCode_ET.getText().toString()) &&
        !STRING_EMPTY.equals(EtLibelleET.getText().toString()) &&
        !STRING_EMPTY.equals(EtMtMinMise_ET.getText().toString()) &&
        !STRING_EMPTY.equals(EtMtMaxMise_ET.getText().toString()) &&
        !STRING_EMPTY.equals(EtDureeMin_ET.getText().toString()) &&
        !STRING_EMPTY.equals(EtDureeMax_ET.getText().toString()) &&
        !STRING_EMPTY.equals(EtNbreUnitePas_ET.getText().toString())
){
            EtCode = MyData.normalizeSymbolsAndAccents(EtCode_ET.getText().toString());
            EtLibelle = MyData.normalizeSymbolsAndAccents(EtLibelleET.getText().toString());
            EtMtMinMise = EtMtMinMise_ET.getText().toString();
            EtMtMaxMise = EtMtMaxMise_ET.getText().toString();
            EtDureeMin = EtDureeMin_ET.getText().toString();
            EtDureeMax = EtDureeMax_ET.getText().toString();


   // EtNaturePas  = EtNaturePas.getText().toString();
    EtNbreUnitePas  =  EtNbreUnitePas_ET.getText().toString();
   // EtTypTxInter  = ;
    EtValTxInter  = EtValTxInter_ET.getText().toString();
    EtBaseTxInter  = ProduitEAT.encodeEtBaseTxInter(mySpinnerEtBaseTxInter.getText().toString());
//    EtPlageTxInterFrom  = EtPlageTxInterFrom_ET.getText().toString();
//    EtPlageTxInterTo  = EtPlageTxInterFrom_ET.getText().toString();
    if (EtIsTxIntNeg_SW.isChecked()){
        EtIsTxIntNeg = "Y";
    }else{
        EtIsTxIntNeg = "N";
    }
//    EtIsTxIntNeg   = EtIsTxIntNeg_SW.isChecked();
    if (EtIsPriseIntMiseOn_SW.isChecked()){
        EtIsPriseIntMiseOn = "Y";
    }else{
        EtIsPriseIntMiseOn = "N";
    }
//    EtIsPriseIntMiseOn  = EtIsPriseIntMiseOn_SW.isChecked();
    if (EtIsPenalRupAnt_SW.isChecked()){
        EtIsPenalRupAnt = "Y";
    }else{
        EtIsPenalRupAnt = "N";
    }
//    EtIsPenalRupAnt   = EtIsPenalRupAnt_SW.isChecked();
   // EtNatureRupAn   = ;
    EtValTxMtRupture  = EtValTxMtRupture_ET.getText().toString() ;
//    EtPlageTxMtRuptureFrom  = EtPlageTxMtRuptureFrom_ET.getText().toString();
//    EtPlageTxMtRuptureTo   = EtPlageTxMtRuptureTo_ET.getText().toString();
    EtBaseTxPenal   = ProduitEAT.encodeEtBaseTxPenal(EtBaseTxPenal_ET.getText().toString());
    if (EtIsEparRetireFin_SW.isChecked()){
        EtIsEparRetireFin = "Y";
    }else{
        EtIsEparRetireFin = "N";
    }
//    EtIsEparRetireFin  = EtIsEparRetireFin_SW.isChecked();
    if (EtIsEparTransfFin_SW.isChecked()){
        EtIsEparTransfFin = "Y";
    }else{
        EtIsEparTransfFin = "N";
    }
//    EtIsEparTransfFin  = EtIsEparTransfFin_SW.isChecked();
    if (EtIsOnlyTotTransf_SW.isChecked()){
        EtIsOnlyTotTransf = "Y";
    }else{
        EtIsOnlyTotTransf = "N";
    }
//    EtIsOnlyTotTransf  = EtIsOnlyTotTransf_SW.isChecked();
    if (EtIsEparRenouvFin_SW.isChecked()){
        EtIsEparRenouvFin = "Y";
    }else{
        EtIsEparRenouvFin = "N";
    }
//    EtIsEparRenouvFin  = EtIsEparRenouvFin_SW.isChecked();
    /*if (EtActionDefATerme_SW.isChecked()){
        EtActionDefATerme = "Y";
    }else{
        EtActionDefATerme = "N";
    }*/
//    EtActionDefATerme  = EtActionDefATerme_SW.isChecked();
    if (EtIsMultiEATOn_SW.isChecked()){
        EtIsMultiEATOn = "Y";
    }else{
        EtIsMultiEATOn = "N";
    }
//    EtIsMultiEATOn   = EtIsMultiEATOn_SW.isChecked();
    if (EtIsInterDusRupAnt_SW.isChecked()){
        EtIsInterDusRupAnt = "Y";
    }else{
        EtIsInterDusRupAnt = "N";
    }
//    EtIsInterDusRupAnt  = EtIsInterDusRupAnt_SW.isChecked();
    if (EtIsNewTxIntRupAnt_SW.isChecked()){
        EtIsNewTxIntRupAnt = "Y";
    }else{
        EtIsNewTxIntRupAnt = "N";
    }
//    EtIsNewTxIntRupAnt   = EtIsNewTxIntRupAnt_SW.isChecked();
   // EtTypNewTxIntRupAnt  = ;
    EtValTxIntPenal  = EtValTxIntPenal_ET.getText().toString();
//    EtPlageTxIntPenalFrom  = EtPlageTxIntPenalFrom_ET.getText().toString();
//    EtPlageTxIntPenalTo  = EtPlageTxIntPenalTo_ET.getText().toString();
    EtBaseTxIntPenal   = ProduitEAT.encodeEtBaseTxIntPenal(EtBaseTxIntPenal_ET.getText().toString());
    if (EtTxIntPenalNeg_SW.isChecked()){
        EtTxIntPenalNeg = "Y";
    }else{
        EtTxIntPenalNeg = "N";
    }

    if (EtIsPenalDeblocageRupAnt_SW.isChecked()){
        EtIsPenalDeblocageRupAnt = "Y";
    }else{
        EtIsPenalDeblocageRupAnt = "N";
    }
    EtValTxMtPenaliteDeblocage = EtValTxMtPenaliteDeblocage_ET.getText().toString();
    EtBaseTxMtPenalDeblocage   = ProduitEAT.encodeEtBaseTxMtPenalDeblocage(EtBaseTxMtPenalDeblocage_JR.getText().toString());

    //TVA
    if (EtIsTVAOn.isChecked()) {
        st_EtIsTVAOn = "Y";
    }else {
        st_EtIsTVAOn = "N";
    }

    //#TIT
    for (int i=0; i<plageDataListTIT.size();i++){
        tabPlageDebutTIT +=";"+plageDataListTIT.get(i).getPdValDe();
        tabPlageFinTIT +=";"+plageDataListTIT.get(i).getPdValA();
        tabPlageValeurTIT +=";"+plageDataListTIT.get(i).getPdValTaux();
        tabPlageBaseTIT +=";"+plageDataListTIT.get(i).getPdBase();
        tabPlageNatureTIT +=";"+plageDataListTIT.get(i).getPdNature();
    }
    //#TPT
    for (int i=0; i<plageDataListTPT.size();i++){
        tabPlageDebutTPT +=";"+plageDataListTPT.get(i).getPdValDe();
        tabPlageFinTPT +=";"+plageDataListTPT.get(i).getPdValA();
        tabPlageValeurTPT +=";"+plageDataListTPT.get(i).getPdValTaux();
        tabPlageBaseTPT +=";"+plageDataListTPT.get(i).getPdBase();
        tabPlageNatureTPT +=";"+plageDataListTPT.get(i).getPdNature();
    }
    //#RAT
    for (int i=0; i<plageDataListRAT.size();i++){
        tabPlageDebutRAT +=";"+plageDataListRAT.get(i).getPdValDe();
        tabPlageFinRAT +=";"+plageDataListRAT.get(i).getPdValA();
        tabPlageValeurRAT +=";"+plageDataListRAT.get(i).getPdValTaux();
        tabPlageBaseRAT +=";"+plageDataListRAT.get(i).getPdBase();
        tabPlageNatureRAT +=";"+plageDataListRAT.get(i).getPdNature();
    }
    //#DBT
    for (int i=0; i<plageDataListDBT.size();i++){
        tabPlageDebutDBT +=";"+plageDataListDBT.get(i).getPdValDe();
        tabPlageFinDBT +=";"+plageDataListDBT.get(i).getPdValA();
        tabPlageValeurDBT +=";"+plageDataListDBT.get(i).getPdValTaux();
        tabPlageBaseDBT +=";"+plageDataListDBT.get(i).getPdBase();
        tabPlageNatureDBT +=";"+plageDataListDBT.get(i).getPdNature();
    }

            new AddEATAsyncTask().execute();
        } else {
            Toast.makeText(CreateProduitEAT.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for adding a movie
     */
    private class AddEATAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(CreateProduitEAT.this);
            pDialog.setMessage("Adding EAT. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_EAT_CODE, EtCode);
            httpParams.put(KEY_EAT_LIBELLE, EtLibelle);
            httpParams.put(KEY_EAT_MT_MIN_MISE, EtMtMinMise);
            httpParams.put(KEY_EAT_MT_MAX_MISE, EtMtMaxMise);
            httpParams.put(KEY_EAT_DUREE_MIN, EtDureeMin);
            httpParams.put(KEY_EAT_DUREE_MAX, EtDureeMax);
            httpParams.put(KEY_EAT_NATURE_PAS, EtNaturePas);
            httpParams.put(KEY_EAT_NBRE_UNITE_PAS, EtNbreUnitePas);

            httpParams.put(KEY_EAT_TYPE_TX_INTER, EtTypTxInter);
            httpParams.put(KEY_EAT_VAL_TX_INTER, EtValTxInter);
            httpParams.put(KEY_EAT_BaseTxInter, EtBaseTxInter);

            httpParams.put(ET_IS_TX_INT_NEG, EtIsTxIntNeg);
            httpParams.put(ET_IS_PRISE_INT_MISE_ON, EtIsPriseIntMiseOn);
            httpParams.put(ET_IS_PENAL_RUP_ANT, EtIsPenalRupAnt);
            httpParams.put(ET_NATURE_RUP_AN, EtNatureRupAn);
            httpParams.put(ET_VAL_TX_MT_RUPTURE, EtValTxMtRupture);
            httpParams.put(ET_BASE_TX_PENAL, EtBaseTxPenal);

            httpParams.put(ET_IS_EPAR_RETIRE_FIN, EtIsEparRetireFin);
            httpParams.put(ET_IS_EPAR_TRANSF_FIN, EtIsEparTransfFin);
            httpParams.put(ET_IS_ONLY_TOT_TRANSF, EtIsOnlyTotTransf);
            httpParams.put(ET_IS_EPAR_RENOUV_FIN, EtIsEparRenouvFin);
            httpParams.put(ET_ACTION_DEF_A_TERME, EtActionDefATerme);
            httpParams.put(ET_IS_MULTI_EAT_ON, EtIsMultiEATOn);
            httpParams.put(ET_IS_INTER_DUS_RUP_ANT, EtIsInterDusRupAnt);
            httpParams.put(ET_IS_NEW_TX_INT_RUP_ANT, EtIsNewTxIntRupAnt);
            httpParams.put(ET_TYP_NEW_TX_INT_RUP_ANT, EtTypNewTxIntRupAnt);
            httpParams.put(ET_VAL_TX_INT_PENAL, EtValTxIntPenal);
            httpParams.put(ET_BASE_TX_INT_PENAL, EtBaseTxIntPenal);
            httpParams.put(ET_TX_INT_PENAL_NEG, EtTxIntPenalNeg);

            httpParams.put(KEY_EtIsPenalDeblocageRupAnt, EtIsPenalDeblocageRupAnt);
            httpParams.put(KEY_EtNaturePenalDeblocage, EtNaturePenalDeblocage);
            httpParams.put(KEY_EtValTxMtPenaliteDeblocage, EtValTxMtPenaliteDeblocage);
            httpParams.put(KEY_EtBaseTxMtPenalDeblocage, EtBaseTxMtPenalDeblocage);

            httpParams.put(ET_CAISSE_ID, String.valueOf(MyData.CAISSE_ID));
            //PLAGE
            //TIT

            httpParams.put(KEY_EAT_PLAGE_TX_INTER_DEBUT, tabPlageDebutTIT);
            httpParams.put(KEY_EAT_PLAGE_TX_INTER_FIN, tabPlageFinTIT);
            httpParams.put(KEY_EAT_PLAGE_TX_INTER_VALEUR, tabPlageValeurTIT);
            httpParams.put(KEY_EAT_PLAGE_TX_INTER_BASE, tabPlageBaseTIT);
            httpParams.put(KEY_EAT_PLAGE_TX_INTER_NATURE, tabPlageNatureTIT);


            //TPT
            httpParams.put(KEY_EAT_PLAGE_TPT_DEBUT, tabPlageDebutTPT);
            httpParams.put(KEY_EAT_PLAGE_TPT_FIN, tabPlageFinTPT);
            httpParams.put(KEY_EAT_PLAGE_TPT_VALEUR, tabPlageValeurTPT);
            httpParams.put(KEY_EAT_PLAGE_TPT_BASE, tabPlageBaseTPT);
            httpParams.put(KEY_EAT_PLAGE_TPT_NATURE, tabPlageNatureTPT);
            //RAT
            httpParams.put(KEY_EAT_PLAGE_RAT_DEBUT, tabPlageDebutRAT);
            httpParams.put(KEY_EAT_PLAGE_RAT_FIN, tabPlageFinRAT);
            httpParams.put(KEY_EAT_PLAGE_RAT_VALEUR, tabPlageValeurRAT);
            httpParams.put(KEY_EAT_PLAGE_RAT_BASE, tabPlageBaseRAT);
            httpParams.put(KEY_EAT_PLAGE_RAT_NATURE, tabPlageNatureRAT);
            //DBT 06/11/2020
            httpParams.put(KEY_EAT_PLAGE_DBT_DEBUT, tabPlageDebutDBT);
            httpParams.put(KEY_EAT_PLAGE_DBT_FIN, tabPlageFinDBT);
            httpParams.put(KEY_EAT_PLAGE_DBT_VALEUR, tabPlageValeurDBT);
            httpParams.put(KEY_EAT_PLAGE_DBT_BASE, tabPlageBaseDBT);
            httpParams.put(KEY_EAT_PLAGE_DBT_NATURE, tabPlageNatureDBT);


            //TVA
            httpParams.put(KEY_EtIsTVAOn, st_EtIsTVAOn);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_eat.php", "POST", httpParams);
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
                        Toast.makeText(CreateProduitEAT.this,
                                "EAT Ajouté", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CreateProduitEAT.this,
                                "Some error occurred while adding EAT",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}