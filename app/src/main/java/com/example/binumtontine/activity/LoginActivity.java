package com.example.binumtontine.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.binumtontine.R;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.activity.jourOuvert.JourOuvert;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.example.binumtontine.helper.HttpJsonParser;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;


public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, SERVER_ADDRESS {


    private Spinner spinnerProfil;
    private Spinner spinnerGuichet;
    private Button connect;
    private EditText pseudo;
    private EditText password;
    private TextView tv_caisse;
    private TextView tv_guichet;
    private TextView warningInfo;
    private int attemptConnection=3;
    CircularProgressButton btn;

    /* manage spinner*/
    // array list for spinner adapter
    private ArrayList<Category> caisseList;
    private ArrayList<Category> guichetList;
    List<Integer> caisseListID = new ArrayList<Integer>();
    List<Integer> guichetListID = new ArrayList<Integer>();
    //private int CAISSE_ID=0;
    //private int GUICHET_ID;
    private Spinner spinnerCaisse;
    private TextInputLayout textInputLayoutCaisse;
    private ProgressDialog pDialog;
    /*end manage*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);

        spinnerProfil = findViewById(R.id.spinner1);
        /* Block Caisse start*/
        tv_caisse =(TextView) findViewById(R.id.lblCaisse);
        spinnerCaisse = findViewById(R.id.spinnerCaisse);
        tv_caisse.setVisibility(View.GONE);
        spinnerCaisse.setVisibility(View.GONE);
        /*Block Caisse End*/

        /* Block Guichey start*/
        tv_guichet =(TextView) findViewById(R.id.lblGuichet);
        tv_guichet.setVisibility(View.GONE);
        spinnerGuichet = findViewById(R.id.spinnerGuichet);
        spinnerGuichet.setVisibility(View.GONE);
        /*Block Guichet End*/

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.type_admin,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProfil.setAdapter(adapter);

/*
        ArrayAdapter<CharSequence> adapterCaisse = ArrayAdapter.createFromResource(
                this, R.array.array_caisse,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCaisse.setAdapter(adapterCaisse);

        ArrayAdapter<CharSequence> adapterGuichet = ArrayAdapter.createFromResource(
                this, R.array.array_guichet,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGuichet.setAdapter(adapterGuichet);*/

       /* if (spinnerProfil.getSelectedItem().toString().equals("Administrateur OF")){
            spinnerCaisse.setVisibility(View.GONE);
            spinnerGuichet.setVisibility(View.GONE);
        }else{
            spinnerCaisse.setVisibility(View.VISIBLE);
            spinnerGuichet.setVisibility(View.VISIBLE);
        } */
       /* textInputLayoutCaisse = (TextInputLayout) findViewById(R.id.til_caisse);
        textInputLayoutCaisse.setVisibility(View.GONE);*/




        caisseList = new ArrayList<Category>();
        guichetList = new ArrayList<Category>();
        // spinner item select listener
        spinnerProfil.setOnItemSelectedListener(LoginActivity.this);
        spinnerCaisse.setOnItemSelectedListener(LoginActivity.this);
        spinnerGuichet.setOnItemSelectedListener(LoginActivity.this);
        new LoginActivity.GetCategories().execute();

        //recupération des attributs de la page d'authentification
        pseudo = (EditText)findViewById(R.id.txtPseudo);
        password = (EditText)findViewById(R.id.txtPassword);
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

       // new LoginActivity.FetchMovieDetailsAsyncTask().execute(pseudo.getText().toString(),password.getText().toString());
        validate(pseudo.getText().toString(),password.getText().toString());

    }


    private void validate (String pseudo, String password){
        if ((pseudo.equals("")) && (password.equals(""))
                && spinnerProfil.getSelectedItem().toString().equals("Administrateur OF")){
            Intent i = new Intent(this, MainActivity.class);

            startActivity(i);
        }else if((pseudo.equals("")) && (password.equals(""))
                &&(spinnerProfil.getSelectedItem().toString().equals("Administrateur caisse"))){

            Intent i = new Intent(this, MainActivityAdminCaisse.class);
            startActivity(i);
        }else if((pseudo.equals("")) && (password.equals(""))
                && (spinnerProfil.getSelectedItem().toString().equals("Usager"))){

            Intent i = new Intent(this, JourOuvert.class);
            startActivity(i);
        } else{
            attemptConnection--;
            warningInfo.setVisibility(View.VISIBLE);
            warningInfo.setText("Number of attempts remaining: "+String.valueOf(attemptConnection));
            if (attemptConnection==0)
                connect.setEnabled(false);

        }
    }


    /* Begin */
    /**
     * Fetches single preParamProduit details from the server
     */
    private class FetchMovieDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            btn.startAnimation();
           /* pDialog = new ProgressDialog(PrivacyPolicyActivity.this);
            pDialog.setMessage("Loading Pre Paramètres Details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/
        }

        @Override
        protected String doInBackground(String... params) {
            validate(params[0],params[1]);



            //  HttpJsonParser httpJsonParser = new HttpJsonParser();
            /*JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_user.php", "GET", null);*/
            /*JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_pre_parametrage_details.php", "GET", null);*/


            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            //pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    //Populate the Edit Texts once the network activity is finished executing
                    btn.revertAnimation();
                    //startActivity(i);

                }
            });
        }


    }

    /* end */
    /**
     * Adding spinner data
     * */
    private void populateSpinner() {
        List<String> lables = new ArrayList<String>(); //for caisses
        List<String> lablesGuichet = new ArrayList<String>(); //for guichets

        //tvCaisse.setText("");

        for (int i = 0; i < caisseList.size(); i++) {
            lables.add(caisseList.get(i).getName());//recupère les noms de caisses
            caisseListID.add(caisseList.get(i).getId()); //recupère les Id
        }

        for (int i = 0; i < guichetList.size(); i++) {
            if (guichetList.get(i).getFk()==CAISSE_ID) {
                lablesGuichet.add(guichetList.get(i).getName());//recupère les noms de guichets
                guichetListID.add(guichetList.get(i).getId()); //recupère les Id de guichet
            }
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(LoginActivity.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Creating adapter for spinner guichet
        ArrayAdapter<String> spinnerGuichetAdapter = new ArrayAdapter<String>(LoginActivity.this,
                android.R.layout.simple_spinner_item, lablesGuichet);

        // Drop down layout style - list view with radio button
        spinnerGuichetAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerCaisse.setAdapter(spinnerAdapter);
        spinnerGuichet.setAdapter(spinnerGuichetAdapter);
    }

    /**
     * Async task to get all food categories
     * */
    private class GetCategories extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                pDialog = new ProgressDialog(LoginActivity.this);
                pDialog.setMessage("Fetching parameters's list..");
                pDialog.setCancelable(false);
                pDialog.show();
            } else {
                Toast.makeText(LoginActivity.this,
                        "Impossible de se connecter à Internet",
                        Toast.LENGTH_LONG).show();

            }


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
            httpParams.add(new BasicNameValuePair(KEY_GX_CX_NUMERO, "1"));
            //httpParams.add(new BasicNameValuePair(KEY_GX_CX_NUMERO, String.valueOf(CAISSE_ID)));


            String json = jsonParser.makeServiceCall(URL_CATEGORIES, ServiceHandler.GET);
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            //String jsonGuichet = jsonParser.makeServiceCall(URL_GUICHETS, ServiceHandler.GET,httpParams);
            String jsonGuichet = (String) jsonParser.makeServiceCall( BASE_URL + "get_guichets_by_caisse_id.php", ServiceHandler.GET, httpParams);
           // JSONObject jsonGuichet =  httpJsonParser.makeHttpRequest( BASE_URL + "get_guichets_by_caisse_id.php", "GET", httpParams);

/*
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_GUICHET_ID, guichetId);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_guichet_details.php", "GET", httpParams);*/


            Log.e("Response: ", "> " + json);
            Log.e("Response: ", "> " + jsonGuichet);

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

            //for manage list of guichet
            if (jsonGuichet != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonGuichet);
                  //  JSONObject jsonObj = jsonGuichet.getJSONObject("categories");
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("categories");


                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category cat = new Category(catObj.getInt("id"),catObj.getInt("fk"),
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
            if (pDialog!=null &&pDialog.isShowing()){
                pDialog.dismiss();
             populateSpinner();
            }
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
                }else if(spinnerProfil.getSelectedItem().toString().equals("Usager")){
                    tv_caisse.setVisibility(View.VISIBLE);
                    tv_guichet.setVisibility(View.VISIBLE);
                    spinnerCaisse.setVisibility(View.VISIBLE);
                    spinnerGuichet.setVisibility(View.VISIBLE);
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
                if (caisseListID.size()>0)
                MyData.CAISSE_ID = caisseListID.get(position);//pour recuperer l'ID de la caisse selectionnée
                break;
            case R.id.spinnerGuichet:
                // your stuff here
                if (guichetListID.size()>0)
                MyData.GUICHET_ID = guichetListID.get(position);//pour recuperer l'ID du guichet selectionnée
                break;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }


}
