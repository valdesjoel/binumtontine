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
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import com.example.binumtontine.activity.Category;
import com.example.binumtontine.activity.ServiceHandler;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.example.binumtontine.modele.ProduitEAP;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
public class CreateEAP extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    /*Begin*/
         //to fetchProduitList by caisse and guichet ID
    private static final String KEY_EP_CAISSE_ID = "EpCaisseId";
    private static final String KEY_EP_GUICHET_ID = "EpGuichetId";
    /* end*/
    private static final String KEY_EAP_ID = "EpNumero";
    private static final String KEY_EAP_LIBELLE = "EpLibelle";
    private static final String KEY_EAP_TAUX = "EpValTxInter";
    private static final String KEY_EpDureeMin = "EpDureeMin";
    private static final String KEY_EpMtMinMisePer = "EpMtMinMisePer";
    private static final String KEY_EpIsTxIntNeg = "EpIsTxIntNeg";

    private static final String KEY_CP_PRODUIT = "CpProduit";
    private static final String KEY_CP_MEMBRE = "CpMembre";
    private static final String KEY_CP_GUICHET = "CpGuichet";
    private static final String KEY_CP_NUM_DOSSIER = "CpNumDossier";
    private static final String KEY_CP_DEPOT_MIN = "CpDepotMin";
    private static final String KEY_CP_TAUX = "CpTaux";
    private static final String KEY_CP_NBRE_UNITE = "CpNbUnites";
    private static final String KEY_CP_USER_CREE = "CpUserCree";
    private static final String KEY_CP_MOD_RENOUV = "CpModRenouv";

    private static final String KEY_CpDateDebut = "CpDateDebut";
    private static final String KEY_CpDateFin = "CpDateFin";

    //private static final String KEY_ADHERENT_NUM_DOSSIER = "CpNumDossier";

    /*Param for get extra*/
    private static final String KEY_ADHERENT_ID = "IpMembre";
    private static final String KEY_ADHERENT_NOM = "AdNom";
    private static final String KEY_ADHERENT_PRENOM = "AdPrenom";
    private static final String KEY_ADHERENT_NUM_MANUEL = "AdNumManuel";
    private static final String KEY_ADHERENT_CODE = "AdCode";



    private static String STRING_EMPTY = "";

    private EditText EapDepotMinEditText;
    private EditText EapTauxEditText;
    private EditText NumDossierEditText;


    private String adherentId;
    private String eapDepotMin;
    private String eapTaux="";
    private String adNom;
    private String adPrenom;
    private String adNumManuel;
    private String adCode;
    private String adNumDossier;
    private String eapNbreUnite;
    private String eapCtModRenouv;

    /* manage spinner*/
    // array list for spinner adapter
//    private ArrayList<Category> eapList;
    private ArrayList<ProduitEAP> eapList;
    List<Integer> eapListID = new ArrayList<Integer>();
    List<String> eapListTaux = new ArrayList<String>();
    private int eapID;
    private Spinner spinnerListEAP;
    private Spinner spinnerNbreUnite;
    private TextView tvAdherentNom;
    private TextView tvAdherentNumManuel;
    private TextView tvAdherentCode;
    private RadioButton rb_transfert_vers_eav;
    private RadioButton rb_renouveller;
    private RadioButton rb_retirer;
    private TextView tvAdherentNumDossier;
    /*end manage*/

    private Button addButton;
    private Button annulerButton;
    private int success;
    private ProgressDialog pDialog;
    private ProgressDialog pDialogFetchProduitEapList;


    private Double EatMinMontantMise; //to control min mise when creating eat account
    private Double EatMaxMontantMise; //to control max mise when creating eat account
    private Double parseEatMontantMise;
    private SimpleDateFormat dateFormatter; //propriété permettant de gérer le format de la date
    private EditText Ad_DateDelivranceEditText;
    private EditText Ad_DateExpirationEditText;

    private DatePickerDialog Ad_DateDelivrance_PickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date
    private DatePickerDialog Ad_DateExpiration_PickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date
    private TextInputLayout til_EatNumDossier;
    private TextInputLayout til_EatDateDebut;
    private TextInputLayout til_EatDateFin;
    private TextInputLayout til_EatMontantMise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_eap_adherent);


        Intent intent = getIntent();
        adherentId = intent.getStringExtra(KEY_ADHERENT_ID);
        adNom = intent.getStringExtra(KEY_ADHERENT_NOM);
        adPrenom = intent.getStringExtra(KEY_ADHERENT_PRENOM);
        adNumManuel = intent.getStringExtra(KEY_ADHERENT_NUM_MANUEL);
        adCode = intent.getStringExtra(KEY_ADHERENT_CODE);
        //adNumDossier = intent.getStringExtra(KEY_ADHERENT_NUM_DOSSIER);

        EapDepotMinEditText = (EditText) findViewById(R.id.input_txt_montant_mise);
        EapDepotMinEditText.addTextChangedListener(MyData.onTextChangedListener(EapDepotMinEditText));
        til_EatMontantMise = (TextInputLayout) findViewById(R.id.input_layout_montant_mise);

        EapTauxEditText = (EditText) findViewById(R.id.input_txt_taux);
        EapTauxEditText.setText(eapTaux+" %");
        NumDossierEditText = (EditText) findViewById(R.id.input_txt_num_dossier_adherent);
        til_EatNumDossier = (TextInputLayout) findViewById(R.id.input_layout_num_dossier_adherent);
        NumDossierEditText.addTextChangedListener(new CreateEAP.MyTextWatcher(NumDossierEditText));
        rb_transfert_vers_eav = (RadioButton) findViewById(R.id.rb_CtModRenouv_transfert_vers_eav);
        rb_renouveller = (RadioButton) findViewById(R.id.rb_CtModRenouv_renouveller);
        rb_retirer = (RadioButton) findViewById(R.id.rb_CtModRenouv_retirer);


        spinnerListEAP = (Spinner) findViewById(R.id.spn_list_eap);
        spinnerNbreUnite = (Spinner) findViewById(R.id.spn_list_duree);
        tvAdherentNom = (TextView) findViewById(R.id.tv_nom_adherent);
        tvAdherentNom.setText(adNom+"\n"+adPrenom);
        tvAdherentNumManuel = (TextView) findViewById(R.id.tv_num_manuel_adherent);
        tvAdherentNumManuel.setText(adNumManuel);
        tvAdherentCode = (TextView) findViewById(R.id.tv_code_adherent);
        tvAdherentCode.setText(adCode);
       /* tvAdherentNumDossier = (TextView) findViewById(R.id.tv_num_dossier_adherent);
        tvAdherentNumDossier.setText(adNumDossier);*/

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();

        setDateTimeField();


        eapList = new ArrayList<ProduitEAP>();
        // spinner item select listener
        spinnerListEAP.setOnItemSelectedListener(CreateEAP.this);
        new GetProduitEATList().execute();
        onRadioButtonClicked(rb_transfert_vers_eav);

        addButton = (Button) findViewById(R.id.btn_save_compte_eap);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(CreateEAP.this,
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
                    Toast.makeText(CreateEAP.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });


    }


    /**
     * Method to make cursor focus on a mandtory field
     * @param view
     */
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_txt_montant_mise:
                    validateLogin();

                    break;
                case R.id.input_txt_num_dossier_adherent:
                    validateNumDossier();
                    break;
//                case R.id.txtPassword:
//                    validatePassword();
//                    break;

            }
        }
    }

    private void findViewsById() {

        //Date de debut
        Ad_DateDelivranceEditText = (EditText) findViewById(R.id.input_txt_validite_debut_eap_adherent);
        til_EatDateDebut = (TextInputLayout) findViewById(R.id.input_layout_validite_debut_eap_adherent);

        Ad_DateDelivranceEditText.requestFocus();
        Ad_DateDelivranceEditText.setInputType(InputType.TYPE_NULL);

        //Date de fin
        Ad_DateExpirationEditText = (EditText) findViewById(R.id.input_txt_validite_fin_eap_adherent);
        Ad_DateExpirationEditText.setEnabled(false);
        til_EatDateFin = (TextInputLayout) findViewById(R.id.input_layout_validite_fin_eap_adherent);
        Ad_DateExpirationEditText.requestFocus();
        Ad_DateExpirationEditText.setInputType(InputType.TYPE_NULL);

        Ad_DateDelivranceEditText.setVisibility(View.VISIBLE);
        Ad_DateExpirationEditText.setVisibility(View.VISIBLE);

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
                newDate.add(Calendar.MONTH, Integer.parseInt(spinnerNbreUnite.getSelectedItem().toString()));
                Ad_DateExpirationEditText.setText(dateFormatter.format(newDate.getTime()));
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
                if (rb_transfert_vers_eav.isChecked()) {
                    eapCtModRenouv = "T";
                    //str = checked1?"Nature frais fixe":"";

                }
                break;
            case R.id.rb_CtModRenouv_renouveller:
                if (rb_renouveller.isChecked()) {
                    eapCtModRenouv = "R";
                   // str = checked1?"Nature frais taux":"";

                }
                break;
            case R.id.rb_CtModRenouv_retirer:
                if (rb_retirer.isChecked()) {
                    eapCtModRenouv = "E";
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

      /*  for (int i = 0; i < eapList.size(); i++) {
            lables.add(eapList.get(i).getName());//recupère les noms
            eapListID.add(eapList.get(i).getId()); //recupère les Id
            eapListTaux.add(eapList.get(i).getTaux()); //recupère les Taux
        }*/
        for (int i = 0; i < eapList.size(); i++) {
            lables.add(eapList.get(i).getEpLibelle());//recupère les noms
            eapListID.add(eapList.get(i).getEpNumero()); //recupère les Id
            eapListTaux.add(eapList.get(i).getEpValTxInter()); //recupère les Taux
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(CreateEAP.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerListEAP.setAdapter(spinnerAdapter);
    }

    /**
     * Async task to get all food categories
     * */
    private class GetProduitEATList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogFetchProduitEapList = new ProgressDialog(CreateEAP.this);
            pDialogFetchProduitEapList.setMessage("Fetching produits's list..");
            pDialogFetchProduitEapList.setCancelable(false);
            pDialogFetchProduitEapList.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair(KEY_EP_CAISSE_ID, String.valueOf(MyData.CAISSE_ID)));
            httpParams.add(new BasicNameValuePair(KEY_EP_GUICHET_ID, String.valueOf(MyData.GUICHET_ID)));
            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_eap_by_guichet.php", ServiceHandler.GET, httpParams);
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
                            Category cat = new Category(catObj.getInt(KEY_EAP_ID),
                                    catObj.getString(KEY_EAP_LIBELLE), catObj.getString(KEY_EAP_TAUX));

                            ProduitEAP pdEAP = new ProduitEAP(catObj.getInt(KEY_EAP_ID),
                                    catObj.getString(KEY_EAP_LIBELLE),
                                    catObj.getString(KEY_EAP_TAUX),
                                    catObj.getString(KEY_EpDureeMin),
                                    catObj.getString(KEY_EpMtMinMisePer),
                                    catObj.getString(KEY_EpIsTxIntNeg)
                            );
//                            eapList.add(cat);
                            eapList.add(pdEAP);
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
            if (pDialogFetchProduitEapList.isShowing())
                pDialogFetchProduitEapList.dismiss();
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
        eapID = eapListID.get(position);//pour recuperer l'ID du guichet selectionnée
        //eapTaux = eapListTaux.get(position);//pour recuperer le taux du produit selectionnée
        EapTauxEditText.setText(eapList.get(position).getEpValTxInter()+" %");


        // EatMinMontantMise = eatList.get(position).getEtMtMinMise();
        EatMinMontantMise =  Double.valueOf(eapList.get(position).getEpMtMinMisePer());
//        EatMaxMontantMise =  Double.valueOf(eapList.get(position).getEtMtMaxMise());
        Ad_DateDelivranceEditText.setText("");
        Ad_DateExpirationEditText.setText("");
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    private boolean validateLogin() {

        if (EapDepotMinEditText.getText().toString().trim().isEmpty()){


            til_EatMontantMise.setError(getString(R.string.err_msg_montant_mise));
            requestFocus(EapDepotMinEditText);
            // testError=false;
            return false;





        } else {
            EapDepotMinEditText.setText(EapDepotMinEditText.getText().toString().replaceAll(",", "").trim());
            parseEatMontantMise = Double.valueOf(EapDepotMinEditText.getText().toString().replaceAll(",", "").trim());
//
//            if (parseEatMontantMise<EatMinMontantMise ||
//                    parseEatMontantMise>EatMaxMontantMise) {
            if (parseEatMontantMise<EatMinMontantMise) {
                til_EatMontantMise.setError(getString(R.string.err_msg_montant_mise_invalide)+"\nLe Montant minimum de la mise pour ce produit est de: "+EatMinMontantMise);
//                til_EatMontantMise.setError("Le Montant minimum de la mise pour ce produit est de: "+EatMinMontantMise);
                requestFocus(EapDepotMinEditText);

                Toast.makeText(CreateEAP.this,
                        "Le montant de la mise doit être au minimum de "+EatMinMontantMise,
//                        "Le montant de la mise doit être compris entre "+EatMinMontantMise+" et "+EatMaxMontantMise,
                        Toast.LENGTH_LONG).show();
                // testError=false;
                return false;
            }
//            EatMontantMiseEditText.setText(EatMontantMiseEditText.getText().toString().replaceAll(",", "").trim());
//            parseEatMontantMise = Double.valueOf(EatMontantMiseEditText.getText().toString().replaceAll(",", "").trim());

            til_EatMontantMise.setErrorEnabled(false);
            // testError=true;
        }

        return true;
    }
    private boolean validateDateStartAndEnd() {

        if (Ad_DateDelivranceEditText.getText().toString().trim().isEmpty()){
            til_EatDateDebut.setError(getString(R.string.err_msg_date_debut));
            requestFocus(Ad_DateDelivranceEditText);
            // testError=false;
            return false;

        }else if (Ad_DateExpirationEditText.getText().toString().trim().isEmpty()){

            til_EatDateFin.setError(getString(R.string.err_msg_date_fin));
            requestFocus(Ad_DateExpirationEditText);
            // testError=false;
            return false;

        }else {

            try {
                Date start = new SimpleDateFormat("dd-MM-yyyy", Locale.US)
                        .parse(Ad_DateDelivranceEditText.getText().toString());
                Date end = new SimpleDateFormat("dd-MM-yyyy", Locale.US)
                        .parse(Ad_DateExpirationEditText.getText().toString());

//                Date start = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH)
//                        .parse(Ad_DateDelivranceEditText.getText().toString());
//                Date end = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH)
//                        .parse(Ad_DateExpirationEditText.getText().toString());
                if (start.compareTo(end) > 0) {
                    System.out.println("start is after end");
                    til_EatDateDebut.setError(getString(R.string.err_msg_date_debut_anterieure));
                    requestFocus(Ad_DateDelivranceEditText);
                    // testError=false;
                    return false;
                } else if (start.compareTo(end) < 0) {
                    System.out.println("start is before end");
                    til_EatDateDebut.setErrorTextAppearance(R.style.loginButton);
                    til_EatDateDebut.setError("Date valide !");
                    til_EatDateDebut.setErrorEnabled(false);
                    til_EatDateFin.setErrorEnabled(false);
                } else if (start.compareTo(end) == 0) {
                    System.out.println("start is equal to end");
                    til_EatDateDebut.setError(getString(R.string.err_msg_date_debut_egale));
                    requestFocus(Ad_DateDelivranceEditText);
                    return false;
                } else {
                    System.out.println("Something weird happened...");
                    til_EatDateDebut.setError(getString(R.string.err_msg_date_debut));
                    til_EatDateFin.setError(getString(R.string.err_msg_date_fin));
                    requestFocus(Ad_DateDelivranceEditText);
                    requestFocus(Ad_DateExpirationEditText);
                    return false;
                }
            }catch (ParseException e){
                e.printStackTrace();
            }

//            til_EatMontantMise.setErrorEnabled(false);
            // testError=true;
        }

        return true;
    }

    /**
     * Method to validate Numero Dossier
     * @return
     */
    private boolean validateNumDossier() {
        if (NumDossierEditText.getText().toString().trim().isEmpty()) {
            til_EatNumDossier.setError(getString(R.string.err_msg_num_dossier));
            requestFocus(NumDossierEditText);
            // testError=false;
            return false;
        } else {

            til_EatNumDossier.setErrorEnabled(false);
            // testError=true;
        }

        return true;
    }


    /**
     * Checks whether all files are filled. If so then calls AddEatAdherentAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addEatAdherent() {
        if (validateNumDossier() &&
                validateLogin()&&
                validateDateStartAndEnd()&&
                !STRING_EMPTY.equals(EapTauxEditText.getText().toString().trim()) &&

                eapID !=0 )
//        if (!STRING_EMPTY.equals(EapDepotMinEditText.getText().toString()) &&
//            !STRING_EMPTY.equals(NumDossierEditText.getText().toString()) &&
//            !STRING_EMPTY.equals(EapTauxEditText.getText().toString()) &&
//                eapID !=0 )
                            {


//                eapDepotMin = EapDepotMinEditText.getText().toString();
                                eapDepotMin = EapDepotMinEditText.getText().toString().replaceAll(",", "").trim();
                adNumDossier = NumDossierEditText.getText().toString();
            eapTaux = (EapTauxEditText.getText().toString()).replace(" %","");
                eapNbreUnite = spinnerNbreUnite.getSelectedItem().toString();
         /*   if (rb_transfert_vers_eav.isChecked()) {
                eapCtModRenouv = "T";
                //str = checked1?"Nature frais fixe":"";

            }else  if (rb_renouveller.isChecked()) {
                eapCtModRenouv = "R";
                // str = checked1?"Nature frais taux":"";

            } else {
                eapCtModRenouv = "R";
                // str = checked1?"Nature frais taux":"";

            }*/

                new AddEatAdherentAsyncTask().execute();


        } else {
            Toast.makeText(CreateEAP.this,
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
            pDialog = new ProgressDialog(CreateEAP.this);
            pDialog.setMessage("Adding compte à périodicité. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
           // httpParams.put(KEY_EAP_ID, uxGuichetId);

            httpParams.put(KEY_CP_MEMBRE, adherentId);
            httpParams.put(KEY_CP_PRODUIT, String.valueOf(eapID));
            httpParams.put(KEY_CP_GUICHET, String.valueOf(MyData.GUICHET_ID));
            httpParams.put(KEY_CP_NUM_DOSSIER, adNumDossier);
            httpParams.put(KEY_CP_DEPOT_MIN, eapDepotMin);
            httpParams.put(KEY_CP_NBRE_UNITE, eapNbreUnite);
            httpParams.put(KEY_CP_USER_CREE, String.valueOf(MyData.USER_ID));
            httpParams.put(KEY_CP_TAUX, eapTaux);
            httpParams.put(KEY_CP_MOD_RENOUV, eapCtModRenouv);
            httpParams.put(KEY_CpDateDebut, Ad_DateDelivranceEditText.getText().toString());
            httpParams.put(KEY_CpDateFin, Ad_DateExpirationEditText.getText().toString());

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_eap_adherent.php", "POST", httpParams);
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
                        Toast.makeText(CreateEAP.this,
                                "Compte créé avec succès", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CreateEAP.this,
                                "Some error occurred while adding Compte",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}