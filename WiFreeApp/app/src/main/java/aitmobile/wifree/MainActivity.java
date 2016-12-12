package aitmobile.wifree;

import android.content.Context;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import aitmobile.wifree.adapter.NetworkAdapter;
import aitmobile.wifree.data.Network;
import aitmobile.wifree.fragments.MessageFragment;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback {


    public static final String KEY_MSG = "KEY_MSG";


    private WifiManager wifiMan;
    private Button btnAdd;
    private LinearLayout linLayout;
    private NetworkAdapter netwAdapter;
    private RecyclerView recyclerNetwork;
    private LocationManager locoMan;
    private GoogleMap mMap;
    private GoogleApiClient myGoogleApiClient;
    double lon;
    double lat;
    private LocationRequest myLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(myLocationRequest);
        final PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(myGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        myLocationRequest = new LocationRequest();
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
        myGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .build();


        linLayout = (LinearLayout) findViewById(R.id.activity_main);
        recyclerNetwork = (RecyclerView) findViewById(R.id.recyclerViewNewt);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerNetwork.setLayoutManager(mLayoutManager);
        recyclerNetwork.setHasFixedSize(true);

        netwAdapter = new NetworkAdapter(this);

        recyclerNetwork.setAdapter(netwAdapter);


        this.wifiMan = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNetworkFragment();
            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onStart() {
        myGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        myGoogleApiClient.disconnect();
        super.onStop();
    }



    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng szilard = new LatLng(47.490915, 19.070002);
        LatLng sierra = new LatLng(34.170871, -118.031933);
        addNetworkToMap(mMap,"UPC0777948","VOQAKOZE",szilard);
        addNetworkToMap(mMap, "Sivilotti", "carloandali",sierra);
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void addNetworkToMap(GoogleMap gmap, String SSID, String key) {
        
    }


    public void addNetworkToMap(GoogleMap gmap, String SSID, String key, LatLng loc) {

        Marker newmark;
//        double lati = 47.562478;
//        double longi = 19.055066;



        try {
            Location location = LocationServices.FusedLocationApi.getLastLocation(myGoogleApiClient);

            if(location != null) {
                lat = location.getLatitude();
                lon = location.getLongitude();
            }

            LatLng currLoc =  new LatLng(lat, lon);


            newmark = gmap.addMarker(new MarkerOptions()
                    .position(currLoc)
                    .title(SSID)
                    .snippet(key)
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("wifi", 75, 75))));
            gmap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    downloadNetwork(marker.getTitle(), marker.getSnippet());
                    return true;
                }
            });

            mMap.moveCamera(CameraUpdateFactory.newLatLng(currLoc));
        } catch (SecurityException ex) {

        }
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

    public void downloadNetwork(String SSID, String key){
        showToastMessage(SSID+"   "+key);
        WifiConfiguration wifiConfig = new WifiConfiguration();


        wifiConfig.SSID = "\""+SSID+"\"";
        wifiConfig.priority = 42;
        wifiConfig.status = WifiConfiguration.Status.DISABLED;

        wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        wifiConfig.preSharedKey = "\""+key+"\"";



        int netwId = this.wifiMan.addNetwork(wifiConfig);
        this.wifiMan.disconnect();
        this.wifiMan.enableNetwork(netwId,true);
        this.wifiMan.reconnect();

    }

    public void addCurrNetwork(String passwd){
        String SSID;
        Network ourNet;


        this.wifiMan = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        WifiInfo wifiInfo = this.wifiMan.getConnectionInfo();
        SSID = wifiInfo.getSSID();
        CharSequence blank = "";
        CharSequence topq = "\"";

        SSID = SSID.replace(topq,blank);
        ourNet = new Network(SSID,passwd);


        showToastMessage(ourNet.toString());

        netwAdapter.addNetwork(new Network(SSID, passwd));
        addNetworkToMap(mMap,SSID,passwd);


    }

    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showSimpleSnackbarMessage(String message) {

        Snackbar.make(linLayout, message, Snackbar.LENGTH_LONG).show();
    }


    public Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

}
