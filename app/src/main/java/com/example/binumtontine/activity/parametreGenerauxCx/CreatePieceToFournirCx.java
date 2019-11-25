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
package com.example.binumtontine.activity.parametreGenerauxCx;


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

//public class CreatePieceToFournirCx extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SERVER_ADDRESS {
public class CreatePieceToFournirCx extends AppCompatActivity implements SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";

    private static final String KEY_FC_CAISSE_ID = "FcCaisse";
    private static final String KEY_FC_GUICHET_ID = "FcGuichet";
    private static final String KEY_FC_PIECE_ID = "FcFraisPiece";
    private static final String KEY_FC_LIBELLE = "FcNewLibelle";
    private static final String KEY_FC_Is_PIECE_OBLIG = "FcIsOblig";
    private static final String KEY_FC_CAT_ADH = "FcCategAdh";




    private static String STRING_EMPTY = "";

    private EditText FpLibelleEditText;
    private RadioButton rbFpIsPieceObligOui;
    private RadioButton rbFpIsPieceObligNon;


    private String FpLibelle;
    private boolean FpIsPieceOblig;

    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Category> guichetList;
    private ArrayList<Category> pieceList;
    List<Integer> guichetListID = new ArrayList<Integer>();
    List<Integer> pieceListID = new ArrayList<Integer>();
    private int guichetID;
    private int pieceID;
    private Spinner spinnerGuichet;
    private Spinner spinnerPiece;

    /*end manage*/

    private Button addButton;
    private Button deleteButton;
    private Button annulerButton;
    private int success;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pg_piece_to_fournir_cx);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_produit_eav);
        setSupportActionBar(toolbar);
        setToolbarTitle(); */

        spinnerGuichet = (Spinner) findViewById(R.id.spn_select_guichet_fp);
        spinnerPiece = (Spinner) findViewById(R.id.spn_select_piece_fp);
        guichetList = new ArrayList<Category>();
        pieceList = new ArrayList<Category>();
        spinnerGuichet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                //   checkOffersSum(); // same method for first 4 spinners. for last 4 spinners is checkScoresSum()
                guichetID = guichetListID.get(position);//pour recuperer l'ID du guichet selectionné
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub.

            }

        });
        spinnerPiece.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
             //   checkOffersSum(); // same method for first 4 spinners. for last 4 spinners is checkScoresSum()
                pieceID = pieceListID.get(position);//pour recuperer l'ID de la pièce selectionnée
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub.

            }

        });

        // spinner item select listener
        //spinnerGuichet.setOnItemSelectedListener(CreatePieceToFournirCx.this);
        //spinnerPiece.setOnItemSelectedListener(CreatePieceToFournirCx.this);
        new CreatePieceToFournirCx.GetCategories().execute();




       /* rbEpTypTxInterFixe = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
        rbEpTypTxInterTaux = (RadioButton) findViewById(R.id.rbEpTypTxInterTaux);
        rbEpTypTxInterPlage = (RadioButton) findViewById(R.id.rbEpTypTxInterPlage);
        blkPlageEav = (LinearLayout) findViewById(R.id.blk_plage_eav);*/

        FpLibelleEditText = (EditText) findViewById(R.id.input_txt_Libelle_pg_piece_cx);
        rbFpIsPieceObligOui = (RadioButton) findViewById(R.id.rb_isPieceObligatoire_fp_oui);
        rbFpIsPieceObligNon = (RadioButton) findViewById(R.id.rb_isPieceObligatoire_fp_non);

        annulerButton = (Button) findViewById(R.id.btn_clean);
        deleteButton = (Button) findViewById(R.id.btn_delete_eav);
        deleteButton.setVisibility(View.GONE);
        addButton = (Button) findViewById(R.id.btn_save_eav);
        //cirLoginButton
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addEAV();
                } else {
                    Toast.makeText(CreatePieceToFournirCx.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

    }

    /**
     * Adding spinner data
     * */
    private void populateSpinner() {
        List<String> lablesGuichet = new ArrayList<String>(); //for guichets
        List<String> lablesPiece = new ArrayList<String>(); //for pieces

        //tvCaisse.setText("");

        for (int i = 0; i < guichetList.size(); i++) {
            lablesGuichet.add(guichetList.get(i).getName());//recupère les noms de guichets
            guichetListID.add(guichetList.get(i).getId()); //recupère les Id de guichet
        }

        for (int i = 0; i < pieceList.size(); i++) {
            lablesPiece.add(pieceList.get(i).getName());//recupère les noms de pieces
            pieceListID.add(pieceList.get(i).getId()); //recupère les Id de pieces
        }

        // Creating adapter for spinner guichet
        ArrayAdapter<String> spinnerGuichetAdapter = new ArrayAdapter<String>(CreatePieceToFournirCx.this,
                android.R.layout.simple_spinner_item, lablesGuichet);

        // Drop down layout style - list view with radio button
        spinnerGuichetAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Creating adapter for spinner guichet
        ArrayAdapter<String> spinnerPieceAdapter = new ArrayAdapter<String>(CreatePieceToFournirCx.this,
                android.R.layout.simple_spinner_item, lablesPiece);

        // Drop down layout style - list view with radio button
        spinnerPieceAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerGuichet.setAdapter(spinnerGuichetAdapter);
        spinnerPiece.setAdapter(spinnerPieceAdapter);
    }

    /**
     * Async task to get all food categories
     * */
    private class GetCategories extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CreatePieceToFournirCx.this);
            pDialog.setMessage("Fetching guichet's list..");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            String jsonGuichet = jsonParser.makeServiceCall(URL_GUICHETS, ServiceHandler.GET);
            String jsonPiece = jsonParser.makeServiceCall(URL_GET_PIECE_OF, ServiceHandler.GET);

            Log.e("Response: ", "> " + jsonGuichet);
            //for manage list of guichet
            if (jsonGuichet != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonGuichet);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj
                                .getJSONArray("categories");

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category cat = new Category(catObj.getInt("id"),
                                    catObj.getString("name"));
                            guichetList.add(cat);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data guichet", "Didn't receive any data from server!");
            }
            //for manage list of piece
            if (jsonPiece != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonPiece);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj
                                .getJSONArray("categories");

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category cat = new Category(catObj.getInt("id"),
                                    catObj.getString("name"));
                            pieceList.add(cat);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data piece", "Didn't receive any data from server!");
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
/*
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        //guichetID = guichetListID.get(position);//pour recuperer l'ID de la caisse selectionnée
        int idSpinner = view.getId();

        if(idSpinner==R.id.spn_select_guichet_fp ){

            // do this
            guichetID = guichetListID.get(position);//pour recuperer l'ID de la caisse selectionnée
        }

        else{
            //do this
            pieceID = fraisListID.get(position);//pour recuperer l'ID de la caisse selectionnée
        }

        Toast.makeText(
                getApplicationContext(),
                parent.getItemAtPosition(position).toString() + " Selected" ,
                Toast.LENGTH_LONG).show();
        guichetID = guichetListID.get(position);//pour recuperer l'ID de la caisse selectionnée


    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }*/

    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Ajout d'une pièce");

    }

    public void onRadioButtonClicked(View view) {
        boolean checked1 = ((RadioButton) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.rb_isPieceObligatoire_fp_oui:
                if (rbFpIsPieceObligOui.isChecked()) {
                    str = checked1?"Cette pièce est obligatoire":"Type Fixe Deselected";

                }

                break;
            case R.id.rb_isPieceObligatoire_fp_non:
                if (rbFpIsPieceObligNon.isChecked()){
                    str = checked1?"Cette pièce n'est pas obligatoire":"Type Taux Deselected";

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


FpIsPieceOblig = rbFpIsPieceObligOui.isChecked();
    FpLibelle = FpLibelleEditText.getText().toString();
           /* ev_code = ev_codeEditText.getText().toString();
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
            ev_frais_clot_cpt = ev_frais_clot_cptEditText.getText().toString();*/
            new AddEAVAsyncTask().execute();
        } else {
            Toast.makeText(CreatePieceToFournirCx.this,
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
            pDialog = new ProgressDialog(CreatePieceToFournirCx.this);
            pDialog.setMessage("Adding new Piece. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_FC_CAISSE_ID, String.valueOf(guichetID)); // A modifier
            httpParams.put(KEY_FC_GUICHET_ID, String.valueOf(guichetID));
            httpParams.put(KEY_FC_PIECE_ID, String.valueOf(pieceID)); // A modifier
            httpParams.put(KEY_FC_LIBELLE, FpLibelle);
            httpParams.put(KEY_FC_Is_PIECE_OBLIG, String.valueOf(FpIsPieceOblig));
            httpParams.put(KEY_FC_CAT_ADH, String.valueOf(guichetID)); // A modifier

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_piece_cx.php", "POST", httpParams);
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
                        Toast.makeText(CreatePieceToFournirCx.this,
                                "Pièce Ajoutée", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CreatePieceToFournirCx.this,
                                "Some error occurred while adding Pièce",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}