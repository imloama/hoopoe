package hoopoe.sys.service;

import com.github.imloama.mybatisplus.bootext.base.BaseServiceImpl;
import hoopoe.sys.mapper.UserMapper;
import hoopoe.sys.model.Menu;
import hoopoe.sys.model.Role;
import hoopoe.sys.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService extends BaseServiceImpl<UserMapper, User> {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;


    @Transactional(readOnly = true)
    public User getByName(String name){
        return this.baseMapper.selectByName(name);
    }


    @Transactional(readOnly = true)
    public Set<Long> getUserRoles(String userId) {
        return this.roleService.findByUser(Long.parseLong(userId)).stream().map(Role::getId).collect(Collectors.toSet());

    }
    @Transactional(readOnly = true)
    public Set<Long> getUserPermissions(String userId) {
        assert StringUtils.isNotBlank(userId);
        return this.menuService.findByUser(Long.parseLong(userId)).stream().map(Menu::getId).collect(Collectors.toSet());
    }
    @Transactional(readOnly = true)
    public User getUser(String userId) {
        assert StringUtils.isNotBlank(userId);
        return this.getById(Long.parseLong(userId));
    }
}
