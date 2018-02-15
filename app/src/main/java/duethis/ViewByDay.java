package duethis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.Assignment;
import model.Event;

import static duethis.DueThisApplication.student;

/**
 * Created by Rahul on 2/13/2018.
 */

public class ViewByDay extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_by_day);
        getSupportActionBar().setTitle("View Assignments");

        Intent parameters = getIntent();
        int year = parameters.getIntExtra("year", 0);
        int month = parameters.getIntExtra("month", 0);
        int day = parameters.getIntExtra("day", 0);

        String dateString = year+"-"+(month+1)+"-"+day;
        Date date = Date.valueOf(dateString);


        final List<Assignment> assignmentList = DueThisApplication.controller.showAssignment(student, date);
        List<String> displayAssignmentList = getAssignmentStringList(assignmentList);
        ListAdapter assignmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayAssignmentList);
        ListView assignmentView = (ListView) findViewById(R.id.assignmentListView);
        assignmentView.setAdapter(assignmentAdapter);

        assignmentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Assignment assignment = assignmentList.get(position);
                Intent intent = new Intent(ViewByDay.this, EditAssignmentActivity.class); //TODO: change to editAssignmentActivity
                String assignmentId = assignment.getId();
                intent.putExtra("AssignmentID", assignmentId);
                startActivity(intent);
            }
        });

        final List<Event> eventList = DueThisApplication.controller.showEvent(student, date);
        List<String> displayEventList = getEventStringList(eventList);
        ListAdapter eventAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayEventList);
        ListView eventView = (ListView) findViewById(R.id.eventListView);
        eventView.setAdapter(eventAdapter);

        eventView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Event event = eventList.get(position);
                Intent intent = new Intent(ViewByDay.this, EditEventActivity.class); //TODO: change to editEventActivity
                String eventId = event.getId();
                intent.putExtra("EventID", eventId);
                startActivity(intent);
            }
        });


    }

    public static List<String> getAssignmentStringList(List<Assignment> assignments){
        List<String> assignmentStrings = new ArrayList<String>();
        Iterator<Assignment> iterator = assignments.iterator();
        while(iterator.hasNext()){
            Assignment tempAssignment = iterator.next();
            assignmentStrings.add("Name: "+ tempAssignment.getName() + ", Course: " + tempAssignment.getCourse());
        }
        return assignmentStrings;
    }

    public static List<String> getEventStringList(List<Event> events){
        List<String> eventStrings = new ArrayList<String>();
        Iterator<Event> iterator = events.iterator();
        while(iterator.hasNext()){
            Event tempAssignment = iterator.next();
            eventStrings.add(tempAssignment.getName());
        }
        return eventStrings;
    }





}
