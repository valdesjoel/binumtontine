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

public class OperationDecouvertPermanent extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    /*Begin*/
         //to fetchProduitList by caisse and guichet ID
    private static final String KEY_EV_CAISSE_ID = "ev_caisse_id";
    private static final String KEY_EV_GUICHET_ID = "ev_gx_numero";
    /* end*/
    private static final String KEY_EAV_ID = "ev_numero";
    private static final String KEY_EAV_LIBELLE = "ev_libelle";

    private static final String KEY_CV_PRODUIT = "CvProduit";
    private static final String KEY_CV_MEMBRE = "CvMembre";
    private static final String KEY_CV_GUICHET = "CvGuichet";
    private static final String KEY_CV_NUM_DOSSIER = "CcaNumDossier";
    private static final String KEY_CV_MT_SOLDE = "CcaMtSolde";
    private static final String KEY_CV_NATURE_OPERATION = "NatureOp";
    private static final String KEY_CV_USER_CREE = "CcaUserCree";
    private static final String KEY_ADHERENT_NUM_DOSSIER = "CvNumDossier";
    private static final String KEY_OccLibelleMvmCC = "OccLibelleMvmCC";

    private static final String KEY_MONTANT_COMPTE = "MtSolde";
    private static final String KEY_TYPE_OPERATION = "TypeOperation";
    private static final String KEY_ADHERENT = "ADHERENT";
    private static final String KEY_LIBELLE_PRODUIT = "Libelle";
    private Adherent adherent;
    /*Param for get extra*/
    private static final String KEY_ADHERENT_ID = "IpMembre";
    private static final String KEY_COMPTE_ID = "Numero";
    private static final String KEY_CV_NUMERO = "CcaNumero";

    private static final String KEY_ADHERENT_NOM = "AdNom";
    private static final String KEY_ADHERENT_PRENOM = "AdPrenom";
    private static final String KEY_ADHERENT_NUM_MANUEL = "AdNumManuel";
    private static final String KEY_ADHERENT_CODE = "AdCode";



    private static String STRING_EMPTY = "";

    TextInputLayout layoutNumDossier, layoutPiecesFournies, layoutMontantDemande;
    private EditText EavDepotMinEditText;
    private EditText NumDossierEditText;
    private EditText OccLibelleMvmCC;
    private EditText OcMotifDemande;
    private EditText OcDocumentFournies;
    public static RadioButton rb_depot;
    public static RadioButton rb_retrait;
    public static RadioButton rb_decouvert;
    public static RadioButton rb_avance_speciale;


    private String compteId;
    private String eavDepotMin;
    private String adNom;
    private String adPrenom;
    private String adNumManuel;
    private String adCode;
    private String compteSolde;
    private String typeOperation;
    private String adNumDossier;
    private String libelleProduit;

    private String natureOperation;
    private String st_OccLibelleMvmCC;

    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Category> eavList;
    List<Integer> eavListID = new ArrayList<Integer>();
    private int eavID;
    private Spinner spinnerListEAV;
    private TextView tvAdherentNom;
    private TextView tvHeaderOperationEAV;
    private TextView tvAdherentNumManuel;
    private TextView tvAdherentCode;
    private TextView tvCompteSolde;
    private TextView tvLibelleProduit;
    /*end manage*/

    private Button addButton;
    private Button annulerButton;
    private int success;
    private ProgressDialog pDialog;
    private ProgressDialog pDialogFetchProduitEavList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demande_decouvert_permanent_adherent);


        Intent intent = getIntent();
        compteId = intent.getStringExtra(KEY_COMPTE_ID);

//        Toast.makeText(OperationCompteCourant.this,
//                compteId,
//                Toast.LENGTH_LONG).show();
        compteSolde = intent.getStringExtra(KEY_MONTANT_COMPTE);
        typeOperation = intent.getStringExtra(KEY_TYPE_OPERATION);
        libelleProduit = intent.getStringExtra(KEY_LIBELLE_PRODUIT);
        Bundle bundle = intent.getExtras();
        adherent = (Adherent) bundle.getSerializable(KEY_ADHERENT);


        EavDepotMinEditText = (EditText) findViewById(R.id.input_txt_depot_min);
        EavDepotMinEditText.addTextChangedListener(MyData.onTextChangedListener(EavDepotMinEditText));


        layoutNumDossier = (TextInputLayout) findViewById(R.id.input_layout_numero_bordereau_operation);
        layoutPiecesFournies = (TextInputLayout) findViewById(R.id.input_layout_OcLibelleMvmEAV);
        layoutMontantDemande = (TextInputLayout) findViewById(R.id.input_layout_montant_operation);
        layoutMontantDemande.setHint("Montant de la demande");


        NumDossierEditText = (EditText) findViewById(R.id.input_txt_numero_bordereau_operation);
//        NumDossierEditText.setHint("Numéro de dossier");
        OccLibelleMvmCC = (EditText) findViewById(R.id.input_txt_OcLibelleMvmEAV);
        OcMotifDemande = (EditText) findViewById(R.id.input_txt_OcMotifDemande);
        OcDocumentFournies = (EditText) findViewById(R.id.input_txt_OcDocumentFournies);
        OcMotifDemande.setVisibility(View.VISIBLE);
        rb_depot = (RadioButton) findViewById(R.id.rb_nature_operation_depot);
        //rb_decision_accordee.performClick();
        //onRadioButtonClicked(rb_decision_accordee);
        rb_retrait = (RadioButton) findViewById(R.id.rb_nature_operation_retrait);
        rb_decouvert = (RadioButton) findViewById(R.id.rb_nature_operation_decouvert);
        rb_avance_speciale = (RadioButton) findViewById(R.id.rb_nature_operation_avance_speciale);
        tvHeaderOperationEAV = (TextView) findViewById(R.id.header_operation_eav_adherent);
        spinnerListEAV = (Spinner) findViewById(R.id.spn_mode_paiement);
        tvAdherentNom = (TextView) findViewById(R.id.tv_nom_adherent);

        tvAdherentNom.setText(ListCompteAdherentActivity_New.adherent.getAdNom()+"\n"+ListCompteAdherentActivity_New.adherent.getAdPrenom());
        tvAdherentNumManuel = (TextView) findViewById(R.id.tv_num_manuel_adherent);
        tvAdherentNumManuel.setText(ListCompteAdherentActivity_New.adherent.getAdNumManuel());
        tvAdherentCode = (TextView) findViewById(R.id.tv_code_adherent);
        tvAdherentCode.setText(ListCompteAdherentActivity_New.adherent.getAdCode());
        tvCompteSolde = (TextView) findViewById(R.id.tv_solde_compte);
        tvCompteSolde.setText(compteSolde);

        tvLibelleProduit = (TextView) findViewById(R.id.tv_libelle_produit_adherent);
//        tvLibelleProduit.setText(libelleProduit);
        tvLibelleProduit.setText(MyData.LIBELLE_PRODUIT_CPTE_COURANT); //A revoir

        if (typeOperation.equals("depot")){
            tvHeaderOperationEAV.setText("TYPE OPERATION: DEPOT");
            rb_depot.setChecked(true);
            rb_retrait.setVisibility(View.GONE);
            rb_decouvert.setVisibility(View.GONE);
            rb_avance_speciale.setVisibility(View.GONE);
            rb_depot.setVisibility(View.VISIBLE);
            onRadioButtonClicked(rb_depot);
            OccLibelleMvmCC.setText("VERSEMENT CPT COUR/\n"+
                    ListCompteAdherentActivity_New.adherent.getAdCode()+ "DE "+
                    ListCompteAdherentActivity_New.adherent.getAdNom()+" "+
                    ListCompteAdherentActivity_New.adherent.getAdPrenom());
        }else if(typeOperation.equals("retrait")){
            tvHeaderOperationEAV.setText("TYPE OPERATION: RETRAIT");
            rb_retrait.setChecked(true);
            OccLibelleMvmCC.setText("RETRAIT CPT COUR/\n"+
                    ListCompteAdherentActivity_New.adherent.getAdCode()+ "DE "+
                    ListCompteAdherentActivity_New.adherent.getAdNom()+" "+
                    ListCompteAdherentActivity_New.adherent.getAdPrenom());
            onRadioButtonClicked(rb_retrait);
            rb_retrait.setVisibility(View.VISIBLE);
            rb_depot.setVisibility(View.GONE);
            rb_decouvert.setVisibility(View.GONE);
            rb_avance_speciale.setVisibility(View.GONE);
        }else if(typeOperation.equals("decouvert")){
            compteId = ListCompteDecouvertAdherentActivity.adherent.getNumero_compte()+"";
            tvHeaderOperationEAV.setText("DEMANDE DE DECOUVERT");
            rb_decouvert.setChecked(true);
            NumDossierEditText.setText("DECOUV SIMPLE/\n"+
                    ListCompteAdherentActivity_New.adherent.getAdCode()+ "DE "+
                    ListCompteAdherentActivity_New.adherent.getAdNom()+" "+
                    ListCompteAdherentActivity_New.adherent.getAdPrenom());
            onRadioButtonClicked(rb_decouvert);
            rb_retrait.setVisibility(View.GONE);
            rb_depot.setVisibility(View.GONE);
            rb_avance_speciale.setVisibility(View.GONE);
            rb_decouvert.setVisibility(View.VISIBLE);
            layoutPiecesFournies.setHint("Pièces fournies");
            layoutNumDossier.setHint("Numéro de dossier");
        }else if(typeOperation.equals("avance_speciale")){
            compteId = ListCompteAvanceSpecialeAdherentActivity.adherent.getNumero_compte()+"";
            tvHeaderOperationEAV.setText("DEMANDE AVANCE SPECIALE");
            rb_avance_speciale.setChecked(true);
            NumDossierEditText.setText("AVANCE SPEC/\n"+
                    ListCompteAdherentActivity_New.adherent.getAdCode()+ "DE "+
                    ListCompteAdherentActivity_New.adherent.getAdNom()+" "+
                    ListCompteAdherentActivity_New.adherent.getAdPrenom());
            onRadioButtonClicked(rb_avance_speciale);
            rb_retrait.setVisibility(View.GONE);
            rb_depot.setVisibility(View.GONE);
            rb_decouvert.setVisibility(View.GONE);
            rb_avance_speciale.setVisibility(View.VISIBLE);

            layoutPiecesFournies.setHint("Pièces fournies");
            layoutNumDossier.setHint("Numéro de dossier");

        }

       /* tvAdherentNumDossier = (TextView) findViewById(R.id.tv_num_dossier_adherent);
        tvAdherentNumDossier.setText(adNumDossier);*/
/*
        eavList = new ArrayList<Category>();
        // spinner item select listener
        spinnerListEAV.setOnItemSelectedListener(OperationEAV.this);
        new GetProduitEAVList().execute();
*/
        addButton = (Button) findViewById(R.id.btn_save_operation_eav);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(OperationDecouvertPermanent.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addEavAdherent();
                } else {
                    Toast.makeText(OperationDecouvertPermanent.this,
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

            case R.id.rb_nature_operation_depot:
                if (rb_depot.isChecked()) {
                    natureOperation = "D";
                    //str = checked1?"Nature frais fixe":"";

                }
                break;
            case R.id.rb_nature_operation_retrait:
                if (rb_retrait.isChecked()) {
                    natureOperation = "R";
                    // str = checked1?"Nature frais taux":"";

                }

                break;
            case R.id.rb_nature_operation_decouvert:
                if (rb_decouvert.isChecked()) {
                    natureOperation = "V";
                    // str = checked1?"Nature frais taux":"";

                }

                break;
            case R.id.rb_nature_operation_avance_speciale:
                if (rb_avance_speciale.isChecked()) {
                    natureOperation = "A";
                    // str = checked1?"Nature frais taux":"";

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

        for (int i = 0; i < eavList.size(); i++) {
            lables.add(eavList.get(i).getName());//recupère les noms
            eavListID.add(eavList.get(i).getId()); //recupère les Id
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(OperationDecouvertPermanent.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerListEAV.setAdapter(spinnerAdapter);
    }

    /**
     * Async task to get all food categories
     * */
    private class GetProduitEAVList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogFetchProduitEavList = new ProgressDialog(OperationDecouvertPermanent.this);
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


    /**
     * Checks whether all files are filled. If so then calls AddEavAdherentAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addEavAdherent() {
        if (!STRING_EMPTY.equals(EavDepotMinEditText.getText().toString())

                 ) {
//            Log.e("**************",compteSolde);
////String rr = compteSolde.replace(" FCFA","").replaceAll(",","\\.");
//String rr = compteSolde.replace("FCFA","").trim().replaceAll(",","\\.").replaceAll(" ","");
//
//            NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
//            nf.setGroupingUsed(false);
            //nf.format(rr);
//if (natureOperation.equals("R")&&
//                    (Double.parseDouble(EavDepotMinEditText.getText().toString())<Double.parseDouble(rr))){
            Double h1;

            h1 = Double.valueOf(EavDepotMinEditText.getText().toString().replaceAll(",", "").trim());
            EavDepotMinEditText.setText(h1+"");
if (true){
//                Toast.makeText(OperationEAV.this,
//                        "Solde insuffisant!",
//                        Toast.LENGTH_LONG).show();
//                Toast.makeText(OperationEAV.this,
//                        rr.trim()+ "\n"+rr.length(),
//                        Toast.LENGTH_LONG).show();

//   eavDepotMin = EavDepotMinEditText.getText().toString().replaceAll(",", "").trim();
   eavDepotMin = EavDepotMinEditText.getText().toString();
//    eavDepotMin = EavDepotMinEditText.getText().toString();
    adNumDossier = NumDossierEditText.getText().toString();
    st_OccLibelleMvmCC = OccLibelleMvmCC.getText().toString();

    new AddEavAdherentAsyncTask().execute();
            }else
            {
//
//                eavDepotMin = EavDepotMinEditText.getText().toString();
//                adNumDossier = NumDossierEditText.getText().toString();
//
//                new AddEavAdherentAsyncTask().execute();
            }




        } else {
            Toast.makeText(OperationDecouvertPermanent.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for adding a compte eav
     */
    private class AddEavAdherentAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(OperationDecouvertPermanent.this);
            pDialog.setMessage("Transaction en cours. Veuillez patienter...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
           // httpParams.put(KEY_EAV_ID, uxGuichetId);

            httpParams.put(KEY_CV_NUMERO, compteId);

            httpParams.put(KEY_CV_NUM_DOSSIER, adNumDossier);
            httpParams.put(KEY_CV_MT_SOLDE, eavDepotMin );
            httpParams.put(KEY_CV_NATURE_OPERATION, natureOperation );
            httpParams.put(KEY_CV_USER_CREE, String.valueOf(MyData.USER_ID));
            httpParams.put(KEY_OccLibelleMvmCC, st_OccLibelleMvmCC);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "operation_compte_courant_adherent_new.php", "POST", httpParams);
            Log.e("***compteId",compteId+"\n");
            Log.e("***eavDepotMin",eavDepotMin+"\n");
            Log.e("***natureOperation",natureOperation+"\n");
            Log.e("***KEY_CV_USER_CREE",MyData.USER_ID+"\n");
            Log.e("***KEY_OccLibelleMvmCC",st_OccLibelleMvmCC+"\n");
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
                        Toast.makeText(OperationDecouvertPermanent.this,
                                "Opération réussie !", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(OperationDecouvertPermanent.this,
                                "Echec!\n Vérifiez votre solde ",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}