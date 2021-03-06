package com.mmc.mateusz.gymbuddies;

import android.app.Activity;
import android.content.Context;
import android.nfc.Tag;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mmc.mateusz.gymbuddies.utils.LoginAsyncTask;
import com.mmc.mateusz.gymbuddies.utils.User;

import java.util.ArrayList;


public class ListFragmentUsers extends ListFragment implements LoginAsyncTask.CommunicationWithAsynckTask, SwipeRefreshLayout.OnRefreshListener {
    private ArrayList<User> arrayUserList;
    private User Me;
    private Context context;
    public LoginAsyncTask.CommunicationWithAsynckTask  communicate;
    SwipeRefreshLayout swype;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);

    }

    public void setMe(User me){
        this.Me=me;
    }

    public void setConetext(Context context){
     this.context=context;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         swype = (SwipeRefreshLayout)getActivity().findViewById(R.id.swiperefresh);


        swype.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pobierzDane();

            }
        });



    }

    @Override
    public void onFinishAsyncTaskLogin(int aInt, User aUser) {

    }

    @Override
    public void arrayDelivery(ArrayList<User> arrayList) {
        CustomAdapter customAdapter = new CustomAdapter(getActivity(),arrayList);
        setListAdapter(customAdapter);
        swype.setRefreshing(false);



    }

    @Override
    public void onBooBuddyAnswer(Boolean booAnswer) {
        BuddyStatusFragment buddyStatusFragment = new BuddyStatusFragment();


        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        if (booAnswer==true){

            fragmentTransaction.add(R.id.testFragmentu2, buddyStatusFragment).commit();

            if (buddyStatusFragment.isVisible()==true){

            }
        } else {
            fragmentTransaction.remove(buddyStatusFragment).commit();

        }
    }

    @Override
    public void onRefresh() {

    }

    public void pobierzDane(){
        LoginAsyncTask loginAsyncTask = new LoginAsyncTask(LoginAsyncTask.REQUEST_ARRAY,Me,ListFragmentUsers.this,false);
        loginAsyncTask.execute();
    }
}
