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
package com.example.binumtontine.activity.parametreGenerauxCx.comiteDeCredit;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class CreateComiteCredit extends AppCompatActivity implements AdapterView.OnItemSelectedListener,SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";

    private static final String KEY_GX_NUMERO = "gx_numero";
    private static final String GX_DENOMINATION = "gx_denomination";

    private EditText CmNbMembre_ET;
    private EditText CmNom_ET;

    private RadioButton rb_CmTypeCA;
    private RadioButton rb_CmTypeDI;
    private RadioButton rb_CmTypeAG;
    private RadioButton rb_CmTypeIN;
    private RadioButton rb_CmIsOnOui;
    private RadioButton rb_CmIsOnNon;

    ComiteCredit comiteCredit;
    private static String STRING_EMPTY = "";
    private TextInputLayout til_CmNom;
    private TextInputLayout til_CmNbMembre;



    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Category> CmGuichetList;
    List<Integer> CmGuichetListID = new ArrayList<Integer>();
    private int CmGuichet;
    private Spinner spinnerCmGuichet;
    private Spinner spinnerFonctionFrais;
    private LinearLayout textInputLayoutCaisse;
    private LinearLayout textInputLayoutFpNbrePartMin;
    /*end manage*/


    private Button addButton;
    private Button annulerButton;
    private Button deleteButton;
    private int success;
    private ProgressDialog pDialog;
    private ProgressDialog pDialogFetchBaseFraisList;
    private ProgressDialog pDialogFetchProduitCreditList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_comite_credit);

        CmNom_ET = (EditText) findViewById(R.id.input_txt_CmNom);
        til_CmNom = (TextInputLayout) findViewById(R.id.input_layout_CmNom);
        CmNbMembre_ET = (EditText) findViewById(R.id.input_txt_CmNbMembre);
        til_CmNbMembre = (TextInputLayout) findViewById(R.id.input_layout_CmNbMembre);
        spinnerCmGuichet = (Spinner) findViewById(R.id.spn_list_CmGuichet);
        rb_CmTypeAG = (RadioButton) findViewById(R.id.rb_CmType_AG);
        rb_CmTypeCA = (RadioButton) findViewById(R.id.rb_CmType_CA);
        rb_CmTypeDI = (RadioButton) findViewById(R.id.rb_CmType_DI);
        rb_CmTypeIN = (RadioButton) findViewById(R.id.rb_CmType_IN);
        rb_CmIsOnOui = (RadioButton) findViewById(R.id.rb_CmIsOn_oui);
        rb_CmIsOnNon = (RadioButton) findViewById(R.id.rb_CmIsOn_non);

//
        CmGuichetList = new ArrayList<Category>();
//        // spinner item select listener
        spinnerCmGuichet.setOnItemSelectedListener(CreateComiteCredit.this);
        new GetCmGuichetList().execute();

        deleteButton = (Button) findViewById(R.id.btn_delete_comite_credit);
        deleteButton.setVisibility(View.GONE);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        addButton = (Button) findViewById(R.id.btn_save_comite_credit);
        //cirLoginButton
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(CreateComiteCredit.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addComiteCredit();
                } else {
                    Toast.makeText(CreateComiteCredit.this,
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

        for (int i = 0; i < CmGuichetList.size(); i++) {
            lables.add(CmGuichetList.get(i).getName());//recupère les noms
            CmGuichetListID.add(CmGuichetList.get(i).getId()); //recupère les Id
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(CreateComiteCredit.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerCmGuichet.setAdapter(spinnerAdapter);
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

            case R.id.rb_CmIsOn_oui:
                if (rb_CmIsOnOui.isChecked()) {
                    str="Comité activé";
                }
                break;
            case R.id.rb_CmIsOn_non:
                if (rb_CmIsOnNon.isChecked()) {
                    str="Comité désactivé";
                }
                break;
        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    /**
     * Checks whether all files are filled. If so then calls AddComiteCreditAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addComiteCredit() {

            comiteCredit = new ComiteCredit();
            if (!STRING_EMPTY.equals(CmNom_ET.getText().toString().trim())){
                comiteCredit.setCmNom(CmNom_ET.getText().toString().trim());
            }else{
                MyData.bipError();
                til_CmNom.setError("Indiquer le nom du comité");
                til_CmNom.requestFocus();
                Toast.makeText(CreateComiteCredit.this,
                        "le nom du comité est vide!",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (!STRING_EMPTY.equals(CmNbMembre_ET.getText().toString().trim())){
                comiteCredit.setCmNbMembre(Integer.parseInt(CmNbMembre_ET.getText().toString().trim()));
            }else{
                MyData.bipError();
                til_CmNbMembre.setError("Indiquer le nombre de membre du comité");
                til_CmNbMembre.requestFocus();
                Toast.makeText(CreateComiteCredit.this,
                        "le nombre de membre du comité est vide!",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (rb_CmTypeCA.isChecked()){
                comiteCredit.setCmType("CA");
            }else if (rb_CmTypeDI.isChecked()){
                comiteCredit.setCmType("DI");
            }else if (rb_CmTypeAG.isChecked()){
                comiteCredit.setCmType("AG");
            }else if (rb_CmTypeIN.isChecked()){
                comiteCredit.setCmType("IN");
            }else{
                notificationCmTypeNonRenseignee();
                return;
            }
            if (rb_CmIsOnOui.isChecked()){
                comiteCredit.setCmIsOn("Y");
            }else if (rb_CmIsOnNon.isChecked()){
                comiteCredit.setCmIsOn("N");
            }else{
                notificationCmIsOnNonRenseignee();
                return;
            }

            if (CmGuichet==0 && !comiteCredit.getCmType().equals("AG")){
                notificationGuichetVide();
            }
            comiteCredit.setCmGuichet(CmGuichet);
            comiteCredit.setCmCaisse(MyData.CAISSE_ID);
            comiteCredit.setCmUserCree(MyData.USER_ID);

            new AddComiteCreditAsyncTask().execute();



    }
    public void notificationCmTypeNonRenseignee() {
        MyData.bipError();
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Type de comité absent!")
                .setMessage("Veuillez renseigner le type de comité !")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .show();
    }
    public void notificationCmIsOnNonRenseignee() {
        MyData.bipError();
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Statut du comité absent!")
                .setMessage("Veuillez renseigner le statut du comité !")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .show();
    }
    public void notificationGuichetVide() {

        ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        toneGen1.startTone(ToneGenerator.TONE_SUP_ERROR,150);
//        toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,150);
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Aucun guichet  sélectionné")
                .setMessage("Veuillez sélectionner un guichet pour un comité de type Agence !")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        spinnerListCredit.setError ("Donnez vos observations SVP!");
                        spinnerCmGuichet.requestFocus();
                    }

                })
                .show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int idSpinner = parent.getId();

        switch (idSpinner)
        {
            case R.id.spn_list_CmGuichet:
                // your stuff here
                CmGuichet = CmGuichetListID.get(position);//pour recuperer l'ID de l'étape de demande de crédit selectionné
                break;
//            case R.id.spn_list_objet_credit:
//                // your stuff here
//                objetCreditID = objetCreditListID.get(position);//pour recuperer l'ID de l'objet crédit selectionné
//                break;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * AsyncTask for adding a movie
     */
    private class AddComiteCreditAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(CreateComiteCredit.this);
            pDialog.setMessage("Ajout d'un comité de crédit. SVP, patientez...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(ComiteCredit.KEY_CmNom, comiteCredit.getCmNom());
            httpParams.put(ComiteCredit.KEY_CmType, comiteCredit.getCmType());
            httpParams.put(ComiteCredit.KEY_CmNbMembre, String.valueOf(comiteCredit.getCmNbMembre()));
            httpParams.put(ComiteCredit.KEY_CmCaisse, String.valueOf(comiteCredit.getCmCaisse()));
            httpParams.put(ComiteCredit.KEY_CmGuichet, String.valueOf(comiteCredit.getCmGuichet()));
            httpParams.put(ComiteCredit.KEY_CmUserCree, String.valueOf(comiteCredit.getCmUserCree()));
            httpParams.put(ComiteCredit.KEY_CmIsOn, String.valueOf(comiteCredit.getCmIsOn()));


            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_comite_credit.php", "POST", httpParams);
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
                        Toast.makeText(CreateComiteCredit.this,
                                "Statut étape demande de crédit Ajouté", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CreateComiteCredit.this,
                                "Une erreur s'est produite lors de l'ajout du statut",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }


    /**
     * Async task to get all food categories
     * */
    private class GetCmGuichetList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogFetchProduitCreditList = new ProgressDialog(CreateComiteCredit.this);
            pDialogFetchProduitCreditList.setMessage("Fetching guichets's list..");
            pDialogFetchProduitCreditList.setCancelable(false);
            pDialogFetchProduitCreditList.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair("cx_numero", String.valueOf(MyData.CAISSE_ID)));
            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_guichet.php", ServiceHandler.GET, httpParams);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj
                                .getJSONArray(KEY_DATA);

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category cat = new Category(catObj.getInt(KEY_GX_NUMERO),
                                    catObj.getString(GX_DENOMINATION));
                            CmGuichetList.add(cat);
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
}