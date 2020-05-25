package com.example.binumtontine.adapter;

        import android.app.Activity;
        import android.content.Context;

        import androidx.appcompat.widget.PopupMenu;
        import androidx.recyclerview.widget.RecyclerView;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.binumtontine.R;
        import com.example.binumtontine.activity.CaisseActivity;
        import com.example.binumtontine.activity.adherent.CreateAdherent;
        import com.example.binumtontine.activity.adherent.CreateCpteCourant;
        import com.example.binumtontine.activity.adherent.CreateEAP;
        import com.example.binumtontine.activity.adherent.CreateEAT;
        import com.example.binumtontine.activity.adherent.CreateEAV;
        import com.example.binumtontine.activity.adherent.DemandeCredit;
        import com.example.binumtontine.activity.adherent.GetPieceAdherent;
        //  import com.example.binumtontine.activity.adherent.MyList;
        import com.example.binumtontine.activity.adherent.Adherent;
        import com.example.binumtontine.activity.adherent.ListCompteAdherentActivity;
        import com.example.binumtontine.activity.adherent.ListCompteAdherentActivity_New;
        import com.example.binumtontine.activity.adherent.MainActivityUsager;
        import com.example.binumtontine.helper.CheckNetworkStatus;

        import java.io.Serializable;
        import java.util.List;


/**
 * Created by Valdès on 02/12/19.
 */

public class CustomAdapterListAdherent extends RecyclerView.Adapter<CustomAdapterListAdherent.ViewHolder> {

    //private List<MyList> list;
    private List<Adherent> list;
    private Context mCtx;
    private static final String KEY_ADHERENT_ID = "IpMembre";
    private static final String KEY_ADHERENT_NOM = "AdNom";
    private static final String KEY_ADHERENT_PRENOM = "AdPrenom";
    private static final String KEY_ADHERENT_NUM_MANUEL = "AdNumManuel";
    private static final String KEY_ADHERENT_CODE = "AdCode";

    private static final String KEY_ADHERENT = "ADHERENT";
    //private static final String KEY_ADHERENT_NUM_DOSSIER = "IpMembre";


    //public CustomAdapterListAdherent(List<MyList> list, Context mCtx) {
    public CustomAdapterListAdherent(List<Adherent> list, Context mCtx) {
        this.list = list;
        this.mCtx = mCtx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_items_home_adherent, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CustomAdapterListAdherent.ViewHolder holder, int position) {
        //MyList myList = list.get(position);
        final Adherent myList = list.get(position);
        holder.textViewId.setText(myList.getAdNumero());
        holder.textViewNom.setText(myList.getAdNom()+" "+myList.getAdPrenom());
        if(Integer.parseInt(myList.getAdNbreCompte())>1){
            holder.textViewNationalite.setText(myList.getAdNbreCompte()+" comptes");
        }else{
            holder.textViewNationalite.setText(myList.getAdNbreCompte()+" compte");
        }
      //  holder.tvNumDossier.setText("2 comptes");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check for network connectivity
                if (CheckNetworkStatus.isNetworkAvailable(mCtx)) {
                   /* String movieId = ((TextView) view.findViewById(R.id.movieId))
                            .getText().toString();
                    Intent intent = new Intent(mCtx,
                            CaisseActivity.class);
                    intent.putExtra(KEY_CAISSE_ID, movieId);
                    //startActivityForResult(intent, 20);

                    startActivity(intent); */
                    //handle menu0 click
                    //Intent i = new Intent(mCtx, ListCompteAdherentActivity.class);
                    Intent i = new Intent(mCtx, ListCompteAdherentActivity_New.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(KEY_ADHERENT, (Serializable) myList);
                    // bundle.putSerializable(KEY_ADHERENT, adherent);
                    i.putExtras(bundle);
                    i.putExtra(KEY_ADHERENT_ID, holder.textViewId.getText());
                    ((Activity) mCtx).startActivityForResult(i, 20);


                    //mCtx.startActivity(i);
//                    Toast.makeText(mCtx,
//                            "Liste des comptes de l'adhérent en construction!!!",
//                            Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(mCtx,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }
            }
        });


        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //will show popup menu here
                //creating a popup menu
                //PopupMenu popup = new PopupMenu(mCtx, holder.buttonViewOption);
                PopupMenu popup = new PopupMenu(mCtx, holder.buttonViewOption); // i made holer final
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu_adherent);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.menu0:
                                //handle menu0 click
                                  Intent i = new Intent(mCtx, GetPieceAdherent.class);
                                i.putExtra(KEY_ADHERENT_ID, holder.textViewId.getText());
                               // startActivityForResult(intent, 20);
                                mCtx.startActivity(i);

                                break;
                            case R.id.menu1:
                                //handle menu1 click

                                Intent ii = new Intent(mCtx, CreateAdherent.class);
                                CreateAdherent.to_update_adherent = true;
                                ii.putExtra(KEY_ADHERENT_ID, holder.textViewId.getText());
                                ((Activity) mCtx).startActivityForResult(ii, 20);
                               // CreateAdherent.to_update_adherent = false; //to reset default value


//                                Toast.makeText(mCtx,
//                                        "ID adhérent "+holder.textViewId.getText(), Toast.LENGTH_LONG).show();
                                break;
                            case R.id.menu2:
                                //handle menu2 click
                                Intent intentCreateEAV = new Intent(mCtx, CreateEAV.class);
                                intentCreateEAV.putExtra(KEY_ADHERENT_ID, myList.getAdNumero());
                                intentCreateEAV.putExtra(KEY_ADHERENT_NOM, myList.getAdNom());
                                intentCreateEAV.putExtra(KEY_ADHERENT_PRENOM, myList.getAdPrenom());
                                intentCreateEAV.putExtra(KEY_ADHERENT_NUM_MANUEL, myList.getAdNumManuel());
                                intentCreateEAV.putExtra(KEY_ADHERENT_CODE, myList.getAdCode());
                                //intentCreateEAV.putExtra(KEY_ADHERENT_NUM_DOSSIER, "");
                                // startActivityForResult(intent, 20);
                                //mCtx.startActivity(intentCreateEAV);
                                ((Activity) mCtx).startActivityForResult(intentCreateEAV, 20);
                                break;
                                //menu3 => compte cournt
                            case R.id.menu3:
                                //handle menu3 click
                                Intent intentCreateCpteCourant = new Intent(mCtx, CreateCpteCourant.class);
                                intentCreateCpteCourant.putExtra(KEY_ADHERENT_ID, myList.getAdNumero());
                                intentCreateCpteCourant.putExtra(KEY_ADHERENT_NOM, myList.getAdNom());
                                intentCreateCpteCourant.putExtra(KEY_ADHERENT_PRENOM, myList.getAdPrenom());
                                intentCreateCpteCourant.putExtra(KEY_ADHERENT_NUM_MANUEL, myList.getAdNumManuel());
                                intentCreateCpteCourant.putExtra(KEY_ADHERENT_CODE, myList.getAdCode());
                                //intentCreateEAV.putExtra(KEY_ADHERENT_NUM_DOSSIER, "");
                                // startActivityForResult(intent, 20);
                                //mCtx.startActivity(intentCreateEAV);
                                ((Activity) mCtx).startActivityForResult(intentCreateCpteCourant, 20);
                                break;
                            case R.id.menu4:
                                //handle menu3 click
                                Intent intentCreateEAT = new Intent(mCtx, CreateEAT.class);
                                intentCreateEAT.putExtra(KEY_ADHERENT_ID, myList.getAdNumero());
                                intentCreateEAT.putExtra(KEY_ADHERENT_NOM, myList.getAdNom());
                                intentCreateEAT.putExtra(KEY_ADHERENT_PRENOM, myList.getAdPrenom());
                                intentCreateEAT.putExtra(KEY_ADHERENT_NUM_MANUEL, myList.getAdNumManuel());
                                intentCreateEAT.putExtra(KEY_ADHERENT_CODE, myList.getAdCode());
                                //intentCreateEAV.putExtra(KEY_ADHERENT_NUM_DOSSIER, "");
                                // startActivityForResult(intent, 20);
                               // mCtx.startActivity(intentCreateEAT);
                                ((Activity) mCtx).startActivityForResult(intentCreateEAT, 20);
                                break;
                            case R.id.menu5:
                                //handle menu3 click
                                Intent intentCreateEAP = new Intent(mCtx, CreateEAP.class);
                                intentCreateEAP.putExtra(KEY_ADHERENT_ID, myList.getAdNumero());
                                intentCreateEAP.putExtra(KEY_ADHERENT_NOM, myList.getAdNom());
                                intentCreateEAP.putExtra(KEY_ADHERENT_PRENOM, myList.getAdPrenom());
                                intentCreateEAP.putExtra(KEY_ADHERENT_NUM_MANUEL, myList.getAdNumManuel());
                                intentCreateEAP.putExtra(KEY_ADHERENT_CODE, myList.getAdCode());
                                //intentCreateEAV.putExtra(KEY_ADHERENT_NUM_DOSSIER, "");
                                // startActivityForResult(intent, 20);
                               // mCtx.startActivity(intentCreateEAP);
                                ((Activity) mCtx).startActivityForResult(intentCreateEAP, 20);
                                break;
                            case R.id.menu7:
                                //handle menu3 click
                                Intent intentCreateDemandeCredit = new Intent(mCtx, DemandeCredit.class);
                                intentCreateDemandeCredit.putExtra(KEY_ADHERENT_ID, myList.getAdNumero());
                                intentCreateDemandeCredit.putExtra(KEY_ADHERENT_NOM, myList.getAdNom());
                                intentCreateDemandeCredit.putExtra(KEY_ADHERENT_PRENOM, myList.getAdPrenom());
                                intentCreateDemandeCredit.putExtra(KEY_ADHERENT_NUM_MANUEL, myList.getAdNumManuel());
                                intentCreateDemandeCredit.putExtra(KEY_ADHERENT_CODE, myList.getAdCode());
                                //intentCreateEAV.putExtra(KEY_ADHERENT_NUM_DOSSIER, "");
                                // startActivityForResult(intent, 20);
                               // mCtx.startActivity(intentCreateEAP);
                                ((Activity) mCtx).startActivityForResult(intentCreateDemandeCredit, 20);
                                break;
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
        public TextView textViewNumManuel;
        public TextView textViewNumDossier;
        public TextView textViewNom;
        public TextView textViewPrenom;
        public TextView textViewNationalite;
        public Button btnClick;
        public TextView buttonViewOption;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewId = (TextView) itemView.findViewById(R.id.textViewId);
            textViewNom = (TextView) itemView.findViewById(R.id.textViewNom);
            textViewNationalite = (TextView) itemView.findViewById(R.id.textViewNationalite);
            btnClick = (Button)itemView.findViewById(R.id.btnClick);
            buttonViewOption = (TextView) itemView.findViewById(R.id.textViewOptions);
        }
    }
}