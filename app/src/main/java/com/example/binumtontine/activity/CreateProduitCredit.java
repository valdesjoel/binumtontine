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

public class CreateProduitCredit extends AppCompatActivity implements SERVER_ADDRESS {
    private Credit monProduitCredit;
    private static final String KEY_SUCCESS = "success";

    private static final String KEY_EAV_CODE = "ev_code";
    private static final String KEY_EAV_LIBELLE = "ev_libelle";
    private static final String KEY_EAV_MIN_CPTE = "ev_min_cpte";
    private static final String KEY_EAV_IS_MIN_CPTE_OBLIG = "ev_is_min_cpte_oblig";
    private static final String KEY_EAV_TX_INTER_AN = "ev_tx_inter_an";
    private static final String KEY_EAV_IS_TX_INTER_AN_OBLIG = "ev_is_tx_inter_an_oblig";
    private static final String KEY_EAV_TYP_DAT_VAL = "ev_typ_dat_val";
    private static final String KEY_EAV_TYP_DAT_RETRAIT_VAL = "ev_typ_dat_retrait_val";
    private static final String KEY_EAV_IS_MULTI_EAV_ON = "ev_is_multi_eav_on";
    private static final String KEY_EAV_IS_PAIE_PS_ON = "ev_is_paie_ps_on";
    private static final String KEY_EAV_IS_AGIOS_ON = "ev_is_agios_on";
    private static final String KEY_EAV_TYP_FR_AGIOS = "ev_typ_fr_agios";
    private static final String KEY_EAV_MT_TX_AGIOS_PRELEV = "ev_mt_tx_agios_prelev";
    private static final String KEY_EAV_PLAGE_AGIOS_FROM = "ev_plage_agios_from";
    private static final String KEY_EAV_PLAGE_AGIOS_TO = "ev_plage_agios_to";
    private static final String KEY_EAV_IS_CHEQUE_ON = "ev_is_cheque_on";
    private static final String KEY_EAV_FRAIS_CLOT_CPT = "ev_frais_clot_cpt";
    private static final String KEY_EAV_CX_NUMERO = "ev_caisse_id";


    private static final String KEY_EAV_PLAGE_FRAIS_DE_TENUE_DE_COMPTE_DEBUT = "EvTivDebut";
    private static final String KEY_EAV_PLAGE_FRAIS_DE_TENUE_DE_COMPTE_FIN = "EvTivFin";
    private static final String KEY_EAV_PLAGE_FRAIS_DE_TENUE_DE_COMPTE_VALEUR = "EvTivValeur";
    private static final String KEY_EAV_PLAGE_FRAIS_DE_TENUE_DE_COMPTE_BASE = "EvTivBase";
    private static final String KEY_EAV_PLAGE_FRAIS_DE_TENUE_DE_COMPTE_NATURE = "EvTivNature";


    private String tabPlageDebut ="";
    private String tabPlageFin ="";
    private String tabPlageValeur ="";
    private String tabPlageBase ="";
    private String tabPlageNature ="";





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
    private RadioButton rbCrTypTxInterFixe;
    private RadioButton rbCrTypTxInterMontant;
    private RadioButton rbCrTypTxInterDuree;
    private RadioButton rbCrTypTxInterMontantDuree;
//    private String CrTypTxInter;
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
//    private String CrNatFrEtudDoss;
    private EditText ET_CrValTxFrEtudDoss;
    private EditText ET_CrBaseTxFrEtudDoss;
    private Switch SW_CrIsFraisDeblocCredOn;

    private RadioButton rbCrNatFraisDeblocCredFixe;
    private RadioButton rbCrNatFraisDeblocCredTaux;
    private RadioButton rbCrNatFraisDeblocCredPlage;
//    private String CrNatFraisDeblocCred;
    private EditText ET_CrValTxFraisDeblocCred;
    private EditText ET_CrBaseTxFraisDeblocCred;
    private Switch SW_CrIsFraisDecaissCredOn;

    private RadioButton rbCrNatFraisDecaissCredFixe;
    private RadioButton rbCrNatFraisDecaissCredTaux;
    private RadioButton rbCrNatFraisDecaissCredPlage;
//    private String CrNatFraisDecaissCred;
    private EditText ET_CrValTxFraisDecaissCred;
    private EditText ET_CrBaseFraisDecaissCred;
    private Switch SW_CrIsFraisEtudByDAV;
    private Switch SW_CrIsFraisDeblocByDAV;
    private Switch SW_CrIsFraisDecaissByDAV;
    private Switch SW_CrIsModDecaissByObjet;
    private Switch SW_CrIsDeblocTransfDAVOn;
    private Switch SW_CrIsMtPlafByObjet;
    private EditText ET_CrModeRemb;
    private Switch SW_CrIsCptEATRemCredOn;
    private Switch SW_CrIsCptEAPRemCredOn;
    private Switch SW_CrIsInterOffSiCapRembAnt;
    private EditText ET_CrTxInterEchNHon;
    private EditText ET_CrBaseInterEchNHon;
    private EditText ET_CrPlanningRembCred;
    private Switch SW_CrIsRappDatEchCred;
    private EditText ET_CrModelTextRappEchRemb;
    private EditText ET_CrNbreJrAvantDatEch;
    private EditText ET_CrNbreJrApreEchSiNHon;
//    private String CrUser;
//    private String CrDateHCree;
//    private String CrUserModif;
//    private String CrDatHModif;
//    private String CrCaisseId;
//    private String CrGuichetId;


//    private EditText ev_codeEditText;
//    private EditText ev_libelleEditText;
//    private EditText ev_min_cpteEditText;
//    private Switch ev_is_min_cpte_obligSwitch;
//    private EditText ev_tx_inter_anEditText;
//    private Switch ev_is_tx_inter_an_obligSwitch;
//    private EditText ev_typ_dat_valEditText;
//    private EditText ev_typ_dat_retrait_valEditText;
//    private Switch ev_is_multi_eav_onSwitch;
//    private Switch ev_is_paie_ps_onSwitch;
//    private Switch ev_is_agios_onSwitch;
//   // private EditText ev_typ_fr_agiosEditText;RadioButton
//    private RadioButton ev_typ_fr_agiosEditText;
//    private EditText ev_mt_tx_agios_prelevEditText;
//    private EditText ev_plage_agios_fromEditText;
//    private EditText ev_plage_agios_toEditText;
//    private Switch ev_is_cheque_onSwitch;
//    private EditText ev_frais_clot_cptEditText;
//
//    private String ev_code;
//    private String ev_libelle;
//    private String ev_min_cpte;
//    private Boolean ev_is_min_cpte_oblig;
//    private String ev_tx_inter_an;
//    private Boolean ev_is_tx_inter_an_oblig;
//    private String ev_typ_dat_val;
//    private String ev_typ_dat_retrait_val;
//    private Boolean ev_is_multi_eav_on;
//    private Boolean ev_is_paie_ps_on;
//    private Boolean ev_is_agios_on;
//    private String ev_typ_fr_agios;
//    private String ev_mt_tx_agios_prelev;
//    private String base_ev_mt_tx_agios_prelev;
//    private String base_ev_tx_inter_an;
//    private String ev_plage_agios_from;
//    private String ev_plage_agios_to;
//    private Boolean ev_is_cheque_on;
//    private String ev_frais_clot_cpt;
    private int ev_caisse_id = MyData.CAISSE_ID;

    private LinearLayout blkPlageEav;
    private LinearLayout LL_TypeFraisCpteEAV;

    private RadioButton rbEpTypTxInterFixe;
    private RadioButton rbEpTypTxInterTaux;
    private RadioButton rbEpTypTxInterPlage;
    private TextInputLayout layout_TauxAPreleveCpteEAV;
    private TextInputLayout layout_MinCompteEAV;
    private TextInputLayout layout_TauxInteretAnnuelEAV;
    private TextInputLayout layout_DateValeur;
    private TextInputLayout layout_DateRetrait;
    private TextInputLayout layout_BaseTauxAPreleveCpteEAV;
    private TextInputLayout layout_BaseInteretAnnuelEAV;

    private JRSpinner mySpinnerLocalite; //pour gérer le spinner contenant les localités
    private JRSpinner mySpinnerBaseTxIntMensuel; //pour gérer le spinner contenant les localités

    private Button addButton;
    private Button cancelButton;
    private Button deleteButton;
    private int success;
    private ProgressDialog pDialog;

    private TextView tv_header_produit;
    public static ArrayList<ModelPlageData> plageDataList; //to manage plageData
    private TextView tv_config_plage_tiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_add_movie);
      //  setContentView(R.layout.fragment_param_produit_eav);
        setContentView(R.layout.activity_credit);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_produit_eav);
        setSupportActionBar(toolbar);
        setToolbarTitle(); */


        plageDataList = new ArrayList<>();
        tv_header_produit = (TextView) findViewById(R.id.header_produit);
        tv_header_produit.setText("Produit Crédit\n"+"Caisse: "+MyData.CAISSE_NAME);
//        tv_config_plage_tiv = (TextView) findViewById(R.id.tv_plage_tiv_eav);
        ET_CrCode = (EditText) findViewById(R.id.input_txt_Code_credit);
        ET_CrLibelle = (EditText) findViewById(R.id.input_txt_LibelleCredit);
        ET_CrDureeMax = (EditText) findViewById(R.id.input_txt_CrDureeMax);
        ET_CrDureeMin = (EditText) findViewById(R.id.input_txt_CrDureeMin);
//        rbCrNaturePasFixe = (RadioButton)findViewById(R.id.rbCrNaturePasFixe);
//        rbCrNaturePasSaut = (RadioButton)findViewById(R.id.rbCrNaturePasSaut);
//        ET_CrNbreUPas = (RadioButton)findViewById(R.id.rbCrNaturePasSaut);
      /*  rbCrTypTxInterFixe = (RadioButton)findViewById(R.id.rbCrNaturePasSaut);
        rbCrTypTxInterDuree = (RadioButton)findViewById(R.id.rbCrNaturePasSaut);
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
        ET_CrBaseTxFrEtudDoss = (EditText) findViewById(R.id.spn_my_spinner_base_tauxCrNatFrEtudDoss); //A revoir car c'est un JRSpinner
        SW_CrIsFraisDeblocCredOn = (Switch) findViewById(R.id.SwitchCrIsFraisDeblocCredOn);

        rbCrNatFraisDeblocCredFixe = (RadioButton) findViewById(R.id.rbCrNatFraisDeblocCredFixe);
        rbCrNatFraisDeblocCredTaux = (RadioButton) findViewById(R.id.rbCrNatFraisDeblocCredTaux);
        rbCrNatFraisDeblocCredPlage = (RadioButton) findViewById(R.id.rbCrNatFraisDeblocCredPlage);
        ET_CrValTxFraisDeblocCred = (EditText) findViewById(R.id.input_txt_CrValTxFraisDeblocCred);
        ET_CrBaseTxFraisDeblocCred = (EditText) findViewById(R.id.spn_my_spinner_CrBaseTxFraisDeblocCred); //A revoir car c'est un JRSpinner

        SW_CrIsFraisDecaissCredOn = (Switch) findViewById(R.id.SwitchCrIsFraisDecaissCredOn);

        rbCrNatFraisDecaissCredFixe = (RadioButton) findViewById(R.id.rbCrNatFraisDecaissCredFixe);
        rbCrNatFraisDecaissCredTaux = (RadioButton) findViewById(R.id.rbCrNatFraisDecaissCredTaux);
        rbCrNatFraisDecaissCredPlage = (RadioButton) findViewById(R.id.rbCrNatFraisDecaissCredPlage);
        ET_CrValTxFraisDecaissCred = (EditText) findViewById(R.id.input_txt_CrValTxFraisDecaissCred);
        ET_CrBaseFraisDecaissCred = (EditText) findViewById(R.id.spn_my_spinner_CrBaseFraisDecaissCred); //A revoir car c'est un JRSpinner
        SW_CrIsFraisEtudByDAV = (Switch) findViewById(R.id.SwitchCrIsFraisEtudByDAV);
        SW_CrIsFraisDeblocByDAV = (Switch) findViewById(R.id.SwitchCrIsFraisDeblocByDAV);
        SW_CrIsFraisDecaissByDAV = (Switch) findViewById(R.id.SwitchCrIsFraisDecaissByDAV);
        SW_CrIsModDecaissByObjet = (Switch) findViewById(R.id.SwitchCrIsModDecaissByObjet);
        SW_CrIsDeblocTransfDAVOn = (Switch) findViewById(R.id.SwitchCrIsDeblocTransfDAVOn);
        SW_CrIsMtPlafByObjet = (Switch) findViewById(R.id.SwitchCrIsMtPlafByObjet);
        ET_CrModeRemb = (EditText) findViewById(R.id.spn_my_spinner_CrModeRemb); //A revoir car c'est un JRSpinner
        SW_CrIsCptEATRemCredOn = (Switch) findViewById(R.id.SwitchCrIsCptEATRemCredOn);
        SW_CrIsCptEAPRemCredOn = (Switch) findViewById(R.id.SwitchCrIsCptEAPRemCredOn);
        SW_CrIsInterOffSiCapRembAnt = (Switch) findViewById(R.id.SwitchCrIsInterOffSiCapRembAnt);
        ET_CrTxInterEchNHon = (EditText) findViewById(R.id.input_txt_CrTxInterEchNHon);
        ET_CrBaseInterEchNHon = (EditText) findViewById(R.id.spn_my_spinner_CrBaseInterEchNHon);//A revoir car c'est un JRSpinner
        ET_CrPlanningRembCred = (EditText) findViewById(R.id.spn_my_spinner_CrPlanningRembCred);//A revoir car c'est un JRSpinner
        SW_CrIsRappDatEchCred = (Switch) findViewById(R.id.SwitchCrIsRappDatEchCred);
        ET_CrModelTextRappEchRemb = (EditText) findViewById(R.id.input_txt_CrModelTextRappEchRemb);
        ET_CrNbreJrAvantDatEch = (EditText) findViewById(R.id.input_txt_CrNbreJrAvantDatEch);
        ET_CrNbreJrApreEchSiNHon = (EditText) findViewById(R.id.input_txt_CrNbreJrApreEchSiNHon);

//        ev_libelleEditText = (EditText) findViewById(R.id.input_txt_LibelleEAV);
//        ev_min_cpteEditText = (EditText) findViewById(R.id.input_txt_MinCompteEAV);
//        ev_is_min_cpte_obligSwitch = (Switch) findViewById(R.id.SwitchMinCpteEAVOblig);
//        ev_tx_inter_anEditText = (EditText) findViewById(R.id.input_txt_TauxInteretAnnuelEAV);
//        ev_is_tx_inter_an_obligSwitch = (Switch) findViewById(R.id.SwitchTauxInteretAnnuelEAV);
//        ev_typ_dat_valEditText = (EditText) findViewById(R.id.input_txt_type_de_date);
//        ev_typ_dat_retrait_valEditText = (EditText) findViewById(R.id.input_txt_type_de_date_retrait);
//        ev_is_multi_eav_onSwitch = (Switch) findViewById(R.id.SwitchMultiEAV);
//        ev_is_paie_ps_onSwitch = (Switch) findViewById(R.id.SwitchPayerPSOnEAV);
//        ev_is_agios_onSwitch = (Switch) findViewById(R.id.SwitchFraisTenuCpteOnEAV);
//
//
//        //ev_typ_fr_agiosEditText = (EditText) findViewById(R.id.input_txt_TauxAPreleveCpteEAV);
//
//        ev_mt_tx_agios_prelevEditText = (EditText) findViewById(R.id.input_txt_TauxAPreleveCpteEAV);
//        ev_plage_agios_fromEditText = (EditText) findViewById(R.id.txt_EvTypTxInterFrom);
//        ev_plage_agios_toEditText = (EditText) findViewById(R.id.txt_EvTypTxInterTo);
//        ev_is_cheque_onSwitch = (Switch) findViewById(R.id.SwitchChequeOnEAV);
//        ev_frais_clot_cptEditText = (EditText) findViewById(R.id.input_txt_FraisClotureCpteEAV);
//
//        //ev_frais_clot_cptEditText.addTextChangedListener(new DigitFormatWatcher(ev_frais_clot_cptEditText));
//
//
//
//        rbEpTypTxInterFixe = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
//
//        rbEpTypTxInterTaux = (RadioButton) findViewById(R.id.rbEpTypTxInterTaux);
//        rbEpTypTxInterPlage = (RadioButton) findViewById(R.id.rbEpTypTxInterPlage);
//        blkPlageEav = (LinearLayout) findViewById(R.id.blk_plage_eav);
//        LL_TypeFraisCpteEAV = (LinearLayout) findViewById(R.id.ll_TypeFraisCpteEAV);
//
//        layout_MinCompteEAV = (TextInputLayout) findViewById(R.id.input_layout_MinCompteEAV);
//        layout_TauxInteretAnnuelEAV = (TextInputLayout) findViewById(R.id.input_layout_TauxInteretAnnuelEAV);
//        layout_DateValeur = (TextInputLayout) findViewById(R.id.input_layout_type_de_date);
//        layout_DateRetrait = (TextInputLayout) findViewById(R.id.input_layout_type_de_date_retrait);
//        layout_TauxAPreleveCpteEAV = (TextInputLayout) findViewById(R.id.input_layout_TauxAPreleveCpteEAV);
//        layout_BaseTauxAPreleveCpteEAV = (TextInputLayout) findViewById(R.id.input_layout_BaseTauxAPreleveCpteEAV);
//        layout_BaseInteretAnnuelEAV = (TextInputLayout) findViewById(R.id.input_layout_BaseInteretAnnuelEAV);
//
//        mySpinnerLocalite = (JRSpinner)findViewById(R.id.spn_my_spinner_base_taux);
//        mySpinnerBaseTxIntMensuel = (JRSpinner)findViewById(R.id.spn_my_spinner_base_taux_mensuel);
//
//        mySpinnerLocalite.setItems(getResources().getStringArray(R.array.array_base_taux_frais_tenue_compte)); //this is important, you must set it to set the item list
//        mySpinnerLocalite.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
//        mySpinnerLocalite.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
//
//        mySpinnerLocalite.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
//            @Override
//            public void onItemClick(int position) {
//                //do what you want to the selected position
//                //  cxLocalite = mySpinnerLocalite.getText().toString();
//                // Log.d("iddddddd***",caisseLocalite);
//            }
//        });
//        mySpinnerBaseTxIntMensuel.setItems(getResources().getStringArray(R.array.array_base_taux)); //this is important, you must set it to set the item list
//        mySpinnerBaseTxIntMensuel.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
//        mySpinnerBaseTxIntMensuel.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
//
//        mySpinnerBaseTxIntMensuel.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
//            @Override
//            public void onItemClick(int position) {
//                //do what you want to the selected position
//                //  cxLocalite = mySpinnerLocalite.getText().toString();
//                // Log.d("iddddddd***",caisseLocalite);
//            }
//        });
//
//
//
////        rbEpTypTxInterFixe.setChecked(true);
////        ev_is_agios_onSwitch.setChecked(false);
//
//        onRadioButtonClicked(rbEpTypTxInterFixe);
//        onSwitchButtonClicked(ev_is_agios_onSwitch);

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

//        tv_config_plage_tiv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
//                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Frais de tenue de compte";
//                    ListPlageDateActivity.IS_TO_CREATE_OR_TO_UPDATE = true;
//                    Intent i = new Intent(CreateProduitCredit.this,ListPlageDateActivity.class);
//                    startActivityForResult(i,20);
//
//                } else {
//                    Toast.makeText(CreateProduitCredit.this,
//                            "Unable to connect to internet",
//                            Toast.LENGTH_LONG).show();
//
//                }
//
//            }
//        });

    }
    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Ajout d'un produit EAV");




    }

    /* public class CurrencyTextWatcher implements TextWatcher {

        private EditText ed;
        private String lastText;
        private boolean bDel = false;
        private boolean bInsert = false;
        private int pos;

        public CurrencyTextWatcher(EditText ed) {
            this.ed = ed;
        }

       // public static String getStringWithSeparator(long value) {
        public  String getStringWithSeparator(long value) {
            DecimalFormat formatter = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
            String f = formatter.format(value);
            return f;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            bDel = false;
            bInsert = false;
            if (before == 1 && count == 0) {
                bDel = true;
                pos = start;
            } else if (before == 0 && count == 1) {
                bInsert = true;
                pos = start;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            lastText = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {
            ed.removeTextChangedListener(this);
            StringBuilder sb = new StringBuilder();
            String text = s.toString();
            for (int i = 0; i < text.length(); i++) {
                if ((text.charAt(i) >= 0x30 && text.charAt(i) <= 0x39) || text.charAt(i) == '.' || text.charAt(i) == ',')
                    sb.append(text.charAt(i));
            }
            if (!sb.toString().equals(s.toString())) {
                bDel = bInsert = false;
            }
            String newText = getFormattedString(sb.toString());
            s.clear();
            s.append(newText);
            ed.addTextChangedListener(this);

            if (bDel) {
                int idx = pos;
                if (lastText.length() - 1 > newText.length())
                    idx--; // if one , is removed
                if (idx < 0)
                    idx = 0;
                ed.setSelection(idx);
            } else if (bInsert) {
                int idx = pos + 1;
                if (lastText.length() + 1 < newText.length())
                    idx++; // if one , is added
                if (idx > newText.length())
                    idx = newText.length();
                ed.setSelection(idx);
            }
        }

        private String getFormattedString(String text) {
            String res = "";
            try {
                String temp = text.replace(",", "");
                long part1;
                String part2 = "";
                int dotIndex = temp.indexOf(".");
                if (dotIndex >= 0) {
                    part1 = Long.parseLong(temp.substring(0, dotIndex));
                    if (dotIndex + 1 <= temp.length()) {
                        part2 = temp.substring(dotIndex + 1).trim().replace(".", "").replace(",", "");
                    }
                } else
                    part1 = Long.parseLong(temp);

                res = getStringWithSeparator(part1);
                if (part2.length() > 0)
                    res += "." + part2;
                else if (dotIndex >= 0)
                    res += ".";
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return res;
        }
    } */
    public void onSwitchButtonClicked(View view) {
//        boolean checked1 = ((Switch) view).isChecked();
//        String str="";
//        // Check which checkbox was clicked
//        switch(view.getId()) {
//
//            case R.id.SwitchMinCpteEAVOblig:
//                if (ev_is_min_cpte_obligSwitch.isChecked()) {
//                    str = checked1?"Minimum en compte obligatoire":"le minimum en compte n'est pas obligatoire";
//
//                    layout_MinCompteEAV.setVisibility(View.VISIBLE);
//                }else{
//                    layout_MinCompteEAV.setVisibility(View.GONE);
//                }
//
//                break;
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
//                    LL_TypeFraisCpteEAV.setVisibility(View.VISIBLE);
//                    //layout_TauxAPreleveCpteEAV.setVisibility(View.VISIBLE);
//                    onRadioButtonClicked(rbEpTypTxInterFixe);
//                    //layout_TauxAPreleveCpteEAV.setVisibility(View.VISIBLE);
//                }else{
//                    layout_TauxAPreleveCpteEAV.setVisibility(View.GONE);
//                    LL_TypeFraisCpteEAV.setVisibility(View.GONE);
//                    blkPlageEav.setVisibility(View.GONE);
//                }
//
//
//                break;
//
//        }
//        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    public void onRadioButtonClicked(View view) {
//        boolean checked1 = ((RadioButton) view).isChecked();
//        String str="";
//        // Check which checkbox was clicked
//        switch(view.getId()) {
//
//            case R.id.rbEpTypTxInterFixe:
//                if (rbEpTypTxInterFixe.isChecked()) {
//                    str = checked1?"Type Fixe sélectionné":"";
//                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
//                    ev_typ_fr_agios ="F";
//                    blkPlageEav.setVisibility(View.GONE);
//                    ev_mt_tx_agios_prelevEditText.setHint("Montant mensuel à préléver");
//                    layout_TauxAPreleveCpteEAV.setVisibility(View.VISIBLE);
//                    layout_BaseTauxAPreleveCpteEAV.setVisibility(View.GONE);
//                    tv_config_plage_tiv.setVisibility(View.GONE);
//
//                }
//
//                break;
//            case R.id.rbEpTypTxInterTaux:
//                if (rbEpTypTxInterTaux.isChecked()){
//                    str = checked1?"Type Taux sélectionné":"";
//                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
//                    ev_typ_fr_agios ="T";
//                    blkPlageEav.setVisibility(View.GONE);
//                    ev_mt_tx_agios_prelevEditText.setHint("Taux mensuel à préléver");
//                    layout_TauxAPreleveCpteEAV.setVisibility(View.VISIBLE);
//                    layout_BaseTauxAPreleveCpteEAV.setVisibility(View.VISIBLE);
//                    tv_config_plage_tiv.setVisibility(View.GONE);
//        }
//
//
//                break;
//            case R.id.rbEpTypTxInterPlage:
//                if (rbEpTypTxInterPlage.isChecked()) {
//
//                    str = checked1?"Type Plage sélectionné":"";
//                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
//                    ev_typ_fr_agios ="P";
//                   // blkPlageEav.setVisibility(View.VISIBLE);
//                    layout_TauxAPreleveCpteEAV.setVisibility(View.GONE);
//                    layout_BaseTauxAPreleveCpteEAV.setVisibility(View.GONE);
//                    tv_config_plage_tiv.setVisibility(View.VISIBLE);
//                }
//                break;
//        }
//        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }



    /**
     * Checks whether all files are filled. If so then calls AddEAVAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addEAV() {
       /* if (!STRING_EMPTY.equals(ev_codeEditText.getText().toString()) &&

                !STRING_EMPTY.equals(ev_libelleEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ev_tx_inter_anEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ev_is_tx_inter_an_obligSwitch.getText().toString()) &&
                !STRING_EMPTY.equals(ev_typ_dat_valEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ev_is_multi_eav_onSwitch.getText().toString()) &&
                !STRING_EMPTY.equals(ev_is_paie_ps_onSwitch.getText().toString()) &&
                !STRING_EMPTY.equals(ev_is_agios_onSwitch.getText().toString()) &&
                !STRING_EMPTY.equals(ev_typ_fr_agiosEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ev_mt_tx_agios_prelevEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ev_plage_agios_fromEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ev_plage_agios_toEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ev_is_cheque_onSwitch.getText().toString()) &&
                !STRING_EMPTY.equals(ev_frais_clot_cptEditText.getText().toString()) &&

                !STRING_EMPTY.equals(ev_min_cpteEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ev_is_min_cpte_obligSwitch.getText().toString())) { */
if (true){
    monProduitCredit = new Credit(ET_CrCode.getText().toString(),ET_CrLibelle.getText().toString(),ET_CrDureeMin.getText().toString(),
            ET_CrDureeMax.getText().toString(),"F","1","F",ET_CrValTxInter.getText().toString(),
            SW_CrIsTxIntNeg.isChecked(),ET_CrNbreAvalDmde.getText().toString(),ET_CrNbreMinAvalExig.getText().toString(),
            ET_CrTxCouvCrAval.getText().toString(), SW_CrIsTxCouvAvalOblig.isChecked(), SW_CrIsCautionMorAvalAcc.isChecked(),
            SW_CrIsGarBloqCptOblig.isChecked(),SW_CrIsGarCptEAVOn.isChecked(),SW_CrIsGarCptEATOn.isChecked(),SW_CrIsGarCptEAPOn.isChecked(),
            ET_CrMtMaxSansAval.getText().toString(),SW_CrIsAvalSansCredOn.isChecked(),SW_CrIsTxGarMemObl.isChecked(),
            ET_CrTauxGarMemb.getText().toString(),SW_CrIsPersMorAvalOn.isChecked(),SW_CrIsCouvPartSOn.isChecked(),
            ET_CrTxCouvPSOblig.getText().toString(),SW_CrIsAffCollCredOn.isChecked(),ET_CrNbreAnAncMinCred.getText().toString(),
            SW_CrNbAnAncNeg.isChecked(),ET_CrMtPlafondMax.getText().toString(),SW_CrIsMtPlafCredLeve.isChecked(),
            SW_CrIsGarMatExige.isChecked(),SW_CrIsFraisEtudDossOn.isChecked(),"F",ET_CrValTxFrEtudDoss.getText().toString(),
            ET_CrBaseTxFrEtudDoss.getText().toString(),SW_CrIsFraisDeblocCredOn.isChecked(),"Fixe",
            ET_CrValTxFraisDeblocCred.getText().toString(),
            ET_CrBaseTxFraisDeblocCred.getText().toString(),SW_CrIsFraisDecaissCredOn.isChecked(),
            "",ET_CrValTxFraisDecaissCred.getText().toString(),ET_CrBaseFraisDecaissCred.getText().toString(),
            SW_CrIsFraisEtudByDAV.isChecked(),SW_CrIsFraisDeblocByDAV.isChecked(),SW_CrIsFraisDecaissByDAV.isChecked(),
            SW_CrIsModDecaissByObjet.isChecked(),SW_CrIsDeblocTransfDAVOn.isChecked(),SW_CrIsMtPlafByObjet.isChecked(),
            ET_CrModeRemb.getText().toString(),SW_CrIsCptEATRemCredOn.isChecked(),SW_CrIsCptEAPRemCredOn.isChecked(),
            SW_CrIsInterOffSiCapRembAnt.isChecked(),ET_CrTxInterEchNHon.getText().toString(),ET_CrBaseInterEchNHon.getText().toString(),
            ET_CrPlanningRembCred.getText().toString(),SW_CrIsRappDatEchCred.isChecked(),ET_CrModelTextRappEchRemb.getText().toString(),
            ET_CrNbreJrAvantDatEch.getText().toString(),ET_CrNbreJrApreEchSiNHon.getText().toString(),
            MyData.USER_ID+"",
            null,
            MyData.USER_ID+"",
            null,
            MyData.CAISSE_ID+"",
            null);


//    base_ev_mt_tx_agios_prelev = mySpinnerLocalite.getText().toString();
//    base_ev_tx_inter_an = mySpinnerBaseTxIntMensuel.getText().toString();
//to manage plage data
   /* for (int i=0; i<plageDataList.size();i++){
        tabPlageDebut+=";"+plageDataList.get(i).getPdValDe();
        tabPlageFin+=";"+plageDataList.get(i).getPdValA();
        tabPlageValeur+=";"+plageDataList.get(i).getPdValTaux();
        tabPlageBase+=";"+plageDataList.get(i).getPdBase();
        tabPlageNature+=";"+plageDataList.get(i).getPdNature();
    }*/

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



//            httpParams.put(KEY_EAV_PLAGE_FRAIS_DE_TENUE_DE_COMPTE_DEBUT, tabPlageDebut);
//            httpParams.put(KEY_EAV_PLAGE_FRAIS_DE_TENUE_DE_COMPTE_FIN, tabPlageFin);
//            httpParams.put(KEY_EAV_PLAGE_FRAIS_DE_TENUE_DE_COMPTE_VALEUR, tabPlageValeur);
//            httpParams.put(KEY_EAV_PLAGE_FRAIS_DE_TENUE_DE_COMPTE_BASE, tabPlageBase);
//            httpParams.put(KEY_EAV_PLAGE_FRAIS_DE_TENUE_DE_COMPTE_NATURE, tabPlageNature);

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