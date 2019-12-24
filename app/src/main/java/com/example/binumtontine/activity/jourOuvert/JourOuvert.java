
package com.example.binumtontine.activity.jourOuvert;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class JourOuvert extends AppCompatActivity implements SERVER_ADDRESS {
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





    private static String STRING_EMPTY = "";
    private NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());


    private static final String TAG = JourOuvert.class.getSimpleName();


    private Button addButton;
    private Button cancelButton;
    private int success;
    private int successCxGxMvt;
    private ProgressDialog pDialogFetchLastSolde;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jour_ouvert);
        defaultFormat.setCurrency(Currency.getInstance("FCF"));
        JoMtDemarrEditText = (EditText) findViewById(R.id.input_txt_montant_demarrage_usager);
        JoMtPMonnaieEditText = (EditText) findViewById(R.id.input_txt_montant_piece_monnaie_usager);
        CgLastSoldeTextView = (TextView) findViewById(R.id.tv_montant_coffre_usager);

        rbStartNewDay = (RadioButton) findViewById(R.id.rb_que_voulez_vous_faire_NewDay_usager);
        rbStartOldDay = (RadioButton) findViewById(R.id.rb_que_voulez_vous_faire_OldDay_usager);
// Add an OnEditorActionListener to the EditText view.
        /* JoMtDemarrEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Close the keyboard.
                    InputMethodManager imm = (InputMethodManager)
                            v.getContext().getSystemService
                                    (Context.INPUT_METHOD_SERVICE);

                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    // TODO: Parse string in view v to a number.
                    try {
                        // Use the number format for the locale.
                        JoMtDemarrDouble = defaultFormat.parse(v.getText()
                                .toString()).doubleValue();
                        v.setError(null);
                    } catch (ParseException e) {
                        Log.e(TAG,Log.getStackTraceString(e));
                        v.setError(getText(R.string.enter_number));
                        return false;
                    }
                    // Convert to string using locale's number format.
                    JoMtDemarr = defaultFormat.format(JoMtDemarrDouble);
                // Show the locale-formatted quantity.
                    v.setText(JoMtDemarr);

                    return true;
                }
                return false;
            }
        });*/
        /* don't allow user to move cursor while entering price */
       // JoMtDemarrEditText.setMovementMethod(null);
       /* JoMtDemarrEditText.addTextChangedListener(new TextWatcher() {
            private String current = "";
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            private double parsed;

             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                      }

              @Override
              public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                          if (!s.toString().equals(current)) {
                                                              // remove listener to prevent stack overflow from infinite loop //
                                                              JoMtDemarrEditText.removeTextChangedListener(this);
                                                              String cleanString = s.toString().replaceAll("[$,]", "");

                                                              try {
                                                                  parsed = Double.parseDouble(cleanString);
                                                              } catch (java.lang.NumberFormatException e) {
                                                                  parsed = 0;
                                                              }

                                                              formatter.setMaximumFractionDigits(0);
                                                              String formatted = formatter.format(parsed);

                                                              current = formatted;
                                                              JoMtDemarrEditText.setText(formatted);
                                                              JoMtDemarrEditText.setSelection(formatted.length()); //for test

                                                              // add listener back //
                                                              JoMtDemarrEditText.addTextChangedListener(this);
                                                              // print a toast when limit is reached... see xml below.
                                                               // this is for 6 chars //
                                                              //if (start == 7) {
                                                                  Toast toast = Toast.makeText(getApplicationContext(),
                                                                          "Maximum Limit Reached", Toast.LENGTH_LONG);
                                                                  toast.setGravity(Gravity.CENTER, 0, 0);
                                                                  toast.show();
                                                              }
                                                          }
                                                      }

                                                      @Override
                                                      public void afterTextChanged(Editable s) {

                                                      }
                                                  }); */
//...

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
                    addJourOuvert();
                } else {
                    Toast.makeText(JourOuvert.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });


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
                   // textInputLayoutCaisse.setVisibility(View.GONE);
                    str = checked1?"Précisez le montant de démarrage":"";
                    //ev_typ_fr_agiosEditText = (RadioButton) findViewById(R.id.rbEpTypTxInterFixe);
                    //FpNature ="F";

                }

                break;
            case R.id.rb_que_voulez_vous_faire_OldDay_usager:
                if (rbStartOldDay.isChecked()){
                    JoIsClosed = true;
                    //textInputLayoutCaisse.setVisibility(View.VISIBLE);
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

                !STRING_EMPTY.equals(ev_libelleEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ev_tx_inter_anEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ev_is_tx_inter_an_obligSwitch.getText().toString()) &&
                !STRING_EMPTY.equals(ev_typ_dat_valEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ev_is_multi_eav_onSwitch.getText().toString()) &&
                !STRING_EMPTY.equals(ev_is_paie_ps_onSwitch.getText().toString()) &&
                !STRING_EMPTY.equals(ev_is_agios_onSwitch.getText().toString()) &&
                !STRING_EMPTY.equals(ev_typ_fr_agiosEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ev_mt_tx_agios_prelevEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ev_plage_agios_fromEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ev_plage_agios_toEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ev_is_cheque_onSwitch.getText().toString()) &&
                !STRING_EMPTY.equals(ev_frais_clot_cptEditText.getText().toString()) &&

                !STRING_EMPTY.equals(ev_min_cpteEditText.getText().toString()) &&
                !STRING_EMPTY.equals(ev_is_min_cpte_obligSwitch.getText().toString())) { */
if (!STRING_EMPTY.equals(JoMtDemarrEditText.getText().toString())){
            JoMtDemarr = JoMtDemarrEditText.getText().toString();
            JoMtPMonnaie = JoMtPMonnaieEditText.getText().toString();
    // Parse string in view v to a number.


                if (rbStartNewDay.isChecked()){
                    JoIsClosed =false;
                }else JoIsClosed =true;






            new AddJourOuvertAsyncTask().execute();
    } else {
            Toast.makeText(JourOuvert.this,
                    "Un ou plusieurs champs sont vides!",
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
            httpParamsCaisseMvt.put(KEY_CM_NEW_SOLDE,(parseDouble(CgLastSolde)-parseDouble(JoMtDemarr))+"");
            httpParamsCaisseMvt.put(KEY_CM_NATURE, CmNature);
            httpParamsCaisseMvt.put(KEY_CM_USER, String.valueOf(MyData.USER_ID));




            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_jour_ouvert.php", "POST", httpParams);
            JSONObject jsonObjectCxMvt = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_caisse_guichet_mouvement.php", "POST", httpParamsCaisseMvt);
            try {
                success = jsonObject.getInt(KEY_SUCCESS);
                successCxGxMvt = jsonObjectCxMvt.getInt(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1 && successCxGxMvt == 1) {
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
}