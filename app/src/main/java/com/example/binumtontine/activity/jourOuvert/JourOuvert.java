
package com.example.binumtontine.activity.jourOuvert;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;

import com.example.binumtontine.activity.adherent.MainActivityUsager;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class JourOuvert extends AppCompatActivity implements  View.OnClickListener,SERVER_ADDRESS {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";

    private static final String KEY_JO_MT_DEMARRAGE = "JoMtDemarr";
    private static final String KEY_JO_MT_PIECE_MONNAIE = "JoMtPMonnaie";
    private static final String KEY_JO_IS_CLOSED = "JoIsClosed";
    private static final String KEY_JO_GUICHET = "JoGuichet";
    private static final String KEY_CG_LAST_SOLDE = "CgLastSolde";
    private static final String KEY_CG_GUICHET = "CgGuichet";

    private static final String KEY_CM_ID = "CgNumero";
    private static final String KEY_CM_NUMERO = "CmCxGuichet";
    private static final String KEY_CM_SENS = "CmSens";
    private static final String KEY_CM_MONTANT = "CmMontant";
    private static final String KEY_CM_NEW_SOLDE = "CmNewSolde";
    private static final String KEY_CM_NATURE = "CmNature";
    private static final String KEY_CM_USER = "CmUser";


    private EditText JoMtDemarrEditText;
    private EditText JoMtPMonnaieEditText;
    private TextView CgLastSoldeTextView;



    private RadioButton rbStartNewDay;
    private RadioButton rbStartOldDay;




    private String JoMtDemarr;
    private String CmSens="R";
    private String CmNature="O";
    private double JoMtDemarrDouble;
    private String JoMtPMonnaie;
    private String CgLastSolde;
    private String CgNumero;
    private String CgDevise ="A";
    private Boolean JoIsClosed;
    private TextInputLayout til_journee_anterieure;
    private TextInputLayout til_montant_piece_monnaie_usager;
    private TextInputLayout til_montant_demarrage_usager;

    private DatePickerDialog Ad_JourneeAnterieure_PickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date
    private SimpleDateFormat dateFormatter; //propriété permettant de gérer le format de la date



    private static String STRING_EMPTY = "";
    private NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());


    private static final String TAG = JourOuvert.class.getSimpleName();


    private Button addButton;
    private Button cancelButton;
    private int success;
    private int successCxGxMvt;
    private ProgressDialog pDialogFetchLastSolde;
    private ProgressDialog pDialog;
    private Double valueNew;
    private Double valueLAst;
    private EditText Ad_JourneeAnterieureEditText;
    private TextView ConnexionGuichetTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jour_ouvert);
        defaultFormat.setCurrency(Currency.getInstance("FCF"));
        JoMtDemarrEditText = (EditText) findViewById(R.id.input_txt_montant_demarrage_usager);
        JoMtDemarrEditText.addTextChangedListener(MyData.onTextChangedListener(JoMtDemarrEditText));

        til_montant_demarrage_usager = (TextInputLayout) findViewById(R.id.input_layout_montant_demarrage_usager);
        //JoMtDemarrEditText.addTextChangedListener(new MyTextWatcher(JoMtDemarrEditText));

        JoMtPMonnaieEditText = (EditText) findViewById(R.id.input_txt_montant_piece_monnaie_usager);
        JoMtPMonnaieEditText.addTextChangedListener(MyData.onTextChangedListener(JoMtPMonnaieEditText));

        CgLastSoldeTextView = (TextView) findViewById(R.id.tv_montant_coffre_usager);
        ConnexionGuichetTextView = (TextView) findViewById(R.id.tv_header_connexion_au_guichet);
        ConnexionGuichetTextView.setText("Connexion au guichet"+"\n"+MyData.GUICHET_NAME);
        til_journee_anterieure = (TextInputLayout) findViewById(R.id.input_layout_date_anterieure);

        til_montant_piece_monnaie_usager = (TextInputLayout) findViewById(R.id.input_layout_montant_piece_monnaie_usager);

        rbStartNewDay = (RadioButton) findViewById(R.id.rb_que_voulez_vous_faire_NewDay_usager);
        rbStartOldDay = (RadioButton) findViewById(R.id.rb_que_voulez_vous_faire_OldDay_usager);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();

        setDateTimeField();
//// Add an OnEditorActionListener to the EditText view.
//         JoMtDemarrEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    // Close the keyboard.
//                    InputMethodManager imm = (InputMethodManager)
//                            v.getContext().getSystemService
//                                    (Context.INPUT_METHOD_SERVICE);
//
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                    // TODO: Parse string in view v to a number.
//                    try {
//                        // Use the number format for the locale.
//                        JoMtDemarrDouble = defaultFormat.parse(v.getText()
//                                .toString()).doubleValue();
//                        v.setError(null);
//                    } catch (ParseException e) {
//                        Log.e(TAG,Log.getStackTraceString(e));
//                        v.setError(getText(R.string.enter_number));
//                        return false;
//                    }
//                    // Convert to string using locale's number format.
//                    JoMtDemarr = defaultFormat.format(JoMtDemarrDouble);
//                // Show the locale-formatted quantity.
//                    v.setText(JoMtDemarr);
//
//                    return true;
//                }
//                return false;
//            }
//        });
        /* don't allow user to move cursor while entering price */
       // JoMtDemarrEditText.setMovementMethod(null);
//        JoMtDemarrEditText.addTextChangedListener(new TextWatcher() {
//            private String current = "";
//            NumberFormat formatter = NumberFormat.getCurrencyInstance();
//            private double parsed;
//
//             @Override
//             public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                                                      }
//
//              @Override
//              public void onTextChanged(CharSequence s, int start, int before, int count) {
//                                                          if (!s.toString().equals(current)) {
//                                                              // remove listener to prevent stack overflow from infinite loop //
//                                                              JoMtDemarrEditText.removeTextChangedListener(this);
//                                                              String cleanString = s.toString().replaceAll("[$,]", "");
//
//                                                              try {
//                                                                  parsed = Double.parseDouble(cleanString);
//                                                              } catch (java.lang.NumberFormatException e) {
//                                                                  parsed = 0;
//                                                              }
//
//                                                              formatter.setMaximumFractionDigits(0);
//                                                              String formatted = formatter.format(parsed);
//
//                                                              current = formatted;
//                                                              JoMtDemarrEditText.setText(formatted);
//                                                              JoMtDemarrEditText.setSelection(formatted.length()); //for test
//
//                                                              // add listener back //
//                                                              JoMtDemarrEditText.addTextChangedListener(this);
//                                                              // print a toast when limit is reached... see xml below.
//                                                               // this is for 6 chars //
//                                                              //if (start == 7) {
//                                                                  Toast toast = Toast.makeText(getApplicationContext(),
//                                                                          "Maximum Limit Reached", Toast.LENGTH_LONG);
//                                                                  toast.setGravity(Gravity.CENTER, 0, 0);
//                                                                  toast.show();
//                                                              }
//                                                          }
//                                                      }
//
//                                                      @Override
//                                                      public void afterTextChanged(Editable s) {
//
//                                                      }
//                                                  });


        new JourOuvert.FetchLastSoldeGuichetDetailsAsyncTask().execute();

     //   new JourOuvert.GetCategories().execute();

        cancelButton = (Button) findViewById(R.id.btn_clean);
        addButton = (Button) findViewById(R.id.btn_save_montant_demarrage);
        //cirLoginButton
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    finish();
                } else {
                    Toast.makeText(JourOuvert.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        }); addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                   if (JoIsClosed){

                       Toast.makeText(JourOuvert.this,
                               "Fonctionnalité en cours de traitement",
                               Toast.LENGTH_LONG).show();
                   }else{
                       addJourOuvert();
                   }

                } else {
                    Toast.makeText(JourOuvert.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

        onRadioButtonClicked(rbStartNewDay);
        onRadioButtonClicked(rbStartOldDay);
    }


    private void findViewsById() {
        Ad_JourneeAnterieureEditText = (EditText) findViewById(R.id.input_txt_date_anterieure);
        Ad_JourneeAnterieureEditText.requestFocus();
        Ad_JourneeAnterieureEditText.setInputType(InputType.TYPE_NULL);

    }


    private void setDateTimeField() {
        Ad_JourneeAnterieureEditText.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();
        Ad_JourneeAnterieure_PickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                Ad_JourneeAnterieureEditText.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));





    }

    @Override
    public void onClick(View v) {
        if(v == Ad_JourneeAnterieureEditText) {

            Ad_JourneeAnterieure_PickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            Ad_JourneeAnterieure_PickerDialog.show();
        }
//        else if (v == Ad_DateDelivranceEditText){
//            Ad_DateDelivrance_PickerDialog.show();
//        }else if (v == Ad_DateExpirationEditText){
//            Ad_DateExpiration_PickerDialog.show();
//        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_txt_montant_demarrage_usager:
                    //validateLogin();
                    break;
                //case R.id.input_email:
//                case R.id.txtPassword:
//                    validatePassword();
//                    break;

            }
        }
    }

    private boolean validateLogin() {
        if (JoMtDemarrEditText.getText().toString().trim().isEmpty()) {
            til_montant_demarrage_usager.setError(getString(R.string.err_msg_montant_demarrage));
            requestFocus(JoMtDemarrEditText);
           // testError=false;
            return false;
        } else {
           // valueNew = Double.valueOf(JoMtDemarrEditText.getText().toString().trim());
//            JoMtDemarrEditText.setText(JoMtDemarrEditText.getText().toString().replaceAll(",", "").trim());
            valueNew = Double.valueOf(JoMtDemarrEditText.getText().toString().replaceAll(",", "").trim());
            JoMtDemarrEditText.setText(valueNew+"");
            //JoMtDemarrEditText.getText().toString().replaceAll(",", "");
            valueLAst = Double.valueOf(CgLastSolde);

            til_montant_demarrage_usager.setErrorEnabled(false);
           // testError=true;
        }

        return true;
    }

    /**
     * Validating form
     */

    private void submitForm() {
        if (!validateLogin()) {
            return;
        }

//        if (!validatePassword()) {
//            return;
//        }

    }
    /**
     * Fetches single caisse_guichet details from the server
     */
    private class FetchLastSoldeGuichetDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialogFetchLastSolde = new ProgressDialog(JourOuvert.this);
            pDialogFetchLastSolde.setMessage("Loading guichet Details. Please wait...");
            pDialogFetchLastSolde.setIndeterminate(false);
            pDialogFetchLastSolde.setCancelable(false);
            pDialogFetchLastSolde.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_CG_GUICHET, String.valueOf(MyData.GUICHET_ID));
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_caisse_guichet_details.php", "GET", httpParams);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject user;
                if (success == 1) {
                    //Parse the JSON response
                    user = jsonObject.getJSONObject(KEY_DATA);
                    CgLastSolde = user.getString(KEY_CG_LAST_SOLDE);
                    CgNumero = user.getString(KEY_CM_ID);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialogFetchLastSolde.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {

                    //Populate the Edit Texts once the network activity is finished executing
                    //defaultFormat.setCurrency(Currency.getInstance("FCF"));
                    CgLastSoldeTextView.setText(defaultFormat.format(parseDouble(CgLastSolde)));
                    //NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
                    CgDevise.toUpperCase();
                    CgLastSoldeTextView.setTypeface(null, Typeface.BOLD);
                    CgLastSoldeTextView.setText(CgLastSoldeTextView.getText()+ CgDevise);



                }
            });
        }


    }




    public void onRadioButtonClicked(View view) {
        boolean checked1 = ((RadioButton) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.rb_que_voulez_vous_faire_NewDay_usager:
                if (rbStartNewDay.isChecked()) {
                    JoIsClosed = false;
                    til_journee_anterieure.setVisibility(View.GONE);
                    til_montant_demarrage_usager.setVisibility(View.VISIBLE);
                    til_montant_piece_monnaie_usager.setVisibility(View.VISIBLE);
                    str = checked1?"Précisez le montant de démarrage":"";
                    //ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    //FpNature ="F";

                }

                break;
            case R.id.rb_que_voulez_vous_faire_OldDay_usager:
                if (rbStartOldDay.isChecked()){
                    JoIsClosed = true;
                    til_journee_anterieure.setVisibility(View.VISIBLE);
                    til_montant_demarrage_usager.setVisibility(View.GONE);
                    til_montant_piece_monnaie_usager.setVisibility(View.GONE);

                    str = checked1?"Choisissez une date antérieure":"";
                    //ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    //FpNature ="T";

        }


                break;

        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }



    /**
     * Checks whether all files are filled. If so then calls AddJourOuvertAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addJourOuvert() {
       /* if (!STRING_EMPTY.equals(ev_codeEditText.getText().toString()) &&

               */
        // now let's use Double.valueOf() method to get double from String
        //submitForm();
       // validateLogin();
        //String str = JoMtDemarrEditText.getText().toString();
       // if (!STRING_EMPTY.equals(JoMtDemarrEditText.getText().toString())){
//        double valueNew = Double.valueOf(str);
//        double valueLAst = Double.valueOf(CgLastSolde);
        //System.out.println("String to double conversion using valueOf : " + valueNew);
//if (!STRING_EMPTY.equals(JoMtDemarrEditText.getText().toString())){
if (validateLogin() && valueNew<=valueLAst){
            JoMtDemarr = JoMtDemarrEditText.getText().toString();
            JoMtPMonnaie = JoMtPMonnaieEditText.getText().toString();
    // Parse string in view v to a number.


                if (rbStartNewDay.isChecked()){
                    JoIsClosed =false;
                }else JoIsClosed =true;






            new AddJourOuvertAsyncTask().execute();
    } else {
            Toast.makeText(JourOuvert.this,
                    "Entrez un montant inférieur ou égal à celui du coffre fort!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     * AsyncTask for adding a Jour ouvert
     */
    private class AddJourOuvertAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(JourOuvert.this);
            if(!JoIsClosed)
            pDialog.setMessage("Démarrage d'une nouvelle journée . Please wait...");
            else pDialog.setMessage("Connexion à une journée antérieure . Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            Map<String, String> httpParamsCaisseMvt = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_JO_MT_DEMARRAGE, JoMtDemarr);
            httpParams.put(KEY_JO_MT_PIECE_MONNAIE, JoMtPMonnaie);
            httpParams.put(KEY_JO_IS_CLOSED, JoIsClosed.toString());
            httpParams.put(KEY_JO_GUICHET, String.valueOf(MyData.GUICHET_ID));

            httpParamsCaisseMvt.put(KEY_CM_NUMERO, CgNumero);
            httpParamsCaisseMvt.put(KEY_CM_SENS, CmSens);
            httpParamsCaisseMvt.put(KEY_CM_MONTANT, JoMtDemarr);
           // httpParamsCaisseMvt.put(KEY_CM_NEW_SOLDE,(parseDouble(CgLastSolde)-parseDouble(JoMtDemarr))+"");
            httpParamsCaisseMvt.put(KEY_CM_NEW_SOLDE,(valueLAst-valueNew+""));
            httpParamsCaisseMvt.put(KEY_CM_NATURE, CmNature);
            httpParamsCaisseMvt.put(KEY_CM_USER, String.valueOf(MyData.USER_ID));




            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_jour_ouvert.php", "POST", httpParams);
            JSONObject jsonObjectCxMvt = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_caisse_guichet_mouvement.php", "POST", httpParamsCaisseMvt);
            try {
                success = jsonObject.getInt(KEY_SUCCESS);
                successCxGxMvt = jsonObjectCxMvt.getInt(KEY_SUCCESS);
                Log.e("*********","success "+success+" "+"successCxGxMvt "+successCxGxMvt+" New solde "+valueNew+ " JoMt "+JoMtDemarr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
//                    if (success == 1 && successCxGxMvt == 1) { // à revoir pourquoi success renvoie 0 au lieu de 1
                    if (successCxGxMvt == 1) {
                        //Display success message
                        Toast.makeText(JourOuvert.this,
                                "Journée ouverte", Toast.LENGTH_LONG).show();
                      /*  Intent i = getIntent();*/
                        //send result code 20 to notify about movie update
                        /* setResult(20, i); */
                        //Finish ths activity and go back to listing activity

                        //Intent i = new Intent(this, CreateAdherent.class);
                        Intent i = new Intent(JourOuvert.this, MainActivityUsager.class);
                        startActivity(i);
                        finish();

                    } else {
                        Toast.makeText(JourOuvert.this,
                                "Some error occurred while open day",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }


//    private TextWatcher onTextChangedListener( final EditText et_filed) {
//
//        return new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                et_filed.removeTextChangedListener(this);
//
//                try {
//                    String originalString = s.toString();
//
//                    Long longval;
//                    if (originalString.contains(",")) {
//                        originalString = originalString.replaceAll(",", "");
//                    }
//                    longval = Long.parseLong(originalString);
//
//                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
//                    formatter.applyPattern("#,###,###,###");
//                    String formattedString = formatter.format(longval);
//
//                    //setting text after format to EditText
//                    et_filed.setText(formattedString);
//                    et_filed.setSelection(et_filed.getText().length());
//                } catch (NumberFormatException nfe) {
//                    nfe.printStackTrace();
//                }
//
//                et_filed.addTextChangedListener(this);
//            }
//        };
//    }
}