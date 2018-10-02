package com.example.rocheparadox.homeautomationremotecontroller;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiUtils wifiUtils = new WifiUtils(wifiManager);
        HomeAutomationConstants.systemStatusView = findViewById(R.id.systemStatus);
        //InitAllSystems initAllSystems = new InitAllSystems();
        InitAllSystems.initAppliancesAsJsonObject(this);
        InitAllSystems.initWifiSystems();
    }

    public void applianceButtonClick(android.view.View view){
        Log.i("HARC_info","Into applianceButtonClick");

        if(InitAllSystems.initWifiSystems()){
            String applianceCode = view.getTag().toString().split("_")[0];
            Button button = (Button) view;
            String operation = button.getText().toString();
            //Log.i("HARC_info",view.toString());
            Log.i("HARC_info","applianceCode : "+applianceCode + " Operation : " + operation);
            String command = PrepUtils.commandMaker(applianceCode,operation);
            CommUtils sendCommnd = new CommUtils(command);
            new Thread(sendCommnd).start();


        }

    }
    

}
