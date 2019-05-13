package hoopoe.monitor.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.lettuce.core.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Properties;

/**
 * 基于lettuce的
 */
@Service
@Slf4j
public class RedisService {


    @Autowired
    private LettuceConnectionFactory lettuceConnectionFactory;


    public Properties info(){
        RedisConnection conn = this.lettuceConnectionFactory.getConnection();
        Properties info = conn.info();
        //String info = this.redisClient.connect().sync().info();
        //log.debug(info.toString());
        long keys = conn.dbSize();
        JSONObject result = new JSONObject();
        result.putAll((Map)info);
        result.put("keys_size", keys);
        return info;
    }


}
