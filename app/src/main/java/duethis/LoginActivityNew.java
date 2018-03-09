package duethis;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import controller.DueThisController;

//import controller.AccountController; //////////////////////////////////////////////////////////////////////////////////////


import model.Student;


public class LoginActivityNew  extends AppCompatActivity {

    private EditText unameField;
    private String thisUname;

    private EditText pwordField;
    private String thisPword;


    DueThisController controller = new DueThisController();
    // AccountController acctController = new AccountController(); //////////////////////////////////////////////////////


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);
        getSupportActionBar().setTitle("Login");

        // Click Login Button
        Button loginSubmitButton = findViewById(R.id.loginSubmitButton);
        loginSubmitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                unameField = findViewById(R.id.loginUsernameField);
                String unameText = unameField.getText().toString();


                pwordField = findViewById(R.id.loginPasswordField);
                String pwordText = pwordField.getText().toString();


                /*
                Student s = acctController.logIn(unameText, pwordText);


                if(s == null) // null if login is invalid
                {

                }
                else
                {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                */


            }
        });

    }
}







