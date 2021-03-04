package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rub_a_dub_grub3.BreakfastMenuActivity;
import com.example.rub_a_dub_grub3.DinnerMenuActivity;
import com.example.rub_a_dub_grub3.DrinksMenuActivity;
import com.example.rub_a_dub_grub3.LunchMenuActivity;
import com.example.rub_a_dub_grub3.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Model.SelectionListItem;

public class SelectionAdapter extends RecyclerView.Adapter<SelectionAdapter.SelectionViewHolder> {

    private Context context;
    private ArrayList<SelectionListItem> DiningSelection;

    public SelectionAdapter(Context context, ArrayList<SelectionListItem> listItems) {
        this.context = context;
        this.DiningSelection = listItems;
    }

    @NonNull
    @Override
    public SelectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.layout_selection_listitem, parent,false );
        return new SelectionViewHolder( v );
    }

    @Override
    public void onBindViewHolder(@NonNull SelectionViewHolder holder, int position) {

        holder.name.setText(DiningSelection.get(position).getName());
        holder.text.setText(DiningSelection.get(position).getText());
        Picasso.get().load(DiningSelection.get(position).getImageSelection()).into(holder.imageSelection);
    }

    @Override
    public int getItemCount() {
        return DiningSelection.size();
    }

    public class SelectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, text;
        ImageView imageSelection;

        SelectionViewHolder(@NonNull View itemView) {
            super(itemView);

            Context context = itemView.getContext();
            name = itemView.findViewById(R.id.image_name);
            text = itemView.findViewById(R.id.txtExtras);
            imageSelection = itemView.findViewById(R.id.imageSelection);
            itemView.setOnClickListener( this );
        }

        @Override
        public void onClick(View view) {

            switch (getAdapterPosition()) {
                case 0:
                    context.startActivity( new Intent( view.getContext(), BreakfastMenuActivity.class ) );
                    break;
                case 1:
                    context.startActivity( new Intent( view.getContext(), LunchMenuActivity.class ) );
                    break;
                case 2:
                    context.startActivity( new Intent( view.getContext(), DinnerMenuActivity.class ) );
                    break;
                /* case 3:
                    context.startActivity( new Intent( view.getContext(), DrinksMenuActivity.class ) );
                    break; */
            }
        }
    }
}
