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
package com.example.binumtontine.activity;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.JRSpinner;
import com.example.binumtontine.R;
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

public class PlageDataCAP extends AppCompatActivity implements  SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    /*Begin*/
         //to fetchProduitList by caisse and guichet ID
    private static final String KEY_EV_CAISSE_ID = "ev_caisse_id";
    private static final String KEY_EV_GUICHET_ID = "ev_gx_numero";
    /* end*/
    private static final String KEY_CAP_ID = "CapNumero";
//    private static final String KEY_CAP_ID = "CTP_numero";

    private static final String KEY_EAV_LIBELLE = "ev_libelle";

    private static final String KEY_CV_PRODUIT = "CvProduit";
    private static final String KEY_CV_MEMBRE = "CvMembre";
    private static final String KEY_CV_GUICHET = "CvGuichet";
    private static final String KEY_CV_NUM_DOSSIER = "CvNumDossier";
    private static final String KEY_CV_MT_SOLDE = "CvMtSolde";
    private static final String KEY_CV_USER_CREE = "CvUserCree";
    private static final String KEY_ADHERENT_NUM_DOSSIER = "CvNumDossier";

    /*Param for get extra*/
    private static final String KEY_ADHERENT_ID = "IpMembre";
    private static final String KEY_ADHERENT_NOM = "AdNom";
    private static final String KEY_ADHERENT_PRENOM = "AdPrenom";
    private static final String KEY_ADHERENT_NUM_MANUEL = "AdNumManuel";
    private static final String KEY_ADHERENT_CODE = "AdCode";
    private static final String KEY_PD_NUMERO = "PdNumero";
    private static final String KEY_PD_NATURE = "PdNature";
    private static final String KEY_PD_VAL_TAUX = "PdValTaux" ;
    private static final String KEY_PD_VAL_DE = "PdValDe" ;
    private static final String KEY_PD_VAL_A = "PdValA";
    private static final String KEY_PD_BASE ="PdBase" ;
    private static final String KEY_PD_PRODUIT ="PdProduit" ;


    private static String STRING_EMPTY = "";

    private EditText valeurEditText;
    private EditText valeurDebutEditText;
    private EditText valeurFinEditText;
    private EditText baseEditText;
   // public static ArrayList<ModelPlageData> plageDataListCTP;

    private String guichetId;
    private String valeur;
    private String valeurDebut;
    private String valeurFin;
    private String base;
    private String guichetDenomination;

    private RadioButton rbEtTypTxInterFixe;
    private RadioButton rbEtTypTxInterTaux;
    private RadioButton rbEtTypTxInterPlageFixe;
    private RadioButton rbEtTypTxInterPlageTaux;


    /* manage spinner*/

    private TextView tvGuichetDenomination;
    private TextView tv_type_frais;

    /*end manage*/

    private Button addButton;
    private Button annulerButton;
    private int success;
    private ProgressDialog pDialogInitialisationCaisseGuichet;
    private TextInputLayout ET_input_layout_ValeurTaluxInteretEAT;
    private LinearLayout LL_blk_EtPlageTxInter;
    private LinearLayout LL_blk_EtValeur;
    private String EtTypTxInter="";
    private static final String  PdTypeData="CAP";
    private JRSpinner mySpinnerBaseTxTIV; // a revoir le get
    private String PdNature;
    private String PdValTaux;
    private String PdValDe;
    private String PdValA;
    private String PdBase;
    private String PdProduit;
    private ProgressDialog pDialog;
    private Button deleteButton;
    public static int what_to_do=0;
    private String plageDataId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plage_data);


        //guichetId = String.valueOf(MyData.GUICHET_ID);
        guichetDenomination = MyData.GUICHET_NAME;
        Intent intent = getIntent();
        plageDataId = intent.getStringExtra(KEY_CAP_ID);
      //  plageDataListCTP = new ArrayList<>();

        LL_blk_EtValeur = (LinearLayout) findViewById(R.id.input_layout_ValeurTauxInteretEAT);
        LL_blk_EtValeur.setVisibility(View.GONE);
        valeurEditText = (EditText) findViewById(R.id.input_txt_ValeurTauxInteretEAT);
        valeurDebutEditText = (EditText) findViewById(R.id.txt_EtValTxInterFrom);
        valeurFinEditText = (EditText) findViewById(R.id.txt_EtValTxInterTo);
        baseEditText = (EditText) findViewById(R.id.input_txt_PdMtMinimum);
        rbEtTypTxInterFixe = (RadioButton) findViewById(R.id.rb_EtTypTxInterFixe);
        tv_type_frais = (TextView) findViewById(R.id.tv_type_frais_plage_data_body);
        tv_type_frais.setText(MyData.TYPE_DE_FRAIS_PLAGE_DATA);

        rbEtTypTxInterTaux = (RadioButton) findViewById(R.id.rb_EtTypTxInterTaux);
        rbEtTypTxInterPlageFixe = (RadioButton) findViewById(R.id.rb_EtTypTxInterPlageFixe);
        rbEtTypTxInterPlageTaux = (RadioButton) findViewById(R.id.rb_EtTypTxInterPlageTaux);
        LL_blk_EtPlageTxInter = (LinearLayout) findViewById(R.id.blk_EtPlageTxInter);
        ET_input_layout_ValeurTaluxInteretEAT = (TextInputLayout) findViewById(R.id.input_layout_BaseTauxAPreleveCpteEAV);
        rbEtTypTxInterFixe.performClick(); // le positionner en dessous des autres

        mySpinnerBaseTxTIV = (JRSpinner)findViewById(R.id.spn_my_spinner_base_taux);

        mySpinnerBaseTxTIV.setItems(getResources().getStringArray(R.array.array_base_taux_cpte_courant)); //this is important, you must set it to set the item list
        mySpinnerBaseTxTIV.setTitle("Sélectionner la base du taux"); //change title of spinner-dialog programmatically
        mySpinnerBaseTxTIV.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically

        mySpinnerBaseTxTIV.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position
                //  cxLocalite = mySpinnerLocalite.getText().toString();
                // Log.d("iddddddd***",caisseLocalite);
            }
        });

       if (what_to_do==3 || what_to_do==2){
           new FetchCapCcDetailsAsyncTask().execute();
       }
        addButton = (Button) findViewById(R.id.btn_save_plage_data);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(PlageDataCAP.this,
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
                    Toast.makeText(PlageDataCAP.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

        deleteButton = (Button) findViewById(R.id.btn_delete_plage);
        if (what_to_do==3|| what_to_do==2 ){
            deleteButton.setVisibility(View.VISIBLE);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                        updatePlageData();

                    } else {
                        Toast.makeText(PlageDataCAP.this,
                                "Impossible de se connecter à Internet",
                                Toast.LENGTH_LONG).show();

                    }

                }
            });
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                    confirmDelete();

            }
        });




    }

    /**
     * Checks whether all files are filled. If so then calls UpdatePlageDataAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void updatePlageData() {


        if (!STRING_EMPTY.equals(valeurEditText.getText().toString()))
        {


            valeur = valeurEditText.getText().toString();
            valeurDebut = valeurDebutEditText.getText().toString();
            valeurFin = valeurFinEditText.getText().toString();
            // base = baseEditText.getText().toString();
            base = mySpinnerBaseTxTIV.getText().toString();


            new UpdatePlageDataAsyncTask().execute();
        } else {
            Toast.makeText(PlageDataCAP.this,
                    "One or more fields left empty!",
                    Toast.LENGTH_LONG).show();

        }


    }
    /**
     * AsyncTask for updating a plage data details
     */

    private class UpdatePlageDataAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(PlageDataCAP.this);
            pDialog.setMessage("Updating Plage data. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            if (ListPlageDataCAPActivity.IS_TO_CREATE_OR_TO_UPDATE){
                updatePlageForCreate();
            }else{
                updatePlageForUpdate();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(PlageDataCAP.this,
                                "Plage mis à jour !", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(PlageDataCAP.this,
                                "Some error occurred while updating",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    /**
     * Displays an alert dialogue to confirm the deletion
     */
    private void confirmDelete() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                PlageDataCAP.this);
        alertDialogBuilder.setMessage("Voulez-vous vraiment supprimer cette plage ?");
        alertDialogBuilder.setPositiveButton("Supprimer",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                            //If the user confirms deletion, execute DeletePlageDataAsyncTask


                            if (ListPlageDataCAPActivity.IS_TO_CREATE_OR_TO_UPDATE){
//                                CreateProduitEAV.plageDataListCTP.remove(Integer.parseInt(plageDataId));

                                Toast.makeText(PlageDataCAP.this,
                                        "Fonctionnalité à prendre en compte dans la prochaine version",
                                        Toast.LENGTH_LONG).show();
                            }else{
                                new DeletePlageDataAsyncTask().execute();
                            }
                        } else {
                            Toast.makeText(PlageDataCAP.this,
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
    private class DeletePlageDataAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(PlageDataCAP.this);
            pDialog.setMessage("Suppression de la plage. SVP, patientez...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Set movie_id parameter in request
            httpParams.put(KEY_PD_NUMERO, plageDataId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "delete_plage_data_tiv.php", "POST", httpParams);
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
                        Toast.makeText(PlageDataCAP.this,
                                "Plage supprimée", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie deletion
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(PlageDataCAP.this,
                                "Une erreur s'est produite lors de la suppression de la plage",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }


    /**
     * Fetches single plage data details from the server
     */
    private class FetchCapCcDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(PlageDataCAP.this);
            pDialog.setMessage("Chargement des détails de la plage. SVP, patientez...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            if(what_to_do==2){
                fetchPlageForCreate();
            }else{
                HttpJsonParser httpJsonParser = new HttpJsonParser();
                Map<String, String> httpParams = new HashMap<>();
                httpParams.put(KEY_PD_NUMERO, plageDataId);
                JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                        BASE_URL + "get_plage_data_tiv_details.php", "GET", httpParams);
                //le fichier get_plage_data_tiv_details.php est utilisable pour l'obtention d'une plage donnée dans la table plage data
                //ceci, indépendamment du produit
                try {
                    int success = jsonObject.getInt(KEY_SUCCESS);
                    JSONObject plageData;
                    if (success == 1) {
                        //Parse the JSON response
                        plageData = jsonObject.getJSONObject(KEY_DATA);

                        EtTypTxInter = plageData.getString(KEY_PD_NATURE);
                        valeur = plageData.getString(KEY_PD_VAL_TAUX);
                        valeurDebut = plageData.getString(KEY_PD_VAL_DE);
                        valeurFin = plageData.getString(KEY_PD_VAL_A);
                        base = plageData.getString(KEY_PD_BASE);



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    //Populate the Edit Texts once the network activity is finished executing


                    valeurEditText.setText(valeur);
                    valeurDebutEditText.setText(valeurDebut);
                    valeurFinEditText.setText(valeurFin);
//                    baseEditText.setText(base);
                    mySpinnerBaseTxTIV.setText(base);
                    if (EtTypTxInter.equals("F")){
                        rbEtTypTxInterPlageFixe.setChecked(true);
                        onRadioButtonClicked(rbEtTypTxInterPlageFixe);
                    }else if (EtTypTxInter.equals("P")){
                        rbEtTypTxInterPlageTaux.setChecked(true);
                        onRadioButtonClicked(rbEtTypTxInterPlageTaux);
                    }

                }
            });
        }


    }


    public void onRadioButtonClicked(View view) {
        boolean checked1 = ((RadioButton) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.rb_EtTypTxInterFixe:
                if (rbEtTypTxInterFixe.isChecked()) {

                    //EtNaturePas ="F";
                    //ET_input_layout_ValeurTaluxInteretEAT = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    LL_blk_EtPlageTxInter.setVisibility(View.GONE);
                    ET_input_layout_ValeurTaluxInteretEAT.setVisibility(View.GONE);
                    //layout_TauxAPreleveCpteEAV.setVisibility(View.VISIBLE);

                }

                break;
            case R.id.rb_EtTypTxInterTaux:
                if (rbEtTypTxInterTaux.isChecked()){
                    LL_blk_EtPlageTxInter.setVisibility(View.GONE);
                    ET_input_layout_ValeurTaluxInteretEAT.setVisibility(View.VISIBLE);
                   // EtNaturePas ="S";
               /*     ET_input_layout_ValeurTaluxInteretEAT = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    ev_typ_fr_agios ="T";
                    LL_blk_EtPlageTxInter.setVisibility(View.INVISIBLE);
                layout_TauxAPreleveCpteEAV.setVisibility(View.VISIBLE);
                */
                }


                break;
            case R.id.rb_EtTypTxInterPlageFixe:
                if (rbEtTypTxInterPlageFixe.isChecked()) {
                    LL_blk_EtPlageTxInter.setVisibility(View.VISIBLE);
                    LL_blk_EtValeur.setVisibility(View.VISIBLE);
                    ET_input_layout_ValeurTaluxInteretEAT.setVisibility(View.GONE);

                    EtTypTxInter ="F";
                    /*
                    ET_input_layout_ValeurTaluxInteretEAT = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);

                    LL_blk_EtPlageTxInter.setVisibility(View.VISIBLE);
                    layout_TauxAPreleveCpteEAV.setVisibility(View.INVISIBLE);
                    */
                }
                break;
            case R.id.rb_EtTypTxInterPlageTaux:
                if (rbEtTypTxInterPlageTaux.isChecked()) {
                    LL_blk_EtValeur.setVisibility(View.VISIBLE);
                    LL_blk_EtPlageTxInter.setVisibility(View.VISIBLE);
                    ET_input_layout_ValeurTaluxInteretEAT.setVisibility(View.VISIBLE);
                    EtTypTxInter ="P";
                    /*
                    ET_input_layout_ValeurTaluxInteretEAT = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    ev_typ_fr_agios ="P";
                    LL_blk_EtPlageTxInter.setVisibility(View.VISIBLE);
                    layout_TauxAPreleveCpteEAV.setVisibility(View.INVISIBLE);
                    */
                }
                break;
        }
        // Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }


    /**
     * Checks whether all files are filled. If so then calls InitialisationCaisseGuichetAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void initialisationCaisseGuichet() {
        if (!STRING_EMPTY.equals(valeurEditText.getText().toString())
                ) {


                valeur = valeurEditText.getText().toString();
                valeurDebut = valeurDebutEditText.getText().toString();
                valeurFin = valeurFinEditText.getText().toString();
               // base = baseEditText.getText().toString();
            base = mySpinnerBaseTxTIV.getText().toString();

                new InitialisationCaisseGuichetAsyncTask().execute();


        } else {
            Toast.makeText(PlageDataCAP.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();

        }


    }

    private void savePlageForCreate(){
        ModelPlageData maPlage = new ModelPlageData(PdTypeData,EtTypTxInter,
                valeur,valeurDebut,valeurFin,base,"");
        CreateProduitCpteCourant.plageDataListCAP.add(maPlage);
        try {
            //success = jsonObject.getInt(KEY_SUCCESS);
            success = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updatePlageForCreate(){
        ModelPlageData maPlage = new ModelPlageData(PdTypeData,EtTypTxInter,
                valeur,valeurDebut,valeurFin,base,"");
        //CreateProduitEAV.plageDataListCTP.add(maPlage);
        CreateProduitCpteCourant.plageDataListCAP.set(Integer.parseInt(plageDataId),maPlage);
        try {
            //success = jsonObject.getInt(KEY_SUCCESS);
            success = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void fetchPlageForCreate(){
            EtTypTxInter = CreateProduitCpteCourant.plageDataListCAP.get(Integer.parseInt(plageDataId)).getPdNature();
        valeur = CreateProduitCpteCourant.plageDataListCAP.get(Integer.parseInt(plageDataId)).getPdValTaux();
        valeurDebut = CreateProduitCpteCourant.plageDataListCAP.get(Integer.parseInt(plageDataId)).getPdValDe();
        valeurFin = CreateProduitCpteCourant.plageDataListCAP.get(Integer.parseInt(plageDataId)).getPdValA();
        base = CreateProduitCpteCourant.plageDataListCAP.get(Integer.parseInt(plageDataId)).getPdBase();

        try {
            //success = jsonObject.getInt(KEY_SUCCESS);
            success = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void savePlageForUpdate(){

            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
           // httpParams.put(KEY_CAP_ID, uxGuichetId);

            httpParams.put(KEY_PD_NATURE, EtTypTxInter);
            httpParams.put(KEY_PD_VAL_TAUX, valeur);
            httpParams.put(KEY_PD_VAL_DE, valeurDebut);
            httpParams.put(KEY_PD_VAL_A, valeurFin);
            httpParams.put(KEY_PD_BASE, base);
            httpParams.put(KEY_PD_PRODUIT, UpdateProduitCpteCourant.cpteCourantId);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_cap_plage_data.php", "POST", httpParams);



//        ModelPlageData maPlage = new ModelPlageData(PdTypeData,EtTypTxInter,
//                valeur,valeurDebut,valeurFin,base,"");
//        CreateProduitEAV.plageDataListCTP.add(maPlage);
        try {
            success = jsonObject.getInt(KEY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updatePlageForUpdate(){

            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters

            httpParams.put(KEY_PD_NATURE, EtTypTxInter);
            httpParams.put(KEY_PD_VAL_TAUX, valeur);
            httpParams.put(KEY_PD_VAL_DE, valeurDebut);
            httpParams.put(KEY_PD_VAL_A, valeurFin);
            httpParams.put(KEY_PD_BASE, base);
            httpParams.put(KEY_PD_NUMERO, plageDataId);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "update_tiv_plage_data.php", "POST", httpParams); //Idem pour les plages CAP

        try {
            success = jsonObject.getInt(KEY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * AsyncTask for adding a compte eav
     */
    private class InitialisationCaisseGuichetAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialogInitialisationCaisseGuichet = new ProgressDialog(PlageDataCAP.this);
            pDialogInitialisationCaisseGuichet.setMessage("Enregistrement plage. Veuillez patienter...");
            pDialogInitialisationCaisseGuichet.setIndeterminate(false);
            pDialogInitialisationCaisseGuichet.setCancelable(false);
            pDialogInitialisationCaisseGuichet.show();
        }

        @Override
        protected String doInBackground(String... params) {
            /*
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
           // httpParams.put(KEY_CAP_ID, uxGuichetId);

            httpParams.put(KEY_CV_GUICHET, String.valueOf(MyData.GUICHET_ID));
            httpParams.put(KEY_CV_MT_SOLDE, valeur);
            httpParams.put(KEY_CV_USER_CREE, String.valueOf(MyData.USER_ID));

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_eav_adherent.php", "POST", httpParams);
            */


           if (ListPlageDataCAPActivity.IS_TO_CREATE_OR_TO_UPDATE){
               savePlageForCreate();
           }else{
               savePlageForUpdate();
           }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialogInitialisationCaisseGuichet.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(PlageDataCAP.this,
                                "Plage enregistrée", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(PlageDataCAP.this,
                                "Une erreur est survenue lors de l'enregistrement de la plage",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}