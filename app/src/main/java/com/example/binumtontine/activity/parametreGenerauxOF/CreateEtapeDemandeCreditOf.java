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
package com.example.binumtontine.activity.parametreGenerauxOF;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.Category;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateEtapeDemandeCreditOf extends AppCompatActivity implements SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";

    private static final String KEY_ED_CODE = "EdCode";
    private static final String KEY_ED_IS_ON = "EdIsOn";
    private static final String KEY_ED_LIBELLE = "EdLibelle";
    private static final String KEY_ED_NUM_ORDRE = "EdNumOrdr";
    private static final String KEY_ED_TYP_ETAPE = "EdTypEtape";

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



    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Category> baseFraisList;
    List<Integer> baseFraisListID = new ArrayList<Integer>();
    private int baseFraisID;
    private Spinner spinnerBaseFrais;
    private LinearLayout textInputLayoutCaisse;
    private LinearLayout textInputLayoutFpNbrePartMin;
    /*end manage*/


    private Button addButton;
    private Button annulerButton;
    private Button deleteButton;
    private int success;
    private ProgressDialog pDialog;
    private ProgressDialog pDialogFetchBaseFraisList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etape_demande_credit_of);

        EdCodeEditText = (EditText) findViewById(R.id.input_txt_Code_Etape_of);
        EdLibelleEditText = (EditText) findViewById(R.id.input_txt_Libelle_etape_demande_credit_of);
        EdNumOrdreEditText = (EditText) findViewById(R.id.input_txt_Num_ordre_etape_demande_credit_of);
//
        rbEtapeOn = (RadioButton) findViewById(R.id.rb_Ed_On_of);
        rbEtapeOff = (RadioButton) findViewById(R.id.rb_Ed_Off_of);
        onRadioButtonClicked(rbEtapeOn);


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
//
//        baseFraisList = new ArrayList<Category>();
//        // spinner item select listener
//
//        new GetBaseFraisList().execute();

        deleteButton = (Button) findViewById(R.id.btn_delete_etapes_demande_credit_of);
        deleteButton.setVisibility(View.GONE);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        addButton = (Button) findViewById(R.id.btn_save_etapes_demande_credit_of);
        //cirLoginButton
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(CreateEtapeDemandeCreditOf.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addObjetCredit();
                } else {
                    Toast.makeText(CreateEtapeDemandeCreditOf.this,
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

            case R.id.rb_Ed_On_of:
                if (rbEtapeOn.isChecked()) {
                    str = checked1?"Etape activée":"";
                    EdIson="Y";
                    //ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    //    FpTypeAdh="P";

                }

                break;
            case R.id.rb_Ed_Off_of:
                if (rbEtapeOff.isChecked()){
                    str = checked1?"Etape désactivée":"";
                    EdIson="N";
                    //ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    //  FpTypeAdh ="M";

                }


                break;

        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }
    /**
     * Adding spinner data
     * */
    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();

        //tvCaisse.setText("");

        for (int i = 0; i < baseFraisList.size(); i++) {
            lables.add(baseFraisList.get(i).getName());//recupère les noms
            baseFraisListID.add(baseFraisList.get(i).getId()); //recupère les Id
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(CreateEtapeDemandeCreditOf.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerBaseFrais.setAdapter(spinnerAdapter);
    }






    /**
     * Checks whether all files are filled. If so then calls AddObjetCreditAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addObjetCredit() {

if (!STRING_EMPTY.equals(EdCodeEditText.getText().toString()) &&
        !STRING_EMPTY.equals(EdLibelleEditText.getText().toString()) &&
        !STRING_EMPTY.equals(EdNumOrdreEditText.getText().toString())){

            EdCode = EdCodeEditText.getText().toString();
            EdLibelle = EdLibelleEditText.getText().toString();
            EdNumOrdre = EdNumOrdreEditText.getText().toString();


            new AddObjetCreditAsyncTask().execute();
        } else {
            Toast.makeText(CreateEtapeDemandeCreditOf.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for adding a movie
     */
    private class AddObjetCreditAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(CreateEtapeDemandeCreditOf.this);
            pDialog.setMessage("Ajout d'une étape de demande de crédit. SVP, patientez...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_ED_CODE, EdCode);
            httpParams.put(KEY_ED_LIBELLE, EdLibelle);
            httpParams.put(KEY_ED_NUM_ORDRE, EdNumOrdre);
            httpParams.put(KEY_ED_TYP_ETAPE, EdTypEtape);
            httpParams.put(KEY_ED_IS_ON, EdIson);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_etape_demande_credit_of.php", "POST", httpParams);
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
                        Toast.makeText(CreateEtapeDemandeCreditOf.this,
                                "Etape demande de crédit Ajoutée", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CreateEtapeDemandeCreditOf.this,
                                "Une erreur s'est produite lors de l'ajout de l'étape",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}