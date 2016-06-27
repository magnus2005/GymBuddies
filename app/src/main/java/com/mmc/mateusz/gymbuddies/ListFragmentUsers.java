package com.mmc.mateusz.gymbuddies;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);


    }
    public void setMe(User me){
        this.Me=me;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SwipeRefreshLayout swype = (SwipeRefreshLayout)getActivity().findViewById(R.id.swiperefresh);

        swype.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoginAsyncTask loginAsyncTask = new LoginAsyncTask(LoginAsyncTask.REQUEST_ARRAY,Me,ListFragmentUsers.this,false);
                loginAsyncTask.execute();
            }
        });



    }

    @Override
    public void onFinishAsyncTaskLogin(int aInt, User aUser) {

    }

    @Override
    public void arrayDelivery(ArrayList<User> arrayList) {
        ListFragmentUsers listFragmentUsers = new ListFragmentUsers();


        CustomAdapter customAdapter = new CustomAdapter(getView().getContext(),arrayList);

        listFragmentUsers.setListAdapter(customAdapter);


        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.UserListLayout, listFragmentUsers).commit();

    }

    @Override
    public void onRefresh() {

    }
}
