package ru.denfad.hackatonproject.model;

public class Task {

    private int id;
    private String title;
    private String info;
    private String type;
    private Integer goal;
    private Integer progress;
    private Boolean done;

    public Task(int id, String title, String info, String type, Integer goal, Integer progress, Boolean done) {
        this.id = id;
        this.title = title;
        this.info = info;
        this.type = type;
        this.goal = goal;
        this.progress = progress;
        this.done = done;
    }

    public Task(String title, String info, String type, Integer goal, Integer progress, Boolean done) {
        this.title = title;
        this.info = info;
        this.type = type;
        this.goal = goal;
        this.progress = progress;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getGoal() {
        return goal;
    }

    public void setGoal(Integer goal) {
        this.goal = goal;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public void updateProgress(int delta){this.progress=this.progress+delta;}
}
