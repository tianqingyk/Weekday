package com.team3.weekday.valueobject;

public enum TaskType {
    Task(0),
    SubTask(1);

    private int type;

    TaskType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }
    public static TaskType getTaskType(int type) {
        for (var taskType : TaskType.values()) {
            if (taskType.type == type) {
                return taskType;
            }
        }
        return null;
    }
}
