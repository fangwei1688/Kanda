package com.cherry.winter.kanda.controller;

import com.cherry.winter.kanda.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by Fangwei on 16/5/29.
 */
@Controller
public class TestController {

  @Resource(name = "taskService")
  TaskService taskService;

  @RequestMapping(value = "/test", method = RequestMethod.GET)
  @ResponseBody
  Object test() {
    taskService.takePackage();
    return "success";
  }

}
