package aitmobile.wifree.data;

/**
 * Created by C_lo on 12/9/2016.
 */

public class netw {
    private String SSID;
    private String key;

    public void netw(String name, String pass){
        this.SSID = name;
        this.key = pass;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }
}
