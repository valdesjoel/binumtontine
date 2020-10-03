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
    private static final String KEY_EAT_PLAGE_TX_INTER_FROM = "EtPlageTxInterFrom";
    private static final String KEY_EAT_PLAGE_TX_INTER_TO = "EtPlageTxInterTo";
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
    private JRSpinner EtBaseTxPenal_ET;

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



    public static String eatId;
    private static final String KEY_EAT_ID = "EtNumero";
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private TextView headerEATTextView;
    private TextView tv_header_produit;



    /*START*/
    private RadioButton rbEtTypTxInterTaux;
    private RadioButton rbEtTypNewTxIntRupAntTaux;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.fragment_param_produit_eav);
        setContentView(R.layout.activity_eat);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_produit_eav);
        setSupportActionBar(toolbar);
        setToolbarTitle(); */

        Intent intent = getIntent();
        headerEATTextView = (TextView) findViewById(R.id.header_eat);
        headerEATTextView.setText("Mise à jour EAT");

        tv_header_produit = (TextView) findViewById(R.id.header_produit);
        tv_header_produit.setText("Produit EAT\n"+"Caisse: "+ MyData.CAISSE_NAME);

        /*begin*/

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
        EtBaseTxPenal_ET = (JRSpinner) findViewById(R.id.input_txt_BaseTxPenalEAT);
        /*Base EtBaseTxPenal_ET debut*/
        EtBaseTxPenal_ET.setItems(getResources().getStringArray(R.array.array_base_taux_int_avce_spec)); //this is important, you must set it to set the item list
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

        EtActionDefATerme_SW = (Switch) findViewById(R.id.SwitchActionParDefautEAT);
        EtIsMultiEATOn_SW = (Switch) findViewById(R.id.SwitchMultiEAT);
        EtIsInterDusRupAnt_SW = (Switch) findViewById(R.id.SwitchInteretDusRuptureEAP);
        EtIsNewTxIntRupAnt_SW = (Switch) findViewById(R.id.SwitchDefinirNouveauTxInteretEAT);
        rbEtTypNewTxIntRupAntFixe = (RadioButton) findViewById(R.id.rbEtTypeNouveauTxInteretRuptureEATFixe);
        rbEtTypNewTxIntRupAntTaux = (RadioButton) findViewById(R.id.rbEtTypeNouveauTxInteretRuptureEATTaux);
        rbEtTypNewTxIntRupAntPlage = (RadioButton) findViewById(R.id.rbEtTypeNouveauTxInteretRuptureEATPlage);
        EtValTxIntPenal_ET = (EditText) findViewById(R.id.txtValeurNewTxInteretRuptureEAT);
        EtPlageTxIntPenalFrom_ET = (EditText) findViewById(R.id.txt_EtNewValTxInterFrom);
        EtPlageTxIntPenalTo_ET = (EditText) findViewById(R.id.txt_EtNewValTxInterTo);
        EtPlageTxIntPenalValeur_ET = (EditText) findViewById(R.id.txt_EtNewValTxInterValeur);
        EtBaseTxIntPenal_ET = (JRSpinner) findViewById(R.id.txtBaseNewTxInteretEAT);
        /*Base EtBaseTxIntPenal_ET debut*/
        EtBaseTxIntPenal_ET.setItems(getResources().getStringArray(R.array.array_base_taux_int_avce_spec)); //this is important, you must set it to set the item list
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
                    ListPlageDataTASActivity.IS_TO_CREATE_OR_TO_UPDATE = false;
                    Intent i = new Intent(UpdateEAT.this,ListPlageDataTASActivity.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(UpdateEAT.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

        tv_plage_EtValTxMtRupture = (TextView) findViewById(R.id.tv_plage_EtValTxMtRupture);
        tv_plage_EtValTxMtRupture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux de pénalité";
                    ListPlageDataTASActivity.IS_TO_CREATE_OR_TO_UPDATE = false;
                    Intent i = new Intent(UpdateEAT.this,ListPlageDataTASActivity.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(UpdateEAT.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        tv_plage_EtValTxIntPenal = (TextView) findViewById(R.id.tv_plage_EtValTxIntPenal);

        tv_plage_EtValTxIntPenal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Nouveau Taux d'intérêt";
                    ListPlageDataTASActivity.IS_TO_CREATE_OR_TO_UPDATE = false;
                    Intent i = new Intent(UpdateEAT.this,ListPlageDataTASActivity.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(UpdateEAT.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

      /*  onRadioButtonClicked(rbEtNaturePasFixe);
        onRadioButtonClicked(rbEtTypTxInterFixe);
        onRadioButtonClicked(rbEtNatureRupAnTaux);
        onRadioButtonClicked(rbEtTypNewTxIntRupAntFixe);

        onSwitchButtonClicked(EtIsPenalRupAnt_SW);
        onSwitchButtonClicked(EtIsNewTxIntRupAnt_SW);*/
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
                    tv_plage_EtValTxMtRupture.setVisibility(View.GONE);

                }
                break;
            case R.id.rb_EtNatureRupAnMontant:
                if (rbEtNatureRupAnMontant.isChecked()) {
                    EtNatureRupAn="M";


                    EtValTxMtRupture_ET.setVisibility(View.VISIBLE);
                    EtBaseTxPenal_ET.setVisibility(View.GONE);
                    ll_EtValTxMtRupture.setVisibility(View.GONE);
                    ll_btn_EtValTxMtRupture.setVisibility(View.GONE);
                    tv_plage_EtValTxMtRupture.setVisibility(View.GONE);

                }
                break;
            case R.id.rb_EtNatureRupAnPlage:
                if (rbEtNatureRupAnPlage.isChecked()) {
                    EtNatureRupAn="P";


                    ll_EtValTxMtRupture.setVisibility(View.GONE);
                    if (numberOfLinesDebut_EtValTxMtRupture<=0){
                        remove_button_EtValTxMtRupture.setVisibility(View.INVISIBLE);
                    }
                    EtValTxMtRupture_ET.setVisibility(View.GONE);
                    EtBaseTxPenal_ET.setVisibility(View.GONE);
//                    ll_btn_EtValTxMtRupture.setVisibility(View.VISIBLE);
                    ll_btn_EtValTxMtRupture.setVisibility(View.GONE);
                    tv_plage_EtValTxMtRupture.setVisibility(View.VISIBLE);

                }
                break;
            case R.id.rbEtTypeNouveauTxInteretRuptureEATFixe:
                if (rbEtTypNewTxIntRupAntFixe.isChecked()) {

                    EtTypNewTxIntRupAnt = "F";
                    EtValTxIntPenal_ET.setVisibility(View.VISIBLE);
                    EtBaseTxIntPenal_ET.setVisibility(View.GONE);
                    ll_EtValTxIntPenal.setVisibility(View.GONE);
                    ll_btn_EtValTxIntPenal.setVisibility(View.GONE);
                    tv_plage_EtValTxIntPenal.setVisibility(View.GONE);


                }

                break;
            case R.id.rbEtTypeNouveauTxInteretRuptureEATTaux:
                if (rbEtTypNewTxIntRupAntTaux.isChecked()) {

                    EtTypNewTxIntRupAnt = "T";

                    EtValTxIntPenal_ET.setVisibility(View.VISIBLE);
                    EtBaseTxIntPenal_ET.setVisibility(View.VISIBLE);
                    ll_EtValTxIntPenal.setVisibility(View.GONE);
                    ll_btn_EtValTxIntPenal.setVisibility(View.GONE);
                    tv_plage_EtValTxIntPenal.setVisibility(View.GONE);

                }

                break;
            case R.id.rbEtTypeNouveauTxInteretRuptureEATPlage:
                if (rbEtTypNewTxIntRupAntPlage.isChecked()) {
                    EtTypNewTxIntRupAnt = "P";
//                    ll_EtValTxIntPenal.setVisibility(View.VISIBLE);
                    if (numberOfLinesDebut_EtTypNewTxIntRupAnt<=0){
                        remove_button_EtTypNewTxIntRupAnt.setVisibility(View.INVISIBLE);
                    }

                    EtValTxIntPenal_ET.setVisibility(View.GONE);
                    EtBaseTxIntPenal_ET.setVisibility(View.GONE);
//                    ll_btn_EtValTxIntPenal.setVisibility(View.VISIBLE);
                    ll_EtValTxIntPenal.setVisibility(View.GONE);
                    ll_btn_EtValTxIntPenal.setVisibility(View.GONE);
                    tv_plage_EtValTxIntPenal.setVisibility(View.VISIBLE);

                }


                break;
        }
        // Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

/*
    public void onSwitchButtonClicked(View view) {
        boolean checked1 = ((Switch) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.SwitchMinCpteEAVOblig:
                if (ev_is_min_cpte_obligSwitch.isChecked()) {
                    str = checked1?"Minimum en compte obligatoire":"le minimum en compte n'est pas obligatoire";

                    layout_MinCompteEAV.setVisibility(View.VISIBLE);
                }else{
                    layout_MinCompteEAV.setVisibility(View.GONE);
                }

                break;
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

        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }
    public void onRadioButtonClicked(View view) {
        boolean checked1 = ((RadioButton) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.rbEpTypTxInterFixe:
                if (rbEpTypTxInterFixe.isChecked()) {
                    str = checked1?"Type Fixe Selected":"Type Fixe Deselected";
                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    ev_typ_fr_agios ="F";
                    blkPlageEav.setVisibility(View.GONE);
                    layout_TauxAPreleveCpteEAV.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.rbEpTypTxInterTaux:
                if (rbEpTypTxInterTaux.isChecked()){
                    str = checked1?"Type Taux Selected":"Type Taux Deselected";
                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    ev_typ_fr_agios ="T";
                    blkPlageEav.setVisibility(View.GONE);
                    layout_TauxAPreleveCpteEAV.setVisibility(View.VISIBLE);
                }


                break;
            case R.id.rbEpTypTxInterPlage:
                if (rbEpTypTxInterPlage.isChecked()) {
                    str = checked1?"Type Plage Selected":"Type Plage Deselected";
                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    ev_typ_fr_agios ="P";
                    blkPlageEav.setVisibility(View.VISIBLE);
                    layout_TauxAPreleveCpteEAV.setVisibility(View.GONE);
                }
                break;
        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    } */


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
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject eav;
                if (success == 1) {
                    //Parse the JSON response
                    eav = jsonObject.getJSONObject(KEY_DATA);


                    EtCode = eav.getString(KEY_EAT_CODE);
                    EtLibelle = eav.getString(KEY_EAT_LIBELLE);
                    EtMtMinMise = eav.getString(KEY_EAT_MT_MIN_MISE);
                    EtMtMaxMise = eav.getString(KEY_EAT_MT_MAX_MISE);
                    EtDureeMin = eav.getString(KEY_EAT_DUREE_MIN);
                    EtDureeMax = eav.getString(KEY_EAT_DUREE_MAX);
                    EtNaturePas = eav.getString(KEY_EAT_NATURE_PAS);

//                    EtNaturePas  = EtNaturePas.getText().toString();
                    EtNbreUnitePas = eav.getString(KEY_EAT_NBRE_UNITE_PAS);
                    EtTypTxInter = eav.getString(KEY_EAT_TYPE_TX_INTER);
                    EtValTxInter = eav.getString(KEY_EAT_VAL_TX_INTER);
                    EtBaseTxInter = eav.getString(KEY_EAT_BaseTxInter);


                    EtIsTxIntNeg = Boolean.valueOf(eav.getString(ET_IS_TX_INT_NEG));
                    EtIsPriseIntMiseOn = Boolean.valueOf(eav.getString(ET_IS_PRISE_INT_MISE_ON));

                    EtIsPenalRupAnt = Boolean.valueOf(eav.getString(ET_IS_PENAL_RUP_ANT));

                    // EtNatureRupAn
                    EtNatureRupAn = eav.getString(ET_NATURE_RUP_AN);


                    EtValTxMtRupture = eav.getString(ET_VAL_TX_MT_RUPTURE);
                    EtPlageTxMtRuptureFrom = eav.getString(ET_PLAGE_TX_MT_RUPTURE_FROM);
                    EtPlageTxMtRuptureTo = eav.getString(ET_PLAGE_TX_MT_RUPTURE_TO);
                    EtBaseTxPenal = eav.getString(ET_BASE_TX_PENAL);
                    EtIsEparRetireFin = Boolean.valueOf(eav.getString(ET_IS_EPAR_RETIRE_FIN));
                    EtIsEparTransfFin = Boolean.valueOf(eav.getString(ET_IS_EPAR_TRANSF_FIN));
                    EtIsOnlyTotTransf = Boolean.valueOf(eav.getString(ET_IS_ONLY_TOT_TRANSF));
                    EtIsEparRenouvFin = Boolean.valueOf(eav.getString(ET_IS_EPAR_RENOUV_FIN));
                    EtActionDefATerme = Boolean.valueOf(eav.getString(ET_ACTION_DEF_A_TERME));
                    EtIsMultiEATOn = Boolean.valueOf(eav.getString(ET_IS_MULTI_EAT_ON));
                    EtIsInterDusRupAnt = Boolean.valueOf(eav.getString(ET_IS_INTER_DUS_RUP_ANT));
                    EtIsNewTxIntRupAnt = Boolean.valueOf(eav.getString(ET_IS_NEW_TX_INT_RUP_ANT));

                    // EtTypNewTxIntRupAnt  = ;
                    EtTypNewTxIntRupAnt = eav.getString(ET_TYP_NEW_TX_INT_RUP_ANT);


                    EtValTxIntPenal = eav.getString(ET_VAL_TX_INT_PENAL);
                    EtPlageTxIntPenalFrom = eav.getString(ET_PLAGE_TX_INT_PENAL_FROM);
                    EtPlageTxIntPenalTo = eav.getString(ET_PLAGE_TX_INT_PENAL_TO);
                    EtBaseTxIntPenal = eav.getString(ET_BASE_TX_INT_PENAL);
                    EtTxIntPenalNeg = Boolean.valueOf(eav.getString(ET_TX_INT_PENAL_NEG));


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

/*
                    ev_codeEditText.setText(ev_code);
                    ev_libelleEditText.setText(ev_libelle);
                    ev_min_cpteEditText.setText(ev_min_cpte);
                    ev_is_min_cpte_obligSwitch.setChecked(ev_is_min_cpte_oblig);
                    ev_tx_inter_anEditText.setText(ev_tx_inter_an);
                    ev_is_tx_inter_an_obligSwitch.setChecked(ev_is_tx_inter_an_oblig);
                    ev_typ_dat_valEditText.setText(ev_typ_dat_val);
                    ev_is_multi_eav_onSwitch.setChecked(ev_is_multi_eav_on);
                    ev_is_paie_ps_onSwitch.setChecked(ev_is_paie_ps_on);
                    ev_is_agios_onSwitch.setChecked(ev_is_agios_on);
                    if (ev_typ_fr_agios.equals("F")){
                        rbEpTypTxInterFixe.setChecked(true);
                    }else if (ev_typ_fr_agios.equals("T")){
                        rbEpTypTxInterTaux.setChecked(true);
                    }else rbEpTypTxInterPlage.setChecked(true);
                    //ev_typ_fr_agiosEditText.setText(ev_typ_fr_agios);
                    ev_mt_tx_agios_prelevEditText.setText(ev_mt_tx_agios_prelev);
                    ev_plage_agios_fromEditText.setText(ev_plage_agios_from);
                    ev_plage_agios_toEditText.setText(ev_plage_agios_to);
                    ev_is_cheque_onSwitch.setChecked(ev_is_cheque_on);
                    ev_frais_clot_cptEditText.setText(ev_frais_clot_cpt);
                    */


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
//    EtValTxInter  = EtValTxInter_ET.getText().toString();
//    EtPlageTxInterFrom  = EtPlageTxInterFrom_ET.getText().toString();
//    EtPlageTxInterTo  = EtPlageTxInterFrom_ET.getText().toString();

                    EtIsTxIntNeg_SW.setChecked(EtIsTxIntNeg);
                    EtIsPriseIntMiseOn_SW.setChecked(EtIsPriseIntMiseOn);

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
                    EtIsPenalRupAnt_SW.setChecked(EtIsPenalRupAnt);
                    onSwitchButtonClicked(EtIsPenalRupAnt_SW);

                    // EtNatureRupAn   = ;
                    EtValTxMtRupture_ET.setText(EtValTxMtRupture) ;
                    EtPlageTxMtRuptureFrom_ET.setText(EtPlageTxMtRuptureFrom);
                    EtPlageTxMtRuptureTo_ET.setText(EtPlageTxMtRuptureTo);
                    EtBaseTxPenal_ET.setText(EtBaseTxPenal);
                    EtIsEparRetireFin_SW.setChecked(EtIsEparRetireFin);
                    EtIsEparTransfFin_SW.setChecked(EtIsEparTransfFin);
                    EtIsOnlyTotTransf_SW.setChecked(EtIsOnlyTotTransf);
                    EtIsEparRenouvFin_SW.setChecked(EtIsEparRenouvFin);
                    EtActionDefATerme_SW.setChecked(EtActionDefATerme);
                    EtIsMultiEATOn_SW.setChecked(EtIsMultiEATOn);
                    EtIsInterDusRupAnt_SW.setChecked(EtIsInterDusRupAnt);
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
                    }

                    EtIsNewTxIntRupAnt_SW.setChecked(EtIsNewTxIntRupAnt);
                    onSwitchButtonClicked(EtIsNewTxIntRupAnt_SW);

                    EtValTxIntPenal_ET.setText(EtValTxIntPenal);
                    EtPlageTxIntPenalFrom_ET.setText(EtPlageTxIntPenalFrom);
                    EtPlageTxIntPenalTo_ET.setText(EtPlageTxIntPenalTo);
                     EtBaseTxIntPenal_ET.setText(EtBaseTxIntPenal);
                    EtTxIntPenalNeg_SW.setChecked(EtTxIntPenalNeg);
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


      /*  if (!STRING_EMPTY.equals(movieNameEditText.getText().toString()) &&
                !STRING_EMPTY.equals(genreEditText.getText().toString()) &&
                !STRING_EMPTY.equals(yearEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ratingEditText.getText().toString())) {*/
if(true){

    EtCode = EtCode_ET.getText().toString();
    EtLibelle = EtLibelleET.getText().toString();
    EtMtMinMise = EtMtMinMise_ET.getText().toString();
    EtMtMaxMise = EtMtMaxMise_ET.getText().toString();
    EtDureeMin = EtDureeMin_ET.getText().toString();
    EtDureeMax = EtDureeMax_ET.getText().toString();


    // EtNaturePas  = EtNaturePas.getText().toString();
    EtNbreUnitePas  =  EtNbreUnitePas_ET.getText().toString();
    // EtTypTxInter  = ;
//    EtValTxInter  = EtValTxInter_ET.getText().toString();
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


        Toast.makeText(UpdateEAT.this,
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
            httpParams.put(KEY_EAT_PLAGE_TX_INTER_FROM, EtPlageTxInterFrom);
            httpParams.put(KEY_EAT_PLAGE_TX_INTER_TO, EtPlageTxInterTo);
            httpParams.put(KEY_EAT_PLAGE_TX_INTER_DEBUT, tabPlageDebut);
            httpParams.put(KEY_EAT_PLAGE_TX_INTER_FIN, tabPlageFin);
            httpParams.put(KEY_EAT_PLAGE_TX_INTER_VALEUR, tabPlageValeur);
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
