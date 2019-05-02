package hoopoe.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.imloama.mybatisplus.bootext.base.APIResult;
import com.github.imloama.mybatisplus.bootext.base.CustomException;
import com.google.common.collect.Lists;
import hoopoe.core.base.BaseServiceImpl;
import hoopoe.sys.mapper.RoleMapper;
import hoopoe.sys.model.Menu;
import hoopoe.sys.model.Role;
import hoopoe.sys.model.RoleMenu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoleService extends BaseServiceImpl<RoleMapper, Role> {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Transactional(readOnly = true)
    public List<Role> findByUser(Long userId){
        return this.baseMapper.selectByUserId(userId);
    }

    @Override
    public boolean create(Role entity) throws Exception {
        // 校验编码，不能重复
        int count = this.baseMapper.countByCode(entity.getCode());
        if(count > 0)throw new CustomException("编码["+entity.getCode()+"]不能重复！");
        List<Long> menuIds = entity.getMenuIds();
        entity.setCreateTime(new Date());
        boolean result  =  super.create(entity);
        if(!result)return result;
        if(menuIds!=null && menuIds.size() > 0){
            List<RoleMenu> menus = menuIds.stream().map( id -> {
                RoleMenu rm = new RoleMenu();
                rm.setRoleId(entity.getId());
                rm.setMenuId(id);
                return rm;
            }).collect(Collectors.toList());
            return this.roleMenuService.saveBatch(menus);
        }
        return result;
    }

    @Override
    public boolean update(Role role) throws Exception {
        Role origin = this.getById(role.getId());
//        origin.setCode(role.getCode());//编码不能修改
        origin.setName(role.getName());
        origin.setModifyTime(new Date());
        origin.setRemark(role.getRemark());
        List<Long> hasInIds = this.roleMenuService.getMenuIdByRoleId(role.getId());
        List<Long> menus = role.getMenuIds();
        final List<Long> newIds = menus == null ?  Lists.newArrayList() : menus;
        // ids中不存在，但，hasInIds存在的
        List<Long> willDel = hasInIds.stream().filter(i -> !newIds.contains(i)).collect(Collectors.toList());
        // hasInIds不存在，ids存在的
        List<Long> willInsert = newIds.stream().filter(i -> !hasInIds.contains(i)).collect(Collectors.toList());
        boolean result = true;
        if(willDel.size()>0){
            QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id", role.getId()).in("menu_id", willDel);
            result = this.roleMenuService.remove(queryWrapper);
        }
        if(!result)return false;
        if(willInsert.size()>0){
            List<RoleMenu>  list = willInsert.stream().map(i -> {
                RoleMenu rm = new RoleMenu();
                rm.setMenuId(i);
                rm.setRoleId(role.getId());
                return rm;
            }).collect(Collectors.toList());
            result = this.roleMenuService.saveBatch(list);
        }
        if(!result)return result;
        result = this.updateById(origin);
        return result;
    }
}