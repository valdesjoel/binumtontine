package com.example.binumtontine.activity.parametreGenerauxOF;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.Category;
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

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class UpdateEtapeDemandeCreditOF extends AppCompatActivity implements  SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_ED_CODE = "EdCode";
    private static final String KEY_ED_IS_ON = "EdIsOn";
    private static final String KEY_ED_LIBELLE = "EdLibelle";
    private static final String KEY_ED_NUM_ORDRE = "EdNumOrdr";
    private static final String KEY_ED_TYP_ETAPE = "EdTypEtape";
    private static final String KEY_ED_NUMERO = "EdNumero";

    private EditText EdCodeEditText;
    private EditText EdLibelleEditText;
    private EditText EdNumOrdreEditText;
    private Spinner spinnerTypeEtape;

    private RadioButton rbEtapeOn;
    private RadioButton rbEtapeOff;


    private String EdCode;
    private String EdLibelle;
    private String EdNumOrdre;
    private String EdIson;
    private String EdTypEtape;

    private static String STRING_EMPTY = "";

    private TextView headerEtapeDemandeCreditTextView;



    private TextView cxTitle;
    private String cxName;
    private String etapeDemandeCreditID;

    private String gxCaisse;
    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Category> defaultEavList;
    List<Integer> defaultEavListID = new ArrayList<Integer>();
    private int defaultEavId;
    private Spinner spinnerDefaultEAV;
    private TextView tvCaisse;
    private TextInputLayout textInputLayoutCaisse;
    private LinearLayout LL_default_eav;
    /*end manage*/


    private CircularProgressButton updateButton;
    private CircularProgressButton annulerButton;
    private CircularProgressButton deleteButton;
    private int success;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etape_demande_credit_of);

        Intent intent = getIntent();
        headerEtapeDemandeCreditTextView = (TextView) findViewById(R.id.tv_header_etape_demande_credit);

        headerEtapeDemandeCreditTextView.setText("Mise à jour d'une étape de demande de crédit");

        EdCodeEditText = (EditText) findViewById(R.id.input_txt_Code_Etape_of);
        EdLibelleEditText = (EditText) findViewById(R.id.input_txt_Libelle_etape_demande_credit_of);
        EdNumOrdreEditText = (EditText) findViewById(R.id.input_txt_Num_ordre_etape_demande_credit_of);
//
        rbEtapeOn = (RadioButton) findViewById(R.id.rb_Ed_On_of);
        rbEtapeOff = (RadioButton) findViewById(R.id.rb_Ed_Off_of);
        onRadioButtonClicked(rbEtapeOn);
        alreadyUpperCase(EdLibelleEditText);

        etapeDemandeCreditID = intent.getStringExtra(KEY_ED_NUMERO);

        spinnerTypeEtape = (Spinner) findViewById(R.id.spn_EdTypEtape_of);
        spinnerTypeEtape.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                //   checkOffersSum(); // same method for first 4 spinners. for last 4 spinners is checkScoresSum()
                // FcFonctionFrais = spinnerTypeEtape.getSelectedItem().toString();//pour recuperer l'ID de la pièce selectionnée
                // your stuff here
                EdTypEtape = spinnerTypeEtape.getSelectedItem().toString();

                if (spinnerTypeEtape.getSelectedItem().toString().equals("Demande et mise en place du crédit")){
//                    textInputLayoutFcNbrePartMin.setVisibility(View.VISIBLE);
                    EdTypEtape = "DCR";

                }else{
//                    textInputLayoutFcNbrePartMin.setVisibility(View.GONE);
                    if (spinnerTypeEtape.getSelectedItem().toString().equals("Comité de crédit")){
                        EdTypEtape = "CCR";
                    }else if (spinnerTypeEtape.getSelectedItem().toString().equals("Déblocage du crédit")){
                        EdTypEtape = "DBC";
                    }else if (spinnerTypeEtape.getSelectedItem().toString().equals("Décaissement du crédit")){
                        EdTypEtape = "DEC";
                    }else if (spinnerTypeEtape.getSelectedItem().toString().equals("Remboursement ou paiement échéance du crédit")){
                        EdTypEtape = "REC";
                    }else if (spinnerTypeEtape.getSelectedItem().toString().equals("Remboursement des échéances du crédit")){
                        EdTypEtape = "REC";
                    }else if (spinnerTypeEtape.getSelectedItem().toString().equals("Clôture du crédit")){
                        EdTypEtape = "CLC";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub.

            }

        });
//
//

        new FetchGuichetDetailsAsyncTask().execute();
        deleteButton = (CircularProgressButton) findViewById(R.id.btn_delete_etapes_demande_credit_of);
        deleteButton.setVisibility(View.VISIBLE);
        annulerButton = (CircularProgressButton) findViewById(R.id.btn_clean);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(UpdateEtapeDemandeCreditOF.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete();
            }
        });
        updateButton = (CircularProgressButton) findViewById(R.id.btn_save_etapes_demande_credit_of);
        updateButton.setText("MODIFIER");
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    updateEtapeDemandeCredit();

                } else {
                    Toast.makeText(UpdateEtapeDemandeCreditOF.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });



    }

    public void onRadioButtonClicked(View view) {
        boolean checked1 = ((RadioButton) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.rb_Ed_On_of:
                if (rbEtapeOn.isChecked()) {
                    str = checked1?"Etape activée":"";
                    EdIson="TRUE";
                    //ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    //    FpTypeAdh="P";

                }

                break;
            case R.id.rb_Ed_Off_of:
                if (rbEtapeOff.isChecked()){
                    str = checked1?"Etape désactivée":"";
                    EdIson="FALSE";
                    //ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    //  FpTypeAdh ="M";

                }


                break;

        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    private void alreadyUpperCase(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable et) {
                String s = et.toString();
                if (!s.equals(s.toUpperCase())) {
                    s = s.toUpperCase();
                    editText.setText(s);
                    editText.setSelection(editText.length()); //fix reverse texting
                }
            }
        });
    }

    /**
     * Fetches single movie details from the server
     */
    private class FetchGuichetDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateEtapeDemandeCreditOF.this);
            pDialog.setMessage("Chargement des détails sur l'étape de crédit. Veuillez patienter svp...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_ED_NUMERO, etapeDemandeCreditID);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_etape_demande_credit_details.php", "GET", httpParams);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject guichet;
                if (success == 1) {
                    //Parse the JSON response
                    guichet = jsonObject.getJSONObject(KEY_DATA);

                    EdCode = guichet.getString(KEY_ED_CODE);
                    EdLibelle = guichet.getString(KEY_ED_LIBELLE);
                    EdNumOrdre = guichet.getString(KEY_ED_NUM_ORDRE);
                    EdTypEtape = guichet.getString(KEY_ED_TYP_ETAPE);
                    EdIson = guichet.getString(KEY_ED_IS_ON);


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

                    EdCodeEditText.setText(EdCode);
                    EdLibelleEditText.setText(EdLibelle);
                    EdNumOrdreEditText.setText(EdNumOrdre);
                    rbEtapeOn.setChecked(Boolean.parseBoolean(EdIson));
                    rbEtapeOff.setChecked(!Boolean.parseBoolean(EdIson));
                    onRadioButtonClicked(rbEtapeOn);
                    onRadioButtonClicked(rbEtapeOff);
                    if (EdTypEtape!=null){
                        // Creating adapter for spinner piece
                        String[] mTestArray;
                        mTestArray = getResources().getStringArray(R.array.EdTypEtape);
                        ArrayAdapter<String> spinnerFraisAdapter = new ArrayAdapter<String>(UpdateEtapeDemandeCreditOF.this,
                                android.R.layout.simple_spinner_item, mTestArray);

                        // Drop down layout style - list view with radio button
                        spinnerFraisAdapter
                                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        if (EdTypEtape.equals("DCR")){
                            EdTypEtape = "Demande et mise en place du crédit";

                        }else{
                            if (EdTypEtape.equals("CCR")){
                                EdTypEtape = "Comité de crédit";
                            }else if (EdTypEtape.equals("DBC")){
                                EdTypEtape = "Déblocage du crédit";
                            }else if (EdTypEtape.equals("DEC")){
                                EdTypEtape = "Décaissement du crédit";
                            }else if (EdTypEtape.equals("REC")){
                                EdTypEtape = "Remboursement des échéances du crédit";
                            }else if (EdTypEtape.equals("CLC")){
                                EdTypEtape = "Clôture du crédit";
                            }
                        }

                        spinnerTypeEtape.setSelection(spinnerFraisAdapter.getPosition(EdTypEtape));
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
                UpdateEtapeDemandeCreditOF.this);
        alertDialogBuilder.setMessage("Are you sure, you want to delete this Etape demande crédit?");
        alertDialogBuilder.setPositiveButton("Supprimer",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                            //If the user confirms deletion, execute DeleteObjetCreditAsyncTask
                            new DeleteObjetCreditAsyncTask().execute();
                        } else {
                            Toast.makeText(UpdateEtapeDemandeCreditOF.this,
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
     * AsyncTask to delete a guichet
     */
    private class DeleteObjetCreditAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateEtapeDemandeCreditOF.this);
            pDialog.setMessage("Suppression de l'étape de demande de Crédit. Veuillez patienter...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            deleteButton.startAnimation() ;// to start animation on button delete
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Set guichet_id parameter in request
            httpParams.put(KEY_ED_NUMERO, etapeDemandeCreditID);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "delete_etape_demande_credit.php", "POST", httpParams);
            try {
                success = jsonObject.getInt(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            deleteButton.startAnimation() ;// to stop animation on button delete
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(UpdateEtapeDemandeCreditOF.this,
                                "Etape Demande Crédit Supprimé", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie deletion
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(UpdateEtapeDemandeCreditOF.this,
                                "Some error occurred while deleting Etape Demande Crédit",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    /**
     * Checks whether all files are filled. If so then calls UpdateObjetCreditAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void updateEtapeDemandeCredit() {

        if (!STRING_EMPTY.equals(EdCodeEditText.getText().toString()) &&
                !STRING_EMPTY.equals(EdLibelleEditText.getText().toString()) &&
                !STRING_EMPTY.equals(EdNumOrdreEditText.getText().toString())){

                EdCode = EdCodeEditText.getText().toString();
                EdLibelle = EdLibelleEditText.getText().toString();
                EdNumOrdre = EdNumOrdreEditText.getText().toString();
              new UpdateObjetCreditAsyncTask().execute();
        } else {
            Toast.makeText(UpdateEtapeDemandeCreditOF.this,
                    "One or more fields left empty!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for updating a movie details
     */

    private class UpdateObjetCreditAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateEtapeDemandeCreditOF.this);
            pDialog.setMessage("Updating Etape Demande Crédit. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            updateButton.startAnimation() ;// to start animation on button update
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
           // httpParams.put(KEY_GUICHET_ID, etapeDemandeCreditID);
            httpParams.put(KEY_ED_NUMERO, String.valueOf(etapeDemandeCreditID));

            httpParams.put(KEY_ED_CODE, EdCode);
            httpParams.put(KEY_ED_LIBELLE, EdLibelle);
            httpParams.put(KEY_ED_NUM_ORDRE, EdNumOrdre);
            httpParams.put(KEY_ED_TYP_ETAPE, EdTypEtape);
            httpParams.put(KEY_ED_IS_ON, EdIson);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "update_etape_demande_credit.php", "POST", httpParams);
            try {
                success = jsonObject.getInt(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            updateButton.startAnimation() ;// to stop animation on button update
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(UpdateEtapeDemandeCreditOF.this,
                                "Etape Demande Crédit Mis à Jour !", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(UpdateEtapeDemandeCreditOF.this,
                                "Some error occurred while updating Etape Demande Crédit",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }



}
