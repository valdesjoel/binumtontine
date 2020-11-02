package com.example.binumtontine.activity.parametreGenerauxCx.operationExterne;

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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.example.binumtontine.modele.OperationExterne;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class AffectOperationExterneCx extends AppCompatActivity implements  SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    /*private static final String KEY_ED_CODE = "EdCode";
    private static final String KEY_ED_IS_ON = "EdIsOn";
    private static final String KEY_ED_LIBELLE = "EdLibelle";
    private static final String KEY_ED_NUM_ORDRE = "EdNumOrdr";
    private static final String KEY_ED_TYP_ETAPE = "EdTypEtape";
    private static final String KEY_ED_NUMERO = "EdNumero";
    private static final String KEY_ED_CAISSE = "EdCaisse";
*/
    private EditText ET_OeCode;
    private EditText ET_OeLibelle;
    private Spinner spinnerOeType;

    private RadioButton rbOeIsOnOn;
    private RadioButton rbOeIsOnOff;

    private String OeIsOn;
    private String OeType;

    private static String STRING_EMPTY = "";

    private TextView headerOperationExterneTextView;

    OperationExterne monOE = new OperationExterne();
//    private String etapeDemandeCreditID;

    private CircularProgressButton updateButton;
    private CircularProgressButton annulerButton;
    private CircularProgressButton deleteButton;
    private int success;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_externe_of);

        Intent intent = getIntent();
        headerOperationExterneTextView = (TextView) findViewById(R.id.tv_header_operation_externe);

        headerOperationExterneTextView.setText("Affectation d'une opération externe");

        ET_OeCode = (EditText) findViewById(R.id.input_txt_OeCode);
        ET_OeLibelle = (EditText) findViewById(R.id.input_txt_Libelle_operation_externe);
        ET_OeCode.setEnabled(false);
//
        rbOeIsOnOn = (RadioButton) findViewById(R.id.rb_OeIsOn_On);
        rbOeIsOnOff = (RadioButton) findViewById(R.id.rb_OeIsOn_Off);
        onRadioButtonClicked(rbOeIsOnOn);
//        alreadyUpperCase(ET_OeLibelle);

        monOE.setOeNumero(Integer.parseInt(intent.getStringExtra(OperationExterne.KEY_OeNumero)));

        spinnerOeType = (Spinner) findViewById(R.id.spn_OeType);
        spinnerOeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                OeType = spinnerOeType.getSelectedItem().toString();

                if (spinnerOeType.getSelectedItem().toString().equals("Charge")){
                    OeType = "C";
                }else if (spinnerOeType.getSelectedItem().toString().equals("Produit")){
                    OeType = "P";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub.

            }

        });
//

        new FetchOperationExterneDetailsAsyncTask().execute();
        deleteButton = (CircularProgressButton) findViewById(R.id.btn_delete);
        deleteButton.setVisibility(View.GONE);
        annulerButton = (CircularProgressButton) findViewById(R.id.btn_clean);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(AffectOperationExterneCx.this,
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
        updateButton = (CircularProgressButton) findViewById(R.id.btn_save);
        updateButton.setText("AFFECTER");
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    operationExterne();

                } else {
                    Toast.makeText(AffectOperationExterneCx.this,
                            "Impossible de se connecter à Internet",
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
            case R.id.rb_OeIsOn_On:
                if (rbOeIsOnOn.isChecked()) {
                    str = checked1?"Opération activée":"";
                    OeIsOn ="Y";
                }
                break;
            case R.id.rb_OeIsOn_Off:
                if (rbOeIsOnOff.isChecked()){
                    str = checked1?"Opération désactivée":"";
                    OeIsOn ="N";
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
    private class FetchOperationExterneDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(AffectOperationExterneCx.this);
            pDialog.setMessage("Chargement des détails sur l'opération. Veuillez patienter svp...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                HttpJsonParser httpJsonParser = new HttpJsonParser();
                Map<String, String> httpParams = new HashMap<>();
                httpParams.put(OperationExterne.KEY_OeNumero, String.valueOf(monOE.getOeNumero()));
                JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                        BASE_URL + "get_operation_externe_details.php", "GET", httpParams);
/*
                    int success = jsonObject.getInt(KEY_SUCCESS);
                    JSONObject guichet;
                    if (success == 1) {
                        //Parse the JSON response
                        guichet = jsonObject.getJSONObject(KEY_DATA);*/
                monOE.setOeCode(MyData.normalizeSymbolsAndAccents(jsonObject.getString(OperationExterne.KEY_OeCode)));
                monOE.setOeLibelle(MyData.normalizeSymbolsAndAccents(jsonObject.getString(OperationExterne.KEY_OeLibelle)));
                monOE.setOeType(MyData.normalizeSymbolsAndAccents(jsonObject.getString(OperationExterne.KEY_OeType)));
                monOE.setOeIsOn(MyData.normalizeSymbolsAndAccents(jsonObject.getString(OperationExterne.KEY_OeIsOn)));

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

                    ET_OeCode.setText(monOE.getOeCode());
                    ET_OeLibelle.setText(monOE.getOeLibelle());
                    if (monOE.getOeIsOn().equals("Y")){
                        rbOeIsOnOn.setChecked(true);
                    }else{
                        rbOeIsOnOff.setChecked(true);
                    }
                    onRadioButtonClicked(rbOeIsOnOn);
                    onRadioButtonClicked(rbOeIsOnOff);
                    if (monOE.getOeType() !=null){
                        // Creating adapter for spinner piece
                        String[] mTestArray;
                        mTestArray = getResources().getStringArray(R.array.OeType);
                        ArrayAdapter<String> spinnerFraisAdapter = new ArrayAdapter<String>(AffectOperationExterneCx.this,
                                android.R.layout.simple_spinner_item, mTestArray);

                        // Drop down layout style - list view with radio button
                        spinnerFraisAdapter
                                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        if (monOE.getOeType().equals("C")){
                            OeType = "Charge";

                        }else if (monOE.getOeType().equals("P")){
                            OeType = "Produit";
                        }

                        spinnerOeType.setSelection(spinnerFraisAdapter.getPosition(OeType));
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
                AffectOperationExterneCx.this);
        alertDialogBuilder.setMessage("Etes-vous sûr de vouloir supprimer cette Opération Externe ?");
        alertDialogBuilder.setPositiveButton("Supprimer",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                            //If the user confirms deletion, execute DeleteObjetCreditAsyncTask
                            new AffectOperationExterneCx.DeleteObjetCreditAsyncTask().execute();
                        } else {
                            Toast.makeText(AffectOperationExterneCx.this,
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
            pDialog = new ProgressDialog(AffectOperationExterneCx.this);
            pDialog.setMessage("Suppression de l'opération externe. Veuillez patienter...");
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
            httpParams.put(OperationExterne.KEY_OeNumero, String.valueOf(monOE.getOeNumero()));
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "delete_operation_externe.php", "POST", httpParams);
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
                        Toast.makeText(AffectOperationExterneCx.this,
                                "Opération Externe Supprimée", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie deletion
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(AffectOperationExterneCx.this,
                                "Une erreur s'est produite lors de la suppression de l'opération Externe",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    /**
     * Checks whether all files are filled. If so then calls UpdateOperationExterneAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void operationExterne() {
        if (!STRING_EMPTY.equals(ET_OeCode.getText().toString().trim()) &&
                !STRING_EMPTY.equals(ET_OeLibelle.getText().toString().trim())
        ){
            monOE.setOeCode(MyData.normalizeSymbolsAndAccents(ET_OeCode.getText().toString()));
            monOE.setOeLibelle(MyData.normalizeSymbolsAndAccents(ET_OeLibelle.getText().toString()));
            monOE.setOeType(OeType);
            monOE.setOeIsOn(OeIsOn);
              new UpdateOperationExterneAsyncTask().execute();
        } else {
            Toast.makeText(AffectOperationExterneCx.this,
                    "One or more fields left empty!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for updating a movie details
     */

    private class UpdateOperationExterneAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(AffectOperationExterneCx.this);
//            pDialog.setMessage("Updating Etape Demande Crédit. Please wait...");
            pDialog.setMessage("Veuillez patienter...");
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

            httpParams.put(OperationExterne.KEY_OeCaisse, String.valueOf(MyData.CAISSE_ID));

            httpParams.put(OperationExterne.KEY_OeNumero, String.valueOf(monOE.getOeNumero()));
            httpParams.put(OperationExterne.KEY_OeCode, monOE.getOeCode());
            httpParams.put(OperationExterne.KEY_OeLibelle, monOE.getOeLibelle());
            httpParams.put(OperationExterne.KEY_OeType, monOE.getOeType());
            httpParams.put(OperationExterne.KEY_OeIsOn, monOE.getOeIsOn());
            httpParams.put(OperationExterne.KEY_OeUserCree, String.valueOf(MyData.USER_ID));
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "affect_operation_externe_on_cx.php", "POST", httpParams);
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
                        Toast.makeText(AffectOperationExterneCx.this,
                                "Opération Externe Affectée !", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(AffectOperationExterneCx.this,
                                "Une erreur s'est produite lors de l'opération",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }


}
