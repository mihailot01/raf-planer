package rs.raf.projekat1.rafplaner.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import rs.raf.projekat1.rafplaner.R;

public class TaskPreviewActivity extends AppCompatActivity {

    TextView tvTaskTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_preview);

        int index = getIntent().getIntExtra("task_index",0);
        tvTaskTitle = findViewById(R.id.tvTaskTitle);
        tvTaskTitle.setText("Task " + index);
    }
}