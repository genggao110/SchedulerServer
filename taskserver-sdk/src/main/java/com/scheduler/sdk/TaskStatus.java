package com.scheduler.sdk;

/**
 * Task任务状态信息
 * @Author: wangming
 * @Date: 2019-11-28 14:22
 */
public enum TaskStatus {
    /*
     * 任务初始化
     */
    TASK_INITED(0, "Inited"),
    /*
     * 任务开始
     */
    TASK_STARTED(1, "Started"),
    /*
     * 任务完成
     */
    TASK_FINISHED(2, "Finished"),
    /*
     * 任务失败
     */
    TASK_ERROR(-1, "Error");

    private int code;
    private String name;

    TaskStatus(int code, String name){
        this.code = code;
        this.name= name;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getCode(){
        return code;
    }

    public void setCode(int code){
        this.code = code;
    }
}
