package hoopoe.security;
//
//import hoopoe.core.HoopoeConsts;
//import hoopoe.jwt.JWTToken;
//import hoopoe.jwt.JWTUtil;
//import hoopoe.sys.model.User;
//import hoopoe.sys.service.UserService;
//import hoopoe.utils.EncryptUtil;
//import hoopoe.utils.HttpContextUtil;
//import hoopoe.utils.IPUtil;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.SimpleAuthenticationInfo;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Set;
//import java.util.stream.Collectors;
//
///**
// * 自定义实现 ShiroRealm，包含认证和授权两大模块
// *
// * @author MrBird
// */
//public class ShiroRealm extends AuthorizingRealm {
//
//    @Autowired
//    private StringRedisTemplate redisTemplate;
//    @Autowired
//    private UserService userService;
//
//    @Override
//    public boolean supports(AuthenticationToken token) {
//        return token instanceof JWTToken;
//    }
//
//    /**`
//     * 授权模块，获取用户角色和权限
//     *
//     * @param token token
//     * @return AuthorizationInfo 权限信息
//     */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection token) {
//        String userId = JWTUtil.getUserId(token.toString());
//
//        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//
//        // 获取用户角色集
//        Set<String> roleSet = userService.getUserRoles(userId).stream().map(String::valueOf).collect(Collectors.toSet());
//        simpleAuthorizationInfo.setRoles(roleSet);
//
//        // 获取用户权限集
//        Set<String> permissionSet = userService.getUserPermissions(userId).stream().map(String::valueOf).collect(Collectors.toSet());
//        simpleAuthorizationInfo.setStringPermissions(permissionSet);
//        return simpleAuthorizationInfo;
//    }
//
//    /**
//     * 用户认证
//     *
//     * @param authenticationToken 身份认证 token
//     * @return AuthenticationInfo 身份认证信息
//     * @throws AuthenticationException 认证相关异常
//     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        // 这里的 token是从 JWTFilter 的 executeLogin 方法传递过来的，已经经过了解密
//        String token = (String) authenticationToken.getCredentials();
//
//        // 从 redis里获取这个 token
//        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
//        String ip = IPUtil.getIpAddr(request);
//
//        String encryptToken = EncryptUtil.doEncrypt(token);
//        String encryptTokenInRedis = null;
//        try {
//            encryptTokenInRedis = this.redisTemplate.opsForValue().get(HoopoeConsts.TOKEN_PREFIX+ encryptToken + "." + ip);
//        } catch (Exception ignore) {
//        }
//        // 如果找不到，说明已经失效
//        if (StringUtils.isBlank(encryptTokenInRedis))
//            throw new AuthenticationException("token已经过期");
//
//        String userId = JWTUtil.getUserId(token);
//
//        if (StringUtils.isBlank(userId))
//            throw new AuthenticationException("token校验不通过");
//
//        // 通过用户名查询用户信息
//        User user = userService.getUser(userId);
//
//        if (user == null)
//            throw new AuthenticationException("用户名或密码错误");
//        if (!JWTUtil.verify(token, userId, user.getPwd()))
//            throw new AuthenticationException("token校验不通过");
//        return new SimpleAuthenticationInfo(token, token, "hoopoe_shiro_realm");
//    }
//}