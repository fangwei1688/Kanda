package com.cherry.winter.kanda.controller;

import com.google.common.collect.Lists;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Fangwei on 16/5/29.
 */
@Controller
public class SenderController {

  @Resource(name = "redisTemplate")
  RedisTemplate redisTemplate;

  private DefaultRedisScript<Integer> redisScript;

  //  "-- project id must has 2 digits, 01 - 15\n" +
  //  "-- instance id must has 2 digits, 01 - 15\n" +

  //  private static final String script = "local pid = KEYS[1]\n" +
  //      "local iid = KEYS[2]\n" +
  //      "local idnum = redis.call(\"INCR\", \"ID_IDX\") % 65536\n" +
  //      "local sec = redis.call(\"TIME\")[1] - 1420041600\n" +
  //      "return sec * 16777216 + pid * 1048576 + iid * 65536 + idnum";
  //private static final String script = "local key1 = KEYS[1]\n"
  //    + "local key2 = KEYS[2]"
  //    + "local pid = tonumber(key1)\n"
  //    + "local iid = tonumber(key2)\n"
  //    + "return pid+iid";
  private static final String script = "redis.call(\")";
  private List<String> keyList;

  @PostConstruct
  public void init() {
    redisScript = new DefaultRedisScript<>();
    redisScript.setScriptText(script);
    redisScript.setResultType(Integer.class);
    keyList = Lists.newArrayList();
    keyList.add("123");
  }

  @RequestMapping(value = "/id", method = RequestMethod.GET)
  @ResponseBody
  Object singleID() {
    //    RedisScript<T> script, RedisSerializer<?> argsSerializer, RedisSerializer<T> resultSerializer,
    //        List<K> keys, Object... args
    return redisTemplate.execute(redisScript, keyList);
  }

}
