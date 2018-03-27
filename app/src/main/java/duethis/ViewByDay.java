package duethis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import model.Assignment;
import model.Event;

import static duethis.DueThisApplication.student;


// Created by Rahul on 2/13/2018.

public class ViewByDay extends AppCompatActivity {

    public static ArrayList<String> getAssignmentStringList(List<Assignment> assignments) {
        ArrayList<String> assignmentStrings = new ArrayList<>();

        for (Assignment a : assignments) {
            String toDo = "To Do";
            if (a.isIsCompleted()) {
                toDo = "Done";
            }

            assignmentStrings.add("Name: " + a.getName() + ", Course: " + a.getCourse() + " " + toDo);
        }

        return assignmentStrings;
    }

    public static List<String> getEventStringList(List<Event> events) {
        List<String> eventStrings = new ArrayList<>();

        for (Event e : events) {
            eventStrings.add(e.getName());
        }

        return eventStrings;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_by_day);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("View Assignments");

        Intent parameters = getIntent();
        int year = parameters.getIntExtra("year", 0);
        int month = parameters.getIntExtra("month", 0);
        int day = parameters.getIntExtra("day", 0);

        String dateString = year + "-" + (month + 1) + "-" + day;
        Date date = Date.valueOf(dateString);


        final List<Assignment> assignmentList = DueThisApplication.controller.showAssignment(student, date);
        List<String> displayAssignmentList = getAssignmentStringList(assignmentList);
        ListAdapter assignmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayAssignmentList);
        ListView assignmentView = findViewById(R.id.assignmentListView);
        assignmentView.setAdapter(assignmentAdapter);

        assignmentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Assignment assignment = assignmentList.get(position);
                Intent intent = new Intent(ViewByDay.this, EditAssignmentActivity.class);
                String assignmentId = assignment.getId();
                intent.putExtra("AssignmentID", assignmentId);
                startActivity(intent);
            }
        });

        final List<Event> eventList = DueThisApplication.controller.showEvent(student, date);
        List<String> displayEventList = getEventStringList(eventList);
        ListAdapter eventAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayEventList);
        ListView eventView = findViewById(R.id.eventListView);
        eventView.setAdapter(eventAdapter);

        eventView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Event event = eventList.get(position);
                Intent intent = new Intent(ViewByDay.this, EditEventActivity.class);
                String eventId = event.getId();
                intent.putExtra("EventID", eventId);
                startActivity(intent);
            }
        });


        Button addAssignmentButton = findViewById(R.id.addAssignmentButton);
        addAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewByDay.this, AssignmentActivity.class));
            }
        });

        Button addEventButton = findViewById(R.id.addEventButton);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewByDay.this, EventActivity.class));
            }
        });

    }
}
