package com.example.binumtontine.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.adherent.MainActivityUsager;
import com.example.binumtontine.activity.jourOuvert.JourOuvert;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;


public class LoginActivity_NEW extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, SERVER_ADDRESS {

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";

    public static final String KEY_JO_GUICHET = "JoGuichet";
    public static final String KEY_UX_PROFIL = "ux_profil";
    public static final String KEY_UX_LOGIN = "ux_login";
    public static final String KEY_UX_PASSWORD = "ux_password";
    public static final String KEY_UX_GUICHET = "gx_numero";
    public static final String KEY_UX_CAISSE = "cx_numero";
    public static final String KEY_UX_CAISSE_DENOMINATION = "cx_denomination";
    public static final String KEY_UX_GUICHET_DENOMINATION = "gx_denomination";
    public static final String KEY_UX_NUMERO = "ux_numero";
    public static final String KEY_UX_NOM = "ux_nom";
    public static final String KEY_UX_PRENOM = "ux_prenom";
    public static final String KEY_UX_EMAIL = "ux_email";
    public static final String KEY_EMPTY = "";

    private Spinner spinnerProfil;
    private Spinner spinnerCaisse;
    private Spinner spinnerGuichet;
    private Button connect;
    private EditText pseudoEditText;
    private EditText passwordEditText;
    private TextView tv_caisse;
    private TextView tv_profil;
    private TextView tv_guichet;
    private TextView warningInfo;
    private String pseudo;
    private String password;
    private int attemptConnection=3;
    private int userId;
    private String userNom;
    private String userPrenom;
    private String userEmail;
    private String userProfil;
    private String userCxNumero;
    private String userGxNumero;
    private String userCxDenomination;
    private String userGxDenomination;

    private Boolean userFound=false;
    CircularProgressButton btn;
    private  String jourIsClosed="FALSE";
    //private  String maDate;

    Date todayDateInit = Calendar.getInstance().getTime();
    //SimpleDateFormat formatterInint = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatterInint = new SimpleDateFormat("dd-MM-yyyy");
    private  String maDate = formatterInint.format(todayDateInit);
    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Category> caisseList;
    private ArrayList<Category> guichetList;
    private ArrayList<Category> jourOuvertList;
    List<Integer> caisseListID = new ArrayList<Integer>();
    List<Integer> guichetListID = new ArrayList<Integer>();
    private String uxProfil;

    private TextInputLayout textInputLayoutCaisse;
    private ProgressDialog pDialogFectchCaisse;
    private ProgressDialog pDialogFectchGuichet;
    private ProgressDialog pDialogFectchUserDetails;
    /*end manage*/
    private TextInputLayout inputLayoutLogin, inputLayoutPassword;
    private  boolean testError=false; //to check if errors don't exist

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);

//        spinnerProfil = findViewById(R.id.spinner1);
//        spinnerProfil.setVisibility(View.GONE);
//        tv_profil =(TextView) findViewById(R.id.lblAdminOF);
//        tv_profil.setVisibility(View.GONE);
//        /* Block Caisse start*/
//        tv_caisse =(TextView) findViewById(R.id.lblCaisse);
//        spinnerCaisse = findViewById(R.id.spinnerCaisse);
//        tv_caisse.setVisibility(View.GONE);
//        spinnerCaisse.setVisibility(View.GONE);
//
//        /*Block Caisse End*/
//
//        /* Block Guichey start*/
        tv_guichet =(TextView) findViewById(R.id.lblGuichet);
        tv_guichet.setVisibility(View.GONE);
        spinnerGuichet = findViewById(R.id.spinnerGuichet);
        spinnerGuichet.setVisibility(View.GONE);

//        /*Block Guichet End*/
//
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//                this, R.array.type_admin,android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerProfil.setAdapter(adapter);

//        caisseList = new ArrayList<Category>();
        guichetList = new ArrayList<Category>();
        jourOuvertList = new ArrayList<Category>();
        // spinner item select listener
        //new LoginActivity.GetCaisses().execute();



        //recupération des attributs de la page d'authentification
        pseudoEditText = (EditText)findViewById(R.id.txtPseudo);

        inputLayoutLogin = (TextInputLayout) findViewById(R.id.textInputEmail);
        pseudoEditText.addTextChangedListener(new MyTextWatcher(pseudoEditText));

        passwordEditText = (EditText)findViewById(R.id.txtPassword);

        inputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputPassword);
        passwordEditText.addTextChangedListener(new MyTextWatcher(passwordEditText));

        warningInfo= (TextView)findViewById(R.id.lblWarningInfo);
        warningInfo.setVisibility(View.GONE);
        warningInfo.setText("");
        connect = (Button) findViewById(R.id.btnConnect);
        connect.setOnClickListener(this);


        btn = (CircularProgressButton) findViewById(R.id.btn_id);
        btn.setVisibility(View.GONE);
        btn.setOnClickListener(this);


/*
        ImageView imagepass = (ImageView) findViewById(R.id.imagepassword);
        final EditText  editpass = (EditText) findViewById(R.id.new_pawd);
        imagepass .setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        editpass.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        editpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });*/
    }



    @Override
    public void onClick(View v) {
        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
            if (!KEY_EMPTY.equals(pseudoEditText.getText().toString()) &&
                    !KEY_EMPTY.equals(passwordEditText.getText().toString())) {
                pseudo = pseudoEditText.getText().toString();
                password = passwordEditText.getText().toString();
                // new LoginActivity.FetchMovieDetailsAsyncTask().execute(pseudoEditText.getText().toString(),passwordEditText.getText().toString());
                pseudoEditText.setText("");
                passwordEditText.setText("");
                //validate(pseudoEditText.getText().toString(), passwordEditText.getText().toString());
                validate(pseudo, password);

            }else {
                submitForm();
//                Toast.makeText(LoginActivity.this,
//                        "Un ou plusieurs champs sont laissés vides!",
//                        Toast.LENGTH_LONG).show();

            }
        }else{
            connect.setEnabled(false);

            Toast.makeText(LoginActivity_NEW.this,
                    "Impossible de se connecter à Internet",
                    Toast.LENGTH_LONG).show();
        }

    }


//     private void validate (String pseudo, String password){
//        if ((pseudo.equals("admin")) && (password.equals("admin"))
//                && spinnerProfil.getSelectedItem().toString().equals("Administrateur OF")){
//            Intent i = new Intent(this, MainActivity.class);
//
//            startActivity(i);
//        }else if((pseudo.equals("admin")) && (password.equals("admin"))
//                &&(spinnerProfil.getSelectedItem().toString().equals("Administrateur caisse"))){
//            uxProfil = "Administrateur caisse";
//
//            Intent i = new Intent(this, MainActivityAdminCaisse.class);
//            startActivity(i);
//        }else if((pseudo.equals("admin")) && (password.equals("admin"))
//                && (spinnerProfil.getSelectedItem().toString().equals("Usager"))){
//            uxProfil = "Agent de guichet";
//            new LoginActivity.GetIfJourOuvertIsOn().execute();
//            /*Toast.makeText(LoginActivity.this,
//                    "SQL: "+maDate+"  NOW: "+new Date(), Toast.LENGTH_LONG).show();*/
//             // warningInfo.setVisibility(View.VISIBLE);
//            //warningInfo.setText("SQL: "+maDate+"  NOW: "+new Date()+" Isclosed: "+jourIsClosed);
//
//            Date todayDate = Calendar.getInstance().getTime();
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            String todayString = formatter.format(todayDate);
////            warningInfo.setText("SQL: "+(MyData.JOUR_OUVERT_DATE).substring(0,11)+"\nNOW: "+todayString+"\n Isclosed: "+MyData.JOUR_OUVERT_IS_CLOSED);
//            //substr = str.substring(0, 7);
//            //Intent i = new Intent(this, JourOuvert.class);
//
//            //Intent i = new Intent(this, CreateAdherent.class);
//            Intent i = new Intent(this, MainActivityUsager.class);
//           startActivity(i);
//        } else{
//            attemptConnection--;
//            warningInfo.setVisibility(View.VISIBLE);
//            warningInfo.setText("Number of attempts remaining: "+String.valueOf(attemptConnection));
//            if (attemptConnection==0)
//                connect.setEnabled(false);
//
//        }
//    }
private boolean validateLogin() {
    if (pseudoEditText.getText().toString().trim().isEmpty()) {
        inputLayoutLogin.setError(getString(R.string.err_msg_login));
        requestFocus(pseudoEditText);
        testError=false;
        return false;
    } else {
        inputLayoutLogin.setErrorEnabled(false);
        testError=true;
    }

    return true;
}
private boolean validatePassword() {
    if (passwordEditText.getText().toString().trim().isEmpty()) {
        inputLayoutPassword.setError(getString(R.string.err_msg_password));
        requestFocus(passwordEditText);
        testError=false;
        return false;
    } else {
        inputLayoutPassword.setErrorEnabled(false);
        testError=true;
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

        if (!validatePassword()) {
            return;
        }

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
                case R.id.txtPseudo:
                    validateLogin();
                    break;
                //case R.id.input_email:
                case R.id.txtPassword:
                    validatePassword();
                    break;

            }
        }
    }

    public void avertissement() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("AVERTISSEMENT !")
                .setMessage("Avec quel profil souhaitez-vous vous connecter ? ")
                .setNegativeButton("Agent de guichet", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                                tv_guichet.setVisibility(View.VISIBLE);
                        spinnerGuichet.setVisibility(View.VISIBLE);
                        fetchGuichetSpinner();
                        spinnerGuichet.setOnItemSelectedListener(LoginActivity_NEW.this);
//                        finish();
                    }

                })
                .setPositiveButton("Directeur de caisse", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new LoginActivity_NEW.GetUserCaisse().execute();
                    }

                })
                .show();
    }
    private void populateUser(){
        //userFound=true;

        MyData.USER_ID = userId;
        MyData.USER_NOM = userNom;
        MyData.USER_PRENOM = userPrenom;
        MyData.USER_EMAIL = userEmail;
        MyData.USER_PROFIL = userProfil;

        if (userFound){
            warningInfo.setVisibility(View.GONE);
            attemptConnection=3;

            if (userProfil.equals("Administrateur OF")){
                Intent i = new Intent(this, MainActivity.class);

                startActivity(i);
            }else if(userProfil.equals("Administrateur caisse")){

                uxProfil = "Administrateur caisse";
                MyData.CAISSE_ID = Integer.parseInt(userCxNumero);
                MyData.CAISSE_NAME = userCxDenomination;
                avertissement();
//                        new LoginActivity_NEW.GetUserCaisse().execute();


            }else if(userProfil.equals("Agent de guichet")){
                uxProfil = "Agent de guichet";
                MyData.GUICHET_ID = Integer.parseInt(userGxNumero);
                MyData.GUICHET_NAME = userGxDenomination;
                new LoginActivity_NEW.GetUserGuichet().execute();

            }
//            else{
//                attemptConnection--;
//                warningInfo.setVisibility(View.VISIBLE);
//                warningInfo.setText("Number of attempts remaining: "+String.valueOf(attemptConnection));
//                if (attemptConnection<=0)
//                    connect.setEnabled(false);
//
//            }


        }else{
            attemptConnection--;
            warningInfo.setVisibility(View.VISIBLE);
            warningInfo.setText("Number of attempts remaining: "+String.valueOf(attemptConnection));
            if (attemptConnection<=0){
                connect.setEnabled(false);
                //connect.setStyle;
            }

        }
    }
    private void fetchGuichetSpinner(){
        new LoginActivity_NEW.GetGuichets().execute();
    }
    private void populate(){
        //userFound=true;

        MyData.USER_ID = userId;
        MyData.USER_NOM = userNom;
        MyData.USER_PRENOM = userPrenom;
        MyData.USER_EMAIL = userEmail;
        if (userFound){
            warningInfo.setVisibility(View.GONE);
            attemptConnection=3;

            Intent i = new Intent(this, MainActivityAdminCaisse.class);
            startActivity(i);
        }else{
            attemptConnection--;
            warningInfo.setVisibility(View.VISIBLE);
            warningInfo.setText("Number of attempts remaining: "+String.valueOf(attemptConnection));
            if (attemptConnection<=0){
                connect.setEnabled(false);
                //connect.setStyle;
            }

        }
    }
    private void populateParamGuichet(){
        //userFound=true;
        if (userFound){
        //if (true){
            warningInfo.setVisibility(View.GONE);
            attemptConnection=3;


            MyData.USER_ID = userId;
            MyData.USER_NOM = userNom;
            MyData.USER_PRENOM = userPrenom;
            MyData.USER_EMAIL = userEmail;
            try {
                MyData.CAISSE_ID = Integer.parseInt(userCxNumero);//NEW
                MyData.CAISSE_NAME = userCxDenomination;//NEW
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            new LoginActivity_NEW.GetIfJourOuvertIsOn().execute();




//
//
//            Intent i = new Intent(this, JourOuvert.class);
//            startActivity(i);
        }else{
            attemptConnection--;
            warningInfo.setVisibility(View.VISIBLE);
            warningInfo.setText("Number of attempts remaining: "+String.valueOf(attemptConnection));
            if (attemptConnection<=0){
                connect.setEnabled(false);
                //connect.setStyle;
            }

        }
    }
    public void avertissement(String dateJournee) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Journée non clôturée !")
                .setMessage("La journée du: " + dateJournee+" n'a pas été correctement clôturée"+
                        "\n\t\t Voulez-vous clôturer cette journée ?")
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
                        Intent i = new Intent(LoginActivity_NEW.this, ClotureJourneeActivity.class);
                        startActivity(i);
                    }

                })
                .show();
    }
    private void populateJourOuvert(){
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = formatter.format(todayDate);
        if (!Boolean.valueOf(jourIsClosed) && maDate.substring(0,10).equals(todayString)){
             Intent i = new Intent(this, MainActivityUsager.class);
            startActivity(i);

        }else if (!Boolean.valueOf(jourIsClosed) && !(maDate.substring(0,10).equals(todayString))){
            Toast.makeText(LoginActivity_NEW.this,
                    "Que voulez-vous faire ?", Toast.LENGTH_LONG).show();
//            //warningInfo.setText("SQL: "+maDate+"  NOW: "+new Date()+" Isclosed: "+jourIsClosed);
//
//            Intent i = new Intent(this, JourOuvert.class);
//            startActivity(i);
avertissement(maDate.substring(0,10));

        }else{
            Toast.makeText(LoginActivity_NEW.this,
                    "Que voulez-vous faire ?", Toast.LENGTH_LONG).show();
            //warningInfo.setText("SQL: "+maDate+"  NOW: "+new Date()+" Isclosed: "+jourIsClosed);

            Intent i = new Intent(this, JourOuvert.class);
            startActivity(i);


        }
    }
    private void validate (String pseudo, String password){
        new LoginActivity_NEW.GetUser().execute();

    }



    /**
     * Adding spinner data
     * */
    private void populateSpinnerCaisses() {
        List<String> lables = new ArrayList<String>(); //for caisses

        caisseListID.clear();
        for (int i = 0; i < caisseList.size(); i++) {
            lables.add(caisseList.get(i).getName());//recupère les noms de caisses
            caisseListID.add(caisseList.get(i).getId()); //recupère les Id
        }



        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(LoginActivity_NEW.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerAdapter.notifyDataSetChanged();
        // attaching data adapter to spinner caisse
        spinnerCaisse.setAdapter(spinnerAdapter);
    }

    private void populateSpinnerGuichets() {
        List<String> lablesGuichet = new ArrayList<String>(); //for guichets

        guichetListID.clear();
        for (int i = 0; i < guichetList.size(); i++) {
                lablesGuichet.add(guichetList.get(i).getName());//recupère les noms de guichets
                guichetListID.add(guichetList.get(i).getId()); //recupère les Id de guichet

        }


        // Creating adapter for spinner guichet
        ArrayAdapter<String> spinnerGuichetAdapter = new ArrayAdapter<String>(LoginActivity_NEW.this,
                android.R.layout.simple_spinner_item, lablesGuichet);

        // Drop down layout style - list view with radio button
        spinnerGuichetAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerGuichet.setAdapter(spinnerGuichetAdapter);
//        spinnerGuichet.performClick();
    }
    private void populateJourOuvertFields() {

        for (int i = 0; i < jourOuvertList.size(); i++) {
            maDate = jourOuvertList.get(i).getMyDate(); //recupère la date
//            jourIsClosed = jourOuvertList.get(i).getName(); //recupère l'état de jourIsClosed
            if(jourOuvertList.get(i).getName().equals("Y")){
                jourIsClosed="TRUE";
            }else{
                jourIsClosed="FALSE";
            }


        }
        populateJourOuvert();


    }

    /**
     * Async task to get all caisses
     * */
    private class GetCaisses extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                pDialogFectchCaisse = new ProgressDialog(LoginActivity_NEW.this);
                pDialogFectchCaisse.setMessage("Fetching parameters's list..");
                pDialogFectchCaisse.setCancelable(false);
                pDialogFectchCaisse.show();
            } else {
                Toast.makeText(LoginActivity_NEW.this,
                        "Impossible de se connecter à Internet",
                        Toast.LENGTH_LONG).show();

            }


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();

            String json = jsonParser.makeServiceCall(URL_CATEGORIES, ServiceHandler.GET);

            caisseList.clear(); // pour vider la liste
            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj
                                .getJSONArray("categories");

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category cat = new Category(catObj.getInt("id"),
                                    catObj.getString("name"));
                            caisseList.add(cat);
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
            if (pDialogFectchCaisse !=null && pDialogFectchCaisse.isShowing()){
                pDialogFectchCaisse.dismiss();
             populateSpinnerCaisses();
            }
        }

    }
    /**
     * Async task to get all guichets by caisse
     * */
    private class GetGuichets extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
//                pDialogFectchGuichet = new ProgressDialog(LoginActivity.this);
//                pDialogFectchGuichet.setMessage("Fetching guichets's list..");
//                pDialogFectchGuichet.setCancelable(false);
//                pDialogFectchGuichet.show();
            } else {
                Toast.makeText(LoginActivity_NEW.this,
                        "Impossible de se connecter à Internet",
                        Toast.LENGTH_LONG).show();

            }


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair(KEY_GX_CX_NUMERO, String.valueOf(MyData.CAISSE_ID)));
            //httpParams.add(new BasicNameValuePair(KEY_GX_CX_NUMERO, String.valueOf(CAISSE_ID)));


           // HttpJsonParser httpJsonParser = new HttpJsonParser();
            //String jsonGuichet = jsonParser.makeServiceCall(URL_GUICHETS, ServiceHandler.GET,httpParams);
            String jsonGuichet = (String) jsonParser.makeServiceCall( BASE_URL + "get_guichets_by_caisse_id_to_exploitation.php", ServiceHandler.GET, httpParams);




            Log.e("Response: ", "> " + jsonGuichet);

            guichetList.clear(); //pour vider la liste de guichets

            //for manage list of guichet
            if (jsonGuichet != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonGuichet);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");


                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category cat = new Category(catObj.getInt("id"),
                                    catObj.getString("name"));
                            guichetList.add(cat);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data guichet", "Didn't receive any data from server!");
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
           /* if (pDialogFectchGuichet !=null && pDialogFectchGuichet.isShowing()){
                pDialogFectchGuichet.dismiss();
                populateSpinnerGuichets();
            }*/
            populateSpinnerGuichets();

        }

    }

    /**
     * Async task to get all guichets by caisse
     * */
    private class GetUserCaisse1 extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
//                pDialogFectchGuichet = new ProgressDialog(LoginActivity.this);
//                pDialogFectchGuichet.setMessage("Fetching guichets's list..");
//                pDialogFectchGuichet.setCancelable(false);
//                pDialogFectchGuichet.show();
            } else {
                Toast.makeText(LoginActivity_NEW.this,
                        "Impossible de se connecter à Internet",
                        Toast.LENGTH_LONG).show();

            }


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair(KEY_GX_CX_NUMERO, String.valueOf(MyData.CAISSE_ID)));
            httpParams.add(new BasicNameValuePair(KEY_UX_PROFIL, uxProfil));
            httpParams.add(new BasicNameValuePair(KEY_UX_LOGIN, pseudo));
            httpParams.add(new BasicNameValuePair(KEY_UX_PASSWORD, password));
            //httpParams.add(new BasicNameValuePair(KEY_GX_CX_NUMERO, String.valueOf(CAISSE_ID)));


           // HttpJsonParser httpJsonParser = new HttpJsonParser();
            //String jsonGuichet = jsonParser.makeServiceCall(URL_GUICHETS, ServiceHandler.GET,httpParams);
            String jsonGuichet = (String) jsonParser.makeServiceCall( BASE_URL + "get_guichets_by_caisse_id.php", ServiceHandler.GET, httpParams);




            Log.e("Response: ", "> " + jsonGuichet);

            guichetList.clear(); //pour vider la liste de guichets

            //for manage list of guichet
            if (jsonGuichet != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonGuichet);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");


                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category cat = new Category(catObj.getInt("id"),
                                    catObj.getString("name"));
                            guichetList.add(cat);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data guichet", "Didn't receive any data from server!");
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
           /* if (pDialogFectchGuichet !=null && pDialogFectchGuichet.isShowing()){
                pDialogFectchGuichet.dismiss();
                populateSpinnerGuichets();
            }*/
            populateSpinnerGuichets();

        }

    }

    private class GetUser extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar

            pDialogFectchUserDetails = new ProgressDialog(LoginActivity_NEW.this);
            pDialogFectchUserDetails.setMessage("Checking. Please wait...");
            pDialogFectchUserDetails.setIndeterminate(false);
            pDialogFectchUserDetails.setCancelable(false);
            pDialogFectchUserDetails.show();

        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            userFound = false;
//            httpParams.put(KEY_GX_CX_NUMERO, String.valueOf(MyData.CAISSE_ID));
//            httpParams.put(KEY_UX_PROFIL, uxProfil);
            httpParams.put(KEY_UX_LOGIN, pseudo);
            httpParams.put(KEY_UX_PASSWORD, password);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_user_by_login_and_password.php", "GET", httpParams);
//            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
//                    BASE_URL + "get_user_by_login_and_password.php", "GET", httpParams);
            Log.e("get to user: ", "> " + jsonObject);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject user;
                if (success == 1) {
                    //Parse the JSON response
                    user = jsonObject.getJSONObject(KEY_DATA);
                    userFound = true;
                    Log.e("test userFound: ", "> " + userFound);
                    userId = user.getInt(KEY_UX_NUMERO);
                    userNom = user.getString(KEY_UX_NOM);
                    userPrenom = user.getString(KEY_UX_PRENOM);
                    userEmail = user.getString(KEY_UX_EMAIL);
                    userProfil = user.getString(KEY_UX_PROFIL);
                    userCxNumero = user.getString(KEY_UX_CAISSE);
                    userGxNumero = user.getString(KEY_UX_GUICHET);
                    userCxDenomination = user.getString(KEY_UX_CAISSE_DENOMINATION);
                    userGxDenomination = user.getString(KEY_UX_GUICHET_DENOMINATION);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialogFectchUserDetails.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    //Populate the Edit Texts once the network activity is finished executing
                    try {
                        populateUser();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            });
        }


    }
    private class GetUserCaisse extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar

            pDialogFectchUserDetails = new ProgressDialog(LoginActivity_NEW.this);
            pDialogFectchUserDetails.setMessage("Checking. Please wait...");
            pDialogFectchUserDetails.setIndeterminate(false);
            pDialogFectchUserDetails.setCancelable(false);
            pDialogFectchUserDetails.show();

        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            userFound = false;
            httpParams.put(KEY_GX_CX_NUMERO, String.valueOf(MyData.CAISSE_ID));
            httpParams.put(KEY_UX_PROFIL, uxProfil);
            httpParams.put(KEY_UX_LOGIN, pseudo);
            httpParams.put(KEY_UX_PASSWORD, password);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_user_by_caisse_id_and_pseudo.php", "GET", httpParams);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject user;
                if (success == 1) {
                    //Parse the JSON response
                    user = jsonObject.getJSONObject(KEY_DATA);
                    userFound = true;
                    userId = user.getInt(KEY_UX_NUMERO);
                    userNom = user.getString(KEY_UX_NOM);
                    userPrenom = user.getString(KEY_UX_PRENOM);
                    userEmail = user.getString(KEY_UX_EMAIL);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialogFectchUserDetails.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    //Populate the Edit Texts once the network activity is finished executing
populate();
                    //mySpinnerCaisse.setText(gxCaisse);
//                    userFound=true;
//                    MyData.USER_ID = userId;
//                    MyData.USER_NOM = userNom;
//                    MyData.USER_PRENOM = userPrenom;
//                    MyData.USER_EMAIL = userEmail;

                }
            });
        }


    }
    private class GetUserGuichet extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar

            pDialogFectchUserDetails = new ProgressDialog(LoginActivity_NEW.this);
            pDialogFectchUserDetails.setMessage("Checking. Please wait...");
            pDialogFectchUserDetails.setIndeterminate(false);
            pDialogFectchUserDetails.setCancelable(false);
            pDialogFectchUserDetails.show();

        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            userFound = false;
            httpParams.put(KEY_UX_GUICHET, String.valueOf(MyData.GUICHET_ID));
            //httpParams.put(KEY_UX_GUICHET, String.valueOf(16));
            httpParams.put(KEY_UX_PROFIL, uxProfil);
            httpParams.put(KEY_UX_LOGIN, pseudo);
            httpParams.put(KEY_UX_PASSWORD, password);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_user_by_guichet_id_and_pseudo.php", "GET", httpParams);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                Log.e("Response to username: ", "> " + success+" "+MyData.GUICHET_ID);
                JSONObject user;
                if (success == 1) {
                    //Parse the JSON response
                    user = jsonObject.getJSONObject(KEY_DATA);
                    userFound = true;
                    userId = user.getInt(KEY_UX_NUMERO);
                    userNom = user.getString(KEY_UX_NOM);
                    userPrenom = user.getString(KEY_UX_PRENOM);
                    userEmail = user.getString(KEY_UX_EMAIL);
                    userCxNumero = user.getString(KEY_UX_CAISSE);//new
                    userCxDenomination = user.getString(KEY_UX_CAISSE_DENOMINATION);//new
                    Log.d("username",userNom);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialogFectchUserDetails.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    //Populate the Edit Texts once the network activity is finished executing
            populateParamGuichet();

                }
            });
        }


    }

    /**
     * Async task to get statut of jour ouvert
     * */
    private class GetIfJourOuvertIsOn extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                /*pDialogFectchGuichet = new ProgressDialog(LoginActivity.this);
                pDialogFectchGuichet.setMessage("Get statut guichet...");
                pDialogFectchGuichet.setCancelable(true);
                pDialogFectchGuichet.show();*/
            } else {
                Toast.makeText(LoginActivity_NEW.this,
                        "Impossible de se connecter à Internet",
                        Toast.LENGTH_LONG).show();

            }


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair(KEY_JO_GUICHET, String.valueOf(MyData.GUICHET_ID)));
            //httpParams.add(new BasicNameValuePair(KEY_GX_CX_NUMERO, String.valueOf(CAISSE_ID)));


            // HttpJsonParser httpJsonParser = new HttpJsonParser();
            //String jsonGuichet = jsonParser.makeServiceCall(URL_GUICHETS, ServiceHandler.GET,httpParams);
            String jsonJourOuvert = (String) jsonParser.makeServiceCall( BASE_URL + "get_jour_ouvert_by_guichet_id.php", ServiceHandler.GET, httpParams);




            Log.e("Response: ", "> " + jsonJourOuvert);

            jourOuvertList.clear(); //pour vider la liste

            //for manage list of guichet
            if (jsonJourOuvert != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonJourOuvert);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");


                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                           /* maDate = catObj.getString("id");
                            jourIsClosed  = catObj.getString("name");
                            //Log.e("SQL: ", "> " + maDate);
                            jourOuvertList.add(maDate);
                            jourOuvertList.add(jourIsClosed);*/

                            Category cat = new Category(catObj.getString("id"),
                                    catObj.getString("name"));
                            jourOuvertList.add(cat);
//                            Log.d("list1*******",jourOuvertList.get(i).getMyDate());
//                            Log.d("list1*******",jourOuvertList.get(i).getName());
                            MyData.JOUR_OUVERT_DATE = jourOuvertList.get(i).getMyDate() ;
                            MyData.JOUR_OUVERT_IS_CLOSED = jourOuvertList.get(i).getName() ;
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data Jour ouvert", "Didn't receive any data from server!");
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
           /* if (pDialogFectchGuichet !=null && pDialogFectchGuichet.isShowing()){
                pDialogFectchGuichet.dismiss();
                populateJourOuvertFields();
            }*/
            populateJourOuvertFields();

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
       /* Toast.makeText(
                getApplicationContext(),
                parent.getItemAtPosition(position).toString() + " Selected" ,
                Toast.LENGTH_LONG).show();*/
        int idSpinner = parent.getId();

        switch (idSpinner)
        {
            case R.id.spinner1:
                // your stuff here
                if (spinnerProfil.getSelectedItem().toString().equals("Administrateur caisse")) {
                    tv_caisse.setVisibility(View.VISIBLE);
                    tv_guichet.setVisibility(View.GONE);
                    spinnerCaisse.setVisibility(View.VISIBLE);
                    spinnerGuichet.setVisibility(View.GONE);
                    //spinnerCaisse.setOnItemSelectedListener(LoginActivity.this);
                    new LoginActivity_NEW.GetCaisses().execute();
                }else if(spinnerProfil.getSelectedItem().toString().equals("Agent de guichet")){
                    tv_caisse.setVisibility(View.VISIBLE);
                    tv_guichet.setVisibility(View.VISIBLE);
                    spinnerCaisse.setVisibility(View.VISIBLE);
                    spinnerGuichet.setVisibility(View.VISIBLE);
                   // spinnerCaisse.setOnItemSelectedListener(LoginActivity.this);
                    //spinnerGuichet.setOnItemSelectedListener(LoginActivity.this);
                    new LoginActivity_NEW.GetCaisses().execute();
                    //spinnerCaisse.performClick();
                   // new LoginActivity.GetGuichets().execute();
                }
                else{
                    tv_caisse.setVisibility(View.GONE);
                    tv_guichet.setVisibility(View.GONE);
                    spinnerCaisse.setVisibility(View.GONE);
                    spinnerGuichet.setVisibility(View.GONE);
                }
                break;
            case R.id.spinnerCaisse:
                // your stuff here
                if (caisseListID.size()>0){
                    MyData.CAISSE_ID = caisseListID.get(position);//pour recuperer l'ID de la caisse selectionnée
                    // new LoginActivity.GetCaisses().execute();
                    new LoginActivity_NEW.GetGuichets().execute();
                    MyData.CAISSE_NAME = caisseList.get(position).getName();//pour recuperer le nom de la caisse selectionnée

                }
                break;
            case R.id.spinnerGuichet:
                // your stuff here
                if (guichetListID.size()>0){
                    MyData.GUICHET_ID = guichetListID.get(position);//pour recuperer l'ID du guichet selectionnée
                    MyData.GUICHET_NAME = guichetList.get(position).getName();//pour recuperer le nom du guichet selectionné
                    populateParamGuichet();
                }
                break;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }


}
