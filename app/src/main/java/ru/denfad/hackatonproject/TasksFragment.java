package ru.denfad.hackatonproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.denfad.hackatonproject.db.DbService;
import ru.denfad.hackatonproject.model.Task;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class TasksFragment extends Fragment {
    private DbService dbService;
    private List<Task> tasks;
    private ArrayAdapter adapter;

    public TasksFragment(Context context){
        dbService = new DbService(context);
    }

    public static TasksFragment newInstance(Context context){
        return new TasksFragment(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_fragment, container, false);

        ListView taskListView = rootView.findViewById(R.id.tasks_list);

        tasks = dbService.getAllTasks();
        adapter = new TaskAdapter(getContext(),android.R.layout.simple_list_item_1,tasks);
        taskListView.setAdapter(adapter);

        Button add = rootView.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),AddTaskActivity.class));
            }
        });

        return rootView;
    }

    private class TaskAdapter extends ArrayAdapter<Task> {


        public TaskAdapter(@NonNull Context context, int resource, @NonNull List<Task> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent){
            final Task task = getItem(position);

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.task_item,null);

            TaskHolder holder = new TaskHolder();
            holder.title = convertView.findViewById(R.id.title);
            holder.type = convertView.findViewById(R.id.type);
            holder.typeImage = convertView.findViewById(R.id.type_image);
            holder.doneCheck = convertView.findViewById(R.id.done_check);

            holder.title.setText(task.getTitle());
            holder.type.setText(task.getType());
            holder.typeImage.setImageResource(getTypeColor(task.getType()));
            if(task.getDone()){
                holder.doneCheck.setChecked(true);
                holder.doneCheck.setClickable(false );
            }
            holder.doneCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Task uTask = task;
                    uTask.setDone(true);
                    dbService.updateTask(uTask);
                }
            });
            convertView.setTag(holder);
            return convertView;
        }
    }

    private static class TaskHolder{
        public TextView title;
        public TextView type;
        public ImageView typeImage;
        public CheckBox doneCheck;
    }

    private int getTypeColor(String type){
        Map<String, Integer> colors = new HashMap<>();
        colors.put("Спорт",R.color.sport_color);
        colors.put("Самообразование",R.color.self_education_color);
        if (colors.get(type)==null) return R.color.nothing_color;
        else return colors.get(type);
    }
}
