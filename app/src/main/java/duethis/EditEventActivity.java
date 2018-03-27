package duethis;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Time;
import java.util.Calendar;
import java.util.List;

import controller.DueThisController;
import controller.InvalidInputException;
import model.Event;
import model.Student;

public class EditEventActivity extends EditActivity {
    private Calendar calendarEventStartDate = Calendar.getInstance();
    private Calendar calendarEventStopDate = Calendar.getInstance();
    final private String fromTimeField = "fromTimeField";
    final private String toTimeField = "toTimeField";
    private String lastClicked = fromTimeField;

    DueThisController controller = new DueThisController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Edit Event");

        // getting the event id from a bundle.
        Intent parameters = getIntent();
        String eventId = parameters.getStringExtra("EventID");


        final Student student = duethis.DueThisApplication.student;

        List<Event> events = student.getEvents();

        // getting the event to modify from the student.
        Event eventToModify = null;
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getId().equals(eventId)) {
                eventToModify = events.get(i);
                break;
            }
        }

        // in practice this should not happen but just in case.
        if (eventToModify == null) {
            startActivity(new Intent(EditEventActivity.this, MainActivity.class));
            Tools.exceptionToast(getApplicationContext(), "Event not found.");
        }


        EditText nameText = findViewById(R.id.eventNameTextfield);
        nameText.setText(eventToModify.getName(), TextView.BufferType.EDITABLE);


        final CheckBox checkBox = findViewById(R.id.eventRepeatCheckBox);
        checkBox.setChecked(eventToModify.getRepeatedWeekly());

        java.sql.Time eventStartDate = eventToModify.getStartTime();
        java.sql.Time eventEndDate = eventToModify.getEndTime();
        calendarEventStartDate.setTime(eventStartDate);
        calendarEventStopDate.setTime(eventEndDate);


        final Event eventOnSubmit = eventToModify;


        // Delete event
        Button deleteButton = findViewById(R.id.eventDeleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean successful = false;
                try {
                    successful = controller.removeEvent(student, eventOnSubmit);
                } catch (InvalidInputException e) {
                    Tools.exceptionToast(getApplicationContext(), "Can't remove event");
                }

                if (successful) {
                    startActivity(new Intent(EditEventActivity.this, MainActivity.class));
                    Tools.exceptionToast(getApplicationContext(), "Event deleted");
                }
            }
        });

        // Click Submit
        Button submitButton = findViewById(R.id.eventSubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText nameField = findViewById(R.id.eventNameTextfield);
                String name = nameField.getText().toString();
                java.sql.Date date = new java.sql.Date(calendarEventStartDate.getTimeInMillis());
                Time startTime = new Time(calendarEventStartDate.getTimeInMillis());
                Time endTime = new Time(calendarEventStopDate.getTimeInMillis());
                final CheckBox checkBox = findViewById(R.id.eventRepeatCheckBox);
                boolean repeatWeekly = checkBox.isChecked();

                boolean successful = false;
                try {
                    successful = controller.editEvent(eventOnSubmit, name, date, startTime, endTime, repeatWeekly);
                } catch (InvalidInputException e) {
                    Tools.exceptionToast(getApplicationContext(), e.getMessage());
                }

                if (successful) {
                    startActivity(new Intent(EditEventActivity.this, MainActivity.class));
                }
            }
        });

        final EditEventActivity currentActivity = this;

        //Click on From Date Picker
        Button fromTimeButton = findViewById(R.id.eventFromTimeButton);
        fromTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastClicked = fromTimeField;
                if (getFragmentManager().findFragmentByTag("fromTimePicker") == null) {
                    currentActivity.showTimePickerDialog(v);
                }
            }
        });

        //Click on To Date Picker
        Button toTimeButton = findViewById(R.id.eventToTimeButton);
        toTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastClicked = toTimeField;
                if (getFragmentManager().findFragmentByTag("toTimePicker") == null) {
                    currentActivity.showTimePickerDialog(v);
                }
            }
        });

    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
        calendarEventStartDate.set(Calendar.YEAR, year);
        calendarEventStartDate.set(Calendar.MONTH, month);
        calendarEventStartDate.set(Calendar.DATE, day);
        calendarEventStopDate.set(Calendar.YEAR, year);
        calendarEventStopDate.set(Calendar.MONTH, month);
        calendarEventStopDate.set(Calendar.DATE, day);
    }

    @Override
    public void onTimeSet(android.widget.TimePicker timePicker, int hour, int minute) {
        if (lastClicked.equals(fromTimeField)) {
            calendarEventStartDate.set(Calendar.HOUR_OF_DAY, hour);
            calendarEventStartDate.set(Calendar.MINUTE, minute);
        } else {
            calendarEventStopDate.set(Calendar.HOUR_OF_DAY, hour);
            calendarEventStopDate.set(Calendar.MINUTE, minute);
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        Bundle args = new Bundle();
        java.sql.Date theDate = new java.sql.Date(calendarEventStartDate.getTimeInMillis());
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
        java.sql.Time eventTime;
        if (lastClicked.equals(fromTimeField)) {
            eventTime = new java.sql.Time(calendarEventStartDate.getTimeInMillis());
        } else {
            eventTime = new java.sql.Time(calendarEventStopDate.getTimeInMillis());
        }
        int hours = eventTime.getHours();
        int minutes = eventTime.getMinutes();
        args.putInt("hour", hours);
        args.putInt("minute", minutes);
        newFragment.setArguments(args);
        newFragment.show(getFragmentManager(), "timePicker");
    }
}
