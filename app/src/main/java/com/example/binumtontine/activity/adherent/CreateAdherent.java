
package com.example.binumtontine.activity.adherent;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.JRSpinner;
import com.example.binumtontine.R;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.hbb20.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class CreateAdherent extends AppCompatActivity implements View.OnClickListener, SERVER_ADDRESS {


    private static final String KEY_SUCCESS = "success";

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

    private static final String KEY_ADHERENT = "ADHERENT";


    private static String STRING_EMPTY = "";

    private TextView cxTitle;
    private String cxName;
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

    private Adherent adherent;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_adherent);


        /*end manage*/

        /* Begin manage country*/

        cxTitle = (TextView) findViewById(R.id.tv_caisse_name);
        cxName= MyData.CAISSE_NAME.toUpperCase();
        cxTitle.setTypeface(null, Typeface.BOLD);
        cxTitle.setText(cxTitle.getText()+" "+ cxName);

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

            }
        });


        Ad_NumManuelEditText = (EditText) findViewById(R.id.input_numero_manuel_adherent);
        Ad_NomEditText = (EditText) findViewById(R.id.input_nom_adherent);
        alreadyUpperCase(Ad_NomEditText);
        Ad_PrenomEditText = (EditText) findViewById(R.id.input_prenom_adherent);
       // Ad_DateNaissEditText = (EditText) findViewById(R.id.input_txt_date_naiss_adherent);
        Ad_LieuNaissEditText = (EditText) findViewById(R.id.input_lieu_naiss_adherent);
        Ad_SexeSpinner = (Spinner) findViewById(R.id.spn_sexe);
        Ad_NationaliteSpinner = (CountryCodePicker) findViewById(R.id.ccp_nationalite_adherent);
        Ad_SituationMatSpinner = (Spinner) findViewById(R.id.spn_situation_matrimoiale);
        Ad_NombreEnfantEditText = (EditText) findViewById(R.id.input_nbre_enfant_adherent);
        Ad_EmailEditText = (EditText) findViewById(R.id.input_txt_email_adherent);
        Ad_DomicileEditText = (EditText) findViewById(R.id.input_domicile_adherent);
        Ad_LieuTravailEditText = (EditText) findViewById(R.id.input_lieu_travail_adherent);
        Ad_ActivitePrincipaleEditText = (EditText) findViewById(R.id.input_activite_principal_adherent);

        Ad_NumPieceEditText = (EditText) findViewById(R.id.input_numero_piece_adherent);
      //  Ad_DateDelivranceEditText = (EditText) findViewById(R.id.input_txt_validite_debut_adherent);
       // Ad_DateExpirationEditText = (EditText) findViewById(R.id.input_txt_validite_fin_adherent);
        Ad_TypeLocationSpinner = (Spinner) findViewById(R.id.spn_location);
     /*
        gx_denominationEditText = (EditText) findViewById(R.id.input_denomination_guichet);
        alreadyUpperCase(gx_denominationEditText);
        Ad_DateNaissEditText = (EditText) findViewById(R.id.input_txt_dateDebut_guichet);
        gx_adresseEditText = (EditText) findViewById(R.id.input_txt_AdresseGx);

        gx_nom_pcaEditText = (EditText) findViewById(R.id.input_txt_NomPCA_Gx);
        alreadyUpperCase(gx_nom_pcaEditText);
        gx_nom_dgEditText = (EditText) findViewById(R.id.input_txt_NomDG_Gx);
        alreadyUpperCase(gx_nom_dgEditText);
        gx_is_forcer_clotSwitch = (Switch) findViewById(R.id.SwitchForcer_la_clotureGx);
        gx_heure_clotEditText = (EditText) findViewById(R.id.input_txt_heure_cloture_Gx);
        gx_heure_clotEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                picker = new TimePickerDialog(CreateAdherent.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                gx_heure_clotEditText.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });
        gx_is_oper_apres_clotSwitch = (Switch) findViewById(R.id.Switch_gx_is_oper_apres_clot);
        gx_nbre_rapp_by_jourEditText = (EditText) findViewById(R.id.input_txt_Nombre_de_rappel_par_jour);
        gx_nbre_jr_av_rappEditText = (EditText) findViewById(R.id.input_txt_Nombre_jour_av_rappel);
        gx_is_jour8_onSwitch = (Switch) findViewById(R.id.Switch8jr_semaine_Gx);
        gx_is_credit_by_objetSwitch = (Switch) findViewById(R.id.Switch_credit_par_objet_Gx);
        gx_first_jr_onEditText = (EditText) findViewById(R.id.input_txt_GuFirstJrOn);
        gx_freq_reun_com_credEditText = (EditText) findViewById(R.id.input_txt_GuFreqReunComCred);
        gx_is_rapp_net_msg_cred_onSwitch = (Switch) findViewById(R.id.Switch_GuIsRappNetMsgCredOn);
*/

        // spinner item select listener
        addButton = (CircularProgressButton) findViewById(R.id.btn_save_adherent);
        annulerButton = (CircularProgressButton) findViewById(R.id.btn_clean);
        delButton = (CircularProgressButton) findViewById(R.id.btn_delete_adherent);
        delButton.setVisibility(View.GONE);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addAdherent();
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



    private void addAdherent() {
       /* if (!STRING_EMPTY.equals(gx_denominationEditText.getText().toString()) &&
                !STRING_EMPTY.equals(Ad_ProfessionSpinner.getText().toString()) &&
                !STRING_EMPTY.equals(Ad_DateNaissEditText.getText().toString()) &&
                !STRING_EMPTY.equals(gx_adresseEditText.getText().toString()) &&
                !STRING_EMPTY.equals(gx_tel2EditText.getText().toString()) &&
                !STRING_EMPTY.equals(gx_tel3EditText.getText().toString()) &&
                !STRING_EMPTY.equals(gx_is_forcer_clotSwitch.getText().toString()) &&
                !STRING_EMPTY.equals(gx_heure_clotEditText.getText().toString()) &&
                !STRING_EMPTY.equals(gx_tel1EditText.getText().toString())) {*/
        if (true) {
            //gxCaisse = mySpinnerCaisse.getText().toString();
            //gxCaisse = "1";//gérer plutot dynamiquement
            AdNumManuel = Ad_NumManuelEditText.getText().toString();
            AdNom = Ad_NomEditText.getText().toString();
            AdPrenom = Ad_PrenomEditText.getText().toString();
            AdDateNaiss = Ad_DateNaissEditText.getText().toString();
            AdLieuNaiss = Ad_LieuNaissEditText.getText().toString();
           // AdSexe = Ad_SexeSpinner.getSelectedItem().toString();
            if (Ad_SexeSpinner.getSelectedItem().toString().equals("Masculin")) {
                AdSexe = "M";
            }else {
                AdSexe = "F";
            }
            AdNationalite = Ad_NationaliteSpinner.getSelectedCountryName();
            AdSitFam = Ad_SituationMatSpinner.getSelectedItem().toString();
            AdNbreEnfACh = Ad_NombreEnfantEditText.getText().toString();
            AdTel1 = ccp_phone1.getFullNumberWithPlus();
            AdTel2 = ccp_phone2.getFullNumberWithPlus();
            AdTel3 = ccp_phone3.getFullNumberWithPlus();
            AdEMail = Ad_EmailEditText.getText().toString();
            AdProfess = Ad_ProfessionSpinner.getText().toString();
            AdDomicile = Ad_DomicileEditText.getText().toString();
            AdLieuTrav = Ad_LieuTravailEditText.getText().toString();
            AdActivitePr = Ad_ActivitePrincipaleEditText.getText().toString();
            AdTypCarteID = Ad_TypePieceSpinner.getText().toString();
            AdNumCarteID = Ad_NumPieceEditText.getText().toString();
            AdValideDu = Ad_DateDelivranceEditText.getText().toString();
            AdValideAu = Ad_DateExpirationEditText.getText().toString();

            if (Ad_TypeLocationSpinner.getSelectedItem().toString().equals("Propriétaire")) {
                AdTypHabite = "P";
            }else if (Ad_TypeLocationSpinner.getSelectedItem().toString().equals("Locataire")){

                AdTypHabite = "L";

            } else {
                AdTypHabite = "C";
            }
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
                    "false",
                    null,
                    null,
                    MyData.GUICHET_ID


            );

            Intent i = new Intent(CreateAdherent.this, GetPieceAdherent.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(KEY_ADHERENT, (Serializable) adherent);
           // bundle.putSerializable(KEY_ADHERENT, adherent);
            i.putExtras(bundle);
           // i.putExtra(KEY_ADHERENT, adherent);
            // startActivityForResult(intent, 20);
            startActivityForResult(i,20);
            //finish();

           // new AddAdherentAsyncTask().execute();
        } else {
            Toast.makeText(CreateAdherent.this,
                    "Un ou plusieurs champs sont vides!",
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
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
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
                     /*   Toast.makeText(CreateAdherent.this,
                                "Adhérent ajouté mais inactif, veuillez compléter ses informations", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();*/

                    } else {
                        Toast.makeText(CreateAdherent.this,
                                "Some error occurred while adding Adhérent",
                                Toast.LENGTH_LONG).show();

                    }
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
           /* finish();
            startActivity(intent);
            */
        }
    }


}