package com.example.binumtontine.activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.example.binumtontine.modele.ProduitEAP;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateEAP extends AppCompatActivity implements SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String EP_CAISSE_ID = "EpCaisseId";



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

    private JRSpinner JR_EpBaseTxInter;
    private TextInputLayout input_layout_EpBaseTxInter;
    private TextInputLayout input_layout_ValeurTauxInteretEAP;
    private String EpBaseTxInter;

    private JRSpinner EpBaseTxPenal_ET;
    private TextView tv_plageEpValTxInter;
    private TextView tv_plageEpValTxMtRupture_ET;
    private TextView tv_plageEpValTxIntRupant;
    private TextView tv_plageEpValTxMtPenalite;
    private TextInputLayout input_layout_BaseTxPenalEAP;
    private TextInputLayout input_layout_BaseNewTxInteretEAP;
    private TextInputLayout input_layout_EpBaseTxPenal;

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


    private RadioButton rb_CtModRenouv_transfert_vers_eav;
    private RadioButton rb_CtModRenouv_renouveller;
    private RadioButton rb_CtModRenouv_retirer;
    private RadioButton rb_CtModRenouv_ras;

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
    private JRSpinner EpBaseTxMtPenal_ET;
    private String EpIsMtMultMise;
    private String EpIsVersemAntic;
    private String EpDureeMax;
    private String EpNaturePas;
    private String EpNbreUPas;
    private String EpTypTxInter;
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

    public static String eapId;
    private static final String KEY_EAP_ID = "EpNumero";
    private static final String KEY_DATA = "data";
    private TextView headerEAPTextView;
    private TextView tv_header_produit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eap);

        Intent intent = getIntent();
        headerEAPTextView = (TextView) findViewById(R.id.header_eap);
        headerEAPTextView.setText("Mise à jour EAP");

        tv_header_produit = (TextView) findViewById(R.id.header_produit);
        tv_header_produit.setText("Produit EAP\n"+"Caisse: "+ MyData.CAISSE_NAME);

        ll_EcheancePenaliteSuccessiveEAP = (LinearLayout)findViewById(R.id.ll_EcheancePenaliteSuccessiveEAP);
        ll_NatureFraisPenaliteEAP = (LinearLayout)findViewById(R.id.ll_NatureFraisPenaliteEAP);

        ll_DefinirNouveauTxInteretEAP = (LinearLayout)findViewById(R.id.ll_DefinirNouveauTxInteretEAP);
        ll_TypeNouveauTxInteretRuptureEAP = (LinearLayout)findViewById(R.id.ll_TypeNouveauTxInteretRuptureEAP);
        ll_TxNewInteretNegociableEAP = (LinearLayout)findViewById(R.id.ll_TxNewInteretNegociableEAP);
        ll_EpNaturePenal = (LinearLayout)findViewById(R.id.ll_EpNaturePenal);

        EpCode_ET = (EditText) findViewById(R.id.input_txt_Code_EAP);
        EpCode_ET.setEnabled(false);
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

        EpIsTxIntNeg_SW = (Switch) findViewById(R.id.SwitchTauxInteretNegocieEAP);
        EpIsPriseIntMiseOn_SW = (Switch) findViewById(R.id.SwitchPrendreInteretDesLaMiseEAP);
        EpIsPenalNRespMise_SW = (Switch) findViewById(R.id.SwitchPenaliteEnCasDeRuptureEAP);
        EpNbEchPenalOn_ET = (EditText) findViewById(R.id.input_txt_EpNbEchPenalOn);
        EpIsEchPenalSucces_SW = (Switch) findViewById(R.id.SwitchEcheancePenaliteSuccessiveEAP);
        rbEpNatureRupAnTaux = (RadioButton) findViewById(R.id.rbEpNatureRupAnTaux);
        rbEpNatureRupAnMontant = (RadioButton) findViewById(R.id.rbEpNatureRupAnMontant);
        rbEpNatureRupAnPlage = (RadioButton) findViewById(R.id.rbEpNatureRupAnPlage);

        EpValTxMtRupture_ET = (EditText) findViewById(R.id.input_txt_ValTxMtRuptureEAP);


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

        EpIsEparRetireFin_SW = (Switch) findViewById(R.id.SwitchPossibiliteRetirerEAP);
        EpIsEparTransfFin_SW = (Switch) findViewById(R.id.SwitchPossibiliteTransfererVersEAV_EAP);
        EpIsOnlyTotTransf_SW = (Switch) findViewById(R.id.SwitchTransfererTotaliteVersEAV_EAP);
        EpIsEparRenouvFin_SW = (Switch) findViewById(R.id.SwitchPossibiliteRenouvelerEAP);
        EpIsMultiEAPOn_SW = (Switch) findViewById(R.id.SwitchMultiEAP);
        EpIsInterDusRupAnt_SW = (Switch) findViewById(R.id.SwitchInteretDusRuptureEAP);
        EpIsNewTxIntRupAnt_SW = (Switch) findViewById(R.id.SwitchDefinirNouveauTxInteretEAP);
        //EpIsNewTxIntRupAnt_SW = (Switch) findViewById(R.id.SwitchDefinirNouveauTxInteretEAP);


        rbEpTypNewTxIntRupAntFixe = (RadioButton) findViewById(R.id.rbEpTypeNouveauTxInteretRuptureEAPFixe);
        rbEpTypNewTxIntRupAntPlage = (RadioButton) findViewById(R.id.rbEpTypeNouveauTxInteretRuptureEAPPlage);

        EpValTxIntRupant_ET = (EditText) findViewById(R.id.txtValeurNewTxInteretRuptureEAP);
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
        EpTxIntRupantNeg_SW = (Switch) findViewById(R.id.SwitchTxNewInteretNegociableEAP);
        EpIsPenalRupAnt_SW = (Switch) findViewById(R.id.SwitchPenaliteDeblocageEnCasDeRuptureEAP);

        rbEpNaturePenalTaux = (RadioButton) findViewById(R.id.rb_EpNaturePenalTaux);
        rbEpNaturePenalMontant = (RadioButton) findViewById(R.id.rb_EpNaturePenalMontant);
        rbEpNaturePenalPlage = (RadioButton) findViewById(R.id.rb_EpNaturePenalPlage);

        EpValTxMtPenalite_ET = (EditText) findViewById(R.id.txtEpValTxMtPenalite);

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

        rb_CtModRenouv_transfert_vers_eav = (RadioButton) findViewById(R.id.rb_CtModRenouv_transfert_vers_eav); //29_a
        rb_CtModRenouv_renouveller = (RadioButton) findViewById(R.id.rb_CtModRenouv_renouveller); //29_b
        rb_CtModRenouv_retirer = (RadioButton) findViewById(R.id.rb_CtModRenouv_retirer); //29_c
        rb_CtModRenouv_ras = (RadioButton) findViewById(R.id.rb_CtModRenouv_ras); //29_d

        //Plage
        //TEP
        tv_plageEpValTxMtRupture_ET = (TextView) findViewById(R.id.tv_plageEpValTxMtRupture_ET);
        tv_plageEpValTxMtRupture_ET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux pénalité";
                    ListPlageTEP.IS_TO_CREATE_OR_TO_UPDATE = false;
                    Intent i = new Intent(UpdateEAP.this,ListPlageTEP.class);
                    i.putExtra(KEY_EAP_ID, eapId);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(UpdateEAP.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        //TIP
        tv_plageEpValTxInter = (TextView) findViewById(R.id.tv_plageEpValTxInter);
        tv_plageEpValTxInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Taux d'intérêt";
                    ListPlageTIP.IS_TO_CREATE_OR_TO_UPDATE = false;
                    Intent i = new Intent(UpdateEAP.this,ListPlageTIP.class);
                    i.putExtra(KEY_EAP_ID, eapId);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(UpdateEAP.this,
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
                    ListPlageNTP.IS_TO_CREATE_OR_TO_UPDATE = false;
                    Intent i = new Intent(UpdateEAP.this,ListPlageNTP.class);
                    i.putExtra(KEY_EAP_ID, eapId);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(UpdateEAP.this,
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
                    ListPlageRAP.IS_TO_CREATE_OR_TO_UPDATE = false;
                    Intent i = new Intent(UpdateEAP.this,ListPlageRAP.class);
                    i.putExtra(KEY_EAP_ID, eapId);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(UpdateEAP.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });


/*
        onRadioButtonClicked(rbEpNaturePasFixe);
        onRadioButtonClicked(rbEpTypTxInterFixe);
        onRadioButtonClicked(rbEpNatureRupAnTaux);
        onRadioButtonClicked(rbEpTypNewTxIntRupAntFixe);
        onRadioButtonClicked(rbEpNaturePenalMontant);

        onSwitchButtonClicked(EpIsPenalNRespMise_SW);
        onSwitchButtonClicked(EpIsInterDusRupAnt_SW);
        onSwitchButtonClicked(EpIsNewTxIntRupAnt_SW);
        onSwitchButtonClicked(EpIsPenalRupAnt_SW);
*/
        //onRadioButtonClicked();
        deleteButton = (Button) findViewById(R.id.btn_delete_eap);
        eapId = intent.getStringExtra(KEY_EAP_ID);
        new FetchEapDetailsAsyncTask().execute();
        deleteButton.setVisibility(View.VISIBLE);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete();
            }
        });
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
                    Toast.makeText(UpdateEAP.this,
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
                    updateEAP();
                } else {
                    Toast.makeText(UpdateEAP.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

    }


    /**
     * Fetches single movie details from the server
     */
    private class FetchEapDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateEAP.this);
            pDialog.setMessage("Loading EAP Details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_EAP_ID, eapId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_eap_details.php", "GET", httpParams);
//            Log.e("jsonObject**",jsonObject+"");
            try {
                Log.e("httpParams",jsonObject+"");
//                int success = jsonObject.getInt(KEY_SUCCESS);
                //JSONObject jsonObject;
//                if (success == 1) {
                    //Parse the JSON response
//                    eav = jsonObject.getJSONObject(KEY_DATA);
                    EpCode = MyData.normalizeSymbolsAndAccents(jsonObject.getString(KEY_EAP_CODE));
                    EpLibelle = MyData.normalizeSymbolsAndAccents(jsonObject.getString(KEY_EAP_LIBELLE));
//                    EpLibelle = URLEncoder.encode(jsonObject.getString(KEY_EAP_LIBELLE),"UTF-8");
                    EpMinMtMise = jsonObject.getString(KEY_EAP_MT_MIN_MISE_PER);
                    EpDureeMin = jsonObject.getString(KEY_EAP_DUREE_MIN);
                    EpIsMtMultMise = jsonObject.getString(KEY_EAP_IS_MT_MULT_MISE);
                    EpIsVersemAntic = jsonObject.getString(KEY_EAP_IS_VERSEM_ANTIC);
                    EpDureeMax = jsonObject.getString(KEY_EAP_DUREE_MAX);
                    EpNaturePas = jsonObject.getString(KEY_EAP_NATURE_PAS);
                    EpNbreUPas = jsonObject.getString(KEY_EAP_NBRE_U_PAS);
                    EpTypTxInter = jsonObject.getString(KEY_EAP_TYP_TX_INTER);
                    EpValTxInter = jsonObject.getString(KEY_EpValTxInter);
                    EpBaseTxInter = ProduitEAP.decodeEpBaseTxInter(jsonObject.getString(KEY_EpBaseTxInter));//Add at 04/11/2020
                    EpIsTxIntNeg = jsonObject.getString(KEY_EpIsTxIntNeg);
                    EpIsPriseIntMiseOn = jsonObject.getString(KEY_EpIsPriseIntMiseOn);
                    EpIsPenalNRespMise = jsonObject.getString(EP_IsPenalNRespMise);
                    EpNbEchPenalOn = jsonObject.getString(EP_NbEchPenalOn);
                    EpIsEchPenalSucces = jsonObject.getString(EP_IsEchPenalSucces);
                    EpNatureRupAn = jsonObject.getString(EP_NatureRupAn);
                    EpValTxMtRupture = jsonObject.getString(EP_ValTxMtRupture);
                    EpBaseTxPenal = ProduitEAP.decodeEpBaseTxPenal(jsonObject.getString(EP_BaseTxPenal));
                    EpIsEparRetireFin = jsonObject.getString(EP_IsEparRetireFin);
                    EpIsEparTransfFin = jsonObject.getString(EP_IsEparTransfFin);
                    EpIsOnlyTotTransf = jsonObject.getString(EP_IsOnlyTotTransf);
                    EpIsEparRenouvFin = jsonObject.getString(EP_IsEparRenouvFin);
                    EpActionDefATerme = jsonObject.getString(EP_ActionDefATerme);
                    EpIsMultiEAPOn = jsonObject.getString(EP_IsMultiEAPOn);
                    EpIsInterDusRupAnt = jsonObject.getString(EP_IsInterDusRupAnt);
                    EpIsNewTxIntRupAnt = jsonObject.getString(EP_IsNewTxIntRupAnt);
                    EpTypNewTxIntRupAnt = jsonObject.getString(EP_TypNewTxIntRupAnt);
                    EpValTxIntRupant = jsonObject.getString(EP_ValTxIntRupant);
                    EpBaseTxIntRupant = ProduitEAP.decodeEpBaseTxIntRupant(jsonObject.getString(EP_BaseTxIntRupant));
                    EpTxIntRupantNeg = jsonObject.getString(EP_TxIntRupantNeg);
                    EpIsPenalRupAnt = jsonObject.getString(EP_IsPenalRupAnt);
                    EpNaturePenal = jsonObject.getString(EP_NaturePenal);
                    EpValTxMtPenalite = jsonObject.getString(EP_ValTxMtPenalite);
                    EpBaseTxMtPenal = ProduitEAP.decodeEpBaseTxMtPenal(jsonObject.getString(EP_BaseTxMtPenal));





//                }
            } catch (Exception  e) {
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


    EpCode_ET.setText(EpCode);
    EpLibelle_ET.setText(EpLibelle);
    Ep_MinMtMiseEAP_ET.setText(EpMinMtMise);
    EP_DureeMinEAP_ET.setText(EpDureeMin);

    if (EpIsMtMultMise.equals("Y")) {
        EpIsMtMultMise_SW.setChecked(true);
    } else {
        EpIsMtMultMise_SW.setChecked(false);
    }
    if (EpIsVersemAntic.equals("Y")) {
        EpIsVersemAntic_SW.setChecked(true);
    } else {
        EpIsVersemAntic_SW.setChecked(false);
    }
    EpDureeMax_ET.setText(EpDureeMax);
    //EpNaturePas = ;
    EpNbreUPas_ET.setText(EpNbreUPas);
    //EpTypTxInter = ;
    EpValTxInter_ET.setText(EpValTxInter);
    //    EpPlageTxInterFrom, EpPlageTxInterTo;
    if (EpIsTxIntNeg.equals("Y")) {
        EpIsTxIntNeg_SW.setChecked(true);
    } else {
        EpIsTxIntNeg_SW.setChecked(false);
    }
    if (EpIsPriseIntMiseOn.equals("Y")) {
        EpIsPriseIntMiseOn_SW.setChecked(true);
    } else {
        EpIsPriseIntMiseOn_SW.setChecked(false);
    }
    if (EpIsPenalNRespMise.equals("Y")) {
        EpIsPenalNRespMise_SW.setChecked(true);
    } else {
        EpIsPenalNRespMise_SW.setChecked(false);
    }
    EpNbEchPenalOn_ET.setText(EpNbEchPenalOn);
    if (EpIsEchPenalSucces.equals("Y")) {
        EpIsEchPenalSucces_SW.setChecked(true);
    } else {
        EpIsEchPenalSucces_SW.setChecked(false);
    }
    //   EpNatureRupAn;
    EpValTxMtRupture_ET.setText(EpValTxMtRupture);
    // EpPlageTxMtRuptureFrom,EpPlageTxMtRuptureTo;
    EpBaseTxPenal_ET.setText(EpBaseTxPenal);
    if (EpIsEparRetireFin.equals("Y")) {
        EpIsEparRetireFin_SW.setChecked(true);
    } else {
        EpIsEparRetireFin_SW.setChecked(false);
    }
    if (EpIsEparTransfFin.equals("Y")) {
        EpIsEparTransfFin_SW.setChecked(true);
    } else {
        EpIsEparTransfFin_SW.setChecked(false);
    }
    if (EpIsOnlyTotTransf.equals("Y")) {
        EpIsOnlyTotTransf_SW.setChecked(true);
    } else {
        EpIsOnlyTotTransf_SW.setChecked(false);
    }
    if (EpIsEparRenouvFin.equals("Y")) {
        EpIsEparRenouvFin_SW.setChecked(true);
    } else {
        EpIsEparRenouvFin_SW.setChecked(false);
    }

    if (EpActionDefATerme.equals("TCV")) {
        rb_CtModRenouv_transfert_vers_eav.setChecked(true);
    } else if (EpActionDefATerme.equals("RAE")) {
        rb_CtModRenouv_renouveller.setChecked(true);
    } else if (EpActionDefATerme.equals("CTR")) {
        rb_CtModRenouv_retirer.setChecked(true);
    } else if (EpActionDefATerme.equals("RAS")) {
        rb_CtModRenouv_ras.setChecked(true);
    }

    if (EpIsMultiEAPOn.equals("Y")) {
        EpIsMultiEAPOn_SW.setChecked(true);
    } else {
        EpIsMultiEAPOn_SW.setChecked(false);
    }
    if (EpIsInterDusRupAnt.equals("Y")) {
        EpIsInterDusRupAnt_SW.setChecked(true);
    } else {
        EpIsInterDusRupAnt_SW.setChecked(false);
    }
    if (EpIsNewTxIntRupAnt.equals("Y")) {
        EpIsNewTxIntRupAnt_SW.setChecked(true);
    } else {
        EpIsNewTxIntRupAnt_SW.setChecked(false);
    }
    // EpTypNewTxIntRupAnt;
    EpValTxIntRupant_ET.setText(EpValTxIntRupant);
    //  EpPlageTxIntRupantFrom, EpPlageTxIntRupantTo;
    EpBaseTxIntRupant_ET.setText(EpBaseTxIntRupant);

    if (EpTxIntRupantNeg.equals("Y")) {
        EpTxIntRupantNeg_SW.setChecked(true);
    } else {
        EpTxIntRupantNeg_SW.setChecked(false);
    }
    if (EpIsPenalRupAnt.equals("Y")) {
        EpIsPenalRupAnt_SW.setChecked(true);
    } else {
        EpIsPenalRupAnt_SW.setChecked(false);
    }
    // EpNaturePenal;
    EpValTxMtPenalite_ET.setText(EpValTxMtPenalite);
    // EpPlageTxMtPenaliteFrom, EpPlageTxMtPenaliteTo;
    EpBaseTxMtPenal_ET.setText(EpBaseTxMtPenal);

    if (EpNaturePas.equals("F")){
        rbEpNaturePasFixe.setChecked(true);
        onRadioButtonClicked(rbEpNaturePasFixe);
    }else if (EpNaturePas.equals("S")){
        rbEpNaturePasSaut.setChecked(true);
        onRadioButtonClicked(rbEpNaturePasSaut);
    }

    if (EpTypTxInter.equals("F")){
        rbEpTypTxInterFixe.setChecked(true);
        onRadioButtonClicked(rbEpTypTxInterFixe);
    }else if (EpTypTxInter.equals("P")){
        rbEpTypTxInterPlage.setChecked(true);
        onRadioButtonClicked(rbEpTypTxInterPlage);
    }
    if (EpNatureRupAn.equals("T")){
        rbEpNatureRupAnTaux.setChecked(true);
        onRadioButtonClicked(rbEpNatureRupAnTaux);
    }else if (EpNatureRupAn.equals("M")){
        rbEpNatureRupAnMontant.setChecked(true);
        onRadioButtonClicked(rbEpNatureRupAnMontant);
    }else if (EpNatureRupAn.equals("P")){
        rbEpNatureRupAnPlage.setChecked(true);
        onRadioButtonClicked(rbEpNatureRupAnPlage);
    }
    if (EpTypNewTxIntRupAnt.equals("F")){
        rbEpTypNewTxIntRupAntFixe.setChecked(true);
        onRadioButtonClicked(rbEpTypNewTxIntRupAntFixe);
    }else if (EpTypNewTxIntRupAnt.equals("P")){
        rbEpTypNewTxIntRupAntPlage.setChecked(true);
        onRadioButtonClicked(rbEpTypNewTxIntRupAntPlage);
    }
    if (EpNaturePenal.equals("T")){
        rbEpNaturePenalTaux.setChecked(true);
        onRadioButtonClicked(rbEpNaturePenalTaux);
    }else if (EpNaturePenal.equals("M")){
        rbEpNaturePenalMontant.setChecked(true);
        onRadioButtonClicked(rbEpNaturePenalMontant);
    }else if (EpNaturePenal.equals("P")){
        rbEpNaturePenalPlage.setChecked(true);
        onRadioButtonClicked(rbEpNaturePenalPlage);
    }


/*
    onRadioButtonClicked(rbEpNatureRupAnTaux);
    onRadioButtonClicked(rbEpTypNewTxIntRupAntFixe);
    onRadioButtonClicked(rbEpNaturePenalMontant);
    onRadioButtonClicked(rb_CtModRenouv_transfert_vers_eav);*/

    onSwitchButtonClicked(EpIsPenalNRespMise_SW);
    onSwitchButtonClicked(EpIsInterDusRupAnt_SW);
    onSwitchButtonClicked(EpIsNewTxIntRupAnt_SW);
    onSwitchButtonClicked(EpIsPenalRupAnt_SW);
}catch (Exception e){
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
                UpdateEAP.this);
        alertDialogBuilder.setMessage("Voulez-vous vraiment supprimer cet EAP ?");
        alertDialogBuilder.setPositiveButton("Supprimer",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                            //If the user confirms deletion, execute DeleteMovieAsyncTask
                            new UpdateEAP.DeleteMovieAsyncTask().execute();
                        } else {
                            Toast.makeText(UpdateEAP.this,
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
            pDialog = new ProgressDialog(UpdateEAP.this);
            pDialog.setMessage("Deleting EAP. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Set movie_id parameter in request
            httpParams.put(KEY_EAP_ID, eapId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "delete_eap.php", "POST", httpParams);
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
                        Toast.makeText(UpdateEAP.this,
                                "EAP Deleted", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie deletion
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(UpdateEAP.this,
                                "Some error occurred while deleting EAP",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
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
                    str = checked1?"Intérêts dûs  en cas de rupture anticipée activé":"Intérêts dûs  en cas de rupture anticipée désactivé";
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
                    str = checked1?"Pénalité en cas de non respect des échéances activée":"Pénalité en cas de non respect des échéances désactivée";

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
                    str = checked1?"Pénalité en cas de non respect des échéances activée":"Pénalité en cas de non respect des échéances désactivée";
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
     * Checks whether all files are filled. If so then calls UpdateEapAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void updateEAP() {
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
            EpBaseTxMtPenal = EpBaseTxMtPenal_ET.getText().toString();
            EpBaseTxMtPenal = ProduitEAP.encodeEpBaseTxMtPenal(EpBaseTxMtPenal_ET.getText().toString());

            new UpdateEapAsyncTask().execute();
        } else {
            Toast.makeText(UpdateEAP.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for adding a movie
     */
    private class UpdateEapAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(UpdateEAP.this);
            pDialog.setMessage("Mise à jour EAP. Veuillez patienter...");
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


            httpParams.put(KEY_EAP_ID, eapId);
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
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "update_eap.php", "POST", httpParams);


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
                        Toast.makeText(UpdateEAP.this,
                                "EAP modifié", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(UpdateEAP.this,
                                "Some error occurred while updating EAP",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}