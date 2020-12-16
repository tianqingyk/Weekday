package com.team3.weekday.valueobject;


public enum TaskStatus {
    BEFORE(0),
    PROCESSING(1),
    FINISH(2);

    public int status;

    TaskStatus(int status) { this.status = status; }

    public int getStatus() {
        return status;
    }

    public static TaskStatus getTaskStatus(Integer status){
        for ( var taskStatus : values()){
            if (taskStatus.status == status){
                return taskStatus;
            }
        }
        return null;
    }
}