package com.example.rub_a_dub_grub3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

import info.hoang8f.widget.FButton;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener {

    //private static final String TAG = "CheckoutActivity"; // Debug
    
    private TextView txtTableSelected;
    private TextView txtDetailQty1;
    private TextView txtDetailQty2;
    private TextView txtDetailView1;
    private TextView txtDetailView2;
    private TextView txtDetailPrice1;
    private TextView txtDetailPrice2;
    private TextView txtTotal;

    //private static final String TAG = "CheckoutActivity";
    private SharedPreferences myPref;
    private SharedPreferences.Editor myEditor;

    public String getSring;

    double totalMeal = 0, totalDrinks = 0, total=0;

    String grandTotal;

    DecimalFormat currencyGBP = new DecimalFormat("£###,##0.00");

    /* @Override
    protected void onPause() {
        super.onPause();
        if(txtDetailQty1.hasSelection())
        myPref.edit().putBoolean("TextInTxtDetailQty1", true).apply();
        txtDetailQty1.setText(txtDetailQty1.getText());
    } */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        this.setTitle(getResources().getString(R.string.checkout));

        /* myPref = PreferenceManager.getDefaultSharedPreferences(this);
        myEditor = myPref.edit(); */

        //Bundle extras = getIntent().getExtras();

        Layout_Views();
        changeButtonColor();
        OnClick_Vars();

        Intent i = getIntent();
        //if (extras !=null) {
            String qty = i.getStringExtra(BreakfastMenuActivity.EXTRA_TEXT_QTY_MEAL);
            String qtyD = i.getStringExtra(DrinksMenuActivity.EXTRA_TEXT_QTY_DRINKS);
            String details = i.getStringExtra(BreakfastMenuActivity.EXTRA_TEXT_SELECTION_MEAL);
            String detailsD = i.getStringExtra(DrinksMenuActivity.EXTRA_TEXT_SELECTION_DRINKS);
            String price = i.getStringExtra(BreakfastMenuActivity.EXTRA_TEXT_PRICE_MEAL);
            String priceD = i.getStringExtra(DrinksMenuActivity.EXTRA_TEXT_PRICE_DRINKS);
            totalMeal = i.getDoubleExtra(BreakfastMenuActivity.EXTRA_TEXT_TOTAL_MEAL, 0.00);
            totalDrinks = i.getDoubleExtra(DrinksMenuActivity.EXTRA_TEXT_TOTAL_DRINKS, 0.00);
            //totalDrinks = 0;
        //String qtD = extras.getString("qtyD");
            //txtDetailView.setText(FE_Order);
        //}

        //Bundle bundle = getIntent().getExtras();
        //assert bundle != null;
        //String qty = bundle.getString("qty");
        //String qtyD = bundle.getString("qtyD");
        //String details = bundle.getString("selection");
        //String detailsD = bundle.getString("selectionD");
        //String price = bundle.getString("price");
        //String priceD = bundle.getString("priceD");
        //String total1 = bundle.getString("total1");
        //String totalD = bundle.getString("totalD");

        total = totalMeal + totalDrinks;
        grandTotal = currencyGBP.format(total);

        txtDetailQty1.setText(qty);
        txtDetailQty2.setText(qtyD);
        txtDetailView1.setText(details);
        txtDetailView2.setText(detailsD);
        txtDetailPrice1.setText(price);
        txtDetailPrice2.setText(priceD);
        txtTotal.setText(grandTotal);

        /* checkSharedPreferences();

        myEditor.putString("qty_meal", txtDetailQty1.getText().toString());
        myEditor.putString("details_meal", txtDetailView1.getText().toString());
        myEditor.putString("price_meal", txtDetailPrice1.getText().toString());
        myEditor.commit(); */

        //Log.d(TAG, "onCreate: name: " + qty_meal);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTBL1:
                Toast.makeText(this, "Table 1 selected", Toast.LENGTH_SHORT).show();
                txtTableSelected.setText("1");
                break;
            case R.id.btnTBL2:
                Toast.makeText(this, "Table 2 selected", Toast.LENGTH_SHORT).show();
                txtTableSelected.setText("2");
                break;
            case R.id.btnTBL3:
                Toast.makeText(this, "Table 3 selected", Toast.LENGTH_SHORT).show();
                txtTableSelected.setText("3");
                break;
            case R.id.btnTBL4:
                Toast.makeText(this, "Table 4 selected", Toast.LENGTH_SHORT).show();
                txtTableSelected.setText("4");
                break;
            case R.id.btnTBL5:
                Toast.makeText(this, "Table 5 selected", Toast.LENGTH_SHORT).show();
                txtTableSelected.setText("5");
                break;
            case R.id.btnReStart:
                /* // Save qty
                String qty_meal = txtDetailQty1.getText().toString();
                myEditor.putString(getString(R.string.qty_meal), qty_meal);
                myEditor.commit();
                // Save details
                String detail_meal = txtDetailView1.getText().toString();
                myEditor.putString(getString(R.string.detail_meal), detail_meal);
                myEditor.commit();
                // Save price
                String price_meal = txtDetailPrice1.getText().toString();
                myEditor.putString(getString(R.string.price_meal), price_meal);
                myEditor.commit(); */
                openSelectionActivity();
                finish();
                break;
        }
    }

    public void Layout_Views() {
        // Initialise textviews
        txtDetailQty1 = findViewById(R.id.txtDetailQty1);
        txtDetailQty2 = findViewById(R.id.txtDetailQty2);
        txtDetailView1 = findViewById(R.id.txtDetailView1);
        txtDetailView2 = findViewById(R.id.txtDetailView2);
        txtDetailPrice1 = findViewById(R.id.txtDetailPrice1);
        txtDetailPrice2 = findViewById(R.id.txtDetailPrice2);
        txtTotal = findViewById(R.id.txtTotal);
    }

    public void changeButtonColor() {
        FButton btnTBL1 = findViewById(R.id.btnTBL1);
        FButton btnTBL2 = findViewById(R.id.btnTBL2);
        FButton btnTBL3 = findViewById(R.id.btnTBL3);
        FButton btnTBL4 = findViewById(R.id.btnTBL4);
        FButton btnTBL5 = findViewById(R.id.btnTBL5);
        FButton btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        btnTBL1.setButtonColor(getResources().getColor(R.color.logoGreen));
        btnTBL2.setButtonColor(getResources().getColor(R.color.logoGreen));
        btnTBL3.setButtonColor(getResources().getColor(R.color.logoGreen));
        btnTBL4.setButtonColor(getResources().getColor(R.color.logoGreen));
        btnTBL5.setButtonColor(getResources().getColor(R.color.logoGreen));
        btnPlaceOrder.setButtonColor(getResources().getColor(R.color.logoGreen));
    }

    public void OnClick_Vars() {
        txtTableSelected = findViewById(R.id.txtTableSelected);
        // Buttons & vars for OnClickListener - tables - https://www.youtube.com/watch?v=GtxVILjLcw8
        FButton btnTBL1 = findViewById(R.id.btnTBL1);
        FButton btnTBL2 = findViewById(R.id.btnTBL2);
        FButton btnTBL3 = findViewById(R.id.btnTBL3);
        FButton btnTBL4 = findViewById(R.id.btnTBL4);
        FButton btnTBL5 = findViewById(R.id.btnTBL5);
        // Other button vars
        FButton btnRestart = findViewById(R.id.btnReStart);

        // OnclickListener items implemented via main class
        btnTBL1.setOnClickListener(this);
        btnTBL2.setOnClickListener(this);
        btnTBL3.setOnClickListener(this);
        btnTBL4.setOnClickListener(this);
        btnTBL5.setOnClickListener(this);
        btnRestart.setOnClickListener(this);
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

    /* private void checkSharedPreferences() {
        String qty_meal = myPref.getString(getString(R.string.qty_meal), "");
        String detail_meal = myPref.getString(getString(R.string.detail_meal), "");
        String price_meal = myPref.getString(getString(R.string.price_meal), "");

        txtDetailQty1.setText(qty_meal);
        txtDetailView1.setText(detail_meal);
        txtDetailPrice1.setText(price_meal);
    } */

    public void openSelectionActivity() {
        Intent intent = new Intent( this, SelectionActivity.class );
        startActivity( intent );
    }
}
