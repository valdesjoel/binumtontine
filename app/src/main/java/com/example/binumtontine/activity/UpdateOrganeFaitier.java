package com.example.binumtontine.activity;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.binumtontine.JRSpinner;
import com.example.binumtontine.R;
import com.example.binumtontine.controleur.OrganeFaitierControler;
import com.example.binumtontine.helper.CheckNetworkStatus;
import com.hbb20.CountryCodePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UpdateOrganeFaitier extends AppCompatActivity implements View.OnClickListener {
    //
    private OrganeFaitierControler controle; //instance du controleur
    private Integer num_of;
    private Integer success=0;

    //propriétés graphiques
    private Button btn_save_of;
    private Button btnCancel;
    private CountryCodePicker ccp_phone1;
    private CountryCodePicker ccp_phone2;
    private CountryCodePicker ccp_phone3;
    private EditText editTextCarrierPhone1;
    private EditText editTextCarrierPhone2;
    private EditText editTextCarrierPhone3;
    private EditText txt_sigle;
    private EditText txt_libelle;
    private EditText txt_num_agrement;

    private EditText OfNumAgremCobac;
    private EditText OfDatAgremCobac;
    private EditText OfNumAgremCNC;
    private EditText OfDatAgremCNC;

    //  private Date date_agrement;
    private EditText txt_boite_postale;

    private EditText txt_ville_of;
    private JRSpinner mySpinnerVille; //pour gérer le spinner contenant les villes
    private JRSpinner mySpinnerSiege; //pour gérer le spinner contenant les villes
    private EditText txt_pays_of;
    private CountryCodePicker ccp_country;
    private EditText txt_adresse_of;
    //private EditText txt_telephone1_of;
    private EditText txt_siege_of;
    private EditText txt_nom_pca_of;
    private EditText txt_nom_vpca_of;
    private EditText txt_nom_dg_of;
    private EditText dateAgrementOF_txt; //champ de texte qui gère la dateAgrement

    private String sigle ;
    private String libelle ;
    private String num_agrement ;

    private String OfNumAgremCobac_st;
    private String OfDatAgremCobac_st;
    private String OfNumAgremCNC_st;
    private String OfDatAgremCNC_st;
    //      String boite_postale = txt_boite_postale.getText().toString();
    private Integer boite_postale;
    private String ville;
    // String pays = txt_pays_of.getText().toString();
    private String pays ;
    private String adresse;



    //  String telephone1 = txt_telephone1_of.getText().toString();

    private String telephone1;
    private String telephone2;
    private String telephone3;
    private  String siege;
    private  String nom_pca ;
    private  String nom_vpca;
    private  String nom_dg ;
    private  String dateAgrementOF;

    private ProgressDialog pDialog;
    private DatePickerDialog dateAgrementOF_PickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date

    private DatePickerDialog OfDatAgremCobacPickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date
    private DatePickerDialog OfDatAgremCNCPickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date
 // private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter; //propriété permettant de gérer le format de la date

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maj_of);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_update_Of);
        setSupportActionBar(toolbar);
        setToolbarTitle();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();

        setDateTimeField();
        init();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Mise à jour Organe faitier");

    }
    private void init(){
        //initialisation de toutes les propriétés
        this.controle = OrganeFaitierControler.getInstance(this);
        txt_sigle = (EditText)findViewById(R.id.input_txt_sigle_of);
        txt_libelle = (EditText)findViewById(R.id.input_txt_denominationOF);
        txt_num_agrement = (EditText)findViewById(R.id.input_num_agrement_OF);

        OfNumAgremCobac = (EditText) findViewById(R.id.input_txt_OfNumAgremCobac);

        OfNumAgremCNC = (EditText) findViewById(R.id.input_txt_OfNumAgremCNC);
//    private Date date_agrement;
        txt_boite_postale = (EditText)findViewById(R.id.input_txt_BP_OF);
        //txt_ville_of = (EditText)findViewById(R.id.input_txt_Ville_OF);
        mySpinnerVille = (JRSpinner)findViewById(R.id.spn_my_spinner_ville_of);
        mySpinnerSiege = (JRSpinner)findViewById(R.id.spn_my_spinner_siege_of);

        mySpinnerVille.setItems(getResources().getStringArray(R.array.array_localite)); //this is important, you must set it to set the item list
        mySpinnerSiege.setItems(getResources().getStringArray(R.array.array_localite)); //this is important, you must set it to set the item list
        mySpinnerVille.setTitle("Sélectionner votre ville"); //change title of spinner-dialog programmatically
        mySpinnerSiege.setTitle("Sélectionner votre siège"); //change title of spinner-dialog programmatically
        mySpinnerVille.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically
        mySpinnerSiege.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically


        mySpinnerVille.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });
        mySpinnerSiege.setOnItemClickListener(new JRSpinner.OnItemClickListener() { //set it if you want the callback
            @Override
            public void onItemClick(int position) {
                //do what you want to the selected position

            }
        });
       // txt_pays_of = (EditText)findViewById(R.id.input_txt_Pays_OF);
        ccp_country = (CountryCodePicker) findViewById(R.id.ccp_country);
        txt_adresse_of = (EditText)findViewById(R.id.input_txt_Adresse_OF);

        ccp_phone1 = (CountryCodePicker) findViewById(R.id.ccp_phone1);
        editTextCarrierPhone1 = (EditText) findViewById(R.id.editText_carrierPhone1);
        ccp_phone1.registerCarrierNumberEditText(editTextCarrierPhone1);


        ccp_phone2 = (CountryCodePicker) findViewById(R.id.ccp_phone2);
        editTextCarrierPhone2 = (EditText) findViewById(R.id.editText_carrierPhone2);
        ccp_phone2.registerCarrierNumberEditText(editTextCarrierPhone2);

        ccp_phone3 = (CountryCodePicker) findViewById(R.id.ccp_phone3);
        editTextCarrierPhone3 = (EditText) findViewById(R.id.editText_carrierPhone3);
        ccp_phone3.registerCarrierNumberEditText(editTextCarrierPhone3);

       // txt_telephone1_of = (EditText)findViewById(R.id.input_txt_Tel1_OF);
        //txt_siege_of = (EditText)findViewById(R.id.input_txt_siege_of);
        txt_nom_pca_of = (EditText)findViewById(R.id.input_txt_NomPCA_OF);
        txt_nom_vpca_of = (EditText)findViewById(R.id.input_txt_NomVPCA_OF);
        txt_nom_dg_of = (EditText)findViewById(R.id.input_txt_NomDG_OF);
       // private EditText dateAgrementOF_txt;
        btn_save_of = (Button)findViewById(R.id.btn_update_of);
        btnCancel = (Button)findViewById(R.id.btn_clean);
        btnCancel.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }});

        btn_save_of.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                sigle = txt_sigle.getText().toString() ;
                libelle = txt_libelle.getText().toString();
                num_agrement = txt_num_agrement.getText().toString();

                OfNumAgremCobac_st = OfNumAgremCobac.getText().toString();
                OfDatAgremCobac_st = OfDatAgremCobac.getText().toString();
                OfNumAgremCNC_st = OfNumAgremCNC.getText().toString();
                OfDatAgremCNC_st = OfDatAgremCNC.getText().toString();

          //      String boite_postale = txt_boite_postale.getText().toString();
                boite_postale = Integer.parseInt(txt_boite_postale.getText().toString());
               // ville = txt_ville_of.getText().toString();
                ville = mySpinnerVille.getText().toString();
               // String pays = txt_pays_of.getText().toString();
                pays = ccp_country.getSelectedCountryName();
                adresse = txt_adresse_of.getText().toString();



              //  String telephone1 = txt_telephone1_of.getText().toString();

                telephone1 = ccp_phone1.getFullNumberWithPlus();
                telephone2 = ccp_phone2.getFullNumberWithPlus();
                telephone3 = ccp_phone3.getFullNumberWithPlus();
               // siege = txt_siege_of.getText().toString();
                siege =  mySpinnerSiege.getText().toString();
                nom_pca = txt_nom_pca_of.getText().toString();
                nom_vpca = txt_nom_vpca_of.getText().toString();
                nom_dg = txt_nom_dg_of.getText().toString();
                dateAgrementOF = dateAgrementOF_txt.getText().toString();

                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    if (true){
                        new UpdateOrganeFaitier.UpdateOfAsyncTask().execute();
                    } else {
                        Toast.makeText(UpdateOrganeFaitier.this,
                                "Un ou plusieurs champs sont vides!",
                                Toast.LENGTH_LONG).show();


                    }
                } else {
                    Toast.makeText(UpdateOrganeFaitier.this,
                            "Impossible de se connecter à Internet",
                            Toast.LENGTH_LONG).show();

                }
             //  Toast toast = new Toast(CreateOrganeFaitier.this, "Success saving!", Toast.LENGTH_SHORT);
               // toast.show();
//                controle.creerOrganeFaitier(num_of, sigle,libelle,num_agrement,dateAgrementOF,boite_postale,ville,
//                        pays,adresse,telephone1,telephone2,telephone3,siege,nom_pca,nom_vpca,nom_dg, UpdateOrganeFaitier.this);
//


            }
        });

        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
            recupOrganeFaitier(); //on restaure (deserialise) l'organe faitier sauvegardé (serialisé)
        } else {
            Toast.makeText(UpdateOrganeFaitier.this,
                    "Impossible de se connecter à Internet",
                    Toast.LENGTH_LONG).show();

        }



    }
    private void findViewsById() {
        dateAgrementOF_txt = (EditText) findViewById(R.id.input_dateAgrementOF);
        dateAgrementOF_txt.setInputType(InputType.TYPE_NULL);
        dateAgrementOF_txt.requestFocus();


        OfDatAgremCobac = (EditText) findViewById(R.id.input_txt_OfDatAgremCobac);
        OfDatAgremCobac.setInputType(InputType.TYPE_NULL);
        OfDatAgremCobac.requestFocus();

        OfDatAgremCNC = (EditText) findViewById(R.id.input_txt_OfDatAgremCNC);
        OfDatAgremCNC.setInputType(InputType.TYPE_NULL);
        OfDatAgremCNC.requestFocus();
    }

    private void setDateTimeField() {
        dateAgrementOF_txt.setOnClickListener(this);
        OfDatAgremCobac.setOnClickListener(this);
        OfDatAgremCNC.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();
        dateAgrementOF_PickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateAgrementOF_txt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        OfDatAgremCobacPickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                OfDatAgremCobac.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        OfDatAgremCNCPickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                OfDatAgremCNC.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    @Override
    public void onClick(View view) {
        if(view == dateAgrementOF_txt) {
            dateAgrementOF_PickerDialog.show();
        } else if(view == OfDatAgremCobac) {
            OfDatAgremCobacPickerDialog.show();
        } else if(view == OfDatAgremCNC) {
            OfDatAgremCNCPickerDialog.show();
        }
    }


    /**
     * Récupération et valorisation des champs d'un organe faitier s'il a été sérialisé
     */
    public void recupOrganeFaitier(){
        if (controle.getSigle()!=null){
            num_of = controle.getNumero();
            txt_sigle.setText(controle.getSigle());
            txt_libelle.setText(controle.getLibelle());
            txt_num_agrement.setText(controle.getNum_agrement());
            dateAgrementOF_txt.setText(controle.getDate_agrement());

            OfNumAgremCobac.setText(controle.getOfNumAgremCobac());
            OfDatAgremCobac.setText(controle.getOfDatAgremCobac());
            OfNumAgremCNC.setText(controle.getOfNumAgremCNC());
            OfDatAgremCNC.setText(controle.getOfDatAgremCNC());

            txt_boite_postale.setText(controle.getBoite_postale().toString());
            //txt_ville_of.setText(controle.getVille_of());
            mySpinnerVille.setText(controle.getVille_of());
           // txt_pays_of.setText(controle.getPays_of());
            //ccp_country.setCountryForNameCode(controle.getPays_of());
            ccp_country.setCountryForNameCode(controle.getPays_of());
            txt_adresse_of.setText(controle.getAdresse_of());
           // ccp_phone1txt_telephone1_of.setText(controle.getTelephone1_of());
            ccp_phone1.setFullNumber(controle.getTelephone1_of());
            ccp_phone2.setFullNumber(controle.getTelephone2_of());
            ccp_phone3.setFullNumber(controle.getTelephone3_of());
            //txt_siege_of.setText(controle.getSiege_of());
            mySpinnerSiege.setText(controle.getSiege_of());
            txt_nom_pca_of.setText(controle.getNom_pca_of());
            txt_nom_vpca_of.setText(controle.getNom_vpca_of());
            txt_nom_dg_of.setText(controle.getNom_dg_of());


            //s'il y'a un calcul à faire, on peut simuler le clic sur le bouton calcul
            //((Button)findViewById(R.id.btnSave_OF)).performClick();
        }
    }

    /**
     * AsyncTask for adding a movie
     */
    private class UpdateOfAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(UpdateOrganeFaitier.this);
            pDialog.setMessage("Mise à jour des paramètres. Veuillez patienter...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {


            try {

                controle.creerOrganeFaitier(num_of, sigle,libelle,num_agrement,dateAgrementOF, OfNumAgremCobac_st, OfDatAgremCobac_st, OfNumAgremCNC_st, OfDatAgremCNC_st,boite_postale,ville,
                        pays,adresse,telephone1,telephone2,telephone3,siege,nom_pca,nom_vpca,nom_dg, UpdateOrganeFaitier.this);

                success=1;
            } catch (Exception e) {
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
                        Toast.makeText(UpdateOrganeFaitier.this,
                                " Mise à jour effectuée", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        //send result code 20 to notify about movie update
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(UpdateOrganeFaitier.this,
                                "Some error occurred while updating",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

}
