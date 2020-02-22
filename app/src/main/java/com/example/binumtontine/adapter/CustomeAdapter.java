package com.example.binumtontine.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

    @Override
    public int getViewTypeCount() {
        return getCount();
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

            holder.editText = (EditText) convertView.findViewById(R.id.fraisMontant);
            holder.ET_nbre_part_sociale = (EditText) convertView.findViewById(R.id.nombrePS);
            holder.tvFraisId = (TextView) convertView.findViewById(R.id.fraisId);
            holder.tvLibelleFrais = (TextView) convertView.findViewById(R.id.fraisLibelle);
            holder.tvFraisFonction = (TextView) convertView.findViewById(R.id.fraisFonction);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.editText.setText(editModelArrayList.get(position).getEditTextValue());
        holder.ET_nbre_part_sociale.setText(editModelArrayList.get(position).getET_nbre_part_socialeValue());
        holder.tvLibelleFrais.setText(editModelArrayList.get(position).getFraisLibelle());
        holder.tvFraisId.setText(editModelArrayList.get(position).getFraisID());
        holder.tvFraisFonction.setText(editModelArrayList.get(position).getFraisFonction());
        if ((editModelArrayList.get(position).getFraisFonction()).equals("P")){
            holder.ET_nbre_part_sociale.setVisibility(View.VISIBLE);
        }else{
            holder.ET_nbre_part_sociale.setVisibility(View.GONE);
        }


        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editModelArrayList.get(position).setEditTextValue(holder.editText.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        holder.ET_nbre_part_sociale.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editModelArrayList.get(position).setET_nbre_part_socialeValue(holder.ET_nbre_part_sociale.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return convertView;
    }

    private class ViewHolder {

        protected EditText editText;
        protected EditText ET_nbre_part_sociale;
        protected TextView tvLibelleFrais;
        protected TextView tvFraisId;
        protected TextView tvFraisFonction;

    }

}
