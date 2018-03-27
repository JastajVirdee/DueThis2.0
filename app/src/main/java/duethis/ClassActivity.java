package duethis;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ClassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Add Course");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePicker();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePicker();
        newFragment.show(getFragmentManager(), "timePicker");
    }
}
