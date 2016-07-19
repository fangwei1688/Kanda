package com.cherry.winter.kanda.dao.task;

import com.cherry.winter.kanda.model.TaskInfo;
import com.cherry.winter.kanda.util.KeyUtils;
import com.google.common.collect.Lists;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.hash.JacksonHashMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by fangwei.f on 2016/6/27.
 */
@Repository("taskRedisDao")
public class TaskRedisDaoImpl implements TaskDao {
    @Resource(name = "redisTemplate")
    RedisTemplate redisTemplate;

    @Override
    public void createTaskInfo(List<TaskInfo> taskInfoList) {
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.multi();
        for (TaskInfo taskInfo : taskInfoList) {
            Map<String, Object> map = new JacksonHashMapper(TaskInfo.class).toHash(taskInfo);
            redisTemplate.boundHashOps("1").putAll(map);
        }
        redisTemplate.exec();
    }

    @Override
    public TaskInfo getTaskInfo(long taskId) {
        Map<String, Object> map = redisTemplate.boundHashOps(KeyUtils.productInfo(taskId)).entries();
        if (map == null || map.isEmpty()) {
            // throw new ApplicationException(ErrorMessages.PRODUCT_IS_NOT_EXIST);
        }
        return (TaskInfo) new JacksonHashMapper(TaskInfo.class).fromHash(map);
    }

    @Override
    public void takePackage() {

        //String
       long stockCount = redisTemplate.boundValueOps("my_key").increment(-1);
        if(stockCount<0){

        }
        redisTemplate.boundValueOps("key").set("value", 3, TimeUnit.SECONDS);


        if (redisTemplate.boundValueOps("key").setIfAbsent("value")) {

        }

        redisTemplate.boundValueOps("task_id").increment(1);
        redisTemplate.boundValueOps("key").increment(1);

        //List
//         redisTemplate.boundListOps("user:"++":fol").leftPushAll("124");
        //Set

        //Zset
//
//
//        List<String> keyList = Lists.newArrayList();
//        keyList.add("k1");
//        keyList.add("k2");
//        keyList.add("k3");
//        String values[] = new String[3];
//        values[0]="v1";
//        values[1]="v2";
//        values[2]="v3";
//        multiSet( keyList,values);
//
//        for(String key:keyList){
//            System.out.println(redisTemplate.boundValueOps(key).get());
//        }

        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.multi();
        redisTemplate.boundValueOps("key1").set("hi alibaba");
        redisTemplate.boundValueOps("key2").set("bye bye!");
        System.out.println(redisTemplate.boundValueOps("key1").get());
        redisTemplate.exec();
        System.out.println(redisTemplate.boundValueOps("key1").get());

    }

    private void pipelined(Map<String,String> map) {
        List results = redisTemplate.executePipelined(
                new RedisCallback<Object>() {
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        StringRedisConnection stringRedisConn = (StringRedisConnection) connection;
                        for (int i = 0; i < 100; i++) {
                            stringRedisConn.set("", map.get(""));
                        }
                        return null;
                    }
                });
    }

    @Override
    public List<TaskInfo> getTaskInfoList(long packageId) {
        return null;
    }

    private static final String BATCH_DELETE_LUA_SCRIPT = "local lenght=#KEYS\n" +
            "for i=1, lenght do\n" +
            "   redis.call(\"SET\",KEYS[i],ARGV[i])\n" +
            "end";

    private void multiSet(List<String> keyList, String... values) {
        DefaultRedisScript<Integer> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(BATCH_DELETE_LUA_SCRIPT);
//        redisScript.setResultType(Integer.class);

        redisTemplate.execute(redisScript, keyList, values);
    }

    private void multiDel(List<String> keyList) {
        DefaultRedisScript<Integer> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(BATCH_DELETE_LUA_SCRIPT);
        redisScript.setResultType(Integer.class);
        keyList = Lists.newArrayList();
        keyList.add("123");
        redisTemplate.execute(redisScript, keyList);
    }
}
