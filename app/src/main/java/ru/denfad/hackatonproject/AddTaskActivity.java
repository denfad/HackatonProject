package ru.denfad.hackatonproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;

import java.text.SimpleDateFormat;
import java.util.Date;

import ru.denfad.hackatonproject.db.DbService;
import ru.denfad.hackatonproject.model.Task;

public class AddTaskActivity extends AppCompatActivity {

    private DbService dbService;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_activity);

        dbService = new DbService(getApplicationContext());

        final EditText title=findViewById(R.id.add_title);
        final EditText info = findViewById(R.id.add_info);
        final Spinner type = findViewById(R.id.add_type);
        final EditText goal = findViewById(R.id.add_goal);



        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-d");
                String date = dateformat.format(new Date());
                dbService.addTask(new Task(
                        title.getText().toString(),
                        info.getText().toString(),
                        type.getSelectedItem().toString(),
                        Integer.parseInt(goal.getText().toString()),
                        date,
                        null,
                        0,false));
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}
