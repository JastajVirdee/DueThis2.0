package duethis;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.time.Duration;
import java.util.Calendar;

import controller.InvalidInputException;

public class AssignmentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    Calendar c = Calendar.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignement);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Add Assignment");

        Button deleteButton = findViewById(R.id.assignmentDeleteButton);
        deleteButton.setVisibility(View.GONE);

        // Click Submit
        Button submitButton = findViewById(R.id.assignmentSubmitButton);

        submitButton.setOnClickListener(v -> {
            // Get fields from assignment form
            final EditText nameField = findViewById(R.id.assignmentNameTextfield);
            String name = nameField.getText().toString();

            final EditText courseField = findViewById(R.id.assignmentCourseTextfield);
            String course = courseField.getText().toString();

            final EditText weightField = findViewById(R.id.assignmentWeightTextfield);
            float weight = !weightField.getText().toString().isEmpty() ? Float.parseFloat(weightField.getText().toString()) : 0;
            final EditText estimatedTimeOfCompletionField = findViewById(R.id.assignmentEstimatedTimeTextfield);

            long estimatedTimeOfCompletion = !estimatedTimeOfCompletionField.getText().toString().isEmpty() ? Long.parseLong(estimatedTimeOfCompletionField.getText().toString()) : 0;

            Duration duration = Duration.ofHours(estimatedTimeOfCompletion);
            java.sql.Date date = new java.sql.Date(c.getTimeInMillis());

            boolean successful = false;
            // Submit assignment call to backend
            try {
                successful = DueThisApplication.controller.createAssignment(name, course, date, weight, duration, DueThisApplication.student);
            } catch (InvalidInputException e) {
                Tools.exceptionToast(getApplicationContext(), e.getMessage());
            }

            if (successful) {
                startActivity(new Intent(AssignmentActivity.this, MainActivity.class));
            }
        });
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePicker();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePicker();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DATE, day);
    }

    @Override
    public void onTimeSet(android.widget.TimePicker timePicker, int hour, int minute) {
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, hour);
    }
}
