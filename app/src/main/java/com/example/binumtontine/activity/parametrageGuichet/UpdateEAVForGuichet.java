package com.example.binumtontine.activity.parametrageGuichet;

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
import com.example.binumtontine.activity.ListPlageFCV;
import com.example.binumtontine.activity.ListPlageTIV;
import com.example.binumtontine.activity.UpdateEAV;
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

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class UpdateEAVForGuichet extends AppCompatActivity implements SERVER_ADDRESS {
    private static String STRING_EMPTY = "";
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_EAV_ID = "ev_numero"; //to get intent.extra
    private static final String KEY_EXTRA_ACTION_TO_AFFECT = "ACTION_TO_AFFECT"; //to get intent.extra
    private static final String KEY_EAV_CODE = "ev_code";
    private static final String KEY_EAV_LIBELLE = "ev_libelle";
    private static final String KEY_EAV_MIN_CPTE = "ev_min_cpte";
    private static final String KEY_EAV_IS_MIN_CPTE_OBLIG = "ev_is_min_cpte_oblig";
    private static final String KEY_EAV_TX_INTER_AN = "ev_tx_inter_an";
    private static final String KEY_EAV_IS_TX_INTER_AN_OBLIG = "ev_is_tx_inter_an_oblig";
    private static final String KEY_EAV_TYP_DAT_VAL = "ev_typ_dat_val";
    private static final String KEY_EAV_TYP_DAT_RETRAIT_VAL = "ev_date_retrait";
    private static final String KEY_EAV_IS_MULTI_EAV_ON = "ev_is_multi_eav_on";
    private static final String KEY_EAV_IS_PAIE_PS_ON = "ev_is_paie_ps_on";
    private static final String KEY_EAV_IS_AGIOS_ON = "ev_is_agios_on";
    private static final String KEY_EAV_TYP_FR_AGIOS = "ev_typ_fr_agios";
    private static final String KEY_EAV_MT_TX_AGIOS_PRELEV = "ev_mt_tx_agios_prelev";
//    private static final String KEY_EAV_PLAGE_AGIOS_FROM = "ev_plage_agios_from";
//    private static final String KEY_EAV_PLAGE_AGIOS_TO = "ev_plage_agios_to";
    private static final String KEY_EAV_IS_CHEQUE_ON = "ev_is_cheque_on";
//    private static final String KEY_EAV_FRAIS_CLOT_CPT = "ev_frais_clot_cpt";
    private static final String KEY_CAISSE_ID = "ev_caisse_id";
    private static final String KEY_GX_NUMERO = "ev_gx_numero";


    private Switch CcIsChequierM1On;
    private Switch CcIsChequierM2On;
    private LinearLayout ll_bloc_chequier;
    private LinearLayout bloc_cc2;
    private LinearLayout bloc_cc3;

    private String st_CcIsChequierM1On, st_CcIsChequierM2On, st_CcNbPagesCheqM1,st_CcPrixVteCheqM1,st_CcNbPagesCheqM2,
            st_CcPrixVteCheqM2;
    private EditText CcNbPagesCheqM1,CcPrixVteCheqM1,CcNbPagesCheqM2,CcPrixVteCheqM2;
    private static final String KEY_CcIsChequierM1On = "EvIsCheqTyp1On";
    private static final String KEY_CcNbPagesCheqM1 = "EvNbPageCheqT1";
    private static final String KEY_CcPrixVteCheqM1 = "EvMtCheqTyp1";
    private static final String KEY_CcIsChequierM2On = "EvIsCheqTyp2On";
    private static final String KEY_CcNbPagesCheqM2 = "EvNbPageCheqT2";
    private static final String KEY_CcPrixVteCheqM2 = "EvMtCheqTyp2";


    private String eavId;
    private Boolean action_to_affect;
    private TextView headerEAVTextView;

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
    private String ev_plage_agios_from;
    private String ev_plage_agios_to;
    private String ev_is_cheque_on;
//    private String ev_frais_clot_cpt;

    private LinearLayout blkPlageEav;
    private LinearLayout LL_TypeFraisCpteEAV;
    private RadioButton rbEpTypTxInterFixe;
    private RadioButton rbEpTypTxInterTaux;
    private RadioButton rbEpTypTxInterPlage;
    private TextInputLayout layout_TauxAPreleveCpteEAV;

    private TextInputLayout layout_MinCompteEAV;
    private TextInputLayout layout_TauxInteretAnnuelEAV;

    private Button deleteButton;
    private br.com.simplepass.loadingbutton.customViews.CircularProgressButton annulerButton;
    private Button updateButton;
    private int success;
    private ProgressDialog pDialog;
    private TextView tv_header_produit;


    /*begin*/
    private static final String KEY_EAV_BASE_TX_INTER_AN = "ev_base_tx_inter_an";
    private static final String KEY_EAV_BASE_TX_AGIOS_PRELEV = "ev_base_tx_agios_prelev";
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





    private String base_ev_mt_tx_agios_prelev;
    private String base_ev_tx_inter_an;
    private int ev_caisse_id = MyData.CAISSE_ID;

    private TextInputLayout layout_DateValeur;
    private TextInputLayout layout_DateRetrait;
    private TextInputLayout layout_BaseTauxAPreleveCpteEAV;
    private TextInputLayout layout_BaseInteretAnnuelEAV;

    private JRSpinner mySpinnerLocalite; //pour gérer le spinner contenant les bases des taux pour les frais de tenue de compte
    private JRSpinner mySpinnerBaseTxIntMensuel; //pour gérer le spinner contenant les localités

    public static ArrayList<ModelPlageData> plageDataList; //to manage plageData
    private TextView tv_config_plage_tiv;
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
       // setContentView(R.layout.fragment_param_produit_eav);
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



        Intent intent = getIntent();
        headerEAVTextView = (TextView) findViewById(R.id.header_eav);


        tv_header_produit = (TextView) findViewById(R.id.header_produit);
        tv_header_produit.setText("Produit EAV\n"+"Guichet: "+ MyData.GUICHET_NAME);


        tv_config_plage_tiv = (TextView) findViewById(R.id.tv_plage_tiv_eav);

        /*Begin*/
        ev_codeEditText = (EditText) findViewById(R.id.input_txt_Code_EAV);
        ev_codeEditText.setEnabled(false);
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


        ev_mt_tx_agios_prelevEditText = (EditText) findViewById(R.id.input_txt_TauxAPreleveCpteEAV);
//        ev_plage_agios_fromEditText = (EditText) findViewById(R.id.txt_EvTypTxInterFrom);
//        ev_plage_agios_toEditText = (EditText) findViewById(R.id.txt_EvTypTxInterTo);
        ev_is_cheque_onSwitch = (Switch) findViewById(R.id.SwitchChequeOnEAV);
//        ev_frais_clot_cptEditText = (EditText) findViewById(R.id.input_txt_FraisClotureCpteEAV);

        //ev_frais_clot_cptEditText.addTextChangedListener(new DigitFormatWatcher(ev_frais_clot_cptEditText));



        rbEpTypTxInterFixe = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);

        rbEpTypTxInterTaux = (RadioButton) findViewById(R.id.rbEpTypTxInterTaux);
        rbEpTypTxInterPlage = (RadioButton) findViewById(R.id.rbEpTypTxInterPlage);
        blkPlageEav = (LinearLayout) findViewById(R.id.blk_plage_eav);
        LL_TypeFraisCpteEAV = (LinearLayout) findViewById(R.id.ll_TypeFraisCpteEAV);

        layout_MinCompteEAV = (TextInputLayout) findViewById(R.id.input_layout_MinCompteEAV);
        layout_TauxInteretAnnuelEAV = (TextInputLayout) findViewById(R.id.input_layout_TauxInteretAnnuelEAV);
        layout_DateValeur = (TextInputLayout) findViewById(R.id.input_layout_type_de_date);
        layout_DateRetrait = (TextInputLayout) findViewById(R.id.input_layout_type_de_date_retrait);
        layout_TauxAPreleveCpteEAV = (TextInputLayout) findViewById(R.id.input_layout_TauxAPreleveCpteEAV);
        layout_BaseTauxAPreleveCpteEAV = (TextInputLayout) findViewById(R.id.input_layout_BaseTauxAPreleveCpteEAV);
        layout_BaseInteretAnnuelEAV = (TextInputLayout) findViewById(R.id.input_layout_BaseInteretAnnuelEAV);

        mySpinnerLocalite = (JRSpinner)findViewById(R.id.spn_my_spinner_base_taux);
        mySpinnerBaseTxIntMensuel = (JRSpinner)findViewById(R.id.spn_my_spinner_base_taux_mensuel);

        mySpinnerLocalite.setItems(getResources().getStringArray(R.array.array_ev_base_tx_agios_prelev)); //this is important, you must set it to set the item list
        mySpinnerLocalite.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        mySpinnerLocalite.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically

        mySpinnerLocalite.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position
                //  cxLocalite = mySpinnerLocalite.getText().toString();
                // Log.d("iddddddd***",caisseLocalite);
            }
        });
        mySpinnerBaseTxIntMensuel.setItems(getResources().getStringArray(R.array.array_ev_base_tx_inter_an)); //this is important, you must set it to set the item list
        mySpinnerBaseTxIntMensuel.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        mySpinnerBaseTxIntMensuel.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically

        mySpinnerBaseTxIntMensuel.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position
                //  cxLocalite = mySpinnerLocalite.getText().toString();
                // Log.d("iddddddd***",caisseLocalite);
            }
        });

        /*End*/


        eavId = intent.getStringExtra(KEY_EAV_ID);
        action_to_affect = getIntent().getExtras().getBoolean(KEY_EXTRA_ACTION_TO_AFFECT);

        new FetchEavDetailsAsyncTask().execute();
        deleteButton = (Button) findViewById(R.id.btn_delete_eav);

        updateButton = (Button) findViewById(R.id.btn_save_eav);
        annulerButton = (CircularProgressButton) findViewById(R.id.btn_clean);
        annulerButton.setVisibility(View.VISIBLE);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        if (action_to_affect){
            headerEAVTextView.setText("Affectation du produit au guichet ");
            updateButton.setText("AFFECTER");
            deleteButton.setVisibility(View.GONE);

        }else{
            headerEAVTextView.setText("Mise à jour du produit ");
            updateButton.setText("MODIFIER");
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirmDelete();
                }
            });
        }


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    updateEAV();

                } else {
                    Toast.makeText(UpdateEAVForGuichet.this,
                            "Unable to connect to internet",
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
                    ListPlageTIV.IS_TO_CREATE_OR_TO_UPDATE = false;
                    Intent i = new Intent(UpdateEAVForGuichet.this,ListPlageTIV.class);

                    i.putExtra(KEY_EAV_ID, eavId);
                    startActivityForResult(i, 20);

                } else {
                    Toast.makeText(UpdateEAVForGuichet.this,
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
                    ListPlageFCV.IS_TO_CREATE_OR_TO_UPDATE = false;
                    Intent i = new Intent(UpdateEAVForGuichet.this,ListPlageFCV.class);

                    i.putExtra(KEY_EAV_ID, eavId);
                    startActivityForResult(i, 20);

                } else {
                    Toast.makeText(UpdateEAVForGuichet.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });


    }
    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Mise à jour d'un produit EAV");

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
     * Fetches single movie details from the server
     */
    private class FetchEavDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateEAVForGuichet.this);
            pDialog.setMessage("Loading EAV Details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_EAV_ID, eavId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_eav_details.php", "GET", httpParams);
            try {
       /*         int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject eav;
                if (success == 1) {
                    //Parse the JSON response
                    eav = jsonObject.getJSONObject(KEY_DATA);
*/
                    ev_code = MyData.normalizeSymbolsAndAccents(jsonObject.getString(KEY_EAV_CODE));
                    ev_libelle = MyData.normalizeSymbolsAndAccents( jsonObject.getString(KEY_EAV_LIBELLE));

                    ev_min_cpte = jsonObject.getString(KEY_EAV_MIN_CPTE);
                    ev_is_min_cpte_oblig = jsonObject.getString(KEY_EAV_IS_MIN_CPTE_OBLIG);
//                    Log.e("***KEY_EAV_IS_MIN_CPTE_OBLIG",ev_is_min_cpte_oblig+"");
                    ev_tx_inter_an = jsonObject.getString(KEY_EAV_TX_INTER_AN);
                base_ev_tx_inter_an =ProduitEAV.decodeEvBaseTxInterAn(jsonObject.getString(KEY_EAV_BASE_TX_INTER_AN));
                    ev_is_tx_inter_an_oblig = jsonObject.getString(KEY_EAV_IS_TX_INTER_AN_OBLIG);
                    ev_typ_dat_val = jsonObject.getString(KEY_EAV_TYP_DAT_VAL);
                    ev_typ_dat_retrait_val = jsonObject.getString(KEY_EAV_TYP_DAT_RETRAIT_VAL);
                    ev_is_multi_eav_on = jsonObject.getString(KEY_EAV_IS_MULTI_EAV_ON);
                    ev_is_paie_ps_on = jsonObject.getString(KEY_EAV_IS_PAIE_PS_ON);
                    ev_is_agios_on = jsonObject.getString(KEY_EAV_IS_AGIOS_ON);
                    ev_typ_fr_agios =jsonObject.getString(KEY_EAV_TYP_FR_AGIOS);
                    ev_mt_tx_agios_prelev = jsonObject.getString(KEY_EAV_MT_TX_AGIOS_PRELEV);
                base_ev_mt_tx_agios_prelev = ProduitEAV.decodeEvBaseTxAgiosPrelev(jsonObject.getString(KEY_EAV_BASE_TX_AGIOS_PRELEV));
//                    ev_plage_agios_from = jsonObject.getString(KEY_EAV_PLAGE_AGIOS_FROM);
//                    ev_plage_agios_to = jsonObject.getString(KEY_EAV_PLAGE_AGIOS_TO);
                    ev_is_cheque_on = jsonObject.getString(KEY_EAV_IS_CHEQUE_ON);
//                    ev_frais_clot_cpt = jsonObject.getString(KEY_EAV_FRAIS_CLOT_CPT);

                    st_CcIsChequierM1On = jsonObject.getString(KEY_CcIsChequierM1On);
                    st_CcNbPagesCheqM1 = jsonObject.getString(KEY_CcNbPagesCheqM1);
                    st_CcPrixVteCheqM1 = jsonObject.getString(KEY_CcPrixVteCheqM1);
                    st_CcIsChequierM2On = jsonObject.getString(KEY_CcIsChequierM2On);
                    st_CcNbPagesCheqM2 = jsonObject.getString(KEY_CcNbPagesCheqM2);
                    st_CcPrixVteCheqM2 = jsonObject.getString(KEY_CcPrixVteCheqM2);


                    EvNatureFraisClot = jsonObject.getString(KEY_EvNatureFraisClot);
                    EvValFraisCloture = jsonObject.getString(KEY_EvValFraisCloture);
                    EvBaseFraisCloture = ProduitEAV.decodeEvBaseFraisCloture(jsonObject.getString(KEY_EvBaseFraisCloture));


                //}
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    //Populate the Edit Texts once the network activity is finished executing


                    ev_codeEditText.setText(ev_code);
                    ev_libelleEditText.setText(ev_libelle);
                    ev_min_cpteEditText.setText(ev_min_cpte);
                    if (ev_is_min_cpte_oblig.equals("Y")){
                        ev_is_min_cpte_obligSwitch.setChecked(true);
                    }else{
                        ev_is_min_cpte_obligSwitch.setChecked(false);
                    }
                    onSwitchButtonClicked(ev_is_min_cpte_obligSwitch);
                    ev_tx_inter_anEditText.setText(ev_tx_inter_an);
                    mySpinnerBaseTxIntMensuel.setText(base_ev_tx_inter_an);
                    if (ev_is_tx_inter_an_oblig.equals("Y")){
                        ev_is_tx_inter_an_obligSwitch.setChecked(true);
                    }else{
                        ev_is_tx_inter_an_obligSwitch.setChecked(false);
                    }
                    onSwitchButtonClicked(ev_is_tx_inter_an_obligSwitch);
                    ev_typ_dat_valEditText.setText(ev_typ_dat_val);
                    ev_typ_dat_retrait_valEditText.setText(ev_typ_dat_retrait_val);
                    if (ev_is_multi_eav_on.equals("Y")){
                        ev_is_multi_eav_onSwitch.setChecked(true);
                    }else{
                        ev_is_multi_eav_onSwitch.setChecked(false);
                    }
                    if (ev_is_paie_ps_on.equals("Y")){
                        ev_is_paie_ps_onSwitch.setChecked(true);
                    }else{
                        ev_is_paie_ps_onSwitch.setChecked(false);
                    }
                    if (ev_is_agios_on.equals("Y")){
                        ev_is_agios_onSwitch.setChecked(true);
                    }else{
                        ev_is_agios_onSwitch.setChecked(false);
                    }

                    if (ev_typ_fr_agios.equals("F")){
                        rbEpTypTxInterFixe.setChecked(true);
                        onRadioButtonClicked(rbEpTypTxInterFixe);
                    }else if (ev_typ_fr_agios.equals("T")){
                        rbEpTypTxInterTaux.setChecked(true);
                        onRadioButtonClicked(rbEpTypTxInterTaux);
                    }else if (ev_typ_fr_agios.equals("P")){
                        rbEpTypTxInterPlage.setChecked(true);
                        onRadioButtonClicked(rbEpTypTxInterPlage);}
                    onSwitchButtonClicked(ev_is_agios_onSwitch);

                    //ev_typ_fr_agiosEditText.setText(ev_typ_fr_agios);
                    ev_mt_tx_agios_prelevEditText.setText(ev_mt_tx_agios_prelev);
                    mySpinnerLocalite.setText(base_ev_mt_tx_agios_prelev);
//                    ev_plage_agios_fromEditText.setText(ev_plage_agios_from);
//                    ev_plage_agios_toEditText.setText(ev_plage_agios_to);
                    if (ev_is_cheque_on.equals("Y")){
                        ev_is_cheque_onSwitch.setChecked(true);
                    }else{
                        ev_is_cheque_onSwitch.setChecked(false);
                    }
//                    ev_frais_clot_cptEditText.setText(ev_frais_clot_cpt);

                    if (st_CcIsChequierM1On.equals("Y") || st_CcIsChequierM1On.equals("y")){
                        CcIsChequierM1On.setChecked(true);

                    }else{
                        CcIsChequierM1On.setChecked(false);
                    }
                    if (st_CcIsChequierM2On.equals("Y") || st_CcIsChequierM2On.equals("y")){
                        CcIsChequierM2On.setChecked(true);
                    }else{
                        CcIsChequierM2On.setChecked(false);
                    }
                    onSwitchButtonClicked(ev_is_cheque_onSwitch);
                    onSwitchButtonClicked(CcIsChequierM1On);
                    onSwitchButtonClicked(CcIsChequierM2On);

                    CcNbPagesCheqM1.setText(st_CcNbPagesCheqM1);
                    CcPrixVteCheqM1.setText(st_CcPrixVteCheqM1);
                    CcNbPagesCheqM2.setText(st_CcNbPagesCheqM2);
                    CcPrixVteCheqM2.setText(st_CcPrixVteCheqM2);

                    if (EvNatureFraisClot.equals("F")){
                        rbEvNatureFraisClotFixe.setChecked(true);
                        onRadioButtonClicked(rbEvNatureFraisClotFixe);
                    }else if (EvNatureFraisClot.equals("T")){
                        rbEvNatureFraisClotTaux.setChecked(true);
                        onRadioButtonClicked(rbEvNatureFraisClotTaux);
                    }else if (EvNatureFraisClot.equals("P")){
                        rbEvNatureFraisClotPlage.setChecked(true);
                        onRadioButtonClicked(rbEvNatureFraisClotPlage);
                    }

                    ET_EvValFraisCloture.setText(EvValFraisCloture);
                    JR_EvBaseFraisCloture.setText(EvBaseFraisCloture);


                }
            });
        }


    }

    /**
     * Displays an alert dialogue to confirm the deletion
     */
    private void confirmDelete() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                UpdateEAVForGuichet.this);
        alertDialogBuilder.setMessage("Are you sure, you want to delete this EAV?");
        alertDialogBuilder.setPositiveButton("Delete",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                            //If the user confirms deletion, execute DeleteMovieAsyncTask
                            new UpdateEAVForGuichet.DeleteMovieAsyncTask().execute();
                        } else {
                            Toast.makeText(UpdateEAVForGuichet.this,
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
            pDialog = new ProgressDialog(UpdateEAVForGuichet.this);
            pDialog.setMessage("Deleting EAV. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Set movie_id parameter in request
            httpParams.put(KEY_EAV_ID, eavId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "delete_eav.php", "POST", httpParams);
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
                        Toast.makeText(UpdateEAVForGuichet.this,
                                "EAV Deleted", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie deletion
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(UpdateEAVForGuichet.this,
                                "Some error occurred while deleting EAV",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    /**
     * Checks whether all files are filled. If so then calls UpdateEavAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */

    private void updateEAV() {

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



                new UpdateEAVForGuichet.UpdateEavAsyncTask().execute();
            }catch (Exception e){
                e.printStackTrace();
            }
        } else {
            Toast.makeText(UpdateEAVForGuichet.this,
                    "One or more fields left empty!",
                    Toast.LENGTH_LONG).show();

        }


    }
    /**
     * AsyncTask for updating a movie details
     */

    private class UpdateEavAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateEAVForGuichet.this);
            if (action_to_affect){
                pDialog.setMessage("Assigning to guichet. Please wait...");
            }else{
                pDialog.setMessage("Updating. Please wait...");
            }

            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters

            /**/
            httpParams.put(KEY_EAV_ID, eavId);

            httpParams.put(KEY_EAV_CODE, ev_code);
            httpParams.put(KEY_EAV_LIBELLE, ev_libelle);
            httpParams.put(KEY_EAV_MIN_CPTE, ev_min_cpte);
            httpParams.put(KEY_EAV_IS_MIN_CPTE_OBLIG, ev_is_min_cpte_oblig.toString());

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

            httpParams.put(KEY_GX_NUMERO, MyData.GUICHET_ID+"");
            httpParams.put(KEY_CAISSE_ID, MyData.CAISSE_ID+"");
            /**/


            JSONObject jsonObject =(action_to_affect)?
                    httpJsonParser.makeHttpRequest(
                    BASE_URL + "assign_eav_to_guichet.php", "POST", httpParams)
                    :
                    httpJsonParser.makeHttpRequest(
                            BASE_URL + "update_eav.php", "POST", httpParams);
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
                        if (action_to_affect){
                            //Display success message
                            Toast.makeText(UpdateEAVForGuichet.this,
                                    "Successful assigning", Toast.LENGTH_LONG).show();
                            Intent i = getIntent();
                            //send result code 20 to notify about movie update
                            setResult(20, i);
                            finish();
                        }else{
                            //Display success message
                            Toast.makeText(UpdateEAVForGuichet.this,
                                    "Update success", Toast.LENGTH_LONG).show();
                            Intent i = getIntent();
                            //send result code 20 to notify about movie update
                            setResult(20, i);
                            finish();
                        }


                    } else {
                        Toast.makeText(UpdateEAVForGuichet.this,
                                "Some error occurred, check and retry ",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}
