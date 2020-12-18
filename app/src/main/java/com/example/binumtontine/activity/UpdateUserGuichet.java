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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.JRSpinner;
import com.example.binumtontine.R;
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

public class UpdateUserGuichet extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SERVER_ADDRESS {
    private static String STRING_EMPTY = "";
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";

    private static final String KEY_USER_ID = "ux_numero";
    private static final String KEY_USER_GUICHET_ID = "gx_numero";
    private static final String KEY_USER_GUICHET_DENOMINATION = "gx_denomination";
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
    private static final String KEY_USER_EMAIL = "ux_email";
    private static final String KEY_PROFIL_CAISSE_OR_GUICHET = "profilCaisseOrGuichet";

    private String userGuichetId;
    private JRSpinner uxGuichetIdSpinner; //pour gérer le spinner contenant les guichets
    private Spinner spinnerGuichet;
    private ArrayList<Category> guichetList;
    List<Integer> guichetListID = new ArrayList<Integer>();
    private int guichetID;
    //private EditText uxCaisseIdEditText;
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

    private EditText uxLoginEditText;
    private EditText uxPasswordEditText;
    private EditText uxConfirmPasswordEditText;
    private EditText uxEmailEditText;

    private String uxGuichetId;
    private String uxGuichetDenomination;
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
    private Button updateButton;
    private Button annulerButton;
    private int success;
    private ProgressDialog pDialog;
    private ProgressDialog pDialogFetchGuichetsList;

    private RadioButton rbGuichet;
    private RadioButton rbCaisse;
    private String profilCaisseOrGuichet;
    private JRSpinner spnNewProfil;
    private TextView tvGuichet;
    private TextView header_user_guichet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_guichet);


        Intent intent = getIntent();


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


        spinnerGuichet = (Spinner) findViewById(R.id.spn_my_spinner_guichet1);

        tvGuichet = (TextView) findViewById(R.id.tv_guichet);
        header_user_guichet = (TextView) findViewById(R.id.header_user_guichet);
        header_user_guichet.setText("Mise à jour du profil utilisateur");

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
//        onRadioButtonClicked(rbCaisse);

        guichetList = new ArrayList<Category>();
        // spinner item select listener
        spinnerGuichet.setOnItemSelectedListener(UpdateUserGuichet.this);

        userGuichetId = intent.getStringExtra(KEY_USER_ID);
        new UpdateUserGuichet.FetchUserGuichetDetailsAsyncTask().execute();
        new UpdateUserGuichet.GetGuichetsList().execute();
        deleteButton = (Button) findViewById(R.id.btn_delete_Ux);
        deleteButton.setVisibility(View.VISIBLE);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete();
            }
        });
        updateButton = (Button) findViewById(R.id.btn_save_Ux);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        annulerButton.setVisibility(View.VISIBLE);
        updateButton.setText("MODIFIER");
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    updateUser();

                } else {
                    Toast.makeText(UpdateUserGuichet.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();

                } else {
                    Toast.makeText(UpdateUserGuichet.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });


    }
    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Mise à jour d'un utilisateur");

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
     * Fetches single movie details from the server
     */
    private class FetchUserGuichetDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateUserGuichet.this);
            pDialog.setMessage("Loading User Details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_USER_ID, userGuichetId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_user_guichet_details.php", "GET", httpParams);
            try {
                Log.e("httpParams",httpParams+"");
                Log.e("jsonObject",jsonObject+"");
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject user;
                if (success == 1) {
                    //Parse the JSON response
                    user = jsonObject.getJSONObject(KEY_DATA);
                    //uxGuichetId = user.getString(KEY_USER_GUICHET_ID);
                    uxGuichetDenomination = user.getString(KEY_USER_GUICHET_DENOMINATION);
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
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    //Populate the Edit Texts once the network activity is finished executing
                   // uxGuichetIdSpinner.setText(uxGuichetDenomination);  //pas indispensable car on le fait directement dans populate
//                     uxProfilEditText.setText(uxProfil); OLD
                    try {
                        spnNewProfil.setText(uxProfil);
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
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(UpdateUserGuichet.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerGuichet.setAdapter(spinnerAdapter);
        //Make uxCaisseDenomination as default Item in caisseList spinner
        spinnerGuichet.setSelection(spinnerAdapter.getPosition(uxGuichetDenomination));
    }
    /**
     * Async task to get all guichet in caisse
     * */
    private class GetGuichetsList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogFetchGuichetsList = new ProgressDialog(UpdateUserGuichet.this);
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
     * Displays an alert dialogue to confirm the deletion
     */
    private void confirmDelete() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                UpdateUserGuichet.this);
        alertDialogBuilder.setMessage("Are you sure, you want to delete this user?");
        alertDialogBuilder.setPositiveButton("Delete",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                            //If the user confirms deletion, execute DeleteUserGuichetAsyncTask
                            new DeleteUserGuichetAsyncTask().execute();
                        } else {
                            Toast.makeText(UpdateUserGuichet.this,
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
    private class DeleteUserGuichetAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateUserGuichet.this);
            pDialog.setMessage("Deleting user. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Set movie_id parameter in request
            httpParams.put(KEY_USER_ID, userGuichetId);
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
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(UpdateUserGuichet.this,
                                "User Deleted", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie deletion
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(UpdateUserGuichet.this,
                                "Some error occurred while deleting user",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    /**
     * Checks whether all files are filled. If so then calls UpdateUserGuichetAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void updateUser() {


        if (!STRING_EMPTY.equals(uxNomEditText.getText().toString()) &&
//                guichetID!=0 &&
//                !STRING_EMPTY.equals(uxProfilEditText.getText().toString()) &&
                !STRING_EMPTY.equals(uxAdresseEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ccp_phone1.getFullNumberWithPlus()) &&
                !STRING_EMPTY.equals(uxLoginEditText.getText().toString()) &&
                !STRING_EMPTY.equals(uxPasswordEditText.getText().toString()) &&
                !STRING_EMPTY.equals(uxConfirmPasswordEditText.getText().toString()) &&
                !STRING_EMPTY.equals(uxEmailEditText.getText().toString())) {

            if ((uxPasswordEditText.getText().toString()).equals(uxConfirmPasswordEditText.getText().toString())){
              //  uxGuichetId = uxGuichetIdSpinner.getText().toString();
//                uxGuichetDenomination = uxGuichetIdSpinner.getText().toString(); //pas nécessaire
//                uxProfil = uxProfilEditText.getText().toString();//OLD
                uxProfil = spnNewProfil.getText().toString();//NEW
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
                new UpdateUserGuichetAsyncTask().execute();
            }else{
                Toast.makeText(UpdateUserGuichet.this,
                        "Vos mots de passes ne correspondent pas!",
                        Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(UpdateUserGuichet.this,
                    "One or more fields left empty!",
                    Toast.LENGTH_LONG).show();

        }


    }
    /**
     * AsyncTask for updating a movie details
     */

    private class UpdateUserGuichetAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateUserGuichet.this);
            pDialog.setMessage("Updating User. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            if (UserGuichetActivity.profilCaisseOrGuichet.equals("caisse")){
                httpParams.put(KEY_USER_GUICHET_ID, String.valueOf(MyData.CAISSE_ID));
            }else if (UserGuichetActivity.profilCaisseOrGuichet.equals("guichet")){
//                httpParams.put(KEY_USER_GUICHET_ID, String.valueOf(guichetID));
                httpParams.put(KEY_USER_GUICHET_ID, String.valueOf(MyData.GUICHET_ID));
            }
            httpParams.put(KEY_USER_ID, userGuichetId);
//            httpParams.put(KEY_USER_GUICHET_ID, String.valueOf(guichetID));
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
            httpParams.put(KEY_PROFIL_CAISSE_OR_GUICHET, UserGuichetActivity.profilCaisseOrGuichet);//NEW

            Log.e("Response: ", "> " + UserGuichetActivity.profilCaisseOrGuichet+"\n"+
                    String.valueOf(MyData.CAISSE_ID)+"\n"+
                    String.valueOf(MyData.GUICHET_ID)+"\n"+
                    userGuichetId+"\n"+
                    uxProfil+"\n"+
                    uxNom+"\n"+
                    uxPrenom+"\n"+
                    uxAdresse+"\n"+
                    uxTel1+"\n"+
                    uxTel2+"\n"+
                    uxTel3+"\n"+
                    uxLogin+"\n"+
                    uxPassword+"\n"+
                    uxEmail
                    );
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "update_user_guichet.php", "POST", httpParams);
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
                        Toast.makeText(UpdateUserGuichet.this,
                                "User "+uxNom+" Updated", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(UpdateUserGuichet.this,
                                "Some error occurred while updating User",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}
