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
package com.example.binumtontine.activity.parametreGenerauxCx.membreComiteCredit;


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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.Category;
import com.example.binumtontine.activity.ServiceHandler;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.example.binumtontine.modele.ComiteCredit;
import com.example.binumtontine.modele.MembreComiteCredit;
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

public class CreateMembreComiteCredit extends AppCompatActivity implements AdapterView.OnItemSelectedListener,SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";

    private EditText McPrenom_ET;
    private EditText McNom_ET;

    private RadioButton rb_McTypMembrePR;
    private RadioButton rb_McTypMembreME;
    private RadioButton rb_McTypMembreGC;

    private RadioButton rb_McIsOnOui;
    private RadioButton rb_McIsOnNon;

    MembreComiteCredit membreComiteCredit;
    private static String STRING_EMPTY = "";
    private TextInputLayout til_McNom;
    private TextInputLayout til_McPrenom;

    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Category> McComiteCreditList;
    List<Integer> McComiteCreditListID = new ArrayList<Integer>();
    private int McComiteCredit;
    private Spinner spinnerMcComiteCredit;
    /*end manage*/
    /* manage spinner McUser*/
    // array list for spinner adapter
    private ArrayList<Category> McUserList;
    List<Integer> McUserListID = new ArrayList<Integer>();
    private int McUser;
    private Spinner spinnerMcUser;
    /*end manage*/


    private Button addButton;
    private Button annulerButton;
    private Button deleteButton;
    private int success;
    private ProgressDialog pDialog;
    private ProgressDialog pDialogFetchProduitCreditList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_membre_comite_credit);

        McNom_ET = (EditText) findViewById(R.id.input_txt_McNom);
        til_McNom = (TextInputLayout) findViewById(R.id.input_layout_McNom);
        McPrenom_ET = (EditText) findViewById(R.id.input_txt_McPrenom);
        til_McPrenom = (TextInputLayout) findViewById(R.id.input_layout_PcQuantite);
        spinnerMcComiteCredit = (Spinner) findViewById(R.id.spn_list_McComiteCredit);
        spinnerMcUser = (Spinner) findViewById(R.id.spn_list_McUser);
        rb_McTypMembreGC = (RadioButton) findViewById(R.id.rb_McTypMembre_GC);
        rb_McTypMembrePR = (RadioButton) findViewById(R.id.rb_McTypMembre_PR);
        rb_McTypMembreME = (RadioButton) findViewById(R.id.rb_McTypMembre_ME);
        rb_McIsOnOui = (RadioButton) findViewById(R.id.rb_McIsOn_oui);
        rb_McIsOnNon = (RadioButton) findViewById(R.id.rb_McIsOn_non);

//
        McComiteCreditList = new ArrayList<Category>();
        McUserList = new ArrayList<Category>();
//        // spinner item select listener
        spinnerMcComiteCredit.setOnItemSelectedListener(CreateMembreComiteCredit.this);
        spinnerMcUser.setOnItemSelectedListener(CreateMembreComiteCredit.this);
        new GetMcComiteCreditList().execute();
        new GetMcUserList().execute();

        deleteButton = (Button) findViewById(R.id.btn_delete_membre_comite_credit);
        deleteButton.setVisibility(View.GONE);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        addButton = (Button) findViewById(R.id.btn_save_membre_comite_credit);
        //cirLoginButton
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(CreateMembreComiteCredit.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addMembreComiteCredit();
                } else {
                    Toast.makeText(CreateMembreComiteCredit.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

    }
    /**
     * Adding spinner data
     * */
    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();

        //tvCaisse.setText("");

        for (int i = 0; i < McComiteCreditList.size(); i++) {
            lables.add(McComiteCreditList.get(i).getName());//recupère les noms
            McComiteCreditListID.add(McComiteCreditList.get(i).getId()); //recupère les Id
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(CreateMembreComiteCredit.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerMcComiteCredit.setAdapter(spinnerAdapter);
    }
    /**
     * Adding spinner data
     * */
    private void populateMcUserSpinner() {
        List<String> lables = new ArrayList<String>();

        //tvCaisse.setText("");

        for (int i = 0; i < McUserList.size(); i++) {
            lables.add(McUserList.get(i).getName());//recupère les noms
            McUserListID.add(McUserList.get(i).getId()); //recupère les Id
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(CreateMembreComiteCredit.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerMcUser.setAdapter(spinnerAdapter);
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

            case R.id.rb_McIsOn_oui:
                if (rb_McIsOnOui.isChecked()) {
                    str="Membre Comité activé";
                }
                break;
            case R.id.rb_McIsOn_non:
                if (rb_McIsOnNon.isChecked()) {
                    str="Membre Comité désactivé";
                }
                break;
        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    /**
     * Checks whether all files are filled. If so then calls AddMembreComiteCreditAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addMembreComiteCredit() {

            membreComiteCredit = new MembreComiteCredit();
            if (!STRING_EMPTY.equals(McNom_ET.getText().toString().trim())){
                membreComiteCredit.setMcNom(McNom_ET.getText().toString().trim());
            }else{
                MyData.bipError();
                til_McNom.setError("Indiquer le nom du membre");
                til_McNom.requestFocus();
                Toast.makeText(CreateMembreComiteCredit.this,
                        "le nom du membre est vide!",
                        Toast.LENGTH_LONG).show();
                return;
            }
        membreComiteCredit.setMcPrenom(McPrenom_ET.getText().toString().trim());

            if (rb_McTypMembrePR.isChecked()){
                membreComiteCredit.setMcTypMembre("PR");
            }else if (rb_McTypMembreME.isChecked()){
                membreComiteCredit.setMcTypMembre("ME");
            }else if (rb_McTypMembreGC.isChecked()){
                membreComiteCredit.setMcTypMembre("GC");
            }else{
                notificationMcTypMembreNonRenseignee();
                return;
            }
            if (rb_McIsOnOui.isChecked()){
                membreComiteCredit.setMcIsOn("Y");
            }else if (rb_McIsOnNon.isChecked()){
                membreComiteCredit.setMcIsOn("N");
            }else{
                notificationMcIsOnNonRenseignee();
                return;
            }

            if (McComiteCredit ==0 ){
                notificationMcComiteCreditVide();
                return;
            }
            if (McUser ==0 && !membreComiteCredit.getMcTypMembre().equals("ME")){
                notificationMcUserVide();
                return;
            }
            membreComiteCredit.setMcComiteCredit(McComiteCredit);
            membreComiteCredit.setMcUser(McUser);
            membreComiteCredit.setMcUserCree(MyData.USER_ID);

            new AddMembreComiteCreditAsyncTask().execute();



    }
    public void notificationMcTypMembreNonRenseignee() {
        MyData.bipError();
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Type du membre absent!")
                .setMessage("Veuillez renseigner le type du membre !")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .show();
    }
    public void notificationMcIsOnNonRenseignee() {
        MyData.bipError();
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Statut du membre absent!")
                .setMessage("Veuillez renseigner le statut du membre !")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .show();
    }
    public void notificationMcComiteCreditVide() {

        MyData.bipError();
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Aucun Comité  sélectionné")
                .setMessage("Veuillez sélectionner un comité de crédit !")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        spinnerListCredit.setError ("Donnez vos observations SVP!");
                        spinnerMcComiteCredit.requestFocus();
                    }

                })
                .show();
    }
    public void notificationMcUserVide() {

        MyData.bipError();
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Aucun utilisateur sélectionné")
                .setMessage("Veuillez sélectionner un utilisateur pour le comité de crédit !")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        spinnerMcUser.requestFocus();
                    }

                })
                .show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int idSpinner = parent.getId();

        switch (idSpinner)
        {
            case R.id.spn_list_McComiteCredit:
                // your stuff here
                McComiteCredit = McComiteCreditListID.get(position);//pour recuperer l'ID de l'étape de demande de crédit selectionné
                break;
            case R.id.spn_list_McUser:
                // your stuff here
                McUser = McUserListID.get(position);//pour recuperer l'ID du membre du comité selectionné
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * AsyncTask for adding a movie
     */
    private class AddMembreComiteCreditAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(CreateMembreComiteCredit.this);
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
            httpParams.put(MembreComiteCredit.KEY_McNom, membreComiteCredit.getMcNom());
            httpParams.put(MembreComiteCredit.KEY_McPrenom, membreComiteCredit.getMcPrenom());
            httpParams.put(MembreComiteCredit.KEY_McTypMembre, membreComiteCredit.getMcTypMembre());
            httpParams.put(MembreComiteCredit.KEY_McComiteCredit, String.valueOf(membreComiteCredit.getMcComiteCredit()));
            httpParams.put(MembreComiteCredit.KEY_McUser, String.valueOf(membreComiteCredit.getMcUser()));
            httpParams.put(MembreComiteCredit.KEY_McUserCree, String.valueOf(membreComiteCredit.getMcUserCree()));
            httpParams.put(MembreComiteCredit.KEY_McIsOn, String.valueOf(membreComiteCredit.getMcIsOn()));

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_membre_comite_credit.php", "POST", httpParams);
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
                        Toast.makeText(CreateMembreComiteCredit.this,
                                "Membre du comité de crédit Ajouté", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CreateMembreComiteCredit.this,
                                "Une erreur s'est produite lors de l'ajout du Membre",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }


    /**
     * Async task to get all food categories
     * */
    private class GetMcComiteCreditList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogFetchProduitCreditList = new ProgressDialog(CreateMembreComiteCredit.this);
            pDialogFetchProduitCreditList.setMessage("Chargement de la liste des comités de crédit..");
            pDialogFetchProduitCreditList.setCancelable(false);
            pDialogFetchProduitCreditList.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair(ComiteCredit.KEY_CmCaisse, String.valueOf(MyData.CAISSE_ID)));
            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_comite_credit.php", ServiceHandler.GET, httpParams);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj
                                .getJSONArray(KEY_DATA);

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category cat = new Category(catObj.getInt(ComiteCredit.KEY_CmNumero),
                                    catObj.getString(ComiteCredit.KEY_CmNom));
                            McComiteCreditList.add(cat);
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
            populateSpinner();
        }

    }

    /**
     * Async task to get all food categories
     * */
    private class GetMcUserList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* pDialogFetchProduitCreditList = new ProgressDialog(CreateMembreComiteCredit.this);
            pDialogFetchProduitCreditList.setMessage("Chargement de la liste des utilisateur de crédit..");
            pDialogFetchProduitCreditList.setCancelable(false);
            pDialogFetchProduitCreditList.show();*/

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair(ComiteCredit.KEY_CmCaisse, String.valueOf(MyData.CAISSE_ID)));
            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_McUser.php", ServiceHandler.GET, httpParams);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj
                                .getJSONArray(KEY_DATA);

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category cat = new Category(catObj.getInt("ux_numero"),
                                    catObj.getString("ux_nom").concat(" ").concat(catObj.getString("ux_prenom")));
                            McUserList.add(cat);
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
            /*if (pDialogFetchProduitCreditList.isShowing())
                pDialogFetchProduitCreditList.dismiss();*/
            populateMcUserSpinner();
        }

    }
}