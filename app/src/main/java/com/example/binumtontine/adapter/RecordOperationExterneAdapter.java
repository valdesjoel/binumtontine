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
import com.example.binumtontine.modele.OperationExterneDetails;

import java.util.List;

public class RecordOperationExterneAdapter extends BaseAdapter {

    private Context recordContext;
    private List<OperationExterneDetails> recordList;

    public RecordOperationExterneAdapter(Context context, List<OperationExterneDetails> records) {
        recordList = records;
        recordContext = context;
    }

    public void add(OperationExterneDetails record) {
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
            view = recordInflater.inflate(R.layout.record_operation_externe_details, null);

            holder = new RecordViewHolder();
            holder.libelleView = (TextView) view.findViewById(R.id.op_ext_libelle);
            holder.heureView = (TextView) view.findViewById(R.id.op_ext_heure);
            holder.produitView = (TextView) view.findViewById(R.id.op_ext_produit);
            holder.chargeView = (TextView) view.findViewById(R.id.op_ext_charge);
            view.setTag(holder);

        }else {
            holder = (RecordViewHolder) view.getTag();
        }

//        Record brouillard = (Record) getItem(i);
       // Brouillard brouillard = (Brouillard) getItem(i);
        OperationExterneDetails operationExterneDetails = (OperationExterneDetails) getItem(i);
        System.out.println(operationExterneDetails.toString()); ;
       /* Log.e("heureView**", operationExterneDetails.getOdDatHCree());
        Log.e("adherentView**", operationExterneDetails.getNomAdherent());*/

        holder.heureView.setText(operationExterneDetails.getOdDatHCree());
//        holder.adherentView.setText(operationExterneDetails.getNomAdherent());
        holder.libelleView.setText(operationExterneDetails.getOdLibelle());
//        holder.produitView.setText(operationExterneDetails.getOdMontant());
//        holder.chargeView.setText(operationExterneDetails.getOdMontant());
////        holder.soldeView.setText(operationExterneDetails.getSoldeCaisse());

        if ((operationExterneDetails.getOdSensOper()).equals("E")){
            holder.produitView.setText(operationExterneDetails.getOdMontant());
            holder.chargeView.setText("");
        }else if(operationExterneDetails.getOdSensOper().equals("S")){
            holder.produitView.setText("");
            holder.chargeView.setText(operationExterneDetails.getOdMontant());
        }
//        holder.heureView.setText(operationExterneDetails.getOdDatHCree());
//        holder.libelleView.setText(operationExterneDetails.getOdLibelle());
//        holder.ageView.setText(brouillard.age);
//        holder.positionView.setText(brouillard.position);
//        holder.compteView.setText(brouillard.address);
        return view;
    }

    private static class RecordViewHolder {

        public TextView heureView;
        public TextView adherentView;
        public TextView libelleView;
        public TextView produitView;
        public TextView chargeView;
        public TextView soldeView;
    }
}
