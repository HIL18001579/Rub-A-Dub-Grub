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
import com.example.rub_a_dub_grub3.LunchMenuActivity;
import com.example.rub_a_dub_grub3.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Model.BreakfastMenuListItem;
import Model.LunchMenuListItem;
import info.hoang8f.widget.FButton;

public class LunchMenuAdapter extends RecyclerView.Adapter<LunchMenuAdapter.LunchMenuViewHolder>{
    private Context context;
    private ArrayList<LunchMenuListItem> LunchMenu;

    int qtyCS = 0, qtyPR = 0, qtyPM = 0, qtyFG = 0;

    public LunchMenuAdapter(Context context, ArrayList<LunchMenuListItem> listItems) {
        this.context = context;
        this.LunchMenu = listItems;
    }

    public static class LunchMenuViewHolder extends RecyclerView.ViewHolder {
        ImageView imageLunch;
        TextView image_name_lunch;
        TextView description_lunch;
        TextView price_lunch;
        ImageView imgRemove;
        FButton btnAdd;
        //FButton btnCheckOut;
        TextView txtOrderQty;

        FButton button;

        LunchMenuViewHolder(@NonNull View itemView) { // Sets the items for the RecyclerView - the constructor | pass the OnitemClicklistenr to enable the interface access to the static class
            super( itemView );
            Context context = itemView.getContext();

            imageLunch = itemView.findViewById( R.id.imageLunch );
            image_name_lunch = itemView.findViewById( R.id.image_name_lunch );
            description_lunch = itemView.findViewById( R.id.description_lunch );
            price_lunch = itemView.findViewById( R.id.price_lunch );
            imgRemove = itemView.findViewById( R.id.imgRemove );
            btnAdd = itemView.findViewById( R.id.btnAdd );
        }
    }

    @NonNull
    @Override
    public LunchMenuViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.layout_lunch_menu_listitem, parent, false );
        return new LunchMenuAdapter.LunchMenuViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final LunchMenuAdapter.LunchMenuViewHolder holder, final int position) { // Sets the values from the Viewholder from itemlist

        holder.getAdapterPosition();
        Picasso.get().load(LunchMenu.get(position).getImageLunch()).into(holder.imageLunch);
        holder.image_name_lunch.setText( LunchMenu.get( position ).getName() );
        holder.description_lunch.setText( LunchMenu.get( position ).getDescription() );
        holder.price_lunch.setText( LunchMenu.get( position ).getPrice() );
        holder.imgRemove.findViewById( R.id.imgRemove );
        holder.btnAdd.setButtonColor( Color.parseColor( "#37AF3C" ) );

        holder.btnAdd.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        qtyCS = qtyCS +1;
                        Toast.makeText(context, "Chicken Caesar Salad added", Toast.LENGTH_SHORT).show();
                        ((LunchMenuActivity) context).ChickSaladCalled(qtyCS);
                        break;
                    case 1:
                        qtyPR = qtyPR +1;
                        Toast.makeText(context, "Prego Roll added", Toast.LENGTH_SHORT).show();
                        ((LunchMenuActivity) context).PregoRollCalled(qtyPR);
                        break;
                    case 2:
                        qtyPM = qtyPM +1;
                        Toast.makeText(context, "Ploughman's Lunch added", Toast.LENGTH_SHORT).show();
                        ((LunchMenuActivity) context).PloghmansCalled(qtyPM);
                        break;
                    case 3:
                        qtyFG = qtyFG +1;
                        Toast.makeText(context, "Fish Goujons added", Toast.LENGTH_SHORT).show();
                        ((LunchMenuActivity) context).FishGoujonsCalled(qtyFG);
                        break;
                }
            }
        } );

        holder.imgRemove.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        if (qtyCS > 0) {
                            Toast.makeText(context, "Chicken Caesar Salad removed", Toast.LENGTH_SHORT).show();
                            qtyCS = qtyCS - 1;
                            ((LunchMenuActivity) context).ChickSaladCalled(qtyCS);
                        } else {
                            Toast.makeText(context, "Nothing to remove", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
                        if (qtyPR > 0) {
                            Toast.makeText(context, "Prego Roll removed", Toast.LENGTH_SHORT).show();
                            qtyPR = qtyPR - 1;
                            ((LunchMenuActivity) context).PregoRollCalled(qtyPR);
                        } else {
                            Toast.makeText(context, "Nothing to remove", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        if (qtyPM > 0) {
                            Toast.makeText(context, "Ploughman's Lunch removed", Toast.LENGTH_SHORT).show();
                            qtyPM = qtyPM - 1;
                            ((LunchMenuActivity) context).PloghmansCalled(qtyPM);
                        } else {
                            Toast.makeText(context, "Nothing to remove", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 3:
                        if (qtyFG > 0) {
                            Toast.makeText(context, "Fish Goujons removed", Toast.LENGTH_SHORT).show();
                            qtyFG = qtyFG - 1;
                            ((LunchMenuActivity) context).FishGoujonsCalled(qtyFG);
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
        return LunchMenu.size();
    }
}
