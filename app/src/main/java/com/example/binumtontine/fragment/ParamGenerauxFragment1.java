package com.example.binumtontine.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.example.binumtontine.activity.MainActivity;
import com.example.binumtontine.activity.MainActivityAdminCaisse;
import com.example.binumtontine.dao.SERVER_ADDRESS;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;


public class ParamGenerauxFragment1 extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, SERVER_ADDRESS {
    private Spinner spinnerProfil;
    private Spinner spinnerCaisse;
    private Spinner spinnerGuichet;
    private Button connect;
    private EditText pseudo;
    private EditText password;
    private TextView warningInfo;
    private int attemptConnection=3;
    CircularProgressButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);

        spinnerProfil = findViewById(R.id.spinner1);

        spinnerCaisse = findViewById(R.id.spinnerCaisse);
        spinnerCaisse.setVisibility(View.GONE);
        spinnerGuichet = findViewById(R.id.spinnerGuichet);
        spinnerGuichet.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.type_admin,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProfil.setAdapter(adapter);


        ArrayAdapter<CharSequence> adapterCaisse = ArrayAdapter.createFromResource(
                this, R.array.array_caisse,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCaisse.setAdapter(adapterCaisse);

        ArrayAdapter<CharSequence> adapterGuichet = ArrayAdapter.createFromResource(
                this, R.array.array_guichet,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGuichet.setAdapter(adapterGuichet);

        if (spinnerProfil.getSelectedItem().toString().equals("Administrateur OF")){
            spinnerCaisse.setVisibility(View.GONE);
            spinnerGuichet.setVisibility(View.GONE);
        }else{
            spinnerCaisse.setVisibility(View.VISIBLE);
            spinnerGuichet.setVisibility(View.VISIBLE);
        }


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
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                && (spinnerProfil.getSelectedItem().toString().equals("Administrateur OF")
                || spinnerProfil.getSelectedItem().toString().equals("Administrateur caisse")
                || spinnerProfil.getSelectedItem().toString().equals("Administrateur guichet"))){

            Intent i = new Intent(this, MainActivityAdminCaisse.class);
            startActivity(i);
        } else{
            attemptConnection--;
            warningInfo.setVisibility(View.VISIBLE);
            warningInfo.setText("No of attempts remaining: "+String.valueOf(attemptConnection));
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


}
