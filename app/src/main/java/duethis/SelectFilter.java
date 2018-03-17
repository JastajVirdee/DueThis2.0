package duethis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import model.Student;
import model.Assignment;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SelectFilter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_filter);
        getSupportActionBar().setTitle("Select Filter");

        final Student student = DueThisApplication.student;


        // All Assignments
        Button allAssignmentButton = findViewById(R.id.allAssignmentButton);
        allAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectFilter.this, ViewAssignmentList.class);
                // pass to view assignments list the boolean flag all students
                intent.putExtra("all_students",true);
                startActivity(new Intent(SelectFilter.this, ViewAssignmentList.class));
            }
        });

    }


    public static ArrayList<String> getAssignmentStringList(List<Assignment> assignments){
        ArrayList<String> assignmentStrings = new ArrayList<String>();
        Iterator<Assignment> iterator = assignments.iterator();
        while(iterator.hasNext()){
            Assignment tempAssignment = iterator.next();
            String toDo = "To Do";
            if(tempAssignment.isIsCompleted()){
                toDo = "Done";
            }
            assignmentStrings.add("Name: "+ tempAssignment.getName() + ", Course: " + tempAssignment.getCourse() + " " + toDo);
        }
        return assignmentStrings;
    }
}
