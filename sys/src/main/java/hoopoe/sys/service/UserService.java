package hoopoe.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.imloama.mybatisplus.bootext.base.BaseServiceImpl;
import hoopoe.core.HoopoeConsts;
import hoopoe.jwt.JWTUtil;
import hoopoe.sys.mapper.UserMapper;
import hoopoe.sys.model.Menu;
import hoopoe.sys.model.Role;
import hoopoe.sys.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService extends BaseServiceImpl<UserMapper, User> implements UserDetailsService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Transactional(readOnly = true)
    public User getByName(String name){
        User user = this.baseMapper.selectByName(name);
        if(user!=null){
            this.getCache().put(user.getId(), user);
        }
        return user;
    }


    @Transactional(readOnly = true)
    public Set<String> getUserRoles(String userId) {
        return this.roleService.findByUser(Long.parseLong(userId)).stream().map(Role::getName).collect(Collectors.toSet());

    }
    @Transactional(readOnly = true)
    public Set<String> getUserPermissions(String userId) {
        assert StringUtils.isNotBlank(userId);
        return this.menuService.findByUser(Long.parseLong(userId)).stream().map(Menu::getCode).collect(Collectors.toSet());
    }
    @Transactional(readOnly = true)
    public User getUser(String userId) {
        assert StringUtils.isNotBlank(userId);
        return this.getById(Long.parseLong(userId));
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.getByName(username);
    }

    @Transactional(readOnly = true)
    public User login( String username, String password ) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken( username, password );
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = getByName(username);
        final String token = JWTUtil.generateToken(user);
        user.setToken(token);
        user.setPwd(null);
        user.setIdCard(null);
        return user;
    }

    // 注册
    @Transactional
    public User register( User userToAdd ) {
        final String username = userToAdd.getUsername();
        if( this.getByName(username)!=null ) {
            return null;
        }
        userToAdd.setCreateTime(new Date());
        userToAdd.setStatus(User.STATUS_VALID);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = userToAdd.getPassword();
        userToAdd.setPwd( encoder.encode(rawPassword) );
        this.save(userToAdd);
        return userToAdd;
    }


    public boolean deleteUsers(List<Long> ids){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        return this.remove(queryWrapper);
    }

}
