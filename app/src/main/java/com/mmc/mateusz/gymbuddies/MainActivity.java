package com.mmc.mateusz.gymbuddies;


import android.content.Intent;

import android.support.design.widget.FloatingActionButton;

import android.support.v4.view.ViewPager;

import android.support.v7.app.*;
import android.os.Bundle;

import android.view.View;


import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;



import com.mmc.mateusz.gymbuddies.utils.LoginAsyncTask;
import com.mmc.mateusz.gymbuddies.utils.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoginAsyncTask.CommunicationWithAsynckTask {
    public static final String LOGIN_SPREFERENCES = "com.mmc.mateusz.gymudies.MY_LOGIN_PREF";
    public User Me;
    public ListFragmentUsers listFragmentUsers=null;
    public MyProfil myProfil=null;
    public TextView tvName, tvMiasto, tvMyGym;

    public  FloatingActionButton fabBeBuddy;
    public ViewPager viewPager;
    public TabsAdapter tabsAdapter;

    public static LoginAsyncTask.CommunicationWithAsynckTask communicate;
    public ImageButton btnToProfil, btnToBeBuddy;
    private View topProfil;
    public boolean BootestFab=false;
    private ImageView avatar;

    public int lastPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        communicate = this;
        tvName = (TextView) findViewById(R.id.tvMyname);
        tvMiasto = (TextView) findViewById(R.id.tvMycity);
        //tvMyGym = (TextView) findViewById(R.id.tvMyGym);
        avatar = (ImageView)findViewById(R.id.my_avatar);

        fabBeBuddy = (FloatingActionButton)findViewById(R.id.fabBeBuddy);

        fabBeBuddy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginAsyncTask loginAsyncTask = new LoginAsyncTask(LoginAsyncTask.REQUEST_SET_BUDDY,Me,MainActivity.this,true);
                loginAsyncTask.execute();

                LoginAsyncTask loginAsyncTaska = new LoginAsyncTask(LoginAsyncTask.REQUEST_ARRAY,Me,MainActivity.this,false);
                loginAsyncTaska.execute();

               if (BootestFab ==false){
                   fabBeBuddy.setImageResource(R.drawable.ic_close_24dp);
                   BootestFab=true;

               }else{
                   fabBeBuddy.setImageResource(R.drawable.ic_add_circle_24dp);
                   BootestFab=false;
               }

            }
        });



        Me = new User();
        Intent getU = getIntent();
        if (getU.hasExtra("EXISTED_USER") == true) {
            Me = (User) getU.getSerializableExtra("EXISTED_USER");
            //setTextViews();
        }

        LoginAsyncTask loginAsyncTask = new LoginAsyncTask(LoginAsyncTask.REQUEST_ARRAY,Me,MainActivity.this,false);
        loginAsyncTask.execute();





/*
        listFragmentUsers = new ListFragmentUsers();
        listFragmentUsers.setMe(Me);

        CustomAdapter customAdapter = new CustomAdapter(this,xxxx);
        listFragmentUsers.setListAdapter(customAdapter);

        tabsAdapter = new TabsAdapter(getSupportFragmentManager(),listFragmentUsers);
        viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(tabsAdapter);



*/






    }


    public void setTextViews(){
        tvName.setText(Me.getName());
        tvMiasto.setText(Me.getCity());
       // tvMyGym.setText(Me.getGymPlace());
    }



    public void uruchomProfil(){
        Intent passObj = new Intent(MainActivity.this, Profil.class);
        passObj.putExtra("EXISTED_USER", Me);

        startActivity(passObj);
        finish();
    }


    public void onStop(){
        super.onStop();

    }

    @Override
    public void arrayDelivery(ArrayList<User> arrayList) {
if (listFragmentUsers!=null){
       lastPosition =  viewPager.getCurrentItem();


}
        listFragmentUsers = new ListFragmentUsers();
        myProfil=new MyProfil(Me);
        listFragmentUsers.setMe(Me);

        CustomAdapter customAdapter = new CustomAdapter(this,arrayList);
        listFragmentUsers.setListAdapter(customAdapter);

        tabsAdapter = new TabsAdapter(getSupportFragmentManager(),listFragmentUsers, myProfil);
        viewPager = (ViewPager)findViewById(R.id.pager);

        viewPager.setAdapter(tabsAdapter);
        if (listFragmentUsers!=null){
           viewPager.setCurrentItem(lastPosition);

        }


        if (listFragmentUsers==null){



        }
        /*else{
            listFragmentUsers = new ListFragmentUsers();
            listFragmentUsers.setMe(Me);


            CustomAdapter customAdapter = new CustomAdapter(this,arrayList);

            listFragmentUsers.setListAdapter(customAdapter);


            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.UserListLayout, listFragmentUsers).commit();
        }*/

    }

    @Override
    public void onFinishAsyncTaskLogin(int aInt, User aUser) {

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
