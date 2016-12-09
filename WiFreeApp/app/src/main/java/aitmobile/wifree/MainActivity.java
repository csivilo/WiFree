package aitmobile.wifree;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import aitmobile.wifree.data.Netw;
import aitmobile.wifree.fragments.MessageFragment;


public class MainActivity extends AppCompatActivity {


    public static final String KEY_MSG = "KEY_MSG";

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
                addNetworkFragment();
            }
        });
    }


    private void addNetworkFragment() {
        MessageFragment messageFragment = new MessageFragment();
        messageFragment.setCancelable(false);

        Bundle bundle = new Bundle();
        bundle.putString(KEY_MSG,getString(R.string.netw_add));
        messageFragment.setArguments(bundle);

        messageFragment.show(getSupportFragmentManager(),
                "MessageFragment");

    }

    public void addCurrNetwork(String passwd){
        String SSID;
        WifiManager wifiMan;
        Netw ourNet;

        wifiMan = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        WifiInfo wifiInfo = wifiMan.getConnectionInfo();
        SSID = wifiInfo.getSSID();
        ourNet = new Netw(SSID,passwd);

        showToastMessage(ourNet.toString());
    }

    private void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showSimpleSnackbarMessage(String message) {

        Snackbar.make(linLayout, message, Snackbar.LENGTH_LONG).show();
    }

}
