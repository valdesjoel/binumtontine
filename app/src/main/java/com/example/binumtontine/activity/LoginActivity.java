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
import com.example.binumtontine.activity.adherent.CreateAdherent;
import com.example.binumtontine.activity.adherent.MainActivityUsager;
import com.example.binumtontine.controleur.MyData;
import com.example.binumtontine.activity.jourOuvert.JourOuvert;
import com.example.binumtontine.dao.SERVER_ADDRESS;
import com.example.binumtontine.helper.CheckNetworkStatus;
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
    private Spinner spinnerCaisse;
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


    private TextInputLayout textInputLayoutCaisse;
    private ProgressDialog pDialogFectchCaisse;
    private ProgressDialog pDialogFectchGuichet;
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

        caisseList = new ArrayList<Category>();
        guichetList = new ArrayList<Category>();
        // spinner item select listener
        //new LoginActivity.GetCaisses().execute();
        spinnerProfil.setOnItemSelectedListener(LoginActivity.this);
        spinnerCaisse.setOnItemSelectedListener(LoginActivity.this);
        spinnerGuichet.setOnItemSelectedListener(LoginActivity.this);



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

            //Intent i = new Intent(this, JourOuvert.class);
            //Intent i = new Intent(this, CreateAdherent.class);
            Intent i = new Intent(this, MainActivityUsager.class);
            startActivity(i);
        } else{
            attemptConnection--;
            warningInfo.setVisibility(View.VISIBLE);
            warningInfo.setText("Number of attempts remaining: "+String.valueOf(attemptConnection));
            if (attemptConnection==0)
                connect.setEnabled(false);

        }
    }



    /**
     * Adding spinner data
     * */
    private void populateSpinnerCaisses() {
        List<String> lables = new ArrayList<String>(); //for caisses


        for (int i = 0; i < caisseList.size(); i++) {
            lables.add(caisseList.get(i).getName());//recupère les noms de caisses
            caisseListID.add(caisseList.get(i).getId()); //recupère les Id
        }



        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(LoginActivity.this,
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


        for (int i = 0; i < guichetList.size(); i++) {
                lablesGuichet.add(guichetList.get(i).getName());//recupère les noms de guichets
                guichetListID.add(guichetList.get(i).getId()); //recupère les Id de guichet

        }


        // Creating adapter for spinner guichet
        ArrayAdapter<String> spinnerGuichetAdapter = new ArrayAdapter<String>(LoginActivity.this,
                android.R.layout.simple_spinner_item, lablesGuichet);

        // Drop down layout style - list view with radio button
        spinnerGuichetAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerGuichet.setAdapter(spinnerGuichetAdapter);
    }

    /**
     * Async task to get all food categories
     * */
    private class GetCaisses extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                pDialogFectchCaisse = new ProgressDialog(LoginActivity.this);
                pDialogFectchCaisse.setMessage("Fetching parameters's list..");
                pDialogFectchCaisse.setCancelable(false);
                pDialogFectchCaisse.show();
            } else {
                Toast.makeText(LoginActivity.this,
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
    private class GetGuichets extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                pDialogFectchGuichet = new ProgressDialog(LoginActivity.this);
                pDialogFectchGuichet.setMessage("Fetching guichets's list..");
                pDialogFectchGuichet.setCancelable(true);
                pDialogFectchGuichet.show();
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
            httpParams.add(new BasicNameValuePair(KEY_GX_CX_NUMERO, String.valueOf(MyData.CAISSE_ID)));
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
            if (pDialogFectchGuichet !=null && pDialogFectchGuichet.isShowing()){
                pDialogFectchGuichet.dismiss();
                populateSpinnerGuichets();
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
                    //spinnerCaisse.setOnItemSelectedListener(LoginActivity.this);
                    new LoginActivity.GetCaisses().execute();
                }else if(spinnerProfil.getSelectedItem().toString().equals("Usager")){
                    tv_caisse.setVisibility(View.VISIBLE);
                    tv_guichet.setVisibility(View.VISIBLE);
                    spinnerCaisse.setVisibility(View.VISIBLE);
                    spinnerGuichet.setVisibility(View.VISIBLE);
                   // spinnerCaisse.setOnItemSelectedListener(LoginActivity.this);
                    //spinnerGuichet.setOnItemSelectedListener(LoginActivity.this);
                    new LoginActivity.GetCaisses().execute();
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
                if (caisseListID.size()>0)
                MyData.CAISSE_ID = caisseListID.get(position);//pour recuperer l'ID de la caisse selectionnée
               // new LoginActivity.GetCaisses().execute();
                new LoginActivity.GetGuichets().execute();
                MyData.CAISSE_NAME = caisseList.get(position).getName();//pour recuperer le nom de la caisse selectionnée
                break;
            case R.id.spinnerGuichet:
                // your stuff here
                if (guichetListID.size()>0)
                MyData.GUICHET_ID = guichetListID.get(position);//pour recuperer l'ID du guichet selectionnée
                MyData.GUICHET_NAME = guichetList.get(position).getName();//pour recuperer le nom du guichet selectionné
                break;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }


}
