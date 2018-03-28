package duethis;

// Created by Oli on February-142018.

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle b = this.getArguments();
        int year = b.getInt("year");
        int month = b.getInt("month");
        int day = b.getInt("day");

        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), this, year, month, day);
        datePicker.setOnDateSetListener((EditActivity) getActivity());
        return datePicker;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        //Do something with the date chosen by the user
    }
}