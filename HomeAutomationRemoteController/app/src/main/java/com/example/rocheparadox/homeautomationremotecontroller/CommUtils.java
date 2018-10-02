package com.example.rocheparadox.homeautomationremotecontroller;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Iterator;

public class CommUtils  implements Runnable{
    String command;
    CommUtils(String command){
        this.command = command;
    }
    @Override
    public void run() {
        try {
            String hostAddress = getHostAddress();
            int port = HomeAutomationConstants.tcpPort;
            Socket tcpSocket = new Socket(hostAddress, port);       //Connects to the server
            sendMessageToHost(tcpSocket);
            Log.i("HARC_info","Message sent to host "+hostAddress+" and port "+port +" the message is: "+command);
            String responseMessage = readFromSocket(tcpSocket);
            Log.i("HARC_info","Response Message "+ responseMessage );
            //operationAfterMath(responseMessage);
        }
        catch(Exception e){
            Log.i("HARC_info","Error while trying to send message to server" + e.toString());
        }
        //
    }

    private void sendMessageToHost(Socket socket){
        Log.i("HARC_info","Into the sendMessageToHost class");
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write(command);
            bufferedWriter.flush();
        }

        catch (Exception e){
            e.printStackTrace();
            Log.i("myInfo","Error occured while sending message to Socket");
        }
    }

    private String readFromSocket(Socket socket){
        String responseMessage = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            responseMessage = bufferedReader.readLine();
            Log.i("HARC_info","responseMessage "+ responseMessage);
        }
        catch(Exception exc){
            Log.i("HARC_info","Error occured while reading the message from the socket");
        }
        return responseMessage;
    }

    public String getHostAddress(){
        return WifiUtils.getWifiHostAddress();
    }

}
