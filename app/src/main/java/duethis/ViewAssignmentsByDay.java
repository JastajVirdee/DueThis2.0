package duethis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.sql.Date;
import java.util.List;

import model.Assignment;
import model.Event;

import static duethis.DueThisApplication.student;

/**
 * Created by Rahul on 2/13/2018.
 */

public class ViewAssignmentsByDay  extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assignments_by_day);
        getSupportActionBar().setTitle("View Assignments");

        Intent parameters = getIntent();
        int year = parameters.getIntExtra("year", 0);
        int month = parameters.getIntExtra("month", 0);
        int day = parameters.getIntExtra("day", 0);

        String dateString = year+"-"+(month+1)+"-"+day;
        Date date = Date.valueOf(dateString);

        List<Assignment> assignmentList = DueThisApplication.controller.showAssignment(student, date);
        ListAdapter assignmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, assignmentList);
        ListView assignmentView = (ListView) findViewById(R.id.assignmentListView);
        assignmentView.setAdapter(assignmentAdapter);

        List<Event> eventList = DueThisApplication.controller.showEvent(student, date);
        ListAdapter eventAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventList);
        ListView eventView = (ListView) findViewById(R.id.eventListView);
        eventView.setAdapter(eventAdapter);
    }

}
