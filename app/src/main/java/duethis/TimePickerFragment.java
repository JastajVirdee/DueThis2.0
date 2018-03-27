package duethis;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    public void onTimeSet(TimePicker view, int hour, int minute) {
        // Do something with the time chosen by the user.
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle b = this.getArguments();
        int hour = b.getInt("hour");
        int minute = b.getInt("minute");
        EditActivity editActivity = (EditActivity) getActivity();
        return new TimePickerDialog(getActivity(), editActivity, hour, minute, false);
    }
}