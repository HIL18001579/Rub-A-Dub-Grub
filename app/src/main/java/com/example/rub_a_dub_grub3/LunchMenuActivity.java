package com.example.rub_a_dub_grub3;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

import Adapter.LunchMenuAdapter;
import Animations.LoadingDialog;
import Model.LunchMenuListItem;
import Model.QtyOrderList;
import info.hoang8f.widget.FButton;

public class LunchMenuActivity extends AppCompatActivity {

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<LunchMenuListItem> LunchMenu;
    LunchMenuAdapter adapter;
    private AlertDialog.Builder alertDialog;

    private FButton btnAddToOrder;
    private FButton btnDrinks;

    private TextView txtChickSalad;
    private TextView txtPregoRoll;
    private TextView txtPloughmans;
    private TextView txtFishGoujons;

    public static final String EXTRA_TEXT_QTY_MEAL = "com.example.rub_a_dub_grub3.EXTRA_TEXT_QTY_MEAL";
    public static final String EXTRA_TEXT_SELECTION_MEAL = "com.example.rub_a_dub_grub3.EXTRA_TEXT_SELECTION_MEAL";
    public static final String EXTRA_TEXT_PRICE_MEAL = "com.example.rub_a_dub_grub3.EXTRA_TEXT_PRICE_MEAL";
    public static final String EXTRA_TEXT_TOTAL_MEAL = "com.example.rub_a_dub_grub3.EXTRA_TEXT_TOTAL_MEAL";
    private final int REQUEST_CODE = 42;

    String selectionQty = "";
    String selectionDetail = "";
    String selectionPrice = "";
    double price_CS = 0, price_PR = 0, price_PM = 0, price_FG = 0, total1 = 0;

    DecimalFormat currency = new DecimalFormat("###,###.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_lunch_menu );
        this.setTitle( getResources().getString( R.string.lunchMenu ) );
        btnAddToOrder = findViewById(R.id.btnAddToOrder);
        txtChickSalad = findViewById(R.id.txtMenuItem1);
        txtPregoRoll = findViewById(R.id.txtMenuItem2);
        txtPloughmans = findViewById(R.id.txtMenuItem3);
        txtFishGoujons = findViewById(R.id.txtMenuItem4);

        buildRecyclerView();
        changeButtonColor();

        btnAddToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int lcs = Integer.parseInt(txtChickSalad.getText().toString());
                int lpr = Integer.parseInt(txtPregoRoll.getText().toString());
                int lpm = Integer.parseInt(txtPloughmans.getText().toString());
                int lfg = Integer.parseInt(txtFishGoujons.getText().toString());

                if(lcs != 0 | lpr !=0 | lpm !=0 | lfg !=0) {

                    order_bundle();

                } else {
                    alertDialog = new AlertDialog.Builder(LunchMenuActivity.this);
                    alertDialog.setTitle(R.string.lunchAlertTitle);
                    alertDialog.setIcon(R.drawable.stop);
                    alertDialog.setMessage(R.string.lunchAlertMsg);
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



        final LoadingDialog loadingDialog = new LoadingDialog(LunchMenuActivity.this);
        loadingDialog.startLoadingDialog();

        reference = FirebaseDatabase.getInstance().getReference().child("LunchMenu");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    LunchMenuListItem Lli = dataSnapshot1.getValue(LunchMenuListItem.class);
                    LunchMenu.add(Lli);
                    loadingDialog.dismissDialog();
                }

                adapter = new LunchMenuAdapter(LunchMenuActivity.this, LunchMenu);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LunchMenuActivity.this, "Error downloading information from database, please restart the app and try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openDrinksMenuActivity() {
        Intent intent = new Intent(this, DrinksMenuActivity.class);
        startActivity(intent);
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
        Intent mealIntent = new Intent(LunchMenuActivity.this, DrinksMenuActivity.class);
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
        String lblChickSalad = getString(R.string.lblChickSalad);
        String lblPregoRoll = getString(R.string.lblPregoRoll);
        String lblPloughmans = getString(R.string.lblPloughmans);
        String lblFishGoujons = getString(R.string.lblFishGoujons);
        int lcs = Integer.parseInt(txtChickSalad.getText().toString());
        int lpr = Integer.parseInt(txtPregoRoll.getText().toString());
        int lpm = Integer.parseInt(txtPloughmans.getText().toString());
        int lfg = Integer.parseInt(txtFishGoujons.getText().toString());
        price_CS = lcs * 8.50;
        price_PR = lpr * 15.00;
        price_PM = lpm * 12.50;
        price_FG = lfg * 12.00;
        total1 = price_CS + price_PR + price_PM + price_FG;

        if(lcs > 0) {
            selectionQty = selectionQty + lcs;
            selectionDetail = selectionDetail + lblChickSalad;
            selectionPrice = selectionPrice + currency.format(price_CS);
        }
        if(lpr > 0) {
            selectionQty = selectionQty + "\n" + lpr;
            selectionDetail = selectionDetail + "\n" + lblPregoRoll;
            selectionPrice = selectionPrice + "\n" + currency.format(price_PR);
        }
        if(lpm > 0) {
            selectionQty = selectionQty + "\n" + lpm;
            selectionDetail = selectionDetail + "\n" + lblPloughmans;
            selectionPrice = selectionPrice + "\n" + currency.format(price_PM);
        }
        if(lfg > 0) {
            selectionQty = selectionQty + "\n" + lfg;
            selectionDetail = selectionDetail + "\n" + lblFishGoujons;
            selectionPrice = selectionPrice + "\n" + currency.format(price_FG);
        }
    }

    public void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerLunchMenu);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LunchMenu = new ArrayList<>();
    }

    public void changeButtonColor() {
        FButton fButton;
        fButton = findViewById(R.id.btnAddToOrder);
        fButton.setButtonColor(getResources().getColor(R.color.logoGreen));
    }

    public void ChickSaladCalled(int qtyCS) {
        txtChickSalad.setText(String.valueOf(qtyCS));
    }

    public void PregoRollCalled(int qtyPR) {
        txtPregoRoll.setText(String.valueOf(qtyPR));
    }

    public void PloghmansCalled(int qtyPM) {
        txtPloughmans.setText(String.valueOf(qtyPM));
    }

    public void FishGoujonsCalled(int qtyFC) {
        txtFishGoujons.setText(String.valueOf(qtyFC));
    }
}

