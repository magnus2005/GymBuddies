package com.mmc.mateusz.gymbuddies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mmc.mateusz.gymbuddies.utils.User;

/**
 * Created by Mateusz on 2016-06-26.
 */
public class MyProfil extends Fragment {

    public User Me;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_profil, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView myName=(TextView)getView().findViewById(R.id.tvMyname);
        TextView myCity=(TextView)getView().findViewById(R.id.tvMycity);
        myName.setText(Me.getName());
        myCity.setText(Me.getCity());



    }

    public MyProfil(User me){
        Me=me;
    }
}
