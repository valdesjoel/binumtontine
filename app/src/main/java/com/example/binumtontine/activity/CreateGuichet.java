
package com.example.binumtontine.activity;


import android.app.DatePickerDialog;
import android.app.MediaRouteButton;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.binumtontine.JRSpinner;
import com.example.binumtontine.R;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class CreateGuichet extends AppCompatActivity implements View.OnClickListener, SERVER_ADDRESS {

    
    private static final String KEY_SUCCESS = "success";

    private static final String KEY_GX_CX_NUMERO = "cx_numero";
    private static final String KEY_GX_LOCALITE = "gx_localite";
    private static final String KEY_GX_DENOMINATION = "gx_denomination";
    private static final String KEY_GX_DATE_DEBUT = "gx_date_debut";
    private static final String KEY_GX_ADRESSE = "gx_adresse";
    private static final String KEY_GX_TEL1 = "gx_tel1";
    private static final String KEY_GX_TEL2 = "gx_tel2";
    private static final String KEY_GX_TEL3 = "gx_tel3";
    private static final String KEY_GX_NOM_PCA = "gx_nom_pca";
    private static final String KEY_GX_NOM_DG = "gx_nom_dg";
    private static final String KEY_GX_IS_FORCER_CLOT = "gx_is_forcer_clot";
    private static final String KEY_GX_HEURE_CLOT = "gx_heure_clot";
    private static final String KEY_GX_IS_OPER_APRES_CLOT = "gx_is_oper_apres_clot";
    private static final String KEY_GX_NBRE_RAPP_BY_JOUR = "gx_nbre_rapp_by_jour";
    private static final String KEY_GX_NBRE_JR_AV_RAPP = "gx_nbre_jr_av_rapp";
    private static final String KEY_GX_IS_JOUR8_ON = "gx_is_jour8_on";
    private static final String KEY_GX_IS_CREDIT_BY_OBJET = "gx_is_credit_by_objet";
    private static final String KEY_GX_FIRST_JR_ON = "gx_first_jr_on";
    private static final String KEY_GX_FREQ_REUN_COM_CRED = "gx_freq_reun_com_cred";
    private static final String KEY_GX_IS_RAPP_NET_MSG_CRED_ON = "gx_is_rapp_net_msg_cred_on";

    private static final String KEY_GxIsPreComGesGuPGC = "GxIsPreComGesGuPGC";
    private static final String KEY_GxIsChefGuichCG = "GxIsChefGuichCG";
    private static final String KEY_GxIsAgentGuichAG = "GxIsAgentGuichAG";
    private static final String KEY_GxMtPlafDecAutPCG = "GxMtPlafDecAutPCG";
    private static final String KEY_GxNbMoisHistoDef = "GxNbMoisHistoDef";

    private static final String KEY_GxJoursVillage = "GxJoursVillage";


    private static String STRING_EMPTY = "";

    private TextView cxTitle;
    private String cxName;
    private CountryCodePicker ccp_phone1;
    private CountryCodePicker ccp_phone2;
    private CountryCodePicker ccp_phone3;
    private EditText editTextCarrierPhone1;
    private EditText editTextCarrierPhone2;
    private EditText editTextCarrierPhone3;


    private Spinner countrySpinner; //pour gérer le spinner contenant les pays
    private JRSpinner mySpinnerCaisse; //pour gérer le spinner contenant les caisses
    private JRSpinner mySpinnerLocalite; //pour gérer le spinner contenant les localités
    private JRSpinner mySpinnerFreqComCredit; //pour gérer le spinner contenant les localités
    private EditText gx_denominationEditText;
    private EditText gx_date_debutEditText;
    private EditText gx_adresseEditText;
    private EditText gx_tel1EditText;
    private EditText gx_tel2EditText;
    private EditText gx_tel3EditText;
    private EditText gx_nom_pcaEditText;
    private EditText gx_nom_dgEditText;
    private Switch gx_is_forcer_clotSwitch;
    private EditText gx_heure_clotEditText;
    private TimePickerDialog picker;
    private Switch gx_is_oper_apres_clotSwitch;
    private EditText gx_nbre_rapp_by_jourEditText;
    private EditText gx_nbre_jr_av_rappEditText;
    private Switch gx_is_jour8_onSwitch;
    private Switch SW_GuIsJrSemInit;
    private Switch gx_is_credit_by_objetSwitch;
    private EditText gx_first_jr_onEditText;
    private EditText gx_freq_reun_com_credEditText;
    private Switch gx_is_rapp_net_msg_cred_onSwitch;

    private String gxLocalite;
    private String gxDenomination;
    private String gx_date_debut;
    private String gx_adresse;
    private String gx_tel1;
    private String gx_tel2;
    private String gx_tel3;
    private String gx_nom_pca;
    private String gx_nom_dg;
    private Boolean gx_is_forcer_clot;
    private String gx_heure_clot;
    private Boolean gx_is_oper_apres_clot;
    private String gx_nbre_rapp_by_jour;
    private String gx_nbre_jr_av_rapp;
    private Boolean gx_is_jour8_on;
    private Boolean gx_is_credit_by_objet;
    private String gx_first_jr_on;
    private String gx_freq_reun_com_cred;
    private Boolean gx_is_rapp_net_msg_cred_on;

    private EditText GxMtPlafDecAutPCG;
    private String st_GxMtPlafDecAutPCG;
    private EditText GxNbMoisHistoDef;
    private String st_GxNbMoisHistoDef;
    private SwitchCompat GxIsPreComGesGuPGC,GxIsChefGuichCG,GxIsAgentGuichAG;
    private Boolean bool_GxIsPreComGesGuPGC, bool_GxIsChefGuichCG,bool_GxIsAgentGuichAG;

    /* manage spinner*/

    private TextView tvCaisse;


    //private Button addButton;
    private br.com.simplepass.loadingbutton.customViews.CircularProgressButton addButton;
    private br.com.simplepass.loadingbutton.customViews.CircularProgressButton annulerButton;
    private br.com.simplepass.loadingbutton.customViews.CircularProgressButton delButton;
   // private Button delButton;
    private int success;
    private ProgressDialog pDialog;





    private DatePickerDialog gx_date_debut_PickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date

    private SimpleDateFormat dateFormatter; //propriété permettant de gérer le format de la date
    private LinearLayout block_Switch8jr_semaine_Gx, ll_getTodayDay;
    private EditText jv_jour_1_EditText;
    private EditText jv_jour_2_EditText;
    private EditText jv_jour_3_EditText;
    private EditText jv_jour_4_EditText;
    private EditText jv_jour_5_EditText;
    private EditText jv_jour_6_EditText;
    private EditText jv_jour_7_EditText;
    private EditText jv_jour_8_EditText;
//    private String[] joursVillage = new String[8] ;
    private String joursVillage = "" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_add_movie);
        //setContentView(R.layout.fragment_caisses);spn_my_spinner_localite_caisse
        setContentView(R.layout.activity_create_guichet);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_caisse);
        setSupportActionBar(toolbar);
        setToolbarTitle();*/

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
       // mySpinnerCaisse = (JRSpinner)findViewById(R.id.spn_my_spinner_select_caisse);
       // textInputLayoutCaisse = (TextInputLayout) findViewById(R.id.til_caisse);
       // textInputLayoutCaisse.setVisibility(View.GONE);
        mySpinnerLocalite = (JRSpinner)findViewById(R.id.spn_my_spinner_localite_guichet);

       // mySpinnerCaisse.setItems(getResources().getStringArray(R.array.array_caisse)); //this is important, you must set it to set the item list
        mySpinnerLocalite.setItems(getResources().getStringArray(R.array.array_localite)); //this is important, you must set it to set the item list
//        mySpinnerCaisse.setTitle("Sélectionner une caisse"); //change title of spinner-dialog programmatically
        mySpinnerLocalite.setTitle("Sélectionner une localité"); //change title of spinner-dialog programmatically
       // mySpinnerCaisse.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        mySpinnerLocalite.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically

     /*   mySpinnerCaisse.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });*/
        mySpinnerLocalite.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });


        mySpinnerFreqComCredit = (JRSpinner)findViewById(R.id.spn_GuFreqReunComCred);

        mySpinnerFreqComCredit.setItems(getResources().getStringArray(R.array.array_GuFreqReunComCred)); //this is important, you must set it to set the item list
        mySpinnerFreqComCredit.setTitle("Sélectionner la fréquence des réunions"); //change title of spinner-dialog programmatically
        mySpinnerFreqComCredit.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically

        mySpinnerFreqComCredit.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position
                //  cxLocalite = mySpinnerLocalite.getText().toString();
                // Log.d("iddddddd***",caisseLocalite);
            }
        });


        gx_denominationEditText = (EditText) findViewById(R.id.input_denomination_guichet);
        alreadyUpperCase(gx_denominationEditText);
        gx_date_debutEditText = (EditText) findViewById(R.id.input_txt_dateDebut_guichet);
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
                picker = new TimePickerDialog(CreateGuichet.this,
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
        //SW_GuIsJrSemInit = (Switch) findViewById(R.id.Switch_GuIsJrSemInit);
        block_Switch8jr_semaine_Gx = (LinearLayout) findViewById(R.id.block_Switch8jr_semaine_Gx);
        //ll_getTodayDay = (LinearLayout) findViewById(R.id.ll_getTodayDay);
        jv_jour_1_EditText = (EditText) findViewById(R.id.input_txt_jour_1);
        jv_jour_2_EditText = (EditText) findViewById(R.id.input_txt_jour_2);
        jv_jour_3_EditText = (EditText) findViewById(R.id.input_txt_jour_3);
        jv_jour_4_EditText = (EditText) findViewById(R.id.input_txt_jour_4);
        jv_jour_5_EditText = (EditText) findViewById(R.id.input_txt_jour_5);
        jv_jour_6_EditText = (EditText) findViewById(R.id.input_txt_jour_6);
        jv_jour_7_EditText = (EditText) findViewById(R.id.input_txt_jour_7);
        jv_jour_8_EditText = (EditText) findViewById(R.id.input_txt_jour_8);
        gx_is_credit_by_objetSwitch = (Switch) findViewById(R.id.Switch_credit_par_objet_Gx);
        gx_first_jr_onEditText = (EditText) findViewById(R.id.input_txt_GuFirstJrOn);
        gx_freq_reun_com_credEditText = (EditText) findViewById(R.id.input_txt_GuFreqReunComCred);
        gx_is_rapp_net_msg_cred_onSwitch = (Switch) findViewById(R.id.Switch_GuIsRappNetMsgCredOn);

        GxIsPreComGesGuPGC= (SwitchCompat) findViewById(R.id.SwitchGxIsPreComGesGuPGC);
        GxIsChefGuichCG= (SwitchCompat) findViewById(R.id.SwitchGxIsChefGuichCG);
        GxIsAgentGuichAG= (SwitchCompat) findViewById(R.id.SwitchGxIsAgentGuichAG);
        GxMtPlafDecAutPCG = (EditText) findViewById(R.id.input_txt_GxMtPlafDecAutPCG);
        GxNbMoisHistoDef = (EditText) findViewById(R.id.input_txt_GxNbMoisHistoDef);

        onSwitchButtonClicked(gx_is_jour8_onSwitch);
   //     onSwitchButtonClicked(SW_GuIsJrSemInit);
        // spinner item select listener
        addButton = (CircularProgressButton) findViewById(R.id.btn_save_guichet);
        annulerButton = (CircularProgressButton) findViewById(R.id.btn_clean);
        delButton = (CircularProgressButton) findViewById(R.id.btn_delete_guichet);
        delButton.setVisibility(View.GONE);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addGuichet();
                } else {
                    Toast.makeText(CreateGuichet.this,
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
    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Ajout d'un guichet");

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

    public void onSwitchButtonClicked(View view) {
        boolean checked1 = ((Switch) view).isChecked();
        String str = "";
        // Check which checkbox was clicked
        switch (view.getId()) {
//
            case R.id.Switch8jr_semaine_Gx:
                if (gx_is_jour8_onSwitch.isChecked()) {
                    str = checked1 ? "Minimum en compte obligatoire" : "le minimum en compte n'est pas obligatoire";

                    block_Switch8jr_semaine_Gx.setVisibility(View.VISIBLE);
                    //onRadioButtonClicked(rbCrNatFrEtudDossFixe);
                } else {
                    block_Switch8jr_semaine_Gx.setVisibility(View.GONE);
                }

                break;
            case R.id.Switch_GuIsJrSemInit:
                if (SW_GuIsJrSemInit.isChecked()) {

                    ll_getTodayDay.setVisibility(View.VISIBLE);
                } else {
                    ll_getTodayDay.setVisibility(View.GONE);
                }

                break;

        }
    }

    private void addGuichet() {
       /* if (!STRING_EMPTY.equals(gx_denominationEditText.getText().toString()) &&
                !STRING_EMPTY.equals(mySpinnerLocalite.getText().toString()) &&
                !STRING_EMPTY.equals(gx_date_debutEditText.getText().toString()) &&
                !STRING_EMPTY.equals(gx_adresseEditText.getText().toString()) &&
                !STRING_EMPTY.equals(gx_tel2EditText.getText().toString()) &&
                !STRING_EMPTY.equals(gx_tel3EditText.getText().toString()) &&
                !STRING_EMPTY.equals(gx_is_forcer_clotSwitch.getText().toString()) &&
                !STRING_EMPTY.equals(gx_heure_clotEditText.getText().toString()) &&
                !STRING_EMPTY.equals(gx_tel1EditText.getText().toString())) {*/
if (true){
            //gxCaisse = mySpinnerCaisse.getText().toString();
            //gxCaisse = "1";//gérer plutot dynamiquement
            gxLocalite = mySpinnerLocalite.getText().toString();
            gxDenomination = gx_denominationEditText.getText().toString();
            gx_date_debut = gx_date_debutEditText.getText().toString();
            gx_adresse = gx_adresseEditText.getText().toString();
            gx_tel1 = ccp_phone1.getFullNumberWithPlus();
            gx_tel2 = ccp_phone2.getFullNumberWithPlus();
            gx_tel3 = ccp_phone3.getFullNumberWithPlus();
            /*gx_tel1 = gx_tel1EditText.getText().toString();
            gx_tel2 = gx_tel2EditText.getText().toString();
            gx_tel3 = gx_tel3EditText.getText().toString();*/

            gx_nom_pca = gx_nom_pcaEditText.getText().toString();
            gx_nom_dg = gx_nom_dgEditText.getText().toString();
           // gx_is_forcer_clot = Boolean.parseBoolean(gx_is_forcer_clotSwitch.isChecked());
            gx_is_forcer_clot = gx_is_forcer_clotSwitch.isChecked();
            gx_heure_clot = gx_heure_clotEditText.getText().toString();
           // gx_is_oper_apres_clot = Boolean.parseBoolean(gx_is_oper_apres_clotSwitch.getText().toString());
            gx_is_oper_apres_clot = gx_is_oper_apres_clotSwitch.isChecked();
            gx_nbre_rapp_by_jour = gx_nbre_rapp_by_jourEditText.getText().toString();
            gx_nbre_jr_av_rapp = gx_nbre_jr_av_rappEditText.getText().toString();
            gx_nbre_jr_av_rapp = gx_nbre_jr_av_rappEditText.getText().toString();
           // gx_is_jour8_on = Boolean.parseBoolean(gx_is_jour8_onSwitch.getText().toString());
            gx_is_jour8_on = gx_is_jour8_onSwitch.isChecked();
            //gx_is_credit_by_objet = Boolean.parseBoolean(gx_is_credit_by_objetSwitch.getText().toString());
            gx_is_credit_by_objet = gx_is_credit_by_objetSwitch.isChecked();
            gx_first_jr_on = gx_first_jr_onEditText.getText().toString();
           // gx_freq_reun_com_cred = gx_freq_reun_com_credEditText.getText().toString();
    gx_freq_reun_com_cred = mySpinnerFreqComCredit.getText().toString();
           // gx_is_rapp_net_msg_cred_on = Boolean.parseBoolean(gx_is_rapp_net_msg_cred_onSwitch.getText().toString());
            gx_is_rapp_net_msg_cred_on =gx_is_rapp_net_msg_cred_onSwitch.isChecked();


    st_GxMtPlafDecAutPCG = GxMtPlafDecAutPCG.getText().toString();
    st_GxNbMoisHistoDef = GxNbMoisHistoDef.getText().toString();

    bool_GxIsPreComGesGuPGC=  GxIsPreComGesGuPGC.isChecked();
    bool_GxIsChefGuichCG=  GxIsChefGuichCG.isChecked();
    bool_GxIsAgentGuichAG=  GxIsAgentGuichAG.isChecked();

    if (gx_is_jour8_onSwitch.isChecked()){
        joursVillage +=jv_jour_1_EditText.getText().toString().trim();
        joursVillage +=";"+jv_jour_2_EditText.getText().toString().trim();
        joursVillage +=";"+jv_jour_3_EditText.getText().toString().trim();
        joursVillage +=";"+jv_jour_4_EditText.getText().toString().trim();
        joursVillage +=";"+jv_jour_5_EditText.getText().toString().trim();
        joursVillage +=";"+jv_jour_6_EditText.getText().toString().trim();
        joursVillage +=";"+jv_jour_7_EditText.getText().toString().trim();
        joursVillage +=";"+jv_jour_8_EditText.getText().toString().trim();
    }



            new AddGuichetAsyncTask().execute();
        } else {
            Toast.makeText(CreateGuichet.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();

        }


    }
    private void findViewsById() {
        gx_date_debutEditText = (EditText) findViewById(R.id.input_txt_dateDebut_guichet);
        gx_date_debutEditText.requestFocus();
        gx_date_debutEditText.setInputType(InputType.TYPE_NULL);




    }

    private void setDateTimeField() {
        gx_date_debutEditText.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();
        gx_date_debut_PickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                gx_date_debutEditText.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));



    }

    @Override
    public void onClick(View v) {
        if(v == gx_date_debutEditText) {
            gx_date_debut_PickerDialog.show();
        }
    }



    /**
     * AsyncTask for adding a movie
     */
    private class AddGuichetAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            addButton.startAnimation() ;// to start animation on button save
            //Display proggress bar
            pDialog = new ProgressDialog(CreateGuichet.this);
            pDialog.setMessage("Adding Guichet. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
           // httpParams.put(KEY_GX_CX_NUMERO, gxCaisse);
            //httpParams.put(KEY_GX_CX_NUMERO, String.valueOf(caisseID));
            httpParams.put(KEY_GX_CX_NUMERO, String.valueOf(MyData.CAISSE_ID));
            httpParams.put(KEY_GX_LOCALITE, gxLocalite);
            httpParams.put(KEY_GX_DENOMINATION, gxDenomination);
            httpParams.put(KEY_GX_DATE_DEBUT, gx_date_debut);
            httpParams.put(KEY_GX_ADRESSE, gx_adresse);
            httpParams.put(KEY_GX_TEL1, gx_tel1);
            httpParams.put(KEY_GX_TEL2, gx_tel2);
            httpParams.put(KEY_GX_TEL3, gx_tel3);
            httpParams.put(KEY_GX_NOM_PCA, gx_nom_pca);
            httpParams.put(KEY_GX_NOM_DG, gx_nom_dg);
            httpParams.put(KEY_GX_IS_FORCER_CLOT, gx_is_forcer_clot.toString());
            httpParams.put(KEY_GX_HEURE_CLOT, gx_heure_clot);
            httpParams.put(KEY_GX_IS_OPER_APRES_CLOT, gx_is_oper_apres_clot.toString());
            httpParams.put(KEY_GX_NBRE_RAPP_BY_JOUR, gx_nbre_rapp_by_jour);
            httpParams.put(KEY_GX_NBRE_JR_AV_RAPP, gx_nbre_jr_av_rapp);
            httpParams.put(KEY_GX_IS_JOUR8_ON, gx_is_jour8_on.toString());
            httpParams.put(KEY_GX_IS_CREDIT_BY_OBJET, gx_is_credit_by_objet.toString());
            httpParams.put(KEY_GX_FIRST_JR_ON, gx_first_jr_on);
            httpParams.put(KEY_GX_FREQ_REUN_COM_CRED, gx_freq_reun_com_cred);
            httpParams.put(KEY_GX_IS_RAPP_NET_MSG_CRED_ON, gx_is_rapp_net_msg_cred_on.toString());


            httpParams.put(KEY_GxMtPlafDecAutPCG, st_GxMtPlafDecAutPCG);
            httpParams.put(KEY_GxNbMoisHistoDef, st_GxNbMoisHistoDef);
            httpParams.put(KEY_GxIsPreComGesGuPGC, bool_GxIsPreComGesGuPGC.toString());
            httpParams.put(KEY_GxIsChefGuichCG, bool_GxIsChefGuichCG.toString());
            httpParams.put(KEY_GxIsAgentGuichAG, bool_GxIsAgentGuichAG.toString());


            httpParams.put(KEY_GxJoursVillage, joursVillage);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_guichet.php", "POST", httpParams);
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
                        Toast.makeText(CreateGuichet.this,
                                "Guichet Ajouté", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(CreateGuichet.this,
                                "Some error occurred while adding Guichet",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}