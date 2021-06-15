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
package com.example.binumtontine.activity.parametrageGuichet;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.ListNomPrenomAvalisteDemandeCredit;
import com.example.binumtontine.activity.adherent.ComiteCreditNew;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.example.binumtontine.modele.Produit;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class CreateProduitGx extends AppCompatActivity implements SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
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

    /* manage spinner*/
    // array list for spinner adapter
    List<Integer> baseFraisListID = new ArrayList<Integer>();

    /*end manage*/

    private Button addButton;
    private Button annulerButton;
    private Button deleteButton;
    private int success;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit_gx);

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

        deleteButton = (Button) findViewById(R.id.btn_delete_produit);
        deleteButton.setVisibility(View.GONE);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        addButton = (Button) findViewById(R.id.btn_save_produit);
        //cirLoginButton
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(CreateProduitGx.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addProduitCredit();
                } else {
                    Toast.makeText(CreateProduitGx.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public void notificationPdIsOnNonRenseignee() {
        MyData.bipError();
        new AlertDialog.Builder(this)
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

    /**
     * Checks whether all files are filled. If so then calls AddObjetCreditAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addProduitCredit() {
        produit = new Produit();
        if (!STRING_EMPTY.equals(PdNom_ET.getText().toString().trim())){
            produit.setPdNom(PdNom_ET.getText().toString().trim());
        }else{
            MyData.bipError();
            til_PdNom.setError("Indiquer le nom du produit");
            til_PdNom.requestFocus();
            Toast.makeText(CreateProduitGx.this,
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
            Toast.makeText(CreateProduitGx.this,
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
            Toast.makeText(CreateProduitGx.this,
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
            Toast.makeText(CreateProduitGx.this,
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
        produit.setPdUserCree(MyData.USER_ID+"");
        new AddObjetCreditAsyncTask().execute();
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
     * AsyncTask for adding a movie
     */
    private class AddObjetCreditAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(CreateProduitGx.this);
            pDialog.setMessage("Ajout d'un produit de crédit. SVP, patientez...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(Produit.KEY_PdNom, produit.getPdNom());
            httpParams.put(Produit.KEY_PdPrixUnit, produit.getPdPrixUnit());
            httpParams.put(Produit.KEY_PdQteStock, produit.getPdQteStock());
            httpParams.put(Produit.KEY_PdStockMin, produit.getPdStockMin());
            httpParams.put(Produit.KEY_PdGuichet, produit.getPdGuichet());
            httpParams.put(Produit.KEY_PdUserCree, produit.getPdUserCree());
            httpParams.put(Produit.KEY_PdIsOn, produit.getPdIsOn());

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_produit_gx.php", "POST", httpParams);
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
                        Toast.makeText(CreateProduitGx.this,
                                "Produit crédit Ajouté", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();
                    } else {
                        Toast.makeText(CreateProduitGx.this,
                                "Une erreur s'est produite lors de l'ajout de l'objet crédit",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}