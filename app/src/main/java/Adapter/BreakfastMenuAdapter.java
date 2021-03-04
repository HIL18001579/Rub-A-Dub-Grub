package Adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.ChangeBounds;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rub_a_dub_grub3.BreakfastMenuActivity;
import com.example.rub_a_dub_grub3.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Model.BreakfastMenuListItem;
import info.hoang8f.widget.FButton;

public class BreakfastMenuAdapter extends RecyclerView.Adapter<BreakfastMenuAdapter.BreakfastMenuViewHolder> {

    private Context context;
    private ArrayList<BreakfastMenuListItem> BreakfastMenu;

    int qtyFE = 0, qtyEB = 0, qtyCB = 0, qtyOB = 0;

    public BreakfastMenuAdapter(Context context, ArrayList<BreakfastMenuListItem> listItems) {
        this.context = context;
        this.BreakfastMenu = listItems;
    }

    public static class BreakfastMenuViewHolder extends RecyclerView.ViewHolder {
        ImageView imageBreakfast;
        TextView image_name_breakfast;
        TextView description_breakfast;
        TextView price_breakfast;
        ImageView imgRemove;
        FButton btnAdd;
        //FButton btnCheckOut;
        TextView txtOrderQty;

        FButton button;

        BreakfastMenuViewHolder(@NonNull View itemView) { // Sets the items for the RecyclerView - the constructor | pass the OnitemClicklistenr to enable the interface access to the static class
            super( itemView );
            Context context = itemView.getContext();

            imageBreakfast = itemView.findViewById( R.id.imageBreakfast );
            image_name_breakfast = itemView.findViewById( R.id.image_name_breakfast );
            description_breakfast = itemView.findViewById( R.id.description_breakfast );
            price_breakfast = itemView.findViewById( R.id.price_breakfast );
            imgRemove = itemView.findViewById( R.id.imgRemove );
            btnAdd = itemView.findViewById( R.id.btnAdd );
            //btnCheckOut = itemView.findViewById( R.id.btnCheckOut );
        }
    }

    @NonNull
    @Override
    public BreakfastMenuViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.layout_breakfast_menu_listitem, parent, false );
        return new BreakfastMenuViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final BreakfastMenuViewHolder holder, final int position) { // Sets the values from the Viewholder from itemlist

        holder.getAdapterPosition();
        Picasso.get().load(BreakfastMenu.get(position).getImageBreakfast()).into(holder.imageBreakfast);
        holder.image_name_breakfast.setText( BreakfastMenu.get( position ).getName() );
        holder.description_breakfast.setText( BreakfastMenu.get( position ).getDescription() );
        holder.price_breakfast.setText( BreakfastMenu.get( position ).getPrice() );
        holder.imgRemove.findViewById( R.id.imgRemove );
        holder.btnAdd.setButtonColor( Color.parseColor( "#37AF3C" ) );

        holder.btnAdd.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        qtyFE = qtyFE +1;
                        Toast.makeText(context, "Full English added", Toast.LENGTH_SHORT).show();
                        ((BreakfastMenuActivity) context).FullEngCalled(qtyFE);
                        break;
                    case 1:
                        qtyEB = qtyEB +1;
                        Toast.makeText(context, "Eggs Benedict added", Toast.LENGTH_SHORT).show();
                        ((BreakfastMenuActivity) context).EggsBenCalled(qtyEB);
                        break;
                    case 2:
                        qtyCB = qtyCB +1;
                        Toast.makeText(context, "Continental breakfast added", Toast.LENGTH_SHORT).show();
                        ((BreakfastMenuActivity) context).ContinentalCalled(qtyCB);
                        break;
                    case 3:
                        qtyOB = qtyOB +1;
                        Toast.makeText(context, "R-A-Dub Omelette added", Toast.LENGTH_SHORT).show();
                        ((BreakfastMenuActivity) context).OmeletteCalled(qtyOB);
                        break;
                }
            }
        } );

        holder.imgRemove.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        if (qtyFE > 0) {
                            Toast.makeText(context, "Full English removed", Toast.LENGTH_SHORT).show();
                            qtyFE = qtyFE - 1;
                            ((BreakfastMenuActivity) context).FullEngCalled(qtyFE);
                        } else {
                            Toast.makeText(context, "Nothing to remove", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
                        if (qtyEB > 0) {
                            Toast.makeText(context, "Eggs Benedict removed", Toast.LENGTH_SHORT).show();
                            qtyEB = qtyEB - 1;
                            ((BreakfastMenuActivity) context).EggsBenCalled(qtyEB);
                        } else {
                            Toast.makeText(context, "Nothing to remove", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        if (qtyCB > 0) {
                            Toast.makeText(context, "Continental breakfast removed", Toast.LENGTH_SHORT).show();
                            qtyCB = qtyCB - 1;
                            ((BreakfastMenuActivity) context).ContinentalCalled(qtyCB);
                        } else {
                            Toast.makeText(context, "Nothing to remove", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 3:
                        if (qtyOB > 0) {
                            Toast.makeText(context, "R-A-Dub Omelette removed", Toast.LENGTH_SHORT).show();
                            qtyOB = qtyOB - 1;
                            ((BreakfastMenuActivity) context).OmeletteCalled(qtyOB);
                        } else {
                            Toast.makeText(context, "Nothing to remove", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        } );
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return BreakfastMenu.size();
    }
}
