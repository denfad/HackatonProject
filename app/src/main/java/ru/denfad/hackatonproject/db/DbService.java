package ru.denfad.hackatonproject.db;

import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.denfad.hackatonproject.model.Task;

public class DbService {
    private DbWorker mDbWorker;

    public DbService(Context context){
        mDbWorker=new DbWorker(context);
    }

    //удаляет цель
    public void deleteTask(int id){
        mDbWorker.deleteTask(id);
    }

    //обновляет цель
    public void updateTask(Task task){
        mDbWorker.updateTask(task.getId(),task.getTitle(),task.getInfo(),task.getType(),task.getGoal(), task.getStartDate(),task.getEndDate(),task.getProgress(),task.getDone());
    }

    //возвращает цель по id
    public Task getTask(int id){
        return mDbWorker.selectTask(id);
    }

    //добавляет цель
    public void addTask(Task task){
        mDbWorker.insertTask(task.getTitle(),task.getInfo(),task.getType(),task.getGoal(), task.getStartDate(),task.getEndDate(),task.getProgress(),task.getDone());
    }

    //возвращает все цели
    public List<Task> getAllTasks(){
        return mDbWorker.selectAllTasks();
    }

    //возращает колличество целей для переданного типа
    public int getCountOfTasksByType(String type){return mDbWorker.selectTasksByType(type).size();}

    //возвращает колличетво выполненых целей для переданного типа
    public int getCountOfDoneTasks(String type){
        return mDbWorker.selectDoneTaskByType(type).size();
    }

    //возвращает максимальное колличсетво целей из всех целей
    public int getMaxCountAll(String[] arr){
        int max = mDbWorker.selectTasksByType(arr[0]).size();
        for(int i=1; i<arr.length;i++){
            if(mDbWorker.selectTasksByType(arr[i]).size()>max) max = mDbWorker.selectTasksByType(arr[i]).size();
        }
        return max;
    }

    //возвращает махсимальное колличество выполненых целей среди всех типов
    public int getMaxCountDone(String[] arr){
        int max = mDbWorker.selectDoneTaskByType(arr[0]).size();
        for(int i=1; i<arr.length;i++){
            if(mDbWorker.selectTasksByType(arr[i]).size()>max) max = mDbWorker.selectDoneTaskByType(arr[i]).size();
        }
        return max;
    }

    public List<Task> getTaskByMonth(int month,int year){
        List<Task> all = getAllTasks();
        List<Task> byMonth = new ArrayList<>();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-d");
        for(Task t:all){
            String dateStr = t.getStartDate();
            try {
                Date date = dateformat.parse(dateStr);
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                if(c.get(Calendar.MONTH)+1==month & c.get(Calendar.YEAR)==year) byMonth.add(t);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return byMonth;
    }
}
