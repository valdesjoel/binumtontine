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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.binumtontine.JRSpinner;
import com.example.binumtontine.R;
import com.example.binumtontine.activity.CreateProduitCredit;
import com.example.binumtontine.activity.ListPlageDataFCXActivity;
import com.example.binumtontine.activity.adherent.ModelPlageData;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateWorkflowGx extends AppCompatActivity implements  SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    /*Begin*/
    private static final String KEY_CvgNumero = "CvgNumero";
    private static final String KEY_CvgCaisse = "CvgCaisse";
    private static final String KEY_CvgGuichet = "CvgGuichet";
    private static final String KEY_CvgTypeOper = "CvgTypeOper";
    private static final String KEY_CvgTypeUsrSaisie = "CvgTypeUsrSaisie";
    private static final String KEY_CvgTypeUsrControl = "CvgTypeUsrControl";
    private static final String KEY_CvgTypeUsrValid = "CvgTypeUsrValid";
    private static final String KEY_CvgIsChaineON = "CvgIsChaineON";

    private String st_CvgNumero;

    private String st_CvgTypeUsrSaisie;
    private String st_CvgTypeUsrControl;
    private String st_CvgTypeUsrValid;
    private String st_CvgIsChaineON;
//
    private static String STRING_EMPTY = "";

    public static String CvgTypeOper="DEC";

//    /*end manage*/
//
    private Button addButton;
    private Button annulerButton;
    private int success;
    private ProgressDialog pDialogInitialisationCaisseGuichet;
    private TextView headerLayoutWorkflow;
//
    private JRSpinner mySpinnerCvgTypeUsrSaisie; // a revoir le get
    private JRSpinner mySpinnerCvgTypeUsrControl; // a revoir le get
    private JRSpinner mySpinnerCvgTypeUsrValid; // a revoir le get
    private SwitchCompat SwitchCvgIsChaineON; // a revoir le get
    private SwitchCompat SwitchCvgTypeUsrControlOblig;

    private Button deleteButton;
    public static Boolean action_to_update=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workflow_gx);
        if (action_to_update){
            Intent intent = getIntent();
            st_CvgNumero = intent.getStringExtra(KEY_CvgNumero);
                    new FetchCVGDetailsAsyncTask().execute();
        }



        headerLayoutWorkflow = (TextView)findViewById(R.id.header_initialisation_caisse_guichet);
        headerLayoutWorkflow.setText(CvgTypeOper);
        mySpinnerCvgTypeUsrSaisie = (JRSpinner)findViewById(R.id.spnCvgTypeUsrSaisie);
        mySpinnerCvgTypeUsrControl = (JRSpinner)findViewById(R.id.spnCvgTypeUsrControl);
        mySpinnerCvgTypeUsrValid = (JRSpinner)findViewById(R.id.spnCvgTypeUsrValid);
        SwitchCvgIsChaineON = (SwitchCompat) findViewById(R.id.SwitchCvgIsChaineON);
        SwitchCvgTypeUsrControlOblig = (SwitchCompat) findViewById(R.id.SwitchCvgTypeUsrControlOblig);

        mySpinnerCvgTypeUsrSaisie.setItems(getResources().getStringArray(R.array.array_all_profil)); //this is important, you must set it to set the item list
        mySpinnerCvgTypeUsrSaisie.setTitle("Sélectionner le profil concerné"); //change title of spinner-dialog programmatically
        mySpinnerCvgTypeUsrSaisie.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically

        mySpinnerCvgTypeUsrSaisie.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position
                //  cxLocalite = mySpinnerLocalite.getText().toString();
                // Log.d("iddddddd***",caisseLocalite);
            }
        });
        mySpinnerCvgTypeUsrControl.setItems(getResources().getStringArray(R.array.array_all_profil)); //this is important, you must set it to set the item list
        mySpinnerCvgTypeUsrControl.setTitle("Sélectionner le profil concerné"); //change title of spinner-dialog programmatically
        mySpinnerCvgTypeUsrControl.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically

        mySpinnerCvgTypeUsrControl.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position
                //  cxLocalite = mySpinnerLocalite.getText().toString();
                // Log.d("iddddddd***",caisseLocalite);
            }
        });
        mySpinnerCvgTypeUsrValid.setItems(getResources().getStringArray(R.array.array_all_profil)); //this is important, you must set it to set the item list
        mySpinnerCvgTypeUsrValid.setTitle("Sélectionner le profil concerné"); //change title of spinner-dialog programmatically
        mySpinnerCvgTypeUsrValid.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically

        mySpinnerCvgTypeUsrValid.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position
                //  cxLocalite = mySpinnerLocalite.getText().toString();
                // Log.d("iddddddd***",caisseLocalite);
            }
        });
//
        addButton = (Button) findViewById(R.id.btn_save_workflow);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(CreateWorkflowGx.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    initialisationCaisseGuichet();
                } else {
                    Toast.makeText(CreateWorkflowGx.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

        deleteButton = (Button) findViewById(R.id.btn_delete_plage);
//        if (what_to_do==3|| what_to_do==2 ){
//            deleteButton.setVisibility(View.VISIBLE);
//            addButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
//                        updatePlageData();
//
//                    } else {
//                        Toast.makeText(CreateWorkflowGx.this,
//                                "Impossible de se connecter à Internet",
//                                Toast.LENGTH_LONG).show();
//
//                    }
//
//                }
//            });
//        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                    confirmDelete();

            }
        });




    }


    /**
     * Fetches single movie details from the server
     */
    private class FetchCVGDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialogInitialisationCaisseGuichet = new ProgressDialog(CreateWorkflowGx.this);
            pDialogInitialisationCaisseGuichet.setMessage("Chargement des détails...");
            pDialogInitialisationCaisseGuichet.setIndeterminate(false);
            pDialogInitialisationCaisseGuichet.setCancelable(false);
            pDialogInitialisationCaisseGuichet.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_CvgNumero, st_CvgNumero);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_ChaineValidGuichet_details.php", "GET", httpParams);
            Log.e("Response: ", "> " + jsonObject);
            try {
//                int success = jsonObject.getInt(KEY_SUCCESS);
              success = 1;
                JSONObject ChaineValidGuichet;
//                if (success == 1) {
                    //Parse the JSON response
//                    ChaineValidGuichet = jsonObject.getJSONObject(KEY_DATA);
//                    Log.e("ChaineValidGuichet: ", "> " + ChaineValidGuichet);
                    st_CvgTypeUsrSaisie = jsonObject.getString(KEY_CvgTypeUsrSaisie);
                    st_CvgTypeUsrControl = jsonObject.getString(KEY_CvgTypeUsrControl);
                    st_CvgTypeUsrValid = jsonObject.getString(KEY_CvgTypeUsrValid);
                    st_CvgIsChaineON = jsonObject.getString(KEY_CvgIsChaineON);

//                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialogInitialisationCaisseGuichet.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    //Populate the Edit Texts once the network activity is finished executing


                    mySpinnerCvgTypeUsrSaisie.setText(st_CvgTypeUsrSaisie);
                    mySpinnerCvgTypeUsrControl.setText(st_CvgTypeUsrControl);
                    mySpinnerCvgTypeUsrValid.setText(st_CvgTypeUsrValid);
                    SwitchCvgIsChaineON.setChecked(Boolean.parseBoolean(st_CvgIsChaineON));



                }
            });
        }


    }


    /**
     * Checks whether all files are filled. If so then calls UpdatePlageDataAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
//    private void updatePlageData() {
//        Double h1, h2, h3;
//
//        h1 = Double.valueOf(valeurEditText.getText().toString().replaceAll(",", "").trim());
//        valeurEditText.setText(h1+"");
//        h2 = Double.valueOf(valeurDebutEditText.getText().toString().replaceAll(",", "").trim());
//        valeurDebutEditText.setText(h2+"");
//        h3 = Double.valueOf(valeurFinEditText.getText().toString().replaceAll(",", "").trim());
//        valeurFinEditText.setText(h3+"");
//
////        valeurEditText.setText(valeurEditText.getText().toString().replaceAll(",", "").trim());
////        valeurDebutEditText.setText(valeurDebutEditText.getText().toString().replaceAll(",", "").trim());
////        valeurFinEditText.setText(valeurFinEditText.getText().toString().replaceAll(",", "").trim());
//        if (!STRING_EMPTY.equals(valeurEditText.getText().toString())&&
//                !STRING_EMPTY.equals(valeurDebutEditText.getText().toString())&&
//                !STRING_EMPTY.equals(valeurFinEditText.getText().toString())&&
//                (!STRING_EMPTY.equals(mySpinnerCvgTypeUsrSaisie.getText().toString()) || EtTypTxInter.equals("F") )) {
////            valeurEditText.setText(valeurEditText.getText().toString().replaceAll(",", "").trim());
////            JoMtDemarrEditText.setText(valueNew+"");
//
//            valeur = valeurEditText.getText().toString();
//            valeurDebut = valeurDebutEditText.getText().toString();
//            valeurFin = valeurFinEditText.getText().toString();
//            // base = baseEditText.getText().toString();
//            base = mySpinnerCvgTypeUsrSaisie.getText().toString();
//
//
//            new UpdatePlageDataAsyncTask().execute();
//        } else {
//            Toast.makeText(CreateWorkflowGx.this,
//                    "One or more fields left empty!",
//                    Toast.LENGTH_LONG).show();
//
//        }
//
//
//    }
    /**
     * AsyncTask for updating a plage data details
     */

//    private class UpdatePlageDataAsyncTask extends AsyncTask<String, String, String> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            //Display progress bar
//            pDialog = new ProgressDialog(CreateWorkflowGx.this);
//            pDialog.setMessage("Updating Plage data. Please wait...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(false);
//            pDialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//
//            if (ListPlageDataFCXActivity.IS_TO_CREATE_OR_TO_UPDATE){
//                updatePlageForCreate();
//            }else{
//                updatePlageForUpdate();
//            }
//            return null;
//        }
//
//        protected void onPostExecute(String result) {
//            pDialog.dismiss();
//            runOnUiThread(new Runnable() {
//                public void run() {
//                    if (success == 1) {
//                        //Display success message
//                        Toast.makeText(CreateWorkflowGx.this,
//                                "Plage Updated", Toast.LENGTH_LONG).show();
//                        Intent i = getIntent();
//                        //send result code 20 to notify about movie update
//                        setResult(20, i);
//                        finish();
//
//                    } else {
//                        Toast.makeText(CreateWorkflowGx.this,
//                                "Some error occurred while updating",
//                                Toast.LENGTH_LONG).show();
//
//                    }
//                }
//            });
//        }
//    }

    /**
     * Displays an alert dialogue to confirm the deletion
     */
    private void confirmDelete() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                CreateWorkflowGx.this);
        alertDialogBuilder.setMessage("Voulez-vous vraiment supprimer cette plage ?");
        alertDialogBuilder.setPositiveButton("Supprimer",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                            //If the user confirms deletion, execute DeletePlageDataAsyncTask


                            if (ListPlageDataFCXActivity.IS_TO_CREATE_OR_TO_UPDATE){
//                                CreateProduitEAV.plageDataListCTP.remove(Integer.parseInt(plageDataId));

                                Toast.makeText(CreateWorkflowGx.this,
                                        "Fonctionnalité à prendre en compte dans la prochaine version",
                                        Toast.LENGTH_LONG).show();
                            }else{
//                                new DeletePlageDataAsyncTask().execute();
                            }
                        } else {
                            Toast.makeText(CreateWorkflowGx.this,
                                    "Impossible de se connecter à Internet",
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                });

        alertDialogBuilder.setNegativeButton("Annuler", null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * AsyncTask to delete a plage data
     */
//    private class DeletePlageDataAsyncTask extends AsyncTask<String, String, String> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            //Display progress bar
//            pDialog = new ProgressDialog(CreateWorkflowGx.this);
//            pDialog.setMessage("Suppression de la plage. SVP, patientez...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(false);
//            pDialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            HttpJsonParser httpJsonParser = new HttpJsonParser();
//            Map<String, String> httpParams = new HashMap<>();
//            //Set movie_id parameter in request
//            httpParams.put(KEY_PD_NUMERO, plageDataId);
//            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
//                    BASE_URL + "delete_plage_data_tiv.php", "POST", httpParams);
//            try {
//                success = jsonObject.getInt(KEY_SUCCESS);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        protected void onPostExecute(String result) {
//            pDialog.dismiss();
//            runOnUiThread(new Runnable() {
//                public void run() {
//                    if (success == 1) {
//                        //Display success message
//                        Toast.makeText(CreateWorkflowGx.this,
//                                "Plage supprimée", Toast.LENGTH_LONG).show();
//                        Intent i = getIntent();
//                        //send result code 20 to notify about movie deletion
//                        setResult(20, i);
//                        finish();
//
//                    } else {
//                        Toast.makeText(CreateWorkflowGx.this,
//                                "Une erreur s'est produite lors de la suppression de la plage",
//                                Toast.LENGTH_LONG).show();
//
//                    }
//                }
//            });
//        }
//    }


    /**
     * Fetches single plage data details from the server
     */
//    private class FetchCrFCXDetailsAsyncTask extends AsyncTask<String, String, String> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            //Display progress bar
//            pDialog = new ProgressDialog(CreateWorkflowGx.this);
//            pDialog.setMessage("Chargement des détails de la plage. SVP, patientez...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(false);
//            pDialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            if(what_to_do==2){
//                fetchPlageForCreate();
//            }else{
//                HttpJsonParser httpJsonParser = new HttpJsonParser();
//                Map<String, String> httpParams = new HashMap<>();
//                httpParams.put(KEY_PD_NUMERO, plageDataId);
//                JSONObject jsonObject = httpJsonParser.makeHttpRequest(
//                        BASE_URL + "get_plage_data_tiv_details.php", "GET", httpParams);
//                    //le fichier get_plage_data_tiv_details.php est utilisable pour l'obtention d'une plage donnée dans la table plage data
//                    //ceci, indépendamment du produit
//                try {
//                    int success = jsonObject.getInt(KEY_SUCCESS);
//                    JSONObject plageData;
//                    if (success == 1) {
//                        //Parse the JSON response
//                        plageData = jsonObject.getJSONObject(KEY_DATA);
//
//                        EtTypTxInter = plageData.getString(KEY_PD_NATURE);
//                        valeur = plageData.getString(KEY_PD_VAL_TAUX);
//                        valeurDebut = plageData.getString(KEY_PD_VAL_DE);
//                        valeurFin = plageData.getString(KEY_PD_VAL_A);
//                        base = plageData.getString(KEY_PD_BASE);
//
//
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            return null;
//        }
//
//        protected void onPostExecute(String result) {
//            pDialog.dismiss();
//            runOnUiThread(new Runnable() {
//                public void run() {
//                    //Populate the Edit Texts once the network activity is finished executing
//
//
//                    valeurEditText.setText(valeur);
//                    valeurDebutEditText.setText(valeurDebut);
//                    valeurFinEditText.setText(valeurFin);
////                    baseEditText.setText(base);
//                    mySpinnerCvgTypeUsrSaisie.setText(base);
//                    if (EtTypTxInter.equals("F")){
//                        rbEtTypTxInterPlageFixe.setChecked(true);
//                        onRadioButtonClicked(rbEtTypTxInterPlageFixe);
//                    }else if (EtTypTxInter.equals("P")){
//                        rbEtTypTxInterPlageTaux.setChecked(true);
//                        onRadioButtonClicked(rbEtTypTxInterPlageTaux);
//                    }
//
//                }
//            });
//        }
//
//
//    }


//    public void onRadioButtonClicked(View view) {
//        boolean checked1 = ((RadioButton) view).isChecked();
//        String str="";
//        // Check which checkbox was clicked
//        switch(view.getId()) {
//
//            case R.id.rb_EtTypTxInterFixe:
//                if (rbEtTypTxInterFixe.isChecked()) {
//
//                    //EtNaturePas ="F";
//                    //JRS_CvgTypeUsrSaisie = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
//                    LL_blk_EtPlageTxInter.setVisibility(View.GONE);
//                    JRS_CvgTypeUsrSaisie.setVisibility(View.GONE);
//                    //layout_TauxAPreleveCpteEAV.setVisibility(View.VISIBLE);
//
//                }
//
//                break;
//            case R.id.rb_EtTypTxInterTaux:
//                if (rbEtTypTxInterTaux.isChecked()){
//                    LL_blk_EtPlageTxInter.setVisibility(View.GONE);
//                    JRS_CvgTypeUsrSaisie.setVisibility(View.VISIBLE);
//                   // EtNaturePas ="S";
//               /*     JRS_CvgTypeUsrSaisie = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
//                    ev_typ_fr_agios ="T";
//                    LL_blk_EtPlageTxInter.setVisibility(View.INVISIBLE);
//                layout_TauxAPreleveCpteEAV.setVisibility(View.VISIBLE);
//                */
//                }
//
//
//                break;
//            case R.id.rb_EtTypTxInterPlageFixe:
//                if (rbEtTypTxInterPlageFixe.isChecked()) {
//                    LL_blk_EtPlageTxInter.setVisibility(View.VISIBLE);
//                    LL_blk_EtValeur.setVisibility(View.VISIBLE);
//                    JRS_CvgTypeUsrSaisie.setVisibility(View.GONE);
//
//                    EtTypTxInter ="F";
//                    /*
//                    JRS_CvgTypeUsrSaisie = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
//
//                    LL_blk_EtPlageTxInter.setVisibility(View.VISIBLE);
//                    layout_TauxAPreleveCpteEAV.setVisibility(View.INVISIBLE);
//                    */
//                }
//                break;
//            case R.id.rb_EtTypTxInterPlageTaux:
//                if (rbEtTypTxInterPlageTaux.isChecked()) {
//                    LL_blk_EtValeur.setVisibility(View.VISIBLE);
//                    LL_blk_EtPlageTxInter.setVisibility(View.VISIBLE);
//                    JRS_CvgTypeUsrSaisie.setVisibility(View.VISIBLE);
//                    EtTypTxInter ="P";
//                    /*
//                    JRS_CvgTypeUsrSaisie = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
//                    ev_typ_fr_agios ="P";
//                    LL_blk_EtPlageTxInter.setVisibility(View.VISIBLE);
//                    layout_TauxAPreleveCpteEAV.setVisibility(View.INVISIBLE);
//                    */
//                }
//                break;
//        }
//        // Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
//    }


    /**
     * Checks whether all files are filled. If so then calls InitialisationCaisseGuichetAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void initialisationCaisseGuichet() {

if (!STRING_EMPTY.equals(mySpinnerCvgTypeUsrSaisie.getText().toString())&&
        (!STRING_EMPTY.equals(mySpinnerCvgTypeUsrControl.getText().toString()) || !SwitchCvgTypeUsrControlOblig.isChecked())&&
                !STRING_EMPTY.equals(mySpinnerCvgTypeUsrValid.getText().toString())
//                (!STRING_EMPTY.equals(mySpinnerCvgTypeUsrSaisie.getText().toString()) || EtTypTxInter.equals("F") )
) {

    if ((mySpinnerCvgTypeUsrSaisie.getText().toString()).equals(mySpinnerCvgTypeUsrControl.getText().toString())
        || (mySpinnerCvgTypeUsrSaisie.getText().toString()).equals(mySpinnerCvgTypeUsrValid.getText().toString())
        || (mySpinnerCvgTypeUsrControl.getText().toString()).equals(mySpinnerCvgTypeUsrValid.getText().toString())){
            Toast.makeText(CreateWorkflowGx.this,
            "La même profil ne peut pas exister plusieurs fois dans la même chaîne!",
            Toast.LENGTH_LONG).show();
    }else{
        st_CvgTypeUsrSaisie = mySpinnerCvgTypeUsrSaisie.getText().toString();
        st_CvgTypeUsrControl = mySpinnerCvgTypeUsrControl.getText().toString();
        st_CvgTypeUsrValid = mySpinnerCvgTypeUsrValid.getText().toString();
        st_CvgIsChaineON = String.valueOf(SwitchCvgIsChaineON.isChecked());

        new InitialisationCaisseGuichetAsyncTask().execute();
        }



} else {
            Toast.makeText(CreateWorkflowGx.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();

        }


    }
//
//    private void savePlageForCreateFCX(){
//        ModelPlageData maPlage = new ModelPlageData(PdTypeData,EtTypTxInter,
//                valeur,valeurDebut,valeurFin,base,"");
//        CreateProduitCredit.plageDataListFCX.add(maPlage);
//        try {
//            //success = jsonObject.getInt(KEY_SUCCESS);
//            success = 1;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    private void updatePlageForCreate(){
//        ModelPlageData maPlage = new ModelPlageData(PdTypeData,EtTypTxInter,
//                valeur,valeurDebut,valeurFin,base,"");
//        //CreateProduitEAV.plageDataListCTP.add(maPlage);
//        CreateProduitCredit.plageDataListFCX.set(Integer.parseInt(plageDataId),maPlage);
//        try {
//            //success = jsonObject.getInt(KEY_SUCCESS);
//            success = 1;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    private void fetchPlageForCreate(){
//            EtTypTxInter = CreateProduitCredit.plageDataListFCX.get(Integer.parseInt(plageDataId)).getPdNature();
//        valeur = CreateProduitCredit.plageDataListFCX.get(Integer.parseInt(plageDataId)).getPdValTaux();
//        valeurDebut = CreateProduitCredit.plageDataListFCX.get(Integer.parseInt(plageDataId)).getPdValDe();
//        valeurFin = CreateProduitCredit.plageDataListFCX.get(Integer.parseInt(plageDataId)).getPdValA();
//        base = CreateProduitCredit.plageDataListFCX.get(Integer.parseInt(plageDataId)).getPdBase();
//
//        try {
//            //success = jsonObject.getInt(KEY_SUCCESS);
//            success = 1;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    private void savePlageForUpdateFCX(){
//
//            HttpJsonParser httpJsonParser = new HttpJsonParser();
//            Map<String, String> httpParams = new HashMap<>();
//            //Populating request parameters
//           // httpParams.put(KEY_FCX_ID, uxGuichetId);
//
//            httpParams.put(KEY_PD_NATURE, EtTypTxInter);
//            httpParams.put(KEY_PD_VAL_TAUX, valeur);
//            httpParams.put(KEY_PD_VAL_DE, valeurDebut);
//            httpParams.put(KEY_PD_VAL_A, valeurFin);
//            httpParams.put(KEY_PD_BASE, base);
//        httpParams.put(KEY_PD_TYPE_DATA, PdTypeData); //A mettre à jour pour les autres plages
//        httpParams.put(KEY_PD_PRODUIT, UpdateProduitCredit.creditId );
//
//            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
//                    BASE_URL + "add_plage_data.php", "POST", httpParams);
//        try {
//            success = jsonObject.getInt(KEY_SUCCESS);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    private void updatePlageForUpdate(){
//
//            HttpJsonParser httpJsonParser = new HttpJsonParser();
//            Map<String, String> httpParams = new HashMap<>();
//            //Populating request parameters
//
//            httpParams.put(KEY_PD_NATURE, EtTypTxInter);
//            httpParams.put(KEY_PD_VAL_TAUX, valeur);
//            httpParams.put(KEY_PD_VAL_DE, valeurDebut);
//            httpParams.put(KEY_PD_VAL_A, valeurFin);
//            httpParams.put(KEY_PD_BASE, base);
//            httpParams.put(KEY_PD_NUMERO, plageDataId);
//
//            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
//                    BASE_URL + "update_tiv_plage_data.php", "POST", httpParams); //Idem pour les plages FCX
//
//        try {
//            success = jsonObject.getInt(KEY_SUCCESS);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    /**
     * AsyncTask for adding a compte eav
     */
    private class InitialisationCaisseGuichetAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialogInitialisationCaisseGuichet = new ProgressDialog(CreateWorkflowGx.this);
            pDialogInitialisationCaisseGuichet.setMessage("Enregistrement du Workflow. Veuillez patienter...");
            pDialogInitialisationCaisseGuichet.setIndeterminate(false);
            pDialogInitialisationCaisseGuichet.setCancelable(false);
            pDialogInitialisationCaisseGuichet.show();
        }

        @Override
        protected String doInBackground(String... params) {

           if (CvgTypeOper.equals("DEC")){ //DEC => DECOUVERT
//               savePlageForCreateFCX();
           }else if (CvgTypeOper.equals("CRE")){ // CRE => CREDIT
//               savePlageForUpdateFCX();
           }else if (CvgTypeOper.equals("CRA")){ // CRA => Credit en Remboursement Anticipé
//               savePlageForUpdateFCX();
           }else if (CvgTypeOper.equals("FCX")){ // FCX => Facilité de caisse
//               savePlageForUpdateFCX();
           }else if (CvgTypeOper.equals("XXX")){ // XXX => Découvert Permanent
//               savePlageForUpdateFCX();
           }else if (CvgTypeOper.equals("XXX")){ // XXX => Modification Découvert
//               savePlageForUpdateFCX();
           }else if (CvgTypeOper.equals("XXX")){ // XXX => Re-échelonnement Echéances crédit
//               savePlageForUpdateFCX();
           }
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters

            httpParams.put(KEY_CvgCaisse, String.valueOf(MyData.CAISSE_ID));
            httpParams.put(KEY_CvgGuichet, String.valueOf(MyData.GUICHET_ID));
            httpParams.put(KEY_CvgTypeOper, CvgTypeOper);
            httpParams.put(KEY_CvgTypeUsrSaisie, st_CvgTypeUsrSaisie);
            httpParams.put(KEY_CvgTypeUsrControl, st_CvgTypeUsrControl);
        httpParams.put(KEY_CvgTypeUsrValid, st_CvgTypeUsrValid);
        httpParams.put(KEY_CvgIsChaineON, st_CvgIsChaineON );

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_ChaineValidGuichet.php", "POST", httpParams);
        try {
            success = jsonObject.getInt(KEY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialogInitialisationCaisseGuichet.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(CreateWorkflowGx.this,
                                "Workflow enregistré", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        ListWorkflowGuichet.setSubtitle = CvgTypeOper;
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CreateWorkflowGx.this,
                                "Une erreur est survenue lors de l'enregistrement du workflow\nVérifiez si cette chaîne n'existente pas déjà ",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}