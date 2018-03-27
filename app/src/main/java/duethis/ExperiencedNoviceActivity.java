package duethis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import controller.AccountController;
import controller.InvalidInputException;
import model.Student;

public class ExperiencedNoviceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experienced_novice);
        getSupportActionBar().setTitle("Set Experienced or Novice");

        // used to get global student variable.
        final DueThisApplication application = (DueThisApplication) this.getApplication();

        // Setting the experience by default here.
        Student student = application.student;
        TextView textView = (TextView) findViewById(R.id.experienceStatus);

        if (student.getExperienced()) {
            textView.setText("Experienced Student");
        } else {
            textView.setText("Novice Student");
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // used to get global student variable.
        final DueThisApplication application = (DueThisApplication) this.getApplication();

        // Setting the experience by default here.
        Student student = application.student;

        TextView textView = (TextView) findViewById(R.id.experienceStatus);

        // - FIXME Proper controller updated required for persistence to work

        AccountController accountController = new AccountController();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_experienced:
                if (checked) {
                    if (student.getExperienced())
                        textView.setText("You are already an experienced student.");
                    else {
                        try {
                            accountController.changeRole(student, true, 0, 0, 0, 0, 0, 0, 0);
                            textView.setText("You are now an experienced student.");
                        } catch (InvalidInputException e) {
                            System.out.println(e.getMessage());
                            textView.setText("Could not change student role");
                        }
                    }
                }

                break;
            case R.id.radio_novice:
                   if (checked) {
                    if (!student.getExperienced())
                        textView.setText("You are already a novice student.");
                    else {
                        try {
                            accountController.changeRole(student, false, 0, 0, 0, 0, 0, 0, 0);
                            textView.setText("You are now a novice student.");
                        } catch (InvalidInputException e) {
                            System.out.println(e.getMessage());
                            textView.setText("Could not change student role");
                        }
                    }
                }

                break;
        }
    }
}
