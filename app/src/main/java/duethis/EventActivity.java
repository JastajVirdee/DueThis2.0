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
import android.util.Log;

import java.sql.Time;
import java.util.Calendar;
import java.sql.Date;

import controller.DueThisController;
import controller.InvalidInputException;
import model.Student;


public class EventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


    DialogFragment fromTimeFragment = new TimePicker();
    DialogFragment toTimeFragment = new TimePicker();
    final private String  fromTimeField = "fromTimeField";
    final private String toTimeField = "toTimeField";
    private String lastClicked = fromTimeField;

    Calendar start = Calendar.getInstance();
    Calendar end = Calendar.getInstance();

    DueThisController controller = new DueThisController();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        getSupportActionBar().setTitle("Create Event");

        // used to get global student variable.
        final DueThisApplication application = (DueThisApplication) this.getApplication();

        Button deleteButton = findViewById(R.id.eventDeleteButton);
        deleteButton.setVisibility(View.GONE);

        // Click Submit
        Button submitButton = findViewById(R.id.eventSubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText nameField = (EditText) findViewById(R.id.eventNameTextfield);
                String name = nameField.getText().toString();
                Date date = new Date(start.getTimeInMillis());
                Time startTime = new Time(start.getTimeInMillis());
                Time endTime = new Time(end.getTimeInMillis());
                final CheckBox checkBox = (CheckBox) findViewById(R.id.eventRepeatCheckBox);
                boolean repeatWeekly = checkBox.isChecked();

                Student student = application.student;
                boolean successful = false;
                try{
                    boolean created = controller.createEvent(student, name, date, startTime, endTime, repeatWeekly);
                    successful = true;
                }catch (InvalidInputException e) {
                    Tools.exceptionToast(getApplicationContext(),e.getMessage());
                }
                if(successful){
                    startActivity(new Intent(EventActivity.this, MainActivity.class));
                }
            }
        });

        //Click on From Date Picker
        Button fromTimeButton = findViewById(R.id.eventFromTimeButton);
        fromTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastClicked = fromTimeField;
                if(getFragmentManager().findFragmentByTag("fromTimePicker") == null){
                    fromTimeFragment.show(getFragmentManager(), "fromTimePicker");
                }
            }
        });

        //Click on To Date Picker
        Button toTimeButton = findViewById(R.id.eventToTimeButton);
        toTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                lastClicked = toTimeField;
                if(getFragmentManager().findFragmentByTag("toTimePicker") == null){
                    toTimeFragment.show(getFragmentManager(), "toTimePicker");
                }
            }
        });
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePicker();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day){
        start.set(Calendar.YEAR, year);
        start.set(Calendar.MONTH, month);
        start.set(Calendar.DATE, day);
        end.set(Calendar.YEAR, year);
        end.set(Calendar.MONTH, month);
        end.set(Calendar.DATE, day);
    }

    @Override
    public void onTimeSet(android.widget.TimePicker timePicker, int hour, int minute) {
        if(lastClicked.equals(fromTimeField)){
            start.set(Calendar.HOUR_OF_DAY, hour);
            start.set(Calendar.MINUTE, minute);
        }else{
            end.set(Calendar.HOUR_OF_DAY, hour);
            end.set(Calendar.MINUTE, minute);
        }

    }
}
