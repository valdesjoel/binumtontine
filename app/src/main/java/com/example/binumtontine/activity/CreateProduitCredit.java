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
import android.widget.Spinner;
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
import com.example.binumtontine.modele.Credit;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.binumtontine.controleur.MyData.alreadyUpperCase;

public class CreateProduitCredit extends AppCompatActivity implements SERVER_ADDRESS {
    private Credit monProduitCredit;
    private static final String KEY_SUCCESS = "success";




    private static final String KEY_CREDIT_PLAGE_FRAIS_ETUDE_DEBUT = "CcFecDebut";
    private static final String KEY_CREDIT_PLAGE_FRAIS_ETUDE_FIN = "CcFecFin";
    private static final String KEY_CREDIT_PLAGE_FRAIS_ETUDE_VALEUR = "CcFecValeur";
    private static final String KEY_CREDIT_PLAGE_FRAIS_ETUDE_BASE = "CcFecBase";
    private static final String KEY_CREDIT_PLAGE_FRAIS_ETUDE_NATURE = "CcFecNature";
    private static final String KEY_CREDIT_PLAGE_FRAIS_ETUDE_PdMtMinimum = "CcFecPdMtMinimum";


    private String tabPlageDebutFEC ="";
    private String tabPlageFinFEC ="";
    private String tabPlageValeurFEC ="";
    private String tabPlageBaseFEC ="";
    private String tabPlageNatureFEC ="";
    private String tabPlagePdMtMinimumFEC ="";

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

//Taux d'intérêt crédit TIC
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

    //Taux d'intérêt annuel TIA
    private static final String KEY_TIA_DEBUT = "CcTiaDebut";
    private static final String KEY_TIA_FIN = "CcTiaFin";
    private static final String KEY_TIA_VALEUR = "CcTiaValeur";
    private static final String KEY_TIA_BASE = "CcTiaBase";
    private static final String KEY_TIA_NATURE = "CcTiaNature";

    private String tabPlageDebutTIA ="";
    private String tabPlageFinTIA ="";
    private String tabPlageValeurTIA ="";
    private String tabPlageBaseTIA ="";
    private String tabPlageNatureTIA ="";


    private static String STRING_EMPTY = "";
//    private String CrNumero;
    private EditText ET_CrCode;
    private EditText ET_CrLibelle;
    private EditText ET_CrDureeMin;
    private EditText ET_CrDureeMax;
    private RadioButton rbCrNaturePasFixe;
    private RadioButton rbCrNaturePasSaut;
//    private String CrNaturePas;
    private EditText ET_CrNbreUPas;
    private EditText ET_CrNbreJrDelaiGrace;
    private RadioButton rbCrTypTxInterFixe;
    private RadioButton rbCrTypTxInterPlage;
    private RadioButton rbCrTypTxInterPenRetardFixe;
    private RadioButton rbCrTypTxInterPenRetardPlage;
    private RadioButton rbCrTypTxInterRetardFixe;
    private RadioButton rbCrTypTxInterRetardPlage;

    private RadioButton rbCrNatureTxIntAnFixe;
    private RadioButton rbCrNatureTxIntAnTaux;
    private RadioButton rbCrNatureTxIntAnPlage;

    private RadioButton rbCrModeCalcInteretSimple;
    private RadioButton rbCrModeCalcInteretCompose;
    private RadioButton rbCrModeCalcInteretDegressif;
    private RadioButton rbCrPeriodCalcInteretJournalier;
    private RadioButton rbCrPeriodCalcInteretMensuel;
    private RadioButton rbCrPeriodCalcInteretTrimestre;
    private RadioButton rbCrPeriodCalcInteretAnnee;
    private RadioButton rbCrPeriodCalcInteretPenRetardJournalier;
    private RadioButton rbCrPeriodCalcInteretPenRetardMensuel;
    private RadioButton rbCrPeriodCalcInteretPenRetardTrimestre;
    private RadioButton rbCrPeriodCalcInteretPenRetardAnnee;
    private RadioButton rbCrPeriodCalcInteretRetardJournalier;
    private RadioButton rbCrPeriodCalcInteretRetardMensuel;
    private RadioButton rbCrPeriodCalcInteretRetardTrimestre;
    private RadioButton rbCrPeriodCalcInteretRetardAnnee;
    private RadioButton rbCrNatureJrTxIntJrLunVen;
    private RadioButton rbCrNatureJrTxIntJrLunSam;
    private RadioButton rbCrNatureJrTxIntJrJrOuvertGu;
    private RadioButton rbCrNatureJrTxIntJrTous;
    private RadioButton rbCrNatureJrTxIntPenRetardJrLunVen;
    private RadioButton rbCrNatureJrTxIntPenRetardJrLunSam;
    private RadioButton rbCrNatureJrTxIntPenRetardJrJrOuvertGu;
    private RadioButton rbCrNatureJrTxIntPenRetardJrTous;
    private RadioButton rbCrNatureJrTxIntRetardJrLunVen;
    private RadioButton rbCrNatureJrTxIntRetardJrLunSam;
    private RadioButton rbCrNatureJrTxIntRetardJrJrOuvertGu;
    private RadioButton rbCrNatureJrTxIntRetardJrTous;
    private RadioButton rbCrTypTxInterMontant;
    private RadioButton rbCrTypTxInterDuree;
    private RadioButton rbCrTypTxInterMontantDuree;
    private String CrTypTxInter;
    private String CrNatureTxPenRet;
    private String CrNatureTxInt_IntRetCred;
    private EditText ET_CrValTxInter;
    private EditText ET_CrValTxInterPenRetard;
    private EditText ET_CrValTxInterRetard;
    private Switch SW_CrIsTxIntNeg;
    private Spinner SW_CrNbreUPas;
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
    private EditText ET_CrTauxValTxIntAn;
    private JRSpinner JR_CrBaseTxIntAn;
    private JRSpinner JR_CrBase_tauxInt;
    private JRSpinner JR_Crbase_tauxIntPenRetard;
    private JRSpinner JR_Crbase_tauxIntRetard;
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
    private Switch SW_CrIsCpteEAVOnRembCred;
    private Switch SW_CrIsCpteCourantOnRembCred;
    private Switch SW_CrIsInterOffSiCapRembAnt;
    private EditText ET_CrTxInterEchNHon;
    private JRSpinner JR_CrBaseInterEchNHon;
    private JRSpinner JR_CrPlanningRembCred;
    private Switch SW_CrIsRappDatEchCred;
    private Switch SW_CrIsIntRetCreditOn;
    private EditText ET_CrModelTextRappEchRemb;
    private EditText ET_CrNbreJrAvantDatEch;
    private EditText ET_CrNbreJrApreEchSiNHon;
    private Switch SW_CrIsTxIntDegressif;
    private Switch SW_CrIsDelaiGraceNegoc;
    private Switch SW_CrIsJoursOuvresOnly;
    private SwitchCompat SW_CrIsTxInteretJrOn;
    private SwitchCompat SW_CrIsTxPenRetardOn;
    private SwitchCompat SW_CrIsTxIntJrOn_IntRetCred;
    private SwitchCompat SW_CrIsSoldPenRetObligSiNewEchCred;
    private SwitchCompat SW_CrIsSoldIntRetObligSiNewEchCred;
    private SwitchCompat SW_CrIsTauxInteretAnOn;
//    private String CrUser;
//    private String CrDateHCree;
//    private String CrUserModif;
//    private String CrDatHModif;
//    private String CrCaisseId;
//    private String CrGuichetId;



    private LinearLayout LL_CrNatFrEtudDoss;
    private LinearLayout LL_CrNatFraisDeblocCred;
    private LinearLayout LL_CrNatFraisDecaissCred;
    private LinearLayout ll_CrNatureJrTxIntJr;
    private LinearLayout ll_CrNatureJrTxIntPenRetardJr;
    private LinearLayout ll_CrNatureJrTxIntRetardJr;
    private LinearLayout ll_TauxInteretAnOn;
    private LinearLayout ll_InteretDeRetard;

    private TextInputLayout layout_TauxCrNatFrEtudDoss;
    private TextInputLayout layout_TauxCrValTxFraisDeblocCred;
    private TextInputLayout input_layout_CrTauxValTxIntAn;

    private Button addButton;
    private Button cancelButton;
    private Button deleteButton;
    private int success;
    private ProgressDialog pDialog;

    private TextView tv_header_produit;
    private TextView tv_plage_CrNatFrEtudDoss;
    private TextView tv_plage_CrTypTxInter;
    private TextView tv_plage_cr_PenRetard;
    private TextView tv_plage_cr_IntRetard;
    private TextView tv_plage_tia_cc;
    private TextView tv_plage_CrNatFraisDeblocCred;
    private TextView tv_plage_CrNatFraisDecaissCred;
    public static ArrayList<ModelPlageData> plageDataListTIC = new ArrayList<ModelPlageData>(); //to manage plageData taux d'intérêt nominal
    public static ArrayList<ModelPlageData> plageDataListTIA = new ArrayList<ModelPlageData>(); //to manage plageData taux d'intérêt annuel
    public static ArrayList<ModelPlageData> plageDataListPRC = new ArrayList<ModelPlageData>(); //to manage plageData taux de pénalité de retard
    public static ArrayList<ModelPlageData> plageDataListIRC = new ArrayList<ModelPlageData>(); //to manage plageData taux d'intérêt de retard
    public static ArrayList<ModelPlageData> plageDataListFEC = new ArrayList<ModelPlageData>(); //to manage plageData frais etude
    public static ArrayList<ModelPlageData> plageDataListFDB = new ArrayList<ModelPlageData>(); //to manage plageData frais deblocage
    public static ArrayList<ModelPlageData> plageDataListFCX = new ArrayList<ModelPlageData>(); //to manage plageData frais decaissement
    private TextView tv_config_plage_tiv;
    private String CrModeCalcInteret;
    private String CrPeriodCalcInteret;
    private String CrPeriodNatureTxPenRet;
    private String CrNatureJrTxIntJr;
    private String CrNatureJrTxPenRet;
//    private String CrNatureTxInt_IntRetCred;
    private String CrPeriod_IntRetCred;
    private String CrNatJrTxIntJr_IntRetCred;
    private String CrIsDelaiGraceNegoc;
    private String CrIsJoursOuvresOnly;
    private String CrIsTxIntNeg;
    private String CrIsTxIntDegressif;
    private String CrIsTxInteretJrOn;
    private String CrIsTxPenRetardOn;
    private String CrIsTxIntJrOn_IntRetCred;
    private String CrIsTxCouvAvalOblig;
    private String CrIsCautionMorAvalAcc;
    private String CrIsGarBloqCptOblig;
    private String CrIsGarCptEAVOn;
    private String CrIsGarCptEATOn;
    private String CrIsGarCptEAPOn;
    private String CrIsAvalSansCredOn;
    private String CrIsTxGarMemObl;
    private String CrIsPersMorAvalOn;
    private String CrIsCouvPartSOn;
    private String CrIsAffCollCredOn;
    private String CrNbAnAncNeg;
    private String CrIsMtPlafCredLeve;
    private String CrIsGarMatExige;
    private String CrIsFraisEtudDossOn;
    private String CrIsFraisDeblocCredOn;
    private String CrIsFraisDecaissCredOn;
    private String CrIsFraisEtudByDAV;
    private String CrIsFraisDeblocByDAV;
    private String CrIsFraisDecaissByDAV;
    private String CrIsModDecaissByObjet;
    private String CrIsDeblocTransfDAVOn;
    private String CrIsMtPlafByObjet;
    private String CrIsCptEATRemCredOn;
    private String CrIsCptEAPRemCredOn;
    private String CrIsCpteEAVOnRembCred;
    private String CrIsCpteCourantOnRembCred;
    private String CrIsInterOffSiCapRembAnt;
    private String CrIsRappDatEchCred;
    private String CrIsIntRetCreditOn;
    private String CrIsSoldPenRetObligSiNewEchCred;
    private String CrIsSoldIntRetObligSiNewEchCred;
    private String CrIsTauxInteretAnOn;
    private String CrNatureTxIntAn;

    private String st_CrIsTVAOn;
    private SwitchCompat CrIsTVAOn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);
        tv_header_produit = (TextView) findViewById(R.id.header_produit);
        tv_header_produit.setText("Produit Crédit\n"+"Caisse: "+MyData.CAISSE_NAME);

        tv_plage_CrTypTxInter = (TextView) findViewById(R.id.tv_plage_tic_cc);
        tv_plage_cr_PenRetard = (TextView) findViewById(R.id.tv_plage_cr_PenRetard);
        tv_plage_cr_IntRetard = (TextView) findViewById(R.id.tv_plage_cr_IntRetard);
        tv_plage_CrNatFrEtudDoss = (TextView) findViewById(R.id.tv_plage_CrNatFrEtudDoss);
        tv_plage_CrNatFraisDeblocCred = (TextView) findViewById(R.id.tv_plage_CrNatFraisDeblocCred);
        tv_plage_CrNatFraisDecaissCred = (TextView) findViewById(R.id.tv_plage_CrNatFraisDecaissCred);
        tv_plage_tia_cc = (TextView) findViewById(R.id.tv_plage_tia_cc);
        ET_CrCode = (EditText) findViewById(R.id.input_txt_Code_credit);
        alreadyUpperCase(ET_CrCode);
        ET_CrLibelle = (EditText) findViewById(R.id.input_txt_LibelleCredit);
        ET_CrDureeMax = (EditText) findViewById(R.id.input_txt_CrDureeMax);
        ET_CrDureeMin = (EditText) findViewById(R.id.input_txt_CrDureeMin);
        ET_CrNbreJrDelaiGrace = (EditText) findViewById(R.id.input_txt_CrNbreJrDelaiGrace);
        rbCrTypTxInterFixe = (RadioButton)findViewById(R.id.rbCrTypTxInterFixe);
        rbCrTypTxInterPlage = (RadioButton)findViewById(R.id.rbCrTypTxInterPlage);
        rbCrTypTxInterPenRetardFixe = (RadioButton)findViewById(R.id.rbCrTypTxInterPenRetardFixe);
        rbCrTypTxInterPenRetardPlage = (RadioButton)findViewById(R.id.rbCrTypTxInterPenRetardPlage);
        rbCrTypTxInterRetardFixe = (RadioButton)findViewById(R.id.rbCrTypTxInterRetardFixe);
        rbCrTypTxInterRetardPlage = (RadioButton)findViewById(R.id.rbCrTypTxInterRetardPlage);

        rbCrNatureTxIntAnFixe = (RadioButton)findViewById(R.id.rbCrNatureTxIntAnFixe);
        rbCrNatureTxIntAnTaux = (RadioButton)findViewById(R.id.rbCrNatureTxIntAnTaux);
        rbCrNatureTxIntAnPlage = (RadioButton)findViewById(R.id.rbCrNatureTxIntAnPlage);
        rbCrModeCalcInteretSimple = (RadioButton)findViewById(R.id.rbCrModeCalcInteretSimple);
        rbCrModeCalcInteretCompose = (RadioButton)findViewById(R.id.rbCrModeCalcInteretCompose);
        rbCrModeCalcInteretDegressif = (RadioButton)findViewById(R.id.rbCrModeCalcInteretDegressif);
        rbCrPeriodCalcInteretMensuel = (RadioButton)findViewById(R.id.rbCrPeriodCalcInteretMensuel);
        rbCrPeriodCalcInteretTrimestre = (RadioButton)findViewById(R.id.rbCrPeriodCalcInteretTrimestre);
        rbCrPeriodCalcInteretAnnee = (RadioButton)findViewById(R.id.rbCrPeriodCalcInteretAnnee);
        rbCrPeriodCalcInteretJournalier = (RadioButton)findViewById(R.id.rbCrPeriodCalcInteretJournalier);

        rbCrPeriodCalcInteretPenRetardMensuel = (RadioButton)findViewById(R.id.rbCrPeriodCalcInteretPenRetardMensuel);
        rbCrPeriodCalcInteretPenRetardTrimestre = (RadioButton)findViewById(R.id.rbCrPeriodCalcInteretPenRetardTrimestre);
        rbCrPeriodCalcInteretPenRetardAnnee = (RadioButton)findViewById(R.id.rbCrPeriodCalcInteretPenRetardAnnee);
        rbCrPeriodCalcInteretPenRetardJournalier = (RadioButton)findViewById(R.id.rbCrPeriodCalcInteretPenRetardJournalier);

        rbCrPeriodCalcInteretRetardMensuel = (RadioButton)findViewById(R.id.rbCrPeriodCalcInteretRetardMensuel);
        rbCrPeriodCalcInteretRetardTrimestre = (RadioButton)findViewById(R.id.rbCrPeriodCalcInteretRetardTrimestre);
        rbCrPeriodCalcInteretRetardAnnee = (RadioButton)findViewById(R.id.rbCrPeriodCalcInteretRetardAnnee);
        rbCrPeriodCalcInteretRetardJournalier = (RadioButton)findViewById(R.id.rbCrPeriodCalcInteretRetardJournalier);

        rbCrNatureJrTxIntJrLunVen = (RadioButton)findViewById(R.id.rbCrNatureJrTxIntJrLunVen);
        rbCrNatureJrTxIntJrLunSam = (RadioButton)findViewById(R.id.rbCrNatureJrTxIntJrLunSam);
        rbCrNatureJrTxIntJrJrOuvertGu = (RadioButton)findViewById(R.id.rbCrNatureJrTxIntJrJrOuvertGu);
        rbCrNatureJrTxIntJrTous = (RadioButton)findViewById(R.id.rbCrNatureJrTxIntJrTous);

        rbCrNatureJrTxIntPenRetardJrLunVen = (RadioButton)findViewById(R.id.rbCrNatureJrTxIntPenRetardJrLunVen);
        rbCrNatureJrTxIntPenRetardJrLunSam = (RadioButton)findViewById(R.id.rbCrNatureJrTxIntPenRetardJrLunSam);
        rbCrNatureJrTxIntPenRetardJrJrOuvertGu = (RadioButton)findViewById(R.id.rbCrNatureJrTxIntPenRetardJrJrOuvertGu);
        rbCrNatureJrTxIntPenRetardJrTous = (RadioButton)findViewById(R.id.rbCrNatureJrTxIntPenRetardJrTous);

        rbCrNatureJrTxIntRetardJrLunVen = (RadioButton)findViewById(R.id.rbCrNatureJrTxIntRetardJrLunVen);
        rbCrNatureJrTxIntRetardJrLunSam = (RadioButton)findViewById(R.id.rbCrNatureJrTxIntRetardJrLunSam);
        rbCrNatureJrTxIntRetardJrJrOuvertGu = (RadioButton)findViewById(R.id.rbCrNatureJrTxIntRetardJrJrOuvertGu);
        rbCrNatureJrTxIntRetardJrTous = (RadioButton)findViewById(R.id.rbCrNatureJrTxIntRetardJrTous);

        ET_CrValTxInter = (EditText) findViewById(R.id.input_txt_CrValTxInter);
        ET_CrValTxInterPenRetard = (EditText) findViewById(R.id.input_txt_CrValTxInterPenRetard);
        ET_CrValTxInterRetard = (EditText) findViewById(R.id.input_txt_CrValTxInterRetard);
        SW_CrIsTxIntNeg = (Switch) findViewById(R.id.SwitchCrIsTxIntNeg);
        SW_CrNbreUPas = (Spinner) findViewById(R.id.spn_list_duree);

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
        JR_CrBaseTxFrEtudDoss.setItems(getResources().getStringArray(R.array.array_CrBaseTxFrEtudDoss)); //this is important, you must set it to set the item list
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
        ET_CrTauxValTxIntAn = (EditText) findViewById(R.id.input_txt_CrTauxValTxIntAn);
        JR_CrBase_tauxInt = (JRSpinner) findViewById(R.id.spn_my_spinner_base_tauxInt); //A revoir car c'est un JRSpinner
        JR_CrBase_tauxInt.setItems(getResources().getStringArray(R.array.array_base_taux_interet_nominal_credit)); //this is important, you must set it to set the item list
        JR_CrBase_tauxInt.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        JR_CrBase_tauxInt.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        JR_CrBase_tauxInt.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });
        JR_Crbase_tauxIntPenRetard = (JRSpinner) findViewById(R.id.spn_my_spinner_base_tauxIntPenRetard); //A revoir car c'est un JRSpinner
        JR_Crbase_tauxIntPenRetard.setItems(getResources().getStringArray(R.array.array_base_taux_penalite_retard_credit)); //this is important, you must set it to set the item list
        JR_Crbase_tauxIntPenRetard.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        JR_Crbase_tauxIntPenRetard.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        JR_Crbase_tauxIntPenRetard.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });
        JR_Crbase_tauxIntRetard = (JRSpinner) findViewById(R.id.spn_my_spinner_base_tauxInterRetard); //A revoir car c'est un JRSpinner
        JR_Crbase_tauxIntRetard.setItems(getResources().getStringArray(R.array.array_base_taux_interet_retard_credit)); //this is important, you must set it to set the item list
        JR_Crbase_tauxIntRetard.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        JR_Crbase_tauxIntRetard.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        JR_Crbase_tauxIntRetard.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });

        JR_CrBaseTxFraisDeblocCred = (JRSpinner) findViewById(R.id.spn_my_spinner_CrBaseTxFraisDeblocCred); //A revoir car c'est un JRSpinner
        JR_CrBaseTxFraisDeblocCred.setItems(getResources().getStringArray(R.array.array_CrBaseTxFraisDeblocCred)); //this is important, you must set it to set the item list
        JR_CrBaseTxFraisDeblocCred.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        JR_CrBaseTxFraisDeblocCred.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        JR_CrBaseTxFraisDeblocCred.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });

        JR_CrBaseTxIntAn = (JRSpinner) findViewById(R.id.spn_my_spinner_CrBaseTxIntAn);
        JR_CrBaseTxIntAn.setItems(getResources().getStringArray(R.array.array_base_taux_credit)); //this is important, you must set it to set the item list
        JR_CrBaseTxIntAn.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        JR_CrBaseTxIntAn.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        JR_CrBaseTxIntAn.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
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
        JR_CrBaseTxFraisDecaissCred.setItems(getResources().getStringArray(R.array.array_CrBaseFraisDecaissCred)); //this is important, you must set it to set the item list
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
        SW_CrIsCpteEAVOnRembCred = (Switch) findViewById(R.id.SwitchCrIsCptEAVRemCredOn);
        SW_CrIsCpteCourantOnRembCred = (Switch) findViewById(R.id.SwitchCrIsCpteCourantOnRembCred);
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
        SW_CrIsIntRetCreditOn = (Switch) findViewById(R.id.SwitchCrIsIntRetCreditOn);
        SW_CrIsTxIntDegressif = (Switch) findViewById(R.id.SwitchCrIsTxIntDegressif);
        SW_CrIsDelaiGraceNegoc = (Switch) findViewById(R.id.SwitchCrIsDelaiGraceNegoc);
        SW_CrIsJoursOuvresOnly = (Switch) findViewById(R.id.SwitchCrIsJoursOuvresOnly);
        SW_CrIsTxInteretJrOn = (SwitchCompat) findViewById(R.id.SwitchCrIsTxInteretJrOn);
        SW_CrIsSoldPenRetObligSiNewEchCred = (SwitchCompat) findViewById(R.id.SwitchCrIsSoldPenRetObligSiNewEchCred);
        SW_CrIsSoldIntRetObligSiNewEchCred = (SwitchCompat) findViewById(R.id.SwitchCrIsSoldIntRetObligSiNewEchCred);
        SW_CrIsTauxInteretAnOn = (SwitchCompat) findViewById(R.id.SwitchCrIsTauxInteretAnOn);
        SW_CrIsTxPenRetardOn = (SwitchCompat) findViewById(R.id.SwitchCrIsTxInteretPenRetardJrOn);
        SW_CrIsTxIntJrOn_IntRetCred = (SwitchCompat) findViewById(R.id.SwitchCrIsTxInteretRetardJrOn);
        ET_CrModelTextRappEchRemb = (EditText) findViewById(R.id.input_txt_CrModelTextRappEchRemb);
        ET_CrNbreJrAvantDatEch = (EditText) findViewById(R.id.input_txt_CrNbreJrAvantDatEch);
        ET_CrNbreJrApreEchSiNHon = (EditText) findViewById(R.id.input_txt_CrNbreJrApreEchSiNHon);

        LL_CrNatFrEtudDoss = (LinearLayout) findViewById(R.id.ll_CrNatFrEtudDoss);
        LL_CrNatFraisDeblocCred = (LinearLayout) findViewById(R.id.ll_CrNatFraisDeblocCred);
        LL_CrNatFraisDecaissCred = (LinearLayout) findViewById(R.id.ll_CrNatFraisDecaissCred);
        ll_CrNatureJrTxIntJr = (LinearLayout) findViewById(R.id.ll_CrNatureJrTxIntJr);
        ll_CrNatureJrTxIntPenRetardJr = (LinearLayout) findViewById(R.id.ll_CrNatureJrTxIntPenRetardJr);
        ll_CrNatureJrTxIntRetardJr = (LinearLayout) findViewById(R.id.ll_CrNatureJrTxIntRetardJr);
        ll_TauxInteretAnOn = (LinearLayout) findViewById(R.id.ll_TauxInteretAnOn);
        ll_InteretDeRetard = (LinearLayout) findViewById(R.id.ll_InteretDeRetard);
        layout_TauxCrNatFrEtudDoss = (TextInputLayout) findViewById(R.id.input_layout_TauxCrNatFrEtudDoss);
        layout_TauxCrValTxFraisDeblocCred = (TextInputLayout) findViewById(R.id.input_layout_TauxCrValTxFraisDeblocCred);
        input_layout_CrTauxValTxIntAn = (TextInputLayout) findViewById(R.id.input_layout_CrTauxValTxIntAn);
        CrIsTVAOn = (SwitchCompat) findViewById(R.id.SwitchCrIsTVAOn);
        //TIC
        tv_plage_CrTypTxInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux d'intérêt crédit";
                    ListPlageTIC.IS_TO_CREATE_OR_TO_UPDATE = true;
                    Intent i = new Intent(CreateProduitCredit.this, ListPlageTIC.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(CreateProduitCredit.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        //TIA
        tv_plage_tia_cc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux d'intérêt annuel";
                    ListPlageTIA.IS_TO_CREATE_OR_TO_UPDATE = true;
                    Intent i = new Intent(CreateProduitCredit.this, ListPlageTIA.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(CreateProduitCredit.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        //PRC
        tv_plage_cr_PenRetard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux pénalité de retard";
                    ListPlagePRC.IS_TO_CREATE_OR_TO_UPDATE = true;
                    Intent i = new Intent(CreateProduitCredit.this, ListPlagePRC.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(CreateProduitCredit.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        //IRC
        tv_plage_cr_IntRetard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux Intérêt de retard";
                    ListPlageIRC.IS_TO_CREATE_OR_TO_UPDATE = true;
                    Intent i = new Intent(CreateProduitCredit.this, ListPlageIRC.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(CreateProduitCredit.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        //FEC
        tv_plage_CrNatFrEtudDoss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux frais d'étude de dossier";
                    ListPlageFEC.IS_TO_CREATE_OR_TO_UPDATE = true;
                    Intent i = new Intent(CreateProduitCredit.this, ListPlageFEC.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(CreateProduitCredit.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        //FDB
        tv_plage_CrNatFraisDeblocCred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux frais de déblocage";
                    ListPlageFDB.IS_TO_CREATE_OR_TO_UPDATE = true;
                    Intent i = new Intent(CreateProduitCredit.this, ListPlageFDB.class);
                    startActivityForResult(i,20);
                } else {
                    Toast.makeText(CreateProduitCredit.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        //FCX
        tv_plage_CrNatFraisDecaissCred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux frais de décaissement";
                    ListPlageFCX.IS_TO_CREATE_OR_TO_UPDATE = true;
                    Intent i = new Intent(CreateProduitCredit.this, ListPlageFCX.class);
                    startActivityForResult(i,20);
                } else {
                    Toast.makeText(CreateProduitCredit.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        onRadioButtonClicked(rbCrTypTxInterFixe);
        onRadioButtonClicked(rbCrTypTxInterPenRetardFixe);
        onRadioButtonClicked(rbCrTypTxInterRetardFixe);
        onRadioButtonClicked(rbCrNatureTxIntAnFixe);
        onRadioButtonClicked(rbCrNatFrEtudDossFixe);
        onRadioButtonClicked(rbCrNatFraisDeblocCredFixe);
        onRadioButtonClicked(rbCrNatFraisDecaissCredFixe);
        onRadioButtonClicked(rbCrModeCalcInteretSimple);
        onRadioButtonClicked(rbCrPeriodCalcInteretMensuel);
        onRadioButtonClicked(rbCrPeriodCalcInteretPenRetardMensuel);
        onRadioButtonClicked(rbCrPeriodCalcInteretRetardMensuel);
        onRadioButtonClicked(rbCrNatureJrTxIntJrTous);
        onRadioButtonClicked(rbCrNatureJrTxIntPenRetardJrTous);
        onRadioButtonClicked(rbCrNatureJrTxIntRetardJrTous);
        onSwitchButtonClicked(SW_CrIsTxGarMemObl);
        onSwitchButtonClicked(SW_CrIsCouvPartSOn);
//        onSwitchButtonClicked(SW_CrIsAffCollCredOn);
        onSwitchButtonClicked(SW_CrIsFraisEtudDossOn);
        onSwitchButtonClicked(SW_CrIsFraisDeblocCredOn);
        onSwitchButtonClicked(SW_CrIsFraisDecaissCredOn);
        onSwitchButtonClicked(SW_CrIsTxIntDegressif);
        onSwitchButtonClicked(SW_CrIsDelaiGraceNegoc);
        onSwitchButtonClicked(SW_CrIsJoursOuvresOnly);
        onSwitchButtonClicked(SW_CrIsTxIntNeg);
        onSwitchButtonClicked(SW_CrIsTxInteretJrOn);
        onSwitchButtonClicked(SW_CrIsSoldPenRetObligSiNewEchCred);
        onSwitchButtonClicked(SW_CrIsSoldIntRetObligSiNewEchCred);
        onSwitchButtonClicked(SW_CrIsTxPenRetardOn);
        onSwitchButtonClicked(SW_CrIsTxIntJrOn_IntRetCred);
        onSwitchButtonClicked(SW_CrIsTauxInteretAnOn);
        onSwitchButtonClicked(SW_CrIsTxCouvAvalOblig);
        onSwitchButtonClicked(SW_CrIsCautionMorAvalAcc);
        onSwitchButtonClicked(SW_CrIsGarBloqCptOblig);
        onSwitchButtonClicked(SW_CrIsGarCptEAVOn);
        onSwitchButtonClicked(SW_CrIsGarCptEATOn);
        onSwitchButtonClicked(SW_CrIsGarCptEAPOn);
        onSwitchButtonClicked(SW_CrIsAvalSansCredOn);
        onSwitchButtonClicked(SW_CrIsPersMorAvalOn);
        onSwitchButtonClicked(SW_CrIsAffCollCredOn);
        onSwitchButtonClicked(SW_CrNbAnAncNeg);
        onSwitchButtonClicked(SW_CrIsMtPlafCredLeve);
        onSwitchButtonClicked(SW_CrIsGarMatExige);
        onSwitchButtonClicked(SW_CrIsFraisEtudByDAV);
        onSwitchButtonClicked(SW_CrIsFraisDeblocByDAV);
        onSwitchButtonClicked(SW_CrIsFraisDecaissByDAV);
        onSwitchButtonClicked(SW_CrIsModDecaissByObjet);
        onSwitchButtonClicked(SW_CrIsDeblocTransfDAVOn);
        onSwitchButtonClicked(SW_CrIsMtPlafByObjet);
        onSwitchButtonClicked(SW_CrIsCpteEAVOnRembCred);
        onSwitchButtonClicked(SW_CrIsCpteCourantOnRembCred);
        onSwitchButtonClicked(SW_CrIsCptEATRemCredOn);
        onSwitchButtonClicked(SW_CrIsCptEAPRemCredOn);
        onSwitchButtonClicked(SW_CrIsInterOffSiCapRembAnt);
        onSwitchButtonClicked(SW_CrIsRappDatEchCred);
        onSwitchButtonClicked(SW_CrIsIntRetCreditOn);
        onSwitchButtonClicked(CrIsTVAOn);

        deleteButton = (Button) findViewById(R.id.btn_delete_eav);
        deleteButton.setVisibility(View.GONE);
        addButton = (Button) findViewById(R.id.btn_save_eav);
        cancelButton = (Button) findViewById(R.id.btn_clean);
        //cirLoginButton
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(CreateProduitCredit.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addProduitCredit();
                } else {
                    Toast.makeText(CreateProduitCredit.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onSwitchButtonClicked(View view) {
//        boolean checked1 = ((Switch) view).isChecked();
        //String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {
//

            case R.id.SwitchCrIsTVAOn:
                if (CrIsTVAOn.isChecked()) {
                    st_CrIsTVAOn = "Y";
                }else{
                    st_CrIsTVAOn = "N";
                }
                break;
            case R.id.SwitchCrIsSoldIntRetObligSiNewEchCred:
                if (SW_CrIsSoldIntRetObligSiNewEchCred.isChecked()) {
                    CrIsSoldIntRetObligSiNewEchCred = "Y";
                }else{
                    CrIsSoldIntRetObligSiNewEchCred = "N";
                }
                break;
            case R.id.SwitchCrIsSoldPenRetObligSiNewEchCred:
                if (SW_CrIsSoldPenRetObligSiNewEchCred.isChecked()) {
                    CrIsSoldPenRetObligSiNewEchCred = "Y";
                }else{
                    CrIsSoldPenRetObligSiNewEchCred = "N";
                }
                break;
            case R.id.SwitchCrIsIntRetCreditOn:
                if (SW_CrIsIntRetCreditOn.isChecked()) {
                    CrIsIntRetCreditOn = "Y";
                    ll_InteretDeRetard.setVisibility(View.VISIBLE);
                }else{
                    CrIsIntRetCreditOn = "N";
                    ll_InteretDeRetard.setVisibility(View.GONE);
                }
                break;
            case R.id.SwitchCrIsRappDatEchCred:
                if (SW_CrIsRappDatEchCred.isChecked()) {
                    CrIsRappDatEchCred = "Y";
                }else{
                    CrIsRappDatEchCred = "N";
                }
                break;
            case R.id.SwitchCrIsInterOffSiCapRembAnt:
                if (SW_CrIsInterOffSiCapRembAnt.isChecked()) {
                    CrIsInterOffSiCapRembAnt = "Y";
                }else{
                    CrIsInterOffSiCapRembAnt = "N";
                }
                break;
            case R.id.SwitchCrIsCpteCourantOnRembCred:
                if (SW_CrIsCpteCourantOnRembCred.isChecked()) {
                    CrIsCpteCourantOnRembCred = "Y";
                }else{
                    CrIsCpteCourantOnRembCred = "N";
                }
                break;

            case R.id.SwitchCrIsCptEAVRemCredOn:
                if (SW_CrIsCpteEAVOnRembCred.isChecked()) {
                    CrIsCpteEAVOnRembCred = "Y";
                }else{
                    CrIsCpteEAVOnRembCred = "N";
                }
                break;

            case R.id.SwitchCrIsCptEAPRemCredOn:
                if (SW_CrIsCptEAPRemCredOn.isChecked()) {
                    CrIsCptEAPRemCredOn = "Y";
                }else{
                    CrIsCptEAPRemCredOn = "N";
                }
                break;

            case R.id.SwitchCrIsCptEATRemCredOn:
                if (SW_CrIsCptEATRemCredOn.isChecked()) {
                    CrIsCptEATRemCredOn = "Y";
                }else{
                    CrIsCptEATRemCredOn = "N";
                }
                break;
            case R.id.SwitchCrIsMtPlafByObjet:
                if (SW_CrIsMtPlafByObjet.isChecked()) {
                    CrIsMtPlafByObjet = "Y";
                }else{
                    CrIsMtPlafByObjet = "N";
                }
                break;
            case R.id.SwitchCrIsDeblocTransfDAVOn:
                if (SW_CrIsDeblocTransfDAVOn.isChecked()) {
                    CrIsDeblocTransfDAVOn = "Y";
                }else{
                    CrIsDeblocTransfDAVOn = "N";
                }
                break;
            case R.id.SwitchCrIsModDecaissByObjet:
                if (SW_CrIsModDecaissByObjet.isChecked()) {
                    CrIsModDecaissByObjet = "Y";
                }else{
                    CrIsModDecaissByObjet = "N";
                }
                break;
            case R.id.SwitchCrIsFraisDecaissByDAV:
                if (SW_CrIsFraisDecaissByDAV.isChecked()) {
                    CrIsFraisDecaissByDAV = "Y";
                }else{
                    CrIsFraisDecaissByDAV = "N";
                }
                break;
            case R.id.SwitchCrIsFraisDeblocByDAV:
                if (SW_CrIsFraisDeblocByDAV.isChecked()) {
                    CrIsFraisDeblocByDAV = "Y";
                }else{
                    CrIsFraisDeblocByDAV = "N";
                }
                break;
            case R.id.SwitchCrIsFraisEtudByDAV:
                if (SW_CrIsFraisEtudByDAV.isChecked()) {
                    CrIsFraisEtudByDAV = "Y";
                }else{
                    CrIsFraisEtudByDAV = "N";
                }
                break;
            case R.id.SwitchCrIsGarMatExige:
                if (SW_CrIsGarMatExige.isChecked()) {
                    CrIsGarMatExige = "Y";
                }else{
                    CrIsGarMatExige = "N";
                }
                break;
            case R.id.SwitchCrIsMtPlafCredLeve:
                if (SW_CrIsMtPlafCredLeve.isChecked()) {
                    CrIsMtPlafCredLeve = "Y";
                }else{
                    CrIsMtPlafCredLeve = "N";
                }
                break;
            case R.id.SwitchCrNbAnAncNeg:
                if (SW_CrNbAnAncNeg.isChecked()) {
                    CrNbAnAncNeg = "Y";
                }else{
                    CrNbAnAncNeg = "N";
                }
                break;
            case R.id.SwitchCrIsAffCollCredOn:
                if (SW_CrIsAffCollCredOn.isChecked()) {
                    CrIsAffCollCredOn = "Y";
                }else{
                    CrIsAffCollCredOn = "N";
                }
                break;
            case R.id.SwitchCrIsPersMorAvalOn:
                if (SW_CrIsPersMorAvalOn.isChecked()) {
                    CrIsPersMorAvalOn = "Y";
                }else{
                    CrIsPersMorAvalOn = "N";
                }
                break;
            case R.id.SwitchCrIsAvalSansCredOn:
                if (SW_CrIsAvalSansCredOn.isChecked()) {
                    CrIsAvalSansCredOn = "Y";
                }else{
                    CrIsAvalSansCredOn = "N";
                }
                break;
            case R.id.SwitchCrIsGarCptEAPOn:
                if (SW_CrIsGarCptEAPOn.isChecked()) {
                    CrIsGarCptEAPOn = "Y";
                }else{
                    CrIsGarCptEAPOn = "N";
                }
                break;
            case R.id.SwitchCrIsGarCptEATOn:
                if (SW_CrIsGarCptEATOn.isChecked()) {
                    CrIsGarCptEATOn = "Y";
                }else{
                    CrIsGarCptEATOn = "N";
                }
                break;
            case R.id.SwitchCrIsGarCptEAVOn:
                if (SW_CrIsGarCptEAVOn.isChecked()) {
                    CrIsGarCptEAVOn = "Y";
                }else{
                    CrIsGarCptEAVOn = "N";
                }
                break;
            case R.id.SwitchCrIsGarBloqCptOblig:
                if (SW_CrIsGarBloqCptOblig.isChecked()) {
                    CrIsGarBloqCptOblig = "Y";
                }else{
                    CrIsGarBloqCptOblig = "N";
                }
                break;
            case R.id.SwitchCrIsCautionMorAvalAcc:
                if (SW_CrIsCautionMorAvalAcc.isChecked()) {
                    CrIsCautionMorAvalAcc = "Y";
                }else{
                    CrIsCautionMorAvalAcc = "N";
                }
                break;
            case R.id.SwitchCrIsTxCouvAvalOblig:
                if (SW_CrIsTxCouvAvalOblig.isChecked()) {
                    CrIsTxCouvAvalOblig = "Y";
                }else{
                    CrIsTxCouvAvalOblig = "N";
                }
                break;
            case R.id.SwitchCrIsTauxInteretAnOn:
                if (SW_CrIsTauxInteretAnOn.isChecked()) {
                    CrIsTauxInteretAnOn = "Y";
                    ll_TauxInteretAnOn.setVisibility(View.VISIBLE);
                }else{
                    CrIsTauxInteretAnOn = "N";
                    ll_TauxInteretAnOn.setVisibility(View.GONE);
                }
                break;
            case R.id.SwitchCrIsTxInteretRetardJrOn:
                if (SW_CrIsTxIntJrOn_IntRetCred.isChecked()) {
                    CrIsTxIntJrOn_IntRetCred = "Y";
                    ll_CrNatureJrTxIntRetardJr.setVisibility(View.VISIBLE);
                }else{
                    CrIsTxIntJrOn_IntRetCred = "N";
                    ll_CrNatureJrTxIntRetardJr.setVisibility(View.GONE);
                }
                break;
            case R.id.SwitchCrIsTxInteretPenRetardJrOn:
                if (SW_CrIsTxPenRetardOn.isChecked()) {
                    CrIsTxPenRetardOn = "Y";
                    ll_CrNatureJrTxIntPenRetardJr.setVisibility(View.VISIBLE);
                }else{
                    CrIsTxPenRetardOn = "N";
                    ll_CrNatureJrTxIntPenRetardJr.setVisibility(View.GONE);
                }
                break;
            case R.id.SwitchCrIsTxInteretJrOn:
                if (SW_CrIsTxInteretJrOn.isChecked()) {
                    CrIsTxInteretJrOn = "Y";
                    ll_CrNatureJrTxIntJr.setVisibility(View.VISIBLE);
                }else{
                    CrIsTxInteretJrOn = "N";
                    ll_CrNatureJrTxIntJr.setVisibility(View.GONE);
                }
                break;
            case R.id.SwitchCrIsTxIntNeg:
                if (SW_CrIsTxIntNeg.isChecked()) {
                    CrIsTxIntNeg = "Y";
                }else{
                    CrIsTxIntNeg = "N";
                }

                break;
            case R.id.SwitchCrIsDelaiGraceNegoc:
                if (SW_CrIsDelaiGraceNegoc.isChecked()) {
                   CrIsDelaiGraceNegoc = "Y";
                }else{
                    CrIsDelaiGraceNegoc = "N";
                }

                break;
            case R.id.SwitchCrIsJoursOuvresOnly:
                if (SW_CrIsJoursOuvresOnly.isChecked()) {
                    CrIsJoursOuvresOnly = "Y";
                }else{
                    CrIsJoursOuvresOnly = "N";
                }

                break;
//
            case R.id.SwitchCrIsTxIntDegressif:
                if (SW_CrIsTxIntDegressif.isChecked()) {
                    CrIsTxIntDegressif = "Y";
                    rbCrModeCalcInteretCompose.setChecked(true);
                    onRadioButtonClicked(rbCrModeCalcInteretCompose);
                }else{
                    CrIsTxIntDegressif = "N";
                    rbCrModeCalcInteretSimple.setChecked(true);
                    onRadioButtonClicked(rbCrModeCalcInteretSimple);
                }
                break;
            case R.id.SwitchCrIsTxGarMemObl:
                if (SW_CrIsTxGarMemObl.isChecked()) {
                    CrIsTxGarMemObl = "Y";
                    ET_CrTauxGarMemb.setVisibility(View.VISIBLE);
                }else{
                    CrIsTxGarMemObl = "N";
                    ET_CrTauxGarMemb.setVisibility(View.GONE);
                }

                break;
            case R.id.SwitchCrIsCouvPartSOn:
                if (SW_CrIsCouvPartSOn.isChecked()) {
                    CrIsCouvPartSOn = "Y";
                    ET_CrTxCouvPSOblig.setVisibility(View.VISIBLE);
                }else{
                    CrIsCouvPartSOn = "N";
                    ET_CrTxCouvPSOblig.setVisibility(View.GONE);
                }
                break;
            case R.id.SwitchCrIsFraisEtudDossOn:
                if (SW_CrIsFraisEtudDossOn.isChecked()) {
                    CrIsFraisEtudDossOn = "Y";
                    LL_CrNatFrEtudDoss.setVisibility(View.VISIBLE);
                    onRadioButtonClicked(rbCrNatFrEtudDossFixe);
                    onRadioButtonClicked(rbCrNatFrEtudDossTaux);
                    onRadioButtonClicked(rbCrNatFrEtudDossPlage);

                }else{
                    CrIsFraisEtudDossOn = "N";
                    LL_CrNatFrEtudDoss.setVisibility(View.GONE);
                    ET_CrValTxFrEtudDoss.setVisibility(View.GONE);
                    JR_CrBaseTxFrEtudDoss.setVisibility(View.GONE);
                    tv_plage_CrNatFrEtudDoss.setVisibility(View.GONE);
                }

                break;
            case R.id.SwitchCrIsFraisDeblocCredOn:
                if (SW_CrIsFraisDeblocCredOn.isChecked()) {
                    CrIsFraisDeblocCredOn = "Y";
//                    str = checked1?"Minimum en compte obligatoire":"le minimum en compte n'est pas obligatoire";

                    LL_CrNatFraisDeblocCred.setVisibility(View.VISIBLE);
                    onRadioButtonClicked(rbCrNatFraisDeblocCredFixe);
                    onRadioButtonClicked(rbCrNatFraisDeblocCredTaux);
                    onRadioButtonClicked(rbCrNatFraisDeblocCredPlage);
                }else{
                    CrIsFraisDeblocCredOn = "N";
                    LL_CrNatFraisDeblocCred.setVisibility(View.GONE);
                    ET_CrValTxFraisDeblocCred.setVisibility(View.GONE);
                    JR_CrBaseTxFraisDeblocCred.setVisibility(View.GONE);
                    tv_plage_CrNatFraisDeblocCred.setVisibility(View.GONE);
                }

                break;
            case R.id.SwitchCrIsFraisDecaissCredOn:
                if (SW_CrIsFraisDecaissCredOn.isChecked()) {
                    CrIsFraisDecaissCredOn = "Y";
//                    str = checked1?"Minimum en compte obligatoire":"le minimum en compte n'est pas obligatoire";
                    LL_CrNatFraisDecaissCred.setVisibility(View.VISIBLE);
                    onRadioButtonClicked(rbCrNatFraisDecaissCredFixe);
                    onRadioButtonClicked(rbCrNatFraisDecaissCredTaux);
                    onRadioButtonClicked(rbCrNatFraisDecaissCredPlage);
                }else{
                    CrIsFraisDecaissCredOn = "N";
                    LL_CrNatFraisDecaissCred.setVisibility(View.GONE);
                    ET_CrValTxFraisDecaissCred.setVisibility(View.GONE);
                    JR_CrBaseTxFraisDecaissCred.setVisibility(View.GONE);
                    tv_plage_CrNatFraisDecaissCred.setVisibility(View.GONE);
                }

                break;

        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked1 = ((RadioButton) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {
//
            case R.id.rbCrModeCalcInteretSimple:
                if (rbCrModeCalcInteretSimple.isChecked()) {
                    CrModeCalcInteret ="S";
                    SW_CrIsTxIntDegressif.setChecked(false);

                }
                break;
            case R.id.rbCrModeCalcInteretCompose:
                if (rbCrModeCalcInteretCompose.isChecked()) {
                    CrModeCalcInteret ="C";
                    SW_CrIsTxIntDegressif.setChecked(true);

                }
                break;
            case R.id.rbCrModeCalcInteretDegressif:
                if (rbCrModeCalcInteretDegressif.isChecked()) {
                    CrModeCalcInteret ="D";

                }
                break;
            case R.id.rbCrPeriodCalcInteretJournalier:
                if (rbCrPeriodCalcInteretJournalier.isChecked()) {
                    CrPeriodCalcInteret ="J";

                }
                break;

            case R.id.rbCrPeriodCalcInteretMensuel:
                if (rbCrPeriodCalcInteretMensuel.isChecked()) {
                    CrPeriodCalcInteret ="M";

                }
                break;
            case R.id.rbCrPeriodCalcInteretTrimestre:
                if (rbCrPeriodCalcInteretTrimestre.isChecked()) {
                    CrPeriodCalcInteret ="T";

                }
                break;
            case R.id.rbCrPeriodCalcInteretAnnee:
                if (rbCrPeriodCalcInteretAnnee.isChecked()) {
                    CrPeriodCalcInteret ="A";

                }
                break;
            case R.id.rbCrPeriodCalcInteretRetardJournalier:
                if (rbCrPeriodCalcInteretRetardJournalier.isChecked()) {
                    CrPeriod_IntRetCred ="J";

                }
                break;

            case R.id.rbCrPeriodCalcInteretRetardMensuel:
                if (rbCrPeriodCalcInteretRetardMensuel.isChecked()) {
                    CrPeriod_IntRetCred ="M";

                }
                break;
            case R.id.rbCrPeriodCalcInteretRetardTrimestre:
                if (rbCrPeriodCalcInteretRetardTrimestre.isChecked()) {
                    CrPeriod_IntRetCred ="T";

                }
                break;
            case R.id.rbCrPeriodCalcInteretRetardAnnee:
                if (rbCrPeriodCalcInteretRetardAnnee.isChecked()) {
                    CrPeriod_IntRetCred ="A";

                }
                break;
            case R.id.rbCrPeriodCalcInteretPenRetardMensuel:
                if (rbCrPeriodCalcInteretPenRetardMensuel.isChecked()) {
                    CrPeriodNatureTxPenRet ="M";

                }
                break;
            case R.id.rbCrPeriodCalcInteretPenRetardJournalier:
                if (rbCrPeriodCalcInteretPenRetardJournalier.isChecked()) {
                    CrPeriodNatureTxPenRet ="J";

                }
                break;
            case R.id.rbCrPeriodCalcInteretPenRetardTrimestre:
                if (rbCrPeriodCalcInteretPenRetardTrimestre.isChecked()) {
                    CrPeriodNatureTxPenRet ="T";

                }
                break;
            case R.id.rbCrPeriodCalcInteretPenRetardAnnee:
                if (rbCrPeriodCalcInteretPenRetardAnnee.isChecked()) {
                    CrPeriodNatureTxPenRet ="A";

                }
                break;
            case R.id.rbCrNatureJrTxIntJrLunVen:
                if (rbCrNatureJrTxIntJrLunVen.isChecked()) {
                    CrNatureJrTxIntJr ="S";

                }
                break;
            case R.id.rbCrNatureJrTxIntJrLunSam:
                if (rbCrNatureJrTxIntJrLunSam.isChecked()) {
                    CrNatureJrTxIntJr ="S";

                }
                break;
            case R.id.rbCrNatureJrTxIntJrJrOuvertGu:
                if (rbCrNatureJrTxIntJrJrOuvertGu.isChecked()) {
                    CrNatureJrTxIntJr ="G";

                }
                break;
            case R.id.rbCrNatureJrTxIntJrTous:
                if (rbCrNatureJrTxIntJrTous.isChecked()) {
                    CrNatureJrTxIntJr ="T";

                }
                break;
            case R.id.rbCrNatureJrTxIntPenRetardJrLunVen:
                if (rbCrNatureJrTxIntPenRetardJrLunVen.isChecked()) {
                    CrNatureJrTxPenRet ="V";

                }
                break;
            case R.id.rbCrNatureJrTxIntPenRetardJrLunSam:
                if (rbCrNatureJrTxIntPenRetardJrLunSam.isChecked()) {
                    CrNatureJrTxPenRet ="S";

                }
                break;
            case R.id.rbCrNatureJrTxIntPenRetardJrJrOuvertGu:
                if (rbCrNatureJrTxIntPenRetardJrJrOuvertGu.isChecked()) {
                    CrNatureJrTxPenRet ="G";

                }
                break;
            case R.id.rbCrNatureJrTxIntPenRetardJrTous:
                if (rbCrNatureJrTxIntPenRetardJrTous.isChecked()) {
                    CrNatureJrTxPenRet ="T";

                }
                break;
            case R.id.rbCrNatureJrTxIntRetardJrLunVen:
                if (rbCrNatureJrTxIntRetardJrLunVen.isChecked()) {
                    CrNatJrTxIntJr_IntRetCred ="V";

                }
                break;
            case R.id.rbCrNatureJrTxIntRetardJrLunSam:
                if (rbCrNatureJrTxIntRetardJrLunSam.isChecked()) {
                    CrNatJrTxIntJr_IntRetCred ="S";

                }
                break;
            case R.id.rbCrNatureJrTxIntRetardJrJrOuvertGu:
                if (rbCrNatureJrTxIntRetardJrJrOuvertGu.isChecked()) {
                    CrNatJrTxIntJr_IntRetCred ="G";

                }
                break;
            case R.id.rbCrNatureJrTxIntRetardJrTous:
                if (rbCrNatureJrTxIntRetardJrTous.isChecked()) {
                    CrNatJrTxIntJr_IntRetCred ="T";

                }
                break;
            case R.id.rbCrTypTxInterFixe:
                if (rbCrTypTxInterFixe.isChecked()) {
                    CrTypTxInter ="F";
                    tv_plage_CrTypTxInter.setVisibility(View.GONE);
                    JR_CrBase_tauxInt.setVisibility(View.VISIBLE);
                    ET_CrValTxInter.setVisibility(View.VISIBLE);

                }

                break;
            case R.id.rbCrTypTxInterPlage:
                if (rbCrTypTxInterPlage.isChecked()) {
                    CrTypTxInter ="P";
                    tv_plage_CrTypTxInter.setVisibility(View.VISIBLE);
                    ET_CrValTxInter.setVisibility(View.GONE);
                    JR_CrBase_tauxInt.setVisibility(View.GONE);

                }

                break;
            case R.id.rbCrTypTxInterPenRetardFixe:
                if (rbCrTypTxInterPenRetardFixe.isChecked()) {
                    CrNatureTxPenRet ="F";
                    tv_plage_cr_PenRetard.setVisibility(View.GONE);
                    JR_Crbase_tauxIntPenRetard.setVisibility(View.VISIBLE);
                    ET_CrValTxInterPenRetard.setVisibility(View.VISIBLE);

                }
                break;
            case R.id.rbCrTypTxInterPenRetardPlage:
                if (rbCrTypTxInterPenRetardPlage.isChecked()) {
                    CrNatureTxPenRet ="P";
                    tv_plage_cr_PenRetard.setVisibility(View.VISIBLE);
                    ET_CrValTxInterPenRetard.setVisibility(View.GONE);
                    JR_Crbase_tauxIntPenRetard.setVisibility(View.GONE);

                }
                break;
            case R.id.rbCrTypTxInterRetardFixe:
                if (rbCrTypTxInterRetardFixe.isChecked()) {
                    CrNatureTxInt_IntRetCred ="F";
                    tv_plage_cr_IntRetard.setVisibility(View.GONE);
                    JR_Crbase_tauxIntRetard.setVisibility(View.VISIBLE);
                    ET_CrValTxInterRetard.setVisibility(View.VISIBLE);

                }
                break;

            case R.id.rbCrTypTxInterRetardPlage:
                if (rbCrTypTxInterRetardPlage.isChecked()) {
                    CrNatureTxInt_IntRetCred ="P";
                    tv_plage_cr_IntRetard.setVisibility(View.VISIBLE);
                    ET_CrValTxInterRetard.setVisibility(View.GONE);
                    JR_Crbase_tauxIntRetard.setVisibility(View.GONE);

                }
                break;
            case R.id.rbCrNatFrEtudDossFixe:
                if (rbCrNatFrEtudDossFixe.isChecked()) {
                    CrNatFrEtudDoss ="F";
                    ET_CrValTxFrEtudDoss.setVisibility(View.VISIBLE);
                    JR_CrBaseTxFrEtudDoss.setVisibility(View.GONE);
                    tv_plage_CrNatFrEtudDoss.setVisibility(View.GONE);
                    ET_CrValTxFrEtudDoss.setHint("Montant des frais d'étude");
//
                }
//
                break;
            case R.id.rbCrNatFrEtudDossTaux:
                if (rbCrNatFrEtudDossTaux.isChecked()){
                    CrNatFrEtudDoss ="T";
                    if(SW_CrIsFraisEtudDossOn.isChecked()) {
                        ET_CrValTxFrEtudDoss.setVisibility(View.VISIBLE);
                        JR_CrBaseTxFrEtudDoss.setVisibility(View.VISIBLE);
                        tv_plage_CrNatFrEtudDoss.setVisibility(View.GONE);
                        ET_CrValTxFrEtudDoss.setHint("Taux des frais d'étude");
                    }
                }
//
//
                break;
            case R.id.rbCrNatFrEtudDossPlage:
                if (rbCrNatFrEtudDossPlage.isChecked()) {
                    if(SW_CrIsFraisEtudDossOn.isChecked()) {
                        CrNatFrEtudDoss ="P";
                        JR_CrBaseTxFrEtudDoss.setVisibility(View.GONE);
                        ET_CrValTxFrEtudDoss.setVisibility(View.GONE);
                        tv_plage_CrNatFrEtudDoss.setVisibility(View.VISIBLE);
                    }
                }
                break;

            case R.id.rbCrNatureTxIntAnFixe:
                if (rbCrNatureTxIntAnFixe.isChecked()) {
                    CrNatureTxIntAn ="F";
                    tv_plage_tia_cc.setVisibility(View.GONE);
                    JR_CrBaseTxIntAn.setVisibility(View.GONE);
                    ET_CrTauxValTxIntAn.setVisibility(View.VISIBLE);
                    input_layout_CrTauxValTxIntAn.setHint("Montant en FCFA");
                }
                break;

            case R.id.rbCrNatureTxIntAnTaux:
                if (rbCrNatureTxIntAnTaux.isChecked()){
                    CrNatureTxIntAn ="T";
                    if(SW_CrIsTauxInteretAnOn.isChecked()) {
                        input_layout_CrTauxValTxIntAn.setVisibility(View.VISIBLE);
                        ET_CrTauxValTxIntAn.setVisibility(View.VISIBLE);
                        JR_CrBaseTxIntAn.setVisibility(View.VISIBLE);
                        tv_plage_tia_cc.setVisibility(View.GONE);
                        input_layout_CrTauxValTxIntAn.setHint("Taux annuel (%)");
                    }else{
                        input_layout_CrTauxValTxIntAn.setVisibility(View.GONE);
                        ET_CrTauxValTxIntAn.setVisibility(View.GONE);
                        JR_CrBaseTxIntAn.setVisibility(View.GONE);
                        tv_plage_tia_cc.setVisibility(View.GONE);
                    }
                }
                break;

            case R.id.rbCrNatureTxIntAnPlage:
                if (rbCrNatureTxIntAnPlage.isChecked()) {
                    CrNatureTxIntAn ="P";
                    if(SW_CrIsTauxInteretAnOn.isChecked()) {
                        JR_CrBaseTxIntAn.setVisibility(View.GONE);
                        input_layout_CrTauxValTxIntAn.setVisibility(View.GONE);
                        ET_CrTauxValTxIntAn.setVisibility(View.GONE);
                        tv_plage_tia_cc.setVisibility(View.VISIBLE);
                    }else{

                        JR_CrBaseTxIntAn.setVisibility(View.GONE);
                        input_layout_CrTauxValTxIntAn.setVisibility(View.GONE);
                        ET_CrTauxValTxIntAn.setVisibility(View.GONE);
                        tv_plage_tia_cc.setVisibility(View.GONE);
                    }
                }
                break;
            case R.id.rbCrNatFraisDeblocCredFixe:
                if (rbCrNatFraisDeblocCredFixe.isChecked()) {
                    CrNatFraisDeblocCred ="F";
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
                }
//
                break;
            case R.id.rbCrNatFraisDeblocCredTaux:
                if (rbCrNatFraisDeblocCredTaux.isChecked()){
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
                break;
            case R.id.rbCrNatFraisDeblocCredPlage:
                if (rbCrNatFraisDeblocCredPlage.isChecked()) {
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
                }
                break;
            case R.id.rbCrNatFraisDecaissCredFixe:
                if (rbCrNatFraisDecaissCredFixe.isChecked()) {
                    CrNatFraisDecaissCred ="F";
                    if(SW_CrIsFraisDecaissCredOn.isChecked()) {
                        ET_CrValTxFraisDecaissCred.setVisibility(View.VISIBLE);
                        JR_CrBaseTxFraisDecaissCred.setVisibility(View.GONE);
                        tv_plage_CrNatFraisDecaissCred.setVisibility(View.GONE);
                        ET_CrValTxFraisDecaissCred.setHint("Montant des frais de décaissement");
                    }else{
                        ET_CrValTxFraisDecaissCred.setVisibility(View.GONE);
                        JR_CrBaseTxFraisDecaissCred.setVisibility(View.GONE);
                        tv_plage_CrNatFraisDecaissCred.setVisibility(View.GONE);
                    }
                }
//
                break;
            case R.id.rbCrNatFraisDecaissCredTaux:
                if (rbCrNatFraisDecaissCredTaux.isChecked()){
                    CrNatFraisDecaissCred ="T";
                    if(SW_CrIsFraisDecaissCredOn.isChecked()) {

                        ET_CrValTxFraisDecaissCred.setVisibility(View.VISIBLE);
                        JR_CrBaseTxFraisDecaissCred.setVisibility(View.VISIBLE);
                        tv_plage_CrNatFraisDecaissCred.setVisibility(View.GONE);
                        ET_CrValTxFraisDecaissCred.setHint("Taux des frais de décaissement");
                    }else{

                        ET_CrValTxFraisDecaissCred.setVisibility(View.GONE);
                        JR_CrBaseTxFraisDecaissCred.setVisibility(View.GONE);
                        tv_plage_CrNatFraisDecaissCred.setVisibility(View.GONE);
                    }
                }

                break;
            case R.id.rbCrNatFraisDecaissCredPlage:
                if (rbCrNatFraisDecaissCredPlage.isChecked()) {
                    CrNatFraisDecaissCred ="P";
                    if(SW_CrIsFraisDecaissCredOn.isChecked()) {
                        JR_CrBaseTxFraisDecaissCred.setVisibility(View.GONE);
                        ET_CrValTxFraisDecaissCred.setVisibility(View.GONE);
                        tv_plage_CrNatFraisDecaissCred.setVisibility(View.VISIBLE);
                    }else{
                        JR_CrBaseTxFraisDecaissCred.setVisibility(View.GONE);
                        ET_CrValTxFraisDecaissCred.setVisibility(View.GONE);
                        tv_plage_CrNatFraisDecaissCred.setVisibility(View.GONE);
                    }
                }
                break;
        }
    }



    /**
     * Checks whether all files are filled. If so then calls AddEAVAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addProduitCredit() {

if (!STRING_EMPTY.equals(ET_CrCode.getText().toString().trim()) &&
        !STRING_EMPTY.equals(ET_CrLibelle.getText().toString().trim()) &&
        !STRING_EMPTY.equals(ET_CrDureeMin.getText().toString().trim()) &&
        !STRING_EMPTY.equals(ET_CrDureeMax.getText().toString().trim())
        ){
    monProduitCredit = new Credit();
    monProduitCredit.setCrCode(MyData.normalizeSymbolsAndAccents(ET_CrCode.getText().toString()) );
    monProduitCredit.setCrLibelle(MyData.normalizeSymbolsAndAccents(ET_CrLibelle.getText().toString()) );
    monProduitCredit.setCrDureeMin(ET_CrDureeMin.getText().toString());
    monProduitCredit.setCrDureeMax(ET_CrDureeMax.getText().toString());
    monProduitCredit.setCrNaturePas("F");
    monProduitCredit.setCrNbreUPas((String) SW_CrNbreUPas.getSelectedItem());
    monProduitCredit.setCrNbreJrDelaiGrace(ET_CrNbreJrDelaiGrace.getText().toString());
    monProduitCredit.setCrIsDelaiGraceNegoc(CrIsDelaiGraceNegoc);
    monProduitCredit.setCrIsJoursOuvresOnly(CrIsJoursOuvresOnly);
    monProduitCredit.setCrTypTxInter(CrTypTxInter);
    monProduitCredit.setCrValTxInter(ET_CrValTxInter.getText().toString());
    monProduitCredit.setCrBaseTxInter(Credit.encodeCrBaseTxInter(JR_CrBase_tauxInt.getText().toString()));
    monProduitCredit.setCrIsTauxInteretAnOn(CrIsTauxInteretAnOn);
    monProduitCredit.setCrNatureTxIntAn(CrNatureTxIntAn);
    monProduitCredit.setCrTauxValTxIntAn(ET_CrTauxValTxIntAn.getText().toString());
    monProduitCredit.setCrBaseTxIntAn(Credit.encodeCrBaseTxIntAn(JR_CrBaseTxIntAn.getText().toString()));
    monProduitCredit.setCrIsTxIntDegressif(CrIsTxIntDegressif);
    monProduitCredit.setCrModeCalcInteret(CrModeCalcInteret);
    monProduitCredit.setCrPeriodCalcInteret(CrPeriodCalcInteret);
    monProduitCredit.setCrIsTxInteretJrOn(CrIsTxInteretJrOn);
    monProduitCredit.setCrNatureJrTxIntJr(CrNatureJrTxIntJr);
    monProduitCredit.setCrIsTxIntNeg(CrIsTxIntNeg);
    monProduitCredit.setCrNatureTxPenRet(CrNatureTxPenRet);
    monProduitCredit.setCrValTxPenRet(ET_CrValTxInterPenRetard.getText().toString());
    monProduitCredit.setCrBaseTxPenRet(Credit.encodeCrBaseTxPenRet(JR_Crbase_tauxIntPenRetard.getText().toString()));
    monProduitCredit.setCrPeriodNatureTxPenRet(CrPeriodNatureTxPenRet);
    monProduitCredit.setCrIsTxPenRetardOn(CrIsTxPenRetardOn);
    monProduitCredit.setCrNatureJrTxPenRet(CrNatureJrTxPenRet);
    monProduitCredit.setCrIsSoldPenRetObligSiNewEchCred(CrIsSoldPenRetObligSiNewEchCred);
    monProduitCredit.setCrIsIntRetCreditOn(CrIsIntRetCreditOn);
    monProduitCredit.setCrNatureTxInt_IntRetCred(CrNatureTxInt_IntRetCred);
    monProduitCredit.setCrTauxInt_IntRetCred(ET_CrValTxInterRetard.getText().toString());
    monProduitCredit.setCrBasexInt_IntRetCred(Credit.encodeCrBasexInt_IntRetCred(JR_Crbase_tauxIntRetard.getText().toString()));
    monProduitCredit.setCrPeriod_IntRetCred(CrPeriod_IntRetCred);
    monProduitCredit.setCrIsTxIntJrOn_IntRetCred(CrIsTxIntJrOn_IntRetCred);
    monProduitCredit.setCrNatJrTxIntJr_IntRetCred(CrNatJrTxIntJr_IntRetCred);
    monProduitCredit.setCrIsSoldIntRetObligSiNewEchCred(CrIsSoldIntRetObligSiNewEchCred);
    monProduitCredit.setCrNbreAvalDmde(ET_CrNbreAvalDmde.getText().toString());
    monProduitCredit.setCrNbreMinAvalExig(ET_CrNbreMinAvalExig.getText().toString());
    monProduitCredit.setCrTxCouvCrAval(ET_CrTxCouvCrAval.getText().toString());
    monProduitCredit.setCrIsTxCouvAvalOblig(CrIsTxCouvAvalOblig);
    monProduitCredit.setCrIsCautionMorAvalAcc(CrIsCautionMorAvalAcc);
    monProduitCredit.setCrIsGarBloqCptOblig(CrIsGarBloqCptOblig);
    monProduitCredit.setCrIsGarCptEAVOn(CrIsGarCptEAVOn);
    monProduitCredit.setCrIsGarCptEATOn(CrIsGarCptEATOn);
    monProduitCredit.setCrIsGarCptEAPOn(CrIsGarCptEAPOn);
    monProduitCredit.setCrMtMaxSansAval(ET_CrMtMaxSansAval.getText().toString());
    monProduitCredit.setCrIsAvalSansCredOn(CrIsAvalSansCredOn);
    monProduitCredit.setCrIsTxGarMemObl(CrIsTxGarMemObl);
    monProduitCredit.setCrTauxGarMemb(ET_CrTauxGarMemb.getText().toString());
    monProduitCredit.setCrIsPersMorAvalOn(CrIsPersMorAvalOn);
    monProduitCredit.setCrIsCouvPartSOn(CrIsCouvPartSOn);
    monProduitCredit.setCrTxCouvPSOblig(ET_CrTxCouvPSOblig.getText().toString());
    monProduitCredit.setCrIsAffCollCredOn(CrIsAffCollCredOn);
    monProduitCredit.setCrNbreAnAncMinCred(ET_CrNbreAnAncMinCred.getText().toString());
    monProduitCredit.setCrNbAnAncNeg(CrNbAnAncNeg);
    monProduitCredit.setCrMtPlafondMax(ET_CrMtPlafondMax.getText().toString());
    monProduitCredit.setCrIsMtPlafCredLeve(CrIsMtPlafCredLeve);
    monProduitCredit.setCrIsGarMatExige(CrIsGarMatExige);
    monProduitCredit.setCrIsFraisEtudDossOn(CrIsFraisEtudDossOn);
    monProduitCredit.setCrNatFrEtudDoss(CrNatFrEtudDoss);
    monProduitCredit.setCrValTxFrEtudDoss(ET_CrValTxFrEtudDoss.getText().toString());
    monProduitCredit.setCrBaseTxFrEtudDoss(Credit.encodeCrBaseTxFrEtudDoss(JR_CrBaseTxFrEtudDoss.getText().toString()));
    monProduitCredit.setCrIsFraisDeblocCredOn(CrIsFraisDeblocCredOn);
    monProduitCredit.setCrNatFraisDeblocCred(CrNatFraisDeblocCred);
    monProduitCredit.setCrValTxFraisDeblocCred(ET_CrValTxFraisDeblocCred.getText().toString());
    monProduitCredit.setCrBaseTxFraisDeblocCred(Credit.encodeCrBaseTxFraisDeblocCred(JR_CrBaseTxFraisDeblocCred.getText().toString()));
    monProduitCredit.setCrIsFraisDecaissCredOn(CrIsFraisDecaissCredOn);
    monProduitCredit.setCrNatFraisDecaissCred(CrNatFraisDecaissCred);
    monProduitCredit.setCrValTxFraisDecaissCred(ET_CrValTxFraisDecaissCred.getText().toString());
    monProduitCredit.setCrBaseFraisDecaissCred(Credit.encodeCrBaseFraisDecaissCred(JR_CrBaseTxFraisDecaissCred.getText().toString()));
    monProduitCredit.setCrIsFraisEtudByDAV(CrIsFraisEtudByDAV);
    monProduitCredit.setCrIsFraisDeblocByDAV(CrIsFraisDeblocByDAV);
    monProduitCredit.setCrIsFraisDecaissByDAV(CrIsFraisDecaissByDAV);
    monProduitCredit.setCrIsModDecaissByObjet(CrIsModDecaissByObjet);
    monProduitCredit.setCrIsDeblocTransfDAVOn(CrIsDeblocTransfDAVOn);
    monProduitCredit.setCrIsMtPlafByObjet(CrIsMtPlafByObjet);
    monProduitCredit.setCrModeRemb(Credit.encodeCrModeRemb(JR_CrModeRemb.getText().toString()));
    monProduitCredit.setCrIsCpteEAVOnRembCred(CrIsCpteEAVOnRembCred);
    monProduitCredit.setCrIsCpteCourantOnRembCred(CrIsCpteCourantOnRembCred);
    monProduitCredit.setCrIsCptEATRemCredOn(CrIsCptEATRemCredOn);
    monProduitCredit.setCrIsCptEAPRemCredOn(CrIsCptEAPRemCredOn);
    monProduitCredit.setCrIsInterOffSiCapRembAnt(CrIsInterOffSiCapRembAnt);
    monProduitCredit.setCrTxInterEchNHon(ET_CrTxInterEchNHon.getText().toString());
    monProduitCredit.setCrBaseInterEchNHon(Credit.encodeCrBaseInterEchNHon(JR_CrBaseInterEchNHon.getText().toString()));
    monProduitCredit.setCrPlanningRembCred(Credit.encodeCrPlanningRembCred(JR_CrPlanningRembCred.getText().toString()));
    monProduitCredit.setCrIsRappDatEchCred(CrIsRappDatEchCred);
    monProduitCredit.setCrModelTextRappEchRemb(ET_CrModelTextRappEchRemb.getText().toString());
    monProduitCredit.setCrNbreJrAvantDatEch(ET_CrNbreJrAvantDatEch.getText().toString());
    monProduitCredit.setCrNbreJrApreEchSiNHon(ET_CrNbreJrApreEchSiNHon.getText().toString());
    monProduitCredit.setCrUser(MyData.USER_ID+"");
    monProduitCredit.setCrDateHCree(null);
    monProduitCredit.setCrUserModif(MyData.USER_ID+"");
    monProduitCredit.setCrDatHModif(null);
    monProduitCredit.setCrCaisseId(MyData.CAISSE_ID+"");
    monProduitCredit.setCrGuichetId(null);
    monProduitCredit.setCrIsTVAOn(st_CrIsTVAOn);


//to manage plage data
   //Frais d'étude FEC
    for (int i=0; i<plageDataListFEC.size();i++){
        tabPlageDebutFEC +=";"+plageDataListFEC.get(i).getPdValDe().replaceAll(",", "").trim();
        tabPlageFinFEC +=";"+plageDataListFEC.get(i).getPdValA().replaceAll(",", "").trim();
        tabPlageValeurFEC +=";"+plageDataListFEC.get(i).getPdValTaux().replaceAll(",", "").trim();
        tabPlageBaseFEC +=";"+plageDataListFEC.get(i).getPdBase().replaceAll(",", "").trim();
        tabPlageNatureFEC +=";"+plageDataListFEC.get(i).getPdNature().replaceAll(",", "").trim();
        tabPlagePdMtMinimumFEC +=";"+plageDataListFEC.get(i).getPdMtMinimum().replaceAll(",", "").trim();
    }
//Déblocage FDB
    for (int i=0; i<plageDataListFDB.size();i++){
        tabPlageDebutFDB +=";"+plageDataListFDB.get(i).getPdValDe().replaceAll(",", "").trim();
        tabPlageFinFDB +=";"+plageDataListFDB.get(i).getPdValA().replaceAll(",", "").trim();
        tabPlageValeurFDB +=";"+plageDataListFDB.get(i).getPdValTaux().replaceAll(",", "").trim();
        tabPlageBaseFDB +=";"+plageDataListFDB.get(i).getPdBase().replaceAll(",", "").trim();
        tabPlageNatureFDB +=";"+plageDataListFDB.get(i).getPdNature().replaceAll(",", "").trim();
    }
//Décaissement FCX
    for (int i=0; i<plageDataListFCX.size();i++){
        tabPlageDebutFCX +=";"+plageDataListFCX.get(i).getPdValDe().replaceAll(",", "").trim();
        tabPlageFinFCX +=";"+plageDataListFCX.get(i).getPdValA().replaceAll(",", "").trim();
        tabPlageValeurFCX +=";"+plageDataListFCX.get(i).getPdValTaux().replaceAll(",", "").trim();
        tabPlageBaseFCX +=";"+plageDataListFCX.get(i).getPdBase().replaceAll(",", "").trim();
        tabPlageNatureFCX +=";"+plageDataListFCX.get(i).getPdNature().replaceAll(",", "").trim();
    }
    //Taux d'intérêt de crédit TIC
    for (int i=0; i<plageDataListTIC.size();i++){
        tabPlageDebutTIC +=";"+plageDataListTIC.get(i).getPdValDe().replaceAll(",", "").trim();
        tabPlageFinTIC +=";"+plageDataListTIC.get(i).getPdValA().replaceAll(",", "").trim();
        tabPlageValeurTIC +=";"+plageDataListTIC.get(i).getPdValTaux().replaceAll(",", "").trim();
        tabPlageBaseTIC +=";"+plageDataListTIC.get(i).getPdBase().replaceAll(",", "").trim();
        tabPlageNatureTIC +=";"+plageDataListTIC.get(i).getPdNature().replaceAll(",", "").trim();
    }
    //Taux d'intérêt annuel TIA
    for (int i=0; i<plageDataListTIA.size();i++){
        tabPlageDebutTIA +=";"+plageDataListTIA.get(i).getPdValDe().replaceAll(",", "").trim();
        tabPlageFinTIA +=";"+plageDataListTIA.get(i).getPdValA().replaceAll(",", "").trim();
        tabPlageValeurTIA +=";"+plageDataListTIA.get(i).getPdValTaux().replaceAll(",", "").trim();
        tabPlageBaseTIA +=";"+plageDataListTIA.get(i).getPdBase().replaceAll(",", "").trim();
        tabPlageNatureTIA +=";"+plageDataListTIA.get(i).getPdNature().replaceAll(",", "").trim();
    }

            new AddEAVAsyncTask().execute();
        } else {
            Toast.makeText(CreateProduitCredit.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for adding a movie
     */
    private class AddEAVAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(CreateProduitCredit.this);
            pDialog.setMessage("Ajout d'un produit Crédit. Veuillez patienter...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(monProduitCredit.KEY_CREDIT_Numero, monProduitCredit.getCrNumero()); //1
            httpParams.put(monProduitCredit.KEY_CREDIT_Code, monProduitCredit.getCrCode());//2
            httpParams.put(monProduitCredit.KEY_CREDIT_Libelle, monProduitCredit.getCrLibelle());//3
            httpParams.put(monProduitCredit.KEY_CREDIT_DureeMin, monProduitCredit.getCrDureeMin());//4
            httpParams.put(monProduitCredit.KEY_CREDIT_DureeMax, monProduitCredit.getCrDureeMax());//5
            httpParams.put(monProduitCredit.KEY_CREDIT_NaturePas, monProduitCredit.getCrNaturePas());//6
            httpParams.put(monProduitCredit.KEY_CREDIT_NbreUPas, monProduitCredit.getCrNbreUPas());//7
            httpParams.put(monProduitCredit.KEY_CrNbreJrDelaiGrace, monProduitCredit.getCrNbreJrDelaiGrace());//8
            httpParams.put(monProduitCredit.KEY_CrIsDelaiGraceNegoc, monProduitCredit.getCrIsDelaiGraceNegoc());//9
            httpParams.put(monProduitCredit.KEY_CrIsJoursOuvresOnly, monProduitCredit.getCrIsJoursOuvresOnly());//10
            httpParams.put(monProduitCredit.KEY_CREDIT_TypTxInter, monProduitCredit.getCrTypTxInter());//11
            httpParams.put(monProduitCredit.KEY_CREDIT_ValTxInter, monProduitCredit.getCrValTxInter());//12
            httpParams.put(monProduitCredit.KEY_CREDIT_Base_TxInter, monProduitCredit.getCrBaseTxInter());//13
            httpParams.put(monProduitCredit.KEY_CrIsTauxInteretAnOn, monProduitCredit.getCrIsTauxInteretAnOn());//14
            httpParams.put(monProduitCredit.KEY_CrNatureTxIntAn, monProduitCredit.getCrNatureTxIntAn());//15
            httpParams.put(monProduitCredit.KEY_CrTauxValTxIntAn, monProduitCredit.getCrTauxValTxIntAn());//16
            httpParams.put(monProduitCredit.KEY_CrBaseTxIntAn, monProduitCredit.getCrBaseTxIntAn());//17
            httpParams.put(monProduitCredit.KEY_CrIsTxIntDegressif, monProduitCredit.getCrIsTxIntDegressif());//18
            httpParams.put(monProduitCredit.KEY_CrModeCalcInteret, monProduitCredit.getCrModeCalcInteret());//19
            httpParams.put(monProduitCredit.KEY_CrPeriodCalcInteret, monProduitCredit.getCrPeriodCalcInteret());//20
            httpParams.put(monProduitCredit.KEY_CrIsTxInteretJrOn, String.valueOf(monProduitCredit.getCrIsTxInteretJrOn()));//21
            httpParams.put(monProduitCredit.KEY_CrNatureJrTxIntJr, String.valueOf(monProduitCredit.getCrNatureJrTxIntJr()));//22
            httpParams.put(monProduitCredit.KEY_CREDIT_IsTxIntNeg, monProduitCredit.getCrIsTxIntNeg());//23
            httpParams.put(monProduitCredit.KEY_CrNatureTxPenRet, String.valueOf(monProduitCredit.getCrNatureTxPenRet()));//24
            httpParams.put(monProduitCredit.KEY_CrValTxPenRet, String.valueOf(monProduitCredit.getCrValTxPenRet()));//25
            httpParams.put(monProduitCredit.KEY_CrBaseTxPenRet, String.valueOf(monProduitCredit.getCrBaseTxPenRet()));//26
            httpParams.put(monProduitCredit.KEY_CrPeriodNatureTxPenRet, String.valueOf(monProduitCredit.getCrPeriodNatureTxPenRet()));//27
            httpParams.put(monProduitCredit.KEY_CrIsTxPenRetardOn, String.valueOf(monProduitCredit.getCrIsTxPenRetardOn()));//28
            httpParams.put(monProduitCredit.KEY_CrNatureJrTxPenRet, String.valueOf(monProduitCredit.getCrNatureJrTxPenRet()));//29
            httpParams.put(monProduitCredit.KEY_CrIsSoldPenRetObligSiNewEchCred, String.valueOf(monProduitCredit.getCrIsSoldPenRetObligSiNewEchCred()));//30
            httpParams.put(monProduitCredit.KEY_CrIsIntRetCreditOn, String.valueOf(monProduitCredit.getCrIsIntRetCreditOn()));//31
            httpParams.put(monProduitCredit.KEY_CrNatureTxInt_IntRetCred, String.valueOf(monProduitCredit.getCrNatureTxInt_IntRetCred()));//32
            httpParams.put(monProduitCredit.KEY_CrTauxInt_IntRetCred, String.valueOf(monProduitCredit.getCrTauxInt_IntRetCred()));//33
            httpParams.put(monProduitCredit.KEY_CrBasexInt_IntRetCred, String.valueOf(monProduitCredit.getCrBasexInt_IntRetCred()));//34
            httpParams.put(monProduitCredit.KEY_CrPeriod_IntRetCred, String.valueOf(monProduitCredit.getCrPeriod_IntRetCred()));//35
            httpParams.put(monProduitCredit.KEY_CrIsTxIntJrOn_IntRetCred, String.valueOf(monProduitCredit.getCrIsTxIntJrOn_IntRetCred()));//36
            httpParams.put(monProduitCredit.KEY_CrNatJrTxIntJr_IntRetCred, monProduitCredit.getCrNatJrTxIntJr_IntRetCred());//37
            httpParams.put(monProduitCredit.KEY_CrIsSoldIntRetObligSiNewEchCred, String.valueOf(monProduitCredit.getCrIsSoldIntRetObligSiNewEchCred()));//38
            httpParams.put(monProduitCredit.KEY_CREDIT_NbreAvalDmde, monProduitCredit.getCrNbreAvalDmde());//39
            httpParams.put(monProduitCredit.KEY_CREDIT_NbreMinAvalExig, monProduitCredit.getCrNbreMinAvalExig());//40
            httpParams.put(monProduitCredit.KEY_CREDIT_TxCouvCrAval, monProduitCredit.getCrTxCouvCrAval());//41
            httpParams.put(monProduitCredit.KEY_CREDIT_IsTxCouvAvalOblig, monProduitCredit.getCrIsTxCouvAvalOblig());//42
            httpParams.put(monProduitCredit.KEY_CREDIT_IsCautionMorAvalAcc, String.valueOf(monProduitCredit.getCrIsCautionMorAvalAcc()));//43
            httpParams.put(monProduitCredit.KEY_CREDIT_IsGarBloqCptOblig, String.valueOf(monProduitCredit.getCrIsGarBloqCptOblig()));//44
            httpParams.put(monProduitCredit.KEY_CREDIT_IsGarCptEAVOn, String.valueOf(monProduitCredit.getCrIsGarCptEAVOn()));//45
            httpParams.put(monProduitCredit.KEY_CREDIT_IsGarCptEATOn, String.valueOf(monProduitCredit.getCrIsGarCptEATOn()));//46
            httpParams.put(monProduitCredit.KEY_CREDIT_IsGarCptEAPOn, String.valueOf(monProduitCredit.getCrIsGarCptEAPOn()));//47
            httpParams.put(monProduitCredit.KEY_CREDIT_MtMaxSansAval, monProduitCredit.getCrMtMaxSansAval());//48
            httpParams.put(monProduitCredit.KEY_CREDIT_IsAvalSansCredOn, String.valueOf(monProduitCredit.getCrIsAvalSansCredOn()));//49
            httpParams.put(monProduitCredit.KEY_CREDIT_IsTxGarMemObl, String.valueOf(monProduitCredit.getCrIsTxGarMemObl()));//50
            httpParams.put(monProduitCredit.KEY_CREDIT_TauxGarMemb, monProduitCredit.getCrTauxGarMemb());//51
            httpParams.put(monProduitCredit.KEY_CREDIT_IsPersMorAvalOn, String.valueOf(monProduitCredit.getCrIsPersMorAvalOn()));//52
            httpParams.put(monProduitCredit.KEY_CREDIT_IsCouvPartSOn, String.valueOf(monProduitCredit.getCrIsCouvPartSOn()));//53
            httpParams.put(monProduitCredit.KEY_CREDIT_TxCouvPSOblig, monProduitCredit.getCrTxCouvPSOblig());//54
            httpParams.put(monProduitCredit.KEY_CREDIT_IsAffCollCredOn, String.valueOf(monProduitCredit.getCrIsAffCollCredOn()));//55
            httpParams.put(monProduitCredit.KEY_CREDIT_NbreAnAncMinCred, monProduitCredit.getCrNbreAnAncMinCred());//56
            httpParams.put(monProduitCredit.KEY_CREDIT_NbAnAncNeg, String.valueOf(monProduitCredit.getCrNbAnAncNeg()));//57
            httpParams.put(monProduitCredit.KEY_CREDIT_MtPlafondMax, monProduitCredit.getCrMtPlafondMax());//58
            httpParams.put(monProduitCredit.KEY_CREDIT_IsMtPlafCredLeve, String.valueOf(monProduitCredit.getCrIsMtPlafCredLeve()));//59
            httpParams.put(monProduitCredit.KEY_CREDIT_IsGarMatExige, String.valueOf(monProduitCredit.getCrIsGarMatExige()));//60
            httpParams.put(monProduitCredit.KEY_CREDIT_IsFraisEtudDossOn, String.valueOf(monProduitCredit.getCrIsFraisEtudDossOn()));//61
            httpParams.put(monProduitCredit.KEY_CREDIT_NatFrEtudDoss, monProduitCredit.getCrNatFrEtudDoss());//62
            httpParams.put(monProduitCredit.KEY_CREDIT_ValTxFrEtudDoss, monProduitCredit.getCrValTxFrEtudDoss());//63
            httpParams.put(monProduitCredit.KEY_CREDIT_BaseTxFrEtudDoss, monProduitCredit.getCrBaseTxFrEtudDoss());//64
            httpParams.put(monProduitCredit.KEY_CREDIT_IsFraisDeblocCredOn, String.valueOf(monProduitCredit.getCrIsFraisDeblocCredOn()));//65
            httpParams.put(monProduitCredit.KEY_CREDIT_NatFraisDeblocCred, monProduitCredit.getCrNatFraisDeblocCred());//66
            httpParams.put(monProduitCredit.KEY_CREDIT_ValTxFraisDeblocCred, monProduitCredit.getCrValTxFraisDeblocCred());//67
            httpParams.put(monProduitCredit.KEY_CREDIT_BaseTxFraisDeblocCred, monProduitCredit.getCrBaseTxFraisDeblocCred());//68
            httpParams.put(monProduitCredit.KEY_CREDIT_IsFraisDecaissCredOn, String.valueOf(monProduitCredit.getCrIsFraisDecaissCredOn()));//69
            httpParams.put(monProduitCredit.KEY_CREDIT_NatFraisDecaissCred, monProduitCredit.getCrNatFraisDecaissCred());//70
            httpParams.put(monProduitCredit.KEY_CREDIT_ValTxFraisDecaissCred, monProduitCredit.getCrValTxFraisDecaissCred());//71
            httpParams.put(monProduitCredit.KEY_CREDIT_BaseFraisDecaissCred, monProduitCredit.getCrBaseFraisDecaissCred());//72
            httpParams.put(monProduitCredit.KEY_CREDIT_IsFraisEtudByDAV, String.valueOf(monProduitCredit.getCrIsFraisEtudByDAV()));//73
            httpParams.put(monProduitCredit.KEY_CREDIT_IsFraisDeblocByDAV, String.valueOf(monProduitCredit.getCrIsFraisDeblocByDAV()));//74
            httpParams.put(monProduitCredit.KEY_CREDIT_IsFraisDecaissByDAV, String.valueOf(monProduitCredit.getCrIsFraisDecaissByDAV()));//75
            httpParams.put(monProduitCredit.KEY_CREDIT_IsModDecaissByObjet, String.valueOf(monProduitCredit.getCrIsModDecaissByObjet()));//76
            httpParams.put(monProduitCredit.KEY_CREDIT_IsDeblocTransfDAVOn, String.valueOf(monProduitCredit.getCrIsDeblocTransfDAVOn()));//77
            httpParams.put(monProduitCredit.KEY_CREDIT_IsMtPlafByObjet, String.valueOf(monProduitCredit.getCrIsMtPlafByObjet()));//78
            httpParams.put(monProduitCredit.KEY_CREDIT_ModeRemb, monProduitCredit.getCrModeRemb());//79
            httpParams.put(monProduitCredit.KEY_CrIsCpteEAVOnRembCred, String.valueOf(monProduitCredit.getCrIsCpteEAVOnRembCred()));//80
            httpParams.put(monProduitCredit.KEY_CrIsCpteCourantOnRembCred, String.valueOf(monProduitCredit.getCrIsCpteCourantOnRembCred()));//81
            httpParams.put(monProduitCredit.KEY_CREDIT_IsCptEATRemCredOn, String.valueOf(monProduitCredit.getCrIsCptEATRemCredOn()));//82
            httpParams.put(monProduitCredit.KEY_CREDIT_IsCptEAPRemCredOn, String.valueOf(monProduitCredit.getCrIsCptEAPRemCredOn()));//83
            httpParams.put(monProduitCredit.KEY_CREDIT_IsInterOffSiCapRembAnt, String.valueOf(monProduitCredit.getCrIsInterOffSiCapRembAnt()));//84
            httpParams.put(monProduitCredit.KEY_CREDIT_TxInterEchNHon, monProduitCredit.getCrTxInterEchNHon());//85
            httpParams.put(monProduitCredit.KEY_CREDIT_BaseInterEchNHon, monProduitCredit.getCrBaseInterEchNHon());//86
            httpParams.put(monProduitCredit.KEY_CREDIT_PlanningRembCred, monProduitCredit.getCrPlanningRembCred());//87
            httpParams.put(monProduitCredit.KEY_CREDIT_IsRappDatEchCred, String.valueOf(monProduitCredit.getCrIsRappDatEchCred()));//88
            httpParams.put(monProduitCredit.KEY_CREDIT_ModelTextRappEchRemb, monProduitCredit.getCrModelTextRappEchRemb());//89
            httpParams.put(monProduitCredit.KEY_CREDIT_NbreJrAvantDatEch, monProduitCredit.getCrNbreJrAvantDatEch());//90
            httpParams.put(monProduitCredit.KEY_CREDIT_NbreJrApreEchSiNHon, monProduitCredit.getCrNbreJrApreEchSiNHon());//91

            httpParams.put(monProduitCredit.KEY_CREDIT_User, monProduitCredit.getCrUser());//93
            httpParams.put(monProduitCredit.KEY_CREDIT_DateHCree, monProduitCredit.getCrDateHCree());//94
            httpParams.put(monProduitCredit.KEY_CREDIT_UserModif, monProduitCredit.getCrUserModif());//95
            httpParams.put(monProduitCredit.KEY_CREDIT_DatHModif, monProduitCredit.getCrDatHModif());//96
            httpParams.put(monProduitCredit.KEY_CREDIT_CaisseId, monProduitCredit.getCrCaisseId());//97
            httpParams.put(monProduitCredit.KEY_CREDIT_GuichetId, monProduitCredit.getCrGuichetId());//98
            httpParams.put(monProduitCredit.KEY_CrIsTVAOn, monProduitCredit.getCrIsTVAOn());





            httpParams.put(KEY_CREDIT_PLAGE_FRAIS_ETUDE_DEBUT, tabPlageDebutFEC);
            httpParams.put(KEY_CREDIT_PLAGE_FRAIS_ETUDE_FIN, tabPlageFinFEC);
            httpParams.put(KEY_CREDIT_PLAGE_FRAIS_ETUDE_VALEUR, tabPlageValeurFEC);
            httpParams.put(KEY_CREDIT_PLAGE_FRAIS_ETUDE_BASE, tabPlageBaseFEC);
            httpParams.put(KEY_CREDIT_PLAGE_FRAIS_ETUDE_NATURE, tabPlageNatureFEC);
            httpParams.put(KEY_CREDIT_PLAGE_FRAIS_ETUDE_PdMtMinimum, tabPlagePdMtMinimumFEC);

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

            httpParams.put(KEY_TIA_DEBUT, tabPlageDebutTIA);
            httpParams.put(KEY_TIA_FIN, tabPlageFinTIA);
            httpParams.put(KEY_TIA_VALEUR, tabPlageValeurTIA);
            httpParams.put(KEY_TIA_BASE, tabPlageBaseTIA);
            httpParams.put(KEY_TIA_NATURE, tabPlageNatureTIA);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_credit.php", "POST", httpParams);
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
                        Toast.makeText(CreateProduitCredit.this,
                                "Produit Crédit Ajouté", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        plageDataListFEC.clear();
                        plageDataListFDB.clear();
                        plageDataListFCX.clear();
                        plageDataListTIC.clear();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CreateProduitCredit.this,
                                "Une erreur s'est produite lors de l'ajout du produit crédit",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}