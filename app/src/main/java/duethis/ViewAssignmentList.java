package duethis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import model.Assignment;
import model.Student;

public class ViewAssignmentList extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assignment_list);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("View Assignments");

        Intent parameters = getIntent();
        boolean all_students = parameters.getBooleanExtra("all_students", true);

        Student student = DueThisApplication.student;
        List<Assignment> assignments = new ArrayList<>();

        // implement the backend filtering methods here. you should be able to do
        if (all_students) {
            assignments = student.getAssignments();
        }


        // displaying the assignments as a list, can potentially always be displayed the same way for
        // simplicity.

        final ArrayList<Assignment> assignmentsReference = new ArrayList<>();
        assignmentsReference.addAll(assignments);
        final ArrayList<String> assignmentsDisplay = getAssignmentStringList(assignments);

        ListAdapter assignmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, assignmentsDisplay);
        ListView assignmentView = findViewById(R.id.assignmentFilterListView);
        assignmentView.setAdapter(assignmentAdapter);

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
