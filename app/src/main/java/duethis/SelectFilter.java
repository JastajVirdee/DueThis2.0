package duethis;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

public class SelectFilter extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_filter);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Select Filter");

        // All Assignments
        Button allAssignmentButton = findViewById(R.id.allAssignmentButton);
        //noinspection Convert2Lambda
        allAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectFilter.this, ViewAssignmentList.class);
                // pass to view assignments list the boolean flag all students
                intent.putExtra("all_students", true);
                intent.putExtra("incomplete", false);
                intent.putExtra("completed", false);
                startActivity(intent);
            }
        });

        // Incomplete Assignments
        Button incompleteAssignmentButton = findViewById(R.id.incompleteAssignmentButton);
        //noinspection Convert2Lambda
        incompleteAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectFilter.this, ViewAssignmentList.class);
                // pass to view assignments list the boolean flag all students and incomplete
                intent.putExtra("all_students", false);
                intent.putExtra("incomplete", true);
                intent.putExtra("completed", false);
                startActivity(intent);
            }
        });

        // Complete Assignments
        Button completedAssignmentButton = findViewById(R.id.completedAssignmentButton);
        //noinspection Convert2Lambda
        completedAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectFilter.this, ViewAssignmentList.class);
                // pass to view assignments list
                intent.putExtra("all_students", false);
                intent.putExtra("by_course", false);
                intent.putExtra("incomplete", false);
                intent.putExtra("completed", true);

                startActivity(intent);
            }
        });

        // Assignments by course
        Button courseAssignmentButton = findViewById(R.id.courseAssignmentButton);
        //noinspection Convert2Lambda
        courseAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText courseNameText = findViewById(R.id.courseNameTextField);
                String courseName = courseNameText.getText().toString();
                Intent intent = new Intent(SelectFilter.this, ViewAssignmentList.class);

                // pass to view assignments list the boolean flag all students
                intent.putExtra("by_course", true);
                intent.putExtra("course_name", courseName);
                intent.putExtra("incomplete", false);
                intent.putExtra("completed", false);
                startActivity(intent);
            }
        });
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePicker();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DATE, day);
        long result = c.getTimeInMillis();
        Intent intent = new Intent(SelectFilter.this, ViewAssignmentList.class);
        intent.putExtra("all_students", false);
        intent.putExtra("date", result);
        startActivity(intent);
    }
}
