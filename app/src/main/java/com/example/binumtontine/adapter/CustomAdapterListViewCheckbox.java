package com.example.binumtontine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.adherent.CheckBoxModel;

import java.util.ArrayList;

/**
 * Created by Valdès on 29/11/19.
 */
public class CustomAdapterListViewCheckbox extends BaseAdapter {

    private Context context;
    public static ArrayList<CheckBoxModel> checkBoxModelArrayList;
    public CustomAdapterListViewCheckbox(Context context, ArrayList<CheckBoxModel> checkBoxModelArrayList) {
        this.context = context;
        this.checkBoxModelArrayList = checkBoxModelArrayList;
    }
/*
    @Override
    public int getViewTypeCount() {
        return getCount();
    }*/
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
        return checkBoxModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return checkBoxModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder(); LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_view_checkbox_item, null, true);

            holder.tvId = (TextView) convertView.findViewById(R.id.pieceId);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb);
            holder.tvAnimal = (TextView) convertView.findViewById(R.id.animal);
            holder.tvIsOblig = (TextView) convertView.findViewById(R.id.isOblig);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }


      //  holder.checkBox.setText("Checkbox "+position); //I put it in comment
        holder.tvAnimal.setText(checkBoxModelArrayList.get(position).getAnimal());
        holder.tvId.setText(checkBoxModelArrayList.get(position).getPieceID());

        if(checkBoxModelArrayList.get(position).isOblig()){
            holder.tvIsOblig.setVisibility(View.VISIBLE);
        }else{
            holder.tvIsOblig.setVisibility(View.INVISIBLE);
        }

        holder.checkBox.setChecked(checkBoxModelArrayList.get(position).getSelected());

        holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.checkBox.setTag( position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View tempview = (View) holder.checkBox.getTag(R.integer.btnplusview);
                TextView tv = (TextView) tempview.findViewById(R.id.animal);
                TextView tvIdPiece = (TextView) tempview.findViewById(R.id.pieceId); // It's me who added it
                Integer pos = (Integer)  holder.checkBox.getTag();
               // Toast.makeText(context, "Checkbox "+pos+" clicked!", Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, tv.getText()+" "+tvIdPiece.getText()+" "+pos+" clicked!", Toast.LENGTH_SHORT).show();

                if(checkBoxModelArrayList.get(pos).getSelected()){
                    checkBoxModelArrayList.get(pos).setSelected(false);
                }else {
                    checkBoxModelArrayList.get(pos).setSelected(true);
                }

            }
        });

        return convertView;
    }

    private class ViewHolder {

        protected CheckBox checkBox;
        private TextView tvAnimal;
        private TextView tvId;
        private TextView tvIsOblig;

    }

}
