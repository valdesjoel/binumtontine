package com.example.binumtontine.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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
import com.example.binumtontine.modele.ProduitEAP;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.binumtontine.controleur.MyData.alreadyUpperCase;

public class CreateProduitEAP extends AppCompatActivity implements SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String EP_CAISSE_ID = "EpCaisseId";

    private List<String> tabPlageDebutList;
    private List<String> tabPlageDebutList1;
    private List<String> tabPlageDebutList2;
    private List<String> tabPlageDebutList3;
    private List<String> tabPlageFinList;
    private List<String> tabPlageFinList1;
    private List<String> tabPlageFinList2;
    private List<String> tabPlageFinList3;
    private List<String> tabPlageValeurList;
    private List<String> tabPlageValeurList1;
    private List<String> tabPlageValeurList2;
    private List<String> tabPlageValeurList3;
    public int numberOfLinesFin = 0;
    public int numberOfLinesFin1 = 0;
    public int numberOfLinesFin2 = 0;
    public int numberOfLinesFin3 = 0;
    public int numberOfLinesValeur = 0;
    public int numberOfLinesValeur1 = 0;
    public int numberOfLinesValeur2 = 0;
    public int numberOfLinesValeur3 = 0;


    private static final String KEY_EAP_CODE = "EpCode";
    private static final String KEY_EAP_LIBELLE = "EpLibelle";
    private static final String KEY_EAP_MT_MIN_MISE_PER = "EpMtMinMisePer";
    private static final String KEY_EAP_IS_MT_MULT_MISE = "EpIsMtMultMise";
    private static final String KEY_EAP_IS_VERSEM_ANTIC = "EpIsVersemAntic";
    private static final String KEY_EAP_DUREE_MIN = "EpDureeMin";
    private static final String KEY_EAP_DUREE_MAX = "EpDureeMax";
    private static final String KEY_EAP_NATURE_PAS = "EpNaturePas";
    private static final String KEY_EAP_NBRE_U_PAS = "EpNbreUPas";
    private static final String KEY_EAP_TYP_TX_INTER = "EpTypTxInter";
    private static final String KEY_EpValTxInter = "EpValTxInter";
    private static final String KEY_EpBaseTxInter = "EpBaseTxInter";
//    private static final String KEY_EpPlageTxInterFrom = "EpPlageTxInterFrom";
//    private static final String KEY_EpPlageTxInterTo = "EpPlageTxInterTo";
    private static final String KEY_EpIsTxIntNeg = "EpIsTxIntNeg";
    private static final String KEY_EpIsPriseIntMiseOn = "EpIsPriseIntMiseOn";
    private static final String EP_IsPenalNRespMise = "EpIsPenalNRespMise";
    private static final String EP_NbEchPenalOn = "EpNbEchPenalOn";
    private static final String EP_IsEchPenalSucces = "EpIsEchPenalSucces";
    private static final String EP_NatureRupAn = "EpNatureRupAn";
    private static final String EP_ValTxMtRupture = "EpValTxMtRupture";
//    private static final String EP_PlageTxMtRuptureFrom = "EpPlageTxMtRuptureFrom";
//    private static final String EP_PlageTxMtRuptureTo = "EpPlageTxMtRuptureTo";
    private static final String EP_BaseTxPenal = "EpBaseTxPenal";


    private static final String KEY_EAP_PLAGE_TX_INT_DEBUT = "EpTipDebut";
    private static final String KEY_EAP_PLAGE_TX_INT_FIN = "EpTipFin";
    private static final String KEY_EAP_PLAGE_TX_INT_VALEUR = "EpTipValeur";

    private static final String EP_IsEparRetireFin = "EpIsEparRetireFin";
    private static final String EP_IsEparTransfFin = "EpIsEparTransfFin";
    private static final String EP_IsOnlyTotTransf = "EpIsOnlyTotTransf";
    private static final String EP_IsEparRenouvFin = "EpIsEparRenouvFin";
    private static final String EP_ActionDefATerme = "EpActionDefATerme";
    private static final String EP_IsMultiEAPOn = "EpIsMultiEAPOn";
    private static final String EP_IsInterDusRupAnt = "EpIsInterDusRupAnt";
    private static final String EP_IsNewTxIntRupAnt = "EpIsNewTxIntRupAnt";
    private static final String EP_TypNewTxIntRupAnt = "EpTypNewTxIntRupAnt";
    private static final String EP_ValTxIntRupant = "EpValTxIntRupant";
    private static final String EP_PlageTxIntRupantFrom = "EpPlageTxIntRupantFrom";
    private static final String EP_PlageTxIntRupantTo = "EpPlageTxIntRupantTo";
    private static final String EP_BaseTxIntRupant = "EpBaseTxIntRupant";


    private static final String KEY_EAT_PLAGE_TX_INT_PENAL_DEBUT = "EtRatDebut";
    private static final String KEY_EAT_PLAGE_TX_INT_PENAL_FIN = "EtRatFin";
    private static final String KEY_EAT_PLAGE_TX_INT_PENAL_VALEUR = "EtRatValeur";

    private static final String EP_TxIntRupantNeg = "EpTxIntRupantNeg";

    private static final String EP_IsPenalRupAnt = "EpIsPenalRupAnt";
    private static final String EP_NaturePenal = "EpNaturePenal";
    private static final String EP_ValTxMtPenalite = "EpValTxMtPenalite";
    private static final String EP_PlageTxMtPenaliteFrom = "EpPlageTxMtPenaliteFrom";
    private static final String EP_PlageTxMtPenaliteTo = "EpPlageTxMtPenaliteTo";
    private static final String EP_BaseTxMtPenal = "EpBaseTxMtPenal";
    private static final String KEY_EpIsTVAOn = "EpIsTVAOn";


    private static String STRING_EMPTY = "";

    private JRSpinner JR_EpBaseTxInter;
    private TextInputLayout input_layout_EpBaseTxInter;
    private TextInputLayout input_layout_ValeurTauxInteretEAP;
    private String EpBaseTxInter;

    private EditText EpCode_ET;
    private EditText EpLibelle_ET;
    private EditText Ep_MinMtMiseEAP_ET;
    private EditText EP_DureeMinEAP_ET;
    private String EpCode;
    private String EpLibelle;
    private String EpMinMtMise;
    private String EpDureeMin;
    private Button addButton;
    private Button deleteButton;
    private Button cancelButton;
    private int success;
    private ProgressDialog pDialog;
    private Switch EpIsMtMultMise_SW;
    private Switch EpIsVersemAntic_SW;
    private EditText EpDureeMax_ET;
    private RadioButton rbEpNaturePasFixe;
    private RadioButton rbEpNaturePasSaut;
    private EditText EpNbreUPas_ET;
    private RadioButton rbEpTypTxInterFixe;
    private RadioButton rbEpTypTxInterPlage;
    private RadioButton rbEpTypTxInterPlageMois;
    private EditText EpValTxInter_ET;
    private Switch EpIsTxIntNeg_SW;
    private Switch EpIsPriseIntMiseOn_SW;
    private Switch EpIsPenalNRespMise_SW;
    private EditText EpNbEchPenalOn_ET;

    private LinearLayout ll_EcheancePenaliteSuccessiveEAP;
    private LinearLayout ll_DefinirNouveauTxInteretEAP;
    private LinearLayout ll_TypeNouveauTxInteretRuptureEAP;
    private LinearLayout ll_TxNewInteretNegociableEAP;
    private LinearLayout ll_NatureFraisPenaliteEAP;

    private Switch EpIsEchPenalSucces_SW;
    private RadioButton rbEpNatureRupAnTaux;
    private RadioButton rbEpNatureRupAnMontant;
    private RadioButton rbEpNatureRupAnPlage;
    private EditText EpValTxMtRupture_ET;

    private RadioButton rb_CtModRenouv_transfert_vers_eav;
    private RadioButton rb_CtModRenouv_renouveller;
    private RadioButton rb_CtModRenouv_retirer;
    private RadioButton rb_CtModRenouv_ras;
    private JRSpinner EpBaseTxPenal_ET;
    private TextView tv_plageEpValTxInter;
    private TextView tv_plageEpValTxMtRupture_ET;
    private TextView tv_plageEpValTxIntRupant;
    private TextView tv_plageEpValTxMtPenalite;
    private Switch EpIsEparRetireFin_SW;
    private Switch EpIsEparTransfFin_SW;
    private Switch EpIsOnlyTotTransf_SW;
    private Switch EpIsEparRenouvFin_SW;
    private Switch EpActionDefATerme_SW;
    private Switch EpIsMultiEAPOn_SW;
    private Switch EpIsInterDusRupAnt_SW;
    private Switch EpIsNewTxIntRupAnt_SW;
    private RadioButton rbEpTypNewTxIntRupAntFixe;
    private RadioButton rbEpTypNewTxIntRupAntPlage;
    private EditText EpValTxIntRupant_ET;
    private EditText EpPlageTxIntRupanFrom_ET;
    private EditText EpPlageTxIntRupanTo_ET;
    private EditText EpPlageTxIntRupanValeur_ET;
    private JRSpinner EpBaseTxIntRupant_ET;
    private Switch EpTxIntRupantNeg_SW;
    private Switch EpIsPenalRupAnt_SW;
    private RadioButton rbEpNaturePenalTaux;
    private RadioButton rbEpNaturePenalMontant;
    private RadioButton rbEpNaturePenalPlage;
    private EditText EpValTxMtPenalite_ET;
    private EditText EpPlageTxMtPenaliteFrom_ET;
    private EditText EpPlageTxMtPenaliteTo_ET;
    private EditText EpPlageTxMtPenaliteValeur_ET;
    private TextInputLayout input_layout_BaseTxPenalEAP;

    private TextInputLayout input_layout_BaseNewTxInteretEAP;
    private TextInputLayout input_layout_EpBaseTxPenal;
    private JRSpinner EpBaseTxMtPenal_ET;
    private String EpIsMtMultMise;
    private String EpIsVersemAntic;
    private String EpDureeMax;
    private String EpNaturePas;
    private String EpNbreUPas;
    private String EpTypTxInter; //F=Fixe; P = Plage de valeur; M=Plage de mois
    private String EpValTxInter;
    private String EpIsTxIntNeg;
    private String EpIsPriseIntMiseOn;
    private String EpIsPenalNRespMise;
    private String EpNbEchPenalOn;
    private String EpIsEchPenalSucces;
    private String EpNatureRupAn;
    private String EpValTxMtRupture;
    private String EpBaseTxPenal;
    private String EpIsEparRetireFin;
    private String EpIsEparTransfFin;
    private String EpIsOnlyTotTransf;
    private String EpIsEparRenouvFin;
    private String EpActionDefATerme;
    private String EpIsMultiEAPOn;
    private String EpIsInterDusRupAnt;
    private String EpIsNewTxIntRupAnt;
    private String EpTypNewTxIntRupAnt;
    private String EpValTxIntRupant;
    private String EpBaseTxIntRupant;
    private String EpTxIntRupantNeg;
    private String EpIsPenalRupAnt;
    private String EpNaturePenal;
    private String EpValTxMtPenalite;
    private String EpBaseTxMtPenal;

    private LinearLayout ll_EpNaturePenal;

    public static ArrayList<ModelPlageData> plageDataListTIP = new ArrayList<ModelPlageData>(); //to manage plageData
    public static ArrayList<ModelPlageData> plageDataListTEP = new ArrayList<ModelPlageData>(); //to manage plageData TEP
    public static ArrayList<ModelPlageData> plageDataLisNTP = new ArrayList<ModelPlageData>(); //to manage plageData NTP
    public static ArrayList<ModelPlageData> plageDataLisRAP  = new ArrayList<ModelPlageData>(); //to manage plageData NTP



    private SwitchCompat EpIsTVAOn;
    private String st_EpIsTVAOn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eap);


        tabPlageDebutList = new ArrayList<>();
        tabPlageFinList = new ArrayList<>();
        tabPlageValeurList = new ArrayList<>();


        tabPlageDebutList1 = new ArrayList<>();
        tabPlageFinList1 = new ArrayList<>();
        tabPlageValeurList1 = new ArrayList<>();

        tabPlageDebutList2 = new ArrayList<>();
        tabPlageFinList2 = new ArrayList<>();
        tabPlageValeurList2 = new ArrayList<>();
        tabPlageDebutList3 = new ArrayList<>();
        tabPlageFinList3 = new ArrayList<>();
        tabPlageValeurList3 = new ArrayList<>();
        ll_EcheancePenaliteSuccessiveEAP = (LinearLayout)findViewById(R.id.ll_EcheancePenaliteSuccessiveEAP);
        ll_NatureFraisPenaliteEAP = (LinearLayout)findViewById(R.id.ll_NatureFraisPenaliteEAP);

        ll_DefinirNouveauTxInteretEAP = (LinearLayout)findViewById(R.id.ll_DefinirNouveauTxInteretEAP);
        ll_TypeNouveauTxInteretRuptureEAP = (LinearLayout)findViewById(R.id.ll_TypeNouveauTxInteretRuptureEAP);
        ll_TxNewInteretNegociableEAP = (LinearLayout)findViewById(R.id.ll_TxNewInteretNegociableEAP);
        ll_EpNaturePenal = (LinearLayout)findViewById(R.id.ll_EpNaturePenal);

        EpCode_ET = (EditText) findViewById(R.id.input_txt_Code_EAP); //2
        alreadyUpperCase(EpCode_ET);
        EpLibelle_ET = (EditText) findViewById(R.id.input_txt_LibelleEAP); //3
        Ep_MinMtMiseEAP_ET = (EditText) findViewById(R.id.input_txt_MinMtMiseEAP); //4
        EpIsMtMultMise_SW = (Switch) findViewById(R.id.SwitchMtMultMiseEAP); //5
        EpIsVersemAntic_SW = (Switch) findViewById(R.id.SwitchVersementAnticipeEAP); //6

        EP_DureeMinEAP_ET = (EditText) findViewById(R.id.input_txt_DureeMinEAP); //7
        EpDureeMax_ET = (EditText) findViewById(R.id.input_txt_DureeMaxEAP); //8

        rbEpNaturePasFixe = (RadioButton) findViewById(R.id.rb_EpNaturePasFixe); //9_a
        rbEpNaturePasSaut = (RadioButton) findViewById(R.id.rb_EpNaturePasSaut); //9_b
        EpNbreUPas_ET = (EditText) findViewById(R.id.input_txt_NbreUnitePasEAP); //10
        rbEpTypTxInterFixe = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe); //11_a
        rbEpTypTxInterPlage = (RadioButton) findViewById(R.id.rbEpTypTxInterPlage); //11_b
        rbEpTypTxInterPlageMois = (RadioButton) findViewById(R.id.rbEpTypTxInterPlageMois); //11_c
        EpValTxInter_ET = (EditText) findViewById(R.id.input_txt_ValeurTauxInteretEAP); //12

        /*Base JR_EpBaseTxInter debut*/
        JR_EpBaseTxInter = (JRSpinner) findViewById(R.id.input_txt_EpBaseTxInter); //Nouveau champ 04/11/2020
        input_layout_EpBaseTxInter = (TextInputLayout) findViewById(R.id.input_layout_EpBaseTxInter);
        input_layout_ValeurTauxInteretEAP = (TextInputLayout) findViewById(R.id.input_layout_ValeurTauxInteretEAP);

        JR_EpBaseTxInter.setItems(getResources().getStringArray(R.array.array_EpBaseTxInter)); //this is important, you must set it to set the item list
        JR_EpBaseTxInter.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        JR_EpBaseTxInter.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        JR_EpBaseTxInter.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position
            }
        });
        /*Base JR_EpBaseTxInter fin*/
        EpIsTxIntNeg_SW = (Switch) findViewById(R.id.SwitchTauxInteretNegocieEAP); //15
        EpIsPriseIntMiseOn_SW = (Switch) findViewById(R.id.SwitchPrendreInteretDesLaMiseEAP); //16
        EpIsPenalNRespMise_SW = (Switch) findViewById(R.id.SwitchPenaliteEnCasDeRuptureEAP); //17
        EpNbEchPenalOn_ET = (EditText) findViewById(R.id.input_txt_EpNbEchPenalOn); //18
        EpIsEchPenalSucces_SW = (Switch) findViewById(R.id.SwitchEcheancePenaliteSuccessiveEAP); //19
        rbEpNatureRupAnTaux = (RadioButton) findViewById(R.id.rbEpNatureRupAnTaux); //20_a
        rbEpNatureRupAnMontant = (RadioButton) findViewById(R.id.rbEpNatureRupAnMontant); //20_b
        rbEpNatureRupAnPlage = (RadioButton) findViewById(R.id.rbEpNatureRupAnPlage); //20_c
        EpValTxMtRupture_ET = (EditText) findViewById(R.id.input_txt_ValTxMtRuptureEAP); //21
        /*Base EpBaseTxPenal_ET debut*/
        EpBaseTxPenal_ET = (JRSpinner) findViewById(R.id.input_txt_BaseTxPenalEAP); //24 il faut mettre un JSPinner
        input_layout_BaseTxPenalEAP = (TextInputLayout) findViewById(R.id.input_layout_BaseTxPenalEAP);


        EpBaseTxPenal_ET.setItems(getResources().getStringArray(R.array.array_EpBaseTxPenal)); //this is important, you must set it to set the item list
        EpBaseTxPenal_ET.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        EpBaseTxPenal_ET.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        EpBaseTxPenal_ET.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });
        /*Base EpBaseTxPenal_ET fin*/
        EpIsEparRetireFin_SW = (Switch) findViewById(R.id.SwitchPossibiliteRetirerEAP); //25
        EpIsEparTransfFin_SW = (Switch) findViewById(R.id.SwitchPossibiliteTransfererVersEAV_EAP); //26
        EpIsOnlyTotTransf_SW = (Switch) findViewById(R.id.SwitchTransfererTotaliteVersEAV_EAP); //27
        EpIsEparRenouvFin_SW = (Switch) findViewById(R.id.SwitchPossibiliteRenouvelerEAP); //28
        rb_CtModRenouv_transfert_vers_eav = (RadioButton) findViewById(R.id.rb_CtModRenouv_transfert_vers_eav); //29_a
        rb_CtModRenouv_renouveller = (RadioButton) findViewById(R.id.rb_CtModRenouv_renouveller); //29_b
        rb_CtModRenouv_retirer = (RadioButton) findViewById(R.id.rb_CtModRenouv_retirer); //29_c
        rb_CtModRenouv_ras = (RadioButton) findViewById(R.id.rb_CtModRenouv_ras); //29_d
//        EpActionDefATerme_SW = (Switch) findViewById(R.id.SwitchActionParDefautEAP); // à transformer en radio button
        EpIsMultiEAPOn_SW = (Switch) findViewById(R.id.SwitchMultiEAP); //30
        EpIsInterDusRupAnt_SW = (Switch) findViewById(R.id.SwitchInteretDusRuptureEAP); //31
        EpIsNewTxIntRupAnt_SW = (Switch) findViewById(R.id.SwitchDefinirNouveauTxInteretEAP); //32

        rbEpTypNewTxIntRupAntFixe = (RadioButton) findViewById(R.id.rbEpTypeNouveauTxInteretRuptureEAPFixe); //33_a
        rbEpTypNewTxIntRupAntPlage = (RadioButton) findViewById(R.id.rbEpTypeNouveauTxInteretRuptureEAPPlage); //33_b
        EpValTxIntRupant_ET = (EditText) findViewById(R.id.txtValeurNewTxInteretRuptureEAP); //34


        /*Base EpBaseTxIntRupant_ET debut*/
        EpBaseTxIntRupant_ET = (JRSpinner) findViewById(R.id.txtBaseNewTxInteretEAP); //37 A mettre en JRSpinner
        input_layout_BaseNewTxInteretEAP = (TextInputLayout) findViewById(R.id.input_layout_BaseNewTxInteretEAP);


        EpBaseTxIntRupant_ET.setItems(getResources().getStringArray(R.array.array_EpBaseTxIntRupant)); //this is important, you must set it to set the item list
        EpBaseTxIntRupant_ET.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        EpBaseTxIntRupant_ET.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        EpBaseTxIntRupant_ET.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });
        /*Base EpBaseTxIntRupant_ET fin*/

        EpTxIntRupantNeg_SW = (Switch) findViewById(R.id.SwitchTxNewInteretNegociableEAP); //38
        EpIsPenalRupAnt_SW = (Switch) findViewById(R.id.SwitchPenaliteDeblocageEnCasDeRuptureEAP); //39

        rbEpNaturePenalTaux = (RadioButton) findViewById(R.id.rb_EpNaturePenalTaux); //40_a
        rbEpNaturePenalMontant = (RadioButton) findViewById(R.id.rb_EpNaturePenalMontant); //40_b
        rbEpNaturePenalPlage = (RadioButton) findViewById(R.id.rb_EpNaturePenalPlage); //40_c
        EpValTxMtPenalite_ET = (EditText) findViewById(R.id.txtEpValTxMtPenalite); //41


        /*Base EpBaseTxMtPenal_ET debut*/
        EpBaseTxMtPenal_ET = (JRSpinner) findViewById(R.id.input_txt_EpBaseTxPenal); //44
        input_layout_EpBaseTxPenal = (TextInputLayout) findViewById(R.id.input_layout_EpBaseTxPenal);


        EpBaseTxMtPenal_ET.setItems(getResources().getStringArray(R.array.array_EpBaseTxMtPenal)); //this is important, you must set it to set the item list
        EpBaseTxMtPenal_ET.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        EpBaseTxMtPenal_ET.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        EpBaseTxMtPenal_ET.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });
        /*Base EpBaseTxMtPenal_ET fin*/

        EpIsTVAOn = (SwitchCompat) findViewById(R.id.SwitchEpIsTVAOn);

        //Plage

        //TIP
        tv_plageEpValTxInter = (TextView) findViewById(R.id.tv_plageEpValTxInter);
        tv_plageEpValTxInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux d'intérêt";
                    ListPlageTIP.IS_TO_CREATE_OR_TO_UPDATE = true;
//                    Intent i = new Intent(CreateProduitEAT.this,ListPlageDataTASActivity.class);
                    Intent i = new Intent(CreateProduitEAP.this,ListPlageTIP.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(CreateProduitEAP.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
        //TEP
        tv_plageEpValTxMtRupture_ET = (TextView) findViewById(R.id.tv_plageEpValTxMtRupture_ET);
        tv_plageEpValTxMtRupture_ET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux pénalité";
                    ListPlageTEP.IS_TO_CREATE_OR_TO_UPDATE = true;
//                    Intent i = new Intent(CreateProduitEAT.this,ListPlageDataTASActivity.class);
                    Intent i = new Intent(CreateProduitEAP.this,ListPlageTEP.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(CreateProduitEAP.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
//NTP
        tv_plageEpValTxIntRupant = (TextView) findViewById(R.id.tv_plageEpValTxIntRupant);
        tv_plageEpValTxIntRupant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux d'int Rup";
                    ListPlageNTP.IS_TO_CREATE_OR_TO_UPDATE = true;
//                    Intent i = new Intent(CreateProduitEAT.this,ListPlageDataTASActivity.class);
                    Intent i = new Intent(CreateProduitEAP.this,ListPlageNTP.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(CreateProduitEAP.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        //RAP
        tv_plageEpValTxMtPenalite = (TextView) findViewById(R.id.tv_plageEpValTxMtPenalite);
        tv_plageEpValTxMtPenalite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux pén débloc";
                    ListPlageRAP.IS_TO_CREATE_OR_TO_UPDATE = true;
//                    Intent i = new Intent(CreateProduitEAT.this,ListPlageDataTASActivity.class);
                    Intent i = new Intent(CreateProduitEAP.this,ListPlageRAP.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(CreateProduitEAP.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });


        onRadioButtonClicked(rbEpNaturePasFixe);
        onRadioButtonClicked(rbEpTypTxInterFixe);
        onRadioButtonClicked(rbEpNatureRupAnTaux);
        onRadioButtonClicked(rbEpTypNewTxIntRupAntFixe);
        onRadioButtonClicked(rbEpNaturePenalMontant);
        onRadioButtonClicked(rb_CtModRenouv_transfert_vers_eav);

        onSwitchButtonClicked(EpIsPenalNRespMise_SW);
        onSwitchButtonClicked(EpIsInterDusRupAnt_SW);
        onSwitchButtonClicked(EpIsNewTxIntRupAnt_SW);
        onSwitchButtonClicked(EpIsPenalRupAnt_SW);


        deleteButton = (Button) findViewById(R.id.btn_delete_eap);
        deleteButton.setVisibility(View.GONE);
        cancelButton = (Button) findViewById(R.id.btn_clean);
        //cirLoginButton
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                     finish();
                } else {
                    Toast.makeText(CreateProduitEAP.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

        addButton = (Button) findViewById(R.id.btn_save_eap);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addEap();
                } else {
                    Toast.makeText(CreateProduitEAP.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

    }



    public void onSwitchButtonClicked(View view) {
        //boolean checked1 = ((Switch) view).isChecked();
        String str="";

        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.SwitchPenaliteEnCasDeRuptureEAP:
                if (EpIsPenalNRespMise_SW.isChecked()) {
                   // str = checked1?"Pénalité en cas de non respect des échéances activée":"Pénalité en cas de non respect des échéances désactivée";

                    EpNbEchPenalOn_ET.setVisibility(View.VISIBLE);
                    ll_EcheancePenaliteSuccessiveEAP.setVisibility(View.VISIBLE);
                    ll_NatureFraisPenaliteEAP.setVisibility(View.VISIBLE);
                    EpValTxMtRupture_ET.setVisibility(View.VISIBLE);
//                    EpBaseTxPenal_ET.setVisibility(View.VISIBLE);
                    input_layout_BaseTxPenalEAP.setVisibility(View.VISIBLE);
                }else{
                    EpNbEchPenalOn_ET.setVisibility(View.GONE);
                    ll_EcheancePenaliteSuccessiveEAP.setVisibility(View.GONE);
                    ll_NatureFraisPenaliteEAP.setVisibility(View.GONE);
                    EpValTxMtRupture_ET.setVisibility(View.GONE);
//                    EpBaseTxPenal_ET.setVisibility(View.GONE);
                    input_layout_BaseTxPenalEAP.setVisibility(View.GONE);
                }
                break;
            case R.id.SwitchInteretDusRuptureEAP:
                if (EpIsInterDusRupAnt_SW.isChecked()) {
                   // str = checked1?"Intérêts dûs  en cas de rupture anticipée activé":"Intérêts dûs  en cas de rupture anticipée désactivé";
                    ll_DefinirNouveauTxInteretEAP.setVisibility(View.VISIBLE);
                }else{
                    ll_DefinirNouveauTxInteretEAP.setVisibility(View.GONE);
                    EpValTxIntRupant_ET.setVisibility(View.GONE);
                    input_layout_BaseNewTxInteretEAP.setVisibility(View.GONE);
                    ll_TypeNouveauTxInteretRuptureEAP.setVisibility(View.GONE);
                    ll_TxNewInteretNegociableEAP.setVisibility(View.GONE);
                }

                break;

            case R.id.SwitchDefinirNouveauTxInteretEAP:
                if (EpIsNewTxIntRupAnt_SW.isChecked()) {
                  //  str = checked1?"Pénalité en cas de non respect des échéances activée":"Pénalité en cas de non respect des échéances désactivée";

                    //LL_EtNatureRupAn.setVisibility(View.VISIBLE);

                    ll_TypeNouveauTxInteretRuptureEAP.setVisibility(View.VISIBLE);
                    ll_TxNewInteretNegociableEAP.setVisibility(View.VISIBLE);
                    EpValTxIntRupant_ET.setVisibility(View.VISIBLE);
                    input_layout_BaseNewTxInteretEAP.setVisibility(View.VISIBLE);


                }else{
                    ll_TypeNouveauTxInteretRuptureEAP.setVisibility(View.GONE);
                    ll_TxNewInteretNegociableEAP.setVisibility(View.GONE);
                    EpValTxIntRupant_ET.setVisibility(View.GONE);
                    input_layout_BaseNewTxInteretEAP.setVisibility(View.GONE);
                }

                break;

            case R.id.SwitchPenaliteDeblocageEnCasDeRuptureEAP:
                if (EpIsPenalRupAnt_SW.isChecked()) {
                   // str = checked1?"Pénalité en cas de non respect des échéances activée":"Pénalité en cas de non respect des échéances désactivée";
                    ll_EpNaturePenal.setVisibility(View.VISIBLE);
                    EpValTxMtPenalite_ET.setVisibility(View.VISIBLE);



                }else{
                    ll_EpNaturePenal.setVisibility(View.GONE);
                    EpValTxMtPenalite_ET.setVisibility(View.GONE);
                }

                break;
        }
//        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();

    }
    public void onRadioButtonClicked(View view) {
        boolean checked1 = ((RadioButton) view).isChecked();
        String str="";
        // Check which RadioButton was clicked
        switch(view.getId()) {
            case R.id.rb_CtModRenouv_transfert_vers_eav:
                if (rb_CtModRenouv_transfert_vers_eav.isChecked()) {
                    EpActionDefATerme ="TCV";
                }
                break;
            case R.id.rb_CtModRenouv_renouveller:
                if (rb_CtModRenouv_renouveller.isChecked()) {
                    EpActionDefATerme ="RAE";
                }
                break;
            case R.id.rb_CtModRenouv_retirer:
                if (rb_CtModRenouv_retirer.isChecked()) {
                    EpActionDefATerme ="CTR";
                }
                break;
            case R.id.rb_CtModRenouv_ras:
                if (rb_CtModRenouv_ras.isChecked()) {
                    EpActionDefATerme ="RAS";
                }
                break;
            case R.id.rb_EpNaturePasFixe:
                if (rbEpNaturePasFixe.isChecked()) {
                    EpNaturePas ="F";
                }
                break;
            case R.id.rb_EpNaturePasSaut:
                if (rbEpNaturePasSaut.isChecked()){
                    EpNaturePas ="S";
                }
                break;
            case R.id.rbEpTypTxInterFixe:
                if (rbEpTypTxInterFixe.isChecked()) {
                    EpTypTxInter ="F";
                    EpValTxInter_ET.setVisibility(View.VISIBLE);
                    input_layout_EpBaseTxInter.setVisibility(View.VISIBLE);
                    tv_plageEpValTxInter.setVisibility(View.GONE);
                }
                break;
            case R.id.rbEpTypTxInterPlage:
                if (rbEpTypTxInterPlage.isChecked()) {
                    EpTypTxInter ="P";
                    input_layout_EpBaseTxInter.setVisibility(View.GONE);
                    EpValTxInter_ET.setVisibility(View.GONE);
                    tv_plageEpValTxInter.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rbEpTypTxInterPlageMois:
                if (rbEpTypTxInterPlageMois.isChecked()) {
                    EpTypTxInter ="M";
                    input_layout_EpBaseTxInter.setVisibility(View.GONE);
                    EpValTxInter_ET.setVisibility(View.GONE);
                    tv_plageEpValTxInter.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rbEpNatureRupAnTaux:
                if (rbEpNatureRupAnTaux.isChecked()) {
                    EpNatureRupAn="T";
                    EpValTxMtRupture_ET.setVisibility(View.VISIBLE);
//                    EpBaseTxPenal_ET.setVisibility(View.VISIBLE);
                    input_layout_BaseTxPenalEAP.setVisibility(View.VISIBLE);
                    tv_plageEpValTxMtRupture_ET.setVisibility(View.GONE);

                }
                break;
            case R.id.rbEpNatureRupAnMontant:
                if (rbEpNatureRupAnMontant.isChecked()) {
                    EpNatureRupAn="M";
                    EpValTxMtRupture_ET.setVisibility(View.VISIBLE);
//                    EpBaseTxPenal_ET.setVisibility(View.GONE);
                    input_layout_BaseTxPenalEAP.setVisibility(View.GONE);
                    tv_plageEpValTxMtRupture_ET.setVisibility(View.GONE);
                }
                break;
            case R.id.rbEpNatureRupAnPlage:
                if (rbEpNatureRupAnPlage.isChecked()) {
                    EpNatureRupAn="P";
                    EpValTxMtRupture_ET.setVisibility(View.GONE);
//                    EpBaseTxPenal_ET.setVisibility(View.GONE);
                    input_layout_BaseTxPenalEAP.setVisibility(View.GONE);
                    tv_plageEpValTxMtRupture_ET.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rbEpTypeNouveauTxInteretRuptureEAPFixe:
                if (rbEpTypNewTxIntRupAntFixe.isChecked()) {
                    EpTypNewTxIntRupAnt="F";
                    EpValTxIntRupant_ET.setVisibility(View.VISIBLE);
//                    EpBaseTxIntRupant_ET.setVisibility(View.VISIBLE);
                    input_layout_BaseNewTxInteretEAP.setVisibility(View.VISIBLE);
                    tv_plageEpValTxIntRupant.setVisibility(View.GONE);
                }/*else{
                    EpValTxIntRupant_ET.setVisibility(View.GONE);
                }*/
                break;
            case R.id.rbEpTypeNouveauTxInteretRuptureEAPPlage:
                if (rbEpTypNewTxIntRupAntPlage.isChecked()) {
                    EpTypNewTxIntRupAnt="P";
                    EpValTxIntRupant_ET.setVisibility(View.GONE);
                    //EpBaseTxIntRupant_ET.setVisibility(View.GONE);
                    input_layout_BaseNewTxInteretEAP.setVisibility(View.GONE);
                    tv_plageEpValTxIntRupant.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.rb_EpNaturePenalMontant:
                if (rbEpNaturePenalMontant.isChecked()) {
                    EpNaturePenal="M";
                    EpValTxMtPenalite_ET.setVisibility(View.VISIBLE);
//                    EpBaseTxMtPenal_ET.setVisibility(View.GONE);
                    input_layout_EpBaseTxPenal.setVisibility(View.GONE);
                    tv_plageEpValTxMtPenalite.setVisibility(View.GONE);

                }/*else{
                    EpValTxMtPenalite_ET.setVisibility(View.GONE);
                }*/
                break;
            case R.id.rb_EpNaturePenalTaux:
                if (rbEpNaturePenalTaux.isChecked()) {
                    EpNaturePenal="T";
                    EpValTxMtPenalite_ET.setVisibility(View.VISIBLE);
//                    EpBaseTxMtPenal_ET.setVisibility(View.VISIBLE);
                    input_layout_EpBaseTxPenal.setVisibility(View.VISIBLE);
                    tv_plageEpValTxMtPenalite.setVisibility(View.GONE);
                }/*else{
                    EpValTxMtPenalite_ET.setVisibility(View.GONE);
                    EpBaseTxMtPenal_ET.setVisibility(View.GONE);
                }*/
                break;
            case R.id.rb_EpNaturePenalPlage:
                if (rbEpNaturePenalPlage.isChecked()) {
                    EpNaturePenal="P";
                    EpValTxMtPenalite_ET.setVisibility(View.GONE);
//                    EpBaseTxMtPenal_ET.setVisibility(View.GONE);
                    input_layout_EpBaseTxPenal.setVisibility(View.GONE);
                    tv_plageEpValTxMtPenalite.setVisibility(View.VISIBLE);
                }
                break;
        }
    }


    /**
     * Checks whether all files are filled. If so then calls AddMovieAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addEap() {
        if (!STRING_EMPTY.equals(EpCode_ET.getText().toString()) &&
                !STRING_EMPTY.equals(EpLibelle_ET.getText().toString()) &&
                !STRING_EMPTY.equals(Ep_MinMtMiseEAP_ET.getText().toString()) &&
                !STRING_EMPTY.equals(EP_DureeMinEAP_ET.getText().toString())) {

            EpCode = MyData.normalizeSymbolsAndAccents(EpCode_ET.getText().toString());
            EpLibelle = MyData.normalizeSymbolsAndAccents(EpLibelle_ET.getText().toString());
            EpMinMtMise = Ep_MinMtMiseEAP_ET.getText().toString();
            EpDureeMin = EP_DureeMinEAP_ET.getText().toString();
            if (EpIsMtMultMise_SW.isChecked()){
                EpIsMtMultMise = "Y";
            }else{
                EpIsMtMultMise = "N";
            }
//            EpIsMtMultMise = EpIsMtMultMise_SW.isChecked();
            if (EpIsVersemAntic_SW.isChecked()){
                EpIsVersemAntic = "Y";
            }else{
                EpIsVersemAntic = "N";
            }
//            EpIsVersemAntic = EpIsVersemAntic_SW.isChecked();
            EpDureeMax = EpDureeMax_ET.getText().toString();
            EpNbreUPas = EpNbreUPas_ET.getText().toString();
            EpValTxInter = EpValTxInter_ET.getText().toString();
            EpBaseTxInter = ProduitEAP.encodeEpBaseTxInter(JR_EpBaseTxInter.getText().toString());
            if (EpTypTxInter.equals("F") && EpValTxInter.equals(STRING_EMPTY)){
                input_layout_ValeurTauxInteretEAP.setError("Entrez la valeur du taux d'intérêt");
                input_layout_ValeurTauxInteretEAP.requestFocus();
                return;
            }
            if (EpTypTxInter.equals("F") && EpValTxInter.equals(STRING_EMPTY)){
                input_layout_ValeurTauxInteretEAP.setError("Entrez la valeur du taux d'intérêt");
                input_layout_ValeurTauxInteretEAP.requestFocus();
                return;
            }
            if (EpIsTxIntNeg_SW.isChecked()){
                EpIsTxIntNeg = "Y";
            }else{
                EpIsTxIntNeg = "N";
            }
//            EpIsTxIntNeg = EpIsTxIntNeg_SW.isChecked();
            if (EpIsPriseIntMiseOn_SW.isChecked()){
                EpIsPriseIntMiseOn = "Y";
            }else{
                EpIsPriseIntMiseOn = "N";
            }
//            EpIsPriseIntMiseOn = EpIsPriseIntMiseOn_SW.isChecked();
            if (EpIsPenalNRespMise_SW.isChecked()){
                EpIsPenalNRespMise = "Y";
            }else{
                EpIsPenalNRespMise = "N";
            }
//            EpIsPenalNRespMise = EpIsPenalNRespMise_SW.isChecked();

            EpNbEchPenalOn = EpNbEchPenalOn_ET.getText().toString();
            if (EpIsEchPenalSucces_SW.isChecked()){
                EpIsEchPenalSucces = "Y";
            }else{
                EpIsEchPenalSucces = "N";
            }
//            EpIsEchPenalSucces = EpIsEchPenalSucces_SW.isChecked();
            EpValTxMtRupture = EpValTxMtRupture_ET.getText().toString();
            EpBaseTxPenal = EpBaseTxPenal_ET.getText().toString();
            EpBaseTxPenal = ProduitEAP.encodeEpBaseTxPenal(EpBaseTxPenal_ET.getText().toString());
            if (EpIsEparRetireFin_SW.isChecked()){
                EpIsEparRetireFin = "Y";
            }else{
                EpIsEparRetireFin = "N";
            }
//            EpIsEparRetireFin = EpIsEparRetireFin_SW.isChecked();
            if (EpIsEparTransfFin_SW.isChecked()){
                EpIsEparTransfFin = "Y";
            }else{
                EpIsEparTransfFin = "N";
            }
//            EpIsEparTransfFin = EpIsEparTransfFin_SW.isChecked();
            if (EpIsOnlyTotTransf_SW.isChecked()){
                EpIsOnlyTotTransf = "Y";
            }else{
                EpIsOnlyTotTransf = "N";
            }
//            EpIsOnlyTotTransf = EpIsOnlyTotTransf_SW.isChecked();
            if (EpIsEparRenouvFin_SW.isChecked()){
                EpIsEparRenouvFin = "Y";
            }else{
                EpIsEparRenouvFin = "N";
            }
//            EpIsEparRenouvFin = EpIsEparRenouvFin_SW.isChecked();

            if (EpIsMultiEAPOn_SW.isChecked()){
                EpIsMultiEAPOn = "Y";
            }else{
                EpIsMultiEAPOn = "N";
            }
//            EpIsMultiEAPOn = EpIsMultiEAPOn_SW.isChecked();
            if (EpIsInterDusRupAnt_SW.isChecked()){
                EpIsInterDusRupAnt = "Y";
            }else{
                EpIsInterDusRupAnt = "N";
            }
//            EpIsInterDusRupAnt = EpIsInterDusRupAnt_SW.isChecked();
            if (EpIsNewTxIntRupAnt_SW.isChecked()){
                EpIsNewTxIntRupAnt = "Y";
            }else{
                EpIsNewTxIntRupAnt = "N";
            }
//            EpIsNewTxIntRupAnt = EpIsNewTxIntRupAnt_SW.isChecked();
           // EpTypNewTxIntRupAnt;
            EpValTxIntRupant = EpValTxIntRupant_ET.getText().toString();
          //  EpPlageTxIntRupantFrom, EpPlageTxIntRupantTo;
            EpBaseTxIntRupant = ProduitEAP.encodeEpBaseTxIntRupant(EpBaseTxIntRupant_ET.getText().toString());
            if (EpTxIntRupantNeg_SW.isChecked()){
                EpTxIntRupantNeg = "Y";
            }else{
                EpTxIntRupantNeg = "N";
            }
//            EpTxIntRupantNeg = EpTxIntRupantNeg_SW.isChecked();
            if (EpIsPenalRupAnt_SW.isChecked()){
                EpIsPenalRupAnt = "Y";
            }else{
                EpIsPenalRupAnt = "N";
            }
//            EpIsPenalRupAnt = EpIsPenalRupAnt_SW.isChecked();
           // EpNaturePenal;
            EpValTxMtPenalite = EpValTxMtPenalite_ET.getText().toString();
           // EpPlageTxMtPenaliteFrom, EpPlageTxMtPenaliteTo;
            EpBaseTxMtPenal = ProduitEAP.encodeEpBaseTxMtPenal(EpBaseTxMtPenal_ET.getText().toString());


            //TVA
            if (EpIsTVAOn.isChecked()) {
                st_EpIsTVAOn = "Y";
            }else {
                st_EpIsTVAOn = "N";
            }

            new CreateProduitEAP.AddMovieAsyncTask().execute();
        } else {
            Toast.makeText(CreateProduitEAP.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for adding a movie
     */
    private class AddMovieAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(CreateProduitEAP.this);
            pDialog.setMessage("Adding EAP. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters

            httpParams.put(KEY_EAP_CODE, EpCode);
            httpParams.put(KEY_EAP_LIBELLE, EpLibelle);
            httpParams.put(KEY_EAP_MT_MIN_MISE_PER, EpMinMtMise);
            httpParams.put(KEY_EAP_IS_MT_MULT_MISE, String.valueOf(EpIsMtMultMise));
            httpParams.put(KEY_EAP_IS_VERSEM_ANTIC, String.valueOf(EpIsVersemAntic));
            httpParams.put(KEY_EAP_DUREE_MIN, EpDureeMin);
            httpParams.put(KEY_EAP_DUREE_MAX, EpDureeMax);
            httpParams.put(KEY_EAP_NATURE_PAS, EpNaturePas);
            httpParams.put(KEY_EAP_NBRE_U_PAS, EpNbreUPas);
            httpParams.put(KEY_EAP_TYP_TX_INTER, EpTypTxInter);
            httpParams.put(KEY_EpValTxInter, EpValTxInter);
            httpParams.put(KEY_EpBaseTxInter, EpBaseTxInter); //Add at 04/11/2020
            httpParams.put(KEY_EpIsTxIntNeg, String.valueOf(EpIsTxIntNeg));
            httpParams.put(KEY_EpIsPriseIntMiseOn, String.valueOf(EpIsPriseIntMiseOn));
            httpParams.put(EP_IsPenalNRespMise, String.valueOf(EpIsPenalNRespMise));
            httpParams.put(EP_NbEchPenalOn, EpNbEchPenalOn);
            httpParams.put(EP_IsEchPenalSucces, String.valueOf(EpIsEchPenalSucces));
            httpParams.put(EP_NatureRupAn, String.valueOf(EpNatureRupAn));
            httpParams.put(EP_ValTxMtRupture, String.valueOf(EpValTxMtRupture));
            httpParams.put(EP_BaseTxPenal, String.valueOf(EpBaseTxPenal));
            httpParams.put(EP_IsEparRetireFin, String.valueOf(EpIsEparRetireFin));
            httpParams.put(EP_IsEparTransfFin, String.valueOf(EpIsEparTransfFin));
            httpParams.put(EP_IsOnlyTotTransf, String.valueOf(EpIsOnlyTotTransf));
            httpParams.put(EP_IsEparRenouvFin, String.valueOf(EpIsEparRenouvFin));
            httpParams.put(EP_ActionDefATerme, String.valueOf(EpActionDefATerme));
            httpParams.put(EP_IsMultiEAPOn, String.valueOf(EpIsMultiEAPOn));
            httpParams.put(EP_IsInterDusRupAnt, String.valueOf(EpIsInterDusRupAnt));
            httpParams.put(EP_IsNewTxIntRupAnt, String.valueOf(EpIsNewTxIntRupAnt));
            httpParams.put(EP_TypNewTxIntRupAnt, String.valueOf(EpTypNewTxIntRupAnt));
            httpParams.put(EP_ValTxIntRupant, String.valueOf(EpValTxIntRupant));
            httpParams.put(EP_BaseTxIntRupant, String.valueOf(EpBaseTxIntRupant));
            httpParams.put(EP_TxIntRupantNeg, String.valueOf(EpTxIntRupantNeg));
            httpParams.put(EP_IsPenalRupAnt, String.valueOf(EpIsPenalRupAnt));
            httpParams.put(EP_NaturePenal, String.valueOf(EpNaturePenal));
            httpParams.put(EP_ValTxMtPenalite, String.valueOf(EpValTxMtPenalite));
            httpParams.put(EP_BaseTxMtPenal, String.valueOf(EpBaseTxMtPenal));
            httpParams.put(EP_CAISSE_ID, String.valueOf(MyData.CAISSE_ID));


            //TVA
            httpParams.put(KEY_EpIsTVAOn, st_EpIsTVAOn);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_eap.php", "POST", httpParams);
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
                        Toast.makeText(CreateProduitEAP.this,
                                "EAP Ajouté", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CreateProduitEAP.this,
                                "Some error occurred while adding EAP",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}