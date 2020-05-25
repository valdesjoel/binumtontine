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
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateProduitCpteCourant extends AppCompatActivity implements SERVER_ADDRESS {
    private static String STRING_EMPTY = "";
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_CPTE_COURANT_ID = "CcNumero";




    public static String cpteCourantId;
    private TextView headerCCTextView;


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

    private static final String KEY_CcCode = "CcCode";
    private static final String KEY_CcLibelle = "CcLibelle";
    private static final String KEY_CcIsDecouvOn = "CcIsDecouvOn";
    private static final String KEY_CcMtMaxDecouv = "CcMtMaxDecouv";
    private static final String KEY_CcIsMaxDecouvNeg = "CcIsMaxDecouvNeg";
    private static final String KEY_CcNatureTxIntDecouv = "CcNatureTxIntDecouv";
    private static final String KEY_CcValTxIntDecouv = "CcValTxIntDecouv";
    private static final String KEY_CcBaseTxIntDecouv = "CcBaseTxIntDecouv";
    private static final String KEY_CcPlageTxIntDecouvFrom = "CcPlageTxIntDecouvFrom";
    private static final String KEY_CcPlageTxIntDecouvTo = "CcPlageTxIntDecouvTo";
    private static final String KEY_CcDureeMaxDecouv = "CcDureeMaxDecouv";
    private static final String KEY_CcNatureTypDureDecouv = "CcNatureTypDureDecouv";
    private static final String KEY_CcNatureTxMtAgio = "CcNatureTxMtAgio";
    private static final String KEY_CcValTxMtAgio = "CcValTxMtAgio";
    private static final String KEY_CcPlageTxMtAgioFrom = "CcPlageTxMtAgioFrom";
    private static final String KEY_CcPlageTxMtAgioTo = "CcPlageTxMtAgioTo";
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
    private static final String KEY_CcPlageTxCommMvmFrom = "CcPlageTxCommMvmFrom";
    private static final String KEY_CcPlageTxCommMvmTo = "CcPlageTxCommMvmTo";
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
    /*Update compte courant END*/


    private EditText CcCode,CcLibelle,CcMtMaxDecouv,CcNatureTxIntDecouv,CcValTxIntDecouv,
            CcPlageTxIntDecouvFrom,CcPlageTxIntDecouvTo,CcDureeMaxDecouv,CcNatureTypDureDecouv,
            CcNatureTxMtAgio,CcValTxMtAgio,CcPlageTxMtAgioFrom,CcPlageTxMtAgioTo,CcNatBaseAgio,
            CcNbPagesCheqM1,CcPrixVteCheqM1,CcNbPagesCheqM2,CcPrixVteCheqM2,CcNbPagesCheqM3,
            CcPrixVteCheqM3,CcDureeValidCheq,CcNbMinSignatChq,CcNatTxComm,CcValTxCommMvm,
            CcPlageTxCommMvmFrom,CcPlageTxCommMvmTo, CcMtPlafondAvceSpec,CcTauxIntAvceSpec,CcTauxIntPenAvceSpec;
    private Switch CcIsDecouvOn,CcIsMaxDecouvNeg,CcIsChequierM1On,CcIsChequierM2On,CcIsChequierM3On,
            CcIsTxCommMvmOper,CcIsAvanceSpecialOn, CcIsPlafAvceSpecNegoc, CcIsTxIntDegressif;
    private String st_CcCode,st_CcLibelle,st_CcMtMaxDecouv,
            st_CcNatureTxIntDecouv,st_CcValTxIntDecouv,st_CcPlageTxIntDecouvFrom,st_CcPlageTxIntDecouvTo,
            st_CcDureeMaxDecouv,st_CcNatureTypDureDecouv,st_CcNatureTxMtAgio,st_CcValTxMtAgio,
            st_CcPlageTxMtAgioFrom,st_CcPlageTxMtAgioTo,st_CcNatBaseAgio,
            st_CcNbPagesCheqM1,st_CcPrixVteCheqM1,st_CcNbPagesCheqM2,
            st_CcPrixVteCheqM2,st_CcNbPagesCheqM3,st_CcPrixVteCheqM3,
            st_CcDureeValidCheq,st_CcNbMinSignatChq,st_CcNatTxComm,st_CcValTxCommMvm,
            st_CcPlageTxCommMvmFrom,st_CcPlageTxCommMvmTo,st_CcMtPlafondAvceSpec, st_CcNatTauxIntAvceSpec,
            st_CcTauxIntAvceSpec, st_CcNatTauxIntPenAvceSpec,st_CcTauxIntPenAvceSpec;

    private Boolean st_CcIsDecouvOn, st_CcIsMaxDecouvNeg,st_CcIsChequierM1On,st_CcIsChequierM2On,
            st_CcIsChequierM3On,st_CcIsTxCommMvmOper,st_CcIsAvanceSpecialOn,st_CcIsPlafAvceSpecNegoc, st_CcIsTxIntDegressif;





    private RadioButton CcNatureTxIntDecouvFixe,CcNatureTxIntDecouvTaux,CcNatureTxIntDecouvPlage;
    private RadioButton CcNatureTypDureDecouvJour,CcNatureTypDureDecouvSemaine,CcNatureTypDureDecouvMois;
    private RadioButton CcNatureTxMtAgioTaux, CcNatureTxMtAgioMontant, CcNatureTxMtAgioPlage;
    private RadioButton CcNatTxCommFixe, CcNatTxCommMontant, CcNatTxCommPlage;
    private RadioButton CcNatTauxIntAvceSpecFixe,CcNatTauxIntAvceSpecTaux,CcNatTauxIntAvceSpecPlage;
    private RadioButton CcNatTauxIntPenAvceSpecFixe,CcNatTauxIntPenAvceSpecTaux,CcNatTauxIntPenAvceSpecPlage;
    private JRSpinner mySpinnerBaseTxIntDecouv; //pour gérer le spinner contenant les bases du Tx Int Decouvert
    private JRSpinner mySpinnerBaseTxMtAgio; //pour gérer le spinner contenant les bases taux
    private JRSpinner mySpinnerBaseTxCommMvm; //pour gérer le spinner contenant les bases taux
    private JRSpinner mySpinnerCcBaseTxIntAvceSpec; //pour gérer le spinner contenant les bases du Tx Int avance spéciale
    private JRSpinner mySpinnerCcBaseTxIntPenAvceSpec; //pour gérer le spinner contenant les bases du Tx Int penalités avance spéciale


    private TextInputLayout input_layout_CcValTxMtAgio;
    private TextInputLayout input_layout_CcBaseTxMtAgio;
    private TextInputLayout input_layout_CcBaseCalculAgiosCC;
    private TextInputLayout input_layout_CcBaseTxCommMvmCC;
    private TextInputLayout input_layout_CcValTxIntDecouv;
    private TextInputLayout input_layout_CcValTxCommMvm,
            input_layout_CcTauxIntAvceSpec, input_layout_CcTauxIntPenAvceSpec ,
            input_layout_CcBaseTxIntAvceSpec, input_layout_CcBaseTxIntPenAvceSpec;
    private LinearLayout bloc_cc1;
    private LinearLayout bloc_cc2;
    private LinearLayout bloc_cc3;
    private LinearLayout bloc_cc4;
    private LinearLayout bloc_cc5;
    private LinearLayout blk_plage_cc;
    private LinearLayout blk_plage_agios_cc;
    private LinearLayout blk_plage_CcValTxCommMvm;
    private LinearLayout bloc_cc_avance_speciale;
    private TextView tv_plageTxMtAgio,tv_plageCcTauxIntAvceSpec, tv_plageCcTauxIntPenAvceSpec;
    public static ArrayList<ModelPlageData> plageDataListCTP = new ArrayList<ModelPlageData>(); //to manage plageData
    private TextView tv_plageCalculAgiosCC;
    public static ArrayList<ModelPlageData> plageDataListCAP = new ArrayList<ModelPlageData>(); //to manage plageData
    private TextView tv_plageTxCommMvmCC;
    public static ArrayList<ModelPlageData> plageDataListTCP = new ArrayList<ModelPlageData>(); //to manage plageData

    public static ArrayList<ModelPlageData> plageDataListTAS = new ArrayList<ModelPlageData>(); //to manage plageData
    public static ArrayList<ModelPlageData> plageDataListPAS = new ArrayList<ModelPlageData>(); //to manage plageData
    private Button addButton;
    private String baseCcTxIntDecouv;
    private String baseCcTxCommMvm;

    private String baseTxIntAvceSpec;
    private String baseTxIntPenAvceSpec;

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



    //END




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.fragment_param_produit_eav);
        setContentView(R.layout.activity_cpte_courant);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_produit_eav);
        setSupportActionBar(toolbar);
        setToolbarTitle(); */
        plageDataList = new ArrayList<>();
        Intent intent = getIntent();
        headerCCTextView = (TextView) findViewById(R.id.header_cpte_courant);
        headerCCTextView.setText("Mise à jour Compte Courant");

        tv_header_produit = (TextView) findViewById(R.id.header_produit_cc);

        tv_header_produit.setText("Produit Compte Courant\n"+"Caisse: "+ MyData.CAISSE_NAME);


        bloc_cc1 = (LinearLayout) findViewById(R.id.ll_bloc_cc1);
        bloc_cc2 = (LinearLayout) findViewById(R.id.ll_bloc_cc2);
        bloc_cc3 = (LinearLayout) findViewById(R.id.ll_bloc_cc3);
        bloc_cc4 = (LinearLayout) findViewById(R.id.ll_bloc_cc4);
        bloc_cc5 = (LinearLayout) findViewById(R.id.ll_bloc_cc5);
        blk_plage_cc = (LinearLayout) findViewById(R.id.blk_plage_cc);
        blk_plage_agios_cc = (LinearLayout) findViewById(R.id.blk_plage_agios_cc);
        blk_plage_CcValTxCommMvm = (LinearLayout) findViewById(R.id.blk_plage_CcValTxCommMvm);
        input_layout_CcValTxMtAgio = (TextInputLayout) findViewById(R.id.input_layout_CcValTxMtAgio);
        input_layout_CcBaseTxMtAgio = (TextInputLayout) findViewById(R.id.input_layout_BaseCcValTxMtAgio);
        input_layout_CcBaseCalculAgiosCC = (TextInputLayout) findViewById(R.id.input_layout_txtNatureBaseCalculAgiosCC_new);
        input_layout_CcBaseTxCommMvmCC = (TextInputLayout) findViewById(R.id.input_layout_BaseCcTxCommMvm);
        input_layout_CcValTxIntDecouv = (TextInputLayout) findViewById(R.id.input_layout_CcValTxIntDecouv);
        input_layout_CcValTxCommMvm = (TextInputLayout) findViewById(R.id.input_layout_CcValTxCommMvm);
        bloc_cc_avance_speciale = (LinearLayout) findViewById(R.id.ll_bloc_ccAvancement);
        input_layout_CcTauxIntAvceSpec = (TextInputLayout) findViewById(R.id.input_layout_CcTauxIntAvceSpec);
        input_layout_CcTauxIntPenAvceSpec = (TextInputLayout) findViewById(R.id.input_layout_CcTauxIntPenAvceSpec);
        input_layout_CcBaseTxIntAvceSpec = (TextInputLayout) findViewById(R.id.input_layout_CcBaseTxIntAvceSpec);
        input_layout_CcBaseTxIntPenAvceSpec = (TextInputLayout) findViewById(R.id.input_layout_CcBaseTxIntPenAvceSpec);
        CcIsAvanceSpecialOn= (Switch) findViewById(R.id.SwitchCcIsAvanceSpecialOn);
        CcMtPlafondAvceSpec= (EditText) findViewById(R.id.input_txt_CcMtPlafondAvceSpec);
        CcIsMaxDecouvNeg= (Switch) findViewById(R.id.SwitchPlafondDecouvertNegociableCC);
        CcNatTauxIntAvceSpecFixe = (RadioButton) findViewById(R.id.rb_CcNatTauxIntAvceSpec_Fixe);
        CcNatTauxIntAvceSpecTaux = (RadioButton) findViewById(R.id.rb_CcNatTauxIntAvceSpec_Taux);
        CcNatTauxIntAvceSpecPlage = (RadioButton) findViewById(R.id.rb_CcNatTauxIntAvceSpec_Plage);

        CcNatTauxIntPenAvceSpecFixe = (RadioButton) findViewById(R.id.rb_CcNatTauxIntPenAvceSpec_Fixe);
        CcNatTauxIntPenAvceSpecTaux = (RadioButton) findViewById(R.id.rb_CcNatTauxIntPenAvceSpec_Taux);
        CcNatTauxIntPenAvceSpecPlage = (RadioButton) findViewById(R.id.rb_CcNatTauxIntPenAvceSpec_Plage);
        CcTauxIntAvceSpec= (EditText) findViewById(R.id.input_txt_CcTauxIntAvceSpec);
        CcTauxIntPenAvceSpec= (EditText) findViewById(R.id.input_txt_CcTauxIntPenAvceSpec);
        mySpinnerCcBaseTxIntAvceSpec = (JRSpinner)findViewById(R.id.spn_my_spinner_base_CcBaseTxIntAvceSpec);
        mySpinnerCcBaseTxIntPenAvceSpec = (JRSpinner)findViewById(R.id.spn_my_spinner_base_CcBaseTxIntPenAvceSpec);
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
        tv_plageCcTauxIntAvceSpec= (TextView) findViewById(R.id.tv_plage_CcTauxIntAvceSpec);
        tv_plageCcTauxIntPenAvceSpec= (TextView) findViewById(R.id.tv_plage_CcTauxIntPenAvceSpec);
        CcIsPlafAvceSpecNegoc=(Switch) findViewById(R.id.SwitchCcIsPlafAvceSpecNegoc);
        CcIsTxIntDegressif=(Switch) findViewById(R.id.SwitchCcIsTxIntDegressif);


        CcCode = (EditText) findViewById(R.id.input_txtCodeCC);
        CcLibelle = (EditText) findViewById(R.id.input_txt_LibelleCC);
        CcIsDecouvOn= (Switch) findViewById(R.id.SwitchAutoriserDecouvertCC);
        CcMtMaxDecouv= (EditText) findViewById(R.id.input_txt_MaxMtDecouvertAutoriseCC);
        CcIsMaxDecouvNeg= (Switch) findViewById(R.id.SwitchPlafondDecouvertNegociableCC);

        //CcNatureTxIntDecouv= (EditText) findViewById(R.id.SwitchPlafondDecouvertNegociableCC);
        CcNatureTxIntDecouvFixe = (RadioButton) findViewById(R.id.rb_CcNatureTxIntDecouv_Fixe);
        CcNatureTxIntDecouvTaux = (RadioButton) findViewById(R.id.rb_CcNatureTxIntDecouv_Taux);
        CcNatureTxIntDecouvPlage = (RadioButton) findViewById(R.id.rb_CcNatureTxIntDecouv_Plage);

        CcValTxIntDecouv= (EditText) findViewById(R.id.input_txt_CcValTxMtAgio);
        mySpinnerBaseTxIntDecouv = (JRSpinner)findViewById(R.id.spn_my_spinner_base_CcValTxMtAgio);
        mySpinnerBaseTxMtAgio = (JRSpinner)findViewById(R.id.spn_my_spinner_BaseCalculAgiosCC);
        mySpinnerBaseTxCommMvm = (JRSpinner)findViewById(R.id.spn_my_spinner_base_CcTxCommMvm);
        mySpinnerBaseTxIntDecouv.setItems(getResources().getStringArray(R.array.array_base_taux_cpte_courant)); //this is important, you must set it to set the item list
        mySpinnerBaseTxIntDecouv.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        mySpinnerBaseTxIntDecouv.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        mySpinnerBaseTxIntDecouv.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });
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
        tv_plageCalculAgiosCC= (TextView) findViewById(R.id.tv_plage_CalculAgiosCC);
        tv_plageTxCommMvmCC= (TextView) findViewById(R.id.tv_plage_CcTxCommMvm);
        CcPlageTxIntDecouvFrom= (EditText) findViewById(R.id.txt_CcValTxIntDecouvFrom);
        CcPlageTxIntDecouvTo= (EditText) findViewById(R.id.txt_CcValTxIntDecouvTo);
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
        CcPlageTxMtAgioFrom=(EditText) findViewById(R.id.txt_CcValTxMtAgioFrom);
        CcPlageTxMtAgioTo=(EditText) findViewById(R.id.txt_CcValTxMtAgioTo);
//                CcNatBaseAgio=(EditText) findViewById(R.id.input_txt_NatureBaseCalculAgiosCC);
        CcIsChequierM1On=(Switch) findViewById(R.id.Switch_txtDisponibiliteChequier1CC);
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
        CcPlageTxCommMvmFrom=(EditText) findViewById(R.id.txt_CcValTxCommMvm_From);
        CcPlageTxCommMvmTo=(EditText) findViewById(R.id.txt_CcValTxCommMvm_To);


        cpteCourantId = intent.getStringExtra(KEY_CPTE_COURANT_ID);
        new FetchCpteCourantDetailsAsyncTask().execute();
        deleteButton = (Button) findViewById(R.id.btn_delete_cc);
        deleteButton.setVisibility(View.VISIBLE);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete();
            }
        });
        updateButton = (Button) findViewById(R.id.btn_save_cc);
        cancelButton = (Button) findViewById(R.id.btn_clean);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

                    finish();

                } else {
                    Toast.makeText(UpdateProduitCpteCourant.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    updateCpteCourant();

                } else {
                    Toast.makeText(UpdateProduitCpteCourant.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        tv_plageTxMtAgio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux d'intérêt découvert";
                    ListPlageDataCTPActivity.IS_TO_CREATE_OR_TO_UPDATE = false;
                    Intent i = new Intent(UpdateProduitCpteCourant.this, ListPlageDataCTPActivity.class);
                    i.putExtra(KEY_CPTE_COURANT_ID, cpteCourantId);

                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(UpdateProduitCpteCourant.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        tv_plageCalculAgiosCC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux des agios";
                    ListPlageDataCAPActivity.IS_TO_CREATE_OR_TO_UPDATE = false;
                    Intent i = new Intent(UpdateProduitCpteCourant.this,ListPlageDataCAPActivity.class);
                    i.putExtra(KEY_CPTE_COURANT_ID, cpteCourantId);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(UpdateProduitCpteCourant.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        tv_plageTxCommMvmCC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux de commission";
                    ListPlageDataTCPActivity.IS_TO_CREATE_OR_TO_UPDATE = false;
                    Intent i = new Intent(UpdateProduitCpteCourant.this,ListPlageDataTCPActivity.class);
                    i.putExtra(KEY_CPTE_COURANT_ID, cpteCourantId);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(UpdateProduitCpteCourant.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

        tv_plageCcTauxIntAvceSpec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Tx int avance spéciale";
                    ListPlageDataTASActivity.IS_TO_CREATE_OR_TO_UPDATE = false;
                    Intent i = new Intent(UpdateProduitCpteCourant.this,ListPlageDataTASActivity.class);
                    i.putExtra(KEY_CPTE_COURANT_ID, cpteCourantId);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(UpdateProduitCpteCourant.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        tv_plageCcTauxIntPenAvceSpec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Tx int pénalité A.S";
                    ListPlageDataPASActivity.IS_TO_CREATE_OR_TO_UPDATE = false;
                    Intent i = new Intent(UpdateProduitCpteCourant.this,ListPlageDataPASActivity.class);
                    i.putExtra(KEY_CPTE_COURANT_ID, cpteCourantId);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(UpdateProduitCpteCourant.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });



    }

    public void onSwitchButtonClicked(View view) {
        boolean checked1 = ((Switch) view).isChecked();
        String str = "";
        // Check which checkbox was clicked
        switch (view.getId()) {
//
            case R.id.SwitchAutoriserDecouvertCC:
                if (CcIsDecouvOn.isChecked()) {
                    str = checked1 ? "Minimum en compte obligatoire" : "le minimum en compte n'est pas obligatoire";

                    bloc_cc1.setVisibility(View.VISIBLE);
                    //onRadioButtonClicked(rbCrNatFrEtudDossFixe);
                } else {
                    bloc_cc1.setVisibility(View.GONE);
                }

                break;

            case R.id.SwitchCcIsAvanceSpecialOn:
                if (CcIsAvanceSpecialOn.isChecked()) {
                    str = checked1 ? "Minimum en compte obligatoire" : "le minimum en compte n'est pas obligatoire";

                    bloc_cc_avance_speciale.setVisibility(View.VISIBLE);
                    //onRadioButtonClicked(rbCrNatFrEtudDossFixe);
                } else {
                    bloc_cc_avance_speciale.setVisibility(View.GONE);
                }

                break;
            case R.id.Switch_txtDisponibiliteChequier1CC:
                if (CcIsChequierM1On.isChecked()) {
                    str = checked1 ? "Minimum en compte obligatoire" : "le minimum en compte n'est pas obligatoire";

                    bloc_cc2.setVisibility(View.VISIBLE);
                    //onRadioButtonClicked(rbCrNatFrEtudDossFixe);
                } else {
                    bloc_cc2.setVisibility(View.GONE);
                }

                break;
            case R.id.Switch_txtDisponibiliteChequier2CC:
                if (CcIsChequierM2On.isChecked()) {
                    str = checked1 ? "Minimum en compte obligatoire" : "le minimum en compte n'est pas obligatoire";

                    bloc_cc3.setVisibility(View.VISIBLE);
                    //onRadioButtonClicked(rbCrNatFrEtudDossFixe);
                } else {
                    bloc_cc3.setVisibility(View.GONE);
                }

                break;
            case R.id.Switch_txtDisponibiliteChequier3CC:
                if (CcIsChequierM3On.isChecked()) {
                    str = checked1 ? "Minimum en compte obligatoire" : "le minimum en compte n'est pas obligatoire";

                    bloc_cc4.setVisibility(View.VISIBLE);
                    //onRadioButtonClicked(rbCrNatFrEtudDossFixe);
                } else {
                    bloc_cc4.setVisibility(View.GONE);
                }

                break;
            case R.id.SwitchActiverTxCommissionCC:
                if (CcIsTxCommMvmOper.isChecked()) {
                    str = checked1 ? "Minimum en compte obligatoire" : "le minimum en compte n'est pas obligatoire";

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
            case R.id.rb_CcNatTauxIntPenAvceSpec_Fixe:
                if (CcNatTauxIntPenAvceSpecFixe.isChecked()) {
                    // ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    st_CcNatTauxIntPenAvceSpec ="F";
                    str = checked1?"Type Fixe Selected":"Type Fixe Deselected";
                    CcTauxIntPenAvceSpec.setHint("Montant");
                    tv_plageCcTauxIntPenAvceSpec.setVisibility(View.GONE);
                    input_layout_CcTauxIntPenAvceSpec.setVisibility(View.VISIBLE);
                    input_layout_CcBaseTxIntPenAvceSpec.setVisibility(View.GONE);

                }

                break;

            case R.id.rb_CcNatTauxIntPenAvceSpec_Taux:
                if (CcNatTauxIntPenAvceSpecTaux.isChecked()) {
                    // ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    st_CcNatTauxIntPenAvceSpec ="T";
                    str = checked1?"Type Taux Selected":"Type Taux Deselected";
                    CcTauxIntPenAvceSpec.setHint("Taux");
                    tv_plageCcTauxIntPenAvceSpec.setVisibility(View.GONE);
                    input_layout_CcTauxIntPenAvceSpec.setVisibility(View.VISIBLE);
                    input_layout_CcBaseTxIntPenAvceSpec.setVisibility(View.VISIBLE);

                }

                break;

            case R.id.rb_CcNatTauxIntPenAvceSpec_Plage:
                if (CcNatTauxIntPenAvceSpecPlage.isChecked()){
                    // ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    str = checked1?"Type Plage Selected":"Type Plage Deselected";
                    st_CcNatTauxIntPenAvceSpec ="P";
                    tv_plageCcTauxIntPenAvceSpec.setVisibility(View.VISIBLE);
                    input_layout_CcTauxIntPenAvceSpec.setVisibility(View.GONE);
                    input_layout_CcBaseTxIntPenAvceSpec.setVisibility(View.GONE);
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
                    blk_plage_cc.setVisibility(View.GONE);
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
                    blk_plage_cc.setVisibility(View.GONE);
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
                    blk_plage_cc.setVisibility(View.GONE);
                    tv_plageTxMtAgio.setVisibility(View.VISIBLE);
                    input_layout_CcValTxMtAgio.setVisibility(View.GONE);
                    input_layout_CcBaseTxMtAgio.setVisibility(View.GONE);
                }


                break;
            case R.id.rb_CcNatureTypDureDecouv_jour:
                if (CcNatureTypDureDecouvJour.isChecked()){
                    str = checked1?"Type Jour Selected":"Type Jour Deselected";
                    st_CcNatureTypDureDecouv ="J";
                   /* blk_plage_cc.setVisibility(View.VISIBLE);
                    input_layout_CcValTxMtAgio.setVisibility(View.GONE);*/
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
                    blk_plage_agios_cc.setVisibility(View.GONE);
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
                    blk_plage_agios_cc.setVisibility(View.GONE);
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
                    blk_plage_agios_cc.setVisibility(View.GONE);
                    tv_plageCalculAgiosCC.setVisibility(View.VISIBLE);
                    input_layout_CcValTxIntDecouv.setVisibility(View.GONE);
                    input_layout_CcBaseCalculAgiosCC.setVisibility(View.GONE);
                }


                break;
            case R.id.rb_CcNatTxComm_fixe:
                if (CcNatTxCommFixe.isChecked()){
                    str = checked1?"Type Fixe Selected":"Type Fixe Deselected";
                    st_CcNatTxComm ="T";
                    blk_plage_CcValTxCommMvm.setVisibility(View.GONE);
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
                    blk_plage_CcValTxCommMvm.setVisibility(View.GONE);
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
                    blk_plage_CcValTxCommMvm.setVisibility(View.GONE);// a supprimer tout le bloc car inutile
                    tv_plageTxCommMvmCC.setVisibility(View.VISIBLE);
                    input_layout_CcValTxCommMvm.setVisibility(View.GONE);
                    input_layout_CcBaseTxCommMvmCC.setVisibility(View.GONE);
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
     * Fetches single movie details from the server
     */
    private class FetchCpteCourantDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateProduitCpteCourant.this);
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
            httpParams.put(KEY_CPTE_COURANT_ID, cpteCourantId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_cpte_courant_details.php", "GET", httpParams);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject cpteCourant;
                if (success == 1) {
                    //Parse the JSON response
                    cpteCourant = jsonObject.getJSONObject(KEY_DATA);

                    st_CcCode = cpteCourant.getString(KEY_CcCode);
                    st_CcLibelle = cpteCourant.getString(KEY_CcLibelle);
                    st_CcIsDecouvOn = Boolean.parseBoolean(cpteCourant.getString(KEY_CcIsDecouvOn));
                    st_CcMtMaxDecouv = cpteCourant.getString(KEY_CcMtMaxDecouv);
                    st_CcIsMaxDecouvNeg = Boolean.parseBoolean(cpteCourant.getString(KEY_CcIsMaxDecouvNeg));
                    st_CcNatureTxIntDecouv = cpteCourant.getString(KEY_CcNatureTxIntDecouv);
                    st_CcValTxIntDecouv = cpteCourant.getString(KEY_CcValTxIntDecouv);
                    st_CcPlageTxIntDecouvFrom = cpteCourant.getString(KEY_CcPlageTxIntDecouvFrom);
                    st_CcPlageTxIntDecouvTo = cpteCourant.getString(KEY_CcPlageTxIntDecouvTo);
                    st_CcDureeMaxDecouv = cpteCourant.getString(KEY_CcDureeMaxDecouv);
                    st_CcNatureTypDureDecouv = cpteCourant.getString(KEY_CcNatureTypDureDecouv);
                    st_CcNatureTxMtAgio = cpteCourant.getString(KEY_CcNatureTxMtAgio);
                    st_CcValTxMtAgio = cpteCourant.getString(KEY_CcValTxMtAgio);
                    st_CcPlageTxMtAgioFrom = cpteCourant.getString(KEY_CcPlageTxMtAgioFrom);
                    st_CcPlageTxMtAgioTo = cpteCourant.getString(KEY_CcPlageTxMtAgioTo);
                    st_CcNatBaseAgio = cpteCourant.getString(KEY_CcNatBaseAgio);
                    st_CcIsChequierM1On = Boolean.parseBoolean(cpteCourant.getString(KEY_CcIsChequierM1On));
                    st_CcNbPagesCheqM1 = cpteCourant.getString(KEY_CcNbPagesCheqM1);
                    st_CcPrixVteCheqM1 = cpteCourant.getString(KEY_CcPrixVteCheqM1);
                    st_CcIsChequierM2On = Boolean.parseBoolean(cpteCourant.getString(KEY_CcIsChequierM2On));
                    st_CcNbPagesCheqM2 = cpteCourant.getString(KEY_CcNbPagesCheqM2);
                    st_CcPrixVteCheqM2 = cpteCourant.getString(KEY_CcPrixVteCheqM2);
                    st_CcIsChequierM3On = Boolean.parseBoolean(cpteCourant.getString(KEY_CcIsChequierM3On));
                    st_CcNbPagesCheqM3 = cpteCourant.getString(KEY_CcNbPagesCheqM3);
                    st_CcPrixVteCheqM3 = cpteCourant.getString(KEY_CcPrixVteCheqM3);
                    st_CcDureeValidCheq = cpteCourant.getString(KEY_CcDureeValidCheq);
                    st_CcNbMinSignatChq = cpteCourant.getString(KEY_CcNbMinSignatChq);
                    st_CcIsTxCommMvmOper = Boolean.parseBoolean(cpteCourant.getString(KEY_CcIsTxCommMvmOper));
                    st_CcNatTxComm = cpteCourant.getString(KEY_CcNatTxComm);
                    st_CcValTxCommMvm = cpteCourant.getString(KEY_CcValTxCommMvm);
                    st_CcPlageTxCommMvmFrom = cpteCourant.getString(KEY_CcPlageTxCommMvmFrom);
                    st_CcPlageTxCommMvmTo = cpteCourant.getString(KEY_CcPlageTxCommMvmTo);

                    st_CcIsAvanceSpecialOn = Boolean.parseBoolean(cpteCourant.getString(KEY_CcIsAvanceSpecialOn));
                    st_CcIsTxIntDegressif = Boolean.parseBoolean(cpteCourant.getString(KEY_CcIsTxIntDegressif));
                    st_CcMtPlafondAvceSpec = cpteCourant.getString(KEY_CcMtPlafondAvceSpec);
                    st_CcIsPlafAvceSpecNegoc = Boolean.parseBoolean(cpteCourant.getString(KEY_CcIsPlafAvceSpecNegoc));
                    st_CcNatTauxIntAvceSpec = cpteCourant.getString(KEY_CcNatTauxIntAvceSpec);
                    st_CcTauxIntAvceSpec = cpteCourant.getString(KEY_CcTauxIntAvceSpec);
                    baseTxIntAvceSpec = cpteCourant.getString(KEY_CcBaseTxIntAvceSpec);
                    st_CcNatTauxIntPenAvceSpec = cpteCourant.getString(KEY_CcNatTauxIntPenAvceSpec);
                    st_CcTauxIntPenAvceSpec = cpteCourant.getString(KEY_CcTauxIntPenAvceSpec);
                    baseTxIntPenAvceSpec = cpteCourant.getString(KEY_CcBaseTxIntPenAvceSpec);


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



                    CcIsAvanceSpecialOn.setChecked(st_CcIsAvanceSpecialOn);
                    onSwitchButtonClicked(CcIsAvanceSpecialOn);
                    CcMtPlafondAvceSpec.setText(st_CcMtPlafondAvceSpec);
                    CcIsPlafAvceSpecNegoc.setChecked(st_CcIsPlafAvceSpecNegoc);
                    CcIsTxIntDegressif.setChecked(st_CcIsTxIntDegressif);
                    onSwitchButtonClicked(CcIsPlafAvceSpecNegoc);
                    if (st_CcNatTauxIntAvceSpec.equals("F")){
                        CcNatTauxIntAvceSpecFixe.setChecked(true);
                        onRadioButtonClicked(CcNatTauxIntAvceSpecFixe);
                    }else if (st_CcNatTauxIntAvceSpec.equals("T")){
                        CcNatTauxIntAvceSpecTaux.setChecked(true);
                        onRadioButtonClicked(CcNatTauxIntAvceSpecTaux);
                    }else if (st_CcNatTauxIntAvceSpec.equals("P")){
                        CcNatTauxIntAvceSpecPlage.setChecked(true);
                        onRadioButtonClicked(CcNatTauxIntAvceSpecPlage);
                    }
                    CcTauxIntAvceSpec.setText(st_CcTauxIntAvceSpec);
                    mySpinnerCcBaseTxIntAvceSpec.setText(baseTxIntAvceSpec);

                    if (st_CcNatTauxIntPenAvceSpec.equals("F")){
                        CcNatTauxIntPenAvceSpecFixe.setChecked(true);
                        onRadioButtonClicked(CcNatTauxIntPenAvceSpecFixe);
                    }else if (st_CcNatTauxIntPenAvceSpec.equals("T")){
                        CcNatTauxIntPenAvceSpecTaux.setChecked(true);
                        onRadioButtonClicked(CcNatTauxIntPenAvceSpecTaux);
                    }else if (st_CcNatTauxIntPenAvceSpec.equals("P")){
                        CcNatTauxIntPenAvceSpecPlage.setChecked(true);
                        onRadioButtonClicked(CcNatTauxIntPenAvceSpecPlage);
                    }
                    CcTauxIntPenAvceSpec.setText(st_CcTauxIntPenAvceSpec);
                    mySpinnerCcBaseTxIntPenAvceSpec.setText(baseTxIntPenAvceSpec);

                    CcCode.setText(st_CcCode);
                    CcLibelle.setText(st_CcLibelle);
                    CcIsDecouvOn.setChecked(st_CcIsDecouvOn);
                    onSwitchButtonClicked(CcIsDecouvOn);
                    CcMtMaxDecouv.setText(st_CcMtMaxDecouv);
                    CcIsMaxDecouvNeg.setChecked(st_CcIsMaxDecouvNeg);
                    onSwitchButtonClicked(CcIsMaxDecouvNeg);
                    if (st_CcNatureTxIntDecouv.equals("F")){
                        CcNatureTxIntDecouvFixe.setChecked(true);
                        onRadioButtonClicked(CcNatureTxIntDecouvFixe);
                    }else if (st_CcNatureTxIntDecouv.equals("T")){
                        CcNatureTxIntDecouvTaux.setChecked(true);
                        onRadioButtonClicked(CcNatureTxIntDecouvTaux);
                    }else if (st_CcNatureTxIntDecouv.equals("P")){
                        CcNatureTxIntDecouvPlage.setChecked(true);
                        onRadioButtonClicked(CcNatureTxIntDecouvPlage);
                    }
                    CcValTxIntDecouv.setText(st_CcValTxIntDecouv);
                    mySpinnerBaseTxIntDecouv.setText(baseCcTxIntDecouv);
                    CcPlageTxIntDecouvFrom.setText(st_CcPlageTxIntDecouvFrom);
                    CcPlageTxIntDecouvTo.setText(st_CcPlageTxIntDecouvTo);
                    CcDureeMaxDecouv.setText(st_CcDureeMaxDecouv);
                    if (st_CcNatureTypDureDecouv.equals("J")){
                        CcNatureTypDureDecouvJour.setChecked(true);
                        onRadioButtonClicked(CcNatureTypDureDecouvJour);
                    }else if (st_CcNatureTypDureDecouv.equals("S")){
                        CcNatureTypDureDecouvSemaine.setChecked(true);
                        onRadioButtonClicked(CcNatureTypDureDecouvSemaine);
                    }else if (st_CcNatureTypDureDecouv.equals("M")){
                        CcNatureTypDureDecouvMois.setChecked(true);
                        onRadioButtonClicked(CcNatureTypDureDecouvMois);
                    }
                    if (st_CcNatureTxMtAgio.equals("T")){
                        CcNatureTxMtAgioTaux.setChecked(true);
                        onRadioButtonClicked(CcNatureTxMtAgioTaux);
                    }else if (st_CcNatureTxMtAgio.equals("M")){
                        CcNatureTxMtAgioMontant.setChecked(true);
                        onRadioButtonClicked(CcNatureTxMtAgioMontant);
                    }else if (st_CcNatureTxMtAgio.equals("P")){
                        CcNatureTxMtAgioPlage.setChecked(true);
                        onRadioButtonClicked(CcNatureTxMtAgioPlage);
                    }

                    CcValTxMtAgio.setText(st_CcValTxMtAgio);
                    mySpinnerBaseTxMtAgio.setText(st_CcNatBaseAgio);
                    CcPlageTxMtAgioFrom.setText(st_CcPlageTxMtAgioFrom);
                    CcPlageTxMtAgioTo.setText(st_CcPlageTxMtAgioTo);
                    CcIsChequierM1On.setChecked(st_CcIsChequierM1On);
                    onSwitchButtonClicked(CcIsChequierM1On);
                    CcNbPagesCheqM1.setText(st_CcNbPagesCheqM1);
                    CcPrixVteCheqM1.setText(st_CcPrixVteCheqM1);
                    CcIsChequierM2On.setChecked(st_CcIsChequierM2On);
                    onSwitchButtonClicked(CcIsChequierM2On);
                    CcNbPagesCheqM2.setText(st_CcNbPagesCheqM2);
                    CcPrixVteCheqM2.setText(st_CcPrixVteCheqM2);
                    CcIsChequierM3On.setChecked(st_CcIsChequierM3On);
                    onSwitchButtonClicked(CcIsChequierM3On);
                    CcNbPagesCheqM3.setText(st_CcNbPagesCheqM3);
                    CcPrixVteCheqM3.setText(st_CcPrixVteCheqM3);
                    CcDureeValidCheq.setText(st_CcDureeValidCheq);
                    CcNbMinSignatChq.setText(st_CcNbMinSignatChq);
                    CcIsTxCommMvmOper.setChecked(st_CcIsTxCommMvmOper);
                    onSwitchButtonClicked(CcIsTxCommMvmOper);
                    if (st_CcNatTxComm.equals("T")){
                        CcNatTxCommFixe.setChecked(true);
                        onRadioButtonClicked(CcNatTxCommFixe);
                    }else if (st_CcNatTxComm.equals("M")){
                        CcNatTxCommMontant.setChecked(true);
                        onRadioButtonClicked(CcNatTxCommMontant);
                    }else if (st_CcNatTxComm.equals("P")){
                        CcNatTxCommPlage.setChecked(true);
                        onRadioButtonClicked(CcNatTxCommPlage);
                    }
                    CcValTxCommMvm.setText(st_CcValTxCommMvm);
                    mySpinnerBaseTxCommMvm.setText(baseCcTxCommMvm);
                    CcPlageTxCommMvmFrom.setText(st_CcPlageTxCommMvmFrom);
                    CcPlageTxCommMvmTo.setText(st_CcPlageTxCommMvmTo);


                }
            });
        }


    }

    /**
     * Displays an alert dialogue to confirm the deletion
     */
    private void confirmDelete() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                UpdateProduitCpteCourant.this);
        alertDialogBuilder.setMessage("Voulez-vous vraiment supprimer ce compte courant ?");
        alertDialogBuilder.setPositiveButton("Supprimer",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                            //If the user confirms deletion, execute DeleteCpteCourantAsyncTask
                            new DeleteCpteCourantAsyncTask().execute();
                        } else {
                            Toast.makeText(UpdateProduitCpteCourant.this,
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
    private class DeleteCpteCourantAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateProduitCpteCourant.this);
            pDialog.setMessage("Suppression du compte courant...");
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
            httpParams.put(KEY_CPTE_COURANT_ID, cpteCourantId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "delete_cpte_courant.php", "POST", httpParams);
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
                        Toast.makeText(UpdateProduitCpteCourant.this,
                                "Compte courant Supprimé", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie deletion
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(UpdateProduitCpteCourant.this,
                                "Some error occurred while deleting Compte courant",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    /**
     * Checks whether all files are filled. If so then calls UpdateCompteCourantAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void updateCpteCourant() {


      /*  if (!STRING_EMPTY.equals(movieNameEditText.getText().toString()) &&
                !STRING_EMPTY.equals(genreEditText.getText().toString()) &&
                !STRING_EMPTY.equals(yearEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ratingEditText.getText().toString())) {*/
        if (!STRING_EMPTY.equals(CcCode.getText().toString().trim()) &&
                !STRING_EMPTY.equals(CcLibelle.getText().toString().trim())){

//            st_CcCode=  CcCode.getText().toString();
//            st_CcLibelle=  CcLibelle.getText().toString();
//            st_CcIsDecouvOn=  CcIsDecouvOn.isChecked();
//            st_CcMtMaxDecouv=  CcMtMaxDecouv.getText().toString();
//            st_CcIsMaxDecouvNeg=  CcIsMaxDecouvNeg.isChecked();
//            //st_CcNatureTxIntDecouv=  CcNatureTxIntDecouv.getText().toString();
//            st_CcValTxIntDecouv=  CcValTxIntDecouv.getText().toString();
//            baseCcTxIntDecouv = mySpinnerBaseTxIntDecouv.getText().toString();
//            st_CcPlageTxIntDecouvFrom=  CcPlageTxIntDecouvFrom.getText().toString();
//            st_CcPlageTxIntDecouvTo=  CcPlageTxIntDecouvTo.getText().toString();
//            st_CcDureeMaxDecouv=  CcDureeMaxDecouv.getText().toString();
//            // st_CcNatureTypDureDecouv=  CcNatureTypDureDecouv.getText().toString();
//            //st_CcNatureTxMtAgio=  CcNatureTxMtAgio.getText().toString();
//            st_CcValTxMtAgio=  CcValTxMtAgio.getText().toString();
//            st_CcNatBaseAgio = mySpinnerBaseTxMtAgio.getText().toString();
//            st_CcPlageTxMtAgioFrom=  CcPlageTxMtAgioFrom.getText().toString();
//            st_CcPlageTxMtAgioTo=  CcPlageTxMtAgioTo.getText().toString();
//            //st_CcNatBaseAgio=  CcNatBaseAgio.getText().toString();
//            st_CcIsChequierM1On=  CcIsChequierM1On.isChecked();
//            st_CcNbPagesCheqM1=  CcNbPagesCheqM1.getText().toString();
//            st_CcPrixVteCheqM1=  CcPrixVteCheqM1.getText().toString();
//            st_CcIsChequierM2On=  CcIsChequierM2On.isChecked();
//            st_CcNbPagesCheqM2=  CcNbPagesCheqM2.getText().toString();
//            st_CcPrixVteCheqM2=  CcPrixVteCheqM2.getText().toString();
//            st_CcIsChequierM3On=  CcIsChequierM3On.isChecked();
//            st_CcNbPagesCheqM3=  CcNbPagesCheqM3.getText().toString();
//            st_CcPrixVteCheqM3=  CcPrixVteCheqM3.getText().toString();
//            st_CcDureeValidCheq=  CcDureeValidCheq.getText().toString();
//            st_CcNbMinSignatChq=  CcNbMinSignatChq.getText().toString();
//            st_CcIsTxCommMvmOper=  CcIsTxCommMvmOper.isChecked();
//            //st_CcNatTxComm=  CcNatTxComm.getText().toString();
//            st_CcValTxCommMvm=  CcValTxCommMvm.getText().toString();
//            baseCcTxCommMvm  = mySpinnerBaseTxCommMvm.getText().toString();
//            st_CcPlageTxCommMvmFrom=  CcPlageTxCommMvmFrom.getText().toString();
//            st_CcPlageTxCommMvmTo=  CcPlageTxCommMvmTo.getText().toString();
//
//            //to manage plage data
//            //#CTP
//            for (int i=0; i<plageDataListCTP.size();i++){
//                tabPlageDebutCTP +=";"+plageDataListCTP.get(i).getPdValDe();
//                tabPlageFinCTP +=";"+plageDataListCTP.get(i).getPdValA();
//                tabPlageValeurCTP +=";"+plageDataListCTP.get(i).getPdValTaux();
//                tabPlageBaseCTP +=";"+plageDataListCTP.get(i).getPdBase();
//                tabPlageNatureCTP +=";"+plageDataListCTP.get(i).getPdNature();
//            }
//            //#CAP
//            for (int i=0; i<plageDataListCAP.size();i++){
//                tabPlageDebutCAP +=";"+plageDataListCAP.get(i).getPdValDe();
//                tabPlageFinCAP +=";"+plageDataListCAP.get(i).getPdValA();
//                tabPlageValeurCAP +=";"+plageDataListCAP.get(i).getPdValTaux();
//                tabPlageBaseCAP +=";"+plageDataListCAP.get(i).getPdBase();
//                tabPlageNatureCAP +=";"+plageDataListCAP.get(i).getPdNature();
//            }
//            //#TCP
//            for (int i=0; i<plageDataListTCP.size();i++){
//                tabPlageDebutTCP +=";"+plageDataListTCP.get(i).getPdValDe();
//                tabPlageFinTCP +=";"+plageDataListTCP.get(i).getPdValA();
//                tabPlageValeurTCP +=";"+plageDataListTCP.get(i).getPdValTaux();
//                tabPlageBaseTCP +=";"+plageDataListTCP.get(i).getPdBase();
//                tabPlageNatureTCP +=";"+plageDataListTCP.get(i).getPdNature();
//            }
            st_CcCode=  CcCode.getText().toString();
            st_CcLibelle=  CcLibelle.getText().toString();
            st_CcIsDecouvOn=  CcIsDecouvOn.isChecked();
            st_CcMtMaxDecouv=  CcMtMaxDecouv.getText().toString();
            st_CcMtPlafondAvceSpec=  CcMtPlafondAvceSpec.getText().toString();

            st_CcIsMaxDecouvNeg=  CcIsMaxDecouvNeg.isChecked();
            st_CcIsAvanceSpecialOn=  CcIsAvanceSpecialOn.isChecked();
            //st_CcNatureTxIntDecouv=  CcNatureTxIntDecouv.getText().toString();
            st_CcValTxIntDecouv=  CcValTxIntDecouv.getText().toString();
            baseCcTxIntDecouv = mySpinnerBaseTxIntDecouv.getText().toString();
            st_CcPlageTxIntDecouvFrom=  CcPlageTxIntDecouvFrom.getText().toString();
            st_CcPlageTxIntDecouvTo=  CcPlageTxIntDecouvTo.getText().toString();
            st_CcDureeMaxDecouv=  CcDureeMaxDecouv.getText().toString();
            // st_CcNatureTypDureDecouv=  CcNatureTypDureDecouv.getText().toString();
            //st_CcNatureTxMtAgio=  CcNatureTxMtAgio.getText().toString();
            st_CcValTxMtAgio=  CcValTxMtAgio.getText().toString();
            st_CcNatBaseAgio = mySpinnerBaseTxMtAgio.getText().toString();
            st_CcPlageTxMtAgioFrom=  CcPlageTxMtAgioFrom.getText().toString();
            st_CcPlageTxMtAgioTo=  CcPlageTxMtAgioTo.getText().toString();
            //st_CcNatBaseAgio=  CcNatBaseAgio.getText().toString();
            st_CcIsChequierM1On=  CcIsChequierM1On.isChecked();
            st_CcIsPlafAvceSpecNegoc=  CcIsPlafAvceSpecNegoc.isChecked();
            st_CcNbPagesCheqM1=  CcNbPagesCheqM1.getText().toString();
            st_CcPrixVteCheqM1=  CcPrixVteCheqM1.getText().toString();
            st_CcIsChequierM2On=  CcIsChequierM2On.isChecked();
            st_CcNbPagesCheqM2=  CcNbPagesCheqM2.getText().toString();
            st_CcPrixVteCheqM2=  CcPrixVteCheqM2.getText().toString();
            st_CcIsChequierM3On=  CcIsChequierM3On.isChecked();
            st_CcNbPagesCheqM3=  CcNbPagesCheqM3.getText().toString();
            st_CcPrixVteCheqM3=  CcPrixVteCheqM3.getText().toString();
            st_CcDureeValidCheq=  CcDureeValidCheq.getText().toString();
            st_CcNbMinSignatChq=  CcNbMinSignatChq.getText().toString();
            st_CcIsTxCommMvmOper=  CcIsTxCommMvmOper.isChecked();
            //st_CcNatTxComm=  CcNatTxComm.getText().toString();
            st_CcValTxCommMvm=  CcValTxCommMvm.getText().toString();
            baseCcTxCommMvm  = mySpinnerBaseTxCommMvm.getText().toString();
            st_CcPlageTxCommMvmFrom=  CcPlageTxCommMvmFrom.getText().toString();
            st_CcPlageTxCommMvmTo=  CcPlageTxCommMvmTo.getText().toString();

            st_CcTauxIntAvceSpec=  CcTauxIntAvceSpec.getText().toString();
            baseTxIntAvceSpec  = mySpinnerCcBaseTxIntAvceSpec.getText().toString();
            st_CcTauxIntPenAvceSpec=  CcTauxIntPenAvceSpec.getText().toString();
            baseTxIntPenAvceSpec  = mySpinnerCcBaseTxIntPenAvceSpec.getText().toString();

            st_CcIsTxIntDegressif=  CcIsTxIntDegressif.isChecked();

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





            new UpdateCompteCourantAsyncTask().execute();
        } else {
            Toast.makeText(UpdateProduitCpteCourant.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();

        }


    }
    /**
     * AsyncTask for updating a movie details
     */

    private class UpdateCompteCourantAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateProduitCpteCourant.this);
            pDialog.setMessage("Mise à jour Compte courant...");
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
            httpParams.put(KEY_CPTE_COURANT_ID, cpteCourantId);

            httpParams.put(KEY_CcCode, st_CcCode);
            httpParams.put(KEY_CcLibelle, st_CcLibelle);
            httpParams.put(KEY_CcIsDecouvOn, st_CcIsDecouvOn.toString());
            httpParams.put(KEY_CcMtMaxDecouv, st_CcMtMaxDecouv);
            httpParams.put(KEY_CcIsMaxDecouvNeg, st_CcIsMaxDecouvNeg.toString());
            httpParams.put(KEY_CcNatureTxIntDecouv, st_CcNatureTxIntDecouv);
            httpParams.put(KEY_CcValTxIntDecouv, st_CcValTxIntDecouv);
            httpParams.put(KEY_CcBaseTxIntDecouv, baseCcTxIntDecouv);
            httpParams.put(KEY_CcPlageTxIntDecouvFrom, st_CcPlageTxIntDecouvFrom);
            httpParams.put(KEY_CcPlageTxIntDecouvTo, st_CcPlageTxIntDecouvTo);
            httpParams.put(KEY_CcDureeMaxDecouv, st_CcDureeMaxDecouv);
            httpParams.put(KEY_CcNatureTypDureDecouv, st_CcNatureTypDureDecouv);
            httpParams.put(KEY_CcNatureTxMtAgio, st_CcNatureTxMtAgio);
            httpParams.put(KEY_CcValTxMtAgio, st_CcValTxMtAgio);
            httpParams.put(KEY_CcPlageTxMtAgioFrom, st_CcPlageTxMtAgioFrom);
            httpParams.put(KEY_CcPlageTxMtAgioTo, st_CcPlageTxMtAgioTo);
            httpParams.put(KEY_CcNatBaseAgio, st_CcNatBaseAgio);
            httpParams.put(KEY_CcIsChequierM1On, st_CcIsChequierM1On.toString());
            httpParams.put(KEY_CcNbPagesCheqM1, st_CcNbPagesCheqM1);
            httpParams.put(KEY_CcPrixVteCheqM1, st_CcPrixVteCheqM1);
            httpParams.put(KEY_CcIsChequierM2On, st_CcIsChequierM2On.toString());
            httpParams.put(KEY_CcNbPagesCheqM2, st_CcNbPagesCheqM2);
            httpParams.put(KEY_CcPrixVteCheqM2, st_CcPrixVteCheqM2);
            httpParams.put(KEY_CcIsChequierM3On, st_CcIsChequierM3On.toString());
            httpParams.put(KEY_CcNbPagesCheqM3, st_CcNbPagesCheqM3);
            httpParams.put(KEY_CcPrixVteCheqM3, st_CcPrixVteCheqM3);
            httpParams.put(KEY_CcDureeValidCheq, st_CcDureeValidCheq);
            httpParams.put(KEY_CcNbMinSignatChq, st_CcNbMinSignatChq);
            httpParams.put(KEY_CcIsTxCommMvmOper, st_CcIsTxCommMvmOper.toString());
            httpParams.put(KEY_CcNatTxComm, st_CcNatTxComm);
            httpParams.put(KEY_CcValTxCommMvm, st_CcValTxCommMvm);
            httpParams.put(KEY_CcBaseTxCommMvm, baseCcTxCommMvm);
            httpParams.put(KEY_CcPlageTxCommMvmFrom, st_CcPlageTxCommMvmFrom);
            httpParams.put(KEY_CcPlageTxCommMvmTo, st_CcPlageTxCommMvmTo);
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


            httpParams.put(KEY_CcIsAvanceSpecialOn, st_CcIsAvanceSpecialOn.toString());
            httpParams.put(KEY_CcMtPlafondAvceSpec, st_CcMtPlafondAvceSpec);
            httpParams.put(KEY_CcIsPlafAvceSpecNegoc, st_CcIsPlafAvceSpecNegoc.toString());
            httpParams.put(KEY_CcNatTauxIntAvceSpec, st_CcNatTauxIntAvceSpec);
            httpParams.put(KEY_CcTauxIntAvceSpec, st_CcTauxIntAvceSpec);
            httpParams.put(KEY_CcBaseTxIntAvceSpec, baseTxIntAvceSpec);


            httpParams.put(KEY_CcNatTauxIntPenAvceSpec, st_CcNatTauxIntPenAvceSpec);
            httpParams.put(KEY_CcTauxIntPenAvceSpec, st_CcTauxIntPenAvceSpec);
            httpParams.put(KEY_CcBaseTxIntPenAvceSpec, baseTxIntPenAvceSpec);

            httpParams.put(KEY_CcIsTxIntDegressif, st_CcIsTxIntDegressif.toString());

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "update_cpte_courant.php", "POST", httpParams);
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
                        Toast.makeText(UpdateProduitCpteCourant.this,
                                "Compte courant mis à jour !", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(UpdateProduitCpteCourant.this,
                                "Some error occurred while updating",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}
