package duethis;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import controller.InvalidInputException;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Home");

        // Click Add Assignment
        Button addAssignmentButton = findViewById(R.id.mainAddAssignmentButton);
        //noinspection Convert2Lambda
        addAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AssignmentActivity.class));
            }
        });

        // Click Add Assignment
        Button addEventButton = findViewById(R.id.mainAddEventButton);
        //noinspection Convert2Lambda
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EventActivity.class));
            }
        });

        // Click Hours Available
        Button hoursAvailableButton = findViewById(R.id.mainHoursAvailableButton);
        //noinspection Convert2Lambda
        hoursAvailableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HoursAvailableActivity.class));
            }
        });

        // ExperiencedNovice Selection
        Button setExperienceButton = findViewById(R.id.mainSetExperience);
        //noinspection Convert2Lambda
        setExperienceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ExperiencedNoviceActivity.class));
            }
        });

        // Delete account
        Button deleteAccountButton = findViewById(R.id.mainDeleteAccountButton);
        //noinspection Convert2Lambda
        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //noinspection Convert2Lambda
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete Account")
                        .setMessage("Do you really want to permanently delete this account?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        try {
                                            duethis.DueThisApplication.controllerAccount.deleteAccount(duethis.DueThisApplication.student.getUsername(), duethis.DueThisApplication.student.getPassword());
                                        } catch (InvalidInputException e) {
                                            e.printStackTrace();
                                        }
                                        duethis.DueThisApplication.student = null;
                                        startActivity(new Intent(MainActivity.this, LoginActivityNew.class));
                                        Toast.makeText(getApplicationContext(), "User account deleted", Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        // Logout Button
        Button logoutButton = findViewById(R.id.mainLogout);
        //noinspection Convert2Lambda
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivityNew.class));

            }
        });

        //View Assignments/Exams
        Button viewAssignmentExamButton = findViewById(R.id.mainViewAssignmentExam);
        //noinspection Convert2Lambda
        viewAssignmentExamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SelectFilter.class));
            }
        });
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePicker();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
        Intent intent = new Intent(MainActivity.this, ViewByDay.class);
        Bundle parameters = new Bundle();
        parameters.putInt("year", year);
        parameters.putInt("month", month);
        parameters.putInt("day", day);
        intent.putExtras(parameters);
        startActivity(intent);
    }
}
