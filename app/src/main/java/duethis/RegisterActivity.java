package duethis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Register");

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
                final EditText userNameField = findViewById(R.id.registerUsername);
                String userName = userNameField.getText().toString();

                final EditText passwordField = findViewById(R.id.registerPassword);
                String password = passwordField.getText().toString();

                final EditText emailField = findViewById(R.id.registerEmail);
                String email = emailField.getText().toString();

                final CheckBox experiencedField = findViewById(R.id.registerExperienced);
                boolean isCompleted = experiencedField.isChecked();
                boolean successful = false;

                try {
                    successful = duethis.DueThisApplication.controllerAccount.createAccount(userName, password,
                            email, isCompleted,
                            0, 0,
                            0, 0,
                            0, 0, 0);

                    duethis.DueThisApplication.student = duethis.DueThisApplication.controllerAccount.logIn(userName, password);
                } catch (controller.InvalidInputException e) {
                    Tools.exceptionToast(getApplicationContext(), e.getMessage());
                }

                if (successful) {
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    Tools.exceptionToast(getApplicationContext(), "Welcome " + userName);
                }
            }
        });
    }
}
