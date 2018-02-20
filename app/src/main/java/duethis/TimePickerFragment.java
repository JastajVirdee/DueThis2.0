package duethis;


import android.app.TimePickerDialog;
import android.widget.TimePicker;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.widget.TextView;
import android.widget.DatePicker;
import android.app.Dialog;
import java.util.Calendar;
import android.app.Activity;


public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    public void onTimeSet(TimePicker view, int hour, int minute) {
        // Do something with the time chosen by the user.
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Bundle b = this.getArguments();
        int hour = b.getInt("hour");
        int minute = b.getInt("minute");
        EditActivity editActivity = (EditActivity)getActivity();
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), editActivity, hour, minute, false);
        return timePickerDialog;
    }
}