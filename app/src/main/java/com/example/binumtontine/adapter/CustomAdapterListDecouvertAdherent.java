package com.example.binumtontine.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.adherent.Adherent;
import com.example.binumtontine.activity.adherent.ComiteCredit;
import com.example.binumtontine.activity.adherent.ComptesAdherent;
import com.example.binumtontine.activity.adherent.ConsulterCompte;
import com.example.binumtontine.activity.adherent.ControleOperationCompteCourant;
import com.example.binumtontine.activity.adherent.DeblocageCredit;
import com.example.binumtontine.activity.adherent.DecaissementCredit;
import com.example.binumtontine.activity.adherent.HistoriqueCpteCourant;
import com.example.binumtontine.activity.adherent.HistoriqueEAV;
import com.example.binumtontine.activity.adherent.ListCompteAdherentActivity_New;
import com.example.binumtontine.activity.adherent.ListCompteDecouvertAdherentActivity;
import com.example.binumtontine.activity.adherent.OperationCompteCourant;
import com.example.binumtontine.activity.adherent.OperationEAP;
import com.example.binumtontine.activity.adherent.OperationEAT;
import com.example.binumtontine.activity.adherent.OperationEAV;
import com.example.binumtontine.activity.adherent.Record;
import com.example.binumtontine.activity.adherent.ValidationOperationCompteCourant;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

//  import com.example.binumtontine.activity.adherent.MyList;


/**
 * Created by Valdès on 13/02/20.
 * It allows to customize List Comptes Adherent
 */

public class CustomAdapterListDecouvertAdherent extends RecyclerView.Adapter<CustomAdapterListDecouvertAdherent.ViewHolder> {

    //private List<MyList> list;
    private List<ComptesAdherent> list;
    private Context mCtx;
    private static final String KEY_ADHERENT_ID = "IpMembre";
    private static final String KEY_ADHERENT_NOM = "AdNom";
    private static final String KEY_ADHERENT_PRENOM = "AdPrenom";
    private static final String KEY_ADHERENT_NUM_MANUEL = "AdNumManuel";
    private static final String KEY_ADHERENT_CODE = "AdCode";

    private static final String KEY_ADHERENT = "ADHERENT";
    //private static final String KEY_ADHERENT_NUM_DOSSIER = "IpMembre";


    /*BEGIN */

    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_DATA_EAV = "EAV";
    private static final String KEY_DATA_EAT = "EAT";
    private static final String KEY_DATA_EAP = "EAP";
    private static final String KEY_COMPTE_ID = "Numero";
    private static final String KEY_LIBELLE_PRODUIT = "Libelle";
    private static final String KEY_NUM_DOSSIER_COMPTE = "NumDossier";
    private static final String KEY_MONTANT_COMPTE = "MtSolde";
    private static final String KEY_DATE_H_CREE = "DateHCree";
    private static final String KEY_TAUX = "Taux";
    private static final String KEY_HEADER_ACTIVITY_CONSULTER_COMPTE = "tvHeaderActivityConsulterCompte";
    private static final String KEY_HEADER_LAYOUT_CONSULTER_COMPTE = "tvHeaderLayoutConsulterCompte";

    private static final String KEY_TYPE_COMPTE = "TypeCompte";
    private static final String KEY_TYPE_OPERATION = "TypeOperation";
    private Adherent adherent;
    private String adherentId;
    private Intent intent;
    private NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
    private String CgDevise ="A";
    private String headerActivity = "CONSULTATION DE COMPTE";
    private String headerLayout = "Consultez votre compte";
    /* END */

    /*start*/
    private ProgressDialog pDialog;
    private List<Record> listOperationCompteAdherent;

    private String compteId;
    private static final String KEY_CODE_COMPTE_EAV = "OcCodeCompteEAV";
    private static final String KEY_TYPE_OPERATION_EAV = "OcTypeOperation";
    private static final String KEY_DATE_OPERATION = "OcDateOperation";
    private static final String KEY_OC_MONTANT = "OcMontant";
    private static final String KEY_OC_NEW_SOLDE = "OcNewSolde";
    /*end*/

    //public CustomAdapterListAdherent(List<MyList> list, Context mCtx) {
    public CustomAdapterListDecouvertAdherent(List<ComptesAdherent> list, Context mCtx) {
        this.list = list;
        this.mCtx = mCtx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_items_compte_adherent_new, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CustomAdapterListDecouvertAdherent.ViewHolder holder, int position) {
        //MyList myList = list.get(position);
        final ComptesAdherent myList = list.get(position);
        holder.textViewId.setText(myList.getNumero_compte()+"");
        holder.tvTypeCompte.setText(myList.getType_compte());
        holder.tvTaux.setText(myList.getTaux_compte());
        if (myList.getLibelleProduit().equals("D")){
            holder.tvLibelleProduit.setText("En attente de controle");
            holder.tvLibelleProduit.setBackgroundColor(Color.parseColor("#FFFF00"));
        }else if (myList.getLibelleProduit().equals("A")){
            holder.tvLibelleProduit.setText("Demande Autorisée");
            holder.tvLibelleProduit.setBackgroundColor(Color.GREEN);
        }else if (myList.getLibelleProduit().equals("R")){
            holder.tvLibelleProduit.setText("Demande Refusée");
            holder.tvLibelleProduit.setBackgroundColor(Color.RED);
        }else if (myList.getLibelleProduit().equals("C")){
            holder.tvLibelleProduit.setText("En attente du comité de crédit");
            holder.tvLibelleProduit.setBackgroundColor(Color.parseColor("#42f5ef"));
        }else{
            holder.tvLibelleProduit.setText("Statut inconnu");

            holder.tvLibelleProduit.setBackgroundColor(Color.GRAY);
        }

        holder.tvNumDossier.setText(myList.getNumeroDossier());
        holder.tvDateCreation.setText(myList.getDateHCree());
        holder.tvSolde.setText(myList.getMontantSolde());



//        if ((holder.tvTypeCompte.getText()).equals("EAV")){
//            intent = new Intent(mCtx,
//                    OperationEAV.class);
//        }else if ((holder.tvTypeCompte.getText()).equals("EAT")){
//            intent = new Intent(mCtx,
//                    OperationEAT.class);
//        }else{
//            intent = new Intent(mCtx,
//                    OperationEAP.class);
//        }
        /*
        intent = new Intent(mCtx,
                OperationEAV.class);

        intent.putExtra(KEY_COMPTE_ID, myList.getNumero_compte());
        intent.putExtra(KEY_MONTANT_COMPTE, myList.getMontantSolde());
        intent.putExtra(KEY_LIBELLE_PRODUIT, myList.getLibelleProduit());
        intent.putExtra(KEY_DATE_H_CREE, myList.getDateHCree());
        intent.putExtra(KEY_TAUX, myList.getTaux_compte());
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_ADHERENT, (Serializable) ListCompteAdherentActivity_New.adherent);
        // bundle.putSerializable(KEY_ADHERENT, adherent);
        intent.putExtras(bundle); */


//pour mettre un évènement d'écoute lorsqu'on clique sur une ligne (un compte)
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Check for network connectivity
//                if (CheckNetworkStatus.isNetworkAvailable(mCtx)) {
//                   /* String movieId = ((TextView) view.findViewById(R.id.movieId))
//                            .getText().toString();
//                    Intent intent = new Intent(mCtx,
//                            CaisseActivity.class);
//                    intent.putExtra(KEY_CAISSE_ID, movieId);
//                    //startActivityForResult(intent, 20);
//
//                    startActivity(intent); */
//                    //handle menu0 click
//                    Intent i = new Intent(mCtx, ListCompteAdherentActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable(KEY_ADHERENT, (Serializable) myList);
//                    // bundle.putSerializable(KEY_ADHERENT, adherent);
//                    i.putExtras(bundle);
//                    i.putExtra(KEY_ADHERENT_ID, holder.textViewId.getText());
//                    ((Activity) mCtx).startActivityForResult(i, 20);
//
//
//                    //mCtx.startActivity(i);
////                    Toast.makeText(mCtx,
////                            "Liste des comptes de l'adhérent en construction!!!",
////                            Toast.LENGTH_LONG).show();
//
//                } else {
//                    Toast.makeText(mCtx,
//                            "Unable to connect to internet",
//                            Toast.LENGTH_LONG).show();
//
//                }
//            }
//        });


        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (myList.getType_compte().equals("COMPTE COURANT")){
                if (myList.getType_compte().equals("DECOUVERT SIMPLE")|| myList.getType_compte().equals("AVANCE SPECIALE")){
                    intent = new Intent(mCtx,
                            OperationCompteCourant.class);
                    intent.putExtra(KEY_COMPTE_ID, myList.getNumero_compte()+"");

                    intent.putExtra(KEY_MONTANT_COMPTE, myList.getMontantSolde());
                    intent.putExtra(KEY_LIBELLE_PRODUIT, myList.getLibelleProduit());
                    intent.putExtra(KEY_DATE_H_CREE, myList.getDateHCree());
                    intent.putExtra(KEY_TAUX, myList.getTaux_compte());
                    intent.putExtra(KEY_TYPE_COMPTE, myList.getType_compte());

                    Bundle bundle = new Bundle();
                    bundle.putSerializable(KEY_ADHERENT, (Serializable) ListCompteAdherentActivity_New.adherent);
                    // bundle.putSerializable(KEY_ADHERENT, adherent);
                    intent.putExtras(bundle);
                }else if (myList.getType_compte().equals("EAV")){
                    intent = new Intent(mCtx,
                            OperationEAV.class);
                    intent.putExtra(KEY_COMPTE_ID, myList.getNumero_compte()+"");

                    intent.putExtra(KEY_MONTANT_COMPTE, myList.getMontantSolde());
                    intent.putExtra(KEY_LIBELLE_PRODUIT, myList.getLibelleProduit());
                    intent.putExtra(KEY_DATE_H_CREE, myList.getDateHCree());
                    intent.putExtra(KEY_TAUX, myList.getTaux_compte());
                    intent.putExtra(KEY_TYPE_COMPTE, myList.getType_compte());

                    Bundle bundle = new Bundle();
                    bundle.putSerializable(KEY_ADHERENT, (Serializable) ListCompteAdherentActivity_New.adherent);
                    // bundle.putSerializable(KEY_ADHERENT, adherent);
                    intent.putExtras(bundle);
                }



                //will show popup menu here
                //creating a popup menu
                //PopupMenu popup = new PopupMenu(mCtx, holder.buttonViewOption);
                PopupMenu popup = new PopupMenu(mCtx, holder.buttonViewOption); // i made holer final
                //inflating menu from xml resource
                popup.inflate(R.menu.menu_body_decouvert_adherent);
                if (myList.getLibelleProduit().equals("D")){
                    popup.getMenu().findItem(R.id.action_etape_suivante).setTitle("Etape suivante: Contrôle");
                }else if (myList.getLibelleProduit().equals("C")){
                    popup.getMenu().findItem(R.id.action_etape_suivante).setTitle("Etape suivante: Décision Comité");
                }else if (myList.getLibelleProduit().equals("A")){
                    popup.getMenu().findItem(R.id.action_etape_suivante).setTitle("En cours: Remboursement");
                    popup.getMenu().findItem(R.id.action_etape_suivante).setEnabled(false);
                }else if (myList.getLibelleProduit().equals("R")){
                    popup.getMenu().findItem(R.id.action_etape_suivante).setTitle("Etape suivante: Aucune");
                    popup.getMenu().findItem(R.id.action_etape_suivante).setEnabled(false);
                }
//
//                if ((holder.tvTypeCompte.getText()).equals("EAV")){
//                    popup.getMenu().findItem(R.id.menu_group).getSubMenu().setGroupVisible (R.id.eav_group, true);
//                    popup.getMenu().findItem(R.id.menu_group).getSubMenu().setGroupVisible (R.id.eap_group, false);
//                    popup.getMenu().findItem(R.id.menu_group).getSubMenu().setGroupVisible (R.id.credit_group, false);
//                    popup.getMenu().findItem(R.id.menu_consulter_compte).setVisible(true);
//                    popup.getMenu().findItem(R.id.menu_decouvert).setVisible(false);
//                    popup.getMenu().findItem(R.id.menu_avance_speciale).setVisible(false);
//                    popup.getMenu().findItem(R.id.menu_avance_permanent).setVisible(false);
////                    popup.getMenu().findItem(R.id.eav_group).setVisible(true);
////                    popup.getMenu().findItem(R.id.eap_group).setVisible(false);
//
//                }else if((holder.tvTypeCompte.getText()).equals("EAP")){
//
//                    popup.getMenu().findItem(R.id.menu_group).getSubMenu().setGroupVisible (R.id.eav_group, false);
//                    popup.getMenu().findItem(R.id.menu_group).getSubMenu().setGroupVisible (R.id.eap_group, true);
//                    popup.getMenu().findItem(R.id.menu_group).getSubMenu().setGroupVisible (R.id.credit_group, false);
//
//
////                    popup.getMenu().findItem(R.id.eav_group).setVisible(false);
////                    popup.getMenu().findItem(R.id.eap_group).setVisible(true);
//                }else if((holder.tvTypeCompte.getText()).equals("DECOUVERT")){
//
//                    popup.getMenu().findItem(R.id.menu_group).getSubMenu().setGroupVisible (R.id.eav_group, true);
//                    popup.getMenu().findItem(R.id.menu_group).getSubMenu().setGroupVisible (R.id.eap_group, false);
//                    popup.getMenu().findItem(R.id.menu_group).getSubMenu().setGroupVisible (R.id.credit_group, false);
//                    popup.getMenu().findItem(R.id.menu_consulter_compte).setVisible(true);
//                    popup.getMenu().findItem(R.id.menu_decouvert).setVisible(true);
//                    popup.getMenu().findItem(R.id.menu_avance_speciale).setVisible(true);
//                    popup.getMenu().findItem(R.id.menu_avance_permanent).setVisible(true);
//                    popup.getMenu().findItem(R.id.title_group_eav).setTitle("DECOUVERT");
//
//
////                    popup.getMenu().findItem(R.id.eav_group).setVisible(false);
////                    popup.getMenu().findItem(R.id.eap_group).setVisible(true);
//                }else if((holder.tvTypeCompte.getText()).equals("EAT")){
//
//
//                    popup.getMenu().findItem(R.id.menu_group).getSubMenu().setGroupVisible (R.id.eav_group, false);
//                    popup.getMenu().findItem(R.id.menu_group).getSubMenu().setGroupVisible (R.id.eap_group, true);
//                    popup.getMenu().findItem(R.id.menu_group).getSubMenu().setGroupVisible (R.id.credit_group, false);
////                    popup.getMenu().findItem(R.id.eav_group).setVisible(false);
////                    popup.getMenu().findItem(R.id.eap_group).setVisible(true);
//                    popup.getMenu().findItem(R.id.menu_payer_mise).setVisible(false);
//                    popup.getMenu().findItem(R.id.menu_historique_mise).setVisible(false);
//                }else if((holder.tvTypeCompte.getText()).equals("CREDIT")){
//
//
//                    popup.getMenu().findItem(R.id.menu_group).getSubMenu().setGroupVisible (R.id.eav_group, false);
//                    popup.getMenu().findItem(R.id.menu_group).getSubMenu().setGroupVisible (R.id.eap_group, false);
//                    popup.getMenu().findItem(R.id.menu_group).getSubMenu().setGroupVisible (R.id.credit_group, true);
//                    popup.getMenu().findItem(R.id.menu_consulter_compte).setVisible(true);
//
////                    popup.getMenu().findItem(R.id.eav_group).setVisible(false);
////                    popup.getMenu().findItem(R.id.eap_group).setVisible(true);
////                    popup.getMenu().findItem(R.id.menu_payer_mise).setVisible(false);
////                    popup.getMenu().findItem(R.id.menu_historique_mise).setVisible(false);
//                }
               // popup.getMenu().findItem(R.id.next).setVisible(true);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {

//                            case R.id.menu0:
//                                //handle menu0 click
//                                  Intent i = new Intent(mCtx, GetPieceAdherent.class);
//                                i.putExtra(KEY_ADHERENT_ID, holder.textViewId.getText());
//                               // startActivityForResult(intent, 20);
//                                mCtx.startActivity(i);
//
//                                break;
                            case R.id.action_informations:
                                //handle menu1 click
//                                //startActivityForResult(intent, 20);
//                                intent.putExtra(KEY_TYPE_OPERATION, "depot");
//                                ((Activity) mCtx).startActivityForResult(intent, 20);


                                intent = new Intent(mCtx,
                                        ConsulterCompte.class);

                                intent.putExtra(KEY_COMPTE_ID, myList.getNumero_compte()+"");

                                intent.putExtra(KEY_MONTANT_COMPTE, myList.getMontantSolde());
                                intent.putExtra(KEY_LIBELLE_PRODUIT, myList.getLibelleProduit());
                                intent.putExtra(KEY_DATE_H_CREE, myList.getDateHCree());
                                intent.putExtra(KEY_TAUX, myList.getTaux_compte());
                                intent.putExtra(KEY_HEADER_ACTIVITY_CONSULTER_COMPTE, headerActivity);
                                intent.putExtra(KEY_HEADER_LAYOUT_CONSULTER_COMPTE, headerLayout);
                                intent.putExtra(KEY_TYPE_COMPTE, myList.getType_compte());

                                Bundle bundleConsulterCompte = new Bundle();
                                bundleConsulterCompte.putSerializable(KEY_ADHERENT, (Serializable) ListCompteAdherentActivity_New.adherent);
                                // bundle.putSerializable(KEY_ADHERENT, adherent);
                                intent.putExtras(bundleConsulterCompte);

                                ((Activity) mCtx).startActivityForResult(intent, 20);


                                break;
                            case R.id.action_etape_suivante:
                                //handle menu2 click

                                if (myList.getLibelleProduit().equals("D")){
                                    intent = new Intent(mCtx,
                                            ControleOperationCompteCourant.class);
                                }else if (myList.getLibelleProduit().equals("C")){

                                    intent = new Intent(mCtx,
                                            ValidationOperationCompteCourant.class);
                                }
                                if (myList.getType_compte().equals("DECOUVERT SIMPLE")){
                                    intent.putExtra(KEY_TYPE_OPERATION, "decouvert");
                                }else if (myList.getType_compte().equals("AVANCE SPECIALE")){
                                    intent.putExtra(KEY_TYPE_OPERATION, "avance_speciale");
                                }

//                                ((Activity) mCtx).startActivityForResult(intent, 20);
//
//                                Intent i = new Intent(mCtx, ListCompteDecouvertAdherentActivity.class);
//                                Bundle bundleDecouv = new Bundle();
//                                bundleDecouv.putSerializable(KEY_ADHERENT, (Serializable) myList);
//                                // bundle.putSerializable(KEY_ADHERENT, adherent);
//                                i.putExtras(bundleDecouv);
//                                i.putExtra(KEY_ADHERENT_ID, holder.textViewId.getText());
//                                ((Activity) mCtx).startActivityForResult(i, 20);



                                intent.putExtra(KEY_COMPTE_ID, myList.getNumero_compte()+"");

                                intent.putExtra(KEY_MONTANT_COMPTE, myList.getMontantSolde());
                                intent.putExtra(KEY_LIBELLE_PRODUIT, myList.getLibelleProduit());
                                intent.putExtra(KEY_DATE_H_CREE, myList.getDateHCree());
                                intent.putExtra(KEY_TAUX, myList.getTaux_compte());
                                intent.putExtra(KEY_TYPE_COMPTE, myList.getType_compte());

                                Bundle bundle = new Bundle();
                                bundle.putSerializable(KEY_ADHERENT, (Serializable) ListCompteAdherentActivity_New.adherent);
                                // bundle.putSerializable(KEY_ADHERENT, adherent);
                                intent.putExtras(bundle);
                                ((Activity) mCtx).startActivityForResult(intent, 20);
                                /*
                                Intent intentCreateEAV = new Intent(mCtx, CreateEAV.class);
                                intentCreateEAV.putExtra(KEY_ADHERENT_ID, myList.getAdNumero());
                                intentCreateEAV.putExtra(KEY_ADHERENT_NOM, myList.getAdNom());
                                intentCreateEAV.putExtra(KEY_ADHERENT_PRENOM, myList.getAdPrenom());
                                intentCreateEAV.putExtra(KEY_ADHERENT_NUM_MANUEL, myList.getAdNumManuel());
                                intentCreateEAV.putExtra(KEY_ADHERENT_CODE, myList.getAdCode());
                                ((Activity) mCtx).startActivityForResult(intentCreateEAV, 20);
                                */
                                //intentCreateEAV.putExtra(KEY_ADHERENT_NUM_DOSSIER, "");
                                // startActivityForResult(intent, 20);
                                //mCtx.startActivity(intentCreateEAV);

                                break;
                            case R.id.action_interets:
                                //handle menu2 click
//                                intent.putExtra(KEY_TYPE_OPERATION, "decouvert");
//                                ((Activity) mCtx).startActivityForResult(intent, 20);

                                Intent i = new Intent(mCtx, ListCompteDecouvertAdherentActivity.class);
                                Bundle bundleDecouv = new Bundle();
                                bundleDecouv.putSerializable(KEY_ADHERENT, (Serializable) myList);
                                // bundle.putSerializable(KEY_ADHERENT, adherent);
                                i.putExtras(bundleDecouv);
                                i.putExtra(KEY_ADHERENT_ID, holder.textViewId.getText());
                                ((Activity) mCtx).startActivityForResult(i, 20);

                                /*
                                Intent intentCreateEAV = new Intent(mCtx, CreateEAV.class);
                                intentCreateEAV.putExtra(KEY_ADHERENT_ID, myList.getAdNumero());
                                intentCreateEAV.putExtra(KEY_ADHERENT_NOM, myList.getAdNom());
                                intentCreateEAV.putExtra(KEY_ADHERENT_PRENOM, myList.getAdPrenom());
                                intentCreateEAV.putExtra(KEY_ADHERENT_NUM_MANUEL, myList.getAdNumManuel());
                                intentCreateEAV.putExtra(KEY_ADHERENT_CODE, myList.getAdCode());
                                ((Activity) mCtx).startActivityForResult(intentCreateEAV, 20);
                                */
                                //intentCreateEAV.putExtra(KEY_ADHERENT_NUM_DOSSIER, "");
                                // startActivityForResult(intent, 20);
                                //mCtx.startActivity(intentCreateEAV);

                                break;
                            case R.id.action_penalites:
                                //handle menu3 click
                                if (myList.getType_compte().equals("COMPTE COURANT")){

                                    intent = new Intent(mCtx,
                                            HistoriqueCpteCourant.class);
                                    intent.putExtra(KEY_COMPTE_ID, myList.getNumero_compte()+"");
                                }else if (myList.getType_compte().equals("EAV")){

                                    intent = new Intent(mCtx,
                                            HistoriqueEAV.class);
                                    intent.putExtra(KEY_COMPTE_ID, myList.getNumero_compte()+"");
                                }
//                                Log.d("*********************",myList.getNumero_compte()+"");
//                                Toast.makeText(mCtx,
//                                        myList.getNumero_compte(),
//                                        Toast.LENGTH_LONG).show();
                                ((Activity) mCtx).startActivityForResult(intent, 20);
                                break;
                            case R.id.action_remboursements:
                                //handle menu3 click
                                /*
                                Intent intentCreateEAT = new Intent(mCtx, CreateEAT.class);
                                intentCreateEAT.putExtra(KEY_ADHERENT_ID, myList.getAdNumero());
                                intentCreateEAT.putExtra(KEY_ADHERENT_NOM, myList.getAdNom());
                                intentCreateEAT.putExtra(KEY_ADHERENT_PRENOM, myList.getAdPrenom());
                                intentCreateEAT.putExtra(KEY_ADHERENT_NUM_MANUEL, myList.getAdNumManuel());
                                intentCreateEAT.putExtra(KEY_ADHERENT_CODE, myList.getAdCode());
                                ((Activity) mCtx).startActivityForResult(intentCreateEAT, 20);
                                */
                                //intentCreateEAV.putExtra(KEY_ADHERENT_NUM_DOSSIER, "");
                                // startActivityForResult(intent, 20);
                               // mCtx.startActivity(intentCreateEAT);

                                break;
//                            case R.id.menu_frais:
//                                //handle menu3 click
//                                /*
//                                Intent intentCreateEAP = new Intent(mCtx, CreateEAP.class);
//                                intentCreateEAP.putExtra(KEY_ADHERENT_ID, myList.getAdNumero());
//                                intentCreateEAP.putExtra(KEY_ADHERENT_NOM, myList.getAdNom());
//                                intentCreateEAP.putExtra(KEY_ADHERENT_PRENOM, myList.getAdPrenom());
//                                intentCreateEAP.putExtra(KEY_ADHERENT_NUM_MANUEL, myList.getAdNumManuel());
//                                intentCreateEAP.putExtra(KEY_ADHERENT_CODE, myList.getAdCode());
//                                ((Activity) mCtx).startActivityForResult(intentCreateEAP, 20);
//                                */
//                                //intentCreateEAV.putExtra(KEY_ADHERENT_NUM_DOSSIER, "");
//                                // startActivityForResult(intent, 20);
//                               // mCtx.startActivity(intentCreateEAP);
//                                compteId= myList.getNumero_compte()+"";
//                                listOperationCompteAdherent = new ArrayList<>();
//
//                                break;
/*
                            case R.id.menu_payer_mise:
                                //handle menu3 click
                                intent = new Intent(mCtx,
                                        OperationEAP.class);

                                intent.putExtra(KEY_COMPTE_ID, myList.getNumero_compte()+"");

                                intent.putExtra(KEY_MONTANT_COMPTE, myList.getMontantSolde());
                                intent.putExtra(KEY_LIBELLE_PRODUIT, myList.getLibelleProduit());
                                intent.putExtra(KEY_DATE_H_CREE, myList.getDateHCree());
                                intent.putExtra(KEY_TAUX, myList.getTaux_compte());

                                Bundle bundle = new Bundle();
                                bundle.putSerializable(KEY_ADHERENT, (Serializable) ListCompteAdherentActivity_New.adherent);
                                // bundle.putSerializable(KEY_ADHERENT, adherent);
                                intent.putExtras(bundle);
                                ((Activity) mCtx).startActivityForResult(intent, 20);

                                break;
                            case R.id.menu_historique_mise:
                                //handle menu3 click


                                break;
                            case R.id.menu_consulter_compte:
                                //handle menu3 click


                                intent = new Intent(mCtx,
                                        ConsulterCompte.class);

                                intent.putExtra(KEY_COMPTE_ID, myList.getNumero_compte()+"");

                                intent.putExtra(KEY_MONTANT_COMPTE, myList.getMontantSolde());
                                intent.putExtra(KEY_LIBELLE_PRODUIT, myList.getLibelleProduit());
                                intent.putExtra(KEY_DATE_H_CREE, myList.getDateHCree());
                                intent.putExtra(KEY_TAUX, myList.getTaux_compte());
                                intent.putExtra(KEY_HEADER_ACTIVITY_CONSULTER_COMPTE, headerActivity);
                                intent.putExtra(KEY_HEADER_LAYOUT_CONSULTER_COMPTE, headerLayout);
                                intent.putExtra(KEY_TYPE_COMPTE, myList.getType_compte());

                                Bundle bundleConsulterCompte = new Bundle();
                                bundleConsulterCompte.putSerializable(KEY_ADHERENT, (Serializable) ListCompteAdherentActivity_New.adherent);
                                // bundle.putSerializable(KEY_ADHERENT, adherent);
                                intent.putExtras(bundleConsulterCompte);

                                ((Activity) mCtx).startActivityForResult(intent, 20);


                                break;
                            case R.id.menu_cloture_anticipee:
                                //handle menu3 click


                                break;
                            case R.id.menu_cloture:
                                //handle menu3 click

                                intent = new Intent(mCtx,
                                        OperationEAT.class);

                                intent.putExtra(KEY_COMPTE_ID, myList.getNumero_compte()+"");

                                intent.putExtra(KEY_MONTANT_COMPTE, myList.getMontantSolde());
                                intent.putExtra(KEY_LIBELLE_PRODUIT, myList.getLibelleProduit());
                                intent.putExtra(KEY_DATE_H_CREE, myList.getDateHCree());
                                intent.putExtra(KEY_TAUX, myList.getTaux_compte());

                                Bundle bundleEAT = new Bundle();
                                bundleEAT.putSerializable(KEY_ADHERENT, (Serializable) ListCompteAdherentActivity_New.adherent);
                                // bundle.putSerializable(KEY_ADHERENT, adherent);
                                intent.putExtras(bundleEAT);

                            ((Activity) mCtx).startActivityForResult(intent, 20);

                                break;
                            case R.id.menu_comite_credit:
                                //handle menu3 click

                                intent = new Intent(mCtx,
                                        ComiteCredit.class);

                                intent.putExtra(KEY_COMPTE_ID, myList.getNumero_compte()+"");

                                intent.putExtra(KEY_MONTANT_COMPTE, myList.getMontantSolde());
                                intent.putExtra(KEY_LIBELLE_PRODUIT, myList.getLibelleProduit());
                                intent.putExtra(KEY_DATE_H_CREE, myList.getDateHCree());
                                intent.putExtra(KEY_TAUX, myList.getTaux_compte());

                                Bundle bundleComiteCredit = new Bundle();
                                bundleComiteCredit.putSerializable(KEY_ADHERENT, (Serializable) ListCompteAdherentActivity_New.adherent);
                                // bundle.putSerializable(KEY_ADHERENT, adherent);
                                intent.putExtras(bundleComiteCredit);

                            ((Activity) mCtx).startActivityForResult(intent, 20);

                                break;
                            case R.id.menu_deblocge_credit:
                                //handle menu3 click

                                intent = new Intent(mCtx,
                                        DeblocageCredit.class);

                                intent.putExtra(KEY_COMPTE_ID, myList.getNumero_compte()+"");

                                intent.putExtra(KEY_MONTANT_COMPTE, myList.getMontantSolde());
                                intent.putExtra(KEY_LIBELLE_PRODUIT, myList.getLibelleProduit());
                                intent.putExtra(KEY_DATE_H_CREE, myList.getDateHCree());
                                intent.putExtra(KEY_TAUX, myList.getTaux_compte());

                                Bundle bundleDeblocageCredit = new Bundle();
                                bundleDeblocageCredit.putSerializable(KEY_ADHERENT, (Serializable) ListCompteAdherentActivity_New.adherent);
                                // bundle.putSerializable(KEY_ADHERENT, adherent);
                                intent.putExtras(bundleDeblocageCredit);

                            ((Activity) mCtx).startActivityForResult(intent, 20);
                            case R.id.menu_decaissement_credit:
                                //handle menu3 click

                                intent = new Intent(mCtx,
                                        DecaissementCredit.class);

                                intent.putExtra(KEY_COMPTE_ID, myList.getNumero_compte()+"");

                                intent.putExtra(KEY_MONTANT_COMPTE, myList.getMontantSolde());
                                intent.putExtra(KEY_LIBELLE_PRODUIT, myList.getLibelleProduit());
                                intent.putExtra(KEY_DATE_H_CREE, myList.getDateHCree());
                                intent.putExtra(KEY_TAUX, myList.getTaux_compte());

                                Bundle bundleDecaissementCredit = new Bundle();
                                bundleDecaissementCredit.putSerializable(KEY_ADHERENT, (Serializable) ListCompteAdherentActivity_New.adherent);
                                // bundle.putSerializable(KEY_ADHERENT, adherent);
                                intent.putExtras(bundleDecaissementCredit);

                            ((Activity) mCtx).startActivityForResult(intent, 20);

                                break;
                                */
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();

            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView textViewId;
        public TextView textViewCode;
        public TextView tvSolde;
        public TextView tvTaux;
        public TextView tvLibelleProduit;
        public TextView tvTypeCompte;
        public TextView tvDateCreation;
        public TextView tvNumDossier;
        public Button btnClick;
        public TextView buttonViewOption;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewId = (TextView) itemView.findViewById(R.id.compteId);
            tvTypeCompte = (TextView) itemView.findViewById(R.id.typeCompte);
            tvTaux = (TextView) itemView.findViewById(R.id.tauxCompte);
            tvLibelleProduit = (TextView) itemView.findViewById(R.id.libelleProduit);
            tvNumDossier = (TextView) itemView.findViewById(R.id.compteNumDossier);
            tvDateCreation = (TextView) itemView.findViewById(R.id.compteDateCreation);
            tvSolde = (TextView) itemView.findViewById(R.id.compteMontant);
            buttonViewOption = (TextView) itemView.findViewById(R.id.textViewOptions);
        }
    }


}