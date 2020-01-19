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


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

//public class CreatePieceToFournirCx extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SERVER_ADDRESS {
public class CreateTypeMembreOf extends AppCompatActivity implements SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";

    private static final String KEY_FC_CAISSE_ID = "FcCaisse";
    private static final String KEY_FC_GUICHET_ID = "FcGuichet";
    private static final String KEY_FC_PIECE_ID = "FcFraisPiece";

    private static final String KEY_FC_LIBELLE = "TmLibelle";
    private static final String KEY_FC_CODE = "TmNature";
    private static final String KEY_FC_Is_PIECE_OBLIG = "TmType";
    private static final String KEY_FC_Is_TM_ON = "TmIsOn";

    private static final String KEY_FC_CAT_ADH = "FcCategAdh";
    private static final String KEY_EXTRA_ACTION_TO_AFFECT = "ACTION_TO_AFFECT"; //to get intent.extra
    private static final String KEY_TYPE_MEMBRE_ID = "TmNumero"; //to get intent.extra




    private static String STRING_EMPTY = "";

    private EditText FpLibelleEditText;
    private EditText FpCodeEditText;
    private RadioButton rbFpIsPersonnePhysiqueOui;
    private RadioButton rbFpISPersonnePhysiqueNon;
    private RadioButton rbFpIsTmOnOui;
    private RadioButton rbFpIsTmOnNon;


    private String FpLibelle;
    private String FpCode;
    private String FcCategAdh;
    private String FcTypeMembre;
    private boolean FpIsPieceOblig;
    private boolean FpIsTmOn;

    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Category> guichetList;
    private ArrayList<Category> pieceList;
    List<Integer> guichetListID = new ArrayList<Integer>();
    List<Integer> pieceListID = new ArrayList<Integer>();
    private int guichetID = 0;
    private int pieceID;

    private String eavId;
    private Boolean action_to_affect;
    private TextView headerEAVTextView;

    private Spinner spinnerGuichet;
    private Spinner spinnerPiece;
    private Spinner spinnerFonctionFrais;
    /*end manage*/

    private Button addButton;
    private Button deleteButton;
    private Button annulerButton;
    private int success;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_type_membre);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_produit_eav);
        setSupportActionBar(toolbar);
        setToolbarTitle(); */

        Intent intent = getIntent();

        eavId = intent.getStringExtra(KEY_TYPE_MEMBRE_ID);
        action_to_affect = getIntent().getExtras().getBoolean(KEY_EXTRA_ACTION_TO_AFFECT);
        headerEAVTextView = (TextView) findViewById(R.id.header_eav);
        if (action_to_affect)new FetchPieceDetailsAsyncTask().execute();

        spinnerGuichet = (Spinner) findViewById(R.id.spn_select_guichet_fp);
        spinnerPiece = (Spinner) findViewById(R.id.spn_select_piece_fp);

        spinnerFonctionFrais = (Spinner) findViewById(R.id.spn_type_membre_fc);
        spinnerFonctionFrais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                //   checkOffersSum(); // same method for first 4 spinners. for last 4 spinners is checkScoresSum()
                // FcFonctionFrais = spinnerFonctionFrais.getSelectedItem().toString();//pour recuperer l'ID de la pièce selectionnée
                // your stuff here
                FcTypeMembre = spinnerFonctionFrais.getSelectedItem().toString();

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






        FpCodeEditText = (EditText) findViewById(R.id.input_txt_Nature_type_membre);
        alreadyUpperCase(FpCodeEditText);
        FpLibelleEditText = (EditText) findViewById(R.id.input_txt_Libelle_type_membre);
        //alreadyUpperCase(FpLibelleEditText);
        rbFpIsPersonnePhysiqueOui = (RadioButton) findViewById(R.id.rb_isPieceObligatoire_fp_oui);
        rbFpISPersonnePhysiqueNon = (RadioButton) findViewById(R.id.rb_isPieceObligatoire_fp_non);
        rbFpIsTmOnOui = (RadioButton) findViewById(R.id.rb_isTmOn_oui);
        rbFpIsTmOnNon = (RadioButton) findViewById(R.id.rb_isTmOn_non);

        annulerButton = (Button) findViewById(R.id.btn_clean);
        deleteButton = (Button) findViewById(R.id.btn_delete_type_membre);
        deleteButton.setVisibility(View.GONE);
        addButton = (Button) findViewById(R.id.btn_save_type_membre);

        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });



        if (action_to_affect){
            headerEAVTextView.setText("Mise à jour du type de membre");
            addButton.setText("MODIFIER");
            deleteButton.setVisibility(View.VISIBLE);
            annulerButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirmDelete();
                }
            });
        }
        /*else{
            headerEAVTextView.setText("Mise à jour de la pièce");
            addButton.setText("MODIFIER");
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirmDelete();
                }
            });
        }*/


        //cirLoginButton
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                        addEAV();

                } else {
                    Toast.makeText(CreateTypeMembreOf.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });


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
    private class FetchPieceDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(CreateTypeMembreOf.this);
            pDialog.setMessage("\n" +
                    "Chargement des détails du type de membre . SVP veuillez patienter...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_TYPE_MEMBRE_ID, eavId);
//            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
//                    BASE_URL + "get_piece_of_details.php", "GET", httpParams);

            JSONObject jsonObject =(action_to_affect)?
                    httpJsonParser.makeHttpRequest(
                            BASE_URL + "get_type_membre_details.php", "GET", httpParams)
                    :
                    httpJsonParser.makeHttpRequest(
                            BASE_URL + "get_piece_cx_details.php", "GET", httpParams);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject eav;
                if (success == 1) {
                    //Parse the JSON response
                    eav = jsonObject.getJSONObject(KEY_DATA);


//                    if (action_to_affect){
//
//                        FpCode = eav.getString(KEY_FC_CODE);
//                        FpLibelle = eav.getString(KEY_FC_LIBELLE);
//                        FcTypeMembre = eav.getString(KEY_FC_CAT_ADH);
                   // }else{
                        FpCode = eav.getString(KEY_FC_CODE);
                        FpLibelle = eav.getString(KEY_FC_LIBELLE);
                        FpIsPieceOblig = (eav.getString(KEY_FC_Is_PIECE_OBLIG).equals("P"))?true:false;
                        //FpIsPieceOblig = Boolean.parseBoolean(eav.getString(KEY_FC_Is_PIECE_OBLIG));
                        FpIsTmOn = Boolean.parseBoolean(eav.getString(KEY_FC_Is_TM_ON));
//
                    //}
//

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


//                    FpCodeEditText.setText(FpCode);
//                    FpLibelleEditText.setText(FpLibelle);
//if (action_to_affect){
//    FpCodeEditText.setText(FpCode);
//    FpLibelleEditText.setText(FpLibelle);
//}else{
    FpCodeEditText.setText(FpCode);
    FpLibelleEditText.setText(FpLibelle);
    rbFpIsPersonnePhysiqueOui.setChecked(FpIsPieceOblig);
    rbFpISPersonnePhysiqueNon.setChecked(!FpIsPieceOblig);
    rbFpIsTmOnOui.setChecked(FpIsTmOn);
    rbFpIsTmOnNon.setChecked(!FpIsTmOn);

//}

                }
            });
        }


    }

    /**
     * Displays an alert dialogue to confirm the deletion
     */
    private void confirmDelete() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                CreateTypeMembreOf.this);
        alertDialogBuilder.setMessage("Êtes-vous sûr de vouloir supprimer ce type de membre ?");
        alertDialogBuilder.setPositiveButton("Supprimer",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                            //If the user confirms deletion, execute DeleteMovieAsyncTask
                            new CreateTypeMembreOf.DeleteMovieAsyncTask().execute();
                        } else {
                            Toast.makeText(CreateTypeMembreOf.this,
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
     * AsyncTask to delete a movie
     */
    private class DeleteMovieAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(CreateTypeMembreOf.this);
            pDialog.setMessage("Suppression du type de membre. SVP veuillez patienter...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Set movie_id parameter in request
            httpParams.put(KEY_TYPE_MEMBRE_ID, eavId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "delete_type_membre.php", "POST", httpParams);
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
                        Toast.makeText(CreateTypeMembreOf.this,
                                "Type membre supprimé", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie deletion
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(CreateTypeMembreOf.this,
                                "\n" +
                                        "Une erreur s'est produite lors de la suppression du type de membre",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
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
        ArrayAdapter<String> spinnerGuichetAdapter = new ArrayAdapter<String>(CreateTypeMembreOf.this,
                android.R.layout.simple_spinner_item, lablesGuichet);

        // Drop down layout style - list view with radio button
        spinnerGuichetAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Creating adapter for spinner guichet
        ArrayAdapter<String> spinnerPieceAdapter = new ArrayAdapter<String>(CreateTypeMembreOf.this,
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
            pDialog = new ProgressDialog(CreateTypeMembreOf.this);
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
                if (rbFpIsPersonnePhysiqueOui.isChecked()) {
                    str = checked1?"Personne physique":"";

                }

                break;
            case R.id.rb_isPieceObligatoire_fp_non:
                if (rbFpISPersonnePhysiqueNon.isChecked()){
                    str = checked1?"Personne morale":"";

        }


                break;
            case R.id.rb_isTmOn_oui:
                if (rbFpIsTmOnOui.isChecked()) {
                    str = checked1?"Statut activé":"";

                }

                break;
            case R.id.rb_isTmOn_non:
                if (rbFpIsTmOnNon.isChecked()){
                    str = checked1?"Statut désactivé":"";

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

if (!STRING_EMPTY.equals(FpLibelleEditText.getText().toString()) &&
        !STRING_EMPTY.equals(FpCodeEditText.getText().toString()) &&
        (rbFpIsPersonnePhysiqueOui.isChecked() || rbFpISPersonnePhysiqueNon.isChecked() ) &&
        (rbFpIsTmOnOui.isChecked() || rbFpIsTmOnNon.isChecked() )
){


FpIsPieceOblig = rbFpIsPersonnePhysiqueOui.isChecked();

FpIsTmOn = rbFpIsTmOnOui.isChecked();
    FpLibelle = FpLibelleEditText.getText().toString();
    FpCode = FpCodeEditText.getText().toString();
        new AddEAVAsyncTask().execute();


        } else {
            Toast.makeText(CreateTypeMembreOf.this,
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
            pDialog = new ProgressDialog(CreateTypeMembreOf.this);
            if (!action_to_affect){
                pDialog.setMessage("Enregistrement du type de membre. Veuillez patienter...");
            }else{
                pDialog.setMessage("Mise à jour du type de membre. Veuillez patienter...");
            }

            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_FC_CAISSE_ID, String.valueOf(MyData.CAISSE_ID)); // A modifier
            //httpParams.put(KEY_FC_GUICHET_ID, String.valueOf(guichetID));
            httpParams.put(KEY_FC_GUICHET_ID, String.valueOf(guichetID)); //I fix it to 0 to indicate that it's only for caisse
            //httpParams.put(KEY_FC_PIECE_ID, String.valueOf(pieceID)); // A modifier
            httpParams.put(KEY_FC_PIECE_ID, String.valueOf(eavId)); // A modifier

            httpParams.put(KEY_FC_LIBELLE, FpLibelle);
            httpParams.put(KEY_FC_CODE, FpCode);
            httpParams.put(KEY_FC_Is_PIECE_OBLIG, String.valueOf(FpIsPieceOblig));
            httpParams.put(KEY_FC_Is_TM_ON, String.valueOf(FpIsTmOn));

           // httpParams.put(KEY_FC_CAT_ADH, String.valueOf(guichetID)); // A modifier
            httpParams.put(KEY_FC_CAT_ADH, String.valueOf(FcTypeMembre));
            httpParams.put(KEY_TYPE_MEMBRE_ID, String.valueOf(eavId)); // A modifier car les noms sont un peu inversés

//            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
//                    BASE_URL + "add_piece_cx.php", "POST", httpParams);

            JSONObject jsonObject =(!action_to_affect)?
                    httpJsonParser.makeHttpRequest(
                            BASE_URL + "add_type_membre.php", "POST", httpParams)
                    :
                    httpJsonParser.makeHttpRequest(
                            BASE_URL + "update_type_membre.php", "POST", httpParams);
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
                        Toast.makeText(CreateTypeMembreOf.this,
                                "Type de membre Ajouté", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CreateTypeMembreOf.this,
                                "Some error occurred while adding Type membre",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}