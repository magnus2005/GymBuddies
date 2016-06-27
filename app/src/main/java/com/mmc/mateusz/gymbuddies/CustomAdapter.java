package com.mmc.mateusz.gymbuddies;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import android.widget.TextView;

import com.mmc.mateusz.gymbuddies.utils.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz on 2016-06-25.
 */
public class CustomAdapter extends ArrayAdapter{

    private Context mContext;
    private ArrayList<User> mList;
    UserDetal userDetal=null,userDetalnew=null;
    FragmentTransaction ft;

    public CustomAdapter(Context context, ArrayList<User> list) {
       super (context, R.layout.card_layout,list);
        mContext = context;
        mList = list;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        View view = null;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.card_layout,null);
        }else{
            view=convertView;
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userDetal==null){
                    userDetal = new UserDetal(mList.get(position));
                    ft = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
                    ft.add(R.id.testFragmentu, userDetal).commit();
                }
                else{
                    userDetalnew = new UserDetal(mList.get(position));
                    ft = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.testFragmentu,userDetalnew).commit();
                }



            }
        });

        TextView personName = (TextView)view.findViewById(R.id.person_name);
        TextView city = (TextView)view.findViewById(R.id.person_age);

        personName.setText(mList.get(position).getName());
        city.setText( mList.get(position).getCity() );
        return view;
    }
}
