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
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.Category;
import com.example.binumtontine.activity.convertisseur.FrenchNumberToWords;
import com.example.binumtontine.activity.pdf.Common;
import com.example.binumtontine.activity.pdf.PdfDocumentAdapter;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.example.binumtontine.modele.DemandeCreditModele;
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
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class DecaissementCredit extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SERVER_ADDRESS {
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
    private static final String KEY_TYPE_OPERATION = "TypeOperation";
    private static final String KEY_ADHERENT = "ADHERENT";
    private static final String KEY_LIBELLE_PRODUIT = "Libelle";
    private Adherent adherent;
    /*Param for get extra*/
    private static final String KEY_COMPTE_ID = "Numero";
    private static final String KEY_CV_NUMERO = "CvNumero";

    private static String STRING_EMPTY = "";

    private EditText DcDetailsDecaiss_ET;
    private EditText DcCodeDebl_ET;
    private TextInputLayout til_DcDetailsDecaiss;

    public static RadioButton rb_DcModeDecaissEspece;
    public static RadioButton rb_DcModeDecaissEAV;

    private String compteId;
    private String DcDetailsDecaiss;
    private String adNom;
    private String adPrenom;
    private String adNumManuel;
    private String adCode;
    private String compteSolde;
    private String typeOperation;
    private String DcCodeDebl;
    private String libelleProduit;

    private String DcModeDecaiss;

    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Category> eavList;
    List<Integer> eavListID = new ArrayList<Integer>();
    private int eavID;
    private Spinner spinnerListEAV;
    private TextView tvAdherentNom;
    private TextView tvHeaderOperationEAV;
    private TextView tvAdherentNumManuel;
    private TextView tvAdherentCode;
    private TextView tvCompteSolde;
    private TextView tvLibelleProduit;
    /*end manage*/

    private Button addButton;
    private Button annulerButton;
    private int success;
    private ProgressDialog pDialog;
    private ProgressDialog pDialogFetchCvNumeroAdherent;
    private ProgressDialog pDialogFetchTrnsaction;
    private String DcMtAccorde;
    private String DcMembre;
    private String GxLastBordOperat;
    private String adNumDossier;
    private int CvNumero;
    private String CvMtSolde;
    private String AdEMail;
    private String AdTel1;
    private String DcNumDossier;
    private NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
//    private String DcModeDecaiss;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decaissement_credit);

        Intent intent = getIntent();
        compteId = intent.getStringExtra(KEY_COMPTE_ID);

        compteSolde = intent.getStringExtra(KEY_MONTANT_COMPTE);
        typeOperation = intent.getStringExtra(KEY_TYPE_OPERATION);
        libelleProduit = intent.getStringExtra(KEY_LIBELLE_PRODUIT);
        Bundle bundle = intent.getExtras();
        adherent = (Adherent) bundle.getSerializable(KEY_ADHERENT);
        adNom = adherent.getAdNom();
        adPrenom = adherent.getAdPrenom();
        adNumManuel = adherent.getAdNumManuel();
        adCode = adherent.getAdCode();


        DcDetailsDecaiss_ET = (EditText) findViewById(R.id.input_txt_DcDetailsDecaiss);
        DcDetailsDecaiss_ET.setText("DECAISS CRED/"+adCode+ "DE "+adNom+" "+adPrenom);
        til_DcDetailsDecaiss = (TextInputLayout) findViewById(R.id.input_layout_DcDetailsDecaiss);
        DcCodeDebl_ET = (EditText) findViewById(R.id.input_txt_DcCodeDebl);

        rb_DcModeDecaissEspece = (RadioButton) findViewById(R.id.rb_DcModeDecaissEspece);
        rb_DcModeDecaissEAV = (RadioButton) findViewById(R.id.rb_DcModeDecaissEAV);
        tvAdherentNom = (TextView) findViewById(R.id.tv_nom_adherent);

        tvAdherentNom.setText(adNom+"\n"+adPrenom);
        tvAdherentNumManuel = (TextView) findViewById(R.id.tv_num_manuel_adherent);
        tvAdherentNumManuel.setText(adNumManuel);
        tvAdherentCode = (TextView) findViewById(R.id.tv_code_adherent);
        tvAdherentCode.setText(adCode);
        tvCompteSolde = (TextView) findViewById(R.id.tv_solde_compte);
        tvCompteSolde.setText(compteSolde);

        tvLibelleProduit = (TextView) findViewById(R.id.tv_libelle_produit_adherent);
        tvLibelleProduit.setText(libelleProduit);


        getDcCodeDeblDialog(this);
        defaultFormat.setCurrency(Currency.getInstance("xaf"));
        addButton = (Button) findViewById(R.id.btn_save_operation_eav);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(DecaissementCredit.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addDecaissementCredit();
                } else {
                    Toast.makeText(DecaissementCredit.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void getDcCodeDeblDialog(Context c) {
        final EditText taskEditText = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Code de déblocage:")
                .setMessage("Saisir le code de déblocage (*)")
                .setView(taskEditText)
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DcCodeDebl = String.valueOf(taskEditText.getText().toString().trim());
                        new FetchDcCodeDeblocageDetailsAsyncTask().execute();
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .create();
        dialog.show();
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

            case R.id.rb_DcModeDecaissEspece:
                if (rb_DcModeDecaissEspece.isChecked()) {
                    DcModeDecaiss = "ESP";
                    //str = checked1?"Nature frais fixe":"";

                }
                break;
            case R.id.rb_DcModeDecaissEAV:
                if (rb_DcModeDecaissEAV.isChecked()) {
                    DcModeDecaiss = "EAV";
                    // str = checked1?"Nature frais taux":"";

                }

                break;


        }
    }
    /**
     * Fetches single DcCodeDebl transaction details from the server
     */
    private class FetchDcCodeDeblocageDetailsAsyncTask extends AsyncTask<String, String, String> {
        private int trouve;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialogFetchTrnsaction = new ProgressDialog(DecaissementCredit.this);
            pDialogFetchTrnsaction.setMessage("Veuillez patienter...");
            pDialogFetchTrnsaction.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialogFetchTrnsaction.setIndeterminate(false);
            pDialogFetchTrnsaction.setCancelable(false);
            pDialogFetchTrnsaction.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(DemandeCreditModele.KEY_DcCodeDebl, DcCodeDebl);
            httpParams.put(DemandeCreditModele.KEY_DcNumero, compteId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_DcCodeDeblocage_details.php", "GET", httpParams);
            Log.e("jsonObject", jsonObject+"");
            try {

                if (!jsonObject.isNull(DemandeCreditModele.KEY_DcCodeDebl)){
                    trouve = 1;
                    DcCodeDebl = (jsonObject.getString(DemandeCreditModele.KEY_DcCodeDebl));
                    DcMtAccorde = (jsonObject.getString(DemandeCreditModele.KEY_DcMtAccorde));
                    DcMembre = (jsonObject.getString(DemandeCreditModele.KEY_DcMembre));
                    DcNumDossier = (jsonObject.getString(DemandeCreditModele.KEY_DcNumDossier));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialogFetchTrnsaction.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    //Populate the Edit Texts once the network activity is finished executing
                    try {
                        if (trouve==1){
                            notificationSuccessGetnumRef();
                            DcCodeDebl_ET.setText(DcCodeDebl);
                        }else{
                            notificationBadNumRef();
                            addButton.setVisibility(View.GONE);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }


    }
    private class FetchCvNumeroAdherentAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialogFetchCvNumeroAdherent = new ProgressDialog(DecaissementCredit.this);
            pDialogFetchCvNumeroAdherent.setMessage("Loading list's avalistes. Please wait...");
            pDialogFetchCvNumeroAdherent.setIndeterminate(false);
            pDialogFetchCvNumeroAdherent.setCancelable(false);
            pDialogFetchCvNumeroAdherent.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(DemandeCreditModele.KEY_DcMembre, DcMembre);
            httpParams.put(DemandeCreditModele.KEY_DcGuichet, String.valueOf(MyData.GUICHET_ID));
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_CvNumeroAdherent.php", "GET", httpParams);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                Log.e("jsonObject", jsonObject+"");
                JSONArray movies;
                if (success == 1) {
//                    movieList = new ArrayList<>();
                    movies = jsonObject.getJSONArray(KEY_DATA);
                    //Iterate through the response and populate movies list
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject movie = movies.getJSONObject(i);
                    /*    Integer gx_numero = movie.getInt(Adherent.KEY_AD_AdNumero);
                        String cx_denomination = movie.getString(Adherent.KEY_AD_AdNom);
                        String gx_denomination = movie.getString(Adherent.KEY_AD_AdPrenom);*/
                        CvNumero = movie.getInt(KEY_CV_NUMERO);
                        CvMtSolde = movie.getString(KEY_CV_MT_SOLDE);
                        AdEMail = movie.getString(Adherent.KEY_AD_AdEMail);
                        AdTel1 = movie.getString(Adherent.KEY_AdTel1);
    /*
                        String AdTel1 = movie.getString("AdTel1");
                        String AdTypCarteID = movie.getString("AdTypCarteID");
                        String AdNumCarteID = movie.getString("AdNumCarteID");
                        String AdDomicile = movie.getString("AdDomicile");
*/
//                        Log.e("get_adherents_expediteurs><<<<<<  ",gx_numero+"  "+cx_denomination+"  "+gx_denomination+" "+cvMtSolde);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialogFetchCvNumeroAdherent.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
//                    populateMovieList();
                }
            });
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {

//        eavID = eavListID.get(position);//pour recuperer l'ID du guichet selectionnée

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }
    public void notificationBadNumRef() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Code de déblocage incorrect ")
                .setMessage("Votre code de déblocage n'a pas été trouvé !"
                )
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .show();
    }
    public void notificationSoldeCaisseInsuffisant() {
        MyData.bipError();
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Echec !")
                .setMessage(" Vérifiez le solde de la caisse !"
                )
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .show();
    }
    public void notificationSuccessGetnumRef() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Code de déblocage correct ")
                .setMessage("Votre code de déblocage est correct !")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .show();
        new FetchCvNumeroAdherentAsyncTask().execute();
    }
    public void notificationModeDecaissementNonRenseignee() {
        MyData.bipError();
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Mode de décaissement absent !")
                .setMessage("Veuillez renseigner le Mode de décaissement!")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .show();
    }
    /**
     * Checks whether all files are filled. If so then calls AddEavAdherentAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addDecaissementCredit() {
            if (rb_DcModeDecaissEspece.isChecked()){
                DcModeDecaiss = "ESP";
            }else if (rb_DcModeDecaissEAV.isChecked()){
                DcModeDecaiss = "EAV";
            }else{
                notificationModeDecaissementNonRenseignee();
                return;
            }

            if (STRING_EMPTY.equals(DcDetailsDecaiss_ET.getText().toString().trim())){
                MyData.bipError();
                til_DcDetailsDecaiss.setError("Indiquer le détail du décaissement");
                til_DcDetailsDecaiss.requestFocus();
                Toast.makeText(DecaissementCredit.this,
                        "le détail du décaissement est vide!",
                        Toast.LENGTH_LONG).show();
                return;
            }

            DcDetailsDecaiss = DcDetailsDecaiss_ET.getText().toString().trim();
            DcCodeDebl = DcCodeDebl_ET.getText().toString();
        new getGxLastBordOperatAsyncTask().execute();
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
//            MyEvent event = new MyEvent(this);
//            writer.setBoxSize("headerBox", headerBox);
//            writer.setPageEvent(event);//for logo


            //Open to write
            document.open();

            //Add image
//            Image image = Image.getInstance("C:/logomifucam.png");
//            image.setAbsolutePosition(100f, 500f);
//            image.scaleAbsolute(200f, 200f);
//            document.add(image);
            // load image


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
            Font titleFont = new Font(fontName,36.0f,Font.NORMAL, BaseColor.BLACK);
            try {
//                            String value = String.format("{0:D5}", (Integer.parseInt(GxLastBordOperat)+1));
                String value = String.format("%05d", (Integer.parseInt(GxLastBordOperat)+1));
//                String.format("%05d",number);
//                            String value = (Integer.parseInt(GxLastBordOperat)+1)+"";
                if (DcModeDecaiss.equals("EAV")){
                    addNewItem(document, "OPERATION DE DECAISSEMENT SUR COMPTE EAV", Element.ALIGN_CENTER, titleFont);
                    addNewItem(document, "N° "+ value + "-CR", Element.ALIGN_CENTER, titleFont);

                }else if (DcModeDecaiss.equals("ESP")){
                    addNewItem(document, "OPERATION DE DECAISSEMENT EN ESPECE", Element.ALIGN_CENTER, titleFont);
                    addNewItem(document, "N° Opération:"+ value+ "-CR", Element.ALIGN_CENTER, titleFont);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

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
            addNewItemWithLeftAndRight(document, "Type de compte:", "CREDIT", titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "N° de dossier crédit:", DcNumDossier, titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Nom client:", adNom+" "+adPrenom, titleFont, orderNumberValueFont);

            addLineSeperator(document);


            //Item 2
            try {
                addNewItemWithLeftAndRight(document, "Montant opération:", defaultFormat.format(parseDouble(DcMtAccorde))+" (" +
                        ""+ FrenchNumberToWords.convert(Long.parseLong(DcMtAccorde))+" )", titleFont, orderNumberValueFont);

            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } //04/03/2021 A modifier

            addNewItemWithLeftAndRight(document, "Type opération:", "Décaissemment de crédit", titleFont, orderNumberValueFont);

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
            PrintDocumentAdapter printDocumentAdapter = new PdfDocumentAdapter(DecaissementCredit.this, Common.getAppPath(DecaissementCredit.this)+"decaissement_mifucam.pdf");
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
                .setTitle("Opération de décaissement réussie ")
                .setMessage("L'enregistrement de votre opération s'est bien effectuée !")
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
//                        "Montant Démarrage: " + tv_montant_demarrage.getText().toString()+
//                        "\nTotal Dépôt: " + tv_depot.getText().toString()+
//                        "\nTotal Retrait: " + tv_retrait.getText().toString()+
//                        "\nSolde Théorique: " + tv_total.getText().toString()+
//                        "\nSolde Réel: " + SoldeReelEditText.getText().toString().replaceAll(",", "").trim() +
//                        "\nMontant écart: " + tv_montant_ecart.getText().toString()+
//                        "\nTotal Billetage: " + tv_total_billetage.getText().toString()+
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
                        Dexter.withActivity(DecaissementCredit.this)
                                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse response) {

                                        createPDFFile(Common.getAppPath(DecaissementCredit.this)+"decaissement_mifucam.pdf");
                                    }

                                    @Override
                                    public void onPermissionDenied(PermissionDeniedResponse response) {

                                    }

                                    @Override
                                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                                    }
                                })
                                .check();
                        DcCodeDebl_ET.setText("");
                        DcDetailsDecaiss_ET.setText("");

                    }

                })
                .show();

    }
    /*END BLOC TO MANAGE PDF FILE*/


    /**
     * Fetches the GxLastBordOperat from the guichet table from the server
     */
    private class getGxLastBordOperatAsyncTask extends AsyncTask<String, String, String> {
        int successGetFrais;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(DecaissementCredit.this);
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
                        new DecaissementCredit.AddEavAdherentAsyncTask().execute();
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
            pDialog = new ProgressDialog(DecaissementCredit.this);
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
//            httpParams.put(KEY_CV_NUMERO, compteId);
            httpParams.put(KEY_CV_NUMERO, String.valueOf(CvNumero));
            httpParams.put(DemandeCreditModele.KEY_DcNumero, String.valueOf(compteId));

            httpParams.put(KEY_CV_NUM_DOSSIER, adNumDossier);
            httpParams.put(KEY_OcLibelleMvmEAV, DcDetailsDecaiss);
            httpParams.put(KEY_CV_MT_SOLDE, DcMtAccorde);
            httpParams.put(KEY_CV_NATURE_OPERATION, DcModeDecaiss);
            httpParams.put(Adherent.KEY_AD_AdEMail, AdEMail);
            httpParams.put(KEY_CV_USER_CREE, String.valueOf(MyData.USER_ID));
            httpParams.put(KEY_OC_GUICHET, String.valueOf(MyData.GUICHET_ID));

            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "operation_decaissement_credit_eav.php", "POST", httpParams);
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
                        //Start building pdf
                        notificationSuccessAdd();
                        avertissement();

                    } else {
                        notificationSoldeCaisseInsuffisant();
                        Toast.makeText(DecaissementCredit.this,
                                "Echec!\n Le Solde de la caisse est peut-être insuffisant pour l'opération ! ",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    /**
     * AsyncTask for adding a compte eav
     */
//    private class AddEavAdherentAsyncTask extends AsyncTask<String, String, String> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            //Display proggress bar
//            pDialog = new ProgressDialog(DecaissementCredit.this);
//            pDialog.setMessage("Transaction en cours. Veuillez patienter...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(false);
//            pDialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            HttpJsonParser httpJsonParser = new HttpJsonParser();
//            Map<String, String> httpParams = new HashMap<>();
//            //Populating request parameters
//           // httpParams.put(KEY_EAV_ID, uxGuichetId);
//
//            httpParams.put(KEY_CV_NUMERO, compteId);
//
//            httpParams.put(KEY_CV_NUM_DOSSIER, DcCodeDebl);
//            httpParams.put(KEY_CV_MT_SOLDE, DcDetailsDecaiss);
//            httpParams.put(KEY_CV_NATURE_OPERATION, natureOperation );
//            httpParams.put(KEY_CV_USER_CREE, String.valueOf(MyData.USER_ID));
//
//            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
//                    BASE_URL + "operation_compte_courant_adherent.php", "POST", httpParams);
//            try {
//                success = jsonObject.getInt(KEY_SUCCESS);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        protected void onPostExecute(String result) {
//            pDialog.dismiss();
//            runOnUiThread(new Runnable() {
//                public void run() {
//                    if (success == 1) {
//                        //Display success message
//                        Toast.makeText(DecaissementCredit.this,
//                                "Opération réussie !", Toast.LENGTH_LONG).show();
//                        Intent i = getIntent();
//                        //send result code 20 to notify about movie update
//                        setResult(20, i);
//                        //Finish ths activity and go back to listing activity
//                        finish();
//
//                    } else {
//                        Toast.makeText(DecaissementCredit.this,
//                                "Echec!\n Vérifiez votre solde ",
//                                Toast.LENGTH_LONG).show();
//
//                    }
//                }
//            });
//        }
//    }
}