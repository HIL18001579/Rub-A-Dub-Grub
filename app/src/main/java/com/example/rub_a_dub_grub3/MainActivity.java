package com.example.rub_a_dub_grub3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import info.hoang8f.widget.FButton;

public class MainActivity extends AppCompatActivity {

    private FButton btnEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(!isConnected(MainActivity.this)) buildDialog(MainActivity.this).show(); // Check for internet connection - stops the possibility of app hanging on loading dialog if no internet access
        else {

        }

        FButton fButton; // Change button color as it will not hold the change via xml https://github.com/hoang8f/android-flat-button/issues/34
        fButton = findViewById(R.id.btnEnter);
        fButton.setButtonColor(getResources().getColor(R.color.logoGreen));

        btnEnter = findViewById(R.id.btnEnter);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectionActivity();
            }
        });
    }

    public void openSelectionActivity() {
        Intent intent = new Intent(this, SelectionActivity.class);
        startActivity(intent);
    }
// Connected to internet check and alert builder (next two methods)
    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            return (mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting());
        } else
        return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setIcon( android.R.drawable.ic_dialog_alert );
        builder.setTitle("No Internet Connection");
        builder.setMessage("This app requires internet access. please connect to the internet. Alternatively see the signs on display explaining how to connect to our Wi-Fi");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish(); // Closes the app

                startActivityForResult( new Intent( Settings.ACTION_WIFI_SETTINGS ), 0 ); // Redirects to Wi-Fi settings on phone (not sure about this as it could scare people knowing the control that apps can have), if anything it shows what can be done)
            }
        });
        return builder;
    }
}
