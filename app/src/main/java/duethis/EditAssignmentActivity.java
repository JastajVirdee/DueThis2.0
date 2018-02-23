package duethis;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.CheckBox;

import java.util.List;
import java.time.Duration;
import java.util.Date;
import java.util.Calendar;


import controller.DueThisController;
import controller.InvalidInputException;
import model.Student;
import model.Assignment;

public class EditAssignmentActivity extends EditActivity {

    DueThisController controller = new DueThisController();
    private Calendar calendarAssignmentDueDate = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignement);
        getSupportActionBar().setTitle("Edit Assignment");


        // used to get global student variable.
        final DueThisApplication application = (DueThisApplication) this.getApplication();

        Intent parameters = getIntent();
        String assignmentId = parameters.getStringExtra("AssignmentID");
        Student student = application.student;


        List<Assignment> assignments = student.getAssignments();

        // getting the assignment to modify from the student.
        Assignment assignmentToModify = null;
        for (int i = 0; i < assignments.size(); i++) {
            if (assignments.get(i).getId().equals(assignmentId)) {
                assignmentToModify = assignments.get(i);
                break;
            }
        }

        // in practice this should not happen but just in case.
        if (assignmentToModify == null) {
            startActivity(new Intent(EditAssignmentActivity.this, MainActivity.class));
            Tools.exceptionToast(getApplicationContext(), "Assignment not found.");
        }


        // setting text in fields based on the assignment object

        EditText nameText = (EditText) findViewById(R.id.assignmentNameTextfield);
        nameText.setText(assignmentToModify.getName(), TextView.BufferType.EDITABLE);

        EditText courseText = (EditText) findViewById(R.id.assignmentCourseTextfield);
        courseText.setText(assignmentToModify.getCourse(), TextView.BufferType.EDITABLE);

        EditText weightField = (EditText) findViewById(R.id.assignmentWeightTextfield);
        float weight = assignmentToModify.getGradeWeight();
        String weightString = Float.toString(weight);
        weightField.setText(weightString, TextView.BufferType.EDITABLE);


        final EditText estimatedTimeOfCompletionField = (EditText) findViewById(R.id.assignmentEstimatedTimeTextfield);
        Duration estimatedTimeOfCompletion = assignmentToModify.getCompletionTime();
        long hours = estimatedTimeOfCompletion.toHours();
        estimatedTimeOfCompletionField.setText(Long.toString(hours), TextView.BufferType.EDITABLE);


        Date assignmentDueDate = assignmentToModify.getDueDate();
        calendarAssignmentDueDate.setTime(assignmentDueDate);

        //completed assignment or not
        final CheckBox completedField = (CheckBox) findViewById(R.id.assignmentDoneCheckBox);
        completedField.setChecked(assignmentToModify.getIsCompleted());



        // Click Back
        Button backButton = findViewById(R.id.assignmentBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditAssignmentActivity.this, MainActivity.class));
            }
        });

        // Click Submit
        Button submitButton = findViewById(R.id.assignmentSubmitButton);


        final Assignment assignmentOnSubmit = assignmentToModify;

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get fields from assignment form
                final EditText nameField = (EditText) findViewById(R.id.assignmentNameTextfield);
                String name = nameField.getText().toString();

                final EditText courseField = (EditText) findViewById(R.id.assignmentCourseTextfield);
                String course = courseField.getText().toString();

                final EditText weightField = (EditText) findViewById(R.id.assignmentWeightTextfield);
                float weight;
                try {
                    weight = Float.parseFloat(weightField.getText().toString());
                }catch(NumberFormatException e){
                    weight = 0;
                }

                final EditText estimatedTimeOfCompletionField = (EditText) findViewById(R.id.assignmentEstimatedTimeTextfield);
                long estimatedTimeOfCompletion = Long.parseLong(estimatedTimeOfCompletionField.getText().toString());

                final CheckBox completedField  = (CheckBox)findViewById(R.id.assignmentDoneCheckBox);
                boolean isCompleted = completedField.isChecked();


                Duration duration = Duration.ofHours(estimatedTimeOfCompletion);
                java.sql.Date date = new java.sql.Date(calendarAssignmentDueDate.getTimeInMillis());

                // getting student from global variable student.
                Student student = application.student;

                boolean successful = false;
                // Submit assignment call to backend
                try {
                    controller.editAssignment(assignmentOnSubmit, name, course, date, weight, duration, student);
                    if(isCompleted){
                        controller.completeAssignment(student, assignmentOnSubmit);
                    }
                    successful = true;
                } catch (InvalidInputException e) {
                    Tools.exceptionToast(getApplicationContext(), e.getMessage());
                }

                if (successful) {
                    startActivity(new Intent(EditAssignmentActivity.this, MainActivity.class));
                }
            }
        });


    }


    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        Bundle args = new Bundle();
        java.sql.Date theDate = new java.sql.Date(calendarAssignmentDueDate.getTimeInMillis());
        String[] dateString = theDate.toString().split("-");
        int year = Integer.parseInt(dateString[0]);
        int month = Integer.parseInt(dateString[1]) - 1; // need to subtract 1 here for the date.
        int day = Integer.parseInt(dateString[2]);
        args.putInt("year", year);
        args.putInt("month", month);
        args.putInt("day", day);
        newFragment.setArguments(args);
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        Bundle args = new Bundle();
        Date assignmentTime = calendarAssignmentDueDate.getTime();
        int hours = assignmentTime.getHours();
        int minutes = assignmentTime.getMinutes();
        args.putInt("hour", hours);
        args.putInt("minute", minutes);
        newFragment.setArguments(args);
        newFragment.show(getFragmentManager(), "timePicker");
    }


    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
        calendarAssignmentDueDate.set(Calendar.YEAR, year);
        calendarAssignmentDueDate.set(Calendar.MONTH, month);
        calendarAssignmentDueDate.set(Calendar.DATE, day);
    }

    @Override
    public void onTimeSet(android.widget.TimePicker timePicker, int hour, int minute) {
        calendarAssignmentDueDate.set(Calendar.HOUR_OF_DAY, hour);
        calendarAssignmentDueDate.set(Calendar.MINUTE, hour);
    }

}
