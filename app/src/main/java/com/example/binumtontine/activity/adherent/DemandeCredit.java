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


import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.Category;
import com.example.binumtontine.activity.CreateOperationTransfertEnvoyer;
import com.example.binumtontine.activity.ListNomPrenomAdherentTransfert;
import com.example.binumtontine.activity.ListNomPrenomAvalisteDemandeCredit;
import com.example.binumtontine.activity.ListPlageFCV;
import com.example.binumtontine.activity.ListProduitDmdeCredit;
import com.example.binumtontine.activity.ServiceHandler;
import com.example.binumtontine.activity.convertisseur.FrenchNumberToWords;
import com.example.binumtontine.activity.pdf.Common;
import com.example.binumtontine.activity.pdf.PdfDocumentAdapter;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.example.binumtontine.modele.ComiteCredit;
import com.example.binumtontine.modele.DemandeCreditModele;
import com.example.binumtontine.modele.ProduitDmdeCredit;
import com.example.binumtontine.modele.Transfert;
import com.google.android.material.textfield.TextInputLayout;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.poi.sl.usermodel.Comment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class DemandeCredit extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    /*Begin*/
         //to fetchProduitList by caisse and guichet ID
    private static final String KEY_CREDIT_CAISSE_ID = "CrCaisseId";
    private static final String KEY_GX_NUMERO = "CrGuichetId";
    private static final String KEY_EV_GUICHET_ID = "ev_gx_numero";
    /* end*/
    private static final String KEY_CR_ID = "CrNumero";
    private static final String KEY_CR_LIBELLE = "CrLibelle";

    private static final String KEY_DC_GUICHET = "DcGuichet";
    private static final String KEY_GUICHET_ID = "gx_numero";
    /*Param for get extra*/
    private static final String KEY_ADHERENT_ID = "IpMembre";
    private static final String KEY_ADHERENT_NOM = "AdNom";
    private static final String KEY_ADHERENT_PRENOM = "AdPrenom";
    private static final String KEY_ADHERENT_NUM_MANUEL = "AdNumManuel";
    private static final String KEY_ADHERENT_CODE = "AdCode";
    private static final String KEY_CAISSE_ID = "OcCaisse";
    private static final String KEY_OC_NUMERO = "OcNumero";
    private static final String KEY_OC_LIBELLE = "OcLibelle";


    private static String STRING_EMPTY = "";

    private DatePickerDialog DcDate_PickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date
    private DatePickerDialog DcDatePremEch_PickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date
    private DatePickerDialog DcDateSignat_PickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date
    private SimpleDateFormat dateFormatter; //propriété permettant de gérer le format de la date

    public static DemandeCreditModele demandeCreditModele;
    private EditText DcDateET;
    private EditText DcNumDossierET;
    private EditText DcCpteEAVPrincET;
    private EditText DcMtSoldeEAVET;
    private EditText DcMtEpargnObligET;
    private EditText DcMtPartSocialET;
    private EditText DcActivitePrincET;
    private EditText DcNbAnneeExperET;
    private EditText DcAutresActiviteET;

    private RadioButton rb_DcIsEmpruntPasseNon;
    private RadioButton rb_DcIsEmpruntPasseOui;

    private RadioButton rb_DcPeriodRemb_HEB;
    private RadioButton rb_DcPeriodRemb_QUI;
    private RadioButton rb_DcPeriodRemb_MEN;
    private RadioButton rb_DcPeriodRemb_BIM;
    private RadioButton rb_DcPeriodRemb_TRI;
    private RadioButton rb_DcPeriodRemb_SEM;
    private RadioButton rb_DcPeriodRemb_ANN;

    private EditText DcEmpruntPasseOuET;
    private TextInputLayout til_DcEmpruntPasseOu;


    private RadioButton rb_DcIsEchRembRespNon;
    private RadioButton rb_DcIsEchRembRespOui;
    private EditText DcPourqRembSiNonET;
    private TextInputLayout til_DcPourqRembSiNon;

    private RadioButton rb_DcPretEnCoursNon;
    private RadioButton rb_DcPretEnCoursOui;
    private EditText DcPretInstitET;

    private EditText DcNaturePretET;
    private EditText DcMtPrincPretET;
    private EditText DcMtRestARembET;
    private EditText DcMtPretSolliciteET;
    private EditText DcDescriptPretET;
    private EditText DcDureeRembET;
    private EditText DcDatePremEchET;
    //private EditText DcPeriodRembET; //a demander à diff l'unité
    private EditText DcNbJrDiffereET;
    private EditText DcGarantiesPropET;
    private EditText DcIsAvaliste1ET;

    private RadioButton rb_DcIsAvaliste1Non;
    private RadioButton rb_DcIsAvaliste1Oui;
    private RadioButton rb_DcIsAvaliste2Non;
    private RadioButton rb_DcIsAvaliste2Oui;
    private RadioButton rb_DcIsAvaliste3Non;
    private RadioButton rb_DcIsAvaliste3Oui;

    public static EditText DcMtSoldAvalist1ET;
    public static EditText DcNomAval1ET;
    public static EditText DcNomAval2ET;
    public static EditText DcNomAval3ET;
    public static EditText DcPrenomAval3ET;
    public static EditText DcPrenomAval2ET;
    public static EditText DcPrenomAval1ET;
    private EditText DcIsAvaliste2ET;
    public static EditText DcMtSoldAvalist2ET;
    private EditText DcIsAvaliste3ET;
    public static EditText DcMtSoldAvalist3ET;

    private RadioButton rb_DcIsBesoinsAFinNon;
    private RadioButton rb_DcIsBesoinsAFinOui;
    public static EditText DcMtTotalBesoinsET;
    private EditText DcMotivationPretET;

    private TextInputLayout til_DcMtTotalBesoins;
    private TextInputLayout til_DcMotivationPret;
    private TextInputLayout til_DcGarantiesProp;
    private CheckBox DcIsPhotocopieCNICB;
    private CheckBox DcIsCpteExploitCB;
    private CheckBox DcIsDmdeManuscrCB;
    private CheckBox DcIsPceGarantieCB;

    private RadioButton rb_DcIsAutresDocumNon;
    private RadioButton rb_DcIsAutresDocumOui;
    private EditText DcAutresDocumET;
    private TextInputLayout til_DcAutresDocum;

    private RadioButton rb_DcIsSignatAdhNon;
    private RadioButton rb_DcIsSignatAdhOui;
    private EditText DcDateSignatET;
    private EditText DcDetailsET;
    private TextInputLayout til_DcDetails;

    private String adherentId;
    private String dcMtDmde;
    private String adNom;
    private String adPrenom;
    private String adNumManuel;
    private String adCode;
    private String dcNumDossier;

    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Category> creditList;
    List<Integer> creditListID = new ArrayList<Integer>();
    private ArrayList<Category> objetCreditList;
    List<Integer> objetCreditListID = new ArrayList<Integer>();
    private int creditID;
    private Spinner spinnerListCredit;
    private int objetCreditID;
    private Spinner spinnerListObjetCredit;
    private TextView tvAdherentNom;
    private TextView tvAdherentNumManuel;
    private TextView tvAdherentCode;
    private TextView tvAdherentNumDossier;
    /*end manage*/

    private Button addButton;
    private Button annulerButton;
    private int success;
    private ProgressDialog pDialog;
    private ProgressDialog pDialogDmdeCredit;
    private ProgressDialog pDialogFetchProduitCreditList;
    private LinearLayout ll_DcEmpruntPasseOu;
    private LinearLayout ll_DcMtSoldAvalist1;
    private LinearLayout ll_DcMtSoldAvalist2;
    private LinearLayout ll_DcMtSoldAvalist3;
    private LinearLayout ll_DcPourqRembSiNon;
    private LinearLayout ll_DcPretInstit;
    private LinearLayout ll_DcMtTotalBesoins;
    private LinearLayout ll_DcAutresDocum;
    private LinearLayout ll_DcDateSignat;
    private TextInputLayout til_DcMtPretSollicite;
    private TextInputLayout til_DcDescriptPret;
    private TextInputLayout til_DcDureeRemb;
    private TextInputLayout til_DcPeriodRemb;
    private TextInputLayout til_DcNbJrDiffere;
    private TextInputLayout til_DcDatePremEch;
    private TextInputLayout til_DcMtRestARemb;
    private TextInputLayout til_DcMtPrincPret;
    private TextInputLayout til_DcNaturePret;
    private TextInputLayout til_DcPretInstit;
    private TextInputLayout til_DcDateSignat;
    public static String CvDateHCree="";
    private TextView tv_DcCpteEAVPrinc;
    private TextView tv_DcMtSoldeEAV;
    private TextView tv_date_ouverture;

    public static ArrayList<ProduitDmdeCredit> listProduitDmdeProduit = new ArrayList<>(); //to manage listProduitDmdeProduit
    private TextView tv_list_produit_dmde_credit;
    private NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_demande_credit_adherent);
        demandeCreditModele = new DemandeCreditModele();
        defaultFormat.setCurrency(Currency.getInstance("XAF"));
        Intent intent = getIntent();
        adherentId = intent.getStringExtra(KEY_ADHERENT_ID);
        adNom = intent.getStringExtra(KEY_ADHERENT_NOM);
        adPrenom = intent.getStringExtra(KEY_ADHERENT_PRENOM);
        adNumManuel = intent.getStringExtra(KEY_ADHERENT_NUM_MANUEL);
        adCode = intent.getStringExtra(KEY_ADHERENT_CODE);

        DcNumDossierET = (EditText) findViewById(R.id.input_txt_DcNumDossier);
        DcMtEpargnObligET = (EditText) findViewById(R.id.input_txt_DcMtEpargnOblig);
        DcMtPartSocialET = (EditText) findViewById(R.id.input_txt_DcMtPartSocial);
        DcActivitePrincET = (EditText) findViewById(R.id.input_txt_DcActivitePrinc);
        DcNbAnneeExperET = (EditText) findViewById(R.id.input_txt_DcNbAnneeExper);
        DcAutresActiviteET = (EditText) findViewById(R.id.input_txt_DcAutresActivite);
        DcAutresActiviteET = (EditText) findViewById(R.id.input_txt_DcAutresActivite);


        rb_DcPeriodRemb_HEB = (RadioButton) findViewById(R.id.rb_DcPeriodRemb_HEB);
        rb_DcPeriodRemb_QUI = (RadioButton) findViewById(R.id.rb_DcPeriodRemb_QUI);
        rb_DcPeriodRemb_MEN = (RadioButton) findViewById(R.id.rb_DcPeriodRemb_MEN);
        rb_DcPeriodRemb_BIM = (RadioButton) findViewById(R.id.rb_DcPeriodRemb_BIM);
        rb_DcPeriodRemb_TRI = (RadioButton) findViewById(R.id.rb_DcPeriodRemb_TRI);
        rb_DcPeriodRemb_SEM = (RadioButton) findViewById(R.id.rb_DcPeriodRemb_SEM);
        rb_DcPeriodRemb_ANN = (RadioButton) findViewById(R.id.rb_DcPeriodRemb_ANN);

        rb_DcIsEmpruntPasseOui = (RadioButton) findViewById(R.id.rb_DcIsEmpruntPasse_oui);
        rb_DcIsEmpruntPasseNon = (RadioButton) findViewById(R.id.rb_DcIsEmpruntPasse_non);
        DcEmpruntPasseOuET = (EditText) findViewById(R.id.input_txt_DcEmpruntPasseOu);
        til_DcEmpruntPasseOu = (TextInputLayout) findViewById(R.id.input_layout_DcEmpruntPasseOu);


        rb_DcIsEchRembRespOui = (RadioButton) findViewById(R.id.rb_DcIsEchRembResp_oui);
        rb_DcIsEchRembRespNon = (RadioButton) findViewById(R.id.rb_DcIsEchRembResp_non);
        DcPourqRembSiNonET = (EditText) findViewById(R.id.input_txt_DcPourqRembSiNon);
        til_DcPourqRembSiNon = (TextInputLayout) findViewById(R.id.input_layout_DcPourqRembSiNon);

        rb_DcPretEnCoursOui = (RadioButton) findViewById(R.id.rb_DcPretEnCours_oui);
        rb_DcPretEnCoursNon = (RadioButton) findViewById(R.id.rb_DcPretEnCours_non);
        DcPretInstitET = (EditText) findViewById(R.id.input_txt_DcPretInstit);
        DcNaturePretET = (EditText) findViewById(R.id.input_txt_DcNaturePret);
        DcMtPrincPretET = (EditText) findViewById(R.id.input_txt_DcMtPrincPret);
        DcMtRestARembET = (EditText) findViewById(R.id.input_txt_DcMtRestARemb);

//        4- Détails du prêt sollicité
        DcMtPretSolliciteET = (EditText) findViewById(R.id.input_txt_DcMtPretSollicite);
        DcDescriptPretET = (EditText) findViewById(R.id.input_txt_DcDescriptPret);
        DcDureeRembET = (EditText) findViewById(R.id.input_txt_DcDureeRemb);
//        DcPeriodRembET = (EditText) findViewById(R.id.input_txt_DcPeriodRemb);
        DcNbJrDiffereET = (EditText) findViewById(R.id.input_txt_DcNbJrDiffere);
        DcGarantiesPropET = (EditText) findViewById(R.id.input_txt_DcGarantiesProp);
        til_DcGarantiesProp = (TextInputLayout) findViewById(R.id.input_layout_DcGarantiesProp);
        til_DcNbJrDiffere = (TextInputLayout) findViewById(R.id.input_layout_DcNbJrDiffere);
        til_DcPeriodRemb = (TextInputLayout) findViewById(R.id.input_layout_DcPeriodRemb);
        til_DcDureeRemb = (TextInputLayout) findViewById(R.id.input_layout_DcDureeRemb);
        til_DcDescriptPret = (TextInputLayout) findViewById(R.id.input_layout_DcDescriptPret);
        til_DcMtPretSollicite = (TextInputLayout) findViewById(R.id.input_layout_DcMtPretSollicite);
        ll_DcEmpruntPasseOu = (LinearLayout) findViewById(R.id.ll_DcEmpruntPasseOu);
        ll_DcMtSoldAvalist1 = (LinearLayout) findViewById(R.id.ll_DcMtSoldAvalist1);
        ll_DcMtSoldAvalist2 = (LinearLayout) findViewById(R.id.ll_DcMtSoldAvalist2);
        ll_DcMtSoldAvalist3 = (LinearLayout) findViewById(R.id.ll_DcMtSoldAvalist3);
        ll_DcPourqRembSiNon = (LinearLayout) findViewById(R.id.ll_DcPourqRembSiNon);
        ll_DcPretInstit = (LinearLayout) findViewById(R.id.ll_DcPretInstit);
        ll_DcMtTotalBesoins = (LinearLayout) findViewById(R.id.ll_DcMtTotalBesoins);
        ll_DcAutresDocum = (LinearLayout) findViewById(R.id.ll_DcAutresDocum);
        ll_DcDateSignat = (LinearLayout) findViewById(R.id.ll_DcDateSignat);

        til_DcDatePremEch = (TextInputLayout) findViewById(R.id.input_layout_DcDatePremEch);
        til_DcMtRestARemb = (TextInputLayout) findViewById(R.id.input_layout_DcMtRestARemb);
        til_DcMtPrincPret = (TextInputLayout) findViewById(R.id.input_layout_DcMtPrincPret);
        til_DcNaturePret = (TextInputLayout) findViewById(R.id.input_layout_DcNaturePret);
        til_DcPretInstit = (TextInputLayout) findViewById(R.id.input_layout_DcPretInstit);
        til_DcDateSignat = (TextInputLayout) findViewById(R.id.input_layout_DcDateSignat);

        //        5- Avalistes

        rb_DcIsAvaliste1Oui = (RadioButton) findViewById(R.id.rb_DcIsAvaliste1_oui);
        rb_DcIsAvaliste1Non = (RadioButton) findViewById(R.id.rb_DcIsAvaliste1_non);
        DcNomAval1ET = (EditText) findViewById(R.id.input_DcNomAval1);
        rb_DcIsAvaliste2Oui = (RadioButton) findViewById(R.id.rb_DcIsAvaliste2_oui);
        rb_DcIsAvaliste2Non = (RadioButton) findViewById(R.id.rb_DcIsAvaliste2_non);
        DcNomAval2ET = (EditText) findViewById(R.id.input_DcNomAval2);
        rb_DcIsAvaliste3Oui = (RadioButton) findViewById(R.id.rb_DcIsAvaliste3_oui);
        rb_DcIsAvaliste3Non = (RadioButton) findViewById(R.id.rb_DcIsAvaliste3_non);
        DcNomAval3ET = (EditText) findViewById(R.id.input_DcNomAval3);
        DcPrenomAval3ET = (EditText) findViewById(R.id.input_DcPrenomAval3);
        DcPrenomAval2ET = (EditText) findViewById(R.id.input_DcPrenomAval2);
        DcPrenomAval1ET = (EditText) findViewById(R.id.input_DcPrenomAval1);
        DcMtSoldAvalist1ET = (EditText) findViewById(R.id.input_txt_DcMtSoldAvalist1);
        DcMtSoldAvalist2ET = (EditText) findViewById(R.id.input_txt_DcMtSoldAvalist2);
        DcMtSoldAvalist3ET = (EditText) findViewById(R.id.input_txt_DcMtSoldAvalist3);
        //        6- Détails des besoins à financier
        rb_DcIsBesoinsAFinOui = (RadioButton) findViewById(R.id.rb_DcIsBesoinsAFin_oui);
        rb_DcIsBesoinsAFinNon = (RadioButton) findViewById(R.id.rb_DcIsBesoinsAFin_non);
        DcMtTotalBesoinsET = (EditText) findViewById(R.id.input_txt_DcMtTotalBesoins);


        DcMotivationPretET = (EditText) findViewById(R.id.input_txt_DcMotivationPret);
        til_DcMotivationPret = (TextInputLayout) findViewById(R.id.input_layout_DcMotivationPret);
        til_DcMtTotalBesoins = (TextInputLayout) findViewById(R.id.input_layout_DcMtTotalBesoins);


//        7- Pièces fournies
        DcIsPhotocopieCNICB = (CheckBox)findViewById(R.id.DcIsPhotocopieCNICheckBox);
        DcIsCpteExploitCB = (CheckBox)findViewById(R.id.DcIsCpteExploitCheckBox);
        DcIsDmdeManuscrCB = (CheckBox)findViewById(R.id.DcIsDmdeManuscrCheckBox);
        DcIsPceGarantieCB = (CheckBox)findViewById(R.id.DcIsPceGarantieCheckBox);


        rb_DcIsAutresDocumOui = (RadioButton) findViewById(R.id.rb_DcIsAutresDocum_oui);
        rb_DcIsAutresDocumNon = (RadioButton) findViewById(R.id.rb_DcIsAutresDocum_non);
        DcAutresDocumET = (EditText) findViewById(R.id.input_txt_DcAutresDocum);
        til_DcAutresDocum = (TextInputLayout) findViewById(R.id.input_layout_DcAutresDocum);


        rb_DcIsSignatAdhOui = (RadioButton) findViewById(R.id.rb_DcIsSignatAdh_oui);
        rb_DcIsSignatAdhNon = (RadioButton) findViewById(R.id.rb_DcIsSignatAdh_non);
        DcDetailsET = (EditText) findViewById(R.id.input_txt_DcDetails);
        til_DcDetails = (TextInputLayout) findViewById(R.id.input_layout_DcDetails);

        tv_DcCpteEAVPrinc = (TextView) findViewById(R.id.tv_DcCpteEAVPrinc);
        tv_DcMtSoldeEAV = (TextView) findViewById(R.id.tv_DcMtSoldeEAV);
        tv_date_ouverture = (TextView) findViewById(R.id.tv_date_ouverture);
        tv_list_produit_dmde_credit = (TextView) findViewById(R.id.tv_list_produit_dmde_credit);

        spinnerListCredit = (Spinner) findViewById(R.id.spn_list_credit);
        spinnerListObjetCredit = (Spinner) findViewById(R.id.spn_list_objet_credit);
        tvAdherentNom = (TextView) findViewById(R.id.tv_nom_adherent);
        tvAdherentNom.setText(adNom+"\n"+adPrenom);
        tvAdherentNumManuel = (TextView) findViewById(R.id.tv_num_manuel_adherent);
        tvAdherentNumManuel.setText(adNumManuel);
        tvAdherentCode = (TextView) findViewById(R.id.tv_code_adherent);
        tvAdherentCode.setText(adCode);


        creditList = new ArrayList<Category>();
        objetCreditList = new ArrayList<Category>();
        // spinner item select listener
        spinnerListCredit.setOnItemSelectedListener(DemandeCredit.this);
        spinnerListObjetCredit.setOnItemSelectedListener(DemandeCredit.this);
        new GetAdherentDemandeCreditAsyncTask().execute();
        new GetProduitCreditList().execute();
        new FetchObjetCreditAsyncTask().execute();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();

        setDateTimeField();

//        onRadioButtonClicked(rb_avalisteCreditNon);
        onRadioButtonClicked(rb_DcIsEmpruntPasseNon);
        onRadioButtonClicked(rb_DcIsEchRembRespOui);
        onRadioButtonClicked(rb_DcPretEnCoursNon);
        onRadioButtonClicked(rb_DcIsBesoinsAFinNon);
        onRadioButtonClicked(rb_DcIsAutresDocumNon);
        onRadioButtonClicked(rb_DcIsSignatAdhNon);
        onRadioButtonClicked(rb_DcIsAvaliste3Non);
        onRadioButtonClicked(rb_DcIsAvaliste2Non);
        onRadioButtonClicked(rb_DcIsAvaliste1Non);
        addButton = (Button) findViewById(R.id.btn_save_demande_credit);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(DemandeCredit.this,
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
                    Toast.makeText(DemandeCredit.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        //tv_plage_membres_comite_dmde_cred
        tv_list_produit_dmde_credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    if (rb_DcIsBesoinsAFinNon.isChecked()){
                        Toast.makeText(DemandeCredit.this,
                                "Veuillez cochez oui au préalable",
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                    Intent i = new Intent(DemandeCredit.this, ListProduitDmdeCredit.class);
                    startActivityForResult(i,20);
                } else {
                    Toast.makeText(DemandeCredit.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {
         /*   case R.id.eavCheckBox:
                str = checked?"Produit EAV Selected":"Produit EAV Deselected";
                break;

        */

        }
//        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
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

            case R.id.rb_DcIsAvaliste1_oui:
                if (rb_DcIsAvaliste1Oui.isChecked()) {
                    ll_DcMtSoldAvalist1.setVisibility(View.VISIBLE);
                    DcNomAval1ET.setFocusable(false);
                    DcPrenomAval1ET.setFocusable(false);
                    DcMtSoldAvalist1ET.setFocusable(false);

                    DcNomAval1ET.setClickable(true);
                    DcPrenomAval1ET.setClickable(false);
                    DcMtSoldAvalist1ET.setClickable(false);
                    try {
                        DcNomAval1ET.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent  i = new Intent(DemandeCredit.this, ListNomPrenomAvalisteDemandeCredit.class);
                                i.putExtra(KEY_GUICHET_ID, MyData.GUICHET_ID+"");
                                i.putExtra("sens", "avaliste1");
                                startActivity(i);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.rb_DcIsAvaliste1_non:
                if (rb_DcIsAvaliste1Non.isChecked()) {
                    ll_DcMtSoldAvalist1.setVisibility(View.GONE);
                    DcNomAval1ET.setText("");
                    DcPrenomAval1ET.setText("");
                    DcMtSoldAvalist1ET.setText("");
                    demandeCreditModele.setDcNumAvaliste1("");
                    demandeCreditModele.setDcMtSoldAvalist1("");
                }
                break;
            case R.id.rb_DcIsAvaliste2_oui:
                if (rb_DcIsAvaliste2Oui.isChecked()) {
                    ll_DcMtSoldAvalist2.setVisibility(View.VISIBLE);
                    DcNomAval2ET.setFocusable(false);
                    DcPrenomAval2ET.setFocusable(false);
                    DcMtSoldAvalist2ET.setFocusable(false);

                    DcNomAval2ET.setClickable(true);
                    DcPrenomAval2ET.setClickable(false);
                    DcMtSoldAvalist2ET.setClickable(false);
                    try {
                        DcNomAval2ET.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent  i = new Intent(DemandeCredit.this, ListNomPrenomAvalisteDemandeCredit.class);
                                i.putExtra(KEY_GUICHET_ID, MyData.GUICHET_ID+"");
                                i.putExtra("sens", "avaliste2");
                                startActivity(i);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.rb_DcIsAvaliste2_non:
                if (rb_DcIsAvaliste2Non.isChecked()) {
                    ll_DcMtSoldAvalist2.setVisibility(View.GONE);
                    DcNomAval2ET.setText("");
                    DcPrenomAval2ET.setText("");
                    DcMtSoldAvalist2ET.setText("");
                    demandeCreditModele.setDcNumAvaliste2("");
                    demandeCreditModele.setDcMtSoldAvalist2("");
                }
                break;
            case R.id.rb_DcIsAvaliste3_oui:
                if (rb_DcIsAvaliste3Oui.isChecked()) {
                    ll_DcMtSoldAvalist3.setVisibility(View.VISIBLE);
                    DcNomAval3ET.setFocusable(false);
                    DcPrenomAval3ET.setFocusable(false);
                    DcMtSoldAvalist3ET.setFocusable(false);

                    DcNomAval3ET.setClickable(true);
                    DcPrenomAval3ET.setClickable(false);
                    DcMtSoldAvalist3ET.setClickable(false);
                    try {
                        DcNomAval3ET.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent  i = new Intent(DemandeCredit.this, ListNomPrenomAvalisteDemandeCredit.class);
                                i.putExtra(KEY_GUICHET_ID, MyData.GUICHET_ID+"");
                                i.putExtra("sens", "avaliste3");
                                startActivity(i);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.rb_DcIsAvaliste3_non:
                if (rb_DcIsAvaliste3Non.isChecked()) {
                    ll_DcMtSoldAvalist3.setVisibility(View.GONE);
                    DcNomAval3ET.setText("");
                    DcPrenomAval3ET.setText("");
                    DcMtSoldAvalist3ET.setText("");
                    demandeCreditModele.setDcNumAvaliste3("");
                    demandeCreditModele.setDcMtSoldAvalist3("");
                }
                break;
            case R.id.rb_DcIsEmpruntPasse_oui:
                if (rb_DcIsEmpruntPasseOui.isChecked()) {
                    ll_DcEmpruntPasseOu.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rb_DcIsEmpruntPasse_non:
                if (rb_DcIsEmpruntPasseNon.isChecked()) {
                    ll_DcEmpruntPasseOu.setVisibility(View.GONE);
                }
                break;
            case R.id.rb_DcIsEchRembResp_oui:
                if (rb_DcIsEchRembRespOui.isChecked()) {
                    ll_DcPourqRembSiNon.setVisibility(View.GONE);
                }
                break;
            case R.id.rb_DcIsEchRembResp_non:
                if (rb_DcIsEchRembRespNon.isChecked()) {
                    ll_DcPourqRembSiNon.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.rb_DcPretEnCours_oui:
                if (rb_DcPretEnCoursOui.isChecked()) {
                    ll_DcPretInstit.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rb_DcPretEnCours_non:
                if (rb_DcPretEnCoursNon.isChecked()) {
                    ll_DcPretInstit.setVisibility(View.GONE);
                }
                break;
            case R.id.rb_DcIsBesoinsAFin_oui:
                if (rb_DcIsBesoinsAFinOui.isChecked()) {
                    ll_DcMtTotalBesoins.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rb_DcIsBesoinsAFin_non:
                if (rb_DcIsBesoinsAFinNon.isChecked()) {
                    ll_DcMtTotalBesoins.setVisibility(View.GONE);
                }
                break;
            case R.id.rb_DcIsAutresDocum_oui:
                if (rb_DcIsAutresDocumOui.isChecked()) {
                    ll_DcAutresDocum.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rb_DcIsAutresDocum_non:
                if (rb_DcIsAutresDocumNon.isChecked()) {
                    ll_DcAutresDocum.setVisibility(View.GONE);
                }
                break;
            case R.id.rb_DcIsSignatAdh_oui:
                if (rb_DcIsSignatAdhOui.isChecked()) {
                    ll_DcDateSignat.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rb_DcIsSignatAdh_non:
                if (rb_DcIsSignatAdhNon.isChecked()) {
                    ll_DcDateSignat.setVisibility(View.GONE);
                }
                break;


        }
        // Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }


    private void findViewsById() {
        DcDateET = (EditText) findViewById(R.id.input_txt_DcDate);
        DcDateET.requestFocus();
        DcDateET.setInputType(InputType.TYPE_NULL);

        DcDateSignatET = (EditText) findViewById(R.id.input_txt_DcDateSignat);
        DcDateSignatET.requestFocus();
        DcDateSignatET.setInputType(InputType.TYPE_NULL);

        DcDatePremEchET = (EditText) findViewById(R.id.input_txt_DcDatePremEch);
        DcDatePremEchET.requestFocus();
        DcDatePremEchET.setInputType(InputType.TYPE_NULL);





    }

    private void setDateTimeField() {
        DcDateET.setOnClickListener(this);
        DcDateSignatET.setOnClickListener(this);
        DcDatePremEchET.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();
        DcDate_PickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                DcDateET.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        DcDateSignat_PickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                DcDateSignatET.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        DcDatePremEch_PickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                DcDatePremEchET.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));



    }

    @Override
    public void onClick(View v) {
        if(v == DcDateET) {
            DcDate_PickerDialog.show();
        }else if (v == DcDateSignatET){
            DcDateSignat_PickerDialog.show();
        }else if (v == DcDatePremEchET){
            DcDatePremEch_PickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            DcDatePremEch_PickerDialog.show();
        }
    }

    /**
     * Adding Credit spinner data
     * */
    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();

        //tvCaisse.setText("");

        for (int i = 0; i < creditList.size(); i++) {
            lables.add(creditList.get(i).getName());//recupère les noms
            creditListID.add(creditList.get(i).getId()); //recupère les Id
        }
        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(DemandeCredit.this,
                android.R.layout.simple_spinner_item, lables);
        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinnerListCredit.setAdapter(spinnerAdapter);
    }
    /**
     * Adding Objet Credit spinner data
     * */
    private void populateObjetCreditSpinner() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < objetCreditList.size(); i++) {
            lables.add(objetCreditList.get(i).getName());//recupère les noms
            objetCreditListID.add(objetCreditList.get(i).getId()); //recupère les Id
        }
        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(DemandeCredit.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerListObjetCredit.setAdapter(spinnerAdapter);
    }

    /**
     * Fetches the details adherent who want to make demande credit from the server
     */
    private class GetAdherentDemandeCreditAsyncTask extends AsyncTask<String, String, String> {
        int successGetFrais;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialogDmdeCredit = new ProgressDialog(DemandeCredit.this);
            pDialogDmdeCredit.setMessage("Please wait...");
            pDialogDmdeCredit.setIndeterminate(false);
            pDialogDmdeCredit.setCancelable(false);
            pDialogDmdeCredit.show();
        }
        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_GX_NUMERO, String.valueOf(MyData.GUICHET_ID));
            httpParams.put(KEY_ADHERENT_ID, adherentId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_adherent_demande_credit.php", "GET", httpParams);
            try {
                successGetFrais = jsonObject.getInt(KEY_SUCCESS);
                JSONArray movies;
                if (successGetFrais == 1) {
                    movies = jsonObject.getJSONArray(KEY_DATA);
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject movie = movies.getJSONObject(i);
                        Log.e("get_adherent_demande_credit", CvDateHCree);
                         demandeCreditModele.setDcCpteEAVPrinc(movie.getString(DemandeCreditModele.KEY_DcCpteEAVPrinc)) ;
                         demandeCreditModele.setDcMtSoldeEAV(movie.getString(DemandeCreditModele.KEY_DcMtSoldeEAV)) ;
                         demandeCreditModele.setDcMtPartSocial(movie.getString(DemandeCreditModele.KEY_DcMtPartSocial)) ;
                        CvDateHCree = movie.getString("CvDateHCree");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String result) {
            pDialogDmdeCredit.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (successGetFrais ==1){
                        try {
                            tv_DcCpteEAVPrinc.setText(demandeCreditModele.getDcCpteEAVPrinc());
                            tv_DcMtSoldeEAV.setText(demandeCreditModele.getDcMtSoldeEAV());
                            DcMtPartSocialET.setText(demandeCreditModele.getDcMtPartSocial());
                            tv_date_ouverture.setText(CvDateHCree);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }
    /**
     * Async task to get all food categories
     * */
    private class GetProduitCreditList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogFetchProduitCreditList = new ProgressDialog(DemandeCredit.this);
            pDialogFetchProduitCreditList.setMessage("Fetching produits's list..");
            pDialogFetchProduitCreditList.setCancelable(false);
            pDialogFetchProduitCreditList.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair(KEY_CREDIT_CAISSE_ID, String.valueOf(MyData.CAISSE_ID)));
            httpParams.add(new BasicNameValuePair(KEY_GX_NUMERO, String.valueOf(MyData.GUICHET_ID)));
            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_credit_by_guichet.php", ServiceHandler.GET, httpParams);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj
                                .getJSONArray(KEY_DATA);

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category cat = new Category(catObj.getInt(KEY_CR_ID),
                                    catObj.getString(KEY_CR_LIBELLE));
                            creditList.add(cat);
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
            if (pDialogFetchProduitCreditList.isShowing())
                pDialogFetchProduitCreditList.dismiss();
            populateSpinner();
        }
    }
    /**
     * Async task to get all objet credit categories
     * */
    private class FetchObjetCreditAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DemandeCredit.this);
            pDialog.setMessage("Fetching objets's list..");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair(KEY_CAISSE_ID, String.valueOf(MyData.CAISSE_ID)));
//            httpParams.add(new BasicNameValuePair(KEY_EV_GUICHET_ID, String.valueOf(MyData.GUICHET_ID)));
            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_objet_credit_by_caisse.php", ServiceHandler.GET, httpParams);

//            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_credit_by_guichet.php", ServiceHandler.GET, httpParams);
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
                            Category cat = new Category(catObj.getInt(KEY_OC_NUMERO),
                                    catObj.getString(KEY_OC_LIBELLE));
                            objetCreditList.add(cat);
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
            if (pDialog.isShowing())
                pDialog.dismiss();
            populateObjetCreditSpinner();
        }

    }



    /**
     * Async task to get all adherents on guichet
     * */
    private class GetProduitAvalisteList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogFetchProduitCreditList = new ProgressDialog(DemandeCredit.this);
            pDialogFetchProduitCreditList.setMessage("Fetching produits's list..");
            pDialogFetchProduitCreditList.setCancelable(false);
            pDialogFetchProduitCreditList.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair(KEY_CREDIT_CAISSE_ID, String.valueOf(MyData.CAISSE_ID)));
            httpParams.add(new BasicNameValuePair(KEY_EV_GUICHET_ID, String.valueOf(MyData.GUICHET_ID)));
            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_credit_by_caisse.php", ServiceHandler.GET, httpParams);
//            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_credit_by_guichet.php", ServiceHandler.GET, httpParams);
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
                            Category cat = new Category(catObj.getInt(KEY_CR_ID),
                                    catObj.getString(KEY_CR_LIBELLE));
                            creditList.add(cat);
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
            if (pDialogFetchProduitCreditList.isShowing())
                pDialogFetchProduitCreditList.dismiss();
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


        int idSpinner = parent.getId();

        switch (idSpinner)
        {
            case R.id.spn_list_credit:
                // your stuff here
                creditID = creditListID.get(position);//pour recuperer l'ID du produit crédit selectionné
                break;
            case R.id.spn_list_objet_credit:
                // your stuff here
                objetCreditID = objetCreditListID.get(position);//pour recuperer l'ID de l'objet crédit selectionné
                break;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
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
    /**
     * Checks whether all files are filled. If so then calls AddDcAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addEavAdherent() {
        if (
                creditID !=0 && objetCreditID !=0 && !demandeCreditModele.getDcCpteEAVPrinc().equals("0") ) {
//                creditID !=0  && !demandeCreditModele.getDcCpteEAVPrinc().equals("0") ) {
//                creditID !=0 ) {
//            demandeCreditModele = new DemandeCreditModele();
            demandeCreditModele.setDcMembre(adherentId);
            demandeCreditModele.setDcDate(DcDateET.getText().toString());
            demandeCreditModele.setDcNumDossier(DcNumDossierET.getText().toString());
//            demandeCreditModele.setDcCpteEAVPrinc(DcCpteEAVPrinc.getText().toString());
//            demandeCreditModele.setDcMtSoldeEAV(DcMtSoldeEAV.getText().toString());
            demandeCreditModele.setDcMtEpargnOblig(DcMtEpargnObligET.getText().toString());
            demandeCreditModele.setDcMtPartSocial(DcMtPartSocialET.getText().toString());
            demandeCreditModele.setDcActivitePrinc(DcActivitePrincET.getText().toString());
            demandeCreditModele.setDcNbAnneeExper(DcNbAnneeExperET.getText().toString());
            demandeCreditModele.setDcAutresActivite(DcAutresActiviteET.getText().toString());
            if (rb_DcIsEmpruntPasseOui.isChecked()){
                if (DcEmpruntPasseOuET.getText().toString().trim().isEmpty()){
                    til_DcEmpruntPasseOu.setError("Indiquer le lieu de l'emprunt");
                    requestFocus(DcEmpruntPasseOuET);
                    return;
                }else{
                    demandeCreditModele.setDcIsEmpruntPasse("Y");
                }
            }else{
                demandeCreditModele.setDcIsEmpruntPasse("N");
            }
            demandeCreditModele.setDcEmpruntPasseOu(DcEmpruntPasseOuET.getText().toString());
            if (rb_DcIsEchRembRespNon.isChecked()){
                if (DcPourqRembSiNonET.getText().toString().trim().isEmpty()){
                    til_DcPourqRembSiNon.setError("pourquoi les échéances n'ont pas été respectées");
                    requestFocus(DcPourqRembSiNonET);
                    return;
                }else{
                    demandeCreditModele.setDcIsEchRembResp("N");
                }
            }else{
                demandeCreditModele.setDcIsEchRembResp("Y");
            }
            demandeCreditModele.setDcPourqRembSiNon(DcPourqRembSiNonET.getText().toString());

            //            DcPretEnCours DEBUT

            if (rb_DcPretEnCoursOui.isChecked()){
                if (DcPretInstitET.getText().toString().trim().isEmpty()){
                    til_DcPretInstit.setError("Dire dans quelle institution l'on a le prêt");
                    requestFocus(DcPretInstitET);
                    return;
                }else if (DcNaturePretET.getText().toString().trim().isEmpty()){
                    til_DcNaturePret.setError("Dire de quelle nature est ce prêt");
                    requestFocus(DcNaturePretET);
                    return;
                }else if (DcMtPrincPretET.getText().toString().trim().isEmpty()){
                    til_DcMtPrincPret.setError("Dire quel était le montant du Principal du Prêt");
                    requestFocus(DcMtPrincPretET);
                    return;
                }else if (DcMtRestARembET.getText().toString().trim().isEmpty()){
                    til_DcMtRestARemb.setError("Dire combien il reste à rembourser sur ce prêt");
                    requestFocus(DcMtRestARembET);
                    return;
                }else{
                    demandeCreditModele.setDcPretEnCours("Y");
                }
            }else{
                demandeCreditModele.setDcPretEnCours("N");
            }
            demandeCreditModele.setDcPretInstit(DcPretInstitET.getText().toString());
            demandeCreditModele.setDcNaturePret(DcNaturePretET.getText().toString());
            demandeCreditModele.setDcMtPrincPret(DcMtPrincPretET.getText().toString());
            demandeCreditModele.setDcMtRestARemb(DcMtRestARembET.getText().toString());
//            DcPretEnCours FIN

//            4- Détails du prêt sollicité

            if (DcMtPretSolliciteET.getText().toString().trim().isEmpty()){
                til_DcMtPretSollicite.setError("Indiquer le Montant sollicité pour le prêt");
                requestFocus(DcMtPretSolliciteET);
                return;
            }else{
                demandeCreditModele.setDcMtPretSollicite(DcMtPretSolliciteET.getText().toString());

            }

            demandeCreditModele.setDcObjet(objetCreditID+"");
//            demandeCreditModele.setDcProduit(objetCreditID+"");
            if (DcDescriptPretET.getText().toString().trim().isEmpty()){
                til_DcDescriptPret.setError("Indiquer la Description du prêt");
                requestFocus(DcDescriptPretET);
                return;
            }else{
                demandeCreditModele.setDcDescriptPret(DcDescriptPretET.getText().toString());

            }
            if (DcDureeRembET.getText().toString().trim().isEmpty()){
                til_DcDureeRemb.setError("Indiquer la Durée du remboursement en mois");
                requestFocus(DcDureeRembET);
                return;
            }else{
                demandeCreditModele.setDcDureeRemb(DcDureeRembET.getText().toString());

            }
            if (DcDatePremEchET.getText().toString().trim().isEmpty()){
                til_DcDatePremEch.setError("Indiquer la Date de la première échéance du Prêt");
                requestFocus(DcDatePremEchET);
                return;
            }else{
                demandeCreditModele.setDcDatePremEch(DcDatePremEchET.getText().toString());
            }

            if(rb_DcPeriodRemb_HEB.isChecked()){
                demandeCreditModele.setDcPeriodRemb("HEB");
            }else if(rb_DcPeriodRemb_QUI.isChecked()){
                demandeCreditModele.setDcPeriodRemb("QUI");
            }else if(rb_DcPeriodRemb_MEN.isChecked()){
                demandeCreditModele.setDcPeriodRemb("MEN");
            }else if(rb_DcPeriodRemb_BIM.isChecked()){
                demandeCreditModele.setDcPeriodRemb("BIM");
            }else if(rb_DcPeriodRemb_TRI.isChecked()){
                demandeCreditModele.setDcPeriodRemb("TRI");
            }else if(rb_DcPeriodRemb_SEM.isChecked()){
                demandeCreditModele.setDcPeriodRemb("SEM");
            }else if(rb_DcPeriodRemb_ANN.isChecked()){
                demandeCreditModele.setDcPeriodRemb("ANN");
            }


            if (DcNbJrDiffereET.getText().toString().trim().isEmpty()){
                til_DcNbJrDiffere.setError("Indiquer le Nombre de jours de diffèré");
                requestFocus(DcNbJrDiffereET);
                return;
            }else{
                demandeCreditModele.setDcNbJrDiffere(DcNbJrDiffereET.getText().toString());
            }
            if (DcGarantiesPropET.getText().toString().trim().isEmpty()){
                til_DcGarantiesProp.setError("Indiquer les Garanties proposées par le membre");
                requestFocus(DcGarantiesPropET);
                return;
            }else{
                demandeCreditModele.setDcGarantiesProp(DcGarantiesPropET.getText().toString());
            }
//            5- Avalistes

            if (rb_DcIsAvaliste1Oui.isChecked()){
                if (DcNomAval1ET.getText().toString().trim().isEmpty()){
                    DcNomAval1ET.setError("Indiquer le nom");
                    requestFocus(DcNomAval1ET);
                    return;
                }else{
                    demandeCreditModele.setDcIsAvaliste1("Y");
                }
            }else{
                demandeCreditModele.setDcIsAvaliste1("N");
            }
            //Aval2
            if (rb_DcIsAvaliste2Oui.isChecked()){
                if (DcNomAval2ET.getText().toString().trim().isEmpty()){
                    DcNomAval2ET.setError("Indiquer le nom");
                    requestFocus(DcNomAval2ET);
                    return;
                }else{
                    demandeCreditModele.setDcIsAvaliste2("Y");
                }
            }else{
                demandeCreditModele.setDcIsAvaliste2("N");
            }
            //Aval3
            if (rb_DcIsAvaliste3Oui.isChecked()){
                if (DcNomAval3ET.getText().toString().trim().isEmpty()){
                    DcNomAval3ET.setError("Indiquer le nom");
                    requestFocus(DcNomAval3ET);
                    return;
                }else{
                    demandeCreditModele.setDcIsAvaliste3("Y");
                }
            }else{
                demandeCreditModele.setDcIsAvaliste3("N");
            }
//            6- Détails des besoins à financier

            if (rb_DcIsBesoinsAFinOui.isChecked()){
                if (DcMtTotalBesoinsET.getText().toString().trim().isEmpty()){
                    til_DcMtTotalBesoins.setError("Indiquer le Montant total des besoins à financer");
                    requestFocus(DcMtTotalBesoinsET);
                    return;
                }else{
                    demandeCreditModele.setDcIsBesoinsAFin("Y");
                }
            }else{
                demandeCreditModele.setDcIsBesoinsAFin("N");
            }
            demandeCreditModele.setDcMtTotalBesoins(DcMtTotalBesoinsET.getText().toString());

            if (DcMotivationPretET.getText().toString().trim().isEmpty()){
                til_DcMotivationPret.setError("Indiquer les Détails des motivations du Prêt");
                requestFocus(DcMotivationPretET);
                return;
            }else{
                demandeCreditModele.setDcMotivationPret(DcMotivationPretET.getText().toString());
            }
            if (DcIsPhotocopieCNICB.isChecked()){
                demandeCreditModele.setDcIsPhotocopieCNI("Y");
            }else{
                demandeCreditModele.setDcIsPhotocopieCNI("N");
            }
            if (DcIsCpteExploitCB.isChecked()){
                demandeCreditModele.setDcIsCpteExploit("Y");
            }else{
                demandeCreditModele.setDcIsCpteExploit("N");
            }
            if (DcIsDmdeManuscrCB.isChecked()){
                demandeCreditModele.setDcIsDmdeManuscr("Y");
            }else{
                demandeCreditModele.setDcIsDmdeManuscr("N");
            }

            if (DcIsPceGarantieCB.isChecked()){
                demandeCreditModele.setDcIsPceGarantie("Y");
            }else{
                demandeCreditModele.setDcIsPceGarantie("N");
            }

            if (rb_DcIsAutresDocumOui.isChecked()){
                if (DcAutresDocumET.getText().toString().trim().isEmpty()){
                    til_DcAutresDocum.setError("Indiquer la Liste des autres documents remis");
                    requestFocus(DcAutresDocumET);
                    return;
                }else{
                    demandeCreditModele.setDcIsAutresDocum("Y");
                }
            }else{
                demandeCreditModele.setDcIsAutresDocum("N");
            }
            demandeCreditModele.setDcAutresDocum(DcAutresDocumET.getText().toString());
            if (rb_DcIsSignatAdhOui.isChecked()){
                if (DcDateSignatET.getText().toString().trim().isEmpty()){
                    til_DcDateSignat.setError("Indiquer la  Date de signature du document");
                    requestFocus(DcDateSignatET);
                    return;
                }else{
                    demandeCreditModele.setDcIsSignatAdh("Y");
                }
            }else{
                demandeCreditModele.setDcIsSignatAdh("N");
            }
            demandeCreditModele.setDcDateSignat(DcDateSignatET.getText().toString());

            if (DcDetailsET.getText().toString().trim().isEmpty()){
                til_DcDetails.setError("Indiquer les Détails sur le produit de Crédit");
                requestFocus(DcDetailsET);
                return;
            }else{
                demandeCreditModele.setDcDetails(DcDetailsET.getText().toString());
            }

                new AddDcAsyncTask().execute();


        } else if(creditID ==0) {
            notificationProduitCreditVide();
        }else if(objetCreditID ==0) {
            notificationObjetVide();
        }else if(demandeCreditModele.getDcCpteEAVPrinc().equals("0")) {
            notificationCpteEAVVide();
        }else {

            ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
            toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,150);
            Toast.makeText(DemandeCredit.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();
        }


    }

    public void notificationProduitCreditVide() {

        ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,150);
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Aucun produit crédit sélectionné")
                .setMessage("Veuillez affecter un produit crédit à ce guichet!")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        spinnerListCredit.setError ("Donnez vos observations SVP!");
                        spinnerListCredit.requestFocus();
                    }

                })
                .show();
    }
    public void notificationObjetVide() {

        ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        toneGen1.startTone(ToneGenerator.TONE_SUP_ERROR,150);
//        toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,150);
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Aucun objet de crédit sélectionné")
                .setMessage("Veuillez affecter un objet de crédit à ce guichet!")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        spinnerListCredit.setError ("Donnez vos observations SVP!");
                        spinnerListObjetCredit.requestFocus();
                    }

                })
                .show();
    }
    public void notificationCpteEAVVide() {

        ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,150);
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Votre compte EAV principal est vide")
                .setMessage("Veuillez créditer votre compte EAV!")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        spinnerListCredit.setError ("Donnez vos observations SVP!");
                        tv_DcMtSoldeEAV.requestFocus();
                        tv_DcMtSoldeEAV.setBackgroundColor(Color.RED);
                    }

                })
                .show();
    }
    /**
     * AsyncTask for adding a compte eav
     */
    private class AddDcAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(DemandeCredit.this);
            pDialog.setMessage("Adding demande crédit. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
           // httpParams.put(KEY_CR_ID, uxGuichetId);

            httpParams.put(DemandeCreditModele.KEY_DcMembre, adherentId);
            httpParams.put(DemandeCreditModele.KEY_DcDate, demandeCreditModele.getDcDate());
            httpParams.put(DemandeCreditModele.KEY_DcNumDossier, demandeCreditModele.getDcNumDossier());
            httpParams.put(DemandeCreditModele.KEY_DcCpteEAVPrinc, demandeCreditModele.getDcCpteEAVPrinc());
            httpParams.put(DemandeCreditModele.KEY_DcMtSoldeEAV, demandeCreditModele.getDcMtSoldeEAV());
            httpParams.put(DemandeCreditModele.KEY_DcMtEpargnOblig, demandeCreditModele.getDcMtEpargnOblig());
            httpParams.put(DemandeCreditModele.KEY_DcMtPartSocial, demandeCreditModele.getDcMtPartSocial());
            httpParams.put(DemandeCreditModele.KEY_DcActivitePrinc, demandeCreditModele.getDcActivitePrinc());
            httpParams.put(DemandeCreditModele.KEY_DcNbAnneeExper, demandeCreditModele.getDcNbAnneeExper());
            httpParams.put(DemandeCreditModele.KEY_DcAutresActivite, demandeCreditModele.getDcAutresActivite());
            httpParams.put(DemandeCreditModele.KEY_DcIsEmpruntPasse, demandeCreditModele.getDcIsEmpruntPasse());
            httpParams.put(DemandeCreditModele.KEY_DcEmpruntPasseOu, demandeCreditModele.getDcEmpruntPasseOu());
            httpParams.put(DemandeCreditModele.KEY_DcIsEchRembResp, demandeCreditModele.getDcIsEchRembResp());
            httpParams.put(DemandeCreditModele.KEY_DcPourqRembSiNon, demandeCreditModele.getDcPourqRembSiNon());
            httpParams.put(DemandeCreditModele.KEY_DcPretEnCours, demandeCreditModele.getDcPretEnCours());
            httpParams.put(DemandeCreditModele.KEY_DcPretInstit, demandeCreditModele.getDcPretInstit());
            httpParams.put(DemandeCreditModele.KEY_DcNaturePret, demandeCreditModele.getDcNaturePret());
            httpParams.put(DemandeCreditModele.KEY_DcMtPrincPret, demandeCreditModele.getDcMtPrincPret());
            httpParams.put(DemandeCreditModele.KEY_DcMtRestARemb, demandeCreditModele.getDcMtRestARemb());
            httpParams.put(DemandeCreditModele.KEY_DcMtPretSollicite, demandeCreditModele.getDcMtPretSollicite());
//            httpParams.put(DemandeCreditModele.KEY_DcObjet, demandeCreditModele.getDcObjet());
            httpParams.put(DemandeCreditModele.KEY_DcObjet, String.valueOf(objetCreditID));
            httpParams.put(DemandeCreditModele.KEY_DcCredit, String.valueOf(creditID));
            httpParams.put(DemandeCreditModele.KEY_DcDescriptPret, demandeCreditModele.getDcDescriptPret());
            httpParams.put(DemandeCreditModele.KEY_DcDureeRemb, demandeCreditModele.getDcDureeRemb());
            httpParams.put(DemandeCreditModele.KEY_DcDatePremEch, demandeCreditModele.getDcDatePremEch());
            httpParams.put(DemandeCreditModele.KEY_DcPeriodRemb, demandeCreditModele.getDcPeriodRemb());
            httpParams.put(DemandeCreditModele.KEY_DcNbJrDiffere, demandeCreditModele.getDcNbJrDiffere());
            httpParams.put(DemandeCreditModele.KEY_DcGarantiesProp, demandeCreditModele.getDcGarantiesProp());
            httpParams.put(DemandeCreditModele.KEY_DcIsAvaliste1, demandeCreditModele.getDcIsAvaliste1());
            httpParams.put(DemandeCreditModele.KEY_DcNumAvaliste1, demandeCreditModele.getDcNumAvaliste1());
            httpParams.put(DemandeCreditModele.KEY_DcMtSoldAvalist1, demandeCreditModele.getDcMtSoldAvalist1());
            httpParams.put(DemandeCreditModele.KEY_DcIsAvaliste2, demandeCreditModele.getDcIsAvaliste2());
            httpParams.put(DemandeCreditModele.KEY_DcNumAvaliste2, demandeCreditModele.getDcNumAvaliste2());
            httpParams.put(DemandeCreditModele.KEY_DcMtSoldAvalist2, demandeCreditModele.getDcMtSoldAvalist2());
            httpParams.put(DemandeCreditModele.KEY_DcIsAvaliste3, demandeCreditModele.getDcIsAvaliste3());
            httpParams.put(DemandeCreditModele.KEY_DcNumAvaliste3, demandeCreditModele.getDcNumAvaliste3());
            httpParams.put(DemandeCreditModele.KEY_DcMtSoldAvalist3, demandeCreditModele.getDcMtSoldAvalist3());
            httpParams.put(DemandeCreditModele.KEY_DcIsBesoinsAFin, demandeCreditModele.getDcIsBesoinsAFin());
            httpParams.put(DemandeCreditModele.KEY_DcMtTotalBesoins, demandeCreditModele.getDcMtTotalBesoins());
            httpParams.put(DemandeCreditModele.KEY_DcMotivationPret, demandeCreditModele.getDcMotivationPret());
            httpParams.put(DemandeCreditModele.KEY_DcIsPhotocopieCNI, demandeCreditModele.getDcIsPhotocopieCNI());
            httpParams.put(DemandeCreditModele.KEY_DcIsCpteExploit, demandeCreditModele.getDcIsCpteExploit());
            httpParams.put(DemandeCreditModele.KEY_DcIsDmdeManuscr, demandeCreditModele.getDcIsDmdeManuscr());
            httpParams.put(DemandeCreditModele.KEY_DcIsPceGarantie, demandeCreditModele.getDcIsPceGarantie());
            httpParams.put(DemandeCreditModele.KEY_DcIsAutresDocum, demandeCreditModele.getDcIsAutresDocum());
            httpParams.put(DemandeCreditModele.KEY_DcAutresDocum, demandeCreditModele.getDcAutresDocum());
            httpParams.put(DemandeCreditModele.KEY_DcIsSignatAdh, demandeCreditModele.getDcIsSignatAdh());
            httpParams.put(DemandeCreditModele.KEY_DcDateSignat, demandeCreditModele.getDcDateSignat());
            httpParams.put(DemandeCreditModele.KEY_DcDetails, demandeCreditModele.getDcDetails());
            httpParams.put(DemandeCreditModele.KEY_DcUserCree, String.valueOf(MyData.USER_ID));
/*
            httpParams.put(KEY_DC_CREDIT, String.valueOf(creditID));
            httpParams.put(KEY_DC_OBJET, String.valueOf(objetCreditID));
            httpParams.put(KEY_DC_NUM_DOSSIER, dcNumDossier);
            httpParams.put(KEY_DC_DESCRIPTION, dcDescription);
            httpParams.put(KEY_DC_MT_DMDE, dcMtDmde);
            httpParams.put(KEY_DC_DUREE_MOIS, dcDureeMois);
            httpParams.put(KEY_DC_TAUX_INTER, dcTauxInter);
            httpParams.put(KEY_DC_CUMUL_DETTE_ENCOURS, dcCumulDetteEncours);
            httpParams.put(KEY_DC_NAT_ACTIV, dcNatActiv);
            httpParams.put(KEY_DC_MT_SOLDE_EPARGNE, dcMtSoldeEpargne);
            httpParams.put(KEY_DC_FREQ_REVENUS, dcFreqRevenus);
            httpParams.put(KEY_DC_MT_REVENUS_FIXE, dcMtRevenusFixe);
            httpParams.put(KEY_DC_MT_AUTRE_REVENUS, dcMtAutreRevenus);
*/


            httpParams.put(KEY_DC_GUICHET, String.valueOf(MyData.GUICHET_ID));


//            httpParams.put(KEY_DC_USER_CREE, String.valueOf(MyData.USER_ID));

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_demande_credit.php", "POST", httpParams);
            Log.e("httpParams", httpParams+"");
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

                        notificationSuccessAdd();
                        avertissement();
                    /*    Toast.makeText(DemandeCredit.this,
                                "Demande créée avec succès", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();*/

                    } else {
                        notificationEchecAdd();
                        Toast.makeText(DemandeCredit.this,
                                "Some error occurred while adding Demande",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    public void notificationSuccessAdd() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Demande enregistrée ")
                .setMessage("L'enregistrement de votre opération s'est bien effectué !")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();
                    }

                })
                .show();
    }
    public void notificationEchecAdd() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Opération échouée ")
                .setMessage("L'enregistrement de votre opération a échoué!"
                        +"\n Veuillez réessayer !"
                )
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .show();
    }

    public void avertissement() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Impression du Bordereau !")
                .setMessage(
                        "\t\t Voulez-vous imprimer le bordereau ?"
                )
                .setNegativeButton("Non", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setPositiveButton("Oui", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dexter.withActivity(DemandeCredit.this)
                                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse response) {
                                        createPDFFile(Common.getAppPath(DemandeCredit.this)+"demande_credit_mifucam.pdf");
                                    }

                                    @Override
                                    public void onPermissionDenied(PermissionDeniedResponse response) {

                                    }

                                    @Override
                                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                                    }
                                })
                                .check();
                    }

                })
                .show();

    }

    /*START BLOC TO MANAGE PDF FILE*/
    private void createPDFFile(String path) {
        if (new File(path).exists()){
            new File(path).delete();
        }
        try {
            Document document = new Document();
            //Save
            PdfWriter.getInstance(document, new FileOutputStream(path));
            //Open to write
            document.open();
            //Setting
            document.setPageSize(PageSize.A4);
//            document.setPageSize(PageSize.A8);
            document.addCreationDate();
            document.addAuthor("MIFUCAM");
            document.addCreator("Valdes FOTSO");

            //Font Setting
            BaseColor colorAccent = new BaseColor(0,153,204,255);
            float fontSize = 15.0f;
            float valueFontSize = 18.0f;

            //Custom font
//            BaseFont fontName = BaseFont.createFont("assets/fonts/brandon_medium.otf","UTF-8" , BaseFont.EMBEDDED );
            BaseFont fontName = BaseFont.createFont("assets/fonts/brandon_medium.otf",BaseFont.IDENTITY_H , BaseFont.EMBEDDED );
//            BaseFont bf = BaseFont.createFont("arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            //Create Title of document
            Font titleFont = new Font(fontName,16.0f,Font.NORMAL, BaseColor.BLACK);

            addNewItem(document, "DEMANDE DE CREDIT", Element.ALIGN_CENTER, titleFont);

            addNewItem(document, "Ref: "+ demandeCreditModele.getDcNumDossier(), Element.ALIGN_CENTER, titleFont);

            //Add More
            Font orderNumberFont = new Font(fontName, fontSize, Font.NORMAL, colorAccent);
//            addNewItem(document, "Compagnie:", Element.ALIGN_LEFT, orderNumberFont);

            Font orderNumberValueFont = new Font(fontName, valueFontSize, Font.NORMAL, BaseColor.BLACK);

            //More 1
            addNewItemWithLeftAndRight(document, "CAISSE:", MyData.CAISSE_NAME, orderNumberFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "GUICHET:", MyData.GUICHET_NAME, orderNumberFont, orderNumberValueFont);


            addLineSeperator(document);
            //More 2
            String dateOperation = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss",
                    Locale.getDefault()).format(System.currentTimeMillis());
            addNewItemWithLeftAndRight(document, "Date opération:", dateOperation, orderNumberFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Opérateur:", MyData.USER_NOM + " "+ MyData.USER_PRENOM, orderNumberFont, orderNumberValueFont);


            addLineSeperator(document);


            addNewItem(document, "Détails Opération", Element.ALIGN_CENTER, titleFont);

            addLineSeperator(document);

            //Item 1
            addNewItemWithLeftAndRight(document, "Nom :", adNom+" "+adPrenom, titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "N° Manuel:", adNumManuel, titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Activité principale:", demandeCreditModele.getDcActivitePrinc(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Produit crédit:", spinnerListCredit.getSelectedItem().toString(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Objet crédit:", spinnerListObjetCredit.getSelectedItem().toString(), titleFont, orderNumberValueFont);
            addLineSeperator(document);
            //Item 2
            addNewItemWithLeftAndRight(document, "Nom avaliste 1:", DcNomAval1ET.getText().toString()+" "+DcPrenomAval1ET.getText().toString(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Nom avaliste 2:", DcNomAval2ET.getText().toString()+" "+DcPrenomAval2ET.getText().toString(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Nom avaliste 3:", DcNomAval3ET.getText().toString()+" "+DcPrenomAval3ET.getText().toString(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Garanties proposées:", demandeCreditModele.getDcGarantiesProp(), titleFont, orderNumberValueFont);
            addLineSeperator(document);
            //Item 3
            try {
                addNewItemWithLeftAndRight(document, "Montant sollicité:", defaultFormat.format(parseDouble(demandeCreditModele.getDcMtPretSollicite()))+" (" +
                        ""+ FrenchNumberToWords.convert(Double.parseDouble(demandeCreditModele.getDcMtPretSollicite()))+" )", titleFont, orderNumberValueFont);
                addNewItemWithLeftAndRight(document, "Périodicité:", demandeCreditModele.getDcPeriodRemb(), titleFont, orderNumberValueFont);
                addNewItemWithLeftAndRight(document, "Durée de remboursement:", demandeCreditModele.getDcDureeRemb().concat(" ").concat("mois"), titleFont, orderNumberValueFont);
                addNewItemWithLeftAndRight(document, "Date de 1ère échéance:", demandeCreditModele.getDcDatePremEch(), titleFont, orderNumberValueFont);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            addNewItemWithLeftAndRight(document, "Type de transaction:", transfert.getTrTypTransf(), titleFont, orderNumberValueFont);

            addLineSeperator(document);

            //Signatures
            addLineSpace(document);
            addLineSpace(document);

            addNewItemWithLeftAndRight(document, "Signature demandeur:", "Signature Opérateur:", titleFont, orderNumberValueFont);

            document.close();

            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

            printPDF();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void printPDF() {
        PrintManager printManager = (PrintManager)getSystemService(Context.PRINT_SERVICE);
        try {
            PrintDocumentAdapter printDocumentAdapter = new PdfDocumentAdapter(DemandeCredit.this, Common.getAppPath(DemandeCredit.this)+"demande_credit_mifucam.pdf");
            printManager.print("Document", printDocumentAdapter, new PrintAttributes.Builder().build());

        }catch (Exception ex){
            Log.e("VALDES",""+ex.getMessage());
        }
    }
    private void addNewItemWithLeftAndRight(Document document, String textLeft, String textRight, Font textLeftFont, Font textRightFont) throws DocumentException {
        Chunk chunkTextLeft = new Chunk(textLeft, textRightFont); //ce n'est pas une erreur
        Chunk chunkTextRight= new Chunk(textRight, textRightFont);
        Paragraph p = new Paragraph(chunkTextLeft);
        p.add(new Chunk(new VerticalPositionMark()));
        p.add(chunkTextRight);
        document.add(p);
    }

    private void addLineSeperator(Document document) throws DocumentException {
        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineColor(new BaseColor(0,0,0,68));
        addLineSpace(document);
        document.add(new Chunk(lineSeparator));
        addLineSpace(document);
    }

    private void addLineSpace(Document document) throws DocumentException {
        document.add(new Paragraph(""));
    }

    private void addNewItem(Document document, String text, int align, Font font) throws DocumentException {

        Chunk chunk = new Chunk(text, font);
        Paragraph paragraph = new Paragraph(chunk);
        paragraph.setAlignment(align);
        document.add(paragraph);


    }
}