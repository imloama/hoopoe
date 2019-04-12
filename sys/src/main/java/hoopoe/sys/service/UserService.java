package hoopoe.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.imloama.mybatisplus.bootext.base.CustomException;
import com.google.common.collect.Lists;
import hoopoe.core.base.BaseServiceImpl;
import hoopoe.jwt.JWTUtil;
import hoopoe.sys.mapper.UserMapper;
import hoopoe.sys.model.Menu;
import hoopoe.sys.model.Role;
import hoopoe.sys.model.User;
import hoopoe.sys.model.UserRole;
import hoopoe.utils.ValueCheck;
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

import java.io.Serializable;
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

    @Autowired
    private UserRoleService userRoleService;
    /**
     * 创建用户
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean create(User model) throws Exception{
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        ValueCheck.isNull(model.getUsername(),"用户名不能为空！");
        ValueCheck.isNull(model.getPwd(),"密码不能为空！");
        model.setPwd(encoder.encode(model.getPwd()));
        model.setStatus(User.STATUS_VALID);
        model.setCreateTime(new Date());
        model.setLastLoginTime(null);
        model.setModifyTime(null);
        List<Role> roles = model.getRoles();
        boolean result = super.create(model);
        if(!result)throw new CustomException("新增用户失败！");
        if(roles == null || roles.size() == 0 || model.getId() == null)return result;
        List<UserRole> urs = roles.stream().map(r -> {
            UserRole ur = new UserRole();
            ur.setRoleId(r.getId());
            ur.setUserId(model.getId());
            return ur;
        }).collect(Collectors.toList());
        this.userRoleService.saveBatch(urs);
        return result;
    }

    @Override
    public boolean update(User newModel) throws Exception {
        User oldModel = this.getById(newModel.getId());
        newModel.setPwd(oldModel.getPwd());
        newModel.setModifyTime(new Date());
        newModel.setCreateTime(oldModel.getCreateTime());
        //newModel.setStatus(oldModel.getStatus());
        newModel.setLastLoginTime(oldModel.getLastLoginTime());
        newModel.setName(oldModel.getName());
        boolean result = super.update(newModel);
        if(!result)throw new Exception("更新失败！");
        this.afterUpdate(oldModel, newModel);
        return result;
    }

    protected void afterUpdate(User oldModel, User newModel) throws Exception {
        List<Role> oldRoles = oldModel.getRoles();
        List<Role> newRoles = newModel.getRoles();
        List<Long> oldIds = oldRoles == null ? Lists.newArrayList() : oldRoles.stream().map(Role::getId).collect(Collectors.toList());
        List<Long> newIds = newRoles == null ? Lists.newArrayList() : newRoles.stream().map(Role::getId).collect(Collectors.toList());
        List<Long> needDel = Lists.newArrayList();
        List<UserRole> needAdd = Lists.newArrayList();
        for(int i=0,n=oldIds.size();i<n;i++){
            if(!newIds.contains(oldIds.get(i))){
                needDel.add(oldIds.get(i));
            }
        }
        for(int i=0,n=newIds.size();i<n;i++){
            if(!oldIds.contains(newIds.get(i))){
                UserRole ur = new UserRole();
                ur.setUserId(newModel.getId());
                ur.setRoleId(newIds.get(i));
                needAdd.add(ur);
            }
        }
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("role_id", needDel).eq("user_id", newModel.getId());
        this.userRoleService.remove(queryWrapper);
        this.userRoleService.saveBatch(needAdd);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void doAction(String action, Serializable id) throws Exception {
        User user = this.getById(id);
        if(user == null)throw new Exception("未找到用户");
        if(User.LOCK.equals(action)){
            if(user.getStatus()!=User.STATUS_VALID)throw new Exception("用户已经被锁定");
            user.setStatus(User.STATUS_LOCK);
            this.updateById(user);
        }else if(User.UNLOCK.equals(action)){
            if(user.getStatus()!=User.STATUS_LOCK)throw new Exception("用户已经解锁了");
            user.setStatus(User.STATUS_VALID);
            this.updateById(user);
        }
    }

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
        //查询用户的角色，
        List<Role> roles = this.roleService.findByUser(user.getId());
        user.setRoles(roles);
        //查询用户的菜单
        List<Menu> menus = this.menuService.findByUser(user.getId());
        user.setMenus(menus);
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
