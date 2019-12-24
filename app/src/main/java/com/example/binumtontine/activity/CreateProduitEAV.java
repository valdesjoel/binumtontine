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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
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

import java.util.HashMap;
import java.util.Map;

public class CreateProduitEAV extends AppCompatActivity implements SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";

    private static final String KEY_EAV_CODE = "ev_code";
    private static final String KEY_EAV_LIBELLE = "ev_libelle";
    private static final String KEY_EAV_MIN_CPTE = "ev_min_cpte";
    private static final String KEY_EAV_IS_MIN_CPTE_OBLIG = "ev_is_min_cpte_oblig";
    private static final String KEY_EAV_TX_INTER_AN = "ev_tx_inter_an";
    private static final String KEY_EAV_IS_TX_INTER_AN_OBLIG = "ev_is_tx_inter_an_oblig";
    private static final String KEY_EAV_TYP_DAT_VAL = "ev_typ_dat_val";
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



    private static String STRING_EMPTY = "";

    private EditText ev_codeEditText;
    private EditText ev_libelleEditText;
    private EditText ev_min_cpteEditText;
    private Switch ev_is_min_cpte_obligSwitch;
    private EditText ev_tx_inter_anEditText;
    private Switch ev_is_tx_inter_an_obligSwitch;
    private EditText ev_typ_dat_valEditText;
    private Switch ev_is_multi_eav_onSwitch;
    private Switch ev_is_paie_ps_onSwitch;
    private Switch ev_is_agios_onSwitch;
   // private EditText ev_typ_fr_agiosEditText;RadioButton
    private RadioButton ev_typ_fr_agiosEditText;
    private EditText ev_mt_tx_agios_prelevEditText;
    private EditText ev_plage_agios_fromEditText;
    private EditText ev_plage_agios_toEditText;
    private Switch ev_is_cheque_onSwitch;
    private EditText ev_frais_clot_cptEditText;

    private String ev_code;
    private String ev_libelle;
    private String ev_min_cpte;
    private Boolean ev_is_min_cpte_oblig;
    private String ev_tx_inter_an;
    private Boolean ev_is_tx_inter_an_oblig;
    private String ev_typ_dat_val;
    private Boolean ev_is_multi_eav_on;
    private Boolean ev_is_paie_ps_on;
    private Boolean ev_is_agios_on;
    private String ev_typ_fr_agios;
    private String ev_mt_tx_agios_prelev;
    private String ev_plage_agios_from;
    private String ev_plage_agios_to;
    private Boolean ev_is_cheque_on;
    private String ev_frais_clot_cpt;
    private int ev_caisse_id = MyData.CAISSE_ID;

    private LinearLayout blkPlageEav;
    private RadioButton rbEpTypTxInterFixe;
    private RadioButton rbEpTypTxInterTaux;
    private RadioButton rbEpTypTxInterPlage;
    private TextInputLayout layout_TauxAPreleveCpteEAV;

    private Button addButton;
    private Button cancelButton;
    private Button deleteButton;
    private int success;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_add_movie);
      //  setContentView(R.layout.fragment_param_produit_eav);
        setContentView(R.layout.activity_eav);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_produit_eav);
        setSupportActionBar(toolbar);
        setToolbarTitle(); */



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

        rbEpTypTxInterFixe = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
        rbEpTypTxInterTaux = (RadioButton) findViewById(R.id.rbEpTypTxInterTaux);
        rbEpTypTxInterPlage = (RadioButton) findViewById(R.id.rbEpTypTxInterPlage);
        blkPlageEav = (LinearLayout) findViewById(R.id.blk_plage_eav);
        layout_TauxAPreleveCpteEAV = (TextInputLayout) findViewById(R.id.input_layout_TauxAPreleveCpteEAV);



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

    }
    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Ajout d'un produit EAV");

    }

    public void onRadioButtonClicked(View view) {
        boolean checked1 = ((RadioButton) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.rbEpTypTxInterFixe:
                if (rbEpTypTxInterFixe.isChecked()) {
                    str = checked1?"Type Fixe Selected":"Type Fixe Deselected";
                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    ev_typ_fr_agios ="F";
                    blkPlageEav.setVisibility(View.GONE);
                    layout_TauxAPreleveCpteEAV.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.rbEpTypTxInterTaux:
                if (rbEpTypTxInterTaux.isChecked()){
                    str = checked1?"Type Taux Selected":"Type Taux Deselected";
                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    ev_typ_fr_agios ="T";
                    blkPlageEav.setVisibility(View.GONE);
                layout_TauxAPreleveCpteEAV.setVisibility(View.VISIBLE);
        }


                break;
            case R.id.rbEpTypTxInterPlage:
                if (rbEpTypTxInterPlage.isChecked()) {
                    str = checked1?"Type Plage Selected":"Type Plage Deselected";
                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    ev_typ_fr_agios ="P";
                    blkPlageEav.setVisibility(View.VISIBLE);
                    layout_TauxAPreleveCpteEAV.setVisibility(View.GONE);
                }
                break;
        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
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
            ev_code = ev_codeEditText.getText().toString();
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
            ev_frais_clot_cpt = ev_frais_clot_cptEditText.getText().toString();
            new AddEAVAsyncTask().execute();
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
            httpParams.put(KEY_EAV_IS_MIN_CPTE_OBLIG, ev_is_min_cpte_oblig.toString());

            httpParams.put(KEY_EAV_TX_INTER_AN, ev_tx_inter_an);
            httpParams.put(KEY_EAV_IS_TX_INTER_AN_OBLIG, ev_is_tx_inter_an_oblig.toString());
            httpParams.put(KEY_EAV_TYP_DAT_VAL, ev_typ_dat_val);
            httpParams.put(KEY_EAV_IS_MULTI_EAV_ON, ev_is_multi_eav_on.toString());
            httpParams.put(KEY_EAV_IS_PAIE_PS_ON, ev_is_paie_ps_on.toString());
            httpParams.put(KEY_EAV_IS_AGIOS_ON, ev_is_agios_on.toString());
            httpParams.put(KEY_EAV_TYP_FR_AGIOS, ev_typ_fr_agios);
            httpParams.put(KEY_EAV_MT_TX_AGIOS_PRELEV, ev_mt_tx_agios_prelev);
            httpParams.put(KEY_EAV_PLAGE_AGIOS_FROM, ev_plage_agios_from);
            httpParams.put(KEY_EAV_PLAGE_AGIOS_TO, ev_plage_agios_to);
            httpParams.put(KEY_EAV_IS_CHEQUE_ON, ev_is_cheque_on.toString());
            httpParams.put(KEY_EAV_FRAIS_CLOT_CPT, ev_frais_clot_cpt);
            httpParams.put(KEY_EAV_CX_NUMERO, String.valueOf(ev_caisse_id));
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_eav.php", "POST", httpParams);
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