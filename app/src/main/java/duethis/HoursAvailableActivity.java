package duethis;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import controller.DueThisController;
import model.Student;

public class HoursAvailableActivity extends AppCompatActivity {

    private EditText mondayField;
    private EditText tuesdayField;
    private EditText wedField;
    private EditText thurField;
    private EditText friField;
    private EditText satField;
    private EditText sunField;

    private int mondayHours;
    private int tuesdayHours;
    private int wedHours;
    private int thurHours;
    private int friHours;
    private int satHours;
    private int sunHours;

    DueThisController controller = new DueThisController();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hours_available);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Set Availability");

        final Student student = duethis.DueThisApplication.student;

        // Setting text in text fields to what they currently are
        mondayField = findViewById(R.id.hoursAvailableMonTextfield);
        mondayHours = student.getMondayAvailability();
        mondayField.setText(Integer.toString(mondayHours), TextView.BufferType.EDITABLE);

        tuesdayField = findViewById(R.id.hoursAvailableTueTextfield);
        tuesdayHours = student.getTuesdayAvailability();
        tuesdayField.setText(Integer.toString(tuesdayHours), TextView.BufferType.EDITABLE);

        wedField = findViewById(R.id.hoursAvailableWedTextfield);
        wedHours = student.getWednesdayAvailability();
        wedField.setText(Integer.toString(wedHours), TextView.BufferType.EDITABLE);

        thurField = findViewById(R.id.hoursAvailableThurTextfield);
        thurHours = student.getThursdayAvailability();
        thurField.setText(Integer.toString(thurHours), TextView.BufferType.EDITABLE);

        friField = findViewById(R.id.hoursAvailableFriTextfield);
        friHours = student.getFridayAvailability();
        friField.setText(Integer.toString(friHours), TextView.BufferType.EDITABLE);

        satField = findViewById(R.id.hoursAvailableSatTextfield);
        satHours = student.getSaturdayAvailability();
        satField.setText(Integer.toString(satHours), TextView.BufferType.EDITABLE);

        sunField = findViewById(R.id.hoursAvailableSunTextfield);
        sunHours = student.getSundayAvailability();
        sunField.setText(Integer.toString(sunHours), TextView.BufferType.EDITABLE);


        // Click Back
        Button backButton = findViewById(R.id.hoursAvailableBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HoursAvailableActivity.this, MainActivity.class));
            }
        });

        // Click Submit
        Button submitButton = findViewById(R.id.hoursAvailableSubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mondayText = mondayField.getText().toString();
                if (mondayText.matches("")) {
                    mondayHours = 0;
                } else {
                    mondayHours = Integer.parseInt(mondayText);
                }

                String tuesdayText = tuesdayField.getText().toString();
                if (tuesdayText.matches("")) {
                    tuesdayHours = 0;
                } else {
                    tuesdayHours = Integer.parseInt(tuesdayText);
                }

                String wedText = wedField.getText().toString();
                if (wedText.matches("")) {
                    wedHours = 0;
                } else {
                    wedHours = Integer.parseInt(wedText);
                }

                String thurText = thurField.getText().toString();
                if (thurText.matches("")) {
                    thurHours = 0;
                } else {
                    thurHours = Integer.parseInt(thurText);
                }

                String friText = friField.getText().toString();
                if (friText.matches("")) {
                    friHours = 0;
                } else {
                    friHours = Integer.parseInt(friText);
                }

                String satText = satField.getText().toString();
                if (satText.matches("")) {
                    satHours = 0;
                } else {
                    satHours = Integer.parseInt(satText);
                }

                String sunText = sunField.getText().toString();
                if (sunText.matches("")) {
                    sunHours = 0;
                } else {
                    sunHours = Integer.parseInt(sunText);
                }

                // getting student from global variable student.
                //Student student = application.student; ALREADY DONE EARLIER

                try {
                    controller.updateAvailabilities(student, sunHours, mondayHours, tuesdayHours, wedHours, thurHours, friHours, satHours);
                    startActivity(new Intent(HoursAvailableActivity.this, MainActivity.class));
                } catch (controller.InvalidInputException e) {
                    Tools.exceptionToast(getApplicationContext(), e.getMessage());
                }
            }
        });
    }
}
