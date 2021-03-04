package com.example.rub_a_dub_grub3;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.savedstate.SavedStateRegistry;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;

import Adapter.BreakfastMenuAdapter;
import Animations.LoadingDialog;
import Model.BreakfastMenuListItem;
import Model.QtyOrderList;
import info.hoang8f.widget.FButton;

public class BreakfastMenuActivity extends AppCompatActivity {

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<BreakfastMenuListItem> BreakfastMenu;
    BreakfastMenuAdapter adapter;
    private AlertDialog.Builder alertDialog;

    private FButton btnAddToOrder;
    private FButton btnDrinks;

    ArrayList<QtyOrderList> QtyOrdered;
    private TextView txtQtyFullEng;
    private TextView txtQtyEggsBen;
    private TextView txtQtyContinental;
    private TextView txtQtyRADOmlette;

    //public static final String SHARED_PREF = "BF_shared";
    //public static final String TEXT = "FE_text";
    public static final String EXTRA_TEXT_QTY_MEAL = "com.example.rub_a_dub_grub3.EXTRA_TEXT_QTY_MEAL";
    public static final String EXTRA_TEXT_SELECTION_MEAL = "com.example.rub_a_dub_grub3.EXTRA_TEXT_SELECTION_MEAL";
    public static final String EXTRA_TEXT_PRICE_MEAL = "com.example.rub_a_dub_grub3.EXTRA_TEXT_PRICE_MEAL";
    public static final String EXTRA_TEXT_TOTAL_MEAL = "com.example.rub_a_dub_grub3.EXTRA_TEXT_TOTAL_MEAL";
    private final int REQUEST_CODE = 42;

    //private ImageView imgRemove;
    //private FButton btnAdd;
    String selectionQty = "";
    String selectionDetail = "";
    String selectionPrice = "";
    double price_FE = 0, price_EB = 0, price_CON = 0, price_RADO = 0, total1 = 0;

    DecimalFormat currency = new DecimalFormat("###,###.00"); //Last two digits not # signs as they do not force a decimal point if no value to declare

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast_menu);
        this.setTitle(getResources().getString(R.string.breakfastMenu));

        btnAddToOrder = findViewById(R.id.btnAddToOrder);
        txtQtyFullEng = findViewById(R.id.txtMenuItem1);
        txtQtyEggsBen = findViewById(R.id.txtMenuItem2);
        txtQtyContinental = findViewById(R.id.txtMenuItem3);
        txtQtyRADOmlette = findViewById(R.id.txtMenuItem4);

        buildRecyclerView();
        changeButtonColor();

        //btnSelection.setOnClickListener(new View.OnClickListener() {
        //@Override
        //public void onClick(View v) {
        //openSelectionActivity();
        //}
        //});

        btnAddToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // Trying to solve this but can't get the value of the textview = solved by parsing the textview
                int bfe = Integer.parseInt(txtQtyFullEng.getText().toString());
                int beb = Integer.parseInt(txtQtyEggsBen.getText().toString());
                int bcon = Integer.parseInt(txtQtyContinental.getText().toString());
                int brado = Integer.parseInt(txtQtyRADOmlette.getText().toString());

                if(bfe != 0 | beb !=0 | bcon !=0 | brado !=0) {
                    order_bundle();
                    //Intent intent = new Intent(BreakfastMenuActivity.this, CheckoutActivity.class);
                    //intent.putExtra("FE_Order", String.valueOf(bfe));
                    //intent.putExtra("BE_Order", beb);
                    //startActivityForResult(intent, REQUEST_CODE);
                    //openCheckoutActivity();
                } else {
                    //Toast.makeText(BreakfastMenuActivity.this, "Please add items to place an order.", Toast.LENGTH_SHORT).show();
                    alertDialog = new AlertDialog.Builder(BreakfastMenuActivity.this);
                    alertDialog.setTitle(R.string.breakfastAlertTitle);
                    alertDialog.setIcon(R.drawable.stop);
                    alertDialog.setMessage(R.string.breakfastAlertTMsg);
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



        final LoadingDialog loadingDialog = new LoadingDialog(BreakfastMenuActivity.this);
        loadingDialog.startLoadingDialog(); // As device is connected to the database this is hardly noticeable, however left it in for slower connections

        reference = FirebaseDatabase.getInstance().getReference().child("BreakfastMenu");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    BreakfastMenuListItem Bli = dataSnapshot1.getValue(BreakfastMenuListItem.class);
                    BreakfastMenu.add(Bli);
                    loadingDialog.dismissDialog();
                }

                adapter = new BreakfastMenuAdapter(BreakfastMenuActivity.this, BreakfastMenu);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(BreakfastMenuActivity.this, "Error downloading information from database, please restart the app and try again", Toast.LENGTH_SHORT).show();
            }
        });
        //update();
    }

    private void openDrinksMenuActivity() {
        Intent intent = new Intent(this, DrinksMenuActivity.class);
        startActivity(intent);
    }

    //private void update() {
    //SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
    //text = sharedPreferences.getString(TEXT, qtyFE);
    //txtQtyFullEng.setText(text);
    //}

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

    public void order_bundle() { //https://stackoverflow.com/questions/9886866/how-to-restore-values-of-a-activity-when-i-move-to-next-activity-and-come-back-a
        order_list();
        Intent mealIntent = new Intent(BreakfastMenuActivity.this, DrinksMenuActivity.class);
        //Bundle bundle = new Bundle();
        mealIntent.putExtra(EXTRA_TEXT_QTY_MEAL, selectionQty);
        mealIntent.putExtra(EXTRA_TEXT_SELECTION_MEAL, selectionDetail);
        mealIntent.putExtra(EXTRA_TEXT_PRICE_MEAL, selectionPrice);
        mealIntent.putExtra(EXTRA_TEXT_TOTAL_MEAL, total1);
        //i.putExtras(bundle);
        startActivityForResult(mealIntent, REQUEST_CODE);
        //setResult(REQUEST_CODE, i);
        //openCheckoutActivity();
        selectionQty = "";
        selectionDetail = "";
        selectionPrice = "";
        total1 = getDoubleFromString("");
    }

    public void order_list() {
        String lblFullEng = getString(R.string.lblFullEng);
        String lblEggsBen = getString(R.string.lblEggsBen);
        String lblContinetal = getString(R.string.lblContinetal);
        String lblRADOmelette = getString(R.string.lblRADOmelette);
        int bfe = Integer.parseInt(txtQtyFullEng.getText().toString());
        int beb = Integer.parseInt(txtQtyEggsBen.getText().toString());
        int bcon = Integer.parseInt(txtQtyContinental.getText().toString());
        int brado = Integer.parseInt(txtQtyRADOmlette.getText().toString());
        price_FE = bfe * 15.00;
        price_EB = beb * 12.00;
        price_CON = bcon * 10.00;
        price_RADO = brado * 10.00;
        total1 = price_FE + price_EB + price_CON + price_RADO;

        if(bfe > 0) {
            selectionQty = selectionQty + bfe;
            selectionDetail = selectionDetail + lblFullEng;
            selectionPrice = selectionPrice + currency.format(price_FE);
        }
        if(beb > 0) {
            selectionQty = selectionQty + "\n" + beb;
            selectionDetail = selectionDetail + "\n" + lblEggsBen;
            selectionPrice = selectionPrice + "\n" + currency.format(price_EB);
        }
        if(bcon > 0) {
            selectionQty = selectionQty + "\n" + bcon;
            selectionDetail = selectionDetail + "\n" + lblContinetal;
            selectionPrice = selectionPrice + "\n" + currency.format(price_CON);
        }
        if(brado > 0) {
            selectionQty = selectionQty + "\n" + brado;
            selectionDetail = selectionDetail + "\n" + lblRADOmelette;
            selectionPrice = selectionPrice + "\n" + currency.format(price_RADO);
        }
    }

    /* public void openSelectionActivity() {
        Intent intent = new Intent(this, SelectionActivity.class);
        startActivity(intent);
    }

    public void openCheckoutActivity() {
        Intent intent = new Intent(this, CheckoutActivity.class);
        startActivity(intent);
    } */

    public void buildRecyclerView() { // Created these methods to clear the clutter in the OnCreate
        recyclerView = findViewById(R.id.recyclerBreakfastMenu);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BreakfastMenu = new ArrayList<>();
        //btnAddToOrder = findViewById(R.id.btnAddToOrder);
        //txtQtyFullEng = findViewById(R.id.txtQtyFullEng);
    }

    public void changeButtonColor() {
        FButton fButton; // Change button color as it will not hold the change via xml https://github.com/hoang8f/android-flat-button/issues/34
        fButton = findViewById(R.id.btnAddToOrder);
        fButton.setButtonColor(getResources().getColor(R.color.logoGreen));
    }

    public void FullEngCalled(int qtyFE) {
        //int qtyfE = qtyFE;
        txtQtyFullEng.setText(String.valueOf(qtyFE));
        //SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putString(TEXT, txtQtyFullEng.getText().toString());

        //getString = txtQtyFullEng.getText().toString();
    }

    public void EggsBenCalled(int qtyEB) {
        //int qtyEB = qtyBE;
        txtQtyEggsBen.setText(String.valueOf(qtyEB));
    }

    public void ContinentalCalled(int qtyCB) {
        //int qtyCB = qtyBE;
        txtQtyContinental.setText(String.valueOf(qtyCB));
    }

    public void OmeletteCalled(int qtyOB) {
        //int qtyEB = qtyBE;
        txtQtyRADOmlette.setText(String.valueOf(qtyOB));
    }
}
