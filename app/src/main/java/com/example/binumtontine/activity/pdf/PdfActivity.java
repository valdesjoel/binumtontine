package com.example.binumtontine.activity.pdf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.binumtontine.R;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PdfActivity extends AppCompatActivity {
    private static final int STORAGE_CODE = 1000;
    //declaring views
    EditText mTextEt;
    Button mSaveBtn;
    List <View> viewList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        //initializing views (activity_main.xml)
        mTextEt = findViewById(R.id.textEt);
        mSaveBtn = findViewById(R.id.saveBtn);
        viewList.add(mSaveBtn);
        viewList.add(mTextEt);


        //handle button click
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //we need to handle runtime permission for devices with marshmallow and above
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                    //system OS >= Marshmallow(6.0), check if permission is enabled or not
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED){
                        //permission was not granted, request it
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, STORAGE_CODE);
                    }
                    else {
                        //permission already granted, call save pdf method
//                        savePdf();
                        String mFileName = new SimpleDateFormat("yyyyMMdd_HHmmss",
                                Locale.getDefault()).format(System.currentTimeMillis());
                        //pdf file path
                        String mFilePath = Environment.getExternalStorageDirectory() + "/" + mFileName + ".pdf";
                        PDFUtil pdfUtil = PDFUtil.getInstance();
                        pdfUtil.generatePDF(viewList, mFilePath, null  );
                    }
                }
                else {
                    //system OS < Marshmallow, call save pdf method
//                    savePdf();
                    String mFileName = new SimpleDateFormat("yyyyMMdd_HHmmss",
                            Locale.getDefault()).format(System.currentTimeMillis());
                    //pdf file path
                    String mFilePath = Environment.getExternalStorageDirectory() + "/" + mFileName + ".pdf";
                    PDFUtil pdfUtil = PDFUtil.getInstance();
                    pdfUtil.generatePDF(viewList, mFilePath, null  );
                }
            }
        });


    }
    private void savePdf() {
        //create object of Document class
        Document mDoc = new Document();
        //pdf file name
        String mFileName = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(System.currentTimeMillis());
        //pdf file path
        String mFilePath = Environment.getExternalStorageDirectory() + "/" + mFileName + ".pdf";

        try {
            //create instance of PdfWriter class
            PdfWriter.getInstance(mDoc, new FileOutputStream(mFilePath));
            //open the document for writing
            mDoc.open();
            //get text from EditText i.e. mTextEt
            String mText = mTextEt.getText().toString();

            //add author of the document (optional)
            mDoc.addAuthor("MIFUCAM");

            //add paragraph to the document
            mDoc.add(new Paragraph(mText));

            //close the document
            mDoc.close();
            //show message that file is saved, it will show file name and file path too
            Toast.makeText(this, mFileName +".pdf\nis saved to\n"+ mFilePath, Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            //if any thing goes wrong causing exception, get and show exception message
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    //handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case STORAGE_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission was granted from popup, call savepdf method
                    savePdf();
                }
                else {
                    //permission was denied from popup, show error message
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    /*Write PDF using EditText (Description):
      1) We will use iText PDF Library to create pdf
      2) We will input text Using EditText
      3) Button to save text as PDF file
      4) It will require WRITE_EXTERNAL_STORAGE permission to save pdf file,
      5) We'll handle runtime permission too*/
//Library link is available in description of the video...



}