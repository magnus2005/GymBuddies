package com.mmc.mateusz.gymbuddies.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mmc.mateusz.gymbuddies.utils.ManagerServerConnection;

import java.io.Serializable;
import java.util.ArrayList;

public class LoginAsyncTask extends AsyncTask<Integer, Void, Integer> implements Serializable {
    private static final long serialVersionUID = 41L;

    public static final int REQUEST_CHECK_USER =0;
    public static final int REQUEST_WRITE_USER =1;
    public static final int REQUEST_ARRAY =2;
    public static final int REQUEST_SET_BUDDY =3;
    public static final int REQUEST_CHECK_BUDDY =4;

    public ProgressDialog progress;

    private ManagerServerConnection serverConnection;
    public CommunicationWithAsynckTask comunicate=null;
    public Boolean showProgress = false;

    public ProgressBar progressBar = null;

    private User userObjectToCheck;
    private int activeRequst;
    private Boolean ifBuddyAnswer = null;

    private int intAnswer = -2;
    private User userAnswer = null;

    private ArrayList<User> arrayList=null;

    public LoginAsyncTask(int aRequest, User user, CommunicationWithAsynckTask commWithAsynckTask, Boolean showProgressDialog){
        this.userObjectToCheck=user;
        this.activeRequst=aRequest;
        this.comunicate=commWithAsynckTask;
        this.showProgress=showProgressDialog;

    }

    public void setProgressBar(ProgressBar pb){
        this.progressBar=pb;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (showProgress==true){
            progress = new ProgressDialog((Context)comunicate);
            progress.setTitle("Proszę czekać...");
            progress.show();
        }

        if(progressBar!=null){
            progressBar.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected Integer doInBackground(Integer... params) {

        serverConnection = new ManagerServerConnection();
        if (serverConnection.connectionActive(true) == true) {
            serverConnection.streamsActive(true);

            switch (activeRequst) {
                case REQUEST_CHECK_USER:
                    serverRequest(REQUEST_CHECK_USER, userObjectToCheck);
                    loginReturnAnswer();
                    break;
                case REQUEST_WRITE_USER:
                    serverRequest(REQUEST_WRITE_USER, userObjectToCheck);
                    serverConnection.sendUserObject(userObjectToCheck);
                    break;
                case REQUEST_ARRAY:
                    serverConnection.sendRequest(REQUEST_ARRAY);
                    arrayList = serverConnection.getUsersArray();
                    break;

                case REQUEST_SET_BUDDY:
                    serverConnection.sendRequest(REQUEST_SET_BUDDY);
                    serverConnection.sendUserObject(userObjectToCheck);
                    break;

                case REQUEST_CHECK_BUDDY:
                    serverConnection.sendRequest(REQUEST_CHECK_BUDDY);
                    serverConnection.sendUserObject(userObjectToCheck);
                    buddyAnswer();
                    break;
            }
        } else{
          return -2;
        }

        return 0;
    }

    private void serverRequest(int request, User user){
        serverConnection.sendRequest(request);
        serverConnection.sendUserObject(userObjectToCheck);
    }

    private void loginReturnAnswer(){
        intAnswer=serverConnection.getIntAnswer();
        userAnswer=serverConnection.getUserObject();
    }

    private void buddyAnswer(){
        ifBuddyAnswer = serverConnection.getBooleanAnswer();
    }

    public interface CommunicationWithAsynckTask{
        void onFinishAsyncTaskLogin(int aInt, User aUser);
        void arrayDelivery(ArrayList<User> arrayList);
        void onBooBuddyAnswer(Boolean booAnswer);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (arrayList!=null){
            comunicate.arrayDelivery(arrayList);
        }else{
            comunicate.onFinishAsyncTaskLogin(intAnswer,userAnswer);
        }

        if(integer==-2){

        }

        if(progress!=null){
            progress.dismiss();
        }
        if(progressBar!=null){
            progressBar.setVisibility(View.GONE);
        }

        if (ifBuddyAnswer !=null){
            comunicate.onBooBuddyAnswer(ifBuddyAnswer);
        }
    }
}
