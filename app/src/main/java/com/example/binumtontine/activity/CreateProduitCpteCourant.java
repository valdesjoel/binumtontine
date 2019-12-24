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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

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
    private static final String KEY_CcPlageTxCommMvmFrom = "CcPlageTxCommMvmFrom";
    private static final String KEY_CcPlageTxCommMvmTo = "CcPlageTxCommMvmTo";


    private static String STRING_EMPTY = "";

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





    private RadioButton CcNatureTxIntDecouvFixe,CcNatureTxIntDecouvPlage;
    private RadioButton CcNatureTypDureDecouvJour,CcNatureTypDureDecouvSemaine,CcNatureTypDureDecouvMois;
    private RadioButton CcNatureTxMtAgioTaux, CcNatureTxMtAgioMontant, CcNatureTxMtAgioPlage;
    private RadioButton CcNatTxCommFixe, CcNatTxCommMontant, CcNatTxCommPlage;


    private RadioButton rbEpTypTxInterPlage;
    private TextInputLayout input_layout_CcValTxMtAgio;
    private TextInputLayout input_layout_CcValTxIntDecouv;
    private TextInputLayout input_layout_CcValTxCommMvm;
    private LinearLayout blk_plage_cc;
    private LinearLayout blk_plage_agios_cc;
    private LinearLayout blk_plage_CcValTxCommMvm;

    private Button addButton;
    private Button cancelButton;
    private Button deleteButton;
    private int success;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cpte_courant);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_produit_eav);
        setSupportActionBar(toolbar);
        setToolbarTitle(); */

/*

        ev_codeEditText = (EditText) findViewById(R.id.input_txt_Code_EAV);
        ev_libelleEditText = (EditText) findViewById(R.id.input_txt_LibelleEAV);
        ev_min_cpteEditText = (EditText) findViewById(R.id.input_txt_MinCompteEAV);
        ev_is_min_cpte_obligSwitch = (Switch) findViewById(R.id.SwitchMinCpteEAVOblig);
        ev_tx_inter_anEditText = (EditText) findViewById(R.id.input_txt_TauxInteretAnnuelEAV);
        ev_is_tx_inter_an_obligSwitch = (Switch) findViewById(R.id.SwitchTauxInteretAnnuelEAV);
        ev_typ_dat_valEditText = (EditText) findViewById(R.id.input_txt_type_de_date);
        ev_is_multi_eav_onSwitch = (Switch) findViewById(R.id.SwitchMultiEAV);
        ev_is_paie_ps_onSwitch = (Switch) findViewById(R.id.SwitchPayerPSOnEAV);
        ev_is_agios_onSwitch = (Switch) findViewById(R.id.SwitchFraisTenuCpteOnEAV);

        //ev_typ_fr_agiosEditText = (EditText) findViewById(R.id.input_txt_TauxAPreleveCpteEAV);

        ev_mt_tx_agios_prelevEditText = (EditText) findViewById(R.id.input_txt_TauxAPreleveCpteEAV);
        ev_plage_agios_fromEditText = (EditText) findViewById(R.id.txt_EvTypTxInterFrom);
        ev_plage_agios_toEditText = (EditText) findViewById(R.id.txt_EvTypTxInterTo);
        ev_is_cheque_onSwitch = (Switch) findViewById(R.id.SwitchChequeOnEAV);
        ev_frais_clot_cptEditText = (EditText) findViewById(R.id.input_txt_FraisClotureCpteEAV);

        CcNatureTxIntDecouvFixe = (RadioButton) findViewById(R.id.CcNatureTxIntDecouvFixe);
        CcNatureTxIntDecouvPlage = (RadioButton) findViewById(R.id.CcNatureTxIntDecouvPlage);
        rbEpTypTxInterPlage = (RadioButton) findViewById(R.id.rbEpTypTxInterPlage);
        blk_plage_cc = (LinearLayout) findViewById(R.id.blk_plage_eav);
        input_layout_CcValTxMtAgio = (TextInputLayout) findViewById(R.id.input_layout_TauxAPreleveCpteEAV);


*/


        rbEpTypTxInterPlage = (RadioButton) findViewById(R.id.rbEpTypTxInterPlage);
        blk_plage_cc = (LinearLayout) findViewById(R.id.blk_plage_cc);
        blk_plage_agios_cc = (LinearLayout) findViewById(R.id.blk_plage_agios_cc);
        blk_plage_CcValTxCommMvm = (LinearLayout) findViewById(R.id.blk_plage_CcValTxCommMvm);
        input_layout_CcValTxMtAgio = (TextInputLayout) findViewById(R.id.input_layout_CcValTxMtAgio);
        input_layout_CcValTxIntDecouv = (TextInputLayout) findViewById(R.id.input_layout_CcValTxIntDecouv);
        input_layout_CcValTxCommMvm = (TextInputLayout) findViewById(R.id.input_layout_CcValTxCommMvm);

        CcCode = (EditText) findViewById(R.id.input_txtCodeCC);
        CcLibelle = (EditText) findViewById(R.id.input_txt_LibelleCC);
        CcIsDecouvOn= (Switch) findViewById(R.id.SwitchAutoriserDecouvertCC);
        CcMtMaxDecouv= (EditText) findViewById(R.id.input_txt_MaxMtDecouvertAutoriseCC);
        CcIsMaxDecouvNeg= (Switch) findViewById(R.id.SwitchPlafondDecouvertNegociableCC);

        //CcNatureTxIntDecouv= (EditText) findViewById(R.id.SwitchPlafondDecouvertNegociableCC);
        CcNatureTxIntDecouvFixe = (RadioButton) findViewById(R.id.rb_CcNatureTxIntDecouv_Fixe);
        CcNatureTxIntDecouvPlage = (RadioButton) findViewById(R.id.rb_CcNatureTxIntDecouv_Plage);

        CcValTxIntDecouv= (EditText) findViewById(R.id.input_txt_CcValTxMtAgio);
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
                CcNatBaseAgio=(EditText) findViewById(R.id.input_txt_NatureBaseCalculAgiosCC);
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


        deleteButton = (Button) findViewById(R.id.btn_delete_cc);
        deleteButton.setVisibility(View.GONE);
        addButton = (Button) findViewById(R.id.btn_save_cc);
        cancelButton = (Button) findViewById(R.id.btn_clean);
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
    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Ajout d'un produit Compte courant");

    }

    public void onRadioButtonClicked(View view) {
        boolean checked1 = ((RadioButton) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.rb_CcNatureTxIntDecouv_Fixe:
                if (CcNatureTxIntDecouvFixe.isChecked()) {
                   // ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    st_CcNatureTxIntDecouv ="F";
                    str = checked1?"Type Fixe Selected":"Type Fixe Deselected";
                    blk_plage_cc.setVisibility(View.GONE);
                    input_layout_CcValTxMtAgio.setVisibility(View.VISIBLE);

                }

                break;
            case R.id.rb_CcNatureTxIntDecouv_Plage:
                if (CcNatureTxIntDecouvPlage.isChecked()){
                   // ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    str = checked1?"Type Plage Selected":"Type Plage Deselected";
                    st_CcNatureTxIntDecouv ="P";
                    blk_plage_cc.setVisibility(View.VISIBLE);
                input_layout_CcValTxMtAgio.setVisibility(View.GONE);
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
                    st_CcNatureTypDureDecouv ="J";
                   /* blk_plage_cc.setVisibility(View.VISIBLE);
                    input_layout_CcValTxMtAgio.setVisibility(View.GONE);*/
                }


                break;
            case R.id.rb_CcNatureTxMtAgio_taux:
                if (CcNatureTxMtAgioTaux.isChecked()){
                    str = checked1?"Type Taux Selected":"Type Taux Deselected";
                    st_CcNatureTxMtAgio ="T";
                    blk_plage_agios_cc.setVisibility(View.GONE);
                    input_layout_CcValTxIntDecouv.setVisibility(View.VISIBLE);
                }


                break;
            case R.id.rb_CcNatureTxMtAgio_montant:
                if (CcNatureTxMtAgioMontant.isChecked()){
                    str = checked1?"Type Montant Selected":"Type Montant Deselected";
                    st_CcNatureTxMtAgio ="M";
                   blk_plage_agios_cc.setVisibility(View.GONE);
                    input_layout_CcValTxIntDecouv.setVisibility(View.VISIBLE);
                }


                break;
            case R.id.rb_CcNatureTxMtAgio_plage:
                if (CcNatureTxMtAgioPlage.isChecked()){
                    str = checked1?"Type Plage Selected":"Type Plage Deselected";
                    st_CcNatureTxMtAgio ="P";
                    blk_plage_agios_cc.setVisibility(View.VISIBLE);
                    input_layout_CcValTxIntDecouv.setVisibility(View.GONE);
                }


                break;
            case R.id.rb_CcNatTxComm_fixe:
                if (CcNatTxCommFixe.isChecked()){
                    str = checked1?"Type Fixe Selected":"Type Fixe Deselected";
                    st_CcNatTxComm ="F";
                    blk_plage_CcValTxCommMvm.setVisibility(View.GONE);
                    input_layout_CcValTxCommMvm.setVisibility(View.VISIBLE);
                }


                break;
            case R.id.rb_CcNatTxComm_Montant:
                if (CcNatTxCommMontant.isChecked()){
                    str = checked1?"Type Montant Selected":"Type Montant Deselected";
                    st_CcNatTxComm ="M";
                    blk_plage_CcValTxCommMvm.setVisibility(View.GONE);
                    input_layout_CcValTxCommMvm.setVisibility(View.VISIBLE);
                }


                break;
            case R.id.rb_CcNatTxComm_Plage:
                if (CcNatTxCommPlage.isChecked()){
                    str = checked1?"Type Plage Selected":"Type Plage Deselected";
                    st_CcNatTxComm ="P";
                    blk_plage_CcValTxCommMvm.setVisibility(View.VISIBLE);
                    input_layout_CcValTxCommMvm.setVisibility(View.GONE);
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
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }



    /**
     * Checks whether all files are filled. If so then calls AddCpteCourantAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addCompteCourant() {
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
           /* ev_code = ev_codeEditText.getText().toString();
            ev_libelle = ev_libelleEditText.getText().toString();
            ev_min_cpte = ev_min_cpteEditText.getText().toString();
            ev_is_min_cpte_oblig = ev_is_min_cpte_obligSwitch.isChecked();
            ev_tx_inter_an = ev_tx_inter_anEditText.getText().toString();
            ev_is_tx_inter_an_oblig = ev_is_tx_inter_an_obligSwitch.isChecked();
            ev_typ_dat_val = ev_typ_dat_valEditText.getText().toString();
            ev_is_multi_eav_on = ev_is_multi_eav_onSwitch.isChecked();
            ev_is_paie_ps_on = ev_is_paie_ps_onSwitch.isChecked();
            ev_is_agios_on = ev_is_agios_onSwitch.isChecked();

            //ev_typ_fr_agios = ev_typ_fr_agiosEditText.getText().toString();

            ev_mt_tx_agios_prelev = ev_mt_tx_agios_prelevEditText.getText().toString();
            ev_plage_agios_from = ev_plage_agios_fromEditText.getText().toString();
            ev_plage_agios_to = ev_plage_agios_toEditText.getText().toString();
            ev_is_cheque_on = ev_is_cheque_onSwitch.isChecked();
            ev_frais_clot_cpt = ev_frais_clot_cptEditText.getText().toString(); */

            st_CcCode=  CcCode.getText().toString();
            st_CcLibelle=  CcLibelle.getText().toString();
            st_CcIsDecouvOn=  CcIsDecouvOn.isChecked();
            st_CcMtMaxDecouv=  CcMtMaxDecouv.getText().toString();
            st_CcIsMaxDecouvNeg=  CcIsMaxDecouvNeg.isChecked();
            //st_CcNatureTxIntDecouv=  CcNatureTxIntDecouv.getText().toString();
            st_CcValTxIntDecouv=  CcValTxIntDecouv.getText().toString();
    st_CcPlageTxIntDecouvFrom=  CcPlageTxIntDecouvFrom.getText().toString();
    st_CcPlageTxIntDecouvTo=  CcPlageTxIntDecouvTo.getText().toString();
    st_CcDureeMaxDecouv=  CcDureeMaxDecouv.getText().toString();
   // st_CcNatureTypDureDecouv=  CcNatureTypDureDecouv.getText().toString();
    //st_CcNatureTxMtAgio=  CcNatureTxMtAgio.getText().toString();
    st_CcValTxMtAgio=  CcValTxMtAgio.getText().toString();
    st_CcPlageTxMtAgioFrom=  CcPlageTxMtAgioFrom.getText().toString();
            st_CcPlageTxMtAgioTo=  CcPlageTxMtAgioTo.getText().toString();
    //st_CcNatBaseAgio=  CcNatBaseAgio.getText().toString();
    st_CcIsChequierM1On=  CcIsChequierM1On.isChecked();
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
    st_CcPlageTxCommMvmFrom=  CcPlageTxCommMvmFrom.getText().toString();
    st_CcPlageTxCommMvmTo=  CcPlageTxCommMvmTo.getText().toString();





            new AddCpteCourantAsyncTask().execute();
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
            httpParams.put(KEY_CcIsDecouvOn, st_CcIsDecouvOn.toString());
            httpParams.put(KEY_CcMtMaxDecouv, st_CcMtMaxDecouv);
            httpParams.put(KEY_CcIsMaxDecouvNeg, st_CcIsMaxDecouvNeg.toString());
            httpParams.put(KEY_CcNatureTxIntDecouv, st_CcNatureTxIntDecouv);
            httpParams.put(KEY_CcValTxIntDecouv, st_CcValTxIntDecouv);
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
            httpParams.put(KEY_CcPlageTxCommMvmFrom, st_CcPlageTxCommMvmFrom);
            httpParams.put(KEY_CcPlageTxCommMvmTo, st_CcPlageTxCommMvmTo);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_cpte_courant.php", "POST", httpParams);
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