package hoopoe.sys.controller;

import com.github.imloama.mybatisplus.bootext.base.APIResult;
import hoopoe.annotation.Token;
import hoopoe.core.HoopoeConsts;
import hoopoe.jwt.JWTToken;
import hoopoe.jwt.JWTUtil;
import hoopoe.sys.model.Menu;
import hoopoe.sys.model.Role;
import hoopoe.sys.model.User;
import hoopoe.sys.service.MenuService;
import hoopoe.sys.service.RoleService;
import hoopoe.sys.service.UserService;
import hoopoe.sys.vm.LoginRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Api("用户登陆")
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class LoginController {


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;


    @ApiOperation("登录接口")
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = HoopoeConsts.JSON_CONTENT_TYPE, produces = HoopoeConsts.JSON_CONTENT_TYPE)
    public APIResult login(@RequestBody @Valid LoginRequest param, HttpServletRequest request){
//        log.debug(JSON.toJSONString(request.getParameterMap().keySet()));
        User user = this.userService.login(param.getUsername(), param.getPassword());
        String token = user.getToken();
        if(StringUtils.isBlank(token))return APIResult.fail("用户名或密码不正确！");
        //token保存到redis
        JWTToken jwtToken = JWTUtil.getFromToken(token);
        String key = jwtToken.toRedisKey(token);
        this.redisTemplate.opsForValue().set(key, token,
                JWTUtil.generateExpirationDate().getTime(), TimeUnit.MILLISECONDS);
        return APIResult.ok("success",user);
    }


    @ApiOperation("获取用户信息")
    @GetMapping("/userinfo")
    public APIResult userInfo(@Token String token){
        JWTToken jwtToken = JWTUtil.getFromToken(token);
        String key = jwtToken.toRedisKey(token);
        String tokenCopy = this.redisTemplate.opsForValue().get(key);
        if(StringUtils.isBlank(tokenCopy) || !token.equals(tokenCopy)){
            return APIResult.fail("请求参数不正确！");
        }
        User user = this.userService.getById(jwtToken.getId());
        user.setPwd(null);
        user.setIdCard(null);
        //查询用户roles
        List<Role> roles = this.roleService.findByUser(user.getId());
        //查询用户权限
        List<Menu> menus = this.menuService.findByUser(user.getId());
        user.setRoles(roles);
        user.setMenus(menus);
        return APIResult.ok("success", user);
    }


    @ApiOperation("退出登陆接口")
    @GetMapping("/logout")
    public APIResult logout(@Token String token){
        JWTToken jwtToken = JWTUtil.getFromToken(token);
        String key = jwtToken.toRedisKey(token);
        this.redisTemplate.delete(key);
        return APIResult.ok("success");
    }
}
