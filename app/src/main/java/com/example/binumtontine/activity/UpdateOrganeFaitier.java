package com.example.binumtontine.activity;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.binumtontine.R;
import com.example.binumtontine.controleur.OrganeFaitierControler;
import com.example.binumtontine.fragment.UpdateOFFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UpdateOrganeFaitier extends AppCompatActivity implements View.OnClickListener {
    //
    private OrganeFaitierControler controle; //instance du controleur
    private Integer num_of;

    //propriétés graphiques
    private Button btn_save_of;

    private EditText txt_sigle;
    private EditText txt_libelle;
    private EditText txt_num_agrement;
//  private Date date_agrement;
    private EditText txt_boite_postale;
    private EditText txt_ville_of;
    private EditText txt_pays_of;
    private EditText txt_adresse_of;
    private EditText txt_telephone1_of;
    private EditText txt_siege_of;
    private EditText txt_nom_pca_of;
    private EditText txt_nom_vpca_of;
    private EditText txt_nom_dg_of;
    private EditText dateAgrementOF_txt; //champ de texte qui gère la dateAgrement


    private DatePickerDialog dateAgrementOF_PickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date
 // private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter; //propriété permettant de gérer le format de la date

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maj_of);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_update_Of);
        setSupportActionBar(toolbar);
        setToolbarTitle();
        init();
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();

        setDateTimeField();
    }
    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Mise à jour Organe faitier");

    }
    private void init(){
        //initialisation de toutes les propriétés
        this.controle = OrganeFaitierControler.getInstance(this);
        txt_sigle = (EditText)findViewById(R.id.input_txt_sigle_of);
        txt_libelle = (EditText)findViewById(R.id.input_txt_denominationOF);
        txt_num_agrement = (EditText)findViewById(R.id.input_num_agrement_OF);;
//    private Date date_agrement;
        txt_boite_postale = (EditText)findViewById(R.id.input_txt_BP_OF);;
        txt_ville_of = (EditText)findViewById(R.id.input_txt_Ville_OF);;
        txt_pays_of = (EditText)findViewById(R.id.input_txt_Pays_OF);;
        txt_adresse_of = (EditText)findViewById(R.id.input_txt_Adresse_OF);;
        txt_telephone1_of = (EditText)findViewById(R.id.input_txt_Tel1_OF);;
        txt_siege_of = (EditText)findViewById(R.id.input_txt_siege_of);;
        txt_nom_pca_of = (EditText)findViewById(R.id.input_txt_NomPCA_OF);;
        txt_nom_vpca_of = (EditText)findViewById(R.id.input_txt_NomVPCA_OF);;
        txt_nom_dg_of = (EditText)findViewById(R.id.input_txt_NomDG_OF);;
       // private EditText dateAgrementOF_txt;
        btn_save_of = (Button)findViewById(R.id.btn_update_of);
        btn_save_of.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                String sigle = txt_sigle.getText().toString() ;
                String libelle = txt_libelle.getText().toString();
                String num_agrement = txt_num_agrement.getText().toString();
          //      String boite_postale = txt_boite_postale.getText().toString();
                Integer boite_postale = Integer.parseInt(txt_boite_postale.getText().toString());
                String ville = txt_ville_of.getText().toString();
                String pays = txt_pays_of.getText().toString();
                String adresse = txt_adresse_of.getText().toString();
                String telephone1 = txt_telephone1_of.getText().toString();
                String siege = txt_siege_of.getText().toString();
                String nom_pca = txt_nom_pca_of.getText().toString();
                String nom_vpca = txt_nom_vpca_of.getText().toString();
                String nom_dg = txt_nom_dg_of.getText().toString();
                String dateAgrementOF = dateAgrementOF_txt.getText().toString();


             //  Toast toast = new Toast(CreateOrganeFaitier.this, "Success saving!", Toast.LENGTH_SHORT);
               // toast.show();
                controle.creerOrganeFaitier(num_of, sigle,libelle,num_agrement,dateAgrementOF,boite_postale,ville,
                        pays,adresse,telephone1,siege,nom_pca,nom_vpca,nom_dg, UpdateOrganeFaitier.this);



            }
        });
        recupOrganeFaitier(); //on restaure (deserialise) l'organe faitier sauvegardé (serialisé)


    }
    private void findViewsById() {
        dateAgrementOF_txt = (EditText) findViewById(R.id.input_dateAgrementOF);
        dateAgrementOF_txt.setInputType(InputType.TYPE_NULL);
        dateAgrementOF_txt.requestFocus();


    }

    private void setDateTimeField() {
        dateAgrementOF_txt.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();
        dateAgrementOF_PickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateAgrementOF_txt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

       /* toDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        */
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
        } /* else if(view == toDateEtxt) {
            toDatePickerDialog.show();
        }*/
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
            txt_boite_postale.setText(controle.getBoite_postale().toString());
            txt_ville_of.setText(controle.getVille_of());
            txt_pays_of.setText(controle.getPays_of());
            txt_adresse_of.setText(controle.getAdresse_of());
            txt_telephone1_of.setText(controle.getTelephone1_of());
            txt_siege_of.setText(controle.getSiege_of());
            txt_nom_pca_of.setText(controle.getNom_pca_of());
            txt_nom_vpca_of.setText(controle.getNom_vpca_of());
            txt_nom_dg_of.setText(controle.getNom_dg_of());


            //s'il y'a un calcul à faire, on peut simuler le clic sur le bouton calcul
            //((Button)findViewById(R.id.btnSave_OF)).performClick();
        }
    }
}
