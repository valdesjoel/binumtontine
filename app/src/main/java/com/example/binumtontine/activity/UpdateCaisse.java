package com.example.binumtontine.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.example.binumtontine.JRSpinner;
import com.example.binumtontine.R;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.hbb20.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UpdateCaisse extends AppCompatActivity implements View.OnClickListener, SERVER_ADDRESS {
    private static String STRING_EMPTY = "";
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";

    private static final String KEY_CX_DENOMINATION = "cx_denomination";
    private static final String KEY_CX_SIEGE = "cx_siege";
    private static final String KEY_CAISSE_ID = "cx_numero";
    private static final String KEY_CX_LOCALITE = "cx_localite";
    private static final String KEY_CX_NUM_AGREM = "cx_num_agrem";
    private static final String KEY_CX_DATE_AGREM = "cx_date_agrem";
    private static final String KEY_CX_DATE_DEBUT = "cx_date_debut";

    private static final String KEY_CxNumAgremCobac = "CxNumAgremCobac";
    private static final String KEY_CxDatAgremCobac = "CxDatAgremCobac";
    private static final String KEY_CxNumAgremCNC = "CxNumAgremCNC";
    private static final String KEY_CxDatAgremCNC = "CxDatAgremCNC";
    private static final String KEY_CX_ADRESSE = "cx_adresse";
    private static final String KEY_CX_TEL1 = "cx_tel1";
    private static final String KEY_CX_TEL2 = "cx_tel2";
    private static final String KEY_CX_TEL3 = "cx_tel3";
    private static final String KEY_CX_NOM_PCA = "cx_nom_pca";
    private static final String KEY_CX_NOM_DG = "cx_nom_dg";


    private static final String KEY_CxIsPrConsAdmPCA = "CxIsPrConsAdmPCA";
    private static final String KEY_CxIsPrComCredPCC = "CxIsPrComCredPCC";
    private static final String KEY_CxIsDirCredDC = "CxIsDirCredDC";
    private static final String KEY_CxIsAgentCredAC = "CxIsAgentCredAC";
    private static final String KEY_CxIsDirGenCxDG = "CxIsDirGenCxDG";
    private static final String KEY_CxIsMultiCpteCourByMemb = "CxIsMultiCpteCourByMemb";





    private String caisseId;
    private EditText cx_denominationEditText;
    private JRSpinner mySpinnerLocalite; //pour gérer le spinner contenant les localités
    private EditText cx_num_agremEditText;
    private EditText cx_date_agremEditText;
    private EditText cx_date_debutEditText;
    private EditText cx_adresseEditText;

    private EditText CxNumAgremCobac;
    private EditText CxDatAgremCobac;
    private EditText CxNumAgremCNC;
    private EditText CxDatAgremCNC;

    private EditText cx_siegeEditText;
    private String cxSiege;


    private CountryCodePicker ccp_phone1;
    private CountryCodePicker ccp_phone2;
    private CountryCodePicker ccp_phone3;
    private EditText editTextCarrierPhone1;
    private EditText editTextCarrierPhone2;
    private EditText editTextCarrierPhone3;

    private EditText cx_tel1EditText;
    private EditText cx_tel2EditText;
    private EditText cx_tel3EditText;
    private EditText cx_nom_pcaEditText;
    private EditText cx_nom_dgEditText;

    private String cxDenomination;
    private String cxLocalite;
    private String cx_num_agrem;
    private String cx_date_agrem;
    private String cx_date_debut;
    private String CxNumAgremCobac_st;
    private String CxDatAgremCobac_st;
    private String CxNumAgremCNC_st;
    private String CxDatAgremCNC_st;
    private String cx_adresse;
    private String cx_tel1;
    private String cx_tel2;
    private String cx_tel3;
    private String cx_nom_pca;
    private String cx_nom_dg;


    private SwitchCompat CxIsPrConsAdmPCA,CxIsPrComCredPCC,CxIsDirCredDC,CxIsAgentCredAC,CxIsDirGenCxDG, CxIsMultiCpteCourByMemb;
    private String bool_CxIsPrConsAdmPCA;
    private String bool_CxIsPrComCredPCC;
    private String bool_CxIsDirCredDC;
    private String bool_CxIsAgentCredAC;
    private String bool_CxIsDirGenCxDG;
    private String bool_CxIsMultiCpteCourByMemb;

    private Button deleteButton;
    private Button updateButton;
    private Button annulerButton;
    private int success;
    private ProgressDialog pDialog;
    private ProgressDialog pDialogFetchCaisseDetails;



    private DatePickerDialog cx_date_agrem_PickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date
    private DatePickerDialog cx_date_debut_PickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date

    private DatePickerDialog CxDatAgremCobacPickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date
    private DatePickerDialog CxDatAgremCNCPickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date
    private SimpleDateFormat dateFormatter; //propriété permettant de gérer le format de la date

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_caisses);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_caisse);
        setSupportActionBar(toolbar);
        setToolbarTitle();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        findViewsById();

        setDateTimeField();

        mySpinnerLocalite = (JRSpinner)findViewById(R.id.spn_my_spinner_localite_caisse);

        mySpinnerLocalite.setItems(getResources().getStringArray(R.array.array_localite)); //this is important, you must set it to set the item list
        mySpinnerLocalite.setTitle("Sélectionner une localité"); //change title of spinner-dialog programmatically
        mySpinnerLocalite.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically

        mySpinnerLocalite.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });




        cx_denominationEditText = (EditText) findViewById(R.id.input_denominationCx);
        cx_siegeEditText = (EditText) findViewById(R.id.input_siegeCx);
        mySpinnerLocalite = (JRSpinner) findViewById(R.id.spn_my_spinner_localite_caisse);
        cx_num_agremEditText = (EditText) findViewById(R.id.input_txt_num_agrement_cx);
        cx_date_agremEditText = (EditText) findViewById(R.id.input_txt_dateAgrementCx);

        CxNumAgremCobac = (EditText) findViewById(R.id.input_txt_CxNumAgremCobac);
        CxNumAgremCNC = (EditText) findViewById(R.id.input_txt_CxNumAgremCNC);

        cx_date_debutEditText = (EditText) findViewById(R.id.input_txt_dateDebutCx);
        cx_adresseEditText = (EditText) findViewById(R.id.input_txt_AdresseCx);
//        cx_tel1EditText = (EditText) findViewById(R.id.input_txt_Tel1_Cx);
//        cx_tel2EditText = (EditText) findViewById(R.id.input_txt_Tel2_Cx);
//        cx_tel3EditText = (EditText) findViewById(R.id.input_txt_Tel3_Cx);

        ccp_phone1 = (CountryCodePicker) findViewById(R.id.ccp_phone1);
        editTextCarrierPhone1 = (EditText) findViewById(R.id.editText_carrierPhone1);
        ccp_phone1.registerCarrierNumberEditText(editTextCarrierPhone1);


        ccp_phone2 = (CountryCodePicker) findViewById(R.id.ccp_phone2);
        editTextCarrierPhone2 = (EditText) findViewById(R.id.editText_carrierPhone2);
        ccp_phone2.registerCarrierNumberEditText(editTextCarrierPhone2);

        ccp_phone3 = (CountryCodePicker) findViewById(R.id.ccp_phone3);
        editTextCarrierPhone3 = (EditText) findViewById(R.id.editText_carrierPhone3);
        ccp_phone3.registerCarrierNumberEditText(editTextCarrierPhone3);



        cx_nom_pcaEditText = (EditText) findViewById(R.id.input_txt_NomPCA_Cx);
        cx_nom_dgEditText = (EditText) findViewById(R.id.input_txt_NomDG_Cx);

        CxIsPrConsAdmPCA= (SwitchCompat) findViewById(R.id.SwitchCxIsPrConsAdmPCA);
        CxIsPrComCredPCC= (SwitchCompat) findViewById(R.id.SwitchCxIsPrComCredPCC);
        CxIsDirCredDC= (SwitchCompat) findViewById(R.id.SwitchCxIsDirCredDC);
        CxIsAgentCredAC= (SwitchCompat) findViewById(R.id.SwitchCxIsAgentCredAC);
        CxIsDirGenCxDG= (SwitchCompat) findViewById(R.id.SwitchCxIsDirGenCxDG);
        CxIsMultiCpteCourByMemb= (SwitchCompat) findViewById(R.id.SwitchCxIsMultiCpteCourByMemb);

        Intent intent = getIntent();
        caisseId = intent.getStringExtra(KEY_CAISSE_ID);
        new FetchCaisseDetailsAsyncTask().execute();
        deleteButton = (Button) findViewById(R.id.btn_delete_Cx);
        deleteButton.setVisibility(View.VISIBLE);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete();
            }
        });
        updateButton = (Button) findViewById(R.id.btn_save_Cx);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        annulerButton.setVisibility(View.VISIBLE);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(UpdateCaisse.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        updateButton.setText("MODIFIER");
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    updateMovie();

                } else {
                    Toast.makeText(UpdateCaisse.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Mise à jour d'une caisse");

    }
    private void findViewsById() {
        cx_date_agremEditText = (EditText) findViewById(R.id.input_txt_dateAgrementCx);
        cx_date_agremEditText.requestFocus();
        cx_date_agremEditText.setInputType(InputType.TYPE_NULL);

        cx_date_debutEditText = (EditText) findViewById(R.id.input_txt_dateDebutCx);
        cx_date_debutEditText.setInputType(InputType.TYPE_NULL);
        cx_date_debutEditText.requestFocus();

        CxDatAgremCobac = (EditText) findViewById(R.id.input_txt_CxDatAgremCobac);
        CxDatAgremCobac.setInputType(InputType.TYPE_NULL);
        CxDatAgremCobac.requestFocus();

        CxDatAgremCNC = (EditText) findViewById(R.id.input_txt_CxDatAgremCNC);
        CxDatAgremCNC.setInputType(InputType.TYPE_NULL);
        CxDatAgremCNC.requestFocus();
    }

    private void setDateTimeField() {
        cx_date_agremEditText.setOnClickListener(this);
        cx_date_debutEditText.setOnClickListener(this);

        CxDatAgremCobac.setOnClickListener(this);
        CxDatAgremCNC.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();
        cx_date_agrem_PickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                cx_date_agremEditText.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        cx_date_debut_PickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                cx_date_debutEditText.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        CxDatAgremCobacPickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                CxDatAgremCobac.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        CxDatAgremCNCPickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                CxDatAgremCNC.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onClick(View v) {
        if(v == cx_date_agremEditText) {
            cx_date_agrem_PickerDialog.show();
        }  else if(v == cx_date_debutEditText) {
            cx_date_debut_PickerDialog.show();
        } else if(v == CxDatAgremCobac) {
            CxDatAgremCobacPickerDialog.show();
        } else if(v == CxDatAgremCNC) {
            CxDatAgremCNCPickerDialog.show();
        }
    }


    /**
     * Fetches single movie details from the server
     */
    private class FetchCaisseDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialogFetchCaisseDetails = new ProgressDialog(UpdateCaisse.this);
            pDialogFetchCaisseDetails.setMessage("Loading Caisse Details. Please wait...");
            pDialogFetchCaisseDetails.setIndeterminate(false);
            pDialogFetchCaisseDetails.setCancelable(false);
            pDialogFetchCaisseDetails.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_CAISSE_ID, caisseId);
            /*JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_caisse_details.php", "GET", httpParams);*/
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_caisse_details_new.php", "GET", httpParams);
            try {
              /*  int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject caisse;
                if (success == 1) {
                    //Parse the JSON response

                    caisse = jsonObject.getJSONObject(KEY_DATA);*/
                //monProduitCredit.setCrCode(MyData.normalizeSymbolsAndAccents(jsonObject.getString(KEY_CREDIT_Code)));
                    cxDenomination = jsonObject.getString(KEY_CX_DENOMINATION);
                    cxSiege = jsonObject.getString(KEY_CX_SIEGE);
                    cxLocalite = jsonObject.getString(KEY_CX_LOCALITE);
                    cx_num_agrem = jsonObject.getString(KEY_CX_NUM_AGREM);
                    cx_date_agrem = jsonObject.getString(KEY_CX_DATE_AGREM);
                    cx_date_debut = jsonObject.getString(KEY_CX_DATE_DEBUT);

                    CxNumAgremCobac_st = jsonObject.getString(KEY_CxNumAgremCobac);
                    CxDatAgremCobac_st = jsonObject.getString(KEY_CxDatAgremCobac);
                    CxNumAgremCNC_st = jsonObject.getString(KEY_CxNumAgremCNC);
                    CxDatAgremCNC_st = jsonObject.getString(KEY_CxDatAgremCNC);

                    cx_adresse = jsonObject.getString(KEY_CX_ADRESSE);
                    cx_tel1 = jsonObject.getString(KEY_CX_TEL1);
                    cx_tel2 = jsonObject.getString(KEY_CX_TEL2);
                    cx_tel3 = jsonObject.getString(KEY_CX_TEL3);
                    cx_nom_pca = jsonObject.getString(KEY_CX_NOM_PCA);
                    cx_nom_dg = jsonObject.getString(KEY_CX_NOM_DG);

                    bool_CxIsPrConsAdmPCA = jsonObject.getString(KEY_CxIsPrConsAdmPCA);
                    bool_CxIsPrComCredPCC = jsonObject.getString(KEY_CxIsPrComCredPCC);
                    bool_CxIsDirCredDC = jsonObject.getString(KEY_CxIsDirCredDC);
                    bool_CxIsAgentCredAC = jsonObject.getString(KEY_CxIsAgentCredAC);
                    bool_CxIsDirGenCxDG = jsonObject.getString(KEY_CxIsDirGenCxDG);
                    bool_CxIsMultiCpteCourByMemb = jsonObject.getString(KEY_CxIsMultiCpteCourByMemb);

//                } //end if
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialogFetchCaisseDetails.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    //Populate the Edit Texts once the network activity is finished executing
                    try {
                        cx_denominationEditText.setText(cxDenomination);
                        cx_siegeEditText.setText(cxSiege);
                        mySpinnerLocalite.setText(cxLocalite);
                        cx_num_agremEditText.setText(cx_num_agrem);
                        cx_date_agremEditText.setText(cx_date_agrem);
                        cx_date_debutEditText.setText(cx_date_debut);

                        CxNumAgremCobac.setText(CxNumAgremCobac_st);
                        CxDatAgremCobac.setText(CxDatAgremCobac_st);
                        CxNumAgremCNC.setText(CxNumAgremCNC_st);
                        CxDatAgremCNC.setText(CxDatAgremCNC_st);

                        cx_adresseEditText.setText(cx_num_agrem);
                   /* cx_tel1EditText.setText(cx_tel1);
                    cx_tel2EditText.setText(cx_tel2);
                    cx_tel3EditText.setText(cx_tel3); */

                        ccp_phone1.setFullNumber(cx_tel1);
                        ccp_phone2.setFullNumber(cx_tel2);
                        ccp_phone3.setFullNumber(cx_tel3);
                        cx_nom_pcaEditText.setText(cx_nom_pca);
                        cx_nom_dgEditText.setText(cx_nom_dg);

                        if (bool_CxIsPrConsAdmPCA.equals("Y")){
                            CxIsPrConsAdmPCA.setChecked(true);
                        }else{
                            CxIsPrConsAdmPCA.setChecked(false);
                        }
                        if (bool_CxIsPrComCredPCC.equals("Y")){
                            CxIsPrComCredPCC.setChecked(true);
                        }else{
                            CxIsPrComCredPCC.setChecked(false);
                        }

                        if (bool_CxIsDirCredDC.equals("Y")){
                            CxIsDirCredDC.setChecked(true);
                        }else{
                            CxIsDirCredDC.setChecked(false);
                        }

                        if (bool_CxIsAgentCredAC.equals("Y")){
                            CxIsAgentCredAC.setChecked(true);
                        }else{
                            CxIsAgentCredAC.setChecked(false);
                        }
                        if (bool_CxIsDirGenCxDG.equals("Y")){
                            CxIsDirGenCxDG.setChecked(true);
                        }else{
                            CxIsDirGenCxDG.setChecked(false);
                        }
                        if (bool_CxIsMultiCpteCourByMemb.equals("Y")){
                            CxIsMultiCpteCourByMemb.setChecked(true);
                        }else{
                            CxIsMultiCpteCourByMemb.setChecked(false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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
                UpdateCaisse.this);
        alertDialogBuilder.setMessage("Êtes-vous sûr de vouloir supprimer cette Caisse ?");
        alertDialogBuilder.setPositiveButton("Supprimer",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                            //If the user confirms deletion, execute DeleteCaisseAsyncTask
                            new DeleteCaisseAsyncTask().execute();
                        } else {
                            Toast.makeText(UpdateCaisse.this,
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
     * AsyncTask to delete a movie
     */
    private class DeleteCaisseAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateCaisse.this);
            pDialog.setMessage("Deleting Caisse. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Set movie_id parameter in request
            httpParams.put(KEY_CAISSE_ID, caisseId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "delete_caisse.php", "POST", httpParams);
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
                        Toast.makeText(UpdateCaisse.this,
                                "Caisse Deleted", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie deletion
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(UpdateCaisse.this,
                                "Some error occurred while deleting caisse",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    /**
     * Checks whether all files are filled. If so then calls UpdateCaisseAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void updateMovie() {


        if (!STRING_EMPTY.equals(cx_denominationEditText.getText().toString()) &&
                !STRING_EMPTY.equals(mySpinnerLocalite.getText().toString()) &&
                !STRING_EMPTY.equals(cx_num_agremEditText.getText().toString()) &&
                !STRING_EMPTY.equals(cx_date_agremEditText.getText().toString()) &&
                !STRING_EMPTY.equals(cx_adresseEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ccp_phone1.getFullNumberWithPlus()) &&
                !STRING_EMPTY.equals(cx_nom_pcaEditText.getText().toString()) &&
                !STRING_EMPTY.equals(cx_nom_dgEditText.getText().toString()) &&
                !STRING_EMPTY.equals(cx_date_debutEditText.getText().toString())) {

            cxDenomination = cx_denominationEditText.getText().toString();
            cxSiege = cx_siegeEditText.getText().toString();
            cxLocalite = mySpinnerLocalite.getText().toString();
            cx_num_agrem = cx_num_agremEditText.getText().toString();
            cx_date_agrem = cx_date_agremEditText.getText().toString();
            cx_date_debut = cx_date_debutEditText.getText().toString();
            cx_adresse = cx_adresseEditText.getText().toString();

          /*  cx_tel1 = cx_tel1EditText.getText().toString();
            cx_tel2 = cx_tel2EditText.getText().toString();
            cx_tel3 = cx_tel3EditText.getText().toString(); */

            cx_tel1 = ccp_phone1.getFullNumberWithPlus();
            cx_tel2 = ccp_phone2.getFullNumberWithPlus();
            cx_tel3 = ccp_phone3.getFullNumberWithPlus();

            cx_nom_pca = cx_nom_pcaEditText.getText().toString();
            cx_nom_dg = cx_nom_dgEditText.getText().toString();


            if (CxIsPrConsAdmPCA.isChecked()){
                bool_CxIsPrConsAdmPCA="Y";
            }else{
                bool_CxIsPrConsAdmPCA="N";
            }

            if (CxIsPrComCredPCC.isChecked()){
                bool_CxIsPrComCredPCC="Y";
            }else{
                bool_CxIsPrComCredPCC="N";
            }

            if (CxIsDirCredDC.isChecked()){
                bool_CxIsDirCredDC="Y";
            }else{
                bool_CxIsDirCredDC="N";
            }


            if (CxIsAgentCredAC.isChecked()){
                bool_CxIsAgentCredAC="Y";
            }else{
                bool_CxIsAgentCredAC="N";
            }

            if (CxIsDirGenCxDG.isChecked()){
                bool_CxIsDirGenCxDG="Y";
            }else{
                bool_CxIsDirGenCxDG="N";
            }
            if (CxIsMultiCpteCourByMemb.isChecked()){
                bool_CxIsMultiCpteCourByMemb="Y";
            }else{
                bool_CxIsMultiCpteCourByMemb="N";
            }

            new UpdateCaisseAsyncTask().execute();
        } else {
            Toast.makeText(UpdateCaisse.this,
                    "One or more fields left empty!",
                    Toast.LENGTH_LONG).show();

        }


    }
    /**
     * AsyncTask for updating a movie details
     */

    private class UpdateCaisseAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateCaisse.this);
            pDialog.setMessage("Updating Caisse. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_CAISSE_ID, caisseId);
            httpParams.put(KEY_CX_DENOMINATION, cxDenomination);
            httpParams.put(KEY_CX_SIEGE, cxSiege);
            httpParams.put(KEY_CX_LOCALITE, cxLocalite);
            httpParams.put(KEY_CX_NUM_AGREM, cx_num_agrem);
            httpParams.put(KEY_CX_DATE_AGREM, cx_date_agrem);
            httpParams.put(KEY_CX_DATE_DEBUT, cx_date_debut);

            httpParams.put(KEY_CxNumAgremCobac, CxNumAgremCobac.getText().toString().trim());
            httpParams.put(KEY_CxDatAgremCobac, CxDatAgremCobac.getText().toString().trim());
            httpParams.put(KEY_CxNumAgremCNC, CxNumAgremCNC.getText().toString().trim());
            httpParams.put(KEY_CxDatAgremCNC, CxDatAgremCNC.getText().toString().trim());

            httpParams.put(KEY_CX_ADRESSE, cx_adresse);
            httpParams.put(KEY_CX_TEL1, cx_tel1);
            httpParams.put(KEY_CX_TEL2, cx_tel2);
            httpParams.put(KEY_CX_TEL3, cx_tel3);
            httpParams.put(KEY_CX_NOM_PCA, cx_nom_pca);
            httpParams.put(KEY_CX_NOM_DG, cx_nom_dg);


            httpParams.put(KEY_CxIsPrConsAdmPCA, bool_CxIsPrConsAdmPCA);
            httpParams.put(KEY_CxIsPrComCredPCC, bool_CxIsPrComCredPCC);
            httpParams.put(KEY_CxIsDirCredDC, bool_CxIsDirCredDC);
            httpParams.put(KEY_CxIsAgentCredAC, bool_CxIsAgentCredAC);
            httpParams.put(KEY_CxIsDirGenCxDG, bool_CxIsDirGenCxDG);
            httpParams.put(KEY_CxIsMultiCpteCourByMemb, bool_CxIsMultiCpteCourByMemb);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "update_caisse.php", "POST", httpParams);
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
                        Toast.makeText(UpdateCaisse.this,
                                "Caisse Updated", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(UpdateCaisse.this,
                                "Some error occurred while updating Caisse",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}
