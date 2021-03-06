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


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
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

import androidx.annotation.RequiresApi;
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OperationEAT extends AppCompatActivity implements  View.OnClickListener, AdapterView.OnItemSelectedListener, SERVER_ADDRESS {
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
    private static final String KEY_CV_NUM_DOSSIER = "CvNumDossier";
    private static final String KEY_CV_MT_SOLDE = "CvMtSolde";
    private static final String KEY_CV_NATURE_OPERATION = "NatureOp";
    private static final String KEY_CV_USER_CREE = "CvUserCree";
    private static final String KEY_ADHERENT_NUM_DOSSIER = "CvNumDossier";

    private static final String KEY_MONTANT_COMPTE = "MtSolde";
    private static final String KEY_ADHERENT = "ADHERENT";
    private Adherent adherent;
    /*Param for get extra*/
    private static final String KEY_ADHERENT_ID = "IpMembre";
    private static final String KEY_COMPTE_ID = "Numero";
    private static final String KEY_CV_NUMERO = "CvNumero";
    private static final String KEY_DATE_H_CREE = "DateHCree";
    private static final String KEY_CtDateFin = "CtDateFin";
    private static final String KEY_CtNbUnites = "CtNbUnites";
    private static final String KEY_CtPeriod = "CtPeriod";
    private static final String KEY_TAUX = "Taux";
    private static final String KEY_LIBELLE_PRODUIT = "Libelle";
    private static final String KEY_ADHERENT_NOM = "AdNom";
    private static final String KEY_ADHERENT_PRENOM = "AdPrenom";
    private static final String KEY_ADHERENT_NUM_MANUEL = "AdNumManuel";
    private static final String KEY_ADHERENT_CODE = "AdCode";
    private static final String KEY_MontantARetirer = "MontantARetirer";



    private static String STRING_EMPTY = "";

    private EditText EavDepotMinEditText;
    private EditText NumDossierEditText;
    private TextView tvLibelleProduit;

    private RadioButton rb_depot;
    private RadioButton rb_retrait;


    private String compteId;
    private String eavDepotMin;
    private String adNom;
    private String adPrenom;
    private String adNumManuel;
    private String adCode;
    private String compteSolde;
    private String adNumDossier;
    private String dateCreation;
    private String taux;
    private String libelleProduit;

    private String dateFin;
    private String nbUnites;
    private String period;
    private String dureeDuCompte;
    private String interet;
    private String montantARetirer;

    private String AdValideDu;
    private String AdValideAu;
    private String natureOperation;

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

    private TextView tvDateEcheance;
    private TextView tvDureeCompte;
    private TextView tvMontantARetirer;

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

    private RadioButton rb_transfert_eav;
    private RadioButton rb_renouveller;
    private RadioButton rb_retirer;
    private String eatCtModRenouv="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_eat_adherent);


        Intent intent = getIntent();
        compteId = intent.getStringExtra(KEY_COMPTE_ID);
        dateCreation = intent.getStringExtra(KEY_DATE_H_CREE);

        dateFin = intent.getStringExtra(KEY_CtDateFin);
        nbUnites = intent.getStringExtra(KEY_CtNbUnites);
        period = intent.getStringExtra(KEY_CtPeriod);
        compteSolde = intent.getStringExtra(KEY_MONTANT_COMPTE);
        taux = intent.getStringExtra(KEY_TAUX);
        libelleProduit = intent.getStringExtra(KEY_LIBELLE_PRODUIT);
        Bundle bundle = intent.getExtras();
        adherent = (Adherent) bundle.getSerializable(KEY_ADHERENT);
        adNom = adherent.getAdNom();
        adPrenom = adherent.getAdPrenom();
        adNumManuel = adherent.getAdNumManuel();
        adCode = adherent.getAdCode();
        new getMontantARetirerAsyncTask().execute();



        //adNumDossier = intent.getStringExtra(KEY_ADHERENT_NUM_DOSSIER);
/*
        EavDepotMinEditText = (EditText) findViewById(R.id.input_txt_depot_min);
        NumDossierEditText = (EditText) findViewById(R.id.input_txt_numero_bordereau_operation);
*//*
        rb_decision_accordee = (RadioButton) findViewById(R.id.rb_nature_operation_depot);
        rb_decision_accordee.performClick();
        rb_decision_refusee = (RadioButton) findViewById(R.id.rb_nature_operation_retrait);
        spinnerListEAV = (Spinner) findViewById(R.id.spn_mode_paiement);
        */
        tvAdherentNom = (TextView) findViewById(R.id.tv_nom_adherent);
        tvAdherentNom.setText(adNom+"\n"+adPrenom);
        tvAdherentNumManuel = (TextView) findViewById(R.id.tv_num_manuel_adherent);
        tvAdherentNumManuel.setText(adNumManuel);
        tvAdherentCode = (TextView) findViewById(R.id.tv_code_adherent);
        tvAdherentCode.setText(adCode);
        tvCompteSolde = (TextView) findViewById(R.id.tv_solde_compte);
        tvCompteSolde.setText(compteSolde);
        tvDateCreation = (TextView) findViewById(R.id.tv_date_creation_compte_adherent);
        tvDateCreation.setText(dateCreation);
        tvTaux = (TextView) findViewById(R.id.tv_taux_compte_adherent);
        tvTaux.setText(taux+" %");

        /*Start manage date échéance*/

        tvDateEcheance = (TextView) findViewById(R.id.tv_date_echeance_compte_adherent);
        tvDateEcheance.setText(dateFin+"\n ("+nbUnites+" "+period+")");
        /**/
        /*Start manage durée du compte*/

        tvDureeCompte = (TextView) findViewById(R.id.tv_duree_compte);
//        tvDureeCompte.setText(nbUnites+" "+period);
        tvDureeCompte.setText(calculateDifferenceBetweenTwoDates(dateCreation, dateFin));
        ;
        /*End manage durée du compte*/
        /*Start manage durée du compte*/

        tvMontantARetirer = (TextView) findViewById(R.id.tv_montant_total_a_retirer);
//        tvMontantARetirer.setText(nbUnites +" "+period);
        /*End manage durée du compte*/

        tvLibelleProduit = (TextView) findViewById(R.id.tv_libelle_produit_adherent);
        tvLibelleProduit.setText(libelleProduit);

        spinnerListEAV = (Spinner) findViewById(R.id.spn_mode_paiement);

        rb_transfert_eav = (RadioButton) findViewById(R.id.rb_CtModRenouv_transfert_vers_eav);
        rb_renouveller = (RadioButton) findViewById(R.id.rb_CtModRenouv_renouveller);
        rb_retirer = (RadioButton) findViewById(R.id.rb_CtModRenouv_retirer);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        onRadioButtonClicked(rb_transfert_eav);

//        findViewsById();
//
//        setDateTimeField();

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
                    Toast.makeText(OperationEAT.this,
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
                    Toast.makeText(OperationEAT.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });


    }

@RequiresApi(api = Build.VERSION_CODES.O)
private String calculateDifferenceBetweenTwoDates(String startDate1, String endDate1){
/*
    DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd/MM/yyyy").withLocale(Locale.US);
    LocalDate startDate = LocalDate.parse("01/09/1995", formatter);
    LocalDate endDate = LocalDate.parse("30/11/2018", formatter);*/

    String dateOperation = new SimpleDateFormat("yyyy-MM-dd",
            Locale.getDefault()).format(System.currentTimeMillis());
    DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.US);
    LocalDate startDate = LocalDate.parse(startDate1, formatter);
    LocalDate endDate = LocalDate.parse(dateOperation, formatter);

    Period period = Period.between(startDate, endDate);
    System.out.println(String.format("No Of Years : %d Years, \nNo Of Months : %d Months, \nNo Of Days : %d Days, ",
            period.getYears(), period.getMonths(), period.getDays())
    );
    if (period.getYears()>0){
        return period.getYears()+" an "+ period.getMonths()+ " mois "+ period.getDays()+ " jours";
    }else{
        return period.getMonths() + " mois "+ period.getDays()+ " jours";
    }

    /*Other method*/
        /*
        //Comparing dates
        long difference = Math.abs(date1.getTime() - date2.getTime());
        long differenceDates = difference / (24 * 60 * 60 * 1000);

        //Convert long to String
        String dayDifference = Long.toString(differenceDates);

        Log.e("HERE","HERE: " + dayDifference);
        */

}

    /**
     * Get the Montant solde à retirer from the compte_eat table from the server
     */
    private class getMontantARetirerAsyncTask extends AsyncTask<String, String, String> {
        int successGetFrais;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(OperationEAT.this);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_COMPTE_ID, String.valueOf(compteId));
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fonctions_financieres/EAT/calcMontantARetirer.php", "GET", httpParams);
            try {
                successGetFrais = jsonObject.getInt(KEY_SUCCESS);
                Log.e("calcMontantARetirer", jsonObject+"");
                JSONArray movies;
                if (successGetFrais == 1) {
                    montantARetirer = jsonObject.getString(KEY_MontantARetirer);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (successGetFrais ==1){
                   //     new OperationEAV.AddEavAdherentAsyncTask().execute();
                        tvMontantARetirer.setText(montantARetirer);
                    }
                }
            });
        }

    }
    private void findViewsById() {

        Ad_DateDelivranceEditText = (EditText) findViewById(R.id.input_txt_validite_debut_adherent);
        Ad_DateDelivranceEditText.requestFocus();
        Ad_DateDelivranceEditText.setInputType(InputType.TYPE_NULL);

        Ad_DateExpirationEditText = (EditText) findViewById(R.id.input_txt_validite_fin_adherent);
        Ad_DateExpirationEditText.requestFocus();
        Ad_DateExpirationEditText.setInputType(InputType.TYPE_NULL);




    }

    private void setDateTimeField() {
        Ad_DateDelivranceEditText.setOnClickListener(this);
        Ad_DateExpirationEditText.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();

        Ad_DateDelivrance_PickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                Ad_DateDelivranceEditText.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        Ad_DateExpiration_PickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                Ad_DateExpirationEditText.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));



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
     * To manage Radio button
     * @param view
     */
    /* public void onRadioButtonClicked(View view) {
        boolean checked1 = ((RadioButton) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.rb_nature_operation_depot:
                if (rb_decision_accordee.isChecked()) {
                    natureOperation = "D";
                    //str = checked1?"Nature frais fixe":"";

                }
                break;
            case R.id.rb_nature_operation_retrait:
                if (rb_decision_refusee.isChecked()) {
                    natureOperation = "R";
                    // str = checked1?"Nature frais taux":"";

                }

                break;


        }
        // Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    } */


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
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(OperationEAT.this,
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
            pDialogFetchProduitEavList = new ProgressDialog(OperationEAT.this);
            pDialogFetchProduitEavList.setMessage("Fetching produits's list..");
            pDialogFetchProduitEavList.setCancelable(false);
            pDialogFetchProduitEavList.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair(KEY_COMPTE_ID, compteId));
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
        if (!STRING_EMPTY.equals(eatCtModRenouv)

                 ) {
//String rr = compteSolde.replace(" FCFA","").replaceAll(",","\\.");
//String rr = compteSolde.replace("FCFA","").trim().replaceAll(",","\\.").replaceAll(" ","");
//
//            NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
//            nf.setGroupingUsed(false);
            //nf.format(rr);
//if (natureOperation.equals("R")&&
//                    (Double.parseDouble(EavDepotMinEditText.getText().toString())<Double.parseDouble(rr))){
//


//    eavDepotMin = EavDepotMinEditText.getText().toString();
//    adNumDossier = NumDossierEditText.getText().toString();

    new AddEavAdherentAsyncTask().execute();





        } else {
            Toast.makeText(OperationEAT.this,
                    "Choisir une action à faire!",
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
            pDialog = new ProgressDialog(OperationEAT.this);
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

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "operation_eav_adherent.php", "POST", httpParams);
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
                        Toast.makeText(OperationEAT.this,
                                "Opération réussie !", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(OperationEAT.this,
                                "Echec!\n Vérifiez votre solde ",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}