package com.example.binumtontine.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

import com.example.binumtontine.JRSpinner;
import com.example.binumtontine.R;
import com.example.binumtontine.activity.adherent.ModelPlageData;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.example.binumtontine.modele.Credit;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.binumtontine.modele.Credit.KEY_CREDIT_Code;
import static com.example.binumtontine.modele.Credit.KEY_CREDIT_Libelle;
import static com.example.binumtontine.modele.Credit.*;

public class UpdateProduitCredit extends AppCompatActivity implements SERVER_ADDRESS {
    private static String STRING_EMPTY = "";
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_CREDIT_ID = "CrNumero";




    public static String creditId;
    private TextView headerCreditTextView;


    private Button deleteButton;
    private Button updateButton;
    private Button cancelButton;
    private int success;
    private ProgressDialog pDialog;
    private TextView tv_header_produit;
    private TextView tv_config_plage_tiv;
    private JRSpinner mySpinnerBaseTxIntMensuel; //pour gérer la base des taux
    public static ArrayList<ModelPlageData> plageDataList; //to manage plageData

//BEGIN


    private static final String KEY_CREDIT_PLAGE_FRAIS_ETUDE_DEBUT = "CcFecDebut";
    private static final String KEY_CREDIT_PLAGE_FRAIS_ETUDE_FIN = "CcFecFin";
    private static final String KEY_CREDIT_PLAGE_FRAIS_ETUDE_VALEUR = "CcFecValeur";
    private static final String KEY_CREDIT_PLAGE_FRAIS_ETUDE_BASE = "CcFecBase";
    private static final String KEY_CREDIT_PLAGE_FRAIS_ETUDE_NATURE = "CcFecNature";


    private String tabPlageDebutFEC ="";
    private String tabPlageFinFEC ="";
    private String tabPlageValeurFEC ="";
    private String tabPlageBaseFEC ="";
    private String tabPlageNatureFEC ="";

    private static final String KEY_CREDIT_PLAGE_FRAIS_DEBLOCAGE_DEBUT = "CcFdbDebut";
    private static final String KEY_CREDIT_PLAGE_FRAIS_DEBLOCAGE_FIN = "CcFdbFin";
    private static final String KEY_CREDIT_PLAGE_FRAIS_DEBLOCAGE_VALEUR = "CcFdbValeur";
    private static final String KEY_CREDIT_PLAGE_FRAIS_DEBLOCAGE_BASE = "CcFdbBase";
    private static final String KEY_CREDIT_PLAGE_FRAIS_DEBLOCAGE_NATURE = "CcFdbNature";


    private String tabPlageDebutFDB ="";
    private String tabPlageFinFDB ="";
    private String tabPlageValeurFDB ="";
    private String tabPlageBaseFDB ="";
    private String tabPlageNatureFDB ="";

    private static final String KEY_CREDIT_PLAGE_FRAIS_DECAISSEMENT_DEBUT = "CcFcxDebut";
    private static final String KEY_CREDIT_PLAGE_FRAIS_DECAISSEMENT_FIN = "CcFcxFin";
    private static final String KEY_CREDIT_PLAGE_FRAIS_DECAISSEMENT_VALEUR = "CcFcxValeur";
    private static final String KEY_CREDIT_PLAGE_FRAIS_DECAISSEMENT_BASE = "CcFcxBase";
    private static final String KEY_CREDIT_PLAGE_FRAIS_DECAISSEMENT_NATURE = "CcFcxNature";


    private String tabPlageDebutFCX ="";
    private String tabPlageFinFCX ="";
    private String tabPlageValeurFCX ="";
    private String tabPlageBaseFCX ="";
    private String tabPlageNatureFCX ="";

    //Taux d'intérêt crédit
    private static final String KEY_CREDIT_PLAGE_TAUX_INETERET_DEBUT = "CcTicDebut";
    private static final String KEY_CREDIT_PLAGE_TAUX_INETERET_FIN = "CcTicFin";
    private static final String KEY_CREDIT_PLAGE_TAUX_INETERET_VALEUR = "CcTicValeur";
    private static final String KEY_CREDIT_PLAGE_TAUX_INETERET_BASE = "CcTicBase";
    private static final String KEY_CREDIT_PLAGE_TAUX_INETERET_NATURE = "CcTicNature";


    private String tabPlageDebutTIC ="";
    private String tabPlageFinTIC ="";
    private String tabPlageValeurTIC ="";
    private String tabPlageBaseTIC ="";
    private String tabPlageNatureTIC ="";





    private static final String KEY_CC_CAISSE_NUMERO = "CcCaisseId";
    private Credit monProduitCredit;
    //    private String CrNumero;
    private EditText ET_CrCode;
    private EditText ET_CrLibelle;
    private EditText ET_CrDureeMin;
    private EditText ET_CrDureeMax;
    private RadioButton rbCrNaturePasFixe;
    private RadioButton rbCrNaturePasSaut;
    //    private String CrNaturePas;
    private EditText ET_CrNbreUPas;
    private RadioButton rbCrTypTxInterFixe;
    private RadioButton rbCrTypTxInterPlage;
    private RadioButton rbCrTypTxInterMontant;
    private RadioButton rbCrTypTxInterDuree;
    private RadioButton rbCrTypTxInterMontantDuree;
    private String CrTypTxInter;
    private EditText ET_CrValTxInter;
    private Switch SW_CrIsTxIntNeg;
    private EditText ET_CrNbreAvalDmde;
    private EditText ET_CrNbreMinAvalExig;
    private EditText ET_CrTxCouvCrAval;
    private Switch SW_CrIsTxCouvAvalOblig;
    private Switch SW_CrIsCautionMorAvalAcc;
    private Switch SW_CrIsGarBloqCptOblig;
    private Switch SW_CrIsGarCptEAVOn;
    private Switch SW_CrIsGarCptEATOn;
    private Switch SW_CrIsGarCptEAPOn;
    private EditText ET_CrMtMaxSansAval;
    private Switch SW_CrIsAvalSansCredOn;
    private Switch SW_CrIsTxGarMemObl;
    private EditText ET_CrTauxGarMemb;
    private Switch SW_CrIsPersMorAvalOn;
    private Switch SW_CrIsCouvPartSOn;
    private EditText ET_CrTxCouvPSOblig;
    private Switch SW_CrIsAffCollCredOn;
    private EditText ET_CrNbreAnAncMinCred;
    private Switch SW_CrNbAnAncNeg;
    private EditText ET_CrMtPlafondMax;
    private Switch SW_CrIsMtPlafCredLeve;
    private Switch SW_CrIsGarMatExige;
    private Switch SW_CrIsFraisEtudDossOn;

    private RadioButton rbCrNatFrEtudDossFixe;
    private RadioButton rbCrNatFrEtudDossTaux;
    private RadioButton rbCrNatFrEtudDossPlage;
    private String CrNatFrEtudDoss;
    private EditText ET_CrValTxFrEtudDoss;
    private JRSpinner JR_CrBaseTxFrEtudDoss;
    private Switch SW_CrIsFraisDeblocCredOn;

    private RadioButton rbCrNatFraisDeblocCredFixe;
    private RadioButton rbCrNatFraisDeblocCredTaux;
    private RadioButton rbCrNatFraisDeblocCredPlage;
    private String CrNatFraisDeblocCred;
    private EditText ET_CrValTxFraisDeblocCred;
    private JRSpinner JR_CrBaseTxFraisDeblocCred;
    private Switch SW_CrIsFraisDecaissCredOn;

    private RadioButton rbCrNatFraisDecaissCredFixe;
    private RadioButton rbCrNatFraisDecaissCredTaux;
    private RadioButton rbCrNatFraisDecaissCredPlage;
    private String CrNatFraisDecaissCred;
    private EditText ET_CrValTxFraisDecaissCred;
    private JRSpinner JR_CrBaseTxFraisDecaissCred;
    private Switch SW_CrIsFraisEtudByDAV;
    private Switch SW_CrIsFraisDeblocByDAV;
    private Switch SW_CrIsFraisDecaissByDAV;
    private Switch SW_CrIsModDecaissByObjet;
    private Switch SW_CrIsDeblocTransfDAVOn;
    private Switch SW_CrIsMtPlafByObjet;
    private JRSpinner JR_CrModeRemb;
    private Switch SW_CrIsCptEATRemCredOn;
    private Switch SW_CrIsCptEAPRemCredOn;
    private Switch SW_CrIsInterOffSiCapRembAnt;
    private EditText ET_CrTxInterEchNHon;
    private JRSpinner JR_CrBaseInterEchNHon;
    private JRSpinner JR_CrPlanningRembCred;
    private Switch SW_CrIsRappDatEchCred;
    private EditText ET_CrModelTextRappEchRemb;
    private EditText ET_CrNbreJrAvantDatEch;
    private EditText ET_CrNbreJrApreEchSiNHon;
    private Switch SW_CrIsTxIntDegressif;
//    private String CrUser;
//    private String CrDateHCree;
//    private String CrUserModif;
//    private String CrDatHModif;
//    private String CrCaisseId;
//    private String CrGuichetId;




    private EditText CcCode,CcLibelle,CcMtMaxDecouv,CcNatureTxIntDecouv,CcValTxIntDecouv,
            CcPlageTxIntDecouvFrom,CcPlageTxIntDecouvTo,CcDureeMaxDecouv,CcNatureTypDureDecouv,
            CcNatureTxMtAgio,CcValTxMtAgio,CcPlageTxMtAgioFrom,CcPlageTxMtAgioTo,CcNatBaseAgio,
            CcNbPagesCheqM1,CcPrixVteCheqM1,CcNbPagesCheqM2,CcPrixVteCheqM2,CcNbPagesCheqM3,
            CcPrixVteCheqM3,CcDureeValidCheq,CcNbMinSignatChq,CcNatTxComm,CcValTxCommMvm,
            CcPlageTxCommMvmFrom,CcPlageTxCommMvmTo;
    private Switch CcIsDecouvOn,CcIsMaxDecouvNeg,CcIsChequierM1On,CcIsChequierM2On,CcIsChequierM3On,
            CcIsTxCommMvmOper;
    private String st_CcCode,st_CcLibelle,st_CcMtMaxDecouv,
            st_CcNatureTxIntDecouv,st_CcValTxIntDecouv,st_CcPlageTxIntDecouvFrom,st_CcPlageTxIntDecouvTo,
            st_CcDureeMaxDecouv,st_CcNatureTypDureDecouv,st_CcNatureTxMtAgio,st_CcValTxMtAgio,
            st_CcPlageTxMtAgioFrom,st_CcPlageTxMtAgioTo,st_CcNatBaseAgio,
            st_CcNbPagesCheqM1,st_CcPrixVteCheqM1,st_CcNbPagesCheqM2,
            st_CcPrixVteCheqM2,st_CcNbPagesCheqM3,st_CcPrixVteCheqM3,
            st_CcDureeValidCheq,st_CcNbMinSignatChq,st_CcNatTxComm,st_CcValTxCommMvm,
            st_CcPlageTxCommMvmFrom,st_CcPlageTxCommMvmTo;

    private Boolean st_CcIsDecouvOn, st_CcIsMaxDecouvNeg,st_CcIsChequierM1On,st_CcIsChequierM2On,
            st_CcIsChequierM3On,st_CcIsTxCommMvmOper;





    private RadioButton CcNatureTxIntDecouvFixe,CcNatureTxIntDecouvTaux,CcNatureTxIntDecouvPlage;
    private RadioButton CcNatureTypDureDecouvJour,CcNatureTypDureDecouvSemaine,CcNatureTypDureDecouvMois;
    private RadioButton CcNatureTxMtAgioTaux, CcNatureTxMtAgioMontant, CcNatureTxMtAgioPlage;
    private RadioButton CcNatTxCommFixe, CcNatTxCommMontant, CcNatTxCommPlage;
    private JRSpinner mySpinnerBaseTxIntDecouv; //pour gérer le spinner contenant les bases du Tx Int Decouvert
    private JRSpinner mySpinnerBaseTxMtAgio; //pour gérer le spinner contenant les bases taux
    private JRSpinner mySpinnerBaseTxCommMvm; //pour gérer le spinner contenant les bases taux


    private TextInputLayout input_layout_CcValTxMtAgio;
    private TextInputLayout input_layout_CcBaseTxMtAgio;
    private TextInputLayout input_layout_CcBaseCalculAgiosCC;
    private TextInputLayout input_layout_CcBaseTxCommMvmCC;
    private TextInputLayout input_layout_CcValTxIntDecouv;
    private TextInputLayout input_layout_CcValTxCommMvm;
    private LinearLayout bloc_cc1;
    private LinearLayout bloc_cc2;
    private LinearLayout bloc_cc3;
    private LinearLayout bloc_cc4;
    private LinearLayout bloc_cc5;
    private LinearLayout blk_plage_cc;
    private LinearLayout blk_plage_agios_cc;
    private LinearLayout blk_plage_CcValTxCommMvm;
    private TextView tv_plageTxMtAgio;
    public static ArrayList<ModelPlageData> plageDataListCTP = new ArrayList<ModelPlageData>(); //to manage plageData
    private TextView tv_plageCalculAgiosCC;
    public static ArrayList<ModelPlageData> plageDataListCAP = new ArrayList<ModelPlageData>(); //to manage plageData
    private TextView tv_plageTxCommMvmCC;
    public static ArrayList<ModelPlageData> plageDataListTCP = new ArrayList<ModelPlageData>(); //to manage plageData

    private Button addButton;
    private String baseCcTxIntDecouv;
    private String baseCcTxCommMvm;

    //for manage plages
//#plage CTP
    private static final String KEY_CC_PLAGE_TAUX_INTERET_DECOUVERT_DEBUT = "CcCtpDebut";
    private static final String KEY_CC_PLAGE_TAUX_INTERET_DECOUVERT_FIN = "CcCtpFin";
    private static final String KEY_CC_PLAGE_TAUX_INTERET_DECOUVERT_VALEUR = "CcCtpValeur";
    private static final String KEY_CC_PLAGE_TAUX_INTERET_DECOUVERT_BASE = "CcCtpBase";
    private static final String KEY_CC_PLAGE_TAUX_INTERET_DECOUVERT_NATURE = "CcCtpNature";


    private String tabPlageDebutCTP ="";
    private String tabPlageFinCTP ="";
    private String tabPlageValeurCTP ="";
    private String tabPlageBaseCTP ="";
    private String tabPlageNatureCTP ="";

    //#CAP
    private static final String KEY_CC_PLAGE_TAUX_CALCUL_AGIO_DEBUT = "CcCapDebut";
    private static final String KEY_CC_PLAGE_TAUX_CALCUL_AGIO_FIN = "CcCapFin";
    private static final String KEY_CC_PLAGE_TAUX_CALCUL_AGIO_VALEUR = "CcCapValeur";
    private static final String KEY_CC_PLAGE_TAUX_CALCUL_AGIO_BASE = "CcCapBase";
    private static final String KEY_CC_PLAGE_TAUX_CALCUL_AGIO_NATURE = "CcCapNature";


    private String tabPlageDebutCAP ="";
    private String tabPlageFinCAP ="";
    private String tabPlageValeurCAP ="";
    private String tabPlageBaseCAP ="";
    private String tabPlageNatureCAP ="";
    //#TCP
    private static final String KEY_CC_PLAGE_TAUX_COMMISSION_DEBUT = "CcTcpDebut";
    private static final String KEY_CC_PLAGE_TAUX_COMMISSION_FIN = "CcTcpFin";
    private static final String KEY_CC_PLAGE_TAUX_COMMISSION_VALEUR = "CcTcpValeur";
    private static final String KEY_CC_PLAGE_TAUX_COMMISSION_BASE = "CcTcpBase";
    private static final String KEY_CC_PLAGE_TAUX_COMMISSION_NATURE = "CcTcpNature";

    private String tabPlageDebutTCP ="";
    private String tabPlageFinTCP ="";
    private String tabPlageValeurTCP ="";
    private String tabPlageBaseTCP ="";
    private String tabPlageNatureTCP ="";

    //END

    private TextView tv_plage_CrNatFrEtudDoss;
    private TextView tv_plage_CrTypTxInter;
    private TextView tv_plage_CrNatFraisDeblocCred;
    private TextView tv_plage_CrNatFraisDecaissCred;
    public static ArrayList<ModelPlageData> plageDataListTIC = new ArrayList<ModelPlageData>(); //to manage plageData taux d'intérêt
    public static ArrayList<ModelPlageData> plageDataListFEC = new ArrayList<ModelPlageData>(); //to manage plageData frais etude
    public static ArrayList<ModelPlageData> plageDataListFDB = new ArrayList<ModelPlageData>(); //to manage plageData frais deblocage
    public static ArrayList<ModelPlageData> plageDataListFCX = new ArrayList<ModelPlageData>(); //to manage plageData frais decaissement


    private LinearLayout LL_CrNatFrEtudDoss;
    private LinearLayout LL_CrNatFraisDeblocCred;
    private LinearLayout LL_CrNatFraisDecaissCred;

    private TextInputLayout layout_TauxCrNatFrEtudDoss;
    private TextInputLayout layout_TauxCrValTxFraisDeblocCred;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.fragment_param_produit_eav);
        setContentView(R.layout.activity_credit);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_produit_eav);
        setSupportActionBar(toolbar);
        setToolbarTitle(); */
        plageDataList = new ArrayList<>();
        Intent intent = getIntent();

        headerCreditTextView = (TextView) findViewById(R.id.header_credit);
        headerCreditTextView.setText("Mise à jour Crédit");

        tv_header_produit = (TextView) findViewById(R.id.header_produit);

        tv_header_produit.setText("Produit Crédit\n"+"Caisse: "+MyData.CAISSE_NAME);

        tv_plage_CrTypTxInter = (TextView) findViewById(R.id.tv_plage_tic_cc);
        tv_plage_CrNatFrEtudDoss = (TextView) findViewById(R.id.tv_plage_CrNatFrEtudDoss);
        tv_plage_CrNatFraisDeblocCred = (TextView) findViewById(R.id.tv_plage_CrNatFraisDeblocCred);
        tv_plage_CrNatFraisDecaissCred = (TextView) findViewById(R.id.tv_plage_CrNatFraisDecaissCred);
        ET_CrCode = (EditText) findViewById(R.id.input_txt_Code_credit);
        ET_CrLibelle = (EditText) findViewById(R.id.input_txt_LibelleCredit);
        ET_CrDureeMax = (EditText) findViewById(R.id.input_txt_CrDureeMax);
        ET_CrDureeMin = (EditText) findViewById(R.id.input_txt_CrDureeMin);
//        rbCrNaturePasFixe = (RadioButton)findViewById(R.id.rbCrNaturePasFixe);
//        rbCrNaturePasSaut = (RadioButton)findViewById(R.id.rbCrNaturePasSaut);
//        ET_CrNbreUPas = (RadioButton)findViewById(R.id.rbCrNaturePasSaut);
        rbCrTypTxInterFixe = (RadioButton)findViewById(R.id.rbCrTypTxInterFixe);
        rbCrTypTxInterPlage = (RadioButton)findViewById(R.id.rbCrTypTxInterPlage);
        /*rbCrTypTxInterDuree = (RadioButton)findViewById(R.id.rbCrNaturePasSaut);
        rbCrTypTxInterMontant = (RadioButton)findViewById(R.id.rbCrNaturePasSaut);
        rbCrTypTxInterMontantDuree = (RadioButton)findViewById(R.id.rbCrNaturePasSaut);
        */
        ET_CrValTxInter = (EditText) findViewById(R.id.input_txt_CrValTxInter);
        SW_CrIsTxIntNeg = (Switch) findViewById(R.id.SwitchCrIsTxIntNeg);
        ET_CrNbreAvalDmde = (EditText) findViewById(R.id.input_txt_CrNbreAvalDmde);
        ET_CrNbreMinAvalExig = (EditText) findViewById(R.id.input_txt_CrNbreMinAvalExig);
        ET_CrTxCouvCrAval = (EditText) findViewById(R.id.input_txt_CrTxCouvCrAval);
        SW_CrIsTxCouvAvalOblig = (Switch) findViewById(R.id.SwitchCrIsTxCouvAvalOblig);
        SW_CrIsCautionMorAvalAcc = (Switch) findViewById(R.id.SwitchCrIsCautionMorAvalAcc);
        SW_CrIsGarBloqCptOblig = (Switch) findViewById(R.id.SwitchCrIsGarBloqCptOblig);
        SW_CrIsGarCptEAVOn = (Switch) findViewById(R.id.SwitchCrIsGarCptEAVOn);
        SW_CrIsGarCptEATOn = (Switch) findViewById(R.id.SwitchCrIsGarCptEATOn);
        SW_CrIsGarCptEAPOn = (Switch) findViewById(R.id.SwitchCrIsGarCptEAPOn);
        ET_CrMtMaxSansAval = (EditText) findViewById(R.id.input_txt_CrMtMaxSansAval);
        SW_CrIsAvalSansCredOn = (Switch) findViewById(R.id.SwitchCrIsAvalSansCredOn);
        SW_CrIsTxGarMemObl = (Switch) findViewById(R.id.SwitchCrIsTxGarMemObl);
        ET_CrTauxGarMemb = (EditText) findViewById(R.id.input_txt_CrTauxGarMemb);
        SW_CrIsPersMorAvalOn = (Switch) findViewById(R.id.SwitchCrIsPersMorAvalOn);
        SW_CrIsCouvPartSOn = (Switch) findViewById(R.id.SwitchCrIsCouvPartSOn);
        ET_CrTxCouvPSOblig = (EditText) findViewById(R.id.input_txt_CrTxCouvPSOblig);
        SW_CrIsAffCollCredOn = (Switch) findViewById(R.id.SwitchCrIsAffCollCredOn);
        ET_CrNbreAnAncMinCred = (EditText) findViewById(R.id.input_txt_CrNbreAnAncMinCred);
        SW_CrNbAnAncNeg = (Switch) findViewById(R.id.SwitchCrNbAnAncNeg);
        ET_CrMtPlafondMax = (EditText) findViewById(R.id.input_txt_CrMtPlafondMax);
        SW_CrIsMtPlafCredLeve = (Switch) findViewById(R.id.SwitchCrIsMtPlafCredLeve);
        SW_CrIsGarMatExige = (Switch) findViewById(R.id.SwitchCrIsGarMatExige);
        SW_CrIsFraisEtudDossOn = (Switch) findViewById(R.id.SwitchCrIsFraisEtudDossOn);
        rbCrNatFrEtudDossFixe = (RadioButton) findViewById(R.id.rbCrNatFrEtudDossFixe);
        rbCrNatFrEtudDossTaux = (RadioButton) findViewById(R.id.rbCrNatFrEtudDossTaux);
        rbCrNatFrEtudDossPlage = (RadioButton) findViewById(R.id.rbCrNatFrEtudDossPlage);
        ET_CrValTxFrEtudDoss = (EditText) findViewById(R.id.input_txt_CrNatFrEtudDoss);
        JR_CrBaseTxFrEtudDoss = (JRSpinner) findViewById(R.id.spn_my_spinner_base_tauxCrNatFrEtudDoss); //A revoir car c'est un JRSpinner
        JR_CrBaseTxFrEtudDoss.setItems(getResources().getStringArray(R.array.array_base_taux_credit)); //this is important, you must set it to set the item list
        JR_CrBaseTxFrEtudDoss.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        JR_CrBaseTxFrEtudDoss.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        JR_CrBaseTxFrEtudDoss.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });
        SW_CrIsFraisDeblocCredOn = (Switch) findViewById(R.id.SwitchCrIsFraisDeblocCredOn);

        rbCrNatFraisDeblocCredFixe = (RadioButton) findViewById(R.id.rbCrNatFraisDeblocCredFixe);
        rbCrNatFraisDeblocCredTaux = (RadioButton) findViewById(R.id.rbCrNatFraisDeblocCredTaux);
        rbCrNatFraisDeblocCredPlage = (RadioButton) findViewById(R.id.rbCrNatFraisDeblocCredPlage);
        ET_CrValTxFraisDeblocCred = (EditText) findViewById(R.id.input_txt_CrValTxFraisDeblocCred);
        JR_CrBaseTxFraisDeblocCred = (JRSpinner) findViewById(R.id.spn_my_spinner_CrBaseTxFraisDeblocCred); //A revoir car c'est un JRSpinner
        JR_CrBaseTxFraisDeblocCred.setItems(getResources().getStringArray(R.array.array_base_taux_credit)); //this is important, you must set it to set the item list
        JR_CrBaseTxFraisDeblocCred.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        JR_CrBaseTxFraisDeblocCred.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        JR_CrBaseTxFraisDeblocCred.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });
        SW_CrIsFraisDecaissCredOn = (Switch) findViewById(R.id.SwitchCrIsFraisDecaissCredOn);

        rbCrNatFraisDecaissCredFixe = (RadioButton) findViewById(R.id.rbCrNatFraisDecaissCredFixe);
        rbCrNatFraisDecaissCredTaux = (RadioButton) findViewById(R.id.rbCrNatFraisDecaissCredTaux);
        rbCrNatFraisDecaissCredPlage = (RadioButton) findViewById(R.id.rbCrNatFraisDecaissCredPlage);
        ET_CrValTxFraisDecaissCred = (EditText) findViewById(R.id.input_txt_CrValTxFraisDecaissCred);
        JR_CrBaseTxFraisDecaissCred = (JRSpinner) findViewById(R.id.spn_my_spinner_CrBaseFraisDecaissCred); //A revoir car c'est un JRSpinner
        JR_CrBaseTxFraisDecaissCred.setItems(getResources().getStringArray(R.array.array_base_taux_credit)); //this is important, you must set it to set the item list
        JR_CrBaseTxFraisDecaissCred.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        JR_CrBaseTxFraisDecaissCred.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        JR_CrBaseTxFraisDecaissCred.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });
        SW_CrIsFraisEtudByDAV = (Switch) findViewById(R.id.SwitchCrIsFraisEtudByDAV);
        SW_CrIsFraisDeblocByDAV = (Switch) findViewById(R.id.SwitchCrIsFraisDeblocByDAV);
        SW_CrIsFraisDecaissByDAV = (Switch) findViewById(R.id.SwitchCrIsFraisDecaissByDAV);
        SW_CrIsModDecaissByObjet = (Switch) findViewById(R.id.SwitchCrIsModDecaissByObjet);
        SW_CrIsDeblocTransfDAVOn = (Switch) findViewById(R.id.SwitchCrIsDeblocTransfDAVOn);
        SW_CrIsMtPlafByObjet = (Switch) findViewById(R.id.SwitchCrIsMtPlafByObjet);
        JR_CrModeRemb = (JRSpinner) findViewById(R.id.spn_my_spinner_CrModeRemb); //A revoir car c'est un JRSpinner
        JR_CrModeRemb.setItems(getResources().getStringArray(R.array.array_mode_remboursement_credit)); //this is important, you must set it to set the item list
        JR_CrModeRemb.setTitle("Sélectionner le mode de remboursement du crédit"); //change title of spinner-dialog programmatically
        JR_CrModeRemb.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        JR_CrModeRemb.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });
        SW_CrIsCptEATRemCredOn = (Switch) findViewById(R.id.SwitchCrIsCptEATRemCredOn);
        SW_CrIsCptEAPRemCredOn = (Switch) findViewById(R.id.SwitchCrIsCptEAPRemCredOn);
        SW_CrIsInterOffSiCapRembAnt = (Switch) findViewById(R.id.SwitchCrIsInterOffSiCapRembAnt);
        ET_CrTxInterEchNHon = (EditText) findViewById(R.id.input_txt_CrTxInterEchNHon);
        JR_CrBaseInterEchNHon = (JRSpinner) findViewById(R.id.spn_my_spinner_CrBaseInterEchNHon);//A revoir car c'est un JRSpinner
        JR_CrBaseInterEchNHon.setItems(getResources().getStringArray(R.array.array_base_interet_echeance_non_honoree)); //this is important, you must set it to set the item list
        JR_CrBaseInterEchNHon.setTitle("Sélectionner la base du taux pour les échéances non honorées"); //change title of spinner-dialog programmatically
        JR_CrBaseInterEchNHon.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        JR_CrBaseInterEchNHon.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });
        JR_CrPlanningRembCred = (JRSpinner) findViewById(R.id.spn_my_spinner_CrPlanningRembCred);//A revoir car c'est un JRSpinner
        JR_CrPlanningRembCred.setItems(getResources().getStringArray(R.array.array_nature_planning_remboursement)); //this is important, you must set it to set the item list
        JR_CrPlanningRembCred.setTitle("Sélectionner la nature du planning de remboursement"); //change title of spinner-dialog programmatically
        JR_CrPlanningRembCred.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        JR_CrPlanningRembCred.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });
        SW_CrIsRappDatEchCred = (Switch) findViewById(R.id.SwitchCrIsRappDatEchCred);
        ET_CrModelTextRappEchRemb = (EditText) findViewById(R.id.input_txt_CrModelTextRappEchRemb);
        ET_CrNbreJrAvantDatEch = (EditText) findViewById(R.id.input_txt_CrNbreJrAvantDatEch);
        ET_CrNbreJrApreEchSiNHon = (EditText) findViewById(R.id.input_txt_CrNbreJrApreEchSiNHon);
        SW_CrIsTxIntDegressif = (Switch) findViewById(R.id.SwitchCrIsTxIntDegressif);
        LL_CrNatFrEtudDoss = (LinearLayout) findViewById(R.id.ll_CrNatFrEtudDoss);
        LL_CrNatFraisDeblocCred = (LinearLayout) findViewById(R.id.ll_CrNatFraisDeblocCred);
        LL_CrNatFraisDecaissCred = (LinearLayout) findViewById(R.id.ll_CrNatFraisDecaissCred);
        layout_TauxCrNatFrEtudDoss = (TextInputLayout) findViewById(R.id.input_layout_TauxCrNatFrEtudDoss);
        layout_TauxCrValTxFraisDeblocCred = (TextInputLayout) findViewById(R.id.input_layout_TauxCrValTxFraisDeblocCred);

//


        creditId = intent.getStringExtra(KEY_CREDIT_ID);
        new FetchCreditDetailsAsyncTask().execute();
        deleteButton = (Button) findViewById(R.id.btn_delete_eav);
        deleteButton.setVisibility(View.VISIBLE);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete();
            }
        });
        updateButton = (Button) findViewById(R.id.btn_save_eav);
        cancelButton = (Button) findViewById(R.id.btn_clean);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();

                } else {
                    Toast.makeText(UpdateProduitCredit.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    updateCredit();

                } else {
                    Toast.makeText(UpdateProduitCredit.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });



        tv_plage_CrTypTxInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux d'intérêt crédit";
                    ListPlageDataTICActivity.IS_TO_CREATE_OR_TO_UPDATE = false;
                    Intent i = new Intent(UpdateProduitCredit.this, ListPlageDataTICActivity.class);
                    i.putExtra(KEY_CREDIT_ID, creditId);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(UpdateProduitCredit.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        tv_plage_CrNatFrEtudDoss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux frais d'étude de dossier";
                    ListPlageDataFECActivity.IS_TO_CREATE_OR_TO_UPDATE = false;
                    Intent i = new Intent(UpdateProduitCredit.this, ListPlageDataFECActivity.class);
                    i.putExtra(KEY_CREDIT_ID, creditId);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(UpdateProduitCredit.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

        tv_plage_CrNatFraisDeblocCred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux frais de déblocage";
                    ListPlageDataFDBActivity.IS_TO_CREATE_OR_TO_UPDATE = false;
                    Intent i = new Intent(UpdateProduitCredit.this, ListPlageDataFDBActivity.class);
                    i.putExtra(KEY_CREDIT_ID, creditId);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(UpdateProduitCredit.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        tv_plage_CrNatFraisDecaissCred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux frais de décaissement";
                    ListPlageDataFCXActivity.IS_TO_CREATE_OR_TO_UPDATE = false;
                    Intent i = new Intent(UpdateProduitCredit.this, ListPlageDataFCXActivity.class);
                    i.putExtra(KEY_CREDIT_ID, creditId);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(UpdateProduitCredit.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });



    }

    public void onSwitchButtonClicked(View view) {
        boolean checked1 = ((Switch) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {
//
            case R.id.SwitchCrIsTxGarMemObl:
                if (SW_CrIsTxGarMemObl.isChecked()) {
//                    str = checked1?"Minimum en compte obligatoire":"le minimum en compte n'est pas obligatoire";

                    ET_CrTauxGarMemb.setVisibility(View.VISIBLE);
                }else{
                    ET_CrTauxGarMemb.setVisibility(View.GONE);
                }

                break;
            case R.id.SwitchCrIsCouvPartSOn:
                if (SW_CrIsCouvPartSOn.isChecked()) {

                    ET_CrTxCouvPSOblig.setVisibility(View.VISIBLE);
                }else{
                    ET_CrTxCouvPSOblig.setVisibility(View.GONE);
                }

                break;
//            case R.id.SwitchCrIsAffCollCredOn:
//                if (SW_CrIsAffCollCredOn.isChecked()) {
//
//                    ET_CrNbreAnAncMinCred.setVisibility(View.VISIBLE);
//                }else{
//                    ET_CrNbreAnAncMinCred.setVisibility(View.GONE);
//                }
//
//                break;
            case R.id.SwitchCrIsFraisEtudDossOn:
                if (SW_CrIsFraisEtudDossOn.isChecked()) {
                    str = checked1?"Minimum en compte obligatoire":"le minimum en compte n'est pas obligatoire";

                    LL_CrNatFrEtudDoss.setVisibility(View.VISIBLE);
                    onRadioButtonClicked(rbCrNatFrEtudDossFixe);
                    onRadioButtonClicked(rbCrNatFrEtudDossTaux);
                    onRadioButtonClicked(rbCrNatFrEtudDossPlage);

                }else{
                    LL_CrNatFrEtudDoss.setVisibility(View.GONE);
                    ET_CrValTxFrEtudDoss.setVisibility(View.GONE);
                    JR_CrBaseTxFrEtudDoss.setVisibility(View.GONE);
                    tv_plage_CrNatFrEtudDoss.setVisibility(View.GONE);
                }

                break;
            case R.id.SwitchCrIsFraisDeblocCredOn:
                if (SW_CrIsFraisDeblocCredOn.isChecked()) {
//                    str = checked1?"Minimum en compte obligatoire":"le minimum en compte n'est pas obligatoire";

                    LL_CrNatFraisDeblocCred.setVisibility(View.VISIBLE);
                    onRadioButtonClicked(rbCrNatFraisDeblocCredFixe);
                    onRadioButtonClicked(rbCrNatFraisDeblocCredTaux);
                    onRadioButtonClicked(rbCrNatFraisDeblocCredPlage);
                }else{
                    LL_CrNatFraisDeblocCred.setVisibility(View.GONE);
                    ET_CrValTxFraisDeblocCred.setVisibility(View.GONE);
                    JR_CrBaseTxFraisDeblocCred.setVisibility(View.GONE);
                    tv_plage_CrNatFraisDeblocCred.setVisibility(View.GONE);
                }

                break;
            case R.id.SwitchCrIsFraisDecaissCredOn:
                if (SW_CrIsFraisDecaissCredOn.isChecked()) {
//                    str = checked1?"Minimum en compte obligatoire":"le minimum en compte n'est pas obligatoire";
                    LL_CrNatFraisDecaissCred.setVisibility(View.VISIBLE);
                    onRadioButtonClicked(rbCrNatFraisDecaissCredFixe);
                    onRadioButtonClicked(rbCrNatFraisDecaissCredTaux);
                    onRadioButtonClicked(rbCrNatFraisDecaissCredPlage);
                }else{
                    LL_CrNatFraisDecaissCred.setVisibility(View.GONE);
                    ET_CrValTxFraisDecaissCred.setVisibility(View.GONE);
                    JR_CrBaseTxFraisDecaissCred.setVisibility(View.GONE);
                    tv_plage_CrNatFraisDecaissCred.setVisibility(View.GONE);
                }

                break;
//            case R.id.SwitchTauxInteretAnnuelEAV:
//                if (ev_is_tx_inter_an_obligSwitch.isChecked()){
//                    str = checked1?"Taux interêt obligatoire":"Taux interêt non obligatoire";
//
//                    layout_TauxInteretAnnuelEAV.setVisibility(View.VISIBLE);
//                    layout_BaseInteretAnnuelEAV.setVisibility(View.VISIBLE);
//                    layout_DateValeur.setVisibility(View.VISIBLE);
//                    layout_DateRetrait.setVisibility(View.VISIBLE);
//                }else{
//                    layout_TauxInteretAnnuelEAV.setVisibility(View.GONE);
//                    layout_BaseInteretAnnuelEAV.setVisibility(View.GONE);
//
//                    layout_DateValeur.setVisibility(View.GONE);
//                    layout_DateRetrait.setVisibility(View.GONE);
//                }
//
//
//                break;
//            case R.id.SwitchFraisTenuCpteOnEAV:
//                if (ev_is_agios_onSwitch.isChecked()){
//                    str = checked1?"Frais de tenue de compte activés":"Frais de tenue de compte désactivés";
//
//                    LL_CrNatFrEtudDoss.setVisibility(View.VISIBLE);
//                    //layout_TauxAPreleveCpteEAV.setVisibility(View.VISIBLE);
//                    onRadioButtonClicked(rbEpTypTxInterFixe);
//                    //layout_TauxAPreleveCpteEAV.setVisibility(View.VISIBLE);
//                }else{
//                    layout_TauxAPreleveCpteEAV.setVisibility(View.GONE);
//                    LL_CrNatFrEtudDoss.setVisibility(View.GONE);
//                    blkPlageEav.setVisibility(View.GONE);
//                }
//
//
//                break;
//
        }
//        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    public void onRadioButtonClicked(View view) {
        boolean checked1 = ((RadioButton) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {
//
            case R.id.rbCrTypTxInterFixe:
                if (rbCrTypTxInterFixe.isChecked()) {
//                    str = checked1?"Type Fixe sélectionné":"";
//                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    CrTypTxInter ="F";
                    tv_plage_CrTypTxInter.setVisibility(View.GONE);
                    ET_CrValTxInter.setVisibility(View.VISIBLE);

                }else {
                    CrTypTxInter ="P";
                    tv_plage_CrTypTxInter.setVisibility(View.VISIBLE);
                    ET_CrValTxInter.setVisibility(View.GONE);
                }
                break;
            case R.id.rbCrTypTxInterPlage:
                if (rbCrTypTxInterPlage.isChecked()) {
                    CrTypTxInter ="P";
                    tv_plage_CrTypTxInter.setVisibility(View.VISIBLE);
                    ET_CrValTxInter.setVisibility(View.GONE);

                }else {
                    CrTypTxInter ="F";
                    tv_plage_CrTypTxInter.setVisibility(View.VISIBLE);
                    ET_CrValTxInter.setVisibility(View.GONE);
                }
//
                break;
            case R.id.rbCrNatFrEtudDossFixe:
                if (rbCrNatFrEtudDossFixe.isChecked()) {
//                    str = checked1?"Type Fixe sélectionné":"";
//                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    CrNatFrEtudDoss ="F";

//                    blkPlageEav.setVisibility(View.GONE);
                    if(SW_CrIsFraisEtudDossOn.isChecked()) {
//                        layout_TauxCrNatFrEtudDoss.setVisibility(View.VISIBLE);

                    ET_CrValTxFrEtudDoss.setVisibility(View.VISIBLE);
                    JR_CrBaseTxFrEtudDoss.setVisibility(View.GONE);
                    tv_plage_CrNatFrEtudDoss.setVisibility(View.GONE);
                    ET_CrValTxFrEtudDoss.setHint("Montant des frais d'étude");
                    }
                    else{
//                        layout_TauxCrNatFrEtudDoss.setVisibility(View.GONE);
                        ET_CrValTxFrEtudDoss.setVisibility(View.GONE);
                        JR_CrBaseTxFrEtudDoss.setVisibility(View.GONE);
                        tv_plage_CrNatFrEtudDoss.setVisibility(View.GONE);
                    }
//
                }
//
                break;
            case R.id.rbCrNatFrEtudDossTaux:
                if (rbCrNatFrEtudDossTaux.isChecked()){
//                    str = checked1?"Type Taux sélectionné":"";
//                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    CrNatFrEtudDoss ="T";
                    if(SW_CrIsFraisEtudDossOn.isChecked()) {
//                        layout_TauxCrNatFrEtudDoss.setVisibility(View.VISIBLE);
                        ET_CrValTxFrEtudDoss.setVisibility(View.VISIBLE);
                        JR_CrBaseTxFrEtudDoss.setVisibility(View.VISIBLE);
                        tv_plage_CrNatFrEtudDoss.setVisibility(View.GONE);
                        ET_CrValTxFrEtudDoss.setHint("Taux des frais d'étude");
                    }/*
                    else{

//                        layout_TauxCrNatFrEtudDoss.setVisibility(View.GONE);
                        ET_CrValTxFrEtudDoss.setVisibility(View.GONE);
                        JR_CrBaseTxFrEtudDoss.setVisibility(View.GONE);
                        tv_plage_CrNatFrEtudDoss.setVisibility(View.GONE);
                    }*/
//                    blkPlageEav.setVisibility(View.GONE);
//                    ev_mt_tx_agios_prelevEditText.setHint("Taux mensuel à préléver");
//                    layout_TauxAPreleveCpteEAV.setVisibility(View.VISIBLE);
//                    layout_BaseTauxAPreleveCpteEAV.setVisibility(View.VISIBLE);
//                    tv_config_plage_tiv.setVisibility(View.GONE);
                }
//
//
                break;
            case R.id.rbCrNatFrEtudDossPlage:
                if (rbCrNatFrEtudDossPlage.isChecked()) {
//
//                    str = checked1?"Type Plage sélectionné":"";
//                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
//                    ev_typ_fr_agios ="P";
                    if(SW_CrIsFraisEtudDossOn.isChecked()) {
                        CrNatFrEtudDoss ="P";

                        JR_CrBaseTxFrEtudDoss.setVisibility(View.GONE);
//                        layout_TauxCrNatFrEtudDoss.setVisibility(View.GONE);
                        ET_CrValTxFrEtudDoss.setVisibility(View.GONE);
                        tv_plage_CrNatFrEtudDoss.setVisibility(View.VISIBLE);
                    }/*
                    else{

                        tv_plage_CrNatFrEtudDoss.setVisibility(View.GONE);
                        JR_CrBaseTxFrEtudDoss.setVisibility(View.GONE);
//                        layout_TauxCrNatFrEtudDoss.setVisibility(View.GONE);
                        ET_CrValTxFrEtudDoss.setVisibility(View.GONE);
                    }*/
//                   // blkPlageEav.setVisibility(View.VISIBLE);
//                    layout_TauxAPreleveCpteEAV.setVisibility(View.GONE);
//                    layout_BaseTauxAPreleveCpteEAV.setVisibility(View.GONE);
//                    tv_config_plage_tiv.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rbCrNatFraisDeblocCredFixe:
                if (rbCrNatFraisDeblocCredFixe.isChecked()) {
//                    str = checked1?"Type Fixe sélectionné":"";
//                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    CrNatFraisDeblocCred ="F";

//                    blkPlageEav.setVisibility(View.GONE);
                    if(SW_CrIsFraisDeblocCredOn.isChecked()) {
                        layout_TauxCrValTxFraisDeblocCred.setVisibility(View.VISIBLE);
                        ET_CrValTxFraisDeblocCred.setVisibility(View.VISIBLE);
                        JR_CrBaseTxFraisDeblocCred.setVisibility(View.GONE);
                        tv_plage_CrNatFraisDeblocCred.setVisibility(View.GONE);
                        ET_CrValTxFraisDeblocCred.setHint("Montant des frais de déblocge");
                    }else{

                        layout_TauxCrValTxFraisDeblocCred.setVisibility(View.GONE);
                        ET_CrValTxFraisDeblocCred.setVisibility(View.GONE);
                        JR_CrBaseTxFraisDeblocCred.setVisibility(View.GONE);
                        tv_plage_CrNatFraisDeblocCred.setVisibility(View.GONE);
                    }
//                    layout_TauxAPreleveCpteEAV.setVisibility(View.VISIBLE);
//                    layout_BaseTauxAPreleveCpteEAV.setVisibility(View.GONE);
//                    tv_config_plage_tiv.setVisibility(View.GONE);
//
                }
//
                break;
            case R.id.rbCrNatFraisDeblocCredTaux:
                if (rbCrNatFraisDeblocCredTaux.isChecked()){
//                    str = checked1?"Type Taux sélectionné":"";
//                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    CrNatFraisDeblocCred ="T";
                    if(SW_CrIsFraisDeblocCredOn.isChecked()) {
                        layout_TauxCrValTxFraisDeblocCred.setVisibility(View.VISIBLE);
                        ET_CrValTxFraisDeblocCred.setVisibility(View.VISIBLE);
                        JR_CrBaseTxFraisDeblocCred.setVisibility(View.VISIBLE);
                        tv_plage_CrNatFraisDeblocCred.setVisibility(View.GONE);
                        ET_CrValTxFraisDeblocCred.setHint("Taux des frais de déblocge");
                    }else{

                        layout_TauxCrValTxFraisDeblocCred.setVisibility(View.GONE);
                        ET_CrValTxFraisDeblocCred.setVisibility(View.GONE);
                        JR_CrBaseTxFraisDeblocCred.setVisibility(View.GONE);
                        tv_plage_CrNatFraisDeblocCred.setVisibility(View.GONE);
                    }
                }
//
//
                break;
            case R.id.rbCrNatFraisDeblocCredPlage:
                if (rbCrNatFraisDeblocCredPlage.isChecked()) {
//
//                    str = checked1?"Type Plage sélectionné":"";
//                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
//                    ev_typ_fr_agios ="P";
                    CrNatFraisDeblocCred ="P";
                    if(SW_CrIsFraisDeblocCredOn.isChecked()) {
                        JR_CrBaseTxFraisDeblocCred.setVisibility(View.GONE);
                        layout_TauxCrValTxFraisDeblocCred.setVisibility(View.GONE);
                        ET_CrValTxFraisDeblocCred.setVisibility(View.GONE);
                        tv_plage_CrNatFraisDeblocCred.setVisibility(View.VISIBLE);
                    }else{

                        JR_CrBaseTxFraisDeblocCred.setVisibility(View.GONE);
                        layout_TauxCrValTxFraisDeblocCred.setVisibility(View.GONE);
                        ET_CrValTxFraisDeblocCred.setVisibility(View.GONE);
                        tv_plage_CrNatFraisDeblocCred.setVisibility(View.GONE);
                    }
//                   // blkPlageEav.setVisibility(View.VISIBLE);
//                    layout_TauxAPreleveCpteEAV.setVisibility(View.GONE);
//                    layout_BaseTauxAPreleveCpteEAV.setVisibility(View.GONE);
//                    tv_config_plage_tiv.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rbCrNatFraisDecaissCredFixe:
                if (rbCrNatFraisDecaissCredFixe.isChecked()) {
//                    str = checked1?"Type Fixe sélectionné":"";
//                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    CrNatFraisDecaissCred ="F";

//                    blkPlageEav.setVisibility(View.GONE);
                    if(SW_CrIsFraisDecaissCredOn.isChecked()) {
//                        layout_TauxCrValTxFraisDeblocCred.setVisibility(View.VISIBLE);
                        ET_CrValTxFraisDecaissCred.setVisibility(View.VISIBLE);
                        JR_CrBaseTxFraisDecaissCred.setVisibility(View.GONE);
                        tv_plage_CrNatFraisDecaissCred.setVisibility(View.GONE);
                        ET_CrValTxFraisDecaissCred.setHint("Montant des frais de décaissement");
                    }else{

//                        layout_TauxCrValTxFraisDeblocCred.setVisibility(View.GONE);
                        ET_CrValTxFraisDecaissCred.setVisibility(View.GONE);
                        JR_CrBaseTxFraisDecaissCred.setVisibility(View.GONE);
                        tv_plage_CrNatFraisDecaissCred.setVisibility(View.GONE);
                    }
//                    layout_TauxAPreleveCpteEAV.setVisibility(View.VISIBLE);
//                    layout_BaseTauxAPreleveCpteEAV.setVisibility(View.GONE);
//                    tv_config_plage_tiv.setVisibility(View.GONE);
//
                }
//
                break;
            case R.id.rbCrNatFraisDecaissCredTaux:
                if (rbCrNatFraisDecaissCredTaux.isChecked()){
//                    str = checked1?"Type Taux sélectionné":"";
//                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    CrNatFraisDecaissCred ="T";
                    if(SW_CrIsFraisDecaissCredOn.isChecked()) {
//                        layout_TauxCrValTxFraisDeblocCred.setVisibility(View.VISIBLE);
                        ET_CrValTxFraisDecaissCred.setVisibility(View.VISIBLE);
                        JR_CrBaseTxFraisDecaissCred.setVisibility(View.VISIBLE);
                        tv_plage_CrNatFraisDecaissCred.setVisibility(View.GONE);
                        ET_CrValTxFraisDecaissCred.setHint("Taux des frais de décaissement");
                    }else{

//                        layout_TauxCrValTxFraisDeblocCred.setVisibility(View.GONE);
                        ET_CrValTxFraisDecaissCred.setVisibility(View.GONE);
                        JR_CrBaseTxFraisDecaissCred.setVisibility(View.GONE);
                        tv_plage_CrNatFraisDecaissCred.setVisibility(View.GONE);
                    }
                }
//
//
                break;
            case R.id.rbCrNatFraisDecaissCredPlage:
                if (rbCrNatFraisDecaissCredPlage.isChecked()) {
//
//                    str = checked1?"Type Plage sélectionné":"";
//                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
//                    ev_typ_fr_agios ="P";
                    CrNatFraisDecaissCred ="P";
                    if(SW_CrIsFraisDecaissCredOn.isChecked()) {
                        JR_CrBaseTxFraisDecaissCred.setVisibility(View.GONE);
//                        layout_TauxCrValTxFraisDeblocCred.setVisibility(View.GONE);
                        ET_CrValTxFraisDecaissCred.setVisibility(View.GONE);
                        tv_plage_CrNatFraisDecaissCred.setVisibility(View.VISIBLE);
                    }else{

                        JR_CrBaseTxFraisDecaissCred.setVisibility(View.GONE);
//                        layout_TauxCrValTxFraisDeblocCred.setVisibility(View.GONE);
                        ET_CrValTxFraisDecaissCred.setVisibility(View.GONE);
                        tv_plage_CrNatFraisDecaissCred.setVisibility(View.GONE);
                    }
                }
                break;
        }
//        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }


    /**
     * Fetches single movie details from the server
     */
    private class FetchCreditDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateProduitCredit.this);
            pDialog.setMessage("Veuillez patienter...");
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_CREDIT_ID, creditId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_credit_details.php", "GET", httpParams);
            try {
//                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject creditJson;
//                if (success == 1) {
                    //Parse the JSON response
//                    creditJson = jsonObject.getJSONObject(KEY_DATA);


                monProduitCredit = new Credit(jsonObject.getString(KEY_CREDIT_Code),jsonObject.getString(KEY_CREDIT_Libelle),jsonObject.getString(KEY_CREDIT_DureeMin),
                        jsonObject.getString(KEY_CREDIT_DureeMax),jsonObject.getString(KEY_CREDIT_NaturePas),jsonObject.getString(KEY_CREDIT_NbreUPas),
                        jsonObject.getString(KEY_CREDIT_TypTxInter),jsonObject.getString(KEY_CREDIT_ValTxInter),
                        Boolean.parseBoolean(jsonObject.getString(KEY_CREDIT_IsTxIntNeg)),jsonObject.getString(KEY_CREDIT_NbreAvalDmde),
                        jsonObject.getString(KEY_CREDIT_NbreMinAvalExig),jsonObject.getString(KEY_CREDIT_TxCouvCrAval),
                        Boolean.parseBoolean(jsonObject.getString(KEY_CREDIT_IsTxCouvAvalOblig)), Boolean.parseBoolean(jsonObject.getString(KEY_CREDIT_IsCautionMorAvalAcc)),
                        Boolean.parseBoolean(jsonObject.getString(KEY_CREDIT_IsGarBloqCptOblig)),Boolean.parseBoolean(jsonObject.getString(KEY_CREDIT_IsGarCptEAVOn)),
                        Boolean.parseBoolean(jsonObject.getString(KEY_CREDIT_IsGarCptEATOn)),Boolean.parseBoolean(jsonObject.getString(KEY_CREDIT_IsGarCptEAPOn)),
                        jsonObject.getString(KEY_CREDIT_MtMaxSansAval),Boolean.parseBoolean(jsonObject.getString(KEY_CREDIT_IsAvalSansCredOn)),
                        Boolean.parseBoolean(jsonObject.getString(KEY_CREDIT_IsTxGarMemObl)),
                        jsonObject.getString(KEY_CREDIT_TauxGarMemb),Boolean.parseBoolean(jsonObject.getString(KEY_CREDIT_IsPersMorAvalOn)),
                        Boolean.parseBoolean(jsonObject.getString(KEY_CREDIT_IsCouvPartSOn)),
                        jsonObject.getString(KEY_CREDIT_TxCouvPSOblig),Boolean.parseBoolean(jsonObject.getString(KEY_CREDIT_IsAffCollCredOn)),
                        jsonObject.getString(KEY_CREDIT_NbreAnAncMinCred),
                        Boolean.parseBoolean(jsonObject.getString(KEY_CREDIT_NbAnAncNeg)),jsonObject.getString(KEY_CREDIT_MtPlafondMax),
                        Boolean.parseBoolean(jsonObject.getString(KEY_CREDIT_IsMtPlafCredLeve)),
                        Boolean.parseBoolean(jsonObject.getString(KEY_CREDIT_IsGarMatExige)),
                        Boolean.parseBoolean(jsonObject.getString(KEY_CREDIT_IsFraisEtudDossOn)),
                        jsonObject.getString(KEY_CREDIT_NatFrEtudDoss),jsonObject.getString(KEY_CREDIT_ValTxFrEtudDoss),
                        jsonObject.getString(KEY_CREDIT_BaseTxFrEtudDoss),Boolean.parseBoolean(jsonObject.getString(KEY_CREDIT_IsFraisDeblocCredOn)),
                        jsonObject.getString(KEY_CREDIT_NatFraisDeblocCred),
                        jsonObject.getString(KEY_CREDIT_ValTxFraisDeblocCred),
                        jsonObject.getString(KEY_CREDIT_BaseTxFraisDeblocCred),Boolean.parseBoolean(jsonObject.getString(KEY_CREDIT_IsFraisDecaissCredOn)),
                        jsonObject.getString(KEY_CREDIT_NatFraisDecaissCred),jsonObject.getString(KEY_CREDIT_ValTxFraisDecaissCred),
                        jsonObject.getString(KEY_CREDIT_BaseFraisDecaissCred),
                        Boolean.parseBoolean(jsonObject.getString(KEY_CREDIT_IsFraisEtudByDAV)),Boolean.parseBoolean(jsonObject.getString(KEY_CREDIT_IsFraisDeblocByDAV)),
                        Boolean.parseBoolean(jsonObject.getString(KEY_CREDIT_IsFraisDecaissByDAV)),
                        Boolean.parseBoolean(jsonObject.getString(KEY_CREDIT_IsModDecaissByObjet)),
                        Boolean.parseBoolean(jsonObject.getString(KEY_CREDIT_IsDeblocTransfDAVOn)),
                        Boolean.parseBoolean(jsonObject.getString(KEY_CREDIT_IsMtPlafByObjet)),
                        jsonObject.getString(KEY_CREDIT_ModeRemb),Boolean.parseBoolean(jsonObject.getString(KEY_CREDIT_IsCptEATRemCredOn)),
                        Boolean.parseBoolean(jsonObject.getString(KEY_CREDIT_IsCptEAPRemCredOn)),
                        Boolean.parseBoolean(jsonObject.getString(KEY_CREDIT_IsInterOffSiCapRembAnt)),
                        jsonObject.getString(KEY_CREDIT_TxInterEchNHon), jsonObject.getString(KEY_CREDIT_BaseInterEchNHon),
                        jsonObject.getString(KEY_CREDIT_PlanningRembCred),Boolean.parseBoolean(jsonObject.getString(KEY_CREDIT_IsRappDatEchCred)),
                        jsonObject.getString(KEY_CREDIT_ModelTextRappEchRemb),
                        jsonObject.getString(KEY_CREDIT_NbreJrAvantDatEch),jsonObject.getString(KEY_CREDIT_NbreJrApreEchSiNHon),
                        jsonObject.getString(KEY_CREDIT_User),
                        null,
                        MyData.USER_ID+"",
                        null,
                        MyData.CAISSE_ID+"",
                        null,
                        Boolean.parseBoolean(jsonObject.getString(KEY_CrIsTxIntDegressif)));

  /*                  st_CcCode = jsonObject.getString(KEY_CcCode);
                    st_CcCode = creditJson.getString(KEY_CcCode);
                    st_CcLibelle = creditJson.getString(KEY_CcLibelle);
                    st_CcIsDecouvOn = Boolean.parseBoolean(creditJson.getString(KEY_CcIsDecouvOn));
                    st_CcMtMaxDecouv = creditJson.getString(KEY_CcMtMaxDecouv);
                    st_CcIsMaxDecouvNeg = Boolean.parseBoolean(creditJson.getString(KEY_CcIsMaxDecouvNeg));
                    st_CcNatureTxIntDecouv = creditJson.getString(KEY_CcNatureTxIntDecouv);
                    st_CcValTxIntDecouv = creditJson.getString(KEY_CcValTxIntDecouv);
                    st_CcPlageTxIntDecouvFrom = creditJson.getString(KEY_CcPlageTxIntDecouvFrom);
                    st_CcPlageTxIntDecouvTo = creditJson.getString(KEY_CcPlageTxIntDecouvTo);
                    st_CcDureeMaxDecouv = creditJson.getString(KEY_CcDureeMaxDecouv);
                    st_CcNatureTypDureDecouv = creditJson.getString(KEY_CcNatureTypDureDecouv);
                    st_CcNatureTxMtAgio = creditJson.getString(KEY_CcNatureTxMtAgio);
                    st_CcValTxMtAgio = creditJson.getString(KEY_CcValTxMtAgio);
                    st_CcPlageTxMtAgioFrom = creditJson.getString(KEY_CcPlageTxMtAgioFrom);
                    st_CcPlageTxMtAgioTo = creditJson.getString(KEY_CcPlageTxMtAgioTo);
                    st_CcNatBaseAgio = creditJson.getString(KEY_CcNatBaseAgio);
                    st_CcIsChequierM1On = Boolean.parseBoolean(creditJson.getString(KEY_CcIsChequierM1On));
                    st_CcNbPagesCheqM1 = creditJson.getString(KEY_CcNbPagesCheqM1);
                    st_CcPrixVteCheqM1 = creditJson.getString(KEY_CcPrixVteCheqM1);
                    st_CcIsChequierM2On = Boolean.parseBoolean(creditJson.getString(KEY_CcIsChequierM2On));
                    st_CcNbPagesCheqM2 = creditJson.getString(KEY_CcNbPagesCheqM2);
                    st_CcPrixVteCheqM2 = creditJson.getString(KEY_CcPrixVteCheqM2);
                    st_CcIsChequierM3On = Boolean.parseBoolean(creditJson.getString(KEY_CcIsChequierM3On));
                    st_CcNbPagesCheqM3 = creditJson.getString(KEY_CcNbPagesCheqM3);
                    st_CcPrixVteCheqM3 = creditJson.getString(KEY_CcPrixVteCheqM3);
                    st_CcDureeValidCheq = creditJson.getString(KEY_CcDureeValidCheq);
                    st_CcNbMinSignatChq = creditJson.getString(KEY_CcNbMinSignatChq);
                    st_CcIsTxCommMvmOper = Boolean.parseBoolean(creditJson.getString(KEY_CcIsTxCommMvmOper));
                    st_CcNatTxComm = creditJson.getString(KEY_CcNatTxComm);
                    st_CcValTxCommMvm = creditJson.getString(KEY_CcValTxCommMvm);
                    st_CcPlageTxCommMvmFrom = creditJson.getString(KEY_CcPlageTxCommMvmFrom);
                    st_CcPlageTxCommMvmTo = creditJson.getString(KEY_CcPlageTxCommMvmTo);
*/

//                }
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


                    ET_CrCode.setText(monProduitCredit.getCrCode());
                    ET_CrLibelle.setText(monProduitCredit.getCrLibelle());
                    ET_CrDureeMin.setText(monProduitCredit.getCrDureeMin());
                    ET_CrDureeMax.setText(monProduitCredit.getCrDureeMax());

                  /*  if (monProduitCredit.getCrNaturePas().equals("F")){
                        rbCrNaturePasFixe.setChecked(true);
                        onRadioButtonClicked(rbCrNaturePasFixe);
                    }else if (monProduitCredit.getCrNaturePas().equals("S")){
                        rbCrNaturePasSaut.setChecked(true);
                        onRadioButtonClicked(rbCrNaturePasSaut);
                    }
                    */
                    if (monProduitCredit.getCrTypTxInter().equals("F")){
                        rbCrTypTxInterFixe.setChecked(true);
                        onRadioButtonClicked(rbCrTypTxInterFixe);
                    }else if (monProduitCredit.getCrTypTxInter().equals("P")){
                        rbCrTypTxInterPlage.setChecked(true);
                        onRadioButtonClicked(rbCrTypTxInterPlage);
                    }
                    ET_CrValTxInter.setText(monProduitCredit.getCrValTxInter());
                    SW_CrIsTxIntNeg.setChecked(monProduitCredit.getCrIsTxIntNeg());
                    onSwitchButtonClicked(SW_CrIsTxIntNeg);

                    ET_CrNbreAvalDmde.setText(monProduitCredit.getCrNbreAvalDmde());
                    ET_CrNbreMinAvalExig.setText(monProduitCredit.getCrNbreMinAvalExig());
                    ET_CrTxCouvCrAval.setText(monProduitCredit.getCrTxCouvCrAval());
                    SW_CrIsTxCouvAvalOblig.setChecked(monProduitCredit.getCrIsTxCouvAvalOblig());
                    onSwitchButtonClicked(SW_CrIsTxCouvAvalOblig);
                    SW_CrIsCautionMorAvalAcc.setChecked(monProduitCredit.getCrIsCautionMorAvalAcc());
                    onSwitchButtonClicked(SW_CrIsCautionMorAvalAcc);
                    SW_CrIsGarCptEAVOn.setChecked(monProduitCredit.getCrIsGarCptEAVOn());
                    onSwitchButtonClicked(SW_CrIsGarCptEAVOn);
                    SW_CrIsGarCptEATOn.setChecked(monProduitCredit.getCrIsGarCptEATOn());
                    onSwitchButtonClicked(SW_CrIsGarCptEATOn);
                    SW_CrIsGarCptEAPOn.setChecked(monProduitCredit.getCrIsGarCptEAPOn());
                    onSwitchButtonClicked(SW_CrIsGarCptEAPOn);
                    ET_CrMtMaxSansAval.setText(monProduitCredit.getCrMtMaxSansAval());

                    SW_CrIsAvalSansCredOn.setChecked(monProduitCredit.getCrIsAvalSansCredOn());
                    onSwitchButtonClicked(SW_CrIsAvalSansCredOn);
                    SW_CrIsTxGarMemObl.setChecked(monProduitCredit.getCrIsTxGarMemObl());
                    onSwitchButtonClicked(SW_CrIsTxGarMemObl);
                    ET_CrTauxGarMemb.setText(monProduitCredit.getCrTauxGarMemb());
                    SW_CrIsPersMorAvalOn.setChecked(monProduitCredit.getCrIsPersMorAvalOn());
                    onSwitchButtonClicked(SW_CrIsPersMorAvalOn);
                    SW_CrIsCouvPartSOn.setChecked(monProduitCredit.getCrIsCouvPartSOn());
                    onSwitchButtonClicked(SW_CrIsCouvPartSOn);
                    ET_CrTxCouvPSOblig.setText(monProduitCredit.getCrTxCouvPSOblig());
                    SW_CrIsAffCollCredOn.setChecked(monProduitCredit.getCrIsAffCollCredOn());
                    onSwitchButtonClicked(SW_CrIsAffCollCredOn);
                    ET_CrNbreAnAncMinCred.setText(monProduitCredit.getCrNbreAnAncMinCred());
                    SW_CrNbAnAncNeg.setChecked(monProduitCredit.getCrNbAnAncNeg());
                    onSwitchButtonClicked(SW_CrNbAnAncNeg);
                    ET_CrMtPlafondMax.setText(monProduitCredit.getCrMtPlafondMax());
                    SW_CrIsMtPlafCredLeve.setChecked(monProduitCredit.getCrIsMtPlafCredLeve());
                    onSwitchButtonClicked(SW_CrIsMtPlafCredLeve);
                    SW_CrIsGarMatExige.setChecked(monProduitCredit.getCrIsGarMatExige());
                    onSwitchButtonClicked(SW_CrIsGarMatExige);

                    if (monProduitCredit.getCrNatFrEtudDoss().equals("F")){
                        rbCrNatFrEtudDossFixe.setChecked(true);
                        onRadioButtonClicked(rbCrNatFrEtudDossFixe);
                    }else if (monProduitCredit.getCrNatFrEtudDoss().equals("T")){
                        rbCrNatFrEtudDossTaux.setChecked(true);
                        onRadioButtonClicked(rbCrNatFrEtudDossTaux);
                    }else if (monProduitCredit.getCrNatFrEtudDoss().equals("P")){
                        rbCrNatFrEtudDossPlage.setChecked(true);
                        onRadioButtonClicked(rbCrNatFrEtudDossPlage);
                    }

                    SW_CrIsFraisEtudDossOn.setChecked(monProduitCredit.getCrIsFraisEtudDossOn());
                    onSwitchButtonClicked(SW_CrIsFraisEtudDossOn);

                    ET_CrValTxFrEtudDoss.setText(monProduitCredit.getCrValTxFrEtudDoss());
                    JR_CrBaseTxFrEtudDoss.setText(monProduitCredit.getCrBaseTxFrEtudDoss());

                    SW_CrIsFraisDeblocCredOn.setChecked(monProduitCredit.getCrIsFraisDeblocCredOn());
                    onSwitchButtonClicked(SW_CrIsFraisDeblocCredOn);
                    if (monProduitCredit.getCrNatFraisDeblocCred().equals("F")){
                        rbCrNatFraisDeblocCredFixe.setChecked(true);
                        onRadioButtonClicked(rbCrNatFraisDeblocCredFixe);
                    }else if (monProduitCredit.getCrNatFraisDeblocCred().equals("T")){
                        rbCrNatFraisDeblocCredTaux.setChecked(true);
                        onRadioButtonClicked(rbCrNatFraisDeblocCredTaux);
                    }else if (monProduitCredit.getCrNatFraisDeblocCred().equals("P")){
                        rbCrNatFraisDeblocCredPlage.setChecked(true);
                        onRadioButtonClicked(rbCrNatFraisDeblocCredPlage);
                    }

                    ET_CrValTxFraisDeblocCred.setText(monProduitCredit.getCrValTxFraisDeblocCred());
                    JR_CrBaseTxFraisDeblocCred.setText(monProduitCredit.getCrBaseTxFraisDeblocCred());

                    SW_CrIsFraisDecaissCredOn.setChecked(monProduitCredit.getCrIsFraisDecaissCredOn());
                    onSwitchButtonClicked(SW_CrIsFraisDecaissCredOn);
                    if (monProduitCredit.getCrNatFraisDecaissCred().equals("F")){
                        rbCrNatFraisDecaissCredFixe.setChecked(true);
                        onRadioButtonClicked(rbCrNatFraisDecaissCredFixe);
                    }else if (monProduitCredit.getCrNatFraisDecaissCred().equals("T")){
                        rbCrNatFraisDecaissCredTaux.setChecked(true);
                        onRadioButtonClicked(rbCrNatFraisDecaissCredTaux);
                    }else if (monProduitCredit.getCrNatFraisDecaissCred().equals("P")){
                        rbCrNatFraisDecaissCredPlage.setChecked(true);
                        onRadioButtonClicked(rbCrNatFraisDecaissCredPlage);
                    }

                    ET_CrValTxFraisDecaissCred.setText(monProduitCredit.getCrValTxFraisDecaissCred());
                    JR_CrBaseTxFraisDecaissCred.setText(monProduitCredit.getCrBaseFraisDecaissCred());

                    SW_CrIsFraisEtudByDAV.setChecked(monProduitCredit.getCrIsFraisEtudByDAV());
                    onSwitchButtonClicked(SW_CrIsFraisEtudByDAV);
                    SW_CrIsFraisDeblocByDAV.setChecked(monProduitCredit.getCrIsFraisDeblocByDAV());
                    onSwitchButtonClicked(SW_CrIsFraisDeblocByDAV);
                    SW_CrIsFraisDecaissByDAV.setChecked(monProduitCredit.getCrIsFraisDecaissByDAV());
                    onSwitchButtonClicked(SW_CrIsFraisDecaissByDAV);
                    SW_CrIsModDecaissByObjet.setChecked(monProduitCredit.getCrIsModDecaissByObjet());
                    onSwitchButtonClicked(SW_CrIsModDecaissByObjet);
                    SW_CrIsDeblocTransfDAVOn.setChecked(monProduitCredit.getCrIsDeblocTransfDAVOn());
                    onSwitchButtonClicked(SW_CrIsDeblocTransfDAVOn);
                    SW_CrIsMtPlafByObjet.setChecked(monProduitCredit.getCrIsMtPlafByObjet());
                    onSwitchButtonClicked(SW_CrIsMtPlafByObjet);
                    JR_CrModeRemb.setText(monProduitCredit.getCrModeRemb());
                    SW_CrIsCptEATRemCredOn.setChecked(monProduitCredit.getCrIsCptEATRemCredOn());
                    onSwitchButtonClicked(SW_CrIsCptEATRemCredOn);
                    SW_CrIsCptEAPRemCredOn.setChecked(monProduitCredit.getCrIsCptEAPRemCredOn());
                    onSwitchButtonClicked(SW_CrIsCptEAPRemCredOn);
                    SW_CrIsInterOffSiCapRembAnt.setChecked(monProduitCredit.getCrIsInterOffSiCapRembAnt());
                    onSwitchButtonClicked(SW_CrIsInterOffSiCapRembAnt);
                    ET_CrTxInterEchNHon.setText(monProduitCredit.getCrTxInterEchNHon());
                    JR_CrBaseInterEchNHon.setText(monProduitCredit.getCrBaseInterEchNHon());
                    JR_CrPlanningRembCred.setText(monProduitCredit.getCrPlanningRembCred());

                    SW_CrIsRappDatEchCred.setChecked(monProduitCredit.getCrIsRappDatEchCred());
                    SW_CrIsTxIntDegressif.setChecked(monProduitCredit.getCrIsTxIntDegressif());
                    onSwitchButtonClicked(SW_CrIsRappDatEchCred);
                    ET_CrModelTextRappEchRemb.setText(monProduitCredit.getCrModelTextRappEchRemb());
                    ET_CrNbreJrAvantDatEch.setText(monProduitCredit.getCrNbreJrAvantDatEch());
                    ET_CrNbreJrApreEchSiNHon.setText(monProduitCredit.getCrNbreJrApreEchSiNHon());

monProduitCredit= null; //a revoir
                }
            });
        }


    }

    /**
     * Displays an alert dialogue to confirm the deletion
     */
    private void confirmDelete() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                UpdateProduitCredit.this);
        alertDialogBuilder.setMessage("Voulez-vous vraiment supprimer ce produit crédit ?");
        alertDialogBuilder.setPositiveButton("Supprimer",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                            //If the user confirms deletion, execute DeleteCreditAsyncTask
                            new DeleteCreditAsyncTask().execute();
                        } else {
                            Toast.makeText(UpdateProduitCredit.this,
                                    "Unable to connect to internet",
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                });

        alertDialogBuilder.setNegativeButton("Annuler", null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * AsyncTask to delete a movie
     */
    private class DeleteCreditAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateProduitCredit.this);
            pDialog.setMessage("Suppression du produit crédit...");
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Set movie_id parameter in request
            httpParams.put(KEY_CREDIT_ID, creditId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "delete_credit.php", "POST", httpParams);
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
                        Toast.makeText(UpdateProduitCredit.this,
                                "Produit crédit Supprimé", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie deletion
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(UpdateProduitCredit.this,
                                "Some error occurred while deleting Crédit",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    /**
     * Checks whether all files are filled. If so then calls UpdateCreditAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void updateCredit() {


        if (!STRING_EMPTY.equals(ET_CrCode.getText().toString().trim()) &&
                !STRING_EMPTY.equals(ET_CrLibelle.getText().toString().trim()) &&
                !STRING_EMPTY.equals(ET_CrDureeMin.getText().toString().trim()) &&
                !STRING_EMPTY.equals(ET_CrDureeMax.getText().toString().trim())
        ){
            monProduitCredit = new Credit(ET_CrCode.getText().toString(),ET_CrLibelle.getText().toString(),ET_CrDureeMin.getText().toString(),
                    ET_CrDureeMax.getText().toString(),"F","1",CrTypTxInter,ET_CrValTxInter.getText().toString(),
                    SW_CrIsTxIntNeg.isChecked(),ET_CrNbreAvalDmde.getText().toString(),ET_CrNbreMinAvalExig.getText().toString(),
                    ET_CrTxCouvCrAval.getText().toString(), SW_CrIsTxCouvAvalOblig.isChecked(), SW_CrIsCautionMorAvalAcc.isChecked(),
                    SW_CrIsGarBloqCptOblig.isChecked(),SW_CrIsGarCptEAVOn.isChecked(),SW_CrIsGarCptEATOn.isChecked(),SW_CrIsGarCptEAPOn.isChecked(),
                    ET_CrMtMaxSansAval.getText().toString(),SW_CrIsAvalSansCredOn.isChecked(),SW_CrIsTxGarMemObl.isChecked(),
                    ET_CrTauxGarMemb.getText().toString(),SW_CrIsPersMorAvalOn.isChecked(),SW_CrIsCouvPartSOn.isChecked(),
                    ET_CrTxCouvPSOblig.getText().toString(),SW_CrIsAffCollCredOn.isChecked(),ET_CrNbreAnAncMinCred.getText().toString(),
                    SW_CrNbAnAncNeg.isChecked(),ET_CrMtPlafondMax.getText().toString(),SW_CrIsMtPlafCredLeve.isChecked(),
                    SW_CrIsGarMatExige.isChecked(),SW_CrIsFraisEtudDossOn.isChecked(),CrNatFrEtudDoss,ET_CrValTxFrEtudDoss.getText().toString(),
                    JR_CrBaseTxFrEtudDoss.getText().toString(),SW_CrIsFraisDeblocCredOn.isChecked(),CrNatFraisDeblocCred,
                    ET_CrValTxFraisDeblocCred.getText().toString(),
                    JR_CrBaseTxFraisDeblocCred.getText().toString(),SW_CrIsFraisDecaissCredOn.isChecked(),
                    CrNatFraisDecaissCred,ET_CrValTxFraisDecaissCred.getText().toString(), JR_CrBaseTxFraisDecaissCred.getText().toString(),
                    SW_CrIsFraisEtudByDAV.isChecked(),SW_CrIsFraisDeblocByDAV.isChecked(),SW_CrIsFraisDecaissByDAV.isChecked(),
                    SW_CrIsModDecaissByObjet.isChecked(),SW_CrIsDeblocTransfDAVOn.isChecked(),SW_CrIsMtPlafByObjet.isChecked(),
                    JR_CrModeRemb.getText().toString(),SW_CrIsCptEATRemCredOn.isChecked(),SW_CrIsCptEAPRemCredOn.isChecked(),
                    SW_CrIsInterOffSiCapRembAnt.isChecked(),ET_CrTxInterEchNHon.getText().toString(), JR_CrBaseInterEchNHon.getText().toString(),
                    JR_CrPlanningRembCred.getText().toString(),SW_CrIsRappDatEchCred.isChecked(),ET_CrModelTextRappEchRemb.getText().toString(),
                    ET_CrNbreJrAvantDatEch.getText().toString(),ET_CrNbreJrApreEchSiNHon.getText().toString(),
                    MyData.USER_ID+"",
                    null,
                    MyData.USER_ID+"",
                    null,
                    MyData.CAISSE_ID+"",
                    null,
                    SW_CrIsTxIntDegressif.isChecked());


//    base_ev_mt_tx_agios_prelev = mySpinnerLocalite.getText().toString();
//    base_ev_tx_inter_an = mySpinnerBaseTxIntMensuel.getText().toString();

//to manage plage data
            //Frais d'étude
            for (int i=0; i<plageDataListFEC.size();i++){
                tabPlageDebutFEC +=";"+plageDataListFEC.get(i).getPdValDe().replaceAll(",", "").trim();
                tabPlageFinFEC +=";"+plageDataListFEC.get(i).getPdValA().replaceAll(",", "").trim();
                tabPlageValeurFEC +=";"+plageDataListFEC.get(i).getPdValTaux().replaceAll(",", "").trim();
                tabPlageBaseFEC +=";"+plageDataListFEC.get(i).getPdBase().replaceAll(",", "").trim();
                tabPlageNatureFEC +=";"+plageDataListFEC.get(i).getPdNature().replaceAll(",", "").trim();
            }
//Déblocage
            for (int i=0; i<plageDataListFDB.size();i++){
                tabPlageDebutFDB +=";"+plageDataListFDB.get(i).getPdValDe().replaceAll(",", "").trim();
                tabPlageFinFDB +=";"+plageDataListFDB.get(i).getPdValA().replaceAll(",", "").trim();
                tabPlageValeurFDB +=";"+plageDataListFDB.get(i).getPdValTaux().replaceAll(",", "").trim();
                tabPlageBaseFDB +=";"+plageDataListFDB.get(i).getPdBase().replaceAll(",", "").trim();
                tabPlageNatureFDB +=";"+plageDataListFDB.get(i).getPdNature().replaceAll(",", "").trim();
            }
//Décaissement
            for (int i=0; i<plageDataListFCX.size();i++){
                tabPlageDebutFCX +=";"+plageDataListFCX.get(i).getPdValDe().replaceAll(",", "").trim();
                tabPlageFinFCX +=";"+plageDataListFCX.get(i).getPdValA().replaceAll(",", "").trim();
                tabPlageValeurFCX +=";"+plageDataListFCX.get(i).getPdValTaux().replaceAll(",", "").trim();
                tabPlageBaseFCX +=";"+plageDataListFCX.get(i).getPdBase().replaceAll(",", "").trim();
                tabPlageNatureFCX +=";"+plageDataListFCX.get(i).getPdNature().replaceAll(",", "").trim();
            }
            //Taux d'intérêt de crédit
            for (int i=0; i<plageDataListTIC.size();i++){
                tabPlageDebutTIC +=";"+plageDataListTIC.get(i).getPdValDe().replaceAll(",", "").trim();
                tabPlageFinTIC +=";"+plageDataListTIC.get(i).getPdValA().replaceAll(",", "").trim();
                tabPlageValeurTIC +=";"+plageDataListTIC.get(i).getPdValTaux().replaceAll(",", "").trim();
                tabPlageBaseTIC +=";"+plageDataListTIC.get(i).getPdBase().replaceAll(",", "").trim();
                tabPlageNatureTIC +=";"+plageDataListTIC.get(i).getPdNature().replaceAll(",", "").trim();
            }


            new UpdateCreditAsyncTask().execute();
        } else {
            Toast.makeText(UpdateProduitCredit.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();

        }


    }
    /**
     * AsyncTask for updating a movie details
     */

    private class UpdateCreditAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateProduitCredit.this);
            pDialog.setMessage("Mise à jour Crédit...");
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_CREDIT_ID, creditId);

//            httpParams.put(monProduitCredit.KEY_CREDIT_Numero, monProduitCredit.getCrNumero());
            httpParams.put(monProduitCredit.KEY_CREDIT_Code, monProduitCredit.getCrCode());
            httpParams.put(monProduitCredit.KEY_CREDIT_Libelle, monProduitCredit.getCrLibelle());
            httpParams.put(monProduitCredit.KEY_CREDIT_DureeMin, monProduitCredit.getCrDureeMin());
            httpParams.put(monProduitCredit.KEY_CREDIT_DureeMax, monProduitCredit.getCrDureeMax());
            httpParams.put(monProduitCredit.KEY_CREDIT_NaturePas, monProduitCredit.getCrNaturePas());
            httpParams.put(monProduitCredit.KEY_CREDIT_NbreUPas, monProduitCredit.getCrNbreUPas());
            httpParams.put(monProduitCredit.KEY_CREDIT_TypTxInter, monProduitCredit.getCrTypTxInter());
            httpParams.put(monProduitCredit.KEY_CREDIT_ValTxInter, monProduitCredit.getCrValTxInter());
            httpParams.put(monProduitCredit.KEY_CREDIT_IsTxIntNeg, String.valueOf(monProduitCredit.getCrIsTxIntNeg()));
            httpParams.put(monProduitCredit.KEY_CREDIT_NbreAvalDmde, monProduitCredit.getCrNbreAvalDmde());
            httpParams.put(monProduitCredit.KEY_CREDIT_NbreMinAvalExig, monProduitCredit.getCrNbreMinAvalExig());
            httpParams.put(monProduitCredit.KEY_CREDIT_TxCouvCrAval, monProduitCredit.getCrTxCouvCrAval());
            httpParams.put(monProduitCredit.KEY_CREDIT_IsTxCouvAvalOblig, String.valueOf(monProduitCredit.getCrIsTxCouvAvalOblig()));
            httpParams.put(monProduitCredit.KEY_CREDIT_IsCautionMorAvalAcc, String.valueOf(monProduitCredit.getCrIsCautionMorAvalAcc()));
            httpParams.put(monProduitCredit.KEY_CREDIT_IsGarBloqCptOblig, String.valueOf(monProduitCredit.getCrIsGarBloqCptOblig()));
            httpParams.put(monProduitCredit.KEY_CREDIT_IsGarCptEAVOn, String.valueOf(monProduitCredit.getCrIsGarCptEAVOn()));
            httpParams.put(monProduitCredit.KEY_CREDIT_IsGarCptEATOn, String.valueOf(monProduitCredit.getCrIsGarCptEATOn()));
            httpParams.put(monProduitCredit.KEY_CREDIT_IsGarCptEAPOn, String.valueOf(monProduitCredit.getCrIsGarCptEAPOn()));
            httpParams.put(monProduitCredit.KEY_CREDIT_MtMaxSansAval, monProduitCredit.getCrMtMaxSansAval());
            httpParams.put(monProduitCredit.KEY_CREDIT_IsAvalSansCredOn, String.valueOf(monProduitCredit.getCrIsAvalSansCredOn()));
            httpParams.put(monProduitCredit.KEY_CREDIT_IsTxGarMemObl, String.valueOf(monProduitCredit.getCrIsTxGarMemObl()));
            httpParams.put(monProduitCredit.KEY_CREDIT_TauxGarMemb, monProduitCredit.getCrTauxGarMemb());
            httpParams.put(monProduitCredit.KEY_CREDIT_IsPersMorAvalOn, String.valueOf(monProduitCredit.getCrIsPersMorAvalOn()));
            httpParams.put(monProduitCredit.KEY_CREDIT_IsCouvPartSOn, String.valueOf(monProduitCredit.getCrIsCouvPartSOn()));
            httpParams.put(monProduitCredit.KEY_CREDIT_TxCouvPSOblig, monProduitCredit.getCrTxCouvPSOblig());
            httpParams.put(monProduitCredit.KEY_CREDIT_IsAffCollCredOn, String.valueOf(monProduitCredit.getCrIsAffCollCredOn()));
            httpParams.put(monProduitCredit.KEY_CREDIT_NbreAnAncMinCred, monProduitCredit.getCrNbreAnAncMinCred());
            httpParams.put(monProduitCredit.KEY_CREDIT_NbAnAncNeg, String.valueOf(monProduitCredit.getCrNbAnAncNeg()));
            httpParams.put(monProduitCredit.KEY_CREDIT_MtPlafondMax, monProduitCredit.getCrMtPlafondMax());
            httpParams.put(monProduitCredit.KEY_CREDIT_IsMtPlafCredLeve, String.valueOf(monProduitCredit.getCrIsMtPlafCredLeve()));
            httpParams.put(monProduitCredit.KEY_CREDIT_IsGarMatExige, String.valueOf(monProduitCredit.getCrIsGarMatExige()));
            httpParams.put(monProduitCredit.KEY_CREDIT_IsFraisEtudDossOn, String.valueOf(monProduitCredit.getCrIsFraisEtudDossOn()));
            httpParams.put(monProduitCredit.KEY_CREDIT_NatFrEtudDoss, monProduitCredit.getCrNatFrEtudDoss());
            httpParams.put(monProduitCredit.KEY_CREDIT_ValTxFrEtudDoss, monProduitCredit.getCrValTxFrEtudDoss());
            httpParams.put(monProduitCredit.KEY_CREDIT_BaseTxFrEtudDoss, monProduitCredit.getCrBaseTxFrEtudDoss());
            httpParams.put(monProduitCredit.KEY_CREDIT_IsFraisDeblocCredOn, String.valueOf(monProduitCredit.getCrIsFraisDeblocCredOn()));
            httpParams.put(monProduitCredit.KEY_CREDIT_NatFraisDeblocCred, monProduitCredit.getCrNatFraisDeblocCred());
            httpParams.put(monProduitCredit.KEY_CREDIT_ValTxFraisDeblocCred, monProduitCredit.getCrValTxFraisDeblocCred());
            httpParams.put(monProduitCredit.KEY_CREDIT_BaseTxFraisDeblocCred, monProduitCredit.getCrBaseTxFraisDeblocCred());
            httpParams.put(monProduitCredit.KEY_CREDIT_IsFraisDecaissCredOn, String.valueOf(monProduitCredit.getCrIsFraisDecaissCredOn()));
            httpParams.put(monProduitCredit.KEY_CREDIT_NatFraisDecaissCred, monProduitCredit.getCrNatFraisDecaissCred());
            httpParams.put(monProduitCredit.KEY_CREDIT_ValTxFraisDecaissCred, monProduitCredit.getCrValTxFraisDecaissCred());
            httpParams.put(monProduitCredit.KEY_CREDIT_BaseFraisDecaissCred, monProduitCredit.getCrBaseFraisDecaissCred());
            httpParams.put(monProduitCredit.KEY_CREDIT_IsFraisEtudByDAV, String.valueOf(monProduitCredit.getCrIsFraisEtudByDAV()));
            httpParams.put(monProduitCredit.KEY_CREDIT_IsFraisDeblocByDAV, String.valueOf(monProduitCredit.getCrIsFraisDeblocByDAV()));
            httpParams.put(monProduitCredit.KEY_CREDIT_IsFraisDecaissByDAV, String.valueOf(monProduitCredit.getCrIsFraisDecaissByDAV()));
            httpParams.put(monProduitCredit.KEY_CREDIT_IsModDecaissByObjet, String.valueOf(monProduitCredit.getCrIsModDecaissByObjet()));
            httpParams.put(monProduitCredit.KEY_CREDIT_IsDeblocTransfDAVOn, String.valueOf(monProduitCredit.getCrIsDeblocTransfDAVOn()));
            httpParams.put(monProduitCredit.KEY_CREDIT_IsMtPlafByObjet, String.valueOf(monProduitCredit.getCrIsMtPlafByObjet()));
            httpParams.put(monProduitCredit.KEY_CREDIT_ModeRemb, monProduitCredit.getCrModeRemb());
            httpParams.put(monProduitCredit.KEY_CREDIT_IsCptEATRemCredOn, String.valueOf(monProduitCredit.getCrIsCptEATRemCredOn()));
            httpParams.put(monProduitCredit.KEY_CREDIT_IsCptEAPRemCredOn, String.valueOf(monProduitCredit.getCrIsCptEAPRemCredOn()));
            httpParams.put(monProduitCredit.KEY_CREDIT_IsInterOffSiCapRembAnt, String.valueOf(monProduitCredit.getCrIsInterOffSiCapRembAnt()));
            httpParams.put(monProduitCredit.KEY_CREDIT_TxInterEchNHon, monProduitCredit.getCrTxInterEchNHon());
            httpParams.put(monProduitCredit.KEY_CREDIT_BaseInterEchNHon, monProduitCredit.getCrBaseInterEchNHon());
            httpParams.put(monProduitCredit.KEY_CREDIT_PlanningRembCred, monProduitCredit.getCrPlanningRembCred());
            httpParams.put(monProduitCredit.KEY_CREDIT_IsRappDatEchCred, String.valueOf(monProduitCredit.getCrIsRappDatEchCred()));
            httpParams.put(monProduitCredit.KEY_CREDIT_ModelTextRappEchRemb, monProduitCredit.getCrModelTextRappEchRemb());
            httpParams.put(monProduitCredit.KEY_CREDIT_NbreJrAvantDatEch, monProduitCredit.getCrNbreJrAvantDatEch());
            httpParams.put(monProduitCredit.KEY_CREDIT_NbreJrApreEchSiNHon, monProduitCredit.getCrNbreJrApreEchSiNHon());
            httpParams.put(monProduitCredit.KEY_CREDIT_User, monProduitCredit.getCrUser());
            httpParams.put(monProduitCredit.KEY_CREDIT_DateHCree, monProduitCredit.getCrDateHCree());
            httpParams.put(monProduitCredit.KEY_CREDIT_UserModif, monProduitCredit.getCrUserModif());
            httpParams.put(monProduitCredit.KEY_CREDIT_DatHModif, monProduitCredit.getCrDatHModif());
            httpParams.put(monProduitCredit.KEY_CREDIT_CaisseId, monProduitCredit.getCrCaisseId());
            httpParams.put(monProduitCredit.KEY_CREDIT_GuichetId, monProduitCredit.getCrGuichetId());
            httpParams.put(monProduitCredit.KEY_CrIsTxIntDegressif, String.valueOf(monProduitCredit.getCrIsTxIntDegressif()));



            httpParams.put(KEY_CREDIT_PLAGE_FRAIS_ETUDE_DEBUT, tabPlageDebutFEC);
            httpParams.put(KEY_CREDIT_PLAGE_FRAIS_ETUDE_FIN, tabPlageFinFEC);
            httpParams.put(KEY_CREDIT_PLAGE_FRAIS_ETUDE_VALEUR, tabPlageValeurFEC);
            httpParams.put(KEY_CREDIT_PLAGE_FRAIS_ETUDE_BASE, tabPlageBaseFEC);
            httpParams.put(KEY_CREDIT_PLAGE_FRAIS_ETUDE_NATURE, tabPlageNatureFEC);

            httpParams.put(KEY_CREDIT_PLAGE_FRAIS_DEBLOCAGE_DEBUT, tabPlageDebutFDB);
            httpParams.put(KEY_CREDIT_PLAGE_FRAIS_DEBLOCAGE_FIN, tabPlageFinFDB);
            httpParams.put(KEY_CREDIT_PLAGE_FRAIS_DEBLOCAGE_VALEUR, tabPlageValeurFDB);
            httpParams.put(KEY_CREDIT_PLAGE_FRAIS_DEBLOCAGE_BASE, tabPlageBaseFDB);
            httpParams.put(KEY_CREDIT_PLAGE_FRAIS_DEBLOCAGE_NATURE, tabPlageNatureFDB);

            httpParams.put(KEY_CREDIT_PLAGE_FRAIS_DECAISSEMENT_DEBUT, tabPlageDebutFCX);
            httpParams.put(KEY_CREDIT_PLAGE_FRAIS_DECAISSEMENT_FIN, tabPlageFinFCX);
            httpParams.put(KEY_CREDIT_PLAGE_FRAIS_DECAISSEMENT_VALEUR, tabPlageValeurFCX);
            httpParams.put(KEY_CREDIT_PLAGE_FRAIS_DECAISSEMENT_BASE, tabPlageBaseFCX);
            httpParams.put(KEY_CREDIT_PLAGE_FRAIS_DECAISSEMENT_NATURE, tabPlageNatureFCX);

            httpParams.put(KEY_CREDIT_PLAGE_TAUX_INETERET_DEBUT, tabPlageDebutTIC);
            httpParams.put(KEY_CREDIT_PLAGE_TAUX_INETERET_FIN, tabPlageFinTIC);
            httpParams.put(KEY_CREDIT_PLAGE_TAUX_INETERET_VALEUR, tabPlageValeurTIC);
            httpParams.put(KEY_CREDIT_PLAGE_TAUX_INETERET_BASE, tabPlageBaseTIC);
            httpParams.put(KEY_CREDIT_PLAGE_TAUX_INETERET_NATURE, tabPlageNatureTIC);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "update_credit.php", "POST", httpParams);
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
                        Toast.makeText(UpdateProduitCredit.this,
                                "Crédit mis à jour !", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        monProduitCredit= null; //a revoir
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(UpdateProduitCredit.this,
                                "Some error occurred while updating",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}
