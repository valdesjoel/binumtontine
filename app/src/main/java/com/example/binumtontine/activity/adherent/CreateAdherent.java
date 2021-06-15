
package com.example.binumtontine.activity.adherent;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.JRSpinner;
import com.example.binumtontine.R;
import com.example.binumtontine.activity.Category;
import com.example.binumtontine.activity.ServiceHandler;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class CreateAdherent extends AppCompatActivity implements View.OnClickListener, SERVER_ADDRESS {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_ADHERENT_ID = "IpMembre";
    private static final String KEY_AD_GX_NUMERO = "AdGuichet";
    private static final String KEY_AD_NUM_MANUEL = "AdNumManuel";
    private static final String KEY_AD_NOM = "AdNom";
    private static final String KEY_AD_PRENOM = "AdPrenom";
    private static final String KEY_AD_DATE_NAISS = "AdDateNaiss";
    private static final String KEY_AD_LIEU_NAISS = "AdLieuNaiss";
    private static final String KEY_AD_SEXE = "AdSexe";
    private static final String KEY_AD_NATIONALITE = "AdNationalite";
    private static final String KEY_AD_SIT_FAM = "AdSitFam";
    private static final String KEY_AD_NBRE_ENFANT = "AdNbreEnfACh";
    private static final String KEY_AD_TEL1 = "AdTel1";
    private static final String KEY_AD_TEL2 = "AdTel2";
    private static final String KEY_AD_TEL3 = "AdTel3";
    private static final String KEY_AD_EMAIL = "AdEMail";
    private static final String KEY_AD_PROFESSION = "AdProfess";
    private static final String KEY_AD_DOMICILE = "AdDomicile";
    private static final String KEY_AD_LIEU_TRAVAIL = "AdLieuTrav";
    private static final String KEY_AD_ACTIVITE_PRINC = "AdActivitePr";
    private static final String KEY_AD_TYPE_CARTE_ID = "AdTypCarteID";
    private static final String KEY_AD_NUM_CARTE_ID = "AdNumCarteID";
    private static final String KEY_AD_VALIDE_DU = "AdValideDu";
    private static final String KEY_AD_VALIDE_AU = "AdValideAu";
    private static final String KEY_AD_TYPE_HABITE = "AdTypHabite";
    private static final String KEY_AdTypMemb2 = "AdTypMemb2";

    private static final String KEY_ADHERENT = "ADHERENT";
    private static final String KEY_TYPE_MEMBRE = "FcCategAdh";
    private static final String KEY_CV_USER_CREE = "CvUserCree";


    private static String STRING_EMPTY = "";

    private TextView cxTitle;
    private String cxName="";
    private CountryCodePicker ccp_phone1;
    private CountryCodePicker ccp_phone2;
    private CountryCodePicker ccp_phone3;
    private EditText editTextCarrierPhone1;
    private EditText editTextCarrierPhone2;
    private EditText editTextCarrierPhone3;

    private JRSpinner mySpinnerCaisse; //pour gérer le spinner contenant les caisses


    private EditText Ad_NumManuelEditText;
    private EditText Ad_NomEditText;
    private EditText Ad_PrenomEditText;
    private EditText Ad_DateNaissEditText;
    private EditText Ad_LieuNaissEditText;
    private Spinner Ad_SexeSpinner;
    private CountryCodePicker Ad_NationaliteSpinner; //pour gérer le spinner contenant les pays
    private Spinner Ad_SituationMatSpinner;
    private EditText Ad_NombreEnfantEditText;
    private EditText Ad_EmailEditText;
    private JRSpinner Ad_ProfessionSpinner; //pour gérer le spinner contenant les professions
    private EditText Ad_DomicileEditText;
    private EditText Ad_LieuTravailEditText;
    private EditText Ad_ActivitePrincipaleEditText;
    private JRSpinner Ad_TypePieceSpinner;
    private EditText Ad_NumPieceEditText;
    private EditText Ad_DateDelivranceEditText;
    private EditText Ad_DateExpirationEditText;
    private Spinner Ad_TypeLocationSpinner;


    private String AdNumManuel;
    private String AdNom;
    private String AdPrenom;
    private String AdDateNaiss;
    private String AdLieuNaiss;
    private String AdSexe;
    private String AdNationalite;
    private String AdSitFam;
    private String AdNbreEnfACh;
    private String AdTel1;
    private String AdTel2;
    private String AdTel3;
    private String AdEMail;
    private String AdProfess;
    private String AdDomicile;
    private String AdLieuTrav;
    private String AdActivitePr;
    private String AdTypCarteID;
    private String AdNumCarteID;
    private String AdValideDu;
    private String AdValideAu;
    private String AdTypHabite;
    private String AdDetailsTypeNature;

    private Adherent adherent;
    private Spinner spinnerGuichet;
    /* manage spinner*/

    private TextView tvCaisse;


    //private Button addButton;
    private CircularProgressButton addButton;
    private CircularProgressButton annulerButton;
    private CircularProgressButton delButton;
   // private Button delButton;
    private int success;
    private ProgressDialog pDialog;





    private DatePickerDialog Ad_DateNaiss_PickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date
    private DatePickerDialog Ad_DateDelivrance_PickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date
    private DatePickerDialog Ad_DateExpiration_PickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date

    private SimpleDateFormat dateFormatter; //propriété permettant de gérer le format de la date
    private EditText Ad_DetailsTypeNatureEditText;
    List<Integer> guichetListID = new ArrayList<Integer>();
    private int typeMembreID = 0; //pour recupérer l'ID du type de membre
    private ArrayList<Category> typeMembreList;
    private Category mainEAV;
    private TextInputLayout inputLayoutName, inputLayoutEmail,inputLayoutNumManuel, inputLayoutPassword;
    private  boolean testError=false; //to check if errors don't exist
    private String eavId;
    public static boolean to_update_adherent=false;
    private ProgressDialog pDialog_update;
    private TextView header_user_guichet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_adherent);


        /*end manage*/
        header_user_guichet = (TextView) findViewById(R.id.header_user_guichet);
        /* Begin manage country*/

        cxTitle = (TextView) findViewById(R.id.tv_caisse_name);
        //cxName= MyData.CAISSE_NAME.toUpperCase();
//        cxName= mainEAV.getName().toUpperCase();
//        cxTitle.setTypeface(null, Typeface.BOLD);
//        cxTitle.setText(cxTitle.getText()+" "+ cxName);

        ccp_phone1 = (CountryCodePicker) findViewById(R.id.ccp_phone1);
        editTextCarrierPhone1 = (EditText) findViewById(R.id.editText_carrierPhone1);
        ccp_phone1.registerCarrierNumberEditText(editTextCarrierPhone1);


        ccp_phone2 = (CountryCodePicker) findViewById(R.id.ccp_phone2);
        editTextCarrierPhone2 = (EditText) findViewById(R.id.editText_carrierPhone2);
        ccp_phone2.registerCarrierNumberEditText(editTextCarrierPhone2);

        ccp_phone3 = (CountryCodePicker) findViewById(R.id.ccp_phone3);
        editTextCarrierPhone3 = (EditText) findViewById(R.id.editText_carrierPhone3);
        ccp_phone3.registerCarrierNumberEditText(editTextCarrierPhone3);
        /* End manage country*/

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();

        setDateTimeField();

        Ad_ProfessionSpinner = (JRSpinner)findViewById(R.id.spn_profession_adherent);
        Ad_ProfessionSpinner.setItems(getResources().getStringArray(R.array.array_list_profession)); //this is important, you must set it to set the item list
        Ad_ProfessionSpinner.setTitle("Sélectionner une profession"); //change title of spinner-dialog programmatically
        Ad_ProfessionSpinner.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        Ad_ProfessionSpinner.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });

        Ad_TypePieceSpinner = (JRSpinner)findViewById(R.id.spn_type_piece_adherent);
        Ad_TypePieceSpinner.setItems(getResources().getStringArray(R.array.array_type_piece)); //this is important, you must set it to set the item list
        Ad_TypePieceSpinner.setTitle("Sélectionner une pièce d'identification"); //change title of spinner-dialog programmatically
        Ad_TypePieceSpinner.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        Ad_TypePieceSpinner.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position
                AdTypCarteID = Adherent.encodeAdTypCarteID(Ad_TypePieceSpinner.getText().toString());

                /*if (Ad_TypePieceSpinner.getText().toString().equals("Carte nationale d'identité")){
                    AdTypCarteID = "CN";
                }else if (Ad_TypePieceSpinner.getText().toString().equals("Carte de séjour")){
                    AdTypCarteID = "CS";
                }else if (Ad_TypePieceSpinner.getText().toString().equals("Carte consulaire")){
                    AdTypCarteID = "CC";
                }else if (Ad_TypePieceSpinner.getText().toString().equals("Carte militaire")){
                    AdTypCarteID = "CM";
                }else if (Ad_TypePieceSpinner.getText().toString().equals("Carte CNPS")){
                    AdTypCarteID = "PS";
                }else if (Ad_TypePieceSpinner.getText().toString().equals("Permis de conduire")){
                    AdTypCarteID = "PC";
                }else if (Ad_TypePieceSpinner.getText().toString().equals("Passeport")){
                    AdTypCarteID = "PP";
                }*/


            }
        });


        Ad_NumManuelEditText = (EditText) findViewById(R.id.input_numero_manuel_adherent);

        inputLayoutNumManuel = (TextInputLayout) findViewById(R.id.input_layout_numero_manuel_adherent);
        Ad_NumManuelEditText.addTextChangedListener(new MyTextWatcher(Ad_NumManuelEditText));

        Ad_DetailsTypeNatureEditText = (EditText) findViewById(R.id.input_details_type_membre);
        alreadyUpperCase(Ad_DetailsTypeNatureEditText);
        spinnerGuichet = (Spinner) findViewById(R.id.spn_type_membre);
        typeMembreList = new ArrayList<Category>();
        spinnerGuichet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                //   checkOffersSum(); // same method for first 4 spinners. for last 4 spinners is checkScoresSum()
                typeMembreID = guichetListID.get(position);//pour recuperer l'ID du type de membre selectionné
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub.

            }

        });
        Ad_NomEditText = (EditText) findViewById(R.id.input_nom_adherent);
        alreadyUpperCase(Ad_NomEditText);

        //to manage user input
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_nom_adherent);
        Ad_NomEditText.addTextChangedListener((TextWatcher) new MyTextWatcher(Ad_NomEditText));

        Ad_PrenomEditText = (EditText) findViewById(R.id.input_prenom_adherent);
       // Ad_DateNaissEditText = (EditText) findViewById(R.id.input_txt_date_naiss_adherent);
        Ad_LieuNaissEditText = (EditText) findViewById(R.id.input_lieu_naiss_adherent);
        Ad_SexeSpinner = (Spinner) findViewById(R.id.spn_sexe);
        Ad_NationaliteSpinner = (CountryCodePicker) findViewById(R.id.ccp_nationalite_adherent);
        Ad_SituationMatSpinner = (Spinner) findViewById(R.id.spn_situation_matrimoiale);
        Ad_NombreEnfantEditText = (EditText) findViewById(R.id.input_nbre_enfant_adherent);
        Ad_EmailEditText = (EditText) findViewById(R.id.input_txt_email_adherent);


        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email_adherent);
        Ad_EmailEditText.addTextChangedListener(new MyTextWatcher(Ad_EmailEditText));

        Ad_DomicileEditText = (EditText) findViewById(R.id.input_domicile_adherent);
        Ad_LieuTravailEditText = (EditText) findViewById(R.id.input_lieu_travail_adherent);
        Ad_ActivitePrincipaleEditText = (EditText) findViewById(R.id.input_activite_principal_adherent);

        Ad_NumPieceEditText = (EditText) findViewById(R.id.input_numero_piece_adherent);
      //  Ad_DateDelivranceEditText = (EditText) findViewById(R.id.input_txt_validite_debut_adherent);
       // Ad_DateExpirationEditText = (EditText) findViewById(R.id.input_txt_validite_fin_adherent);
        Ad_TypeLocationSpinner = (Spinner) findViewById(R.id.spn_location);

        new CreateAdherent.GetCategories().execute();

        if (to_update_adherent){

            Intent intent = getIntent();
            eavId = intent.getStringExtra(KEY_ADHERENT_ID);
            header_user_guichet.setText("Modification d'un adhérent");
            new FetchEavDetailsAsyncTask().execute();

        }


        // spinner item select listener
        addButton = (CircularProgressButton) findViewById(R.id.btn_save_adherent);
        annulerButton = (CircularProgressButton) findViewById(R.id.btn_clean);
        delButton = (CircularProgressButton) findViewById(R.id.btn_delete_adherent);
        delButton.setVisibility(View.GONE);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    submitForm();
                    //addAdherent();
                   // Intent intent = new Intent(CreateAdherent.this,GetPieceAdherent.class);
                   // Intent intent = new Intent(CreateAdherent.this,GetFraisAdherent.class);
                   // startActivity(intent);
                } else {
                    Toast.makeText(CreateAdherent.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });


    }
/*error*/
    /**
     * Validating form
     */

    private void submitForm() {

        if (!validateNumManuel()) {
            return;
        }
        if (!validateName()) {
            return;
        }

       if (!validateEmail()) {
            return;
        }

         /*if (!validatePassword()) {
            return;
        } */
if (testError==true){ addAdherent();}

        //Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }
    private boolean validateName() {
        if (Ad_NomEditText.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(Ad_NomEditText);
            testError=false;
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
            testError=true;
        }

        return true;
    }
    private boolean validateNumManuel() {
        if (Ad_NumManuelEditText.getText().toString().trim().isEmpty()) {
            inputLayoutNumManuel.setError(getString(R.string.err_msg_num_manuel));
            requestFocus(Ad_NumManuelEditText);
            testError=false;
            return false;
        } else {
            inputLayoutNumManuel.setErrorEnabled(false);
            testError=true;
        }

        return true;
    }

    private boolean validateEmail() {
        String email = Ad_EmailEditText.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(Ad_EmailEditText);
            testError=false;
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
            testError=true;
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

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
                case R.id.input_nom_adherent:
                    validateName();
                    break;
               //case R.id.input_email:
                case R.id.input_txt_email_adherent:
                    validateEmail();
                    break;
                //case R.id.input_password:
                case R.id.input_numero_manuel_adherent:
                    validateNumManuel();
                    break;
            }
        }
    }

/*error*/
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


    /**
     * Async task to get all food categories
     * */
    private class GetCategories extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CreateAdherent.this);
            pDialog.setMessage("Chargement de la liste des types de membres...");
            pDialog.setCancelable(false);
//            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();

            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair(KEY_AD_GX_NUMERO, String.valueOf(MyData.GUICHET_ID)));

            String jsonGuichet = jsonParser.makeServiceCall(BASE_URL + "get_type_membre_by_guichet_id.php", ServiceHandler.GET,httpParams);
            String jsonEAV = jsonParser.makeServiceCall(BASE_URL + "get_main_eav_by_guichet_id.php", ServiceHandler.GET,httpParams);

            Log.e("Response: ", "> " + jsonGuichet);
            Log.e("Response: ", "> " + jsonEAV);
            //for manage list of guichet
            if (jsonGuichet != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonGuichet);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj
                                .getJSONArray("categories");

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category cat = new Category(catObj.getInt("id"),
                                    catObj.getString("name"));
                            typeMembreList.add(cat);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data Type membre", "Didn't receive any data from server!");
            }
            //for manage main EAV
            if (jsonEAV != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonEAV);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj
                                .getJSONArray("categories");

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            /*Category cat = new Category(catObj.getInt("id"),
                                    catObj.getString("name"));
                            typeMembreList.add(cat);*/
                            mainEAV = new Category(catObj.getInt("id"),
                                    catObj.getString("name"));
                            cxName= mainEAV.getName().toUpperCase();
//                            cxTitle.setTypeface(null, Typeface.BOLD);
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    // Stuff that updates the UI
                                    cxTitle.setText(cxTitle.getText()+" "+ cxName);

                                }
                            });
//                            cxTitle.setText(cxTitle.getText()+" "+ cxName);   //Génère des erreurs lorsqu'il est ici

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data MAIN EAV", "Didn't receive any data from server!");
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            populateSpinner();
        }

    }

    /**
     * Adding spinner data
     * */
    private void populateSpinner() {
        List<String> lablesGuichet = new ArrayList<String>(); //for guichets

        //tvCaisse.setText("");

        for (int i = 0; i < typeMembreList.size(); i++) {
            lablesGuichet.add(typeMembreList.get(i).getName());//recupère les noms de guichets
            guichetListID.add(typeMembreList.get(i).getId()); //recupère les Id de guichet
        }


        // Creating adapter for spinner guichet
        ArrayAdapter<String> spinnerGuichetAdapter = new ArrayAdapter<String>(CreateAdherent.this,
                android.R.layout.simple_spinner_item, lablesGuichet);

        // Drop down layout style - list view with radio button
        spinnerGuichetAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        // attaching data adapter to spinner
        spinnerGuichet.setAdapter(spinnerGuichetAdapter);
    }

private void getEditText(){
    AdNumManuel = Ad_NumManuelEditText.getText().toString();
    AdNom = Ad_NomEditText.getText().toString();
    AdPrenom = Ad_PrenomEditText.getText().toString();
    AdDateNaiss = Ad_DateNaissEditText.getText().toString();
    AdLieuNaiss = Ad_LieuNaissEditText.getText().toString();
    // AdSexe = Ad_SexeSpinner.getSelectedItem().toString();
     AdSexe = Adherent.encodeAdSexe(Ad_SexeSpinner.getSelectedItem().toString());

    /*if (Ad_SexeSpinner.getSelectedItem().toString().equals("Masculin")) {
        AdSexe = "M";
    }else {
        AdSexe = "F";
    }*/
    AdNationalite = Ad_NationaliteSpinner.getSelectedCountryName();
//    AdSitFam = Ad_SituationMatSpinner.getSelectedItem().toString();
    AdNbreEnfACh = Ad_NombreEnfantEditText.getText().toString();
    AdTel1 = ccp_phone1.getFullNumberWithPlus();
    AdTel2 = ccp_phone2.getFullNumberWithPlus();
    AdTel3 = ccp_phone3.getFullNumberWithPlus();
    AdEMail = Ad_EmailEditText.getText().toString();
    AdProfess = Ad_ProfessionSpinner.getText().toString();
    AdDomicile = Ad_DomicileEditText.getText().toString();
    AdLieuTrav = Ad_LieuTravailEditText.getText().toString();
    AdActivitePr = Ad_ActivitePrincipaleEditText.getText().toString();
//    AdTypCarteID = Ad_TypePieceSpinner.getText().toString();
    AdNumCarteID = Ad_NumPieceEditText.getText().toString();
    AdValideDu = Ad_DateDelivranceEditText.getText().toString();
    AdValideAu = Ad_DateExpirationEditText.getText().toString();
    AdDetailsTypeNature = Ad_DetailsTypeNatureEditText.getText().toString();

    AdTypHabite = Adherent.encodeAdTypHabite(Ad_TypeLocationSpinner.getSelectedItem().toString());
    /*
    if (Ad_TypeLocationSpinner.getSelectedItem().toString().equals("Propriétaire")) {
        AdTypHabite = "P";
    }else if (Ad_TypeLocationSpinner.getSelectedItem().toString().equals("Locataire")){
        AdTypHabite = "L";
    } else {
        AdTypHabite = "C";
    }
*/
    AdSitFam = Adherent.encodeAdSitFam(Ad_SituationMatSpinner.getSelectedItem().toString());
    /*
    if (Ad_SituationMatSpinner.getSelectedItem().toString().equals("Célibataire")) {
        AdSitFam = "C";
    }else if (Ad_SituationMatSpinner.getSelectedItem().toString().equals("Marié")){
        AdSitFam = "M";
    }else if (Ad_SituationMatSpinner.getSelectedItem().toString().equals("Divorcé")){
        AdSitFam = "D";
    } else {
        AdSitFam = "V"; //V: Veuf
    }*/
}

    private void addAdherent() {
        if (!cxName.equals("") && typeMembreID!=0) {
           getEditText();
            adherent = new Adherent(
                    AdSexe+AdDateNaiss+AdNumCarteID,
                    AdNumManuel,
                    AdNom ,
                    AdPrenom,
                    AdDateNaiss,
                    AdLieuNaiss,
                    AdSexe,
                    AdNationalite,
                    AdSitFam,
                    AdNbreEnfACh,
                    AdTel1,
                    AdTel2,
                    AdTel3,
                    AdEMail,
                    AdProfess,
                    AdDomicile,
                    AdLieuTrav,
                    AdActivitePr,
                    AdTypCarteID,
                    AdNumCarteID,
                    AdValideDu,
                    AdValideAu,
                    AdTypHabite,
                    "N",
                    null,
                    null,
                    MyData.GUICHET_ID


            );
            adherent.setAdDetailsTypeMembre(AdDetailsTypeNature);
            if (to_update_adherent){
                new AddAdherentAsyncTask().execute();
            }else{
                Intent i = new Intent(CreateAdherent.this, GetPieceAdherent.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(KEY_ADHERENT, (Serializable) adherent);
                // bundle.putSerializable(KEY_ADHERENT, adherent);
                i.putExtras(bundle);
                i.putExtra(KEY_TYPE_MEMBRE, typeMembreID); // extra pour gérer le type de membre
                // startActivityForResult(intent, 20);
                startActivityForResult(i,20);
                //finish();
            }

        } else if (cxName.equals("")) {
//            Toast.makeText(CreateAdherent.this,
//                    "Un ou plusieurs champs sont vides!",
//                    Toast.LENGTH_LONG).show();
            Toast.makeText(CreateAdherent.this,
                    "Veuillez contacter l'administrateur de la caisse "+MyData.CAISSE_NAME+" pour renseigner l'EAV principal sur le guichet "+MyData.GUICHET_NAME,
                    Toast.LENGTH_LONG).show();

        }else if (typeMembreID==0) {

            Toast.makeText(CreateAdherent.this,
                    "Veuillez contacter l'administrateur de la caisse "+MyData.CAISSE_NAME+" pour affecter les types de membre sur le guichet "+MyData.GUICHET_NAME,
                    Toast.LENGTH_LONG).show();
        }


    }
    private void findViewsById() {
        Ad_DateNaissEditText = (EditText) findViewById(R.id.input_txt_date_naiss_adherent);
        Ad_DateNaissEditText.requestFocus();
        Ad_DateNaissEditText.setInputType(InputType.TYPE_NULL);

        Ad_DateDelivranceEditText = (EditText) findViewById(R.id.input_txt_validite_debut_adherent);
        Ad_DateDelivranceEditText.requestFocus();
        Ad_DateDelivranceEditText.setInputType(InputType.TYPE_NULL);

        Ad_DateExpirationEditText = (EditText) findViewById(R.id.input_txt_validite_fin_adherent);
        Ad_DateExpirationEditText.requestFocus();
        Ad_DateExpirationEditText.setInputType(InputType.TYPE_NULL);




    }

    private void setDateTimeField() {
        Ad_DateNaissEditText.setOnClickListener(this);
        Ad_DateDelivranceEditText.setOnClickListener(this);
        Ad_DateExpirationEditText.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();
        Ad_DateNaiss_PickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                Ad_DateNaissEditText.setText(dateFormatter.format(newDate.getTime()));
}

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

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
        if(v == Ad_DateNaissEditText) {
            Ad_DateNaiss_PickerDialog.show();
        }else if (v == Ad_DateDelivranceEditText){
            Ad_DateDelivrance_PickerDialog.show();
        }else if (v == Ad_DateExpirationEditText){
            Ad_DateExpiration_PickerDialog.show();
        }
    }



    /**
     * AsyncTask for adding a adherent
     */
    private class AddAdherentAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            addButton.startAnimation() ;// to start animation on button save
            //Display proggress bar
            pDialog = new ProgressDialog(CreateAdherent.this);
            pDialog.setMessage("Adding Adhérent. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
           // httpParams.put(KEY_AD_GX_NUMERO, gxCaisse);
            //httpParams.put(KEY_AD_GX_NUMERO, String.valueOf(caisseID));
            /*
            httpParams.put(KEY_AD_GX_NUMERO, String.valueOf(MyData.GUICHET_ID));
            httpParams.put(KEY_AD_NUM_MANUEL, AdNumManuel);
            httpParams.put(KEY_AD_NOM, AdNom);
            httpParams.put(KEY_AD_PRENOM, AdPrenom);
            httpParams.put(KEY_AD_DATE_NAISS, AdDateNaiss);
            httpParams.put(KEY_AD_LIEU_NAISS, AdLieuNaiss);
            httpParams.put(KEY_AD_SEXE, AdSexe);
            httpParams.put(KEY_AD_NATIONALITE, AdNationalite);
            httpParams.put(KEY_AD_SIT_FAM, AdSitFam);
            httpParams.put(KEY_AD_NBRE_ENFANT, AdNbreEnfACh);
            httpParams.put(KEY_AD_TEL1, AdTel1);
            httpParams.put(KEY_AD_TEL2, AdTel2);
            httpParams.put(KEY_AD_TEL3, AdTel3);
            httpParams.put(KEY_AD_EMAIL, AdEMail);
            httpParams.put(KEY_AD_PROFESSION, AdProfess);
            httpParams.put(KEY_AD_DOMICILE, AdDomicile);
            httpParams.put(KEY_AD_LIEU_TRAVAIL, AdLieuTrav);
            httpParams.put(KEY_AD_ACTIVITE_PRINC, AdActivitePr);
            httpParams.put(KEY_AD_TYPE_CARTE_ID, AdTypCarteID);
            httpParams.put(KEY_AD_NUM_CARTE_ID, AdNumCarteID);
            httpParams.put(KEY_AD_VALIDE_DU, AdValideDu);
            httpParams.put(KEY_AD_VALIDE_AU, AdValideAu);
            httpParams.put(KEY_AD_TYPE_HABITE, AdTypHabite);
            httpParams.put(KEY_CV_USER_CREE, String.valueOf(MyData.USER_ID));
            httpParams.put(KEY_AdTypMemb2, AdDetailsTypeNature);

            if (to_update_adherent)httpParams.put(KEY_ADHERENT_ID, String.valueOf(eavId));
//            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
//                    BASE_URL + "add_adherent.php", "POST", httpParams);

            JSONObject jsonObject = (to_update_adherent)? httpJsonParser.makeHttpRequest(
                    BASE_URL + "update_adherent.php", "POST", httpParams):
                    httpJsonParser.makeHttpRequest(
                            BASE_URL + "add_adherent.php", "POST", httpParams);
            */
            httpParams.put(KEY_AD_GX_NUMERO, String.valueOf(MyData.GUICHET_ID));
            httpParams.put(KEY_AD_NUM_MANUEL, AdNumManuel);
            httpParams.put(KEY_AD_NOM, AdNom);
            httpParams.put(KEY_AD_PRENOM, AdPrenom);
            httpParams.put(KEY_AD_DATE_NAISS, AdDateNaiss);
            httpParams.put(KEY_AD_LIEU_NAISS, AdLieuNaiss);
            httpParams.put(KEY_AD_SEXE, AdSexe);
            httpParams.put(KEY_AD_NATIONALITE, AdNationalite);
            httpParams.put(KEY_AD_SIT_FAM, AdSitFam);
            httpParams.put(KEY_AD_NBRE_ENFANT, AdNbreEnfACh);
            httpParams.put(KEY_AD_TEL1, AdTel1);
            httpParams.put(KEY_AD_TEL2, AdTel2);
            httpParams.put(KEY_AD_TEL3, AdTel3);
            httpParams.put(KEY_AD_EMAIL, AdEMail);
            httpParams.put(KEY_AD_PROFESSION, AdProfess);
            httpParams.put(KEY_AD_DOMICILE, AdDomicile);
            httpParams.put(KEY_AD_LIEU_TRAVAIL, AdLieuTrav);
            httpParams.put(KEY_AD_ACTIVITE_PRINC, AdActivitePr);
            httpParams.put(KEY_AD_TYPE_CARTE_ID, AdTypCarteID);
            httpParams.put(KEY_AD_NUM_CARTE_ID, AdNumCarteID);
            httpParams.put(KEY_AD_VALIDE_DU, AdValideDu);
            httpParams.put(KEY_AD_VALIDE_AU, AdValideAu);
            httpParams.put(KEY_AD_TYPE_HABITE, AdTypHabite);
            httpParams.put(KEY_CV_USER_CREE, String.valueOf(MyData.USER_ID));
            httpParams.put(KEY_AdTypMemb2, AdDetailsTypeNature);
            if (to_update_adherent)httpParams.put(KEY_ADHERENT_ID, String.valueOf(eavId));
            JSONObject jsonObject = (to_update_adherent)? httpJsonParser.makeHttpRequest(
                    BASE_URL + "update_adherent.php", "POST", httpParams):
                    httpJsonParser.makeHttpRequest(
                            BASE_URL + "add_adherent.php", "POST", httpParams);

            try {
                success = jsonObject.getInt(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            addButton.revertAnimation(); // to stop animation on button save
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(CreateAdherent.this,
                                "Adhérent mis à jour !", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CreateAdherent.this,
                                "Some error occurred while adding Adhérent",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }


    /**
     * Fetches single movie details from the server
     */
    private class FetchEavDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog_update = new ProgressDialog(CreateAdherent.this);
            pDialog_update.setMessage("Chargement des détails de l'adhérent. Patientez...");
            pDialog_update.setIndeterminate(false);
            pDialog_update.setCancelable(false);
            pDialog_update.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_ADHERENT_ID, eavId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_adherent_details.php", "GET", httpParams);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject eav;
                if (success == 1) {
                    //Parse the JSON response
                    eav = jsonObject.getJSONObject(KEY_DATA);
                    AdNumManuel = eav.getString(KEY_AD_NUM_MANUEL);
                    AdNom = eav.getString(KEY_AD_NOM);
                    AdPrenom = eav.getString(KEY_AD_PRENOM);
                    AdDateNaiss = eav.getString(KEY_AD_DATE_NAISS);
                    AdLieuNaiss = eav.getString(KEY_AD_LIEU_NAISS);
                    AdSexe = eav.getString(KEY_AD_SEXE);
                    AdNationalite = eav.getString(KEY_AD_NATIONALITE);
                    AdSitFam = eav.getString(KEY_AD_SIT_FAM);
                    AdNbreEnfACh = eav.getString(KEY_AD_NBRE_ENFANT);
                    AdTel1 = eav.getString(KEY_AD_TEL1);
                    AdTel2 = eav.getString(KEY_AD_TEL2);
                    AdTel3 = eav.getString(KEY_AD_TEL3);
                    AdEMail = eav.getString(KEY_AD_EMAIL);
                    AdProfess = eav.getString(KEY_AD_PROFESSION);
                    AdDomicile = eav.getString(KEY_AD_DOMICILE);
                    AdLieuTrav = eav.getString(KEY_AD_LIEU_TRAVAIL);
                    AdActivitePr = eav.getString(KEY_AD_ACTIVITE_PRINC);
                    AdTypCarteID = eav.getString(KEY_AD_TYPE_CARTE_ID);
                    AdNumCarteID = eav.getString(KEY_AD_NUM_CARTE_ID);
                    AdValideDu = eav.getString(KEY_AD_VALIDE_DU);
                    AdValideAu = eav.getString(KEY_AD_VALIDE_AU);
                    AdTypHabite = eav.getString(KEY_AD_TYPE_HABITE);
                    AdDetailsTypeNature = eav.getString(KEY_AdTypMemb2);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog_update.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    //Populate the Edit Texts once the network activity is finished executing

/*
                    Ad_NumManuelEditText.setText(AdNumManuel);
                    Ad_NomEditText.setText(AdNom);
                    Ad_PrenomEditText.setText(AdPrenom);
                    Ad_DateNaissEditText.setText(AdDateNaiss);
                    Ad_LieuNaissEditText.setText(AdLieuNaiss);

                    //debut AdSexe
                    String compareValue = "Masculin";

                    if (AdSexe.equals("M")){

                        compareValue = "Masculin";

                    }else{
                        compareValue = "Féminin";
                    }
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(CreateAdherent.this, R.array.type_sexe, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Ad_SexeSpinner.setAdapter(adapter);
                    if (compareValue != null) {
                        int spinnerPosition = adapter.getPosition(compareValue);
                        Ad_SexeSpinner.setSelection(spinnerPosition);}
                    //Fin AdSexe
                    //debut AdSitFam
                    String compareAdSitFam = "C";
                    if (AdSitFam.equals("C")){
                        compareAdSitFam = "Célibataire";
                    }else if (AdSitFam.equals("M")){
                        compareAdSitFam = "Marié";
                    }else if (AdSitFam.equals("D")){
                        compareAdSitFam = "Divorcé";
                    }else if (AdSitFam.equals("V")){
                        compareAdSitFam = "Veuf";
                    }
                    ArrayAdapter<CharSequence> adapterAdSitFam = ArrayAdapter.createFromResource(CreateAdherent.this, R.array.type_situation_matrimoniale, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Ad_SituationMatSpinner.setAdapter(adapterAdSitFam);
                    if (compareAdSitFam != null) {
                        int spinnerPosition = adapterAdSitFam.getPosition(compareAdSitFam);
                        Ad_SituationMatSpinner.setSelection(spinnerPosition);
                    }
                    //Fin AdSitFam

                    //debut Ad_TypePieceSpinner
                    String comparePiece = "";

                    if (AdTypCarteID.equals("CN")){
                        comparePiece = "Carte nationale d'identité";
                    }else if (AdTypCarteID.equals("CS")){
                        comparePiece = "Carte de séjour";
                    }else if (AdTypCarteID.equals("CC")){
                        comparePiece = "Carte consulaire";
                    }else if (AdTypCarteID.equals("CP")){
                        comparePiece = "Carte professionnelle";
                    }else if (AdTypCarteID.equals("CM")){
                        comparePiece = "Carte militaire";
                    }else if (AdTypCarteID.equals("PS")){
                        comparePiece = "Carte CNPS";
                    }else if (AdTypCarteID.equals("PC")){
                        comparePiece = "Permis de conduire";
                    }else if (AdTypCarteID.equals("PP")){
                        comparePiece = "Passeport";
                    }
                    Ad_TypePieceSpinner.setText(comparePiece);

                    //Fin Ad_TypePieceSpinner

                    //Ad_NationaliteSpinner.setsele(AdNationalite);
                    //Ad_SituationMatSpinner.setsele(AdSitFam);
                    Ad_NombreEnfantEditText.setText(AdNbreEnfACh);
                    //AdTel1.setText(AdTel1);
                    Ad_EmailEditText.setText(AdEMail);
                    Ad_ProfessionSpinner.setText(AdProfess);
                    Ad_DomicileEditText.setText(AdDomicile);
                    Ad_LieuTravailEditText.setText(AdLieuTrav);
                    Ad_ActivitePrincipaleEditText.setText(AdActivitePr);
//                    Ad_TypePieceSpinner.setText(AdTypCarteID);
                    Ad_NumPieceEditText.setText(AdNumCarteID);
                    Ad_DateDelivranceEditText.setText(AdValideDu);
                    Ad_DateExpirationEditText.setText(AdValideAu);
                    Ad_DetailsTypeNatureEditText.setText(AdDetailsTypeNature);

                    //AdTypHabite = eav.getString(KEY_AD_TYPE_HABITE);
                    */


                    Ad_NumManuelEditText.setText(AdNumManuel);
                    Ad_NomEditText.setText(AdNom);
                    Ad_PrenomEditText.setText(AdPrenom);
                    Ad_DateNaissEditText.setText(AdDateNaiss);
                    Ad_LieuNaissEditText.setText(AdLieuNaiss);
                    String compareValue = "Masculin";

                    if (AdSexe.equals("M")){



                        compareValue = "Masculin";


                    }else{
                        compareValue = "Féminin";
                    }
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(CreateAdherent.this, R.array.type_sexe, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Ad_SexeSpinner.setAdapter(adapter);
                    if (compareValue != null) {
                        int spinnerPosition = adapter.getPosition(compareValue);
                        Ad_SexeSpinner.setSelection(spinnerPosition);}


                    //debut AdSitFam
                    String compareAdSitFam = "C";
                    if (AdSitFam.equals("C")){
                        compareAdSitFam = "Célibataire";
                    }else if (AdSitFam.equals("M")){
                        compareAdSitFam = "Marié";
                    }else if (AdSitFam.equals("D")){
                        compareAdSitFam = "Divorcé";
                    }else if (AdSitFam.equals("V")){
                        compareAdSitFam = "Veuf";
                    }
                    ArrayAdapter<CharSequence> adapterAdSitFam = ArrayAdapter.createFromResource(CreateAdherent.this, R.array.type_situation_matrimoniale, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Ad_SituationMatSpinner.setAdapter(adapterAdSitFam);
                    if (compareAdSitFam != null) {
                        int spinnerPosition = adapterAdSitFam.getPosition(compareAdSitFam);
                        Ad_SituationMatSpinner.setSelection(spinnerPosition);
                    }
                    //Fin AdSitFam

                    //debut Ad_TypePieceSpinner
                    String comparePiece = "";

                    if (AdTypCarteID.equals("CN")){
                        comparePiece = "Carte nationale d'identité";
                    }else if (AdTypCarteID.equals("CS")){
                        comparePiece = "Carte de séjour";
                    }else if (AdTypCarteID.equals("CC")){
                        comparePiece = "Carte consulaire";
                    }else if (AdTypCarteID.equals("CP")){
                        comparePiece = "Carte professionnelle";
                    }else if (AdTypCarteID.equals("CM")){
                        comparePiece = "Carte militaire";
                    }else if (AdTypCarteID.equals("PS")){
                        comparePiece = "Carte CNPS";
                    }else if (AdTypCarteID.equals("PC")){
                        comparePiece = "Permis de conduire";
                    }else if (AdTypCarteID.equals("PP")){
                        comparePiece = "Passeport";
                    }
                    Ad_TypePieceSpinner.setText(comparePiece);

                    //Fin Ad_TypePieceSpinner
                    //Ad_NationaliteSpinner.setsele(AdNationalite);
                    Ad_NationaliteSpinner.setCountryForNameCode(AdNationalite);
                    //Ad_SituationMatSpinner.setsele(AdSitFam);
                    Ad_NombreEnfantEditText.setText(AdNbreEnfACh);
                    ccp_phone1.setFullNumber(AdTel1);
                    ccp_phone2.setFullNumber(AdTel2);
                    ccp_phone3.setFullNumber(AdTel3);
                    //AdTel1.setText(AdTel1);
                    Ad_EmailEditText.setText(AdEMail);
                    Ad_ProfessionSpinner.setText(AdProfess);
                    Ad_DomicileEditText.setText(AdDomicile);
                    Ad_LieuTravailEditText.setText(AdLieuTrav);
                    Ad_ActivitePrincipaleEditText.setText(AdActivitePr);
//                    Ad_TypePieceSpinner.setText(AdTypCarteID);
                    Ad_NumPieceEditText.setText(AdNumCarteID);
                    Ad_DateDelivranceEditText.setText(AdValideDu);
                    Ad_DateExpirationEditText.setText(AdValideAu);
                    Ad_DetailsTypeNatureEditText.setText(AdDetailsTypeNature);


                }
            });
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 20) {
            // If the result code is 20 that means that
            // the user has deleted/updated the movie.
            // So refresh the movie listing
            Intent intent = getIntent();
            setResult(20, intent);
            finish();
            to_update_adherent = false; //to reset default value
           /* finish();
            startActivity(intent);
            */
        }
    }


}