package hoopoe.sys.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.imloama.mybatisplus.bootext.base.APIResult;
import com.google.common.collect.Lists;
import hoopoe.annotation.Token;
import hoopoe.core.base.BaseController;
import hoopoe.jwt.JWTToken;
import hoopoe.jwt.JWTUtil;
import hoopoe.sys.model.Role;
import hoopoe.sys.model.User;
import hoopoe.sys.model.UserRole;
import hoopoe.sys.service.UserRoleService;
import hoopoe.sys.service.UserService;
import hoopoe.utils.ValueCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Api("用户管理")
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController extends BaseController<User,UserService> {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserRoleService userRoleService;


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
    @PostMapping("/adminresetpwd")
    public APIResult resetPwdByAdmin(@Token String token, @RequestBody List<Long> ids) throws Exception {
        if(ids == null || ids.size() == 0)return APIResult.fail("请求参数不正确！");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        List<User> users = this.service.list(queryWrapper);
        if(users == null || users.size() == 0)return APIResult.fail("请求参数不正确！");
        String pwd = RandomUtil.randomString(6);
        for(User user : users){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String pwdEncode = encoder.encode(pwd);
            user.setPwd(pwdEncode);
        }
        this.service.updateBatchById(users, users.size());
        return APIResult.ok("success", pwd);
    }
//
    private User getByParam(JSONObject params)throws Exception{
        String username = params.getString("username");
        if(StringUtils.isBlank(username))throw  new Exception("请提供用户名称！");
        User user = this.service.getByName(username);
        if(user == null)throw  new Exception("用户不存在！");
        return user;
    }

    //禁用用户
    @GetMapping("/lockuser")
    public APIResult lockUser(@Token String token, @RequestBody JSONObject params) throws Exception {
        User user = getByParam(params);
        user.setStatus(User.STATUS_LOCK);
        this.service.updateById(user);
        return APIResult.ok("success");
    }

    @GetMapping("/check/{username}")
    public APIResult checkUsername(@PathVariable("username")  String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", username);
    	int count = this.service.count(queryWrapper);
    	return APIResult.ok("success", count > 0);
    }

    //TODO 上传头像
    @PostMapping("/avatar")
    public void updateAvatar(@Token String token,@RequestParam("avatar") MultipartFile avatar) throws Exception {

    }

    @Override
    protected Class<User> getModelClass() {
        return User.class;
    }
}
