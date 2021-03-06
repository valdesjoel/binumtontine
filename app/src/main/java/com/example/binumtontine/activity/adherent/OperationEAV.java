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
import android.widget.ArrayAdapter;
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

public class OperationEAV extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    /*Begin*/
         //to fetchProduitList by caisse and guichet ID
    /* end*/
    private static final String KEY_CV_NUM_DOSSIER = "CvNumDossier";
    private static final String KEY_OcLibelleMvmEAV = "OcLibelleMvmEAV";
    private static final String KEY_CV_MT_SOLDE = "CvMtSolde";
    private static final String KEY_CV_NATURE_OPERATION = "NatureOp";
    private static final String KEY_CV_USER_CREE = "CvUserCree";
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

    private EditText EavDepotMinEditText;
    private EditText NumDossierEditText;
    private EditText OcLibelleMvmEAV;

    public static RadioButton rb_depot;
    public static RadioButton rb_retrait;


    private String compteId;
    private String eavDepotMin;
    private String adNom;
    private String adPrenom;
    private String adNumManuel;
    private String adCode;
    private String compteSolde;
    private String typeOperation;
    private String adNumDossier;
    private String libelleProduit;

    private String natureOperation;
    private String st_OcLibelleMvmEAV;

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
    private ProgressDialog pDialogFetchProduitEavList;
    public Button btn_create_pdf; //NEW
    private NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
    public static String GxLastBordOperat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_eav_adherent);


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

        EavDepotMinEditText = (EditText) findViewById(R.id.input_txt_depot_min);
        EavDepotMinEditText.addTextChangedListener(MyData.onTextChangedListener(EavDepotMinEditText));

        NumDossierEditText = (EditText) findViewById(R.id.input_txt_numero_bordereau_operation);
        OcLibelleMvmEAV = (EditText) findViewById(R.id.input_txt_OcLibelleMvmEAV);

        rb_depot = (RadioButton) findViewById(R.id.rb_nature_operation_depot);
        rb_retrait = (RadioButton) findViewById(R.id.rb_nature_operation_retrait);
        tvHeaderOperationEAV = (TextView) findViewById(R.id.header_operation_eav_adherent);
        spinnerListEAV = (Spinner) findViewById(R.id.spn_mode_paiement);
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

        if (typeOperation.equals("depot")){
            tvHeaderOperationEAV.setText("TYPE OPERATION: DEPOT");

            rb_depot.setChecked(true);
            rb_retrait.setVisibility(View.GONE);
            rb_depot.setVisibility(View.VISIBLE);
            onRadioButtonClicked(rb_depot);
            OcLibelleMvmEAV.setText("VERSEMENT EAV/"+adCode+ "DE "+adNom+" "+adPrenom);
        }else if(typeOperation.equals("retrait")){
            tvHeaderOperationEAV.setText("TYPE OPERATION: RETRAIT");
            rb_retrait.setChecked(true);
            OcLibelleMvmEAV.setText("RETRAIT EAV/"+adCode+ "DE "+adNom+" "+adPrenom);
            onRadioButtonClicked(rb_retrait);
            rb_retrait.setVisibility(View.VISIBLE);
            rb_depot.setVisibility(View.GONE);
        }
        /* START 25/11/2020*/
        btn_create_pdf = findViewById(R.id.btn_create_pdf);
        defaultFormat.setCurrency(Currency.getInstance("xaf"));
        /* END 25/11/2020*/
        addButton = (Button) findViewById(R.id.btn_save_operation_eav);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(OperationEAV.this,
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
                    Toast.makeText(OperationEAV.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
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

            case R.id.rb_nature_operation_depot:
                if (rb_depot.isChecked()) {
                    natureOperation = "D";
                    //str = checked1?"Nature frais fixe":"";

                }
                break;
            case R.id.rb_nature_operation_retrait:
                if (rb_retrait.isChecked()) {
                    natureOperation = "R";
                    // str = checked1?"Nature frais taux":"";

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

        for (int i = 0; i < eavList.size(); i++) {
            lables.add(eavList.get(i).getName());//recupère les noms
            eavListID.add(eavList.get(i).getId()); //recupère les Id
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(OperationEAV.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerListEAV.setAdapter(spinnerAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
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
        if (!STRING_EMPTY.equals(EavDepotMinEditText.getText().toString().trim()) &&
            !STRING_EMPTY.equals(OcLibelleMvmEAV.getText().toString().trim())
                 ) {
if (true){

   eavDepotMin = EavDepotMinEditText.getText().toString().replaceAll(",", "").trim();
   adNumDossier = NumDossierEditText.getText().toString();
   st_OcLibelleMvmEAV = OcLibelleMvmEAV.getText().toString();

    new getGxLastBordOperatAsyncTask().execute();

            }else
            {

            }

        } else {
            Toast.makeText(OperationEAV.this,
                    "Un ou plusieurs champs sont vides!",
                    Toast.LENGTH_LONG).show();

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
            pDialog = new ProgressDialog(OperationEAV.this);
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
                        new OperationEAV.AddEavAdherentAsyncTask().execute();
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
            pDialog = new ProgressDialog(OperationEAV.this);
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
            httpParams.put(KEY_OcLibelleMvmEAV, st_OcLibelleMvmEAV);
            httpParams.put(KEY_CV_MT_SOLDE, eavDepotMin );
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
                        //Start building pdf
                        notificationSuccessAdd();
                        avertissement();
                        //Display success message
//                        Toast.makeText(OperationEAV.this,
//                                "Opération réussie !", Toast.LENGTH_LONG).show();
                   //     Intent i = getIntent();
                        //send result code 20 to notify about movie update
                     //   setResult(20, i);
//                        //Finish ths activity and go back to listing activity
//                        finish();

                    } else {
                        Toast.makeText(OperationEAV.this,
                                "Echec!\n Solde du compte ou de la caisse insuffisant ! ",
                                Toast.LENGTH_LONG).show();

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
           PdfWriter.getInstance(document, new FileOutputStream(path));
            Rectangle headerBox = new Rectangle(36, 54, 559, 788);
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
            float fontSize = 20.0f;
            float valueFontSize = 26.0f;

            //Custom font
//            BaseFont fontName = BaseFont.createFont("assets/fonts/brandon_medium.otf","UTF-8" , BaseFont.EMBEDDED );
            BaseFont fontName = BaseFont.createFont("assets/fonts/brandon_medium.otf",BaseFont.IDENTITY_H , BaseFont.EMBEDDED );
//            BaseFont bf = BaseFont.createFont("arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            //Create Title of document
            Font titleFont = new Font(fontName,36.0f,Font.NORMAL, BaseColor.BLACK);
            try {
                            String value = String.format("%05d", (Integer.parseInt(GxLastBordOperat)+1));
                if (typeOperation.equals("depot")){
                    addNewItem(document, "OPERATION DE VERSEMENT", Element.ALIGN_CENTER, titleFont);
                    addNewItem(document, "N° "+ value + "-V", Element.ALIGN_CENTER, titleFont);

                }else if (typeOperation.equals("retrait")){
                    addNewItem(document, "OPERATION DE RETRAIT", Element.ALIGN_CENTER, titleFont);
                    addNewItem(document, "N° Opération:"+ value+ "-R", Element.ALIGN_CENTER, titleFont);

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
            addNewItemWithLeftAndRight(document, "Type de compte:", "EAV", titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "N° de compte:", adNumManuel, titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Nom client:", adNom+" "+adPrenom, titleFont, orderNumberValueFont);

            addLineSeperator(document);


            //Item 2
            try {
                addNewItemWithLeftAndRight(document, "Montant opération:", defaultFormat.format(parseDouble(eavDepotMin))+" (" +
                        ""+ FrenchNumberToWords.convert(Long.parseLong(eavDepotMin))+" )", titleFont, orderNumberValueFont);
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            addNewItemWithLeftAndRight(document, "Type opération:", typeOperation, titleFont, orderNumberValueFont);

            addLineSeperator(document);

            //Signatures
            addLineSpace(document);
            addLineSpace(document);

            addNewItemWithLeftAndRight(document, "Signature Client:", "Signature Caissier:", titleFont, orderNumberValueFont);

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
            PrintDocumentAdapter printDocumentAdapter = new PdfDocumentAdapter(OperationEAV.this, Common.getAppPath(OperationEAV.this)+"bordereau_mifucam.pdf");
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

    public void notificationSuccessAdd() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Opération réussie ")
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
                        Dexter.withActivity(OperationEAV.this)
                                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse response) {
                                        createPDFFile(Common.getAppPath(OperationEAV.this)+"bordereau_mifucam.pdf");
                                    }
                                    @Override
                                    public void onPermissionDenied(PermissionDeniedResponse response) {
                                    }
                                    @Override
                                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                    }
                                })
                                .check();
                        EavDepotMinEditText.setText("");
                        NumDossierEditText.setText("");
                    }

                })
                .show();
    }
    public void buildPdf(){
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        btn_create_pdf.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                createPDFFile(Common.getAppPath(OperationEAV.this)+"bordereau_mifucam.pdf");
                            }
                        });
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
    /*END BLOC TO MANAGE PDF FILE*/
}