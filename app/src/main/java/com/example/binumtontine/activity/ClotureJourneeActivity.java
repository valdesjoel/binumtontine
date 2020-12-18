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
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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

import androidx.appcompat.app.AlertDialog;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class ClotureJourneeActivity extends AppCompatActivity implements  View.OnClickListener, AdapterView.OnItemSelectedListener, SERVER_ADDRESS {
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
    private static final String KEY_total_charge = "total_charge";
    private static final String KEY_total_produit = "total_produit";
    private static final String KEY_total_operation = "total_operation";
    private static final String KEY_cx_denomination = "cx_denomination";
    private NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
    /* end*/
    private static final String KEY_EAV_ID = "ev_numero";
    private static final String KEY_EAV_LIBELLE = "ev_libelle";

    private static final String KEY_CV_PRODUIT = "CvProduit";
    private static final String KEY_CV_MEMBRE = "CvMembre";
    private static final String KEY_CgLastSolde = "CgLastSolde";
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
    private static final String KEY_TAUX = "Taux";
    private static final String KEY_LIBELLE_PRODUIT = "Libelle";
    private static final String KEY_ADHERENT_NOM = "AdNom";
    private static final String KEY_ADHERENT_PRENOM = "AdPrenom";
    private static final String KEY_ADHERENT_NUM_MANUEL = "AdNumManuel";
    private static final String KEY_ADHERENT_CODE = "AdCode";

    private static final String KEY_HEADER_ACTIVITY_CONSULTER_COMPTE = "tvHeaderActivityConsulterCompte";
    private static final String KEY_HEADER_LAYOUT_CONSULTER_COMPTE = "tvHeaderLayoutConsulterCompte";
    private static final String KEY_TYPE_COMPTE = "TypeCompte";

    private String headerActivity = "CONSULTATION DE COMPTE";
    private String headerLayout = "Consultez votre compte";
    private String typeCompte = "";
    private String total_operation = "";
    private String total_operation_bis = "";



    private static String STRING_EMPTY = "";

    private EditText SoldeReelEditText;
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
    private TextView tv_total_billetage;
    private TextView tv_montant_ecart;
    private TextView tv_totalite_piece_1, tv_totalite_piece_5, tv_totalite_piece_10, tv_totalite_piece_25,tv_totalite_piece_50;
    private TextView tv_totalite_piece_100, tv_totalite_piece_500;
    private TextView tv_totalite_10000, tv_totalite_5000, tv_totalite_2000, tv_totalite_1000,tv_totalite_500;
    private EditText et_nombre_10000;
    private EditText et_nombre_5000;
    private EditText et_nombre_2000;
    private EditText et_nombre_1000;
    private EditText et_nombre_500;
    private EditText et_nombre_piece_500;
    private EditText et_nombre_piece_100;
    private EditText et_nombre_piece_50;
    private EditText et_nombre_piece_25;
    private EditText et_nombre_piece_10;
    private EditText et_nombre_piece_5;
    private EditText et_nombre_piece_1;
    private double MontantReel=0.0;
    private double TotalBilletge=0.0;
    private double soldePiece1=0.0;
    private double soldePiece5=0.0;
    private double soldePiece10=0.0;
    private double soldePiece25=0.0;
    private double soldePiece50=0.0;
    private double soldePiece100=0.0;
    private double soldePiece500=0.0;
    private double solde10000=0.0;
    private double solde5000=0.0;
    private double solde2000=0.0;
    private double solde1000=0.0;
    private double solde500=0.0;
    private boolean isMontantEcartNull =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloture_journee);


        Intent intent = getIntent();
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
        tv_total = (TextView) findViewById(R.id.tv_solde_theorique);
        tv_total_billetage = (TextView) findViewById(R.id.tv_total_billetage);
        tv_montant_ecart = (TextView) findViewById(R.id.tv_montant_ecart);

        SoldeReelEditText = (EditText) findViewById(R.id.input_txt_solde_reel);
        NumDossierEditText = (EditText) findViewById(R.id.input_txt_commentaires);

        //EditText for get nombre billet and pieces
        et_nombre_10000 = (EditText) findViewById(R.id.et_nombre_10000);
        et_nombre_5000 = (EditText) findViewById(R.id.tv_nombre_5000);
        et_nombre_2000 = (EditText) findViewById(R.id.tv_nombre_2000);
        et_nombre_1000 = (EditText) findViewById(R.id.tv_nombre_1000);
        et_nombre_500 = (EditText) findViewById(R.id.tv_nombre_500);
        et_nombre_piece_500 = (EditText) findViewById(R.id.tv_nombre_piece_500);
        et_nombre_piece_100 = (EditText) findViewById(R.id.tv_nombre_piece_100);
        et_nombre_piece_50 = (EditText) findViewById(R.id.tv_nombre_piece_50);
        et_nombre_piece_25 = (EditText) findViewById(R.id.tv_nombre_piece_25);
        et_nombre_piece_10 = (EditText) findViewById(R.id.tv_nombre_piece_10);
        et_nombre_piece_5 = (EditText) findViewById(R.id.tv_nombre_piece_5);
        et_nombre_piece_1 = (EditText) findViewById(R.id.tv_nombre_piece_1);

        //TextView for set total billet and pieces
        tv_totalite_10000 = (TextView) findViewById(R.id.tv_totalite_10000);
        tv_totalite_5000 = (TextView) findViewById(R.id.tv_totalite_5000);
        tv_totalite_2000 = (TextView) findViewById(R.id.tv_totalite_2000);
        tv_totalite_1000 = (TextView) findViewById(R.id.tv_totalite_1000);
        tv_totalite_500 = (TextView) findViewById(R.id.tv_totalite_500);
        tv_totalite_piece_500 = (TextView) findViewById(R.id.tv_totalite_piece_500);
        tv_totalite_piece_100 = (TextView) findViewById(R.id.tv_totalite_piece_100);
        tv_totalite_piece_50 = (TextView) findViewById(R.id.tv_totalite_piece_50);
        tv_totalite_piece_25 = (TextView) findViewById(R.id.tv_totalite_piece_25);
        tv_totalite_piece_10 = (TextView) findViewById(R.id.tv_totalite_piece_10);
        tv_totalite_piece_5 = (TextView) findViewById(R.id.tv_totalite_piece_5);
        tv_totalite_piece_1 = (TextView) findViewById(R.id.tv_totalite_piece_1);

        defaultFormat.setCurrency(Currency.getInstance("XAF"));
        new GetSituationGuichetAsyncTask().execute();

        SoldeReelEditText.addTextChangedListener(MyData.onTextChangedListener(SoldeReelEditText));




        try {
            onEditTextClicked(SoldeReelEditText);
            onEditTextClicked(et_nombre_10000);
            onEditTextClicked(et_nombre_5000);
            onEditTextClicked(et_nombre_2000);
            onEditTextClicked(et_nombre_1000);
            onEditTextClicked(et_nombre_500);

            onEditTextClicked(et_nombre_piece_500);
            onEditTextClicked(et_nombre_piece_100);
            onEditTextClicked(et_nombre_piece_50);
            onEditTextClicked(et_nombre_piece_25);
            onEditTextClicked(et_nombre_piece_10);
            onEditTextClicked(et_nombre_piece_5);
            onEditTextClicked(et_nombre_piece_1);
        }catch (Exception e){
            e.printStackTrace();
            Log.e("Memory exceptions","exceptions"+e);
            return ;
        }


  /*      compteId = intent.getStringExtra(KEY_COMPTE_ID);
        dateCreation = intent.getStringExtra(KEY_DATE_H_CREE);
        compteSolde = intent.getStringExtra(KEY_MONTANT_COMPTE);
        taux = intent.getStringExtra(KEY_TAUX);
        typeCompte = intent.getStringExtra(KEY_TYPE_COMPTE);
        if (typeCompte.equals("EAV")){
            typeCompte = "Epargne à vue";
        }else if (typeCompte.equals("EAP")){
            typeCompte = "Epargne à périodicité";
        }else if (typeCompte.equals("EAT")){
            typeCompte = "Epargne à terme";
        }

        libelleProduit = intent.getStringExtra(KEY_LIBELLE_PRODUIT);
//        libelleProduit = MyData.LIBELLE_PRODUIT_CPTE_COURANT;

        if (typeCompte.equals("DECOUVERT SIMPLE") || typeCompte.equals("AVANCE SPECIALE")){
                    libelleProduit = MyData.LIBELLE_PRODUIT_CPTE_COURANT;
        }
        Bundle bundle = intent.getExtras();
        adherent = (Adherent) bundle.getSerializable(KEY_ADHERENT);
        adNom = adherent.getAdNom();
        adPrenom = adherent.getAdPrenom();
        adNumManuel = adherent.getAdNumManuel();
        adCode = adherent.getAdCode();

        tvHeaderActivityConsulterCompte = (TextView) findViewById(R.id.header_consulter_compte);
        tvHeaderLayoutConsulterCompte = (TextView) findViewById(R.id.header_situation_guichet);
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
        tvTypeCompte = (TextView) findViewById(R.id.tv_type_compte_adherent);
        tvTypeCompte.setText(typeCompte);
        tvTaux = (TextView) findViewById(R.id.tv_taux_compte_adherent);
        tvTaux.setText(taux+" %");
//manage header activity and layout
        headerActivity = intent.getStringExtra(KEY_HEADER_ACTIVITY_CONSULTER_COMPTE);
        headerLayout = intent.getStringExtra(KEY_HEADER_LAYOUT_CONSULTER_COMPTE);
        tvHeaderActivityConsulterCompte.setText(headerActivity);
        tvHeaderLayoutConsulterCompte.setText(headerLayout);
        //end manage header activity and layout

        tvLibelleProduit = (TextView) findViewById(R.id.tv_libelle_produit_adherent);
        tvLibelleProduit.setText(libelleProduit);

        spinnerListEAV = (Spinner) findViewById(R.id.spn_mode_paiement);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
*/
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
                    Toast.makeText(ClotureJourneeActivity.this,
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
                    Toast.makeText(ClotureJourneeActivity.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });


    }

    /**
     * To manage Edit Text
     * @param view
     */
    public void onEditTextClicked(View view) {
//        boolean checked1 = ((RadioButton) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        try {
            switch(view.getId()) {

                case R.id.input_txt_solde_reel:
//                 str = "10000";
                    SoldeReelEditText.addTextChangedListener(new TextWatcher() {

                        public void afterTextChanged(Editable s) {

                            // you can call or do what you want with your EditText here
//                SoldeReelEditText.addTextChangedListener(MyData.onTextChangedListener(SoldeReelEditText));
//                tv_montant_ecart.setText(defaultFormat.format((parseDouble(total_operation_bis) - parseDouble(SoldeReelEditText.getText().toString()))));
                            eavDepotMin = SoldeReelEditText.getText().toString().replaceAll(",", "").trim();
                            try {
//                                tv_montant_ecart.setText(defaultFormat.format(parseDouble(total_operation_bis) - parseDouble(eavDepotMin))+"");
                                tv_montant_ecart.setText(defaultFormat.format(parseDouble(eavDepotMin) - parseDouble(total_operation_bis))+"");
                                if((parseDouble(eavDepotMin) - parseDouble(total_operation_bis))<0){
                                    tv_montant_ecart.setBackgroundColor(Color.RED);
                                }else if((parseDouble(eavDepotMin) - parseDouble(total_operation_bis))>0){
                                    tv_montant_ecart.setBackgroundColor(Color.rgb(255,165,0));

                                }else{
                                    tv_montant_ecart.setBackgroundColor(Color.GREEN);
                                    isMontantEcartNull = true;
                                }
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                                eavDepotMin="0.0";
                                SoldeReelEditText.setText("0,0");
                            } finally {

                            }

                            // yourEditText...
//                MontantReel = parseDouble(SoldeReelEditText.getText().toString()+"");
                            MontantReel = parseDouble(eavDepotMin);
                        }

                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    });
                    break;
                case R.id.et_nombre_10000:
//                 str = "10000";
                    et_nombre_10000.addTextChangedListener(new TextWatcher() {

                        public void afterTextChanged(Editable s) {

                            // you can call or do what you want with your EditText here
//                SoldeReelEditText.addTextChangedListener(MyData.onTextChangedListener(SoldeReelEditText));
//                tv_montant_ecart.setText(defaultFormat.format((parseDouble(total_operation_bis) - parseDouble(SoldeReelEditText.getText().toString()))));
                            if ((et_nombre_10000.getText().toString().trim()).equals("")){


                                tv_totalite_10000.setText(defaultFormat.format(parseDouble("0")));
//                                et_nombre_10000.setText("0");
                                solde10000 = parseDouble("0")*10000;
                                sumBilletage();
                            }else {

                                tv_totalite_10000.setText((parseDouble(et_nombre_10000.getText().toString().trim())*10000)+"");
                                tv_totalite_10000.setText(defaultFormat.format(parseDouble(tv_totalite_10000.getText().toString()+"")));
                                solde10000 = parseDouble(et_nombre_10000.getText().toString().trim())*10000;
                                sumBilletage();
                            }
                            // yourEditText...


                        }

                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    });
                    break;
                case R.id.tv_nombre_5000:
                    et_nombre_5000.addTextChangedListener(new TextWatcher() {

                        public void afterTextChanged(Editable s) {

                            // you can call or do what you want with your EditText here
                            if ((et_nombre_5000.getText().toString().trim()).equals("")){

//                                tv_totalite_10000.setText((parseDouble(et_nombre_10000.getText().toString().trim())*10000)+"");
                                tv_totalite_5000.setText(defaultFormat.format(parseDouble("0")));
                                solde5000 = parseDouble("0")*5000;
                                sumBilletage();
                            }else {

                                tv_totalite_5000.setText((parseDouble(et_nombre_5000.getText().toString().trim())*5000)+"");
                                tv_totalite_5000.setText(defaultFormat.format(parseDouble(tv_totalite_5000.getText().toString()+"")));
                                solde5000 = parseDouble(et_nombre_5000.getText().toString().trim())*5000;
                                sumBilletage();
                            }

                            // yourEditText...

                        }

                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    });

                    break;
                case R.id.tv_nombre_2000:
                    et_nombre_2000.addTextChangedListener(new TextWatcher() {

                        public void afterTextChanged(Editable s) {

                            // you can call or do what you want with your EditText here
                            if ((et_nombre_2000.getText().toString().trim()).equals("")){

                                tv_totalite_2000.setText(defaultFormat.format(parseDouble("0")));
                                solde2000 = parseDouble("0")*2000;
                                sumBilletage();
                            }else {

                                tv_totalite_2000.setText((parseDouble(et_nombre_2000.getText().toString().trim())*2000)+"");
                                tv_totalite_2000.setText(defaultFormat.format(parseDouble(tv_totalite_2000.getText().toString()+"")));
                                solde2000 = parseDouble(et_nombre_2000.getText().toString().trim())*2000;
                                sumBilletage();
                            }

                            // yourEditText...
                        }

                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    });

                    break;
                case R.id.tv_nombre_1000:
                    et_nombre_1000.addTextChangedListener(new TextWatcher() {

                        public void afterTextChanged(Editable s) {

                            // you can call or do what you want with your EditText here
                            if ((et_nombre_1000.getText().toString().trim()).equals("")){

                                tv_totalite_1000.setText(defaultFormat.format(parseDouble("0")));
                                solde1000 = parseDouble("0")*1000;
                                sumBilletage();
                            }else {

                                tv_totalite_1000.setText((parseDouble(et_nombre_1000.getText().toString().trim())*1000)+"");
                                tv_totalite_1000.setText(defaultFormat.format(parseDouble(tv_totalite_1000.getText().toString()+"")));
                                solde1000 = parseDouble(et_nombre_1000.getText().toString().trim())*1000;
                                sumBilletage();
                            }


                            // yourEditText...
                        }

                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    });

                    break;
                case R.id.tv_nombre_500:
                    et_nombre_500.addTextChangedListener(new TextWatcher() {

                        public void afterTextChanged(Editable s) {

                            // you can call or do what you want with your EditText here
                            if ((et_nombre_500.getText().toString().trim()).equals("")){

                                tv_totalite_500.setText(defaultFormat.format(parseDouble("0")));
                                solde500 = parseDouble("0")*500;
                                sumBilletage();
                            }else {

                                tv_totalite_500.setText((parseDouble(et_nombre_500.getText().toString().trim())*500)+"");
                                tv_totalite_500.setText(defaultFormat.format(parseDouble(tv_totalite_500.getText().toString()+"")));
                                solde500 = parseDouble(et_nombre_500.getText().toString().trim())*500;
                                sumBilletage();
                            }
                            // yourEditText...


                        }

                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    });

                    break;
                case R.id.tv_nombre_piece_500:
                    et_nombre_piece_500.addTextChangedListener(new TextWatcher() {

                        public void afterTextChanged(Editable s) {

                            // you can call or do what you want with your EditText here

                            if ((et_nombre_piece_500.getText().toString().trim()).equals("")){

                                tv_totalite_piece_500.setText(defaultFormat.format(parseDouble("0")));
                                soldePiece500 = parseDouble("0")*500;
                                sumBilletage();
                            }else {

                                tv_totalite_piece_500.setText((parseDouble(et_nombre_piece_500.getText().toString().trim())*500)+"");
                                tv_totalite_piece_500.setText(defaultFormat.format(parseDouble(tv_totalite_piece_500.getText().toString()+"")));
                                soldePiece500 = parseDouble(et_nombre_piece_500.getText().toString().trim())*500;
                                sumBilletage();
                            }


                            // yourEditText...
                        }

                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    });

                    break;
                case R.id.tv_nombre_piece_100:
                    et_nombre_piece_100.addTextChangedListener(new TextWatcher() {

                        public void afterTextChanged(Editable s) {

                            // you can call or do what you want with your EditText here
                            if ((et_nombre_piece_100.getText().toString().trim()).equals("")){

                                tv_totalite_piece_100.setText(defaultFormat.format(parseDouble("0")));
                                soldePiece100 = parseDouble("0")*100;
                                sumBilletage();
                            }else {

                                tv_totalite_piece_100.setText((parseDouble(et_nombre_piece_100.getText().toString().trim())*100)+"");
                                tv_totalite_piece_100.setText(defaultFormat.format(parseDouble(tv_totalite_piece_100.getText().toString()+"")));
                                soldePiece100 = parseDouble(et_nombre_piece_100.getText().toString().trim())*100;
                                sumBilletage();
                            }

                            // yourEditText...
                        }

                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    });

                    break;
                case R.id.tv_nombre_piece_50:
                    et_nombre_piece_50.addTextChangedListener(new TextWatcher() {

                        public void afterTextChanged(Editable s) {

                            // you can call or do what you want with your EditText here
                            if ((et_nombre_piece_50.getText().toString().trim()).equals("")){

                                tv_totalite_piece_50.setText(defaultFormat.format(parseDouble("0")));
                                soldePiece50 = parseDouble("0")*50;
                                sumBilletage();
                            }else {

                                tv_totalite_piece_50.setText((parseDouble(et_nombre_piece_50.getText().toString().trim())*50)+"");
                                tv_totalite_piece_50.setText(defaultFormat.format(parseDouble(tv_totalite_piece_50.getText().toString()+"")));
                                soldePiece50 = parseDouble(et_nombre_piece_50.getText().toString().trim())*50;
                                sumBilletage();
                            }

                            // yourEditText...
                        }

                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    });

                    break;
                case R.id.tv_nombre_piece_25:
                    et_nombre_piece_25.addTextChangedListener(new TextWatcher() {

                        public void afterTextChanged(Editable s) {

                            // you can call or do what you want with your EditText here
                            if ((et_nombre_piece_25.getText().toString().trim()).equals("")){

                                tv_totalite_piece_25.setText(defaultFormat.format(parseDouble("0")));
                                soldePiece25 = parseDouble("0")*25;
                                sumBilletage();
                            }else {

                                tv_totalite_piece_25.setText((parseDouble(et_nombre_piece_25.getText().toString().trim())*25)+"");
                                tv_totalite_piece_25.setText(defaultFormat.format(parseDouble(tv_totalite_piece_25.getText().toString()+"")));
                                soldePiece25 = parseDouble(et_nombre_piece_25.getText().toString().trim())*25;
                                sumBilletage();
                            }

                            // yourEditText...
                        }

                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    });

                    break;
                case R.id.tv_nombre_piece_10:
                    et_nombre_piece_10.addTextChangedListener(new TextWatcher() {

                        public void afterTextChanged(Editable s) {

                            // you can call or do what you want with your EditText here
                            if ((et_nombre_piece_10.getText().toString().trim()).equals("")){

                                tv_totalite_piece_10.setText(defaultFormat.format(parseDouble("0")));
                                soldePiece10 = parseDouble("0")*10;
                                sumBilletage();
                            }else {

                                tv_totalite_piece_10.setText((parseDouble(et_nombre_piece_10.getText().toString().trim())*10)+"");
                                tv_totalite_piece_10.setText(defaultFormat.format(parseDouble(tv_totalite_piece_10.getText().toString()+"")));
                                soldePiece10 = parseDouble(et_nombre_piece_10.getText().toString().trim())*10;
                                sumBilletage();
                            }

                            // yourEditText...
                        }

                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    });

                    break;
                case R.id.tv_nombre_piece_5:
                    et_nombre_piece_5.addTextChangedListener(new TextWatcher() {

                        public void afterTextChanged(Editable s) {

                            // you can call or do what you want with your EditText here
                            if ((et_nombre_piece_5.getText().toString().trim()).equals("")){

                                tv_totalite_piece_5.setText(defaultFormat.format(parseDouble("0")));
                                soldePiece5 = parseDouble("0")*5;
                                sumBilletage();
                            }else {

                                tv_totalite_piece_5.setText((parseDouble(et_nombre_piece_5.getText().toString().trim())*5)+"");
                                tv_totalite_piece_5.setText(defaultFormat.format(parseDouble(tv_totalite_piece_5.getText().toString()+"")));
                                soldePiece5 = parseDouble(et_nombre_piece_5.getText().toString().trim())*5;
                                sumBilletage();
                            }

                            // yourEditText...
                        }

                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    });

                    break;
                case R.id.tv_nombre_piece_1:
                    et_nombre_piece_1.addTextChangedListener(new TextWatcher() {

                        public void afterTextChanged(Editable s) {

                            // you can call or do what you want with your EditText here

                            if ((et_nombre_piece_1.getText().toString().trim()).equals("")){

                                tv_totalite_piece_1.setText(defaultFormat.format(parseDouble("0")));
                                soldePiece1 = parseDouble("0")*1;
                                sumBilletage();
                            }else {

                                tv_totalite_piece_1.setText((parseDouble(et_nombre_piece_1.getText().toString().trim())*1)+"");
                                tv_totalite_piece_1.setText(defaultFormat.format(parseDouble(tv_totalite_piece_1.getText().toString()+"")));
                                soldePiece1 = parseDouble(et_nombre_piece_1.getText().toString().trim())*1;
                                sumBilletage();
                            }

                            // yourEditText...
                        }

                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    });

                    break;


            }


//        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), TotalBilletge+"", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
            Log.e("Memory exceptions","exceptions"+e);
            return ;
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
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(ClotureJourneeActivity.this,
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
            pDialog = new ProgressDialog(ClotureJourneeActivity.this);
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
                        maSituationGuichet.setCharges(defaultFormat.format(parseDouble(movies.getString(KEY_total_charge))));
                        maSituationGuichet.setProduits(defaultFormat.format(parseDouble(movies.getString(KEY_total_produit))));
                    total_operation = defaultFormat.format(parseDouble(movies.getString(KEY_total_operation)));
                    total_operation_bis = movies.getString(KEY_total_operation);

//                        eavList.add(cat);


//                    }


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
            pDialogFetchProduitEavList = new ProgressDialog(ClotureJourneeActivity.this);
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
    public void avertissement() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Récapitulatif !")
                .setMessage("Montant Démarrage: " + tv_montant_demarrage.getText().toString()+
                "\nTotal Dépôt: " + tv_depot.getText().toString()+
                "\nTotal Retrait: " + tv_retrait.getText().toString()+
                "\nSolde Théorique: " + tv_total.getText().toString()+
                "\nSolde Réel: " + SoldeReelEditText.getText().toString().replaceAll(",", "").trim() +
                "\nMontant écart: " + tv_montant_ecart.getText().toString()+
                "\nTotal Billetage: " + tv_total_billetage.getText().toString()+
                        "\n\t\t Etes-vous sûr de vouloir fermer cette journée ?")
                .setNegativeButton("Non", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
                    }

                })
                .setPositiveButton("Oui", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new ClotureJourneetAsyncTask().execute();
                    }

                })
                .show();
    }
    public void notificationCloture() {
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatterHeure = new SimpleDateFormat("HH:mm:ss");
        String todayString = formatter.format(todayDate);
        String todayStringHeure = formatterHeure.format(todayDate);
        //todayDate.getTime();
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Clôture effectuée avec succès !")
                .setMessage("La journée du " + tv_jour_ouvre.getText().toString()+
                "\na été clôturée avec succès ce  " + todayString+ " à "+todayStringHeure
               )
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//
//                        finish();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity_NEW.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("LOGOUT", true);
                        startActivity(intent);
                    }

                })
                .show();
    }
    public void notificationBilletage() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Billetage incorrect !")
                .setMessage("Le total du billetage est différent du solde réel!")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SoldeReelEditText.setError("Le solde réel doit correspondre au total du billetage");
                        SoldeReelEditText.requestFocus();
                    }

                })
                .show();
    }
    public void notificationObservations() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Renseignez le champ Observations ")
                .setMessage("Donnez vos observations SVP!")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NumDossierEditText.setError("Donnez vos observations SVP!");
                        NumDossierEditText.requestFocus();
                    }

                })
                .show();
    }
    public void notificationChampsVides() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Renseignez le solde réel ")
                .setMessage("Veuillez renseigner le solde réel!")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SoldeReelEditText.setError("Veuillez renseigner le solde réel SVP!");
                        SoldeReelEditText.requestFocus();
                    }

                })
                .show();
    }

    /**
     * Checks whether all files are filled. If so then calls AddEavAdherentAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addEavAdherent() {
        if (!STRING_EMPTY.equals(SoldeReelEditText.getText().toString().trim()) &&
                (isMontantEcartNull || !STRING_EMPTY.equals(NumDossierEditText.getText().toString().trim())) &&
                MontantReel== TotalBilletge
                 ) {
            eavDepotMin = SoldeReelEditText.getText().toString();
            adNumDossier = NumDossierEditText.getText().toString();

            avertissement();

        }else if (MontantReel != TotalBilletge) {
            notificationBilletage();

//        }else if (STRING_EMPTY.equals(NumDossierEditText.getText().toString().trim())) {
        }else if (!isMontantEcartNull) {
            notificationObservations();
//            notificationCloture();


        }else{
            notificationChampsVides();
        }


    }
    private void sumBilletage(){
        TotalBilletge = 0.0;
        TotalBilletge += solde10000;
        TotalBilletge += solde5000;
        TotalBilletge += solde2000;
        TotalBilletge += solde1000;
        TotalBilletge += solde500;
        TotalBilletge += soldePiece500;
        TotalBilletge += soldePiece100;
        TotalBilletge += soldePiece50;
        TotalBilletge += soldePiece25;
        TotalBilletge += soldePiece10;
        TotalBilletge += soldePiece5;
        TotalBilletge += soldePiece1;
        tv_total_billetage.setText(defaultFormat.format(TotalBilletge));
    }

    /**
     * AsyncTask for close day
     */
    private class ClotureJourneetAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(ClotureJourneeActivity.this);
            pDialog.setMessage("Fermeture de la journée en cours. Veuillez patienter...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
//            new SweetAlertDialog(ClotureJourneeActivity.this, SweetAlertDialog.SUCCESS_TYPE)
//                    .setTitleText("Good job!")
//                    .setContentText("You clicked the button!")
//                    .show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
           // httpParams.put(KEY_EAV_ID, uxGuichetId);

//            httpParams.put(KEY_CV_NUMERO, compteId);
//
//            httpParams.put(KEY_CV_NUM_DOSSIER, adNumDossier);
//            httpParams.put(KEY_CV_MT_SOLDE, eavDepotMin );
//            httpParams.put(KEY_CV_NATURE_OPERATION, natureOperation );
            httpParams.put(KEY_CV_USER_CREE, String.valueOf(MyData.USER_ID));
            httpParams.put(KEY_CV_GUICHET, String.valueOf(MyData.GUICHET_ID));
            httpParams.put(KEY_CgLastSolde, String.valueOf(MontantReel));

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_jour_ferme.php", "POST", httpParams);
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
                        notificationCloture();
//                        Toast.makeText(ClotureJourneeActivity.this,
//                                "Journée du"+ tv_jour_ouvre+" fermée avec succès !", Toast.LENGTH_LONG).show();
                      /*  Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();*/
/*
                        Intent intent = new Intent(getApplicationContext(), LoginActivity_NEW.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("LOGOUT", true);
                        startActivity(intent);*/

                    } else {
                        Toast.makeText(ClotureJourneeActivity.this,
                                "Echec!\n Lors de la fermeture de la journée ",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
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
            pDialog = new ProgressDialog(ClotureJourneeActivity.this);
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
                        Toast.makeText(ClotureJourneeActivity.this,
                                "Opération réussie !", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(ClotureJourneeActivity.this,
                                "Echec!\n Vérifiez votre solde ",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}