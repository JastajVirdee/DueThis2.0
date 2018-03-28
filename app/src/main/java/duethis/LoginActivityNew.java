package duethis;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Driver;
import java.sql.DriverManager;

import controller.InvalidInputException;
import model.Application;
import model.Assignment;
import model.Event;
import model.Student;
import persistence.SQLiteInterface;

public class LoginActivityNew extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("DueThis Login");

        // - FIXME SQL Closing
        try {
            DriverManager.registerDriver((Driver) Class.forName("org.sqldroid.SQLDroidDriver").newInstance());
        } catch (Exception e) {
            throw new RuntimeException("Failed to register SQLDroidDriver");
        }

        String path = "jdbc:sqldroid:" + getApplicationContext().getFilesDir() + "/duethis3.db";
        System.out.println(path);

        SQLiteInterface.setFilename(path);
        try {
            SQLiteInterface.ensureConnection();
            SQLiteInterface.load();
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            System.out.println("Couldn't find libsqlitejdbc.so");
        }

        // FIXME: Remove when done debugging but keep if needed
        //noinspection ConstantConditions,ConstantIfStatement
        if (false) {
            System.out.println("Assignments : " + Application.getInstance().getAssignments().size());
            for (Assignment a : Application.getInstance().getAssignments()) {
                System.out.println(a);
            }

            System.out.println("Events : " + Application.getInstance().getEvents().size());
            for (Event e : Application.getInstance().getEvents()) {
                System.out.println(e);
            }

            System.out.println("Students : " + Application.getInstance().getStudents().size());
            for (Student s : Application.getInstance().getStudents()) {
                System.out.println(s);
            }
        }

        // Click Register Button
        Button registerButton = findViewById(R.id.registerButton);
        //noinspection Convert2Lambda
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivityNew.this, RegisterActivity.class));
            }
        });

        // Click Login Button
        Button loginSubmitButton = findViewById(R.id.loginSubmitButton);
        //noinspection Convert2Lambda
        loginSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText unameField = findViewById(R.id.loginUsernameField);
                String unameText = unameField.getText().toString();

                EditText pwordField = findViewById(R.id.loginPasswordField);
                String pwordText = pwordField.getText().toString();

                try {
                    duethis.DueThisApplication.student = duethis.DueThisApplication.controllerAccount.logIn(unameText, pwordText);
                } catch (controller.InvalidInputException e) {
                    Tools.exceptionToast(getApplicationContext(), e.getMessage());
                }

                // Successful
                if (duethis.DueThisApplication.student != null) {
                    Tools.exceptionToast(getApplicationContext(), "Login successful!\n  Welcome " + unameText);
                    startActivity(new Intent(LoginActivityNew.this, MainActivity.class));
                }
            }
        });
    }
}
