package com.mmc.mateusz.gymbuddies;


import android.content.Intent;

import android.support.design.widget.FloatingActionButton;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

    TabLayout tabLayout;



    public static LoginAsyncTask.CommunicationWithAsynckTask communicate;
    public ImageButton btnToProfil, btnToBeBuddy;
    private View topProfil;
    public boolean BootestFab=false;
    private ImageView avatar;

    public int lastPosition=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        communicate = this;
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tvName = (TextView) findViewById(R.id.tvMyname);
        tvMiasto = (TextView) findViewById(R.id.tvMycity);
        avatar = (ImageView)findViewById(R.id.my_avatar);
        fabBeBuddy = (FloatingActionButton)findViewById(R.id.fabBeBuddy);



        Me = new User();
        Intent getU = getIntent();
        if (getU.hasExtra("EXISTED_USER") == true) {
            Me = (User) getU.getSerializableExtra("EXISTED_USER");
            //setTextViews();
        }

        fabBeBuddy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginAsyncTask loginAsyncTaska = new LoginAsyncTask(LoginAsyncTask.REQUEST_ARRAY,Me,MainActivity.this,false);
                loginAsyncTaska.execute();

                LoginAsyncTask loginAsyncTask = new LoginAsyncTask(LoginAsyncTask.REQUEST_SET_BUDDY,Me,MainActivity.this,true);
                loginAsyncTask.execute();

                LoginAsyncTask loginAsyncTask1 = new LoginAsyncTask(LoginAsyncTask.REQUEST_CHECK_BUDDY,Me,MainActivity.this,false);
                loginAsyncTask1.execute();
            }
        });





        LoginAsyncTask loginAsyncTask = new LoginAsyncTask(LoginAsyncTask.REQUEST_ARRAY,Me,MainActivity.this,false);
        loginAsyncTask.execute();

        //LoginAsyncTask loginAsyncTask1 = new LoginAsyncTask(LoginAsyncTask.REQUEST_CHECK_BUDDY,Me,MainActivity.this,false);
        //loginAsyncTask1.execute();

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
            if (listFragmentUsers.isVisible()!=false){
                lastPosition =  viewPager.getCurrentItem();
            }
        }

        listFragmentUsers = new ListFragmentUsers();
        myProfil=new MyProfil(Me);
        listFragmentUsers.setMe(Me);

        CustomAdapter customAdapter = new CustomAdapter(this,arrayList);
        listFragmentUsers.setListAdapter(customAdapter);

        tabsAdapter = new TabsAdapter(getSupportFragmentManager(),listFragmentUsers, myProfil);
        viewPager = (ViewPager)findViewById(R.id.pager);

        viewPager.setAdapter(tabsAdapter);
        viewPager.setCurrentItem(lastPosition);
        tabLayout.addTab(tabLayout.newTab().setText("GYM BUDDIES"));
        tabLayout.addTab(tabLayout.newTab().setText("TWÓJ PROFIL"));


        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public void onFinishAsyncTaskLogin(int aInt, User aUser) {

    }
    @Override
    public void onBooBuddyAnswer(Boolean booAnswer) {
        BuddyStatusFragment buddyStatusFragment = new BuddyStatusFragment();


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        if (booAnswer==true){

            fragmentTransaction.add(R.id.testFragmentu2, buddyStatusFragment).commit();
            fabBeBuddy.setImageResource(R.drawable.ic_close_24dp);
            if (buddyStatusFragment.isVisible()==true){

            }
        } else {
            fragmentTransaction.remove(buddyStatusFragment).commit();
            fabBeBuddy.setImageResource(R.drawable.ic_add_circle_24dp);
        }

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
