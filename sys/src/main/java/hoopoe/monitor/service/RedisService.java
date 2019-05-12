package hoopoe.monitor.service;

import com.alibaba.fastjson.JSONObject;
import io.lettuce.core.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 基于lettuce的
 */
@Service
@Slf4j
public class RedisService {


    @Autowired
    RedisClient redisClient;

    public JSONObject info(){
        String info = this.redisClient.connect().sync().info();
        // TODO
        return null;
    }


}
