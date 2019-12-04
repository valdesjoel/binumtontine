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
import com.example.binumtontine.activity.adherent.MainActivityUsager;
import com.example.binumtontine.controleur.MyData;
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

    private static final String KEY_JO_MT_DEMARRAGE = "JoMtDemarr";
    private static final String KEY_JO_MT_PIECE_MONNAIE = "JoMtPMonnaie";
    private static final String KEY_JO_IS_CLOSED = "JoIsClosed";
    private static final String KEY_JO_GUICHET = "JoGuichet";

    private static final String KEY_FP_VALEUR = "FpVal";
    private static final String KEY_FP_BASE = "FpBase";
    private static final String KEY_FP_TYPE_ADH = "FpTypeAdh";


    private EditText JoMtDemarrEditText;
    private EditText JoMtPMonnaieEditText;

    private EditText FpValEditText;

    private RadioButton rbStartNewDay;
    private RadioButton rbStartOldDay;

    private RadioButton rbNatureFraisFixe;
    private RadioButton rbNatureFraisTaux;


    private String JoMtDemarr;
    private String JoMtPMonnaie;
    private Boolean JoIsClosed;

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
    private Button cancelButton;
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



        JoMtDemarrEditText = (EditText) findViewById(R.id.input_txt_montant_demarrage_usager);
        JoMtPMonnaieEditText = (EditText) findViewById(R.id.input_txt_montant_piece_monnaie_usager);

        rbStartNewDay = (RadioButton) findViewById(R.id.rb_que_voulez_vous_faire_NewDay_usager);
        rbStartOldDay = (RadioButton) findViewById(R.id.rb_que_voulez_vous_faire_OldDay_usager);



/*
  rbNatureFraisFixe = (RadioButton) findViewById(R.id.rb_NatureFraisFixe_fp);
        rbNatureFraisTaux = (RadioButton) findViewById(R.id.rb_NatureFraisTaux_fp);
        FpValEditText = (EditText) findViewById(R.id.input_txt_ValeurFrais_fp);
        //FpBaseEditText = (EditText) findViewById(R.id.Base);
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
*/
        cancelButton = (Button) findViewById(R.id.btn_clean);
        addButton = (Button) findViewById(R.id.btn_save_montant_demarrage);
        //cirLoginButton
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(JourOuvert.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        }); addButton.setOnClickListener(new View.OnClickListener() {
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

            case R.id.rb_que_voulez_vous_faire_NewDay_usager:
                if (rbStartNewDay.isChecked()) {
                    JoIsClosed = false;
                   // textInputLayoutCaisse.setVisibility(View.GONE);
                    str = checked1?"Précisez le montant de démarrage":"";
                    //ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    //FpNature ="F";

                }

                break;
            case R.id.rb_que_voulez_vous_faire_OldDay_usager:
                if (rbStartOldDay.isChecked()){
                    JoIsClosed = true;
                    //textInputLayoutCaisse.setVisibility(View.VISIBLE);
                    str = checked1?"Choisissez une date antérieure":"";
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
            JoMtDemarr = JoMtDemarrEditText.getText().toString();
            JoMtPMonnaie = JoMtPMonnaieEditText.getText().toString();


                if (rbStartNewDay.isChecked()){
                    JoIsClosed =false;
                }else JoIsClosed =true;






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
            if(!JoIsClosed)
            pDialog.setMessage("Démarrage d'une nouvelle journée . Please wait...");
            else pDialog.setMessage("Connexion à une journée antérieure . Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_JO_MT_DEMARRAGE, JoMtDemarr);
            httpParams.put(KEY_JO_MT_PIECE_MONNAIE, JoMtPMonnaie);
            httpParams.put(KEY_JO_IS_CLOSED, JoIsClosed.toString());
            httpParams.put(KEY_JO_GUICHET, String.valueOf(MyData.GUICHET_ID));




            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_jour_ouvert.php", "POST", httpParams);
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
                                "Journée ouverte", Toast.LENGTH_LONG).show();
                      /*  Intent i = getIntent();*/
                        //send result code 20 to notify about movie update
                        /* setResult(20, i); */
                        //Finish ths activity and go back to listing activity

                        //Intent i = new Intent(this, CreateAdherent.class);
                        Intent i = new Intent(JourOuvert.this, MainActivityUsager.class);
                        startActivity(i);
                        finish();

                    } else {
                        Toast.makeText(JourOuvert.this,
                                "Some error occurred while open day",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}