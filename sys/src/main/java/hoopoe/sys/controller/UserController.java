package hoopoe.sys.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.imloama.mybatisplus.bootext.base.APIResult;
import hoopoe.annotation.Token;
import hoopoe.core.base.BaseController;
import hoopoe.jwt.JWTToken;
import hoopoe.jwt.JWTUtil;
import hoopoe.sys.model.User;
import hoopoe.sys.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@Api("用户管理")
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController extends BaseController<User,UserService> {


    @Autowired
    private StringRedisTemplate redisTemplate;


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
        User user = this.service.getById(jwtToken.getId());
        if(user == null)return APIResult.fail("用户不存在！");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(pwd, user.getPassword())){
            return APIResult.fail("原密码不正确！");
        }
        String newpwdEncode = encoder.encode(newpwd);
        user.setPwd(newpwdEncode);
        this.service.updateById(user);
        return APIResult.ok("success");
    }


    //忘记密码, 需要通过邮件重置，暂时不支持


    // 重置密码，由管理员操作
    @ApiOperation("重置密码接口，只有管理员可以操作")
    @Secured(value = "admin")
    @GetMapping("/adminresetpwd")
    public APIResult resetPwdByAdmin(@Token String token, @RequestBody JSONObject params){
        String username = params.getString("username");
        if(StringUtils.isBlank(username))return APIResult.fail("请提供用户名称！");
        User user = this.service.getByName(username);
        if(user == null)return APIResult.fail("用户不存在！");
        String pwd = RandomUtil.randomString(6);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pwdEncode = encoder.encode(pwd);
        user.setPwd(pwdEncode);
        this.service.updateById(user);
        return APIResult.ok("success", pwd);
    }
//

    //禁用用户
    @GetMapping("/lockuser")
    public APIResult lockUser(@Token String token, @RequestBody JSONObject params){
        String username = params.getString("username");
        if(StringUtils.isBlank(username))return APIResult.fail("请提供用户名称！");
        User user = this.service.getByName(username);
        if(user == null)return APIResult.fail("用户不存在！");
        user.setStatus(User.STATUS_LOCK);
        this.service.updateById(user);
        return APIResult.ok("success");
    }


    @Override
    protected Class<User> getModelClass() {
        return User.class;
    }
}
