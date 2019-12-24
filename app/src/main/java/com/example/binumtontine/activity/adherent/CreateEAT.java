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

import com.example.binumtontine.R;
import com.example.binumtontine.activity.Category;
import com.example.binumtontine.activity.ServiceHandler;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateEAT extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    /*Begin*/
         //to fetchProduitList by caisse and guichet ID
    private static final String KEY_ET_CAISSE_ID = "EtCaisseId";
    private static final String KEY_ET_GUICHET_ID = "EtGuichetId";
    /* end*/
    private static final String KEY_EAT_ID = "EtNumero";
    private static final String KEY_EAT_LIBELLE = "EtLibelle";
    private static final String KEY_EAT_TAUX = "EtValTxInter";

    private static final String KEY_CT_PRODUIT = "CtProduit";
    private static final String KEY_CT_MEMBRE = "CtMembre";
    private static final String KEY_CT_GUICHET = "CtGuichet";
    private static final String KEY_CT_NUM_DOSSIER = "CtNumDossier";
    private static final String KEY_CT_MT_MISE = "CtMtMise";
    private static final String KEY_CT_TAUX = "CtTaux";
    private static final String KEY_CT_NBRE_UNITE = "CtNbUnites";
    private static final String KEY_CT_USER_CREE = "CtUserCree";
    private static final String KEY_CT_MOD_RENOUV = "CtModRenouv";
    private static final String KEY_ADHERENT_NUM_DOSSIER = "CtNumDossier";

    /*Param for get extra*/
    private static final String KEY_ADHERENT_ID = "IpMembre";
    private static final String KEY_ADHERENT_NOM = "AdNom";
    private static final String KEY_ADHERENT_PRENOM = "AdPrenom";
    private static final String KEY_ADHERENT_NUM_MANUEL = "AdNumManuel";
    private static final String KEY_ADHERENT_CODE = "AdCode";



    private static String STRING_EMPTY = "";

    private EditText EatMontantMiseEditText;
    private EditText EatTauxEditText;
    private EditText NumDossierEditText;


    private String adherentId;
    private String eatMontantMise;
    private String eatTaux="";
    private String adNom;
    private String adPrenom;
    private String adNumManuel;
    private String adCode;
    private String adNumDossier;
    private String eatNbreUnite;
    private String eatCtModRenouv;

    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Category> eatList;
    List<Integer> eatListID = new ArrayList<Integer>();
    ArrayList<String> eatListTaux = new ArrayList<String>();
    private int eatID;
    private Spinner spinnerListEAT;
    private Spinner spinnerNbreUnite;
    private TextView tvAdherentNom;
    private TextView tvAdherentNumManuel;
    private TextView tvAdherentCode;
    private RadioButton rb_transfert_eav;
    private RadioButton rb_renouveller;
    private RadioButton rb_retirer;
    private TextView tvAdherentNumDossier;
    /*end manage*/

    private Button addButton;
    private Button annulerButton;
    private int success;
    private ProgressDialog pDialog;
    private ProgressDialog pDialogFetchProduitEatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_eat_adherent);


        Intent intent = getIntent();
        adherentId = intent.getStringExtra(KEY_ADHERENT_ID);
        adNom = intent.getStringExtra(KEY_ADHERENT_NOM);
        adPrenom = intent.getStringExtra(KEY_ADHERENT_PRENOM);
        adNumManuel = intent.getStringExtra(KEY_ADHERENT_NUM_MANUEL);
        adCode = intent.getStringExtra(KEY_ADHERENT_CODE);
        //adNumDossier = intent.getStringExtra(KEY_ADHERENT_NUM_DOSSIER);

        EatMontantMiseEditText = (EditText) findViewById(R.id.input_txt_montant_mise);

        NumDossierEditText = (EditText) findViewById(R.id.input_txt_num_dossier_adherent);
        rb_transfert_eav = (RadioButton) findViewById(R.id.rb_CtModRenouv_transfert_vers_eav);
        rb_renouveller = (RadioButton) findViewById(R.id.rb_CtModRenouv_renouveller);
        rb_retirer = (RadioButton) findViewById(R.id.rb_CtModRenouv_retirer);


        spinnerListEAT = (Spinner) findViewById(R.id.spn_list_eat);
        spinnerNbreUnite = (Spinner) findViewById(R.id.spn_list_duree);
        tvAdherentNom = (TextView) findViewById(R.id.tv_nom_adherent);
        tvAdherentNom.setText(adNom+"\n"+adPrenom);
        tvAdherentNumManuel = (TextView) findViewById(R.id.tv_num_manuel_adherent);
        tvAdherentNumManuel.setText(adNumManuel);
        tvAdherentCode = (TextView) findViewById(R.id.tv_code_adherent);
        tvAdherentCode.setText(adCode);
       /* tvAdherentNumDossier = (TextView) findViewById(R.id.tv_num_dossier_adherent);
        tvAdherentNumDossier.setText(adNumDossier);*/

        eatList = new ArrayList<Category>();
        // spinner item select listener
        spinnerListEAT.setOnItemSelectedListener(CreateEAT.this);
        new GetProduitEATList().execute();
        EatTauxEditText = (EditText) findViewById(R.id.input_txt_taux);
        EatTauxEditText.setText(eatTaux+" %");
        addButton = (Button) findViewById(R.id.btn_save_compte_eat);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(CreateEAT.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addEatAdherent();
                } else {
                    Toast.makeText(CreateEAT.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });


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

            case R.id.rb_CtModRenouv_transfert_vers_eav:
                if (rb_transfert_eav.isChecked()) {
                    eatCtModRenouv = "T";
                    //str = checked1?"Nature frais fixe":"";

                }
                break;
            case R.id.rb_CtModRenouv_renouveller:
                if (rb_renouveller.isChecked()) {
                    eatCtModRenouv = "R";
                   // str = checked1?"Nature frais taux":"";

                }
                break;
            case R.id.rb_CtModRenouv_retirer:
                if (rb_retirer.isChecked()) {
                    eatCtModRenouv = "E";
                    //str = checked1?"Ce frais est obligatoire":"";

                }

                break;


        }
       // Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }


    /**
     * Adding spinner data
     * */
    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();

        //tvCaisse.setText("");

        for (int i = 0; i < eatList.size(); i++) {
            lables.add(eatList.get(i).getName());//recupère les noms
            eatListID.add(eatList.get(i).getId()); //recupère les Id
            eatListTaux.add(eatList.get(i).getTaux()); //recupère les Taux
            Log.d("*******",eatList.get(i).getTaux());
        }



        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(CreateEAT.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerListEAT.setAdapter(spinnerAdapter);
    }

    /**
     * Async task to get all food categories
     * */
    private class GetProduitEATList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogFetchProduitEatList = new ProgressDialog(CreateEAT.this);
            pDialogFetchProduitEatList.setMessage("Fetching produits's list..");
            pDialogFetchProduitEatList.setCancelable(false);
            pDialogFetchProduitEatList.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair(KEY_ET_CAISSE_ID, String.valueOf(MyData.CAISSE_ID)));
            httpParams.add(new BasicNameValuePair(KEY_ET_GUICHET_ID, String.valueOf(MyData.GUICHET_ID)));
            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_eat_by_guichet.php", ServiceHandler.GET, httpParams);
           // String json = jsonParser.makeServiceCall(URL_GUICHETS, ServiceHandler.GET);



            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj
                                .getJSONArray(KEY_DATA);

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category cat = new Category(catObj.getInt(KEY_EAT_ID),
                                    catObj.getString(KEY_EAT_LIBELLE),catObj.getString(KEY_EAT_TAUX));
                            eatList.add(cat);
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
            if (pDialogFetchProduitEatList.isShowing())
                pDialogFetchProduitEatList.dismiss();
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
        eatID = eatListID.get(position);//pour recuperer l'ID du guichet selectionnée
        //eatTaux = eatListTaux.get(position);//pour recuperer l'ID du guichet selectionnée
        EatTauxEditText.setText(eatList.get(position).getTaux()+" %");


        Toast.makeText(
                getApplicationContext(),
                eatTaux + " Selected" ,
                Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }


    /**
     * Checks whether all files are filled. If so then calls AddEatAdherentAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addEatAdherent() {
        if (!STRING_EMPTY.equals(EatMontantMiseEditText.getText().toString()) &&
            !STRING_EMPTY.equals(NumDossierEditText.getText().toString()) &&
            !STRING_EMPTY.equals(EatTauxEditText.getText().toString()) &&
                eatID !=0 ) {


                eatMontantMise = EatMontantMiseEditText.getText().toString();
                adNumDossier = NumDossierEditText.getText().toString();
                eatTaux = (EatTauxEditText.getText().toString()).replace(" %","");
                eatNbreUnite = spinnerNbreUnite.getSelectedItem().toString();
         /*   if (rb_transfert_eav.isChecked()) {
                eatCtModRenouv = "T";
                //str = checked1?"Nature frais fixe":"";

            }else  if (rb_renouveller.isChecked()) {
                eatCtModRenouv = "R";
                // str = checked1?"Nature frais taux":"";

            } else {
                eatCtModRenouv = "R";
                // str = checked1?"Nature frais taux":"";

            }*/

                new AddEatAdherentAsyncTask().execute();


        } else {
            Toast.makeText(CreateEAT.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for adding a compte eav
     */
    private class AddEatAdherentAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(CreateEAT.this);
            pDialog.setMessage("Adding compte à terme. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
           // httpParams.put(KEY_EAT_ID, uxGuichetId);

            httpParams.put(KEY_CT_MEMBRE, adherentId);
            httpParams.put(KEY_CT_PRODUIT, String.valueOf(eatID));
            httpParams.put(KEY_CT_GUICHET, String.valueOf(MyData.GUICHET_ID));
            httpParams.put(KEY_CT_NUM_DOSSIER, adNumDossier);
            httpParams.put(KEY_CT_MT_MISE, eatMontantMise);
            httpParams.put(KEY_CT_NBRE_UNITE, eatNbreUnite);
            httpParams.put(KEY_CT_USER_CREE, String.valueOf(MyData.USER_ID));
            httpParams.put(KEY_CT_TAUX, eatTaux);
            httpParams.put(KEY_CT_MOD_RENOUV, eatCtModRenouv);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_eat_adherent.php", "POST", httpParams);
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
                        Toast.makeText(CreateEAT.this,
                                "Compte créé avec succès", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CreateEAT.this,
                                "Some error occurred while adding Compte",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}