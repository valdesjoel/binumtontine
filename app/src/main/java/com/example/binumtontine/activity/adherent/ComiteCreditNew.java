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
import com.example.binumtontine.activity.ListNomPrenomAvalisteDemandeCredit;
import com.example.binumtontine.activity.ListPlageFCV;
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
import com.example.binumtontine.modele.Echeancier;
import com.google.android.material.textfield.TextInputLayout;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
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

import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class ComiteCreditNew extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    /*Begin*/
         //to fetchProduitList by caisse and guichet ID
    private static final String KEY_CREDIT_CAISSE_ID = "CrCaisseId";
    private static final String KEY_GX_NUMERO = "CrGuichetId";
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
    private DatePickerDialog DcDateDecisiion_PickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date
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
    private EditText DcDateDecisiionET;
    //private EditText DcPeriodRembET; //a demander à diff l'unité

    private RadioButton rb_DcPeriodRemb_HEB;
    private RadioButton rb_DcPeriodRemb_QUI;
    private RadioButton rb_DcPeriodRemb_MEN;
    private RadioButton rb_DcPeriodRemb_BIM;
    private RadioButton rb_DcPeriodRemb_TRI;
    private RadioButton rb_DcPeriodRemb_SEM;
    private RadioButton rb_DcPeriodRemb_ANN;

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
    private EditText DcMtTotalBesoinsET;
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

    private RadioButton rb_DcDecisionComGesNon;
    private RadioButton rb_DcDecisionComGesOui;

    private EditText DcDateSignatET;
    private EditText DcDetailsET;
    private EditText DcMotivationsET;
    private TextInputLayout til_DcDetails;
    private TextInputLayout til_DcMotivations;


    private EditText DcDescriptionEditText;
    private EditText DcDureeMoisEditText;
    private EditText DcTauxInterEditText;
    private EditText DcCreditAnterieur1EditText;
    private EditText DcCreditAnterieur2EditText;
    private EditText DcCreditAnterieur3EditText;
    private EditText DcCumulDetteEncoursEditText;
    private EditText DcNatActivEditText;
    private EditText DcMtMoyEpargneEditText;
    private EditText DcMtSoldeEpargneEditText;
    private EditText DcFreqRevenusEditText;
    private EditText DcMtRevenusFixeEditText;
    private EditText DcMtAutreRevenusEditText;
//    private EditText DcMtDmdeEditText;
//    private EditText DcNumDossEditText;


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
    private ArrayList<Category> comiteCreditList;
    List<Integer> comiteCreditListID = new ArrayList<Integer>();
    List<Echeancier> listEcheanciers = new ArrayList<>();

    //Taux d'intérêt annuel TIA
    private static final String KEY_TIA_DEBUT = "CcTiaDebut";
    private static final String KEY_TIA_FIN = "CcTiaFin";
    private static final String KEY_TIA_VALEUR = "CcTiaValeur";
    private static final String KEY_TIA_BASE = "CcTiaBase";
    private static final String KEY_TIA_NATURE = "CcTiaNature";

    private String tabEcCredit ="";
    private String tabEcNumVersion ="";
    private String tabEcDateRemb ="";
    private String tabEcMtCapital ="";
    private String tabEcMtInteret ="";
    
    
    private int demandeCreditID;
    private Spinner spinnerListCredit;
    private int objetCreditID;
    private int comiteCreditID;
    private Spinner spinnerListObjetCredit;
    private Spinner spinnerListComiteCredit;
    private TextView tvAdherentNom;
    private TextView tvAdherentNumManuel;
    private TextView tvAdherentCode;
    private TextView tvAdherentNumDossier;
    /*end manage*/

    private Button addButton;
    private Button showEcheancierButton;
    private Button annulerButton;
    private int success;
    private ProgressDialog pDialog;
    private ProgressDialog pDialogDc;
    private ProgressDialog pDialogDmdeCredit;
    private ProgressDialog pDialogFetchProduitCreditList;
    private String dcMtAutreRevenus;
    private String dcMtRevenusFixe;
    private String dcFreqRevenus;
    private String dcMtSoldeEpargne;
    private String dcMtMoyEpargne;
    private String dcNatActiv;
    private String dcCumulDetteEncours;
    private String dcCreditAnterieur3;
    private String dcCreditAnterieur2;
    private String dcCreditAnterieur1;
    private String dcDureeMois;
    private String dcDescription;
    private String dcTauxInter;
    private RadioButton rb_avalisteCreditNon;
    private RadioButton rb_avalisteCreditOui;
    private LinearLayout tv_plage_CrTypTxInter;
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
    private TextInputLayout til_DcDateDecisiion;
    private TextInputLayout til_DcMtRestARemb;
    private TextInputLayout til_DcMtPrincPret;
    private TextInputLayout til_DcNaturePret;
    private TextInputLayout til_DcPretInstit;
    private TextInputLayout til_DcDateSignat;
    public static String CvDateHCree="";
    private TextView tv_DcCpteEAVPrinc;
    private TextView tv_DcMtSoldeEAV;
    private TextView tv_date_ouverture;
    private TextView header_eav_adherent;
    private Adherent adherent;

    private String compteId;
    private String compteSolde;
    private String libelleProduit;
    private static final String KEY_COMPTE_ID = "Numero";

    private static final String KEY_MONTANT_COMPTE = "MtSolde";
    private static final String KEY_TYPE_OPERATION = "TypeOperation";
    private static final String KEY_ADHERENT = "ADHERENT";
    private static final String KEY_LIBELLE_PRODUIT = "Libelle";
    private TextView tv_list_membres_comite_dmde_cred;
    private NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comite_credit_adherent);
        demandeCreditModele = new DemandeCreditModele();
        defaultFormat.setCurrency(Currency.getInstance("XAF"));
        header_eav_adherent = (TextView) findViewById(R.id.header_eav_adherent);
        header_eav_adherent.setText("Avis du comité de crédit");
        Intent intent = getIntent();

        /*START GETTING EXTRA*/
        compteId = intent.getStringExtra(KEY_COMPTE_ID);

        compteSolde = intent.getStringExtra(KEY_MONTANT_COMPTE);
        //typeOperation = intent.getStringExtra(KEY_TYPE_OPERATION);
        libelleProduit = intent.getStringExtra(KEY_LIBELLE_PRODUIT);
        Bundle bundle = intent.getExtras();
        adherent = (Adherent) bundle.getSerializable(KEY_ADHERENT);
        adNom = adherent.getAdNom();
        adPrenom = adherent.getAdPrenom();
        adNumManuel = adherent.getAdNumManuel();
        adCode = adherent.getAdCode();
        /*END GETTING EXTRA*/

//        adherentId = intent.getStringExtra(KEY_ADHERENT_ID);
        adherentId = adherent.getAdNumero();

        DcNumDossierET = (EditText) findViewById(R.id.input_txt_DcNumDossier);
        DcMtEpargnObligET = (EditText) findViewById(R.id.input_txt_DcMtEpargnOblig);
        DcMtPartSocialET = (EditText) findViewById(R.id.input_txt_DcMtPartSocial);
        DcActivitePrincET = (EditText) findViewById(R.id.input_txt_DcActivitePrinc);
        DcNbAnneeExperET = (EditText) findViewById(R.id.input_txt_DcNbAnneeExper);
        DcAutresActiviteET = (EditText) findViewById(R.id.input_txt_DcAutresActivite);
        DcAutresActiviteET = (EditText) findViewById(R.id.input_txt_DcAutresActivite);


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
        //DcPeriodRembET = (EditText) findViewById(R.id.input_txt_DcPeriodRemb);

        rb_DcPeriodRemb_HEB = (RadioButton) findViewById(R.id.rb_DcPeriodRemb_HEB);
        rb_DcPeriodRemb_QUI = (RadioButton) findViewById(R.id.rb_DcPeriodRemb_QUI);
        rb_DcPeriodRemb_MEN = (RadioButton) findViewById(R.id.rb_DcPeriodRemb_MEN);
        rb_DcPeriodRemb_BIM = (RadioButton) findViewById(R.id.rb_DcPeriodRemb_BIM);
        rb_DcPeriodRemb_TRI = (RadioButton) findViewById(R.id.rb_DcPeriodRemb_TRI);
        rb_DcPeriodRemb_SEM = (RadioButton) findViewById(R.id.rb_DcPeriodRemb_SEM);
        rb_DcPeriodRemb_ANN = (RadioButton) findViewById(R.id.rb_DcPeriodRemb_ANN);


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
        til_DcDateDecisiion = (TextInputLayout) findViewById(R.id.input_layout_DcDateDecisiion);
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

        rb_DcDecisionComGesOui = (RadioButton) findViewById(R.id.rb_DcDecisionComGes_oui);
        rb_DcDecisionComGesNon = (RadioButton) findViewById(R.id.rb_DcDecisionComGes_non);

        DcMotivationsET = (EditText) findViewById(R.id.input_txt_DcMotivations);
        til_DcMotivations = (TextInputLayout) findViewById(R.id.input_layout_DcMotivations);

        tv_DcCpteEAVPrinc = (TextView) findViewById(R.id.tv_DcCpteEAVPrinc);
        tv_DcMtSoldeEAV = (TextView) findViewById(R.id.tv_DcMtSoldeEAV);
        tv_date_ouverture = (TextView) findViewById(R.id.tv_date_ouverture);

        tv_list_membres_comite_dmde_cred = (TextView) findViewById(R.id.tv_plage_membres_comite_dmde_cred);

        spinnerListCredit = (Spinner) findViewById(R.id.spn_list_credit);
        spinnerListObjetCredit = (Spinner) findViewById(R.id.spn_list_objet_credit);
        spinnerListComiteCredit = (Spinner) findViewById(R.id.spn_list_DcCmNumero);
        tvAdherentNom = (TextView) findViewById(R.id.tv_nom_adherent);
        tvAdherentNom.setText(adNom+"\n"+adPrenom);
        tvAdherentNumManuel = (TextView) findViewById(R.id.tv_num_manuel_adherent);
        tvAdherentNumManuel.setText(adNumManuel);
        tvAdherentCode = (TextView) findViewById(R.id.tv_code_adherent);
        tvAdherentCode.setText(adCode);
//        DcDateDecisiion;DcDecisionComGes;DcMtAccorde;DcDureeAccord; DcMotivations;

        creditList = new ArrayList<Category>();
        objetCreditList = new ArrayList<Category>();
        comiteCreditList = new ArrayList<Category>();
        // spinner item select listener
        spinnerListCredit.setOnItemSelectedListener(ComiteCreditNew.this);
        spinnerListObjetCredit.setOnItemSelectedListener(ComiteCreditNew.this);
        spinnerListComiteCredit.setOnItemSelectedListener(ComiteCreditNew.this);
        new GetAdherentDemandeCreditAsyncTask().execute();
        new GetProduitCreditList().execute();
        new FetchObjetCreditAsyncTask().execute();
        new FetchComiteCreditAsyncTask().execute();
        new FetchDemandeCreditDetailsAsyncTask().execute();

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
        showEcheancierButton = (Button) findViewById(R.id.btn_show_echeancier);
        addButton = (Button) findViewById(R.id.btn_save_demande_credit);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(ComiteCreditNew.this,
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
                    Toast.makeText(ComiteCreditNew.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        showEcheancierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    try {
                        if (validateField()) {
                            generateEcheance();
                            Intent intent = new Intent(getApplicationContext(),
                                    EcheancierCredit.class);
                            EcheancierCredit.listEcheancierDemandeCredit = listEcheanciers;
                            startActivityForResult(intent, 20);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(ComiteCreditNew.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        //tv_plage_membres_comite_dmde_cred
        tv_list_membres_comite_dmde_cred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    if (comiteCreditID==0){
                        notificationComiteCreditIDVide();
                        return;
                    }
                    ListPlageFCV.IS_TO_CREATE_OR_TO_UPDATE = true;
                    Intent i = new Intent(ComiteCreditNew.this,GetMembreComiteCredit.class);
                    i.putExtra(ComiteCredit.KEY_CmNumero, comiteCreditID+"");
                    startActivityForResult(i,20);

                } else {
                    Toast.makeText(ComiteCreditNew.this,
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
//                                Intent  i = new Intent(CreateOperationTransfertEnvoyer.this, ListGuichetCaisse.class);
                                Intent  i = new Intent(ComiteCreditNew.this, ListNomPrenomAvalisteDemandeCredit.class);
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
//                                Intent  i = new Intent(CreateOperationTransfertEnvoyer.this, ListGuichetCaisse.class);
                                Intent  i = new Intent(ComiteCreditNew.this, ListNomPrenomAvalisteDemandeCredit.class);
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
//                                Intent  i = new Intent(CreateOperationTransfertEnvoyer.this, ListGuichetCaisse.class);
                                Intent  i = new Intent(ComiteCreditNew.this, ListNomPrenomAvalisteDemandeCredit.class);
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

        DcDateDecisiionET = (EditText) findViewById(R.id.input_txt_DcDateDecisiion);
        DcDateDecisiionET.requestFocus();
        DcDateDecisiionET.setInputType(InputType.TYPE_NULL);





    }

    private void setDateTimeField() {
        DcDateET.setOnClickListener(this);
        DcDateSignatET.setOnClickListener(this);
        DcDatePremEchET.setOnClickListener(this);
        DcDateDecisiionET.setOnClickListener(this);


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

        DcDateDecisiion_PickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                DcDateDecisiionET.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));



    }
    private void generateEcheance() throws Exception {
        listEcheanciers.clear();

        double montantCredit = Double.parseDouble(this.demandeCreditModele.getDcMtAccorde());
        double montantInteret = 0.0;

        double taux = 18;
        int nbreEcheance = 1;

        nbreEcheance = Integer.parseInt(this.demandeCreditModele.getDcDureeRemb());
        montantInteret = calculTauxInteret(montantCredit, taux);

        Date dateDebutEcheance = new SimpleDateFormat("dd-MM-yyyy").parse(this.demandeCreditModele.getDcDatePremEch());
        int mois_suivant = 0;
        Double sommeCapital = 0.0;
        Double sommeInteret = 0.0;

        Echeancier echeancier;
     /*   this.setDcMtAccorde(String.valueOf(montantCredit));
//        this.setDcTauxInt(Float.parseFloat(taux+""));
        this.setDcDatePremEch(DateTools.formatterDateToString(dateDebutEcheance));
//        this.setDcDureeRemb(String.valueOf(nbreEcheance));
*/
        for(int i=0; i<nbreEcheance; i++){
            echeancier = new Echeancier();
            echeancier.setEcNumVersion(i+1);
            if(i+1==nbreEcheance){
                echeancier.setEcMtCapital((montantCredit-sommeCapital));
                echeancier.setEcTotalCapRemb(0);
                echeancier.setEcTotalIntRemb(0);
                echeancier.setEcMtInteret(montantInteret - sommeInteret);

            }else{
                echeancier.setEcMtCapital(Math.round(montantCredit/nbreEcheance));
                echeancier.setEcMtInteret(Math.round(montantInteret/nbreEcheance));

                sommeCapital += echeancier.getEcMtCapital();
                sommeInteret += echeancier.getEcMtInteret();

                echeancier.setEcTotalCapRemb(montantCredit-sommeCapital);
                echeancier.setEcTotalIntRemb(montantInteret-sommeInteret);

            }

//            echeancier.setEcDateRemb(DateUtils.addMonths(dateDebutEcheance, i+1));
            echeancier.setEcDateRemb(DateUtils.addMonths(dateDebutEcheance, i));

            echeancier.setEcCredit(this.demandeCreditModele);
            /*echeancier.setEcStatut(0);
            echeancier.setEcMontantCapitalPaye(0);*/

            listEcheanciers.add(echeancier);

        }





    }
    private double calculTauxInteret (Double capital, double taux){

        return (capital*taux)/100 ;
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
        }else if (v == DcDateDecisiionET){
//            DcDateDecisiionET_PickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            DcDateDecisiion_PickerDialog.show();
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
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(ComiteCreditNew.this,
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

        //tvCaisse.setText("");

        for (int i = 0; i < objetCreditList.size(); i++) {
            lables.add(objetCreditList.get(i).getName());//recupère les noms
            objetCreditListID.add(objetCreditList.get(i).getId()); //recupère les Id
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(ComiteCreditNew.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerListObjetCredit.setAdapter(spinnerAdapter);
    }
    /**
     * Adding Objet Credit spinner data
     * */
    private void populateComiteCreditSpinner() {
        List<String> lables = new ArrayList<String>();

        //tvCaisse.setText("");

        for (int i = 0; i < comiteCreditList.size(); i++) {
            lables.add(comiteCreditList.get(i).getName());//recupère les noms
            comiteCreditListID.add(comiteCreditList.get(i).getId()); //recupère les Id
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(ComiteCreditNew.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerListComiteCredit.setAdapter(spinnerAdapter);
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
            pDialogDmdeCredit = new ProgressDialog(ComiteCreditNew.this);
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
                        CvDateHCree = movie.getString("CvDateHCree");
                    }
//                    demandeCreditModele.setDcMtSoldeEAV(jsonObject.getString("DcMtSoldeEAV")) ;
////                    demandeCreditModele.setDcMtSoldeEAV(jsonObject.getString(DemandeCreditModele.KEY_DcMtSoldeEAV)) ;





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
            pDialogFetchProduitCreditList = new ProgressDialog(ComiteCreditNew.this);
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
//            httpParams.add(new BasicNameValuePair(KEY_EV_GUICHET_ID, String.valueOf(MyData.GUICHET_ID)));
//            httpParams.put(KEY_GX_NUMERO, String.valueOf(MyData.GUICHET_ID));
//            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_credit_by_caisse.php", ServiceHandler.GET, httpParams);
            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_credit_by_guichet.php", ServiceHandler.GET, httpParams);
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
    /**
     * Async task to get all objet credit categories
     * */
    private class FetchObjetCreditAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ComiteCreditNew.this);
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
     * Async task to get all objet credit categories
     * */
    private class FetchComiteCreditAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*pDialog = new ProgressDialog(ComiteCreditNew.this);
            pDialog.setMessage("Fetching objets's list..");
            pDialog.setCancelable(false);
            pDialog.show();*/

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair(ComiteCredit.KEY_CmCaisse, String.valueOf(MyData.CAISSE_ID)));
//            httpParams.add(new BasicNameValuePair(KEY_EV_GUICHET_ID, String.valueOf(MyData.GUICHET_ID)));
            String json = (String) jsonParser.makeServiceCall( BASE_URL + "fetch_all_comite_credit.php", ServiceHandler.GET, httpParams);

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
                            Category cat = new Category(catObj.getInt(ComiteCredit.KEY_CmNumero),
                                    catObj.getString(ComiteCredit.KEY_CmNom));
                            comiteCreditList.add(cat);
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
           /* if (pDialog.isShowing())
                pDialog.dismiss();*/
            populateComiteCreditSpinner();
        }

    }


    /**
     * Fetches single movie details from the server
     */
    private class FetchDemandeCreditDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialogDc = new ProgressDialog(ComiteCreditNew.this);
            pDialogDc.setMessage("Veuillez patienter...");
            pDialogDc.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialogDc.setIndeterminate(false);
            pDialogDc.setCancelable(false);
            pDialogDc.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(DemandeCreditModele.KEY_DcNumero, compteId+"");
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_demande_credit_details.php", "GET", httpParams);
            Log.e("jsonObject", jsonObject+"");
            try {
//                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject creditJson;
//                if (success == 1) {
                //Parse the JSON response
//                    creditJson = jsonObject.getJSONObject(KEY_DATA);
                demandeCreditModele = new DemandeCreditModele();
                demandeCreditModele.setDcNumero(jsonObject.getString(DemandeCreditModele.KEY_DcNumero));
                demandeCreditModele.setDcMembre(jsonObject.getString(DemandeCreditModele.KEY_DcMembre));
                demandeCreditModele.setDcNumDossier(jsonObject.getString(DemandeCreditModele.KEY_DcNumDossier));
                demandeCreditModele.setDcCpteEAVPrinc(jsonObject.getString(DemandeCreditModele.KEY_DcCpteEAVPrinc));
                demandeCreditModele.setDcMtSoldeEAV(jsonObject.getString(DemandeCreditModele.KEY_DcMtSoldeEAV));
                demandeCreditModele.setDcMtEpargnOblig(jsonObject.getString(DemandeCreditModele.KEY_DcMtEpargnOblig));
                demandeCreditModele.setDcMtPartSocial(jsonObject.getString(DemandeCreditModele.KEY_DcMtPartSocial));
                demandeCreditModele.setDcActivitePrinc(jsonObject.getString(DemandeCreditModele.KEY_DcActivitePrinc));
                demandeCreditModele.setDcNbAnneeExper(jsonObject.getString(DemandeCreditModele.KEY_DcNbAnneeExper));
                demandeCreditModele.setDcAutresActivite(jsonObject.getString(DemandeCreditModele.KEY_DcAutresActivite));
                demandeCreditModele.setDcIsEmpruntPasse(jsonObject.getString(DemandeCreditModele.KEY_DcIsEmpruntPasse));
                demandeCreditModele.setDcEmpruntPasseOu(jsonObject.getString(DemandeCreditModele.KEY_DcEmpruntPasseOu));
                demandeCreditModele.setDcIsEchRembResp(jsonObject.getString(DemandeCreditModele.KEY_DcIsEchRembResp));
                demandeCreditModele.setDcPourqRembSiNon(jsonObject.getString(DemandeCreditModele.KEY_DcPourqRembSiNon));
                demandeCreditModele.setDcPretEnCours(jsonObject.getString(DemandeCreditModele.KEY_DcPretEnCours));
                demandeCreditModele.setDcPretInstit(jsonObject.getString(DemandeCreditModele.KEY_DcPretInstit));
                demandeCreditModele.setDcNaturePret(jsonObject.getString(DemandeCreditModele.KEY_DcNaturePret));
                demandeCreditModele.setDcMtPrincPret(jsonObject.getString(DemandeCreditModele.KEY_DcMtPrincPret));
                demandeCreditModele.setDcMtRestARemb(jsonObject.getString(DemandeCreditModele.KEY_DcMtRestARemb));
                demandeCreditModele.setDcMtPretSollicite(jsonObject.getString(DemandeCreditModele.KEY_DcMtPretSollicite));
                demandeCreditModele.setDcObjet(jsonObject.getString(DemandeCreditModele.KEY_DcObjet));
                demandeCreditModele.setDcDescriptPret(jsonObject.getString(DemandeCreditModele.KEY_DcDescriptPret));
                demandeCreditModele.setDcDureeRemb(jsonObject.getString(DemandeCreditModele.KEY_DcDureeRemb));

//                demandeCreditModele.setDcDatePremEch(jsonObject.getString(DemandeCreditModele.KEY_DcDatePremEch));
                String startDateString = jsonObject.getString(DemandeCreditModele.KEY_DcDatePremEch);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                System.out.println(LocalDate.parse(startDateString, formatter2).format(formatter));
                demandeCreditModele.setDcDatePremEch(LocalDate.parse(startDateString, formatter2).format(formatter));
                demandeCreditModele.setDcPeriodRemb(jsonObject.getString(DemandeCreditModele.KEY_DcPeriodRemb));
                demandeCreditModele.setDcNbJrDiffere(jsonObject.getString(DemandeCreditModele.KEY_DcNbJrDiffere));
                demandeCreditModele.setDcGarantiesProp(jsonObject.getString(DemandeCreditModele.KEY_DcGarantiesProp));
                demandeCreditModele.setDcIsAvaliste1(jsonObject.getString(DemandeCreditModele.KEY_DcIsAvaliste1));
                demandeCreditModele.setDcNumAvaliste1(jsonObject.getString(DemandeCreditModele.KEY_DcNumAvaliste1));
                demandeCreditModele.setDcMtSoldAvalist1(jsonObject.getString(DemandeCreditModele.KEY_DcMtSoldAvalist1));
                demandeCreditModele.setDcIsAvaliste2(jsonObject.getString(DemandeCreditModele.KEY_DcIsAvaliste2));
                demandeCreditModele.setDcNumAvaliste2(jsonObject.getString(DemandeCreditModele.KEY_DcNumAvaliste2));
                demandeCreditModele.setDcMtSoldAvalist2(jsonObject.getString(DemandeCreditModele.KEY_DcMtSoldAvalist2));
                demandeCreditModele.setDcIsAvaliste3(jsonObject.getString(DemandeCreditModele.KEY_DcIsAvaliste3));
                demandeCreditModele.setDcNumAvaliste3(jsonObject.getString(DemandeCreditModele.KEY_DcNumAvaliste3));
                demandeCreditModele.setDcMtSoldAvalist3(jsonObject.getString(DemandeCreditModele.KEY_DcMtSoldAvalist3));
                demandeCreditModele.setDcIsBesoinsAFin(jsonObject.getString(DemandeCreditModele.KEY_DcIsBesoinsAFin));
                demandeCreditModele.setDcMtTotalBesoins(jsonObject.getString(DemandeCreditModele.KEY_DcMtTotalBesoins));
                demandeCreditModele.setDcMotivationPret(jsonObject.getString(DemandeCreditModele.KEY_DcMotivationPret));
                demandeCreditModele.setDcIsPhotocopieCNI(jsonObject.getString(DemandeCreditModele.KEY_DcIsPhotocopieCNI));
                demandeCreditModele.setDcIsCpteExploit(jsonObject.getString(DemandeCreditModele.KEY_DcIsCpteExploit));
                demandeCreditModele.setDcIsDmdeManuscr(jsonObject.getString(DemandeCreditModele.KEY_DcIsDmdeManuscr));
                demandeCreditModele.setDcIsPceGarantie(jsonObject.getString(DemandeCreditModele.KEY_DcIsPceGarantie));
                demandeCreditModele.setDcIsAutresDocum(jsonObject.getString(DemandeCreditModele.KEY_DcIsAutresDocum));
                demandeCreditModele.setDcAutresDocum(jsonObject.getString(DemandeCreditModele.KEY_DcAutresDocum));
                demandeCreditModele.setDcIsSignatAdh(jsonObject.getString(DemandeCreditModele.KEY_DcIsSignatAdh));
                demandeCreditModele.setDcDateSignat(jsonObject.getString(DemandeCreditModele.KEY_DcDateSignat));
                demandeCreditModele.setDcDate(jsonObject.getString(DemandeCreditModele.KEY_DcDate));
                demandeCreditModele.setDcDetails(jsonObject.getString(DemandeCreditModele.KEY_DcDetails));
                demandeCreditModele.setDcAgentCredit(jsonObject.getString(DemandeCreditModele.KEY_DcAgentCredit));
                demandeCreditModele.setDcObservAgCred(jsonObject.getString(DemandeCreditModele.KEY_DcObservAgCred));
                demandeCreditModele.setDcAvisAgCredit(jsonObject.getString(DemandeCreditModele.KEY_DcAvisAgCredit));
                demandeCreditModele.setDcDateDecisiion(jsonObject.getString(DemandeCreditModele.KEY_DcDateDecisiion));
                demandeCreditModele.setDcDecisionComGes(jsonObject.getString(DemandeCreditModele.KEY_DcDecisionComGes));
                demandeCreditModele.setDcMtAccorde(jsonObject.getString(DemandeCreditModele.KEY_DcMtAccorde));
                demandeCreditModele.setDcDureeAccord(jsonObject.getString(DemandeCreditModele.KEY_DcDureeAccord));
                demandeCreditModele.setDcMotivations(jsonObject.getString(DemandeCreditModele.KEY_DcMotivations));
                demandeCreditModele.setDcEtapeCredit(jsonObject.getString(DemandeCreditModele.KEY_DcEtapeCredit));
                demandeCreditModele.setDcMtDetteHorsBilan(jsonObject.getString(DemandeCreditModele.KEY_DcMtDetteHorsBilan));
                demandeCreditModele.setDcTotPayeDetteHorsBilan(jsonObject.getString(DemandeCreditModele.KEY_DcTotPayeDetteHorsBilan));
                demandeCreditModele.setDcDateHCree(jsonObject.getString(DemandeCreditModele.KEY_DcDateHCree));
                demandeCreditModele.setDcUserCree(jsonObject.getString(DemandeCreditModele.KEY_DcUserCree));




            } catch (Exception e) {
                e.printStackTrace();
            } /*catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }*/
            return null;
        }

        protected void onPostExecute(String result) {
            pDialogDc.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    //Populate the Edit Texts once the network activity is finished executing
                    try {
                        DcDateET.setText(demandeCreditModele.getDcDate());
                        DcNumDossierET.setText(demandeCreditModele.getDcNumDossier());
                        DcMtEpargnObligET.setText(demandeCreditModele.getDcMtEpargnOblig());
                        DcMtPartSocialET.setText(demandeCreditModele.getDcMtPartSocial());
                        DcActivitePrincET.setText(demandeCreditModele.getDcActivitePrinc());
                        DcNbAnneeExperET.setText(demandeCreditModele.getDcNbAnneeExper());
                        DcAutresActiviteET.setText(demandeCreditModele.getDcAutresActivite());
                        if(demandeCreditModele.getDcIsEmpruntPasse().equals("Y")){
                            rb_DcIsEmpruntPasseOui.setChecked(true);
                            onRadioButtonClicked(rb_DcIsEmpruntPasseOui);
                        }else{
                            rb_DcIsEmpruntPasseNon.setChecked(true);
                            onRadioButtonClicked(rb_DcIsEmpruntPasseNon);
                        }
                        DcEmpruntPasseOuET.setText(demandeCreditModele.getDcEmpruntPasseOu());

                        if(demandeCreditModele.getDcIsEchRembResp().equals("N")){
                            rb_DcIsEchRembRespNon.setChecked(true);
                            onRadioButtonClicked(rb_DcIsEchRembRespNon);
                        }else{
                            rb_DcIsEchRembRespOui.setChecked(true);
                            onRadioButtonClicked(rb_DcIsEchRembRespOui);
                        }
                        DcPourqRembSiNonET.setText(demandeCreditModele.getDcPourqRembSiNon());

                        //            DcPretEnCours DEBUT
                        if(demandeCreditModele.getDcPretEnCours().equals("Y")){
                            rb_DcPretEnCoursOui.setChecked(true);
                            onRadioButtonClicked(rb_DcPretEnCoursOui);
                        }else{
                            rb_DcPretEnCoursNon.setChecked(true);
                            onRadioButtonClicked(rb_DcPretEnCoursNon);
                        }
                        DcPretInstitET.setText(demandeCreditModele.getDcPretInstit());
                        DcNaturePretET.setText(demandeCreditModele.getDcNaturePret());
                        DcMtPrincPretET.setText(demandeCreditModele.getDcMtPrincPret());
                        DcMtRestARembET.setText(demandeCreditModele.getDcMtRestARemb());

                        //            DcPretEnCours FIN
                        //4-
                        DcMtPretSolliciteET.setText(demandeCreditModele.getDcMtPretSollicite());
                        DcDescriptPretET.setText(demandeCreditModele.getDcDescriptPret());
                        DcDureeRembET.setText(demandeCreditModele.getDcDureeRemb());
                        DcDatePremEchET.setText(demandeCreditModele.getDcDatePremEch());
                        //            PERIODICITE DEBUT
                        if(demandeCreditModele.getDcPeriodRemb().equals("HEB")){
                            rb_DcPeriodRemb_HEB.setChecked(true);
                        }else if(demandeCreditModele.getDcPeriodRemb().equals("QUI")){
                            rb_DcPeriodRemb_QUI.setChecked(true);
                        }else if(demandeCreditModele.getDcPeriodRemb().equals("MEN")){
                            rb_DcPeriodRemb_MEN.setChecked(true);
                        }else if(demandeCreditModele.getDcPeriodRemb().equals("BIM")){
                            rb_DcPeriodRemb_BIM.setChecked(true);
                        }else if(demandeCreditModele.getDcPeriodRemb().equals("TRI")){
                            rb_DcPeriodRemb_TRI.setChecked(true);
                        }else if(demandeCreditModele.getDcPeriodRemb().equals("SEM")){
                            rb_DcPeriodRemb_SEM.setChecked(true);
                        }else if(demandeCreditModele.getDcPeriodRemb().equals("ANN")){
                            rb_DcPeriodRemb_ANN.setChecked(true);
                        }
                        //FIN PERIODICITE
                        DcNbJrDiffereET.setText(demandeCreditModele.getDcNbJrDiffere());
                        DcGarantiesPropET.setText(demandeCreditModele.getDcGarantiesProp());


                        //            Avaliste DEBUT
//                        Aval 1
                        if(demandeCreditModele.getDcIsAvaliste1().equals("Y")){
                            rb_DcIsAvaliste1Oui.setChecked(true);
                            onRadioButtonClicked(rb_DcIsAvaliste1Oui);
                        }else{
                            rb_DcIsAvaliste1Non.setChecked(true);
                            onRadioButtonClicked(rb_DcIsAvaliste1Non);
                        }
//                        DcNumAvaliste1.setText(demandeCreditModele.getDcNumAvaliste1());

//                        Aval 2
                        if(demandeCreditModele.getDcIsAvaliste2().equals("Y")){
                            rb_DcIsAvaliste2Oui.setChecked(true);
                            onRadioButtonClicked(rb_DcIsAvaliste2Oui);
                        }else{
                            rb_DcIsAvaliste2Non.setChecked(true);
                            onRadioButtonClicked(rb_DcIsAvaliste2Non);
                        }
//                        Aval 3
                        if(demandeCreditModele.getDcIsAvaliste3().equals("Y")){
                            rb_DcIsAvaliste3Oui.setChecked(true);
                            onRadioButtonClicked(rb_DcIsAvaliste3Oui);
                        }else{
                            rb_DcIsAvaliste3Non.setChecked(true);
                            onRadioButtonClicked(rb_DcIsAvaliste3Non);
                        }
//                        FIN AVALISTE
                        if(demandeCreditModele.getDcIsBesoinsAFin().equals("Y")){
                            rb_DcIsBesoinsAFinOui.setChecked(true);
                            onRadioButtonClicked(rb_DcIsBesoinsAFinOui);
                        }else{
                            rb_DcIsBesoinsAFinNon.setChecked(true);
                            onRadioButtonClicked(rb_DcIsBesoinsAFinNon);
                        }
                        DcMtTotalBesoinsET.setText(demandeCreditModele.getDcMtTotalBesoins());
                        DcMotivationPretET.setText(demandeCreditModele.getDcMotivationPret());


                        if(demandeCreditModele.getDcIsPhotocopieCNI().equals("Y")){
                            DcIsPhotocopieCNICB.setChecked(true);
                        }else{
                            DcIsPhotocopieCNICB.setChecked(false);
                        }
                        if(demandeCreditModele.getDcIsCpteExploit().equals("Y")){
                            DcIsCpteExploitCB.setChecked(true);
                        }else{
                            DcIsCpteExploitCB.setChecked(false);
                        }
                        if(demandeCreditModele.getDcIsDmdeManuscr().equals("Y")){
                            DcIsDmdeManuscrCB.setChecked(true);
                        }else{
                            DcIsDmdeManuscrCB.setChecked(false);
                        }
                        if(demandeCreditModele.getDcIsPceGarantie().equals("Y")){
                            DcIsPceGarantieCB.setChecked(true);
                        }else{
                            DcIsPceGarantieCB.setChecked(false);
                        }

                        if(demandeCreditModele.getDcIsAutresDocum().equals("Y")){
                            rb_DcIsAutresDocumOui.setChecked(true);
                            onRadioButtonClicked(rb_DcIsAutresDocumOui);
                        }else{
                            rb_DcIsAutresDocumNon.setChecked(true);
                            onRadioButtonClicked(rb_DcIsAutresDocumNon);
                        }
                        DcAutresDocumET.setText(demandeCreditModele.getDcAutresDocum());

                        if(demandeCreditModele.getDcIsSignatAdh().equals("Y")){
                            rb_DcIsSignatAdhOui.setChecked(true);
                            onRadioButtonClicked(rb_DcIsSignatAdhOui);
                        }else{
                            rb_DcIsSignatAdhNon.setChecked(true);
                            onRadioButtonClicked(rb_DcIsSignatAdhNon);
                        }
                        DcDateSignatET.setText(demandeCreditModele.getDcDateSignat());
                        //demandeCreditModele.setDcMembre(adherentId);

  /*

                        ET_CrNbreJrDelaiGrace.setText(monProduitCredit.getCrNbreJrDelaiGrace());
                        ET_CrNbreJrAvantDatEch.setText(monProduitCredit.getCrNbreJrAvantDatEch());
                        ET_CrNbreJrApreEchSiNHon.setText(monProduitCredit.getCrNbreJrApreEchSiNHon());
                        monProduitCredit= null; //a revoir
*/
//                    } catch (UnsupportedEncodingException e) {
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {

        int idSpinner = parent.getId();

        switch (idSpinner)
        {
            case R.id.spn_list_credit:
                // your stuff here
                demandeCreditID = creditListID.get(position);//pour recuperer l'ID du produit crédit selectionné
                break;
            case R.id.spn_list_objet_credit:
                // your stuff here
                objetCreditID = objetCreditListID.get(position);//pour recuperer l'ID de l'objet crédit selectionné
                break;
            case R.id.spn_list_DcCmNumero:
                // your stuff here
                comiteCreditID = comiteCreditListID.get(position);//pour recuperer l'ID de l'objet crédit selectionné
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
    private void bipError(){

        ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,150);
    }
    private void addEavAdherent() {
        if (validateField()) {

            //Taux d'intérêt annuel TIA
            for (int i=0; i<listEcheanciers.size();i++){/*
                tabEcCredit +=";"+listEcheanciers.get(i).getEcCredit().getDcNumero();
                tabEcNumVersion +=";"+listEcheanciers.get(i).getEcNumVersion();
                tabEcDateRemb +=";"+listEcheanciers.get(i).getEcDateRemb();
                tabEcMtCapital +=";"+listEcheanciers.get(i).getEcMtCapital();
                tabEcMtInteret +=";"+listEcheanciers.get(i).getEcMtInteret();
                tabEcTotalCapRemb +=";"+listEcheanciers.get(i).getEcTotalCapRemb();
                tabEcTotalIntRemb +=";"+listEcheanciers.get(i).getEcTotalIntRemb();
                tabEcMtPenalite +=";"+listEcheanciers.get(i).getEcMtPenalite();
                tabEcTotalPenaliteRemb +=";"+listEcheanciers.get(i).getEcTotalPenaliteRemb();
                tabEcMtPenalRetard +=";"+listEcheanciers.get(i).getEcMtPenalRetard();
                tabEcTotalPenalRetardRemd +=";"+listEcheanciers.get(i).getEcTotalPenalRetardRemd();
                tabEcIsSolde +=";"+listEcheanciers.get(i).getEcIsSolde();*/
//                tabEcDateHSolde +=";"+listEcheanciers.get(i).getEcDateHSolde();
//                tabEcDetails +=";"+listEcheanciers.get(i).getEcDetails();
            }
            new AddDcAsyncTask().execute();
        }
    }
    private boolean validateField() {
        boolean error = true;
        if (
             demandeCreditID !=0 && objetCreditID !=0 && !demandeCreditModele.getDcCpteEAVPrinc().equals("0") ) {
            demandeCreditModele.setDcMembre(adherentId);
            demandeCreditModele.setDcDate(DcDateET.getText().toString());
            demandeCreditModele.setDcNumDossier(DcNumDossierET.getText().toString());
            demandeCreditModele.setDcMtEpargnOblig(DcMtEpargnObligET.getText().toString());
            demandeCreditModele.setDcMtPartSocial(DcMtPartSocialET.getText().toString());
            demandeCreditModele.setDcActivitePrinc(DcActivitePrincET.getText().toString());
            demandeCreditModele.setDcNbAnneeExper(DcNbAnneeExperET.getText().toString());
            demandeCreditModele.setDcAutresActivite(DcAutresActiviteET.getText().toString());
            if (rb_DcIsEmpruntPasseOui.isChecked()){
                if (DcEmpruntPasseOuET.getText().toString().trim().isEmpty()){
                    til_DcEmpruntPasseOu.setError("Indiquer le lieu de l'emprunt");
                    bipError();
                    requestFocus(DcEmpruntPasseOuET);
                    return false;
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
                    bipError();
                    requestFocus(DcPourqRembSiNonET);
                    return false;
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
                    bipError();
                    requestFocus(DcPretInstitET);
                    return false;
                }else if (DcNaturePretET.getText().toString().trim().isEmpty()){
                    til_DcNaturePret.setError("Dire de quelle nature est ce prêt");
                    bipError();
                    requestFocus(DcNaturePretET);
                    return false;
                }else if (DcMtPrincPretET.getText().toString().trim().isEmpty()){
                    til_DcMtPrincPret.setError("Dire quel était le montant du Principal du Prêt");
                    bipError();
                    requestFocus(DcMtPrincPretET);
                    return false;
                }else if (DcMtRestARembET.getText().toString().trim().isEmpty()){
                    til_DcMtRestARemb.setError("Dire combien il reste à rembourser sur ce prêt");
                    bipError();
                    requestFocus(DcMtRestARembET);
                    return false;
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
                til_DcMtPretSollicite.setError("Indiquer le Montant accordé pour le prêt");
                bipError();
                requestFocus(DcMtPretSolliciteET);
                return false;
            }else{
                demandeCreditModele.setDcMtPretSollicite(DcMtPretSolliciteET.getText().toString());
                demandeCreditModele.setDcMtAccorde(DcMtPretSolliciteET.getText().toString());

            }

            demandeCreditModele.setDcObjet(objetCreditID+"");
//            demandeCreditModele.setDcProduit(objetCreditID+"");
            if (DcDescriptPretET.getText().toString().trim().isEmpty()){
                til_DcDescriptPret.setError("Indiquer la Description du prêt");
                bipError();
                requestFocus(DcDescriptPretET);
                return false;
            }else{
                demandeCreditModele.setDcDescriptPret(DcDescriptPretET.getText().toString());

            }
            if (DcDureeRembET.getText().toString().trim().isEmpty()){
                til_DcDureeRemb.setError("Indiquer la Durée du remboursement accordé en mois");
                bipError();
                requestFocus(DcDureeRembET);
                return false;
            }else{
                demandeCreditModele.setDcDureeRemb(DcDureeRembET.getText().toString());
                demandeCreditModele.setDcDureeAccord(DcDureeRembET.getText().toString());

            }
            if (DcDatePremEchET.getText().toString().trim().isEmpty()){
                til_DcDatePremEch.setError("Indiquer la Date de la première échéance du Prêt");
                bipError();
                requestFocus(DcDatePremEchET);
                return false;
            }else{
                demandeCreditModele.setDcDatePremEch(DcDatePremEchET.getText().toString());
            }
            if (DcDateDecisiionET.getText().toString().trim().isEmpty()){
                til_DcDateDecisiion.setError("Indiquer la Date de décision");
                bipError();
                requestFocus(DcDateDecisiionET);
                return false;
            }else{
                demandeCreditModele.setDcDateDecisiion(DcDateDecisiionET.getText().toString());
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
                bipError();
                requestFocus(DcNbJrDiffereET);
                return false;
            }else{
                demandeCreditModele.setDcNbJrDiffere(DcNbJrDiffereET.getText().toString());
            }
            if (DcGarantiesPropET.getText().toString().trim().isEmpty()){
                til_DcGarantiesProp.setError("Indiquer les Garanties proposées par le membre");
                bipError();
                requestFocus(DcGarantiesPropET);
                return false;
            }else{
                demandeCreditModele.setDcGarantiesProp(DcGarantiesPropET.getText().toString());
            }
//            5- Avalistes

            if (rb_DcIsAvaliste1Oui.isChecked()){
                if (DcNomAval1ET.getText().toString().trim().isEmpty()){
                    DcNomAval1ET.setError("Indiquer le nom");
                    bipError();
                    requestFocus(DcNomAval1ET);
                    return false;
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
                    bipError();
                    requestFocus(DcNomAval2ET);
                    return false;
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
                    bipError();
                    requestFocus(DcNomAval3ET);
                    return false;
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
                    bipError();
                    requestFocus(DcMtTotalBesoinsET);
                    return false;
                }else{
                    demandeCreditModele.setDcIsBesoinsAFin("Y");
                }
            }else{
                demandeCreditModele.setDcIsBesoinsAFin("N");
            }
            demandeCreditModele.setDcMtTotalBesoins(DcMtTotalBesoinsET.getText().toString());

            if (DcMotivationPretET.getText().toString().trim().isEmpty()){
                til_DcMotivationPret.setError("Indiquer les Détails des motivations du Prêt");
                bipError();
                requestFocus(DcMotivationPretET);
                return false;
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

            if (rb_DcDecisionComGesOui.isChecked()){
                demandeCreditModele.setDcDecisionComGes("A");
            }else if (rb_DcDecisionComGesNon.isChecked()){
                demandeCreditModele.setDcDecisionComGes("R");
            }else{
                notificationDecisionNonRenseignee();
                return false;
            }

            if (rb_DcIsAutresDocumOui.isChecked()){
                if (DcAutresDocumET.getText().toString().trim().isEmpty()){
                    til_DcAutresDocum.setError("Indiquer la Liste des autres documents remis");
                    bipError();
                    requestFocus(DcAutresDocumET);
                    return false;
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
                    bipError();
                    requestFocus(DcDateSignatET);
                    return false;
                }else{
                    demandeCreditModele.setDcIsSignatAdh("Y");
                }
            }else{
                demandeCreditModele.setDcIsSignatAdh("N");
            }
            demandeCreditModele.setDcDateSignat(DcDateSignatET.getText().toString());

            if (DcDetailsET.getText().toString().trim().isEmpty()){
                til_DcDetails.setError("Indiquer les Détails sur le produit de Crédit");
                bipError();
                requestFocus(DcDetailsET);
                return false;
            }else{
                demandeCreditModele.setDcDetails(DcDetailsET.getText().toString());
            }
            if (DcMotivationsET.getText().toString().trim().isEmpty()){
                til_DcMotivations.setError("Indiquer les motivations sur la décision du comité");
                bipError();
                requestFocus(DcMotivationsET);
                return false;
            }else{
                demandeCreditModele.setDcMotivations(DcMotivationsET.getText().toString());
            }

//                new AddDcAsyncTask().execute();


        }else if(demandeCreditID ==0) {
            notificationProduitCreditVide();
            return false;
        }else if(objetCreditID ==0) {
            notificationObjetVide();
            return false;
        }else if(demandeCreditModele.getDcCpteEAVPrinc().equals("0")) {
            notificationCpteEAVVide();
            return false;
        }else {
            bipError();
            Toast.makeText(ComiteCreditNew.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return error;


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
    public void notificationComiteCreditIDVide() {
        MyData.bipError();
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Aucun comité de crédit sélectionné")
                .setMessage("Veuillez sélectionner un comité de crédit pour cette demande!")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        spinnerListCredit.setError ("Donnez vos observations SVP!");
                        spinnerListComiteCredit.requestFocus();
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
    public void notificationDecisionNonRenseignee() {

        ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,150);
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Décision comité absente")
                .setMessage("Veuillez renseigner la décision prise au comité!")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        spinnerListCredit.setError ("Donnez vos observations SVP!");

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
            pDialog = new ProgressDialog(ComiteCreditNew.this);
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
            httpParams.put(DemandeCreditModele.KEY_DcNumero, compteId);
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
            httpParams.put(DemandeCreditModele.KEY_DcCmNumero, String.valueOf(comiteCreditID));
            httpParams.put(DemandeCreditModele.KEY_DcCredit, String.valueOf(demandeCreditID));
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

            httpParams.put(DemandeCreditModele.KEY_DcDateDecisiion, demandeCreditModele.getDcDateDecisiion());
            httpParams.put(DemandeCreditModele.KEY_DcDecisionComGes, demandeCreditModele.getDcDecisionComGes());
            httpParams.put(DemandeCreditModele.KEY_DcMtAccorde, demandeCreditModele.getDcMtAccorde());
            httpParams.put(DemandeCreditModele.KEY_DcDureeAccord, demandeCreditModele.getDcDureeAccord());
            httpParams.put(DemandeCreditModele.KEY_DcMotivations, demandeCreditModele.getDcMotivations());


            httpParams.put(DemandeCreditModele.KEY_DcUserCree, String.valueOf(MyData.USER_ID));

            httpParams.put(KEY_DC_GUICHET, String.valueOf(MyData.GUICHET_ID));

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "comite_credit.php", "POST", httpParams);
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
                       /* Toast.makeText(ComiteCreditNew.this,
                                "Comité de crédit effectué", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();*/

                        notificationSuccessAdd();
                        avertissement();

                    } else {
                        notificationEchecAdd();
                        Toast.makeText(ComiteCreditNew.this,
                                "Some error occurred while adding Analyse Technique",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    public void notificationSuccessAdd() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Décision Comité de crédit enregistrée ")
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
                        Dexter.withActivity(ComiteCreditNew.this)
                                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse response) {
                                        createPDFFile(Common.getAppPath(ComiteCreditNew.this)+"comite_credit_mifucam.pdf");
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

            addNewItem(document, "COMITE DE CREDIT", Element.ALIGN_CENTER, titleFont);

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
                addNewItemWithLeftAndRight(document, "Montant accordé:", defaultFormat.format(parseDouble(demandeCreditModele.getDcMtPretSollicite()))+" (" +
                        ""+ FrenchNumberToWords.convert(Double.parseDouble(demandeCreditModele.getDcMtAccorde()))+" )", titleFont, orderNumberValueFont);
                addNewItemWithLeftAndRight(document, "Périodicité:", demandeCreditModele.getDcPeriodRemb(), titleFont, orderNumberValueFont);
                addNewItemWithLeftAndRight(document, "Taux intérêt:", "18 %", titleFont, orderNumberValueFont);
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
            PrintDocumentAdapter printDocumentAdapter = new PdfDocumentAdapter(ComiteCreditNew.this, Common.getAppPath(ComiteCreditNew.this)+"comite_credit_mifucam.pdf");
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