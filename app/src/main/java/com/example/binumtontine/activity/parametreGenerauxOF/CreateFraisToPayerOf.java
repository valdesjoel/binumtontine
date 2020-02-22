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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateFraisToPayerOf extends AppCompatActivity implements SERVER_ADDRESS, AdapterView.OnItemSelectedListener {
    private static final String KEY_SUCCESS = "success";

    private static final String KEY_FP_CODE = "FpCode";
    private static final String KEY_FP_LIBELLE = "FpLibelle";
    private static final String KEY_FP_NATURE_FRAIS = "FpNature";
    private static final String KEY_FP_VALEUR = "FpVal";
    private static final String KEY_FP_BASE = "FpBase";
    private static final String KEY_FP_TYPE_ADH = "FpTypeAdh";
    private static final String KEY_FC_CAT_ADH = "FpCategAdh";


    private static final String KEY_FP_FONCTION_FRAIS = "FpFonction";
    private static final String KEY_FP_NOMBRE_PART_MIN_J = "FpNbrePartMinJ";
    private static final String KEY_FP_NOMBRE_PART_MIN_F = "FpNbrePartMinF";
    private static final String KEY_FP_NOMBRE_PART_MIN_H = "FpNbrePartMinH";


    private EditText FpCodeEditText;
    private EditText FpLibelleEditText;
    private EditText FpValEditText;
    private EditText FpNbrePartMinJEditText;
    private EditText FpNbrePartMinFEditText;
    private EditText FpNbrePartMinHEditText;

    private RadioButton rbTypeAdherentPhysique;
    private RadioButton rbTypeAdherentMorale;
    private RadioButton rbNatureFraisFixe;
    private RadioButton rbNatureFraisTaux;

    private Spinner spinnerTypeMembre;
    private String FcTypeMembre;

    private String FpCode;
    private String FpLibelle;
    private String FpNature;
    private String FpVal;
    private String FpBase;
    private String FpFonctionFrais;
    private String FpNbrePartMinJ;
    private String FpNbrePartMinF;
    private String FpNbrePartMinH;
    private String FpTypeAdh;




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
        // setContentView(R.layout.activity_add_movie);
      //  setContentView(R.layout.fragment_param_produit_eav);
        setContentView(R.layout.activity_pg_frais_to_payer_of);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_produit_eav);
        setSupportActionBar(toolbar);
        setToolbarTitle(); */



        FpCodeEditText = (EditText) findViewById(R.id.input_txt_CodeFrais_of);
        FpLibelleEditText = (EditText) findViewById(R.id.input_txt_LibelleFrais_of);
        rbNatureFraisFixe = (RadioButton) findViewById(R.id.rb_NatureFraisFixe_fp);
        rbNatureFraisTaux = (RadioButton) findViewById(R.id.rb_NatureFraisTaux_fp);
        FpValEditText = (EditText) findViewById(R.id.input_txt_ValeurFrais_fp);
        FpNbrePartMinJEditText = (EditText) findViewById(R.id.input_txt_NbrePartMinJ_fp);
        FpNbrePartMinFEditText = (EditText) findViewById(R.id.input_txt_NbrePartMinF_fp);
        FpNbrePartMinHEditText = (EditText) findViewById(R.id.input_txt_NbrePartMinH_fp);
        //FpBaseEditText = (EditText) findViewById(R.id.Base);
        rbTypeAdherentPhysique = (RadioButton) findViewById(R.id.rb_type_adherent_pg_frais_of_physique);
        rbTypeAdherentMorale = (RadioButton) findViewById(R.id.rb_type_adherent_pg_frais_of_morale);




        textInputLayoutCaisse = (LinearLayout) findViewById(R.id.ll_base_frais_of);
        textInputLayoutFpNbrePartMin = (LinearLayout) findViewById(R.id.ll_NbrePartMin_of);
        if (rbNatureFraisFixe.isChecked())
        textInputLayoutCaisse.setVisibility(View.GONE);
        else textInputLayoutCaisse.setVisibility(View.VISIBLE);



        spinnerBaseFrais = (Spinner) findViewById(R.id.spn_baseFrais_fp);
        spinnerFonctionFrais = (Spinner) findViewById(R.id.spn_fonctionFrais_fp);

        baseFraisList = new ArrayList<Category>();
        // spinner item select listener
        spinnerBaseFrais.setOnItemSelectedListener(CreateFraisToPayerOf.this);
        spinnerFonctionFrais.setOnItemSelectedListener(CreateFraisToPayerOf.this);

        spinnerTypeMembre = (Spinner) findViewById(R.id.spn_type_membre_fc);
        spinnerTypeMembre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                //   checkOffersSum(); // same method for first 4 spinners. for last 4 spinners is checkScoresSum()
                // FcFonctionFrais = spinnerFonctionFrais.getSelectedItem().toString();//pour recuperer l'ID de la pièce selectionnée
                // your stuff here
                FcTypeMembre = spinnerTypeMembre.getSelectedItem().toString();

//                if (spinnerFonctionFrais.getSelectedItem().toString().equals("Part sociale")){
//                    textInputLayoutFcNbrePartMin.setVisibility(View.VISIBLE);
//                    FcFonctionFrais = "P";
//
//                }else{
//                    textInputLayoutFcNbrePartMin.setVisibility(View.GONE);
//                    if (spinnerFonctionFrais.getSelectedItem().toString().equals("Frais d'adhésion")){
//                        FcFonctionFrais = "A";
//                    }else if (spinnerFonctionFrais.getSelectedItem().toString().equals("Fonds de solidarité")){
//                        FcFonctionFrais = "S";
//                    }else if (spinnerFonctionFrais.getSelectedItem().toString().equals("Approvisionnement")){
//                        FcFonctionFrais = "D";
//                    }else if (spinnerFonctionFrais.getSelectedItem().toString().equals("Frais de fonctionnement")){
//                        FcFonctionFrais = "F";
//                    }
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub.

            }

        });


        new GetBaseFraisList().execute();

        deleteButton = (Button) findViewById(R.id.btn_delete_pg_frais_of);
        deleteButton.setVisibility(View.GONE);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        addButton = (Button) findViewById(R.id.btn_save_pg_frais_of);
        //cirLoginButton
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(CreateFraisToPayerOf.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addFraisToPayer();
                } else {
                    Toast.makeText(CreateFraisToPayerOf.this,
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
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(CreateFraisToPayerOf.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerBaseFrais.setAdapter(spinnerAdapter);
    }

    /**
     * Async task to get all base frais
     * */
    private class GetBaseFraisList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogFetchBaseFraisList = new ProgressDialog(CreateFraisToPayerOf.this);
            pDialogFetchBaseFraisList.setMessage("Fetching base frais's list..");
            pDialogFetchBaseFraisList.setCancelable(false);
            pDialogFetchBaseFraisList.show();

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
                            baseFraisList.add(cat);
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
            if (pDialogFetchBaseFraisList.isShowing())
                pDialogFetchBaseFraisList.dismiss();
            populateSpinner();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        int idSpinner = parent.getId();
        switch (idSpinner)
        {
            case R.id.spn_baseFrais_fp:
                Toast.makeText(
                        getApplicationContext(),
                        parent.getItemAtPosition(position).toString() + " Selected" ,
                        Toast.LENGTH_LONG).show();
                baseFraisID = baseFraisListID.get(position);//pour recuperer l'ID de la caisse selectionnée

                break;
            case R.id.spn_fonctionFrais_fp:
                // your stuff here
                if (spinnerFonctionFrais.getSelectedItem().toString().equals("Part sociale")){
                    textInputLayoutFpNbrePartMin.setVisibility(View.VISIBLE);
                    FpFonctionFrais = "P";

                }else{
                    textInputLayoutFpNbrePartMin.setVisibility(View.GONE);
                    if (spinnerFonctionFrais.getSelectedItem().toString().equals("Frais d'adhésion")){
                        FpFonctionFrais = "A";
                    }else if (spinnerFonctionFrais.getSelectedItem().toString().equals("Fonds de solidarité")){
                        FpFonctionFrais = "S";
                    }else if (spinnerFonctionFrais.getSelectedItem().toString().equals("Approvisionnement") ||
                            spinnerFonctionFrais.getSelectedItem().toString().equals("Dépôt initial DAV")){
                        FpFonctionFrais = "D";
                    }else if (spinnerFonctionFrais.getSelectedItem().toString().equals("Frais de fonctionnement")){
                        FpFonctionFrais = "F";
                    }
                }
                break;

        }

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
     * Checks whether all files are filled. If so then calls AddFraisToPayerAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addFraisToPayer() {
       /* if (!STRING_EMPTY.equals(ev_codeEditText.getText().toString()) &&

                !STRING_EMPTY.equals(ev_libelleEditText.getText().toString()) &&

                !STRING_EMPTY.equals(ev_is_min_cpte_obligSwitch.getText().toString())) { */
if (!STRING_EMPTY.equals(FpCodeEditText.getText().toString()) &&
        !STRING_EMPTY.equals(FpLibelleEditText.getText().toString()) &&
        !STRING_EMPTY.equals(FpValEditText.getText().toString())){

            FpCode = FpCodeEditText.getText().toString();
            FpLibelle = FpLibelleEditText.getText().toString();
            FpNbrePartMinJ = FpNbrePartMinJEditText.getText().toString();
            FpNbrePartMinF = FpNbrePartMinFEditText.getText().toString();
            FpNbrePartMinH = FpNbrePartMinHEditText.getText().toString();
    if (rbNatureFraisFixe.isChecked()){
        FpNature ="F";
        FpBase = null; //à revoir, car ça insère avec comme valeur 0
    }else {
        FpNature="T";
        FpBase = String.valueOf(baseFraisID);
    }

            FpVal = FpValEditText.getText().toString();

    if (rbTypeAdherentPhysique.isChecked()){
        FpTypeAdh ="P";
    }else FpTypeAdh="M";






            new AddFraisToPayerAsyncTask().execute();
        } else {
            Toast.makeText(CreateFraisToPayerOf.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for adding a movie
     */
    private class AddFraisToPayerAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(CreateFraisToPayerOf.this);
            pDialog.setMessage("Ajout de nouveaux Frais. SVP, patientez...");
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
            httpParams.put(KEY_FP_FONCTION_FRAIS, FpFonctionFrais);
            httpParams.put(KEY_FP_NOMBRE_PART_MIN_J, FpNbrePartMinJ);
            httpParams.put(KEY_FP_NOMBRE_PART_MIN_F, FpNbrePartMinF);
            httpParams.put(KEY_FP_NOMBRE_PART_MIN_H, FpNbrePartMinH);
            httpParams.put(KEY_FP_TYPE_ADH, FpTypeAdh);
            httpParams.put(KEY_FC_CAT_ADH, String.valueOf(FcTypeMembre));



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
                        Toast.makeText(CreateFraisToPayerOf.this,
                                "Frais Ajouté", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CreateFraisToPayerOf.this,
                                "Une erreur s'est produite lors de l'ajout du Frais",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}