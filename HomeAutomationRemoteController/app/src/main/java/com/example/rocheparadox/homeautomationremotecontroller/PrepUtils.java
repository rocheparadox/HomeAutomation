package com.example.rocheparadox.homeautomationremotecontroller;

import android.util.Log;
import android.view.View;

import org.json.JSONObject;

import java.util.Iterator;

public class PrepUtils {
    public static String commandMaker (String applianceInternalCode,String operation){
        String command = "";
        try {
            String applianceCode;
            JSONObject appliancesCodeNamesJson = HomeAutomationConstants.appliancesCodeNamesJson;
            applianceCode = appliancesCodeNamesJson.optString(applianceInternalCode);
            command = "DO," + applianceCode + "," + operation;
            Log.i("HARC_info", "command : " + command);

        }
        catch (Exception exc){
            exc.printStackTrace();
        }
        return command;
    }


    private void operationAfterMath(String responseMessage){
        String applianceCodeName,presentOperation,applianceInternalKey="",buttonId;
        String[] responseMessageSplit = responseMessage.split(",");
        applianceCodeName = responseMessageSplit[1];
        presentOperation = responseMessageSplit[3];
        JSONObject applianceCodeNamesJson = HomeAutomationConstants.appliancesCodeNamesJson;
        Iterator<String> keys = applianceCodeNamesJson.keys();
        while (keys.hasNext()){
            String key = keys.next();
            String value = applianceCodeNamesJson.optString(key);
            if(value.equals(applianceCodeName)){
                applianceInternalKey = key;
            }
        }

        buttonId = applianceInternalKey+"_"+"button";

    }
}
