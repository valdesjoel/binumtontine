package com.example.binumtontine.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.adherent.Brouillard;

import java.util.List;

public class RecordBrouillardAdapter extends BaseAdapter {

    private Context recordContext;
    private List<Brouillard> recordList;

    public RecordBrouillardAdapter(Context context, List<Brouillard> records) {
        recordList = records;
        recordContext = context;
    }

    public void add(Brouillard record) {
        recordList.add(record);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return recordList.size();
    }
    @Override
    public Object getItem(int i) {
        return recordList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RecordViewHolder holder;

        if (view ==null){
            LayoutInflater recordInflater = (LayoutInflater)
                    recordContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = recordInflater.inflate(R.layout.record_brouillard, null);

            holder = new RecordViewHolder();
            holder.libelleView = (TextView) view.findViewById(R.id.brouillard_libelle);
            holder.heureView = (TextView) view.findViewById(R.id.brouillard_heure);
            holder.adherentView = (TextView) view.findViewById(R.id.brouillard_adherent);
            holder.compteView = (TextView) view.findViewById(R.id.brouillard_compte);
            holder.montantView = (TextView) view.findViewById(R.id.brouillard_montant);
            holder.soldeView = (TextView) view.findViewById(R.id.brouillard_solde_caisse);
            view.setTag(holder);

        }else {
            holder = (RecordViewHolder) view.getTag();
        }

//        Record brouillard = (Record) getItem(i);
        Brouillard brouillard = (Brouillard) getItem(i);
        Log.e("heureView**", brouillard.getHeure());
        Log.e("adherentView**", brouillard.getNomAdherent());

        holder.heureView.setText(brouillard.getHeure());
        holder.adherentView.setText(brouillard.getNomAdherent());
        holder.libelleView.setText(brouillard.getLibelle());
        holder.compteView.setText(brouillard.getCompte());
        holder.montantView.setText(brouillard.getMontantOperation());
        holder.soldeView.setText(brouillard.getSoldeCaisse());
       /*
        if ((brouillard.age).equals("D")){
            holder.libelleView.setText(brouillard.position);
            holder.adherentView.setText("");
        }else if((brouillard.age).equals("R")){
            holder.libelleView.setText("");
            holder.adherentView.setText(brouillard.position);
        }else{

        holder.libelleView.setText(brouillard.age);
        holder.adherentView.setText(brouillard.position);
        }
        holder.heureView.setText(brouillard.name);
//        holder.ageView.setText(brouillard.age);
//        holder.positionView.setText(brouillard.position);
        holder.compteView.setText(brouillard.address);*/
        return view;
    }

    private static class RecordViewHolder {

        public TextView heureView;
        public TextView adherentView;
        public TextView libelleView;
        public TextView compteView;
        public TextView montantView;
        public TextView soldeView;
    }
}
