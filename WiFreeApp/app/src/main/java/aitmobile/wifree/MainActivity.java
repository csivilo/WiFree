package aitmobile.wifree;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import aitmobile.wifree.data.netw;

public class MainActivity extends AppCompatActivity {

    private Button btnAdd;
    private LinearLayout linLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linLayout = (LinearLayout) findViewById(R.id.activity_main);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNetwork();
            }
        });
    }

    public void addNetwork(){

        WifiManager wifiMan;
        wifiMan = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        WifiInfo wifiInfo = wifiMan.getConnectionInfo();

        showToastMessage(wifiInfo.getSSID());


    }

    private void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void showSimpleSnackbarMessage(String message) {

        Snackbar.make(linLayout, message, Snackbar.LENGTH_LONG).show();
    }
}
