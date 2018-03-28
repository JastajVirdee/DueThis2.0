package duethis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import controller.InvalidInputException;

public class ExperiencedNoviceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experienced_novice);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Set Experienced or Novice");

        // Setting the experience by default here.
        TextView textView = findViewById(R.id.experienceStatus);

        if (duethis.DueThisApplication.student.getExperienced()) {
            textView.setText(Tools.getFormattedString("Experienced Student"));
        } else {
            textView.setText(Tools.getFormattedString("Novice Student"));
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Setting the experience by default here.
        TextView textView = findViewById(R.id.experienceStatus);

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_experienced:
                if (checked) {
                    if (duethis.DueThisApplication.student.getExperienced())
                        textView.setText(Tools.getFormattedString("You are already an experienced student."));
                    else {
                        try {
                            DueThisApplication.controllerAccount.changeRole(duethis.DueThisApplication.student, true, 0, 0, 0, 0, 0, 0, 0);
                            textView.setText(Tools.getFormattedString("You are now an experienced student."));
                        } catch (InvalidInputException e) {
                            System.out.println(e.getMessage());
                            textView.setText(Tools.getFormattedString("Could not change student role"));
                        }
                    }
                }

                break;
            case R.id.radio_novice:
                if (checked) {
                    if (!duethis.DueThisApplication.student.getExperienced())
                        textView.setText(Tools.getFormattedString("You are already a novice student."));
                    else {
                        try {
                            DueThisApplication.controllerAccount.changeRole(duethis.DueThisApplication.student, false, 0, 0, 0, 0, 0, 0, 0);
                            textView.setText(Tools.getFormattedString("You are now a novice student."));
                        } catch (InvalidInputException e) {
                            System.out.println(e.getMessage());
                            textView.setText(Tools.getFormattedString("Could not change student role"));
                        }
                    }
                }
                break;
        }
    }
}
