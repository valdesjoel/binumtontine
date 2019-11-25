package com.example.binumtontine.activity;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.binumtontine.R;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateEAV extends AppCompatActivity implements SERVER_ADDRESS {
    private static String STRING_EMPTY = "";
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_EAV_ID = "ev_numero";
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


    private String eavId;
    private TextView headerEAVTextView;

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

    private LinearLayout blkPlageEav;
    private RadioButton rbEpTypTxInterFixe;
    private RadioButton rbEpTypTxInterTaux;
    private RadioButton rbEpTypTxInterPlage;
    private TextInputLayout layout_TauxAPreleveCpteEAV;

    private Button deleteButton;
    private Button updateButton;
    private int success;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.fragment_param_produit_eav);
        setContentView(R.layout.activity_eav);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_produit_eav);
        setSupportActionBar(toolbar);
        setToolbarTitle(); */

        Intent intent = getIntent();
        headerEAVTextView = (TextView) findViewById(R.id.header_eav);
        headerEAVTextView.setText("Mise à jour EAV");

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


        eavId = intent.getStringExtra(KEY_EAV_ID);
        new FetchEavDetailsAsyncTask().execute();
        deleteButton = (Button) findViewById(R.id.btn_delete_eav);
        deleteButton.setVisibility(View.VISIBLE);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete();
            }
        });
        updateButton = (Button) findViewById(R.id.btn_save_eav);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    updateEAV();

                } else {
                    Toast.makeText(UpdateEAV.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });


    }
    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Mise à jour d'un produit EAV");

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
     * Fetches single movie details from the server
     */
    private class FetchEavDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateEAV.this);
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
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject eav;
                if (success == 1) {
                    //Parse the JSON response
                    eav = jsonObject.getJSONObject(KEY_DATA);

                    ev_code = eav.getString(KEY_EAV_CODE);
                    ev_libelle = eav.getString(KEY_EAV_LIBELLE);
                    ev_min_cpte = eav.getString(KEY_EAV_MIN_CPTE);
                    ev_is_min_cpte_oblig = Boolean.parseBoolean(eav.getString(KEY_EAV_IS_MIN_CPTE_OBLIG));
                    ev_tx_inter_an = eav.getString(KEY_EAV_TX_INTER_AN);
                    ev_is_tx_inter_an_oblig = Boolean.parseBoolean(eav.getString(KEY_EAV_IS_TX_INTER_AN_OBLIG));
                    ev_typ_dat_val = eav.getString(KEY_EAV_TYP_DAT_VAL);
                    ev_is_multi_eav_on = Boolean.parseBoolean(eav.getString(KEY_EAV_IS_MULTI_EAV_ON));
                    ev_is_paie_ps_on = Boolean.parseBoolean(eav.getString(KEY_EAV_IS_PAIE_PS_ON));
                    ev_is_agios_on = Boolean.parseBoolean(eav.getString(KEY_EAV_IS_AGIOS_ON));
                    ev_typ_fr_agios =eav.getString(KEY_EAV_TYP_FR_AGIOS);
                    ev_mt_tx_agios_prelev = eav.getString(KEY_EAV_MT_TX_AGIOS_PRELEV);
                    ev_plage_agios_from = eav.getString(KEY_EAV_PLAGE_AGIOS_FROM);
                    ev_plage_agios_to = eav.getString(KEY_EAV_PLAGE_AGIOS_TO);
                    ev_is_cheque_on = Boolean.parseBoolean(eav.getString(KEY_EAV_IS_CHEQUE_ON));
                    ev_frais_clot_cpt = eav.getString(KEY_EAV_FRAIS_CLOT_CPT);

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


                    ev_codeEditText.setText(ev_code);
                    ev_libelleEditText.setText(ev_libelle);
                    ev_min_cpteEditText.setText(ev_min_cpte);
                    ev_is_min_cpte_obligSwitch.setChecked(ev_is_min_cpte_oblig);
                    ev_tx_inter_anEditText.setText(ev_tx_inter_an);
                    ev_is_tx_inter_an_obligSwitch.setChecked(ev_is_tx_inter_an_oblig);
                    ev_typ_dat_valEditText.setText(ev_typ_dat_val);
                    ev_is_multi_eav_onSwitch.setChecked(ev_is_multi_eav_on);
                    ev_is_paie_ps_onSwitch.setChecked(ev_is_paie_ps_on);
                    ev_is_agios_onSwitch.setChecked(ev_is_agios_on);
                    if (ev_typ_fr_agios.equals("F")){
                        rbEpTypTxInterFixe.setChecked(true);
                    }else if (ev_typ_fr_agios.equals("T")){
                        rbEpTypTxInterTaux.setChecked(true);
                    }else rbEpTypTxInterPlage.setChecked(true);
                    //ev_typ_fr_agiosEditText.setText(ev_typ_fr_agios);
                    ev_mt_tx_agios_prelevEditText.setText(ev_mt_tx_agios_prelev);
                    ev_plage_agios_fromEditText.setText(ev_plage_agios_from);
                    ev_plage_agios_toEditText.setText(ev_plage_agios_to);
                    ev_is_cheque_onSwitch.setChecked(ev_is_cheque_on);
                    ev_frais_clot_cptEditText.setText(ev_frais_clot_cpt);
                }
            });
        }


    }

    /**
     * Displays an alert dialogue to confirm the deletion
     */
    private void confirmDelete() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                UpdateEAV.this);
        alertDialogBuilder.setMessage("Are you sure, you want to delete this EAV?");
        alertDialogBuilder.setPositiveButton("Delete",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                            //If the user confirms deletion, execute DeleteMovieAsyncTask
                            new UpdateEAV.DeleteMovieAsyncTask().execute();
                        } else {
                            Toast.makeText(UpdateEAV.this,
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
            pDialog = new ProgressDialog(UpdateEAV.this);
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
                        Toast.makeText(UpdateEAV.this,
                                "EAV Deleted", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie deletion
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(UpdateEAV.this,
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


      /*  if (!STRING_EMPTY.equals(movieNameEditText.getText().toString()) &&
                !STRING_EMPTY.equals(genreEditText.getText().toString()) &&
                !STRING_EMPTY.equals(yearEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ratingEditText.getText().toString())) {*/
if(true){
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

            new UpdateEavAsyncTask().execute();
        } else {
            Toast.makeText(UpdateEAV.this,
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
            pDialog = new ProgressDialog(UpdateEAV.this);
            pDialog.setMessage("Updating EAV. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_EAV_ID, eavId);
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
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
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
                        //Display success message
                        Toast.makeText(UpdateEAV.this,
                                "EAV Updated", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(UpdateEAV.this,
                                "Some error occurred while updating EAV",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}
