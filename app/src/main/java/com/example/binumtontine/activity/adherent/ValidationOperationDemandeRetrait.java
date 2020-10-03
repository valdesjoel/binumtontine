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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.Category;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class ValidationOperationDemandeRetrait extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    /*Begin*/
         //to fetchProduitList by caisse and guichet ID
    private static final String KEY_RaNumero = "RaNumero";
    private static final String KEY_EV_GUICHET_ID = "ev_gx_numero";

    /* end*/
    private static final String KEY_EAV_ID = "ev_numero";
    private static final String KEY_DfNumDoss = "RaAffilier";
//    private static final String KEY_DfNumDoss = "DfNumDoss";
    private static final String KEY_DfMtDecouv = "RaMtDemde";
    private static final String KEY_DfDocumDecFCx = "RaInfos";
    private static final String KEY_RaDatHRetraitSouhaiter = "RaDatHRetraitSouhaiter";
    private static final String KEY_ux_nom = "ux_nom";
    private static final String KEY_ux_prenom = "ux_prenom";
    private static final String KEY_RaStatut = "RaStatut";
    private static final String KEY_DfDetailsReponse = "DfDetailsReponse";
    private static final String KEY_RaIsAccorde = "RaIsAccorde";
    private static final String KEY_RaUserValide = "RaUserValide";
    private static final String KEY_RaMtAccorde = "RaMtAccorde";
    private static final String KEY_DfRepAdherent = "DfRepAdherent";

    private static final String KEY_CV_PRODUIT = "CvProduit";
    private static final String KEY_CV_MEMBRE = "CvMembre";
    private static final String KEY_CV_GUICHET = "CvGuichet";
    private static final String KEY_CV_NUM_DOSSIER = "CcaNumDossier";
    private static final String KEY_CV_MT_SOLDE = "CcaMtSolde";
    private static final String KEY_CV_NATURE_OPERATION = "NatureOp";

    private static final String KEY_ADHERENT_NUM_DOSSIER = "CvNumDossier";
    private static final String KEY_OccLibelleMvmCC = "OccLibelleMvmCC";

    private static final String KEY_MONTANT_COMPTE = "MtSolde";
    private static final String KEY_TYPE_OPERATION = "TypeOperation";
    private static final String KEY_ADHERENT = "ADHERENT";
    private static final String KEY_LIBELLE_PRODUIT = "Libelle";
    private String AdNom="";
    private String AdPrenom="";
    private String AdNumManuel="";
    private String AdCode="";
    private Adherent adherent;
    /*Param for get extra*/
    private static final String KEY_ADHERENT_ID = "IpMembre";
    private static final String KEY_COMPTE_ID = "Numero";
    private static final String KEY_CV_NUMERO = "CcaNumero";

    private static final String KEY_ADHERENT_NOM = "AdNom";
    private static final String KEY_ADHERENT_PRENOM = "AdPrenom";
    private static final String KEY_ADHERENT_NUM_MANUEL = "AdNumManuel";
    private static final String KEY_ADHERENT_CODE = "AdCode";
    private static final String KEY_CvMtSolde = "CvMtSolde";
    private static final String KEY_RaTypOper = "RaTypOper";



    private static String STRING_EMPTY = "";

    TextInputLayout layoutNumDossier, layoutPiecesFournies, layoutMontantDemande;
    private EditText EavDepotMinEditText;
    private EditText NumDossierEditText;
    private EditText OccLibelleMvmCC;
    private EditText OcMotifDemande;
    public static RadioButton rb_decision_accordee;
    public static RadioButton rb_decision_modifiee;
    public static RadioButton rb_decision_refusee;
    public static RadioButton rb_DfRepAdherent_accepte;
    public static RadioButton rb_DfRepAdherent_refuse;
    public static RadioButton rb_decouvert;
    public static RadioButton rb_avance_speciale;


    private String compteId;
    private String RaMtAccorde;
    private String adNom;
    private String adPrenom;
    private String adNumManuel;
    private String adCode;
    private String cvMtSolde;
    private String RaTypOper="";
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
    private TextView tv_title_solde_compte;
    private TextView tv_solde_affilie;
    private TextView tvBodyMotifDemandeAdherent;
    private TextView tvBodyNumDossierAdherent;
    private TextView tvLibelleProduit;
    private TextView header_validation_demande;
    private TextView tv_montant_chequier;
    private NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
    /*end manage*/

    private Button addButton;
    private Button annulerButton;
    private int success;
    private ProgressDialog pDialog;
    private ProgressDialog pDialogFetchProduitEavList;
    private String DfMtDecouv;
    private String DfDocumDecFCx;
    private String DfDetailsReponse;
    private String DfNumDoss;
    private String RaIsAccorde;
    private String RaStatut;
    private String DfRepAdherent;
    private LinearLayout ll_chequier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation_operation_demande_retrait_affilier);
        defaultFormat.setCurrency(Currency.getInstance("XAF"));


        Intent intent = getIntent();
        compteId = intent.getStringExtra(KEY_COMPTE_ID);

//        Toast.makeText(OperationCompteCourant.this,
//                compteId,
//                Toast.LENGTH_LONG).show();
        compteSolde = intent.getStringExtra(KEY_MONTANT_COMPTE);
        typeOperation = intent.getStringExtra(KEY_TYPE_OPERATION);
        libelleProduit = intent.getStringExtra(KEY_LIBELLE_PRODUIT);
        /*Bundle bundle = intent.getExtras();
        adherent = (Adherent) bundle.getSerializable(KEY_ADHERENT);
        */
//        adNom = adherent.getAdNom();
//        adPrenom = adherent.getAdPrenom();
//        adNumManuel = adherent.getAdNumManuel();
//        adCode = adherent.getAdCode();

        //adNumDossier = intent.getStringExtra(KEY_ADHERENT_NUM_DOSSIER);

        EavDepotMinEditText = (EditText) findViewById(R.id.input_txt_depot_min);
        EavDepotMinEditText.addTextChangedListener(MyData.onTextChangedListener(EavDepotMinEditText));


        layoutNumDossier = (TextInputLayout) findViewById(R.id.input_layout_numero_bordereau_operation);
        layoutPiecesFournies = (TextInputLayout) findViewById(R.id.input_layout_OcLibelleMvmEAV);
        layoutMontantDemande = (TextInputLayout) findViewById(R.id.input_layout_montant_operation);
//        layoutMontantDemande.setHint("Montant de la demande");


        NumDossierEditText = (EditText) findViewById(R.id.input_txt_numero_bordereau_operation);
//        NumDossierEditText.setHint("Numéro de dossier");
        OccLibelleMvmCC = (EditText) findViewById(R.id.input_txt_OcLibelleMvmEAV);
        OcMotifDemande = (EditText) findViewById(R.id.input_txt_OcMotifDemande);
        OcMotifDemande.setVisibility(View.VISIBLE);
        rb_DfRepAdherent_refuse = (RadioButton) findViewById(R.id.rb_DfRepAdherent_refuse);
        rb_DfRepAdherent_accepte = (RadioButton) findViewById(R.id.rb_DfRepAdherent_accepte);
        rb_decision_accordee = (RadioButton) findViewById(R.id.rb_nature_operation_depot);
        rb_decision_modifiee = (RadioButton) findViewById(R.id.rb_decision_modifiee);
        //rb_decision_accordee.performClick();
        //onRadioButtonClicked(rb_decision_accordee);
        rb_decision_refusee = (RadioButton) findViewById(R.id.rb_nature_operation_retrait);
        rb_decouvert = (RadioButton) findViewById(R.id.rb_nature_operation_decouvert);
        rb_avance_speciale = (RadioButton) findViewById(R.id.rb_nature_operation_avance_speciale);
        tvHeaderOperationEAV = (TextView) findViewById(R.id.header_operation_eav_adherent);
        spinnerListEAV = (Spinner) findViewById(R.id.spn_mode_paiement);
        tvAdherentNom = (TextView) findViewById(R.id.tv_nom_adherent);

//        tvAdherentNom.setText(ListCompteAdherentActivity_New.adherent.getAdNom()+"\n"+ListCompteAdherentActivity_New.adherent.getAdPrenom());
        tvAdherentNumManuel = (TextView) findViewById(R.id.tv_num_manuel_adherent);
//        tvAdherentNumManuel.setText(ListCompteAdherentActivity_New.adherent.getAdNumManuel());
        tvAdherentCode = (TextView) findViewById(R.id.tv_code_adherent);
//        tvAdherentCode.setText(ListCompteAdherentActivity_New.adherent.getAdCode());
        //        tvAdherentNumManuel.setText(ListCompteAdherentActivity_New.adherent.getAdNumManuel());
        //        tvAdherentNom.setText(ListCompteAdherentActivity_New.adherent.getAdNom()+"\n"+ListCompteAdherentActivity_New.adherent.getAdPrenom());

        tvCompteSolde = (TextView) findViewById(R.id.tv_solde_compte);
        tv_title_solde_compte = (TextView) findViewById(R.id.tv_title_solde_compte);
        tv_solde_affilie = (TextView) findViewById(R.id.tv_solde_affilie);
        tvBodyMotifDemandeAdherent = (TextView) findViewById(R.id.tv_body_motif_demande_adherent);
        tvBodyNumDossierAdherent = (TextView) findViewById(R.id.tv_body_num_dossier_adherent);
        tvCompteSolde.setText(compteSolde);

        tvLibelleProduit = (TextView) findViewById(R.id.tv_libelle_produit_adherent);
        header_validation_demande = (TextView) findViewById(R.id.header_validation_demande);
        tv_montant_chequier = (TextView) findViewById(R.id.tv_montant_chequier);
        ll_chequier = (LinearLayout) findViewById(R.id.ll_chequier);
//        tvLibelleProduit.setText(libelleProduit);
//        tvLibelleProduit.setText(MyData.LIBELLE_PRODUIT_CPTE_COURANT);
        tvLibelleProduit.setText("COMPTE COLLECTE");




        new GetProduitEAVList().execute();

        addButton = (Button) findViewById(R.id.btn_save_operation_eav);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(ValidationOperationDemandeRetrait.this,
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
                    Toast.makeText(ValidationOperationDemandeRetrait.this,
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
//        boolean checked1 = ((RadioButton) view).isChecked();
//        String str="";
//        // Check which checkbox was clicked
//        switch(view.getId()) {
//
//            case R.id.rb_nature_operation_depot:
//                if (rb_decision_accordee.isChecked()) {
//                    natureOperation = "D";
//                    //str = checked1?"Nature frais fixe":"";
//
//                }
//                break;
//            case R.id.rb_nature_operation_retrait:
//                if (rb_decision_refusee.isChecked()) {
//                    natureOperation = "R";
//                    // str = checked1?"Nature frais taux":"";
//
//                }
//
//                break;
//            case R.id.rb_nature_operation_decouvert:
//                if (rb_decouvert.isChecked()) {
//                    natureOperation = "V";
//                    // str = checked1?"Nature frais taux":"";
//
//                }
//
//                break;
//            case R.id.rb_nature_operation_avance_speciale:
//                if (rb_avance_speciale.isChecked()) {
//                    natureOperation = "A";
//                    // str = checked1?"Nature frais taux":"";
//
//                }
//
//                break;
//
//
//        }
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
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(ValidationOperationDemandeRetrait.this,
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
            pDialogFetchProduitEavList = new ProgressDialog(ValidationOperationDemandeRetrait.this);
            pDialogFetchProduitEavList.setMessage("Fetching produits's list..");
            pDialogFetchProduitEavList.setCancelable(false);
            pDialogFetchProduitEavList.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {


            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_RaNumero, compteId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_retrait_affilie_details.php", "GET", httpParams);
            Log.e("JSON Data", jsonObject+"");
            try {
//                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject creditJson;
//                if (success == 1) {
                //Parse the JSON response
//                    creditJson = jsonObject.getJSONObject(KEY_DATA);




                DfMtDecouv = jsonObject.getString(KEY_DfMtDecouv);
                DfNumDoss = jsonObject.getString(KEY_DfNumDoss);
                DfDocumDecFCx = jsonObject.getString(KEY_DfDocumDecFCx);
                DfDetailsReponse = jsonObject.getString(KEY_ux_nom)+" "+jsonObject.getString(KEY_ux_prenom);
                adNom = jsonObject.getString(KEY_ADHERENT_NOM);
                adPrenom = jsonObject.getString(KEY_ADHERENT_PRENOM);
                adNumManuel = jsonObject.getString(KEY_ADHERENT_NUM_MANUEL);
                adCode = jsonObject.getString(KEY_ADHERENT_CODE);
                cvMtSolde = jsonObject.getString(KEY_CvMtSolde);
                RaTypOper = jsonObject.getString(KEY_RaTypOper);
                RaMtAccorde = jsonObject.getString(KEY_RaMtAccorde);

                Log.e("DfDetailsReponse: ", "> " + DfDetailsReponse);

            } catch (JSONException e) {
                e.printStackTrace();
            }

//            Log.e("Response: ", "> " + json);



            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialogFetchProduitEavList.isShowing())
                pDialogFetchProduitEavList.dismiss();
//            populateSpinner();
            runOnUiThread(new Runnable() {
                public void run() {

                    EavDepotMinEditText.setText(DfMtDecouv);
//                    NumDossierEditText.setText(DfNumDoss);
                    OccLibelleMvmCC.setText(DfDocumDecFCx);
                    tvBodyMotifDemandeAdherent.setText(DfDetailsReponse);
                    tvBodyNumDossierAdherent.setText(DfNumDoss);

                    tvAdherentCode.setText(adCode);
                    tvAdherentNumManuel.setText(adNumManuel);
                    tvAdherentNom.setText(adNom+"\n"+adPrenom);
                    tv_solde_affilie.setText(defaultFormat.format(parseDouble(cvMtSolde)));
                    if (RaTypOper.equals("DR")){
                        header_validation_demande.setText("TRAITEMENT DEMANDE DE RETRAIT");
                    }else if (RaTypOper.equals("PC")){
                        header_validation_demande.setText("TRAITEMENT PAIEMENT DE CHEQUE");
                    }else if (RaTypOper.equals("DC")){
                        header_validation_demande.setText("TRAITEMENT DEMANDE DE CHEQUIER");
                        tv_title_solde_compte.setText("Nombre de page");
                        tv_montant_chequier.setText(defaultFormat.format(Double.parseDouble(RaMtAccorde)));
                        ll_chequier.setVisibility(View.VISIBLE);
                        layoutMontantDemande.setVisibility(View.GONE);
                        EavDepotMinEditText.setText(RaMtAccorde);
                    }else if (RaTypOper.equals("TS")){
                        header_validation_demande.setText("TRAITEMENT TRANSFERT SOLDE");
                    }

                }
            });
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
        Log.e("***compteId",compteId+"\n");
        if ((rb_decision_refusee.isChecked() || rb_decision_accordee.isChecked() || rb_decision_modifiee.isChecked()) &&
                !STRING_EMPTY.equals(EavDepotMinEditText.getText().toString())
                 ) {

            Double h1;

            h1 = Double.valueOf(EavDepotMinEditText.getText().toString().replaceAll(",", "").trim());
            EavDepotMinEditText.setText(h1+"");


//   DfMtAccordeCom = EavDepotMinEditText.getText().toString().replaceAll(",", "").trim();
            if (rb_decision_accordee.isChecked()){
                RaIsAccorde = "0";
                RaStatut = "A";
            }else if (rb_decision_refusee.isChecked()){
                RaIsAccorde = "0";
                RaStatut = "R";
            }else if (rb_decision_modifiee.isChecked()){
                RaIsAccorde = "0";
                RaStatut = "A";
            }
      /*      if (rb_DfRepAdherent_accepte.isChecked()){
                DfRepAdherent = "TRUE";
            }else{
                DfRepAdherent = "FALSE";
            }*/

            RaMtAccorde = EavDepotMinEditText.getText().toString();
//    DfMtAccordeCom = EavDepotMinEditText.getText().toString();
//    adNumDossier = NumDossierEditText.getText().toString();
//    st_OccLibelleMvmCC = OccLibelleMvmCC.getText().toString();

//    DfDetailsReponse = OcMotifDemande.getText().toString();

    new AddEavAdherentAsyncTask().execute();





        } else {
            Toast.makeText(ValidationOperationDemandeRetrait.this,
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
            pDialog = new ProgressDialog(ValidationOperationDemandeRetrait.this);
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

            httpParams.put(KEY_RaNumero, compteId);

            httpParams.put(KEY_RaStatut, RaStatut);
            httpParams.put(KEY_RaMtAccorde, RaMtAccorde);
//            httpParams.put(KEY_CV_NATURE_OPERATION, natureOperation );
            httpParams.put(KEY_RaUserValide, String.valueOf(MyData.USER_ID));
//            httpParams.put(KEY_DfDetailsReponse, DfDetailsReponse);
            httpParams.put(KEY_RaIsAccorde, RaIsAccorde);
//            httpParams.put(KEY_DfRepAdherent, DfRepAdherent);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "validation_operation_demande_retrait_affilier.php", "POST", httpParams);
            Log.e("***compteId",compteId+"\n");
            Log.e("***DfMtAccordeCom", RaMtAccorde +"\n");
            Log.e("***natureOperation",natureOperation+"\n");
            Log.e("***KEY_DfUserValide",MyData.USER_ID+"\n");
//            Log.e("***KEY_OccLibelleMvmCC",st_OccLibelleMvmCC+"\n");
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
                        Toast.makeText(ValidationOperationDemandeRetrait.this,
                                "Opération réussie !", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        ListDemandeRetraitAffilierActivity.typeOperation = typeOperation;
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(ValidationOperationDemandeRetrait.this,
                                "Echec!\n Vérifiez votre solde ",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}