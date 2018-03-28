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
import java.util.List;

import controller.InvalidInputException;
import model.Assignment;

public class ViewAssignmentList extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assignment_list);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("View Assignments");

        Intent parameters = getIntent();
        boolean all_students = parameters.getBooleanExtra("all_students", false);
        boolean by_course = parameters.getBooleanExtra("by_course", false);
        boolean incomplete = parameters.getBooleanExtra("incomplete", false);
        boolean completed = parameters.getBooleanExtra("completed", false);
        long date = parameters.getLongExtra("date", 0);

        List<Assignment> assignments = null;

        // implement the backend filtering methods here. you should be able to do
        try {
            if (all_students) {
                assignments = DueThisApplication.student.getAssignments();
            } else if (by_course) {
                String courseName = parameters.getStringExtra("course_name");
                assignments = DueThisApplication.controller.showAssignmentsByCourse(DueThisApplication.student, courseName);
            } else if (date != 0) {
                assignments = DueThisApplication.controller.showFilteredByDateAssignment(DueThisApplication.student, new Date(date));
            } else if (incomplete) {
                assignments = DueThisApplication.controller.showFilteredByIncompleted(DueThisApplication.student);
            } else if (completed) {
                assignments = DueThisApplication.controller.showFilteredByCompleted(DueThisApplication.student);
            }
        } catch (InvalidInputException e) {
            Tools.exceptionToast(getApplicationContext(), e.getMessage());
            return;
        }

        // displaying the assignments as a list, can potentially always be displayed the same way for
        // simplicity.
        // Have to add all assignments here to show in other method.
        final ArrayList<Assignment> assignmentsReference = new ArrayList<>();
        assignmentsReference.addAll(assignments);

        final ArrayList<String> assignmentsDisplay = Tools.getAssignmentStringList(assignmentsReference);

        ListAdapter assignmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, assignmentsDisplay);
        ListView assignmentView = findViewById(R.id.assignmentFilterListView);
        assignmentView.setAdapter(assignmentAdapter);

        //noinspection Convert2Lambda
        assignmentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Assignment assignment = assignmentsReference.get(position);
                Intent intent = new Intent(ViewAssignmentList.this, EditAssignmentActivity.class);
                String assignmentId = assignment.getId();
                intent.putExtra("AssignmentID", assignmentId);
                startActivity(intent);
            }
        });
    }
}
