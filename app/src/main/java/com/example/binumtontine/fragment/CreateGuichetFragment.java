package com.example.binumtontine.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.binumtontine.JRSpinner;
import com.example.binumtontine.R;
import com.example.binumtontine.activity.AboutUsActivity;
import com.example.binumtontine.activity.CaisseActivity;
import com.example.binumtontine.activity.PrivacyPolicyActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateGuichetFragment extends Fragment implements View.OnClickListener, OnDateSetListener{

    private JRSpinner mySpinner; //pour gérer le spinner dcontenant les localités

    private EditText dateAgrementCx_txt; //champ de texte qui gère la dateAgrement

    private DatePickerDialog dateAgrementCx_PickerDialog; //propriété qui permet d'avoir une pop on afin de selectionner une date

    private SimpleDateFormat dateFormatter; //propriété permettant de gérer le format de la date

    private  CardView caisseCard;
    private  CardView preParamCard;
    private  CardView userCard;
    private  CardView majOFCard;
    private  CardView ownInfoCard;

    public CreateGuichetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_fragment_param_gen1, container, false);

       /* mySpinner = (JRSpinner) rootView.findViewById(R.id.spn_my_spinner_localite_guichet);

        mySpinner.setItems(getResources().getStringArray(R.array.array_localite)); //this is important, you must set it to set the item list
        mySpinner.setTitle("Sélectionner une localité"); //change title of spinner-dialog programmatically
        mySpinner.setExpandTint(R.color.jrspinner_color_default); //change expand icon tint programmatically

        mySpinner.setOnSelectMultipleListener(new JRSpinner.OnSelectMultipleListener() {
            @Override
            public void onMultipleSelected(List<Integer> selectedPosition) {
                //do what you want to selected position list
            }
        });


        // debut Gestion du champ date
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        dateAgrementCx_txt = (EditText) rootView.findViewById(R.id.input_txt_dateDebut_guichet);
        dateAgrementCx_txt.setInputType(InputType.TYPE_NULL);
        dateAgrementCx_txt.requestFocus();

       // findViewsById();


        setDateTimeField();  */
        //******fin gestion du champ date

        /* caisseCard = (CardView) rootView.findViewById(R.id.caisse_card);
        majOFCard = (CardView) rootView.findViewById(R.id.majOF_card);
        userCard = (CardView) rootView.findViewById(R.id.user_card);
        preParamCard = (CardView) rootView.findViewById(R.id.preParam_card);
        ownInfoCard = (CardView) rootView.findViewById(R.id.ownInfo_card);

        caisseCard.setOnClickListener(this);
        majOFCard.setOnClickListener(this);
        userCard.setOnClickListener(this);
        preParamCard.setOnClickListener(this);
        ownInfoCard.setOnClickListener(this);*/


        // Inflate the layout for this fragment
        return rootView;
    }

    /*private void findViewsById() {
        dateAgrementCx_txt = (EditText) findViewById(R.id.txt_dateAgrement);
        dateAgrementCx_txt.setInputType(InputType.TYPE_NULL);
        dateAgrementCx_txt.requestFocus();


    }*/

    private void setDateTimeField() {
        dateAgrementCx_txt.setOnClickListener(this);
        dateAgrementCx_txt.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();
        dateAgrementCx_PickerDialog = new DatePickerDialog(getActivity(), new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateAgrementCx_txt.setText(dateFormatter.format(newDate.getTime()));
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        Intent i;

        if(v == dateAgrementCx_txt) {
            dateAgrementCx_PickerDialog.show();
        } /* else if(view == toDateEtxt) {
            toDatePickerDialog.show();
        }*/

        switch (v.getId()){

            case R.id.majOF_card :

                AppCompatActivity activityMajOf = (AppCompatActivity) v.getContext();
                Fragment myFragmentMajOF = new UpdateOFFragment();
                activityMajOf.getSupportFragmentManager().beginTransaction().replace(((ViewGroup)getView().getParent()).getId(), myFragmentMajOF).addToBackStack(null).commit();

                break;
            case R.id.caisse_card :
                i = new Intent(getActivity(), CaisseActivity.class);
                startActivity(i);
                break;
            case R.id.user_card :
                AppCompatActivity activityUserCard = (AppCompatActivity) v.getContext();
                Fragment myFragment = new FriendsFragment();
                activityUserCard.getSupportFragmentManager().beginTransaction().replace(((ViewGroup)getView().getParent()).getId(), myFragment).addToBackStack(null).commit();

                break;
            case R.id.preParam_card :
                i = new Intent(getActivity(), PrivacyPolicyActivity.class);
                startActivity(i);
                break;
            case R.id.param_piece_frais_of_card:
                i = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(i);
                break;

        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}