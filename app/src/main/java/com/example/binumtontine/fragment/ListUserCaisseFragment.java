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
import com.example.binumtontine.activity.AboutUsActivity;
import com.example.binumtontine.activity.CaisseActivity;
import com.example.binumtontine.activity.PreParametrageOFActivity;


public class ListUserCaisseFragment extends Fragment implements View.OnClickListener{
    private  CardView caisseCard;
    private  CardView preParamCard;
    private  CardView userCard;
    private  CardView majOFCard;
    private  CardView ownInfoCard;

    public ListUserCaisseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_user_caisse, container, false);
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
                i = new Intent(getActivity(), PreParametrageOFActivity.class);
                startActivity(i);
                break;
            case R.id.param_piece_frais_of_card:
                i = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(i);
                break;

        }

    }
}