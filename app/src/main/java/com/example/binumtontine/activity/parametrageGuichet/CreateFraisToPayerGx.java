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
package com.example.binumtontine.activity.parametrageGuichet;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.Category;
import com.example.binumtontine.activity.ServiceHandler;
import com.example.binumtontine.controleur.MyData;
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

public class CreateFraisToPayerGx extends AppCompatActivity implements SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";

    private static final String KEY_EXTRA_ACTION_TO_AFFECT = "ACTION_TO_AFFECT"; //to get intent.extra
    private static final String KEY_FP_PIECE_ID = "FpNumero"; //to get intent.extra

    private static final String KEY_FC_CAISSE_ID = "FcCaisse";
    private static final String KEY_FC_GUICHET_ID = "FcGuichet";
    private static final String KEY_FC_FRAIS_ID = "FcFraisPiece";
    private static final String KEY_FC_LIBELLE = "FcNewLibelle";
    private static final String KEY_FC_CODE = "FcCode";
    private static final String KEY_FC_NATURE_FRAIS = "FcNature";
    private static final String KEY_FC_VALEUR = "FcVal";
    private static final String KEY_FC_BASE = "FcBase";
    private static final String KEY_FC_Is_FRAIS_OBLIG = "FcIsOblig";
    private static final String KEY_FC_CAT_ADH = "FcTypeAdh";
    private static final String KEY_FC_CAT_ADH1 = "FcCategAdh";


    private static final String KEY_FC_FONCTION_FRAIS = "FcFonction";
    private static final String KEY_FC_NOMBRE_PART_MIN_J = "FcNbrePartMinJ";
    private static final String KEY_FC_NOMBRE_PART_MIN_F = "FcNbrePartMinF";
    private static final String KEY_FC_NOMBRE_PART_MIN_H = "FcNbrePartMinH";




    private static String STRING_EMPTY = "";

    private EditText FcLibelleEditText;
    private EditText FcCodeEditText;
    private EditText FcValEditText;
    private EditText FcFonctionFraisEditText;
    private EditText FcNbrePartMinJEditText;
    private EditText FcNbrePartMinFEditText;
    private EditText FcNbrePartMinHEditText;
    private RadioButton rbNatureFraisFixe;
    private RadioButton rbNatureFraisTaux;
    private RadioButton rbFcIsPieceObligOui;
    private RadioButton rbFcIsPieceObligNon;
    private LinearLayout textInputLayoutBlocBaseFrais; // for display/hide bloc base frais when radiobutton fixe is selected
    private LinearLayout textInputLayoutFcNbrePartMin;

    private String FcLibelle;
    private String FpCode;
    private String FcNature="";
    private String FcVal;
    private String FcBase;
    private String FcCategAdh;


    private String FcFonctionFrais="";
    private String FcNbrePartMinJ;
    private String FcNbrePartMinF;
    private String FcNbrePartMinH;
    private boolean FcIsPieceOblig;

    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Category> guichetList;
    private ArrayList<Category> fraisList;
    private ArrayList<Category> baseFraisList;
    List<Integer> guichetListID = new ArrayList<Integer>();
    List<Integer> fraisListID = new ArrayList<Integer>();
    List<Integer> baseFraisListID = new ArrayList<Integer>();
    private int guichetID =0;
    private int fraisID;
    private int baseFraisID;
    private Spinner spinnerGuichet;
    private Spinner spinnerFrais;
    private Spinner spinnerBaseFrais;
    private Spinner spinnerFonctionFrais;


    private Spinner spinnerTypeMembre;
    private String FcTypeMembre;


    private String eavId;
    private Boolean action_to_affect;
    private TextView headerEAVTextView;

    /*end manage*/

    private Button addButton;
    private Button deleteButton;
    private Button annulerButton;
    private int success;
    private ProgressDialog pDialog;
    private ProgressDialog pDialogFetchGuichetList;
    private ProgressDialog pDialogFetchFraisList;
    private String FcFraisPiece;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_add_movie);
      //  setContentView(R.layout.fragment_param_produit_eav);
        setContentView(R.layout.activity_pg_frais_to_payer_cx);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_produit_eav);
        setSupportActionBar(toolbar);
        setToolbarTitle(); */



        Intent intent = getIntent();

        eavId = intent.getStringExtra(KEY_FP_PIECE_ID);
        action_to_affect = getIntent().getExtras().getBoolean(KEY_EXTRA_ACTION_TO_AFFECT);
        headerEAVTextView = (TextView) findViewById(R.id.header_eav);
        new FetchFraisDetailsAsyncTask().execute();


        spinnerGuichet = (Spinner) findViewById(R.id.spn_select_guichet_fp);
        spinnerFrais = (Spinner) findViewById(R.id.spn_select_frais_fp);
        spinnerBaseFrais = (Spinner) findViewById(R.id.spn_baseFrais_fp);
        FcNbrePartMinJEditText = (EditText) findViewById(R.id.input_txt_NbrePartMinJ_fc);
        FcNbrePartMinFEditText = (EditText) findViewById(R.id.input_txt_NbrePartMinF_fc);
        FcNbrePartMinHEditText = (EditText) findViewById(R.id.input_txt_NbrePartMinH_fc);
        spinnerFonctionFrais = (Spinner) findViewById(R.id.spn_fonctionFrais_fc);
        guichetList = new ArrayList<Category>();
        fraisList = new ArrayList<Category>();
        baseFraisList = new ArrayList<Category>();
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
        spinnerFrais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                //   checkOffersSum(); // same method for first 4 spinners. for last 4 spinners is checkScoresSum()
                fraisID = fraisListID.get(position);//pour recuperer l'ID de la pièce selectionnée
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub.

            }

        });
        spinnerFonctionFrais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                //   checkOffersSum(); // same method for first 4 spinners. for last 4 spinners is checkScoresSum()
               // FcFonctionFrais = spinnerFonctionFrais.getSelectedItem().toString();//pour recuperer l'ID de la pièce selectionnée
                // your stuff here
                if (spinnerFonctionFrais.getSelectedItem().toString().equals("Part sociale")){
                    textInputLayoutFcNbrePartMin.setVisibility(View.VISIBLE);
                    FcFonctionFrais = "P";

                }else{
                    textInputLayoutFcNbrePartMin.setVisibility(View.GONE);
                    if (spinnerFonctionFrais.getSelectedItem().toString().equals("Frais d'adhésion")){
                        FcFonctionFrais = "A";
                    }else if (spinnerFonctionFrais.getSelectedItem().toString().equals("Fonds de solidarité")){
                        FcFonctionFrais = "S";
                    }else if (spinnerFonctionFrais.getSelectedItem().toString().equals("Approvisionnement")){
                        FcFonctionFrais = "D";
                    }else if (spinnerFonctionFrais.getSelectedItem().toString().equals("Frais de fonctionnement")){
                        FcFonctionFrais = "F";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub.

            }

        });
        spinnerBaseFrais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                //   checkOffersSum(); // same method for first 4 spinners. for last 4 spinners is checkScoresSum()
                baseFraisID = baseFraisListID.get(position);//pour recuperer l'ID de la base des frais selectionnée
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub.

            }

        });

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


        // spinner item select listener
        //spinnerGuichet.setOnItemSelectedListener(CreatePieceToFournirCx.this);
        //spinnerFrais.setOnItemSelectedListener(CreatePieceToFournirCx.this);

        new CreateFraisToPayerGx.GetCategories().execute(); //to get list of guichet & frais & base frais


        FcLibelleEditText = (EditText) findViewById(R.id.input_txt_LibelleFrais_cx);
        FcCodeEditText = (EditText) findViewById(R.id.input_txt_CodeFrais_cx);
        rbNatureFraisFixe = (RadioButton) findViewById(R.id.rb_NatureFraisFixe_fp);
        rbNatureFraisTaux = (RadioButton) findViewById(R.id.rb_NatureFraisTaux_fp);
        FcValEditText = (EditText) findViewById(R.id.input_txt_ValeurFrais_fp);
        FcFonctionFraisEditText = (EditText) findViewById(R.id.input_txt_fonction_frais_cx);
        rbFcIsPieceObligOui = (RadioButton) findViewById(R.id.rb_isPieceObligatoire_fp_oui);
        rbFcIsPieceObligNon = (RadioButton) findViewById(R.id.rb_isPieceObligatoire_fp_non);

        textInputLayoutBlocBaseFrais = (LinearLayout) findViewById(R.id.ll_base_frais_cx);
        textInputLayoutFcNbrePartMin = (LinearLayout) findViewById(R.id.ll_NbrePartMin_cx);
        if (rbNatureFraisFixe.isChecked())
            textInputLayoutBlocBaseFrais.setVisibility(View.GONE);
        //else textInputLayoutBlocBaseFrais.setVisibility(View.VISIBLE);

        annulerButton = (Button) findViewById(R.id.btn_clean);
        deleteButton = (Button) findViewById(R.id.btn_delete_eav);
        deleteButton.setVisibility(View.GONE);
        addButton = (Button) findViewById(R.id.btn_save_eav);
        //cirLoginButton
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addFraisToPayer();
                } else {
                    Toast.makeText(CreateFraisToPayerGx.this,
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


        if (action_to_affect){
            headerEAVTextView.setText("Affectation du frais au guichet");
            addButton.setText("AFFECTER");
            deleteButton.setVisibility(View.GONE);
            annulerButton.setVisibility(View.VISIBLE);
           /* annulerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();

                }
            });*/
        }else{
            headerEAVTextView.setText("Mise à jour du frais");
            addButton.setText("MODIFIER");
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirmDelete();
                }
            });
        }
    }
//    private void setToolbarTitle() {
//        getSupportActionBar().setTitle("Ajout d'un produit EAV");
//
//    }
    /**
     * Displays an alert dialogue to confirm the deletion
     */
    private void confirmDelete() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                CreateFraisToPayerGx.this);
        alertDialogBuilder.setMessage("Êtes-vous sûr de vouloir supprimer ce frais ?");
        alertDialogBuilder.setPositiveButton("Supprimer",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                            //If the user confirms deletion, execute DeleteFraisAsyncTask
                            new DeleteFraisAsyncTask().execute();
                        } else {
                            Toast.makeText(CreateFraisToPayerGx.this,
                                    "Impossible de se connecter à Internet",
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                });

        alertDialogBuilder.setNegativeButton("Cancel", null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * AsyncTask to delete a frais
     */
    private class DeleteFraisAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(CreateFraisToPayerGx.this);
            pDialog.setMessage("Suppression du frais. SVP veuillez patienter...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Set movie_id parameter in request
            httpParams.put(KEY_FP_PIECE_ID, eavId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "delete_frais_cx.php", "POST", httpParams);
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
                        Toast.makeText(CreateFraisToPayerGx.this,
                                "Frais supprimé", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie deletion
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(CreateFraisToPayerGx.this,
                                "\n" +
                                        "Une erreur s'est produite lors de la suppression du frais",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    /**
     * Fetches single frais details from the server
     */
    private class FetchFraisDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
           /* pDialog = new ProgressDialog(CreateFraisToPayerCx.this);
            pDialog.setMessage("\n" +
                    "Chargement des détails du frais . SVP veuillez patienter...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            */
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_FP_PIECE_ID, eavId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_frais_cx_details.php", "GET", httpParams);

//            JSONObject jsonObject =(action_to_affect)?
//                    httpJsonParser.makeHttpRequest(
//                            BASE_URL + "get_frais_cx_details.php", "GET", httpParams)
//                    :
//                    httpJsonParser.makeHttpRequest(
//                            BASE_URL + "get_frais_gx_details.php", "GET", httpParams);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject eav;
                if (success == 1) {
                    //Parse the JSON response
                    eav = jsonObject.getJSONObject(KEY_DATA);


//                    if (action_to_affect){
                        FpCode = eav.getString(KEY_FC_CODE);
                        FcLibelle = eav.getString(KEY_FC_LIBELLE);
                        FcNature = eav.getString(KEY_FC_NATURE_FRAIS);
                        FcVal = eav.getString(KEY_FC_VALEUR);
                        FcBase = eav.getString(KEY_FC_BASE);
                        FcCategAdh = eav.getString(KEY_FC_CAT_ADH);
                        //FcTypeMembre = eav.getString(KEY_FC_CAT_ADH1);
                    ;
                        FcFonctionFrais = eav.getString(KEY_FC_FONCTION_FRAIS);
                        FcNbrePartMinJ = eav.getString(KEY_FC_NOMBRE_PART_MIN_J);
                        FcNbrePartMinF = eav.getString(KEY_FC_NOMBRE_PART_MIN_F);
                        FcNbrePartMinH = eav.getString(KEY_FC_NOMBRE_PART_MIN_H);
                        FcFraisPiece = eav.getString(KEY_FC_FRAIS_ID);
                       // if (!action_to_affect)FcIsPieceOblig = Boolean.parseBoolean(eav.getString(KEY_FC_Is_FRAIS_OBLIG));
                        FcIsPieceOblig = Boolean.parseBoolean(eav.getString(KEY_FC_Is_FRAIS_OBLIG));

                  /*  }else{

                    }*/
//                    FpCode = eav.getString(KEY_FC_CODE);
//                    FpLibelle = eav.getString(KEY_FC_LIBELLE);
//                    FpIsPieceOblig = Boolean.parseBoolean(eav.getString(KEY_FC_Is_PIECE_OBLIG));
//                  ev_is_min_cpte_oblig = Boolean.parseBoolean(eav.getString(KEY_EAV_IS_MIN_CPTE_OBLIG));


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
//            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    //Populate the Edit Texts once the network activity is finished executing


                    FcCodeEditText.setText(FpCode);
                    FcLibelleEditText.setText(FcLibelle);
                    FcValEditText.setText(FcVal);
                    if (FcCategAdh!=null){
                        // Creating adapter for spinner piece
                        String[] mTestArray;
                        mTestArray = getResources().getStringArray(R.array.type_membre);
                        ArrayAdapter<String> spinnerFraisAdapter = new ArrayAdapter<String>(CreateFraisToPayerGx.this,
                                android.R.layout.simple_spinner_item, mTestArray);

                        // Drop down layout style - list view with radio button
                        spinnerFraisAdapter
                                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spinnerTypeMembre.setSelection(spinnerFraisAdapter.getPosition(FcCategAdh));
                    }
                    //FcC.setText(FcCategAdh);
                    if (FcNature.equals("F")){
                        rbNatureFraisFixe.setChecked(true);
                    }else if (FcNature.equals("T")){
                        rbNatureFraisTaux.setChecked(true);
                    }
                    if (FcFonctionFrais.equals("P")){
                        textInputLayoutFcNbrePartMin.setVisibility(View.VISIBLE);
                        FcFonctionFrais = "Part sociale";

                    }else{
                        textInputLayoutFcNbrePartMin.setVisibility(View.GONE);
                        if (FcFonctionFrais.equals("A")){
                            FcFonctionFrais = "Frais d'adhésion";
                        }else if (FcFonctionFrais.equals("S")){
                            FcFonctionFrais = "Fonds de solidarité";
                        }else if (FcFonctionFrais.equals("D")){
                            FcFonctionFrais = "Approvisionnement";
                        }else if (FcFonctionFrais.equals("F")){
                            FcFonctionFrais = "Frais de fonctionnement";
                        }
                    }

                    FcFonctionFraisEditText.setText(FcFonctionFrais);
                    FcNbrePartMinJEditText.setText(FcNbrePartMinJ);
                    FcNbrePartMinFEditText.setText(FcNbrePartMinF);
                    FcNbrePartMinHEditText.setText(FcNbrePartMinH);
//                    if (!action_to_affect){

                        rbFcIsPieceObligOui.setChecked(FcIsPieceOblig);
                        rbFcIsPieceObligNon.setChecked(!FcIsPieceOblig);
//                    }
                   /* if (action_to_affect){
                        FpCodeEditText.setText(FpCode);
                        FpLibelleEditText.setText(FpLibelle);
                    }else{
                        FpCodeEditText.setText(FpCode);
                        FpLibelleEditText.setText(FpLibelle);
                        rbFpIsPieceObligOui.setChecked(FpIsPieceOblig);
                        rbFpIsPieceObligNon.setChecked(!FpIsPieceOblig);

                    }*/

                }
            });
        }


    }


    /**
     * Adding spinner data
     * */
    private void populateSpinner() {
        List<String> lablesGuichet = new ArrayList<String>(); //for guichets
        List<String> lablesFrais = new ArrayList<String>(); //for pieces
        List<String> lablesBaseFrais = new ArrayList<String>(); //for base frais

        //tvCaisse.setText("");

        for (int i = 0; i < guichetList.size(); i++) {
            lablesGuichet.add(guichetList.get(i).getName());//recupère les noms de guichets
            guichetListID.add(guichetList.get(i).getId()); //recupère les Id de guichet
        }

        for (int i = 0; i < fraisList.size(); i++) {
            lablesFrais.add(fraisList.get(i).getName());//recupère les noms de pieces
            fraisListID.add(fraisList.get(i).getId()); //recupère les Id de pieces
        }

        for (int i = 0; i < baseFraisList.size(); i++) {
            lablesBaseFrais.add(baseFraisList.get(i).getName());//recupère les noms de la base des frais
            baseFraisListID.add(baseFraisList.get(i).getId()); //recupère les Id de la base des frais
        }

        // Creating adapter for spinner guichet
        ArrayAdapter<String> spinnerGuichetAdapter = new ArrayAdapter<String>(CreateFraisToPayerGx.this,
                android.R.layout.simple_spinner_item, lablesGuichet);

        // Drop down layout style - list view with radio button
        spinnerGuichetAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Creating adapter for spinner piece
        ArrayAdapter<String> spinnerFraisAdapter = new ArrayAdapter<String>(CreateFraisToPayerGx.this,
                android.R.layout.simple_spinner_item, lablesFrais);

        // Drop down layout style - list view with radio button
        spinnerFraisAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Creating adapter for spinner base frais
        ArrayAdapter<String> spinnerBaseFraisAdapter = new ArrayAdapter<String>(CreateFraisToPayerGx.this,
                android.R.layout.simple_spinner_item, lablesBaseFrais);

        // Drop down layout style - list view with radio button
        spinnerBaseFraisAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinnerGuichet.setAdapter(spinnerGuichetAdapter);
        spinnerFrais.setAdapter(spinnerFraisAdapter);
        spinnerBaseFrais.setAdapter(spinnerBaseFraisAdapter);

//        if (FcBase!=null){
//            spinnerBaseFrais.setSelection(spinnerBaseFraisAdapter.getPosition(FcBase));
//        }
    }

    /**
     * Async task to get all food categories
     * */
    private class GetCategories extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CreateFraisToPayerGx.this);
            pDialog.setMessage("Fetching parameters's list..");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
          //  String jsonGuichet = jsonParser.makeServiceCall(URL_GUICHETS, ServiceHandler.GET);
           // String jsonFrais = jsonParser.makeServiceCall(URL_GET_FRAIS_OF, ServiceHandler.GET);
            String jsonBaseFrais = jsonParser.makeServiceCall(URL_BASE_FRAIS_OF, ServiceHandler.GET);
/*
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
            //for manage list of frais
            if (jsonFrais != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonFrais);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj
                                .getJSONArray("categories");

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category cat = new Category(catObj.getInt("id"),
                                    catObj.getString("name"));
                            fraisList.add(cat);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data frais", "Didn't receive any data from server!");
            }
            */
            //for manage list of base frais
            if (jsonBaseFrais != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonBaseFrais);
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
                Log.e("JSON Data base frais", "Didn't receive any data from server!");
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




    public void onRadioButtonClicked(View view) {
        boolean checked1 = ((RadioButton) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.rb_NatureFraisFixe_fp:
                if (rbNatureFraisFixe.isChecked()) {
                    textInputLayoutBlocBaseFrais.setVisibility(View.GONE);
                    str = checked1?"Nature frais fixe":"";

                }
                break;
            case R.id.rb_NatureFraisTaux_fp:
                if (rbNatureFraisTaux.isChecked()) {
                    textInputLayoutBlocBaseFrais.setVisibility(View.VISIBLE);
                    str = checked1?"Nature frais taux":"";

                }
                break;
            case R.id.rb_isPieceObligatoire_fp_oui:
                if (rbFcIsPieceObligOui.isChecked()) {
                    str = checked1?"Ce frais est obligatoire":"";

                }

                break;
            case R.id.rb_isPieceObligatoire_fp_non:
                if (rbFcIsPieceObligNon.isChecked()){
                    str = checked1?"Ce frais n'est pas obligatoire":"";

                }


                break;

        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }



    /**
     * Checks whether all files are filled. If so then calls AddEAVAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addFraisToPayer() {

        if (!STRING_EMPTY.equals(FcLibelleEditText.getText().toString()) &&
                !STRING_EMPTY.equals(FcValEditText.getText().toString())){


            FcIsPieceOblig = rbFcIsPieceObligOui.isChecked();
            FcLibelle = FcLibelleEditText.getText().toString();
            FcNbrePartMinJ = FcNbrePartMinJEditText.getText().toString();
            FcNbrePartMinF = FcNbrePartMinFEditText.getText().toString();
            FcNbrePartMinH = FcNbrePartMinHEditText.getText().toString();

            if (rbNatureFraisFixe.isChecked()){
                FcNature ="F";
                FcBase = null; //à revoir, car ça insère avec comme valeur 0 (car c'est un integer)
            }else {
                FcNature ="T";
                FcBase = String.valueOf(baseFraisID);
            }

            FcVal = FcValEditText.getText().toString();

            if (FcFonctionFrais.equals("Part sociale")){
                textInputLayoutFcNbrePartMin.setVisibility(View.VISIBLE);
                FcFonctionFrais = "P";

            }else{
                textInputLayoutFcNbrePartMin.setVisibility(View.GONE);
                if (FcFonctionFrais.equals("Frais d'adhésion")){
                    FcFonctionFrais = "A";
                }else if (FcFonctionFrais.equals("Fonds de solidarité")){
                    FcFonctionFrais = "S";
                }else if (FcFonctionFrais.equals("Approvisionnement")){
                    FcFonctionFrais = "D";
                }else if (FcFonctionFrais.equals("Frais de fonctionnement")){
                    FcFonctionFrais = "F";
                }
            }


            new CreateFraisToPayerGx.AddEAVAsyncTask().execute();
        } else {
            Toast.makeText(CreateFraisToPayerGx.this,
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
            pDialogFetchFraisList = new ProgressDialog(CreateFraisToPayerGx.this);
            pDialogFetchFraisList.setMessage("Adding new Frais. Please wait...");
            pDialogFetchFraisList.setIndeterminate(false);
            pDialogFetchFraisList.setCancelable(false);
            pDialogFetchFraisList.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_FC_CAISSE_ID, String.valueOf(MyData.CAISSE_ID));
            httpParams.put(KEY_FC_GUICHET_ID, String.valueOf(MyData.GUICHET_ID)); // A modifier
           // httpParams.put(KEY_FC_FRAIS_ID, String.valueOf(fraisID));
            httpParams.put(KEY_FC_FRAIS_ID, FcFraisPiece);
            httpParams.put(KEY_FC_LIBELLE, FcLibelle);
            httpParams.put(KEY_FC_NATURE_FRAIS, FcNature);
            httpParams.put(KEY_FC_VALEUR, FcVal);
            httpParams.put(KEY_FC_BASE, FcBase);

            httpParams.put(KEY_FC_FONCTION_FRAIS, FcFonctionFrais);
            httpParams.put(KEY_FC_NOMBRE_PART_MIN_J, FcNbrePartMinJ);
            httpParams.put(KEY_FC_NOMBRE_PART_MIN_F, FcNbrePartMinF);
            httpParams.put(KEY_FC_NOMBRE_PART_MIN_H, FcNbrePartMinH);
            httpParams.put(KEY_FC_Is_FRAIS_OBLIG, String.valueOf(FcIsPieceOblig));
            httpParams.put(KEY_FC_CAT_ADH, String.valueOf(guichetID)); // A modifier
            httpParams.put(KEY_FC_CAT_ADH1, String.valueOf(FcCategAdh));
            httpParams.put(KEY_FP_PIECE_ID, eavId);

//            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
//                    BASE_URL + "add_frais_cx.php", "POST", httpParams);


            JSONObject jsonObject =(action_to_affect)?
                    httpJsonParser.makeHttpRequest(
                            BASE_URL + "assign_frais_of_to_caisse.php", "POST", httpParams)
                    :
                    httpJsonParser.makeHttpRequest(
                            BASE_URL + "update_frais_cx.php", "POST", httpParams);
            try {
                success = jsonObject.getInt(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialogFetchFraisList.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(CreateFraisToPayerGx.this,
                                "Frais Modifié", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CreateFraisToPayerGx.this,
                                "Some error occurred while adding Frais",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}