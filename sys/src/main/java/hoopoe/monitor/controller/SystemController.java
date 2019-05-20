package hoopoe.monitor.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.imloama.mybatisplus.bootext.base.APIResult;
import com.google.common.collect.Lists;
import hoopoe.core.HoopoeConsts;
import hoopoe.jwt.JWTToken;
import hoopoe.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/system")
public class SystemController {


    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * 返回当前登陆用户
     * @return
     */
    @GetMapping("/loginusers")
    public APIResult loginusers(){
        Set<String> keys = this.redisTemplate.keys(HoopoeConsts.TOKEN_PREFIX+"*");
        List<String> list = this.redisTemplate.opsForValue().multiGet(keys);
        List<JWTToken> tokens = null;
        if(list.size() > 0){
            tokens = list.stream().map(token -> JWTUtil.getFromToken(token)).collect(Collectors.toList());
        }else{
            tokens = Lists.newArrayList();
        }
        return APIResult.ok("success", tokens);
    }

    /**
     * 强制用户退出
     * @param params
     * @return
     * @throws Exception
     */
    @PostMapping("/dropuser")
    public APIResult dropUser(@RequestBody JSONObject params)throws Exception{
        JSONArray usernames = params.getJSONArray("usernames");
        if(usernames == null || usernames.isEmpty())return APIResult.fail("参数不正确！");
        Map<String,String> keys = usernames.stream().map(name -> HoopoeConsts.TOKEN_PREFIX + name)
                .collect(Collectors.toMap(key -> key, key -> null));
        this.redisTemplate.opsForValue().multiSet(keys);
        return APIResult.ok("success");
    }

}
