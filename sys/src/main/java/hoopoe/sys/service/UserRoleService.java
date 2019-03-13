package hoopoe.sys.service;

import com.github.imloama.mybatisplus.bootext.base.BaseServiceImpl;
import hoopoe.sys.mapper.RoleMapper;
import hoopoe.sys.mapper.UserRoleMapper;
import hoopoe.sys.model.Role;
import hoopoe.sys.model.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserRoleService extends BaseServiceImpl<UserRoleMapper, UserRole> {

}
