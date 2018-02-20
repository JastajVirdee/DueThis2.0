package duethis;

/**
 * Created by Oli on February-142018.
 */

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.widget.TextView;
import android.widget.DatePicker;
import android.app.Dialog;
import android.content.Context;
import android.app.Activity;
import java.util.Calendar;


public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Bundle b = this.getArguments();
        int year = b.getInt("year");
        int month = b.getInt("month");
        int day = b.getInt("day");

        Activity activity = getActivity();
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), this, year, month, day);
        datePicker.setOnDateSetListener((EditActivity)getActivity());
        return datePicker;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        //Do something with the date chosen by the user
    }
}