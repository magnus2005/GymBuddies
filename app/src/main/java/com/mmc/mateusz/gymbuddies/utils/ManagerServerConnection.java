package com.mmc.mateusz.gymbuddies.utils;


import android.app.ProgressDialog;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ManagerServerConnection {
    private ProgressDialog dialogporgres;
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public Boolean streamsActive(Boolean active) {
        try {
            if(active == true) {
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                inputStream = new ObjectInputStream(socket.getInputStream());
            }else{
                inputStream.close();
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Boolean connectionActive(Boolean active){
        int timeOut = 10*1000; // 10s
        try{
            if(active==true){
                socket=new Socket();
                socket.connect(new InetSocketAddress("10.0.0.76", 8882), timeOut);
            }else{
                socket.close();
            }
        }catch(UnknownHostException e){
            e.printStackTrace();
        }catch(ConnectException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket.isConnected();
    }

    public void sendUserObject(User user){
        try {
            outputStream.writeObject(user);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRequest(int request){
        try {
            outputStream.writeInt(request);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getUserObject(){
        User user=null;
        try {
            user=(User)inputStream.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }
    public ArrayList<User> getUsersArray(){
        ArrayList<User> arrayList = null;

        try {
            arrayList=(ArrayList<User>)inputStream.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return arrayList;
    }
    public int getIntAnswer(){
        int answer=-2;
        try {
            answer=(int)inputStream.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer;
    }

}
