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
import com.example.binumtontine.modele.ProduitEAT;
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

public class CreateEAT extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, SERVER_ADDRESS {
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
    private static final String KEY_EAT_MT_MIN_MISE = "EtMtMinMise";
    private static final String KEY_EAT_MT_MAX_MISE = "EtMtMaxMise";

    private static final String KEY_CT_PRODUIT = "CtProduit";
    private static final String KEY_CT_MEMBRE = "CtMembre";
    private static final String KEY_CT_GUICHET = "CtGuichet";
    private static final String KEY_CT_NUM_DOSSIER = "CtNumDossier";
    private static final String KEY_CT_MT_MISE = "CtMtMise";
    private static final String KEY_CT_TAUX = "CtTaux";
    private static final String KEY_CT_NBRE_UNITE = "CtNbUnites";
    private static final String KEY_CT_USER_CREE = "CtUserCree";
    private static final String KEY_CT_MOD_RENOUV = "CtModRenouv";
    private static final String KEY_CtDateDebut = "CtDateDebut";
    private static final String KEY_CtDateFin = "CtDateFin";

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
    private TextInputLayout til_EatMontantMise;

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
    private ArrayList<ProduitEAT> eatList;
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

    private EditText Ad_DateDelivranceEditText;
    private EditText Ad_DateExpirationEditText;

    private DatePickerDialog Ad_DateDelivrance_PickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date
    private DatePickerDialog Ad_DateExpiration_PickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date

    private SimpleDateFormat dateFormatter; //propriété permettant de gérer le format de la date
    private Double EatMinMontantMise; //to control min mise when creating eat account
    private Double EatMaxMontantMise; //to control max mise when creating eat account
    private Double parseEatMontantMise;
    private TextInputLayout til_EatNumDossier;
    private TextInputLayout til_EatDateDebut;
    private TextInputLayout til_EatDateFin;

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
        EatMontantMiseEditText.addTextChangedListener(MyData.onTextChangedListener(EatMontantMiseEditText));
        til_EatMontantMise = (TextInputLayout) findViewById(R.id.input_layout_montant_mise);
//        EatMontantMiseEditText.addTextChangedListener(new MyTextWatcher(EatMontantMiseEditText));

        NumDossierEditText = (EditText) findViewById(R.id.input_txt_num_dossier_adherent);

        til_EatNumDossier = (TextInputLayout) findViewById(R.id.input_layout_num_dossier_adherent);
        NumDossierEditText.addTextChangedListener(new MyTextWatcher(NumDossierEditText));
        rb_transfert_eav = (RadioButton) findViewById(R.id.rb_CtModRenouv_transfert_vers_eav);
        rb_renouveller = (RadioButton) findViewById(R.id.rb_CtModRenouv_renouveller);
        rb_retirer = (RadioButton) findViewById(R.id.rb_CtModRenouv_retirer);


        spinnerListEAT = (Spinner) findViewById(R.id.spn_list_eat);
        spinnerNbreUnite = (Spinner) findViewById(R.id.spn_list_duree);
//        spinnerNbreUnite.setOnItemSelectedListener(this);
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

        eatList = new ArrayList<ProduitEAT>();
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
        Ad_DateDelivranceEditText = (EditText) findViewById(R.id.input_txt_validite_debut_eat_adherent);
        til_EatDateDebut = (TextInputLayout) findViewById(R.id.input_layout_validite_debut_eat_adherent);

        Ad_DateDelivranceEditText.requestFocus();
        Ad_DateDelivranceEditText.setInputType(InputType.TYPE_NULL);

        //Date de fin
        Ad_DateExpirationEditText = (EditText) findViewById(R.id.input_txt_validite_fin_eat_adherent);
        Ad_DateExpirationEditText.setEnabled(false);
        til_EatDateFin = (TextInputLayout) findViewById(R.id.input_layout_validite_fin_eat_adherent);
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
            eatListID.add(eatList.get(i).getEtNumero()); //recupère les Id
            lables.add(eatList.get(i).getEtLibelle());//recupère les noms

            eatListTaux.add(eatList.get(i).getEtValTxInter()); //recupère les Taux
            Log.d("*******",eatList.get(i).getEtValTxInter());
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

                            ProduitEAT pdEAT = new ProduitEAT(catObj.getInt(KEY_EAT_ID),
                                    catObj.getString(KEY_EAT_LIBELLE),
                                    catObj.getString(KEY_EAT_TAUX),
                                    catObj.getString(KEY_EAT_MT_MIN_MISE),
                                    catObj.getString(KEY_EAT_MT_MAX_MISE)
                                    );
                            eatList.add(pdEAT);
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
        EatTauxEditText.setText(eatList.get(position).getEtValTxInter()+" %");
       // EatMinMontantMise = eatList.get(position).getEtMtMinMise();
        EatMinMontantMise =  Double.valueOf(eatList.get(position).getEtMtMinMise());
        EatMaxMontantMise =  Double.valueOf(eatList.get(position).getEtMtMaxMise());
        Ad_DateDelivranceEditText.setText("");
        Ad_DateExpirationEditText.setText("");
//        EatMaxMontantMise = eatList.get(position).getEtMtMaxMise();


//        Toast.makeText(
//                getApplicationContext(),
//                eatTaux + " Selected" ,
//                Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    private boolean validateLogin() {

        if (EatMontantMiseEditText.getText().toString().trim().isEmpty()){


                til_EatMontantMise.setError(getString(R.string.err_msg_montant_mise));
                requestFocus(EatMontantMiseEditText);
                // testError=false;
                return false;





        } else {
            EatMontantMiseEditText.setText(EatMontantMiseEditText.getText().toString().replaceAll(",", "").trim());
            parseEatMontantMise = Double.valueOf(EatMontantMiseEditText.getText().toString().replaceAll(",", "").trim());

            if (parseEatMontantMise<EatMinMontantMise ||
                    parseEatMontantMise>EatMaxMontantMise) {
                til_EatMontantMise.setError(getString(R.string.err_msg_montant_mise_invalide));
                requestFocus(EatMontantMiseEditText);

            Toast.makeText(CreateEAT.this,
                    "Le montant de la mise doit être compris entre "+EatMinMontantMise+" et "+EatMaxMontantMise,
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
     * Validating form
     */

    private void submitForm() {
        if (!validateLogin()) {
            return;
        }

//        if (!validatePassword()) {
//            return;
//        }

    }

    /**
     * Checks whether all files are filled. If so then calls AddEatAdherentAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addEatAdherent() {
        //submitForm();
        if (validateNumDossier() &&
                validateLogin()&&
                validateDateStartAndEnd()&&
            !STRING_EMPTY.equals(EatTauxEditText.getText().toString().trim()) &&

                eatID !=0 ) {


            eatMontantMise = EatMontantMiseEditText.getText().toString().replaceAll(",", "").trim();
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
//            Toast.makeText(CreateEAT.this,
//                    "Un ou plusieurs champs sont vides!",
//                    Toast.LENGTH_LONG).show();

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
            httpParams.put(KEY_CtDateDebut, Ad_DateDelivranceEditText.getText().toString());
            httpParams.put(KEY_CtDateFin, Ad_DateExpirationEditText.getText().toString());

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