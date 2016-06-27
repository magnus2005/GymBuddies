package com.mmc.mateusz.gymbuddies;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;


import com.mmc.mateusz.gymbuddies.utils.LoginAsyncTask;
import com.mmc.mateusz.gymbuddies.utils.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Profil extends AppCompatActivity implements Serializable, LoginAsyncTask.CommunicationWithAsynckTask {
    public User Me;
    private int year, month, day;
    public EditText etDate, etName, etPhoneNumber;
    public Button btnAcceptProfil;
    public Spinner spinnerMiasta, spinnerGymPlaces;
    private LoginAsyncTask loginAsyncTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profil);

        btnAcceptProfil = (Button) findViewById(R.id.btnOK);
        etPhoneNumber = (EditText)findViewById(R.id.etPhoneNumberProfil);
        etName = (EditText) findViewById(R.id.etName);
        etDate = (EditText) findViewById(R.id.etDate);
        spinnerMiasta = (Spinner) findViewById(R.id.spinerMiasta);


        Intent getU = getIntent();

        if(getU.hasExtra("NEW_USER")){
            Me = (User) getU.getSerializableExtra("NEW_USER");
            etPhoneNumber.setText("+48 " + Me.getPhoneNumber());
        }else{
            Me= (User) getU.getSerializableExtra("EXISTED_USER");
            etPhoneNumber.setText("+48" + Me.getPhoneNumber());
            etName.setText((Me.getName()));
/*
            Date date = new Date(Me.getDateBirthday());
            GregorianCalendar gregorianCalendar= new GregorianCalendar();
            gregorianCalendar.setTime(date);
            int aday = gregorianCalendar.get(Calendar.DAY_OF_MONTH);
            int amonth = gregorianCalendar.get(Calendar.MONTH);
            int ayear = gregorianCalendar.get(Calendar.YEAR);
            String data = aday+"/"+amonth+"/"+ayear;

            etDate.setText(data);*/
        }

        btnAcceptProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Me.setName(etName.getText().toString());
                Me.setDateBirthday(day,month,year);
                Me.setCity(spinnerMiasta.getSelectedItem().toString());

                loginAsyncTask= new LoginAsyncTask(LoginAsyncTask.REQUEST_WRITE_USER, Me, Profil.this,false);
                loginAsyncTask.execute();

                SharedPreferences pref = getSharedPreferences(MainActivity.LOGIN_SPREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = pref.edit();

                prefEditor.putInt("USER_PHONE",Me.getPhoneNumber());
                prefEditor.putString("USER_PASSWORD", Me.getPassword());
                prefEditor.putString("USER_NAME", Me.getName());
                //prefEditor.putLong("USER_DATE_BIRTH", Me.getDateBirthday());
                prefEditor.putString("USER_CITY", Me.getCity());

                prefEditor.commit();

                Intent passToMain = new Intent(Profil.this, MainActivity.class);
                passToMain.putExtra("EXISTED_USER", Me);


                startActivity(passToMain);
                finish();

            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(999);
            }
        });

        ustawAdaptery();



    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, 2000, 0, 1);
        }
        return null;
    }

    //ustalam jaka data zsotanie ustawiona po otworzeniu pickera
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            year=arg1;
            month=arg2;
            day=arg3;
            etDate.setText(Integer.toString(day)+"/"+Integer.toString(month+1)+"/"+Integer.toString(year));
        }
    };


    private void ustawAdaptery(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.city_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMiasta.setAdapter(adapter);

        if (Me!=null) {
            int spinnerPosition = adapter.getPosition(Me.getCity());
            spinnerMiasta.setSelection(spinnerPosition);
        }

    }

    public void onPause(){
        super.onPause();


    }


    @Override
    public void onFinishAsyncTaskLogin(int aInt, User aUser) {

    }

    @Override
    public void arrayDelivery(ArrayList<User> arrayList) {

    }


}
