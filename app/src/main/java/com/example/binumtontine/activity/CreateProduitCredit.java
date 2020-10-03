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

public class CreateProduitCredit extends AppCompatActivity implements SERVER_ADDRESS {
    private Credit monProduitCredit;
    private static final String KEY_SUCCESS = "success";




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
    private String CrTypTxInterPenRetard;
    private String CrTypTxInterRetard;
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
    private Switch SW_CrIsInterOffSiCapRembAnt;
    private EditText ET_CrTxInterEchNHon;
    private JRSpinner JR_CrBaseInterEchNHon;
    private JRSpinner JR_CrPlanningRembCred;
    private Switch SW_CrIsRappDatEchCred;
    private EditText ET_CrModelTextRappEchRemb;
    private EditText ET_CrNbreJrAvantDatEch;
    private EditText ET_CrNbreJrApreEchSiNHon;
    private Switch SW_CrIsTxIntDegressif;
    private Switch SW_CrIsDelaiGraceNegoc;
    private Switch SW_CrIsJoursOuvresOnly;
    private SwitchCompat SW_CrIsTxInteretJrOn;
    private SwitchCompat SW_CrIsTxPenRetardOn;
    private SwitchCompat SW_CrIsTxIntJrOn_IntRetCred;
//    private String CrUser;
//    private String CrDateHCree;
//    private String CrUserModif;
//    private String CrDatHModif;
//    private String CrCaisseId;
//    private String CrGuichetId;



    private LinearLayout LL_CrNatFrEtudDoss;
    private LinearLayout LL_CrNatFraisDeblocCred;
    private LinearLayout LL_CrNatFraisDecaissCred;

    private TextInputLayout layout_TauxCrNatFrEtudDoss;
    private TextInputLayout layout_TauxCrValTxFraisDeblocCred;

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
    private TextView tv_plage_CrNatFraisDeblocCred;
    private TextView tv_plage_CrNatFraisDecaissCred;
    public static ArrayList<ModelPlageData> plageDataListTIC = new ArrayList<ModelPlageData>(); //to manage plageData taux d'intérêt
    public static ArrayList<ModelPlageData> plageDataListFEC = new ArrayList<ModelPlageData>(); //to manage plageData frais etude
    public static ArrayList<ModelPlageData> plageDataListFDB = new ArrayList<ModelPlageData>(); //to manage plageData frais deblocage
    public static ArrayList<ModelPlageData> plageDataListFCX = new ArrayList<ModelPlageData>(); //to manage plageData frais decaissement
    private TextView tv_config_plage_tiv;
    private String CrModeCalcInteret;
    private String CrPeriodCalcInteret;
    private String CrPeriodNatureTxPenRet;
    private String CrNatureJrTxIntJr;
    private String CrNatureJrTxPenRet;
    private String CrNatureTxInt_IntRetCred;
    private String CrPeriod_IntRetCred;
    private String CrNatJrTxIntJr_IntRetCred;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_add_movie);
      //  setContentView(R.layout.fragment_param_produit_eav);
        setContentView(R.layout.activity_credit);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_produit_eav);
        setSupportActionBar(toolbar);
        setToolbarTitle(); */


        tv_header_produit = (TextView) findViewById(R.id.header_produit);
        tv_header_produit.setText("Produit Crédit\n"+"Caisse: "+MyData.CAISSE_NAME);

        tv_plage_CrTypTxInter = (TextView) findViewById(R.id.tv_plage_tic_cc);
        tv_plage_cr_PenRetard = (TextView) findViewById(R.id.tv_plage_cr_PenRetard);
        tv_plage_cr_IntRetard = (TextView) findViewById(R.id.tv_plage_cr_IntRetard);
        tv_plage_CrNatFrEtudDoss = (TextView) findViewById(R.id.tv_plage_CrNatFrEtudDoss);
        tv_plage_CrNatFraisDeblocCred = (TextView) findViewById(R.id.tv_plage_CrNatFraisDeblocCred);
        tv_plage_CrNatFraisDecaissCred = (TextView) findViewById(R.id.tv_plage_CrNatFraisDecaissCred);
        ET_CrCode = (EditText) findViewById(R.id.input_txt_Code_credit);
        ET_CrLibelle = (EditText) findViewById(R.id.input_txt_LibelleCredit);
        ET_CrDureeMax = (EditText) findViewById(R.id.input_txt_CrDureeMax);
        ET_CrDureeMin = (EditText) findViewById(R.id.input_txt_CrDureeMin);
        ET_CrNbreJrDelaiGrace = (EditText) findViewById(R.id.input_txt_CrNbreJrDelaiGrace);
//        rbCrNaturePasFixe = (RadioButton)findViewById(R.id.rbCrNaturePasFixe);
//        rbCrNaturePasSaut = (RadioButton)findViewById(R.id.rbCrNaturePasSaut);
//        ET_CrNbreUPas = (RadioButton)findViewById(R.id.rbCrNaturePasSaut);
        rbCrTypTxInterFixe = (RadioButton)findViewById(R.id.rbCrTypTxInterFixe);
        rbCrTypTxInterPlage = (RadioButton)findViewById(R.id.rbCrTypTxInterPlage);
        rbCrTypTxInterPenRetardFixe = (RadioButton)findViewById(R.id.rbCrTypTxInterPenRetardFixe);
        rbCrTypTxInterPenRetardPlage = (RadioButton)findViewById(R.id.rbCrTypTxInterPenRetardPlage);
        rbCrTypTxInterRetardFixe = (RadioButton)findViewById(R.id.rbCrTypTxInterRetardFixe);
        rbCrTypTxInterRetardPlage = (RadioButton)findViewById(R.id.rbCrTypTxInterRetardPlage);
        /*rbCrTypTxInterDuree = (RadioButton)findViewById(R.id.rbCrNaturePasSaut);
        rbCrTypTxInterMontant = (RadioButton)findViewById(R.id.rbCrNaturePasSaut);
        rbCrTypTxInterMontantDuree = (RadioButton)findViewById(R.id.rbCrNaturePasSaut);
        */
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
        SW_CrIsTxIntDegressif = (Switch) findViewById(R.id.SwitchCrIsTxIntDegressif);
        SW_CrIsDelaiGraceNegoc = (Switch) findViewById(R.id.SwitchCrIsDelaiGraceNegoc);
        SW_CrIsJoursOuvresOnly = (Switch) findViewById(R.id.SwitchCrIsJoursOuvresOnly);
        SW_CrIsTxInteretJrOn = (SwitchCompat) findViewById(R.id.SwitchCrIsTxInteretJrOn);
        SW_CrIsTxPenRetardOn = (SwitchCompat) findViewById(R.id.SwitchCrIsTxInteretPenRetardJrOn);
        SW_CrIsTxIntJrOn_IntRetCred = (SwitchCompat) findViewById(R.id.SwitchCrIsTxInteretRetardJrOn);
        ET_CrModelTextRappEchRemb = (EditText) findViewById(R.id.input_txt_CrModelTextRappEchRemb);
        ET_CrNbreJrAvantDatEch = (EditText) findViewById(R.id.input_txt_CrNbreJrAvantDatEch);
        ET_CrNbreJrApreEchSiNHon = (EditText) findViewById(R.id.input_txt_CrNbreJrApreEchSiNHon);

        LL_CrNatFrEtudDoss = (LinearLayout) findViewById(R.id.ll_CrNatFrEtudDoss);
        LL_CrNatFraisDeblocCred = (LinearLayout) findViewById(R.id.ll_CrNatFraisDeblocCred);
        LL_CrNatFraisDecaissCred = (LinearLayout) findViewById(R.id.ll_CrNatFraisDecaissCred);
        layout_TauxCrNatFrEtudDoss = (TextInputLayout) findViewById(R.id.input_layout_TauxCrNatFrEtudDoss);
        layout_TauxCrValTxFraisDeblocCred = (TextInputLayout) findViewById(R.id.input_layout_TauxCrValTxFraisDeblocCred);

        tv_plage_CrTypTxInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux d'intérêt crédit";
                    ListPlageDataTICActivity.IS_TO_CREATE_OR_TO_UPDATE = true;
                    Intent i = new Intent(CreateProduitCredit.this, ListPlageDataTICActivity.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(CreateProduitCredit.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        //provisoire
        tv_plage_cr_PenRetard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux pénalité de retard";
                    ListPlageDataTICActivity.IS_TO_CREATE_OR_TO_UPDATE = true;
                    Intent i = new Intent(CreateProduitCredit.this, ListPlageDataTICActivity.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(CreateProduitCredit.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        //provisoire
        tv_plage_cr_IntRetard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux Intérêt de retard";
                    ListPlageDataTICActivity.IS_TO_CREATE_OR_TO_UPDATE = true;
                    Intent i = new Intent(CreateProduitCredit.this, ListPlageDataFDBActivity.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(CreateProduitCredit.this,
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
                    ListPlageDataFECActivity.IS_TO_CREATE_OR_TO_UPDATE = true;
                    Intent i = new Intent(CreateProduitCredit.this, ListPlageDataFECActivity.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(CreateProduitCredit.this,
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
                    ListPlageDataFDBActivity.IS_TO_CREATE_OR_TO_UPDATE = true;
                    Intent i = new Intent(CreateProduitCredit.this, ListPlageDataFDBActivity.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(CreateProduitCredit.this,
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
                    ListPlageDataFCXActivity.IS_TO_CREATE_OR_TO_UPDATE = true;
                    Intent i = new Intent(CreateProduitCredit.this, ListPlageDataFCXActivity.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(CreateProduitCredit.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
//

        onRadioButtonClicked(rbCrTypTxInterFixe);
        onRadioButtonClicked(rbCrTypTxInterPenRetardFixe);
        onRadioButtonClicked(rbCrTypTxInterRetardFixe);
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
                    addEAV();
                } else {
                    Toast.makeText(CreateProduitCredit.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });


    }
    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Ajout d'un produit EAV");




    }

    public void onSwitchButtonClicked(View view) {
        boolean checked1 = ((Switch) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {
//
            case R.id.SwitchCrIsTxIntDegressif:
                if (SW_CrIsTxIntDegressif.isChecked()) {
//                    str = checked1?"Minimum en compte obligatoire":"le minimum en compte n'est pas obligatoire";
                    rbCrModeCalcInteretCompose.setChecked(true);
                    onRadioButtonClicked(rbCrModeCalcInteretCompose);
                }else{
                    rbCrModeCalcInteretSimple.setChecked(true);
                    onRadioButtonClicked(rbCrModeCalcInteretSimple);
                }

                break;
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
                    CrNatureJrTxIntJr ="A";

                }
                break;
            case R.id.rbCrNatureJrTxIntJrLunSam:
                if (rbCrNatureJrTxIntJrLunSam.isChecked()) {
                    CrNatureJrTxIntJr ="B";

                }
                break;
            case R.id.rbCrNatureJrTxIntJrJrOuvertGu:
                if (rbCrNatureJrTxIntJrJrOuvertGu.isChecked()) {
                    CrNatureJrTxIntJr ="C";

                }
                break;
            case R.id.rbCrNatureJrTxIntJrTous:
                if (rbCrNatureJrTxIntJrTous.isChecked()) {
                    CrNatureJrTxIntJr ="D";

                }
                break;
            case R.id.rbCrNatureJrTxIntPenRetardJrLunVen:
                if (rbCrNatureJrTxIntPenRetardJrLunVen.isChecked()) {
                    CrNatureJrTxPenRet ="A";

                }
                break;
            case R.id.rbCrNatureJrTxIntPenRetardJrLunSam:
                if (rbCrNatureJrTxIntPenRetardJrLunSam.isChecked()) {
                    CrNatureJrTxPenRet ="B";

                }
                break;
            case R.id.rbCrNatureJrTxIntPenRetardJrJrOuvertGu:
                if (rbCrNatureJrTxIntPenRetardJrJrOuvertGu.isChecked()) {
                    CrNatureJrTxPenRet ="C";

                }
                break;
            case R.id.rbCrNatureJrTxIntPenRetardJrTous:
                if (rbCrNatureJrTxIntPenRetardJrTous.isChecked()) {
                    CrNatureJrTxPenRet ="D";

                }
                break;
            case R.id.rbCrNatureJrTxIntRetardJrLunVen:
                if (rbCrNatureJrTxIntRetardJrLunVen.isChecked()) {
                    CrNatJrTxIntJr_IntRetCred ="A";

                }
                break;
            case R.id.rbCrNatureJrTxIntRetardJrLunSam:
                if (rbCrNatureJrTxIntRetardJrLunSam.isChecked()) {
                    CrNatJrTxIntJr_IntRetCred ="B";

                }
                break;
            case R.id.rbCrNatureJrTxIntRetardJrJrOuvertGu:
                if (rbCrNatureJrTxIntRetardJrJrOuvertGu.isChecked()) {
                    CrNatJrTxIntJr_IntRetCred ="C";

                }
                break;
            case R.id.rbCrNatureJrTxIntRetardJrTous:
                if (rbCrNatureJrTxIntRetardJrTous.isChecked()) {
                    CrNatJrTxIntJr_IntRetCred ="D";

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
                    CrTypTxInterPenRetard ="F";
                    tv_plage_cr_PenRetard.setVisibility(View.GONE);
                    JR_Crbase_tauxIntPenRetard.setVisibility(View.VISIBLE);
                    ET_CrValTxInterPenRetard.setVisibility(View.VISIBLE);

                }
                break;
            case R.id.rbCrTypTxInterPenRetardPlage:
                if (rbCrTypTxInterPenRetardPlage.isChecked()) {
                    CrTypTxInterPenRetard ="P";
                    tv_plage_cr_PenRetard.setVisibility(View.VISIBLE);
                    ET_CrValTxInterPenRetard.setVisibility(View.GONE);
                    JR_Crbase_tauxIntPenRetard.setVisibility(View.GONE);

                }
                break;
            case R.id.rbCrTypTxInterRetardFixe:
                if (rbCrTypTxInterRetardFixe.isChecked()) {
                    CrTypTxInterRetard ="F";
                    tv_plage_cr_IntRetard.setVisibility(View.GONE);
                    JR_Crbase_tauxIntRetard.setVisibility(View.VISIBLE);
                    ET_CrValTxInterRetard.setVisibility(View.VISIBLE);

                }
                break;
            case R.id.rbCrTypTxInterRetardPlage:
                if (rbCrTypTxInterRetardPlage.isChecked()) {
                    CrTypTxInterRetard ="P";
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
//                    }
                   /* else{
//                        layout_TauxCrNatFrEtudDoss.setVisibility(View.GONE);
                        ET_CrValTxFrEtudDoss.setVisibility(View.GONE);
                        JR_CrBaseTxFrEtudDoss.setVisibility(View.GONE);
                        tv_plage_CrNatFrEtudDoss.setVisibility(View.GONE);
                    }*/
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
//                   // blkPlageEav.setVisibility(View.VISIBLE);
//                    layout_TauxAPreleveCpteEAV.setVisibility(View.GONE);
//                    layout_BaseTauxAPreleveCpteEAV.setVisibility(View.GONE);
//                    tv_config_plage_tiv.setVisibility(View.VISIBLE);
                }
                break;
        }
//        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }



    /**
     * Checks whether all files are filled. If so then calls AddEAVAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addEAV() {

if (!STRING_EMPTY.equals(ET_CrCode.getText().toString().trim()) &&
        !STRING_EMPTY.equals(ET_CrLibelle.getText().toString().trim()) &&
        !STRING_EMPTY.equals(ET_CrDureeMin.getText().toString().trim()) &&
        !STRING_EMPTY.equals(ET_CrDureeMax.getText().toString().trim())
        ){
    monProduitCredit = new Credit(
            ET_CrCode.getText().toString(),
            ET_CrLibelle.getText().toString(),
            ET_CrDureeMin.getText().toString(),
            ET_CrDureeMax.getText().toString(),
            "F",
            (String) SW_CrNbreUPas.getSelectedItem(),
            CrTypTxInter,
            ET_CrValTxInter.getText().toString(),
            JR_CrBase_tauxInt.getText().toString(),
            SW_CrIsTxIntNeg.isChecked(),
            ET_CrNbreAvalDmde.getText().toString(),
            ET_CrNbreMinAvalExig.getText().toString(),
            ET_CrTxCouvCrAval.getText().toString(),
            SW_CrIsTxCouvAvalOblig.isChecked(),
            SW_CrIsCautionMorAvalAcc.isChecked(),
            SW_CrIsGarBloqCptOblig.isChecked(),
            SW_CrIsGarCptEAVOn.isChecked(),
            SW_CrIsGarCptEATOn.isChecked(),
            SW_CrIsGarCptEAPOn.isChecked(),
            ET_CrMtMaxSansAval.getText().toString(),
            SW_CrIsAvalSansCredOn.isChecked(),
            SW_CrIsTxGarMemObl.isChecked(),
            ET_CrTauxGarMemb.getText().toString(),
            SW_CrIsPersMorAvalOn.isChecked(),
            SW_CrIsCouvPartSOn.isChecked(),
            ET_CrTxCouvPSOblig.getText().toString(),
            SW_CrIsAffCollCredOn.isChecked(),
            ET_CrNbreAnAncMinCred.getText().toString(),
            SW_CrNbAnAncNeg.isChecked(),
            ET_CrMtPlafondMax.getText().toString(),
            SW_CrIsMtPlafCredLeve.isChecked(),
            SW_CrIsGarMatExige.isChecked(),
            SW_CrIsFraisEtudDossOn.isChecked(),
            CrNatFrEtudDoss,
            ET_CrValTxFrEtudDoss.getText().toString(),
            JR_CrBaseTxFrEtudDoss.getText().toString(),
            SW_CrIsFraisDeblocCredOn.isChecked(),
            CrNatFraisDeblocCred,
            ET_CrValTxFraisDeblocCred.getText().toString(),
            JR_CrBaseTxFraisDeblocCred.getText().toString(),
            SW_CrIsFraisDecaissCredOn.isChecked(),
            CrNatFraisDecaissCred,ET_CrValTxFraisDecaissCred.getText().toString(),
            JR_CrBaseTxFraisDecaissCred.getText().toString(),
            SW_CrIsFraisEtudByDAV.isChecked(),
            SW_CrIsFraisDeblocByDAV.isChecked(),
            SW_CrIsFraisDecaissByDAV.isChecked(),
            SW_CrIsModDecaissByObjet.isChecked(),
            SW_CrIsDeblocTransfDAVOn.isChecked(),
            SW_CrIsMtPlafByObjet.isChecked(),
            JR_CrModeRemb.getText().toString(),
            SW_CrIsCptEATRemCredOn.isChecked(),
            SW_CrIsCptEAPRemCredOn.isChecked(),
            SW_CrIsInterOffSiCapRembAnt.isChecked(),
            ET_CrTxInterEchNHon.getText().toString(),
            JR_CrBaseInterEchNHon.getText().toString(),
            JR_CrPlanningRembCred.getText().toString(),
            SW_CrIsRappDatEchCred.isChecked(),
            ET_CrModelTextRappEchRemb.getText().toString(),
            ET_CrNbreJrAvantDatEch.getText().toString(),
            ET_CrNbreJrApreEchSiNHon.getText().toString(),
            MyData.USER_ID+"",
            null,
            MyData.USER_ID+"",
            null,
            MyData.CAISSE_ID+"",
            null,
            SW_CrIsTxIntDegressif.isChecked(),
            CrModeCalcInteret,
            CrPeriodCalcInteret,
            ET_CrNbreJrDelaiGrace.getText().toString(),
            SW_CrIsDelaiGraceNegoc.isChecked(),
            SW_CrIsJoursOuvresOnly.isChecked(),
            SW_CrIsTxInteretJrOn.isChecked(),
            CrNatureJrTxIntJr,
            CrTypTxInterPenRetard,
            ET_CrValTxInterPenRetard.getText().toString(),
            JR_Crbase_tauxIntPenRetard.getText().toString(),
            CrPeriodNatureTxPenRet,
            SW_CrIsTxPenRetardOn.isChecked(),
            CrNatureJrTxPenRet,
            CrTypTxInterRetard,
            ET_CrValTxInterRetard.getText().toString(),
            JR_Crbase_tauxIntRetard.getText().toString(),
            CrPeriod_IntRetCred,
            SW_CrIsTxIntJrOn_IntRetCred.isChecked(),
            CrNatJrTxIntJr_IntRetCred

    );


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
            httpParams.put(monProduitCredit.KEY_CREDIT_Numero, monProduitCredit.getCrNumero());
            httpParams.put(monProduitCredit.KEY_CREDIT_Code, monProduitCredit.getCrCode());
            httpParams.put(monProduitCredit.KEY_CREDIT_Libelle, monProduitCredit.getCrLibelle());
            httpParams.put(monProduitCredit.KEY_CREDIT_DureeMin, monProduitCredit.getCrDureeMin());
            httpParams.put(monProduitCredit.KEY_CREDIT_DureeMax, monProduitCredit.getCrDureeMax());
            httpParams.put(monProduitCredit.KEY_CREDIT_NaturePas, monProduitCredit.getCrNaturePas());
            httpParams.put(monProduitCredit.KEY_CREDIT_NbreUPas, monProduitCredit.getCrNbreUPas());
            httpParams.put(monProduitCredit.KEY_CREDIT_TypTxInter, monProduitCredit.getCrTypTxInter());
            httpParams.put(monProduitCredit.KEY_CREDIT_ValTxInter, monProduitCredit.getCrValTxInter());
            httpParams.put(monProduitCredit.KEY_CREDIT_Base_TxInter, monProduitCredit.getCrBaseTxInter());
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
            httpParams.put(monProduitCredit.KEY_CrModeCalcInteret, monProduitCredit.getCrModeCalcInteret());
            httpParams.put(monProduitCredit.KEY_CrPeriodCalcInteret, monProduitCredit.getCrPeriodCalcInteret());
            httpParams.put(monProduitCredit.KEY_CrNbreJrDelaiGrace, monProduitCredit.getCrNbreJrDelaiGrace());
            httpParams.put(monProduitCredit.KEY_CrIsDelaiGraceNegoc, String.valueOf(monProduitCredit.getCrIsDelaiGraceNegoc()));
            httpParams.put(monProduitCredit.KEY_CrIsJoursOuvresOnly, String.valueOf(monProduitCredit.getCrIsJoursOuvresOnly()));
            httpParams.put(monProduitCredit.KEY_CrIsTxInteretJrOn, String.valueOf(monProduitCredit.getCrIsTxInteretJrOn()));
            httpParams.put(monProduitCredit.KEY_CrNatureJrTxIntJr, String.valueOf(monProduitCredit.getCrNatureJrTxIntJr()));
            httpParams.put(monProduitCredit.KEY_CrNatureTxPenRet, String.valueOf(monProduitCredit.getCrNatureTxPenRet()));
            httpParams.put(monProduitCredit.KEY_CrValTxPenRet, String.valueOf(monProduitCredit.getCrValTxPenRet()));
            httpParams.put(monProduitCredit.KEY_CrBaseTxPenRet, String.valueOf(monProduitCredit.getCrBaseTxPenRet()));
            httpParams.put(monProduitCredit.KEY_CrPeriodNatureTxPenRet, String.valueOf(monProduitCredit.getCrPeriodNatureTxPenRet()));
            httpParams.put(monProduitCredit.KEY_CrIsTxPenRetardOn, String.valueOf(monProduitCredit.getCrIsTxPenRetardOn()));
            httpParams.put(monProduitCredit.KEY_CrNatureJrTxPenRet, String.valueOf(monProduitCredit.getCrNatureJrTxPenRet()));
            httpParams.put(monProduitCredit.KEY_CrNatureTxInt_IntRetCred, String.valueOf(monProduitCredit.getCrNatureTxInt_IntRetCred()));
            httpParams.put(monProduitCredit.KEY_CrTauxInt_IntRetCred, String.valueOf(monProduitCredit.getCrTauxInt_IntRetCred()));
            httpParams.put(monProduitCredit.KEY_CrBasexInt_IntRetCred, String.valueOf(monProduitCredit.getCrBasexInt_IntRetCred()));
            httpParams.put(monProduitCredit.KEY_CrPeriod_IntRetCred, String.valueOf(monProduitCredit.getCrPeriod_IntRetCred()));
            httpParams.put(monProduitCredit.KEY_CrIsTxIntJrOn_IntRetCred, String.valueOf(monProduitCredit.getCrIsTxIntJrOn_IntRetCred()));
            httpParams.put(monProduitCredit.KEY_CrNatJrTxIntJr_IntRetCred, String.valueOf(monProduitCredit.getCrNatJrTxIntJr_IntRetCred()));



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