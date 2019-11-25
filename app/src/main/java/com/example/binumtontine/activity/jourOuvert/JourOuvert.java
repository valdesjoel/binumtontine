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
package com.example.binumtontine.activity.jourOuvert;


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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.Category;
import com.example.binumtontine.activity.ServiceHandler;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JourOuvert extends AppCompatActivity implements SERVER_ADDRESS, AdapterView.OnItemSelectedListener {
    private static final String KEY_SUCCESS = "success";

    private static final String KEY_FP_CODE = "FpCode";
    private static final String KEY_FP_LIBELLE = "FpLibelle";
    private static final String KEY_FP_NATURE_FRAIS = "FpNature";
    private static final String KEY_FP_VALEUR = "FpVal";
    private static final String KEY_FP_BASE = "FpBase";
    private static final String KEY_FP_TYPE_ADH = "FpTypeAdh";


    private EditText FpCodeEditText;
    private EditText FpLibelleEditText;
    private EditText FpValEditText;

    private RadioButton rbTypeAdherentPhysique;
    private RadioButton rbTypeAdherentMorale;
    private RadioButton rbNatureFraisFixe;
    private RadioButton rbNatureFraisTaux;


    private String FpCode;
    private String FpLibelle;
    private String FpNature;
    private String FpVal;
    private String FpBase;
    private String FpTypeAdh;




    private static String STRING_EMPTY = "";


    private LinearLayout blkPlageEav;
    private RadioButton rbEpTypTxInterFixe;
    private RadioButton rbEpTypTxInterTaux;
    private RadioButton rbEpTypTxInterPlage;
    private TextInputLayout layout_TauxAPreleveCpteEAV;

    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Category> caisseList;
    List<Integer> caisseListID = new ArrayList<Integer>();
    private int caisseID;
    private Spinner spinnerCaisse;
    private LinearLayout textInputLayoutCaisse;
    /*end manage*/


    private Button addButton;
    private Button deleteButton;
    private int success;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_add_movie);
      //  setContentView(R.layout.fragment_param_produit_eav);
        setContentView(R.layout.activity_jour_ouvert);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_produit_eav);
        setSupportActionBar(toolbar);
        setToolbarTitle(); */



        FpCodeEditText = (EditText) findViewById(R.id.input_txt_CodeFrais_of);
        FpLibelleEditText = (EditText) findViewById(R.id.input_txt_LibelleFrais_of);
        rbNatureFraisFixe = (RadioButton) findViewById(R.id.rb_NatureFraisFixe_fp);
        rbNatureFraisTaux = (RadioButton) findViewById(R.id.rb_NatureFraisTaux_fp);
        FpValEditText = (EditText) findViewById(R.id.input_txt_ValeurFrais_fp);
        //FpBaseEditText = (EditText) findViewById(R.id.Base);
        rbTypeAdherentPhysique = (RadioButton) findViewById(R.id.rb_type_adherent_pg_frais_of_physique);
        rbTypeAdherentMorale = (RadioButton) findViewById(R.id.rb_type_adherent_pg_frais_of_morale);




        rbEpTypTxInterFixe = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
        rbEpTypTxInterTaux = (RadioButton) findViewById(R.id.rbEpTypTxInterTaux);
        rbEpTypTxInterPlage = (RadioButton) findViewById(R.id.rbEpTypTxInterPlage);
        blkPlageEav = (LinearLayout) findViewById(R.id.blk_plage_eav);
        layout_TauxAPreleveCpteEAV = (TextInputLayout) findViewById(R.id.input_layout_TauxAPreleveCpteEAV);

        textInputLayoutCaisse = (LinearLayout) findViewById(R.id.ll_base_frais_of);
        if (rbNatureFraisFixe.isChecked())
        textInputLayoutCaisse.setVisibility(View.GONE);
        else textInputLayoutCaisse.setVisibility(View.VISIBLE);



        spinnerCaisse = (Spinner) findViewById(R.id.spn_baseFrais_fp);

        caisseList = new ArrayList<Category>();
        // spinner item select listener
        spinnerCaisse.setOnItemSelectedListener(JourOuvert.this);
        new JourOuvert.GetCategories().execute();

        deleteButton = (Button) findViewById(R.id.btn_delete_pg_frais_of);
        deleteButton.setVisibility(View.GONE);
        addButton = (Button) findViewById(R.id.btn_save_pg_frais_of);
        //cirLoginButton
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addEAV();
                } else {
                    Toast.makeText(JourOuvert.this,
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

        for (int i = 0; i < caisseList.size(); i++) {
            lables.add(caisseList.get(i).getName());//recupère les noms
            caisseListID.add(caisseList.get(i).getId()); //recupère les Id
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(JourOuvert.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerCaisse.setAdapter(spinnerAdapter);
    }

    /**
     * Async task to get all food categories
     * */
    private class GetCategories extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(JourOuvert.this);
            pDialog.setMessage("Fetching base frais's list..");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_BASE_FRAIS_OF, ServiceHandler.GET);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj
                                .getJSONArray("categories");

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category cat = new Category(catObj.getInt("id"),
                                    catObj.getString("name"));
                            caisseList.add(cat);
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
            if (pDialog.isShowing())
                pDialog.dismiss();
            populateSpinner();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        Toast.makeText(
                getApplicationContext(),
                parent.getItemAtPosition(position).toString() + " Selected" ,
                Toast.LENGTH_LONG).show();
        caisseID = caisseListID.get(position);//pour recuperer l'ID de la caisse selectionnée

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }
    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Ajout d'un frais à payer");

    }

    public void onRadioButtonClicked(View view) {
        boolean checked1 = ((RadioButton) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.rb_NatureFraisFixe_fp:
                if (rbNatureFraisFixe.isChecked()) {
                    textInputLayoutCaisse.setVisibility(View.GONE);
                    str = checked1?"Nature Fixe Selected":"Nature Fixe Deselected";
                    //ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    //FpNature ="F";

                }

                break;
            case R.id.rb_NatureFraisTaux_fp:
                if (rbNatureFraisTaux.isChecked()){
                    textInputLayoutCaisse.setVisibility(View.VISIBLE);
                    str = checked1?"Nature Taux Selected":"Nature Taux Deselected";
                    //ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    //FpNature ="T";

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
            FpCode = FpCodeEditText.getText().toString();
            FpLibelle = FpLibelleEditText.getText().toString();
    if (rbNatureFraisFixe.isChecked()){
        FpNature ="F";
        FpBase = null;
    }else {
        FpNature="T";
        FpBase = String.valueOf(caisseID);
    }

            FpVal = FpValEditText.getText().toString();

    if (rbTypeAdherentPhysique.isChecked()){
        FpTypeAdh ="P";
    }else FpTypeAdh="M";






            new AddEAVAsyncTask().execute();
        } else {
            Toast.makeText(JourOuvert.this,
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
            pDialog = new ProgressDialog(JourOuvert.this);
            pDialog.setMessage("Adding new Frais. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_FP_CODE, FpCode);
            httpParams.put(KEY_FP_LIBELLE, FpLibelle);
            httpParams.put(KEY_FP_NATURE_FRAIS, FpNature);
            httpParams.put(KEY_FP_VALEUR, FpVal);
            httpParams.put(KEY_FP_BASE, FpBase);
            httpParams.put(KEY_FP_TYPE_ADH, FpTypeAdh);



            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_frais_of.php", "POST", httpParams);
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
                        Toast.makeText(JourOuvert.this,
                                "Frais Ajouté", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(JourOuvert.this,
                                "Some error occurred while adding Frais",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}