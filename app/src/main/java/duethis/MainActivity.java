package duethis;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.UUID;

import model.ExperiencedStudent;
import model.Student;
import model.StudentRole;

public class MainActivity extends AppCompatActivity {

    public static Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Home");



        // Click Add Assignment
        Button addAssignmentButton = findViewById(R.id.mainAddAssignmentButton);
        addAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AssignmentActivity.class));
            }
        });

        // Click Add Assignment
        Button addEventButton = findViewById(R.id.mainAddEventButton);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EventActivity.class));
            }
        });

        // Click Hours Available
        Button hoursAvailableButton = findViewById(R.id.mainHoursAvailableButton);
        hoursAvailableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HoursAvailableActivity.class));
            }
        });


        //ExperiencedNovice Selection
        Button setExperienceButton = findViewById(R.id.mainSetExperience);
        setExperienceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ExperiencedNoviceActivity.class));
            }
        });


    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePicker();
        newFragment.show(getFragmentManager(), "datePicker");
    }


}
