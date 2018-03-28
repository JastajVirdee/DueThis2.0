package duethis;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import controller.InvalidInputException;
import model.Assignment;
import model.Student;

public class EditAssignmentActivity extends EditActivity {
    private Calendar calendarAssignmentDueDate = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignement);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Edit Assignment");

        Intent parameters = getIntent();
        String assignmentId = parameters.getStringExtra("AssignmentID");
        List<Assignment> assignments = duethis.DueThisApplication.student.getAssignments();

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
            return;
        }

        // setting text in fields based on the assignment object

        EditText nameText = findViewById(R.id.assignmentNameTextfield);
        nameText.setText(assignmentToModify.getName(), TextView.BufferType.EDITABLE);

        EditText courseText = findViewById(R.id.assignmentCourseTextfield);
        courseText.setText(assignmentToModify.getCourse(), TextView.BufferType.EDITABLE);

        EditText weightField = findViewById(R.id.assignmentWeightTextfield);
        float weight = assignmentToModify.getGradeWeight();
        String weightString = Float.toString(weight);
        weightField.setText(weightString, TextView.BufferType.EDITABLE);

        final EditText estimatedTimeOfCompletionField = findViewById(R.id.assignmentEstimatedTimeTextfield);
        Duration estimatedTimeOfCompletion = assignmentToModify.getCompletionTime();
        if (estimatedTimeOfCompletion != null) {
            long hours = estimatedTimeOfCompletion.toHours();
            estimatedTimeOfCompletionField.setText(Tools.getFormattedString(hours), TextView.BufferType.EDITABLE);
        } else {
            estimatedTimeOfCompletionField.setText("", TextView.BufferType.EDITABLE);
        }

        Date assignmentDueDate = assignmentToModify.getDueDate();
        calendarAssignmentDueDate.setTime(assignmentDueDate);

        //completed assignment or not
        final CheckBox completedField = findViewById(R.id.assignmentDoneCheckBox);
        completedField.setChecked(assignmentToModify.getIsCompleted());

        // Click Submit
        Button submitButton = findViewById(R.id.assignmentSubmitButton);
        final Assignment assignmentOnSubmit = assignmentToModify;

        //noinspection Convert2Lambda
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get fields from assignment form
                final EditText nameField = findViewById(R.id.assignmentNameTextfield);
                String name = nameField.getText().toString();

                final EditText courseField = findViewById(R.id.assignmentCourseTextfield);
                String course = courseField.getText().toString();

                final EditText weightField = findViewById(R.id.assignmentWeightTextfield);
                float weight;
                try {
                    weight = Float.parseFloat(weightField.getText().toString());
                } catch (NumberFormatException e) {
                    weight = 0;
                }

                final EditText estimatedTimeOfCompletionField = findViewById(R.id.assignmentEstimatedTimeTextfield);
                Duration duration;
                try {
                    long estimatedTimeOfCompletion = Long.parseLong(estimatedTimeOfCompletionField.getText().toString());
                    duration = Duration.ofHours(estimatedTimeOfCompletion);
                } catch (java.lang.NumberFormatException e) {
                    duration = null;
                }

                final CheckBox completedField = findViewById(R.id.assignmentDoneCheckBox);
                boolean isCompleted = completedField.isChecked();

                java.sql.Date date = new java.sql.Date(calendarAssignmentDueDate.getTimeInMillis());

                // getting student from global variable student.
                Student student = duethis.DueThisApplication.student;

                boolean successful = false;
                // Submit assignment call to backend
                try {
                    successful = DueThisApplication.controller.editAssignment(assignmentOnSubmit, name, course, date, weight, duration, student);
                    if (isCompleted) {
                        DueThisApplication.controller.completeAssignment(student, assignmentOnSubmit);
                    }
                } catch (InvalidInputException e) {
                    Tools.exceptionToast(getApplicationContext(), e.getMessage());
                    return;
                }

                if (successful) {
                    startActivity(new Intent(EditAssignmentActivity.this, MainActivity.class));
                }
            }
        });

        Button deleteButton = findViewById(R.id.assignmentDeleteButton);
        //noinspection Convert2Lambda
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean successful = false;
                try {
                    successful = DueThisApplication.controller.removeAssignment(DueThisApplication.student, assignmentOnSubmit);
                } catch (InvalidInputException e) {
                    Tools.exceptionToast(getApplicationContext(), "Can't remove assignment");
                }

                if (successful) {
                    startActivity(new Intent(EditAssignmentActivity.this, MainActivity.class));
                    Tools.exceptionToast(getApplicationContext(), "Assignment removed");
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
