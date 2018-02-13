package duethis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Rahul on 2/13/2018.
 */

public class ViewAssignmentsByDay  extends AppCompatActivity {
    int year;
    int month;
    int day;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("View Assignments");

        Intent parameters = getIntent();
        year = parameters.getIntExtra("year", 0);
        month = parameters.getIntExtra("month", 0);
        day = parameters.getIntExtra("day", 0);
    }
}
