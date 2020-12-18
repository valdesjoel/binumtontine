package com.example.binumtontine.activity;

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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.binumtontine.JRSpinner;
import com.example.binumtontine.R;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateUser extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SERVER_ADDRESS {
    private static String STRING_EMPTY = "";
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_USER_ID = "ux_numero";
   // private static final String KEY_USER_CAISSE_DENOMINATION = "cx_numero";cx_denomination
    private static final String KEY_USER_CAISSE_ID = "cx_numero";
    private static final String KEY_USER_CAISSE_DENOMINATION = "cx_denomination";
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

    private String userId;
    private JRSpinner uxCaisseIdSpinner; //pour gérer le spinner contenant les caisses
    //private EditText uxCaisseIdEditText;
    private EditText uxProfilEditText;
    private EditText uxNomEditText;
    private EditText uxPrenomEditText;
    private EditText uxAdresseEditText;
//    private EditText uxTel1EditText;
//    private EditText uxTel2EditText;
//    private EditText uxTel3EditText;


    private CountryCodePicker ccp_phone1;
    private CountryCodePicker ccp_phone2;
    private CountryCodePicker ccp_phone3;
    private EditText editTextCarrierPhone1;
    private EditText editTextCarrierPhone2;
    private EditText editTextCarrierPhone3;


    private EditText uxLoginEditText;
    private EditText uxPasswordEditText;
    private EditText uxConfirmPasswordEditText;
    private EditText uxEmailEditText;

    private String uxCaisseDenomination;
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

    private ArrayList<Category> caisseList;
    List<Integer> caisseListID = new ArrayList<Integer>();
    private int caisseID;
    private Spinner spinnerCaisse;
    private Button deleteButton;
    private Button updateButton;
    private Button cancelButton;
    private int success;
    private ProgressDialog pDialogGetUserCaisseDetails;
    private ProgressDialog pDialogFetchCaisse;
    private ProgressDialog pDialogUpdateUserCaisse;
    private ProgressDialog pDialogDeleteUserCaisse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_user_caisse);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_user);
        setSupportActionBar(toolbar);
        setToolbarTitle();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        uxCaisseIdSpinner = (JRSpinner) findViewById(R.id.spn_my_spinner_caisse);

        uxCaisseIdSpinner.setItems(getResources().getStringArray(R.array.array_caisse)); //this is important, you must set it to set the item list
        uxCaisseIdSpinner.setTitle("Sélectionner une caisse"); //change title of spinner-dialog programmatically
        uxCaisseIdSpinner.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically

        uxCaisseIdSpinner.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });




        uxProfilEditText = (EditText) findViewById(R.id.input_txt_profil_user);
        uxNomEditText = (EditText) findViewById(R.id.input_txt_Nom_user);
        uxPrenomEditText = (EditText) findViewById(R.id.input_txt_Prenom_user);
        uxAdresseEditText = (EditText) findViewById(R.id.input_txt_Adresse_user);

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

        spinnerCaisse = (Spinner) findViewById(R.id.spn_my_spinner_caisse1);
        caisseList = new ArrayList<Category>();
        // spinner item select listener
        spinnerCaisse.setOnItemSelectedListener(UpdateUser.this);


        Intent intent = getIntent();
        userId = intent.getStringExtra(KEY_USER_ID);
        new UpdateUser.FetchUserCaisseDetailsAsyncTask().execute(); //to Fetch user caisse details
        new GetCaissesList().execute(); //to get caisseList
        deleteButton = (Button) findViewById(R.id.btn_delete_Ux);
        deleteButton.setVisibility(View.VISIBLE);
        cancelButton = (Button) findViewById(R.id.btn_clean);
        cancelButton.setVisibility(View.VISIBLE);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();

                } else {
                    Toast.makeText(UpdateUser.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete();
            }
        });
        updateButton = (Button) findViewById(R.id.btn_save_Ux);
        updateButton.setText("MODIFIER");
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    updateUser();

                } else {
                    Toast.makeText(UpdateUser.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

    }
    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Mise à jour d'un utilisateur");

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    /**
     * Async task to get all caisses in organe faitier
     * */
    private class GetCaissesList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogFetchCaisse = new ProgressDialog(UpdateUser.this);
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
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
       /* Toast.makeText(
                getApplicationContext(),
                parent.getItemAtPosition(position).toString() + " Selected" ,
                Toast.LENGTH_LONG).show(); */
        caisseID = caisseListID.get(position);//pour recuperer l'ID de la caisse selectionnée

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
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
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(UpdateUser.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerCaisse.setAdapter(spinnerAdapter);
        //Make uxCaisseDenomination as default Item in caisseList spinner
        spinnerCaisse.setSelection(spinnerAdapter.getPosition(uxCaisseDenomination));
    }



    /**
     * Fetches single userCaisse details from the server
     */
    private class FetchUserCaisseDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialogGetUserCaisseDetails = new ProgressDialog(UpdateUser.this);
            pDialogGetUserCaisseDetails.setMessage("Loading User Details. Please wait...");
            pDialogGetUserCaisseDetails.setIndeterminate(false);
            pDialogGetUserCaisseDetails.setCancelable(false);
            pDialogGetUserCaisseDetails.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_USER_ID, userId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_user_details.php", "GET", httpParams);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject user;
                if (success == 1) {
                    //Parse the JSON response
                    user = jsonObject.getJSONObject(KEY_DATA);
                    uxCaisseDenomination = user.getString(KEY_USER_CAISSE_DENOMINATION);
                    uxProfil = user.getString(KEY_USER_PROFIL);
                    uxNom = user.getString(KEY_USER_NOM);
                    uxPrenom = user.getString(KEY_USER_PRENOM);
                    uxAdresse = user.getString(KEY_USER_ADRESSE);
                    uxTel1 = user.getString(KEY_USER_TEL1);
                    uxTel2 = user.getString(KEY_USER_TEL2);
                    uxTel3 = user.getString(KEY_USER_TEL3);
                    uxLogin = user.getString(KEY_USER_LOGIN);
                    uxPassword = user.getString(KEY_USER_PASSWORD);
                    uxConfirmPassword = user.getString(KEY_USER_PASSWORD);
                    uxEmail = user.getString(KEY_USER_EMAIL);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialogGetUserCaisseDetails.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    //Populate the Edit Texts once the network activity is finished executing
                    try {
                        uxCaisseIdSpinner.setText(uxCaisseDenomination); //pas indispensable car on le fait directement dans populate
                        uxProfilEditText.setText(uxProfil);
                        uxNomEditText.setText(uxNom);
                        uxPrenomEditText.setText(uxPrenom);
                        uxAdresseEditText.setText(uxAdresse);
                        ccp_phone1.setFullNumber(uxTel1);
                        ccp_phone2.setFullNumber(uxTel2);
                        ccp_phone3.setFullNumber(uxTel3);

                        uxLoginEditText.setText(uxLogin);
                        uxPasswordEditText.setText(uxPassword);
                        uxConfirmPasswordEditText.setText(uxConfirmPassword);
                        uxEmailEditText.setText(uxEmail);
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
                UpdateUser.this);
        alertDialogBuilder.setMessage("Are you sure, you want to delete this user?");
        alertDialogBuilder.setPositiveButton("Delete",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                            //If the user confirms deletion, execute DeleteUserCaisseAsyncTask
                            new DeleteUserCaisseAsyncTask().execute();
                        } else {
                            Toast.makeText(UpdateUser.this,
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
     * AsyncTask to delete a movie
     */
    private class DeleteUserCaisseAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialogDeleteUserCaisse = new ProgressDialog(UpdateUser.this);
            pDialogDeleteUserCaisse.setMessage("Deleting user. Please wait...");
            pDialogDeleteUserCaisse.setIndeterminate(false);
            pDialogDeleteUserCaisse.setCancelable(false);
            pDialogDeleteUserCaisse.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Set movie_id parameter in request
            httpParams.put(KEY_USER_ID, userId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "delete_user.php", "POST", httpParams);
            try {
                success = jsonObject.getInt(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialogDeleteUserCaisse.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(UpdateUser.this,
                                "User "+uxNom+" Deleted", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie deletion
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(UpdateUser.this,
                                "Some error occurred while deleting user",
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
    private void updateUser() {


        if (!STRING_EMPTY.equals(uxNomEditText.getText().toString()) &&
                caisseID!=0 &&
                !STRING_EMPTY.equals(uxProfilEditText.getText().toString()) &&
                !STRING_EMPTY.equals(uxPrenomEditText.getText().toString()) &&
                !STRING_EMPTY.equals(uxAdresseEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ccp_phone1.getFullNumberWithPlus()) &&
                !STRING_EMPTY.equals(uxLoginEditText.getText().toString()) &&
                !STRING_EMPTY.equals(uxPasswordEditText.getText().toString()) &&
                !STRING_EMPTY.equals(uxConfirmPasswordEditText.getText().toString()) &&
                !STRING_EMPTY.equals(uxEmailEditText.getText().toString())) {

            if ((uxPasswordEditText.getText().toString()).equals(uxConfirmPasswordEditText.getText().toString())){
                uxCaisseDenomination = uxCaisseIdSpinner.getText().toString();
                uxProfil = uxProfilEditText.getText().toString();
                uxNom = uxNomEditText.getText().toString();
                uxPrenom = uxPrenomEditText.getText().toString();
                uxAdresse = uxAdresseEditText.getText().toString();
                uxTel1 = ccp_phone1.getFullNumberWithPlus();
                uxTel2 = ccp_phone2.getFullNumberWithPlus();
                uxTel3 = ccp_phone3.getFullNumberWithPlus();
                uxLogin = uxLoginEditText.getText().toString();
                uxPassword = uxPasswordEditText.getText().toString();
                //uxConfirmPassword = uxConfirmPasswordEditText.getText().toString();
                uxEmail = uxEmailEditText.getText().toString();
                new UpdateUser.UpdateMovieAsyncTask().execute();
            }else{
                Toast.makeText(UpdateUser.this,
                        "Vos mots de passes ne correspondent pas!",
                        Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(UpdateUser.this,
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
            pDialogUpdateUserCaisse = new ProgressDialog(UpdateUser.this);
            pDialogUpdateUserCaisse.setMessage("Updating User. Please wait...");
            pDialogUpdateUserCaisse.setIndeterminate(false);
            pDialogUpdateUserCaisse.setCancelable(false);
            pDialogUpdateUserCaisse.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_USER_ID, userId);
            //httpParams.put(KEY_USER_CAISSE_DENOMINATION, uxCaisseDenomination);
            httpParams.put(KEY_USER_CAISSE_ID, String.valueOf(caisseID));
            //httpParams.put(KEY_USER_CAISSE_DENOMINATION, uxCaisseDenomination);
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
                    BASE_URL + "update_user.php", "POST", httpParams);
            try {
                success = jsonObject.getInt(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialogUpdateUserCaisse.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(UpdateUser.this,
                                "User "+uxNom+" Updated", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(UpdateUser.this,
                                "Some error occurred while updating User",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}
