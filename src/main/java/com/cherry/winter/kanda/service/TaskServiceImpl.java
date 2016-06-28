package com.cherry.winter.kanda.service;

import com.cherry.winter.kanda.dao.task.TaskDao;
import com.cherry.winter.kanda.model.TaskInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by fangwei.f on 2016/6/28.
 */
@Service("taskService")
public class TaskServiceImpl implements TaskService{

    @Resource(name = "taskRedisDao")
    TaskDao taskDao;
    @Override
    public void createTaskInfo(TaskInfo taskInfo) {

    }

    @Override
    public TaskInfo getTaskInfo(long taskId) {
        return null;
    }

    @Override
    public void takePackage() {
        taskDao.takePackage();
    }

    @Override
    public List<TaskInfo> getTaskInfoList(long packageId) {
        return null;
    }
}
