package com.mmc.mateusz.gymbuddies;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;


import com.mmc.mateusz.gymbuddies.utils.Imaging;
import com.mmc.mateusz.gymbuddies.utils.LoginAsyncTask;
import com.mmc.mateusz.gymbuddies.utils.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
///http://stackoverflow.com/questions/2507898/how-to-pick-an-image-from-gallery-sd-card-for-my-app
public class Profil extends AppCompatActivity implements Serializable, LoginAsyncTask.CommunicationWithAsynckTask {
    public User Me;
    private int year=2000, month=0, day=1;
    public EditText etDate, etName, etPhoneNumber;
    public Button btnAcceptProfil;
    public Spinner spinnerMiasta, spinnerGymPlaces;
    private LoginAsyncTask loginAsyncTask;
    private ImageView myAvatar;
    private static final int SELECT_PHOTO = 100;
    public String imagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profil);

        btnAcceptProfil = (Button) findViewById(R.id.btnOK);
        etPhoneNumber = (EditText)findViewById(R.id.etPhoneNumberProfil);
        etName = (EditText) findViewById(R.id.etName);
        etDate = (EditText) findViewById(R.id.etDate);
        spinnerMiasta = (Spinner) findViewById(R.id.spinerMiasta);

        myAvatar = (ImageView) findViewById(R.id.iv_my_avatar);



        myAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });


        Intent getU = getIntent();

        if(getU.hasExtra("NEW_USER")){
            Me = (User) getU.getSerializableExtra("NEW_USER");
            etPhoneNumber.setText("+48 " + Me.getPhoneNumber());
        }else{
            Me= (User) getU.getSerializableExtra("EXISTED_USER");
            etPhoneNumber.setText("+48" + Me.getPhoneNumber());
            etName.setText((Me.getName()));

            Date date = new Date(Me.getDateBirthday());
            GregorianCalendar gregorianCalendar= new GregorianCalendar();
            gregorianCalendar.setTime(date);
            int aday = gregorianCalendar.get(Calendar.DAY_OF_MONTH);
            int amonth = gregorianCalendar.get(Calendar.MONTH);
            int ayear = gregorianCalendar.get(Calendar.YEAR);



            if (Me.getDateBirthday()!=0L){
                year=ayear;
                month=amonth;
                day=aday;
            }

            etDate.setText(dataToString(day,month,year));
        }

        btnAcceptProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Me.setName(etName.getText().toString());
                Me.setDateBirthday(year-1900,month,day);
                Me.setCity(spinnerMiasta.getSelectedItem().toString());

                loginAsyncTask= new LoginAsyncTask(LoginAsyncTask.REQUEST_WRITE_USER, Me, Profil.this,false);
                loginAsyncTask.execute();

                SharedPreferences pref = getSharedPreferences(MainActivity.LOGIN_SPREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = pref.edit();

                prefEditor.putInt("USER_PHONE", Me.getPhoneNumber());
                prefEditor.putString("USER_PASSWORD", Me.getPassword());
                prefEditor.putString("USER_NAME", Me.getName());
                prefEditor.putLong("USER_BIRTHDAY", Me.getDateBirthday());
                prefEditor.putString("USER_CITY", Me.getCity());
                prefEditor.putString("USER_IMAGE_PATH", imagePath );

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
            return new DatePickerDialog(this, myDateListener, year, month, day);
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

            etDate.setText(dataToString(day,month,year));

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

    public void onPause(){
        super.onPause();


    }


    @Override
    public void onFinishAsyncTaskLogin(int aInt, User aUser) {

}

    @Override
    public void arrayDelivery(ArrayList<User> arrayList) {

    }

    @Override
    public void onBooBuddyAnswer(Boolean booAnswer) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){

                    Imaging imaging = new Imaging(data, Profil.this);
                    //Uri selectedImage = data.getData();

                    //File obrazek = new File(selectedImage.getPath());
                    imagePath=imaging.givePath();
                   /* InputStream imageStream = null;
                    try {
                        imageStream = getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);

                    Bitmap x = Bitmap.createScaledBitmap(yourSelectedImage,1000 ,1000,false);
*/
                    myAvatar.setImageBitmap(imaging.cos());
                }
        }

    }

    private String getPath(Uri uri) {

        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null,null);

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }



}
