package com.example.binumtontine.activity.parametreGenerauxCx.comiteDeCredit;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.Category;
import com.example.binumtontine.activity.ServiceHandler;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.example.binumtontine.modele.ComiteCredit;
import com.example.binumtontine.modele.Produit;
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

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class UpdateComiteCredit extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SERVER_ADDRESS {
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
    private TextView headerStatutEtapeDemandeCreditTextView;


    private String statutEtapeDemandeCreditID;

    private String gxCaisse;
    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Category> SeEtapeDemList;
    List<Integer> SeEtapeDemListID = new ArrayList<Integer>();
    private int SeEtapeDem;
    private Spinner spinnerDefaultEAV;
    private TextView tvCaisse;
    private TextInputLayout textInputLayoutCaisse;
    private LinearLayout LL_default_eav;
    /*end manage*/


    private CircularProgressButton updateButton;
    private CircularProgressButton annulerButton;
    private CircularProgressButton deleteButton;
    private int success;
    private ProgressDialog pDialog;
    private ProgressDialog pDialogFetchProduitCreditList;
    private String LibelleEtape;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_comite_credit);

        Intent intent = getIntent();
        statutEtapeDemandeCreditID = intent.getStringExtra(ComiteCredit.KEY_CmNumero);
        headerStatutEtapeDemandeCreditTextView = (TextView) findViewById(R.id.tv_header_comite_credit);

        headerStatutEtapeDemandeCreditTextView.setText("Mise à jour d'un comité de créditt");
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

        new GetCmGuichetList().execute();

        new FetchStatutEtapeDetailsAsyncTask().execute();
//        alreadyUpperCase(SeLibelleEditText);
        spinnerCmGuichet.setOnItemSelectedListener(UpdateComiteCredit.this);

        deleteButton = (CircularProgressButton) findViewById(R.id.btn_delete_comite_credit);
        deleteButton.setVisibility(View.VISIBLE);
        annulerButton = (CircularProgressButton) findViewById(R.id.btn_clean);
        annulerButton.setVisibility(View.VISIBLE);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(UpdateComiteCredit.this,
                            "Impossible de se connecter à Internet",
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
        updateButton = (CircularProgressButton) findViewById(R.id.btn_save_comite_credit);
        updateButton.setText("MODIFIER");
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    updateObjetCredit();

                } else {
                    Toast.makeText(UpdateComiteCredit.this,
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        int idSpinner = parent.getId();

        switch (idSpinner)
        {
            case R.id.spn_list_CmGuichet:
                // your stuff here
                CmGuichet = CmGuichetListID.get(position);//pour recuperer l'ID de l'étape de demande de crédit selectionné
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Fetches single movie details from the server
     */
    private class FetchStatutEtapeDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateComiteCredit.this);
            pDialog.setMessage("Chargement des détails sur le comité de crédit. Veuillez patienter svp...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(ComiteCredit.KEY_CmNumero, statutEtapeDemandeCreditID);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_statut_etape_demande_credit_details.php", "GET", httpParams);
            try {
                comiteCredit = new ComiteCredit();
                //Parse the JSON response
                comiteCredit.setCmNom(jsonObject.getString(ComiteCredit.KEY_CmNom));
                comiteCredit.setCmType(jsonObject.getString(ComiteCredit.KEY_CmType));
                comiteCredit.setCmNbMembre(jsonObject.getInt(ComiteCredit.KEY_CmNbMembre));
                comiteCredit.setCmCaisse(jsonObject.getInt(ComiteCredit.KEY_CmCaisse));
                comiteCredit.setCmGuichet(jsonObject.getInt(ComiteCredit.KEY_CmGuichet));
                comiteCredit.setCmUserCree(jsonObject.getInt(ComiteCredit.KEY_CmUserCree));
                comiteCredit.setCmIsOn(jsonObject.getString(ComiteCredit.KEY_CmIsOn));

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
                    CmNom_ET.setText(comiteCredit.getCmNom());
                    CmNbMembre_ET.setText(comiteCredit.getCmNbMembre());

                    if (comiteCredit.getCmType().equals("CA")){
                        rb_CmTypeCA.setChecked(true);
                    }else if (comiteCredit.getCmType().equals("DI")){
                        rb_CmTypeDI.setChecked(true);
                    }else if (comiteCredit.getCmType().equals("AG")){
                        rb_CmTypeAG.setChecked(true);
                    }else if (comiteCredit.getCmType().equals("IN")){
                        rb_CmTypeIN.setChecked(true);
                    }

                    if (comiteCredit.getCmIsOn().equals("Y")){
                        rb_CmIsOnOui.setChecked(true);
                    }else{
                        rb_CmIsOnNon.setChecked(true);
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
                UpdateComiteCredit.this);
        alertDialogBuilder.setMessage("Voulez-vous vraiment supprimer ce Statut?");
        alertDialogBuilder.setPositiveButton("Supprimer",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                            //If the user confirms deletion, execute DeleteObjetCreditAsyncTask
                            new DeleteObjetCreditAsyncTask().execute();
                        } else {
                            Toast.makeText(UpdateComiteCredit.this,
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
    private class DeleteObjetCreditAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateComiteCredit.this);
            pDialog.setMessage("Suppression du Statut. Veuillez patienter...");
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
            httpParams.put(ComiteCredit.KEY_CmNumero, statutEtapeDemandeCreditID);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "delete_comite_credit.php", "POST", httpParams);
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
                        Toast.makeText(UpdateComiteCredit.this,
                                "Comité de crédit Supprimé", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie deletion
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(UpdateComiteCredit.this,
                                "Une erreur s'est produite lors de la suppression du Comité",
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
            pDialogFetchProduitCreditList = new ProgressDialog(UpdateComiteCredit.this);
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

    /**
     * Checks whether all files are filled. If so then calls UpdateObjetCreditAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void updateObjetCredit() {
        comiteCredit = new ComiteCredit();
        if (!STRING_EMPTY.equals(CmNom_ET.getText().toString().trim())){
            comiteCredit.setCmNom(CmNom_ET.getText().toString().trim());
        }else{
            MyData.bipError();
            til_CmNom.setError("Indiquer le nom du comité");
            til_CmNom.requestFocus();
            Toast.makeText(UpdateComiteCredit.this,
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
            Toast.makeText(UpdateComiteCredit.this,
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

        if (CmGuichet==0 && comiteCredit.getCmType().equals("AG")){
            notificationGuichetVide();
        }
        comiteCredit.setCmGuichet(CmGuichet);
        comiteCredit.setCmCaisse(MyData.CAISSE_ID);
        comiteCredit.setCmUserCree(MyData.USER_ID);
              new UpdateObjetCreditAsyncTask().execute();



    }

    public void notificationCmTypeNonRenseignee() {
        MyData.bipError();
        new androidx.appcompat.app.AlertDialog.Builder(this)
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
        new androidx.appcompat.app.AlertDialog.Builder(this)
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
        new androidx.appcompat.app.AlertDialog.Builder(this)
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

    /**
     * AsyncTask for updating a movie details
     */

    private class UpdateObjetCreditAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateComiteCredit.this);
            pDialog.setMessage("Updating Objet Crédit. Please wait...");
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
           // httpParams.put(KEY_GUICHET_ID, statutEtapeDemandeCreditID);
           /* httpParams.put(KEY_SE_NUMERO, String.valueOf(statutEtapeDemandeCreditID));
            httpParams.put(KEY_SE_ETAPE_DEMANDE, String.valueOf(SeEtapeDem));
            httpParams.put(KEY_SE_LIBELLE, SeLibelle);
            httpParams.put(KEY_SE_NUM_ORDRE, SeNumOrdr);*/
            //httpParams.put(KEY_GX_CX_NUMERO, gxCaisse);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "update_statut_etape_demande_credit.php", "POST", httpParams);
            try {
                success = jsonObject.getInt(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            updateButton.stopAnimation(); ;// to stop animation on button update
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(UpdateComiteCredit.this,
                                "Statut de l'étape Mis à Jour !", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(UpdateComiteCredit.this,
                                "Une erreur s'est produite lors de la mise à jour du Statut",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
    /**
     * Adding spinner data
     * */
//    private void populateSpinner() {
//      /*  List<String> lables = new ArrayList<String>();
//
//        //tvCaisse.setText("");
//
//        for (int i = 0; i < defaultEavList.size(); i++) {
//            lables.add(defaultEavList.get(i).getName());//recupère les noms
//            defaultEavListID.add(defaultEavList.get(i).getId()); //recupère les Id
//        }
//
//        // Creating adapter for spinner
//        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(UpdateObjetCreditOF.this,
//                android.R.layout.simple_spinner_item, lables);
//
//        // Drop down layout style - list view with radio button
//        spinnerAdapter
//                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // attaching data adapter to spinner
//        spinnerDefaultEAV.setAdapter(spinnerAdapter);
//
//        //Make uxCaisseDenomination as default Item in caisseList spinner
//        if (gxDefaultEavDenomination!=null){
//            spinnerDefaultEAV.setSelection(spinnerAdapter.getPosition(gxDefaultEavDenomination));
//        }
//*/
//    }


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
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(UpdateComiteCredit.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerCmGuichet.setAdapter(spinnerAdapter);
        //Make uxCaisseDenomination as default Item in caisseList spinner
        if (LibelleEtape !=null){
            spinnerCmGuichet.setSelection(spinnerAdapter.getPosition(LibelleEtape));
        }
    }


}
