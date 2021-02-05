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
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.Category;
import com.example.binumtontine.activity.ServiceHandler;
import com.example.binumtontine.activity.convertisseur.FrenchNumberToWords;
import com.example.binumtontine.activity.pdf.Common;
import com.example.binumtontine.activity.pdf.PdfDocumentAdapter;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
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
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class OperationClotureEAV extends AppCompatActivity implements  View.OnClickListener, AdapterView.OnItemSelectedListener, SERVER_ADDRESS {
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
    private static final String KEY_OcLibelleMvmEAV = "OcLibelleMvmEAV";
    private static final String KEY_CV_MT_SOLDE = "CvMtSolde";
    private static final String KEY_CV_NATURE_OPERATION = "NatureOp";
    private static final String KEY_CV_USER_CREE = "CvUserCree";
    private static final String KEY_ADHERENT_NUM_DOSSIER = "CvNumDossier";
    private static final String KEY_OC_GUICHET = "OcGuichet";

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
    private static final String KEY_FraisDeCloture = "FraisDeCloture";
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
    private String adNumDossier="";
    private String dateCreation;
    private String taux;
    private String libelleProduit;

    private String dateFin;
    private String nbUnites;
    private String period;
    private String dureeDuCompte;
    private String interet;
    private String montantARetirer;
    private String fraisDeCloture;

    private String AdValideDu;
    private String AdValideAu;
    private String natureOperation="C";

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
//    private TextView tvTaux;

//    private TextView tvDateEcheance;
    private TextView tvDureeCompte;
    private TextView tvMontantARetirer;
    private TextView tvFraisDeCloture;

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
    private String GxLastBordOperat;
    private NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloture_eav_adherent);


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
       /*
        tvTaux = (TextView) findViewById(R.id.tv_taux_compte_adherent);
        tvTaux.setText(taux+" %");
*/
        /*Start manage date échéance*/
/*
        tvDateEcheance = (TextView) findViewById(R.id.tv_date_echeance_compte_adherent);
        tvDateEcheance.setText(dateFin+"\n ("+nbUnites+" "+period+")");
        */
        /**/
        /*Start manage durée du compte*/

        tvDureeCompte = (TextView) findViewById(R.id.tv_duree_compte);
//        tvDureeCompte.setText(nbUnites+" "+period);
        tvDureeCompte.setText(calculateDifferenceBetweenTwoDates(dateCreation, dateFin));
        ;
        /*End manage durée du compte*/
        /*Start manage durée du compte*/

        tvMontantARetirer = (TextView) findViewById(R.id.tv_montant_total_a_retirer);
        tvFraisDeCloture = (TextView) findViewById(R.id.tv_frais_de_cloture);
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
        defaultFormat.setCurrency(Currency.getInstance("xaf"));
        addButton = (Button) findViewById(R.id.btn_save_operation_eav);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(OperationClotureEAV.this,
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
                    Toast.makeText(OperationClotureEAV.this,
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
            pDialog = new ProgressDialog(OperationClotureEAV.this);
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
                    BASE_URL + "fonctions_financieres/EAV/calcFraisClotureEAV.php", "GET", httpParams);
            try {
                successGetFrais = jsonObject.getInt(KEY_SUCCESS);
                Log.e("calcMontantARetirer", jsonObject+"");
                JSONArray movies;
                if (successGetFrais == 1) {
                    fraisDeCloture = jsonObject.getString(KEY_FraisDeCloture);
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
                        tvFraisDeCloture.setText(fraisDeCloture);
                        tvMontantARetirer.setText(montantARetirer);
                    }
                }
            });
        }

    }
    /**
     * Fetches the GxLastBordOperat from the guichet table from the server
     */
    private class getGxLastBordOperatAsyncTask extends AsyncTask<String, String, String> {
        int successGetFrais;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(OperationClotureEAV.this);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_OC_GUICHET, String.valueOf(MyData.GUICHET_ID));
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "getGxLastBordOperat.php", "GET", httpParams);
            try {
                successGetFrais = jsonObject.getInt(KEY_SUCCESS);
                Log.e("getGxLastBordOperat", jsonObject+"");
                JSONArray movies;
                if (successGetFrais == 1) {
                    GxLastBordOperat = jsonObject.getString("GxLastBordOperat");


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
                        adNumDossier = String.format("%05d", (Integer.parseInt(GxLastBordOperat)+1));
                        new AddEavAdherentAsyncTask().execute();
                    }
                }
            });
        }

    }
    /*START BLOC TO MANAGE PDF FILE*/
    private void createPDFFile(String path) {
        if (new File(path).exists()){
            new File(path).delete();
        }
        try {
            Document document = new Document();
            //Save
//            PdfWriter writer= PdfWriter.getInstance(document, new FileOutputStream(path)); //for logo
            PdfWriter.getInstance(document, new FileOutputStream(path));
            Rectangle headerBox = new Rectangle(36, 54, 559, 788);
            //Open to write
            document.open();

            //Add image

            //Setting
            document.setPageSize(PageSize.A4);
//            document.setPageSize(PageSize.A8);
            document.addCreationDate();
            document.addAuthor("MIFUCAM");
            document.addCreator("Valdes FOTSO");

            //Font Setting
            BaseColor colorAccent = new BaseColor(0,153,204,255);
            float fontSize = 20.0f;
            float valueFontSize = 26.0f;

            //Custom font
//            BaseFont fontName = BaseFont.createFont("assets/fonts/brandon_medium.otf","UTF-8" , BaseFont.EMBEDDED );
            BaseFont fontName = BaseFont.createFont("assets/fonts/brandon_medium.otf",BaseFont.IDENTITY_H , BaseFont.EMBEDDED );
//            BaseFont bf = BaseFont.createFont("arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            //Create Title of document
            Font titleFont = new Font(fontName,26.0f,Font.NORMAL, BaseColor.BLACK);
            try {
//                            String value = String.format("{0:D5}", (Integer.parseInt(GxLastBordOperat)+1));
                String value = String.format("%05d", (Integer.parseInt(GxLastBordOperat)+1));
//                String.format("%05d",number);
//                            String value = (Integer.parseInt(GxLastBordOperat)+1)+"";
                addNewItem(document, "OPERATION DE CLOTURE DE COMPTE", Element.ALIGN_CENTER, titleFont);
                addNewItem(document, "N° Opération:"+ value+ "-R", Element.ALIGN_CENTER, titleFont);
             /*   if (typeOperation.equals("depot")){
                    addNewItem(document, "OPERATION DE VERSEMENT", Element.ALIGN_CENTER, titleFont);
                    addNewItem(document, "N° "+ value + "-V", Element.ALIGN_CENTER, titleFont);

                }else if (typeOperation.equals("retrait")){
                    addNewItem(document, "OPERATION DE RETRAIT", Element.ALIGN_CENTER, titleFont);
                    addNewItem(document, "N° Opération:"+ value+ "-R", Element.ALIGN_CENTER, titleFont);

                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }

//            addNewItem(document, "N° "+ (Integer.parseInt(GxLastBordOperat)+1) adNumDossier, Element.ALIGN_CENTER, titleFont);
//            addNewItem(document, "N° "+ String.format("{0:D5}", (Integer.parseInt(GxLastBordOperat)+1)), Element.ALIGN_CENTER, titleFont);
//            String value = String.format("{0:D5}", (Integer.parseInt(GxLastBordOperat)+1));
            //Add More
            Font orderNumberFont = new Font(fontName, fontSize, Font.NORMAL, colorAccent);
//            addNewItem(document, "Compagnie:", Element.ALIGN_LEFT, orderNumberFont);

            Font orderNumberValueFont = new Font(fontName, valueFontSize, Font.NORMAL, BaseColor.BLACK);
//            addNewItem(document, "MIFUCAM", Element.ALIGN_LEFT, orderNumberValueFont);

//            addLineSeperator(document);
//
//            addNewItem(document, "CAISSE:", Element.ALIGN_LEFT, orderNumberFont);
//            addNewItem(document, MyData.CAISSE_NAME, Element.ALIGN_LEFT, orderNumberValueFont);
////            addNewItem(document, "24/11/2020", Element.ALIGN_LEFT, orderNumberValueFont);
//
//            addLineSeperator(document);
//
//            addNewItem(document, "GUICHET:", Element.ALIGN_LEFT, orderNumberFont);
//            addNewItem(document, MyData.GUICHET_NAME, Element.ALIGN_LEFT, orderNumberValueFont);
////            addNewItem(document, "24/11/2020", Element.ALIGN_LEFT, orderNumberValueFont);


            //More 1
            addNewItemWithLeftAndRight(document, "CAISSE:", MyData.CAISSE_NAME, orderNumberFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "GUICHET:", MyData.GUICHET_NAME, orderNumberFont, orderNumberValueFont);


            addLineSeperator(document);
            //More 2
            String dateOperation = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss",
                    Locale.getDefault()).format(System.currentTimeMillis());
            addNewItemWithLeftAndRight(document, "Date opération:", dateOperation, orderNumberFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Opérateur:", MyData.USER_NOM + " "+ MyData.USER_PRENOM, orderNumberFont, orderNumberValueFont);

//
//            addLineSeperator(document);
//
//            addNewItem(document, "Date opération:", Element.ALIGN_LEFT, orderNumberFont);
//            addNewItem(document, dateOperation, Element.ALIGN_LEFT, orderNumberValueFont);
////            addNewItem(document, "24/11/2020", Element.ALIGN_LEFT, orderNumberValueFont);

            addLineSeperator(document);

//            addNewItem(document, "Opérateur:", Element.ALIGN_LEFT, orderNumberFont);
//            addNewItem(document, MyData.USER_NOM + " "+ MyData.USER_PRENOM, Element.ALIGN_LEFT, orderNumberValueFont);
//
//            addLineSeperator(document);
////
//            addNewItem(document, "Account Name:", Element.ALIGN_LEFT, orderNumberFont);
//            addNewItem(document, "Valdes FOTSO", Element.ALIGN_LEFT, orderNumberValueFont);
//
//            addLineSeperator(document);

            addNewItem(document, "Détails Opération", Element.ALIGN_CENTER, titleFont);

            addLineSeperator(document);

            //Item 1
            addNewItemWithLeftAndRight(document, "Type de compte:", "EAV", titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "N° de compte:", adNumManuel, titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Nom client:", adNom+" "+adPrenom, titleFont, orderNumberValueFont);

            addLineSeperator(document);


            //Item 2
            try {
                addNewItemWithLeftAndRight(document, "Frais de clôture:", defaultFormat.format(parseDouble(fraisDeCloture))+" (" +
                        ""+ FrenchNumberToWords.convert(Long.parseLong(fraisDeCloture))+" )", titleFont, orderNumberValueFont);
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            try {
                addNewItemWithLeftAndRight(document, "Montant à retirer:", defaultFormat.format(parseDouble(montantARetirer))+" (" +
                        ""+ FrenchNumberToWords.convert(Long.parseLong(montantARetirer))+" )", titleFont, orderNumberValueFont);
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            addNewItemWithLeftAndRight(document, "Type opération:", "clôture de compte", titleFont, orderNumberValueFont);

            addLineSeperator(document);

            //Signatures
            addLineSpace(document);
            addLineSpace(document);

            addNewItemWithLeftAndRight(document, "Signature Client:", "Signature Caissier:", titleFont, orderNumberValueFont);
//            addNewItemWithLeftAndRight(document, "Signature Caissier:", "", titleFont, orderNumberValueFont);


//            addNewItemWithLeftAndRight(document, "Total", "24000.0", titleFont, orderNumberValueFont);

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
            PrintDocumentAdapter printDocumentAdapter = new PdfDocumentAdapter(OperationClotureEAV.this, Common.getAppPath(OperationClotureEAV.this)+"bordereau_cloture_mifucam.pdf");
            printManager.print("Document", printDocumentAdapter, new PrintAttributes.Builder().build());

        }catch (Exception ex){
            Log.e("VALDES",""+ex.getMessage());
        }
    }

    //    private void addNewItemWithLeftAndRight(Document document, String textLeft, String textRight, Font textLeftFont, Font textRightFont) throws DocumentException {
//        Chunk chunkTextLeft = new Chunk(textLeft, textLeftFont);
//        Chunk chunkTextRight= new Chunk(textRight, textRightFont);
//        Paragraph p = new Paragraph(chunkTextLeft);
//        p.add(new Chunk(new VerticalPositionMark()));
//        p.add(chunkTextRight);
//        document.add(p);
//    }
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
    public void notificationSuccessAdd() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Opération réussie ")
                .setMessage("La clôture du compte s'est bien effectuée !")
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
//                        btn_create_pdf.performClick();
                        Dexter.withActivity(OperationClotureEAV.this)
                                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse response) {
//                                        btn_create_pdf.setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View v) {
//                                                createPDFFile(Common.getAppPath(OperationEAV.this)+"test_pdf25.pdf");
//                                            }
//                                        });
                                        createPDFFile(Common.getAppPath(OperationClotureEAV.this)+"bordereau_cloture_mifucam.pdf");
                                    }

                                    @Override
                                    public void onPermissionDenied(PermissionDeniedResponse response) {

                                    }

                                    @Override
                                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                                    }
                                })
                                .check();


//                        Intent intent = new Intent(getApplicationContext(), LoginActivity_NEW.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        intent.putExtra("LOGOUT", true);
//                        startActivity(intent);

                        //Display success message
//                        Toast.makeText(OperationEAV.this,
//                                "Opération réussie !", Toast.LENGTH_LONG).show();
//                        Intent i = getIntent();
//                        //send result code 20 to notify about movie update
//                        setResult(20, i);
//                        //Finish ths activity and go back to listing activity
//                        finish();
                    }

                })
                .show();

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
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(OperationClotureEAV.this,
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
            pDialogFetchProduitEavList = new ProgressDialog(OperationClotureEAV.this);
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
        if (!STRING_EMPTY.equals(eatCtModRenouv)) {
            new getGxLastBordOperatAsyncTask().execute();
//            new AddEavAdherentAsyncTask().execute();

        } else {
            Toast.makeText(OperationClotureEAV.this,
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
            pDialog = new ProgressDialog(OperationClotureEAV.this);
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
            httpParams.put(KEY_OcLibelleMvmEAV, "Cloture de compte");
//            httpParams.put(KEY_CV_MT_SOLDE, eavDepotMin );
            httpParams.put(KEY_CV_MT_SOLDE, montantARetirer );
            httpParams.put(KEY_CV_NATURE_OPERATION, natureOperation );
            httpParams.put(KEY_CV_USER_CREE, String.valueOf(MyData.USER_ID));
            httpParams.put(KEY_OC_GUICHET, String.valueOf(MyData.GUICHET_ID));

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "operation_eav_adherent_new.php", "POST", httpParams);
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
                   /*     Toast.makeText(OperationClotureEAV.this,
                                "Opération réussie !", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();*/


                    } else {
                        Toast.makeText(OperationClotureEAV.this,
                                "Echec!\n Solde du compte ou de la caisse insuffisant ! ",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}