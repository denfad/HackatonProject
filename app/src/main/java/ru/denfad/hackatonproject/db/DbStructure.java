package ru.denfad.hackatonproject.db;

class DbStructure{

    static final String DATABASE_NAME = "tasks.db";
    static final int DATABASE_VERSION = 1;
    static final String TABLE_TASKS = "tasks";

    //TASKS TABLE
    static final String TASK_ID="id";
    static final String TASK_TITLE = "title";
    static final String TASK_INFO = "info";
    static final String TASK_TYPE = "type";
    static final String TASK_GOAL = "goal";
    static final String TASK_PROGRESS="progress";
    static final String TASK_DONE = "done";

}
