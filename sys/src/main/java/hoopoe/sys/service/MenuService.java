package hoopoe.sys.service;

import hoopoe.core.base.BaseServiceImpl;
import hoopoe.sys.mapper.MenuMapper;
import hoopoe.sys.model.Menu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class MenuService extends BaseServiceImpl<MenuMapper, Menu> {


    @Transactional(readOnly = true)
    public List<Menu> findByUser(Long userId){
        return this.baseMapper.findByUser(userId);
    }

    @Transactional(readOnly = true)
    public List<String> findUserIdsByMenuId(Long menuId){
        return this.baseMapper.findUserIdsByMenuId(menuId);
    }

    @Transactional(readOnly = true)
    public List<Menu> findByRoleId(Long roleId){
        return this.baseMapper.findByRoleId(roleId);
    }

}
