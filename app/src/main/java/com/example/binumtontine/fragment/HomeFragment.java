package com.example.binumtontine.fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment; //import android.support.v4.app.Fragment;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.CaisseActivity;
import com.example.binumtontine.activity.PreParametrageOFActivity;
import com.example.binumtontine.activity.parametreGenerauxOF.ListEtapesDemandesCreditOF;
import com.example.binumtontine.activity.parametreGenerauxOF.ListObjetCredit;
import com.example.binumtontine.activity.parametreGenerauxOF.ListStatutEtapesDemandesCreditOF;
import com.example.binumtontine.activity.parametreGenerauxOF.ParametreGenerauxOFActivity;
import com.example.binumtontine.activity.UpdateOrganeFaitier;
import com.example.binumtontine.activity.UserActivity;
import com.example.binumtontine.activity.parametreGenerauxOF.TypeMembreActivity;
import com.example.binumtontine.activity.parametreGenerauxOF.operationExternes.ListOperationExterneOF;


public class HomeFragment extends Fragment implements View.OnClickListener{
    private  CardView caisseCard;
    private  CardView preParamCard;
    private  CardView userCard;
    private  CardView majOFCard;
    private  CardView ownInfoCard;
    private  CardView typeMembreCard;
    private  CardView objetCreditCard;
    private  CardView etapesDemandeCreditCard;
    private  CardView statutEtapesDemandeCreditCard;
    private  CardView operationExterneCard;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        caisseCard = (CardView) rootView.findViewById(R.id.caisse_card);
        majOFCard = (CardView) rootView.findViewById(R.id.majOF_card);
        userCard = (CardView) rootView.findViewById(R.id.user_card);
        preParamCard = (CardView) rootView.findViewById(R.id.preParam_card);
        ownInfoCard = (CardView) rootView.findViewById(R.id.param_piece_frais_of_card);
        typeMembreCard = (CardView) rootView.findViewById(R.id.typeMembre_card);
        objetCreditCard = (CardView) rootView.findViewById(R.id.objet_credit_card);
        etapesDemandeCreditCard = (CardView) rootView.findViewById(R.id.etapes_demande_credit_of_card);
        statutEtapesDemandeCreditCard = (CardView) rootView.findViewById(R.id.statut_etape_demande_credit_card);
        operationExterneCard = (CardView) rootView.findViewById(R.id.operation_externe_card);

        caisseCard.setOnClickListener(this);
        majOFCard.setOnClickListener(this);
        userCard.setOnClickListener(this);
        preParamCard.setOnClickListener(this);
        ownInfoCard.setOnClickListener(this);
        typeMembreCard.setOnClickListener(this);
        objetCreditCard.setOnClickListener(this);
        etapesDemandeCreditCard.setOnClickListener(this);
        statutEtapesDemandeCreditCard.setOnClickListener(this);
        operationExterneCard.setOnClickListener(this);


        // Inflate the layout for this fragment
        return rootView;
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
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.majOF_card :
                i = new Intent(getActivity(), UpdateOrganeFaitier.class);
                startActivity(i);

                /* AppCompatActivity activityMajOf = (AppCompatActivity) v.getContext();
                Fragment myFragmentMajOF = new UpdateOFFragment();
                activityMajOf.getSupportFragmentManager().beginTransaction().replace(((ViewGroup)getView().getParent()).getId(), myFragmentMajOF).addToBackStack(null).commit(); */

                break;
            case R.id.caisse_card :
                i = new Intent(getActivity(), CaisseActivity.class);
                startActivity(i);
                break;
            case R.id.user_card :
               /* AppCompatActivity activityUserCard = (AppCompatActivity) v.getContext();
                Fragment myFragment = new FriendsFragment();
                activityUserCard.getSupportFragmentManager().beginTransaction().replace(((ViewGroup)getView().getParent()).getId(), myFragment).addToBackStack(null).commit();*/
               /* i = new Intent(getActivity(), UsersCaisseActivity.class);
                startActivity(i); */
                i = new Intent(getActivity(), UserActivity.class);
                startActivity(i);
                break;
            case R.id.preParam_card :
                i = new Intent(getActivity(), PreParametrageOFActivity.class);
                startActivity(i);
                break;
           /* case R.id.ownInfo_card :
                i = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(i);
                break;*/
            case R.id.param_piece_frais_of_card:
                i = new Intent(getActivity(), ParametreGenerauxOFActivity.class);
                startActivity(i);
                break;
            case R.id.typeMembre_card:
                i = new Intent(getActivity(), TypeMembreActivity.class);
                startActivity(i);
                break;
            case R.id.objet_credit_card:
                i = new Intent(getActivity(), ListObjetCredit.class);
                startActivity(i);
                break;
            case R.id.etapes_demande_credit_of_card:
                i = new Intent(getActivity(), ListEtapesDemandesCreditOF.class);
                startActivity(i);
                break;
            case R.id.statut_etape_demande_credit_card:
                i = new Intent(getActivity(), ListStatutEtapesDemandesCreditOF.class);
                startActivity(i);
                break;

            case R.id.operation_externe_card:
                i = new Intent(getActivity(), ListOperationExterneOF.class);
                startActivity(i);
                break;

        }

    }
}