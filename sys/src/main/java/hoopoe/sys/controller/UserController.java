package hoopoe.sys.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.imloama.mybatisplus.bootext.base.APIResult;
import hoopoe.annotation.Token;
import hoopoe.core.HoopoeConsts;
import hoopoe.jwt.JWTToken;
import hoopoe.jwt.JWTUtil;
import hoopoe.sys.model.User;
import hoopoe.sys.service.UserService;
import hoopoe.sys.vm.LoginRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Api("用户管理")
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class UserController {


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserService userService;


    @ApiOperation("登录接口")
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = HoopoeConsts.JSON_CONTENT_TYPE, produces = HoopoeConsts.JSON_CONTENT_TYPE)
    public APIResult login(@RequestBody @Valid LoginRequest param, HttpServletRequest request){
//        log.debug(JSON.toJSONString(request.getParameterMap().keySet()));
        String token = this.userService.login(param.getUsername(), param.getPassword());
        if(StringUtils.isBlank(token))return APIResult.fail("用户名或密码不正确！");
        //token保存到redis
        this.redisTemplate.opsForValue().set(HoopoeConsts.TOKEN_PREFIX+param.getUsername()+"_"+token.substring(0,8), token,
                JWTUtil.generateExpirationDate().getTime(), TimeUnit.MILLISECONDS);
        return APIResult.ok("success",token);
    }

    @ApiOperation("退出登陆接口")
    @GetMapping("/logout")
    public APIResult logout(@Token String token){
        JWTToken jwtToken = JWTUtil.getFromToken(token);
        String key = HoopoeConsts.TOKEN_PREFIX+ jwtToken.getUsername()+"_"+token.substring(0,8);
        this.redisTemplate.delete(key);
        return APIResult.ok("success");
    }

    //修改密码，提供原密码和新密码，再重复密码
    @ApiOperation("修改密码")
    @PostMapping("/resetpwd")
    public APIResult resetPassword(@Token String token, @RequestBody JSONObject params){
        if(params.isEmpty())return APIResult.fail("缺少参数！");
        String pwd = params.getString("password");
        String newpwd = params.getString("newpassword");
        String newpwd2 = params.getString("newpassword2");
        if(StringUtils.isNotBlank(pwd)||StringUtils.isNotBlank(newpwd)|| StringUtils.isNotBlank(newpwd2)
            || !newpwd.equals(newpwd2)){
            return APIResult.fail("请求参数不正确！");
        }
        JWTToken jwtToken = JWTUtil.getFromToken(token);
        User user = this.userService.getById(jwtToken.getId());
        if(user == null)return APIResult.fail("用户不存在！");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(pwd, user.getPassword())){
            return APIResult.fail("原密码不正确！");
        }
        String newpwdEncode = encoder.encode(newpwd);
        user.setPwd(newpwdEncode);
        this.userService.updateById(user);
        return APIResult.ok("success");
    }


    //忘记密码, 需要通过邮件重置，暂时不支持


    // 重置密码，由管理员操作
    @ApiOperation("重置密码接口，只有管理员可以操作")
    @Secured(value = "admin")
    @GetMapping("/admin/resetpwd")
    public APIResult resetPwdByAdmin(@Token String token, @RequestBody JSONObject params){
        String username = params.getString("username");
        if(StringUtils.isBlank(username))return APIResult.fail("请提供用户名称！");
        User user = this.userService.getByName(username);
        if(user == null)return APIResult.fail("用户不存在！");
        String pwd = RandomUtil.randomString(6);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pwdEncode = encoder.encode(pwd);
        user.setPwd(pwdEncode);
        this.userService.updateById(user);
        return APIResult.ok("success", pwd);
    }

    // 新建用户
    @PostMapping("/users/add")
    public APIResult createUser(@Token String token, @RequestBody User user){
        user = this.userService.register(user);
        if(user == null)return APIResult.fail("创建用户失败！");
        return APIResult.ok("success");
    }

    //修改用户
    @PostMapping("/usrs/update/{id}")
    public APIResult updateUser(@Token String token,@PathVariable("id") Long id, @RequestBody User user){
        User origin = this.userService.getById(id);
        if(origin.getId()!=user.getId() ){
            return APIResult.fail("请求参数不正确！");
        }
        user.setPwd(origin.getPwd());
        user.setStatus(origin.getStatus());
        this.userService.updateById(user);
        return APIResult.ok("success");
    }



    //删除用户,批量删除
    @GetMapping("/users/del")
    public APIResult deleteUser(@Token String token, @RequestBody JSONObject params){
        String users = params.getString("users");
        if(StringUtils.isBlank(users))return APIResult.fail("参数[users]不存在!");
        String[] ids = users.split(",");
        this.userService.deleteUsers(Arrays.asList(ids).stream().map(Long::valueOf).collect(Collectors.toList()));
        return APIResult.ok("success");
    }

    //禁用用户
    @GetMapping("/admin/lockuser")
    public APIResult lockUser(@Token String token, @RequestBody JSONObject params){
        String username = params.getString("username");
        if(StringUtils.isBlank(username))return APIResult.fail("请提供用户名称！");
        User user = this.userService.getByName(username);
        if(user == null)return APIResult.fail("用户不存在！");
        user.setStatus(User.STATUS_LOCK);
        this.userService.updateById(user);
        return APIResult.ok("success");
    }



}
