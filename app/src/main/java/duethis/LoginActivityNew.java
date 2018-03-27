package duethis;

import java.sql.DriverManager;
import java.sql.Driver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import controller.InvalidInputException;
import model.Student;
import persistence.SQLiteInterface;

public class LoginActivityNew extends AppCompatActivity {
    private EditText unameField;
    private EditText pwordField;
    Student student = null;

    // TODO: Remove data from application.
    // FIXME: Is this the main activity that is being launched? If so, then, loading happens here.

    //@RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);
        getSupportActionBar().setTitle("DueThis Login");

        // used to get global student variable
        final DueThisApplication application = (DueThisApplication) this.getApplication();

        try {
            DriverManager.registerDriver((Driver) Class.forName("org.sqldroid.SQLDroidDriver").newInstance());
        } catch (Exception e) {
            throw new RuntimeException("Failed to register SQLDroidDriver");
        }

        String path = "jdbc:sqldroid:" + getApplicationContext().getFilesDir() + "/duethis1.db";
        System.out.println(path);

        SQLiteInterface.setFilename(path);
        try {
            SQLiteInterface.ensureConnection();
            SQLiteInterface.load();
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        } catch (UnsatisfiedLinkError e) {
            System.out.println("Couldn't find libsqlitejdbc.so");
        }

        // Click Register Button
        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivityNew.this, RegisterActivity.class));
            }
        });


        // Click Login Button
        Button loginSubmitButton = findViewById(R.id.loginSubmitButton);
        loginSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unameField = findViewById(R.id.loginUsernameField);
                String unameText = unameField.getText().toString();

                pwordField = findViewById(R.id.loginPasswordField);
                String pwordText = pwordField.getText().toString();

                //application.student = null;
                try {
                    //Student s = acctController.logIn(unameText, pwordText);
                    student = application.controllerAccount.logIn(unameText, pwordText);
                    application.student = student;
                } catch (controller.InvalidInputException e) {
                    Tools.exceptionToast(getApplicationContext(), e.getMessage());
                }

                if (student != null) // Successful
                {
                    //TODO: Load data from persistence
                    Tools.exceptionToast(getApplicationContext(), "Login successful!\n  Welcome " + unameText);
                    startActivity(new Intent(LoginActivityNew.this, MainActivity.class));
                }
                // no else needed because error is already caught earlier
            }
        });
    }
}







