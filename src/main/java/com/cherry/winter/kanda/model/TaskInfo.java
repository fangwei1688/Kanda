package com.cherry.winter.kanda.model;

import java.util.Date;

/**
 * Created by fangwei.f on 2016/6/27.
 */
public class TaskInfo {
    private long taskId;
    private String description;
    private Date createTime;

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
