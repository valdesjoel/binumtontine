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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.binumtontine.JRSpinner;
import com.example.binumtontine.R;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateUser extends AppCompatActivity {
    private static String STRING_EMPTY = "";
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_USER_ID = "ux_numero";
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

    //private static final String BASE_URL = "http://192.168.1.102/binumTontine/";
    private static final String BASE_URL = "http://binumt.diff-itc.net/binumTontine/";
    private String userId;
    private JRSpinner uxCaisseIdSpinner; //pour gérer le spinner contenant les caisses
    //private EditText uxCaisseIdEditText;
    private EditText uxProfilEditText;
    private EditText uxNomEditText;
    private EditText uxPrenomEditText;
    private EditText uxAdresseEditText;
    private EditText uxTel1EditText;
    private EditText uxTel2EditText;
    private EditText uxTel3EditText;
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
    private Button updateButton;
    private int success;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_user_caisse);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_user);
        setSupportActionBar(toolbar);
        setToolbarTitle();

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

        Intent intent = getIntent();


        uxProfilEditText = (EditText) findViewById(R.id.input_txt_profil_user);
        uxNomEditText = (EditText) findViewById(R.id.input_txt_Nom_user);
        uxPrenomEditText = (EditText) findViewById(R.id.input_txt_Prenom_user);
        uxAdresseEditText = (EditText) findViewById(R.id.input_txt_Adresse_user);
        uxTel1EditText = (EditText) findViewById(R.id.input_txt_Tel1_user);
        uxTel2EditText = (EditText) findViewById(R.id.input_txt_Tel2_user);
        uxTel3EditText = (EditText) findViewById(R.id.input_txt_Tel3_user);
        uxLoginEditText = (EditText) findViewById(R.id.input_txt_Login_user);
        uxPasswordEditText = (EditText) findViewById(R.id.input_txt_pwd_user);
        uxConfirmPasswordEditText = (EditText) findViewById(R.id.input_txt_confirm_pwd_user);
        uxEmailEditText = (EditText) findViewById(R.id.input_txt_email_user);

        userId = intent.getStringExtra(KEY_USER_ID);
        new UpdateUser.FetchMovieDetailsAsyncTask().execute();
        deleteButton = (Button) findViewById(R.id.btn_delete_Ux);
        deleteButton.setVisibility(View.VISIBLE);
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


    /**
     * Fetches single movie details from the server
     */
    private class FetchMovieDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateUser.this);
            pDialog.setMessage("Loading User Details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
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
                    uxCaisseId = user.getString(KEY_USER_CAISSE_ID);
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
                    uxCaisseIdSpinner.setText(uxCaisseId);
                    uxProfilEditText.setText(uxProfil);
                    uxNomEditText.setText(uxNom);
                    uxPrenomEditText.setText(uxPrenom);
                    uxAdresseEditText.setText(uxAdresse);
                    uxTel1EditText.setText(uxTel1);
                    uxTel2EditText.setText(uxTel2);
                    uxTel3EditText.setText(uxTel3);
                    uxLoginEditText.setText(uxLogin);
                    uxPasswordEditText.setText(uxPassword);
                    uxConfirmPasswordEditText.setText(uxConfirmPassword);
                    uxEmailEditText.setText(uxEmail);

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
                            //If the user confirms deletion, execute DeleteMovieAsyncTask
                            new UpdateUser.DeleteMovieAsyncTask().execute();
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
    private class DeleteMovieAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateUser.this);
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
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(UpdateUser.this,
                                "User Deleted", Toast.LENGTH_LONG).show();
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
                !STRING_EMPTY.equals(uxCaisseIdSpinner.getText().toString()) &&
                !STRING_EMPTY.equals(uxProfilEditText.getText().toString()) &&
                !STRING_EMPTY.equals(uxPrenomEditText.getText().toString()) &&
                !STRING_EMPTY.equals(uxAdresseEditText.getText().toString()) &&
                !STRING_EMPTY.equals(uxTel1EditText.getText().toString()) &&
                !STRING_EMPTY.equals(uxTel2EditText.getText().toString()) &&
                !STRING_EMPTY.equals(uxTel3EditText.getText().toString()) &&
                !STRING_EMPTY.equals(uxLoginEditText.getText().toString()) &&
                !STRING_EMPTY.equals(uxPasswordEditText.getText().toString()) &&
                !STRING_EMPTY.equals(uxConfirmPasswordEditText.getText().toString()) &&
                !STRING_EMPTY.equals(uxEmailEditText.getText().toString())) {

            if ((uxPasswordEditText.getText().toString()).equals(uxConfirmPasswordEditText.getText().toString())){
                uxCaisseId = uxCaisseIdSpinner.getText().toString();
                uxProfil = uxProfilEditText.getText().toString();
                uxNom = uxNomEditText.getText().toString();
                uxPrenom = uxPrenomEditText.getText().toString();
                uxAdresse = uxAdresseEditText.getText().toString();
                uxTel1 = uxTel1EditText.getText().toString();
                uxTel2 = uxTel2EditText.getText().toString();
                uxTel3 = uxTel3EditText.getText().toString();
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
            pDialog = new ProgressDialog(UpdateUser.this);
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
            httpParams.put(KEY_USER_ID, userId);
            httpParams.put(KEY_USER_CAISSE_ID, uxCaisseId);
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
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(UpdateUser.this,
                                "User Updated", Toast.LENGTH_LONG).show();
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
