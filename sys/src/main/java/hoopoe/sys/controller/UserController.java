package hoopoe.sys.controller;

import com.alibaba.fastjson.JSON;
import com.github.imloama.mybatisplus.bootext.base.APIResult;
import hoopoe.core.HoopoeConsts;
import hoopoe.sys.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class UserController {


    @Autowired
    private UserService userService;


    @RequestMapping(value = "/login", consumes = HoopoeConsts.JSON_CONTENT_TYPE, produces = HoopoeConsts.JSON_CONTENT_TYPE)
    public APIResult login(@RequestParam("username") String username,@RequestParam("password") String password, HttpServletRequest request){
//        log.debug(JSON.toJSONString(request.getParameterMap().keySet()));
        String token = this.userService.login(username, password);
        if(StringUtils.isBlank(token))return APIResult.fail("用户名或密码不正确！");
        return APIResult.ok("success",token);
    }


}
