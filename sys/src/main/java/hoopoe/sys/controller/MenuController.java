package hoopoe.sys.controller;

import com.github.imloama.mybatisplus.bootext.base.APIResult;
import hoopoe.core.base.BaseController;
import hoopoe.sys.model.Dict;
import hoopoe.sys.model.Menu;
import hoopoe.sys.service.DictService;
import hoopoe.sys.service.MenuService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("菜单管理")
@RestController
@Slf4j
@RequestMapping("/api/v1/menus")
public class MenuController extends BaseController<Menu, MenuService> {
    @Override
    protected Class<Menu> getModelClass() {
        return Menu.class;
    }


    //根据角色ID查询Menu
    @GetMapping("/role/{id}")
    public APIResult getByRoleId(@PathVariable("id") Long id){
        List<Menu> menus = this.service.findByRoleId(id);
        return APIResult.ok("success", menus);
    }

    //根据用户ID查询
    @GetMapping("/user/{id}")
    public APIResult getByUserId(@PathVariable("id") Long id){
        List<Menu> menus = this.service.findByUser(id);
        return APIResult.ok("success", menus);
    }


}
