package com.example.binumtontine.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.JRSpinner;
import com.example.binumtontine.R;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class UpdateGuichet extends AppCompatActivity implements  AdapterView.OnItemSelectedListener, View.OnClickListener, SERVER_ADDRESS {
    private static String STRING_EMPTY = "";
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";


    private static final String KEY_GUICHET_ID = "gx_numero";
    private static final String KEY_GX_CX_NUMERO = "cx_numero";
    private static final String KEY_GX_LOCALITE = "gx_localite";
    private static final String KEY_GX_DENOMINATION = "gx_denomination";
    private static final String KEY_GX_DATE_DEBUT = "gx_date_debut";
    private static final String KEY_GX_ADRESSE = "gx_adresse";
    private static final String KEY_GX_TEL1 = "gx_tel1";
    private static final String KEY_GX_TEL2 = "gx_tel2";
    private static final String KEY_GX_TEL3 = "gx_tel3";
    private static final String KEY_GX_NOM_PCA = "gx_nom_pca";
    private static final String KEY_GX_NOM_DG = "gx_nom_dg";
    private static final String KEY_GX_IS_FORCER_CLOT = "gx_is_forcer_clot";
    private static final String KEY_GX_HEURE_CLOT = "gx_heure_clot";
    private static final String KEY_GX_IS_OPER_APRES_CLOT = "gx_is_oper_apres_clot";
    private static final String KEY_GX_NBRE_RAPP_BY_JOUR = "gx_nbre_rapp_by_jour";
    private static final String KEY_GX_NBRE_JR_AV_RAPP = "gx_nbre_jr_av_rapp";
    private static final String KEY_GX_IS_JOUR8_ON = "gx_is_jour8_on";
    private static final String KEY_GX_IS_CREDIT_BY_OBJET = "gx_is_credit_by_objet";
    private static final String KEY_GX_FIRST_JR_ON = "gx_first_jr_on";
    private static final String KEY_GX_FREQ_REUN_COM_CRED = "gx_freq_reun_com_cred";
    private static final String KEY_GX_IS_RAPP_NET_MSG_CRED_ON = "gx_is_rapp_net_msg_cred_on";

    private TextView headerGuichetTextView;




    private String guichetId;
    private JRSpinner mySpinnerCaisse; //pour gérer le spinner contenant les caisses
    private JRSpinner mySpinnerLocalite; //pour gérer le spinner contenant les localités
    private EditText gx_denominationEditText;
    private EditText gx_date_debutEditText;
    private EditText gx_adresseEditText;
    private EditText gx_tel1EditText;
    private EditText gx_tel2EditText;
    private EditText gx_tel3EditText;
    private EditText gx_nom_pcaEditText;
    private EditText gx_nom_dgEditText;
    private Switch gx_is_forcer_clotSwitch;
    private EditText gx_heure_clotEditText;
    private Switch gx_is_oper_apres_clotSwitch;
    private EditText gx_nbre_rapp_by_jourEditText;
    private EditText gx_nbre_jr_av_rappEditText;
    private Switch gx_is_jour8_onSwitch;
    private Switch gx_is_credit_by_objetSwitch;
    private EditText gx_first_jr_onEditText;
    private EditText gx_freq_reun_com_credEditText;
    private Switch gx_is_rapp_net_msg_cred_onSwitch;

    private String gxCaisse;
    private String gxLocalite;
    private String gxDenomination;
    private String gx_date_debut;
    private String gx_adresse;
    private String gx_tel1;
    private String gx_tel2;
    private String gx_tel3;
    private String gx_nom_pca;
    private String gx_nom_dg;
    private Boolean gx_is_forcer_clot;
    private String gx_heure_clot;
    private Boolean gx_is_oper_apres_clot;
    private String gx_nbre_rapp_by_jour;
    private String gx_nbre_jr_av_rapp;
    private Boolean gx_is_jour8_on;
    private Boolean gx_is_credit_by_objet;
    private String gx_first_jr_on;
    private String gx_freq_reun_com_cred;
    private Boolean gx_is_rapp_net_msg_cred_on;

    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Category> caisseList;
    List<Integer> caisseListID = new ArrayList<Integer>();
    private int caisseID;
    private Spinner spinnerCaisse;
    private TextView tvCaisse;
    private TextInputLayout textInputLayoutCaisse;
    /*end manage*/



    private DatePickerDialog gx_date_debut_PickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date

    private SimpleDateFormat dateFormatter; //propriété permettant de gérer le format de la date

    private Button deleteButton;
    private Button updateButton;
    private int success;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_guichet);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_caisse);
        setSupportActionBar(toolbar);
        setToolbarTitle(); */

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();

        setDateTimeField();



        mySpinnerCaisse = (JRSpinner)findViewById(R.id.spn_my_spinner_select_caisse);
        mySpinnerLocalite = (JRSpinner)findViewById(R.id.spn_my_spinner_localite_guichet);

        mySpinnerCaisse.setItems(getResources().getStringArray(R.array.array_caisse)); //this is important, you must set it to set the item list
        mySpinnerLocalite.setItems(getResources().getStringArray(R.array.array_localite)); //this is important, you must set it to set the item list
        mySpinnerCaisse.setTitle("Sélectionner une caisse"); //change title of spinner-dialog programmatically
        mySpinnerLocalite.setTitle("Sélectionner une localité"); //change title of spinner-dialog programmatically
        mySpinnerCaisse.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        mySpinnerLocalite.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically

        mySpinnerCaisse.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });
        mySpinnerLocalite.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });


        Intent intent = getIntent();
        headerGuichetTextView = (TextView) findViewById(R.id.header_user_guichet);
        headerGuichetTextView.setText("Mise à jour Guichet");

        gx_denominationEditText = (EditText) findViewById(R.id.input_denomination_guichet);
        gx_date_debutEditText = (EditText) findViewById(R.id.input_txt_dateDebut_guichet);
        gx_adresseEditText = (EditText) findViewById(R.id.input_txt_AdresseGx);
        /*gx_tel1EditText = (EditText) findViewById(R.id.input_txt_Tel1_GX);
        gx_tel2EditText = (EditText) findViewById(R.id.input_txt_Tel2_GX);
        gx_tel3EditText = (EditText) findViewById(R.id.input_txt_Tel3_GX);*/
        gx_nom_pcaEditText = (EditText) findViewById(R.id.input_txt_NomPCA_Gx);
        gx_nom_dgEditText = (EditText) findViewById(R.id.input_txt_NomDG_Gx);
        gx_is_forcer_clotSwitch = (Switch) findViewById(R.id.SwitchForcer_la_clotureGx);
        gx_heure_clotEditText = (EditText) findViewById(R.id.input_txt_heure_cloture_Gx);
        gx_is_oper_apres_clotSwitch = (Switch) findViewById(R.id.Switch_gx_is_oper_apres_clot);
        gx_nbre_rapp_by_jourEditText = (EditText) findViewById(R.id.input_txt_Nombre_de_rappel_par_jour);
        gx_nbre_jr_av_rappEditText = (EditText) findViewById(R.id.input_txt_Nombre_jour_av_rappel);
        gx_is_jour8_onSwitch = (Switch) findViewById(R.id.Switch8jr_semaine_Gx);
        gx_is_credit_by_objetSwitch = (Switch) findViewById(R.id.Switch_credit_par_objet_Gx);
        gx_first_jr_onEditText = (EditText) findViewById(R.id.input_txt_GuFirstJrOn);
        gx_freq_reun_com_credEditText = (EditText) findViewById(R.id.input_txt_GuFreqReunComCred);
        gx_is_rapp_net_msg_cred_onSwitch = (Switch) findViewById(R.id.Switch_GuIsRappNetMsgCredOn);

        spinnerCaisse = (Spinner) findViewById(R.id.spn_my_spinner_caisse1);
        tvCaisse = (TextView) findViewById(R.id.tv_caisse);

        caisseList = new ArrayList<Category>();
        // spinner item select listener
        spinnerCaisse.setOnItemSelectedListener(UpdateGuichet.this);
        new UpdateGuichet.GetCategories().execute();


        guichetId = intent.getStringExtra(KEY_GUICHET_ID);
        new FetchGuichetDetailsAsyncTask().execute();
        deleteButton = (Button) findViewById(R.id.btn_delete_guichet);
        deleteButton.setVisibility(View.VISIBLE);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete();
            }
        });
        updateButton = (Button) findViewById(R.id.btn_save_guichet);
        updateButton.setText("MODIFIER");
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    updateGuichet();

                } else {
                    Toast.makeText(UpdateGuichet.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });


    }
    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Mise à jour d'une guichet");

    }

    private void findViewsById() {
        gx_date_debutEditText = (EditText) findViewById(R.id.input_txt_dateDebut_guichet);
        gx_date_debutEditText.requestFocus();
        gx_date_debutEditText.setInputType(InputType.TYPE_NULL);




    }

    private void setDateTimeField() {
        gx_date_debutEditText.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();
        gx_date_debut_PickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                gx_date_debutEditText.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));



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
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(UpdateGuichet.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerCaisse.setAdapter(spinnerAdapter);
    }

    @Override
    public void onClick(View v) {
        if(v == gx_date_debutEditText) {
            gx_date_debut_PickerDialog.show();
        }
    }

    /**
     * Async task to get all food categories
     * */
    private class GetCategories extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpdateGuichet.this);
            pDialog.setMessage("Fetching caisse's list..");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_CATEGORIES, ServiceHandler.GET);

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

    /**
     * Fetches single movie details from the server
     */
    private class FetchGuichetDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateGuichet.this);
            pDialog.setMessage("Loading Guichet Details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_GUICHET_ID, guichetId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_guichet_details.php", "GET", httpParams);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject guichet;
                if (success == 1) {
                    //Parse the JSON response
                    guichet = jsonObject.getJSONObject(KEY_DATA);

                    gxCaisse = guichet.getString(KEY_GX_CX_NUMERO);
                    gxLocalite = guichet.getString(KEY_GX_LOCALITE);
                    gxDenomination = guichet.getString(KEY_GX_DENOMINATION);
                    gx_date_debut = guichet.getString(KEY_GX_DATE_DEBUT);
                    gx_adresse = guichet.getString(KEY_GX_ADRESSE);
                    gx_tel1 = guichet.getString(KEY_GX_TEL1);
                    gx_tel2 = guichet.getString(KEY_GX_TEL2);
                    gx_tel3 = guichet.getString(KEY_GX_TEL3);
                    gx_nom_pca = guichet.getString(KEY_GX_NOM_PCA);
                    gx_nom_dg = guichet.getString(KEY_GX_NOM_DG);
                    gx_is_forcer_clot = Boolean.parseBoolean(guichet.getString(KEY_GX_IS_FORCER_CLOT));
                    gx_heure_clot = guichet.getString(KEY_GX_HEURE_CLOT);
                    gx_is_oper_apres_clot = Boolean.parseBoolean(guichet.getString(KEY_GX_IS_OPER_APRES_CLOT));
                    gx_nbre_rapp_by_jour = guichet.getString(KEY_GX_NBRE_RAPP_BY_JOUR);
                    gx_nbre_jr_av_rapp = guichet.getString(KEY_GX_NBRE_JR_AV_RAPP);
                    gx_is_jour8_on = Boolean.parseBoolean(guichet.getString(KEY_GX_IS_JOUR8_ON));
                    gx_is_credit_by_objet = Boolean.parseBoolean(guichet.getString(KEY_GX_IS_CREDIT_BY_OBJET));
                    gx_first_jr_on = guichet.getString(KEY_GX_FIRST_JR_ON);
                    gx_freq_reun_com_cred = guichet.getString(KEY_GX_FREQ_REUN_COM_CRED);
                    gx_is_rapp_net_msg_cred_on = Boolean.parseBoolean(guichet.getString(KEY_GX_IS_RAPP_NET_MSG_CRED_ON));

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

                    mySpinnerCaisse.setText(gxCaisse);
                    mySpinnerLocalite.setText(gxLocalite);
                    gx_denominationEditText.setText(gxDenomination);
                    gx_date_debutEditText.setText(gx_date_debut);
                    gx_adresseEditText.setText(gx_adresse);
                    gx_tel1EditText.setText(gx_tel1);
                    gx_tel2EditText.setText(gx_tel2);
                    gx_tel3EditText.setText(gx_tel3);
                    gx_nom_pcaEditText.setText(gx_nom_pca);
                    gx_nom_dgEditText.setText(gx_nom_dg);
                    gx_is_forcer_clotSwitch.setChecked(gx_is_forcer_clot);
                    gx_heure_clotEditText.setText(gx_heure_clot);
                    gx_is_oper_apres_clotSwitch.setChecked(gx_is_oper_apres_clot);
                    gx_nbre_rapp_by_jourEditText.setText(gx_nbre_rapp_by_jour);
                    gx_nbre_jr_av_rappEditText.setText(gx_nbre_jr_av_rapp);
                    gx_is_jour8_onSwitch.setChecked(gx_is_jour8_on);
                    gx_is_credit_by_objetSwitch.setChecked(gx_is_credit_by_objet);
                    gx_first_jr_onEditText.setText(gx_first_jr_on);
                    gx_freq_reun_com_credEditText.setText(gx_freq_reun_com_cred);
                    gx_is_rapp_net_msg_cred_onSwitch.setChecked(gx_is_rapp_net_msg_cred_on);

                }
            });
        }


    }

    /**
     * Displays an alert dialogue to confirm the deletion
     */
    private void confirmDelete() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                UpdateGuichet.this);
        alertDialogBuilder.setMessage("Are you sure, you want to delete this Guichet?");
        alertDialogBuilder.setPositiveButton("Delete",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                            //If the user confirms deletion, execute DeleteGuichetAsyncTask
                            new DeleteGuichetAsyncTask().execute();
                        } else {
                            Toast.makeText(UpdateGuichet.this,
                                    "Unable to connect to internet",
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                });

        alertDialogBuilder.setNegativeButton("Cancel", null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * AsyncTask to delete a guichet
     */
    private class DeleteGuichetAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateGuichet.this);
            pDialog.setMessage("Deleting Guichet. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Set guichet_id parameter in request
            httpParams.put(KEY_GUICHET_ID, guichetId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "delete_guichet.php", "POST", httpParams);
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
                        Toast.makeText(UpdateGuichet.this,
                                "Guichet Deleted", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie deletion
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(UpdateGuichet.this,
                                "Some error occurred while deleting guichet",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    /**
     * Checks whether all files are filled. If so then calls UpdateMovieAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void updateGuichet() {

        /*

        if (!STRING_EMPTY.equals(cx_denominationEditText.getText().toString()) &&
                !STRING_EMPTY.equals(mySpinnerLocalite.getText().toString()) &&
                !STRING_EMPTY.equals(cx_num_agremEditText.getText().toString()) &&
                !STRING_EMPTY.equals(cx_date_agremEditText.getText().toString()) &&
                !STRING_EMPTY.equals(cx_adresseEditText.getText().toString()) &&
                !STRING_EMPTY.equals(cx_tel1EditText.getText().toString()) &&
                !STRING_EMPTY.equals(cx_nom_pcaEditText.getText().toString()) &&
                !STRING_EMPTY.equals(cx_nom_dgEditText.getText().toString()) &&
                !STRING_EMPTY.equals(cx_date_debutEditText.getText().toString())) {
            */
            if (true){
                //gxCaisse = "1";//gérer plutot dynamiquement
                gxLocalite = mySpinnerLocalite.getText().toString();
                gxDenomination = gx_denominationEditText.getText().toString();
                gx_date_debut = gx_date_debutEditText.getText().toString();
                gx_adresse = gx_adresseEditText.getText().toString();
                gx_tel1 = gx_tel1EditText.getText().toString();
                gx_tel2 = gx_tel2EditText.getText().toString();
                gx_tel3 = gx_tel3EditText.getText().toString();
                gx_nom_pca = gx_nom_pcaEditText.getText().toString();
                gx_nom_dg = gx_nom_dgEditText.getText().toString();
                gx_is_forcer_clot = gx_is_forcer_clotSwitch.isChecked();
                gx_heure_clot = gx_heure_clotEditText.getText().toString();
                gx_is_oper_apres_clot = gx_is_oper_apres_clotSwitch.isChecked();
                gx_nbre_rapp_by_jour = gx_nbre_rapp_by_jourEditText.getText().toString();
                gx_nbre_jr_av_rapp = gx_nbre_jr_av_rappEditText.getText().toString();
                gx_nbre_jr_av_rapp = gx_nbre_jr_av_rappEditText.getText().toString();
                gx_is_jour8_on = gx_is_jour8_onSwitch.isChecked();
                gx_is_credit_by_objet = gx_is_credit_by_objetSwitch.isChecked();
                gx_first_jr_on = gx_first_jr_onEditText.getText().toString();
                gx_freq_reun_com_cred = gx_freq_reun_com_credEditText.getText().toString();
                gx_is_rapp_net_msg_cred_on = gx_is_rapp_net_msg_cred_onSwitch.isChecked();
            new UpdateGuichet.UpdateMovieAsyncTask().execute();
        } else {
            Toast.makeText(UpdateGuichet.this,
                    "One or more fields left empty!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for updating a movie details
     */

    private class UpdateMovieAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateGuichet.this);
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
           // httpParams.put(KEY_GUICHET_ID, guichetId);
            httpParams.put(KEY_GUICHET_ID, String.valueOf(caisseID));
            httpParams.put(KEY_GX_CX_NUMERO, gxCaisse);
            httpParams.put(KEY_GX_LOCALITE, gxLocalite);
            httpParams.put(KEY_GX_DENOMINATION, gxDenomination);
            httpParams.put(KEY_GX_DATE_DEBUT, gx_date_debut);
            httpParams.put(KEY_GX_ADRESSE, gx_adresse);
            httpParams.put(KEY_GX_TEL1, gx_tel1);
            httpParams.put(KEY_GX_TEL2, gx_tel2);
            httpParams.put(KEY_GX_TEL3, gx_tel3);
            httpParams.put(KEY_GX_NOM_PCA, gx_nom_pca);
            httpParams.put(KEY_GX_NOM_DG, gx_nom_dg);
            httpParams.put(KEY_GX_IS_FORCER_CLOT, gx_is_forcer_clot.toString());
            httpParams.put(KEY_GX_HEURE_CLOT, gx_heure_clot);
            httpParams.put(KEY_GX_IS_OPER_APRES_CLOT, gx_is_oper_apres_clot.toString());
            httpParams.put(KEY_GX_NBRE_RAPP_BY_JOUR, gx_nbre_rapp_by_jour);
            httpParams.put(KEY_GX_NBRE_JR_AV_RAPP, gx_nbre_jr_av_rapp);
            httpParams.put(KEY_GX_IS_JOUR8_ON, gx_is_jour8_on.toString());
            httpParams.put(KEY_GX_IS_CREDIT_BY_OBJET, gx_is_credit_by_objet.toString());
            httpParams.put(KEY_GX_FIRST_JR_ON, gx_first_jr_on);
            httpParams.put(KEY_GX_FREQ_REUN_COM_CRED, gx_freq_reun_com_cred);
            httpParams.put(KEY_GX_IS_RAPP_NET_MSG_CRED_ON, gx_is_rapp_net_msg_cred_on.toString());
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "update_guichet.php", "POST", httpParams);
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
                        Toast.makeText(UpdateGuichet.this,
                                "Guichet Updated", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(UpdateGuichet.this,
                                "Some error occurred while updating Guichet",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}
