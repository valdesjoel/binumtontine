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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.Category;
import com.example.binumtontine.activity.ServiceHandler;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateObjetCreditOf extends AppCompatActivity implements SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";

    private static final String KEY_OC_CODE = "OcCode";
    private static final String KEY_OC_LIBELLE = "OcLibelle";
    private static final String KEY_OC_DETAILS = "OcDetails";

    private EditText OcCodeEditText;
    private EditText OcLibelleEditText;
    private EditText OcDetailsEditText;

    private String OcCode;
    private String OcLibelle;
    private String OcDetails;

    private static String STRING_EMPTY = "";



    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Category> baseFraisList;
    List<Integer> baseFraisListID = new ArrayList<Integer>();
    private int baseFraisID;
    private Spinner spinnerBaseFrais;
    private Spinner spinnerFonctionFrais;
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
        setContentView(R.layout.activity_objet_credit_of);

        OcCodeEditText = (EditText) findViewById(R.id.input_txt_Code_Objet_of);
        OcLibelleEditText = (EditText) findViewById(R.id.input_txt_Libelle_Objet_credit_of);
        OcDetailsEditText = (EditText) findViewById(R.id.input_txt_Details_Objet_credit_of);
//
//        textInputLayoutCaisse = (LinearLayout) findViewById(R.id.ll_base_frais_of);
//        textInputLayoutFpNbrePartMin = (LinearLayout) findViewById(R.id.ll_NbrePartMin_of);
//
//
//
//
//        baseFraisList = new ArrayList<Category>();
//        // spinner item select listener
//
//        new GetBaseFraisList().execute();

        deleteButton = (Button) findViewById(R.id.btn_delete_objet_credit_of);
        deleteButton.setVisibility(View.GONE);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        addButton = (Button) findViewById(R.id.btn_save_objet_credit_of);
        //cirLoginButton
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(CreateObjetCreditOf.this,
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
                    Toast.makeText(CreateObjetCreditOf.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

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
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(CreateObjetCreditOf.this,
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

if (!STRING_EMPTY.equals(OcCodeEditText.getText().toString()) &&
        !STRING_EMPTY.equals(OcLibelleEditText.getText().toString()) &&
        !STRING_EMPTY.equals(OcDetailsEditText.getText().toString())){

            OcCode = OcCodeEditText.getText().toString();
            OcLibelle = OcLibelleEditText.getText().toString();
            OcDetails = OcDetailsEditText.getText().toString();


            new AddObjetCreditAsyncTask().execute();
        } else {
            Toast.makeText(CreateObjetCreditOf.this,
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
            pDialog = new ProgressDialog(CreateObjetCreditOf.this);
            pDialog.setMessage("Ajout d'un objet de crédit. SVP, patientez...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_OC_CODE, OcCode);
            httpParams.put(KEY_OC_LIBELLE, OcLibelle);
            httpParams.put(KEY_OC_DETAILS, OcDetails);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_objet_credit_of.php", "POST", httpParams);
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
                        Toast.makeText(CreateObjetCreditOf.this,
                                "Objet crédit Ajouté", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CreateObjetCreditOf.this,
                                "Une erreur s'est produite lors de l'ajout de l'objet crédit",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}