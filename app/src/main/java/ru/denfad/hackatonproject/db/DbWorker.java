package ru.denfad.hackatonproject.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ru.denfad.hackatonproject.model.Task;

class DbWorker  extends DbStructure{

    private SQLiteDatabase mDataBase;

    DbWorker(Context context){
        OpenHelper mOpenHelper = new OpenHelper(context);
        mDataBase = mOpenHelper.getWritableDatabase();
    }

    void updateTask(int id, String title, String info, String type, Integer goal, Integer progress, Boolean done){
        ContentValues cv = new ContentValues();

        cv.put(TASK_TITLE,title);
        cv.put(TASK_INFO,info);
        cv.put(TASK_TYPE,type);
        cv.put(TASK_GOAL, goal);
        cv.put(TASK_PROGRESS,progress);
        cv.put(TASK_DONE, done ? 1 : 0);

        mDataBase.update(TABLE_TASKS,cv,TASK_ID+" =?",new String[]{String.valueOf(id)});
    }

    void deleteTask(int id){
        mDataBase.delete(TABLE_TASKS, TASK_ID+" =?",new String[]{String.valueOf(id)});
    }

    Task selectTask(int id){
        Cursor mCursor = mDataBase.query(TABLE_TASKS,null,TASK_ID+" =?",new String[]{String.valueOf(id)},null,null,null);

        mCursor.moveToFirst();
        int taskId = mCursor.getInt(0);//id
        String title = mCursor.getString(1); //title
        String info = mCursor.getString(2);//info
        String type = mCursor.getString(3);
        int goal = mCursor.getInt(4);//goal
        int progress = mCursor.getInt(5); //progress
        Boolean done = mCursor.getInt(6)==1;

        return new Task(taskId,title,info,type,goal,progress,done);
    }

    void insertTask(String title, String info, String type, Integer goal, Integer progress, Boolean done){
        ContentValues cv = new ContentValues();

        cv.put(TASK_TITLE,title);
        cv.put(TASK_INFO,info);
        cv.put(TASK_TYPE,type);
        cv.put(TASK_GOAL, goal);
        cv.put(TASK_PROGRESS,progress);
        cv.put(TASK_DONE, done ? 1 : 0);
        mDataBase.insert(TABLE_TASKS, null, cv);
    }

    List<Task> selectAllTasks(){
        Cursor mCursor = mDataBase.query(TABLE_TASKS,null,null,null,null,null,null);

        List<Task> arr = new ArrayList<>();
        mCursor.moveToFirst();

        if(!mCursor.isAfterLast()){
            do{
                int taskId = mCursor.getInt(0);//id
                String title = mCursor.getString(1); //title
                String info = mCursor.getString(2);//info
                String type = mCursor.getString(3);
                int goal = mCursor.getInt(4);//goal
                int progress = mCursor.getInt(5); //progress
                Boolean done = mCursor.getInt(6)==1;
                arr.add(new Task(taskId,title,info,type,goal,progress,done));
            }while(mCursor.moveToNext());
        }
        return arr;
    }

    private class OpenHelper extends SQLiteOpenHelper{

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE "+TABLE_TASKS+" ("+
                    TASK_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    TASK_TITLE+" TEXT, "+
                    TASK_INFO+" TEXT, "+
                    TASK_TYPE+" TEXT, "+
                    TASK_GOAL+" INTEGER, "+
                    TASK_PROGRESS+" INTEGER, "+
                    TASK_DONE+" INTEGER DEFAULT 0 );";

            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
            onCreate(db);
        }
    }
}
