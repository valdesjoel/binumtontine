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
import com.example.binumtontine.activity.convertisseur.DateTools;
import com.example.binumtontine.modele.Echeancier;

import java.util.List;

public class RecordEcheancierAdapter extends BaseAdapter {

    private Context recordContext;
    private List<Echeancier> recordList;

    public RecordEcheancierAdapter(Context context, List<Echeancier> records) {
        recordList = records;
        recordContext = context;
    }

    public void add(Echeancier record) {
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
            view = recordInflater.inflate(R.layout.record_echeancier, null);

            holder = new RecordViewHolder();

            holder.EcnumeroView = (TextView) view.findViewById(R.id.echeancier_numero);
            holder.EcCreditView = (TextView) view.findViewById(R.id.EcCredit);
            holder.EcDateRembView = (TextView) view.findViewById(R.id.EcDateRemb);
            holder.EcMtCapitalView = (TextView) view.findViewById(R.id.EcMtCapital);
            holder.EcMtInteretView = (TextView) view.findViewById(R.id.EcMtInteret);
            holder.EcTotalEcheanceView = (TextView) view.findViewById(R.id.EcTotalEcheance);
            view.setTag(holder);

        }else {
            holder = (RecordViewHolder) view.getTag();
        }

//        Record brouillard = (Record) getItem(i);
        Echeancier echeancier = (Echeancier) getItem(i);
        /*Log.e("heureView**", echeancier.getHeure());
        Log.e("adherentView**", echeancier.getNomAdherent());*/

//        holder.EcnumeroView.setText(echeancier.getEcNumero());
        holder.EcnumeroView.setText(echeancier.getEcNumVersion()+"");
        holder.EcCreditView.setText(echeancier.getEcCredit().getDcCredit());
        holder.EcDateRembView.setText(DateTools.formatterDateToString(echeancier.getEcDateRemb()));
        holder.EcMtCapitalView.setText(echeancier.getEcMtCapital()+"");
        holder.EcMtInteretView.setText(echeancier.getEcMtInteret()+"");
        holder.EcTotalEcheanceView.setText((echeancier.getEcMtCapital() +echeancier.getEcMtInteret())+"");
        return view;
    }

    private static class RecordViewHolder {
        public TextView EcnumeroView;
        public TextView EcCreditView;
        public TextView EcDateRembView;
        public TextView EcMtCapitalView;
        public TextView EcMtInteretView;
        public TextView EcTotalEcheanceView;
    }
}
