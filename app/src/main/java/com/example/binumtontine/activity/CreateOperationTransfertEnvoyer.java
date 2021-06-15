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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.binumtontine.JRSpinner;
import com.example.binumtontine.R;
import com.example.binumtontine.activity.adherent.Adherent;
import com.example.binumtontine.activity.convertisseur.FrenchNumberToWords;
import com.example.binumtontine.activity.pdf.Common;
import com.example.binumtontine.activity.pdf.PdfDocumentAdapter;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.example.binumtontine.modele.Transfert;
import com.hbb20.CountryCodePicker;
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
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class CreateOperationTransfertEnvoyer extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static String STRING_EMPTY = "";
    private NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());

    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Category> AdherentExpediteursListNom;
    private ArrayList<Category> AdherentExpediteursListPrenom;
    List<Integer> AdherentExpediteursListID = new ArrayList<Integer>();
    List<Integer> AdherentExpediteursListCvNumero = new ArrayList<Integer>();

    private ArrayList<Category> AdherentExpediteursListNomDest;
    private ArrayList<Category> AdherentExpediteursListPrenomDest;
    List<Integer> AdherentExpediteursListIDDest = new ArrayList<Integer>();
    List<Integer> AdherentExpediteursListCvNumeroDest = new ArrayList<Integer>();
    private Spinner spinnerListNomExp;
    private Spinner spinnerListNomDest;

    /*end manage*/
    private Button addButton;
    private Button annulerButton;
    private int success;
    private ProgressDialog pDialog;
    private ProgressDialog pDialogFetchProduitEavList;

    public static Transfert transfert = new Transfert();
    private AppCompatEditText acetStatus;
    private ListPopupWindow statusPopupList;
    private ArrayList<HashMap<String, String>> movieList;
    private TextView tv_TrRefOper;
    private RadioButton rb_TrTypTransf_CC;
    private RadioButton rb_TrTypTransf_CE;
    private RadioButton rb_TrTypTransf_EC;
    private RadioButton rb_TrTypTransf_EE;
    private EditText TrMontant;
    private EditText TrMtFrais;
    private EditText TrMtTaxes;
    private TextView tv_total;
    private TextView tv_2;

    public static CountryCodePicker ccp_phone1;
    public static CountryCodePicker ccp_phone2;
    private EditText editTextCarrierPhone1;
    private EditText editTextCarrierPhone2;
    public static EditText TrNomExp;
    public static EditText TrPrenomExp;

    public static JRSpinner TrTypPieceIdExpSpinner;
    public static EditText TrPieceIdExp;
    public static EditText TrAdresseExp;
    private EditText TrCodeSecret;
    private EditText TrDetailsExp;
    private String TrTypPieceIdExp;
    public static EditText TrNomDest;
    public static EditText TrPrenomDest;

    public static JRSpinner TrTypPieceIdDestSpinner;
    public static EditText TrPieceIdDest;
    public static EditText TrAdresseDest;
    private EditText TrDetailsDest;
    private String TrTypPieceIdDest;
    private String TrTypTransf;
    private TextView tv_guichet_exp;
    private LinearLayout ll_content;
    private String PdNature;
    private String PdValTaux;
    private String MontantTotal;
    private static final String KEY_GUICHET_ID = "gx_numero";
    public static TextView tv_solde_eav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_operation_transfert_envoyer);
        acetStatus = findViewById(R.id.acet_status);
        defaultFormat.setCurrency(Currency.getInstance("XAF"));
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        tv_TrRefOper = (TextView)findViewById(R.id.tv_TrRefOper);
        rb_TrTypTransf_CC = (RadioButton) findViewById(R.id.rb_TrTypTransf_CC);
        rb_TrTypTransf_CE = (RadioButton) findViewById(R.id.rb_TrTypTransf_CE);
        rb_TrTypTransf_EC = (RadioButton) findViewById(R.id.rb_TrTypTransf_EC);
        rb_TrTypTransf_EE = (RadioButton) findViewById(R.id.rb_TrTypTransf_EE);
        TrMontant = (EditText) findViewById(R.id.input_txt_TrMontant);
        TrMontant.addTextChangedListener(MyData.onTextChangedListener(TrMontant));

        TrMtFrais = (EditText) findViewById(R.id.input_txt_TrMtFrais);
        TrMtTaxes = (EditText) findViewById(R.id.input_txt_TrMtTaxes);
        tv_total = (TextView) findViewById(R.id.tv_total);
        tv_2 = (TextView) findViewById(R.id.tv_2);

        TrNomExp = (EditText) findViewById(R.id.input_TrNomExp);

        TrPrenomExp = (EditText) findViewById(R.id.input_TrPrenomExp);
        ccp_phone1 = (CountryCodePicker) findViewById(R.id.ccp_TrTelExp);
        editTextCarrierPhone1 = (EditText) findViewById(R.id.editText_carrierTrTelExp);
        ccp_phone1.registerCarrierNumberEditText(editTextCarrierPhone1);
        spinnerListNomExp = (Spinner) findViewById(R.id.spn_list_nom_exp);
        spinnerListNomDest = (Spinner) findViewById(R.id.spn_list_nom_dest);
        spinnerListNomExp.setOnItemSelectedListener(CreateOperationTransfertEnvoyer.this);
        spinnerListNomDest.setOnItemSelectedListener(CreateOperationTransfertEnvoyer.this);

        TrTypPieceIdExpSpinner = (JRSpinner)findViewById(R.id.spn_TrTypPieceIdExp);
        TrTypPieceIdExpSpinner.setItems(getResources().getStringArray(R.array.array_type_piece)); //this is important, you must set it to set the item list
        TrTypPieceIdExpSpinner.setTitle("Sélectionner une pièce d'identification"); //change title of spinner-dialog programmatically
        TrTypPieceIdExpSpinner.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        TrTypPieceIdExpSpinner.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position
                if (TrTypPieceIdExpSpinner.getText().toString().equals("Carte nationale d'identité")){
                    TrTypPieceIdExp = "CN";
                }else if (TrTypPieceIdExpSpinner.getText().toString().equals("Carte de séjour")){
                    TrTypPieceIdExp = "CS";
                }else if (TrTypPieceIdExpSpinner.getText().toString().equals("Carte consulaire")){
                    TrTypPieceIdExp = "CC";
                }else if (TrTypPieceIdExpSpinner.getText().toString().equals("Carte militaire")){
                    TrTypPieceIdExp = "CM";
                }else if (TrTypPieceIdExpSpinner.getText().toString().equals("Carte CNPS")){
                    TrTypPieceIdExp = "PS";
                }else if (TrTypPieceIdExpSpinner.getText().toString().equals("Permis de conduire")){
                    TrTypPieceIdExp = "PC";
                }else if (TrTypPieceIdExpSpinner.getText().toString().equals("Passeport")){
                    TrTypPieceIdExp = "PP";
                }
            }
        });
        TrPieceIdExp = (EditText) findViewById(R.id.input_TrPieceIdExp);
        TrAdresseExp = (EditText) findViewById(R.id.input_TrAdresseExp);
        tv_solde_eav = (TextView) findViewById(R.id.tv_solde_eav);
        TrCodeSecret = (EditText) findViewById(R.id.input_TrCodeSecret);
        TrDetailsExp = (EditText) findViewById(R.id.input_TrDetailsExp);

        TrNomDest = (EditText) findViewById(R.id.input_TrNomDest);
        TrPrenomDest = (EditText) findViewById(R.id.input_TrPrenomDest);


        TrTypPieceIdDestSpinner = (JRSpinner)findViewById(R.id.spn_TrTypPieceIdDest);
        TrTypPieceIdDestSpinner.setItems(getResources().getStringArray(R.array.array_type_piece)); //this is important, you must set it to set the item list
        TrTypPieceIdDestSpinner.setTitle("Sélectionner une pièce d'identification"); //change title of spinner-dialog programmatically
        TrTypPieceIdDestSpinner.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        TrTypPieceIdDestSpinner.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position
                if (TrTypPieceIdDestSpinner.getText().toString().equals("Carte nationale d'identité")){
                    TrTypPieceIdDest = "CN";
                }else if (TrTypPieceIdDestSpinner.getText().toString().equals("Carte de séjour")){
                    TrTypPieceIdDest = "CS";
                }else if (TrTypPieceIdDestSpinner.getText().toString().equals("Carte consulaire")){
                    TrTypPieceIdDest = "CC";
                }else if (TrTypPieceIdDestSpinner.getText().toString().equals("Carte militaire")){
                    TrTypPieceIdDest = "CM";
                }else if (TrTypPieceIdDestSpinner.getText().toString().equals("Carte CNPS")){
                    TrTypPieceIdDest = "PS";
                }else if (TrTypPieceIdDestSpinner.getText().toString().equals("Permis de conduire")){
                    TrTypPieceIdDest = "PC";
                }else if (TrTypPieceIdDestSpinner.getText().toString().equals("Passeport")){
                    TrTypPieceIdDest = "PP";
                }

            }
        });
        TrPieceIdDest = (EditText) findViewById(R.id.input_TrPieceIdDest);
        TrAdresseDest = (EditText) findViewById(R.id.input_TrAdresseDest);
        TrDetailsDest = (EditText) findViewById(R.id.input_TrDetailsDest);
        tv_guichet_exp = (TextView) findViewById(R.id.tv_guichet_exp);
        tv_guichet_exp.setText(MyData.GUICHET_NAME);

        ccp_phone2 = (CountryCodePicker) findViewById(R.id.ccp_TrTelDest);
        editTextCarrierPhone2 = (EditText) findViewById(R.id.editText_carrierTrTelDest);
        ccp_phone2.registerCarrierNumberEditText(editTextCarrierPhone2);
        new GetAdherentListExp().execute();

        addButton = (Button) findViewById(R.id.btn_save);
        annulerButton = (Button) findViewById(R.id.btn_clean);
        annulerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(CreateOperationTransfertEnvoyer.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addOperationExterneDetails();
                } else {
                    Toast.makeText(CreateOperationTransfertEnvoyer.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void setListeners() {
        acetStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusPopupList.show();
            }
        });
    }
    private void setPopupList() {
        final List<String> status = new ArrayList<>();
        movieList = new ArrayList<>();
        for (int i=0; i<AdherentExpediteursListNom.size();i++){
            status.add(AdherentExpediteursListNom.get(i).getName());
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("KEY_ED_NUMERO", String.valueOf(AdherentExpediteursListNom.get(i).getId()));
            map.put("KEY_ED_LIBELLE", AdherentExpediteursListNom.get(i).getName()+" "+AdherentExpediteursListNom.get(i).getTaux());
            movieList.add(map);
        }
        statusPopupList = new ListPopupWindow(CreateOperationTransfertEnvoyer.this);
        ListAdapter adapter = new SimpleAdapter(
                CreateOperationTransfertEnvoyer.this, movieList,
                R.layout.list_item, new String[]{"KEY_ED_NUMERO",
                "KEY_ED_LIBELLE"},
                new int[]{R.id.movieId, R.id.movieName});
        statusPopupList.setAnchorView(acetStatus); //this let as set the popup below the EditText
        statusPopupList.setAdapter(adapter);
        statusPopupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String movieId = ((TextView) view.findViewById(R.id.movieName))
                        .getText().toString();
                acetStatus.setText(movieId);//we set the selected element in the EditText
                statusPopupList.dismiss();
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

                case R.id.input_txt_TrMontant:
//                 str = "10000";
                    TrMontant.addTextChangedListener(new TextWatcher() {

                        public void afterTextChanged(Editable s) {
                           if ((TrMontant.getText().toString().trim()).equals("")){
                                tv_total.setText(defaultFormat.format(parseDouble("0")));
                            }else {
                                new FetchFraisTransfertAsyncTask().execute();
                            }
                            // yourEditText...
                        }
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    });
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("Memory exceptions","exceptions"+e);
            return ;
        }

    }
    public void onRadioButtonClicked(View view) {
//        boolean checked1 = ((RadioButton) view).isChecked();
//        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.rb_TrTypTransf_CC:
                if (rb_TrTypTransf_CC.isChecked()) {
                    ll_content.setVisibility(View.VISIBLE);
                    TrTypTransf ="CC";
                   // loadOperationExterneList();
//                    long first14 = (long) (Math.random() * 100000000000000L);
                    String dateOperation = new SimpleDateFormat("ss-ddMM-HHmm-",
                            Locale.getDefault()).format(System.currentTimeMillis());
                    long first14 = (long) (Math.random() * 10000L);
                    tv_TrRefOper.setText(TrTypTransf+dateOperation+first14);

                    TrNomExp.setFocusable(false);
                    TrPrenomExp.setFocusable(false);
                    TrNomDest.setFocusable(false);
                    TrPrenomDest.setFocusable(false);

                    TrNomExp.setClickable(true);
                    TrPrenomExp.setClickable(false);
                    TrNomDest.setClickable(true);
                    TrPrenomDest.setClickable(false);
                    try {
                        TrNomExp.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent  i = new Intent(CreateOperationTransfertEnvoyer.this, ListNomPrenomAdherentTransfert.class);
                                i.putExtra(KEY_GUICHET_ID, MyData.GUICHET_ID+"");
                                i.putExtra("sens", "exp");
                                startActivity(i);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        TrNomDest.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent  i = new Intent(CreateOperationTransfertEnvoyer.this, ListGuichetCaisse.class);
                                i.putExtra("sens", "dest");
                                startActivity(i);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.rb_TrTypTransf_CE:
                if (rb_TrTypTransf_CE.isChecked()) {
                    ll_content.setVisibility(View.VISIBLE);
                    TrTypTransf ="CE";
                    String dateOperation = new SimpleDateFormat("ss-ddMM-HHmm-",
                            Locale.getDefault()).format(System.currentTimeMillis());
                    long first14 = (long) (Math.random() * 10000L);
                    tv_TrRefOper.setText(TrTypTransf+dateOperation+first14);
                    TrNomExp.setFocusable(false);
                    TrPrenomExp.setFocusable(false);
                    TrNomDest.setFocusableInTouchMode(true);
                    TrPrenomDest.setFocusableInTouchMode(true);

                    TrNomExp.setClickable(true);
                    TrPrenomExp.setClickable(false);
                    TrNomDest.setClickable(false);
                    TrPrenomDest.setClickable(false);

                    try {

                        TrNomDest.setOnClickListener(null);
                        TrNomExp.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent  i = new Intent(CreateOperationTransfertEnvoyer.this, ListNomPrenomAdherentTransfert.class);
                                i.putExtra(KEY_GUICHET_ID, MyData.GUICHET_ID+"");
                                i.putExtra("sens", "exp");
                                startActivity(i);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.rb_TrTypTransf_EC:
                if (rb_TrTypTransf_EC.isChecked()) {
                    ll_content.setVisibility(View.VISIBLE);
                    TrTypTransf ="EC";
                    String dateOperation = new SimpleDateFormat("ss-ddMM-HHmm-",
                            Locale.getDefault()).format(System.currentTimeMillis());
                    long first14 = (long) (Math.random() * 10000L);
                    tv_TrRefOper.setText(TrTypTransf+dateOperation+first14);
                    TrNomExp.setFocusableInTouchMode(true);
                    TrPrenomExp.setFocusableInTouchMode(true);
                    TrNomDest.setFocusable(false);
                    TrPrenomDest.setFocusable(false);
                    try {
                        TrNomExp.setOnClickListener(null);
                        TrNomExp.setClickable(false);
                        TrNomDest.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent  i = new Intent(CreateOperationTransfertEnvoyer.this, ListGuichetCaisse.class);
                                i.putExtra("sens", "dest");
                                startActivity(i);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.rb_TrTypTransf_EE:
                if (rb_TrTypTransf_EE.isChecked()) {
                    ll_content.setVisibility(View.VISIBLE);
                    TrTypTransf ="EE";
                    String dateOperation = new SimpleDateFormat("ss-ddMM-HHmm-",
                            Locale.getDefault()).format(System.currentTimeMillis());
                    long first14 = (long) (Math.random() * 10000L);
                    tv_TrRefOper.setText(TrTypTransf+dateOperation+first14);
                    try {
                        TrNomDest.setOnClickListener(null);
                        TrNomDest.setClickable(false);
                        TrNomExp.setOnClickListener(null);
                        TrNomExp.setClickable(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    TrNomExp.setFocusableInTouchMode(true);
                    TrPrenomExp.setFocusableInTouchMode(true);
                    TrNomDest.setFocusableInTouchMode(true);
                    TrPrenomDest.setFocusableInTouchMode(true);
                }
                break;
        }
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
                        Dexter.withActivity(CreateOperationTransfertEnvoyer.this)
                                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse response) {
                                        createPDFFile(Common.getAppPath(CreateOperationTransfertEnvoyer.this)+"transfert_mifucam.pdf");
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
            float fontSize = 15.0f;
            float valueFontSize = 18.0f;

            //Custom font
//            BaseFont fontName = BaseFont.createFont("assets/fonts/brandon_medium.otf","UTF-8" , BaseFont.EMBEDDED );
            BaseFont fontName = BaseFont.createFont("assets/fonts/brandon_medium.otf",BaseFont.IDENTITY_H , BaseFont.EMBEDDED );
//            BaseFont bf = BaseFont.createFont("arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            //Create Title of document
            Font titleFont = new Font(fontName,16.0f,Font.NORMAL, BaseColor.BLACK);
            if (TrTypTransf.equals("CC")){
                addNewItem(document, "TRANSFERT DE COMPTE A COMPTE", Element.ALIGN_CENTER, titleFont);
            }else if (TrTypTransf.equals("CE")){
                addNewItem(document, "TRANSFERT DE COMPTE A ESPECE", Element.ALIGN_CENTER, titleFont);
            }else if (TrTypTransf.equals("EC")){
                addNewItem(document, "TRANSFERT D'ESPECE A COMPTE", Element.ALIGN_CENTER, titleFont);
            }else if (TrTypTransf.equals("EE")){
                addNewItem(document, "TRANSFERT D'ESPECE A ESPECE", Element.ALIGN_CENTER, titleFont);
            }

            addNewItem(document, "Ref: "+ tv_TrRefOper.getText(), Element.ALIGN_CENTER, titleFont);

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


            addNewItemWithLeftAndRight(document, "Nom expéditeur:", transfert.getTrNomExp()+" "+transfert.getTrPrenomExp(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Téléphone Exp:", transfert.getTrTelExp(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Adresse:", transfert.getTrAdresseExp(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Type de pièce:", Transfert.decodeTypePiece(transfert.getTrTypPieceIdExp()), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "N° de la pièce:", transfert.getTrPieceIdExp(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Code secret:", transfert.getTrCodeSecret(), titleFont, orderNumberValueFont);
            addLineSeperator(document);
            //Item 2
            addNewItemWithLeftAndRight(document, "Nom destinataire:", transfert.getTrNomDest()+" "+transfert.getTrPrenomDest(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Téléphone Dest:", transfert.getTrTelDest(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Adresse Dest:", transfert.getTrAdresseDest(), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "Type de pièce:", Transfert.decodeTypePiece(transfert.getTrTypPieceIdDest()), titleFont, orderNumberValueFont);
            addNewItemWithLeftAndRight(document, "N° de la pièce Dest:", transfert.getTrPieceIdDest(), titleFont, orderNumberValueFont);
            addLineSeperator(document);


            //Item 3
            try {

                /*addNewItemWithLeftAndRight(document, "Montant du transfert:", defaultFormat.format(parseDouble(transfert.getTrMontant()))+" (" +
                        ""+ FrenchNumberToWords.convert(Long.parseLong(transfert.getTrMontant()))+" )", titleFont, orderNumberValueFont);
                addNewItemWithLeftAndRight(document, "Frais du transfert:", defaultFormat.format(parseDouble(PdValTaux))+" (" +
                        ""+ FrenchNumberToWords.convert(Long.parseLong(PdValTaux))+" )", titleFont, orderNumberValueFont);
                addNewItemWithLeftAndRight(document, "Montant Total:", defaultFormat.format(parseDouble(MontantTotal))+" (" +
                        ""+ FrenchNumberToWords.convert(Long.parseLong(MontantTotal))+" )", titleFont, orderNumberValueFont);

//                addNewItemWithLeftAndRight(document, "Montant du transfert:", defaultFormat.format(parseDouble(transfert.getTrMontant())), titleFont, orderNumberValueFont); */
                addNewItemWithLeftAndRight(document, "Montant du transfert:", defaultFormat.format(parseDouble(transfert.getTrMontant()))+" (" +
                        ""+ FrenchNumberToWords.convert(Double.parseDouble(transfert.getTrMontant()))+" )", titleFont, orderNumberValueFont);
                addNewItemWithLeftAndRight(document, "Frais du transfert:", defaultFormat.format(parseDouble(transfert.getTrMtFrais())), titleFont, orderNumberValueFont);
                addNewItemWithLeftAndRight(document, "Montant Total:", defaultFormat.format(parseDouble(MontantTotal)), titleFont, orderNumberValueFont);
            } catch (Exception e) {
                e.printStackTrace();
            }/*catch (DocumentException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }*/
            addNewItemWithLeftAndRight(document, "Type de transaction:", transfert.getTrTypTransf(), titleFont, orderNumberValueFont);

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
            PrintDocumentAdapter printDocumentAdapter = new PdfDocumentAdapter(CreateOperationTransfertEnvoyer.this, Common.getAppPath(CreateOperationTransfertEnvoyer.this)+"transfert_mifucam.pdf");
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
    /**
     * Async task to get all food categories
     * */
    private class GetAdherentListExp extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogFetchProduitEavList = new ProgressDialog(CreateOperationTransfertEnvoyer.this);
            pDialogFetchProduitEavList.setMessage("Fetching produits's list..");
            pDialogFetchProduitEavList.setCancelable(false);
            pDialogFetchProduitEavList.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair(Transfert.KEY_TrGuichetExp, String.valueOf(MyData.GUICHET_ID)));
//            httpParams.add(new BasicNameValuePair(OperationExterne.KEY_OeType, OeType));
            String json = (String) jsonParser.makeServiceCall( BASE_URL + "get_adherents_expediteurs.php", ServiceHandler.GET, httpParams);

//            Log.e("Response: ", "> " + json);
//            Log.e("httpParams: ", "> " + httpParams);
            AdherentExpediteursListNom = new ArrayList<Category>();
            AdherentExpediteursListNomDest = new ArrayList<Category>();

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj
                                .getJSONArray(KEY_DATA);

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category cat = new Category();
                            cat.setId(catObj.getInt(Adherent.KEY_AD_AdNumero));
                            cat.setName(catObj.getString(Adherent.KEY_AD_AdNom));
                            cat.setTaux(catObj.getString(Adherent.KEY_AD_AdPrenom));
                            cat.setFk(catObj.getInt("TrCptEAVExp"));
//                            cat.setMyDate(catObj.getString("CvMtSolde"));
                            /*Category cat = new Category(catObj.getInt(Adherent.KEY_AD_AdNumero),
                                    catObj.getString(Adherent.KEY_AD_AdNom), catObj.getString(Adherent.KEY_AD_AdPrenom));*/
                            AdherentExpediteursListNom.add(cat);
                            AdherentExpediteursListNomDest.add(cat);
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
//            populateSpinner();
        }

    }

    public void recapitulatifTransfert() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Récapitulatif !")
                .setMessage(
                        "\nNom expéditeur: " + transfert.getTrNomExp()+
                        "\nTéléphone Exp: " + transfert.getTrTelExp()+
                        "\nAdresse Exp: " + transfert.getTrAdresseExp()+
                        "\nType de pièce Exp: " + transfert.getTrTypPieceIdExp()+
                        "\nN° de la pièce Exp: " + transfert.getTrPieceIdExp()+
                        "\nCode secret: " + transfert.getTrCodeSecret()+
                        "\nNom destinataire: " + transfert.getTrNomDest()+
                        "\nTéléphone Dest: " + transfert.getTrTelDest()+
                        "\nAdresse Dest: " + transfert.getTrAdresseDest()+
                        "\nType de pièce Dest: " + transfert.getTrTypPieceIdDest()+
                        "\nN° de la pièce Dest: " + transfert.getTrPieceIdDest()+
                        "\nMontant du transfert: " + transfert.getTrMontant()+
                        "\nFrais du transfert: " + transfert.getTrMtFrais()+
                        "\nMontant total: " + MontantTotal+
                        "\nType de transaction: " + transfert.getTrTypTransf()+
                        /*"\nTotal Retrait: " + tv_retrait.getText().toString()+
                        "\nSolde Théorique: " + tv_total.getText().toString()+
                        "\nSolde Réel: " + SoldeReelEditText.getText().toString().replaceAll(",", "").trim() +
                        "\nMontant écart: " + tv_montant_ecart.getText().toString()+
                        "\nTotal Billetage: " + tv_total_billetage.getText().toString()+*/
                        "\n\n\t\t Etes-vous sûr de vouloir effectuer ce transfert ?")
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
                        new AddOperationEnvoyerTransfertAsyncTask().execute();
                    }

                })
                .show();
    }
    /**
     * Fetches the list of movies from the server
     */
    private class FetchFraisTransfertAsyncTask extends AsyncTask<String, String, String> {
        int successGetFrais;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(CreateOperationTransfertEnvoyer.this);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
//            httpParams.put(Transfert.KEY_TrMontant, transfert.getTrMontant());
            httpParams.put(Transfert.KEY_TrMontant, TrMontant.getText().toString().replaceAll(",", "").trim());
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "calcMontantTotalTransfert.php", "GET", httpParams);
            try {
                 successGetFrais = jsonObject.getInt(KEY_SUCCESS);
                Log.e("calcMontantTotalTransfert", jsonObject+"");
                JSONArray movies;
                if (successGetFrais == 1) {
                     PdNature = jsonObject.getString("PdNature");
                     transfert.setTrMtFrais(jsonObject.getString("PdValTaux"));
                     MontantTotal = jsonObject.getString("MontantTotal");


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
                        recapitulatifTransfert();
//                        new AddOperationEnvoyerTransfertAsyncTask().execute();
                        String trMontant = TrMontant.getText().toString().replaceAll(",", "").trim();
//                        TrMtFrais.setText((parseDouble(trMontant)*0.03)+"");
                        TrMtFrais.setText((parseDouble(MontantTotal))+"");
                        tv_total.setText(defaultFormat.format(parseDouble(trMontant+"")+parseDouble(TrMtFrais.getText().toString().trim()+"")));
//
                        try {
//                            onEditTextClicked(TrMontant);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {

//        eavID = OdOperExterneListID.get(position);//pour recuperer l'ID du guichet selectionnée
//        transfert.setTrCptEAVExp(AdherentExpediteursListID.get(position)); ;//pour recuperer l'ID du cpte eav selectionné


        int idSpinner = parent.getId();

        switch (idSpinner)
        {
            case R.id.spn_list_nom_exp:
                // your stuff here
                try {
                    transfert.setTrCptEAVExp(AdherentExpediteursListCvNumero.get(position)+"");
                    ;//pour recuperer l'ID du cpte eav selectionné
                    TrNomExp.setText(AdherentExpediteursListNom.get(position).getName());
                    TrPrenomExp.setText(AdherentExpediteursListNom.get(position).getTaux());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.spn_list_nom_dest:
                // your stuff here
                try {
                    transfert.setTrCptEAVDest(AdherentExpediteursListCvNumeroDest.get(position)+"");
                    ;//pour recuperer l'ID du cpte eav selectionné
                    TrNomDest.setText(AdherentExpediteursListNomDest.get(position).getName());
                    TrPrenomDest.setText(AdherentExpediteursListNomDest.get(position).getTaux());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    public void notificationChampsVides() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Liste Opération externe absente ")
                .setMessage("Veuilez affecter des Opérations externes sur la caisse de ce guichet!")
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }

                })
                .show();
    }
    public void notificationSuccessAdd() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Transfert réussi ")
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
                        +"\n Solde en caisse insuffisant !"
                )
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .show();
    }
    public void notificationCvMontant() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Solde Insuffisant ")
                .setMessage("Le solde de votre compte EAV est insuffisant!"
                        +"\n Veuillez recharger votre compte !"
                )
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .show();
    }
    public void notificationCodeSecret() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Code secret absent ")
                .setMessage("Le code secret est absent!"
                        +"\n Veuillez renseigner le code secret !"
                )
                .setNegativeButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .show();
    }
    /**
     * Checks whether all files are filled. If so then calls AddOperationEnvoyerTransfertAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addOperationExterneDetails() {
        try {
            if (!STRING_EMPTY.equals(TrMontant.getText().toString().trim()) &&
                    !STRING_EMPTY.equals(TrNomDest.getText().toString().trim())
                    &&                   !STRING_EMPTY.equals((TrCodeSecret.getText().toString().trim())  )
            ) {


                Double montantOperation;

                if (!(TrMontant.getText().toString().replaceAll(",", "").trim()).equals(STRING_EMPTY)) {
                    montantOperation = Double.valueOf(TrMontant.getText().toString().replaceAll(",", "").trim());
                    TrMontant.setText(montantOperation + "");
                }
                transfert.setTrRefOper(tv_TrRefOper.getText().toString().trim());
                transfert.setTrTypTransf(TrTypTransf);
                if ((TrTypTransf.equals("CC") || TrTypTransf.equals("CE") ) && (Double.parseDouble(TrMontant.getText().toString())>Double.parseDouble(transfert.getCvMtSolde())) ){
                    notificationCvMontant();
                    return;
                }
//                transfert.setTrCptEAVExp(TrCptEAVExp.getText().toString().trim());
//                transfert.setTrCptEAVExp(TrCptEAVExp.getText().toString().trim());
                transfert.setTrNomExp(TrNomExp.getText().toString().trim());
                transfert.setTrPrenomExp(TrPrenomExp.getText().toString().trim());
                transfert.setTrTelExp(ccp_phone1.getFullNumberWithPlus());
                transfert.setTrPieceIdExp(TrPieceIdExp.getText().toString().trim());
//                transfert.setTrTypPieceIdExp(TrTypPieceIdExp);
//                transfert.setTrTypPieceIdExp(Transfert.encodeTypePiece(TrTypPieceIdExp));
                transfert.setTrTypPieceIdExp(Transfert.encodeTypePiece(TrTypPieceIdExpSpinner.getText().toString()));
                transfert.setTrAdresseExp(TrAdresseExp.getText().toString().trim());
                transfert.setTrCodeSecret(TrCodeSecret.getText().toString().trim());
                transfert.setTrDetailsExp(TrDetailsExp.getText().toString().trim());
                transfert.setTrGuichetExp(String.valueOf(MyData.GUICHET_ID));
                transfert.setTrAgentExp(String.valueOf(MyData.USER_ID));
//                transfert.setTrCptEAVDest(TrCptEAVExp.getText().toString().trim());
                transfert.setTrNomDest(TrNomDest.getText().toString().trim());
                transfert.setTrPrenomDest(TrPrenomDest.getText().toString().trim());
                transfert.setTrTelDest(ccp_phone2.getFullNumberWithPlus());
                transfert.setTrPieceIdDest(TrPieceIdDest.getText().toString().trim());
                transfert.setTrTypPieceIdDest(TrTypPieceIdDest);
                transfert.setTrTypPieceIdDest(Transfert.encodeTypePiece(TrTypPieceIdDestSpinner.getText().toString()));
                transfert.setTrAdresseDest(TrAdresseDest.getText().toString().trim());
                transfert.setTrDetailsDest(TrDetailsDest.getText().toString().trim());
                transfert.setTrMontant(TrMontant.getText().toString().trim());
                transfert.setTrMtFrais(TrMtFrais.getText().toString().trim());
                transfert.setTrMtTaxes(TrMtTaxes.getText().toString().trim());
                transfert.setTrIsPaye("N");
//                transfert.setTrCodeSecret(TrCodeSecret.getText().toString().trim());
//                transfert.setTrToken(TrToken.getText().toString().trim());

//                operationExterneDetails.setOdLibelle(MyData.normalizeSymbolsAndAccents( ET_OdLibelle.getText().toString().trim()));


//                new AddOperationEnvoyerTransfertAsyncTask().execute();
                new FetchFraisTransfertAsyncTask().execute();


            } else {
                if (STRING_EMPTY.equals(TrMontant.getText().toString().trim())){
                    TrMontant.setError("Remplissez le montant SVP! ");
                    TrMontant.requestFocus();
                }else if (STRING_EMPTY.equals(TrNomDest.getText().toString().trim())){
                    TrNomDest.setError("Remplissez le nom de l'expéditeur  SVP! ");
                    TrNomDest.requestFocus();
               }else if (STRING_EMPTY.equals(TrCodeSecret.getText().toString().trim())){
                    notificationCodeSecret();
                    TrCodeSecret.setError("Remplissez le code secret à communiquer au destinataire  SVP! ");
                    TrCodeSecret.requestFocus();
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    /**
     * AsyncTask for adding a compte eav
     */
    private class AddOperationEnvoyerTransfertAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(CreateOperationTransfertEnvoyer.this);
            pDialog.setMessage("Transfert en cours . Patientez...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(Transfert.KEY_TrRefOper , String.valueOf(transfert.getTrRefOper()));
            httpParams.put(Transfert.KEY_TrTypTransf , transfert.getTrTypTransf());
            httpParams.put(Transfert.KEY_TrCptEAVExp , String.valueOf(transfert.getTrCptEAVExp()));
            httpParams.put(Transfert.KEY_TrNomExp , String.valueOf(transfert.getTrNomExp()));
            httpParams.put(Transfert.KEY_TrPrenomExp , String.valueOf(transfert.getTrPrenomExp()));
            httpParams.put(Transfert.KEY_TrTelExp , String.valueOf(transfert.getTrTelExp()));
            httpParams.put(Transfert.KEY_TrPieceIdExp , String.valueOf(transfert.getTrPieceIdExp()));
            httpParams.put(Transfert.KEY_TrTypPieceIdExp , String.valueOf(transfert.getTrTypPieceIdExp()));
            httpParams.put(Transfert.KEY_TrAdresseExp , String.valueOf(transfert.getTrAdresseExp()));
            httpParams.put(Transfert.KEY_TrDetailsExp , String.valueOf(transfert.getTrDetailsExp()));
            httpParams.put(Transfert.KEY_TrGuichetExp , String.valueOf(transfert.getTrGuichetExp()));
            httpParams.put(Transfert.KEY_TrAgentExp , String.valueOf(transfert.getTrAgentExp()));
            httpParams.put(Transfert.KEY_TrCptEAVDest , String.valueOf(transfert.getTrCptEAVDest()));
            httpParams.put(Transfert.KEY_TrNomDest , String.valueOf(transfert.getTrNomDest()));
            httpParams.put(Transfert.KEY_TrPrenomDest , String.valueOf(transfert.getTrPrenomDest()));
            httpParams.put(Transfert.KEY_TrTelDest , String.valueOf(transfert.getTrTelDest()));
            httpParams.put(Transfert.KEY_TrPieceIdDest , String.valueOf(transfert.getTrPieceIdDest()));
            httpParams.put(Transfert.KEY_TrTypPieceIdDest , String.valueOf(transfert.getTrTypPieceIdDest()));
            httpParams.put(Transfert.KEY_TrAdresseDest , String.valueOf(transfert.getTrAdresseDest()));
            httpParams.put(Transfert.KEY_TrDetailsDest , String.valueOf(transfert.getTrDetailsDest()));
            httpParams.put(Transfert.KEY_TrMontant , String.valueOf(transfert.getTrMontant()));
            httpParams.put(Transfert.KEY_TrMtFrais , String.valueOf(transfert.getTrMtFrais()));
            httpParams.put(Transfert.KEY_TrMtTaxes , String.valueOf(transfert.getTrMtTaxes()));
            httpParams.put(Transfert.KEY_TrCodeSecret , String.valueOf(transfert.getTrCodeSecret()));
            httpParams.put(Transfert.KEY_TrToken , String.valueOf(transfert.getTrToken()));
            httpParams.put(Transfert.KEY_TrIsPaye , String.valueOf(transfert.getTrIsPaye()));
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_operation_envoyer_transfert.php", "POST", httpParams);
            try {
                Log.e("add_operation_envoyer_transfert", jsonObject+"");
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
                    } else  if (success == -1) {
                        notificationEchecAdd();
                    } else {
                        Toast.makeText(CreateOperationTransfertEnvoyer.this,
                                "Erreur lors du transfert !",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}