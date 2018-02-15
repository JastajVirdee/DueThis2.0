package duethis;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import controller.DueThisController;
import controller.InvalidInputException;
import model.Event;
import model.Student;

public class EditEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    DialogFragment fromTimeFragment = new TimePicker();
    DialogFragment toTimeFragment = new TimePicker();
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

        // used to get global student variable.
        final DueThisApplication application = (DueThisApplication) this.getApplication();

        // getting the event id from a bundle.

        Intent parameters = getIntent();
        String eventId = parameters.getStringExtra("EventID");


        Student student = application.student;

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


        EditText nameText = (EditText) findViewById(R.id.eventNameTextfield);
        nameText.setText(eventToModify.getName(), TextView.BufferType.EDITABLE);


        final CheckBox checkBox = (CheckBox) findViewById(R.id.eventRepeatCheckBox);
        checkBox.setChecked(eventToModify.getRepeatedWeekly());

        Date eventStartDate = eventToModify.getStartTime();
        Date eventEndDate = eventToModify.getEndTime();
        calendarEventStartDate.setTime(eventStartDate);
        calendarEventStopDate.setTime(eventEndDate);


        final Event eventOnSubmit = eventToModify;


        // Click Back
        Button backButton = findViewById(R.id.eventBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditEventActivity.this, MainActivity.class));
            }
        });

        // Click Submit
        Button submitButton = findViewById(R.id.eventSubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText nameField = (EditText) findViewById(R.id.eventNameTextfield);
                String name = nameField.getText().toString();
                java.sql.Date date = new java.sql.Date(calendarEventStartDate.getTimeInMillis());
                Time startTime = new Time(calendarEventStartDate.getTimeInMillis());
                Time endTime = new Time(calendarEventStopDate.getTimeInMillis());
                final CheckBox checkBox = (CheckBox) findViewById(R.id.eventRepeatCheckBox);
                boolean repeatWeekly = checkBox.isChecked();

                Student student = application.student;
                boolean successful = false;
                try {
                    boolean created = controller.editEvent(eventOnSubmit, name, date, startTime, endTime, repeatWeekly);
                    successful = true;
                } catch (InvalidInputException e) {
                    Tools.exceptionToast(getApplicationContext(), e.getMessage());
                }

                if (successful) {
                    startActivity(new Intent(EditEventActivity.this, MainActivity.class));
                }
            }
        });


        //Click on From Date Picker
        Button fromTimeButton = findViewById(R.id.eventFromTimeButton);
        fromTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastClicked = fromTimeField;
                if (getFragmentManager().findFragmentByTag("fromTimePicker") == null) {
                    fromTimeFragment.show(getFragmentManager(), "fromTimePicker");
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
                    toTimeFragment.show(getFragmentManager(), "toTimePicker");
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
        args.putInt("year", calendarEventStartDate.YEAR);
        args.putInt("month", calendarEventStartDate.MONTH);
        args.putInt("day", calendarEventStartDate.DAY_OF_MONTH);
        newFragment.setArguments(args);
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        Bundle args = new Bundle();
        if (lastClicked.equals(fromTimeField)) {
            args.putInt("hour", calendarEventStartDate.HOUR);
            args.putInt("minute", calendarEventStartDate.MINUTE);
        } else {
            args.putInt("hour", calendarEventStopDate.HOUR);
            args.putInt("minute", calendarEventStopDate.MINUTE);
        }
        newFragment.setArguments(args);
        newFragment.show(getFragmentManager(), "timePicker");
    }
}
