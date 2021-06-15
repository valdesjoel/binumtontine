package com.example.binumtontine.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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
import android.widget.ArrayAdapter;
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
import com.example.binumtontine.activity.adherent.DemandeCredit;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.example.binumtontine.modele.DemandeCreditModele;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
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

public class UpdateGuichet extends AppCompatActivity implements  View.OnClickListener,  AdapterView.OnItemSelectedListener, SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";


    private static final String KEY_GUICHET_ID = "gx_numero";
    private static final String KEY_GX_CX_NUMERO = "cx_numero";

    private static final String KEY_EV_CAISSE_ID = "ev_caisse_id";
    private static final String KEY_EV_GUICHET_ID = "ev_gx_numero";
    private static final String KEY_EAV_ID = "ev_numero";
    private static final String KEY_EAV_LIBELLE = "ev_libelle";
    private static final String KEY_JvNumero = "JvNumero";
    private static final String KEY_JvDesign = "JvDesign";

    private static final String KEY_GX_LOCALITE = "gx_localite";
    private static final String KEY_GX_DENOMINATION = "gx_denomination";
    private static final String KEY_GX_DATE_DEBUT = "gx_date_debut";
/*
    private static final String KEY_GxNumAgremCobac = "GxNumAgremCobac";
    private static final String KEY_GxDatAgremCobac = "GxDatAgremCobac";
    private static final String KEY_GxNumAgremCNC = "GxNumAgremCNC";
    private static final String KEY_GxDatAgremCNC = "GxDatAgremCNC";*/
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
    private static final String KEY_GX_DEFAULT_EAV_NUMERO = "gx_default_eav_numero";
    private static final String KEY_GxCptCollecteDef = "GxCptCollecteDef";


    private static final String KEY_GxIsPreComGesGuPGC = "GxIsPreComGesGuPGC";
    private static final String KEY_GxIsChefGuichCG = "GxIsChefGuichCG";
    private static final String KEY_GxIsAgentGuichAG = "GxIsAgentGuichAG";
    private static final String KEY_GxMtPlafDecAutPCG = "GxMtPlafDecAutPCG";

    private static final String KEY_GxNbMoisHistoDef = "GxNbMoisHistoDef";
    private static final String KEY_GxIsJrSemInit = "GxIsJrSemInit";

    private TextView headerGuichetTextView;



    private TextView cxTitle;
    private String cxName;
    private CountryCodePicker ccp_phone1;
    private CountryCodePicker ccp_phone2;
    private CountryCodePicker ccp_phone3;
    private EditText editTextCarrierPhone1;
    private EditText editTextCarrierPhone2;
    private EditText editTextCarrierPhone3;
    private TimePickerDialog picker;
    private String guichetId;
    private JRSpinner mySpinnerCaisse; //pour gérer le spinner contenant les caisses
    private JRSpinner mySpinnerLocalite; //pour gérer le spinner contenant les localités
    private JRSpinner mySpinnerFreqComCredit; //pour gérer le spinner contenant les localités
    private EditText gx_denominationEditText;
    private EditText gx_date_debutEditText;
/*
    private EditText GxNumAgremCobac;
    private EditText GxDatAgremCobac;
    private EditText GxNumAgremCNC;
    private EditText GxDatAgremCNC;*/
    private EditText gx_adresseEditText;
    private EditText gx_tel1EditText;
    private EditText gx_tel2EditText;
    private EditText gx_tel3EditText;
    private EditText gx_nom_pcaEditText;
    private EditText gx_nom_dgEditText;
    private Switch gx_is_forcer_clotSwitch;
    private EditText gx_heure_clotEditText;
    private Switch gx_is_oper_apres_clotSwitch;
    private EditText gx_nbre_rapp_by_jourEditText;
    private EditText gx_nbre_jr_av_rappEditText;
    private Switch gx_is_jour8_onSwitch;
    private Switch gx_is_credit_by_objetSwitch;
    private EditText gx_first_jr_onEditText;
    private EditText gx_freq_reun_com_credEditText;
    private Switch gx_is_rapp_net_msg_cred_onSwitch;

    private String gxCaisse;
    private String gxLocalite;
    private String gxDenomination;
    private String gx_date_debut;
/*
    private String GxNumAgremCobac_st;
    private String GxDatAgremCobac_st;
    private String GxNumAgremCNC_st;
    private String GxDatAgremCNC_st;*/
    private String gx_adresse;
    private String gx_tel1;
    private String gx_tel2;
    private String gx_tel3;
    private String gx_nom_pca;
    private String gx_nom_dg;
    private String gx_is_forcer_clot;
    private String gx_heure_clot;
    private String gx_is_oper_apres_clot;
    private String gx_nbre_rapp_by_jour;
    private String gx_nbre_jr_av_rapp;
    private String gx_is_jour8_on;
    private String gx_is_credit_by_objet;
    private String gx_first_jr_on;
    private String gx_freq_reun_com_cred;
    private String gx_is_rapp_net_msg_cred_on;
    private String gxDefaultEavDenomination;
    private String gxDefaultEavForCollecteDenomination;


    private EditText GxMtPlafDecAutPCG;
    private String st_GxMtPlafDecAutPCG;
    private SwitchCompat GxIsPreComGesGuPGC,GxIsChefGuichCG,GxIsAgentGuichAG;
    private String bool_GxIsPreComGesGuPGC, bool_GxIsChefGuichCG,bool_GxIsAgentGuichAG, bool_GxIsJrSemInit;


    private EditText GxNbMoisHistoDef;
    private String st_GxNbMoisHistoDef;

    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Category> defaultEavList;
    private ArrayList<Category> defaultEavForCollecteList;
    private ArrayList<Category> joursVillageList;
    List<Integer> defaultEavListID = new ArrayList<Integer>();
    List<Integer> defaultEavForCollecteListID = new ArrayList<Integer>();
    List<Integer> joursVillageListID = new ArrayList<Integer>();
    private int defaultEavId;
    private int defaultEavForCollecteId;
    private int todayDayId;
    private Spinner spinnerDefaultEAV, spinnerDefaultEAVForCollecte, spinnerJoursVillage;
    private TextView tvCaisse;
    private TextInputLayout textInputLayoutCaisse;
    private LinearLayout LL_default_eav;
    private LinearLayout LL_default_eav_for_collecte;
    /*end manage*/



    private DatePickerDialog gx_date_debut_PickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date
/*
    private DatePickerDialog GxDatAgremCobacPickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date
    private DatePickerDialog GxDatAgremCNCPickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date
*/
    private SimpleDateFormat dateFormatter; //propriété permettant de gérer le format de la date

    private br.com.simplepass.loadingbutton.customViews.CircularProgressButton updateButton;
    private br.com.simplepass.loadingbutton.customViews.CircularProgressButton annulerButton;
    private br.com.simplepass.loadingbutton.customViews.CircularProgressButton deleteButton;
    private int success;
    private ProgressDialog pDialog, pDialog1;
    private LinearLayout block_Switch8jr_semaine_Gx, ll_getTodayDay;
    private Switch SW_GuIsJrSemInit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_guichet);
        Intent intent = getIntent();
        guichetId = intent.getStringExtra(KEY_GUICHET_ID);
        headerGuichetTextView = findViewById(R.id.header_user_guichet);
        headerGuichetTextView.setText("Mise à jour Guichet");

        /* Begin manage country*/
        cxTitle = findViewById(R.id.tv_caisse_name);
        cxName= MyData.CAISSE_NAME.toUpperCase();
        cxTitle.setTypeface(null, Typeface.BOLD);
        cxTitle.setText(cxTitle.getText()+" "+ cxName);

        ccp_phone1 =  findViewById(R.id.ccp_phone1);
        editTextCarrierPhone1 = findViewById(R.id.editText_carrierPhone1);
        ccp_phone1.registerCarrierNumberEditText(editTextCarrierPhone1);
//        ccp_phone1.getFullNumberWithPlus();
        ccp_phone2 =  findViewById(R.id.ccp_phone2);
        editTextCarrierPhone2 = findViewById(R.id.editText_carrierPhone2);
        ccp_phone2.registerCarrierNumberEditText(editTextCarrierPhone2);
//        ccp_phone2.getFullNumberWithPlus();
        ccp_phone3 =  findViewById(R.id.ccp_phone3);
        editTextCarrierPhone3 = findViewById(R.id.editText_carrierPhone3);
        ccp_phone3.registerCarrierNumberEditText(editTextCarrierPhone3);
//        ccp_phone3.getFullNumberWithPlus();

        /* End manage country*/
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();
        setDateTimeField();
        mySpinnerLocalite = findViewById(R.id.spn_my_spinner_localite_guichet);
        mySpinnerLocalite.setItems(getResources().getStringArray(R.array.array_localite)); //this is important, you must set it to set the item list
        mySpinnerLocalite.setTitle("Sélectionner une localité"); //change title of spinner-dialog programmatically
        mySpinnerLocalite.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        mySpinnerLocalite.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position
            }
        });

        mySpinnerFreqComCredit = findViewById(R.id.spn_GuFreqReunComCred);

        mySpinnerFreqComCredit.setItems(getResources().getStringArray(R.array.array_GuFreqReunComCred)); //this is important, you must set it to set the item list
        mySpinnerFreqComCredit.setTitle("Sélectionner la fréquence des réunions"); //change title of spinner-dialog programmatically
        mySpinnerFreqComCredit.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically

        mySpinnerFreqComCredit.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
            }
        });
        gx_denominationEditText =  findViewById(R.id.input_denomination_guichet);
        alreadyUpperCase(gx_denominationEditText);
        gx_date_debutEditText =findViewById(R.id.input_txt_dateDebut_guichet);
        gx_adresseEditText =findViewById(R.id.input_txt_AdresseGx);

        gx_nom_pcaEditText =findViewById(R.id.input_txt_NomPCA_Gx);
        alreadyUpperCase(gx_nom_pcaEditText);
        gx_nom_dgEditText =findViewById(R.id.input_txt_NomDG_Gx);
        alreadyUpperCase(gx_nom_dgEditText);
        gx_is_forcer_clotSwitch =findViewById(R.id.SwitchForcer_la_clotureGx);
        gx_heure_clotEditText =findViewById(R.id.input_txt_heure_cloture_Gx);
        gx_heure_clotEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                picker = new TimePickerDialog(UpdateGuichet.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                gx_heure_clotEditText.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });
        gx_is_oper_apres_clotSwitch = findViewById(R.id.Switch_gx_is_oper_apres_clot);
        gx_nbre_rapp_by_jourEditText = findViewById(R.id.input_txt_Nombre_de_rappel_par_jour);
        gx_nbre_jr_av_rappEditText =findViewById(R.id.input_txt_Nombre_jour_av_rappel);
        gx_is_jour8_onSwitch =findViewById(R.id.Switch8jr_semaine_Gx);
        block_Switch8jr_semaine_Gx =findViewById(R.id.block_Switch8jr_semaine_Gx);
        SW_GuIsJrSemInit =findViewById(R.id.Switch_GuIsJrSemInit);
        ll_getTodayDay =  findViewById(R.id.ll_getTodayDay);
        gx_is_credit_by_objetSwitch =findViewById(R.id.Switch_credit_par_objet_Gx);
        gx_first_jr_onEditText =findViewById(R.id.input_txt_GuFirstJrOn);
        gx_freq_reun_com_credEditText =findViewById(R.id.input_txt_GuFreqReunComCred);
        gx_is_rapp_net_msg_cred_onSwitch =findViewById(R.id.Switch_GuIsRappNetMsgCredOn);

        GxIsPreComGesGuPGC=  findViewById(R.id.SwitchGxIsPreComGesGuPGC);
        GxIsChefGuichCG= findViewById(R.id.SwitchGxIsChefGuichCG);
        GxIsAgentGuichAG= findViewById(R.id.SwitchGxIsAgentGuichAG);
        GxMtPlafDecAutPCG =findViewById(R.id.input_txt_GxMtPlafDecAutPCG);
        GxNbMoisHistoDef = findViewById(R.id.input_txt_GxNbMoisHistoDef);

        /*debut*/
        LL_default_eav = findViewById(R.id.ll_default_eav);
        LL_default_eav.setVisibility(View.VISIBLE);
        LL_default_eav_for_collecte =findViewById(R.id.ll_default_eav_for_collecte);
        LL_default_eav_for_collecte.setVisibility(View.VISIBLE);

        spinnerDefaultEAV = findViewById(R.id.spinner_default_eav);
        spinnerDefaultEAVForCollecte = findViewById(R.id.spinner_default_eav_for_collecte);
        spinnerJoursVillage =findViewById(R.id.spinner_getTodayDay);

        defaultEavList = new ArrayList<Category>();
        defaultEavForCollecteList = new ArrayList<Category>();
        joursVillageList = new ArrayList<Category>();
        // spinner item select listener
        spinnerDefaultEAV.setOnItemSelectedListener(UpdateGuichet.this);
        spinnerDefaultEAVForCollecte.setOnItemSelectedListener(UpdateGuichet.this);
        spinnerJoursVillage.setOnItemSelectedListener(UpdateGuichet.this);
        new FetchGuichetDetailsAsyncTask().execute();
        new GetDefaultEAV().execute();
        new GetGxCptCollecteDefAsyncTask().execute();
        new GetTodayDay().execute();
        /*fin*/


        deleteButton = (CircularProgressButton) findViewById(R.id.btn_delete_guichet);
        deleteButton.setVisibility(View.VISIBLE);
        annulerButton = (CircularProgressButton) findViewById(R.id.btn_clean);
        annulerButton.setVisibility(View.VISIBLE);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete();
            }
        });
        updateButton = (CircularProgressButton) findViewById(R.id.btn_save_guichet);
        updateButton.setText("MODIFIER");
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    updateGuichet();
                } else {
                    Toast.makeText(UpdateGuichet.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                   finish();
                } else {
                    Toast.makeText(UpdateGuichet.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void findViewsById() {
        gx_date_debutEditText = findViewById(R.id.input_txt_dateDebut_guichet);
        gx_date_debutEditText.requestFocus();
        gx_date_debutEditText.setInputType(InputType.TYPE_NULL);
    }

    private void setDateTimeField() {
        gx_date_debutEditText.setOnClickListener(this);
/*
        GxDatAgremCobac.setOnClickListener(this);
        GxDatAgremCNC.setOnClickListener(this);
*/
        Calendar newCalendar = Calendar.getInstance();
        gx_date_debut_PickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                gx_date_debutEditText.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
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

    @Override
    public void onClick(View v) {
        if(v == gx_date_debutEditText) {
            gx_date_debut_PickerDialog.show();
        }/*else if(v == GxDatAgremCobac) {
            GxDatAgremCobacPickerDialog.show();
        } else if(v == GxDatAgremCNC) {
            GxDatAgremCNCPickerDialog.show();
        }*/
    }
    /**
     * Fetches single movie details from the server
     */
    private class FetchGuichetDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateGuichet.this);
            pDialog.setMessage("Loading Guichet Details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
//            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_GUICHET_ID, guichetId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_guichet_details.php", "GET", httpParams);
            Log.e("get_guichet_details", String.valueOf(jsonObject));
            Log.e("httpParams", String.valueOf(httpParams));
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject guichet;
                if (success == 1) {
                    //Parse the JSON response
                    guichet = jsonObject.getJSONObject(KEY_DATA);

                    gxCaisse = guichet.getString(KEY_GX_CX_NUMERO);
                    gxLocalite = (guichet.getString(KEY_GX_LOCALITE));
                    gxDenomination = (guichet.getString(KEY_GX_DENOMINATION));
                    gx_date_debut = guichet.getString(KEY_GX_DATE_DEBUT);
                /*    GxNumAgremCobac_st = guichet.getString(KEY_GxNumAgremCobac);
                    GxDatAgremCobac_st = guichet.getString(KEY_GxDatAgremCobac);
                    GxNumAgremCNC_st = guichet.getString(KEY_GxNumAgremCNC);
                    GxDatAgremCNC_st = guichet.getString(KEY_GxDatAgremCNC);*/
                    gx_adresse = guichet.getString(KEY_GX_ADRESSE);
                    gx_tel1 = guichet.getString(KEY_GX_TEL1);
                    gx_tel2 = guichet.getString(KEY_GX_TEL2);
                    gx_tel3 = guichet.getString(KEY_GX_TEL3);
                    gx_nom_pca = MyData.normalizeSymbolsAndAccents(guichet.getString(KEY_GX_NOM_PCA));
                    gx_nom_dg = MyData.normalizeSymbolsAndAccents(guichet.getString(KEY_GX_NOM_DG));
                    gx_is_forcer_clot = guichet.getString(KEY_GX_IS_FORCER_CLOT);
                    gx_heure_clot = guichet.getString(KEY_GX_HEURE_CLOT);
                    gx_is_oper_apres_clot = guichet.getString(KEY_GX_IS_OPER_APRES_CLOT);
                    gx_nbre_rapp_by_jour = guichet.getString(KEY_GX_NBRE_RAPP_BY_JOUR);
                    gx_nbre_jr_av_rapp = guichet.getString(KEY_GX_NBRE_JR_AV_RAPP);
                    gx_is_jour8_on = guichet.getString(KEY_GX_IS_JOUR8_ON);
                    gx_is_credit_by_objet = guichet.getString(KEY_GX_IS_CREDIT_BY_OBJET);
                    gx_first_jr_on = guichet.getString(KEY_GX_FIRST_JR_ON);
                    gx_freq_reun_com_cred = guichet.getString(KEY_GX_FREQ_REUN_COM_CRED);
                    gx_is_rapp_net_msg_cred_on = guichet.getString(KEY_GX_IS_RAPP_NET_MSG_CRED_ON);
                    gxDefaultEavDenomination = guichet.getString(KEY_GX_DEFAULT_EAV_NUMERO);

                    st_GxMtPlafDecAutPCG = guichet.getString(KEY_GxMtPlafDecAutPCG);
                    st_GxNbMoisHistoDef = guichet.getString(KEY_GxNbMoisHistoDef);

                    bool_GxIsPreComGesGuPGC = guichet.getString(KEY_GxIsPreComGesGuPGC);
                    bool_GxIsChefGuichCG = guichet.getString(KEY_GxIsChefGuichCG);
                    bool_GxIsAgentGuichAG = guichet.getString(KEY_GxIsAgentGuichAG);

                    bool_GxIsJrSemInit = guichet.getString(KEY_GxIsJrSemInit);
//                    gxDefaultEavForCollecteDenomination = guichet.getString(KEY_GxCptCollecteDef);
//

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
//            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    //Populate the Edit Texts once the network activity is finished executing
                    try {
                        mySpinnerLocalite.setText(gxLocalite);
                        gx_denominationEditText.setText(gxDenomination);
                        gx_date_debutEditText.setText(gx_date_debut);
/*
                        GxNumAgremCobac.setText(GxNumAgremCobac_st);
                        GxDatAgremCobac.setText(GxDatAgremCobac_st);
                        GxNumAgremCNC.setText(GxNumAgremCNC_st);
                        GxDatAgremCNC.setText(GxDatAgremCNC_st);*/
                        gx_adresseEditText.setText(gx_adresse);

                        ccp_phone1.setFullNumber(gx_tel1);
                        ccp_phone2.setFullNumber(gx_tel2);
                        ccp_phone3.setFullNumber(gx_tel3);

                        gx_nom_pcaEditText.setText(gx_nom_pca);
                        gx_nom_dgEditText.setText(gx_nom_dg);
                        if (gx_is_forcer_clot.equals("Y")){
                            gx_is_forcer_clotSwitch.setChecked(true);
                        }else{
                            gx_is_forcer_clotSwitch.setChecked(false);
                        }
                        gx_heure_clotEditText.setText(gx_heure_clot);

                        if (gx_is_oper_apres_clot.equals("Y")){
                            gx_is_oper_apres_clotSwitch.setChecked(true);
                        }else{
                            gx_is_oper_apres_clotSwitch.setChecked(false);
                        }
                        gx_nbre_rapp_by_jourEditText.setText(gx_nbre_rapp_by_jour);
                        gx_nbre_jr_av_rappEditText.setText(gx_nbre_jr_av_rapp);
                        if (gx_is_jour8_on.equals("Y")){
                            gx_is_jour8_onSwitch.setChecked(true);
                        }else{
                            gx_is_jour8_onSwitch.setChecked(false);
                        }
                        // Debut gestion des jours du village
                        gx_is_jour8_onSwitch.setClickable(false);
                        // onSwitchButtonClicked(gx_is_jour8_onSwitch);
                        //Fin gestion des jours du village
                        if (gx_is_credit_by_objet.equals("Y")){
                            gx_is_credit_by_objetSwitch.setChecked(true);
                        }else{
                            gx_is_credit_by_objetSwitch.setChecked(false);
                        }
                        gx_first_jr_onEditText.setText(gx_first_jr_on);
                        if (gx_freq_reun_com_cred.equals("H")){
                            mySpinnerFreqComCredit.setText("HEBDOMADAIRE");
                        }else if (gx_freq_reun_com_cred.equals("M")){
                            mySpinnerFreqComCredit.setText("MENSUELLE");
                        }else if (gx_freq_reun_com_cred.equals("T")){
                            mySpinnerFreqComCredit.setText("TRIMESTRIELLE");
                        }
                        if (gx_is_rapp_net_msg_cred_on.equals("Y")){
                            gx_is_rapp_net_msg_cred_onSwitch.setChecked(true);
                        }else{
                            gx_is_rapp_net_msg_cred_onSwitch.setChecked(false);
                        }

                        GxMtPlafDecAutPCG.setText(st_GxMtPlafDecAutPCG);
                        GxNbMoisHistoDef.setText(st_GxNbMoisHistoDef);
                        if (bool_GxIsPreComGesGuPGC.equals("Y")){
                            GxIsPreComGesGuPGC.setChecked(true);
                        }else{
                            GxIsPreComGesGuPGC.setChecked(false);
                        }
                        if (bool_GxIsChefGuichCG.equals("Y")){
                            GxIsChefGuichCG.setChecked(true);
                        }else{
                            GxIsChefGuichCG.setChecked(false);
                        }
                        if (bool_GxIsAgentGuichAG.equals("Y")){
                            GxIsAgentGuichAG.setChecked(true);
                        }else{
                            GxIsAgentGuichAG.setChecked(false);
                        }

                        //Manage SW_GuIsJrSemInit

                        if (bool_GxIsJrSemInit.equals("Y")){
                            SW_GuIsJrSemInit.setChecked(true);
                        }else{
                            SW_GuIsJrSemInit.setChecked(false);
                        }
                        if (bool_GxIsJrSemInit.equals("Y")){
                            SW_GuIsJrSemInit.setClickable(false);
                        }else{
                            onSwitchButtonClicked(SW_GuIsJrSemInit);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * Displays an alert dialogue to confirm the deletion
     */
    private void confirmDelete() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                UpdateGuichet.this);
        alertDialogBuilder.setMessage("Are you sure, you want to delete this Guichet?");
        alertDialogBuilder.setPositiveButton("Delete",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                            //If the user confirms deletion, execute DeleteGuichetAsyncTask
                            new DeleteGuichetAsyncTask().execute();
                        } else {
                            Toast.makeText(UpdateGuichet.this,
                                    "Unable to connect to internet",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

        alertDialogBuilder.setNegativeButton("Cancel", null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * AsyncTask to delete a guichet
     */
    private class DeleteGuichetAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateGuichet.this);
            pDialog.setMessage("Deleting Guichet. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            deleteButton.startAnimation() ;// to start animation on button delete
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Set guichet_id parameter in request
            httpParams.put(KEY_GUICHET_ID, guichetId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "delete_guichet.php", "POST", httpParams);
            try {
                success = jsonObject.getInt(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            deleteButton.startAnimation() ;// to stop animation on button delete
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(UpdateGuichet.this,
                                "Guichet Deleted", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie deletion
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(UpdateGuichet.this,
                                "Some error occurred while deleting guichet",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    /**
     * Checks whether all files are filled. If so then calls UpdateMovieAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void updateGuichet() {
            if (true){
                gxLocalite = mySpinnerLocalite.getText().toString();
                gxDenomination = MyData.normalizeSymbolsAndAccents(gx_denominationEditText.getText().toString());
                gx_date_debut = gx_date_debutEditText.getText().toString();
                gx_adresse = MyData.normalizeSymbolsAndAccents(gx_adresseEditText.getText().toString());
                gx_tel1 = ccp_phone1.getFullNumberWithPlus();
                gx_tel2 = ccp_phone2.getFullNumberWithPlus();
                gx_tel3 = ccp_phone3.getFullNumberWithPlus();

                gx_nom_pca = MyData.normalizeSymbolsAndAccents(gx_nom_pcaEditText.getText().toString());
                gx_nom_dg = MyData.normalizeSymbolsAndAccents(gx_nom_dgEditText.getText().toString());
                if (gx_is_forcer_clotSwitch.isChecked()){
                    gx_is_forcer_clot = "Y";
                }else{
                    gx_is_forcer_clot = "N";
                }
                gx_heure_clot = gx_heure_clotEditText.getText().toString();
                if (gx_is_oper_apres_clotSwitch.isChecked()){
                    gx_is_oper_apres_clot = "Y";
                }else{
                    gx_is_oper_apres_clot = "N";
                }
                gx_nbre_rapp_by_jour = gx_nbre_rapp_by_jourEditText.getText().toString();
                gx_nbre_jr_av_rapp = gx_nbre_jr_av_rappEditText.getText().toString();
                gx_nbre_jr_av_rapp = gx_nbre_jr_av_rappEditText.getText().toString();
                if (gx_is_jour8_onSwitch.isChecked()){
                    gx_is_jour8_on = "Y";
                }else{
                    gx_is_jour8_on = "N";
                }
                if (gx_is_credit_by_objetSwitch.isChecked()){
                    gx_is_credit_by_objet = "Y";
                }else{
                    gx_is_credit_by_objet = "N";
                }
                gx_first_jr_on = gx_first_jr_onEditText.getText().toString();
                //gx_freq_reun_com_cred = gx_freq_reun_com_credEditText.getText().toString();
                gx_freq_reun_com_cred = mySpinnerFreqComCredit.getText().toString();
                if (mySpinnerFreqComCredit.getText().toString().equals("HEBDOMADAIRE")){
                    gx_freq_reun_com_cred = "H";
                }else if (mySpinnerFreqComCredit.getText().toString().equals("MENSUELLE")){
                    gx_freq_reun_com_cred = "M";
                }else if (mySpinnerFreqComCredit.getText().toString().equals("TRIMESTRIELLE")){
                    gx_freq_reun_com_cred = "T";
                }

                if (gx_is_rapp_net_msg_cred_onSwitch.isChecked()){
                    gx_is_rapp_net_msg_cred_on = "Y";
                }else{
                    gx_is_rapp_net_msg_cred_on = "N";
                }

                st_GxMtPlafDecAutPCG = GxMtPlafDecAutPCG.getText().toString();

                if (GxIsPreComGesGuPGC.isChecked()){
                    bool_GxIsPreComGesGuPGC = "Y";
                }else{
                    bool_GxIsPreComGesGuPGC = "N";
                }
                if (GxIsChefGuichCG.isChecked()){
                    bool_GxIsChefGuichCG = "Y";
                }else{
                    bool_GxIsChefGuichCG = "N";
                }
                if (GxIsAgentGuichAG.isChecked()){
                    bool_GxIsAgentGuichAG = "Y";
                }else{
                    bool_GxIsAgentGuichAG = "N";
                }
                if (SW_GuIsJrSemInit.isChecked()){
                    bool_GxIsJrSemInit = "Y";
                }else{
                    bool_GxIsJrSemInit = "N";
                }

                st_GxNbMoisHistoDef = GxNbMoisHistoDef.getText().toString();
            new UpdateGuichet.UpdateMovieAsyncTask().execute();
        } else {
            Toast.makeText(UpdateGuichet.this,
                    "One or more fields left empty!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for updating a movie details
     */

    private class UpdateMovieAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(UpdateGuichet.this);
            pDialog.setMessage("Updating Guichet. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            updateButton.startAnimation() ;// to start animation on button update
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
           // httpParams.put(KEY_GUICHET_ID, guichetId);
            httpParams.put(KEY_GUICHET_ID, String.valueOf(guichetId));
            httpParams.put(KEY_GX_CX_NUMERO, String.valueOf(MyData.CAISSE_ID));
            httpParams.put(KEY_GX_LOCALITE, gxLocalite);
            httpParams.put(KEY_GX_DENOMINATION, gxDenomination);
            httpParams.put(KEY_GX_DATE_DEBUT, gx_date_debut);
/*
            httpParams.put(KEY_GxNumAgremCobac, GxNumAgremCobac.getText().toString().trim());
            httpParams.put(KEY_GxDatAgremCobac, GxDatAgremCobac.getText().toString().trim());
            httpParams.put(KEY_GxNumAgremCNC, GxNumAgremCNC.getText().toString().trim());
            httpParams.put(KEY_GxDatAgremCNC, GxDatAgremCNC.getText().toString().trim());*/
            httpParams.put(KEY_GX_ADRESSE, gx_adresse);
            httpParams.put(KEY_GX_TEL1, gx_tel1);
            httpParams.put(KEY_GX_TEL2, gx_tel2);
            httpParams.put(KEY_GX_TEL3, gx_tel3);
            httpParams.put(KEY_GX_NOM_PCA, gx_nom_pca);
            httpParams.put(KEY_GX_NOM_DG, gx_nom_dg);
            httpParams.put(KEY_GX_IS_FORCER_CLOT, gx_is_forcer_clot);
            httpParams.put(KEY_GX_HEURE_CLOT, gx_heure_clot);
            httpParams.put(KEY_GX_IS_OPER_APRES_CLOT, gx_is_oper_apres_clot);
            httpParams.put(KEY_GX_NBRE_RAPP_BY_JOUR, gx_nbre_rapp_by_jour);
            httpParams.put(KEY_GX_NBRE_JR_AV_RAPP, gx_nbre_jr_av_rapp);
            httpParams.put(KEY_GX_IS_JOUR8_ON, gx_is_jour8_on);
            httpParams.put(KEY_GX_IS_CREDIT_BY_OBJET, gx_is_credit_by_objet);
            httpParams.put(KEY_GX_FIRST_JR_ON, gx_first_jr_on);
            httpParams.put(KEY_GX_FREQ_REUN_COM_CRED, gx_freq_reun_com_cred);
            httpParams.put(KEY_GX_IS_RAPP_NET_MSG_CRED_ON, gx_is_rapp_net_msg_cred_on);
            httpParams.put(KEY_GX_DEFAULT_EAV_NUMERO, String.valueOf(defaultEavId));
            httpParams.put(KEY_GxCptCollecteDef, String.valueOf(defaultEavForCollecteId));//New

            httpParams.put(KEY_GxMtPlafDecAutPCG, st_GxMtPlafDecAutPCG);
            httpParams.put(KEY_GxIsPreComGesGuPGC, bool_GxIsPreComGesGuPGC);
            httpParams.put(KEY_GxIsChefGuichCG, bool_GxIsChefGuichCG);
            httpParams.put(KEY_GxIsAgentGuichAG, bool_GxIsAgentGuichAG);
            httpParams.put(KEY_GxIsJrSemInit, bool_GxIsJrSemInit);
            httpParams.put(KEY_GxNbMoisHistoDef, st_GxNbMoisHistoDef);

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "update_guichet.php", "POST", httpParams);
            try {
                success = jsonObject.getInt(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            updateButton.startAnimation() ;// to stop animation on button update
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(UpdateGuichet.this,
                                "Guichet Updated", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        finish();

                    } else {
                        Toast.makeText(UpdateGuichet.this,
                                "Some error occurred while updating Guichet",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
    /**
     * Adding spinner data
     * */
    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();

        //tvCaisse.setText("");

        for (int i = 0; i < defaultEavList.size(); i++) {
            lables.add(defaultEavList.get(i).getName());//recupère les noms
            defaultEavListID.add(defaultEavList.get(i).getId()); //recupère les Id
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(UpdateGuichet.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerDefaultEAV.setAdapter(spinnerAdapter);

        //Make uxCaisseDenomination as default Item in caisseList spinner
        if (gxDefaultEavDenomination!=null){
            spinnerDefaultEAV.setSelection(spinnerAdapter.getPosition(gxDefaultEavDenomination));
        }

    }
    /**
     * Adding spinner data for EAV Collecte
     * */
    private void populateSpinnerEAVCollecte() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < defaultEavForCollecteList.size(); i++) {
            lables.add(defaultEavForCollecteList.get(i).getName());//recupère les noms
            defaultEavForCollecteListID.add(defaultEavForCollecteList.get(i).getId()); //recupère les Id
        }
        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(UpdateGuichet.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerDefaultEAVForCollecte.setAdapter(spinnerAdapter);

        //Make uxCaisseDenomination as default Item in caisseList spinner
        if (gxDefaultEavForCollecteDenomination!=null){
            spinnerDefaultEAVForCollecte.setSelection(spinnerAdapter.getPosition(gxDefaultEavForCollecteDenomination));
        }

    }
    /**
     * Adding spinner data
     * */
    private void populateSpinnerJoursVillage() {
        List<String> lables = new ArrayList<String>();

        //tvCaisse.setText("");



        for (int i = 0; i < joursVillageList.size(); i++) {
            lables.add(joursVillageList.get(i).getName());//recupère les noms
            joursVillageListID.add(joursVillageList.get(i).getId()); //recupère les Id
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(UpdateGuichet.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerJoursVillage.setAdapter(spinnerAdapter);

        //Make uxCaisseDenomination as default Item in caisseList spinner
//        if (gxDefaultEavDenomination!=null){
//            spinnerJoursVillage.setSelection(spinnerAdapter.getPosition(gxDefaultEavDenomination));
//        }

    }

    /**
     * Fetches the details adherent who want to make demande credit from the server
     */
    private class GetGxCptCollecteDefAsyncTask extends AsyncTask<String, String, String> {
        int successGetFrais;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            /*pDialogDmdeCredit = new ProgressDialog(DemandeCredit.this);
            pDialogDmdeCredit.setMessage("Please wait...");
            pDialogDmdeCredit.setIndeterminate(false);
            pDialogDmdeCredit.setCancelable(false);
            pDialogDmdeCredit.show();*/
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_GUICHET_ID, guichetId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_GxCptCollecteDef.php", "GET", httpParams);
            Log.e("get_GxCptCollecteDef", jsonObject+"");
            try {
                successGetFrais = jsonObject.getInt(KEY_SUCCESS);
                JSONObject movies;
                if (successGetFrais == 1) {
                    movies = jsonObject.getJSONObject(KEY_DATA);
                    gxDefaultEavForCollecteDenomination = movies.getString(KEY_GxCptCollecteDef);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (successGetFrais ==1){
                        try {
                            populateSpinnerEAVCollecte();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
        }

    }
    /**
     * Async task to get all EAV on a current guichet for select default's eav
     * */
    private class GetDefaultEAV extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpdateGuichet.this);
            pDialog.setMessage("Fetching EAV's list..");
            pDialog.setCancelable(false);
            //pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();

            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair(KEY_EV_GUICHET_ID, guichetId));
            httpParams.add(new BasicNameValuePair(KEY_EV_CAISSE_ID, String.valueOf(MyData.CAISSE_ID)));
            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_eav_by_guichet.php", ServiceHandler.GET, httpParams);


            Log.e("httpParams: ", "> " + httpParams+"");
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
                            defaultEavList.add(cat);
                            defaultEavForCollecteList.add(cat);
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
            /*if (pDialog.isShowing())
                pDialog.dismiss();*/
            populateSpinner();

        }

    }
    /**
     * Async task to get all EAV on a current guichet for select default's eav
     * */
    private class GetTodayDay extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog1 = new ProgressDialog(UpdateGuichet.this);
            pDialog1.setMessage("Chargement..");
            pDialog1.setCancelable(false);
            //pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();

            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair(KEY_EV_GUICHET_ID, guichetId));
            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_jours_village_by_guichet.php", ServiceHandler.GET, httpParams);


            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj
                                .getJSONArray(KEY_DATA);

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category cat = new Category(catObj.getInt(KEY_JvNumero),
                                    catObj.getString(KEY_JvDesign));
                            joursVillageList.add(cat);
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
            if (pDialog1.isShowing())
                pDialog1.dismiss();
            populateSpinnerJoursVillage();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        int idSpinner = parent.getId();
        switch (idSpinner) {
            case R.id.spinner_default_eav:
                // your stuff here
                defaultEavId = defaultEavListID.get(position);//pour recuperer l'ID de la caisse selectionnée

                break;
            case R.id.spinner_getTodayDay:
                // your stuff here
                todayDayId = joursVillageListID.get(position);//pour recuperer l'ID du jour selectionné
                break;
            case R.id.spinner_default_eav_for_collecte:
                // your stuff here
                defaultEavForCollecteId = defaultEavForCollecteListID.get(position);//pour recuperer l'ID de l'eav  selectionné
                break;
        }




    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }
}
