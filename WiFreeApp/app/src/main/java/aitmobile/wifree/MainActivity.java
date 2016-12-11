package aitmobile.wifree;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import aitmobile.wifree.adapter.NetwAdapter;
import aitmobile.wifree.data.Netw;
import aitmobile.wifree.fragments.MessageFragment;


public class MainActivity extends AppCompatActivity {


    public static final String KEY_MSG = "KEY_MSG";


    private WifiManager wifiMan;
    private Button btnAdd;
    private LinearLayout linLayout;
    private NetwAdapter netwAdapter;
    private RecyclerView recyclerNetw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linLayout = (LinearLayout) findViewById(R.id.activity_main);
        recyclerNetw = (RecyclerView) findViewById(R.id.recyclerViewNewt);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerNetw.setLayoutManager(mLayoutManager);
        recyclerNetw.setHasFixedSize(true);

        netwAdapter = new NetwAdapter(this);

        recyclerNetw.setAdapter(netwAdapter);


        this.wifiMan = (WifiManager) getSystemService(Context.WIFI_SERVICE);

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

    public void downloadNetw(String SSID, String key){
        WifiConfiguration wifiConfig = new WifiConfiguration();

        wifiConfig.SSID = String.format("\"%S\"", SSID);
        wifiConfig.preSharedKey = String.format("\"%S\"", key);

        int netwId = this.wifiMan.addNetwork(wifiConfig);
        this.wifiMan.disconnect();
        this.wifiMan.enableNetwork(netwId,true);
        this.wifiMan.reconnect();

    }

    public void addCurrNetwork(String passwd){
        String SSID;
        Netw ourNet;


        this.wifiMan = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        WifiInfo wifiInfo = this.wifiMan.getConnectionInfo();
        SSID = wifiInfo.getSSID();
        ourNet = new Netw(SSID,passwd);


        showToastMessage(ourNet.toString());

        netwAdapter.addNetw(new Netw(SSID, passwd));


    }

    private void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showSimpleSnackbarMessage(String message) {

        Snackbar.make(linLayout, message, Snackbar.LENGTH_LONG).show();
    }

}
