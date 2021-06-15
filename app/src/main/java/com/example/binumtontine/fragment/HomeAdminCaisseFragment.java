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
import com.example.binumtontine.activity.PreParametrageOFActivity;
import com.example.binumtontine.activity.UserGuichetActivity;
import com.example.binumtontine.activity.UsersCaisseActivity;
import com.example.binumtontine.activity.parametrageGuichet.ListGuichetActivity;
import com.example.binumtontine.activity.parametreGenerauxCx.ListEtapeDemandeCreditCx;
import com.example.binumtontine.activity.parametreGenerauxCx.ListObjetCreditCx;
import com.example.binumtontine.activity.parametreGenerauxCx.ListStatutEtapeCreditCx;
import com.example.binumtontine.activity.parametreGenerauxCx.ListTypeMembrePFActivity;
import com.example.binumtontine.activity.parametreGenerauxCx.TypeMembreCxActivity;
import com.example.binumtontine.activity.parametreGenerauxCx.comiteDeCredit.ListComiteCredit;
import com.example.binumtontine.activity.parametreGenerauxCx.membreComiteCredit.ListMembreComiteCredit;
import com.example.binumtontine.activity.parametreGenerauxCx.operationExterne.ListOperationExterneCx;
import com.example.binumtontine.activity.parametreGenerauxOF.operationExternes.ListOperationExterneOF;
import com.example.binumtontine.activity.pdf.ItextPDF;
import com.example.binumtontine.activity.pdf.PDFWriterDemo;
import com.example.binumtontine.activity.pdf.PdfActivity;
import com.example.binumtontine.activity.pdf.RetraitEavPDFWriter;


public class HomeAdminCaisseFragment extends Fragment implements View.OnClickListener{
    private  CardView guichetCard;
    private  CardView paramGeneralCard;
    private  CardView usagerCard;
    private  CardView paramProduitCard;
    private  CardView infoCaisseCard;
    private  CardView paramTypeMembreCard;

    private  CardView objetCreditCard;
    private  CardView etapesDemandeCreditCard;
    private  CardView statutEtapesDemandeCreditCard;
    private  CardView operationExterneCard;
    private  CardView comiteCreditCard;
    private  CardView membreComiteCreditCard;


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
        paramTypeMembreCard = (CardView) rootView.findViewById(R.id.paramTypeMembre_card);

        objetCreditCard = (CardView) rootView.findViewById(R.id.objet_credit_cx_card);
        etapesDemandeCreditCard = (CardView) rootView.findViewById(R.id.etapes_demande_credit_cx_card);
        statutEtapesDemandeCreditCard = (CardView) rootView.findViewById(R.id.statut_etape_demande_credit_cx_card);
        operationExterneCard = (CardView) rootView.findViewById(R.id.operation_externe_cx_card);
        comiteCreditCard = (CardView) rootView.findViewById(R.id.comite_credit_card);
        membreComiteCreditCard = (CardView) rootView.findViewById(R.id.membre_comite_credit_card);

        guichetCard.setOnClickListener(this);
        paramProduitCard.setOnClickListener(this);
        usagerCard.setOnClickListener(this);
        paramGeneralCard.setOnClickListener(this);
        infoCaisseCard.setOnClickListener(this);
        paramTypeMembreCard.setOnClickListener(this);

        objetCreditCard.setOnClickListener(this);
        etapesDemandeCreditCard.setOnClickListener(this);
        statutEtapesDemandeCreditCard.setOnClickListener(this);
        operationExterneCard.setOnClickListener(this);
        comiteCreditCard.setOnClickListener(this);
        membreComiteCreditCard.setOnClickListener(this);


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
                UserGuichetActivity.profilCaisseOrGuichet="caisse"; //A revoir car il faut éviter l'effet de board
               // i = new Intent(getActivity(), CreateUserGuichet.class);
                startActivity(i);
                break;
            case R.id.paramGeneral_card :
               /* AppCompatActivity activityUserCard = (AppCompatActivity) v.getContext();
                Fragment myFragment = new FriendsFragment();
                activityUserCard.getSupportFragmentManager().beginTransaction().replace(((ViewGroup)getView().getParent()).getId(), myFragment).addToBackStack(null).commit();*/
              //  i = new Intent(getActivity(), GuichetActivity.class);
                i = new Intent(getActivity(), ListTypeMembrePFActivity.class);

                // i = new Intent(getActivity(), CreateUserGuichet.class);
                startActivity(i);
                break;
            case R.id.preParam_card :
                i = new Intent(getActivity(), PreParametrageOFActivity.class);
                startActivity(i);
                break;
            case R.id.param_guichet_card:
                i = new Intent(getActivity(), ListGuichetActivity.class);
                startActivity(i);
                break;
            case R.id.paramTypeMembre_card:
                i = new Intent(getActivity(), TypeMembreCxActivity.class);
                startActivity(i);
                break;

            case R.id.objet_credit_cx_card:
                i = new Intent(getActivity(), ListObjetCreditCx.class);
//                i = new Intent(getActivity(), PdfActivity.class);
//                i = new Intent(getActivity(), PDFWriterDemo.class);
//                i = new Intent(getActivity(), RetraitEavPDFWriter.class);
//                i = new Intent(getActivity(), ItextPDF.class);
                startActivity(i);
                break;
            case R.id.etapes_demande_credit_cx_card:
                i = new Intent(getActivity(), ListEtapeDemandeCreditCx.class);
                startActivity(i);
                break;
            case R.id.statut_etape_demande_credit_cx_card:
                i = new Intent(getActivity(), ListStatutEtapeCreditCx.class);
                startActivity(i);
                break;
            case R.id.operation_externe_cx_card:
                i = new Intent(getActivity(), ListOperationExterneCx.class);
                startActivity(i);
                break;
            case R.id.comite_credit_card:
                i = new Intent(getActivity(), ListComiteCredit.class);
                startActivity(i);
                break;
            case R.id.membre_comite_credit_card:
                i = new Intent(getActivity(), ListMembreComiteCredit.class);
                startActivity(i);
                break;

        }

    }
}