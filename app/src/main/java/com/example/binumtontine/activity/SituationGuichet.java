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


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.adherent.Adherent;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.example.binumtontine.modele.SituationGuichetModel;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class SituationGuichet extends AppCompatActivity implements  View.OnClickListener, AdapterView.OnItemSelectedListener, SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_DATA_JOUR_OUVRE = "jour_ouvre";
    private ArrayList<HashMap<String, String>> compteAdherentList;
    SituationGuichetModel maSituationGuichet = new SituationGuichetModel();
    /*Begin*/
         //to fetchProduitList by caisse and guichet ID
    private static final String KEY_EV_CAISSE_ID = "ev_caisse_id";
    private static final String KEY_EV_GUICHET_ID = "gx_numero";
    private static final String KEY_CmDateH = "CmDateH";
    private static final String KEY_CmMontant = "CmMontant";
    private static final String KEY_total_depot = "total_depot";
    private static final String KEY_total_retrait = "total_retrait";
    private static final String KEY_total_produit = "total_produit";
    private static final String KEY_total_charge = "total_charge";
    private static final String KEY_total_operation = "total_operation";
    private static final String KEY_cx_denomination = "cx_denomination";
    private NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
    /* end*/
    private static final String KEY_EAV_ID = "ev_numero";
    private static final String KEY_EAV_LIBELLE = "ev_libelle";

    private static final String KEY_CV_PRODUIT = "CvProduit";
    private static final String KEY_CV_MEMBRE = "CvMembre";
    private static final String KEY_CV_GUICHET = "CvGuichet";
    private static final String KEY_CV_NUM_DOSSIER = "CvNumDossier";
    private static final String KEY_CV_MT_SOLDE = "CvMtSolde";
    private static final String KEY_CV_NATURE_OPERATION = "NatureOp";
    private static final String KEY_CV_USER_CREE = "CvUserCree";
    private static final String KEY_ADHERENT_NUM_DOSSIER = "CvNumDossier";

    private static final String KEY_MONTANT_COMPTE = "MtSolde";
    private static final String KEY_ADHERENT = "ADHERENT";
    private Adherent adherent;
    /*Param for get extra*/
    private static final String KEY_CV_NUMERO = "CvNumero";
    private String total_operation = "";



    private static String STRING_EMPTY = "";
 private String eavDepotMin;
    private String adNumDossier;

    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Category> eavList;
    List<Integer> eavListID = new ArrayList<Integer>();
    private int eavID;
    private Spinner spinnerListEAV;
    private TextView tvAdherentNom;
    private TextView tvAdherentNumManuel;
    private TextView tvAdherentCode;
    private TextView tvCompteSolde;
    private TextView tvDateCreation;
    private TextView tvTaux;
    private TextView tvTypeCompte;

    private EditText Ad_DateDelivranceEditText;
    private EditText Ad_DateExpirationEditText;
    /*end manage*/

    private DatePickerDialog Ad_DateDelivrance_PickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date
    private DatePickerDialog Ad_DateExpiration_PickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date

    private SimpleDateFormat dateFormatter; //propriété permettant de gérer le format de la date

    private Button addButton;
    private Button annulerButton;
    private int success;
    private ProgressDialog pDialog;
    private ProgressDialog pDialogFetchProduitEavList;
    public static TextView tvHeaderActivityConsulterCompte;
    public static TextView tvHeaderLayoutConsulterCompte;
    private TextView tv_jour_ouvre;
    private TextView tv_caisse;
    private TextView tv_guichet;
    private TextView tv_montant_demarrage;
    private TextView tv_depot;
    private TextView tv_retrait;
    private TextView tv_remboursement_credit;
    private TextView tv_frais_inscription;
    private TextView tv_commission;
    private TextView tv_recuperation_credit;
    private TextView tv_sortie_transfert;
    private TextView tv_entree_transfert;
    private TextView tv_cotisation_annuelle;
    private TextView tv_charges;
    private TextView tv_produits;
    private TextView tv_total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situation_guichet);


        tv_jour_ouvre = (TextView) findViewById(R.id.tv_jour_ouvre);
        tv_caisse = (TextView) findViewById(R.id.tv_caisse);
        tv_guichet = (TextView) findViewById(R.id.tv_guichet);
        tv_montant_demarrage = (TextView) findViewById(R.id.tv_montant_demarrage);
        tv_depot = (TextView) findViewById(R.id.tv_depot);
        tv_retrait = (TextView) findViewById(R.id.tv_retrait);
        tv_remboursement_credit = (TextView) findViewById(R.id.tv_remboursement_credit);
        tv_frais_inscription = (TextView) findViewById(R.id.tv_frais_inscription);
        tv_commission = (TextView) findViewById(R.id.tv_commission);
        tv_recuperation_credit = (TextView) findViewById(R.id.tv_recuperation_credit);
        tv_sortie_transfert = (TextView) findViewById(R.id.tv_sortie_transfert);
        tv_entree_transfert = (TextView) findViewById(R.id.tv_entree_transfert);
        tv_cotisation_annuelle = (TextView) findViewById(R.id.tv_cotisation_annuelle);
        tv_charges = (TextView) findViewById(R.id.tv_charges);
        tv_produits = (TextView) findViewById(R.id.tv_produits);
        tv_total = (TextView) findViewById(R.id.tv_total);

        defaultFormat.setCurrency(Currency.getInstance("XAF"));
        new GetSituationGuichetAsyncTask().execute();
        addButton = (Button) findViewById(R.id.btn_save_operation_eav);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(SituationGuichet.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
//                    addEavAdherent();
                } else {
                    Toast.makeText(SituationGuichet.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });


    }

    @Override
    public void onClick(View v) {
         if (v == Ad_DateDelivranceEditText){
            Ad_DateDelivrance_PickerDialog.show();
        }else if (v == Ad_DateExpirationEditText){
            Ad_DateExpiration_PickerDialog.show();
        }
    }



    /**
     * Adding spinner data
     * */
    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();

        //tvCaisse.setText("");

        for (int i = 0; i < eavList.size(); i++) {
            lables.add(eavList.get(i).getName());//recupère les noms
            eavListID.add(eavList.get(i).getId()); //recupère les Id
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(SituationGuichet.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerListEAV.setAdapter(spinnerAdapter);
    }

    /**
     * Get situation guichet from the server
     */
    private class GetSituationGuichetAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(SituationGuichet.this);
            pDialog.setMessage("Chargement de la situation du guichet. Patientez...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_EV_GUICHET_ID, String.valueOf(MyData.GUICHET_ID));
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_situation_guichet.php", "GET", httpParams);
            Log.e("****************", jsonObject+"");
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
//                JSONArray movies;
                JSONObject movies;
                if (success == 1) {
//                    compteAdherentList = new ArrayList<>();
                    //situation guichet
//                    movies = jsonObject.getJSONArray(KEY_DATA_JOUR_OUVRE);
                    movies = jsonObject.getJSONObject(KEY_DATA_JOUR_OUVRE);
                    //Iterate through the response and populate movies list
//                    for (int i = 0; i < movies.length(); i++) {
//                        JSONObject situationGuichet = movies.getJSONObject(i);


//                        SituationGuichetModel maSituationGuichet = new SituationGuichetModel(
//                        maSituationGuichet = new SituationGuichetModel(
                        maSituationGuichet.setJour_ouvre(movies.getString(KEY_CmDateH).substring(0,10));
                        Log.e("***********getJour_ouvre  ",maSituationGuichet.getJour_ouvre());
                        maSituationGuichet.setCaisse(movies.getString(KEY_cx_denomination));
                        maSituationGuichet.setGuichet(MyData.GUICHET_NAME);
//                        maSituationGuichet.setMontant_demarrage(movies.getString(KEY_CmMontant));
                        maSituationGuichet.setMontant_demarrage(defaultFormat.format(parseDouble(movies.getString(KEY_CmMontant))));
                        maSituationGuichet.setDepot(defaultFormat.format(parseDouble(movies.getString(KEY_total_depot))));
                        maSituationGuichet.setRetrait(defaultFormat.format(parseDouble(movies.getString(KEY_total_retrait))));
                        maSituationGuichet.setRemboursement_credit("0 FCFA");
                        maSituationGuichet.setFrais_inscription("0 FCFA");
                        maSituationGuichet.setCommission("0 FCFA");
                        maSituationGuichet.setRecuperation_credit("0 FCFA");
                        maSituationGuichet.setSortie_transfert("0 FCFA");
                        maSituationGuichet.setEntree_transfert("0 FCFA");
                        maSituationGuichet.setCotisation_annuelle("0 FCFA");
//                        maSituationGuichet.setCharges("0 FCFA");
                        maSituationGuichet.setCharges(defaultFormat.format(parseDouble(movies.getString(KEY_total_charge))));
//                        maSituationGuichet.setProduits("0 FCFA");
                        maSituationGuichet.setProduits(defaultFormat.format(parseDouble(movies.getString(KEY_total_produit))));
                    total_operation = defaultFormat.format(parseDouble(movies.getString(KEY_total_operation)));

//                        eavList.add(cat);


//                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {

                    tv_jour_ouvre.setText(maSituationGuichet.getJour_ouvre());
                    tv_caisse.setText(maSituationGuichet.getCaisse());
                    tv_guichet.setText(maSituationGuichet.getGuichet());
                    tv_montant_demarrage.setText(maSituationGuichet.getMontant_demarrage());
                    tv_depot.setText(maSituationGuichet.getDepot());
                    tv_retrait.setText(maSituationGuichet.getRetrait());
                    tv_remboursement_credit.setText(maSituationGuichet.getRemboursement_credit());
                    tv_frais_inscription.setText(maSituationGuichet.getFrais_inscription());
                    tv_commission.setText(maSituationGuichet.getCommission());
                    tv_recuperation_credit.setText(maSituationGuichet.getRecuperation_credit());
                    tv_sortie_transfert.setText(maSituationGuichet.getSortie_transfert());
                    tv_entree_transfert.setText(maSituationGuichet.getEntree_transfert());
                    tv_cotisation_annuelle.setText(maSituationGuichet.getCotisation_annuelle());
                    tv_charges.setText(maSituationGuichet.getCharges());
                    tv_produits.setText(maSituationGuichet.getProduits());
                    tv_total.setText(total_operation);

                    //populateGuichetList();
//                    loadRecyclerViewItem();
                }
            });
        }

    }
    /**
     * Async task to get all food categories
     * */
    private class GetProduitEAVList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogFetchProduitEavList = new ProgressDialog(SituationGuichet.this);
            pDialogFetchProduitEavList.setMessage("Fetching produits's list..");
            pDialogFetchProduitEavList.setCancelable(false);
            pDialogFetchProduitEavList.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair(KEY_EV_CAISSE_ID, String.valueOf(MyData.CAISSE_ID)));
            httpParams.add(new BasicNameValuePair(KEY_EV_GUICHET_ID, String.valueOf(MyData.GUICHET_ID)));
            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_eav_by_guichet.php", ServiceHandler.GET, httpParams);
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
                            Category cat = new Category(catObj.getInt(KEY_EAV_ID),
                                    catObj.getString(KEY_EAV_LIBELLE));
                            eavList.add(cat);
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
     /*   Toast.makeText(
                getApplicationContext(),
                parent.getItemAtPosition(position).toString() + " Selected" ,
                Toast.LENGTH_LONG).show();
        */
        eavID = eavListID.get(position);//pour recuperer l'ID du guichet selectionnée

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }



}