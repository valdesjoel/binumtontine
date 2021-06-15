package com.example.binumtontine.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.adherent.HistoriqueEAV;
import com.example.binumtontine.activity.adherent.ListCompteAdherentActivity_New;
import com.example.binumtontine.activity.adherent.OperationEAV;
import com.example.binumtontine.activity.adherent.Record;
import com.example.binumtontine.activity.convertisseur.FrenchNumberToWords;
import com.example.binumtontine.activity.pdf.Common;
import com.example.binumtontine.activity.pdf.PdfDocumentAdapter;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.helper.CheckNetworkStatus;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static androidx.core.content.ContextCompat.getSystemService;
import static java.lang.Double.parseDouble;

public class RecordAdapter extends BaseAdapter {

    private Context recordContext;
    private List<Record> recordList;
    private final String FILE_NAME = "historique_detail_mifucam.pdf";

    public RecordAdapter(Context context, List<Record> records) {
        recordList = records;
        recordContext = context;
    }

    public void add(Record record) {
        recordList.add(record);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return recordList.size();
    }
    @Override
    public Object getItem(int i) {
        return recordList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final RecordViewHolder holder;

        if (view ==null){
            LayoutInflater recordInflater = (LayoutInflater)
                    recordContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = recordInflater.inflate(R.layout.record, null);

            holder = new RecordViewHolder();
            holder.ageView = (TextView) view.findViewById(R.id.record_age);
            holder.nameView = (TextView) view.findViewById(R.id.record_name);
            holder.positionView = (TextView) view.findViewById(R.id.record_position);
            holder.addressView = (TextView) view.findViewById(R.id.record_address);
            holder.printView = (Button) view.findViewById(R.id.record_aaction);
            view.setTag(holder);

        }else {
            holder = (RecordViewHolder) view.getTag();
        }

        final Record record = (Record) getItem(i);
        if ((record.age).equals("D") || (record.age).equals("I")){
            holder.ageView.setText(record.position);
            holder.positionView.setText("");
        }else if((record.age).equals("R")){
            holder.ageView.setText("");
            holder.positionView.setText(record.position);
        }else{

        holder.ageView.setText(record.age);
        holder.positionView.setText(record.position);
        }
        holder.nameView.setText(record.name);
        holder.addressView.setText(record.address);

        /*BEGIN manage on click on holder*/
        //debut
        holder.printView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(recordContext)) {
                    avertissement(record);
                } else {
                    Toast.makeText(recordContext,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        /*END manage on click on holder*/
        return view;
    }

    private static class RecordViewHolder {
        public TextView nameView;
        public TextView positionView;
        public TextView ageView;
        public TextView addressView;
        public Button printView;
    }


    /*START BLOC TO MANAGE PDF FILE*/
    private void createPDFFile(String path, Record record) {
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
//                String value = String.format("%05d", (Integer.parseInt(GxLastBordOperat)+1));
                if (record.age.equals("D") || record.age.equals("I")){
                    addNewItem(document, "DETAIL SUR L'OPERATION DE VERSEMENT", Element.ALIGN_CENTER, titleFont);
//                    addNewItem(document, "N° "+ value + "-V", Element.ALIGN_CENTER, titleFont);

                }else if (record.age.equals("R")){
                    addNewItem(document, "DETAIL SUR L'OPERATION DE RETRAIT", Element.ALIGN_CENTER, titleFont);
//                    addNewItem(document, "N° Opération:"+ value+ "-R", Element.ALIGN_CENTER, titleFont);

                }else{
                    addNewItem(document, "DETAIL SUR L'OPERATION", Element.ALIGN_CENTER, titleFont);
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
            addNewItemWithLeftAndRight(document, "Imprimé le:", dateOperation, orderNumberFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Imprimé par:", MyData.USER_NOM + " "+ MyData.USER_PRENOM, orderNumberFont, orderNumberValueFont);

            addLineSeperator(document);

            addNewItem(document, "Détails Opération", Element.ALIGN_CENTER, titleFont);

            addLineSeperator(document);

            //Item 1
            addNewItemWithLeftAndRight(document, "Type de compte:", "EAV", titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "N° Manuel:", ListCompteAdherentActivity_New.adherent.getAdNumManuel(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Nom client:", ListCompteAdherentActivity_New.adherent.getFullName(), titleFont, orderNumberValueFont);

            addNewItemWithLeftAndRight(document, "Date opération:", record.name, orderNumberFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Opérateur:", MyData.USER_NOM + " "+ MyData.USER_PRENOM, orderNumberFont, orderNumberValueFont);

            addLineSeperator(document);


            //Item 2
            try {
                addNewItemWithLeftAndRight(document, "Montant opération:", record.position, titleFont, orderNumberValueFont);
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            if (record.age.equals("D")){
                addNewItemWithLeftAndRight(document, "Type opération:", "Dépôt sur compte", titleFont, orderNumberValueFont);
            }else if (record.age.equals("I")) {

                addNewItemWithLeftAndRight(document, "Type opération:","Dépôt Initial sur compte", titleFont, orderNumberValueFont);
            }else if (record.age.equals("R")) {

                addNewItemWithLeftAndRight(document, "Type opération:","Retrait sur compte", titleFont, orderNumberValueFont);
            }else{
                addNewItemWithLeftAndRight(document, "Type opération:",record.age, titleFont, orderNumberValueFont);
            }

            addLineSeperator(document);

            //Signatures
            addLineSpace(document);
            addLineSpace(document);

//            addNewItemWithLeftAndRight(document, "Signature Client:", "Signature Caissier:", titleFont, orderNumberValueFont);

            document.close();

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
        PrintManager printManager = (PrintManager)recordContext.getSystemService(Context.PRINT_SERVICE);
        try {
            PrintDocumentAdapter printDocumentAdapter = new PdfDocumentAdapter(recordContext, Common.getAppPath(recordContext)+FILE_NAME);
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
    public void avertissement(final Record record) {
        new AlertDialog.Builder(recordContext)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Impression du Bordereau de cette opération !")
                .setMessage(
                        "\t\t Voulez-vous imprimer le bordereau ?"
                )
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
                        Dexter.withActivity((Activity) recordContext)
                                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse response) {
                                        createPDFFile(Common.getAppPath(recordContext)+FILE_NAME,record);
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
    /*END BLOC TO MANAGE PDF FILE*/
}
