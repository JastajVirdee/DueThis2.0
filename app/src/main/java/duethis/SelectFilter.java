package duethis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SelectFilter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_filter);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Select Filter");

        // All Assignments
        Button allAssignmentButton = findViewById(R.id.allAssignmentButton);
        allAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectFilter.this, ViewAssignmentList.class);
                // pass to view assignments list the boolean flag all students
                intent.putExtra("all_students", true);
                startActivity(new Intent(SelectFilter.this, ViewAssignmentList.class));
            }
        });
    }
}
