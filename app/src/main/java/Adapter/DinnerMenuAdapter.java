package Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rub_a_dub_grub3.BreakfastMenuActivity;
import com.example.rub_a_dub_grub3.DinnerMenuActivity;
import com.example.rub_a_dub_grub3.LunchMenuActivity;
import com.example.rub_a_dub_grub3.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Model.DinnerMenuListItem;
import Model.LunchMenuListItem;
import info.hoang8f.widget.FButton;

public class DinnerMenuAdapter extends RecyclerView.Adapter<DinnerMenuAdapter.DinnerMenuViewHolder> {
    private Context context;
    private ArrayList<DinnerMenuListItem> DinnerMenu;

    int qtySM = 0, qtySP = 0, qtyRES = 0, qtyYT = 0;

    public DinnerMenuAdapter(Context context, ArrayList<DinnerMenuListItem> listItems) {
        this.context = context;
        this.DinnerMenu = listItems;
    }

    public static class DinnerMenuViewHolder extends RecyclerView.ViewHolder {
        ImageView imageDinner;
        TextView image_name_dinner;
        TextView description_dinner;
        TextView price_dinner;
        ImageView imgRemove;
        FButton btnAdd;
        TextView txtOrderQty;

        FButton button;

        DinnerMenuViewHolder(@NonNull View itemView) {
            super( itemView );
            Context context = itemView.getContext();

            imageDinner = itemView.findViewById( R.id.imageDinner );
            image_name_dinner = itemView.findViewById( R.id.image_name_dinner );
            description_dinner = itemView.findViewById( R.id.description_dinner );
            price_dinner = itemView.findViewById( R.id.price_dinner );
            imgRemove = itemView.findViewById( R.id.imgRemove );
            btnAdd = itemView.findViewById( R.id.btnAdd );
            //btnCheckOut = itemView.findViewById( R.id.btnCheckOut );
        }
    }

    @NonNull
    @Override
    public DinnerMenuAdapter.DinnerMenuViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.layout_dinner_menu_listitem, parent, false );
        return new DinnerMenuAdapter.DinnerMenuViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final DinnerMenuAdapter.DinnerMenuViewHolder holder, final int position) { // Sets the values from the Viewholder from itemlist

        holder.getAdapterPosition();
        Picasso.get().load(DinnerMenu.get(position).getImageDinner()).into(holder.imageDinner);
        holder.image_name_dinner.setText( DinnerMenu.get( position ).getName() );
        holder.description_dinner.setText( DinnerMenu.get( position ).getDescription() );
        holder.price_dinner.setText( DinnerMenu.get( position ).getPrice() );
        holder.imgRemove.findViewById( R.id.imgRemove );
        holder.btnAdd.setButtonColor( Color.parseColor( "#37AF3C" ) );

        holder.btnAdd.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        qtySM = qtySM +1;
                        Toast.makeText(context, "Sausage and mash added", Toast.LENGTH_SHORT).show();
                        ((DinnerMenuActivity) context).SausMashCalled(qtySM);
                        break;
                    case 1:
                        qtySP = qtySP +1;
                        Toast.makeText(context, "Shepherds pie added", Toast.LENGTH_SHORT).show();
                        ((DinnerMenuActivity) context).ShepsPieCalled(qtySP);
                        break;
                    case 2:
                        qtyRES = qtyRES +1;
                        Toast.makeText(context, "Ribeye steak added", Toast.LENGTH_SHORT).show();
                        ((DinnerMenuActivity) context).RibeyeCalled(qtyRES);
                        break;
                    case 3:
                        qtyYT = qtyYT +1;
                        Toast.makeText(context, "Yellowtail added", Toast.LENGTH_SHORT).show();
                        ((DinnerMenuActivity) context).YellTailCalled(qtyYT);
                        break;
                }
            }
        } );

        holder.imgRemove.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        if (qtySM > 0) {
                            Toast.makeText(context, "Sausage and mash removed", Toast.LENGTH_SHORT).show();
                            qtySM = qtySM - 1;
                            ((DinnerMenuActivity) context).SausMashCalled(qtySM);
                        } else {
                            Toast.makeText(context, "Nothing to remove", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
                        if (qtySP > 0) {
                            Toast.makeText(context, "Shepherds pie removed", Toast.LENGTH_SHORT).show();
                            qtySP = qtySP - 1;
                            ((DinnerMenuActivity) context).ShepsPieCalled(qtySP);
                        } else {
                            Toast.makeText(context, "Nothing to remove", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        if (qtyRES > 0) {
                            Toast.makeText(context, "Ribeye steak removed", Toast.LENGTH_SHORT).show();
                            qtyRES = qtyRES - 1;
                            ((DinnerMenuActivity) context).RibeyeCalled(qtyRES);
                        } else {
                            Toast.makeText(context, "Nothing to remove", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 3:
                        if (qtyYT > 0) {
                            Toast.makeText(context, "Yellowtail removed", Toast.LENGTH_SHORT).show();
                            qtyYT = qtyYT - 1;
                            ((DinnerMenuActivity) context).YellTailCalled(qtyYT);
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
        return DinnerMenu.size();
    }
}
