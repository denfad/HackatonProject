package ru.denfad.hackatonproject.db;

import android.content.Context;

import java.util.List;

import ru.denfad.hackatonproject.model.Task;

public class DbService {
    private DbWorker mDbWorker;

    public DbService(Context context){
        mDbWorker=new DbWorker(context);
    }

    public void deleteTask(int id){
        mDbWorker.deleteTask(id);
    }

    public void updateTask(Task task){
        mDbWorker.updateTask(task.getId(),task.getTitle(),task.getInfo(),task.getType(),task.getGoal(),task.getProgress(),task.getDone());
    }

    public Task getTask(int id){
        return mDbWorker.selectTask(id);
    }

    public void addTask(Task task){
        mDbWorker.insertTask(task.getTitle(),task.getInfo(),task.getType(),task.getGoal(),task.getProgress(),task.getDone());
    }

    public List<Task> getAllTasks(){
        return mDbWorker.selectAllTasks();
    }
}
