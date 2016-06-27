package com.mmc.mateusz.gymbuddies;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;

import com.mmc.mateusz.gymbuddies.utils.User;

/**
 * Created by Mateusz on 2016-06-26.
 */
public class TabsAdapter extends FragmentStatePagerAdapter {
    public TabsAdapter(FragmentManager fm) {
        super(fm);

    }
    private User Me;
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                //Fragement for Android Tab
                return new ListFragmentUsers();
            case 1:
                //Fragment for Ios Tab
                return new MyProfil();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
