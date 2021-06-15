package com.example.binumtontine.activity.parametrageGuichet;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.example.binumtontine.modele.Produit;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class UpdateProduitGx extends AppCompatActivity implements  SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private EditText PdNom_ET;
    private EditText PdPrixUnit_ET;
    private EditText PdQteStock_ET;
    private EditText PdStockMin_ET;

    private RadioButton rb_PdIsOnNon;
    private RadioButton rb_PdIsOnOui;

    private TextInputLayout til_PdNom;
    private TextInputLayout til_PdPrixUnit;
    private TextInputLayout til_PdQteStock;
    private TextInputLayout til_PdStockMin;

    private static String STRING_EMPTY = "";
    Produit produit;


    private TextView headerProduitCreditTextView;

    private String produitCreditID;


    private CircularProgressButton updateButton;
    private CircularProgressButton annulerButton;
    private CircularProgressButton deleteButton;
    private int success;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit_gx);

        Intent intent = getIntent();
        headerProduitCreditTextView = (TextView) findViewById(R.id.tv_header_produit_credit);

        headerProduitCreditTextView.setText("Mise à jour d'un produit de créditt");

        PdNom_ET = (EditText) findViewById(R.id.input_txt_PdNom);
        PdPrixUnit_ET = (EditText) findViewById(R.id.input_txt_PdPrixUnit);
        PdQteStock_ET = (EditText) findViewById(R.id.input_txt_PdQteStock);
        PdStockMin_ET = (EditText) findViewById(R.id.input_txt_PdStockMin);

        rb_PdIsOnOui = (RadioButton) findViewById(R.id.rb_PdIsOn_oui);
        rb_PdIsOnNon = (RadioButton) findViewById(R.id.rb_PdIsOn_non);

        til_PdNom = (TextInputLayout) findViewById(R.id.input_layout_PdNom);
        til_PdPrixUnit = (TextInputLayout) findViewById(R.id.input_layout_PdPrixUnit);
        til_PdQteStock = (TextInputLayout) findViewById(R.id.input_layout_PdQteStock);
        til_PdStockMin = (TextInputLayout) findViewById(R.id.input_layout_PdStockMin);

        produitCreditID = intent.getStringExtra(Produit.KEY_PdNumero);

        new FetchProduitDetailsAsyncTask().execute();
        deleteButton = (CircularProgressButton) findViewById(R.id.btn_delete_produit);
        deleteButton.setVisibility(View.VISIBLE);
        annulerButton = (CircularProgressButton) findViewById(R.id.btn_clean);
        annulerButton.setVisibility(View.VISIBLE);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete();
            }
        });
        updateButton = (CircularProgressButton) findViewById(R.id.btn_save_produit);
        updateButton.setText("MODIFIER");
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    updateProduitCredit();

                } else {
                    Toast.makeText(UpdateProduitGx.this,
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
                    Toast.makeText(UpdateProduitGx.this,
                            "Unable to connect to internet",
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
     * Fetches single movie details from the server
     */
    private class FetchProduitDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateProduitGx.this);
            pDialog.setMessage("Chargement des détails sur le produit de crédit. Veuillez patienter svp...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(Produit.KEY_PdNumero, produitCreditID);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_produit_gx_details.php", "GET", httpParams);
            try {
                produit = new Produit();
                    //Parse the JSON response
                produit.setPdNom(jsonObject.getString(Produit.KEY_PdNom));
                produit.setPdPrixUnit(jsonObject.getString(Produit.KEY_PdPrixUnit));
                produit.setPdQteStock(jsonObject.getString(Produit.KEY_PdQteStock));
                produit.setPdStockMin(jsonObject.getString(Produit.KEY_PdStockMin));
                produit.setPdGuichet(jsonObject.getString(Produit.KEY_PdGuichet));
                produit.setPdUserCree(jsonObject.getString(Produit.KEY_PdUserCree));
                produit.setPdIsOn(jsonObject.getString(Produit.KEY_PdIsOn));

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

                    PdNom_ET.setText(produit.getPdNom());
                    PdPrixUnit_ET.setText(produit.getPdPrixUnit());
                    PdQteStock_ET.setText(produit.getPdQteStock());
                    PdStockMin_ET.setText(produit.getPdStockMin());
                    if (produit.getPdIsOn().equals("Y")){
                        rb_PdIsOnOui.setChecked(true);
                    }else{
                        rb_PdIsOnNon.setChecked(true);
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
                UpdateProduitGx.this);
        alertDialogBuilder.setMessage("Are you sure, you want to delete this produit crédit?");
        alertDialogBuilder.setPositiveButton("Supprimer",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                            //If the user confirms deletion, execute DeleteProduitCreditAsyncTask
                            new DeleteProduitCreditAsyncTask().execute();
                        } else {
                            Toast.makeText(UpdateProduitGx.this,
                                    "Unable to connect to internet",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
        alertDialogBuilder.setNegativeButton("Annuler", null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * AsyncTask to delete a guichet
     */
    private class DeleteProduitCreditAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateProduitGx.this);
            pDialog.setMessage("Suppression du produit Crédit. Veuillez patienter...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            deleteButton.startAnimation() ;// to start animation on button delete
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Set guichet_id parameter in request
            httpParams.put(Produit.KEY_PdNumero, produitCreditID);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "delete_produit_gx.php", "POST", httpParams);
            try {
                success = jsonObject.getInt(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            deleteButton.startAnimation() ;// to stop animation on button delete
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(UpdateProduitGx.this,
                                "Produit de Crédit Supprimé", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie deletion
                        setResult(20, i);
                        finish();
                    } else {
                        Toast.makeText(UpdateProduitGx.this,
                                "Some error occurred while deleting Produit de Crédit",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    /**
     * To manage Radio button
     * @param view
     */
    public void onRadioButtonClicked(View view) {
        boolean checked1 = ((RadioButton) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.rb_PdIsOn_oui:
                if (rb_PdIsOnOui.isChecked()) {
                    str="Produit activé";
                }
                break;
            case R.id.rb_PdIsOn_non:
                if (rb_PdIsOnNon.isChecked()) {
                    str="Produit désactivé";
                }
                break;
        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }
    /**
     * Checks whether all files are filled. If so then calls UpdateProduitGxAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void updateProduitCredit() {
        produit = new Produit();
        if (!STRING_EMPTY.equals(PdNom_ET.getText().toString().trim())){
            produit.setPdNom(PdNom_ET.getText().toString().trim());
        }else{
            MyData.bipError();
            til_PdNom.setError("Indiquer le nom du produit");
            til_PdNom.requestFocus();
            Toast.makeText(UpdateProduitGx.this,
                    "le nom du produit est vide!",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (!STRING_EMPTY.equals(PdPrixUnit_ET.getText().toString().trim())){
            produit.setPdPrixUnit(PdPrixUnit_ET.getText().toString().trim());
        }else{
            MyData.bipError();
            til_PdPrixUnit.setError("Indiquer le prix unitaire du produit");
            til_PdPrixUnit.requestFocus();
            Toast.makeText(UpdateProduitGx.this,
                    "le prix unitaire du produit est vide!",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (!STRING_EMPTY.equals(PdQteStock_ET.getText().toString().trim())){
            produit.setPdQteStock(PdQteStock_ET.getText().toString().trim());
        }else{
            MyData.bipError();
            til_PdQteStock.setError("Indiquer la quantité en stock du produit");
            til_PdQteStock.requestFocus();
            Toast.makeText(UpdateProduitGx.this,
                    "la quantité en stock du produit est vide!",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (!STRING_EMPTY.equals(PdStockMin_ET.getText().toString().trim())){
            produit.setPdStockMin(PdStockMin_ET.getText().toString().trim());
        }else{
            MyData.bipError();
            til_PdStockMin.setError("Indiquer le stock minimum du produit");
            til_PdStockMin.requestFocus();
            Toast.makeText(UpdateProduitGx.this,
                    "la valeur du stock minimum est vide!",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (rb_PdIsOnOui.isChecked()){
            produit.setPdIsOn("Y");
        }else if (rb_PdIsOnNon.isChecked()){
            produit.setPdIsOn("N");
        }else{
            notificationPdIsOnNonRenseignee();
            return;
        }
        produit.setPdGuichet(MyData.GUICHET_ID+"");
              new UpdateProduitGxAsyncTask().execute();
    }

    /**
     * AsyncTask for updating a movie details
     */

    private class UpdateProduitGxAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateProduitGx.this);
            pDialog.setMessage("Updating Produit de Crédit. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            updateButton.startAnimation() ;// to start animation on button update
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(Produit.KEY_PdNumero, String.valueOf(produitCreditID));

            httpParams.put(Produit.KEY_PdNom, produit.getPdNom());
            httpParams.put(Produit.KEY_PdPrixUnit, produit.getPdPrixUnit());
            httpParams.put(Produit.KEY_PdQteStock, produit.getPdQteStock());
            httpParams.put(Produit.KEY_PdStockMin, produit.getPdStockMin());
            httpParams.put(Produit.KEY_PdGuichet, produit.getPdGuichet());
            httpParams.put(Produit.KEY_PdIsOn, produit.getPdIsOn());
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "update_produit_gx.php", "POST", httpParams);
            try {
                success = jsonObject.getInt(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            updateButton.startAnimation() ;// to stop animation on button update
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(UpdateProduitGx.this,
                                "Produit de Crédit Mis à Jour !", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        finish();
                    } else {
                        Toast.makeText(UpdateProduitGx.this,
                                "Some error occurred while updating Produit de Crédit",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
    public void notificationPdIsOnNonRenseignee() {
        MyData.bipError();
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Statut produit absent!")
                .setMessage("Veuillez renseigner le statut du produit !")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
}
