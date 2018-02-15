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

/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {


    public void onTimeSet(TimePicker view, int hour, int minute) {
        // Do something with the time chosen by the user.
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        Bundle b = savedInstanceState;
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);

        if(b != null) {
            hour = b.getInt("hour");
            minute = b.getInt("minute");
        }

        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }
}