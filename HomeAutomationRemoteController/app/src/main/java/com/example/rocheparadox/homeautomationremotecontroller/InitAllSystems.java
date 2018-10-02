package com.example.rocheparadox.homeautomationremotecontroller;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InitAllSystems {

    public static void initAppliancesAsJsonObject(Context context) {
        try {
            String applianceCodeNamesFileName = "appliance_code_names.txt";
            Log.i("HARC_info", "1");
            InputStream inputStream = context.getResources().openRawResource(R.raw.appliances_code_names);
            HomeAutomationConstants.appliancesCodeNamesJson = jsonReader(inputStream);
            inputStream = context.getResources().openRawResource(R.raw.appliances_view_names);
            HomeAutomationConstants.appliancesViewNamesJson = jsonReader(inputStream);

            }
            catch (Exception e){
                Log.i("HARC_info", e.toString());
            }
    }

    public static boolean initWifiSystems() {
        boolean flag = true;
        TextView systemStatus = (TextView) HomeAutomationConstants.systemStatusView;
        WifiUtils.enableWifi();
        if (!WifiUtils.isConnectedToHomeAutomationWifi()) {
            systemStatus.setText("OFFLINE");
            Log.i("HARC_info", "Not connected to Home Automation Wifi");
            if (WifiUtils.isHomeAutomationWifiAvailable()) {
                systemStatus.setText("Connecting...");
                systemStatus.setTextColor(Color.rgb(0, 0, 200));
                //Home Automation wifi is available
                Log.i("HARC_info", "Home Automation Wifi is available");
                //connect to Home automation wifi
                if (WifiUtils.connectToHomeAutomationNetwork()) {
                    systemStatus.setText("ONLINE");
                    systemStatus.setTextColor(Color.rgb(0, 200, 0));
                } else {
                    //Home Automation wifi is not available
                    Log.i("HARC_info", "Home Automation Wifi is not available");
                    flag = false;
                }
            }
        }
        else{
            //Connected to Home automation Wifi
            systemStatus.setText("ONLINE");
            systemStatus.setTextColor(Color.rgb(0, 200, 0));
        }

        return flag;
    }

    private static JSONObject jsonReader(InputStream inputStream){
        JSONObject jsonObject = new JSONObject();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String json = "";
            String tempString;
            Log.i("HARC_info", "Going to read the json File");
            while ((tempString = br.readLine()) != null) {
                json = json + tempString;
                //Log.i("HARC_info", "JSON file \n" + json);
            }
            Log.i("HARC_info", "JSON file \n" + json);
            jsonObject = new JSONObject(json);
        } catch (Exception exc) {
            Log.i("HARC_info", "There was an error while reading the file " + exc.toString());
        }
        return jsonObject;
    }

}
