package duethis;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
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

import controller.DueThisController;
import controller.InvalidInputException;
import model.Student;

//import duethis.duethis.R;


public class AssignmentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Calendar c = Calendar.getInstance();
    DueThisController controller = new DueThisController();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignement);
        getSupportActionBar().setTitle("Add Assignment");

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
        final EditText nameField = (EditText) findViewById(R.id.assignmentNameTextfield);
        String name = nameField.getText().toString();

        final EditText weightField = (EditText) findViewById(R.id.assignmentWeightTextfield);
        float weight = Float.parseFloat(weightField.getText().toString());

        final EditText estimatedTimeOfCompletionField = (EditText) findViewById(R.id.assignmentEstimatedTimeTextfield);
        long estimatedTimeOfCompletion = Long.parseLong(estimatedTimeOfCompletionField.getText().toString());

        Duration duration = Duration.ofHours(estimatedTimeOfCompletion);
        java.sql.Date date = java.sql.Date.valueOf(c.getTime().toString());

        Student student = new Student("testStudentID", "testStudentName");

        try {
            controller.createAssignment(name, "fakeCourseName", date, weight, duration, student);
        } catch (InvalidInputException e) {
            e.printStackTrace();
        }


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day){
        c.set(year, month-1, day, 0, 0);
    }
}


