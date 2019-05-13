package hoopoe.monitor.controller;

import com.github.imloama.mybatisplus.bootext.base.APIResult;
import hoopoe.monitor.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @GetMapping("/info")
    public APIResult info(){
        return APIResult.ok("success", this.redisService.info());
    }

}
