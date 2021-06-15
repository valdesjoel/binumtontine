package com.example.binumtontine.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

public class UpdateEAT extends AppCompatActivity implements SERVER_ADDRESS {
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
    private RadioButton rbEtTypTxInterPlage;
    private EditText EtValTxInter_ET;

    private Switch EtIsTxIntNeg_SW;
    private Switch EtIsPriseIntMiseOn_SW;
    private Switch EtIsPenalRupAnt_SW;
    private RadioButton rbEtNatureRupAnTaux;
    private RadioButton rbEtNatureRupAnMontant;
    private RadioButton rbEtNatureRupAnPlage;
    private EditText EtValTxMtRupture_ET;
    private JRSpinner EtBaseTxPenal_ET;

    private Switch EtIsEparRetireFin_SW;
    private Switch EtIsEparTransfFin_SW;
    private Switch EtIsOnlyTotTransf_SW;
    private Switch EtIsEparRenouvFin_SW;

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

    private RadioButton rb_CtModRenouv_transfert_vers_eav;
    private RadioButton rb_CtModRenouv_renouveller;
    private RadioButton rb_CtModRenouv_retirer;
    private RadioButton rb_CtModRenouv_ras;
    private String EtIsMultiEATOn ;
    private String EtIsInterDusRupAnt;
    private String EtIsNewTxIntRupAnt ;
    private String EtTypNewTxIntRupAnt;
    private String EtValTxIntPenal;
//    private String EtPlageTxIntPenalFrom;
//    private String EtPlageTxIntPenalTo;
    private String EtBaseTxIntPenal ;
    private String EtTxIntPenalNeg ;



    private LinearLayout LL_EtNatureRupAn;
    private LinearLayout LL_EtTypNewTxIntRupAnt;



    private Button addButton;
    private Button deleteButton;
    private Button cancelButton;
    private int success;
    private ProgressDialog pDialog;
//    private Button remove_button;
//    private Button remove_button_EtValTxMtRupture;
//    private Button remove_button_EtTypNewTxIntRupAnt;
    private String tabPlageDebut ="";
    private String tabPlageFin ="";
    private String tabPlageValeur ="";




    public static String eatId;
    private static final String KEY_EAT_ID = "EtNumero";
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private TextView headerEATTextView;
    private TextView tv_header_produit;



    /*START*/
    private RadioButton rbEtTypTxInterTaux;

    private TextInputLayout input_layout_EtBaseTxInter;
    private JRSpinner mySpinnerEtBaseTxInter; //pour gérer le spinner contenant les bases du Tx Int avance spéciale


    private TextView tv_plageEtValTxInter;
    private TextView tv_plage_EtValTxMtRupture;
    private TextView tv_plage_EtValTxIntPenal;
    public static ArrayList<ModelPlageData> plageDataListTIT = new ArrayList<ModelPlageData>(); //to manage plageData

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


    /*END*/

    //START 06/11/2020

    //#DBT
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

        Intent intent = getIntent();
        headerEATTextView = (TextView) findViewById(R.id.header_eat);
        headerEATTextView.setText("Mise à jour EAT");

        tv_header_produit = (TextView) findViewById(R.id.header_produit);
        tv_header_produit.setText("Produit EAT\n"+"Caisse: "+ MyData.CAISSE_NAME);

        /*begin*/

        LL_EtNatureRupAn = (LinearLayout)findViewById(R.id.ll_EtNatureRupAn);
        LL_EtTypNewTxIntRupAnt = (LinearLayout)findViewById(R.id.ll_EtTypNewTxIntRupAnt);

        EtCode_ET = (EditText) findViewById(R.id.input_txt_Code_EAT);
        EtCode_ET.setEnabled(false);
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


        /*Base EtBaseTxInter debut*/
        mySpinnerEtBaseTxInter = (JRSpinner)findViewById(R.id.spn_my_spinner_base_EtBaseTxInter);
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
                    ListPlageTIT.IS_TO_CREATE_OR_TO_UPDATE = false;
//                    Intent i = new Intent(CreateProduitEAT.this,ListPlageDataTASActivity.class);
                    Intent i = new Intent(UpdateEAT.this, ListPlageTIT.class);
                    i.putExtra(KEY_EAT_ID, eatId);
                    startActivityForResult(i,20);
                } else {
                    Toast.makeText(UpdateEAT.this,
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
                    ListPlageTPT.IS_TO_CREATE_OR_TO_UPDATE = false;
                    Intent i = new Intent(UpdateEAT.this,ListPlageTPT.class);
                    i.putExtra(KEY_EAT_ID, eatId);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(UpdateEAT.this,
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
                    ListPlageRAT.IS_TO_CREATE_OR_TO_UPDATE = false;
                    Intent i = new Intent(UpdateEAT.this,ListPlageRAT.class);
                    i.putExtra(KEY_EAT_ID, eatId);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(UpdateEAT.this,
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
                    ListPlageDBT.IS_TO_CREATE_OR_TO_UPDATE = false;
                    Intent i = new Intent(UpdateEAT.this,ListPlageDBT.class);
                    i.putExtra(KEY_EAT_ID, eatId);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(UpdateEAT.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        /*end*/

        eatId = intent.getStringExtra(KEY_EAT_ID);
        new FetchEatDetailsAsyncTask().execute();
        deleteButton = (Button) findViewById(R.id.btn_delete_eat);
        deleteButton.setVisibility(View.VISIBLE);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete();
            }
        });
        addButton = (Button) findViewById(R.id.btn_save_eat);
        cancelButton = (Button) findViewById(R.id.btn_clean);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();

                } else {
                    Toast.makeText(UpdateEAT.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    updateEAT();

                } else {
                    Toast.makeText(UpdateEAT.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });


    }
    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Mise à jour d'un produit EAV");

    }

    public void onSwitchButtonClicked(View view) {
        //boolean checked1 = ((Switch) view).isChecked();
        String str="";

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
                   // str = checked1?"Nouveau taux d'interêt activé":"Nouveau taux d'interêt désactivé";

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
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();

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
     * Fetches single movie details from the server
     */
    private class FetchEatDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateEAT.this);
            pDialog.setMessage("Loading EAT Details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_EAT_ID, eatId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_eat_details.php", "GET", httpParams);
            try {
               /* int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject eav;
                if (success == 1) {
                    //Parse the JSON response
                    eav = jsonObject.getJSONObject(KEY_DATA);
*/

                    EtCode = MyData.normalizeSymbolsAndAccents(jsonObject.getString(KEY_EAT_CODE));
                    EtLibelle = MyData.normalizeSymbolsAndAccents( jsonObject.getString(KEY_EAT_LIBELLE));
                    EtMtMinMise = jsonObject.getString(KEY_EAT_MT_MIN_MISE);
                    EtMtMaxMise = jsonObject.getString(KEY_EAT_MT_MAX_MISE);
                    EtDureeMin = jsonObject.getString(KEY_EAT_DUREE_MIN);
                    EtDureeMax = jsonObject.getString(KEY_EAT_DUREE_MAX);
                    EtNaturePas = jsonObject.getString(KEY_EAT_NATURE_PAS);

//                    EtNaturePas  = EtNaturePas.getText().toString();
                    EtNbreUnitePas = jsonObject.getString(KEY_EAT_NBRE_UNITE_PAS);
                    EtTypTxInter = jsonObject.getString(KEY_EAT_TYPE_TX_INTER);
                    EtValTxInter = jsonObject.getString(KEY_EAT_VAL_TX_INTER);
                    EtBaseTxInter = ProduitEAT.decodeEtBaseTxInter(jsonObject.getString(KEY_EAT_BaseTxInter));


                    EtIsTxIntNeg = jsonObject.getString(ET_IS_TX_INT_NEG);
                    EtIsPriseIntMiseOn = jsonObject.getString(ET_IS_PRISE_INT_MISE_ON);

                    EtIsPenalRupAnt = jsonObject.getString(ET_IS_PENAL_RUP_ANT);

                    // EtNatureRupAn
                    EtNatureRupAn = jsonObject.getString(ET_NATURE_RUP_AN);


                    EtValTxMtRupture = jsonObject.getString(ET_VAL_TX_MT_RUPTURE);
//                    EtPlageTxMtRuptureFrom = jsonObject.getString(ET_PLAGE_TX_MT_RUPTURE_FROM);
//                    EtPlageTxMtRuptureTo = jsonObject.getString(ET_PLAGE_TX_MT_RUPTURE_TO);
                    EtBaseTxPenal = ProduitEAT.decodeEtBaseTxPenal(jsonObject.getString(ET_BASE_TX_PENAL));
                    EtIsEparRetireFin = jsonObject.getString(ET_IS_EPAR_RETIRE_FIN);
                    EtIsEparTransfFin = jsonObject.getString(ET_IS_EPAR_TRANSF_FIN);
                    EtIsOnlyTotTransf = jsonObject.getString(ET_IS_ONLY_TOT_TRANSF);
                    EtIsEparRenouvFin = jsonObject.getString(ET_IS_EPAR_RENOUV_FIN);
                    EtActionDefATerme = jsonObject.getString(ET_ACTION_DEF_A_TERME);
                    EtIsMultiEATOn = jsonObject.getString(ET_IS_MULTI_EAT_ON);
                    EtIsInterDusRupAnt = jsonObject.getString(ET_IS_INTER_DUS_RUP_ANT);
                    EtIsNewTxIntRupAnt = jsonObject.getString(ET_IS_NEW_TX_INT_RUP_ANT);

                    // EtTypNewTxIntRupAnt  = ;
                    EtTypNewTxIntRupAnt = jsonObject.getString(ET_TYP_NEW_TX_INT_RUP_ANT);


                    EtValTxIntPenal = jsonObject.getString(ET_VAL_TX_INT_PENAL);
//                    EtPlageTxIntPenalFrom = jsonObject.getString(ET_PLAGE_TX_INT_PENAL_FROM);
//                    EtPlageTxIntPenalTo = jsonObject.getString(ET_PLAGE_TX_INT_PENAL_TO);
                    EtBaseTxIntPenal = ProduitEAT.decodeEtBaseTxIntPenal(jsonObject.getString(ET_BASE_TX_INT_PENAL));
                    EtTxIntPenalNeg =jsonObject.getString(ET_TX_INT_PENAL_NEG);

                EtIsPenalDeblocageRupAnt =jsonObject.getString(KEY_EtIsPenalDeblocageRupAnt);
                EtNaturePenalDeblocage =jsonObject.getString(KEY_EtNaturePenalDeblocage);
                EtValTxMtPenaliteDeblocage =jsonObject.getString(KEY_EtValTxMtPenaliteDeblocage);
                EtBaseTxMtPenalDeblocage = ProduitEAT.decodeEtBaseTxMtPenalDeblocage(jsonObject.getString(KEY_EtBaseTxMtPenalDeblocage));
                st_EtIsTVAOn = jsonObject.getString(KEY_EtIsTVAOn);

                //}
            } catch (Exception e) {
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
                        EtCode_ET.setText(EtCode);
                        EtLibelleET.setText(EtLibelle);
                        EtMtMinMise_ET.setText(EtMtMinMise);
                        EtMtMaxMise_ET.setText(EtMtMaxMise);
                        EtDureeMin_ET.setText(EtDureeMin);
                        EtDureeMax_ET.setText(EtDureeMax);
                        if (EtNaturePas.equals("F")){
                            rbEtNaturePasFixe.setChecked(true);
                            onRadioButtonClicked(rbEtNaturePasFixe);
                        }else if (EtNaturePas.equals("S")){

                            rbEtNaturePasSaut.setChecked(true);
                            onRadioButtonClicked(rbEtNaturePasSaut);
                        }

                        // EtNaturePas  = EtNaturePas.getText().toString();
                        EtNbreUnitePas_ET.setText(EtNbreUnitePas);
                        // EtTypTxInter  = ;
                        if (EtTypTxInter.equals("F")){
                            rbEtTypTxInterFixe.setChecked(true);
                            onRadioButtonClicked(rbEtTypTxInterFixe);
                        }else if (EtTypTxInter.equals("T")){

                            rbEtTypTxInterTaux.setChecked(true);
                            onRadioButtonClicked(rbEtTypTxInterTaux);
                        }else if (EtTypTxInter.equals("P")){

                            rbEtTypTxInterPlage.setChecked(true);
                            onRadioButtonClicked(rbEtTypTxInterPlage);
                        }
                        EtValTxInter_ET.setText(EtValTxInter) ;
                        mySpinnerEtBaseTxInter.setText(EtBaseTxInter) ;

                        if (EtIsTxIntNeg.equals("Y")){
                            EtIsTxIntNeg_SW.setChecked(true);
                        }else{
                            EtIsTxIntNeg_SW.setChecked(false);
                        }
//                    EtIsTxIntNeg_SW.setChecked(EtIsTxIntNeg);

                        if (EtIsPriseIntMiseOn.equals("Y")){
                            EtIsPriseIntMiseOn_SW.setChecked(true);
                        }else{
                            EtIsPriseIntMiseOn_SW.setChecked(false);
                        }
//                    EtIsPriseIntMiseOn_SW.setChecked(EtIsPriseIntMiseOn);

                        if (EtNatureRupAn.equals("M")){
                            rbEtNatureRupAnMontant.setChecked(true);
                            onRadioButtonClicked(rbEtNatureRupAnMontant);
                        }else if (EtNatureRupAn.equals("T")){

                            rbEtNatureRupAnTaux.setChecked(true);
                            onRadioButtonClicked(rbEtNatureRupAnTaux);
                        }else if (EtNatureRupAn.equals("P")){

                            rbEtNatureRupAnPlage.setChecked(true);
                            onRadioButtonClicked(rbEtNatureRupAnPlage);
                        }
                        if (EtIsPenalRupAnt.equals("Y")){
                            EtIsPenalRupAnt_SW.setChecked(true);
                        }else{
                            EtIsPenalRupAnt_SW.setChecked(false);
                        }
                        onSwitchButtonClicked(EtIsPenalRupAnt_SW);

                        // EtNatureRupAn   = ;
                        EtValTxMtRupture_ET.setText(EtValTxMtRupture) ;
                        EtBaseTxPenal_ET.setText(EtBaseTxPenal);

                        if (EtIsEparRetireFin.equals("Y")){
                            EtIsEparRetireFin_SW.setChecked(true);
                        }else{
                            EtIsEparRetireFin_SW.setChecked(false);
                        }

                        if (EtIsEparTransfFin.equals("Y")){
                            EtIsEparTransfFin_SW.setChecked(true);
                        }else{
                            EtIsEparTransfFin_SW.setChecked(false);
                        }
                        if (EtIsOnlyTotTransf.equals("Y")){
                            EtIsOnlyTotTransf_SW.setChecked(true);
                        }else{
                            EtIsOnlyTotTransf_SW.setChecked(false);
                        }
//                    EtIsOnlyTotTransf_SW.setChecked(EtIsOnlyTotTransf);
                        if (EtIsEparRenouvFin.equals("Y")){
                            EtIsEparRenouvFin_SW.setChecked(true);
                        }else{
                            EtIsEparRenouvFin_SW.setChecked(false);
                        }
                        if (EtActionDefATerme.equals("TCV")) {
                            rb_CtModRenouv_transfert_vers_eav.setChecked(true);
                        } else if (EtActionDefATerme.equals("RAE")) {
                            rb_CtModRenouv_renouveller.setChecked(true);
                        } else if (EtActionDefATerme.equals("CTR")) {
                            rb_CtModRenouv_retirer.setChecked(true);
                        } else if (EtActionDefATerme.equals("RAS")) {
                            rb_CtModRenouv_ras.setChecked(true);
                        }
                        if (EtIsMultiEATOn.equals("Y")){
                            EtIsMultiEATOn_SW.setChecked(true);
                        }else{
                            EtIsMultiEATOn_SW.setChecked(false);
                        }

                        if (EtIsInterDusRupAnt.equals("Y")){
                            EtIsInterDusRupAnt_SW.setChecked(true);
                        }else{
                            EtIsInterDusRupAnt_SW.setChecked(false);
                        }
                        // EtTypNewTxIntRupAnt  = ;
                        if (EtTypNewTxIntRupAnt.equals("F")){
                            rbEtTypNewTxIntRupAntFixe.setChecked(true);
                            onRadioButtonClicked(rbEtTypNewTxIntRupAntFixe);
                        }else if (EtTypNewTxIntRupAnt.equals("T")){

                            rbEtTypNewTxIntRupAntTaux.setChecked(true);
                            onRadioButtonClicked(rbEtTypNewTxIntRupAntTaux);
                        }else if (EtTypNewTxIntRupAnt.equals("P")){

                            rbEtTypNewTxIntRupAntPlage.setChecked(true);
                            onRadioButtonClicked(rbEtTypNewTxIntRupAntPlage);
                        }else if (EtTypNewTxIntRupAnt.equals("M")){
                            rbEtTypNewTxIntRupAntPlageMois.setChecked(true);
                            onRadioButtonClicked(rbEtTypNewTxIntRupAntPlageMois);
                        }
                        if (EtIsNewTxIntRupAnt.equals("Y")){
                            EtIsNewTxIntRupAnt_SW.setChecked(true);
                        }else{
                            EtIsNewTxIntRupAnt_SW.setChecked(false);
                        }
                        onSwitchButtonClicked(EtIsNewTxIntRupAnt_SW);
                        EtValTxIntPenal_ET.setText(EtValTxIntPenal);

                        EtBaseTxIntPenal_ET.setText(EtBaseTxIntPenal);
                        if (EtTxIntPenalNeg.equals("Y")){
                            EtTxIntPenalNeg_SW.setChecked(true);
                        }else{
                            EtTxIntPenalNeg_SW.setChecked(false);
                        }
//DBT

                        if (EtNaturePenalDeblocage.equals("F")){
                            rbEtNaturePenalDeblocageMontant.setChecked(true);
                            onRadioButtonClicked(rbEtNaturePenalDeblocageMontant);
                        }else if (EtNaturePenalDeblocage.equals("T")){

                            rbEtNaturePenalDeblocageTaux.setChecked(true);
                            onRadioButtonClicked(rbEtNaturePenalDeblocageTaux);
                        }else if (EtNaturePenalDeblocage.equals("P")){

                            rbEtNaturePenalDeblocagePlage.setChecked(true);
                            onRadioButtonClicked(rbEtNaturePenalDeblocagePlage);
                        }

                        if (EtIsPenalDeblocageRupAnt.equals("Y")){
                            EtIsPenalDeblocageRupAnt_SW.setChecked(true);
                        }else{
                            EtIsPenalDeblocageRupAnt_SW.setChecked(false);
                        }
                        onSwitchButtonClicked(EtIsPenalDeblocageRupAnt_SW);
                        EtValTxMtPenaliteDeblocage_ET.setText(EtValTxMtPenaliteDeblocage);

                        EtBaseTxMtPenalDeblocage_JR.setText(EtBaseTxMtPenalDeblocage);

                        //TVA
                        if (st_EtIsTVAOn.equals("Y")){
                            EtIsTVAOn.setChecked(true);
                        }else {
                            EtIsTVAOn.setChecked(false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        }


    }

    /**
     * Displays an alert dialogue to confirm the deletion
     */
    private void confirmDelete() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                UpdateEAT.this);
        alertDialogBuilder.setMessage("Voulez-vous vraiment supprimer cet EAT ?");
        alertDialogBuilder.setPositiveButton("Supprimer",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                            //If the user confirms deletion, execute DeleteMovieAsyncTask
                            new UpdateEAT.DeleteMovieAsyncTask().execute();
                        } else {
                            Toast.makeText(UpdateEAT.this,
                                    "Unable to connect to internet",
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                });

        alertDialogBuilder.setNegativeButton("Cancel", null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * AsyncTask to delete a movie
     */
    private class DeleteMovieAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateEAT.this);
            pDialog.setMessage("Deleting EAT. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Set movie_id parameter in request
            httpParams.put(KEY_EAT_ID, eatId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "delete_eat.php", "POST", httpParams);
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
                        Toast.makeText(UpdateEAT.this,
                                "EAT Deleted", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie deletion
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(UpdateEAT.this,
                                "Some error occurred while deleting EAT",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    /**
     * Checks whether all files are filled. If so then calls UpdateEatAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void updateEAT() {


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
//            EtPlageTxMtRuptureFrom  = EtPlageTxMtRuptureFrom_ET.getText().toString();
//            EtPlageTxMtRuptureTo   = EtPlageTxMtRuptureTo_ET.getText().toString();
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
        /*    if (EtActionDefATerme_SW.isChecked()){
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
//            EtPlageTxIntPenalFrom  = EtPlageTxIntPenalFrom_ET.getText().toString();
//            EtPlageTxIntPenalTo  = EtPlageTxIntPenalTo_ET.getText().toString();
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

            new UpdateEatAsyncTask().execute();
        } else {
            Toast.makeText(UpdateEAT.this,
                    "One or more fields left empty!",
                    Toast.LENGTH_LONG).show();

        }


    }
    /**
     * AsyncTask for updating a movie details
     */

    private class UpdateEatAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateEAT.this);
            pDialog.setMessage("Updating EAT. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_EAT_ID, eatId);
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
            httpParams.put(KEY_EAT_PLAGE_TX_INTER_DEBUT, tabPlageDebut);
            httpParams.put(KEY_EAT_PLAGE_TX_INTER_FIN, tabPlageFin);
            httpParams.put(KEY_EAT_PLAGE_TX_INTER_VALEUR, tabPlageValeur);
            httpParams.put(ET_IS_TX_INT_NEG, EtIsTxIntNeg.toString());
            httpParams.put(ET_IS_PRISE_INT_MISE_ON, EtIsPriseIntMiseOn.toString());
            httpParams.put(ET_IS_PENAL_RUP_ANT, EtIsPenalRupAnt.toString());
            httpParams.put(ET_NATURE_RUP_AN, EtNatureRupAn);
            httpParams.put(ET_VAL_TX_MT_RUPTURE, EtValTxMtRupture);
            httpParams.put(ET_BASE_TX_PENAL, EtBaseTxPenal);

            httpParams.put(ET_IS_EPAR_RETIRE_FIN, EtIsEparRetireFin.toString());
            httpParams.put(ET_IS_EPAR_TRANSF_FIN, EtIsEparTransfFin.toString());
            httpParams.put(ET_IS_ONLY_TOT_TRANSF, EtIsOnlyTotTransf.toString());
            httpParams.put(ET_IS_EPAR_RENOUV_FIN, EtIsEparRenouvFin);
            httpParams.put(ET_ACTION_DEF_A_TERME, EtActionDefATerme);
            httpParams.put(ET_IS_MULTI_EAT_ON, EtIsMultiEATOn);
            httpParams.put(ET_IS_INTER_DUS_RUP_ANT, EtIsInterDusRupAnt);
            httpParams.put(ET_IS_NEW_TX_INT_RUP_ANT, EtIsNewTxIntRupAnt);
            httpParams.put(ET_TYP_NEW_TX_INT_RUP_ANT, EtTypNewTxIntRupAnt);
            httpParams.put(ET_VAL_TX_INT_PENAL, EtValTxIntPenal);
            httpParams.put(ET_BASE_TX_INT_PENAL, EtBaseTxIntPenal);
            httpParams.put(ET_TX_INT_PENAL_NEG, EtTxIntPenalNeg);
            httpParams.put(ET_CAISSE_ID, String.valueOf(MyData.CAISSE_ID));



            httpParams.put(KEY_EtIsPenalDeblocageRupAnt, EtIsPenalDeblocageRupAnt);
            httpParams.put(KEY_EtNaturePenalDeblocage, EtNaturePenalDeblocage);
            httpParams.put(KEY_EtValTxMtPenaliteDeblocage, EtValTxMtPenaliteDeblocage);
            httpParams.put(KEY_EtBaseTxMtPenalDeblocage, EtBaseTxMtPenalDeblocage);

            //TVA
            httpParams.put(KEY_EtIsTVAOn, st_EtIsTVAOn);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "update_eat.php", "POST", httpParams);
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
                        Toast.makeText(UpdateEAT.this,
                                "EAT Updated", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(UpdateEAT.this,
                                "Some error occurred while updating",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}
