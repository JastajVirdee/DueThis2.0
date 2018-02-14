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
import model.Student;




public class AssignmentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    Calendar c = Calendar.getInstance();


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignement);
        getSupportActionBar().setTitle("Add Assignment");

        // used to get global student variable.
        final DueThisApplication application = (DueThisApplication) this.getApplication();




        // Click Back
        Button backButton = findViewById(R.id.assignmentBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AssignmentActivity.this, MainActivity.class));
            }
        });

        // Click Submit
        Button submitButton = findViewById(R.id.assignmentSubmitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get fields from assignment form
                final EditText nameField = (EditText) findViewById(R.id.assignmentNameTextfield);
                String name = nameField.getText().toString();

                final EditText courseField = (EditText) findViewById(R.id.assignmentCourseTextfield);
                String course = courseField.getText().toString();

                final EditText weightField = (EditText) findViewById(R.id.assignmentWeightTextfield);
                float weight = Float.parseFloat(weightField.getText().toString());

                final EditText estimatedTimeOfCompletionField = (EditText) findViewById(R.id.assignmentEstimatedTimeTextfield);
                long estimatedTimeOfCompletion = Long.parseLong(estimatedTimeOfCompletionField.getText().toString());

                Duration duration = Duration.ofHours(estimatedTimeOfCompletion);
                java.sql.Date date = new java.sql.Date(c.getTimeInMillis());

                // getting student from global variable student.
                Student student = application.student;

                boolean successful = false;
                // Submit assignment call to backend
                try {
                    boolean result = DueThisApplication.controller.createAssignment(name, course, date, weight, duration, student);
                    successful = true;
                } catch (InvalidInputException e) {
                    Tools.exceptionToast(getApplicationContext(),e.getMessage());
                }

                if(successful){
                    startActivity(new Intent(AssignmentActivity.this, MainActivity.class));
                }
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
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day){
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


