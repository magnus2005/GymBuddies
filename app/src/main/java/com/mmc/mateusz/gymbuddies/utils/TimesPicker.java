package com.mmc.mateusz.gymbuddies.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.mmc.mateusz.gymbuddies.MainActivity;

import java.util.Calendar;

/**
 * Created by Mateusz on 2016-05-18.
 */
public class TimesPicker extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {
    private int hour;
    private int minute;



    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return null;
       //return new TimePickerDialog(getActivity(), (BeBuddyDialog)getTargetFragment(), hour, minute,
              //  DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


    }



}
