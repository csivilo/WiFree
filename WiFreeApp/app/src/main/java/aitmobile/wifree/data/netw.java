package aitmobile.wifree.data;

/**
 * Created by C_lo on 12/9/2016.
 */

public class Netw {
    private String SSID;
    private String key;

    public Netw(String name, String pass){
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

    public String toString(){
        return ("SSID: " + getSSID() + "\n\n" + "Password: " + getKey());
    }
}

