package com.mmc.mateusz.gymbuddies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mmc.mateusz.gymbuddies.utils.LoginAsyncTask;
import com.mmc.mateusz.gymbuddies.utils.User;

import java.util.ArrayList;

public class Login extends AppCompatActivity implements LoginAsyncTask.CommunicationWithAsynckTask{

    private EditText etPhoneNumber, etPassword;

    private Button btnLogIn;
    private int intPhoneNumber=0;
    private String strPassword="";

    private SharedPreferences loginPref;
    private SharedPreferences.Editor loginPrefEditor;

    private User userObj;

    private LoginAsyncTask loginAsyncTask;
    private ProgressBar progres;

    @Override
    protected void onPause() {
        super.onPause();
        if(loginAsyncTask.isCancelled()!=true){
            loginAsyncTask.cancel(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        progres = (ProgressBar)findViewById(R.id.progressBar);
        etPhoneNumber=(EditText)findViewById(R.id.et_phone_number);
        etPassword=(EditText)findViewById(R.id.et_password);
        btnLogIn=(Button)findViewById(R.id.btn_log_in);

        loginPref = getSharedPreferences(MainActivity.LOGIN_SPREFERENCES, MODE_PRIVATE);
        loginPrefEditor = loginPref.edit();

        if (loginPref.contains("USER_PHONE")== true && loginPref.contains("USER_PHONE")){

            userObj = new User(intPhoneNumber, strPassword);
            userObj.setPhoneNumber(loginPref.getInt("USER_PHONE", 0));
            userObj.setPassword(loginPref.getString("USER_PASSWORD", ""));
            userObj.setName(loginPref.getString("USER_NAME", ""));
            userObj.setCity(loginPref.getString("USER_CITY", ""));
            //userObj.setDateBirthday(loginPref.getLong("DATE_BIRTHDAY", 0L));

            startMain(userObj);
        }




        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chowaj klawiature
                View view = Login.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                Boolean loginDataCorrect= true;
                    if (etPhoneNumber.getText().toString().length()!=9){
                        Context context = getApplicationContext();
                        CharSequence text = "Numer musi mieć 9 cyfr. Popraw!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        loginDataCorrect=false;
                    }
                    if (etPassword.getText().toString().length()<5){
                        Context context = getApplicationContext();
                        CharSequence text = "Hasło musi mieć conajmniej 6 znaków!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        loginDataCorrect=false;
                    }

                if (loginDataCorrect==true){
                    intPhoneNumber = Integer.parseInt(String.valueOf(etPhoneNumber.getText().toString()));
                    strPassword = etPassword.getText().toString();

                    userObj = new User(intPhoneNumber, strPassword);

                    loginAsyncTask = new LoginAsyncTask(LoginAsyncTask.REQUEST_CHECK_USER, userObj, Login.this, true);
                    loginAsyncTask.execute();
                }


            }
        });

    }

    @Override
    public void onFinishAsyncTaskLogin(int aInt, User aUser) {

        if (aInt == 1){
            loginPrefEditor.putInt("USER_PHONE", userObj.getPhoneNumber());
            loginPrefEditor.putString("USER_PASSWORD", userObj.getPassword());
            loginPrefEditor.putString("USER_NAME", userObj.getName());
            loginPrefEditor.putString("USER_CITY", userObj.getCity());
            //loginPrefEditor.putLong("USER_BIRTHDAY", userObj.getDateBirthday());

            loginPrefEditor.commit();
            startMain(aUser);
        }else{
            if(aInt == -1){
                startProfil(userObj);
            }else{

            }
        }

        if (aInt == -2 && aUser==null){
            Context context = getApplicationContext();
            CharSequence text = "Server niedostępny. Spróbuj później...";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    public void startMain(User user){
        Intent passToMain = new Intent(Login.this, MainActivity.class);
        passToMain.putExtra("EXISTED_USER",user);
        startActivity(passToMain);
        finish();
    }
    public void startProfil(User user){
        Intent passToProfil = new Intent(Login.this, Profil.class);
        passToProfil.putExtra("NEW_USER", user);
        startActivity(passToProfil);
        finish();
    }

    @Override
    public void arrayDelivery(ArrayList<User> arrayList) {

    }

}
