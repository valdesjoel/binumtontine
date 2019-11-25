package com.example.binumtontine.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.CaisseActivity;
import com.example.binumtontine.activity.GuichetNewActivity;
import com.example.binumtontine.activity.PrivacyPolicyActivity;
import com.example.binumtontine.activity.UserGuichetActivity;
import com.example.binumtontine.activity.UsersCaisseActivity;
import com.example.binumtontine.activity.parametrageGuichet.ListGuichetActivity;
import com.example.binumtontine.activity.parametreGenerauxCx.ParametreGenerauxCxActivity;


public class HomeAdminCaisseFragment extends Fragment implements View.OnClickListener{
    private  CardView guichetCard;
    private  CardView paramGeneralCard;
    private  CardView usagerCard;
    private  CardView paramProduitCard;
    private  CardView infoCaisseCard;

    public HomeAdminCaisseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_admin_caisse, container, false);
        guichetCard = (CardView) rootView.findViewById(R.id.guichet_card);
        paramProduitCard = (CardView) rootView.findViewById(R.id.paramProduit_card);
        usagerCard = (CardView) rootView.findViewById(R.id.usager_card);
        paramGeneralCard = (CardView) rootView.findViewById(R.id.paramGeneral_card);
        infoCaisseCard = (CardView) rootView.findViewById(R.id.param_guichet_card);

        guichetCard.setOnClickListener(this);
        paramProduitCard.setOnClickListener(this);
        usagerCard.setOnClickListener(this);
        paramGeneralCard.setOnClickListener(this);
        infoCaisseCard.setOnClickListener(this);


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
            case R.id.paramProduit_card :

                AppCompatActivity activityProduitCx = (AppCompatActivity) v.getContext();
                Fragment myFragmentProduitCx = new ProduitCaisseFragment();
                activityProduitCx.getSupportFragmentManager().beginTransaction().replace(((ViewGroup)getView().getParent()).getId(), myFragmentProduitCx).addToBackStack(null).commit();

               /* i = new Intent(getActivity(), ParametrageProduitCaisseActivity.class);
                startActivity(i);*/
                break;
            case R.id.guichet_card :
               // i = new Intent(getActivity(), GuichetActivity.class);
                i = new Intent(getActivity(), GuichetNewActivity.class);
               // i = new Intent(getActivity(), CreateGuichet.class);
                startActivity(i);
                break;
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
               /* AppCompatActivity activityUserCard = (AppCompatActivity) v.getContext();
                Fragment myFragment = new FriendsFragment();
                activityUserCard.getSupportFragmentManager().beginTransaction().replace(((ViewGroup)getView().getParent()).getId(), myFragment).addToBackStack(null).commit();*/
                i = new Intent(getActivity(), UsersCaisseActivity.class);

                startActivity(i);
                break;
            case R.id.usager_card :
               /* AppCompatActivity activityUserCard = (AppCompatActivity) v.getContext();
                Fragment myFragment = new FriendsFragment();
                activityUserCard.getSupportFragmentManager().beginTransaction().replace(((ViewGroup)getView().getParent()).getId(), myFragment).addToBackStack(null).commit();*/
                 i = new Intent(getActivity(), UserGuichetActivity.class);

               // i = new Intent(getActivity(), CreateUserGuichet.class);
                startActivity(i);
                break;
            case R.id.paramGeneral_card :
               /* AppCompatActivity activityUserCard = (AppCompatActivity) v.getContext();
                Fragment myFragment = new FriendsFragment();
                activityUserCard.getSupportFragmentManager().beginTransaction().replace(((ViewGroup)getView().getParent()).getId(), myFragment).addToBackStack(null).commit();*/
              //  i = new Intent(getActivity(), GuichetActivity.class);
                i = new Intent(getActivity(), ParametreGenerauxCxActivity.class);

                // i = new Intent(getActivity(), CreateUserGuichet.class);
                startActivity(i);
                break;
            case R.id.preParam_card :
                i = new Intent(getActivity(), PrivacyPolicyActivity.class);
                startActivity(i);
                break;
            case R.id.param_guichet_card:
                i = new Intent(getActivity(), ListGuichetActivity.class);
                startActivity(i);
                break;

        }

    }
}