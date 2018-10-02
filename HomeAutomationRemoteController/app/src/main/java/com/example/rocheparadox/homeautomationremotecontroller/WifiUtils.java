package com.example.rocheparadox.homeautomationremotecontroller;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class WifiUtils extends AppCompatActivity {

    static WifiManager wifiManager ;
    WifiUtils(WifiManager wifiManager){
        this.wifiManager = wifiManager;
    }
    public void disconnectButtonPressed(android.view.View view){
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifiManager.disconnect();

    }
    public static boolean connectToHomeAutomationNetwork() {
        boolean isConnected = false;
        String SSID = HomeAutomationConstants.homeAutomationWifiSSID;
        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        boolean isWifiConfigurationAvailable = false;
        Log.i("MyInfo", "Available Networks");
        for (WifiConfiguration i : list) {
            Log.i("MyInfo", i.SSID);
            if (i.SSID != null && i.SSID.equals("\"" + SSID + "\"")) {
                Log.i("MyInfo", i.SSID);
                isWifiConfigurationAvailable = true;
                Log.i("HARC_info", "Connecting to "+HomeAutomationConstants.homeAutomationWifiSSID);
                wifiManager.disconnect();
                wifiManager.enableNetwork(i.networkId, true);
                isConnected = wifiManager.reconnect();
                break;
            }
        }

        if (!isWifiConfigurationAvailable) {
            addHomeAutomationNetwork();
            list = wifiManager.getConfiguredNetworks();
            for (WifiConfiguration i : list) {
                Log.i("MyInfo", "Available Networks");
                if (i.SSID != null && i.SSID.equals("\"" + SSID + "\"")) {
                    Log.i("MyInfo", "Going to Connect to " + i.SSID);
                    Log.i("HARC_info", "Connecting to "+HomeAutomationConstants.homeAutomationWifiSSID);
                    wifiManager.disconnect();
                    wifiManager.enableNetwork(i.networkId, true);
                    isConnected = wifiManager.reconnect();
                    break;
                }
            }
        }
        return isConnected;
    }

    private static void addHomeAutomationNetwork(){
        Log.i("MyInfo","Adding wifi Configuration");
        String SSID = HomeAutomationConstants.homeAutomationWifiSSID;
        String pass = HomeAutomationConstants.homeAutomationWifiPasskey;

        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\""+SSID+"\"";
        conf.preSharedKey = "\""+pass+"\"";
        wifiManager.addNetwork(conf);
    }

    private void turnOnWifi(){
        Log.i("MyInfo","Turning On wifi");
        WifiManager wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        if(!wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(true);
        }

    }

    public static boolean isHomeAutomationWifiAvailable(){
        boolean isAvailable = false;
        boolean b = wifiManager.startScan();
        Log.i("HARC_info","isWifiScanned: "+Boolean.toString(b));
        String SSID = "Home_automation_check";
        List<ScanResult> scanResult = wifiManager.getScanResults();
        Log.i("HARC_info",scanResult.toString());
        Log.i("HARC_info","Available Networks");
        for( ScanResult i : scanResult ) {
            Log.i("HARC_info",i.SSID);
            if(i.SSID != null && i.SSID.equals(SSID)) {
                Log.i("HARC_info",i.SSID +"is available");
                isAvailable = true;
                break;
            }
        }
        return isAvailable;
    }

    public static boolean isConnectedToHomeAutomationWifi(){

        boolean isConnected = false;
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String currentSSID = wifiInfo.getSSID();
        Log.i("HARC_info","currently connected to  "+wifiInfo.getSSID());
        if(currentSSID.equals("\""+ HomeAutomationConstants.homeAutomationWifiSSID +"\"")){
            Log.i("HARC_info","Currently connected to Home Automation");
            isConnected = true;
        }
        return isConnected;
    }

    public static String getWifiHostAddress(){
        String hostAddress = "";
        try {
            List<NetworkInterface> intfs=Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf: intfs){
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs){
                    if(!addr.isLoopbackAddress()){
                        String tempAddress = addr.getHostAddress();
                        if(tempAddress.indexOf(':')<0){
                            String[] devAddressSplit = tempAddress.split("\\.");
                            Log.i("HARC_info",devAddressSplit.toString());
                            for (int i = 0;i<3;i++){
                                Log.i("HARC_info",devAddressSplit[i]);
                                hostAddress = hostAddress + devAddressSplit[i]   + ".";
                            }
                            hostAddress =hostAddress + "1";
                            break;
                        }
                    }
                }
            }
            Log.i("HARC_info","IPv4 address : "  + hostAddress);
        }
        catch (Exception exc){
            Log.i("HARC_info","Problem occured while trying to fetch the host address");
        }
        return hostAddress;
    }

    public static void enableWifi(){
        if(!wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(true);
        }
    }
}
