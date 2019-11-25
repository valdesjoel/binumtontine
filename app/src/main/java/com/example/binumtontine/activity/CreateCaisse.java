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
/*
import eav.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.binumtontine.R;

public class CreateProduitEAP extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_param_produit_eap);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_produit_eap);
        setSupportActionBar(toolbar);
        setToolbarTitle();
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Ajout d'un produit EAP");

    }
}
*/

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import androidx.appcompat.widget.Toolbar;

import com.example.binumtontine.JRSpinner;
import com.example.binumtontine.R;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CreateCaisse extends AppCompatActivity implements View.OnClickListener{
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_CX_DENOMINATION = "cx_denomination";
    private static final String KEY_CX_LOCALITE = "cx_localite";
    private static final String KEY_CX_NUM_AGREM = "cx_num_agrem";
    private static final String KEY_CX_DATE_AGREM = "cx_date_agrem";
    private static final String KEY_CX_DATE_DEBUT = "cx_date_debut";
    private static final String KEY_CX_ADRESSE = "cx_adresse";
    private static final String KEY_CX_TEL1 = "cx_tel1";
    private static final String KEY_CX_TEL2 = "cx_tel2";
    private static final String KEY_CX_TEL3 = "cx_tel3";
    private static final String KEY_CX_NOM_PCA = "cx_nom_pca";
    private static final String KEY_CX_NOM_DG = "cx_nom_dg";
    //private static final String BASE_URL = "http://192.168.1.102/binumTontine/";
    private static final String BASE_URL = "http://binumt.diff-itc.net/binumTontine/";

    private static String STRING_EMPTY = "";

    private EditText cx_denominationEditText;
    private JRSpinner mySpinnerLocalite; //pour gérer le spinner contenant les localités
    private EditText cx_num_agremEditText;
    private EditText cx_date_agremEditText;
    private EditText cx_date_debutEditText;
    private EditText cx_adresseEditText;
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
    private String cx_adresse;
    private String cx_tel1;
    private String cx_tel2;
    private String cx_tel3;
    private String cx_nom_pca;
    private String cx_nom_dg;

    private Button addButton;
    private Button delButton;
    private int success;
    private ProgressDialog pDialog;




    private DatePickerDialog cx_date_agrem_PickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date
    private DatePickerDialog cx_date_debut_PickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date
    // private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter; //propriété permettant de gérer le format de la date


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_add_movie);
        //setContentView(R.layout.fragment_caisses);spn_my_spinner_localite_caisse
        setContentView(R.layout.fragment_caisses);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_caisse);
        setSupportActionBar(toolbar);
        setToolbarTitle();
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
              //  cxLocalite = mySpinnerLocalite.getText().toString();
               // Log.d("iddddddd***",caisseLocalite);
            }
        });
        cx_denominationEditText = (EditText) findViewById(R.id.input_denominationCx);
        cx_num_agremEditText = (EditText) findViewById(R.id.input_txt_AdresseCx);
       // cx_date_agremEditText = (EditText) findViewById(R.id.input_txt_num_agrement_cx);
        //cx_date_debutEditText = (EditText) findViewById(R.id.input_txt_dateAgrementCx);
        cx_adresseEditText = (EditText) findViewById(R.id.input_txt_AdresseCx);
        cx_tel1EditText = (EditText) findViewById(R.id.input_txt_Tel1_Cx);
        cx_tel2EditText = (EditText) findViewById(R.id.input_txt_Tel2_Cx);
        cx_tel3EditText = (EditText) findViewById(R.id.input_txt_Tel3_Cx);
        cx_nom_pcaEditText = (EditText) findViewById(R.id.input_txt_NomPCA_Cx);
        cx_nom_dgEditText = (EditText) findViewById(R.id.input_txt_NomDG_Cx);

        addButton = (Button) findViewById(R.id.btn_save_Cx);
        delButton = (Button) findViewById(R.id.btn_delete_Cx);
        delButton.setVisibility(View.INVISIBLE);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addCaisse();
                } else {
                    Toast.makeText(CreateCaisse.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });


    }
    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Ajout d'une caisse");

    }

    /**
     * Checks whether all files are filled. If so then calls AddCaisseAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addCaisse() {
        if (!STRING_EMPTY.equals(cx_denominationEditText.getText().toString()) &&
                !STRING_EMPTY.equals(mySpinnerLocalite.getText().toString()) &&
                !STRING_EMPTY.equals(cx_num_agremEditText.getText().toString()) &&
                !STRING_EMPTY.equals(cx_date_agremEditText.getText().toString()) &&
                !STRING_EMPTY.equals(cx_adresseEditText.getText().toString()) &&
                !STRING_EMPTY.equals(cx_tel1EditText.getText().toString()) &&
                !STRING_EMPTY.equals(cx_nom_pcaEditText.getText().toString()) &&
                !STRING_EMPTY.equals(cx_nom_dgEditText.getText().toString()) &&
                !STRING_EMPTY.equals(cx_date_debutEditText.getText().toString())) {

            cxDenomination = cx_denominationEditText.getText().toString();
            cxLocalite = mySpinnerLocalite.getText().toString();
            cx_num_agrem = cx_num_agremEditText.getText().toString();
            cx_date_agrem = cx_date_agremEditText.getText().toString();
            cx_date_debut = cx_date_debutEditText.getText().toString();
            cx_adresse = cx_adresseEditText.getText().toString();
            cx_tel1 = cx_tel1EditText.getText().toString();
            cx_tel2 = cx_tel2EditText.getText().toString();
            cx_tel3 = cx_tel3EditText.getText().toString();
            cx_nom_pca = cx_nom_pcaEditText.getText().toString();
            cx_nom_dg = cx_nom_dgEditText.getText().toString();
            new AddCaisseAsyncTask().execute();
        } else {
            Toast.makeText(CreateCaisse.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();

        }


    }
    private void findViewsById() {
        cx_date_agremEditText = (EditText) findViewById(R.id.input_txt_dateAgrementCx);
        cx_date_agremEditText.requestFocus();
        cx_date_agremEditText.setInputType(InputType.TYPE_NULL);

        cx_date_debutEditText = (EditText) findViewById(R.id.input_txt_dateDebutCx);
        cx_date_debutEditText.setInputType(InputType.TYPE_NULL);
        cx_date_debutEditText.requestFocus();


    }

    private void setDateTimeField() {
        cx_date_agremEditText.setOnClickListener(this);
        cx_date_debutEditText.setOnClickListener(this);


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

    }

    @Override
    public void onClick(View v) {
        if(v == cx_date_agremEditText) {
            cx_date_agrem_PickerDialog.show();
        }  else if(v == cx_date_debutEditText) {
            cx_date_debut_PickerDialog.show();
        }
    }

    /**
     * AsyncTask for adding a movie
     */
    private class AddCaisseAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(CreateCaisse.this);
            pDialog.setMessage("Adding Caisse. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_CX_DENOMINATION, cxDenomination);
            httpParams.put(KEY_CX_LOCALITE, cxLocalite);
            httpParams.put(KEY_CX_NUM_AGREM, cx_num_agrem);
            httpParams.put(KEY_CX_DATE_AGREM, cx_date_agrem);
            httpParams.put(KEY_CX_DATE_DEBUT, cx_date_debut);
            httpParams.put(KEY_CX_ADRESSE, cx_adresse);
            httpParams.put(KEY_CX_TEL1, cx_tel1);
            httpParams.put(KEY_CX_TEL2, cx_tel2);
            httpParams.put(KEY_CX_TEL3, cx_tel3);
            httpParams.put(KEY_CX_NOM_PCA, cx_nom_pca);
            httpParams.put(KEY_CX_NOM_DG, cx_nom_dg);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_caisse.php", "POST", httpParams);
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
                        Toast.makeText(CreateCaisse.this,
                                "Caisse Ajoutée", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CreateCaisse.this,
                                "Some error occurred while adding Caisse",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}