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
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateProduitEAT extends AppCompatActivity implements SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";



    private static final String KEY_EAT_CODE = "EtCode";
    private static final String KEY_EAT_LIBELLE = "EtLibelle";
    private static final String KEY_EAT_MT_MIN_MISE = "EtMtMinMise";
    private static final String KEY_EAT_MT_MAX_MISE = "EtMtMaxMise";
    private static final String KEY_EAT_DUREE_MIN = "EtDureeMin";
    private static final String KEY_EAT_DUREE_MAX = "EtDureeMax";
    private static final String KEY_EAT_NATURE_PAS = "EtNaturePas";
    private static final String KEY_EAT_NBRE_UNITE_PAS = "EtNbreUnitePas";
    private static final String KEY_EAT_TYPE_TX_INTER = "EtTypTxInter";
    private static final String KEY_EAT_VAL_TX_INTER = "EtValTxInter";
    private static final String KEY_EAT_PLAGE_TX_INTER_FROM = "EtPlageTxInterFrom";
    private static final String KEY_EAT_PLAGE_TX_INTER_TO = "EtPlageTxInterTo";
    private static final String ET_IS_TX_INT_NEG = "EtIsTxIntNeg";
    private static final String ET_IS_PRISE_INT_MISE_ON = "EtIsPriseIntMiseOn";
    private static final String ET_IS_PENAL_RUP_ANT = "EtIsPenalRupAnt";
    private static final String ET_NATURE_RUP_AN = "EtNatureRupAn";
    private static final String ET_VAL_TX_MT_RUPTURE = "EtValTxMtRupture";
    private static final String ET_PLAGE_TX_MT_RUPTURE_FROM = "EtPlageTxMtRuptureFrom";
    private static final String ET_PLAGE_TX_MT_RUPTURE_TO = "EtPlageTxMtRuptureTo";
    private static final String ET_BASE_TX_PENAL = "EtBaseTxPenal";
    private static final String ET_IS_EPAR_RETIRE_FIN = "EtIsEparRetireFin";
    private static final String ET_IS_EPAR_TRANSF_FIN = "EtIsEparTransfFin";
    private static final String ET_IS_ONLY_TOT_TRANSF = "EtIsOnlyTotTransf";
    private static final String ET_IS_EPAR_RENOUV_FIN = "EtIsEparRenouvFin";
    private static final String ET_ACTION_DEF_A_TERME = "EtActionDefATerme";
    private static final String ET_IS_MULTI_EAT_ON = "EtIsMultiEATOn";
    private static final String ET_IS_INTER_DUS_RUP_ANT = "EtIsInterDusRupAnt";
    private static final String ET_IS_NEW_TX_INT_RUP_ANT = "EtIsNewTxIntRupAnt";
    private static final String ET_TYP_NEW_TX_INT_RUP_ANT = "EtTypNewTxIntRupAnt";
    private static final String ET_VAL_TX_INT_PENAL = "EtValTxIntPenal";
    private static final String ET_PLAGE_TX_INT_PENAL_FROM = "EtPlageTxIntPenalFrom";
    private static final String ET_PLAGE_TX_INT_PENAL_TO = "EtPlageTxIntPenalTo";
    private static final String ET_BASE_TX_INT_PENAL = "EtBaseTxIntPenal";
    private static final String ET_TX_INT_PENAL_NEG = "EtTxIntPenalNeg";
    private static final String ET_CAISSE_ID = "EtCaisseId";
    //private static final String ET_GUICHET_ID = "EtGuichetId";


    private static String STRING_EMPTY = "";

    private LinearLayout ll;
    private LinearLayout ll_btn;
    private EditText EtCode_ET;
    private EditText EtLibelleET;
    private EditText EtMtMinMise_ET;
    private EditText EtMtMaxMise_ET;
    private EditText EtDureeMin_ET;
    private EditText EtDureeMax_ET;
    private RadioButton rbEtNaturePasFixe;
    private RadioButton rbEtNaturePasSaut;
    private EditText EtNbreUnitePas_ET;
    private RadioButton rbEtTypTxInterFixe;
    private RadioButton rbEtTypTxInterPlage;
    private EditText EtValTxInter_ET;
    private EditText EtPlageTxInterFrom_ET;
    private EditText EtPlageTxInterTo_ET;
    private EditText EtPlageTxInterValeur_ET;

    private Switch EtIsTxIntNeg_SW;
    private Switch EtIsPriseIntMiseOn_SW;
    private Switch EtIsPenalRupAnt_SW;
    private RadioButton rbEtNatureRupAnTaux;
    private RadioButton rbEtNatureRupAnMontant;
    private RadioButton rbEtNatureRupAnPlage;
    private EditText EtValTxMtRupture_ET;
    private EditText EtPlageTxMtRuptureFrom_ET;
    private EditText EtPlageTxMtRuptureTo_ET;
    private EditText EtBaseTxPenal_ET;

    private Switch EtIsEparRetireFin_SW;
    private Switch EtIsEparTransfFin_SW;
    private Switch EtIsOnlyTotTransf_SW;
    private Switch EtIsEparRenouvFin_SW;
    private Switch EtActionDefATerme_SW;
    private Switch EtIsMultiEATOn_SW;
    private Switch EtIsInterDusRupAnt_SW;
    private Switch EtIsNewTxIntRupAnt_SW;
    private RadioButton rbEtTypNewTxIntRupAntFixe;
    private RadioButton rbEtTypNewTxIntRupAntPlage;
    private EditText EtValTxIntPenal_ET;
    private EditText EtPlageTxIntPenalFrom_ET;
    private EditText EtPlageTxIntPenalTo_ET;
    private EditText EtBaseTxIntPenal_ET;
    private Switch EtTxIntPenalNeg_SW;


    private String EtCode;
    private String EtLibelle;
    private String EtMtMinMise;
    private String EtMtMaxMise;
    private String EtDureeMin;
    private String EtDureeMax;

    private String EtNaturePas;
    private String EtNbreUnitePas;
    private String EtTypTxInter;
    private String EtValTxInter;
    private String EtPlageTxInterFrom;
    private String EtPlageTxInterTo;
    private Boolean EtIsTxIntNeg ;
    private Boolean EtIsPriseIntMiseOn;
    private Boolean EtIsPenalRupAnt ;
    private String EtNatureRupAn ;
    private String EtValTxMtRupture;
    private String EtPlageTxMtRuptureFrom;
    private String EtPlageTxMtRuptureTo ;
    private String EtBaseTxPenal ;
    private Boolean EtIsEparRetireFin;
    private Boolean EtIsEparTransfFin;
    private Boolean EtIsOnlyTotTransf;
    private Boolean EtIsEparRenouvFin;
    private Boolean EtActionDefATerme;
    private Boolean EtIsMultiEATOn ;
    private Boolean EtIsInterDusRupAnt;
    private Boolean EtIsNewTxIntRupAnt ;
    private String EtTypNewTxIntRupAnt;
    private String EtValTxIntPenal;
    private String EtPlageTxIntPenalFrom;
    private String EtPlageTxIntPenalTo;
    private String EtBaseTxIntPenal ;
    private Boolean EtTxIntPenalNeg ;



    private LinearLayout blkPlageEav;


    private RadioButton rbEpTypTxInterPlage;
    private TextInputLayout layout_TauxAPreleveCpteEAV;

    private Button addButton;
    private Button deleteButton;
    private Button cancelButton;
    private int success;
    private ProgressDialog pDialog;

    public int numberOfLinesDebut = 0;
    private List<String> tabPlageDebutList;
    private List<String> tabPlageFinList;
    private List<String> tabPlageValeurList;
    public int numberOfLinesFin = 0;
    public int numberOfLinesValeur = 0;
    private Button remove_button;
    private String tabPlageDebut ="";
    private String tabPlageFin ="";
    private String tabPlageValeur ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_add_movie);
      //  setContentView(R.layout.fragment_param_produit_eav);
        setContentView(R.layout.activity_eat);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_produit_eav);
        setSupportActionBar(toolbar);
        setToolbarTitle(); */


        tabPlageDebutList = new ArrayList<>();
        tabPlageFinList = new ArrayList<>();
        tabPlageValeurList = new ArrayList<>();
        ll = (LinearLayout)findViewById(R.id.blk_EtPlageTxInter);
        ll_btn = (LinearLayout)findViewById(R.id.blk_btn_EtPlageTxInter);
        EtCode_ET = (EditText) findViewById(R.id.input_txt_Code_EAT);
        EtLibelleET = (EditText) findViewById(R.id.input_txt_LibelleEAT);
        EtMtMinMise_ET = (EditText) findViewById(R.id.input_txt_MinMtMiseEAT);
        EtMtMaxMise_ET = (EditText) findViewById(R.id.input_txt_MaxMtMiseEAT);
        EtDureeMin_ET = (EditText) findViewById(R.id.input_txt_DureeMinEAT);
        EtDureeMax_ET = (EditText) findViewById(R.id.input_txt_DureeMaxEAT);

        rbEtNaturePasFixe = (RadioButton) findViewById(R.id.rb_EtNaturePasFixe);
        rbEtNaturePasSaut = (RadioButton) findViewById(R.id.rb_EtNaturePasSaut);
        EtNbreUnitePas_ET = (EditText) findViewById(R.id.input_txt_NbreUnitePasEAT);
        rbEtTypTxInterFixe = (RadioButton) findViewById(R.id.rbEtTypTxInterFixe);
        rbEtTypTxInterPlage = (RadioButton) findViewById(R.id.rbEtTypTxInterPlage);
        EtValTxInter_ET = (EditText) findViewById(R.id.input_txt_ValeurTauxInteretEAT);

        EtPlageTxInterFrom_ET = (EditText) findViewById(R.id.txt_EtValTxInterFrom);
        EtPlageTxInterTo_ET = (EditText) findViewById(R.id.txt_EtValTxInterTo);
        EtPlageTxInterValeur_ET = (EditText) findViewById(R.id.txt_EtValTxInterValeur);

        EtIsTxIntNeg_SW = (Switch) findViewById(R.id.SwitchTauxInteretNegocieEAT);
        EtIsPriseIntMiseOn_SW = (Switch) findViewById(R.id.SwitchPrendreInteretDesLaMiseEAT);
        EtIsPenalRupAnt_SW = (Switch) findViewById(R.id.SwitchPenaliteEnCasDeRuptureEAT);

        rbEtNatureRupAnTaux = (RadioButton) findViewById(R.id.rb_EtNatureRupAnTaux);
        rbEtNatureRupAnMontant = (RadioButton) findViewById(R.id.rb_EtNatureRupAnMontant);
        rbEtNatureRupAnPlage = (RadioButton) findViewById(R.id.rb_EtNatureRupAnPlage);

        EtValTxMtRupture_ET = (EditText) findViewById(R.id.input_txt_ValTxMtRuptureEAT);
        EtPlageTxMtRuptureFrom_ET = (EditText) findViewById(R.id.txt_EtValTxMtRuptureFrom);
        EtPlageTxMtRuptureTo_ET = (EditText) findViewById(R.id.txt_EtValTxMtRuptureTo);
        EtBaseTxPenal_ET = (EditText) findViewById(R.id.input_txt_BaseTxPenalEAT);

        EtIsEparRetireFin_SW = (Switch) findViewById(R.id.SwitchPossibiliteRetirerEAT);
        EtIsEparTransfFin_SW = (Switch) findViewById(R.id.SwitchPossibiliteTransfererVersEAT_EAV);
        EtIsOnlyTotTransf_SW = (Switch) findViewById(R.id.SwitchTransfererTotaliteVersEAT_EAV);
        EtIsEparRenouvFin_SW = (Switch) findViewById(R.id.SwitchPossibiliteRenouvelerEAT);

        EtActionDefATerme_SW = (Switch) findViewById(R.id.SwitchActionParDefautEAT);
        EtIsMultiEATOn_SW = (Switch) findViewById(R.id.SwitchMultiEAT);
        EtIsInterDusRupAnt_SW = (Switch) findViewById(R.id.SwitchInteretDusRuptureEAP);
        EtIsNewTxIntRupAnt_SW = (Switch) findViewById(R.id.SwitchDefinirNouveauTxInteretEAT);
        rbEtTypNewTxIntRupAntFixe = (RadioButton) findViewById(R.id.rbEtTypeNouveauTxInteretRuptureEATFixe);
        rbEtTypNewTxIntRupAntPlage = (RadioButton) findViewById(R.id.rbEtTypeNouveauTxInteretRuptureEATPlage);
        EtValTxIntPenal_ET = (EditText) findViewById(R.id.txtValeurNewTxInteretRuptureEAT);
        EtPlageTxIntPenalFrom_ET = (EditText) findViewById(R.id.txt_EtNewValTxInterFrom);
        EtPlageTxIntPenalTo_ET = (EditText) findViewById(R.id.txt_EtNewValTxInterTo);
        EtBaseTxIntPenal_ET = (EditText) findViewById(R.id.txtBaseNewTxInteretEAT);
        EtTxIntPenalNeg_SW = (Switch) findViewById(R.id.SwitchTxNewInteretNegociableEAT);
        rbEtNaturePasFixe.performClick();
        rbEtTypTxInterFixe.performClick();
        rbEtNatureRupAnTaux.performClick();
        rbEtTypNewTxIntRupAntFixe.performClick();

        remove_button = (Button) findViewById(R.id.remove_button);
        remove_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getAnswer();
                Remove_Line();
            }
        });

        final Button Add_button = (Button) findViewById(R.id.add_button);
        Add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add_Line();
            }
        });



//        rbEpTypTxInterPlage = (RadioButton) findViewById(R.id.rbEpTypTxInterPlage);
//        blkPlageEav = (LinearLayout) findViewById(R.id.blk_plage_eav);
//        layout_TauxAPreleveCpteEAV = (TextInputLayout) findViewById(R.id.input_layout_TauxAPreleveCpteEAV);



       deleteButton = (Button) findViewById(R.id.btn_delete_eat);
        deleteButton.setVisibility(View.GONE);
        addButton = (Button) findViewById(R.id.btn_save_eat);
        cancelButton = (Button) findViewById(R.id.btn_clean);
        //cirLoginButton
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    Intent i = new Intent(CreateProduitEAT.this, PlageData.class);
                    startActivityForResult(i,20);
                   // finish();
                } else {
                    Toast.makeText(CreateProduitEAT.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addEAT();
                } else {
                    Toast.makeText(CreateProduitEAT.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

    }
    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Ajout d'un produit EAT");

    }

//    public void Add_Line() {
//        LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayoutDecisions);
//        // add edittext
//        EditText et = new EditText(this);
//        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        et.setLayoutParams(p);
//        et.setText("Text");
//        et.setId(numberOfLinesDebut + 1);
//        ll.addView(et);
//        numberOfLinesDebut++;
//    }
    public void Add_Line() {
//i get current value on last EditText

        if (numberOfLinesDebut==0){
            tabPlageDebutList.add(EtPlageTxInterFrom_ET.getText().toString());
            tabPlageFinList.add(EtPlageTxInterTo_ET.getText().toString());
            tabPlageValeurList.add(EtPlageTxInterValeur_ET.getText().toString());
        }
        if (numberOfLinesDebut>0){
            EditText editTextDebut = (EditText)findViewById(numberOfLinesDebut);
            tabPlageDebutList.add(editTextDebut.getText().toString());
            EditText editTextFin = (EditText)findViewById(numberOfLinesFin);
            tabPlageFinList.add(editTextFin.getText().toString());


            EditText editTextValeur = (EditText)findViewById(numberOfLinesValeur);
            tabPlageValeurList.add(editTextValeur.getText().toString());

        }
//        if (numberOfLinesFin>0){
//            EditText editTextFin = (EditText)findViewById(numberOfLinesFin);
//            tabPlageFinList.add(editTextFin.getText().toString());
//        }
//        if (numberOfLinesValeur>0){
//            EditText editTextValeur = (EditText)findViewById(numberOfLinesValeur);
//            tabPlageValeurList.add(editTextValeur.getText().toString());
//        }


        // add edittext debut
        EditText et_debut = new EditText(this);
        TextView tv_debut = new TextView(this);
        tv_debut.setTextColor(Color.GREEN);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        et_debut.setLayoutParams(p);

        et_debut.setHint("De");
        et_debut.setId(numberOfLinesDebut + 1);
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
    public void Remove_Line() {
        //LinearLayout ll = (LinearLayout)findViewById(R.id.blk_EtPlageTxInter);
        if (numberOfLinesDebut>0){


            ll.removeViewAt(ll.getChildCount()-1);
            ll.removeViewAt(ll.getChildCount()-1);
            ll.removeViewAt(ll.getChildCount()-1);
            numberOfLinesDebut--;
            if (numberOfLinesDebut==0){
                ll.removeViewAt(ll.getChildCount()-1);
                remove_button.setVisibility(View.INVISIBLE);
            }
            //ll.removeViewAt(numberOfLinesDebut);
//            ll.removeViewAt(numberOfLinesFin);
//            ll.removeViewAt(numberOfLinesValeur);
//            numberOfLinesDebut--;
//            numberOfLinesFin = numberOfLinesFin-1000;
//            numberOfLinesValeur = numberOfLinesValeur-2000;
        }
        // add edittext debut
//        EditText et_debut = new EditText(this);
//        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        et_debut.setLayoutParams(p);
//        //et.setText("Text");
//        et_debut.setHint("De");
//        et_debut.setId(numberOfLinesDebut + 1);
//        ll.addView(et_debut);
//        numberOfLinesDebut++;
//
//
//        // add edittext fin
//        EditText et_fin = new EditText(this);
//        LinearLayout.LayoutParams p_fin = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        et_fin.setLayoutParams(p_fin);
//        //et.setText("Text");
//        et_fin.setHint("A");
//        et_fin.setId(numberOfLinesFin + 1000);
//        ll.addView(et_fin);
//        numberOfLinesFin++;
//
//        // add edittext valeur
//        EditText et_valeur = new EditText(this);
//        LinearLayout.LayoutParams p_valeur = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        et_valeur.setLayoutParams(p_valeur);
//        //et.setText("Text");
//        et_valeur.setHint("Valeur");
//        et_valeur.setId(numberOfLinesValeur + 2000);
//        ll.addView(et_valeur);
//        numberOfLinesValeur++;
    }

    public void Add_Line_EtValTxMtRupture() {
//i get current value on last EditText

        if (numberOfLinesDebut==0){
            tabPlageDebutList.add(EtPlageTxInterFrom_ET.getText().toString());
            tabPlageFinList.add(EtPlageTxInterTo_ET.getText().toString());
            tabPlageValeurList.add(EtPlageTxInterValeur_ET.getText().toString());
        }
        if (numberOfLinesDebut>0){
            EditText editTextDebut = (EditText)findViewById(numberOfLinesDebut);
            tabPlageDebutList.add(editTextDebut.getText().toString());
            EditText editTextFin = (EditText)findViewById(numberOfLinesFin);
            tabPlageFinList.add(editTextFin.getText().toString());


            EditText editTextValeur = (EditText)findViewById(numberOfLinesValeur);
            tabPlageValeurList.add(editTextValeur.getText().toString());

        }
//        if (numberOfLinesFin>0){
//            EditText editTextFin = (EditText)findViewById(numberOfLinesFin);
//            tabPlageFinList.add(editTextFin.getText().toString());
//        }
//        if (numberOfLinesValeur>0){
//            EditText editTextValeur = (EditText)findViewById(numberOfLinesValeur);
//            tabPlageValeurList.add(editTextValeur.getText().toString());
//        }


        // add edittext debut
        EditText et_debut = new EditText(this);
        TextView tv_debut = new TextView(this);
        tv_debut.setTextColor(Color.GREEN);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        et_debut.setLayoutParams(p);

        et_debut.setHint("De");
        et_debut.setId(numberOfLinesDebut + 1);
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
    public void Remove_Line_EtValTxMtRupture() {
        //LinearLayout ll = (LinearLayout)findViewById(R.id.blk_EtPlageTxInter);
        if (numberOfLinesDebut>0){


            ll.removeViewAt(ll.getChildCount()-1);
            ll.removeViewAt(ll.getChildCount()-1);
            ll.removeViewAt(ll.getChildCount()-1);
            numberOfLinesDebut--;
            if (numberOfLinesDebut==0){
                ll.removeViewAt(ll.getChildCount()-1);
                remove_button.setVisibility(View.INVISIBLE);
            }
            //ll.removeViewAt(numberOfLinesDebut);
//            ll.removeViewAt(numberOfLinesFin);
//            ll.removeViewAt(numberOfLinesValeur);
//            numberOfLinesDebut--;
//            numberOfLinesFin = numberOfLinesFin-1000;
//            numberOfLinesValeur = numberOfLinesValeur-2000;
        }
        // add edittext debut
//        EditText et_debut = new EditText(this);
//        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        et_debut.setLayoutParams(p);
//        //et.setText("Text");
//        et_debut.setHint("De");
//        et_debut.setId(numberOfLinesDebut + 1);
//        ll.addView(et_debut);
//        numberOfLinesDebut++;
//
//
//        // add edittext fin
//        EditText et_fin = new EditText(this);
//        LinearLayout.LayoutParams p_fin = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        et_fin.setLayoutParams(p_fin);
//        //et.setText("Text");
//        et_fin.setHint("A");
//        et_fin.setId(numberOfLinesFin + 1000);
//        ll.addView(et_fin);
//        numberOfLinesFin++;
//
//        // add edittext valeur
//        EditText et_valeur = new EditText(this);
//        LinearLayout.LayoutParams p_valeur = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        et_valeur.setLayoutParams(p_valeur);
//        //et.setText("Text");
//        et_valeur.setHint("Valeur");
//        et_valeur.setId(numberOfLinesValeur + 2000);
//        ll.addView(et_valeur);
//        numberOfLinesValeur++;
    }


    public void Add_Line_EtValTxIntPenal() {
//i get current value on last EditText

        if (numberOfLinesDebut==0){
            tabPlageDebutList.add(EtPlageTxInterFrom_ET.getText().toString());
            tabPlageFinList.add(EtPlageTxInterTo_ET.getText().toString());
            tabPlageValeurList.add(EtPlageTxInterValeur_ET.getText().toString());
        }
        if (numberOfLinesDebut>0){
            EditText editTextDebut = (EditText)findViewById(numberOfLinesDebut);
            tabPlageDebutList.add(editTextDebut.getText().toString());
            EditText editTextFin = (EditText)findViewById(numberOfLinesFin);
            tabPlageFinList.add(editTextFin.getText().toString());


            EditText editTextValeur = (EditText)findViewById(numberOfLinesValeur);
            tabPlageValeurList.add(editTextValeur.getText().toString());

        }
//        if (numberOfLinesFin>0){
//            EditText editTextFin = (EditText)findViewById(numberOfLinesFin);
//            tabPlageFinList.add(editTextFin.getText().toString());
//        }
//        if (numberOfLinesValeur>0){
//            EditText editTextValeur = (EditText)findViewById(numberOfLinesValeur);
//            tabPlageValeurList.add(editTextValeur.getText().toString());
//        }


        // add edittext debut
        EditText et_debut = new EditText(this);
        TextView tv_debut = new TextView(this);
        tv_debut.setTextColor(Color.GREEN);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        et_debut.setLayoutParams(p);

        et_debut.setHint("De");
        et_debut.setId(numberOfLinesDebut + 1);
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
    public void Remove_Line_EtValTxIntPenal() {
        //LinearLayout ll = (LinearLayout)findViewById(R.id.blk_EtPlageTxInter);
        if (numberOfLinesDebut>0){


            ll.removeViewAt(ll.getChildCount()-1);
            ll.removeViewAt(ll.getChildCount()-1);
            ll.removeViewAt(ll.getChildCount()-1);
            numberOfLinesDebut--;
            if (numberOfLinesDebut==0){
                ll.removeViewAt(ll.getChildCount()-1);
                remove_button.setVisibility(View.INVISIBLE);
            }
            //ll.removeViewAt(numberOfLinesDebut);
//            ll.removeViewAt(numberOfLinesFin);
//            ll.removeViewAt(numberOfLinesValeur);
//            numberOfLinesDebut--;
//            numberOfLinesFin = numberOfLinesFin-1000;
//            numberOfLinesValeur = numberOfLinesValeur-2000;
        }
        // add edittext debut
//        EditText et_debut = new EditText(this);
//        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        et_debut.setLayoutParams(p);
//        //et.setText("Text");
//        et_debut.setHint("De");
//        et_debut.setId(numberOfLinesDebut + 1);
//        ll.addView(et_debut);
//        numberOfLinesDebut++;
//
//
//        // add edittext fin
//        EditText et_fin = new EditText(this);
//        LinearLayout.LayoutParams p_fin = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        et_fin.setLayoutParams(p_fin);
//        //et.setText("Text");
//        et_fin.setHint("A");
//        et_fin.setId(numberOfLinesFin + 1000);
//        ll.addView(et_fin);
//        numberOfLinesFin++;
//
//        // add edittext valeur
//        EditText et_valeur = new EditText(this);
//        LinearLayout.LayoutParams p_valeur = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        et_valeur.setLayoutParams(p_valeur);
//        //et.setText("Text");
//        et_valeur.setHint("Valeur");
//        et_valeur.setId(numberOfLinesValeur + 2000);
//        ll.addView(et_valeur);
//        numberOfLinesValeur++;
    }


    public void getAnswer() {
        String[] options = new String[3];

        EditText text = (EditText)findViewById(R.id.editText2);
        options[0] = text.getText().toString();

        text = (EditText)findViewById(R.id.editText3);
        options[1] = text.getText().toString();

        text = (EditText)findViewById(R.id.editText4);
        options[2] = text.getText().toString();

        int number = (int)(Math.random() * 3);
        String answer = options[number];

        TextView answerBox = (TextView)findViewById(R.id.textView7);
        answerBox.setText(answer);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked1 = ((RadioButton) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.rb_EtNaturePasFixe:
                if (rbEtNaturePasFixe.isChecked()) {

                    EtNaturePas ="F";
                    /*ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    blkPlageEav.setVisibility(View.INVISIBLE);
                    layout_TauxAPreleveCpteEAV.setVisibility(View.VISIBLE);
                    */
                }

                break;
            case R.id.rb_EtNaturePasSaut:
                if (rbEtNaturePasSaut.isChecked()){
                    EtNaturePas ="S";
               /*     ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    ev_typ_fr_agios ="T";
                    blkPlageEav.setVisibility(View.INVISIBLE);
                layout_TauxAPreleveCpteEAV.setVisibility(View.VISIBLE);
                */
        }


                break;
            case R.id.rbEtTypTxInterFixe:
                if (rbEtTypTxInterFixe.isChecked()) {
                    EtTypTxInter ="F";
                    EtValTxInter_ET.setVisibility(View.VISIBLE);
                    ll.setVisibility(View.GONE);
                    ll_btn.setVisibility(View.GONE);

                    /*
                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);

                    blkPlageEav.setVisibility(View.VISIBLE);
                    layout_TauxAPreleveCpteEAV.setVisibility(View.INVISIBLE);
                    */
                }
                break;
            case R.id.rbEtTypTxInterPlage:
                if (rbEtTypTxInterPlage.isChecked()) {
                    EtTypTxInter ="P";
                    ll.setVisibility(View.VISIBLE);
                    if (numberOfLinesDebut<=0){
                        remove_button.setVisibility(View.INVISIBLE);
                    }
                    EtValTxInter_ET.setVisibility(View.GONE);
                    ll_btn.setVisibility(View.VISIBLE);
                    /*
                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    ev_typ_fr_agios ="P";
                    blkPlageEav.setVisibility(View.VISIBLE);
                    layout_TauxAPreleveCpteEAV.setVisibility(View.INVISIBLE);
                    */
                }
                break;
            case R.id.rb_EtNatureRupAnTaux:
                if (rbEtNatureRupAnTaux.isChecked()) {
                    EtNatureRupAn="T";
                    /*
                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    ev_typ_fr_agios ="P";
                    blkPlageEav.setVisibility(View.VISIBLE);
                    layout_TauxAPreleveCpteEAV.setVisibility(View.INVISIBLE);
                    */
                }
                break;
            case R.id.rb_EtNatureRupAnMontant:
                if (rbEtNatureRupAnMontant.isChecked()) {
                    EtNatureRupAn="M";
                    /*
                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    ev_typ_fr_agios ="P";
                    blkPlageEav.setVisibility(View.VISIBLE);
                    layout_TauxAPreleveCpteEAV.setVisibility(View.INVISIBLE);
                    */
                }
                break;
            case R.id.rb_EtNatureRupAnPlage:
                if (rbEtNatureRupAnPlage.isChecked()) {
                    EtNatureRupAn="P";
                    /*
                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    ev_typ_fr_agios ="P";
                    blkPlageEav.setVisibility(View.VISIBLE);
                    layout_TauxAPreleveCpteEAV.setVisibility(View.INVISIBLE);
                    */
                }
                break;
            case R.id.rbEtTypeNouveauTxInteretRuptureEATFixe:
                if (rbEtTypNewTxIntRupAntFixe.isChecked()) {

                    EtTypNewTxIntRupAnt = "F";
                    /*
                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    ev_typ_fr_agios ="P";
                    blkPlageEav.setVisibility(View.VISIBLE);
                    layout_TauxAPreleveCpteEAV.setVisibility(View.INVISIBLE);
                    */
                }
                break;
            case R.id.rbEtTypeNouveauTxInteretRuptureEATPlage:
                if (rbEtTypNewTxIntRupAntPlage.isChecked()) {
                    EtTypNewTxIntRupAnt = "P";
                    /*
                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    ev_typ_fr_agios ="P";
                    blkPlageEav.setVisibility(View.VISIBLE);
                    layout_TauxAPreleveCpteEAV.setVisibility(View.INVISIBLE);
                    */
                }
                break;
        }
       // Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }



    /**
     * Checks whether all files are filled. If so then calls AddEATAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addEAT() {


if (!STRING_EMPTY.equals(EtCode_ET.getText().toString()) &&
        !STRING_EMPTY.equals(EtLibelleET.getText().toString()) &&
        !STRING_EMPTY.equals(EtMtMinMise_ET.getText().toString()) &&
        !STRING_EMPTY.equals(EtMtMaxMise_ET.getText().toString()) &&
        !STRING_EMPTY.equals(EtDureeMin_ET.getText().toString()) &&
        !STRING_EMPTY.equals(EtDureeMax_ET.getText().toString()) &&
        !STRING_EMPTY.equals(EtNbreUnitePas_ET.getText().toString())
//        (!STRING_EMPTY.equals(EtValTxInter_ET.getText().toString()) ||
//                (!STRING_EMPTY.equals(EtPlageTxInterFrom_ET.getText().toString()) &&
//                        !STRING_EMPTY.equals(EtPlageTxInterTo_ET.getText().toString()) )  )&&
//
//                        !STRING_EMPTY.equals(EtPlageTxInterTo_ET.getText().toString())
){
            EtCode = EtCode_ET.getText().toString();
            EtLibelle = EtLibelleET.getText().toString();
            EtMtMinMise = EtMtMinMise_ET.getText().toString();
            EtMtMaxMise = EtMtMaxMise_ET.getText().toString();
            EtDureeMin = EtDureeMin_ET.getText().toString();
            EtDureeMax = EtDureeMax_ET.getText().toString();


   // EtNaturePas  = EtNaturePas.getText().toString();
    EtNbreUnitePas  =  EtNbreUnitePas_ET.getText().toString();
   // EtTypTxInter  = ;
//    EtValTxInter  = EtValTxInter_ET.getText().toString();
//    EtPlageTxInterFrom  = EtPlageTxInterFrom_ET.getText().toString();
//    EtPlageTxInterTo  = EtPlageTxInterFrom_ET.getText().toString();
    EtIsTxIntNeg   = EtIsTxIntNeg_SW.isChecked();
    EtIsPriseIntMiseOn  = EtIsPriseIntMiseOn_SW.isChecked();
    EtIsPenalRupAnt   = EtIsPenalRupAnt_SW.isChecked();
   // EtNatureRupAn   = ;
    EtValTxMtRupture  = EtValTxMtRupture_ET.getText().toString() ;
    EtPlageTxMtRuptureFrom  = EtPlageTxMtRuptureFrom_ET.getText().toString();
    EtPlageTxMtRuptureTo   = EtPlageTxMtRuptureTo_ET.getText().toString();
    EtBaseTxPenal   = EtBaseTxPenal_ET.getText().toString();;
    EtIsEparRetireFin  = EtIsEparRetireFin_SW.isChecked();
    EtIsEparTransfFin  = EtIsEparTransfFin_SW.isChecked();
    EtIsOnlyTotTransf  = EtIsOnlyTotTransf_SW.isChecked();
    EtIsEparRenouvFin  = EtIsEparRenouvFin_SW.isChecked();
    EtActionDefATerme  = EtActionDefATerme_SW.isChecked();
    EtIsMultiEATOn   = EtIsMultiEATOn_SW.isChecked();
    EtIsInterDusRupAnt  = EtIsInterDusRupAnt_SW.isChecked();
    EtIsNewTxIntRupAnt   = EtIsNewTxIntRupAnt_SW.isChecked();
   // EtTypNewTxIntRupAnt  = ;
    EtValTxIntPenal  = EtValTxIntPenal_ET.getText().toString();
    EtPlageTxIntPenalFrom  = EtPlageTxIntPenalFrom_ET.getText().toString();
    EtPlageTxIntPenalTo  = EtPlageTxIntPenalTo_ET.getText().toString();
    EtBaseTxIntPenal   = EtBaseTxIntPenal_ET.getText().toString();
    EtTxIntPenalNeg   = EtTxIntPenalNeg_SW.isChecked();

    if (numberOfLinesDebut==0){
        tabPlageDebutList.clear();
        tabPlageFinList.clear();
        tabPlageValeurList.clear();
        tabPlageDebutList.add(EtPlageTxInterFrom_ET.getText().toString());
        tabPlageFinList.add(EtPlageTxInterTo_ET.getText().toString());
        tabPlageValeurList.add(EtPlageTxInterValeur_ET.getText().toString());
    }

if (EtValTxInter=="P"){
//    for (int i=0; i<tabPlageDebutList.size();i++){
//        tabPlageDebut+=";"+tabPlageDebutList.get(i);
//    }
//    for (int i=0; i<tabPlageFinList.size();i++){
//        tabPlageFin+=";"+tabPlageFinList.get(i);
//    }
//    for (int i=0; i<tabPlageValeurList.size();i++){
//        tabPlageValeur +=";"+tabPlageValeurList.get(i);
//    }

            for (int j = 0; j < ll.getChildCount(); j++) {
                if (ll.getChildAt(j) instanceof EditText) {
                    tabPlageDebut +=";"+ ((EditText) ll.getChildAt(j)).getText().toString();

                    Toast.makeText(CreateProduitEAT.this,
                            ((EditText) ll.getChildAt(j)).getText().toString(),
                            Toast.LENGTH_LONG).show();
                }
            }
}


//
//    Toast.makeText(CreateProduitEAT.this,
//            tabPlageDebut,
//            Toast.LENGTH_LONG).show();

            //new AddEATAsyncTask().execute();
        } else {
            Toast.makeText(CreateProduitEAT.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for adding a movie
     */
    private class AddEATAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(CreateProduitEAT.this);
            pDialog.setMessage("Adding EAT. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_EAT_CODE, EtCode);
            httpParams.put(KEY_EAT_LIBELLE, EtLibelle);
            httpParams.put(KEY_EAT_MT_MIN_MISE, EtMtMinMise);
            httpParams.put(KEY_EAT_MT_MAX_MISE, EtMtMaxMise);
            httpParams.put(KEY_EAT_DUREE_MIN, EtDureeMin);
            httpParams.put(KEY_EAT_DUREE_MAX, EtDureeMax);
            httpParams.put(KEY_EAT_NATURE_PAS, EtNaturePas);
            httpParams.put(KEY_EAT_NBRE_UNITE_PAS, EtNbreUnitePas);

            httpParams.put(KEY_EAT_TYPE_TX_INTER, EtTypTxInter);
            httpParams.put(KEY_EAT_VAL_TX_INTER, EtValTxInter);
            httpParams.put(KEY_EAT_PLAGE_TX_INTER_FROM, EtPlageTxInterFrom);
            httpParams.put(KEY_EAT_PLAGE_TX_INTER_TO, EtPlageTxInterTo);
            httpParams.put(ET_IS_TX_INT_NEG, EtIsTxIntNeg.toString());
            httpParams.put(ET_IS_PRISE_INT_MISE_ON, EtIsPriseIntMiseOn.toString());
            httpParams.put(ET_IS_PENAL_RUP_ANT, EtIsPenalRupAnt.toString());
            httpParams.put(ET_NATURE_RUP_AN, EtNatureRupAn);
            httpParams.put(ET_VAL_TX_MT_RUPTURE, EtValTxMtRupture);
            httpParams.put(ET_PLAGE_TX_MT_RUPTURE_FROM, EtPlageTxMtRuptureFrom);
            httpParams.put(ET_PLAGE_TX_MT_RUPTURE_TO, EtPlageTxMtRuptureTo);
            httpParams.put(ET_BASE_TX_PENAL, EtBaseTxPenal);
            httpParams.put(ET_IS_EPAR_RETIRE_FIN, EtIsEparRetireFin.toString());
            httpParams.put(ET_IS_EPAR_TRANSF_FIN, EtIsEparTransfFin.toString());
            httpParams.put(ET_IS_ONLY_TOT_TRANSF, EtIsOnlyTotTransf.toString());
            httpParams.put(ET_IS_EPAR_RENOUV_FIN, EtIsEparRenouvFin.toString());
            httpParams.put(ET_ACTION_DEF_A_TERME, EtActionDefATerme.toString());
            httpParams.put(ET_IS_MULTI_EAT_ON, EtIsMultiEATOn.toString());
            httpParams.put(ET_IS_INTER_DUS_RUP_ANT, EtIsInterDusRupAnt.toString());
            httpParams.put(ET_IS_NEW_TX_INT_RUP_ANT, EtIsNewTxIntRupAnt.toString());
            httpParams.put(ET_TYP_NEW_TX_INT_RUP_ANT, EtTypNewTxIntRupAnt);
            httpParams.put(ET_VAL_TX_INT_PENAL, EtValTxIntPenal);
            httpParams.put(ET_PLAGE_TX_INT_PENAL_FROM, EtPlageTxIntPenalFrom);
            httpParams.put(ET_PLAGE_TX_INT_PENAL_TO, EtPlageTxIntPenalTo);
            httpParams.put(ET_BASE_TX_INT_PENAL, EtBaseTxIntPenal);
            httpParams.put(ET_TX_INT_PENAL_NEG, EtTxIntPenalNeg.toString());
            httpParams.put(ET_CAISSE_ID, String.valueOf(MyData.CAISSE_ID));

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_eat.php", "POST", httpParams);
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
                        Toast.makeText(CreateProduitEAT.this,
                                "EAT Ajouté", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CreateProduitEAT.this,
                                "Some error occurred while adding EAT",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}