package com.example.rub_a_dub_grub3;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Adapter.DinnerMenuAdapter;
import Adapter.LunchMenuAdapter;
import Animations.LoadingDialog;
import Model.DinnerMenuListItem;
import Model.LunchMenuListItem;
import info.hoang8f.widget.FButton;

public class DinnerMenuActivity extends AppCompatActivity {

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<DinnerMenuListItem> DinnerMenu;
    DinnerMenuAdapter adapter;
    private AlertDialog.Builder alertDialog;

    private FButton btnAddToOrder;
    private FButton btnDrinks;

    private TextView txtSausMash;
    private TextView txtShepsPie;
    private TextView txtRibeye;
    private TextView txtYellTail;

    public static final String EXTRA_TEXT_QTY_MEAL = "com.example.rub_a_dub_grub3.EXTRA_TEXT_QTY_MEAL";
    public static final String EXTRA_TEXT_SELECTION_MEAL = "com.example.rub_a_dub_grub3.EXTRA_TEXT_SELECTION_MEAL";
    public static final String EXTRA_TEXT_PRICE_MEAL = "com.example.rub_a_dub_grub3.EXTRA_TEXT_PRICE_MEAL";
    public static final String EXTRA_TEXT_TOTAL_MEAL = "com.example.rub_a_dub_grub3.EXTRA_TEXT_TOTAL_MEAL";
    private final int REQUEST_CODE = 42;

    String selectionQty = "";
    String selectionDetail = "";
    String selectionPrice = "";
    double price_SM = 0, price_SP = 0, price_RES = 0, price_YT = 0, total1 = 0;

    DecimalFormat currency = new DecimalFormat("###,###.00");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dinner_menu );
        this.setTitle( getResources().getString( R.string.dinnerMenu ) );
        btnAddToOrder = findViewById(R.id.btnAddToOrder);
        txtSausMash = findViewById(R.id.txtMenuItem1);
        txtShepsPie = findViewById(R.id.txtMenuItem2);
        txtRibeye = findViewById(R.id.txtMenuItem3);
        txtYellTail = findViewById(R.id.txtMenuItem4);

        buildRecyclerView();
        changeButtonColor();

        btnAddToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sam = Integer.parseInt(txtSausMash.getText().toString());
                int shp = Integer.parseInt(txtShepsPie.getText().toString());
                int res = Integer.parseInt(txtRibeye.getText().toString());
                int ytl = Integer.parseInt(txtYellTail.getText().toString());

                if(sam != 0 | shp !=0 | res !=0 | ytl !=0) {

                    order_bundle();

                } else {
                    alertDialog = new AlertDialog.Builder(DinnerMenuActivity.this);
                    alertDialog.setTitle(R.string.dinnerAlertTitle);
                    alertDialog.setIcon(R.drawable.stop); // Selected drawable from vector assets
                    alertDialog.setMessage(R.string.dinnerAlertTMsg);
                    alertDialog.setCancelable(false);
                    alertDialog.setPositiveButton(R.string.yes,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    order_bundle();
                                }
                            });
                    alertDialog.setNegativeButton(R.string.no,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog dialog = alertDialog.create();
                    dialog.show();
                }
            }
        });



        final LoadingDialog loadingDialog = new LoadingDialog(DinnerMenuActivity.this);
        loadingDialog.startLoadingDialog();

        reference = FirebaseDatabase.getInstance().getReference().child("DinnerMenu");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    DinnerMenuListItem Dli = dataSnapshot1.getValue(DinnerMenuListItem.class);
                    DinnerMenu.add(Dli);
                    loadingDialog.dismissDialog();
                }

                adapter = new DinnerMenuAdapter(DinnerMenuActivity.this, DinnerMenu);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DinnerMenuActivity.this, "Error downloading information from database, please restart the app and try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static double getDoubleFromString(String s)
    {
        double d;
        try {
            d = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            d = 0.0;
        }
        return d;
    }

    public void order_bundle() {
        order_list();
        Intent mealIntent = new Intent(DinnerMenuActivity.this, DrinksMenuActivity.class);
        mealIntent.putExtra(EXTRA_TEXT_QTY_MEAL, selectionQty);
        mealIntent.putExtra(EXTRA_TEXT_SELECTION_MEAL, selectionDetail);
        mealIntent.putExtra(EXTRA_TEXT_PRICE_MEAL, selectionPrice);
        mealIntent.putExtra(EXTRA_TEXT_TOTAL_MEAL, total1);
        startActivityForResult(mealIntent, REQUEST_CODE);
        selectionQty = "";
        selectionDetail = "";
        selectionPrice = "";
        total1 = getDoubleFromString("");
    }

    public void order_list() {
        String lblSausAndMash = getString(R.string.lblSausAndMash);
        String lblShepsPie = getString(R.string.lblShepsPie);
        String lblRibEye = getString(R.string.lblRibEye);
        String lblYellTail = getString(R.string.lblYellTail);
        int sam = Integer.parseInt(txtSausMash.getText().toString());
        int shp = Integer.parseInt(txtShepsPie.getText().toString());
        int res = Integer.parseInt(txtRibeye.getText().toString());
        int ytl = Integer.parseInt(txtYellTail.getText().toString());
        price_SM = sam * 15.00;
        price_SP = shp * 13.50;
        price_RES = res * 17.50;
        price_YT = ytl * 15.00;
        total1 = price_SM + price_SP + price_RES + price_YT;

        if(sam > 0) {
            selectionQty = selectionQty + sam;
            selectionDetail = selectionDetail + lblSausAndMash;
            selectionPrice = selectionPrice + currency.format(price_SM);
        }
        if(shp > 0) {
            selectionQty = selectionQty + "\n" + shp;
            selectionDetail = selectionDetail + "\n" + lblShepsPie;
            selectionPrice = selectionPrice + "\n" + currency.format(price_SP);
        }
        if(res > 0) {
            selectionQty = selectionQty + "\n" + res;
            selectionDetail = selectionDetail + "\n" + lblRibEye;
            selectionPrice = selectionPrice + "\n" + currency.format(price_RES);
        }
        if(ytl > 0) {
            selectionQty = selectionQty + "\n" + ytl;
            selectionDetail = selectionDetail + "\n" + lblYellTail;
            selectionPrice = selectionPrice + "\n" + currency.format(price_YT);
        }
    }

    public void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerDinnerMenu);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DinnerMenu = new ArrayList<>();
    }

    public void changeButtonColor() {
        FButton fButton;
        fButton = findViewById(R.id.btnAddToOrder);
        fButton.setButtonColor(getResources().getColor(R.color.logoGreen));
    }

    public void SausMashCalled(int qtySM) {
        txtSausMash.setText(String.valueOf(qtySM));
    }

    public void ShepsPieCalled(int qtySP) {
        txtShepsPie.setText(String.valueOf(qtySP));
    }

    public void RibeyeCalled(int qtyRES) {
        txtRibeye.setText(String.valueOf(qtyRES));
    }

    public void YellTailCalled(int qtyYT) {
        txtYellTail.setText(String.valueOf(qtyYT));
    }
}
