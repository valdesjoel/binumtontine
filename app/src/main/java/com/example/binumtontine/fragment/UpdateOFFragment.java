package com.example.binumtontine.fragment;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.Fragment; //import android.support.v4.app.Fragment;

import com.example.binumtontine.R;
import com.example.binumtontine.controleur.OrganeFaitierControler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class UpdateOFFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private OrganeFaitierControler controle; //instance du controleur

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

    public UpdateOFFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maj_of, container, false);


        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById(rootView);


        setDateTimeField();

        // Inflate the layout for this fragment
        return rootView;
    }
    private void findViewsById(View rootView) {


        dateAgrementOF_txt = (EditText) rootView.findViewById(R.id.input_dateAgrementOF);
        dateAgrementOF_txt.setInputType(InputType.TYPE_NULL);
        dateAgrementOF_txt.requestFocus();


    }
    private void setDateTimeField() {
        dateAgrementOF_txt.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();
        dateAgrementOF_PickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateAgrementOF_txt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    @Override
    public void onClick(View v) {
        if(v == dateAgrementOF_txt) {
            dateAgrementOF_PickerDialog.show();
        }

    }
    /**
     * Récupération et valorisation des champs d'un organe faitier s'il a été sérialisé
     */
    public void recupOrganeFaitier(){
        if (controle.getSigle()!=null){
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