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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.binumtontine.JRSpinner;
import com.example.binumtontine.R;
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

import android.widget.AdapterView.OnItemSelectedListener;

public class CreateUser extends AppCompatActivity implements OnItemSelectedListener, SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";

    private static final String KEY_USER_CAISSE_ID = "cx_numero";
    private static final String KEY_USER_PROFIL = "ux_profil";
    private static final String KEY_USER_NOM = "ux_nom";
    private static final String KEY_USER_PRENOM = "ux_prenom";
    private static final String KEY_USER_ADRESSE = "ux_adresse";
    private static final String KEY_USER_TEL1 = "ux_tel1";
    private static final String KEY_USER_TEL2 = "ux_tel2";
    private static final String KEY_USER_TEL3 = "ux_tel3";
    private static final String KEY_USER_LOGIN = "ux_login";
    private static final String KEY_USER_PASSWORD = "ux_password";
    private static final String KEY_USER_EMAIL = "ux_email";



    private static String STRING_EMPTY = "";

    private JRSpinner uxCaisseIdSpinner; //pour gérer le spinner contenant les caisses
    private EditText uxProfilEditText;
    private EditText uxNomEditText;
    private EditText uxPrenomEditText;
    private EditText uxAdresseEditText;


    private CountryCodePicker ccp_phone1;
    private CountryCodePicker ccp_phone2;
    private CountryCodePicker ccp_phone3;
    private EditText editTextCarrierPhone1;
    private EditText editTextCarrierPhone2;
    private EditText editTextCarrierPhone3;

    /*private EditText uxTel1EditText;
    private EditText uxTel2EditText;
    private EditText uxTel3EditText; */
    private EditText uxLoginEditText;
    private EditText uxPasswordEditText;
    private EditText uxConfirmPasswordEditText;
    private EditText uxEmailEditText;

    private String uxCaisseId;
    private String uxProfil;
    private String uxNom;
    private String uxPrenom;
    private String uxAdresse;
    private String uxTel1;
    private String uxTel2;
    private String uxTel3;
    private String uxLogin;
    private String uxPassword;
    private String uxConfirmPassword;
    private String uxEmail;

    private Button deleteButton;
    private Button addButton;
    private Button cancelButton;
    private int success;
    private ProgressDialog pDialogCreateUserCaisse;
    private ProgressDialog pDialogFetchCaisse;

    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Category> caisseList;
    List<Integer> caisseListID = new ArrayList<Integer>();
    private int caisseID;
    private Spinner spinnerCaisse;
    private Button btnAddNewCategory;
    private TextView txtCategory ;
    // API urls
    // Url to create new category
    private String URL_NEW_CATEGORY = "http://10.0.2.2/food_api/new_category.php";
    // Url to get all categories
    //private String URL_CATEGORIES = "http://10.0.2.2/food_api/get_categories.php";
//    private static final String URL_CATEGORIES = "http://binumt.diff-itc.net/binumTontine/get_categories.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_user_caisse);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_user);
        setSupportActionBar(toolbar);
        setToolbarTitle();


        btnAddNewCategory = (Button) findViewById(R.id.btn_save_Ux);
        spinnerCaisse = (Spinner) findViewById(R.id.spn_my_spinner_caisse1);
        txtCategory = (TextView) findViewById(R.id.txtCategory);

        caisseList = new ArrayList<Category>();
        // spinner item select listener
        spinnerCaisse.setOnItemSelectedListener(CreateUser.this);
        // Add new category click event


        new CreateUser.GetCategories().execute();

          /*  mySpinner.setOnSelectMultipleListener(new JRSpinner.OnSelectMultipleListener() {
            @Override
            public void onMultipleSelected(List<Integer> selectedPosition) {
                //do what you want to selected position list
            }
        });*/

        uxProfilEditText = (EditText) findViewById(R.id.input_txt_profil_user);
        uxNomEditText = (EditText) findViewById(R.id.input_txt_Nom_user);
        alreadyUpperCase(uxNomEditText);
        uxPrenomEditText = (EditText) findViewById(R.id.input_txt_Prenom_user);
        uxAdresseEditText = (EditText) findViewById(R.id.input_txt_Adresse_user);
//        uxTel1EditText = (EditText) findViewById(R.id.input_txt_Tel1_user);
//        uxTel2EditText = (EditText) findViewById(R.id.input_txt_Tel2_user);
//        uxTel3EditText = (EditText) findViewById(R.id.input_txt_Tel3_user);

        ccp_phone1 = (CountryCodePicker) findViewById(R.id.ccp_phone1);
        editTextCarrierPhone1 = (EditText) findViewById(R.id.editText_carrierPhone1);
        ccp_phone1.registerCarrierNumberEditText(editTextCarrierPhone1);


        ccp_phone2 = (CountryCodePicker) findViewById(R.id.ccp_phone2);
        editTextCarrierPhone2 = (EditText) findViewById(R.id.editText_carrierPhone2);
        ccp_phone2.registerCarrierNumberEditText(editTextCarrierPhone2);

        ccp_phone3 = (CountryCodePicker) findViewById(R.id.ccp_phone3);
        editTextCarrierPhone3 = (EditText) findViewById(R.id.editText_carrierPhone3);
        ccp_phone3.registerCarrierNumberEditText(editTextCarrierPhone3);



        uxLoginEditText = (EditText) findViewById(R.id.input_txt_Login_user);
        uxPasswordEditText = (EditText) findViewById(R.id.input_txt_pwd_user);
        uxConfirmPasswordEditText = (EditText) findViewById(R.id.input_txt_confirm_pwd_user);
        uxEmailEditText = (EditText) findViewById(R.id.input_txt_email_user);

        deleteButton = (Button) findViewById(R.id.btn_delete_Ux);
        deleteButton.setVisibility(View.GONE);
        addButton = (Button) findViewById(R.id.btn_save_Ux);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addUserCaisse();
                } else {
                    Toast.makeText(CreateUser.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        cancelButton = (Button) findViewById(R.id.btn_clean);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(CreateUser.this,
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
     * Adding spinner data
     * */
    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();

        //txtCategory.setText("");

        for (int i = 0; i < caisseList.size(); i++) {
            lables.add(caisseList.get(i).getName());//recupère les noms
            caisseListID.add(caisseList.get(i).getId()); //recupère les Id
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(CreateUser.this,
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
            pDialogFetchCaisse = new ProgressDialog(CreateUser.this);
            pDialogFetchCaisse.setMessage("Fetching caisse's list..");
            pDialogFetchCaisse.setCancelable(false);
            pDialogFetchCaisse.show();

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
            if (pDialogFetchCaisse.isShowing())
                pDialogFetchCaisse.dismiss();
            populateSpinner();
        }

    }

    /**
     * Async task to create a new food category
     * */
    private class AddNewCategory extends AsyncTask<String, Void, Void> {

        boolean isNewCategoryCreated = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogCreateUserCaisse = new ProgressDialog(CreateUser.this);
            pDialogCreateUserCaisse.setMessage("Creating new category..");
            pDialogCreateUserCaisse.setCancelable(false);
            pDialogCreateUserCaisse.show();

        }

        @Override
        protected Void doInBackground(String... arg) {

            String newCategory = arg[0];

            // Preparing post params
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", newCategory));

            ServiceHandler serviceClient = new ServiceHandler();

            String json = serviceClient.makeServiceCall(URL_NEW_CATEGORY,
                    ServiceHandler.POST, params);

            Log.d("Create Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    boolean error = jsonObj.getBoolean("error");
                    // checking for error node in json
                    if (!error) {
                        // new category created successfully
                        isNewCategoryCreated = true;
                    } else {
                        Log.e("Create Category Error: ", "> " + jsonObj.getString("message"));
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
            if (pDialogCreateUserCaisse.isShowing())
                pDialogCreateUserCaisse.dismiss();
            if (isNewCategoryCreated) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // fetching all categories
                        new GetCategories().execute();
                    }
                });
            }
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
        getSupportActionBar().setTitle("Ajout d'un admin de caisse");

    }

    /**
     * Checks whether all files are filled. If so then calls AddUserCaisseAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addUserCaisse() {
        if (!STRING_EMPTY.equals(uxNomEditText.getText().toString()) &&
                caisseID!=0 &&
                !STRING_EMPTY.equals(uxProfilEditText.getText().toString()) &&
               // !STRING_EMPTY.equals(uxPrenomEditText.getText().toString()) &&
                !STRING_EMPTY.equals(uxAdresseEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ccp_phone1.getFullNumberWithPlus()) &&
                //!STRING_EMPTY.equals(uxTel2EditText.getText().toString()) &&
                //!STRING_EMPTY.equals(uxTel3EditText.getText().toString()) &&
                !STRING_EMPTY.equals(uxLoginEditText.getText().toString()) &&
                !STRING_EMPTY.equals(uxPasswordEditText.getText().toString()) &&
                !STRING_EMPTY.equals(uxConfirmPasswordEditText.getText().toString()) &&
                !STRING_EMPTY.equals(uxEmailEditText.getText().toString())) {

            if ((uxPasswordEditText.getText().toString()).equals(uxConfirmPasswordEditText.getText().toString())){
                uxCaisseId = spinnerCaisse.getSelectedItem().toString();
                uxProfil = uxProfilEditText.getText().toString();
                uxNom = uxNomEditText.getText().toString();
                uxPrenom = uxPrenomEditText.getText().toString();
                uxAdresse = uxAdresseEditText.getText().toString();
//                uxTel1 = uxTel1EditText.getText().toString();
//                uxTel2 = uxTel2EditText.getText().toString();
//                uxTel3 = uxTel3EditText.getText().toString();


                uxTel1 = ccp_phone1.getFullNumberWithPlus();
                uxTel2 = ccp_phone2.getFullNumberWithPlus();
                uxTel3 = ccp_phone3.getFullNumberWithPlus();

                uxLogin = uxLoginEditText.getText().toString();
                uxPassword = uxPasswordEditText.getText().toString();
                //uxConfirmPassword = uxConfirmPasswordEditText.getText().toString();
                uxEmail = uxEmailEditText.getText().toString();
                new AddUserCaisseAsyncTask().execute();
            }else{
                Toast.makeText(CreateUser.this,
                        "Vos mots de passes ne correspondent pas!",
                        Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(CreateUser.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for adding a userCaisse
     */
    private class AddUserCaisseAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialogCreateUserCaisse = new ProgressDialog(CreateUser.this);
            pDialogCreateUserCaisse.setMessage("Adding User. Please wait...");
            pDialogCreateUserCaisse.setIndeterminate(false);
            pDialogCreateUserCaisse.setCancelable(false);
            pDialogCreateUserCaisse.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            //httpParams.put(KEY_USER_CAISSE_ID, uxCaisseId);
            httpParams.put(KEY_USER_CAISSE_ID, String.valueOf(caisseID));
            httpParams.put(KEY_USER_PROFIL, uxProfil);
            httpParams.put(KEY_USER_NOM, uxNom);
            httpParams.put(KEY_USER_PRENOM, uxPrenom);
            httpParams.put(KEY_USER_ADRESSE, uxAdresse);
            httpParams.put(KEY_USER_TEL1, uxTel1);
            httpParams.put(KEY_USER_TEL2, uxTel2);
            httpParams.put(KEY_USER_TEL3, uxTel3);
            httpParams.put(KEY_USER_LOGIN, uxLogin);
            httpParams.put(KEY_USER_PASSWORD, uxPassword);
            httpParams.put(KEY_USER_EMAIL, uxEmail);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_user.php", "POST", httpParams);
            try {
                success = jsonObject.getInt(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialogCreateUserCaisse.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(CreateUser.this,
                                "Utilisateur "+uxNom+" ajouté", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CreateUser.this,
                                "Some error occurred while adding User",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}