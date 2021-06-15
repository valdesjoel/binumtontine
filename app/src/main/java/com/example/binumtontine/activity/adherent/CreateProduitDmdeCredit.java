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
package com.example.binumtontine.activity.adherent;


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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.CreateProduitEAV;
import com.example.binumtontine.modele.Produit;
import com.example.binumtontine.activity.ServiceHandler;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.example.binumtontine.modele.ComiteCredit;
import com.example.binumtontine.modele.ProduitDmdeCredit;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateProduitDmdeCredit extends AppCompatActivity implements AdapterView.OnItemSelectedListener,SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";

    private EditText PcQuantite_ET;
    private EditText PcDesign_ET;
    private TextView PcPrixUnit_TV;
    private TextView PcMtTotal_TV;
    
    ProduitDmdeCredit produitDmdeCredit;
    private static String STRING_EMPTY = "";
    private TextInputLayout til_PcDesign;
    private TextInputLayout til_PcQuantite;

    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Produit> PcProduitList;
    List<Integer> PcProduitListID = new ArrayList<Integer>();
    private int PcProduit;
    private Spinner spinnerPcProduit;
    /*end manage*/
    private Produit produit;

    private Button addButton;
    private Button annulerButton;
    private Button deleteButton;
    private int success;
    private ProgressDialog pDialog;
    private ProgressDialog pDialogFetchProduitCreditList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_produit_dmde_credit);

        PcDesign_ET = (EditText) findViewById(R.id.input_txt_PcDesign);
        til_PcDesign = (TextInputLayout) findViewById(R.id.input_layout_PcDesign);
        PcQuantite_ET = (EditText) findViewById(R.id.input_txt_PcQuantite);
        til_PcQuantite = (TextInputLayout) findViewById(R.id.input_layout_PcQuantite);
        PcPrixUnit_TV = (TextView) findViewById(R.id.tv_PcPrixUnit);
        PcMtTotal_TV = (TextView) findViewById(R.id.tv_PcMtTotal1);
        spinnerPcProduit = (Spinner) findViewById(R.id.spn_list_PcProduit);
     /*   if (PcQuantite_ET.foocus()){
            PcMtTotal_TV.setText(String.valueOf(Double.parseDouble(produitDmdeCredit.getPcPrixUnit())*Double.parseDouble(produitDmdeCredit.getPcQuantite())));
        }*/

        PcProduitList = new ArrayList<Produit>();
//        // spinner item select listener

        new GetPcProduitList().execute();
        spinnerPcProduit.setOnItemSelectedListener(CreateProduitDmdeCredit.this);
        deleteButton = (Button) findViewById(R.id.btn_delete_produit_dmde_credit);
        deleteButton.setVisibility(View.GONE);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        addButton = (Button) findViewById(R.id.btn_save_produit_dmde_credit);
        //cirLoginButton
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(CreateProduitDmdeCredit.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addProduitDmdeCredit();
                } else {
                    Toast.makeText(CreateProduitDmdeCredit.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

    }
    /**
     * Adding spinner data
     * */
    private void populateSpinnerPcProduit() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < PcProduitList.size(); i++) {
            lables.add(PcProduitList.get(i).getPdNom());//recupère les noms
            PcProduitListID.add(Integer.valueOf(PcProduitList.get(i).getPdNumero())); //recupère les Id
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(CreateProduitDmdeCredit.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerPcProduit.setAdapter(spinnerAdapter);
    }
    private boolean validateField(){
        boolean isValid = true;
        if (PcProduit ==0 ){
            notificationPcProduitVide();
            return false;
        }
        if (STRING_EMPTY.equals(PcDesign_ET.getText().toString().trim())){
            notificationPcDesignNonRenseignee();
            return false;
        }
        if (STRING_EMPTY.equals(PcQuantite_ET.getText().toString().trim())){
            notificationPcQuantiteNonRenseignee();
            return false;
        }

        return isValid;
    }
    /**
     * Checks whether all files are filled. If so then calls AddProduitDmdeCreditAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addProduitDmdeCredit() {
        if (!validateField()){
            return;
        }
        produitDmdeCredit = new ProduitDmdeCredit();
        produitDmdeCredit.setPcDesign(PcDesign_ET.getText().toString().trim());
        produitDmdeCredit.setPcQuantite(PcQuantite_ET.getText().toString().trim());
        produitDmdeCredit.setPcPrixUnit(produit.getPdPrixUnit());
        produitDmdeCredit.setPcProduit(String.valueOf(PcProduit));
        produitDmdeCredit.setPcMtTotal(String.valueOf(Double.parseDouble(produitDmdeCredit.getPcPrixUnit())*Double.parseDouble(produitDmdeCredit.getPcQuantite())));
        DemandeCredit.listProduitDmdeProduit.add(produitDmdeCredit);
        Log.e("tttt", String.valueOf(DemandeCredit.listProduitDmdeProduit.toString()));
        new AddProduitDmdeCreditAsyncTask().execute();
    }
    public void notificationPcQuantiteNonRenseignee() {
        MyData.bipError();

        til_PcQuantite.setError("Indiquer la quantité du produit");
        til_PcQuantite.requestFocus();
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Quantité du produit absent!")
                .setMessage("Veuillez renseigner la Quantité du produit !")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .show();
    }
    public void notificationPcDesignNonRenseignee() {
        MyData.bipError();
        til_PcDesign.setError("Indiquer la désignation du produit");
        til_PcDesign.requestFocus();
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Désignation du produit absent!")
                .setMessage("Veuillez renseigner la désignation du produit !")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
    public void notificationPcProduitVide() {
        MyData.bipError();
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Aucun Produit de crédit sélectionné")
                .setMessage("Veuillez sélectionner un Produit de crédit !")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        spinnerPcProduit.requestFocus();
                    }
                })
                .show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int idSpinner = parent.getId();

        switch (idSpinner)
        {
            case R.id.spn_list_PcProduit:
                // your stuff
                produit = new Produit();
                produit = PcProduitList.get(position);
                PcPrixUnit_TV.setText(produit.getPdPrixUnit());
                PcProduit = PcProduitListID.get(position);//pour recuperer l'ID de l'étape de demande de crédit selectionné
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * AsyncTask for adding a movie
     */
    private class AddProduitDmdeCreditAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(CreateProduitDmdeCredit.this);
            pDialog.setMessage("Ajout d'un membre au comité de crédit. SVP, patientez...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
         /*   httpParams.put(produitDmdeCredit.KEY_McNom, produitDmdeCredit.getMcNom());
            httpParams.put(produitDmdeCredit.KEY_McPrenom, produitDmdeCredit.getMcPrenom());
            httpParams.put(produitDmdeCredit.KEY_McTypMembre, produitDmdeCredit.getMcTypMembre());
            httpParams.put(produitDmdeCredit.KEY_McComiteCredit, String.valueOf(produitDmdeCredit.getMcComiteCredit()));
            httpParams.put(produitDmdeCredit.KEY_McUser, String.valueOf(produitDmdeCredit.getMcUser()));
            httpParams.put(produitDmdeCredit.KEY_McUserCree, String.valueOf(produitDmdeCredit.getMcUserCree()));
            httpParams.put(produitDmdeCredit.KEY_McIsOn, String.valueOf(produitDmdeCredit.getMcIsOn()));

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_membre_comite_credit.php", "POST", httpParams);
*/
            try {
                success = 1;
            } catch (Exception e) {
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
                        Toast.makeText(CreateProduitDmdeCredit.this,
                                "Produit ajouté à la demande de crédit", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CreateProduitDmdeCredit.this,
                                "Une erreur s'est produite lors de l'ajout du produit",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }


    /**
     * Async task to get all food categories
     * */
    private class GetPcProduitList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogFetchProduitCreditList = new ProgressDialog(CreateProduitDmdeCredit.this);
            pDialogFetchProduitCreditList.setMessage("Chargement de la liste des comités de crédit..");
            pDialogFetchProduitCreditList.setCancelable(false);
            pDialogFetchProduitCreditList.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair(Produit.KEY_PdGuichet, String.valueOf(MyData.GUICHET_ID)));
            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_produit_gx.php", ServiceHandler.GET, httpParams);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj
                                .getJSONArray(KEY_DATA);

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Produit cat = new Produit();
                            cat.setPdNumero(catObj.getString(Produit.KEY_PdNumero));
                            cat.setPdNom(catObj.getString(Produit.KEY_PdNom));
                            cat.setPdPrixUnit(catObj.getString(Produit.KEY_PdPrixUnit));
                            cat.setPdQteStock(catObj.getString(Produit.KEY_PdQteStock));
                            PcProduitList.add(cat);
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
            if (pDialogFetchProduitCreditList.isShowing())
                pDialogFetchProduitCreditList.dismiss();
            populateSpinnerPcProduit();
        }

    }

}