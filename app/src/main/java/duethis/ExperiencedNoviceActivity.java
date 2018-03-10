package duethis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
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


        if(student.getExperienced()){
            textView.setText("Experienced Student");
        }else {
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

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_experienced:
                if (checked)
                    student.setExperienced(true);
                    textView.setText("Experienced Student");
                    break;
            case R.id.radio_novice:
                if (checked)
                    student.setExperienced(false);
                    textView.setText("Novice Student");
                    break;
        }
    }
}
