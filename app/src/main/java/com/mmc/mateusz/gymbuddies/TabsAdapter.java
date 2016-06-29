package com.mmc.mateusz.gymbuddies;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;

import com.mmc.mateusz.gymbuddies.utils.User;

import java.util.ArrayList;

/**
 * Created by Mateusz on 2016-06-26.
 */

public class TabsAdapter extends FragmentStatePagerAdapter {
    public FragmentManager fragmentManager;


    public ListFragmentUsers listFragmentUsers;
    public Fragment myProfil;


    private User Me;


    public TabsAdapter(FragmentManager fm, ListFragmentUsers lfu, MyProfil myporf){
        super(fm);
        fragmentManager = fm;
        this.listFragmentUsers=lfu;
        this.myProfil = myporf;


    }
    @Override
    public Fragment getItem(int position) {


        switch (position) {
            case 0:
                //Fragement for Android Tab

                return listFragmentUsers;
            case 1:
                //Fragment for Ios Tab
                return myProfil;

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "GYM BUDDIES";
            case 1:
            default:
                return "TWÓJ PROFIL";
        }
    }

}
