
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.binumtontine.R;
import com.example.binumtontine.activity.convertisseur.FrenchNumberToWords;
import com.example.binumtontine.activity.pdf.Common;
import com.example.binumtontine.activity.pdf.PdfDocumentAdapter;
import com.example.binumtontine.adapter.CustomeAdapter;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
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
import java.util.Locale;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class GetFraisAdherent extends AppCompatActivity implements SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private int success;
    private static final String KEY_DATA = "data";
    private static final String KEY_FC_NUMERO = "FcNumero";
    private static final String KEY_FC_NEW_LIBELLE = "FcNewLibelle";
    private static final String KEY_FC_FONCTION = "FcFonction";
    private static final String KEY_FC_NBRE_PART_MIN_J = "FcNbrePartMinJ";
    private static final String KEY_FC_NBRE_PART_MIN_F = "FcNbrePartMinF";
    private static final String KEY_FC_NBRE_PART_MIN_H = "FcNbrePartMinH";
    private static final String KEY_FC_VALEUR = "FcVal";
    private static final String KEY_FcNature = "FcNature";
    private static final String KEY_FcBase = "FcBase";
    private static final String KEY_CAISSE_ID = "FcCaisse";
    private static final String KEY_GUICHET_ID = "FcGuichet";
    private static final String KEY_ADHERENT = "ADHERENT";
    private ArrayList<HashMap<String, String>> fraisList = new ArrayList<>();
    private ProgressDialog pDialog;
    private Adherent adherent;
    /*Begin*/
    private ListView lv;
    private CustomeAdapter customeAdapter;
    public ArrayList<EditModel> editModelArrayList;

    private Button  btnClean, btnnext;
    private String piecesListId="";
    private String fraisListId="";
    private String fraisListMontant="";
    private String fraisListFonction="";
    private static final String KEY_TYPE_MEMBRE = "FcCategAdh"; //pour l'id du type de membre

    private static final String KEY_ADHERENT_PIECE = "AD_PIECE";
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
    private static final String KEY_ADHERENT_FRAIS_ID = "AD_FRAIS_ID";
    private static final String KEY_ADHERENT_FRAIS_MONTANT = "AD_FRAIS_MONTANT";
    private static final String KEY_ADHERENT_FRAIS_FONCTION = "AD_FRAIS_FONCTION";
    private static final String KEY_CV_USER_CREE = "CvUserCree";
//    private double eavDepotMin;
    private NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
    private String typeMembreId;

    /*End*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frais_adherent);
        /*BEGIN*/
        btnClean = (Button) findViewById(R.id.btn_clean);
        btnnext = (Button) findViewById(R.id.save_frais_adherent);
        defaultFormat.setCurrency(Currency.getInstance("xaf"));

        lv = (ListView) findViewById(R.id.lv_frais_adherent);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        adherent = (Adherent) bundle.getSerializable(KEY_ADHERENT);
        piecesListId = bundle.getString(KEY_ADHERENT_PIECE);
        typeMembreId = intent.getStringExtra(KEY_TYPE_MEMBRE);
        new FetchFraisGuichetAsyncTask().execute();

        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAdherent();
            }
        });
    }

    private ArrayList<EditModel> populateList(){
        ArrayList<EditModel> list = new ArrayList<>();
        for(int i = 0; i < fraisList.size(); i++){
            EditModel editModel = new EditModel();
            editModel.setFcValeur(fraisList.get(i).get(KEY_FC_VALEUR));
            editModel.setFcNbrePartMinJ(fraisList.get(i).get(KEY_FC_NBRE_PART_MIN_J));
            editModel.setFcNbrePartMinF(fraisList.get(i).get(KEY_FC_NBRE_PART_MIN_F));
            editModel.setFcNbrePartMinH(fraisList.get(i).get(KEY_FC_NBRE_PART_MIN_H));

            editModel.setFraisLibelle(fraisList.get(i).get(KEY_FC_NEW_LIBELLE));
            editModel.setFraisID(fraisList.get(i).get(KEY_FC_NUMERO));
            editModel.setFraisFonction(fraisList.get(i).get(KEY_FC_FONCTION));
            editModel.setFraisNature(fraisList.get(i).get(KEY_FcNature));
            editModel.setFraisBase(fraisList.get(i).get(KEY_FcBase));
            editModel.setAdherent(adherent);
            editModel.setFcNbrePartSouhaiter(editModel.getFcNbrePartSocialeAdherentByAge()+"");
            list.add(editModel);
        }
        return list;
    }
    /**
     * Fetches the list of movies from the server
     */
    public  class FetchFraisGuichetAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(GetFraisAdherent.this);
            pDialog.setMessage("Loading frais. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_CAISSE_ID, String.valueOf(MyData.CAISSE_ID));
            httpParams.put(KEY_GUICHET_ID, String.valueOf(MyData.GUICHET_ID));
            httpParams.put(KEY_TYPE_MEMBRE, typeMembreId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_frais_by_guichet.php", "GET", httpParams);

            Log.e("Response fetch_all_frais_by_guichet: ", "> " + jsonObject+"");
            Log.e("Param fetch_all_frais_by_guichet: ", "> " + httpParams+"");
            fraisList.clear();
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray movies;
                if (success == 1) {
                    movies = jsonObject.getJSONArray(KEY_DATA);
                    //Iterate through the response and populate movies list
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject fraisGuichet = movies.getJSONObject(i);
                        Integer fraisGuichetId = fraisGuichet.getInt(KEY_FC_NUMERO);
                        String fraisGuichetLibelle = fraisGuichet.getString(KEY_FC_NEW_LIBELLE);
                        String fraisGuichetFonction = fraisGuichet.getString(KEY_FC_FONCTION);
                        String fraisGuichetNbrePartMinJ = fraisGuichet.getString(KEY_FC_NBRE_PART_MIN_J);
                        String fraisGuichetNbrePartMinF = fraisGuichet.getString(KEY_FC_NBRE_PART_MIN_F);
                        String fraisGuichetNbrePartMinH = fraisGuichet.getString(KEY_FC_NBRE_PART_MIN_H);
                        String fraisGuichetValeur = fraisGuichet.getString(KEY_FC_VALEUR);
                        String fraisGuichetFcNature = fraisGuichet.getString(KEY_FcNature);
                        String fraisGuichetFcBase = fraisGuichet.getString(KEY_FcBase);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_FC_NUMERO, fraisGuichetId.toString());
                        map.put(KEY_FC_NEW_LIBELLE, fraisGuichetLibelle);
                        map.put(KEY_FC_FONCTION, fraisGuichetFonction);
                        map.put(KEY_FC_NBRE_PART_MIN_J, fraisGuichetNbrePartMinJ);
                        map.put(KEY_FC_NBRE_PART_MIN_F, fraisGuichetNbrePartMinF);
                        map.put(KEY_FC_NBRE_PART_MIN_H, fraisGuichetNbrePartMinH);
                        map.put(KEY_FC_VALEUR, fraisGuichetValeur);
                        map.put(KEY_FcNature, fraisGuichetFcNature);
                        map.put(KEY_FcBase, fraisGuichetFcBase);
                        fraisList.add(map);
                    }
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
                    populateFraisList();
                }
            });
        }
    }
    /**
     * Updating parsed JSON data into ListView
     * */
    private void populateFraisList() {
        editModelArrayList = populateList();
        updatefcvaleur();
        customeAdapter = new CustomeAdapter(GetFraisAdherent.this,editModelArrayList);
        lv.setAdapter(customeAdapter);
    }
    private void updatefcvaleur (){
        for (int i =0; i< editModelArrayList.size();i++){
            editModelArrayList.get(i).getMontantbase(editModelArrayList);
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
            BaseColor colorAccent = new BaseColor(0, 153, 204, 255);
            float fontSize = 40.0f;
            float valueFontSize = 36.0f;

            //Custom font
//            BaseFont fontName = BaseFont.createFont("assets/fonts/brandon_medium.otf","UTF-8" , BaseFont.EMBEDDED );
            BaseFont fontName = BaseFont.createFont("assets/fonts/brandon_medium.otf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//            BaseFont bf = BaseFont.createFont("arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            //Create Title of document
            Font titleFont = new Font(fontName, 26.0f, Font.NORMAL, BaseColor.BLACK);
            try {
//                String value = String.format("%05d", (Integer.parseInt(GxLastBordOperat)+1));

                addNewItem(document, "FICHE D'ADHESION", Element.ALIGN_CENTER, titleFont);
//                    addNewItem(document, "N° "+ value + "-V", Element.ALIGN_CENTER, titleFont);
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
            addNewItemWithLeftAndRight(document, "Opérateur:", MyData.USER_NOM + " " + MyData.USER_PRENOM, orderNumberFont, orderNumberValueFont);

            addLineSeperator(document);

            addNewItem(document, "Détails Opération", Element.ALIGN_CENTER, titleFont);

            addLineSeperator(document);

            //Item 1

            addNewItemWithLeftAndRight(document, "N° Manuel:", adherent.getAdNumManuel(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Type de Membre:", "PERSONNE PHYSIQUE", titleFont, orderNumberValueFont);//A modifier car doit être dynamique
            addNewItemWithLeftAndRight(document, "Détails type de membre:", adherent.getAdDetailsTypeMembre(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Nom adhérent:", adherent.getAdNom(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Prénom adhérent:", adherent.getAdPrenom(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Date de naissance:", adherent.getAdDateNaiss(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Lieu de naissance:", adherent.getAdLieuNaiss(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Sexe:", adherent.getSexeLibelle(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Nationalité:", adherent.getAdNationalite(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Situation matrimoniale:", adherent.getLibelleSituationMat(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Nombre d'enfant:", adherent.getAdNbreEnfACh(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Téléphone 1:", adherent.getAdTel1(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Téléphone 2:", adherent.getAdTel2(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Téléphone 3:", adherent.getAdTel3(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "E-mail:", adherent.getAdEMail(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Profession:", adherent.getAdProfess(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Domicile:", adherent.getAdDomicile(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Lieu de travail:", adherent.getAdLieuTrav(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Activité principale:", adherent.getAdActivitePr(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Type de pièce:", adherent.getLibelleTypePiece(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Numéro de la pièce:", adherent.getAdNumCarteID(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Date de délivrance:", adherent.getAdValideDu(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Date de d'expiration:", adherent.getAdValideAu(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Type de location:", adherent.getLibelleTypeLocation(), titleFont, orderNumberValueFont);

            addLineSeperator(document);

            //Liste des pièces fournies
            addNewItem(document, "Liste des pièces fournies", Element.ALIGN_CENTER, titleFont);
            addLineSeperator(document);
            try {
                for (int i = 0; i < adherent.getListPieceAdherent().size(); i++) {
                    addNewItemWithLeftAndRight(document, i + 1 + "- "+adherent.getListPieceAdherent().get(i), "", titleFont, orderNumberValueFont);
                }
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            //Liste des pièces non fournies
            if (adherent.getListPieceNonFourniesAdherent().size()>0){
                addNewItem(document, "Liste des pièces non fournies", Element.ALIGN_CENTER, titleFont);
                addLineSeperator(document);
                try {
                    for (int i = 0; i < adherent.getListPieceNonFourniesAdherent().size(); i++) {
                        addNewItemWithLeftAndRight(document, i + 1 + "- "+adherent.getListPieceNonFourniesAdherent().get(i), "", titleFont, orderNumberValueFont);
                    }
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                addLineSeperator(document);
            }
            //Liste des frais payés
            addNewItem(document, "Liste des frais payés", Element.ALIGN_CENTER, titleFont);
            addLineSeperator(document);
            try {
                for (int i=0; i<adherent.getListFraisAdherent().size();i++){
                    if (adherent.getListFraisAdherent().get(i).getFraisFonction().equals("P")){
                        addNewItemWithLeftAndRight(document, i+1+"- "+ adherent.getListFraisAdherent().get(i).getFraisLibelle().concat(" Nombre: ").concat(adherent.getListFraisAdherent().get(i).getFcNbrePartSouhaiter()), defaultFormat.format(parseDouble(String.valueOf(Double.parseDouble(adherent.getListFraisAdherent().get(i).getFcValeur())*Double.parseDouble(adherent.getListFraisAdherent().get(i).getFcNbrePartSouhaiter())))), titleFont, orderNumberValueFont);
                    }else{
                        addNewItemWithLeftAndRight(document, i+1+"- "+ adherent.getListFraisAdherent().get(i).getFraisLibelle(), defaultFormat.format(parseDouble(Double.parseDouble(adherent.getListFraisAdherent().get(i).getFcValeur())+"")), titleFont, orderNumberValueFont);
                    }
                }
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            //Item 2
            try {
                addNewItemWithLeftAndRight(document, "Montant total des frais:", defaultFormat.format(parseDouble(calculMontantTotalFrais()+""))+" (" +
                        ""+ FrenchNumberToWords.convert(Double.parseDouble(calculMontantTotalFrais()+""))+" )", titleFont, orderNumberValueFont);
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
//            addNewItemWithLeftAndRight(document, "Type opération:", typeOperation, titleFont, orderNumberValueFont);

            addLineSeperator(document);

            //Signatures
            addLineSpace(document);
            addLineSpace(document);

            addNewItemWithLeftAndRight(document, "Signature Adhérent:", "Signature Caissier:", titleFont, orderNumberValueFont);

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
            PrintDocumentAdapter printDocumentAdapter = new PdfDocumentAdapter(GetFraisAdherent.this, Common.getAppPath(GetFraisAdherent.this)+"bordereau__adhesion_mifucam.pdf");
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
                .setMessage("L'enregistrement de l'adhérent s'est bien effectué !")
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
    public double calculMontantTotalFrais(){
        double eavDepotMin=0.0;
        for (int i = 0; i < CustomeAdapter.editModelArrayList.size(); i++){
            if (CustomeAdapter.editModelArrayList.get(i).getFraisFonction().equals("P")){
                eavDepotMin +=(Double.parseDouble(CustomeAdapter.editModelArrayList.get(i).getFcValeur())*Double.parseDouble(CustomeAdapter.editModelArrayList.get(i).getFcNbrePartSouhaiter()));
            }else{
                eavDepotMin += Double.parseDouble(CustomeAdapter.editModelArrayList.get(i).getFcValeur());
            }
        }
        return eavDepotMin;
    }

    public void information() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Récapitulatif !")
                .setMessage("Montant Total des frais: " + defaultFormat.format(calculMontantTotalFrais())+
                        "\nNom adhérent: " + adherent.getFullName()+
                        "\nDate de naissance: " + adherent.getAdDateNaiss()+
                        "\nTel: " + adherent.getAdTel1()+
                        "\n\t\t Etes-vous sûr de vouloir Enregistrer cet adhérent ?")
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
                        new AddAdherentAsyncTask().execute();
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
                        Dexter.withActivity(GetFraisAdherent.this)
                                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse response) {
                                        createPDFFile(Common.getAppPath(GetFraisAdherent.this)+"bordereau__adhesion_mifucam.pdf");
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
    private void addAdherent() {
        if (true){
            for (int i = 0; i < CustomeAdapter.editModelArrayList.size(); i++){
                //tv.setText(tv.getText() + " " + CustomeAdapter.editModelArrayList.get(i).getEditTextValue() +System.getProperty("line.separator"));
                fraisListFonction+=";"+CustomeAdapter.editModelArrayList.get(i).getFraisFonction();
                if (CustomeAdapter.editModelArrayList.get(i).getFraisFonction().equals("P")){
                    fraisListMontant+=";"+(Double.parseDouble(CustomeAdapter.editModelArrayList.get(i).getFcValeur())*Double.parseDouble(CustomeAdapter.editModelArrayList.get(i).getFcNbrePartSouhaiter()));

                }else{
                    fraisListMontant+=";"+CustomeAdapter.editModelArrayList.get(i).getFcValeur();
                }
                fraisListId+=";"+CustomeAdapter.editModelArrayList.get(i).getFraisID();
                adherent.addListFraisAdherent(CustomeAdapter.editModelArrayList.get(i));
            }
            information();
        } else {
            Toast.makeText(GetFraisAdherent.this,
                    "One or more fields left empty!",
                    Toast.LENGTH_LONG).show();
        }
    }
    /*END BLOC TO MANAGE PDF FILE*/
    /**
     * AsyncTask for adding a adherent
     */
    private class AddAdherentAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // addButton.startAnimation() ;// to start animation on button save
            //Display proggress bar
            pDialog = new ProgressDialog(GetFraisAdherent.this);
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
            httpParams.put(KEY_AD_NUM_MANUEL, adherent.getAdNumManuel());
            httpParams.put(KEY_AD_NOM, adherent.getAdNom());
            httpParams.put(KEY_AD_PRENOM, adherent.getAdPrenom());
            httpParams.put(KEY_AD_DATE_NAISS, adherent.getAdDateNaiss());
            httpParams.put(KEY_AD_LIEU_NAISS, adherent.getAdLieuNaiss());
            httpParams.put(KEY_AD_SEXE, adherent.getAdSexe());
            httpParams.put(KEY_AD_NATIONALITE, adherent.getAdNationalite());
            httpParams.put(KEY_AD_SIT_FAM, adherent.getAdSitFam());
            httpParams.put(KEY_AD_NBRE_ENFANT, adherent.getAdNbreEnfACh());
            httpParams.put(KEY_AD_TEL1, adherent.getAdTel1());
            httpParams.put(KEY_AD_TEL2, adherent.getAdTel2());
            httpParams.put(KEY_AD_TEL3, adherent.getAdTel3());
            httpParams.put(KEY_AD_EMAIL, adherent.getAdEMail());
            httpParams.put(KEY_AD_PROFESSION, adherent.getAdProfess());
            httpParams.put(KEY_AD_DOMICILE, adherent.getAdDomicile());
            httpParams.put(KEY_AD_LIEU_TRAVAIL, adherent.getAdLieuTrav());
            httpParams.put(KEY_AD_ACTIVITE_PRINC, adherent.getAdActivitePr());
            httpParams.put(KEY_AD_TYPE_CARTE_ID, adherent.getAdTypCarteID());
            httpParams.put(KEY_AD_NUM_CARTE_ID, adherent.getAdNumCarteID());
            httpParams.put(KEY_AD_VALIDE_DU, adherent.getAdValideDu());
            httpParams.put(KEY_AD_VALIDE_AU, adherent.getAdValideAu());
            httpParams.put(KEY_AD_TYPE_HABITE, adherent.getAdTypHabite());
            httpParams.put(KEY_ADHERENT_PIECE, piecesListId);
            httpParams.put(KEY_ADHERENT_FRAIS_ID, fraisListId);
            httpParams.put(KEY_ADHERENT_FRAIS_MONTANT, fraisListMontant);
            httpParams.put(KEY_ADHERENT_FRAIS_FONCTION, fraisListFonction);
            httpParams.put(KEY_CV_USER_CREE, String.valueOf(MyData.USER_ID));
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_adherent_1.php", "POST", httpParams);
            try {
                success = jsonObject.getInt(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            // addButton.revertAnimation(); // to stop animation on button save
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        //Start building pdf
                        notificationSuccessAdd();
                        avertissement();
                    } else {
                        Toast.makeText(GetFraisAdherent.this,
                                "Erreur lors de l'ajout de l'Adhérent",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}