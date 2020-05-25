
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

import com.example.binumtontine.JRSpinner;
import com.example.binumtontine.R;
import com.example.binumtontine.activity.adherent.ModelPlageData;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static final String KEY_EAT_PLAGE_TX_INTER_FROM = "EtPlageTxInterFrom";
    private static final String KEY_EAT_PLAGE_TX_INTER_TO = "EtPlageTxInterTo";
//    private static final String KEY_EAT_PLAGE_TX_INTER_DEBUT = "EtTitDebut";
//    private static final String KEY_EAT_PLAGE_TX_INTER_FIN = "EtTitFin";
//    private static final String KEY_EAT_PLAGE_TX_INTER_VALEUR = "EtTitValeur";
    private static final String ET_IS_TX_INT_NEG = "EtIsTxIntNeg";
    private static final String ET_IS_PRISE_INT_MISE_ON = "EtIsPriseIntMiseOn";
    private static final String ET_IS_PENAL_RUP_ANT = "EtIsPenalRupAnt";
    private static final String ET_NATURE_RUP_AN = "EtNatureRupAn";
    private static final String ET_VAL_TX_MT_RUPTURE = "EtValTxMtRupture";
    private static final String ET_PLAGE_TX_MT_RUPTURE_FROM = "EtPlageTxMtRuptureFrom";
    private static final String ET_PLAGE_TX_MT_RUPTURE_TO = "EtPlageTxMtRuptureTo";
    private static final String ET_BASE_TX_PENAL = "EtBaseTxPenal";


    private static final String KEY_EAT_PLAGE_TX_MT_RUPTURE_DEBUT = "EtTptDebut";
    private static final String KEY_EAT_PLAGE_TX_MT_RUPTURE_FIN = "EtTptFin";
    private static final String KEY_EAT_PLAGE_TX_MT_RUPTURE_VALEUR = "EtTptValeur";

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
    private static final String ET_PLAGE_TX_INT_PENAL_FROM = "EtPlageTxIntPenalFrom";
    private static final String ET_PLAGE_TX_INT_PENAL_TO = "EtPlageTxIntPenalTo";
    private static final String ET_BASE_TX_INT_PENAL = "EtBaseTxIntPenal";


    private static final String KEY_EAT_PLAGE_TX_INT_PENAL_DEBUT = "EtRatDebut";
    private static final String KEY_EAT_PLAGE_TX_INT_PENAL_FIN = "EtRatFin";
    private static final String KEY_EAT_PLAGE_TX_INT_PENAL_VALEUR = "EtRatValeur";

    private static final String ET_TX_INT_PENAL_NEG = "EtTxIntPenalNeg";
    private static final String ET_CAISSE_ID = "EtCaisseId";
    //private static final String ET_GUICHET_ID = "EtGuichetId";


    private static String STRING_EMPTY = "";

    private LinearLayout ll;
    private LinearLayout ll_btn;
    private LinearLayout ll_EtValTxMtRupture;
    private LinearLayout ll_btn_EtValTxMtRupture;
    private LinearLayout ll_EtValTxIntPenal;
    private LinearLayout ll_btn_EtValTxIntPenal;
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
    private EditText EtPlageTxInterFrom_ET;
    private EditText EtPlageTxInterTo_ET;
    private EditText EtPlageTxInterValeur_ET;

    private Switch EtIsTxIntNeg_SW;
    private Switch EtIsPriseIntMiseOn_SW;
    private Switch EtIsPenalRupAnt_SW;
    private RadioButton rbEtNatureRupAnTaux;
    private RadioButton rbEtNatureRupAnMontant;
    private RadioButton rbEtNatureRupAnPlage;
    private EditText EtValTxMtRupture_ET;
    private EditText EtPlageTxMtRuptureFrom_ET;
    private EditText EtPlageTxMtRuptureTo_ET;
    private EditText EtPlageTxMtRuptureValeur_ET;
    private EditText EtBaseTxPenal_ET;

    private Switch EtIsEparRetireFin_SW;
    private Switch EtIsEparTransfFin_SW;
    private Switch EtIsOnlyTotTransf_SW;
    private Switch EtIsEparRenouvFin_SW;
    private Switch EtActionDefATerme_SW;
    private Switch EtIsMultiEATOn_SW;
    private Switch EtIsInterDusRupAnt_SW;
    private Switch EtIsNewTxIntRupAnt_SW;
    private RadioButton rbEtTypNewTxIntRupAntFixe;
    private RadioButton rbEtTypNewTxIntRupAntPlage;
    private EditText EtValTxIntPenal_ET;
    private EditText EtPlageTxIntPenalFrom_ET;
    private EditText EtPlageTxIntPenalTo_ET;
    private EditText EtPlageTxIntPenalValeur_ET;
    private EditText EtBaseTxIntPenal_ET;
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
    private String EtPlageTxInterFrom;
    private String EtPlageTxInterTo;
    private Boolean EtIsTxIntNeg ;
    private Boolean EtIsPriseIntMiseOn;
    private Boolean EtIsPenalRupAnt ;
    private String EtNatureRupAn ;
    private String EtValTxMtRupture;
    private String EtPlageTxMtRuptureFrom;
    private String EtPlageTxMtRuptureTo ;
    private String EtBaseTxPenal ;
    private Boolean EtIsEparRetireFin;
    private Boolean EtIsEparTransfFin;
    private Boolean EtIsOnlyTotTransf;
    private Boolean EtIsEparRenouvFin;
    private Boolean EtActionDefATerme;
    private Boolean EtIsMultiEATOn ;
    private Boolean EtIsInterDusRupAnt;
    private Boolean EtIsNewTxIntRupAnt ;
    private String EtTypNewTxIntRupAnt;
    private String EtValTxIntPenal;
    private String EtPlageTxIntPenalFrom;
    private String EtPlageTxIntPenalTo;
    private String EtBaseTxIntPenal ;
    private Boolean EtTxIntPenalNeg ;



    private LinearLayout blkPlageEav;
    private LinearLayout LL_EtNatureRupAn;
    private LinearLayout LL_EtTypNewTxIntRupAnt;


    private RadioButton rbEpTypTxInterPlage;
    private TextInputLayout layout_TauxAPreleveCpteEAV;
    private TextInputLayout input_layout_EtBaseTxInter;
    private JRSpinner mySpinnerEtBaseTxInter; //pour gérer le spinner contenant les bases du Tx Int avance spéciale

    private Button addButton;
    private Button deleteButton;
    private Button cancelButton;
    private int success;
    private ProgressDialog pDialog;

    public int numberOfLinesDebut = 0;
    public int numberOfLinesDebut_EtValTxMtRupture = 0;
    public int numberOfLinesDebut_EtTypNewTxIntRupAnt = 0;
    private List<String> tabPlageDebutList;
    private List<String> tabPlageDebutList1;
    private List<String> tabPlageDebutList2;
    private List<String> tabPlageFinList;
    private List<String> tabPlageFinList1;
    private List<String> tabPlageFinList2;
    private List<String> tabPlageValeurList;
    private List<String> tabPlageValeurList1;
    private List<String> tabPlageValeurList2;
    public int numberOfLinesFin = 0;
    public int numberOfLinesFin_EtValTxMtRupture = 0;
    public int numberOfLinesFin_EtTypNewTxIntRupAnt = 0;
    public int numberOfLinesValeur = 0;
    public int numberOfLinesValeur_EtValTxMtRupture = 0;
    public int numberOfLinesValeur_EtTypNewTxIntRupAnt = 0;
    private Button remove_button;
    private Button remove_button_EtValTxMtRupture;
    private Button remove_button_EtTypNewTxIntRupAnt;
    private String tabPlageDebut ="";
    private String tabPlageFin ="";
    private String tabPlageValeur ="";
    private String tabPlageDebut1 ="";
    private String tabPlageFin1 ="";
    private String tabPlageValeur1 ="";
    private String tabPlageDebut2 ="";
    private String tabPlageFin2 ="";
    private String tabPlageValeur2 ="";

    private TextView tv_header_produit;
    private TextView tv_plageEtValTxInter;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_add_movie);
      //  setContentView(R.layout.fragment_param_produit_eav);
        setContentView(R.layout.activity_eat);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_produit_eav);
        setSupportActionBar(toolbar);
        setToolbarTitle(); */

        tv_header_produit = (TextView) findViewById(R.id.header_produit);
        tv_header_produit.setText("Produit EAT\n"+"Caisse: "+ MyData.CAISSE_NAME);

        tabPlageDebutList = new ArrayList<>();
        tabPlageFinList = new ArrayList<>();
        tabPlageValeurList = new ArrayList<>();
        tabPlageDebutList1 = new ArrayList<>();
        tabPlageFinList1 = new ArrayList<>();
        tabPlageValeurList1 = new ArrayList<>();
        tabPlageDebutList2 = new ArrayList<>();
        tabPlageFinList2 = new ArrayList<>();
        tabPlageValeurList2 = new ArrayList<>();
        ll = (LinearLayout)findViewById(R.id.blk_EtPlageTxInter);
        ll_EtValTxMtRupture = (LinearLayout)findViewById(R.id.blk_EtValTxMtRupture);
        ll_EtValTxIntPenal = (LinearLayout)findViewById(R.id.blk_EtNewValTxInter);
        LL_EtNatureRupAn = (LinearLayout)findViewById(R.id.ll_EtNatureRupAn);
        LL_EtTypNewTxIntRupAnt = (LinearLayout)findViewById(R.id.ll_EtTypNewTxIntRupAnt);

        ll_btn = (LinearLayout)findViewById(R.id.blk_btn_EtPlageTxInter);
        ll_btn_EtValTxMtRupture = (LinearLayout)findViewById(R.id.blk_btn_EtValTxMtRupture);
        ll_btn_EtValTxIntPenal = (LinearLayout)findViewById(R.id.blk_btn_EtNewValTxInter);
        EtCode_ET = (EditText) findViewById(R.id.input_txt_Code_EAT);
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


        EtPlageTxInterFrom_ET = (EditText) findViewById(R.id.txt_EtValTxInterFrom);
        EtPlageTxInterTo_ET = (EditText) findViewById(R.id.txt_EtValTxInterTo);
        EtPlageTxInterValeur_ET = (EditText) findViewById(R.id.txt_EtValTxInterValeur);

        EtIsTxIntNeg_SW = (Switch) findViewById(R.id.SwitchTauxInteretNegocieEAT);
        EtIsPriseIntMiseOn_SW = (Switch) findViewById(R.id.SwitchPrendreInteretDesLaMiseEAT);
        EtIsPenalRupAnt_SW = (Switch) findViewById(R.id.SwitchPenaliteEnCasDeRuptureEAT);

        rbEtNatureRupAnTaux = (RadioButton) findViewById(R.id.rb_EtNatureRupAnTaux);
        rbEtNatureRupAnMontant = (RadioButton) findViewById(R.id.rb_EtNatureRupAnMontant);
        rbEtNatureRupAnPlage = (RadioButton) findViewById(R.id.rb_EtNatureRupAnPlage);

        EtValTxMtRupture_ET = (EditText) findViewById(R.id.input_txt_ValTxMtRuptureEAT);
        EtPlageTxMtRuptureFrom_ET = (EditText) findViewById(R.id.txt_EtValTxMtRuptureFrom);
        EtPlageTxMtRuptureTo_ET = (EditText) findViewById(R.id.txt_EtValTxMtRuptureTo);
        EtPlageTxMtRuptureValeur_ET = (EditText) findViewById(R.id.txt_EtValTxMtRuptureValeur);
        EtBaseTxPenal_ET = (EditText) findViewById(R.id.input_txt_BaseTxPenalEAT);

        EtIsEparRetireFin_SW = (Switch) findViewById(R.id.SwitchPossibiliteRetirerEAT);
        EtIsEparTransfFin_SW = (Switch) findViewById(R.id.SwitchPossibiliteTransfererVersEAT_EAV);
        EtIsOnlyTotTransf_SW = (Switch) findViewById(R.id.SwitchTransfererTotaliteVersEAT_EAV);
        EtIsEparRenouvFin_SW = (Switch) findViewById(R.id.SwitchPossibiliteRenouvelerEAT);

        EtActionDefATerme_SW = (Switch) findViewById(R.id.SwitchActionParDefautEAT);
        EtIsMultiEATOn_SW = (Switch) findViewById(R.id.SwitchMultiEAT);
        EtIsInterDusRupAnt_SW = (Switch) findViewById(R.id.SwitchInteretDusRuptureEAP);
        EtIsNewTxIntRupAnt_SW = (Switch) findViewById(R.id.SwitchDefinirNouveauTxInteretEAT);
        rbEtTypNewTxIntRupAntFixe = (RadioButton) findViewById(R.id.rbEtTypeNouveauTxInteretRuptureEATFixe);
        rbEtTypNewTxIntRupAntPlage = (RadioButton) findViewById(R.id.rbEtTypeNouveauTxInteretRuptureEATPlage);
        EtValTxIntPenal_ET = (EditText) findViewById(R.id.txtValeurNewTxInteretRuptureEAT);
        EtPlageTxIntPenalFrom_ET = (EditText) findViewById(R.id.txt_EtNewValTxInterFrom);
        EtPlageTxIntPenalTo_ET = (EditText) findViewById(R.id.txt_EtNewValTxInterTo);
        EtPlageTxIntPenalValeur_ET = (EditText) findViewById(R.id.txt_EtNewValTxInterValeur);
        EtBaseTxIntPenal_ET = (EditText) findViewById(R.id.txtBaseNewTxInteretEAT);
        EtTxIntPenalNeg_SW = (Switch) findViewById(R.id.SwitchTxNewInteretNegociableEAT);

        mySpinnerEtBaseTxInter = (JRSpinner)findViewById(R.id.spn_my_spinner_base_EtBaseTxInter);
        /*Base EtBaseTxInter debut*/
        mySpinnerEtBaseTxInter.setItems(getResources().getStringArray(R.array.array_base_taux_int_avce_spec)); //this is important, you must set it to set the item list
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
        tv_plageEtValTxInter = (TextView) findViewById(R.id.tv_plage_EtValTxInter);
        tv_plageEtValTxInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux d'intérêt";
                    ListPlageDataTASActivity.IS_TO_CREATE_OR_TO_UPDATE = true;
                    Intent i = new Intent(CreateProduitEAT.this,ListPlageDataTASActivity.class);
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
       // EtIsPenalRupAnt_SW.setChecked(false);

        remove_button = (Button) findViewById(R.id.remove_button);
        remove_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getAnswer();
                Remove_Line();
            }
        });
        remove_button_EtValTxMtRupture = (Button) findViewById(R.id.remove_button_EtValTxMtRupture);
        remove_button_EtValTxMtRupture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getAnswer();
                Remove_Line_EtValTxMtRupture();
            }
        });
        remove_button_EtTypNewTxIntRupAnt = (Button) findViewById(R.id.remove_button_EtNewValTxInter);
        remove_button_EtTypNewTxIntRupAnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getAnswer();
                Remove_Line_EtValTxIntPenal();
            }
        });

        final Button Add_button = (Button) findViewById(R.id.add_button);
        Add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add_Line();
            }
        });
        final Button add_button_EtValTxMtRupture = (Button) findViewById(R.id.add_button_EtValTxMtRupture);
        add_button_EtValTxMtRupture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add_Line_EtValTxMtRupture();
            }
        });
        final Button add_button_EtNewValTxInter = (Button) findViewById(R.id.add_button_EtNewValTxInter);
        add_button_EtNewValTxInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add_Line_EtValTxIntPenal();
            }
        });



//        rbEpTypTxInterPlage = (RadioButton) findViewById(R.id.rbEpTypTxInterPlage);
//        blkPlageEav = (LinearLayout) findViewById(R.id.blk_plage_eav);
//        layout_TauxAPreleveCpteEAV = (TextInputLayout) findViewById(R.id.input_layout_TauxAPreleveCpteEAV);

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

    public void Add_Line() {
//i get current value on last EditText

        if (numberOfLinesDebut==0){
            tabPlageDebutList.add(EtPlageTxInterFrom_ET.getText().toString());
            tabPlageFinList.add(EtPlageTxInterTo_ET.getText().toString());
            tabPlageValeurList.add(EtPlageTxInterValeur_ET.getText().toString());
        }
        if (numberOfLinesDebut>0){
            EditText editTextDebut = (EditText)findViewById(numberOfLinesDebut);
            tabPlageDebutList.add(editTextDebut.getText().toString());
            EditText editTextFin = (EditText)findViewById(numberOfLinesFin);
            tabPlageFinList.add(editTextFin.getText().toString());


            EditText editTextValeur = (EditText)findViewById(numberOfLinesValeur);
            tabPlageValeurList.add(editTextValeur.getText().toString());

        }

        // add edittext debut
        EditText et_debut = new EditText(this);
        TextView tv_debut = new TextView(this);
        tv_debut.setTextColor(Color.BLUE);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        et_debut.setLayoutParams(p);

        et_debut.setHint("De");
        et_debut.setId(numberOfLinesDebut + 1);
//        int idLastChild= ll.getChildCount()-1;
//        editText.setId(idLastChild+1);
        ll.addView(tv_debut);
        ll.addView(et_debut);
        numberOfLinesDebut++;
        tv_debut.setText("PLAGE "+(numberOfLinesDebut+1));


        // add edittext fin
        EditText et_fin = new EditText(this);
        LinearLayout.LayoutParams p_fin = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        et_fin.setLayoutParams(p_fin);
        //et.setText("Text");
        et_fin.setHint("A");
        et_fin.setId(numberOfLinesFin + 1000);
        ll.addView(et_fin);
        numberOfLinesFin+=1000;
        // add edittext valeur
        EditText et_valeur = new EditText(this);
        LinearLayout.LayoutParams p_valeur = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        et_valeur.setLayoutParams(p_valeur);
        //et.setText("Text");
        et_valeur.setHint("Valeur");
        et_valeur.setId(numberOfLinesValeur + 2000);
        ll.addView(et_valeur);
        numberOfLinesValeur+=2000;



        remove_button.setVisibility(View.VISIBLE);
    }
    public void Remove_Line() {
        //LinearLayout ll = (LinearLayout)findViewById(R.id.blk_EtPlageTxInter);
        if (numberOfLinesDebut>0){


            ll.removeViewAt(ll.getChildCount()-1);
            ll.removeViewAt(ll.getChildCount()-1);
            ll.removeViewAt(ll.getChildCount()-1);
            ll.removeViewAt(ll.getChildCount()-1);
            numberOfLinesDebut--;
            if (numberOfLinesDebut==0){
               // ll.removeViewAt(ll.getChildCount()-1);
                remove_button.setVisibility(View.INVISIBLE);
            }
        }

    }
    public void Remove_Line_EtValTxMtRupture() {
        //LinearLayout ll = (LinearLayout)findViewById(R.id.blk_EtPlageTxInter);
        if (numberOfLinesDebut_EtValTxMtRupture>0){


            ll_EtValTxMtRupture.removeViewAt(ll_EtValTxMtRupture.getChildCount()-1);
            ll_EtValTxMtRupture.removeViewAt(ll_EtValTxMtRupture.getChildCount()-1);
            ll_EtValTxMtRupture.removeViewAt(ll_EtValTxMtRupture.getChildCount()-1);
            ll_EtValTxMtRupture.removeViewAt(ll_EtValTxMtRupture.getChildCount()-1);
            numberOfLinesDebut_EtValTxMtRupture--;
            if (numberOfLinesDebut_EtValTxMtRupture==0){
               // ll.removeViewAt(ll.getChildCount()-1);
                remove_button_EtValTxMtRupture.setVisibility(View.INVISIBLE);
            }
        }

    }
    /*public void Remove_Line_EtValTxMtRupture() {
        //LinearLayout ll = (LinearLayout)findViewById(R.id.blk_EtPlageTxInter);
        if (numberOfLinesDebut_EtValTxMtRupture>0){


            ll_EtValTxMtRupture.removeViewAt(ll_EtValTxMtRupture.getChildCount()-1);
            ll_EtValTxMtRupture.removeViewAt(ll_EtValTxMtRupture.getChildCount()-1);
            ll_EtValTxMtRupture.removeViewAt(ll_EtValTxMtRupture.getChildCount()-1);
            ll_EtValTxMtRupture.removeViewAt(ll_EtValTxMtRupture.getChildCount()-1);
            numberOfLinesDebut_EtValTxMtRupture--;
            if (numberOfLinesDebut_EtValTxMtRupture==0){
               // ll.removeViewAt(ll.getChildCount()-1);
                remove_button_EtValTxMtRupture.setVisibility(View.INVISIBLE);
            }
        }

    }*/

    public void Add_Line_EtValTxMtRupture() {
//i get current value on last EditText


        if (numberOfLinesDebut_EtValTxMtRupture==0){
            tabPlageDebutList1.add(EtPlageTxMtRuptureFrom_ET.getText().toString());
            tabPlageFinList1.add(EtPlageTxMtRuptureTo_ET.getText().toString());
            tabPlageValeurList1.add(EtPlageTxMtRuptureValeur_ET.getText().toString());
        }
        if (numberOfLinesDebut_EtValTxMtRupture>0){
            EditText editTextDebut = (EditText)findViewById(numberOfLinesDebut_EtValTxMtRupture);
            tabPlageDebutList1.add(editTextDebut.getText().toString());
            EditText editTextFin = (EditText)findViewById(numberOfLinesFin_EtValTxMtRupture);
            tabPlageFinList1.add(editTextFin.getText().toString());


            EditText editTextValeur = (EditText)findViewById(numberOfLinesValeur_EtValTxMtRupture);
            tabPlageValeurList1.add(editTextValeur.getText().toString());

        }



        // add edittext debut
        EditText et_debut = new EditText(this);
        TextView tv_debut = new TextView(this);
        tv_debut.setTextColor(Color.BLUE);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        et_debut.setLayoutParams(p);

        et_debut.setHint("De");
        et_debut.setId(numberOfLinesDebut_EtValTxMtRupture + 1);
//        int idLastChild= ll.getChildCount()-1;
//        editText.setId(idLastChild+1);
        ll_EtValTxMtRupture.addView(tv_debut);
        ll_EtValTxMtRupture.addView(et_debut);
        numberOfLinesDebut_EtValTxMtRupture++;
        tv_debut.setText("PLAGE "+(numberOfLinesDebut_EtValTxMtRupture+1));


        // add edittext fin
        EditText et_fin = new EditText(this);
        LinearLayout.LayoutParams p_fin = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        et_fin.setLayoutParams(p_fin);
        //et.setText("Text");
        et_fin.setHint("A");
        et_fin.setId(numberOfLinesFin_EtValTxMtRupture + 1000);
        ll_EtValTxMtRupture.addView(et_fin);
        numberOfLinesFin_EtValTxMtRupture+=1000;
        // add edittext valeur
        EditText et_valeur = new EditText(this);
        LinearLayout.LayoutParams p_valeur = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        et_valeur.setLayoutParams(p_valeur);
        //et.setText("Text");
        et_valeur.setHint("Valeur");
        et_valeur.setId(numberOfLinesValeur_EtValTxMtRupture + 2000);
        ll_EtValTxMtRupture.addView(et_valeur);
        numberOfLinesValeur_EtValTxMtRupture+=2000;



        remove_button_EtValTxMtRupture.setVisibility(View.VISIBLE);
    }
   /*
   public void Remove_Line_EtValTxMtRupture() {
        //LinearLayout ll = (LinearLayout)findViewById(R.id.blk_EtPlageTxInter);
        if (numberOfLinesDebut>0){


            ll.removeViewAt(ll.getChildCount()-1);
            ll.removeViewAt(ll.getChildCount()-1);
            ll.removeViewAt(ll.getChildCount()-1);
            numberOfLinesDebut--;
            if (numberOfLinesDebut==0){
                ll.removeViewAt(ll.getChildCount()-1);
                remove_button.setVisibility(View.INVISIBLE);
            }
            //ll.removeViewAt(numberOfLinesDebut);
//            ll.removeViewAt(numberOfLinesFin);
//            ll.removeViewAt(numberOfLinesValeur);
//            numberOfLinesDebut--;
//            numberOfLinesFin = numberOfLinesFin-1000;
//            numberOfLinesValeur = numberOfLinesValeur-2000;
        }
        // add edittext debut
//        EditText et_debut = new EditText(this);
//        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        et_debut.setLayoutParams(p);
//        //et.setText("Text");
//        et_debut.setHint("De");
//        et_debut.setId(numberOfLinesDebut + 1);
//        ll.addView(et_debut);
//        numberOfLinesDebut++;
//
//
//        // add edittext fin
//        EditText et_fin = new EditText(this);
//        LinearLayout.LayoutParams p_fin = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        et_fin.setLayoutParams(p_fin);
//        //et.setText("Text");
//        et_fin.setHint("A");
//        et_fin.setId(numberOfLinesFin + 1000);
//        ll.addView(et_fin);
//        numberOfLinesFin++;
//
//        // add edittext valeur
//        EditText et_valeur = new EditText(this);
//        LinearLayout.LayoutParams p_valeur = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        et_valeur.setLayoutParams(p_valeur);
//        //et.setText("Text");
//        et_valeur.setHint("Valeur");
//        et_valeur.setId(numberOfLinesValeur + 2000);
//        ll.addView(et_valeur);
//        numberOfLinesValeur++;
    }
    */


    public void Add_Line_EtValTxIntPenal() {
//i get current value on last EditText


        if (numberOfLinesDebut_EtTypNewTxIntRupAnt==0){
            tabPlageDebutList2.add(EtPlageTxIntPenalFrom_ET.getText().toString());
            tabPlageFinList2.add(EtPlageTxIntPenalTo_ET.getText().toString());
            tabPlageValeurList2.add(EtPlageTxIntPenalValeur_ET.getText().toString());
        }
        if (numberOfLinesDebut_EtTypNewTxIntRupAnt>0){
            EditText editTextDebut = (EditText)findViewById(numberOfLinesDebut_EtTypNewTxIntRupAnt);
            tabPlageDebutList2.add(editTextDebut.getText().toString());
            EditText editTextFin = (EditText)findViewById(numberOfLinesFin_EtTypNewTxIntRupAnt);
            tabPlageFinList2.add(editTextFin.getText().toString());


            EditText editTextValeur = (EditText)findViewById(numberOfLinesValeur_EtTypNewTxIntRupAnt);
            tabPlageValeurList2.add(editTextValeur.getText().toString());

        }



        // add edittext debut
        EditText et_debut = new EditText(this);
        TextView tv_debut = new TextView(this);
        tv_debut.setTextColor(Color.BLUE);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        et_debut.setLayoutParams(p);

        et_debut.setHint("De");
        et_debut.setId(numberOfLinesDebut_EtTypNewTxIntRupAnt + 1);
//        int idLastChild= ll.getChildCount()-1;
//        editText.setId(idLastChild+1);
        ll_EtValTxIntPenal.addView(tv_debut);
        ll_EtValTxIntPenal.addView(et_debut);
        numberOfLinesDebut_EtTypNewTxIntRupAnt++;
        tv_debut.setText("PLAGE "+(numberOfLinesDebut_EtTypNewTxIntRupAnt+1));


        // add edittext fin
        EditText et_fin = new EditText(this);
        LinearLayout.LayoutParams p_fin = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        et_fin.setLayoutParams(p_fin);
        //et.setText("Text");
        et_fin.setHint("A");
        et_fin.setId(numberOfLinesFin_EtTypNewTxIntRupAnt + 1000);
        ll_EtValTxIntPenal.addView(et_fin);
        numberOfLinesFin_EtTypNewTxIntRupAnt+=1000;
        // add edittext valeur
        EditText et_valeur = new EditText(this);
        LinearLayout.LayoutParams p_valeur = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        et_valeur.setLayoutParams(p_valeur);
        //et.setText("Text");
        et_valeur.setHint("Valeur");
        et_valeur.setId(numberOfLinesValeur_EtTypNewTxIntRupAnt + 2000);
        ll_EtValTxIntPenal.addView(et_valeur);
        numberOfLinesValeur_EtTypNewTxIntRupAnt+=2000;



        remove_button_EtTypNewTxIntRupAnt.setVisibility(View.VISIBLE);
    }
    public void Remove_Line_EtValTxIntPenal() {
        //LinearLayout ll = (LinearLayout)findViewById(R.id.blk_EtPlageTxInter);
        if (numberOfLinesDebut_EtTypNewTxIntRupAnt>0){


            ll_EtValTxIntPenal.removeViewAt(ll_EtValTxIntPenal.getChildCount()-1);
            ll_EtValTxIntPenal.removeViewAt(ll_EtValTxIntPenal.getChildCount()-1);
            ll_EtValTxIntPenal.removeViewAt(ll_EtValTxIntPenal.getChildCount()-1);
            ll_EtValTxIntPenal.removeViewAt(ll_EtValTxIntPenal.getChildCount()-1);
            numberOfLinesDebut_EtTypNewTxIntRupAnt--;
            if (numberOfLinesDebut_EtTypNewTxIntRupAnt==0){
                // ll.removeViewAt(ll.getChildCount()-1);
                remove_button_EtTypNewTxIntRupAnt.setVisibility(View.INVISIBLE);
            }
        }
    }


    public void getAnswer() {
        String[] options = new String[3];

        EditText text = (EditText)findViewById(R.id.editText2);
        options[0] = text.getText().toString();

        text = (EditText)findViewById(R.id.editText3);
        options[1] = text.getText().toString();

        text = (EditText)findViewById(R.id.editText4);
        options[2] = text.getText().toString();

        int number = (int)(Math.random() * 3);
        String answer = options[number];

        TextView answerBox = (TextView)findViewById(R.id.textView7);
        answerBox.setText(answer);
    }

    public void onSwitchButtonClicked(View view) {
        boolean checked1 = ((Switch) view).isChecked();
        String str="";

        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.SwitchPenaliteEnCasDeRuptureEAT:
                if (EtIsPenalRupAnt_SW.isChecked()) {
                    str = checked1?"Pénalité en cas de rupture anticipéé activée":"Pénalité en cas de rupture anticipéé désactivée";

                    LL_EtNatureRupAn.setVisibility(View.VISIBLE);

                    EtValTxMtRupture_ET.setVisibility(View.VISIBLE);
                    //ll_EtValTxMtRupture.setVisibility(View.VISIBLE);
                    EtBaseTxPenal_ET.setVisibility(View.VISIBLE);

                }else{
                    LL_EtNatureRupAn.setVisibility(View.GONE);

                    ll_EtValTxMtRupture.setVisibility(View.GONE);
                    EtValTxMtRupture_ET.setVisibility(View.GONE);
                    EtBaseTxPenal_ET.setVisibility(View.GONE);
                }

                break;
            case R.id.SwitchDefinirNouveauTxInteretEAT:
                if (EtIsNewTxIntRupAnt_SW.isChecked()) {
                    str = checked1?"Nouveau taux d'interêt activé":"Nouveau taux d'interêt désactivé";

                    LL_EtTypNewTxIntRupAnt.setVisibility(View.VISIBLE);

                    EtValTxIntPenal_ET.setVisibility(View.VISIBLE);
                    //ll_EtValTxMtRupture.setVisibility(View.VISIBLE);
                    EtBaseTxIntPenal_ET.setVisibility(View.VISIBLE);
                }else{
                    LL_EtTypNewTxIntRupAnt.setVisibility(View.GONE);


                    ll_EtValTxIntPenal.setVisibility(View.GONE);
                    EtValTxIntPenal_ET.setVisibility(View.GONE);
                    EtBaseTxIntPenal_ET.setVisibility(View.GONE);
                }

                break;
                /*
            case R.id.SwitchTauxInteretAnnuelEAV:
                if (ev_is_tx_inter_an_obligSwitch.isChecked()){
                    str = checked1?"Taux interêt obligatoire":"Taux interêt non obligatoire";

                    layout_TauxInteretAnnuelEAV.setVisibility(View.VISIBLE);
                }else{
                    layout_TauxInteretAnnuelEAV.setVisibility(View.GONE);
                }


                break;
            case R.id.SwitchFraisTenuCpteOnEAV:
                if (ev_is_agios_onSwitch.isChecked()){
                    str = checked1?"Frais de tenue de compte activés":"Frais de tenue de compte désactivés";

                    LL_TypeFraisCpteEAV.setVisibility(View.VISIBLE);
                    //layout_TauxAPreleveCpteEAV.setVisibility(View.VISIBLE);
                }else{
                    layout_TauxAPreleveCpteEAV.setVisibility(View.GONE);
                    LL_TypeFraisCpteEAV.setVisibility(View.GONE);
                    blkPlageEav.setVisibility(View.GONE);
                }


                break;
*/
        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();

    }
    public void onRadioButtonClicked(View view) {
        boolean checked1 = ((RadioButton) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {

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

                    ll.setVisibility(View.GONE);
                    ll_btn.setVisibility(View.GONE);

                }
                break;
            case R.id.rbEtTypTxInterTaux:
                if (rbEtTypTxInterTaux.isChecked()) {
                    EtTypTxInter ="T";
                    EtValTxInter_ET.setHint("Valeur du taux d'interêt");
                    EtValTxInter_ET.setVisibility(View.VISIBLE);
                    input_layout_EtBaseTxInter.setVisibility(View.VISIBLE);
                    tv_plageEtValTxInter.setVisibility(View.GONE);



                    ll.setVisibility(View.GONE);
                    ll_btn.setVisibility(View.GONE);

                }
                break;
            case R.id.rbEtTypTxInterPlage:
                if (rbEtTypTxInterPlage.isChecked()) {
                    EtTypTxInter ="P";
                    tv_plageEtValTxInter.setVisibility(View.VISIBLE);

                    EtValTxInter_ET.setVisibility(View.GONE);
                    input_layout_EtBaseTxInter.setVisibility(View.GONE);
//
//                    ll.setVisibility(View.VISIBLE);
//                    if (numberOfLinesDebut<=0){
//                        remove_button.setVisibility(View.INVISIBLE);
//                    }
//                    ll_btn.setVisibility(View.VISIBLE);

                    ll.setVisibility(View.GONE);
                    ll_btn.setVisibility(View.GONE);

                }
                break;
            case R.id.rb_EtNatureRupAnTaux:
                if (rbEtNatureRupAnTaux.isChecked()) {
                    EtNatureRupAn="T";


                    EtValTxMtRupture_ET.setVisibility(View.VISIBLE);
                    EtBaseTxPenal_ET.setVisibility(View.VISIBLE);
                    ll_EtValTxMtRupture.setVisibility(View.GONE);
                    ll_btn_EtValTxMtRupture.setVisibility(View.GONE);
                    /*
                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    ev_typ_fr_agios ="P";
                    blkPlageEav.setVisibility(View.VISIBLE);
                    layout_TauxAPreleveCpteEAV.setVisibility(View.INVISIBLE);
                    */
                }
                break;
            case R.id.rb_EtNatureRupAnMontant:
                if (rbEtNatureRupAnMontant.isChecked()) {
                    EtNatureRupAn="M";


                    EtValTxMtRupture_ET.setVisibility(View.VISIBLE);
                    EtBaseTxPenal_ET.setVisibility(View.GONE);
                    ll_EtValTxMtRupture.setVisibility(View.GONE);
                    ll_btn_EtValTxMtRupture.setVisibility(View.GONE);
                    /*
                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    ev_typ_fr_agios ="P";
                    blkPlageEav.setVisibility(View.VISIBLE);
                    layout_TauxAPreleveCpteEAV.setVisibility(View.INVISIBLE);
                    */
                }
                break;
            case R.id.rb_EtNatureRupAnPlage:
                if (rbEtNatureRupAnPlage.isChecked()) {
                    EtNatureRupAn="P";


                    ll_EtValTxMtRupture.setVisibility(View.VISIBLE);
                    if (numberOfLinesDebut_EtValTxMtRupture<=0){
                        remove_button_EtValTxMtRupture.setVisibility(View.INVISIBLE);
                    }
                    EtValTxMtRupture_ET.setVisibility(View.GONE);
                    EtBaseTxPenal_ET.setVisibility(View.GONE);
                    ll_btn_EtValTxMtRupture.setVisibility(View.VISIBLE);

                    /*
                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    ev_typ_fr_agios ="P";
                    blkPlageEav.setVisibility(View.VISIBLE);
                    layout_TauxAPreleveCpteEAV.setVisibility(View.INVISIBLE);
                    */
                }
                break;
            case R.id.rbEtTypeNouveauTxInteretRuptureEATFixe:
                if (rbEtTypNewTxIntRupAntFixe.isChecked()) {

                    EtTypNewTxIntRupAnt = "F";


                    EtValTxIntPenal_ET.setVisibility(View.VISIBLE);
                    EtBaseTxIntPenal_ET.setVisibility(View.GONE);
                    ll_EtValTxIntPenal.setVisibility(View.GONE);
                    ll_btn_EtValTxIntPenal.setVisibility(View.GONE);


                }

                break;
            case R.id.rbEtTypeNouveauTxInteretRuptureEATPlage:
                if (rbEtTypNewTxIntRupAntPlage.isChecked()) {
                    EtTypNewTxIntRupAnt = "P";
                    ll_EtValTxIntPenal.setVisibility(View.VISIBLE);
                    if (numberOfLinesDebut_EtTypNewTxIntRupAnt<=0){
                        remove_button_EtTypNewTxIntRupAnt.setVisibility(View.INVISIBLE);
                    }

                    EtValTxIntPenal_ET.setVisibility(View.GONE);
                    EtBaseTxIntPenal_ET.setVisibility(View.GONE);
                    ll_btn_EtValTxIntPenal.setVisibility(View.VISIBLE);

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
//        (!STRING_EMPTY.equals(EtValTxInter_ET.getText().toString()) ||
//                (!STRING_EMPTY.equals(EtPlageTxInterFrom_ET.getText().toString()) &&
//                        !STRING_EMPTY.equals(EtPlageTxInterTo_ET.getText().toString()) )  )&&
//
//                        !STRING_EMPTY.equals(EtPlageTxInterTo_ET.getText().toString())
){
            EtCode = EtCode_ET.getText().toString();
            EtLibelle = EtLibelleET.getText().toString();
            EtMtMinMise = EtMtMinMise_ET.getText().toString();
            EtMtMaxMise = EtMtMaxMise_ET.getText().toString();
            EtDureeMin = EtDureeMin_ET.getText().toString();
            EtDureeMax = EtDureeMax_ET.getText().toString();


   // EtNaturePas  = EtNaturePas.getText().toString();
    EtNbreUnitePas  =  EtNbreUnitePas_ET.getText().toString();
   // EtTypTxInter  = ;
    EtValTxInter  = EtValTxInter_ET.getText().toString();
//    EtPlageTxInterFrom  = EtPlageTxInterFrom_ET.getText().toString();
//    EtPlageTxInterTo  = EtPlageTxInterFrom_ET.getText().toString();
    EtIsTxIntNeg   = EtIsTxIntNeg_SW.isChecked();
    EtIsPriseIntMiseOn  = EtIsPriseIntMiseOn_SW.isChecked();
    EtIsPenalRupAnt   = EtIsPenalRupAnt_SW.isChecked();
   // EtNatureRupAn   = ;
    EtValTxMtRupture  = EtValTxMtRupture_ET.getText().toString() ;
    EtPlageTxMtRuptureFrom  = EtPlageTxMtRuptureFrom_ET.getText().toString();
    EtPlageTxMtRuptureTo   = EtPlageTxMtRuptureTo_ET.getText().toString();
    EtBaseTxPenal   = EtBaseTxPenal_ET.getText().toString();;
    EtIsEparRetireFin  = EtIsEparRetireFin_SW.isChecked();
    EtIsEparTransfFin  = EtIsEparTransfFin_SW.isChecked();
    EtIsOnlyTotTransf  = EtIsOnlyTotTransf_SW.isChecked();
    EtIsEparRenouvFin  = EtIsEparRenouvFin_SW.isChecked();
    EtActionDefATerme  = EtActionDefATerme_SW.isChecked();
    EtIsMultiEATOn   = EtIsMultiEATOn_SW.isChecked();
    EtIsInterDusRupAnt  = EtIsInterDusRupAnt_SW.isChecked();
    EtIsNewTxIntRupAnt   = EtIsNewTxIntRupAnt_SW.isChecked();
   // EtTypNewTxIntRupAnt  = ;
    EtValTxIntPenal  = EtValTxIntPenal_ET.getText().toString();
    EtPlageTxIntPenalFrom  = EtPlageTxIntPenalFrom_ET.getText().toString();
    EtPlageTxIntPenalTo  = EtPlageTxIntPenalTo_ET.getText().toString();
    EtBaseTxIntPenal   = EtBaseTxIntPenal_ET.getText().toString();
    EtTxIntPenalNeg   = EtTxIntPenalNeg_SW.isChecked();


    //#TIT
    for (int i=0; i<plageDataListTIT.size();i++){
        tabPlageDebutTIT +=";"+plageDataListTIT.get(i).getPdValDe();
        tabPlageFinTIT +=";"+plageDataListTIT.get(i).getPdValA();
        tabPlageValeurTIT +=";"+plageDataListTIT.get(i).getPdValTaux();
        tabPlageBaseTIT +=";"+plageDataListTIT.get(i).getPdBase();
        tabPlageNatureTIT +=";"+plageDataListTIT.get(i).getPdNature();
    }




    if (numberOfLinesDebut==0){
        tabPlageDebutList.clear();
        tabPlageFinList.clear();
        tabPlageValeurList.clear();
        tabPlageDebutList.add(EtPlageTxInterFrom_ET.getText().toString());
        tabPlageFinList.add(EtPlageTxInterTo_ET.getText().toString());
        tabPlageValeurList.add(EtPlageTxInterValeur_ET.getText().toString());
    }
    if (numberOfLinesDebut_EtValTxMtRupture==0){
        tabPlageDebutList1.clear();
        tabPlageFinList1.clear();
        tabPlageValeurList1.clear();
        tabPlageDebutList1.add(EtPlageTxMtRuptureFrom_ET.getText().toString());
        tabPlageFinList1.add(EtPlageTxMtRuptureTo_ET.getText().toString());
        tabPlageValeurList1.add(EtPlageTxMtRuptureValeur_ET.getText().toString());
    }
    if (numberOfLinesDebut_EtTypNewTxIntRupAnt==0){
        tabPlageDebutList2.clear();
        tabPlageFinList2.clear();
        tabPlageValeurList2.clear();
        tabPlageDebutList2.add(EtPlageTxIntPenalFrom_ET.getText().toString());
        tabPlageFinList2.add(EtPlageTxIntPenalTo_ET.getText().toString());
        tabPlageValeurList2.add(EtPlageTxIntPenalValeur_ET.getText().toString());
    }
Add_Line();
    Remove_Line();
Add_Line_EtValTxMtRupture();
    Remove_Line_EtValTxMtRupture();
Add_Line_EtValTxIntPenal();
    Remove_Line_EtValTxIntPenal();
if (EtTypTxInter.equals("P")){
    for (int i=0; i<tabPlageDebutList.size();i++){
        tabPlageDebut+=";"+tabPlageDebutList.get(i);
    }
    for (int i=0; i<tabPlageFinList.size();i++){
        tabPlageFin+=";"+tabPlageFinList.get(i);
    }
    for (int i=0; i<tabPlageValeurList.size();i++){
        tabPlageValeur +=";"+tabPlageValeurList.get(i);

    }
if (EtNatureRupAn.equals("P")) {
    for (int i = 0; i < tabPlageDebutList1.size(); i++) {
        tabPlageDebut1 += ";" + tabPlageDebutList1.get(i);
    }
    for (int i = 0; i < tabPlageFinList1.size(); i++) {
        tabPlageFin1 += ";" + tabPlageFinList1.get(i);
    }
    for (int i = 0; i < tabPlageValeurList1.size(); i++) {
        tabPlageValeur1 += ";" + tabPlageValeurList1.get(i);

    }
}
if (EtTypNewTxIntRupAnt.equals("P")) {
    for (int i = 0; i < tabPlageDebutList2.size(); i++) {
        tabPlageDebut2 += ";" + tabPlageDebutList2.get(i);
    }
    for (int i = 0; i < tabPlageFinList2.size(); i++) {
        tabPlageFin2 += ";" + tabPlageFinList2.get(i);
    }
    for (int i = 0; i < tabPlageValeurList2.size(); i++) {
        tabPlageValeur2 += ";" + tabPlageValeurList2.get(i);

    }
}


    Toast.makeText(CreateProduitEAT.this,
            "debut "+tabPlageDebut1+"\n"+"fin "+tabPlageFin1+"\n"+"valeur "+tabPlageValeur1,
            Toast.LENGTH_LONG).show();

    /*        for (int j = 0; j < ll.getChildCount(); j++) {
                Toast.makeText(CreateProduitEAT.this,ll.getChildCount()+" number", Toast.LENGTH_LONG).show();
                if (ll.getChildAt(j) instanceof EditText) {
                    tabPlageDebut +=";"+ ((EditText) ll.getChildAt(j)).getText().toString();

                    Toast.makeText(CreateProduitEAT.this,
                            "debut "+tabPlageDebut+"\n"+"fin "+tabPlageFin+"\n"+"valeur "+tabPlageValeur,
                            Toast.LENGTH_LONG).show();
//                    Toast.makeText(CreateProduitEAT.this,
//                            ((EditText) ll.getChildAt(j)).getText().toString(),
//                            Toast.LENGTH_LONG).show();
                }
            } */

}


//
//    Toast.makeText(CreateProduitEAT.this,
//            tabPlageDebut,
//            Toast.LENGTH_LONG).show();

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
            httpParams.put(KEY_EAT_PLAGE_TX_INTER_FROM, EtPlageTxInterFrom);
            httpParams.put(KEY_EAT_PLAGE_TX_INTER_TO, EtPlageTxInterTo);

            httpParams.put(KEY_EAT_PLAGE_TX_INTER_DEBUT, tabPlageDebutTIT);
            httpParams.put(KEY_EAT_PLAGE_TX_INTER_FIN, tabPlageFinTIT);
            httpParams.put(KEY_EAT_PLAGE_TX_INTER_VALEUR, tabPlageValeurTIT);
            httpParams.put(KEY_EAT_PLAGE_TX_INTER_BASE, tabPlageBaseTIT);
            httpParams.put(KEY_EAT_PLAGE_TX_INTER_NATURE, tabPlageNatureTIT);

            httpParams.put(ET_IS_TX_INT_NEG, EtIsTxIntNeg.toString());
            httpParams.put(ET_IS_PRISE_INT_MISE_ON, EtIsPriseIntMiseOn.toString());
            httpParams.put(ET_IS_PENAL_RUP_ANT, EtIsPenalRupAnt.toString());
            httpParams.put(ET_NATURE_RUP_AN, EtNatureRupAn);
            httpParams.put(ET_VAL_TX_MT_RUPTURE, EtValTxMtRupture);
            httpParams.put(ET_PLAGE_TX_MT_RUPTURE_FROM, EtPlageTxMtRuptureFrom);
            httpParams.put(ET_PLAGE_TX_MT_RUPTURE_TO, EtPlageTxMtRuptureTo);
            httpParams.put(ET_BASE_TX_PENAL, EtBaseTxPenal);

            httpParams.put(KEY_EAT_PLAGE_TX_MT_RUPTURE_DEBUT, tabPlageDebut1);
            httpParams.put(KEY_EAT_PLAGE_TX_MT_RUPTURE_FIN, tabPlageFin1);
            httpParams.put(KEY_EAT_PLAGE_TX_MT_RUPTURE_VALEUR, tabPlageValeur1);

            httpParams.put(KEY_EAT_PLAGE_TX_INT_PENAL_DEBUT, tabPlageDebut2);
            httpParams.put(KEY_EAT_PLAGE_TX_INT_PENAL_FIN, tabPlageFin2);
            httpParams.put(KEY_EAT_PLAGE_TX_INT_PENAL_VALEUR, tabPlageValeur2);

            httpParams.put(ET_IS_EPAR_RETIRE_FIN, EtIsEparRetireFin.toString());
            httpParams.put(ET_IS_EPAR_TRANSF_FIN, EtIsEparTransfFin.toString());
            httpParams.put(ET_IS_ONLY_TOT_TRANSF, EtIsOnlyTotTransf.toString());
            httpParams.put(ET_IS_EPAR_RENOUV_FIN, EtIsEparRenouvFin.toString());
            httpParams.put(ET_ACTION_DEF_A_TERME, EtActionDefATerme.toString());
            httpParams.put(ET_IS_MULTI_EAT_ON, EtIsMultiEATOn.toString());
            httpParams.put(ET_IS_INTER_DUS_RUP_ANT, EtIsInterDusRupAnt.toString());
            httpParams.put(ET_IS_NEW_TX_INT_RUP_ANT, EtIsNewTxIntRupAnt.toString());
            httpParams.put(ET_TYP_NEW_TX_INT_RUP_ANT, EtTypNewTxIntRupAnt);
            httpParams.put(ET_VAL_TX_INT_PENAL, EtValTxIntPenal);
            httpParams.put(ET_PLAGE_TX_INT_PENAL_FROM, EtPlageTxIntPenalFrom);
            httpParams.put(ET_PLAGE_TX_INT_PENAL_TO, EtPlageTxIntPenalTo);
            httpParams.put(ET_BASE_TX_INT_PENAL, EtBaseTxIntPenal);
            httpParams.put(ET_TX_INT_PENAL_NEG, EtTxIntPenalNeg.toString());
            httpParams.put(ET_CAISSE_ID, String.valueOf(MyData.CAISSE_ID));

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