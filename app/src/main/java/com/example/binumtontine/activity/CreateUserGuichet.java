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


import android.app.ProgressDialog;
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

import com.example.binumtontine.JRSpinner;
import com.example.binumtontine.R;
import com.example.binumtontine.activity.convertisseur.BCrypt;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.hbb20.CountryCodePicker;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateUserGuichet extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";

    private static final String KEY_USER_GUICHET_ID = "gx_numero";
    private static final String KEY_CAISSE_ID = "cx_numero"; //to fetchGuichetList by caisse ID
    private static final String KEY_USER_PROFIL = "ux_profil";
    private static final String KEY_USER_NOM = "ux_nom";
    private static final String KEY_USER_PRENOM = "ux_prenom";
    private static final String KEY_USER_ADRESSE = "ux_adresse";
    private static final String KEY_USER_TEL1 = "ux_tel1";
    private static final String KEY_USER_TEL2 = "ux_tel2";
    private static final String KEY_USER_TEL3 = "ux_tel3";
    private static final String KEY_USER_LOGIN = "ux_login";
    private static final String KEY_USER_PASSWORD = "ux_password";
    private static final String KEY_USER_BCRYPT_PASSWORD = "password";
    private static final String KEY_USER_EMAIL = "ux_email";
    private static final String KEY_PROFIL_CAISSE_OR_GUICHET = "profilCaisseOrGuichet";


    private static String STRING_EMPTY = "";

    private JRSpinner uxGuichetIdSpinner; //pour gérer le spinner contenant les caisses
    private EditText uxProfilEditText;
    private EditText uxNomEditText;
    private EditText uxPrenomEditText;
    private EditText uxAdresseEditText;

    private EditText uxLoginEditText;
    private EditText uxPasswordEditText;
    private EditText uxConfirmPasswordEditText;
    private EditText uxEmailEditText;

    private String uxGuichetId;
    private String uxProfil;
    private String uxNom;
    private String uxPrenom;
    private String uxAdresse;
    private String uxTel1;
    private String uxTel2;
    private String uxTel3;

    private CountryCodePicker ccp_phone1;
    private CountryCodePicker ccp_phone2;
    private CountryCodePicker ccp_phone3;
    private EditText editTextCarrierPhone1;
    private EditText editTextCarrierPhone2;
    private EditText editTextCarrierPhone3;

    private String uxLogin;
    private String uxPassword;
    private String uxBcryptPassword;
    private String uxConfirmPassword;
    private String uxEmail;

    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Category> guichetList;
    List<Integer> guichetListID = new ArrayList<Integer>();
    private int guichetID;
    private Spinner spinnerGuichet;
    private TextView tvGuichet;
    //private TextInputLayout textInputLayoutGuichet;
    /*end manage*/

    private JRSpinner spnNewProfil;
    private Button deleteButton;
    private Button addButton;
    private Button annulerButton;
    private int success;
    private ProgressDialog pDialog;
    private ProgressDialog pDialogFetchGuichetsList;
    private RadioButton rbGuichet;
    private RadioButton rbCaisse;
    private String profilCaisseOrGuichet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_guichet);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_user);
        setSupportActionBar(toolbar);
        setToolbarTitle();*/
       /* textInputLayoutGuichet = (TextInputLayout) findViewById(R.id.til_guichet);
        textInputLayoutGuichet.setVisibility(View.GONE); */
       /* uxGuichetIdSpinner = (JRSpinner) findViewById(R.id.spn_my_spinner_guichet);

        uxGuichetIdSpinner.setItems(getResources().getStringArray(R.array.array_guichet)); //this is important, you must set it to set the item list
        uxGuichetIdSpinner.setTitle("Sélectionner un guichet"); //change title of spinner-dialog programmatically
        uxGuichetIdSpinner.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically

        uxGuichetIdSpinner.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });*/

          /*  mySpinner.setOnSelectMultipleListener(new JRSpinner.OnSelectMultipleListener() {
            @Override
            public void onMultipleSelected(List<Integer> selectedPosition) {
                //do what you want to selected position list
            }
        });*/

        ccp_phone1 = (CountryCodePicker) findViewById(R.id.ccp_phone1);
        editTextCarrierPhone1 = (EditText) findViewById(R.id.editText_carrierPhone1);
        ccp_phone1.registerCarrierNumberEditText(editTextCarrierPhone1);


        ccp_phone2 = (CountryCodePicker) findViewById(R.id.ccp_phone2);
        editTextCarrierPhone2 = (EditText) findViewById(R.id.editText_carrierPhone2);
        ccp_phone2.registerCarrierNumberEditText(editTextCarrierPhone2);

        ccp_phone3 = (CountryCodePicker) findViewById(R.id.ccp_phone3);
        editTextCarrierPhone3 = (EditText) findViewById(R.id.editText_carrierPhone3);
        ccp_phone3.registerCarrierNumberEditText(editTextCarrierPhone3);


        uxProfilEditText = (EditText) findViewById(R.id.input_txt_profil_user);
        uxNomEditText = (EditText) findViewById(R.id.input_txt_Nom_user);
        alreadyUpperCase(uxNomEditText);
        uxPrenomEditText = (EditText) findViewById(R.id.input_txt_Prenom_user);
        uxAdresseEditText = (EditText) findViewById(R.id.input_txt_Adresse_user);
//        uxTel1EditText = (EditText) findViewById(R.id.input_txt_Tel1_user);
//        uxTel2EditText = (EditText) findViewById(R.id.input_txt_Tel2_user);
//        uxTel3EditText = (EditText) findViewById(R.id.input_txt_Tel3_user);
        uxLoginEditText = (EditText) findViewById(R.id.input_txt_Login_user);
        uxPasswordEditText = (EditText) findViewById(R.id.input_txt_pwd_user);
        uxConfirmPasswordEditText = (EditText) findViewById(R.id.input_txt_confirm_pwd_user);
        uxEmailEditText = (EditText) findViewById(R.id.input_txt_email_user);

        spinnerGuichet = (Spinner) findViewById(R.id.spn_my_spinner_guichet1);
        tvGuichet = (TextView) findViewById(R.id.tv_guichet);

        rbGuichet = (RadioButton) findViewById(R.id.rbGuichet);
        rbCaisse = (RadioButton) findViewById(R.id.rbCaisse);
        spnNewProfil = (JRSpinner) findViewById(R.id.spnNewProfil);
        if (UserGuichetActivity.profilCaisseOrGuichet.equals("caisse")){
            spnNewProfil.setItems(getResources().getStringArray(R.array.array_profil));
        }else if (UserGuichetActivity.profilCaisseOrGuichet.equals("guichet")){
            spnNewProfil.setItems(getResources().getStringArray(R.array.array_profilGuichet));
        }
//        spnNewProfil.setItems(getResources().getStringArray(R.array.array_profil)); //this is important, you must set it to set the item list
        spnNewProfil.setTitle("Sélectionner un profil"); //change title of spinner-dialog programmatically
        spnNewProfil.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        spnNewProfil.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });
//onRadioButtonClicked(rbCaisse);

        guichetList = new ArrayList<Category>();
        // spinner item select listener
        spinnerGuichet.setOnItemSelectedListener(CreateUserGuichet.this);
        new GetGuichetsList().execute();

        deleteButton = (Button) findViewById(R.id.btn_delete_Ux);
        deleteButton.setVisibility(View.GONE);
        addButton = (Button) findViewById(R.id.btn_save_Ux);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(CreateUserGuichet.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addUserGuichet();
                } else {
                    Toast.makeText(CreateUserGuichet.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });


    }
    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Ajout d'un agent de guichet");

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
    public void onRadioButtonClicked(View view) {
        boolean checked1 = ((RadioButton) view).isChecked();
        String str = "";
        // Check which checkbox was clicked
        switch (view.getId()) {
//
            case R.id.rbCaisse:
                if (rbCaisse.isChecked()) {
//                    str = checked1?"Type Fixe sélectionné":"";
//                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
//                    CrPeriodCalcInteret = "M";
                    profilCaisseOrGuichet ="caisse";
                    spinnerGuichet.setVisibility(View.GONE);
                    tvGuichet.setVisibility(View.GONE);
                    spnNewProfil.setItems(getResources().getStringArray(R.array.array_profil)); //this is important, you must set it to set the item list
                }
                break;
            case R.id.rbGuichet:
                if (rbGuichet.isChecked()) {
//                    str = checked1?"Type Fixe sélectionné":"";
//                    ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
//                    CrPeriodCalcInteret = "J";
                    profilCaisseOrGuichet ="guichet";

                    spinnerGuichet.setVisibility(View.VISIBLE);
                    tvGuichet.setVisibility(View.VISIBLE);
                    spnNewProfil.setItems(getResources().getStringArray(R.array.array_profilGuichet)); //this is important, you must set it to set the item list

                }
                break;
        }
    }
    /**
     * Adding spinner data
     * */
    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();

        //tvCaisse.setText("");

        for (int i = 0; i < guichetList.size(); i++) {
            lables.add(guichetList.get(i).getName());//recupère les noms
            guichetListID.add(guichetList.get(i).getId()); //recupère les Id
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(CreateUserGuichet.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerGuichet.setAdapter(spinnerAdapter);
    }

    /**
     * Async task to get all food categories
     * */
    private class GetGuichetsList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogFetchGuichetsList = new ProgressDialog(CreateUserGuichet.this);
            pDialogFetchGuichetsList.setMessage("Fetching guichets's list..");
            pDialogFetchGuichetsList.setCancelable(false);
            pDialogFetchGuichetsList.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair(KEY_CAISSE_ID, String.valueOf(MyData.CAISSE_ID)));
            String json = (String) jsonParser.makeServiceCall( BASE_URL + "get_guichets_by_caisse_id.php", ServiceHandler.GET, httpParams);
           // String json = jsonParser.makeServiceCall(URL_GUICHETS, ServiceHandler.GET);



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
                            guichetList.add(cat);
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
            if (pDialogFetchGuichetsList.isShowing())
                pDialogFetchGuichetsList.dismiss();
            populateSpinner();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
     /*   Toast.makeText(
                getApplicationContext(),
                parent.getItemAtPosition(position).toString() + " Selected" ,
                Toast.LENGTH_LONG).show();
        */
        guichetID = guichetListID.get(position);//pour recuperer l'ID du guichet selectionnée

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }


    /**
     * Checks whether all files are filled. If so then calls AddUserAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addUserGuichet() {
        if (!STRING_EMPTY.equals(uxNomEditText.getText().toString()) &&
//                guichetID!=0 &&
//                !STRING_EMPTY.equals(uxProfilEditText.getText().toString()) &&
                !STRING_EMPTY.equals(uxPrenomEditText.getText().toString()) &&
                !STRING_EMPTY.equals(uxAdresseEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ccp_phone1.getFullNumberWithPlus()) &&
                !STRING_EMPTY.equals(uxLoginEditText.getText().toString()) &&
                !STRING_EMPTY.equals(uxPasswordEditText.getText().toString()) &&
                !STRING_EMPTY.equals(uxConfirmPasswordEditText.getText().toString()) &&
                !STRING_EMPTY.equals(uxEmailEditText.getText().toString())) {

            if ((uxPasswordEditText.getText().toString()).equals(uxConfirmPasswordEditText.getText().toString())){

                uxProfil = spnNewProfil.getText().toString();
                uxNom = uxNomEditText.getText().toString();
                uxPrenom = uxPrenomEditText.getText().toString();
                uxAdresse = uxAdresseEditText.getText().toString();


                uxTel1 = ccp_phone1.getFullNumberWithPlus();
                uxTel2 = ccp_phone2.getFullNumberWithPlus();
                uxTel3 = ccp_phone3.getFullNumberWithPlus();
                uxLogin = uxLoginEditText.getText().toString();
                uxPassword = uxPasswordEditText.getText().toString();
                uxBcryptPassword = BCrypt.hashpw(uxPassword, BCrypt.gensalt(12));
                //uxConfirmPassword = uxConfirmPasswordEditText.getText().toString();
                uxEmail = uxEmailEditText.getText().toString();
                new AddUserAsyncTask().execute();
            }else{
                Toast.makeText(CreateUserGuichet.this,
                        "Vos mots de passes ne correspondent pas!",
                        Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(CreateUserGuichet.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for adding a movie
     */
    private class AddUserAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(CreateUserGuichet.this);
            pDialog.setMessage("Adding User. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
           // httpParams.put(KEY_USER_GUICHET_ID, uxGuichetId);
            if (UserGuichetActivity.profilCaisseOrGuichet.equals("caisse")){
                httpParams.put(KEY_USER_GUICHET_ID, String.valueOf(MyData.CAISSE_ID));
            }else if (UserGuichetActivity.profilCaisseOrGuichet.equals("guichet")){
                httpParams.put(KEY_USER_GUICHET_ID, String.valueOf(MyData.GUICHET_ID));
            }

            httpParams.put(KEY_USER_PROFIL, uxProfil);
            httpParams.put(KEY_USER_NOM, uxNom);
            httpParams.put(KEY_USER_PRENOM, uxPrenom);
            httpParams.put(KEY_USER_ADRESSE, uxAdresse);
            httpParams.put(KEY_USER_TEL1, uxTel1);
            httpParams.put(KEY_USER_TEL2, uxTel2);
            httpParams.put(KEY_USER_TEL3, uxTel3);
            httpParams.put(KEY_USER_LOGIN, uxLogin);
            httpParams.put(KEY_USER_PASSWORD, uxPassword);
            httpParams.put(KEY_USER_BCRYPT_PASSWORD, uxBcryptPassword);
            httpParams.put(KEY_USER_EMAIL, uxEmail);
            httpParams.put(KEY_PROFIL_CAISSE_OR_GUICHET, UserGuichetActivity.profilCaisseOrGuichet);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_user_guichet.php", "POST", httpParams);
//            Log.e("**************************", String.valueOf(httpParams));
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
                        Toast.makeText(CreateUserGuichet.this,
                                "Utilisateur Ajouté", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CreateUserGuichet.this,
                                "Some error occurred while adding User",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}