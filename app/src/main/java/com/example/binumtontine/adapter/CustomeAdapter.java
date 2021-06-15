    package com.example.binumtontine.adapter;

    import android.content.Context;
    import android.text.Editable;
    import android.text.TextWatcher;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.BaseAdapter;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.TextView;

    import com.example.binumtontine.R;
    import com.example.binumtontine.activity.adherent.EditModel;

    import java.util.ArrayList;

    /**
     * Created by Valdès FOTSO on 16-Dec-19.
     */
    public class CustomeAdapter extends BaseAdapter {

        private Context context;
        public static ArrayList<EditModel> editModelArrayList;

        public CustomeAdapter(Context context, ArrayList<EditModel> editModelArrayList) {

            this.context = context;
            this.editModelArrayList = editModelArrayList;
        }
        //For result this problem: java.lang.IllegalArgumentException: Can't have a viewTypeCount < 1
        @Override
        public int getViewTypeCount() {
            if(getCount() > 0){
                return getCount();
            }else{
                return super.getViewTypeCount();
            }
        }
        @Override
        public int getItemViewType(int position) {

            return position;
        }

        @Override
        public int getCount() {
            return editModelArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return editModelArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;

            if (convertView == null) {
                holder = new ViewHolder();
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_view_edit_text_item, null, true);

                holder.etFraisMontant = (TextView) convertView.findViewById(R.id.fraisMontant);
                holder.etNbrePartSociale = (TextView) convertView.findViewById(R.id.nombrePS);
                holder.tvFraisId = (TextView) convertView.findViewById(R.id.fraisId);
                holder.tvLibelleFrais = (TextView) convertView.findViewById(R.id.fraisLibelle);
                holder.tvFraisFonction = (TextView) convertView.findViewById(R.id.fraisFonction);
                holder.tvTotalMontantPS = (TextView) convertView.findViewById(R.id.totalMontantPS);
                holder.tvBase = (TextView) convertView.findViewById(R.id.tvBase);
/*
                holder.buttonInc = (Button) convertView.findViewById(R.id.increase);
                holder.buttonDec = (Button) convertView.findViewById(R.id.decrease);*/

                convertView.setTag(holder);
            }else {
                // the getTag returns the viewHolder object set as a tag to the view
                holder = (ViewHolder)convertView.getTag();
            }

            holder.etFraisMontant.setText(editModelArrayList.get(position).getFcValeur());
//            holder.etFraisMontant.setText(editModelArrayList.get(position).getMontantbaseValeur(editModelArrayList));
//            holder.etNbrePartSociale.setText(editModelArrayList.get(position).getFcNbrePartMinH());

            holder.tvLibelleFrais.setText(editModelArrayList.get(position).getFraisLibelle());
            holder.tvFraisId.setText(editModelArrayList.get(position).getFraisID());
            holder.tvFraisFonction.setText(editModelArrayList.get(position).getFraisFonction());
            holder.tvBase.setText(editModelArrayList.get(position).getFraisBase());

            if ((editModelArrayList.get(position).getFraisFonction()).equals("P")){
//                editModelArrayList.get(position).setFcNbrePartSouhaiter(editModelArrayList.get(position).getFcNbrePartSocialeAdherentByAge()+"");
//              holder.etNbrePartSociale.setText(editModelArrayList.get(position).getFcNbrePartSocialeAdherentByAge()+"");
//                holder.count = Integer.parseInt(editModelArrayList.get(position).getFcNbrePartSouhaiter());
                holder.etNbrePartSociale.setText(editModelArrayList.get(position).getFcNbrePartSouhaiter()+"");
//                holder.etNbrePartSociale.setText(holder.count+"");
                holder.etNbrePartSociale.setVisibility(View.VISIBLE);
                holder.tvTotalMontantPS.setVisibility(View.VISIBLE);
                holder.tvTotalMontantPS.setText(editModelArrayList.get(position).calculTotalPartSociales());
            }else{
//                holder.count =1;

                holder.etNbrePartSociale.setVisibility(View.GONE);
                holder.tvTotalMontantPS.setVisibility(View.GONE);
            }
            /*holder.etFraisMontant.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    editModelArrayList.get(position).setFcValeur(holder.etFraisMontant.getText().toString());
                }
                @Override
                public void afterTextChanged(Editable editable) {
                }
            });*/

           /*holder.etNbrePartSociale.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    editModelArrayList.get(position).setFcNbrePartSouhaiter(holder.etNbrePartSociale.getText().toString());
//                    editModelArrayList.get(position).setFcNbrePartSouhaiter(holder.count+"");
    //                editModelArrayList.get(position).set(holder.ET_nbre_part_sociale.getText().toString());
                    holder.tvTotalMontantPS.setText(editModelArrayList.get(position).calculTotalPartSociales());
                    try{
                        if (!holder.etNbrePartSociale.getText().toString().equals("") && editModelArrayList.get(position).getFcNbrePartSocialeAdherentByAge()> Integer.parseInt(holder.etNbrePartSociale.getText().toString())){
                            holder.etNbrePartSociale.setError("Le nombre minimum de part sociale est de: "+editModelArrayList.get(position).getFcNbrePartSocialeAdherentByAge());
                            holder.etNbrePartSociale.requestFocus();
                        }else{
                            holder.etNbrePartSociale.setError(null);
//                            holder.etNbrePartSociale.setText(holder.count+"");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                @Override
                public void afterTextChanged(Editable editable) {
//                    holder.etNbrePartSociale.setText(holder.etNbrePartSociale.getText().toString());
                    }
            });*/

            return convertView;
        }

        private class ViewHolder {

            protected TextView etFraisMontant;
            protected TextView etNbrePartSociale;
            protected TextView tvLibelleFrais;
            protected TextView tvFraisId;
            protected TextView tvFraisFonction;
            protected TextView tvTotalMontantPS;
            protected TextView tvBase;

            /*protected Button buttonInc;
            protected Button buttonDec;
            int count=0;*/
        }
    }
