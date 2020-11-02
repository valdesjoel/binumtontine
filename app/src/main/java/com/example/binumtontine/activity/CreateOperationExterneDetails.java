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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.example.binumtontine.modele.OperationExterne;
import com.example.binumtontine.modele.OperationExterneDetails;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateOperationExterneDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static String STRING_EMPTY = "";

    private EditText ET_OdMontant;
    private EditText ET_OdLibelle;

    private RadioButton rb_OdSensOper_Produits;
    private RadioButton rb_OdSensOper_Charges;

    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Category> OdOperExterneList;
    List<Integer> OdOperExterneListID = new ArrayList<Integer>();
    private Spinner spinnerListOdOperExterne;

    /*end manage*/

    private Button addButton;
    private Button annulerButton;
    private int success;
    private ProgressDialog pDialog;
    private ProgressDialog pDialogFetchProduitEavList;
OperationExterneDetails operationExterneDetails = new OperationExterneDetails();
    private String OeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_operation_externe_details);

        ET_OdMontant = (EditText) findViewById(R.id.input_txt_OdMontant);
        ET_OdMontant.addTextChangedListener(MyData.onTextChangedListener(ET_OdMontant));
        ET_OdLibelle = (EditText) findViewById(R.id.input_txt_OdLibelle);
        rb_OdSensOper_Produits = (RadioButton) findViewById(R.id.rb_OdSensOper_Produits);
        rb_OdSensOper_Charges = (RadioButton) findViewById(R.id.rb_OdSensOper_Charges);
        onRadioButtonClicked(rb_OdSensOper_Produits);


        spinnerListOdOperExterne = (Spinner) findViewById(R.id.spn_list_OdOperExterne);



        // spinner item select listener
        spinnerListOdOperExterne.setOnItemSelectedListener(CreateOperationExterneDetails.this);
//        new GetOperationExterneList().execute();

        addButton = (Button) findViewById(R.id.btn_save);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(CreateOperationExterneDetails.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addOperationExterneDetails();
                } else {
                    Toast.makeText(CreateOperationExterneDetails.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void loadOperationExterneList(){
        new GetOperationExterneList().execute();
    }
    public void onRadioButtonClicked(View view) {
//        boolean checked1 = ((RadioButton) view).isChecked();
//        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.rb_OdSensOper_Produits:
                if (rb_OdSensOper_Produits.isChecked()) {
//                    str = checked1?"Opération produit":"";
                    operationExterneDetails.setOdSensOper("E");
                    OeType = "P";
                    loadOperationExterneList();
                }
                break;
            case R.id.rb_OdSensOper_Charges:
                if (rb_OdSensOper_Charges.isChecked()){
//                    str = checked1?"Opération charges":"";
                    operationExterneDetails.setOdSensOper("S");
                    OeType = "C";
                    loadOperationExterneList();
                }
                break;
        }
//        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    /**
     * Adding spinner data
     * */
    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();

        //tvCaisse.setText("");

        for (int i = 0; i < OdOperExterneList.size(); i++) {
            lables.add(OdOperExterneList.get(i).getName());//recupère les noms
            OdOperExterneListID.add(OdOperExterneList.get(i).getId()); //recupère les Id
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(CreateOperationExterneDetails.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerListOdOperExterne.setAdapter(spinnerAdapter);
    }

    /**
     * Async task to get all food categories
     * */
    private class GetOperationExterneList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogFetchProduitEavList = new ProgressDialog(CreateOperationExterneDetails.this);
            pDialogFetchProduitEavList.setMessage("Fetching produits's list..");
            pDialogFetchProduitEavList.setCancelable(false);
            pDialogFetchProduitEavList.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair(OperationExterne.KEY_OeCaisse, String.valueOf(MyData.CAISSE_ID)));
            httpParams.add(new BasicNameValuePair(OperationExterne.KEY_OeType, OeType));
            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_operation_externe_by_type_on_caisse.php", ServiceHandler.GET, httpParams);

            Log.e("Response: ", "> " + json);
            Log.e("httpParams: ", "> " + httpParams);
            OdOperExterneList = new ArrayList<Category>();

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj
                                .getJSONArray(KEY_DATA);

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category cat = new Category(catObj.getInt(OperationExterne.KEY_OeNumero),
                                    catObj.getString(OperationExterne.KEY_OeLibelle));
                            OdOperExterneList.add(cat);
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
            if (pDialogFetchProduitEavList.isShowing())
                pDialogFetchProduitEavList.dismiss();
            populateSpinner();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {

//        eavID = OdOperExterneListID.get(position);//pour recuperer l'ID du guichet selectionnée
        operationExterneDetails.setOdOperExterne(OdOperExterneListID.get(position)) ;//pour recuperer l'ID du guichet selectionnée

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    public void notificationChampsVides() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Liste Opération externe absente ")
                .setMessage("Veuilez affecter des Opérations externes sur la caisse de ce guichet!")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }

                })
                .show();
    }
    public void notificationSuccessAdd() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Opération réussie ")
                .setMessage("L'enregistrement de votre opération s'est bien effectué !")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();
                    }

                })
                .show();
    }
    public void notificationEchecAdd() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Opération échouée ")
                .setMessage("L'enregistrement de votre opération a échoué!" +
                        "\n Solde en caisse insuffisant !")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .show();
    }
    /**
     * Checks whether all files are filled. If so then calls AddOperationExterneDetailsAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addOperationExterneDetails() {
        try {
            if (!STRING_EMPTY.equals(ET_OdMontant.getText().toString().trim()) &&
                    !STRING_EMPTY.equals(ET_OdLibelle.getText().toString().trim()) &&
                    operationExterneDetails.getOdOperExterne() !=0 ) {

                Double montantOperation;

                if (!(ET_OdMontant.getText().toString().replaceAll(",", "").trim()).equals(STRING_EMPTY)) {
                    montantOperation = Double.valueOf(ET_OdMontant.getText().toString().replaceAll(",", "").trim());
                    ET_OdMontant.setText(montantOperation + "");
                }
                operationExterneDetails.setOdMontant(ET_OdMontant.getText().toString().trim());
                operationExterneDetails.setOdLibelle(MyData.normalizeSymbolsAndAccents( ET_OdLibelle.getText().toString().trim()));


                new AddOperationExterneDetailsAsyncTask().execute();


            } else {
                if (STRING_EMPTY.equals(ET_OdMontant.getText().toString())){
                    ET_OdMontant.setError("Remplissez le montant SVP! ");
                    ET_OdMontant.requestFocus();
                }else if (STRING_EMPTY.equals(ET_OdLibelle.getText().toString())){
                    ET_OdLibelle.setError("Remplissez l'intitulé de l'opération SVP! ");
                    ET_OdLibelle.requestFocus();
                }else if (operationExterneDetails.getOdOperExterne() ==0){
                    notificationChampsVides();
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    /**
     * AsyncTask for adding a compte eav
     */
    private class AddOperationExterneDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(CreateOperationExterneDetails.this);
            pDialog.setMessage("Ajout d'une opération externe . Patientez...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters

            httpParams.put(OperationExterneDetails.KEY_OdOperExterne , String.valueOf(operationExterneDetails.getOdOperExterne()));
            httpParams.put(OperationExterneDetails.KEY_OdLibelle , String.valueOf(operationExterneDetails.getOdLibelle()));
            httpParams.put(OperationExterneDetails.KEY_OdMontant , String.valueOf(operationExterneDetails.getOdMontant()));
            httpParams.put(OperationExterneDetails.KEY_OdSensOper , String.valueOf(operationExterneDetails.getOdSensOper()));
            httpParams.put(OperationExterneDetails.KEY_OdGuichet , String.valueOf(MyData.GUICHET_ID));
            httpParams.put(OperationExterneDetails.KEY_OdUserCree , String.valueOf(MyData.USER_ID));

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_operation_externe_details.php", "POST", httpParams);
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
//                        Toast.makeText(CreateOperationExterneDetails.this,
//                                "Compte créé avec succès", Toast.LENGTH_LONG).show();
                        notificationSuccessAdd();


                    } else  if (success == -1) {

                        notificationEchecAdd();

                    } else {
                        Toast.makeText(CreateOperationExterneDetails.this,
                                "Erreur lors de l'ajout !",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}