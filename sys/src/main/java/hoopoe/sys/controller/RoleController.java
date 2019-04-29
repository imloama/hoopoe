package hoopoe.sys.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.imloama.mybatisplus.bootext.base.APIResult;
import com.google.common.collect.Maps;
import hoopoe.core.base.BaseController;
import hoopoe.core.base.Query;
import hoopoe.core.tree.Tree;
import hoopoe.core.tree.TreeUtil;
import hoopoe.sys.model.Menu;
import hoopoe.sys.model.Role;
import hoopoe.sys.model.RoleMenu;
import hoopoe.sys.service.MenuService;
import hoopoe.sys.service.RoleMenuService;
import hoopoe.sys.service.RoleService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api("角色管理")
@Slf4j
@RestController
@RequestMapping("/api/v1/roles")
public class RoleController extends BaseController<Role,RoleService> {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleMenuService roleMenuService;

    /**
     * 返回当前角色的详细信息，包括menu信息
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/{id}/info")
    public APIResult info(@PathVariable("id") Long id)throws Exception{
        Role role = this.roleService.getById(id);
        List<Menu> menus = this.menuService.findByRoleId(id);
        Tree<Menu> tree = TreeUtil.build(menus);
        Map<String,Object> data = Maps.newHashMap();
        data.put("role", role);
        data.put("menus", tree);
        return APIResult.ok("success", data);
    }


    /**
     * 更新角色权限
     */
    @PostMapping("/{id}/menus")
    public APIResult updateMenus(@PathVariable("id") Long id, @RequestBody JSONObject params)throws Exception{
        if(!params.containsKey("menus"))return APIResult.fail("参数不正确！");
        JSONArray ids = params.getJSONArray("menus");
        List<Menu> menus = this.menuService.findByRoleId(id);
        List<Long> hasInIds = menus.stream().map(Menu::getId).collect(Collectors.toList());
        List<Long> newIds = ids.stream().map(i -> Long.parseLong(i.toString())).collect(Collectors.toList());
        // ids中不存在，但，hasInIds存在的
        List<Long> willDel = hasInIds.stream().filter(i -> !newIds.contains(i)).collect(Collectors.toList());
        // hasInIds不存在，ids存在的
        List<Long> willInsert = newIds.stream().filter(i -> !hasInIds.contains(i)).collect(Collectors.toList());
        boolean result = true;
        if(willDel.size()>0){
            QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id", id).in("menu_id", willDel);
            result = this.roleMenuService.remove(queryWrapper);
        }
        if(!result)return APIResult.fail("更新失败!");
        if(willInsert.size()>0){
            List<RoleMenu>  list = willInsert.stream().map(i -> {
                RoleMenu rm = new RoleMenu();
                rm.setMenuId(i);
                rm.setRoleId(id);
                return rm;
            }).collect(Collectors.toList());
            result = this.roleMenuService.saveBatch(list);
        }
        if(!result)return APIResult.fail("更新失败!");
        return APIResult.ok("success", true);
    }


    @Override
    protected Class<Role> getModelClass() {
        return Role.class;
    }
}
