package duethis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HoursAvailableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hours_available);

        // Click Back
        Button backButton = findViewById(R.id.hoursAvailableBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HoursAvailableActivity.this, MainActivity.class));
            }
        });

        // Click Submit
        Button submitButton = findViewById(R.id.hoursAvailableSubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HoursAvailableActivity.this, MainActivity.class));
            }
        });
    }
}
