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


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
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
import com.example.binumtontine.activity.parametreGenerauxOF.ListEtapesDemandesCreditOF;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.example.binumtontine.modele.Transfert;
import com.hbb20.CountryCodePicker;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
/*
    private EditText ET_OdMontant;
    private EditText ET_OdLibelle;

    private RadioButton rb_OdSensOper_Produits;
    private RadioButton rb_OdSensOper_Charges;
*/
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
/*OperationExterneDetails operationExterneDetails = new OperationExterneDetails();
    private String OeType;*/

    private Transfert transfert = new Transfert();
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

    private CountryCodePicker ccp_phone1;
    private CountryCodePicker ccp_phone2;
    private EditText editTextCarrierPhone1;
    private EditText editTextCarrierPhone2;
    private EditText TrNomExp;
    private EditText TrPrenomExp;

    private JRSpinner TrTypPieceIdExpSpinner;
    private EditText TrPieceIdExp;
    private EditText TrAdresseExp;
    private EditText TrCodeSecret;
    private EditText TrDetailsExp;
    private String TrTypPieceIdExp;
    private EditText TrNomDest;
    private EditText TrPrenomDest;

    private JRSpinner TrTypPieceIdDestSpinner;
    private EditText TrPieceIdDest;
    private EditText TrAdresseDest;
    private EditText TrDetailsDest;
    private String TrTypPieceIdDest;
    private String TrTypTransf;
    private TextView tv_guichet_exp;
    private LinearLayout ll_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_operation_transfert_envoyer);
        acetStatus = findViewById(R.id.acet_status);
//        setPopupList();
//        //we need to show the list when clicking on the field
//        setListeners();
        defaultFormat.setCurrency(Currency.getInstance("XAF"));
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        tv_TrRefOper = (TextView)findViewById(R.id.tv_TrRefOper);
        rb_TrTypTransf_CC = (RadioButton) findViewById(R.id.rb_TrTypTransf_CC);
        rb_TrTypTransf_CE = (RadioButton) findViewById(R.id.rb_TrTypTransf_CE);
        rb_TrTypTransf_EC = (RadioButton) findViewById(R.id.rb_TrTypTransf_EC);
        rb_TrTypTransf_EE = (RadioButton) findViewById(R.id.rb_TrTypTransf_EE);
        TrMontant = (EditText) findViewById(R.id.input_txt_TrMontant);
        TrMontant.addTextChangedListener(MyData.onTextChangedListener(TrMontant));
        try {
            onEditTextClicked(TrMontant);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TrMtFrais = (EditText) findViewById(R.id.input_txt_TrMtFrais);
        TrMtTaxes = (EditText) findViewById(R.id.input_txt_TrMtTaxes);
        tv_total = (TextView) findViewById(R.id.tv_total);

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
/*
        ET_OdMontant = (EditText) findViewById(R.id.input_txt_OdMontant);
        ET_OdMontant.addTextChangedListener(MyData.onTextChangedListener(ET_OdMontant));
        ET_OdLibelle = (EditText) findViewById(R.id.input_txt_OdLibelle);
        rb_OdSensOper_Produits = (RadioButton) findViewById(R.id.rb_OdSensOper_Produits);
        rb_OdSensOper_Charges = (RadioButton) findViewById(R.id.rb_OdSensOper_Charges);
        onRadioButtonClicked(rb_OdSensOper_Produits);


        spinnerListOdOperExterne = (Spinner) findViewById(R.id.spn_list_OdOperExterne);



        // spinner item select listener
        spinnerListOdOperExterne.setOnItemSelectedListener(CreateOperationTransfertEnvoyer.this);*/
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
        /*status.add("Status 1");
        status.add("Status 2");
        status.add("Status 3");
        status.add("Status 4");*/

        statusPopupList = new ListPopupWindow(CreateOperationTransfertEnvoyer.this);
        ListAdapter adapter = new SimpleAdapter(
                CreateOperationTransfertEnvoyer.this, movieList,
                R.layout.list_item, new String[]{"KEY_ED_NUMERO",
                "KEY_ED_LIBELLE"},
                new int[]{R.id.movieId, R.id.movieName});
        // updating listview
//        movieListView.setAdapter(adapter);
//        ArrayAdapter adapter = new ArrayAdapter<>(CreateOperationTransfertEnvoyer.this, R.layout.item_simple_status, R.id.tv_element, status);
    /*    ArrayAdapter adapter = new ArrayAdapter<>(CreateOperationTransfertEnvoyer.this, R.layout.item_simple_status,
                new int[]{R.id.movieId, R.id.movieName},status);
        new ArrayAdapter<>();*/
        statusPopupList.setAnchorView(acetStatus); //this let as set the popup below the EditText
        statusPopupList.setAdapter(adapter);
        statusPopupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                acetStatus.setText(AdherentExpediteursListNom.get(position).getName());//we set the selected element in the EditText

//                acetStatus.setText(status.get(position));//we set the selected element in the EditText
                String movieId = ((TextView) view.findViewById(R.id.movieName))
                        .getText().toString();
                acetStatus.setText(movieId);//we set the selected element in the EditText
                statusPopupList.dismiss();
            }
        });
    }
//    public void loadOperationExterneList(){
//        new GetAdherentListExp().execute();
//    }

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

                            // you can call or do what you want with your EditText here
//                SoldeReelEditText.addTextChangedListener(MyData.onTextChangedListener(SoldeReelEditText));
//                tv_montant_ecart.setText(defaultFormat.format((parseDouble(total_operation_bis) - parseDouble(SoldeReelEditText.getText().toString()))));
                            if ((TrMontant.getText().toString().trim()).equals("")){


                                tv_total.setText(defaultFormat.format(parseDouble("0")));
//                                TrMtFrais.setText(defaultFormat.format(parseDouble("0")));
//                                et_nombre_10000.setText("0");

                            }else {
                               String trMontant = TrMontant.getText().toString().replaceAll(",", "").trim();
                                TrMtFrais.setText((parseDouble(trMontant)*0.03)+"");
                                tv_total.setText(defaultFormat.format(parseDouble(trMontant+"")+parseDouble(TrMtFrais.getText().toString().trim()+"")));
//                                solde10000 = parseDouble(et_nombre_10000.getText().toString().trim())*10000;
                            }
                            // yourEditText...


                        }

                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    });
                    break;


            }


//        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), TotalBilletge+"", Toast.LENGTH_SHORT).show();
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
                }
                break;
        }
//        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    /**
     * Adding spinner data
     * */
    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();
        List<String> lablesDest = new ArrayList<String>();

        //tvCaisse.setText("");

        try {
            for (int i = 0; i < AdherentExpediteursListNom.size(); i++) {
                lables.add(AdherentExpediteursListNom.get(i).getName()+ " " +AdherentExpediteursListNom.get(i).getTaux());//recupère les noms
                AdherentExpediteursListID.add(AdherentExpediteursListNom.get(i).getId()); //recupère les Id AdNumero
                AdherentExpediteursListCvNumero.add(AdherentExpediteursListNom.get(i).getFk()); //recupère les Id des compte eav
             lablesDest.add(AdherentExpediteursListNomDest.get(i).getName()+ " " +AdherentExpediteursListNomDest.get(i).getTaux());//recupère les noms
                AdherentExpediteursListIDDest.add(AdherentExpediteursListNomDest.get(i).getId()); //recupère les Id AdNumero
                AdherentExpediteursListCvNumeroDest.add(AdherentExpediteursListNomDest.get(i).getFk()); //recupère les Id des compte eav
            }
            setPopupList();
            //we need to show the list when clicking on the field
            setListeners();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(CreateOperationTransfertEnvoyer.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> spinnerAdapterDest = new ArrayAdapter<String>(CreateOperationTransfertEnvoyer.this,
                android.R.layout.simple_spinner_item, lablesDest);

        // Drop down layout style - list view with radio button
        spinnerAdapterDest
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerListNomExp.setAdapter(spinnerAdapter);
        spinnerListNomDest.setAdapter(spinnerAdapterDest);
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

            Log.e("Response: ", "> " + json);
            Log.e("httpParams: ", "> " + httpParams);
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
            populateSpinner();
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
    /**
     * Checks whether all files are filled. If so then calls AddOperationEnvoyerTransfertAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addOperationExterneDetails() {
        try {
            if (!STRING_EMPTY.equals(TrMontant.getText().toString().trim()) &&
                    !STRING_EMPTY.equals(TrNomDest.getText().toString().trim())
//                    &&                    operationExterneDetails.getOdOperExterne() !=0
            ) {

                Double montantOperation;

                if (!(TrMontant.getText().toString().replaceAll(",", "").trim()).equals(STRING_EMPTY)) {
                    montantOperation = Double.valueOf(TrMontant.getText().toString().replaceAll(",", "").trim());
                    TrMontant.setText(montantOperation + "");
                }
                transfert.setTrRefOper(tv_TrRefOper.getText().toString().trim());
                transfert.setTrTypTransf(TrTypTransf);
//                transfert.setTrCptEAVExp(TrCptEAVExp.getText().toString().trim());
//                transfert.setTrCptEAVExp(TrCptEAVExp.getText().toString().trim());
                transfert.setTrNomExp(TrNomExp.getText().toString().trim());
                transfert.setTrPrenomExp(TrPrenomExp.getText().toString().trim());
                transfert.setTrTelExp(ccp_phone1.getFullNumberWithPlus());
                transfert.setTrPieceIdExp(TrPieceIdExp.getText().toString().trim());
                transfert.setTrTypPieceIdExp(TrTypPieceIdExp);
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
                transfert.setTrAdresseDest(TrAdresseDest.getText().toString().trim());
                transfert.setTrDetailsDest(TrDetailsDest.getText().toString().trim());
                transfert.setTrMontant(TrMontant.getText().toString().trim());
                transfert.setTrMtFrais(TrMtFrais.getText().toString().trim());
                transfert.setTrMtTaxes(TrMtTaxes.getText().toString().trim());
                transfert.setTrIsPaye("N");
//                transfert.setTrCodeSecret(TrCodeSecret.getText().toString().trim());
//                transfert.setTrToken(TrToken.getText().toString().trim());

//                operationExterneDetails.setOdLibelle(MyData.normalizeSymbolsAndAccents( ET_OdLibelle.getText().toString().trim()));


                new AddOperationEnvoyerTransfertAsyncTask().execute();


            } else {
                if (STRING_EMPTY.equals(TrMontant.getText().toString())){
                    TrMontant.setError("Remplissez le montant SVP! ");
                    TrMontant.requestFocus();
                }else if (STRING_EMPTY.equals(TrNomDest.getText().toString())){
                    TrNomDest.setError("Remplissez le nom de l'expéditeur  SVP! ");
                    TrNomDest.requestFocus();
               }
               /*else if (operationExterneDetails.getOdOperExterne() ==0){
                    notificationChampsVides();
                }*/

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
//            httpParams.put(Transfert.KEY_TrToken , String.valueOf(transfert.getTrToken()));
           /* httpParams.put(OperationExterneDetails.KEY_OdLibelle , String.valueOf(operationExterneDetails.getOdLibelle()));
            httpParams.put(OperationExterneDetails.KEY_OdMontant , String.valueOf(operationExterneDetails.getOdMontant()));
            httpParams.put(OperationExterneDetails.KEY_OdSensOper , String.valueOf(operationExterneDetails.getOdSensOper()));
            httpParams.put(OperationExterneDetails.KEY_OdGuichet , String.valueOf(MyData.GUICHET_ID));
            httpParams.put(OperationExterneDetails.KEY_OdUserCree , String.valueOf(MyData.USER_ID));
*/
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
//                        Toast.makeText(CreateOperationExterneDetails.this,
//                                "Compte créé avec succès", Toast.LENGTH_LONG).show();
                        notificationSuccessAdd();


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