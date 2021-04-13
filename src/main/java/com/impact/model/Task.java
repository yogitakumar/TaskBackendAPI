package com.impact.model;

public class Task {
    public String taskId;
    public String description;
    public boolean completed;


    public Task(){}
    public Task(String taskId, String description, boolean completed) {
        this.taskId = taskId;
        this.description = description;
        this.completed = completed;
    }
        public String getTaskId() {
            return taskId;
        }

        public String getDescription() {
            return description;
        }

        public boolean isCompleted() {
            return completed;
        }

}
