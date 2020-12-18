package com.example.binumtontine.activity.pdf;

//import android.media.Image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyEvent extends PdfPageEventHelper {

        Image image;
    private Context context;

    public MyEvent(Context context) {
        this.context = context;
    }

public  void onOpenDocument(PdfWriter writer, Document document) {
//        image = Image.getInstance(Server.MapPath("~/images/draft.png"));

    try {
        // get input stream
        InputStream ims = context.getAssets().open("logomifucam.png");
        Bitmap bmp = BitmapFactory.decodeStream(ims);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        image = Image.getInstance(stream.toByteArray());
        document.add(image);
    }
    catch(IOException ex)
    {
        return;
    } catch (DocumentException e) {
        e.printStackTrace();
    }
//        image = Image.getInstance("C:/logomifucam.png");
    image.setAbsolutePosition(12, 300);
        }

public  void onEndPage(PdfWriter writer, Document document)  {
//        writer.directContent.AddImage(image);
    try {
        writer.addDirectImageSimple(image);
    } catch (DocumentException e) {
        e.printStackTrace();
    }
}
//public void onEndPage(PdfWriter writer, Document document) {
//    Rectangle rect = writer.getBoxSize("headerBox");
//    // add header text
//    BaseFont fontName = null;
//    try {
//        fontName = BaseFont.createFont("assets/fonts/brandon_medium.otf",BaseFont.IDENTITY_H , BaseFont.EMBEDDED );
//    } catch (DocumentException e) {
//        e.printStackTrace();
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
////            BaseFont bf = BaseFont.createFont("arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//    //Create Title of document
//    Font titleFont = new Font(fontName,36.0f,Font.NORMAL, BaseColor.BLACK);
//    ColumnText.showTextAligned(writer.getDirectContent(),
//            Element.ALIGN_RIGHT, new Phrase("Hello", titleFont),
//            rect.getLeft(), rect.getTop(), 0);
//
//    // add header image
//    try {
//        try {
//            // get input stream
//            InputStream ims = context.getAssets().open("logomifucam.png");
//            Bitmap bmp = BitmapFactory.decodeStream(ims);
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            image = Image.getInstance(stream.toByteArray());
//            document.add(image);
//        }
//        catch(IOException ex)
//        {
//            return;
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
////        Image img = Image.getInstance("C:/logomifucam.png");
//        image.scaleToFit(100,100);
//        document.add(image);
//    } catch (Exception x) {
//        x.printStackTrace();
//    }
//
//}
        }