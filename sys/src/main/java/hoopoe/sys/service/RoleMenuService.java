package hoopoe.sys.service;

import hoopoe.core.base.BaseServiceImpl;
import hoopoe.sys.mapper.RoleMenuMapper;
import hoopoe.sys.model.RoleMenu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class RoleMenuService extends BaseServiceImpl<RoleMenuMapper, RoleMenu> {

    @Transactional(readOnly = true)
    public List<Long> getMenuIdByRoleId(Long roleId){
        return this.baseMapper.getMenuIdByRoleId(roleId);
    }
}
