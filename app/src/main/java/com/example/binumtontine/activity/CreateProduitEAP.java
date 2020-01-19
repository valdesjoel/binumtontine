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

import com.example.binumtontine.R;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static final String KEY_EpPlageTxInterFrom = "EpPlageTxInterFrom";
    private static final String KEY_EpPlageTxInterTo = "EpPlageTxInterTo";
    private static final String KEY_EpIsTxIntNeg = "EpIsTxIntNeg";
    private static final String KEY_EpIsPriseIntMiseOn = "EpIsPriseIntMiseOn";
    private static final String EP_IsPenalNRespMise = "EpIsPenalNRespMise";
    private static final String EP_NbEchPenalOn = "EpNbEchPenalOn";
    private static final String EP_IsEchPenalSucces = "EpIsEchPenalSucces";
    private static final String EP_NatureRupAn = "EpNatureRupAn";
    private static final String EP_ValTxMtRupture = "EpValTxMtRupture";
    private static final String EP_PlageTxMtRuptureFrom = "EpPlageTxMtRuptureFrom";
    private static final String EP_PlageTxMtRuptureTo = "EpPlageTxMtRuptureTo";
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
    //private static final String ET_CAISSE_ID = "EpCaisseId";
    //private static final String ET_GUICHET_ID = "EtGuichetId";


    private static String STRING_EMPTY = "";


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
    private EditText EpValTxInter_ET;
    private EditText EpPlageTxInterFrom_ET;
    private EditText EpPlageTxInterTo_ET;
    private EditText EpPlageTxInterValeur_ET;
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
    private EditText EpPlageTxMtRuptureFrom_ET;
    private EditText EpPlageTxMtRuptureTo_ET;
    private EditText EpPlageTxMtRuptureValeur_ET;
    private EditText EpBaseTxPenal_ET;
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
    private EditText EpBaseTxIntRupant_ET;
    private Switch EpTxIntRupantNeg_SW;
    private Switch EpIsPenalRupAnt_SW;
    private RadioButton rbEpNaturePenalTaux;
    private RadioButton rbEpNaturePenalMontant;
    private RadioButton rbEpNaturePenalPlage;
    private EditText EpValTxMtPenalite_ET;
    private EditText EpPlageTxMtPenaliteFrom_ET;
    private EditText EpPlageTxMtPenaliteTo_ET;
    private EditText EpPlageTxMtPenaliteValeur_ET;
    private EditText EpBaseTxMtPenal_ET;
    private boolean EpIsMtMultMise;
    private boolean EpIsVersemAntic;
    private String EpDureeMax;
    private String EpNaturePas;
    private String EpNbreUPas;
    private String EpTypTxInter;
    private String EpValTxInter;
    private boolean EpIsTxIntNeg;
    private boolean EpIsPriseIntMiseOn;
    private boolean EpIsPenalNRespMise;
    private String EpNbEchPenalOn;
    private boolean EpIsEchPenalSucces;
    private String EpNatureRupAn;
    private String EpValTxMtRupture;
    private String EpBaseTxPenal;
    private boolean EpIsEparRetireFin;
    private boolean EpIsEparTransfFin;
    private boolean EpIsOnlyTotTransf;
    private boolean EpIsEparRenouvFin;
    private boolean EpActionDefATerme;
    private boolean EpIsMultiEAPOn;
    private boolean EpIsInterDusRupAnt;
    private boolean EpIsNewTxIntRupAnt;
    private String EpTypNewTxIntRupAnt;
    private String EpValTxIntRupant;
    private String EpBaseTxIntRupant;
    private boolean EpTxIntRupantNeg;
    private boolean EpIsPenalRupAnt;
    private String EpNaturePenal;
    private String EpValTxMtPenalite;
    private String EpBaseTxMtPenal;
    private LinearLayout ll;
    private LinearLayout ll_btn;

    private LinearLayout ll_1;
    private LinearLayout ll_btn_1;
    private LinearLayout ll_2;
    private LinearLayout ll_btn_2;

    private LinearLayout ll_3;
    private LinearLayout ll_btn_3;

    private int numberOfLinesDebut= 0;
    private int numberOfLinesDebut1= 0;
    private int numberOfLinesDebut2= 0;
    private int numberOfLinesDebut3= 0;
    private Button remove_button;
    private Button remove_button1;
    private Button remove_button2;
    private Button remove_button3;
    private LinearLayout ll_EpNaturePenal;


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

        ll = (LinearLayout)findViewById(R.id.blk_plage_tx_eap);
        ll_btn = (LinearLayout)findViewById(R.id.blk_btn_plage_tx_eap);

        ll_1 = (LinearLayout)findViewById(R.id.blk_plage_tx_penalite_eap);
        ll_btn_1 = (LinearLayout)findViewById(R.id.blk_btn_plage_tx_penalite_eap);
        ll_2 = (LinearLayout)findViewById(R.id.blk_plage_new_tx_eap);
        ll_btn_2 = (LinearLayout)findViewById(R.id.blk_btn_plage_new_tx_eap);
        ll_3 = (LinearLayout)findViewById(R.id.blk_plage_EpValTxMtPenalite);
        ll_btn_3 = (LinearLayout)findViewById(R.id.blk_btn_plage_EpValTxMtPenalite);

        ll_EcheancePenaliteSuccessiveEAP = (LinearLayout)findViewById(R.id.ll_EcheancePenaliteSuccessiveEAP);
        ll_NatureFraisPenaliteEAP = (LinearLayout)findViewById(R.id.ll_NatureFraisPenaliteEAP);

        ll_DefinirNouveauTxInteretEAP = (LinearLayout)findViewById(R.id.ll_DefinirNouveauTxInteretEAP);
        ll_TypeNouveauTxInteretRuptureEAP = (LinearLayout)findViewById(R.id.ll_TypeNouveauTxInteretRuptureEAP);
        ll_TxNewInteretNegociableEAP = (LinearLayout)findViewById(R.id.ll_TxNewInteretNegociableEAP);
        ll_EpNaturePenal = (LinearLayout)findViewById(R.id.ll_EpNaturePenal);

        EpCode_ET = (EditText) findViewById(R.id.input_txt_Code_EAP);
        EpLibelle_ET = (EditText) findViewById(R.id.input_txt_LibelleEAP);
        Ep_MinMtMiseEAP_ET = (EditText) findViewById(R.id.input_txt_MinMtMiseEAP);
        EpIsMtMultMise_SW = (Switch) findViewById(R.id.SwitchMtMultMiseEAP);
        EpIsVersemAntic_SW = (Switch) findViewById(R.id.SwitchVersementAnticipeEAP);

        EP_DureeMinEAP_ET = (EditText) findViewById(R.id.input_txt_DureeMinEAP);
        EpDureeMax_ET = (EditText) findViewById(R.id.input_txt_DureeMaxEAP);

        rbEpNaturePasFixe = (RadioButton) findViewById(R.id.rb_EpNaturePasFixe);
        rbEpNaturePasSaut = (RadioButton) findViewById(R.id.rb_EpNaturePasSaut);
        EpNbreUPas_ET = (EditText) findViewById(R.id.input_txt_NbreUnitePasEAP);
        rbEpTypTxInterFixe = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
        rbEpTypTxInterPlage = (RadioButton) findViewById(R.id.rbEpTypTxInterPlage);
        EpValTxInter_ET = (EditText) findViewById(R.id.input_txt_ValeurTauxInteretEAP);

        EpPlageTxInterFrom_ET = (EditText) findViewById(R.id.txt_EpValTxInterFrom);
        EpPlageTxInterTo_ET = (EditText) findViewById(R.id.txt_EpValTxInterTo);
        EpPlageTxInterValeur_ET = (EditText) findViewById(R.id.txt_EpValTxInterValeur);
        EpIsTxIntNeg_SW = (Switch) findViewById(R.id.SwitchTauxInteretNegocieEAP);
        EpIsPriseIntMiseOn_SW = (Switch) findViewById(R.id.SwitchPrendreInteretDesLaMiseEAP);
        EpIsPenalNRespMise_SW = (Switch) findViewById(R.id.SwitchPenaliteEnCasDeRuptureEAP);
        EpNbEchPenalOn_ET = (EditText) findViewById(R.id.input_txt_EpNbEchPenalOn);
        EpIsEchPenalSucces_SW = (Switch) findViewById(R.id.SwitchEcheancePenaliteSuccessiveEAP);
        rbEpNatureRupAnTaux = (RadioButton) findViewById(R.id.rbEpNatureRupAnTaux);
        rbEpNatureRupAnMontant = (RadioButton) findViewById(R.id.rbEpNatureRupAnMontant);
        rbEpNatureRupAnPlage = (RadioButton) findViewById(R.id.rbEpNatureRupAnPlage);

        EpValTxMtRupture_ET = (EditText) findViewById(R.id.input_txt_ValTxMtRuptureEAP);
        EpPlageTxMtRuptureFrom_ET = (EditText) findViewById(R.id.txt_EpValTxMtRuptureFrom);
        EpPlageTxMtRuptureTo_ET = (EditText) findViewById(R.id.txt_EpValTxMtRuptureTo);
        EpPlageTxMtRuptureValeur_ET = (EditText) findViewById(R.id.txt_EpValTxMtRuptureValeur);
        EpBaseTxPenal_ET = (EditText) findViewById(R.id.input_txt_BaseTxPenalEAP);

        EpIsEparRetireFin_SW = (Switch) findViewById(R.id.SwitchPossibiliteRetirerEAP);
        EpIsEparTransfFin_SW = (Switch) findViewById(R.id.SwitchPossibiliteTransfererVersEAV_EAP);
        EpIsOnlyTotTransf_SW = (Switch) findViewById(R.id.SwitchTransfererTotaliteVersEAV_EAP);
        EpIsEparRenouvFin_SW = (Switch) findViewById(R.id.SwitchPossibiliteRenouvelerEAP);
        EpActionDefATerme_SW = (Switch) findViewById(R.id.SwitchActionParDefautEAP); // à transformer en radio button
        EpIsMultiEAPOn_SW = (Switch) findViewById(R.id.SwitchMultiEAP);
        EpIsInterDusRupAnt_SW = (Switch) findViewById(R.id.SwitchInteretDusRuptureEAP);
        EpIsNewTxIntRupAnt_SW = (Switch) findViewById(R.id.SwitchDefinirNouveauTxInteretEAP);
        //EpIsNewTxIntRupAnt_SW = (Switch) findViewById(R.id.SwitchDefinirNouveauTxInteretEAP);


        rbEpTypNewTxIntRupAntFixe = (RadioButton) findViewById(R.id.rbEpTypeNouveauTxInteretRuptureEAPFixe);
        rbEpTypNewTxIntRupAntPlage = (RadioButton) findViewById(R.id.rbEpTypeNouveauTxInteretRuptureEAPPlage);

        EpValTxIntRupant_ET = (EditText) findViewById(R.id.txtValeurNewTxInteretRuptureEAP);
        EpPlageTxIntRupanFrom_ET = (EditText) findViewById(R.id.txt_EpNewValTxInterFrom);
        EpPlageTxIntRupanTo_ET = (EditText) findViewById(R.id.txt_EpNewValTxInterTo);
        EpPlageTxIntRupanValeur_ET = (EditText) findViewById(R.id.txt_EpNewValTxInterValeur);
        EpBaseTxIntRupant_ET = (EditText) findViewById(R.id.txtBaseNewTxInteretEAP);
        EpTxIntRupantNeg_SW = (Switch) findViewById(R.id.SwitchTxNewInteretNegociableEAP);
        EpIsPenalRupAnt_SW = (Switch) findViewById(R.id.SwitchPenaliteDeblocageEnCasDeRuptureEAP);

        rbEpNaturePenalTaux = (RadioButton) findViewById(R.id.rb_EpNaturePenalTaux);
        rbEpNaturePenalMontant = (RadioButton) findViewById(R.id.rb_EpNaturePenalMontant);
        rbEpNaturePenalPlage = (RadioButton) findViewById(R.id.rb_EpNaturePenalPlage);

        EpValTxMtPenalite_ET = (EditText) findViewById(R.id.txtEpValTxMtPenalite);
        EpPlageTxMtPenaliteFrom_ET = (EditText) findViewById(R.id.txt_EpValTxMtPenaliteFrom);
        EpPlageTxMtPenaliteTo_ET = (EditText) findViewById(R.id.txt_EpValTxMtPenaliteTo);
        EpPlageTxMtPenaliteValeur_ET = (EditText) findViewById(R.id.txt_EpValTxMtPenaliteValeur);
        EpBaseTxMtPenal_ET = (EditText) findViewById(R.id.input_txt_EpBaseTxPenal);


        onRadioButtonClicked(rbEpNaturePasFixe);
        onRadioButtonClicked(rbEpTypTxInterFixe);
        onRadioButtonClicked(rbEpNatureRupAnTaux);
        onRadioButtonClicked(rbEpTypNewTxIntRupAntFixe);
        onRadioButtonClicked(rbEpNaturePenalMontant);

        onSwitchButtonClicked(EpIsPenalNRespMise_SW);
        onSwitchButtonClicked(EpIsInterDusRupAnt_SW);
        onSwitchButtonClicked(EpIsNewTxIntRupAnt_SW);
        onSwitchButtonClicked(EpIsPenalRupAnt_SW);



        remove_button = (Button) findViewById(R.id.remove_button_EpValTxInter);
        remove_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Remove_Line();
            }
        });
        final Button Add_button = (Button) findViewById(R.id.add_button_EpValTxInter);
        Add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add_Line();
            }
        });

        remove_button1 = (Button) findViewById(R.id.remove_button_tx_penalite_eap);
        remove_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Remove_Line1();
            }
        });
        final Button Add_button1 = (Button) findViewById(R.id.add_button_tx_penalite_eap);
        Add_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add_Line1();
            }
        });
remove_button2 = (Button) findViewById(R.id.remove_button_plage_new_tx_eap);
        remove_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Remove_Line2();
            }
        });
        final Button Add_button2 = (Button) findViewById(R.id.add_button_plage_new_tx_eap);
        Add_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add_Line2();
            }
        });

remove_button3 = (Button) findViewById(R.id.remove_button_plage_EpValTxMtPenalite);
        remove_button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Remove_Line3();
            }
        });
        final Button Add_button3 = (Button) findViewById(R.id.add_button_plage_EpValTxMtPenalite);
        Add_button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add_Line3();
            }
        });


        //onRadioButtonClicked();
        deleteButton = (Button) findViewById(R.id.btn_delete_eap);
        deleteButton.setVisibility(View.GONE);
        cancelButton = (Button) findViewById(R.id.btn_clean);
        //cirLoginButton
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
//                    Intent i = new Intent(CreateProduitEAP.this, PlageData.class);
//                    startActivityForResult(i,20);
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
    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Ajout d'un produit EAP");

    }

    public void Add_Line() {
//i get current value on last EditText

        if (numberOfLinesDebut==0){
            tabPlageDebutList.add(EpPlageTxInterFrom_ET.getText().toString());
            tabPlageFinList.add(EpPlageTxInterTo_ET.getText().toString());
            tabPlageValeurList.add(EpPlageTxInterValeur_ET.getText().toString());
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
    public void Add_Line1() {
//i get current value on last EditText

        if (numberOfLinesDebut1==0){
            tabPlageDebutList1.add(EpPlageTxMtRuptureFrom_ET.getText().toString());
            tabPlageFinList1.add(EpPlageTxMtRuptureTo_ET.getText().toString());
            tabPlageValeurList1.add(EpPlageTxMtRuptureValeur_ET.getText().toString());
        }
        if (numberOfLinesDebut1>0){
            EditText editTextDebut = (EditText)findViewById(numberOfLinesDebut1);
            tabPlageDebutList1.add(editTextDebut.getText().toString());
            EditText editTextFin = (EditText)findViewById(numberOfLinesFin1);
            tabPlageFinList1.add(editTextFin.getText().toString());


            EditText editTextValeur = (EditText)findViewById(numberOfLinesValeur1);
            tabPlageValeurList1.add(editTextValeur.getText().toString());

        }

        // add edittext debut
        EditText et_debut = new EditText(this);
        TextView tv_debut = new TextView(this);
        tv_debut.setTextColor(Color.BLUE);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        et_debut.setLayoutParams(p);

        et_debut.setHint("De");
        et_debut.setId(numberOfLinesDebut1 + 1);
//        int idLastChild= ll.getChildCount()-1;
//        editText.setId(idLastChild+1);
        ll_1.addView(tv_debut);
        ll_1.addView(et_debut);
        numberOfLinesDebut1++;
        tv_debut.setText("PLAGE "+(numberOfLinesDebut1+1));


        // add edittext fin
        EditText et_fin = new EditText(this);
        LinearLayout.LayoutParams p_fin = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        et_fin.setLayoutParams(p_fin);
        //et.setText("Text");
        et_fin.setHint("A");
        et_fin.setId(numberOfLinesFin1 + 1000);
        ll_1.addView(et_fin);
        numberOfLinesFin1+=1000;
        // add edittext valeur
        EditText et_valeur = new EditText(this);
        LinearLayout.LayoutParams p_valeur = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        et_valeur.setLayoutParams(p_valeur);
        //et.setText("Text");
        et_valeur.setHint("Valeur");
        et_valeur.setId(numberOfLinesValeur1 + 2000);
        ll_1.addView(et_valeur);
        numberOfLinesValeur1+=2000;



        remove_button1.setVisibility(View.VISIBLE);
    }
    public void Add_Line2() {
//i get current value on last EditText

        if (numberOfLinesDebut2==0){
            tabPlageDebutList2.add(EpPlageTxIntRupanFrom_ET.getText().toString());
            tabPlageFinList2.add(EpPlageTxIntRupanTo_ET.getText().toString());
            tabPlageValeurList2.add(EpPlageTxIntRupanValeur_ET.getText().toString());
        }
        if (numberOfLinesDebut2>0){
            EditText editTextDebut = (EditText)findViewById(numberOfLinesDebut2);
            tabPlageDebutList2.add(editTextDebut.getText().toString());
            EditText editTextFin = (EditText)findViewById(numberOfLinesFin2);
            tabPlageFinList2.add(editTextFin.getText().toString());


            EditText editTextValeur = (EditText)findViewById(numberOfLinesValeur2);
            tabPlageValeurList2.add(editTextValeur.getText().toString());

        }

        // add edittext debut
        EditText et_debut = new EditText(this);
        TextView tv_debut = new TextView(this);
        tv_debut.setTextColor(Color.BLUE);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        et_debut.setLayoutParams(p);

        et_debut.setHint("De");
        et_debut.setId(numberOfLinesDebut2 + 1);
//        int idLastChild= ll.getChildCount()-1;
//        editText.setId(idLastChild+1);
        ll_2.addView(tv_debut);
        ll_2.addView(et_debut);
        numberOfLinesDebut2++;
        tv_debut.setText("PLAGE "+(numberOfLinesDebut2+1));


        // add edittext fin
        EditText et_fin = new EditText(this);
        LinearLayout.LayoutParams p_fin = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        et_fin.setLayoutParams(p_fin);
        //et.setText("Text");
        et_fin.setHint("A");
        et_fin.setId(numberOfLinesFin2 + 1000);
        ll_2.addView(et_fin);
        numberOfLinesFin2+=1000;
        // add edittext valeur
        EditText et_valeur = new EditText(this);
        LinearLayout.LayoutParams p_valeur = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        et_valeur.setLayoutParams(p_valeur);
        //et.setText("Text");
        et_valeur.setHint("Valeur");
        et_valeur.setId(numberOfLinesValeur2 + 2000);
        ll_2.addView(et_valeur);
        numberOfLinesValeur2+=2000;



        remove_button2.setVisibility(View.VISIBLE);
    }
    public void Add_Line3() {
//i get current value on last EditText

        if (numberOfLinesDebut3==0){
            tabPlageDebutList3.add(EpPlageTxMtPenaliteFrom_ET.getText().toString());
            tabPlageFinList3.add(EpPlageTxMtPenaliteTo_ET.getText().toString());
            tabPlageValeurList3.add(EpPlageTxMtPenaliteValeur_ET.getText().toString());
        }
        if (numberOfLinesDebut3>0){
            EditText editTextDebut = (EditText)findViewById(numberOfLinesDebut3);
            tabPlageDebutList3.add(editTextDebut.getText().toString());
            EditText editTextFin = (EditText)findViewById(numberOfLinesFin3);
            tabPlageFinList3.add(editTextFin.getText().toString());


            EditText editTextValeur = (EditText)findViewById(numberOfLinesValeur3);
            tabPlageValeurList3.add(editTextValeur.getText().toString());

        }

        // add edittext debut
        EditText et_debut = new EditText(this);
        TextView tv_debut = new TextView(this);
        tv_debut.setTextColor(Color.BLUE);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        et_debut.setLayoutParams(p);

        et_debut.setHint("De");
        et_debut.setId(numberOfLinesDebut3 + 1);
//        int idLastChild= ll.getChildCount()-1;
//        editText.setId(idLastChild+1);
        ll_3.addView(tv_debut);
        ll_3.addView(et_debut);
        numberOfLinesDebut3++;
        tv_debut.setText("PLAGE "+(numberOfLinesDebut3+1));


        // add edittext fin
        EditText et_fin = new EditText(this);
        LinearLayout.LayoutParams p_fin = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        et_fin.setLayoutParams(p_fin);
        //et.setText("Text");
        et_fin.setHint("A");
        et_fin.setId(numberOfLinesFin3 + 1000);
        ll_3.addView(et_fin);
        numberOfLinesFin3+=1000;
        // add edittext valeur
        EditText et_valeur = new EditText(this);
        LinearLayout.LayoutParams p_valeur = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        et_valeur.setLayoutParams(p_valeur);
        //et.setText("Text");
        et_valeur.setHint("Valeur");
        et_valeur.setId(numberOfLinesValeur3 + 2000);
        ll_3.addView(et_valeur);
        numberOfLinesValeur3+=2000;



        remove_button3.setVisibility(View.VISIBLE);
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
    public void Remove_Line1() {
        //LinearLayout ll = (LinearLayout)findViewById(R.id.blk_EtPlageTxInter);
        if (numberOfLinesDebut1>0){


            ll_1.removeViewAt(ll_1.getChildCount()-1);
            ll_1.removeViewAt(ll_1.getChildCount()-1);
            ll_1.removeViewAt(ll_1.getChildCount()-1);
            ll_1.removeViewAt(ll_1.getChildCount()-1);
            numberOfLinesDebut1--;
            if (numberOfLinesDebut1==0){
                // ll.removeViewAt(ll.getChildCount()-1);
                remove_button1.setVisibility(View.INVISIBLE);
            }
        }

    }
    public void Remove_Line2() {
        //LinearLayout ll = (LinearLayout)findViewById(R.id.blk_EtPlageTxInter);
        if (numberOfLinesDebut2>0){


            ll_2.removeViewAt(ll_2.getChildCount()-1);
            ll_2.removeViewAt(ll_2.getChildCount()-1);
            ll_2.removeViewAt(ll_2.getChildCount()-1);
            ll_2.removeViewAt(ll_2.getChildCount()-1);
            numberOfLinesDebut2--;
            if (numberOfLinesDebut2==0){
                // ll.removeViewAt(ll.getChildCount()-1);
                remove_button2.setVisibility(View.INVISIBLE);
            }
        }

    }
    public void Remove_Line3() {
        //LinearLayout ll = (LinearLayout)findViewById(R.id.blk_EtPlageTxInter);
        if (numberOfLinesDebut3>0){


            ll_3.removeViewAt(ll_3.getChildCount()-1);
            ll_3.removeViewAt(ll_3.getChildCount()-1);
            ll_3.removeViewAt(ll_3.getChildCount()-1);
            ll_3.removeViewAt(ll_3.getChildCount()-1);
            numberOfLinesDebut3--;
            if (numberOfLinesDebut3==0){
                // ll.removeViewAt(ll.getChildCount()-1);
                remove_button3.setVisibility(View.INVISIBLE);
            }
        }

    }


    public void onSwitchButtonClicked(View view) {
        boolean checked1 = ((Switch) view).isChecked();
        String str="";

        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.SwitchPenaliteEnCasDeRuptureEAP:
                if (EpIsPenalNRespMise_SW.isChecked()) {
                    str = checked1?"Pénalité en cas de non respect des échéances activée":"Pénalité en cas de non respect des échéances désactivée";

                    //LL_EtNatureRupAn.setVisibility(View.VISIBLE);
                    EpNbEchPenalOn_ET.setVisibility(View.VISIBLE);
                    ll_EcheancePenaliteSuccessiveEAP.setVisibility(View.VISIBLE);
                    ll_NatureFraisPenaliteEAP.setVisibility(View.VISIBLE);
                    EpValTxMtRupture_ET.setVisibility(View.VISIBLE);
                    ll_1.setVisibility(View.VISIBLE);
                    ll_btn_1.setVisibility(View.VISIBLE);
                    EpBaseTxPenal_ET.setVisibility(View.VISIBLE);
                    //ll_EtValTxMtRupture.setVisibility(View.VISIBLE);

                }else{
                    EpNbEchPenalOn_ET.setVisibility(View.GONE);
                    ll_EcheancePenaliteSuccessiveEAP.setVisibility(View.GONE);
                    ll_NatureFraisPenaliteEAP.setVisibility(View.GONE);
                    EpValTxMtRupture_ET.setVisibility(View.GONE);
                    ll_1.setVisibility(View.GONE);
                    ll_btn_1.setVisibility(View.GONE);
                    EpBaseTxPenal_ET.setVisibility(View.GONE);
                }

                break;
            case R.id.SwitchInteretDusRuptureEAP:
                if (EpIsInterDusRupAnt_SW.isChecked()) {
                    str = checked1?"Intérêts dûs  en cas de rupture anticipée activé":"Intérêts dûs  en cas de rupture anticipée désactivé";

                    ll_DefinirNouveauTxInteretEAP.setVisibility(View.VISIBLE);
//                    //ll_TypeNouveauTxInteretRuptureEAP.setVisibility(View.VISIBLE);
//
//                    EpNbEchPenalOn_ET.setVisibility(View.VISIBLE);
//                    ll_EcheancePenaliteSuccessiveEAP.setVisibility(View.VISIBLE);
//                    ll_NatureFraisPenaliteEAP.setVisibility(View.VISIBLE);
//                    EpValTxMtRupture_ET.setVisibility(View.VISIBLE);
//                    ll_1.setVisibility(View.VISIBLE);
//                    ll_btn_1.setVisibility(View.VISIBLE);
//                    EpBaseTxPenal_ET.setVisibility(View.VISIBLE);
//                    //ll_EtValTxMtRupture.setVisibility(View.VISIBLE);

                }else{
                    ll_DefinirNouveauTxInteretEAP.setVisibility(View.GONE);
                    EpValTxIntRupant_ET.setVisibility(View.GONE);


                    ll_TypeNouveauTxInteretRuptureEAP.setVisibility(View.GONE);
                    ll_TxNewInteretNegociableEAP.setVisibility(View.GONE);

//                    EpNbEchPenalOn_ET.setVisibility(View.GONE);
//                    ll_EcheancePenaliteSuccessiveEAP.setVisibility(View.GONE);
//                    ll_NatureFraisPenaliteEAP.setVisibility(View.GONE);
//                    EpValTxMtRupture_ET.setVisibility(View.GONE);
//                    ll_1.setVisibility(View.GONE);
//                    ll_btn_1.setVisibility(View.GONE);
//                    EpBaseTxPenal_ET.setVisibility(View.GONE);
                }

                break;

            case R.id.SwitchDefinirNouveauTxInteretEAP:
                if (EpIsNewTxIntRupAnt_SW.isChecked()) {
                    str = checked1?"Pénalité en cas de non respect des échéances activée":"Pénalité en cas de non respect des échéances désactivée";

                    //LL_EtNatureRupAn.setVisibility(View.VISIBLE);

                    ll_TypeNouveauTxInteretRuptureEAP.setVisibility(View.VISIBLE);
                    ll_TxNewInteretNegociableEAP.setVisibility(View.VISIBLE);
                    EpValTxIntRupant_ET.setVisibility(View.VISIBLE);


                }else{
                    ll_TypeNouveauTxInteretRuptureEAP.setVisibility(View.GONE);
                    ll_TxNewInteretNegociableEAP.setVisibility(View.GONE);
                    EpValTxIntRupant_ET.setVisibility(View.GONE);
                }

                break;

            case R.id.SwitchPenaliteDeblocageEnCasDeRuptureEAP:
                if (EpIsPenalRupAnt_SW.isChecked()) {
                    str = checked1?"Pénalité en cas de non respect des échéances activée":"Pénalité en cas de non respect des échéances désactivée";

                    //LL_EtNatureRupAn.setVisibility(View.VISIBLE);

                    ll_EpNaturePenal.setVisibility(View.VISIBLE);
                    EpValTxMtPenalite_ET.setVisibility(View.VISIBLE);



                }else{
                    ll_EpNaturePenal.setVisibility(View.GONE);
                    EpValTxMtPenalite_ET.setVisibility(View.GONE);
                    ll_3.setVisibility(View.GONE);
                    ll_btn_3.setVisibility(View.GONE);
                }

                break;
    /*        case R.id.SwitchDefinirNouveauTxInteretEAT:
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
        // Check which RadioButton was clicked
        switch(view.getId()) {

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
                    ll.setVisibility(View.GONE);
                    ll_btn.setVisibility(View.GONE);

                }
                break;
            case R.id.rbEpTypTxInterPlage:
                if (rbEpTypTxInterPlage.isChecked()) {
                    EpTypTxInter ="P";
                    ll.setVisibility(View.VISIBLE);
                    if (numberOfLinesDebut<=0){
                        remove_button.setVisibility(View.INVISIBLE);
                    }
                    EpValTxInter_ET.setVisibility(View.GONE);
                    ll_btn.setVisibility(View.VISIBLE);

                }
                break;
            case R.id.rbEpNatureRupAnTaux:
                if (rbEpNatureRupAnTaux.isChecked()) {
                    EpNatureRupAn="T";


                    EpValTxMtRupture_ET.setVisibility(View.VISIBLE);
                    EpBaseTxPenal_ET.setVisibility(View.VISIBLE);
                    ll_1.setVisibility(View.GONE);
                    ll_btn_1.setVisibility(View.GONE);


                }
                break;
            case R.id.rbEpNatureRupAnMontant:
                if (rbEpNatureRupAnMontant.isChecked()) {
                    EpNatureRupAn="M";


                    EpValTxMtRupture_ET.setVisibility(View.VISIBLE);
                    EpBaseTxPenal_ET.setVisibility(View.GONE);
                    ll_1.setVisibility(View.GONE);
                    ll_btn_1.setVisibility(View.GONE);
                }
                break;
            case R.id.rbEpNatureRupAnPlage:
                if (rbEpNatureRupAnPlage.isChecked()) {
                    EpNatureRupAn="P";



                    ll_1.setVisibility(View.VISIBLE);
                    if (numberOfLinesDebut1<=0){
                        remove_button1.setVisibility(View.INVISIBLE);
                    }
                    EpValTxMtRupture_ET.setVisibility(View.GONE);
                    EpBaseTxPenal_ET.setVisibility(View.GONE);
                    ll_btn_1.setVisibility(View.VISIBLE);

                }


                break;
            case R.id.rbEpTypeNouveauTxInteretRuptureEAPFixe:
                if (rbEpTypNewTxIntRupAntFixe.isChecked()) {
                    EpTypNewTxIntRupAnt="F";


                    EpValTxIntRupant_ET.setVisibility(View.VISIBLE);
                    EpBaseTxIntRupant_ET.setVisibility(View.GONE);
                    ll_2.setVisibility(View.GONE);
                    ll_btn_2.setVisibility(View.GONE);
                }else{
                    EpValTxIntRupant_ET.setVisibility(View.GONE);
                }
                break;
            case R.id.rbEpTypeNouveauTxInteretRuptureEAPPlage:
                if (rbEpTypNewTxIntRupAntPlage.isChecked()) {
                    EpTypNewTxIntRupAnt="P";



                    ll_2.setVisibility(View.VISIBLE);

                    if (numberOfLinesDebut2<=0){
                        remove_button2.setVisibility(View.INVISIBLE);
                    }
                    EpValTxIntRupant_ET.setVisibility(View.GONE);
                    EpBaseTxIntRupant_ET.setVisibility(View.GONE);
                    ll_btn_2.setVisibility(View.VISIBLE);

                }


                break;

            case R.id.rb_EpNaturePenalMontant:
                if (rbEpNaturePenalMontant.isChecked()) {
                    EpNaturePenal="M";


                    EpValTxMtPenalite_ET.setVisibility(View.VISIBLE);
                    EpBaseTxMtPenal_ET.setVisibility(View.GONE);
                    ll_3.setVisibility(View.GONE);
                    ll_btn_3.setVisibility(View.GONE);
                }else{
                    EpValTxMtPenalite_ET.setVisibility(View.GONE);
                }
                break;
            case R.id.rb_EpNaturePenalTaux:
                if (rbEpNaturePenalTaux.isChecked()) {
                    EpNaturePenal="T";


                    EpValTxMtPenalite_ET.setVisibility(View.VISIBLE);
                    EpBaseTxMtPenal_ET.setVisibility(View.VISIBLE);
                    ll_3.setVisibility(View.GONE);
                    ll_btn_3.setVisibility(View.GONE);
                }else{
                    EpValTxMtPenalite_ET.setVisibility(View.GONE);
                    EpBaseTxMtPenal_ET.setVisibility(View.GONE);
                }
                break;
            case R.id.rb_EpNaturePenalPlage:
                if (rbEpNaturePenalPlage.isChecked()) {
                    EpNaturePenal="P";



                    ll_3.setVisibility(View.VISIBLE);

                    if (numberOfLinesDebut3<=0){
                        remove_button3.setVisibility(View.INVISIBLE);
                    }
                    EpValTxMtPenalite_ET.setVisibility(View.GONE);
                    EpBaseTxMtPenal_ET.setVisibility(View.GONE);
                    ll_btn_3.setVisibility(View.VISIBLE);

                }


                break;
//            case R.id.rbEtTypeNouveauTxInteretRuptureEATFixe:
//                if (rbEtTypNewTxIntRupAntFixe.isChecked()) {
//
//                    EtTypNewTxIntRupAnt = "F";
//
//
//                    EtValTxIntPenal_ET.setVisibility(View.VISIBLE);
//                    EtBaseTxIntPenal_ET.setVisibility(View.GONE);
//                    ll_EtValTxIntPenal.setVisibility(View.GONE);
//                    ll_btn_EtValTxIntPenal.setVisibility(View.GONE);
//
//
//                }
//
//                break;
//            case R.id.rbEtTypeNouveauTxInteretRuptureEATPlage:
//                if (rbEtTypNewTxIntRupAntPlage.isChecked()) {
//                    EtTypNewTxIntRupAnt = "P";
//                    ll_EtValTxIntPenal.setVisibility(View.VISIBLE);
//                    if (numberOfLinesDebut_EtTypNewTxIntRupAnt<=0){
//                        remove_button_EtTypNewTxIntRupAnt.setVisibility(View.INVISIBLE);
//                    }
//
//                    EtValTxIntPenal_ET.setVisibility(View.GONE);
//                    EtBaseTxIntPenal_ET.setVisibility(View.GONE);
//                    ll_btn_EtValTxIntPenal.setVisibility(View.VISIBLE);
//
//                }
//
//
//                break;
        }

        // Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
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

            EpCode = EpCode_ET.getText().toString();
            EpLibelle = EpLibelle_ET.getText().toString();
            EpMinMtMise = Ep_MinMtMiseEAP_ET.getText().toString();
            EpDureeMin = EP_DureeMinEAP_ET.getText().toString();

            EpIsMtMultMise = EpIsMtMultMise_SW.isChecked();
            EpIsVersemAntic = EpIsVersemAntic_SW.isChecked();
            EpDureeMax = EpDureeMax_ET.getText().toString();
            //EpNaturePas = ;
            EpNbreUPas = EpNbreUPas_ET.getText().toString();
            //EpTypTxInter = ;
            EpValTxInter = EpValTxInter_ET.getText().toString();
        //    EpPlageTxInterFrom, EpPlageTxInterTo;
            EpIsTxIntNeg = EpIsTxIntNeg_SW.isChecked();
            EpIsPriseIntMiseOn = EpIsPriseIntMiseOn_SW.isChecked();
            EpIsPenalNRespMise = EpIsPenalNRespMise_SW.isChecked();
            EpNbEchPenalOn = EpNbEchPenalOn_ET.getText().toString();
            EpIsEchPenalSucces = EpIsEchPenalSucces_SW.isChecked();
         //   EpNatureRupAn;
            EpValTxMtRupture = EpValTxMtRupture_ET.getText().toString();
           // EpPlageTxMtRuptureFrom,EpPlageTxMtRuptureTo;
            EpBaseTxPenal = EpBaseTxPenal_ET.getText().toString();
            EpIsEparRetireFin = EpIsEparRetireFin_SW.isChecked();
            EpIsEparTransfFin = EpIsEparTransfFin_SW.isChecked();
            EpIsOnlyTotTransf = EpIsOnlyTotTransf_SW.isChecked();
            EpIsEparRenouvFin = EpIsEparRenouvFin_SW.isChecked();
            EpActionDefATerme = EpActionDefATerme_SW.isChecked();
            EpIsMultiEAPOn = EpIsMultiEAPOn_SW.isChecked();
            EpIsInterDusRupAnt = EpIsInterDusRupAnt_SW.isChecked();
            EpIsNewTxIntRupAnt = EpIsNewTxIntRupAnt_SW.isChecked();
           // EpTypNewTxIntRupAnt;
            EpValTxIntRupant = EpValTxIntRupant_ET.getText().toString();
          //  EpPlageTxIntRupantFrom, EpPlageTxIntRupantTo;
            EpBaseTxIntRupant = EpBaseTxIntRupant_ET.getText().toString();
            EpTxIntRupantNeg = EpTxIntRupantNeg_SW.isChecked();
            EpIsPenalRupAnt = EpIsPenalRupAnt_SW.isChecked();
           // EpNaturePenal;
            EpValTxMtPenalite = EpValTxMtPenalite_ET.getText().toString();
           // EpPlageTxMtPenaliteFrom, EpPlageTxMtPenaliteTo;
            EpBaseTxMtPenal = EpBaseTxMtPenal_ET.getText().toString();

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
/*
            EpCode, EpLibelle, EpMtMinMisePer, EpIsMtMultMise, EpIsVersemAntic, EpDureeMin;
            EpDureeMax, EpNaturePas, EpNbreUPas, EpTypTxInter, EpValTxInter;
            EpPlageTxInterFrom, EpPlageTxInterTo;
            */


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
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_eap.php", "POST", httpParams);

        /*    EpIsTxIntNeg, EpIsPriseIntMiseOn, EpIsPenalNRespMise,EpNbEchPenalOn,EpIsEchPenalSucces;
            EpNatureRupAn,EpValTxMtRupture,EpPlageTxMtRuptureFrom,EpPlageTxMtRuptureTo;
            EpBaseTxPenal,EpIsEparRetireFin,EpIsEparTransfFin,EpIsOnlyTotTransf,EpIsEparRenouvFin;
            EpActionDefATerme,EpIsMultiEAPOn,EpIsInterDusRupAnt,EpIsNewTxIntRupAnt,EpTypNewTxIntRupAnt;
            EpValTxIntRupant, EpPlageTxIntRupantFrom, EpPlageTxIntRupantTo;
            EpBaseTxIntRupant, EpTxIntRupantNeg, EpIsPenalRupAnt, EpNaturePenal, EpValTxMtPenalite;
            EpPlageTxMtPenaliteFrom, EpPlageTxMtPenaliteTo, EpBaseTxMtPenal*/
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