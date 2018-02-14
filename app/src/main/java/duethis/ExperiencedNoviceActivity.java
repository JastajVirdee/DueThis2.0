package duethis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.util.Log;
import duethis.MainActivity;
import model.ExperiencedStudent;
import model.NoviceStudent;
import model.Student;
import model.StudentRole;

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

        StudentRole studentRole = student.getStudentRole(0);
        TextView textView = (TextView) findViewById(R.id.experienceStatus);


        if(studentRole instanceof ExperiencedStudent){
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


        StudentRole studentRole = student.getStudentRole(0);
        TextView textView = (TextView) findViewById(R.id.experienceStatus);

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_experienced:
                if (checked)
                    studentRole.delete();
                    student.addStudentRole(new ExperiencedStudent(student,
                            0,
                            0,
                            0,
                            0,
                            0,
                            0,
                            0));

                    textView.setText("Experienced Student");
                    break;
            case R.id.radio_novice:
                if (checked)
                    studentRole.delete();
                    student.addStudentRole(new NoviceStudent(student));
                    textView.setText("Novice Student");
                    break;
        }
    }
}
