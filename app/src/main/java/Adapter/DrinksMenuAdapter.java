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

import com.example.rub_a_dub_grub3.DrinksMenuActivity;
import com.example.rub_a_dub_grub3.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Model.DrinksMenuListItem;
import info.hoang8f.widget.FButton;

public class DrinksMenuAdapter extends RecyclerView.Adapter<DrinksMenuAdapter.DrinksMenuViewHolder> {
    private Context context;
    private ArrayList<DrinksMenuListItem> DrinksMenu;

    int qtyCT = 0, qtyCC = 0, qtyOJ = 0, qtyCL = 0;

    public DrinksMenuAdapter(Context context, ArrayList<DrinksMenuListItem> listItems) {
        this.context = context;
        this.DrinksMenu = listItems;
    }

    public static class DrinksMenuViewHolder extends RecyclerView.ViewHolder {
        ImageView imageDrinks;
        TextView image_name_drinks;
        TextView description_drinks;
        TextView price_drinks;
        ImageView imgRemove;
        FButton btnAdd;
        //FButton btnCheckOut;
        TextView txtOrderQty;

        FButton button;

        DrinksMenuViewHolder(@NonNull View itemView) { // Sets the items for the RecyclerView - the constructor | pass the OnitemClicklistenr to enable the interface access to the static class
            super( itemView );
            Context context = itemView.getContext();

            imageDrinks = itemView.findViewById( R.id.imageDrinks );
            image_name_drinks = itemView.findViewById( R.id.image_name_drinks );
            description_drinks = itemView.findViewById( R.id.description_drinks );
            price_drinks = itemView.findViewById( R.id.price_drinks );
            imgRemove = itemView.findViewById( R.id.imgRemove );
            btnAdd = itemView.findViewById( R.id.btnAdd );
            //btnCheckOut = itemView.findViewById( R.id.btnCheckOut );
        }
    }

    @NonNull
    @Override
    public DrinksMenuAdapter.DrinksMenuViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.layout_drinks_menu_listitem, parent, false );
        return new DrinksMenuAdapter.DrinksMenuViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final DrinksMenuAdapter.DrinksMenuViewHolder holder, final int position) { // Sets the values from the Viewholder from itemlist

        holder.getAdapterPosition();
        Picasso.get().load(DrinksMenu.get(position).getImageDrinks()).into(holder.imageDrinks);
        holder.image_name_drinks.setText( DrinksMenu.get( position ).getName() );
        holder.description_drinks.setText( DrinksMenu.get( position ).getDescription() );
        holder.price_drinks.setText( DrinksMenu.get( position ).getPrice() );
        holder.imgRemove.findViewById( R.id.imgRemove );
        holder.btnAdd.setButtonColor( Color.parseColor( "#37AF3C" ) );

        holder.btnAdd.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        qtyCT = qtyCT +1;
                        Toast.makeText(context, "Coffee/Tea added", Toast.LENGTH_SHORT).show();
                        ((DrinksMenuActivity) context).CoffeeTeaCalled(qtyCT);
                        break;
                    case 1:
                        qtyCC = qtyCC +1;
                        Toast.makeText(context, "Coca-Cola added", Toast.LENGTH_SHORT).show();
                        ((DrinksMenuActivity) context).CokeCalled(qtyCC);
                        break;
                    case 2:
                        qtyOJ = qtyOJ +1;
                        Toast.makeText(context, "Orange Juice added", Toast.LENGTH_SHORT).show();
                        ((DrinksMenuActivity) context).OJCalled(qtyOJ);
                        break;
                    case 3:
                        qtyCL = qtyCL +1;
                        Toast.makeText(context, "Castle Lager added", Toast.LENGTH_SHORT).show();
                        ((DrinksMenuActivity) context).LagerCalled(qtyCL);
                        break;
                }
            }
        } );

        holder.imgRemove.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        if (qtyCT > 0) {
                            Toast.makeText(context, "Coffee/Tea removed", Toast.LENGTH_SHORT).show();
                            qtyCT = qtyCT - 1;
                            ((DrinksMenuActivity) context).CoffeeTeaCalled(qtyCT);
                        } else {
                            Toast.makeText(context, "Nothing to remove", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
                        if (qtyCC > 0) {
                            Toast.makeText(context, "Coca-Cola removed", Toast.LENGTH_SHORT).show();
                            qtyCC = qtyCC - 1;
                            ((DrinksMenuActivity) context).CokeCalled(qtyCC);
                        } else {
                            Toast.makeText(context, "Nothing to remove", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        if (qtyOJ > 0) {
                            Toast.makeText(context, "Orange Juice removed", Toast.LENGTH_SHORT).show();
                            qtyOJ = qtyOJ - 1;
                            ((DrinksMenuActivity) context).OJCalled(qtyOJ);
                        } else {
                            Toast.makeText(context, "Nothing to remove", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 3:
                        if (qtyCL > 0) {
                            Toast.makeText(context, "Castle Lager removed", Toast.LENGTH_SHORT).show();
                            qtyCL = qtyCL - 1;
                            ((DrinksMenuActivity) context).LagerCalled(qtyCL);
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
        return DrinksMenu.size();
    }
}
