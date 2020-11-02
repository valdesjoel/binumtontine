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
/*
import eav.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.binumtontine.R;

public class CreateProduitEAP extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_param_produit_eap);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_produit_eap);
        setSupportActionBar(toolbar);
        setToolbarTitle();
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Ajout d'un produit EAP");

    }
}
*/

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
import com.example.binumtontine.modele.ProduitEAV;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateProduitEAV extends AppCompatActivity implements SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";

    private static final String KEY_EAV_CODE = "ev_code";
    private static final String KEY_EAV_LIBELLE = "ev_libelle";
    private static final String KEY_EAV_MIN_CPTE = "ev_min_cpte";
    private static final String KEY_EAV_IS_MIN_CPTE_OBLIG = "ev_is_min_cpte_oblig";
    private static final String KEY_EAV_TX_INTER_AN = "ev_tx_inter_an";
    private static final String KEY_EAV_BASE_TX_INTER_AN = "ev_base_tx_inter_an";
    private static final String KEY_EAV_IS_TX_INTER_AN_OBLIG = "ev_is_tx_inter_an_oblig";
    private static final String KEY_EAV_TYP_DAT_VAL = "ev_typ_dat_val";
    private static final String KEY_EAV_TYP_DAT_RETRAIT_VAL = "ev_date_retrait";
    private static final String KEY_EAV_IS_MULTI_EAV_ON = "ev_is_multi_eav_on";
    private static final String KEY_EAV_IS_PAIE_PS_ON = "ev_is_paie_ps_on";
    private static final String KEY_EAV_IS_AGIOS_ON = "ev_is_agios_on";
    private static final String KEY_EAV_TYP_FR_AGIOS = "ev_typ_fr_agios";
    private static final String KEY_EAV_MT_TX_AGIOS_PRELEV = "ev_mt_tx_agios_prelev";
    private static final String KEY_EAV_BASE_TX_AGIOS_PRELEV = "ev_base_tx_agios_prelev";
//    private static final String KEY_EAV_PLAGE_AGIOS_FROM = "ev_plage_agios_from";
//    private static final String KEY_EAV_PLAGE_AGIOS_TO = "ev_plage_agios_to";
    private static final String KEY_EAV_IS_CHEQUE_ON = "ev_is_cheque_on";
    private static final String KEY_EAV_FRAIS_CLOT_CPT = "ev_frais_clot_cpt";
    private static final String KEY_EAV_CX_NUMERO = "ev_caisse_id";

    private static final String KEY_CcIsChequierM1On = "EvIsCheqTyp1On";
    private static final String KEY_CcNbPagesCheqM1 = "EvNbPageCheqT1";
    private static final String KEY_CcPrixVteCheqM1 = "EvMtCheqTyp1";
    private static final String KEY_CcIsChequierM2On = "EvIsCheqTyp2On";
    private static final String KEY_CcNbPagesCheqM2 = "EvNbPageCheqT2";
    private static final String KEY_CcPrixVteCheqM2 = "EvMtCheqTyp2";


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

    private EditText ev_codeEditText;
    private EditText ev_libelleEditText;
    private EditText ev_min_cpteEditText;
    private Switch ev_is_min_cpte_obligSwitch;
    private EditText ev_tx_inter_anEditText;
    private Switch ev_is_tx_inter_an_obligSwitch;
    private EditText ev_typ_dat_valEditText;
    private EditText ev_typ_dat_retrait_valEditText;
    private Switch ev_is_multi_eav_onSwitch;
    private Switch ev_is_paie_ps_onSwitch;
    private Switch ev_is_agios_onSwitch;
    private Switch CcIsChequierM1On;
    private Switch CcIsChequierM2On;
    private LinearLayout ll_bloc_chequier;
    private LinearLayout bloc_cc2;
    private LinearLayout bloc_cc3;
   // private EditText ev_typ_fr_agiosEditText;RadioButton
    private RadioButton ev_typ_fr_agiosEditText;
    private EditText ev_mt_tx_agios_prelevEditText;

//    private EditText ev_plage_agios_fromEditText;
//    private EditText ev_plage_agios_toEditText;
    private Switch ev_is_cheque_onSwitch;
//    private EditText ev_frais_clot_cptEditText;

    private String ev_code;
    private String ev_libelle;
    private String ev_min_cpte;
    private String ev_is_min_cpte_oblig;
    private String ev_tx_inter_an;
    private String ev_is_tx_inter_an_oblig;
    private String ev_typ_dat_val;
    private String ev_typ_dat_retrait_val;
    private String ev_is_multi_eav_on;
    private String ev_is_paie_ps_on;
    private String ev_is_agios_on;
    private String ev_typ_fr_agios;
    private String ev_mt_tx_agios_prelev;
    private String base_ev_mt_tx_agios_prelev;
    private String base_ev_tx_inter_an;
//    private String ev_plage_agios_from;
//    private String ev_plage_agios_to;
    private String ev_is_cheque_on;
//    private String ev_frais_clot_cpt;
    private int ev_caisse_id = MyData.CAISSE_ID;

//    private LinearLayout blkPlageEav;
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

    private JRSpinner mySpinnerBaseTxIntMensuel; //pour gérer le spinner contenant les bases des taux pour les ev_base_tx_inter_an
    private JRSpinner mySpinnerLocalite; //pour gérer le spinner contenant les bases des taux pour les frais de tenue de compte

    private Button addButton;
    private Button cancelButton;
    private Button deleteButton;
    private int success;
    private ProgressDialog pDialog;

    private TextView tv_header_produit;
    public static ArrayList<ModelPlageData> plageDataListTIV  = new ArrayList<>(); //to manage plageData TIV

    private TextView tv_config_plage_tiv;


    private String st_CcIsChequierM1On, st_CcIsChequierM2On, st_CcNbPagesCheqM1,st_CcPrixVteCheqM1,st_CcNbPagesCheqM2,
            st_CcPrixVteCheqM2;
    private EditText CcNbPagesCheqM1,CcPrixVteCheqM1,CcNbPagesCheqM2,CcPrixVteCheqM2;
    //START 27/10/2020

    private static final String KEY_EvNatureFraisClot = "EvNatureFraisClot";
    private static final String KEY_EvValFraisCloture = "EvValFraisCloture";
    private static final String KEY_EvBaseFraisCloture = "EvBaseFraisCloture";
    private static final String KEY_EAV_FCV_DEBUT = "EvFcvDebut";
    private static final String KEY_EAV_FCV_FIN = "EvFcvFin";
    private static final String KEY_EAV_FCV_VALEUR = "EvFcvValeur";
    private static final String KEY_EAV_FCV_BASE = "EvFcvBase";
    private static final String KEY_EAV_FCV_NATURE = "EvFcvNature";


    private String tabPlageDebutFCV ="";
    private String tabPlageFinFCV ="";
    private String tabPlageValeurFCV ="";
    private String tabPlageBaseFCV ="";
    private String tabPlageNatureFCV ="";
    public static ArrayList<ModelPlageData> plageDataListFCV = new ArrayList<>(); //to manage plageData FCV
    private EditText ET_EvValFraisCloture;
    private JRSpinner JR_EvBaseFraisCloture; //pour gérer le spinner contenant les bases des taux pour les frais de clôture de compte
    private RadioButton rbEvNatureFraisClotFixe;
    private RadioButton rbEvNatureFraisClotTaux;
    private RadioButton rbEvNatureFraisClotPlage;

    private TextInputLayout layout_EvValFraisCloture;
    private TextInputLayout layout_EvBaseFraisCloture;
    private TextView tv_config_plage_fcv;
    private String EvNatureFraisClot;
    private String EvValFraisCloture;
    private String EvBaseFraisCloture;
    //END 27/10/2020

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_add_movie);
      //  setContentView(R.layout.fragment_param_produit_eav);
        setContentView(R.layout.activity_eav);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_produit_eav);
        setSupportActionBar(toolbar);
        setToolbarTitle(); */
        ll_bloc_chequier = (LinearLayout) findViewById(R.id.ll_bloc_chequier);
        bloc_cc2 = (LinearLayout) findViewById(R.id.ll_bloc_cc2);
        bloc_cc3 = (LinearLayout) findViewById(R.id.ll_bloc_cc3);
        CcIsChequierM1On=(Switch) findViewById(R.id.Switch_txtDisponibiliteChequier1CC);
        CcIsChequierM2On=(Switch) findViewById(R.id.Switch_txtDisponibiliteChequier2CC);
        CcNbPagesCheqM1=(EditText) findViewById(R.id.input_txt_NbrePageChequier1CC);
        CcPrixVteCheqM1=(EditText) findViewById(R.id.input_txt_txtPrixVenteChequier1CC);
        CcNbPagesCheqM2=(EditText) findViewById(R.id.input_txt_NbrePageChequier2CC);
        CcPrixVteCheqM2=(EditText) findViewById(R.id.input_txt_txtPrixVenteChequier2CC);


        tv_header_produit = (TextView) findViewById(R.id.header_produit);
        tv_header_produit.setText("Produit EAV\n"+"Caisse: "+MyData.CAISSE_NAME);
        tv_config_plage_tiv = (TextView) findViewById(R.id.tv_plage_tiv_eav);

        ev_codeEditText = (EditText) findViewById(R.id.input_txt_Code_EAV);
        ev_libelleEditText = (EditText) findViewById(R.id.input_txt_LibelleEAV);
        ev_min_cpteEditText = (EditText) findViewById(R.id.input_txt_MinCompteEAV);
        ev_min_cpteEditText.addTextChangedListener(MyData.onTextChangedListener(ev_min_cpteEditText));
        ev_is_min_cpte_obligSwitch = (Switch) findViewById(R.id.SwitchMinCpteEAVOblig);
        ev_tx_inter_anEditText = (EditText) findViewById(R.id.input_txt_TauxInteretAnnuelEAV);
        ev_is_tx_inter_an_obligSwitch = (Switch) findViewById(R.id.SwitchTauxInteretAnnuelEAV);
        ev_typ_dat_valEditText = (EditText) findViewById(R.id.input_txt_type_de_date);
        ev_typ_dat_retrait_valEditText = (EditText) findViewById(R.id.input_txt_type_de_date_retrait);
        ev_is_multi_eav_onSwitch = (Switch) findViewById(R.id.SwitchMultiEAV);
        ev_is_paie_ps_onSwitch = (Switch) findViewById(R.id.SwitchPayerPSOnEAV);
        ev_is_agios_onSwitch = (Switch) findViewById(R.id.SwitchFraisTenuCpteOnEAV);


        //ev_typ_fr_agiosEditText = (EditText) findViewById(R.id.input_txt_TauxAPreleveCpteEAV);

        ev_mt_tx_agios_prelevEditText = (EditText) findViewById(R.id.input_txt_TauxAPreleveCpteEAV);

        ev_is_cheque_onSwitch = (Switch) findViewById(R.id.SwitchChequeOnEAV);
//        ev_frais_clot_cptEditText = (EditText) findViewById(R.id.input_txt_FraisClotureCpteEAV);

        //ev_frais_clot_cptEditText.addTextChangedListener(new DigitFormatWatcher(ev_frais_clot_cptEditText));



        //START 27/10/2020
        ET_EvValFraisCloture = (EditText) findViewById(R.id.input_txt_EvValFraisCloture);
        ET_EvValFraisCloture.addTextChangedListener(MyData.onTextChangedListener(ET_EvValFraisCloture));
        rbEvNatureFraisClotFixe = (RadioButton) findViewById(R.id.rbEvNatureFraisClotFixe);
        rbEvNatureFraisClotTaux = (RadioButton) findViewById(R.id.rbEvNatureFraisClotTaux);
        rbEvNatureFraisClotPlage = (RadioButton) findViewById(R.id.rbEvNatureFraisClotPlage);
        layout_EvValFraisCloture = (TextInputLayout) findViewById(R.id.input_layout_EvValFraisCloture);
        layout_EvBaseFraisCloture = (TextInputLayout) findViewById(R.id.input_layout_EvBaseFraisCloture);
        tv_config_plage_fcv = (TextView) findViewById(R.id.tv_plage_fcv_eav);
        onRadioButtonClicked(rbEvNatureFraisClotFixe);
        JR_EvBaseFraisCloture = (JRSpinner)findViewById(R.id.spn_my_spinner_EvBaseFraisCloture);
        JR_EvBaseFraisCloture.setItems(getResources().getStringArray(R.array.array_EvBaseFraisCloture)); //this is important, you must set it to set the item list
        JR_EvBaseFraisCloture.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        JR_EvBaseFraisCloture.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically

        JR_EvBaseFraisCloture.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position
                //  cxLocalite = mySpinnerLocalite.getText().toString();
                // Log.d("iddddddd***",caisseLocalite);
            }
        });

        //END 27/10/2020

        rbEpTypTxInterFixe = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);

        rbEpTypTxInterTaux = (RadioButton) findViewById(R.id.rbEpTypTxInterTaux);
        rbEpTypTxInterPlage = (RadioButton) findViewById(R.id.rbEpTypTxInterPlage);
        LL_TypeFraisCpteEAV = (LinearLayout) findViewById(R.id.ll_TypeFraisCpteEAV);

        layout_MinCompteEAV = (TextInputLayout) findViewById(R.id.input_layout_MinCompteEAV);
        layout_TauxInteretAnnuelEAV = (TextInputLayout) findViewById(R.id.input_layout_TauxInteretAnnuelEAV);
        layout_DateValeur = (TextInputLayout) findViewById(R.id.input_layout_type_de_date);
        layout_DateRetrait = (TextInputLayout) findViewById(R.id.input_layout_type_de_date_retrait);
        layout_TauxAPreleveCpteEAV = (TextInputLayout) findViewById(R.id.input_layout_TauxAPreleveCpteEAV);

        layout_BaseTauxAPreleveCpteEAV = (TextInputLayout) findViewById(R.id.input_layout_BaseTauxAPreleveCpteEAV);
        layout_BaseInteretAnnuelEAV = (TextInputLayout) findViewById(R.id.input_layout_BaseInteretAnnuelEAV);



        mySpinnerLocalite = (JRSpinner)findViewById(R.id.spn_my_spinner_base_taux);



        mySpinnerLocalite.setItems(getResources().getStringArray(R.array.array_ev_base_tx_agios_prelev)); //this is important, you must set it to set the item list
        mySpinnerLocalite.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        mySpinnerLocalite.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically

        mySpinnerLocalite.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position
                //  cxLocalite = mySpinnerLocalite.getText().toString();

            }
        });
        mySpinnerBaseTxIntMensuel = (JRSpinner)findViewById(R.id.spn_my_spinner_base_taux_mensuel);
        mySpinnerBaseTxIntMensuel.setItems(getResources().getStringArray(R.array.array_ev_base_tx_inter_an)); //this is important, you must set it to set the item list
        mySpinnerBaseTxIntMensuel.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        mySpinnerBaseTxIntMensuel.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically

        mySpinnerBaseTxIntMensuel.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position


            }
        });



        onRadioButtonClicked(rbEpTypTxInterFixe);
        onSwitchButtonClicked(ev_is_agios_onSwitch);

        onSwitchButtonClicked(CcIsChequierM1On);
        onSwitchButtonClicked(CcIsChequierM2On);
        onSwitchButtonClicked(ev_is_cheque_onSwitch);

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
                    Toast.makeText(CreateProduitEAV.this,
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
                    Toast.makeText(CreateProduitEAV.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

        //TIV
        tv_config_plage_tiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Frais de tenue de compte";
//                    ListPlageDateActivity.IS_TO_CREATE_OR_TO_UPDATE = true;
                    ListPlageTIV.IS_TO_CREATE_OR_TO_UPDATE = true;
//                    Intent i = new Intent(CreateProduitEAV.this,ListPlageDateActivity.class);
                    Intent i = new Intent(CreateProduitEAV.this,ListPlageTIV.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(CreateProduitEAV.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        //FCV
        tv_config_plage_fcv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    MyData.TYPE_DE_FRAIS_PLAGE_DATA = "Frais de clôture de compte";
                    ListPlageFCV.IS_TO_CREATE_OR_TO_UPDATE = true;
                    Intent i = new Intent(CreateProduitEAV.this,ListPlageFCV.class);
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(CreateProduitEAV.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    public void onSwitchButtonClicked(View view) {
//        boolean checked1 = ((Switch) view).isChecked();
//        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.SwitchMinCpteEAVOblig:
                if (ev_is_min_cpte_obligSwitch.isChecked()) {
//                    str = checked1?"Minimum en compte obligatoire":"le minimum en compte n'est pas obligatoire";

                    layout_MinCompteEAV.setVisibility(View.VISIBLE);
                }else{
                    layout_MinCompteEAV.setVisibility(View.GONE);
                }

                break;
            case R.id.SwitchTauxInteretAnnuelEAV:
                if (ev_is_tx_inter_an_obligSwitch.isChecked()){
//                    str = checked1?"Taux interêt obligatoire":"Taux interêt non obligatoire";

                    layout_TauxInteretAnnuelEAV.setVisibility(View.VISIBLE);
                    layout_BaseInteretAnnuelEAV.setVisibility(View.VISIBLE);
                    layout_DateValeur.setVisibility(View.VISIBLE);
                    layout_DateRetrait.setVisibility(View.VISIBLE);
                }else{
                    layout_TauxInteretAnnuelEAV.setVisibility(View.GONE);
                    layout_BaseInteretAnnuelEAV.setVisibility(View.GONE);

                    layout_DateValeur.setVisibility(View.GONE);
                    layout_DateRetrait.setVisibility(View.GONE);
                }


                break;
            case R.id.SwitchFraisTenuCpteOnEAV:
                if (ev_is_agios_onSwitch.isChecked()){
//                    str = checked1?"Frais de tenue de compte activés":"Frais de tenue de compte désactivés";

                    LL_TypeFraisCpteEAV.setVisibility(View.VISIBLE);
                    //layout_TauxAPreleveCpteEAV.setVisibility(View.VISIBLE);
                    onRadioButtonClicked(rbEpTypTxInterFixe);
                    //layout_TauxAPreleveCpteEAV.setVisibility(View.VISIBLE);
                }else{
                    layout_TauxAPreleveCpteEAV.setVisibility(View.GONE);
                    LL_TypeFraisCpteEAV.setVisibility(View.GONE);
                    layout_BaseTauxAPreleveCpteEAV.setVisibility(View.GONE);
                    tv_config_plage_tiv.setVisibility(View.GONE);
                }


                break;

            case R.id.SwitchChequeOnEAV:
                if (ev_is_cheque_onSwitch.isChecked()) {
//                    str = checked1 ? "Chèque disponible" : "Chèque non disponible";

                    ll_bloc_chequier.setVisibility(View.VISIBLE);
                    //onRadioButtonClicked(rbCrNatFrEtudDossFixe);
                } else {
                    ll_bloc_chequier.setVisibility(View.GONE);
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

        }
//        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    public void onRadioButtonClicked(View view) {
//        boolean checked1 = ((RadioButton) view).isChecked();
//        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.rbEvNatureFraisClotFixe:
                if (rbEvNatureFraisClotFixe.isChecked()) {
                    EvNatureFraisClot ="F";
                    layout_EvValFraisCloture.setHint("Montant (FCFA)");
                    layout_EvValFraisCloture.setVisibility(View.VISIBLE);
                    layout_EvBaseFraisCloture.setVisibility(View.GONE);
                    tv_config_plage_fcv.setVisibility(View.GONE);

                }
                break;
            case R.id.rbEvNatureFraisClotTaux:
                if (rbEvNatureFraisClotTaux.isChecked()){
                    EvNatureFraisClot ="T";
                    layout_EvValFraisCloture.setHint("Taux (%)");
                    layout_EvValFraisCloture.setVisibility(View.VISIBLE);
                    layout_EvBaseFraisCloture.setVisibility(View.VISIBLE);
                    tv_config_plage_fcv.setVisibility(View.GONE);
                }

                break;
            case R.id.rbEvNatureFraisClotPlage:
                if (rbEvNatureFraisClotPlage.isChecked()) {
                    EvNatureFraisClot ="P";
                    layout_EvValFraisCloture.setVisibility(View.GONE);
                    layout_EvBaseFraisCloture.setVisibility(View.GONE);
                    tv_config_plage_fcv.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rbEpTypTxInterFixe:
                if (rbEpTypTxInterFixe.isChecked()) {
                    ev_typ_fr_agios ="F";
                    layout_TauxAPreleveCpteEAV.setHint("Montant mensuel à préléver");
                    layout_TauxAPreleveCpteEAV.setVisibility(View.VISIBLE);
                    layout_BaseTauxAPreleveCpteEAV.setVisibility(View.GONE);
                    tv_config_plage_tiv.setVisibility(View.GONE);

                }

                break;
            case R.id.rbEpTypTxInterTaux:
                if (rbEpTypTxInterTaux.isChecked()){
                    ev_typ_fr_agios ="T";
                    layout_TauxAPreleveCpteEAV.setHint("Taux mensuel à préléver");
                    layout_TauxAPreleveCpteEAV.setVisibility(View.VISIBLE);
                    layout_BaseTauxAPreleveCpteEAV.setVisibility(View.VISIBLE);
                    tv_config_plage_tiv.setVisibility(View.GONE);
        }


                break;
            case R.id.rbEpTypTxInterPlage:
                if (rbEpTypTxInterPlage.isChecked()) {
                    ev_typ_fr_agios ="P";
                    layout_TauxAPreleveCpteEAV.setVisibility(View.GONE);
                    layout_BaseTauxAPreleveCpteEAV.setVisibility(View.GONE);
                    tv_config_plage_tiv.setVisibility(View.VISIBLE);
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

if (!STRING_EMPTY.equals(ev_codeEditText.getText().toString().trim()) &&
        !STRING_EMPTY.equals(ev_libelleEditText.getText().toString().trim())
){
    try {


        ev_code = MyData.normalizeSymbolsAndAccents( ev_codeEditText.getText().toString());
        ev_libelle = MyData.normalizeSymbolsAndAccents( ev_libelleEditText.getText().toString());

        Double min_cpte, ev_cloture;


        if (!(ev_min_cpteEditText.getText().toString().replaceAll(",", "").trim()).equals(STRING_EMPTY)) {
            min_cpte = Double.valueOf(ev_min_cpteEditText.getText().toString().replaceAll(",", "").trim());
            ev_min_cpteEditText.setText(min_cpte + "");
        }

        ev_min_cpte = ev_min_cpteEditText.getText().toString();

        if (ev_is_min_cpte_obligSwitch.isChecked()) {
            ev_is_min_cpte_oblig = "Y";
        } else {
            ev_is_min_cpte_oblig = "N";
        }
        if (ev_is_tx_inter_an_obligSwitch.isChecked()) {
            ev_is_tx_inter_an_oblig = "Y";
        } else {
            ev_is_tx_inter_an_oblig = "N";
        }
        ev_tx_inter_an = ev_tx_inter_anEditText.getText().toString();
        ev_typ_dat_val = ev_typ_dat_valEditText.getText().toString();
        ev_typ_dat_retrait_val = ev_typ_dat_retrait_valEditText.getText().toString();
        if (ev_is_multi_eav_onSwitch.isChecked()) {
            ev_is_multi_eav_on = "Y";
        } else {
            ev_is_multi_eav_on = "N";
        }
        if (ev_is_paie_ps_onSwitch.isChecked()) {
            ev_is_paie_ps_on = "Y";
        } else {
            ev_is_paie_ps_on = "N";
        }
        if (ev_is_agios_onSwitch.isChecked()) {
            ev_is_agios_on = "Y";
        } else {
            ev_is_agios_on = "N";
        }

        //ev_typ_fr_agios = ev_typ_fr_agiosEditText.getText().toString();

        ev_mt_tx_agios_prelev = ev_mt_tx_agios_prelevEditText.getText().toString();
        base_ev_mt_tx_agios_prelev = ProduitEAV.encodeEvBaseTxAgiosPrelev(mySpinnerLocalite.getText().toString()) ;
        base_ev_tx_inter_an = ProduitEAV.encodeEvBaseTxInterAn(mySpinnerBaseTxIntMensuel.getText().toString());
//        ev_plage_agios_from = ev_plage_agios_fromEditText.getText().toString();
//        ev_plage_agios_to = ev_plage_agios_toEditText.getText().toString();
//        ev_frais_clot_cpt = ev_frais_clot_cptEditText.getText().toString();
        if (CcIsChequierM1On.isChecked()) {
            st_CcIsChequierM1On = "Y";
        } else {
            st_CcIsChequierM1On = "N";
        }
        if (CcIsChequierM2On.isChecked()) {
            st_CcIsChequierM2On = "Y";
        } else {
            st_CcIsChequierM2On = "N";
        }

        if (ev_is_cheque_onSwitch.isChecked()) {
            ev_is_cheque_on = "Y";
        } else {
            ev_is_cheque_on = "N";
        }

        st_CcNbPagesCheqM1 = CcNbPagesCheqM1.getText().toString();
        st_CcPrixVteCheqM1 = CcPrixVteCheqM1.getText().toString();
        st_CcNbPagesCheqM2 = CcNbPagesCheqM2.getText().toString();
        st_CcPrixVteCheqM2 = CcPrixVteCheqM2.getText().toString();


        if (!(ET_EvValFraisCloture.getText().toString().replaceAll(",", "").trim()).equals(STRING_EMPTY)) {
            ev_cloture = Double.valueOf(ET_EvValFraisCloture.getText().toString().replaceAll(",", "").trim());
            ET_EvValFraisCloture.setText(ev_cloture + "");
        }
        EvValFraisCloture = ET_EvValFraisCloture.getText().toString();
        EvBaseFraisCloture = ProduitEAV.encodeEvBaseFraisCloture(JR_EvBaseFraisCloture.getText().toString()) ;
//to manage plage data
        //TIV Frais de tenue de compte
        for (int i = 0; i < plageDataListTIV.size(); i++) {
            tabPlageDebut += ";" + plageDataListTIV.get(i).getPdValDe();
            tabPlageFin += ";" + plageDataListTIV.get(i).getPdValA();
            tabPlageValeur += ";" + plageDataListTIV.get(i).getPdValTaux();
            tabPlageBase += ";" + plageDataListTIV.get(i).getPdBase();
            tabPlageNature += ";" + plageDataListTIV.get(i).getPdNature();
        }
        //FCV Frais de Clôture de compte
        for (int i = 0; i < plageDataListFCV.size(); i++) {
            tabPlageDebutFCV += ";" + plageDataListFCV.get(i).getPdValDe();
            tabPlageFinFCV += ";" + plageDataListFCV.get(i).getPdValA();
            tabPlageValeurFCV += ";" + plageDataListFCV.get(i).getPdValTaux();
            tabPlageBaseFCV += ";" + plageDataListFCV.get(i).getPdBase();
            tabPlageNatureFCV += ";" + plageDataListFCV.get(i).getPdNature();
        }

        new AddEAVAsyncTask().execute();
    }catch (Exception e){
        e.printStackTrace();
    }
        } else {
            Toast.makeText(CreateProduitEAV.this,
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
            pDialog = new ProgressDialog(CreateProduitEAV.this);
            pDialog.setMessage("Adding EAV. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_EAV_CODE, ev_code);
            httpParams.put(KEY_EAV_LIBELLE, ev_libelle);
            httpParams.put(KEY_EAV_MIN_CPTE, ev_min_cpte);
            httpParams.put(KEY_EAV_IS_MIN_CPTE_OBLIG, ev_is_min_cpte_oblig);

            httpParams.put(KEY_EAV_TX_INTER_AN, ev_tx_inter_an);
            httpParams.put(KEY_EAV_BASE_TX_INTER_AN, base_ev_tx_inter_an);
            httpParams.put(KEY_EAV_IS_TX_INTER_AN_OBLIG, ev_is_tx_inter_an_oblig.toString());
            httpParams.put(KEY_EAV_TYP_DAT_VAL, ev_typ_dat_val);
            httpParams.put(KEY_EAV_TYP_DAT_RETRAIT_VAL, ev_typ_dat_retrait_val);
            httpParams.put(KEY_EAV_IS_MULTI_EAV_ON, ev_is_multi_eav_on.toString());
            httpParams.put(KEY_EAV_IS_PAIE_PS_ON, ev_is_paie_ps_on.toString());
            httpParams.put(KEY_EAV_IS_AGIOS_ON, ev_is_agios_on.toString());
            httpParams.put(KEY_EAV_TYP_FR_AGIOS, ev_typ_fr_agios);
            httpParams.put(KEY_EAV_MT_TX_AGIOS_PRELEV, ev_mt_tx_agios_prelev);
            httpParams.put(KEY_EAV_BASE_TX_AGIOS_PRELEV, base_ev_mt_tx_agios_prelev);
//            httpParams.put(KEY_EAV_PLAGE_AGIOS_FROM, ev_plage_agios_from);
//            httpParams.put(KEY_EAV_PLAGE_AGIOS_TO, ev_plage_agios_to);
            httpParams.put(KEY_EAV_IS_CHEQUE_ON, ev_is_cheque_on.toString());
//            httpParams.put(KEY_EAV_FRAIS_CLOT_CPT, ev_frais_clot_cpt);
            httpParams.put(KEY_EAV_CX_NUMERO, String.valueOf(ev_caisse_id));


            //TIV
            httpParams.put(KEY_EAV_PLAGE_FRAIS_DE_TENUE_DE_COMPTE_DEBUT, tabPlageDebut);
            httpParams.put(KEY_EAV_PLAGE_FRAIS_DE_TENUE_DE_COMPTE_FIN, tabPlageFin);
            httpParams.put(KEY_EAV_PLAGE_FRAIS_DE_TENUE_DE_COMPTE_VALEUR, tabPlageValeur);
            httpParams.put(KEY_EAV_PLAGE_FRAIS_DE_TENUE_DE_COMPTE_BASE, tabPlageBase);
            httpParams.put(KEY_EAV_PLAGE_FRAIS_DE_TENUE_DE_COMPTE_NATURE, tabPlageNature);


            httpParams.put(KEY_CcIsChequierM1On, st_CcIsChequierM1On);
            httpParams.put(KEY_CcNbPagesCheqM1, st_CcNbPagesCheqM1);
            httpParams.put(KEY_CcPrixVteCheqM1, st_CcPrixVteCheqM1);
            httpParams.put(KEY_CcIsChequierM2On, st_CcIsChequierM2On);
            httpParams.put(KEY_CcNbPagesCheqM2, st_CcNbPagesCheqM2);
            httpParams.put(KEY_CcPrixVteCheqM2, st_CcPrixVteCheqM2);

            //FCV
            httpParams.put(KEY_EvNatureFraisClot, EvNatureFraisClot);
            httpParams.put(KEY_EvValFraisCloture, EvValFraisCloture);
            httpParams.put(KEY_EvBaseFraisCloture, EvBaseFraisCloture);

            httpParams.put(KEY_EAV_FCV_DEBUT, tabPlageDebutFCV);
            httpParams.put(KEY_EAV_FCV_FIN, tabPlageFinFCV);
            httpParams.put(KEY_EAV_FCV_VALEUR, tabPlageValeurFCV);
            httpParams.put(KEY_EAV_FCV_BASE, tabPlageBaseFCV);
            httpParams.put(KEY_EAV_FCV_NATURE, tabPlageNatureFCV);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_eav_new.php", "POST", httpParams);
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
                        Toast.makeText(CreateProduitEAV.this,
                                "EAV Ajouté", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CreateProduitEAV.this,
                                "Some error occurred while adding EAV",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}