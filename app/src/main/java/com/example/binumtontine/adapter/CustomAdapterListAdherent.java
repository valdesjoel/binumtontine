package com.example.binumtontine.adapter;

        import android.content.Context;

        import androidx.appcompat.widget.PopupMenu;
        import androidx.recyclerview.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import com.example.binumtontine.R;
        import com.example.binumtontine.activity.adherent.MyList;

        import java.util.List;

/**
 * Created by Valdès on 02/12/19.
 */

public class CustomAdapterListAdherent extends RecyclerView.Adapter<CustomAdapterListAdherent.ViewHolder> {

    private List<MyList> list;
    private Context mCtx;

    public CustomAdapterListAdherent(List<MyList> list, Context mCtx) {
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
        MyList myList = list.get(position);
        holder.textViewHead.setText(myList.getHead());
        holder.textViewDesc.setText(myList.getDesc());

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
                            case R.id.menu1:
                                //handle menu1 click
                                break;
                            case R.id.menu2:
                                //handle menu2 click
                                break;
                            case R.id.menu3:
                                //handle menu3 click
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

        public TextView textViewHead;
        public TextView textViewDesc;
        public TextView buttonViewOption;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
            buttonViewOption = (TextView) itemView.findViewById(R.id.textViewOptions);
        }
    }
}