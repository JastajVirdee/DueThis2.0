package duethis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import controller.InvalidInputException;
import model.Student;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Register");
        // used to get global student variable.
        final DueThisApplication application = (DueThisApplication) this.getApplication();

        // Click Login Button
        Button loginButton = findViewById(R.id.registerLoginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivityNew.class));
            }
        });

        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText userNameField = (EditText) findViewById(R.id.registerUsername);
                String userName = userNameField.getText().toString();

                final EditText passwordField  = (EditText)findViewById(R.id.registerPassword);
                String password = passwordField.getText().toString();

                final EditText emailField = (EditText) findViewById(R.id.registerEmail);
                String email = emailField.getText().toString();

                final CheckBox experiencedField  = (CheckBox)findViewById(R.id.registerExperienced);
                boolean isCompleted = experiencedField.isChecked();


                boolean successful = false;

                try {

                    successful = application.controllerAccount.createAccount(userName, password,
                                                                email, isCompleted,
                                                            0, 0,
                                                            0, 0,
                                                            0, 0, 0);

                    Student student = application.controllerAccount.logIn(userName, password);
                    application.student = student;
                }catch(controller.InvalidInputException e){
                    Tools.exceptionToast(getApplicationContext(), e.getMessage());
                }

                if(successful){
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    Tools.exceptionToast(getApplicationContext(), "Welcome " + userName);
                }
            }
        });

    }
}
