package com.mmc.mateusz.gymbuddies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mmc.mateusz.gymbuddies.utils.User;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Mateusz on 2016-06-26.
 */
public class MyProfil extends Fragment {

    public User Me;
    ImageButton ibtnEditProfil;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_profil, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ibtnEditProfil = (ImageButton)getView().findViewById(R.id.ibtnEditProfil);
        TextView myName=(TextView)getView().findViewById(R.id.tvMyname);
        TextView myCity=(TextView)getView().findViewById(R.id.tvMycity);
        TextView myDateBirth=(TextView)getView().findViewById(R.id.tvMyDateBirth);
        TextView myPhoneNumber=(TextView)getView().findViewById(R.id.tvMyPhoneNumber);
        myName.setText(Me.getName());
        myCity.setText(Me.getCity());
        myPhoneNumber.setText("+48"+Me.getPhoneNumber());

        Date date = new Date(Me.getDateBirthday());
        GregorianCalendar gregorianCalendar= new GregorianCalendar();
        gregorianCalendar.setTime(date);
        int aday = gregorianCalendar.get(Calendar.DAY_OF_MONTH);
        int amonth = gregorianCalendar.get(Calendar.MONTH);
        int ayear = gregorianCalendar.get(Calendar.YEAR);

        myDateBirth.setText(dataToString(aday, amonth,ayear));




        ibtnEditProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passObj = new Intent(getActivity(), Profil.class);
                passObj.putExtra("EXISTED_USER", Me);

                startActivity(passObj);

            }
        });

    }


    public MyProfil(User me){
        Me=me;
    }



    private String dataToString(int day, int month, int year){
        String dzien;
        String miesiac;
        if ((dzien = Integer.toString(day)).length()==1){
            dzien = "0"+dzien;
        }

        if ((miesiac = Integer.toString((month+1))).length()==1){
            miesiac = "0"+miesiac;
        }

        return dzien+"/"+miesiac+"/"+Integer.toString(year);
    }

}
