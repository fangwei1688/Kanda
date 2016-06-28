package com.cherry.winter.kanda.dao.task;

import com.cherry.winter.kanda.model.TaskInfo;

import java.util.List;

/**
 * Created by fangwei.f on 2016/6/27.
 */
public interface TaskDao {
    void createTaskInfo(TaskInfo taskInfo);

    TaskInfo getTaskInfo(long taskId);

    void takePackage();

    List<TaskInfo> getTaskInfoList(long packageId);
}
