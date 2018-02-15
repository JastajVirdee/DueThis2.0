package duethis;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import controller.DueThisController;
import controller.InvalidInputException;
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
        getSupportActionBar().setTitle("Set Availability");

        // used to get global student variable.
        final DueThisApplication application = (DueThisApplication) this.getApplication();

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

                mondayField = findViewById(R.id.hoursAvailableMonTextfield);
                String mondayText = mondayField.getText().toString();
                if (mondayText.matches("") ) { mondayHours = 0; }
                else { mondayHours = Integer.parseInt(mondayText); }

                tuesdayField = findViewById(R.id.hoursAvailableTueTextfield);
                String tuesdayText = tuesdayField.getText().toString();
                if (tuesdayText.matches("")) { tuesdayHours = 0; }
                else { tuesdayHours = Integer.parseInt(tuesdayText); }

                wedField = findViewById(R.id.hoursAvailableWedTextfield);
                String wedText = wedField.getText().toString();
                if (wedText.matches("")) { wedHours = 0; }
                else { wedHours = Integer.parseInt(wedText); }

                thurField = findViewById(R.id.hoursAvailableThurTextfield);
                String thurText = thurField.getText().toString();
                if (thurText.matches("")) { thurHours = 0; }
                else { thurHours = Integer.parseInt(thurText); }

                friField = findViewById(R.id.hoursAvailableFriTextfield);
                String friText = friField.getText().toString();
                if (friText.matches("")) { friHours = 0; }
                else { friHours = Integer.parseInt(friText); }

                satField = findViewById(R.id.hoursAvailableSatTextfield);
                String satText = satField.getText().toString();
                if (satText.matches("")) { satHours = 0; }
                else { satHours = Integer.parseInt(satText); }

                sunField = findViewById(R.id.hoursAvailableSunTextfield);
                String sunText = sunField.getText().toString();
                if (sunText.matches("")) { sunHours = 0; }
                else { sunHours = Integer.parseInt(sunText); }

                // getting student from global variable student.
                Student student = application.student;

                try {
                    controller.updateAvailabilities(student, sunHours, mondayHours, tuesdayHours, wedHours, thurHours, friHours, satHours);
                    startActivity(new Intent(HoursAvailableActivity.this, MainActivity.class));
                } catch (InvalidInputException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
