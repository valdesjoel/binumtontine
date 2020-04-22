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
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateStatutEtapeDemandeCreditOf extends AppCompatActivity implements AdapterView.OnItemSelectedListener,SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";

    private static final String KEY_SE_ETAPE_DEMANDE = "SeEtapeDem";
    private static final String KEY_SE_LIBELLE = "SeLibelle";
    private static final String KEY_SE_NUM_ORDRE = "SeNumOrd";
    private static final String KEY_SE_CODE = "SeCode";
    private static final String KEY_ED_NUMERO = "EdNumero";
    private static final String KEY_ED_LIBELLE = "EdLibelle";

    private EditText SeLibelleEditText;
    private EditText SeNumOrdrEditText;
    private EditText SeCodeEditText;

    private String SeLibelle;
    private String SeNumOrdr;
    private String SeCode;

    private static String STRING_EMPTY = "";



    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Category> SeEtapeDemList;
    List<Integer> SeEtapeDemListID = new ArrayList<Integer>();
    private int SeEtapeDem;
    private Spinner spinnerSeEtapeDem;
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
    private ProgressDialog pDialogFetchProduitCreditList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statut_etape_demande_credit_of);

        SeCodeEditText = (EditText) findViewById(R.id.input_txt_Code_Statut_of);
        SeLibelleEditText = (EditText) findViewById(R.id.input_txt_Libelle_Statut_etape_demande_credit_of);
        SeNumOrdrEditText = (EditText) findViewById(R.id.input_txt_Num_ordre_statut_etape_demande_credit_of);
        spinnerSeEtapeDem = (Spinner) findViewById(R.id.spn_list_etape_credit);
//
//        textInputLayoutCaisse = (LinearLayout) findViewById(R.id.ll_base_frais_of);
//        textInputLayoutFpNbrePartMin = (LinearLayout) findViewById(R.id.ll_NbrePartMin_of);
//
//
//
//
        SeEtapeDemList = new ArrayList<Category>();
//        // spinner item select listener
        spinnerSeEtapeDem.setOnItemSelectedListener(CreateStatutEtapeDemandeCreditOf.this);
        new GetEtapeDemandeCreditList().execute();

        deleteButton = (Button) findViewById(R.id.btn_delete_statut_etape_credit_of);
        deleteButton.setVisibility(View.GONE);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        addButton = (Button) findViewById(R.id.btn_save_statut_etape_credit_of);
        //cirLoginButton
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(CreateStatutEtapeDemandeCreditOf.this,
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
                    Toast.makeText(CreateStatutEtapeDemandeCreditOf.this,
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

        for (int i = 0; i < SeEtapeDemList.size(); i++) {
            lables.add(SeEtapeDemList.get(i).getName());//recupère les noms
            SeEtapeDemListID.add(SeEtapeDemList.get(i).getId()); //recupère les Id
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(CreateStatutEtapeDemandeCreditOf.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerSeEtapeDem.setAdapter(spinnerAdapter);
    }






    /**
     * Checks whether all files are filled. If so then calls AddObjetCreditAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addObjetCredit() {

        if (SeEtapeDem!=0 && !STRING_EMPTY.equals(SeCodeEditText.getText().toString()) &&
                !STRING_EMPTY.equals(SeLibelleEditText.getText().toString()) &&
                !STRING_EMPTY.equals(SeNumOrdrEditText.getText().toString())){

            SeCode = SeCodeEditText.getText().toString();
            SeLibelle = SeLibelleEditText.getText().toString();
            SeNumOrdr = SeNumOrdrEditText.getText().toString();


            new AddObjetCreditAsyncTask().execute();
        } else {
            Toast.makeText(CreateStatutEtapeDemandeCreditOf.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();

        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int idSpinner = parent.getId();

        switch (idSpinner)
        {
            case R.id.spn_list_etape_credit:
                // your stuff here
                SeEtapeDem = SeEtapeDemListID.get(position);//pour recuperer l'ID de l'étape de demande de crédit selectionné
                break;
//            case R.id.spn_list_objet_credit:
//                // your stuff here
//                objetCreditID = objetCreditListID.get(position);//pour recuperer l'ID de l'objet crédit selectionné
//                break;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * AsyncTask for adding a movie
     */
    private class AddObjetCreditAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(CreateStatutEtapeDemandeCreditOf.this);
            pDialog.setMessage("Ajout d'un statut pour une étape de demande de crédit. SVP, patientez...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_SE_CODE, SeCode);
            httpParams.put(KEY_SE_ETAPE_DEMANDE, String.valueOf(SeEtapeDem));
            httpParams.put(KEY_SE_LIBELLE, SeLibelle);
            httpParams.put(KEY_SE_NUM_ORDRE, SeNumOrdr);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_statut_etapes_demande_credit_of.php", "POST", httpParams);
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
                        Toast.makeText(CreateStatutEtapeDemandeCreditOf.this,
                                "Statut étape demande de crédit Ajouté", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CreateStatutEtapeDemandeCreditOf.this,
                                "Une erreur s'est produite lors de l'ajout du statut",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }


    /**
     * Async task to get all food categories
     * */
    private class GetEtapeDemandeCreditList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogFetchProduitCreditList = new ProgressDialog(CreateStatutEtapeDemandeCreditOf.this);
            pDialogFetchProduitCreditList.setMessage("Fetching produits's list..");
            pDialogFetchProduitCreditList.setCancelable(false);
            pDialogFetchProduitCreditList.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_etapes_demande_credit_of.php", ServiceHandler.GET, null);
            // String json = jsonParser.makeServiceCall(URL_GUICHETS, ServiceHandler.GET);



            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj
                                .getJSONArray(KEY_DATA);

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category cat = new Category(catObj.getInt(KEY_ED_NUMERO),
                                    catObj.getString(KEY_ED_LIBELLE));
                            SeEtapeDemList.add(cat);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialogFetchProduitCreditList.isShowing())
                pDialogFetchProduitCreditList.dismiss();
            populateSpinner();
        }

    }
}