package aitmobile.wifree.data;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by C_lo on 12/9/2016.
 */

public class Network extends SugarRecord implements Serializable{
    private String SSID;
    private String key;

    public Network(String name, String pass){
        this.SSID = name;
        this.key = pass;
    }

    public Network(){}


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

