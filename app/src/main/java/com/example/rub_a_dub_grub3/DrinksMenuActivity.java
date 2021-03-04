package com.example.rub_a_dub_grub3;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import java.util.Objects;

import Adapter.BreakfastMenuAdapter;
import Adapter.DrinksMenuAdapter;
import Animations.LoadingDialog;
import Model.BreakfastMenuListItem;
import Model.DrinksMenuListItem;
import info.hoang8f.widget.FButton;

public class DrinksMenuActivity extends AppCompatActivity {
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<DrinksMenuListItem> DrinksMenu;
    DrinksMenuAdapter adapter;
    private AlertDialog.Builder alertDialog;

    private FButton btnAddToOrder;
    private TextView txtCoffeeTea;
    private TextView txtCoke;
    private TextView txtOJ;
    private TextView txtLager;

    String selectionQty = "";
    String selectionDetail = "";
    String selectionPrice = "";
    double price_CT = 0, price_CC = 0, price_OJ = 0, price_CL = 0, totalD = 0;

    public static final String EXTRA_TEXT_QTY_DRINKS = "com.example.rub_a_dub_grub3.EXTRA_TEXT_QTY_DRINKS";
    public static final String EXTRA_TEXT_SELECTION_DRINKS = "com.example.rub_a_dub_grub3.EXTRA_TEXT_SELECTION_DRINKS";
    public static final String EXTRA_TEXT_PRICE_DRINKS = "com.example.rub_a_dub_grub3.EXTRA_TEXT_PRICE_DRINKS";
    public static final String EXTRA_TEXT_TOTAL_DRINKS = "com.example.rub_a_dub_grub3.EXTRA_TEXT_TOTAL_DRINKS";
    private final int REQUEST_CODE = 42;

    DecimalFormat currency = new DecimalFormat("###,###.00"); //Last two digits not # signs as they do not force a decimal point if no value to declare

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_drinks_menu );
        this.setTitle( getResources().getString( R.string.drinksMenu ) );

        btnAddToOrder = findViewById(R.id.btnAddToOrder);
        txtCoffeeTea = findViewById(R.id.txtMenuItem1);
        txtCoke = findViewById(R.id.txtMenuItem2);
        txtOJ = findViewById(R.id.txtMenuItem3);
        txtLager = findViewById(R.id.txtMenuItem4);

        buildRecyclerView();
        changeButtonColor();

        btnAddToOrder.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) { // Trying to solve this but can't get the value of the textview = solved by parsing the textview
                int cat = Integer.parseInt(txtCoffeeTea.getText().toString());
                int coke = Integer.parseInt(txtCoke.getText().toString());
                int oj = Integer.parseInt(txtOJ.getText().toString());
                int lag = Integer.parseInt(txtLager.getText().toString());

                if (cat != 0 | coke != 0 | oj != 0 | lag != 0) {
                    order_bundle();
                } else {
                    //Toast.makeText(DrinksMenuActivity.this, "Please add items to place an order.", Toast.LENGTH_SHORT).show();
                    alertDialog = new AlertDialog.Builder(DrinksMenuActivity.this);
                    alertDialog.setTitle(R.string.drinksAlertTitle);
                    alertDialog.setIcon(R.drawable.stop);
                    alertDialog.setMessage(R.string.drinksAlertMsg);
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

        final LoadingDialog loadingDialog = new LoadingDialog(DrinksMenuActivity.this);
        loadingDialog.startLoadingDialog(); // As device is connected to the database this is hardly noticeable, however left it in for slower connections

        reference = FirebaseDatabase.getInstance().getReference().child("DrinksMenu");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    DrinksMenuListItem DLi = dataSnapshot1.getValue(DrinksMenuListItem.class);
                    DrinksMenu.add(DLi);
                    loadingDialog.dismissDialog();
                }

                adapter = new DrinksMenuAdapter(DrinksMenuActivity.this, DrinksMenu);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DrinksMenuActivity.this, "Error downloading information from database, please restart the app and try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static double getDoubleFromString(String s) // Evaluates strings for null values, thus prevents crashes by not passing null values
    {
        double d;
        try {
            d = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            d = 0.0;
        }
        return d;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void order_bundle() {
        order_list();
        Intent i = new Intent(DrinksMenuActivity.this, CheckoutActivity.class);
        Intent intent;
        intent = i.putExtras(Objects.<Bundle>requireNonNull(getIntent().getExtras()));
        i.putExtra(EXTRA_TEXT_QTY_DRINKS, selectionQty);
        i.putExtra(EXTRA_TEXT_SELECTION_DRINKS, selectionDetail);
        i.putExtra(EXTRA_TEXT_PRICE_DRINKS, selectionPrice);
        i.putExtra(EXTRA_TEXT_TOTAL_DRINKS, totalD);
        startActivityForResult(i, REQUEST_CODE);
        //i.putExtras(bundle);
        //startActivity(i);
        selectionQty = "";
        selectionDetail = "";
        selectionPrice = "";
        totalD = getDoubleFromString("");
    }

    public void order_list() {
        String lblCoffeeTea = getString(R.string.lblCoffeeTea);
        String lblCoke = getString(R.string.lblCoke);
        String lblOJ = getString(R.string.lblOJ);
        String lblLager = getString(R.string.lblLager);
        int cat = Integer.parseInt(txtCoffeeTea.getText().toString());
        int coke = Integer.parseInt(txtCoke.getText().toString());
        int oj = Integer.parseInt(txtOJ.getText().toString());
        int lag = Integer.parseInt(txtLager.getText().toString());
        price_CT = cat * 2.00;
        price_CC = coke * 2.00;
        price_OJ = oj * 2.50;
        price_CL = lag * 3.00;
        totalD = price_CT + price_CC + price_OJ + price_CL;

        if(cat > 0) {
            selectionQty = selectionQty + cat;
            selectionDetail = selectionDetail + lblCoffeeTea;
            selectionPrice = selectionPrice + currency.format(price_CT);
        }
        if(coke > 0) {
            selectionQty = selectionQty + "\n" + coke;
            selectionDetail = selectionDetail + "\n" + lblCoke;
            selectionPrice = selectionPrice + "\n" + currency.format(price_CC);
        }
        if(oj > 0) {
            selectionQty = selectionQty + "\n" + oj;
            selectionDetail = selectionDetail + "\n" + lblOJ;
            selectionPrice = selectionPrice + "\n" + currency.format(price_OJ);
        }
        if(lag > 0) {
            selectionQty = selectionQty + "\n" + lag;
            selectionDetail = selectionDetail + "\n" + lblLager;
            selectionPrice = selectionPrice + "\n" + currency.format(price_CL);
        }
    }

    public void openSelectionActivity() {
        Intent intent = new Intent(this, SelectionActivity.class);
        startActivity(intent);
    }

    public void openCheckoutActivity() {
        Intent intent = new Intent(DrinksMenuActivity.this, CheckoutActivity.class);
        startActivity(intent);
    }

    public void buildRecyclerView() { // Created these methods to clear the clutter in the OnCreate
        recyclerView = findViewById(R.id.recyclerDrinksMenu);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DrinksMenu = new ArrayList<>();
    }

    public void changeButtonColor() {
        FButton fButton; // Change button color as it will not hold the change via xml https://github.com/hoang8f/android-flat-button/issues/34
        fButton = findViewById(R.id.btnAddToOrder);
        fButton.setButtonColor(getResources().getColor(R.color.logoGreen));
    }

    public void CoffeeTeaCalled(int qtyCT) {
        txtCoffeeTea.setText(String.valueOf(qtyCT));
    }

    public void CokeCalled(int qtyCC) {
        txtCoke.setText(String.valueOf(qtyCC));
    }

    public void OJCalled(int qtyOJ) {
        //int qtyCB = qtyBE;
        txtOJ.setText(String.valueOf(qtyOJ));
    }

    public void LagerCalled(int qtyCL) {
        //int qtyEB = qtyBE;
        txtLager.setText(String.valueOf(qtyCL));
    }
}
