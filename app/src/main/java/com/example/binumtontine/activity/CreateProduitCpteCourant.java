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
import android.app.assist.AssistStructure;
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
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateProduitCpteCourant extends AppCompatActivity implements SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";

    private static final String KEY_CcCode = "CcCode";
    private static final String KEY_CcLibelle = "CcLibelle";
    private static final String KEY_CcIsDecouvOn = "CcIsDecouvOn";
    private static final String KEY_CcMtMaxDecouv = "CcMtMaxDecouv";
    private static final String KEY_CcIsMaxDecouvNeg = "CcIsMaxDecouvNeg";
    private static final String KEY_CcNatureTxIntDecouv = "CcNatureTxIntDecouv";
    private static final String KEY_CcValTxIntDecouv = "CcValTxIntDecouv";
    private static final String KEY_CcBaseTxIntDecouv = "CcBaseTxIntDecouv";
    private static final String KEY_CcDureeMaxDecouv = "CcDureeMaxDecouv";
    private static final String KEY_CcNatureTypDureDecouv = "CcNatureTypDureDecouv";
    private static final String KEY_CcNatureTxMtAgio = "CcNatureTxMtAgio";
    private static final String KEY_CcValTxMtAgio = "CcValTxMtAgio";
    private static final String KEY_CcNatBaseAgio = "CcNatBaseAgio";
    private static final String KEY_CcIsChequierM1On = "CcIsChequierM1On";
    private static final String KEY_CcNbPagesCheqM1 = "CcNbPagesCheqM1";
    private static final String KEY_CcPrixVteCheqM1 = "CcPrixVteCheqM1";
    private static final String KEY_CcIsChequierM2On = "CcIsChequierM2On";
    private static final String KEY_CcNbPagesCheqM2 = "CcNbPagesCheqM2";
    private static final String KEY_CcPrixVteCheqM2 = "CcPrixVteCheqM2";
    private static final String KEY_CcIsChequierM3On = "CcIsChequierM3On";
    private static final String KEY_CcNbPagesCheqM3 = "CcNbPagesCheqM3";
    private static final String KEY_CcPrixVteCheqM3 = "CcPrixVteCheqM3";
    private static final String KEY_CcDureeValidCheq = "CcDureeValidCheq";
    private static final String KEY_CcNbMinSignatChq = "CcNbMinSignatChq";
    private static final String KEY_CcIsTxCommMvmOper = "CcIsTxCommMvmOper";
    private static final String KEY_CcNatTxComm = "CcNatTxComm";
    private static final String KEY_CcValTxCommMvm = "CcValTxCommMvm";
    private static final String KEY_CcBaseTxCommMvm = "CcBaseTxCommMvm";
    private static final String KEY_CC_CAISSE_NUMERO = "CcCaisseId";

    /*Update compte courant BEGIN*/

    private static final String KEY_CcIsAvanceSpecialOn = "CcIsAvanceSpecialOn";
    private static final String KEY_CcMtPlafondAvceSpec = "CcMtPlafondAvceSpec";
    private static final String KEY_CcIsPlafAvceSpecNegoc = "CcIsPlafAvceSpecNegoc";
    private static final String KEY_CcNatTauxIntAvceSpec = "CcNatTauxIntAvceSpec";
    private static final String KEY_CcTauxIntAvceSpec = "CcTauxIntAvceSpec";
    private static final String KEY_CcBaseTxIntAvceSpec = "CcBaseTxIntAvceSpec";
    private static final String KEY_CcNatTauxIntPenAvceSpec = "CcNatTauxIntPenAvceSpec";
    private static final String KEY_CcTauxIntPenAvceSpec = "CcTauxIntPenAvceSpec";
    private static final String KEY_CcBaseTxIntPenAvceSpec = "CcBaseTxIntPenAvceSpec";
    private static final String KEY_CcIsTxIntDegressif = "CcIsTxIntDegressif";


    private static final String KEY_CcIsDecouvertPermanentAutorise = "CcIsDecouvertPermanentAutorise";
    private static final String KEY_CcMaxMontantDecouvertPermanentAutorise = "CcMaxMontantDecouvertPermanentAutorise";
    private static final String KEY_CcMaxDureeDecouvertPermanentAutorise = "CcMaxDureeDecouvertPermanentAutorise";
    private static final String KEY_CcModeCalcInteret = "CcModeCalcInteret";
    private static final String KEY_CcModeCalcPenRetardAvceSpec = "CcModeCalcPenRetardAvceSpec";
    private static final String KEY_CcModeCalcIntRetardAvceSpec = "CcModeCalcIntRetardAvceSpec";
    private static final String KEY_CcModeCalcInteretAvceSpec = "CcModeCalcInteretAvceSpec";
    private static final String KEY_CcNaturePerTxInteret = "CcNaturePerTxInteret";
    private static final String KEY_CcIsTxInteretJrOn = "CcIsTxInteretJrOn";
    private static final String KEY_CcNatureJrTxIntJr = "CcNatureJrTxIntJr";
    private static final String KEY_CcNatTauxIntPenRetard = "CcNatTauxIntPenRetard";
    private static final String KEY_CcTauxIntPenRetard = "CcTauxIntPenRetard";
    private static final String KEY_CcBaseTxIntPenRetard = "CcBaseTxIntPenRetard";
    private static final String KEY_CcNaturePerTxPenRet = "CcNaturePerTxPenRet";
    private static final String KEY_CcIsTxPenRetardJrOn = "CcIsTxPenRetardJrOn";
    private static final String KEY_CcNatureJrTxPenRet = "CcNatureJrTxPenRet";
    private static final String KEY_CcNatureTxInt_IntRetDecouv = "CcNatureTxInt_IntRetDecouv";
    private static final String KEY_CcTauxInt_IntRetDecouv = "CcTauxInt_IntRetDecouv";
    private static final String KEY_CcBasexInt_IntRetDecouv = "CcBasexInt_IntRetDecouv";
    private static final String KEY_CcPeriod_IntRetDecouv = "CcPeriod_IntRetDecouv";
    private static final String KEY_CcIsTxIntJrOn_IntRetDecouv = "CcIsTxIntJrOn_IntRetDecouv";
    private static final String KEY_CcNatJrTxIntJr_IntRetDecouv = "CcNatJrTxIntJr_IntRetDecouv";
    private static final String KEY_CcDureeMaxAvceSpec = "CcDureeMaxAvceSpec";
    private static final String KEY_CcNaturePerTxInteretAvceSpec = "CcNaturePerTxInteretAvceSpec";
    private static final String KEY_CcIsTxInteretAvceSpecJrOn = "CcIsTxInteretAvceSpecJrOn";
    private static final String KEY_CcNatureJrTxIntAvceSpecJr = "CcNatureJrTxIntAvceSpecJr";
    private static final String KEY_CcIsTxPenRetardAvceSpecDegressif = "CcIsTxPenRetardAvceSpecDegressif";
    private static final String KEY_CcIsTxIntAvceSpecDegressif = "CcIsTxIntAvceSpecDegressif";
    private static final String KEY_CcPeriod_PenRetAvceSpec = "CcPeriod_PenRetAvceSpec";
    private static final String KEY_CcIsTxIntJrOn_PenRetAvceSpe = "CcIsTxIntJrOn_PenRetAvceSpe";
    private static final String KEY_CcNatJrTxIntJr_PenRetAvceSpe = "CcNatJrTxIntJr_PenRetAvceSpe";
    private static final String KEY_CcIsTxIntRetardAvceSpecDegressif = "CcIsTxIntRetardAvceSpecDegressif";
    private static final String KEY_CcNatureTxInt_IntRetAvceSpec = "CcNatureTxInt_IntRetAvceSpec";
    private static final String KEY_CcTauxInt_IntRetAvceSpec = "CcTauxInt_IntRetAvceSpec";
    private static final String KEY_CcBasexInt_IntRetAvceSpec = "CcBasexInt_IntRetAvceSpec";
    private static final String KEY_CcPeriod_IntRetAvceSpec = "CcPeriod_IntRetAvceSpec";
    private static final String KEY_CcIsTxIntJrOn_IntRetAvceSpe = "CcIsTxIntJrOn_IntRetAvceSpe";
    private static final String KEY_CcNatJrTxIntJr_IntRetAvceSpe = "CcNatJrTxIntJr_IntRetAvceSpe";
    /*Update compte courant END*/

    private static String STRING_EMPTY = "";
//    private  EditText CcPlageTxIntDecouvFrom;
//    private  EditText CcPlageTxIntDecouvTo;
//    private  EditText CcPlageTxMtAgioFrom;
//    private  EditText CcPlageTxMtAgioTo;
//    private  EditText CcPlageTxCommMvmFrom;
//    private  EditText CcPlageTxCommMvmTo;

    private EditText CcCode,CcLibelle,CcMtMaxDecouv,CcNatureTxIntDecouv,CcValTxIntDecouv,
            CcDureeMaxDecouv,CcNatureTypDureDecouv,
            CcNatureTxMtAgio,CcValTxMtAgio,CcNatBaseAgio,
            CcNbPagesCheqM1,CcPrixVteCheqM1,CcNbPagesCheqM2,CcPrixVteCheqM2,CcNbPagesCheqM3,
            CcPrixVteCheqM3,CcDureeValidCheq,CcNbMinSignatChq,CcNatTxComm,CcValTxCommMvm,
            CcMtPlafondAvceSpec,CcTauxIntAvceSpec,CcTauxIntPenAvceSpec,CcTauxInt_IntRetAvceSpec,
            CcTauxIntPenRetard, CcTauxInt_IntRetDecouv;
    private Switch CcIsDecouvOn,CcIsMaxDecouvNeg,CcIsChequierM1On,CcIsChequierM2On,CcIsChequierM3On,
            CcIsTxCommMvmOper,CcIsAvanceSpecialOn, CcIsPlafAvceSpecNegoc, CcIsTxIntDegressif, SwitchDecouvertPermanentAutoriseCC,
            SwitchCcIsTxIntAvceSpecDegressif, SwitchCcIsTxPenRetardAvceSpecDegressif, SwitchCcIsTxIntRetardAvceSpecDegressif;
    private SwitchCompat SwitchCcIsTxInteretJrOn, SwitchCcIsTxPenRetardJrOn, SwitchCcIsTxIntJrOn_IntRetDecouv, SwitchCcIsTxInteretAvceSpecJrOn,
            SwitchCcIsTxIntJrOn_PenRetAvceSpe, SwitchCcIsTxIntJrOn_IntRetAvceSpe;
    private String st_CcCode,st_CcLibelle,st_CcMtMaxDecouv,
            st_CcNatureTxIntDecouv,st_CcValTxIntDecouv,
            st_CcDureeMaxDecouv,st_CcNatureTypDureDecouv,st_CcNatureTxMtAgio,st_CcValTxMtAgio,
            st_CcNatBaseAgio,
            st_CcNbPagesCheqM1,st_CcPrixVteCheqM1,st_CcNbPagesCheqM2,
            st_CcPrixVteCheqM2,st_CcNbPagesCheqM3,st_CcPrixVteCheqM3,
            st_CcDureeValidCheq,st_CcNbMinSignatChq,st_CcNatTxComm,st_CcValTxCommMvm,
             st_CcMtPlafondAvceSpec, st_CcNatTauxIntAvceSpec,
            st_CcTauxIntAvceSpec, st_CcNatTauxIntPenAvceSpec,st_CcNatureTxInt_IntRetAvceSpec,st_CcTauxIntPenAvceSpec,
            st_CcNatTauxIntPenRetard, st_CcNatureTxInt_IntRetDecouv, st_CcTauxIntPenRetard, st_CcTauxInt_IntRetDecouv, st_CcBasexInt_IntRetDecouv;

    private String st_CcIsDecouvOn;
    private String st_CcIsMaxDecouvNeg;
    private String st_CcIsChequierM1On;
    private String st_CcIsChequierM2On;
    private String st_CcIsChequierM3On;
    private String st_CcIsTxCommMvmOper;
    private String st_CcIsAvanceSpecialOn;
    private String st_CcIsPlafAvceSpecNegoc;
    private String st_CcIsTxIntDegressif;





    private RadioButton CcNatureTxIntDecouvFixe,CcNatureTxIntDecouvTaux,CcNatureTxIntDecouvPlage;
    private RadioButton CcNatTauxIntPenRetardFixe,CcNatTauxIntPenRetardTaux,CcNatTauxIntPenRetardPlage;
    private RadioButton CcNatureTxInt_IntRetDecouvFixe,CcNatureTxInt_IntRetDecouvTaux,CcNatureTxInt_IntRetDecouvPlage;
    private RadioButton CcNatTauxIntAvceSpecFixe,CcNatTauxIntAvceSpecTaux,CcNatTauxIntAvceSpecPlage;
    private RadioButton CcNatTauxIntPenAvceSpecFixe,CcNatTauxIntPenAvceSpecTaux,CcNatTauxIntPenAvceSpecPlage;
    private RadioButton CcNatureTxInt_IntRetAvceSpecFixe,CcNatureTxInt_IntRetAvceSpecTaux,CcNatureTxInt_IntRetAvceSpecPlage;
    private RadioButton CcNatureTypDureDecouvJour,CcNatureTypDureDecouvSemaine,CcNatureTypDureDecouvMois;
    private RadioButton CcNatureTxMtAgioTaux, CcNatureTxMtAgioMontant, CcNatureTxMtAgioPlage;
    private RadioButton CcNatTxCommFixe, CcNatTxCommMontant, CcNatTxCommPlage;
    private JRSpinner mySpinnerBaseTxIntDecouv, mySpinnerCcBaseTxIntPenRetard, mySpinnerCcBasexInt_IntRetDecouv ; //pour gérer le spinner contenant les bases du Tx Int Decouvert
    private JRSpinner mySpinnerCcBaseTxIntAvceSpec; //pour gérer le spinner contenant les bases du Tx Int avance spéciale
    private JRSpinner mySpinnerCcBaseTxIntPenAvceSpec; //pour gérer le spinner contenant les bases du Tx Int penalités avance spéciale
    private JRSpinner mySpinnerCcBasexInt_IntRetAvceSpec; //pour gérer le spinner contenant les bases du Tx Int penalités avance spéciale
    private JRSpinner mySpinnerBaseTxMtAgio; //pour gérer le spinner contenant les bases taux
    private JRSpinner mySpinnerBaseTxCommMvm; //pour gérer le spinner contenant les bases taux


    private TextInputLayout input_layout_CcValTxMtAgio,input_layout_CcTauxIntPenRetard,input_layout_CcTauxInt_IntRetDecouv, input_layout_CcTauxIntAvceSpec,
            input_layout_CcTauxIntPenAvceSpec, input_layout_CcTauxInt_IntRetAvceSpec ;
    private TextInputLayout input_layout_CcBaseTxMtAgio,input_layout_CcBaseTxIntPenRetard, input_layout_CcBasexInt_IntRetDecouv,
            input_layout_CcBaseTxIntAvceSpec, input_layout_CcBaseTxIntPenAvceSpec, input_layout_CcBasexInt_IntRetAvceSpec;
    private TextInputLayout input_layout_CcBaseCalculAgiosCC;
    private TextInputLayout input_layout_CcBaseTxCommMvmCC;
    private TextInputLayout input_layout_CcValTxIntDecouv;
    private TextInputLayout input_layout_CcValTxCommMvm;
    private LinearLayout bloc_cc1;
    private LinearLayout bloc_cc_avance_speciale;
    private LinearLayout bloc_cc2;
    private LinearLayout bloc_cc3;
    private LinearLayout bloc_cc4;
    private LinearLayout bloc_cc5;

    private LinearLayout ll_CcNatureJrTxIntJr;
    private LinearLayout ll_CcNatureJrTxIntAvceSpecJr;
    private LinearLayout ll_CcNatJrTxIntJr_PenRetAvceSpe;
    private LinearLayout ll_CcNatJrTxIntJr_IntRetAvceSpe;
    private LinearLayout ll_CcNatureJrTxPenRet;
    private LinearLayout ll_CcNatJrTxIntJr_IntRetDecouv;
    private LinearLayout ll_decouv_permanent;


    private RadioButton rbCrModeCalcInteretSimple;
    private RadioButton rbCrModeCalcInteretCompose;
    private RadioButton rbCrModeCalcInteretAvceSpecSimple;
    private RadioButton rbCrModeCalcInteretAvceSpecCompose;

    private RadioButton rbCcModeCalcPenRetardAvceSpecCompose;
    private RadioButton rbCcModeCalcIntRetardAvceSpecCompose;

    private TextView tv_plageTxMtAgio, tv_plage_pen_retard_decouv_simple,tv_plage_int_retard_decouv_simple,tv_plageCcTauxIntAvceSpec, tv_plageCcTauxIntPenAvceSpec,
            tv_plage_CcTauxInt_IntRetAvceSpec;
    public static ArrayList<ModelPlageData> plageDataListCTP = new ArrayList<ModelPlageData>(); //to manage plageData
    public static ArrayList<ModelPlageData> plageDataListPRD = new ArrayList<ModelPlageData>(); //to manage plageData
    public static ArrayList<ModelPlageData> plageDataListIRD = new ArrayList<ModelPlageData>(); //to manage plageData
    private TextView tv_plageCalculAgiosCC;
    public static ArrayList<ModelPlageData> plageDataListCAP = new ArrayList<ModelPlageData>(); //to manage plageData
    private TextView tv_plageTxCommMvmCC;

    private String baseCcTxIntDecouv;
    private String baseCcTxIntPenRetard;
    private String baseCcTxCommMvm;
    private String baseTxIntAvceSpec;
    private String baseTxIntPenAvceSpec;

    /*START NEW*/
    private RadioButton rbCcNaturePerTxInteretJour;
    private RadioButton rbCcNaturePerTxInteretMois;
    private RadioButton rbCcNaturePerTxInteretTrimestre;
    private RadioButton rbCcNaturePerTxInteretAnnee;

    private RadioButton rbCcNaturePerTxPenRetJour;
    private RadioButton rbCcNaturePerTxPenRetMois;
    private RadioButton rbCcNaturePerTxPenRetTrimestre;
    private RadioButton rbCcNaturePerTxPenRetAnnee;

    private RadioButton rbCcPeriod_IntRetDecouvJour;
    private RadioButton rbCcPeriod_IntRetDecouvMois;
    private RadioButton rbCcPeriod_IntRetDecouvTrimestre;
    private RadioButton rbCcPeriod_IntRetDecouvAnnee;

    private RadioButton rbCcNaturePerTxInteretAvceSpecJour;
    private RadioButton rbCcNaturePerTxInteretAvceSpecMois;
    private RadioButton rbCcNaturePerTxInteretAvceSpecTrimestre;
    private RadioButton rbCcNaturePerTxInteretAvceSpecAnnee;

    private RadioButton rbCcPeriod_PenRetAvceSpecJour;
    private RadioButton rbCcPeriod_PenRetAvceSpecMois;
    private RadioButton rbCcPeriod_PenRetAvceSpecTrimestre;
    private RadioButton rbCcPeriod_PenRetAvceSpecAnnee;

    private RadioButton rbCcPeriod_IntRetAvceSpecJour;
    private RadioButton rbCcPeriod_IntRetAvceSpecMois;
    private RadioButton rbCcPeriod_IntRetAvceSpecTrimestre;
    private RadioButton rbCcPeriod_IntRetAvceSpecAnnee;


    private RadioButton rbCcNatJrTxIntJr_IntRetDecouvLunVen;
    private RadioButton rbCcNatJrTxIntJr_IntRetDecouvLunSam;
    private RadioButton rbCcNatJrTxIntJr_IntRetDecouvJrOuvertGu;
    private RadioButton rbCcNatJrTxIntJr_IntRetDecouvTous;

    private RadioButton rbCcNatureJrTxIntJrLunVen;
    private RadioButton rbCcNatureJrTxIntJrLunSam;
    private RadioButton rbCcNatureJrTxIntJrJrOuvertGu;
    private RadioButton rbCcNatureJrTxIntJrTous;

    private RadioButton rbCcNatureJrTxIntAvceSpecJrLunVen;
    private RadioButton rbCcNatureJrTxIntAvceSpecJrLunSam;
    private RadioButton rbCcNatureJrTxIntAvceSpecJrJrOuvertGu;
    private RadioButton rbCcNatureJrTxIntAvceSpecJrTous;

    private RadioButton rbCcNatJrTxIntJr_PenRetAvceSpeLunVen;
    private RadioButton rbCcNatJrTxIntJr_PenRetAvceSpeLunSam;
    private RadioButton rbCcNatJrTxIntJr_PenRetAvceSpeJrOuvertGu;
    private RadioButton rbCcNatJrTxIntJr_PenRetAvceSpeTous;

    private RadioButton rbCcNatJrTxIntJr_IntRetAvceSpeLunVen;
    private RadioButton rbCcNatJrTxIntJr_IntRetAvceSpeLunSam;
    private RadioButton rbCcNatJrTxIntJr_IntRetAvceSpeJrOuvertGu;
    private RadioButton rbCcNatJrTxIntJr_IntRetAvceSpeTous;

    private RadioButton rbCcNatureJrTxPenRetLunVen;
    private RadioButton rbCcNatureJrTxPenRetLunSam;
    private RadioButton rbCcNatureJrTxPenRetJrOuvertGu;
    private RadioButton rbCcNatureJrTxPenRetTous;
    private String CcNaturePerTxInteret;
    private String CcIsTxInteretJrOn;
    private String CcNatureJrTxIntJr;
    private String CcNaturePerTxPenRet;
    private String CcIsTxPenRetardJrOn;
    private String CcNatureJrTxPenRet;
    private String CcPeriod_IntRetDecouv;
    private String CcIsTxIntJrOn_IntRetDecouv;
    private String CcNatJrTxIntJr_IntRetDecouv;
    private EditText CcDureeMaxAvceSpec;
    private EditText CcMaxMontantDecouvertPermanentAutorise;
    private EditText CcMaxDureeDecouvertPermanentAutorise;
    private String st_CcDureeMaxAvceSpec;
    private String CcNaturePerTxInteretAvceSpec;
    private String CcIsTxInteretAvceSpecJrOn;
    private String CcNatureJrTxIntAvceSpecJr;
    private String CcIsTxPenRetardAvceSpecDegressif;
    private String CcIsTxIntAvceSpecDegressif;
    private String CcPeriod_PenRetAvceSpec;
    private String CcIsTxIntJrOn_PenRetAvceSpe;
    private String CcNatJrTxIntJr_PenRetAvceSpe;
    private String CcIsTxIntRetardAvceSpecDegressif;
    private String st_CcTauxInt_IntRetAvceSpec;
    private String st_CcBasexInt_IntRetAvceSpec;
    private String CcPeriod_IntRetAvceSpec;
    private String CcIsTxIntJrOn_IntRetAvceSpe;
    private String CcNatJrTxIntJr_IntRetAvceSpe;
    private String CcModeCalcInteret;
    private String CcModeCalcInteretAvceSpec;
    private String CcIsDecouvertPermanentAutorise;
    private String st_CcMaxMontantDecouvertPermanentAutorise;
    private String st_CcMaxDureeDecouvertPermanentAutorise;
    private String CcModeCalcPenRetardAvceSpec;
    private String CcModeCalcIntRetardAvceSpec;
    /*END NEW*/

    public static ArrayList<ModelPlageData> plageDataListTCP = new ArrayList<ModelPlageData>(); //to manage plageData
    public static ArrayList<ModelPlageData> plageDataListTAS = new ArrayList<ModelPlageData>(); //to manage plageData
    public static ArrayList<ModelPlageData> plageDataListPAS = new ArrayList<ModelPlageData>(); //to manage plageData
    public static ArrayList<ModelPlageData> plageDataListIRA = new ArrayList<ModelPlageData>(); //to manage plageData

    private Button addButton;
    private Button cancelButton;
    private Button deleteButton;
    private int success;
    private ProgressDialog pDialog;


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
    //#TAS
    private static final String KEY_CC_PLAGE_TAUX_INT_AVANC_SPEC_DEBUT = "CcTasDebut";
    private static final String KEY_CC_PLAGE_TAUX_INT_AVANC_SPEC_FIN = "CcTasFin";
    private static final String KEY_CC_PLAGE_TAUX_INT_AVANC_SPEC_VALEUR = "CcTasValeur";
    private static final String KEY_CC_PLAGE_TAUX_INT_AVANC_SPEC_BASE = "CcTasBase";
    private static final String KEY_CC_PLAGE_TAUX_INT_AVANC_SPEC_NATURE = "CcTasNature";

    private String tabPlageDebutTAS ="";
    private String tabPlageFinTAS ="";
    private String tabPlageValeurTAS ="";
    private String tabPlageBaseTAS ="";
    private String tabPlageNatureTAS ="";
    //#PAS
    private static final String KEY_CC_PLAGE_TAUX_INT_PENAL_AVANC_SPEC_DEBUT = "CcPasDebut";
    private static final String KEY_CC_PLAGE_TAUX_INT_PENAL_AVANC_SPEC_FIN = "CcPasFin";
    private static final String KEY_CC_PLAGE_TAUX_INT_PENAL_AVANC_SPEC_VALEUR = "CcPasValeur";
    private static final String KEY_CC_PLAGE_TAUX_INT_PENAL_AVANC_SPEC_BASE = "CcPasBase";
    private static final String KEY_CC_PLAGE_TAUX_INT_PENAL_AVANC_SPEC_NATURE = "CcPasNature";

    private String tabPlageDebutPAS ="";
    private String tabPlageFinPAS ="";
    private String tabPlageValeurPAS ="";
    private String tabPlageBasePAS ="";
    private String tabPlageNaturePAS ="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cpte_courant);


//        plageDataListCTP = new ArrayList<ModelPlageData>();
        bloc_cc1 = (LinearLayout) findViewById(R.id.ll_bloc_cc1);
        bloc_cc_avance_speciale = (LinearLayout) findViewById(R.id.ll_bloc_ccAvancement);
        bloc_cc2 = (LinearLayout) findViewById(R.id.ll_bloc_cc2);
        bloc_cc3 = (LinearLayout) findViewById(R.id.ll_bloc_cc3);
        bloc_cc4 = (LinearLayout) findViewById(R.id.ll_bloc_cc4);
        bloc_cc5 = (LinearLayout) findViewById(R.id.ll_bloc_cc5);
//        blk_plage_cc = (LinearLayout) findViewById(R.id.blk_plage_cc);
//        blk_plage_agios_cc = (LinearLayout) findViewById(R.id.blk_plage_agios_cc);
//        blk_plage_CcValTxCommMvm = (LinearLayout) findViewById(R.id.blk_plage_CcValTxCommMvm);
        input_layout_CcValTxMtAgio = (TextInputLayout) findViewById(R.id.input_layout_CcValTxMtAgio);
        input_layout_CcTauxIntPenRetard = (TextInputLayout) findViewById(R.id.input_layout_CcTauxIntPenRetard);
        input_layout_CcTauxInt_IntRetDecouv = (TextInputLayout) findViewById(R.id.input_layout_CcTauxInt_IntRetDecouv);
        input_layout_CcTauxIntAvceSpec = (TextInputLayout) findViewById(R.id.input_layout_CcTauxIntAvceSpec);
        input_layout_CcTauxIntPenAvceSpec = (TextInputLayout) findViewById(R.id.input_layout_CcTauxIntPenAvceSpec);
        input_layout_CcTauxInt_IntRetAvceSpec = (TextInputLayout) findViewById(R.id.input_layout_CcTauxInt_IntRetAvceSpec);
        input_layout_CcBaseTxMtAgio = (TextInputLayout) findViewById(R.id.input_layout_BaseCcValTxMtAgio);
        input_layout_CcBaseTxIntPenRetard = (TextInputLayout) findViewById(R.id.input_layout_CcBaseTxIntPenRetard);
        input_layout_CcBasexInt_IntRetDecouv = (TextInputLayout) findViewById(R.id.input_layout_CcBasexInt_IntRetDecouv);
        input_layout_CcBaseTxIntAvceSpec = (TextInputLayout) findViewById(R.id.input_layout_CcBaseTxIntAvceSpec);
        input_layout_CcBaseTxIntPenAvceSpec = (TextInputLayout) findViewById(R.id.input_layout_CcBaseTxIntPenAvceSpec);
        input_layout_CcBasexInt_IntRetAvceSpec = (TextInputLayout) findViewById(R.id.input_layout_CcBasexInt_IntRetAvceSpec);
        input_layout_CcBaseCalculAgiosCC = (TextInputLayout) findViewById(R.id.input_layout_txtNatureBaseCalculAgiosCC_new);
        input_layout_CcBaseTxCommMvmCC = (TextInputLayout) findViewById(R.id.input_layout_BaseCcTxCommMvm);
        input_layout_CcValTxIntDecouv = (TextInputLayout) findViewById(R.id.input_layout_CcValTxIntDecouv);
        input_layout_CcValTxCommMvm = (TextInputLayout) findViewById(R.id.input_layout_CcValTxCommMvm);

        CcCode = (EditText) findViewById(R.id.input_txtCodeCC);
        CcLibelle = (EditText) findViewById(R.id.input_txt_LibelleCC);
        CcIsDecouvOn= (Switch) findViewById(R.id.SwitchAutoriserDecouvertCC);
        CcIsAvanceSpecialOn= (Switch) findViewById(R.id.SwitchCcIsAvanceSpecialOn);
        CcMtMaxDecouv= (EditText) findViewById(R.id.input_txt_MaxMtDecouvertAutoriseCC);
        CcMtMaxDecouv.addTextChangedListener(MyData.onTextChangedListener(CcMtMaxDecouv));
        CcMtPlafondAvceSpec= (EditText) findViewById(R.id.input_txt_CcMtPlafondAvceSpec);
        CcIsMaxDecouvNeg= (Switch) findViewById(R.id.SwitchPlafondDecouvertNegociableCC);

        //CcNatureTxIntDecouv= (EditText) findViewById(R.id.SwitchPlafondDecouvertNegociableCC);
        CcNatureTxIntDecouvFixe = (RadioButton) findViewById(R.id.rb_CcNatureTxIntDecouv_Fixe);
        CcNatureTxIntDecouvTaux = (RadioButton) findViewById(R.id.rb_CcNatureTxIntDecouv_Taux);
        CcNatureTxIntDecouvPlage = (RadioButton) findViewById(R.id.rb_CcNatureTxIntDecouv_Plage);

        CcNatTauxIntPenRetardFixe = (RadioButton) findViewById(R.id.rb_CcNatTauxIntPenRetard_Fixe);
        CcNatTauxIntPenRetardTaux = (RadioButton) findViewById(R.id.rb_CcNatTauxIntPenRetard_Taux);
        CcNatTauxIntPenRetardPlage = (RadioButton) findViewById(R.id.rb_CcNatTauxIntPenRetard_Plage);

        CcNatureTxInt_IntRetDecouvFixe = (RadioButton) findViewById(R.id.rb_CcNatureTxInt_IntRetDecouv_Fixe);
        CcNatureTxInt_IntRetDecouvTaux = (RadioButton) findViewById(R.id.rb_CcNatureTxInt_IntRetDecouv_Taux);
        CcNatureTxInt_IntRetDecouvPlage = (RadioButton) findViewById(R.id.rb_CcNatureTxInt_IntRetDecouv_Plage);

        CcNatTauxIntAvceSpecFixe = (RadioButton) findViewById(R.id.rb_CcNatTauxIntAvceSpec_Fixe);
        CcNatTauxIntAvceSpecTaux = (RadioButton) findViewById(R.id.rb_CcNatTauxIntAvceSpec_Taux);
        CcNatTauxIntAvceSpecPlage = (RadioButton) findViewById(R.id.rb_CcNatTauxIntAvceSpec_Plage);

        CcNatTauxIntPenAvceSpecFixe = (RadioButton) findViewById(R.id.rb_CcNatTauxIntPenAvceSpec_Fixe);
        CcNatTauxIntPenAvceSpecTaux = (RadioButton) findViewById(R.id.rb_CcNatTauxIntPenAvceSpec_Taux);
        CcNatTauxIntPenAvceSpecPlage = (RadioButton) findViewById(R.id.rb_CcNatTauxIntPenAvceSpec_Plage);

        CcNatureTxInt_IntRetAvceSpecFixe = (RadioButton) findViewById(R.id.rb_CcNatureTxInt_IntRetAvceSpec_Fixe);
        CcNatureTxInt_IntRetAvceSpecTaux = (RadioButton) findViewById(R.id.rb_CcNatureTxInt_IntRetAvceSpec_Taux);
        CcNatureTxInt_IntRetAvceSpecPlage = (RadioButton) findViewById(R.id.rb_CcNatureTxInt_IntRetAvceSpec_Plage);

        CcValTxIntDecouv= (EditText) findViewById(R.id.input_txt_CcValTxMtAgio);
        CcValTxIntDecouv.addTextChangedListener(MyData.onTextChangedListener(CcValTxIntDecouv));
        CcTauxIntPenRetard= (EditText) findViewById(R.id.input_txt_CcTauxIntPenRetard);
        CcTauxIntPenRetard.addTextChangedListener(MyData.onTextChangedListener(CcTauxIntPenRetard));
        CcTauxInt_IntRetDecouv= (EditText) findViewById(R.id.input_txt_CcTauxInt_IntRetDecouv);
        CcTauxInt_IntRetDecouv.addTextChangedListener(MyData.onTextChangedListener(CcTauxInt_IntRetDecouv));
        CcTauxIntAvceSpec= (EditText) findViewById(R.id.input_txt_CcTauxIntAvceSpec);
        CcTauxIntAvceSpec.addTextChangedListener(MyData.onTextChangedListener(CcTauxIntAvceSpec));
        CcTauxIntPenAvceSpec= (EditText) findViewById(R.id.input_txt_CcTauxIntPenAvceSpec);
        CcTauxIntPenAvceSpec.addTextChangedListener(MyData.onTextChangedListener(CcTauxIntPenAvceSpec));
        CcTauxInt_IntRetAvceSpec= (EditText) findViewById(R.id.input_txt_CcTauxInt_IntRetAvceSpec);
        CcTauxInt_IntRetAvceSpec.addTextChangedListener(MyData.onTextChangedListener(CcTauxInt_IntRetAvceSpec));
        mySpinnerBaseTxIntDecouv = (JRSpinner)findViewById(R.id.spn_my_spinner_base_CcValTxMtAgio);
        mySpinnerCcBaseTxIntPenRetard = (JRSpinner)findViewById(R.id.spn_my_spinner_base_CcBaseTxIntPenRetard);
        mySpinnerCcBasexInt_IntRetDecouv = (JRSpinner)findViewById(R.id.spn_my_spinner_base_CcBasexInt_IntRetDecouv);
        mySpinnerBaseTxMtAgio = (JRSpinner)findViewById(R.id.spn_my_spinner_BaseCalculAgiosCC);
        mySpinnerBaseTxCommMvm = (JRSpinner)findViewById(R.id.spn_my_spinner_base_CcTxCommMvm);
        mySpinnerCcBaseTxIntAvceSpec = (JRSpinner)findViewById(R.id.spn_my_spinner_base_CcBaseTxIntAvceSpec);
        mySpinnerCcBaseTxIntPenAvceSpec = (JRSpinner)findViewById(R.id.spn_my_spinner_base_CcBaseTxIntPenAvceSpec);
        mySpinnerCcBasexInt_IntRetAvceSpec = (JRSpinner)findViewById(R.id.spn_my_spinner_base_CcBasexInt_IntRetAvceSpec);
        mySpinnerBaseTxIntDecouv.setItems(getResources().getStringArray(R.array.array_base_taux_cpte_courant)); //this is important, you must set it to set the item list
        mySpinnerBaseTxIntDecouv.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        mySpinnerBaseTxIntDecouv.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        mySpinnerBaseTxIntDecouv.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });

        mySpinnerCcBaseTxIntPenRetard.setItems(getResources().getStringArray(R.array.array_base_taux_penal_retard_decouv_simple_cpte_courant)); //this is important, you must set it to set the item list
        mySpinnerCcBaseTxIntPenRetard.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        mySpinnerCcBaseTxIntPenRetard.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        mySpinnerCcBaseTxIntPenRetard.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });

        mySpinnerCcBasexInt_IntRetAvceSpec.setItems(getResources().getStringArray(R.array.array_base_taux_penal_retard_decouv_simple_cpte_courant)); //this is important, you must set it to set the item list
        mySpinnerCcBasexInt_IntRetAvceSpec.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        mySpinnerCcBasexInt_IntRetAvceSpec.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        mySpinnerCcBasexInt_IntRetAvceSpec.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });
        mySpinnerCcBasexInt_IntRetDecouv.setItems(getResources().getStringArray(R.array.array_base_taux_penal_retard_decouv_simple_cpte_courant)); //this is important, you must set it to set the item list
        mySpinnerCcBasexInt_IntRetDecouv.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        mySpinnerCcBasexInt_IntRetDecouv.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        mySpinnerCcBasexInt_IntRetDecouv.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });
        /*Base tx int avance speciale debut*/
        mySpinnerCcBaseTxIntAvceSpec.setItems(getResources().getStringArray(R.array.array_base_taux_int_avce_spec)); //this is important, you must set it to set the item list
        mySpinnerCcBaseTxIntAvceSpec.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        mySpinnerCcBaseTxIntAvceSpec.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        mySpinnerCcBaseTxIntAvceSpec.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });
        /*Base tx int avance speciale fin*/
        /*Base tx int pénalité avance speciale debut*/
        mySpinnerCcBaseTxIntPenAvceSpec.setItems(getResources().getStringArray(R.array.array_base_taux_int_pen_avce_spec)); //this is important, you must set it to set the item list
        mySpinnerCcBaseTxIntPenAvceSpec.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        mySpinnerCcBaseTxIntPenAvceSpec.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        mySpinnerCcBaseTxIntPenAvceSpec.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });
        /*Base tx int pénalité avance speciale fin*/
        mySpinnerBaseTxMtAgio.setItems(getResources().getStringArray(R.array.array_base_taux_cpte_courant)); //this is important, you must set it to set the item list
        mySpinnerBaseTxMtAgio.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        mySpinnerBaseTxMtAgio.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        mySpinnerBaseTxMtAgio.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });
        mySpinnerBaseTxCommMvm.setItems(getResources().getStringArray(R.array.array_base_taux_cpte_courant)); //this is important, you must set it to set the item list
        mySpinnerBaseTxCommMvm.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        mySpinnerBaseTxCommMvm.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        mySpinnerBaseTxCommMvm.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });
        tv_plageTxMtAgio= (TextView) findViewById(R.id.tv_plage_CcTxMtAgio);
        tv_plage_pen_retard_decouv_simple= (TextView) findViewById(R.id.tv_plage_CcTauxIntPenRetard);
        tv_plage_int_retard_decouv_simple= (TextView) findViewById(R.id.tv_plage_CcTauxInt_IntRetDecouv);
        tv_plageCcTauxIntAvceSpec= (TextView) findViewById(R.id.tv_plage_CcTauxIntAvceSpec);
        tv_plageCcTauxIntPenAvceSpec= (TextView) findViewById(R.id.tv_plage_CcTauxIntPenAvceSpec);
        tv_plage_CcTauxInt_IntRetAvceSpec= (TextView) findViewById(R.id.tv_plage_CcTauxInt_IntRetAvceSpec);
        tv_plageCalculAgiosCC= (TextView) findViewById(R.id.tv_plage_CalculAgiosCC);
        tv_plageTxCommMvmCC= (TextView) findViewById(R.id.tv_plage_CcTxCommMvm);
//                CcPlageTxIntDecouvFrom= (EditText) findViewById(R.id.txt_CcValTxIntDecouvFrom);
//                CcPlageTxIntDecouvTo= (EditText) findViewById(R.id.txt_CcValTxIntDecouvTo);
                CcDureeMaxDecouv = (EditText) findViewById(R.id.input_txt_CcDureeMaxDecouv);
                //CcNatureTypDureDecouv= (EditText) findViewById(R.id.input_txt_CcDureeMaxDecouv);
                CcNatureTypDureDecouvJour= (RadioButton) findViewById(R.id.rb_CcNatureTypDureDecouv_jour);
                CcNatureTypDureDecouvSemaine= (RadioButton) findViewById(R.id.rb_CcNatureTypDureDecouv_semaine);
                CcNatureTypDureDecouvMois= (RadioButton) findViewById(R.id.rb_CcNatureTypDureDecouv_mois);

                //CcNatureTxMtAgio,
        CcNatureTxMtAgioTaux= (RadioButton) findViewById(R.id.rb_CcNatureTxMtAgio_taux);
        CcNatureTxMtAgioMontant= (RadioButton) findViewById(R.id.rb_CcNatureTxMtAgio_montant);
        CcNatureTxMtAgioPlage= (RadioButton) findViewById(R.id.rb_CcNatureTxMtAgio_plage);

                CcValTxMtAgio=(EditText) findViewById(R.id.input_txt_CcValTxIntDecouv);
//                CcPlageTxMtAgioFrom=(EditText) findViewById(R.id.txt_CcValTxMtAgioFrom);
//                CcPlageTxMtAgioTo=(EditText) findViewById(R.id.txt_CcValTxMtAgioTo);
//                CcNatBaseAgio=(EditText) findViewById(R.id.input_txt_NatureBaseCalculAgiosCC);
                CcIsChequierM1On=(Switch) findViewById(R.id.Switch_txtDisponibiliteChequier1CC);
                CcIsPlafAvceSpecNegoc=(Switch) findViewById(R.id.SwitchCcIsPlafAvceSpecNegoc);
        CcIsTxIntDegressif=(Switch) findViewById(R.id.SwitchCcIsTxIntDegressif);
                CcNbPagesCheqM1=(EditText) findViewById(R.id.input_txt_NbrePageChequier1CC);
                CcPrixVteCheqM1=(EditText) findViewById(R.id.input_txt_txtPrixVenteChequier1CC);
                CcIsChequierM2On=(Switch) findViewById(R.id.Switch_txtDisponibiliteChequier2CC);
                CcNbPagesCheqM2=(EditText) findViewById(R.id.input_txt_NbrePageChequier2CC);
                CcPrixVteCheqM2=(EditText) findViewById(R.id.input_txt_txtPrixVenteChequier2CC);
                CcIsChequierM3On=(Switch) findViewById(R.id.Switch_txtDisponibiliteChequier3CC);
                CcNbPagesCheqM3=(EditText) findViewById(R.id.input_txt_NbrePageChequier3CC);
                CcPrixVteCheqM3=(EditText) findViewById(R.id.input_txt_txtPrixVenteChequier3CC);
                CcDureeValidCheq=(EditText) findViewById(R.id.input_txt_DureeValiditeChequeEmisCC);
                CcNbMinSignatChq=(EditText) findViewById(R.id.input_txt_NbreMinPersSignataireChequeCC);

                CcIsTxCommMvmOper=(Switch) findViewById(R.id.SwitchActiverTxCommissionCC);
                //CcNatTxComm,
        CcNatTxCommFixe= (RadioButton) findViewById(R.id.rb_CcNatTxComm_fixe);
        CcNatTxCommMontant= (RadioButton) findViewById(R.id.rb_CcNatTxComm_Montant);
        CcNatTxCommPlage= (RadioButton) findViewById(R.id.rb_CcNatTxComm_Plage);
                CcValTxCommMvm=(EditText) findViewById(R.id.input_txt_CcValTxCommMvm);
//                CcPlageTxCommMvmFrom=(EditText) findViewById(R.id.txt_CcValTxCommMvm_From);
//                CcPlageTxCommMvmTo=(EditText) findViewById(R.id.txt_CcValTxCommMvm_To);
/*NEW START*/
        SwitchDecouvertPermanentAutoriseCC=(Switch) findViewById(R.id.SwitchDecouvertPermanentAutoriseCC);
        SwitchCcIsTxIntAvceSpecDegressif=(Switch) findViewById(R.id.SwitchCcIsTxIntAvceSpecDegressif);
        SwitchCcIsTxPenRetardAvceSpecDegressif=(Switch) findViewById(R.id.SwitchCcIsTxPenRetardAvceSpecDegressif);
        SwitchCcIsTxIntRetardAvceSpecDegressif=(Switch) findViewById(R.id.SwitchCcIsTxIntRetardAvceSpecDegressif);
        SwitchCcIsTxInteretJrOn=(SwitchCompat) findViewById(R.id.SwitchCcIsTxInteretJrOn);
        SwitchCcIsTxInteretAvceSpecJrOn=(SwitchCompat) findViewById(R.id.SwitchCcIsTxInteretAvceSpecJrOn);
        SwitchCcIsTxIntJrOn_PenRetAvceSpe=(SwitchCompat) findViewById(R.id.SwitchCcIsTxIntJrOn_PenRetAvceSpe);
        SwitchCcIsTxIntJrOn_IntRetAvceSpe=(SwitchCompat) findViewById(R.id.SwitchCcIsTxIntJrOn_IntRetAvceSpe);
        SwitchCcIsTxPenRetardJrOn=(SwitchCompat) findViewById(R.id.SwitchCcIsTxPenRetardJrOn);
        SwitchCcIsTxIntJrOn_IntRetDecouv=(SwitchCompat) findViewById(R.id.SwitchCcIsTxIntJrOn_IntRetDecouv);
        rbCrModeCalcInteretSimple = (RadioButton)findViewById(R.id.rbCrModeCalcInteretSimple);
        rbCrModeCalcInteretAvceSpecSimple = (RadioButton)findViewById(R.id.rbCrModeCalcInteretAvceSpecSimple);
        rbCrModeCalcInteretAvceSpecCompose = (RadioButton)findViewById(R.id.rbCrModeCalcInteretAvceSpecCompose);
        rbCrModeCalcInteretCompose = (RadioButton)findViewById(R.id.rbCrModeCalcInteretCompose);

        rbCcModeCalcPenRetardAvceSpecCompose = (RadioButton)findViewById(R.id.rbCcModeCalcPenRetardAvceSpecCompose);

        rbCcModeCalcIntRetardAvceSpecCompose = (RadioButton)findViewById(R.id.rbCcModeCalcIntRetardAvceSpecCompose);

        rbCcNaturePerTxInteretJour = (RadioButton)findViewById(R.id.rbCcNaturePerTxInteretJour);
        rbCcNaturePerTxInteretMois = (RadioButton)findViewById(R.id.rbCcNaturePerTxInteretMois);
        rbCcNaturePerTxInteretTrimestre = (RadioButton)findViewById(R.id.rbCcNaturePerTxInteretTrimestre);
        rbCcNaturePerTxInteretAnnee = (RadioButton)findViewById(R.id.rbCcNaturePerTxInteretAnnee);

        rbCcNaturePerTxPenRetJour = (RadioButton)findViewById(R.id.rbCcNaturePerTxPenRetJour);
        rbCcNaturePerTxPenRetMois = (RadioButton)findViewById(R.id.rbCcNaturePerTxPenRetMois);
        rbCcNaturePerTxPenRetTrimestre = (RadioButton)findViewById(R.id.rbCcNaturePerTxPenRetTrimestre);
        rbCcNaturePerTxPenRetAnnee = (RadioButton)findViewById(R.id.rbCcNaturePerTxPenRetAnnee);


        rbCcPeriod_IntRetDecouvJour = (RadioButton)findViewById(R.id.rbCcPeriod_IntRetDecouvJour);
        rbCcPeriod_IntRetDecouvMois = (RadioButton)findViewById(R.id.rbCcPeriod_IntRetDecouvMois);
        rbCcPeriod_IntRetDecouvTrimestre = (RadioButton)findViewById(R.id.rbCcPeriod_IntRetDecouvTrimestre);
        rbCcPeriod_IntRetDecouvAnnee = (RadioButton)findViewById(R.id.rbCcPeriod_IntRetDecouvAnnee);

        rbCcNaturePerTxInteretAvceSpecJour = (RadioButton)findViewById(R.id.rbCcNaturePerTxInteretAvceSpecJour);
        rbCcNaturePerTxInteretAvceSpecMois = (RadioButton)findViewById(R.id.rbCcNaturePerTxInteretAvceSpecMois);
        rbCcNaturePerTxInteretAvceSpecTrimestre = (RadioButton)findViewById(R.id.rbCcNaturePerTxInteretAvceSpecTrimestre);
        rbCcNaturePerTxInteretAvceSpecAnnee = (RadioButton)findViewById(R.id.rbCcNaturePerTxInteretAvceSpecAnnee);

        rbCcPeriod_PenRetAvceSpecJour = (RadioButton)findViewById(R.id.rbCcPeriod_PenRetAvceSpecJour);
        rbCcPeriod_PenRetAvceSpecMois = (RadioButton)findViewById(R.id.rbCcPeriod_PenRetAvceSpecMois);
        rbCcPeriod_PenRetAvceSpecTrimestre = (RadioButton)findViewById(R.id.rbCcPeriod_PenRetAvceSpecTrimestre);
        rbCcPeriod_PenRetAvceSpecAnnee = (RadioButton)findViewById(R.id.rbCcPeriod_PenRetAvceSpecAnnee);

        rbCcPeriod_IntRetAvceSpecJour = (RadioButton)findViewById(R.id.rbCcPeriod_IntRetAvceSpecJour);
        rbCcPeriod_IntRetAvceSpecMois = (RadioButton)findViewById(R.id.rbCcPeriod_IntRetAvceSpecMois);
        rbCcPeriod_IntRetAvceSpecTrimestre = (RadioButton)findViewById(R.id.rbCcPeriod_IntRetAvceSpecTrimestre);
        rbCcPeriod_IntRetAvceSpecAnnee = (RadioButton)findViewById(R.id.rbCcPeriod_IntRetAvceSpecAnnee);

        rbCcNatureJrTxIntJrLunVen = (RadioButton)findViewById(R.id.rbCcNatureJrTxIntJrLunVen);
        rbCcNatureJrTxIntJrLunSam = (RadioButton)findViewById(R.id.rbCcNatureJrTxIntJrLunSam);
        rbCcNatureJrTxIntJrJrOuvertGu = (RadioButton)findViewById(R.id.rbCcNatureJrTxIntJrJrOuvertGu);
        rbCcNatureJrTxIntJrTous = (RadioButton)findViewById(R.id.rbCcNatureJrTxIntJrTous);

        rbCcNatureJrTxPenRetLunVen = (RadioButton)findViewById(R.id.rbCcNatureJrTxPenRetLunVen);
        rbCcNatJrTxIntJr_IntRetDecouvLunSam = (RadioButton)findViewById(R.id.rbCcNatJrTxIntJr_IntRetDecouvLunSam);
        rbCcNatJrTxIntJr_IntRetDecouvJrOuvertGu = (RadioButton)findViewById(R.id.rbCcNatJrTxIntJr_IntRetDecouvJrOuvertGu);
        rbCcNatJrTxIntJr_IntRetDecouvTous = (RadioButton)findViewById(R.id.rbCcNatJrTxIntJr_IntRetDecouvTous);

        rbCcNatJrTxIntJr_IntRetDecouvLunVen = (RadioButton)findViewById(R.id.rbCcNatJrTxIntJr_IntRetDecouvLunVen);
        rbCcNatureJrTxPenRetLunSam = (RadioButton)findViewById(R.id.rbCcNatureJrTxPenRetLunSam);
        rbCcNatureJrTxPenRetJrOuvertGu = (RadioButton)findViewById(R.id.rbCcNatureJrTxPenRetJrOuvertGu);
        rbCcNatureJrTxPenRetTous = (RadioButton)findViewById(R.id.rbCcNatureJrTxPenRetTous);

        rbCcNatureJrTxIntAvceSpecJrLunVen = (RadioButton)findViewById(R.id.rbCcNatureJrTxIntAvceSpecJrLunVen);
        rbCcNatureJrTxIntAvceSpecJrLunSam = (RadioButton)findViewById(R.id.rbCcNatureJrTxIntAvceSpecJrLunSam);
        rbCcNatureJrTxIntAvceSpecJrJrOuvertGu = (RadioButton)findViewById(R.id.rbCcNatureJrTxIntAvceSpecJrJrOuvertGu);
        rbCcNatureJrTxIntAvceSpecJrTous = (RadioButton)findViewById(R.id.rbCcNatureJrTxIntAvceSpecJrTous);

        rbCcNatJrTxIntJr_PenRetAvceSpeLunVen = (RadioButton)findViewById(R.id.rbCcNatJrTxIntJr_PenRetAvceSpeLunVen);
        rbCcNatJrTxIntJr_PenRetAvceSpeLunSam = (RadioButton)findViewById(R.id.rbCcNatJrTxIntJr_PenRetAvceSpeLunSam);
        rbCcNatJrTxIntJr_PenRetAvceSpeJrOuvertGu = (RadioButton)findViewById(R.id.rbCcNatJrTxIntJr_PenRetAvceSpeJrOuvertGu);
        rbCcNatJrTxIntJr_PenRetAvceSpeTous = (RadioButton)findViewById(R.id.rbCcNatJrTxIntJr_PenRetAvceSpeTous);

        rbCcNatJrTxIntJr_IntRetAvceSpeLunVen = (RadioButton)findViewById(R.id.rbCcNatJrTxIntJr_IntRetAvceSpeLunVen);
        rbCcNatJrTxIntJr_IntRetAvceSpeLunSam = (RadioButton)findViewById(R.id.rbCcNatJrTxIntJr_IntRetAvceSpeLunSam);
        rbCcNatJrTxIntJr_IntRetAvceSpeJrOuvertGu = (RadioButton)findViewById(R.id.rbCcNatJrTxIntJr_IntRetAvceSpeJrOuvertGu);
        rbCcNatJrTxIntJr_IntRetAvceSpeTous = (RadioButton)findViewById(R.id.rbCcNatJrTxIntJr_IntRetAvceSpeTous);

        ll_CcNatureJrTxIntJr = (LinearLayout) findViewById(R.id.ll_CcNatureJrTxIntJr);
        ll_CcNatureJrTxIntAvceSpecJr = (LinearLayout) findViewById(R.id.ll_CcNatureJrTxIntAvceSpecJr);
        ll_CcNatJrTxIntJr_PenRetAvceSpe = (LinearLayout) findViewById(R.id.ll_CcNatJrTxIntJr_PenRetAvceSpe);
        ll_CcNatJrTxIntJr_IntRetAvceSpe = (LinearLayout) findViewById(R.id.ll_CcNatJrTxIntJr_IntRetAvceSpe);
       ll_CcNatureJrTxPenRet = (LinearLayout) findViewById(R.id.ll_CcNatureJrTxPenRet);
        ll_CcNatJrTxIntJr_IntRetDecouv = (LinearLayout) findViewById(R.id.ll_CcNatJrTxIntJr_IntRetDecouv);
        ll_decouv_permanent = (LinearLayout) findViewById(R.id.ll_decouv_permanent);
        CcDureeMaxAvceSpec= (EditText) findViewById(R.id.input_txt_CcDureeMaxAvceSpec);
        CcMaxMontantDecouvertPermanentAutorise= (EditText) findViewById(R.id.input_txt_MaxMontantDecouvertPermanentAutoriseCC);
        CcMaxDureeDecouvertPermanentAutorise= (EditText) findViewById(R.id.input_txt_MaxDureeDecouvertPermanentAutoriseCC);
        /*END START*/


        deleteButton = (Button) findViewById(R.id.btn_delete_cc);
        deleteButton.setVisibility(View.GONE);
        addButton = (Button) findViewById(R.id.btn_save_cc);
        cancelButton = (Button) findViewById(R.id.btn_clean);

        tv_plageTxMtAgio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux d'intérêt découvert";
                    ListPlageCTP.IS_TO_CREATE_OR_TO_UPDATE = true;
//                    Intent i = new Intent(CreateProduitCpteCourant.this, ListPlageDataCTPActivity.class);
                    Intent i = new Intent(CreateProduitCpteCourant.this, ListPlageCTP.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(CreateProduitCpteCourant.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

        //CAP
        tv_plageCalculAgiosCC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux des agios";
                    ListPlageCAP.IS_TO_CREATE_OR_TO_UPDATE = true;
                    Intent i = new Intent(CreateProduitCpteCourant.this,ListPlageCAP.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(CreateProduitCpteCourant.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        //TCP
        tv_plageTxCommMvmCC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux de commission";
                    ListPlageTCP.IS_TO_CREATE_OR_TO_UPDATE = true;
                    Intent i = new Intent(CreateProduitCpteCourant.this,ListPlageTCP.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(CreateProduitCpteCourant.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        //TAS
        tv_plageCcTauxIntAvceSpec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Tx int avance spéciale";
                    ListPlageTAS.IS_TO_CREATE_OR_TO_UPDATE = true;
                    Intent i = new Intent(CreateProduitCpteCourant.this,ListPlageTAS.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(CreateProduitCpteCourant.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        //PAS
        tv_plageCcTauxIntPenAvceSpec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Tx int pénalité A.S";
                    ListPlagePAS.IS_TO_CREATE_OR_TO_UPDATE = true;
                    Intent i = new Intent(CreateProduitCpteCourant.this,ListPlagePAS.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(CreateProduitCpteCourant.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        //IRA
        tv_plage_CcTauxInt_IntRetAvceSpec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Tx int retard A.S";
                    ListPlageIRA.IS_TO_CREATE_OR_TO_UPDATE = true;
                    Intent i = new Intent(CreateProduitCpteCourant.this,ListPlageIRA.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(CreateProduitCpteCourant.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

        // PRD
        tv_plage_pen_retard_decouv_simple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux pénalité de retard";
                    ListPlagePRD.IS_TO_CREATE_OR_TO_UPDATE = true;
                    Intent i = new Intent(CreateProduitCpteCourant.this, ListPlagePRD.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(CreateProduitCpteCourant.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        //IRD
        tv_plage_int_retard_decouv_simple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux intérêt de retard";
                    ListPlageIRD.IS_TO_CREATE_OR_TO_UPDATE = true;
                    Intent i = new Intent(CreateProduitCpteCourant.this, ListPlageIRD.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(CreateProduitCpteCourant.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

        onRadioButtonClicked(CcNatureTxIntDecouvFixe);
        onRadioButtonClicked(CcNatTauxIntPenRetardFixe);
        onRadioButtonClicked(CcNatureTxInt_IntRetDecouvFixe);
        onRadioButtonClicked(CcNatureTypDureDecouvJour);
        onRadioButtonClicked(CcNatureTxMtAgioTaux);
        onRadioButtonClicked(CcNatTxCommFixe);
        onRadioButtonClicked(CcNatTauxIntAvceSpecFixe);
        onRadioButtonClicked(CcNatTauxIntPenAvceSpecFixe);
        onRadioButtonClicked(CcNatureTxInt_IntRetAvceSpecFixe);
        onRadioButtonClicked(rbCcModeCalcPenRetardAvceSpecCompose);
        onRadioButtonClicked(rbCcModeCalcIntRetardAvceSpecCompose);
        onRadioButtonClicked(rbCrModeCalcInteretSimple);
        onRadioButtonClicked(rbCcNaturePerTxInteretMois);
        onRadioButtonClicked(rbCcNatureJrTxIntJrTous);
        onRadioButtonClicked(rbCcNaturePerTxPenRetMois);
        onRadioButtonClicked(rbCcNatureJrTxPenRetTous);
        onRadioButtonClicked(rbCcPeriod_IntRetDecouvMois);
        onRadioButtonClicked(rbCcNatJrTxIntJr_IntRetDecouvTous);
        onRadioButtonClicked(CcNatTxCommFixe);
        onRadioButtonClicked(rbCrModeCalcInteretAvceSpecSimple);
        onRadioButtonClicked(rbCcNaturePerTxInteretAvceSpecMois);
        onRadioButtonClicked(rbCcNatureJrTxIntAvceSpecJrTous);
        onRadioButtonClicked(rbCcPeriod_PenRetAvceSpecMois);
        onRadioButtonClicked(rbCcNatJrTxIntJr_PenRetAvceSpeTous);
        onRadioButtonClicked(rbCcPeriod_IntRetAvceSpecMois);
        onRadioButtonClicked(rbCcNatJrTxIntJr_IntRetAvceSpeTous);

        onSwitchButtonClicked(CcIsDecouvOn);
        onSwitchButtonClicked(SwitchDecouvertPermanentAutoriseCC);

        onSwitchButtonClicked(CcIsChequierM1On);
        onSwitchButtonClicked(CcIsChequierM2On);
        onSwitchButtonClicked(CcIsChequierM3On);
        onSwitchButtonClicked(CcIsTxCommMvmOper);
        onSwitchButtonClicked(CcIsAvanceSpecialOn);
        //cirLoginButton
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(CreateProduitCpteCourant.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addCompteCourant();
                } else {
                    Toast.makeText(CreateProduitCpteCourant.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });


    }


    public void onSwitchButtonClicked(View view) {
        // boolean checked1 = ((Switch) view).isChecked();
        String str = "";
        // Check which checkbox was clicked
        switch (view.getId()) {

            case R.id.SwitchDecouvertPermanentAutoriseCC:
                if (SwitchDecouvertPermanentAutoriseCC.isChecked()) {
//                    str = checked1?"Minimum en compte obligatoire":"le minimum en compte n'est pas obligatoire";
                    ll_decouv_permanent.setVisibility(View.VISIBLE);

                }else{
                    ll_decouv_permanent.setVisibility(View.GONE);
                }

                break;
            case R.id.SwitchCcIsTxIntAvceSpecDegressif:
                if (SwitchCcIsTxIntAvceSpecDegressif.isChecked()) {
//                    str = checked1?"Minimum en compte obligatoire":"le minimum en compte n'est pas obligatoire";
                    rbCrModeCalcInteretAvceSpecCompose.setChecked(true);
                    onRadioButtonClicked(rbCrModeCalcInteretAvceSpecCompose);
                }else{
                    rbCrModeCalcInteretAvceSpecSimple.setChecked(true);
                    onRadioButtonClicked(rbCrModeCalcInteretAvceSpecSimple);
                }

                break;
            case R.id.SwitchCcIsTxIntDegressif:
                if (CcIsTxIntDegressif.isChecked()) {
//                    str = checked1?"Minimum en compte obligatoire":"le minimum en compte n'est pas obligatoire";
                    rbCrModeCalcInteretCompose.setChecked(true);
                    onRadioButtonClicked(rbCrModeCalcInteretCompose);
                }else{
                    rbCrModeCalcInteretSimple.setChecked(true);
                    onRadioButtonClicked(rbCrModeCalcInteretSimple);
                }

                break;
            case R.id.SwitchCcIsTxInteretJrOn:
                if (SwitchCcIsTxInteretJrOn.isChecked()) {
//                    str = checked1?"Minimum en compte obligatoire":"le minimum en compte n'est pas obligatoire";
                    ll_CcNatureJrTxIntJr.setVisibility(View.VISIBLE);
                }else{
                    ll_CcNatureJrTxIntJr.setVisibility(View.GONE);
                }

                break;
            case R.id.SwitchCcIsTxInteretAvceSpecJrOn:
                if (SwitchCcIsTxInteretAvceSpecJrOn.isChecked()) {
//                    str = checked1?"Minimum en compte obligatoire":"le minimum en compte n'est pas obligatoire";
                    ll_CcNatureJrTxIntAvceSpecJr.setVisibility(View.VISIBLE);
                }else{
                    ll_CcNatureJrTxIntAvceSpecJr.setVisibility(View.GONE);
                }

                break;
            case R.id.SwitchCcIsTxIntJrOn_PenRetAvceSpe:
                if (SwitchCcIsTxIntJrOn_PenRetAvceSpe.isChecked()) {
//                    str = checked1?"Minimum en compte obligatoire":"le minimum en compte n'est pas obligatoire";
                    ll_CcNatJrTxIntJr_PenRetAvceSpe.setVisibility(View.VISIBLE);
                }else{
                    ll_CcNatJrTxIntJr_PenRetAvceSpe.setVisibility(View.GONE);
                }

                break;
            case R.id.SwitchCcIsTxIntJrOn_IntRetAvceSpe:
                if (SwitchCcIsTxIntJrOn_IntRetAvceSpe.isChecked()) {
//                    str = checked1?"Minimum en compte obligatoire":"le minimum en compte n'est pas obligatoire";
                    ll_CcNatJrTxIntJr_IntRetAvceSpe.setVisibility(View.VISIBLE);
                }else{
                    ll_CcNatJrTxIntJr_IntRetAvceSpe.setVisibility(View.GONE);
                }

                break;
            case R.id.SwitchCcIsTxPenRetardJrOn:
                if (SwitchCcIsTxPenRetardJrOn.isChecked()) {
//                    str = checked1?"Minimum en compte obligatoire":"le minimum en compte n'est pas obligatoire";
                    ll_CcNatureJrTxPenRet.setVisibility(View.VISIBLE);
                }else{
                    ll_CcNatureJrTxPenRet.setVisibility(View.GONE);
                }

                break;
            case R.id.SwitchCcIsTxIntJrOn_IntRetDecouv:
                if (SwitchCcIsTxIntJrOn_IntRetDecouv.isChecked()) {
//                    str = checked1?"Minimum en compte obligatoire":"le minimum en compte n'est pas obligatoire";
                    ll_CcNatJrTxIntJr_IntRetDecouv.setVisibility(View.VISIBLE);
                }else{
                    ll_CcNatJrTxIntJr_IntRetDecouv.setVisibility(View.GONE);
                }

                break;
//
            case R.id.SwitchAutoriserDecouvertCC:
                if (CcIsDecouvOn.isChecked()) {
//                    str = checked1 ? "Minimum en compte obligatoire" : "le minimum en compte n'est pas obligatoire";

                    bloc_cc1.setVisibility(View.VISIBLE);
                    //onRadioButtonClicked(rbCrNatFrEtudDossFixe);
                } else {
                    bloc_cc1.setVisibility(View.GONE);
                }

                break;
            case R.id.SwitchCcIsAvanceSpecialOn:
                if (CcIsAvanceSpecialOn.isChecked()) {
//                    str = checked1 ? "Minimum en compte obligatoire" : "le minimum en compte n'est pas obligatoire";

                    bloc_cc_avance_speciale.setVisibility(View.VISIBLE);
                    //onRadioButtonClicked(rbCrNatFrEtudDossFixe);
                } else {
                    bloc_cc_avance_speciale.setVisibility(View.GONE);
                }

                break;
            case R.id.Switch_txtDisponibiliteChequier1CC:
                if (CcIsChequierM1On.isChecked()) {
//                    str = checked1 ? "Minimum en compte obligatoire" : "le minimum en compte n'est pas obligatoire";

                    bloc_cc2.setVisibility(View.VISIBLE);
                    //onRadioButtonClicked(rbCrNatFrEtudDossFixe);
                } else {
                    bloc_cc2.setVisibility(View.GONE);
                }

                break;
            case R.id.Switch_txtDisponibiliteChequier2CC:
                if (CcIsChequierM2On.isChecked()) {
//                    str = checked1 ? "Minimum en compte obligatoire" : "le minimum en compte n'est pas obligatoire";

                    bloc_cc3.setVisibility(View.VISIBLE);
                    //onRadioButtonClicked(rbCrNatFrEtudDossFixe);
                } else {
                    bloc_cc3.setVisibility(View.GONE);
                }

                break;
            case R.id.Switch_txtDisponibiliteChequier3CC:
                if (CcIsChequierM3On.isChecked()) {
//                    str = checked1 ? "Minimum en compte obligatoire" : "le minimum en compte n'est pas obligatoire";

                    bloc_cc4.setVisibility(View.VISIBLE);
                    //onRadioButtonClicked(rbCrNatFrEtudDossFixe);
                } else {
                    bloc_cc4.setVisibility(View.GONE);
                }

                break;
            case R.id.SwitchActiverTxCommissionCC:
                if (CcIsTxCommMvmOper.isChecked()) {
//                    str = checked1 ? "Minimum en compte obligatoire" : "le minimum en compte n'est pas obligatoire";

                    bloc_cc5.setVisibility(View.VISIBLE);
                    //onRadioButtonClicked(rbCrNatFrEtudDossFixe);
                } else {
                    bloc_cc5.setVisibility(View.GONE);
                }

                break;
            //        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
        }
    }
    public void onRadioButtonClicked(View view) {
        boolean checked1 = ((RadioButton) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.rbCcModeCalcIntRetardAvceSpecCompose:
                if (rbCcModeCalcIntRetardAvceSpecCompose.isChecked()) {
                    CcModeCalcIntRetardAvceSpec ="C";
                }
                break;
            case R.id.rbCcModeCalcPenRetardAvceSpecCompose:
                if (rbCcModeCalcPenRetardAvceSpecCompose.isChecked()) {
                    CcModeCalcPenRetardAvceSpec ="C";
                }
                break;
            case R.id.rbCrModeCalcInteretSimple:
                if (rbCrModeCalcInteretSimple.isChecked()) {
                    CcModeCalcInteret ="S";
                }
                break;
            case R.id.rbCrModeCalcInteretCompose:
                if (rbCrModeCalcInteretCompose.isChecked()) {
                    CcModeCalcInteret ="C";
                }
                break;
            case R.id.rbCrModeCalcInteretAvceSpecSimple:
                if (rbCrModeCalcInteretAvceSpecSimple.isChecked()) {
                    CcModeCalcInteretAvceSpec ="S";
                }
                break;
            case R.id.rbCrModeCalcInteretAvceSpecCompose:
                if (rbCrModeCalcInteretAvceSpecCompose.isChecked()) {
                    CcModeCalcInteretAvceSpec ="C";
                }
                break;

            case R.id.rb_CcNatTauxIntPenAvceSpec_Fixe:
                if (CcNatTauxIntPenAvceSpecFixe.isChecked()) {
                    st_CcNatTauxIntPenAvceSpec ="F";
                    str = checked1?"Type Fixe Selected":"Type Fixe Deselected";
                    CcTauxIntPenAvceSpec.setHint("Montant FCFA");
                    tv_plageCcTauxIntPenAvceSpec.setVisibility(View.GONE);
                    input_layout_CcTauxIntPenAvceSpec.setVisibility(View.VISIBLE);
                    input_layout_CcBaseTxIntPenAvceSpec.setVisibility(View.GONE);

                }

                break;

            case R.id.rb_CcNatTauxIntPenAvceSpec_Taux:
                if (CcNatTauxIntPenAvceSpecTaux.isChecked()) {
                    st_CcNatTauxIntPenAvceSpec ="T";
                    str = checked1?"Type Taux Selected":"Type Taux Deselected";
                    CcTauxIntPenAvceSpec.setHint("Taux %");
                    tv_plageCcTauxIntPenAvceSpec.setVisibility(View.GONE);
                    input_layout_CcTauxIntPenAvceSpec.setVisibility(View.VISIBLE);
                    input_layout_CcBaseTxIntPenAvceSpec.setVisibility(View.VISIBLE);

                }

                break;

            case R.id.rb_CcNatTauxIntPenAvceSpec_Plage:
                if (CcNatTauxIntPenAvceSpecPlage.isChecked()){
                    str = checked1?"Type Plage Selected":"Type Plage Deselected";
                    st_CcNatTauxIntPenAvceSpec ="P";
                    tv_plageCcTauxIntPenAvceSpec.setVisibility(View.VISIBLE);
                    input_layout_CcTauxIntPenAvceSpec.setVisibility(View.GONE);
                    input_layout_CcBaseTxIntPenAvceSpec.setVisibility(View.GONE);
                }
                break;
            case R.id.rb_CcNatureTxInt_IntRetAvceSpec_Fixe:
                if (CcNatureTxInt_IntRetAvceSpecFixe.isChecked()) {
                    st_CcNatureTxInt_IntRetAvceSpec ="F";
                    CcTauxInt_IntRetAvceSpec.setHint("Montant FCFA");
                    tv_plage_CcTauxInt_IntRetAvceSpec.setVisibility(View.GONE);
                    input_layout_CcTauxInt_IntRetAvceSpec.setVisibility(View.VISIBLE);
                    input_layout_CcBasexInt_IntRetAvceSpec.setVisibility(View.GONE);

                }

                break;

            case R.id.rb_CcNatureTxInt_IntRetAvceSpec_Taux:
                if (CcNatureTxInt_IntRetAvceSpecTaux.isChecked()) {
                    st_CcNatureTxInt_IntRetAvceSpec ="T";
                    CcTauxInt_IntRetAvceSpec.setHint("Taux %");
                    tv_plage_CcTauxInt_IntRetAvceSpec.setVisibility(View.GONE);
                    input_layout_CcTauxInt_IntRetAvceSpec.setVisibility(View.VISIBLE);
                    input_layout_CcBasexInt_IntRetAvceSpec.setVisibility(View.VISIBLE);

                }

                break;

            case R.id.rb_CcNatureTxInt_IntRetAvceSpec_Plage:
                if (CcNatureTxInt_IntRetAvceSpecPlage.isChecked()){
                    st_CcNatureTxInt_IntRetAvceSpec ="P";
                    tv_plage_CcTauxInt_IntRetAvceSpec.setVisibility(View.VISIBLE);
                    input_layout_CcTauxInt_IntRetAvceSpec.setVisibility(View.GONE);
                    input_layout_CcBasexInt_IntRetAvceSpec.setVisibility(View.GONE);
                }
                break;
            case R.id.rb_CcNatTauxIntAvceSpec_Fixe:
                if (CcNatTauxIntAvceSpecFixe.isChecked()) {
                    // ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    st_CcNatTauxIntAvceSpec ="F";
                    str = checked1?"Type Fixe Selected":"Type Fixe Deselected";
                    CcTauxIntAvceSpec.setHint("Montant");
                    tv_plageCcTauxIntAvceSpec.setVisibility(View.GONE);
                    input_layout_CcTauxIntAvceSpec.setVisibility(View.VISIBLE);
                    input_layout_CcBaseTxIntAvceSpec.setVisibility(View.GONE);

                }

                break;

            case R.id.rb_CcNatTauxIntAvceSpec_Taux:
                if (CcNatTauxIntAvceSpecTaux.isChecked()) {
                    // ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    st_CcNatTauxIntAvceSpec ="T";
                    str = checked1?"Type Taux Selected":"Type Taux Deselected";
                    CcTauxIntAvceSpec.setHint("Taux");
                    tv_plageCcTauxIntAvceSpec.setVisibility(View.GONE);
                    input_layout_CcTauxIntAvceSpec.setVisibility(View.VISIBLE);
                    input_layout_CcBaseTxIntAvceSpec.setVisibility(View.VISIBLE);

                }

                break;

            case R.id.rb_CcNatTauxIntAvceSpec_Plage:
                if (CcNatTauxIntAvceSpecPlage.isChecked()){
                    // ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    str = checked1?"Type Plage Selected":"Type Plage Deselected";
                    st_CcNatTauxIntAvceSpec ="P";
                    tv_plageCcTauxIntAvceSpec.setVisibility(View.VISIBLE);
                    input_layout_CcTauxIntAvceSpec.setVisibility(View.GONE);
                    input_layout_CcBaseTxIntAvceSpec.setVisibility(View.GONE);
                }


                break;
            case R.id.rb_CcNatureTxIntDecouv_Fixe:
                if (CcNatureTxIntDecouvFixe.isChecked()) {
                    // ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    st_CcNatureTxIntDecouv ="F";
                    str = checked1?"Type Fixe Selected":"Type Fixe Deselected";
                    CcValTxIntDecouv.setHint("Montant");
//                    blk_plage_cc.setVisibility(View.GONE);
                    tv_plageTxMtAgio.setVisibility(View.GONE);
                    input_layout_CcValTxMtAgio.setVisibility(View.VISIBLE);
                    input_layout_CcBaseTxMtAgio.setVisibility(View.GONE);

                }

                break;
            case R.id.rb_CcNatureTxIntDecouv_Taux:
                if (CcNatureTxIntDecouvTaux.isChecked()) {
                    // ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    st_CcNatureTxIntDecouv ="T";
                    str = checked1?"Type Taux Selected":"Type Taux Deselected";
                    CcValTxIntDecouv.setHint("Taux");
//                    blk_plage_cc.setVisibility(View.GONE);
                    tv_plageTxMtAgio.setVisibility(View.GONE);
                    input_layout_CcValTxMtAgio.setVisibility(View.VISIBLE);
                    input_layout_CcBaseTxMtAgio.setVisibility(View.VISIBLE);

                }

                break;
            case R.id.rb_CcNatureTxIntDecouv_Plage:
                if (CcNatureTxIntDecouvPlage.isChecked()){
                    // ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    str = checked1?"Type Plage Selected":"Type Plage Deselected";
                    st_CcNatureTxIntDecouv ="P";
//                    blk_plage_cc.setVisibility(View.GONE);
                    tv_plageTxMtAgio.setVisibility(View.VISIBLE);
                    input_layout_CcValTxMtAgio.setVisibility(View.GONE);
                    input_layout_CcBaseTxMtAgio.setVisibility(View.GONE);
                }


                break;
//                Taux pénalité de retard (découvert simple)
            case R.id.rb_CcNatTauxIntPenRetard_Fixe:
                if (CcNatTauxIntPenRetardFixe.isChecked()) {
                    st_CcNatTauxIntPenRetard ="F";
                    CcTauxIntPenRetard.setHint("Montant FCFA");
//                    blk_plage_cc.setVisibility(View.GONE);
                    tv_plage_pen_retard_decouv_simple.setVisibility(View.GONE);
                    input_layout_CcTauxIntPenRetard.setVisibility(View.VISIBLE);
                    input_layout_CcBaseTxIntPenRetard.setVisibility(View.GONE);

                }

                break;
            case R.id.rb_CcNatTauxIntPenRetard_Taux:
                if (CcNatTauxIntPenRetardTaux.isChecked()) {
                    // ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    st_CcNatTauxIntPenRetard ="T";
                    str = checked1?"Type Taux Selected":"Type Taux Deselected";
                    CcTauxIntPenRetard.setHint("Taux %");
//                    blk_plage_cc.setVisibility(View.GONE);
                    tv_plage_pen_retard_decouv_simple.setVisibility(View.GONE);
                    input_layout_CcTauxIntPenRetard.setVisibility(View.VISIBLE);
                    input_layout_CcBaseTxIntPenRetard.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.rb_CcNatTauxIntPenRetard_Plage:
                if (CcNatTauxIntPenRetardPlage.isChecked()){
                    st_CcNatTauxIntPenRetard ="P";
//                    blk_plage_cc.setVisibility(View.GONE);
                    tv_plage_pen_retard_decouv_simple.setVisibility(View.VISIBLE);
                    input_layout_CcTauxIntPenRetard.setVisibility(View.GONE);
                    input_layout_CcBaseTxIntPenRetard.setVisibility(View.GONE);
                }


                break;
//                Taux intérêt de retard (découvert simple)
            case R.id.rb_CcNatureTxInt_IntRetDecouv_Fixe:
                if (CcNatureTxInt_IntRetDecouvFixe.isChecked()) {
                    st_CcNatureTxInt_IntRetDecouv ="F";
                    CcTauxInt_IntRetDecouv.setHint("Montant FCFA");
//                    blk_plage_cc.setVisibility(View.GONE);
                    tv_plage_int_retard_decouv_simple.setVisibility(View.GONE);
                    input_layout_CcTauxInt_IntRetDecouv.setVisibility(View.VISIBLE);
                    input_layout_CcBasexInt_IntRetDecouv.setVisibility(View.GONE);

                }

                break;
            case R.id.rb_CcNatureTxInt_IntRetDecouv_Taux:
                if (CcNatureTxInt_IntRetDecouvTaux.isChecked()) {
                    st_CcNatureTxInt_IntRetDecouv ="T";
                    CcTauxInt_IntRetDecouv.setHint("Taux %");
                    tv_plage_int_retard_decouv_simple.setVisibility(View.GONE);
                    input_layout_CcTauxInt_IntRetDecouv.setVisibility(View.VISIBLE);
                    input_layout_CcBasexInt_IntRetDecouv.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.rb_CcNatureTxInt_IntRetDecouv_Plage:
                if (CcNatureTxInt_IntRetDecouvPlage.isChecked()){
                    st_CcNatureTxInt_IntRetDecouv ="P";
//                    blk_plage_cc.setVisibility(View.GONE);
                    tv_plage_int_retard_decouv_simple.setVisibility(View.VISIBLE);
                    input_layout_CcTauxInt_IntRetDecouv.setVisibility(View.GONE);
                    input_layout_CcBasexInt_IntRetDecouv.setVisibility(View.GONE);
                }


                break;
            case R.id.rb_CcNatureTypDureDecouv_jour:
                if (CcNatureTypDureDecouvJour.isChecked()){
                    str = checked1?"Type Jour Selected":"Type Jour Deselected";
                    st_CcNatureTypDureDecouv ="J";

                }


                break;
            case R.id.rb_CcNatureTypDureDecouv_semaine:
                if (CcNatureTypDureDecouvSemaine.isChecked()){
                    str = checked1?"Type Semaine Selected":"Type Semaine Deselected";
                    st_CcNatureTypDureDecouv ="S";
                   /* blk_plage_cc.setVisibility(View.VISIBLE);
                    input_layout_CcValTxMtAgio.setVisibility(View.GONE);*/
                }


                break;
            case R.id.rb_CcNatureTypDureDecouv_mois:
                if (CcNatureTypDureDecouvMois.isChecked()){
                    str = checked1?"Type Mois Selected":"Type Mois Deselected";
                    st_CcNatureTypDureDecouv ="M";
                   /* blk_plage_cc.setVisibility(View.VISIBLE);
                    input_layout_CcValTxMtAgio.setVisibility(View.GONE);*/
                }


                break;
            case R.id.rb_CcNatureTxMtAgio_taux:
                if (CcNatureTxMtAgioTaux.isChecked()){
                    str = checked1?"Type Taux Selected":"Type Taux Deselected";
                    st_CcNatureTxMtAgio ="T";
//                    blk_plage_agios_cc.setVisibility(View.GONE);
                    tv_plageCalculAgiosCC.setVisibility(View.GONE);
                    CcValTxMtAgio.setHint("Taux agios");
                    input_layout_CcValTxIntDecouv.setVisibility(View.VISIBLE);
                    input_layout_CcBaseCalculAgiosCC.setVisibility(View.VISIBLE);
                }


                break;
            case R.id.rb_CcNatureTxMtAgio_montant:
                if (CcNatureTxMtAgioMontant.isChecked()){
                    str = checked1?"Type Montant Selected":"Type Montant Deselected";
                    st_CcNatureTxMtAgio ="M";
//                    blk_plage_agios_cc.setVisibility(View.GONE);
                    tv_plageCalculAgiosCC.setVisibility(View.GONE);
                    CcValTxMtAgio.setHint("Montant agios");
                    input_layout_CcValTxIntDecouv.setVisibility(View.VISIBLE);
                    input_layout_CcBaseCalculAgiosCC.setVisibility(View.GONE);
                }



                break;
            case R.id.rb_CcNatureTxMtAgio_plage:
                if (CcNatureTxMtAgioPlage.isChecked()){
                    str = checked1?"Type Plage Selected":"Type Plage Deselected";
                    st_CcNatureTxMtAgio ="P";
//                    blk_plage_agios_cc.setVisibility(View.GONE);
                    tv_plageCalculAgiosCC.setVisibility(View.VISIBLE);
                    input_layout_CcValTxIntDecouv.setVisibility(View.GONE);
                    input_layout_CcBaseCalculAgiosCC.setVisibility(View.GONE);
                }


                break;
            case R.id.rb_CcNatTxComm_fixe:
                if (CcNatTxCommFixe.isChecked()){
                    str = checked1?"Type Fixe Selected":"Type Fixe Deselected";
                    st_CcNatTxComm ="T";
//                    blk_plage_CcValTxCommMvm.setVisibility(View.GONE);
                    tv_plageTxCommMvmCC.setVisibility(View.GONE);
                    input_layout_CcValTxCommMvm.setVisibility(View.VISIBLE);
                    CcValTxCommMvm.setHint("Taux commission");
                    input_layout_CcBaseTxCommMvmCC.setVisibility(View.VISIBLE);
                }


                break;
            case R.id.rb_CcNatTxComm_Montant:
                if (CcNatTxCommMontant.isChecked()){
                    str = checked1?"Type Montant Selected":"Type Montant Deselected";
                    st_CcNatTxComm ="M";
//                    blk_plage_CcValTxCommMvm.setVisibility(View.GONE);
                    tv_plageTxCommMvmCC.setVisibility(View.GONE);
                    input_layout_CcValTxCommMvm.setVisibility(View.VISIBLE);
                    CcValTxCommMvm.setHint("Montant commission");
                    input_layout_CcBaseTxCommMvmCC.setVisibility(View.GONE);
                }


                break;
            case R.id.rb_CcNatTxComm_Plage:
                if (CcNatTxCommPlage.isChecked()){
                    str = checked1?"Type Plage Selected":"Type Plage Deselected";
                    st_CcNatTxComm ="P";
//                    blk_plage_CcValTxCommMvm.setVisibility(View.GONE);// a supprimer tout le bloc car inutile
                    tv_plageTxCommMvmCC.setVisibility(View.VISIBLE);
                    input_layout_CcValTxCommMvm.setVisibility(View.GONE);
                    input_layout_CcBaseTxCommMvmCC.setVisibility(View.GONE);
                }


                break;
            case R.id.rbCcNaturePerTxInteretJour:
                if (rbCcNaturePerTxInteretJour.isChecked()){
                    CcNaturePerTxInteret ="J";

                }
                break;
            case R.id.rbCcNaturePerTxInteretMois:
                if (rbCcNaturePerTxInteretMois.isChecked()){
                    CcNaturePerTxInteret ="M";
                }
                break;
            case R.id.rbCcNaturePerTxInteretTrimestre:
                if (rbCcNaturePerTxInteretTrimestre.isChecked()){
                    CcNaturePerTxInteret ="T";
                }
                break;
            case R.id.rbCcNaturePerTxInteretAnnee:
                if (rbCcNaturePerTxInteretAnnee.isChecked()){
                    CcNaturePerTxInteret ="A";
                }
                break;
            case R.id.rbCcNaturePerTxPenRetJour:
                if (rbCcNaturePerTxPenRetJour.isChecked()){
                    CcNaturePerTxPenRet ="J";

                }
                break;
            case R.id.rbCcNaturePerTxPenRetMois:
                if (rbCcNaturePerTxPenRetMois.isChecked()){
                    CcNaturePerTxPenRet ="M";
                }
                break;
            case R.id.rbCcNaturePerTxPenRetTrimestre:
                if (rbCcNaturePerTxPenRetTrimestre.isChecked()){
                    CcNaturePerTxPenRet ="T";
                }
                break;
            case R.id.rbCcNaturePerTxPenRetAnnee:
                if (rbCcNaturePerTxPenRetAnnee.isChecked()){
                    CcNaturePerTxPenRet ="A";
                }
                break;
            case R.id.rbCcPeriod_IntRetDecouvJour:
                if (rbCcPeriod_IntRetDecouvJour.isChecked()){
                    CcPeriod_IntRetDecouv ="J";

                }
                break;
            case R.id.rbCcPeriod_IntRetDecouvMois:
                if (rbCcPeriod_IntRetDecouvMois.isChecked()){
                    CcPeriod_IntRetDecouv ="M";
                }
                break;
            case R.id.rbCcPeriod_IntRetDecouvTrimestre:
                if (rbCcPeriod_IntRetDecouvTrimestre.isChecked()){
                    CcPeriod_IntRetDecouv ="T";
                }
                break;
            case R.id.rbCcPeriod_IntRetDecouvAnnee:
                if (rbCcPeriod_IntRetDecouvAnnee.isChecked()){
                    CcPeriod_IntRetDecouv ="A";
                }
                break;
            case R.id.rbCcNaturePerTxInteretAvceSpecJour:
                if (rbCcNaturePerTxInteretAvceSpecJour.isChecked()){
                    CcNaturePerTxInteretAvceSpec ="J";
                }
                break;
            case R.id.rbCcNaturePerTxInteretAvceSpecMois:
                if (rbCcNaturePerTxInteretAvceSpecMois.isChecked()){
                    CcNaturePerTxInteretAvceSpec ="M";
                }
                break;
            case R.id.rbCcNaturePerTxInteretAvceSpecTrimestre:
                if (rbCcNaturePerTxInteretAvceSpecTrimestre.isChecked()){
                    CcNaturePerTxInteretAvceSpec ="T";
                }
                break;
            case R.id.rbCcNaturePerTxInteretAvceSpecAnnee:
                if (rbCcNaturePerTxInteretAvceSpecAnnee.isChecked()){
                    CcNaturePerTxInteretAvceSpec ="A";
                }
                break;
            case R.id.rbCcPeriod_PenRetAvceSpecJour:
                if (rbCcPeriod_PenRetAvceSpecJour.isChecked()){
                    CcPeriod_PenRetAvceSpec ="J";
                }
                break;
            case R.id.rbCcPeriod_PenRetAvceSpecMois:
                if (rbCcPeriod_PenRetAvceSpecMois.isChecked()){
                    CcPeriod_PenRetAvceSpec ="M";
                }
                break;
            case R.id.rbCcPeriod_PenRetAvceSpecTrimestre:
                if (rbCcPeriod_PenRetAvceSpecTrimestre.isChecked()){
                    CcPeriod_PenRetAvceSpec ="T";
                }
                break;
            case R.id.rbCcPeriod_PenRetAvceSpecAnnee:
                if (rbCcPeriod_PenRetAvceSpecAnnee.isChecked()){
                    CcPeriod_PenRetAvceSpec ="A";
                }
                break;
            case R.id.rbCcPeriod_IntRetAvceSpecJour:
                if (rbCcPeriod_IntRetAvceSpecJour.isChecked()){
                    CcPeriod_IntRetAvceSpec ="J";
                }
                break;
            case R.id.rbCcPeriod_IntRetAvceSpecMois:
                if (rbCcPeriod_IntRetAvceSpecMois.isChecked()){
                    CcPeriod_IntRetAvceSpec ="M";
                }
                break;
            case R.id.rbCcPeriod_IntRetAvceSpecTrimestre:
                if (rbCcPeriod_IntRetAvceSpecTrimestre.isChecked()){
                    CcPeriod_IntRetAvceSpec ="T";
                }
                break;
            case R.id.rbCcPeriod_IntRetAvceSpecAnnee:
                if (rbCcPeriod_IntRetAvceSpecAnnee.isChecked()){
                    CcPeriod_IntRetAvceSpec ="A";
                }
                break;
            case R.id.rbCcNatureJrTxIntJrLunVen:
                if (rbCcNatureJrTxIntJrLunVen.isChecked()){
                    CcNatureJrTxIntJr ="V";
                }
                break;
            case R.id.rbCcNatureJrTxIntJrLunSam:
                if (rbCcNatureJrTxIntJrLunSam.isChecked()){
                    CcNatureJrTxIntJr ="S";
                }
                break;
            case R.id.rbCcNatureJrTxIntJrJrOuvertGu:
                if (rbCcNatureJrTxIntJrJrOuvertGu.isChecked()){
                    CcNatureJrTxIntJr ="G";
                }
                break;
            case R.id.rbCcNatureJrTxIntJrTous:
                if (rbCcNatureJrTxIntJrTous.isChecked()){
                    CcNatureJrTxIntJr ="T";
                }
                break;
            case R.id.rbCcNatureJrTxPenRetLunVen:
                if (rbCcNatureJrTxPenRetLunVen.isChecked()){
                    CcNatureJrTxPenRet ="V";
                }
                break;
            case R.id.rbCcNatureJrTxPenRetLunSam:
                if (rbCcNatureJrTxPenRetLunSam.isChecked()){
                    CcNatureJrTxPenRet ="S";
                }
                break;
            case R.id.rbCcNatureJrTxPenRetJrOuvertGu:
                if (rbCcNatureJrTxPenRetJrOuvertGu.isChecked()){
                    CcNatureJrTxPenRet ="G";
                }
                break;
            case R.id.rbCcNatureJrTxPenRetTous:
                if (rbCcNatureJrTxPenRetTous.isChecked()){
                    CcNatureJrTxPenRet ="T";
                }
                break;
            case R.id.rbCcNatJrTxIntJr_IntRetDecouvLunVen:
                if (rbCcNatJrTxIntJr_IntRetDecouvLunVen.isChecked()){
                    CcNatJrTxIntJr_IntRetDecouv ="V";
                }
                break;
            case R.id.rbCcNatJrTxIntJr_IntRetDecouvLunSam:
                if (rbCcNatJrTxIntJr_IntRetDecouvLunSam.isChecked()){
                    CcNatJrTxIntJr_IntRetDecouv ="S";
                }
                break;
            case R.id.rbCcNatJrTxIntJr_IntRetDecouvJrOuvertGu:
                if (rbCcNatJrTxIntJr_IntRetDecouvJrOuvertGu.isChecked()){
                    CcNatJrTxIntJr_IntRetDecouv ="G";
                }
                break;
            case R.id.rbCcNatJrTxIntJr_IntRetDecouvTous:
                if (rbCcNatJrTxIntJr_IntRetDecouvTous.isChecked()){
                    CcNatJrTxIntJr_IntRetDecouv ="T";
                }
                break;
            case R.id.rbCcNatureJrTxIntAvceSpecJrLunVen:
                if (rbCcNatureJrTxIntAvceSpecJrLunVen.isChecked()){
                    CcNatureJrTxIntAvceSpecJr ="V";
                }
                break;
            case R.id.rbCcNatureJrTxIntAvceSpecJrLunSam:
                if (rbCcNatureJrTxIntAvceSpecJrLunSam.isChecked()){
                    CcNatureJrTxIntAvceSpecJr ="S";
                }
                break;
            case R.id.rbCcNatureJrTxIntAvceSpecJrJrOuvertGu:
                if (rbCcNatureJrTxIntAvceSpecJrJrOuvertGu.isChecked()){
                    CcNatureJrTxIntAvceSpecJr ="G";
                }
                break;
            case R.id.rbCcNatureJrTxIntAvceSpecJrTous:
                if (rbCcNatureJrTxIntAvceSpecJrTous.isChecked()){
                    CcNatureJrTxIntAvceSpecJr ="T";
                }
                break;
            case R.id.rbCcNatJrTxIntJr_PenRetAvceSpeLunVen:
                if (rbCcNatJrTxIntJr_PenRetAvceSpeLunVen.isChecked()){
                    CcNatJrTxIntJr_PenRetAvceSpe ="V";
                }
                break;
            case R.id.rbCcNatJrTxIntJr_PenRetAvceSpeLunSam:
                if (rbCcNatJrTxIntJr_PenRetAvceSpeLunSam.isChecked()){
                    CcNatJrTxIntJr_PenRetAvceSpe ="S";
                }
                break;
            case R.id.rbCcNatJrTxIntJr_PenRetAvceSpeJrOuvertGu:
                if (rbCcNatJrTxIntJr_PenRetAvceSpeJrOuvertGu.isChecked()){
                    CcNatJrTxIntJr_PenRetAvceSpe ="G";
                }
                break;
            case R.id.rbCcNatJrTxIntJr_PenRetAvceSpeTous:
                if (rbCcNatJrTxIntJr_PenRetAvceSpeTous.isChecked()){
                    CcNatJrTxIntJr_PenRetAvceSpe ="T";
                }
                break;
            case R.id.rbCcNatJrTxIntJr_IntRetAvceSpeLunVen:
                if (rbCcNatJrTxIntJr_IntRetAvceSpeLunVen.isChecked()){
                    CcNatJrTxIntJr_IntRetAvceSpe ="V";
                }
                break;
            case R.id.rbCcNatJrTxIntJr_IntRetAvceSpeLunSam:
                if (rbCcNatJrTxIntJr_IntRetAvceSpeLunSam.isChecked()){
                    CcNatJrTxIntJr_IntRetAvceSpe ="S";
                }
                break;
            case R.id.rbCcNatJrTxIntJr_IntRetAvceSpeJrOuvertGu:
                if (rbCcNatJrTxIntJr_IntRetAvceSpeJrOuvertGu.isChecked()){
                    CcNatJrTxIntJr_IntRetAvceSpe ="G";
                }
                break;
            case R.id.rbCcNatJrTxIntJr_IntRetAvceSpeTous:
                if (rbCcNatJrTxIntJr_IntRetAvceSpeTous.isChecked()){
                    CcNatJrTxIntJr_IntRetAvceSpe ="T";
                }
                break;
           /* case R.id.rbEpTypTxInterPlage:
                if (rbEpTypTxInterPlage.isChecked()) {
                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    ev_typ_fr_agios ="P";
                    blk_plage_cc.setVisibility(View.VISIBLE);
                    input_layout_CcValTxMtAgio.setVisibility(View.INVISIBLE);
                }
                break; */
        }
//        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }



    /**
     * Checks whether all files are filled. If so then calls AddCpteCourantAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addCompteCourant() {


if (!STRING_EMPTY.equals(CcCode.getText().toString().trim()) &&
        !STRING_EMPTY.equals(CcLibelle.getText().toString().trim())
        ){

            try {
                Double h1, h2, h3, h4, h5, h6, h7;
                if (!(CcMtMaxDecouv.getText().toString().replaceAll(",", "").trim()).equals(STRING_EMPTY)){
                    h1 = Double.valueOf(CcMtMaxDecouv.getText().toString().replaceAll(",", "").trim());
                    CcMtMaxDecouv.setText(h1+"");
                }



                if (!(CcValTxIntDecouv.getText().toString().replaceAll(",", "").trim()).equals(STRING_EMPTY)){
                    h2 = Double.valueOf(CcValTxIntDecouv.getText().toString().replaceAll(",", "").trim());
                    CcValTxIntDecouv.setText(h2+"");
                }

                if (!(CcTauxIntPenRetard.getText().toString().replaceAll(",", "").trim()).equals(STRING_EMPTY)){
                    h3 = Double.valueOf(CcTauxIntPenRetard.getText().toString().replaceAll(",", "").trim());
                    CcTauxIntPenRetard.setText(h3+"");
                }


                if (!(CcTauxInt_IntRetDecouv.getText().toString().replaceAll(",", "").trim()).equals(STRING_EMPTY)){
                    h4 = Double.valueOf(CcTauxInt_IntRetDecouv.getText().toString().replaceAll(",", "").trim());
                    CcTauxInt_IntRetDecouv.setText(h4+"");
                }
                if (!(CcTauxIntAvceSpec.getText().toString().replaceAll(",", "").trim()).equals(STRING_EMPTY)){
                    h5 = Double.valueOf(CcTauxIntAvceSpec.getText().toString().replaceAll(",", "").trim());
                    CcTauxIntAvceSpec.setText(h5+"");
                }
                if (!(CcTauxIntPenAvceSpec.getText().toString().replaceAll(",", "").trim()).equals(STRING_EMPTY)){
                    h6 = Double.valueOf(CcTauxIntPenAvceSpec.getText().toString().replaceAll(",", "").trim());
                    CcTauxIntPenAvceSpec.setText(h6+"");
                }
                if (!(CcTauxInt_IntRetAvceSpec.getText().toString().replaceAll(",", "").trim()).equals(STRING_EMPTY)){
                    h7 = Double.valueOf(CcTauxInt_IntRetAvceSpec.getText().toString().replaceAll(",", "").trim());
                    CcTauxInt_IntRetAvceSpec.setText(h7+"");
                }




                st_CcCode=  CcCode.getText().toString();
//                st_CcLibelle=  CcLibelle.getText().toString();
                st_CcLibelle =  MyData.normalizeSymbolsAndAccents(CcLibelle.getText().toString());//Pour échapper les accents
                if (CcIsDecouvOn.isChecked()){
                    st_CcIsDecouvOn = "Y";
                }else{
                    st_CcIsDecouvOn = "N";
                }
//            st_CcIsDecouvOn=  CcIsDecouvOn.isChecked();
                st_CcMtMaxDecouv=  CcMtMaxDecouv.getText().toString();
                st_CcMtPlafondAvceSpec=  CcMtPlafondAvceSpec.getText().toString();

                if (CcIsMaxDecouvNeg.isChecked()){
                    st_CcIsMaxDecouvNeg = "Y";
                }else{
                    st_CcIsMaxDecouvNeg = "N";
                }

                if (CcIsAvanceSpecialOn.isChecked()){
                    st_CcIsAvanceSpecialOn = "Y";
                }else{
                    st_CcIsAvanceSpecialOn = "N";
                }
//            st_CcIsMaxDecouvNeg=  CcIsMaxDecouvNeg.isChecked();
//            st_CcIsAvanceSpecialOn=  CcIsAvanceSpecialOn.isChecked();
                //st_CcNatureTxIntDecouv=  CcNatureTxIntDecouv.getText().toString();
                st_CcValTxIntDecouv=  CcValTxIntDecouv.getText().toString();
                baseCcTxIntDecouv = mySpinnerBaseTxIntDecouv.getText().toString();
                baseCcTxIntPenRetard = mySpinnerCcBaseTxIntPenRetard.getText().toString();
//    st_CcPlageTxIntDecouvFrom=  CcPlageTxIntDecouvFrom.getText().toString();
//    st_CcPlageTxIntDecouvTo=  CcPlageTxIntDecouvTo.getText().toString();
                st_CcDureeMaxDecouv=  CcDureeMaxDecouv.getText().toString();
                // st_CcNatureTypDureDecouv=  CcNatureTypDureDecouv.getText().toString();
                //st_CcNatureTxMtAgio=  CcNatureTxMtAgio.getText().toString();
                st_CcValTxMtAgio=  CcValTxMtAgio.getText().toString();
                st_CcNatBaseAgio = mySpinnerBaseTxMtAgio.getText().toString();
//    st_CcPlageTxMtAgioFrom=  CcPlageTxMtAgioFrom.getText().toString();
//            st_CcPlageTxMtAgioTo=  CcPlageTxMtAgioTo.getText().toString();
                //st_CcNatBaseAgio=  CcNatBaseAgio.getText().toString();
                if (CcIsChequierM1On.isChecked()){
                    st_CcIsChequierM1On = "Y";
                }else{
                    st_CcIsChequierM1On = "N";
                }
                if (CcIsPlafAvceSpecNegoc.isChecked()){
                    st_CcIsPlafAvceSpecNegoc = "Y";
                }else{
                    st_CcIsPlafAvceSpecNegoc = "N";
                }
                if (CcIsTxIntDegressif.isChecked()){
                    st_CcIsTxIntDegressif = "Y";
                }else{
                    st_CcIsTxIntDegressif = "N";
                }

                if (CcIsChequierM2On.isChecked()){
                    st_CcIsChequierM2On = "Y";
                }else{
                    st_CcIsChequierM2On = "N";
                }
                if (CcIsChequierM3On.isChecked()){
                    st_CcIsChequierM3On = "Y";
                }else{
                    st_CcIsChequierM3On = "N";
                }
                if (CcIsTxCommMvmOper.isChecked()){
                    st_CcIsTxCommMvmOper = "Y";
                }else{
                    st_CcIsTxCommMvmOper = "N";
                }
//    st_CcIsChequierM1On=  CcIsChequierM1On.isChecked();
//    st_CcIsPlafAvceSpecNegoc=  CcIsPlafAvceSpecNegoc.isChecked();
//    st_CcIsTxIntDegressif=  CcIsTxIntDegressif.isChecked();
                st_CcNbPagesCheqM1=  CcNbPagesCheqM1.getText().toString();
                st_CcPrixVteCheqM1=  CcPrixVteCheqM1.getText().toString();
//    st_CcIsChequierM2On=  CcIsChequierM2On.isChecked();
                st_CcNbPagesCheqM2=  CcNbPagesCheqM2.getText().toString();
                st_CcPrixVteCheqM2=  CcPrixVteCheqM2.getText().toString();
//    st_CcIsChequierM3On=  CcIsChequierM3On.isChecked();
                st_CcNbPagesCheqM3=  CcNbPagesCheqM3.getText().toString();
                st_CcPrixVteCheqM3=  CcPrixVteCheqM3.getText().toString();
                st_CcDureeValidCheq=  CcDureeValidCheq.getText().toString();
                st_CcNbMinSignatChq=  CcNbMinSignatChq.getText().toString();
//    st_CcIsTxCommMvmOper=  CcIsTxCommMvmOper.isChecked();
                //st_CcNatTxComm=  CcNatTxComm.getText().toString();
                st_CcValTxCommMvm=  CcValTxCommMvm.getText().toString();
                baseCcTxCommMvm  = mySpinnerBaseTxCommMvm.getText().toString();
//    st_CcPlageTxCommMvmFrom=  CcPlageTxCommMvmFrom.getText().toString();
//    st_CcPlageTxCommMvmTo=  CcPlageTxCommMvmTo.getText().toString();

                st_CcTauxIntAvceSpec=  CcTauxIntAvceSpec.getText().toString();
                baseTxIntAvceSpec  = mySpinnerCcBaseTxIntAvceSpec.getText().toString();
                st_CcTauxIntPenAvceSpec=  CcTauxIntPenAvceSpec.getText().toString();
                baseTxIntPenAvceSpec  = mySpinnerCcBaseTxIntPenAvceSpec.getText().toString();
                /*START NEW*/

                if(SwitchCcIsTxInteretJrOn.isChecked()){
                    CcIsTxInteretJrOn  = "Y";
                }else{
                    CcIsTxInteretJrOn  = "N";
                }
                if(SwitchCcIsTxPenRetardJrOn.isChecked()){
                    CcIsTxPenRetardJrOn  = "Y";
                }else{
                    CcIsTxPenRetardJrOn  = "N";
                }
                if(SwitchCcIsTxIntJrOn_IntRetDecouv.isChecked()){
                    CcIsTxIntJrOn_IntRetDecouv  = "Y";
                }else{
                    CcIsTxIntJrOn_IntRetDecouv  = "N";
                }
                if(SwitchCcIsTxInteretAvceSpecJrOn.isChecked()){
                    CcIsTxInteretAvceSpecJrOn  = "Y";
                }else{
                    CcIsTxInteretAvceSpecJrOn  = "N";
                }
                if(SwitchCcIsTxIntAvceSpecDegressif.isChecked()){
                    CcIsTxIntAvceSpecDegressif  = "Y";
                }else{
                    CcIsTxIntAvceSpecDegressif  = "N";
                }
                if(SwitchCcIsTxPenRetardAvceSpecDegressif.isChecked()){
                    CcIsTxPenRetardAvceSpecDegressif  = "Y";
                }else{
                    CcIsTxPenRetardAvceSpecDegressif  = "N";
                }
                if(SwitchCcIsTxIntJrOn_PenRetAvceSpe.isChecked()){
                    CcIsTxIntJrOn_PenRetAvceSpe  = "Y";
                }else{
                    CcIsTxIntJrOn_PenRetAvceSpe  = "N";
                }
                if(SwitchCcIsTxIntRetardAvceSpecDegressif.isChecked()){
                    CcIsTxIntRetardAvceSpecDegressif  = "Y";
                }else{
                    CcIsTxIntRetardAvceSpecDegressif  = "N";
                }
                if(SwitchCcIsTxIntJrOn_IntRetAvceSpe.isChecked()){
                    CcIsTxIntJrOn_IntRetAvceSpe  = "Y";
                }else{
                    CcIsTxIntJrOn_IntRetAvceSpe  = "N";
                }
                if(SwitchDecouvertPermanentAutoriseCC.isChecked()){
                    CcIsDecouvertPermanentAutorise  = "Y";
                }else{
                    CcIsDecouvertPermanentAutorise  = "N";
                }
                st_CcTauxIntPenRetard = CcTauxIntPenRetard.getText().toString();
                st_CcTauxInt_IntRetDecouv = CcTauxInt_IntRetDecouv.getText().toString();
                st_CcBasexInt_IntRetDecouv = mySpinnerCcBasexInt_IntRetDecouv.getText().toString();
                st_CcDureeMaxAvceSpec=  CcDureeMaxAvceSpec.getText().toString();
                st_CcTauxInt_IntRetAvceSpec=  CcTauxInt_IntRetAvceSpec.getText().toString();
                st_CcBasexInt_IntRetAvceSpec=  mySpinnerCcBasexInt_IntRetAvceSpec.getText().toString();
                st_CcMaxMontantDecouvertPermanentAutorise=  CcMaxMontantDecouvertPermanentAutorise.getText().toString();
                st_CcMaxDureeDecouvertPermanentAutorise=  CcMaxDureeDecouvertPermanentAutorise.getText().toString();

                /*END NEW*/
                //to manage plage data
                //#CTP
                for (int i=0; i<plageDataListCTP.size();i++){
                    tabPlageDebutCTP +=";"+plageDataListCTP.get(i).getPdValDe();
                    tabPlageFinCTP +=";"+plageDataListCTP.get(i).getPdValA();
                    tabPlageValeurCTP +=";"+plageDataListCTP.get(i).getPdValTaux();
                    tabPlageBaseCTP +=";"+plageDataListCTP.get(i).getPdBase();
                    tabPlageNatureCTP +=";"+plageDataListCTP.get(i).getPdNature();
                }
                //#CAP
                for (int i=0; i<plageDataListCAP.size();i++){
                    tabPlageDebutCAP +=";"+plageDataListCAP.get(i).getPdValDe();
                    tabPlageFinCAP +=";"+plageDataListCAP.get(i).getPdValA();
                    tabPlageValeurCAP +=";"+plageDataListCAP.get(i).getPdValTaux();
                    tabPlageBaseCAP +=";"+plageDataListCAP.get(i).getPdBase();
                    tabPlageNatureCAP +=";"+plageDataListCAP.get(i).getPdNature();
                }
                //#TCP
                for (int i=0; i<plageDataListTCP.size();i++){
                    tabPlageDebutTCP +=";"+plageDataListTCP.get(i).getPdValDe();
                    tabPlageFinTCP +=";"+plageDataListTCP.get(i).getPdValA();
                    tabPlageValeurTCP +=";"+plageDataListTCP.get(i).getPdValTaux();
                    tabPlageBaseTCP +=";"+plageDataListTCP.get(i).getPdBase();
                    tabPlageNatureTCP +=";"+plageDataListTCP.get(i).getPdNature();
                }
                //#TAS
                for (int i=0; i<plageDataListTAS.size();i++){
                    tabPlageDebutTAS +=";"+plageDataListTAS.get(i).getPdValDe();
                    tabPlageFinTAS +=";"+plageDataListTAS.get(i).getPdValA();
                    tabPlageValeurTAS +=";"+plageDataListTAS.get(i).getPdValTaux();
                    tabPlageBaseTAS +=";"+plageDataListTAS.get(i).getPdBase();
                    tabPlageNatureTAS +=";"+plageDataListTAS.get(i).getPdNature();
                }
                //#PAS
                for (int i=0; i<plageDataListPAS.size();i++){
                    tabPlageDebutPAS +=";"+plageDataListPAS.get(i).getPdValDe();
                    tabPlageFinPAS +=";"+plageDataListPAS.get(i).getPdValA();
                    tabPlageValeurPAS +=";"+plageDataListPAS.get(i).getPdValTaux();
                    tabPlageBasePAS +=";"+plageDataListPAS.get(i).getPdBase();
                    tabPlageNaturePAS +=";"+plageDataListPAS.get(i).getPdNature();
                }





                new AddCpteCourantAsyncTask().execute();


            }catch (Exception e){
            e.printStackTrace();
            }
        } else {
            Toast.makeText(CreateProduitCpteCourant.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for adding a movie
     */
    private class AddCpteCourantAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(CreateProduitCpteCourant.this);
            pDialog.setMessage("Adding Compte Courant. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_CcCode, st_CcCode);
            httpParams.put(KEY_CcLibelle, st_CcLibelle);
            httpParams.put(KEY_CcIsDecouvOn, st_CcIsDecouvOn);
            httpParams.put(KEY_CcMtMaxDecouv, st_CcMtMaxDecouv);
            httpParams.put(KEY_CcIsMaxDecouvNeg, st_CcIsMaxDecouvNeg);
            httpParams.put(KEY_CcDureeMaxDecouv, st_CcDureeMaxDecouv);
            httpParams.put(KEY_CcNatureTypDureDecouv, st_CcNatureTypDureDecouv);

            httpParams.put(KEY_CcNatureTxIntDecouv, st_CcNatureTxIntDecouv);
            httpParams.put(KEY_CcValTxIntDecouv, st_CcValTxIntDecouv);
            httpParams.put(KEY_CcBaseTxIntDecouv, baseCcTxIntDecouv);

            httpParams.put(KEY_CcIsTxIntDegressif, st_CcIsTxIntDegressif);
            httpParams.put(KEY_CcModeCalcInteret, CcModeCalcInteret);
            httpParams.put(KEY_CcNaturePerTxInteret, CcNaturePerTxInteret);
            httpParams.put(KEY_CcIsTxInteretJrOn, CcIsTxInteretJrOn);
            httpParams.put(KEY_CcNatureJrTxIntJr, CcNatureJrTxIntJr);

//            httpParams.put(KEY_CcPlageTxIntDecouvFrom, st_CcPlageTxIntDecouvFrom);
//            httpParams.put(KEY_CcPlageTxIntDecouvTo, st_CcPlageTxIntDecouvTo);

            //Début pénalité de retard decouv simple

            httpParams.put(KEY_CcNatTauxIntPenRetard, st_CcNatTauxIntPenRetard);
            httpParams.put(KEY_CcTauxIntPenRetard, st_CcTauxIntPenRetard);
            httpParams.put(KEY_CcBaseTxIntPenRetard, baseCcTxIntPenRetard);
            httpParams.put(KEY_CcNaturePerTxPenRet, CcNaturePerTxPenRet);
            httpParams.put(KEY_CcIsTxPenRetardJrOn, CcIsTxPenRetardJrOn);
            httpParams.put(KEY_CcNatureJrTxPenRet, CcNatureJrTxPenRet);

            //Fin pénalité de retard decouv simple

            //Début intérêt de retard decouv simple
            httpParams.put(KEY_CcNatureTxInt_IntRetDecouv, st_CcNatureTxInt_IntRetDecouv);
            httpParams.put(KEY_CcTauxInt_IntRetDecouv, st_CcTauxInt_IntRetDecouv);
            httpParams.put(KEY_CcBasexInt_IntRetDecouv, st_CcBasexInt_IntRetDecouv);
            httpParams.put(KEY_CcPeriod_IntRetDecouv, CcPeriod_IntRetDecouv);
            httpParams.put(KEY_CcIsTxIntJrOn_IntRetDecouv, CcIsTxIntJrOn_IntRetDecouv);
            httpParams.put(KEY_CcNatJrTxIntJr_IntRetDecouv, CcNatJrTxIntJr_IntRetDecouv);
            //Fin intérêt de retard decouv simple

            //Début decouv permanent
            httpParams.put(KEY_CcIsDecouvertPermanentAutorise, CcIsDecouvertPermanentAutorise);
            httpParams.put(KEY_CcMaxMontantDecouvertPermanentAutorise, st_CcMaxMontantDecouvertPermanentAutorise);
            httpParams.put(KEY_CcMaxDureeDecouvertPermanentAutorise, st_CcMaxDureeDecouvertPermanentAutorise);
            //Fin decouv permanent

            //Début Agios ou frais de tenue de compte
            httpParams.put(KEY_CcNatureTxMtAgio, st_CcNatureTxMtAgio);
            httpParams.put(KEY_CcValTxMtAgio, st_CcValTxMtAgio);
            httpParams.put(KEY_CcNatBaseAgio, st_CcNatBaseAgio);
//            httpParams.put(KEY_CcPlageTxMtAgioFrom, st_CcPlageTxMtAgioFrom);
//            httpParams.put(KEY_CcPlageTxMtAgioTo, st_CcPlageTxMtAgioTo);

            //Fin Agios ou frais de tenue de compte

            //Début chéquier
            httpParams.put(KEY_CcIsChequierM1On, st_CcIsChequierM1On);
            httpParams.put(KEY_CcNbPagesCheqM1, st_CcNbPagesCheqM1);
            httpParams.put(KEY_CcPrixVteCheqM1, st_CcPrixVteCheqM1);
            httpParams.put(KEY_CcIsChequierM2On, st_CcIsChequierM2On);
            httpParams.put(KEY_CcNbPagesCheqM2, st_CcNbPagesCheqM2);
            httpParams.put(KEY_CcPrixVteCheqM2, st_CcPrixVteCheqM2);
            httpParams.put(KEY_CcIsChequierM3On, st_CcIsChequierM3On);
            httpParams.put(KEY_CcNbPagesCheqM3, st_CcNbPagesCheqM3);
            httpParams.put(KEY_CcPrixVteCheqM3, st_CcPrixVteCheqM3);
            httpParams.put(KEY_CcDureeValidCheq, st_CcDureeValidCheq);
            httpParams.put(KEY_CcNbMinSignatChq, st_CcNbMinSignatChq);
            //Fin chéquier

            //Début Taux de commission par mouvement

            httpParams.put(KEY_CcIsTxCommMvmOper, st_CcIsTxCommMvmOper);
            httpParams.put(KEY_CcNatTxComm, st_CcNatTxComm);
            httpParams.put(KEY_CcValTxCommMvm, st_CcValTxCommMvm);
            httpParams.put(KEY_CcBaseTxCommMvm, baseCcTxCommMvm);
//            httpParams.put(KEY_CcPlageTxCommMvmFrom, st_CcPlageTxCommMvmFrom);
//            httpParams.put(KEY_CcPlageTxCommMvmTo, st_CcPlageTxCommMvmTo);
            //Fin Taux de commission par mouvement


            //Début Taux d'interet nominal avance speciale
            httpParams.put(KEY_CcIsAvanceSpecialOn, st_CcIsAvanceSpecialOn);
            httpParams.put(KEY_CcMtPlafondAvceSpec, st_CcMtPlafondAvceSpec);
            httpParams.put(KEY_CcIsPlafAvceSpecNegoc, st_CcIsPlafAvceSpecNegoc);
            httpParams.put(KEY_CcDureeMaxAvceSpec, st_CcDureeMaxAvceSpec);
            httpParams.put(KEY_CcIsTxIntAvceSpecDegressif, CcIsTxIntAvceSpecDegressif);
            httpParams.put(KEY_CcModeCalcInteretAvceSpec, CcModeCalcInteretAvceSpec);

            httpParams.put(KEY_CcNatTauxIntAvceSpec, st_CcNatTauxIntAvceSpec);
            httpParams.put(KEY_CcTauxIntAvceSpec, st_CcTauxIntAvceSpec);
            httpParams.put(KEY_CcBaseTxIntAvceSpec, baseTxIntAvceSpec);
            httpParams.put(KEY_CcNaturePerTxInteretAvceSpec, CcNaturePerTxInteretAvceSpec);
            httpParams.put(KEY_CcIsTxInteretAvceSpecJrOn, CcIsTxInteretAvceSpecJrOn);
            httpParams.put(KEY_CcNatureJrTxIntAvceSpecJr, CcNatureJrTxIntAvceSpecJr);
            //Fin Taux d'interet nominal avance speciale

            //Début Pénalité de retard avance speciale
            httpParams.put(KEY_CcIsTxPenRetardAvceSpecDegressif, CcIsTxPenRetardAvceSpecDegressif);
            httpParams.put(KEY_CcModeCalcPenRetardAvceSpec, CcModeCalcPenRetardAvceSpec);
            httpParams.put(KEY_CcNatTauxIntPenAvceSpec, st_CcNatTauxIntPenAvceSpec);
            httpParams.put(KEY_CcTauxIntPenAvceSpec, st_CcTauxIntPenAvceSpec);
            httpParams.put(KEY_CcBaseTxIntPenAvceSpec, baseTxIntPenAvceSpec);

            httpParams.put(KEY_CcPeriod_PenRetAvceSpec, CcPeriod_PenRetAvceSpec);
            httpParams.put(KEY_CcIsTxIntJrOn_PenRetAvceSpe, CcIsTxIntJrOn_PenRetAvceSpe);
            httpParams.put(KEY_CcNatJrTxIntJr_PenRetAvceSpe, CcNatJrTxIntJr_PenRetAvceSpe);
            //Fin Pénalité de retard avance speciale

            //Début Interêt de retard avance speciale
            httpParams.put(KEY_CcIsTxIntRetardAvceSpecDegressif, CcIsTxIntRetardAvceSpecDegressif);
            httpParams.put(KEY_CcModeCalcIntRetardAvceSpec, CcModeCalcIntRetardAvceSpec);
            httpParams.put(KEY_CcNatureTxInt_IntRetAvceSpec, st_CcNatureTxInt_IntRetAvceSpec);
            httpParams.put(KEY_CcTauxInt_IntRetAvceSpec, st_CcTauxInt_IntRetAvceSpec);
            httpParams.put(KEY_CcBasexInt_IntRetAvceSpec, st_CcBasexInt_IntRetAvceSpec);
            httpParams.put(KEY_CcPeriod_IntRetAvceSpec, CcPeriod_IntRetAvceSpec);
            httpParams.put(KEY_CcIsTxIntJrOn_IntRetAvceSpe, CcIsTxIntJrOn_IntRetAvceSpe);
            httpParams.put(KEY_CcNatJrTxIntJr_IntRetAvceSpe, CcNatJrTxIntJr_IntRetAvceSpe);
            //Fin Interêt de retard avance speciale
            httpParams.put(KEY_CC_CAISSE_NUMERO, String.valueOf(MyData.CAISSE_ID));



            httpParams.put(KEY_CC_PLAGE_TAUX_INTERET_DECOUVERT_DEBUT, tabPlageDebutCTP);
            httpParams.put(KEY_CC_PLAGE_TAUX_INTERET_DECOUVERT_FIN, tabPlageFinCTP);
            httpParams.put(KEY_CC_PLAGE_TAUX_INTERET_DECOUVERT_VALEUR, tabPlageValeurCTP);
            httpParams.put(KEY_CC_PLAGE_TAUX_INTERET_DECOUVERT_BASE, tabPlageBaseCTP);
            httpParams.put(KEY_CC_PLAGE_TAUX_INTERET_DECOUVERT_NATURE, tabPlageNatureCTP);

            httpParams.put(KEY_CC_PLAGE_TAUX_CALCUL_AGIO_DEBUT, tabPlageDebutCAP);
            httpParams.put(KEY_CC_PLAGE_TAUX_CALCUL_AGIO_FIN, tabPlageFinCAP);
            httpParams.put(KEY_CC_PLAGE_TAUX_CALCUL_AGIO_VALEUR, tabPlageValeurCAP);
            httpParams.put(KEY_CC_PLAGE_TAUX_CALCUL_AGIO_BASE, tabPlageBaseCAP);
            httpParams.put(KEY_CC_PLAGE_TAUX_CALCUL_AGIO_NATURE, tabPlageNatureCAP);

            httpParams.put(KEY_CC_PLAGE_TAUX_COMMISSION_DEBUT, tabPlageDebutTCP);
            httpParams.put(KEY_CC_PLAGE_TAUX_COMMISSION_FIN, tabPlageFinTCP);
            httpParams.put(KEY_CC_PLAGE_TAUX_COMMISSION_VALEUR, tabPlageValeurTCP);
            httpParams.put(KEY_CC_PLAGE_TAUX_COMMISSION_BASE, tabPlageBaseTCP);
            httpParams.put(KEY_CC_PLAGE_TAUX_COMMISSION_NATURE, tabPlageNatureTCP);

            httpParams.put(KEY_CC_PLAGE_TAUX_INT_AVANC_SPEC_DEBUT, tabPlageDebutTAS);
            httpParams.put(KEY_CC_PLAGE_TAUX_INT_AVANC_SPEC_FIN, tabPlageFinTAS);
            httpParams.put(KEY_CC_PLAGE_TAUX_INT_AVANC_SPEC_VALEUR, tabPlageValeurTAS);
            httpParams.put(KEY_CC_PLAGE_TAUX_INT_AVANC_SPEC_BASE, tabPlageBaseTAS);
            httpParams.put(KEY_CC_PLAGE_TAUX_INT_AVANC_SPEC_NATURE, tabPlageNatureTAS);

            httpParams.put(KEY_CC_PLAGE_TAUX_INT_PENAL_AVANC_SPEC_DEBUT, tabPlageDebutPAS);
            httpParams.put(KEY_CC_PLAGE_TAUX_INT_PENAL_AVANC_SPEC_FIN, tabPlageFinPAS);
            httpParams.put(KEY_CC_PLAGE_TAUX_INT_PENAL_AVANC_SPEC_VALEUR, tabPlageValeurPAS);
            httpParams.put(KEY_CC_PLAGE_TAUX_INT_PENAL_AVANC_SPEC_BASE, tabPlageBasePAS);
            httpParams.put(KEY_CC_PLAGE_TAUX_INT_PENAL_AVANC_SPEC_NATURE, tabPlageNaturePAS);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_cpte_courant_new.php", "POST", httpParams);
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
                        Toast.makeText(CreateProduitCpteCourant.this,
                                "Compte courant Ajouté", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CreateProduitCpteCourant.this,
                                "Some error occurred while adding Compte courant",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}