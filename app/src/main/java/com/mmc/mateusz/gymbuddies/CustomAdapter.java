package com.mmc.mateusz.gymbuddies;

import android.content.Context;
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

    public CustomAdapter(Context context, ArrayList<User> list) {
       super (context, R.layout.card_layout,list);
        mContext = context;
        mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view = null;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.card_layout,null);
        }else{
            view=convertView;
        }
        TextView personName = (TextView)view.findViewById(R.id.person_name);
        TextView city = (TextView)view.findViewById(R.id.person_age);

        personName.setText(mList.get(position).getName());
        city.setText( mList.get(position).getCity() );

        return view;
    }
}
