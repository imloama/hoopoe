package hoopoe.sys.controller;

import hoopoe.core.base.BaseController;
import hoopoe.sys.model.Dict;
import hoopoe.sys.model.Menu;
import hoopoe.sys.service.DictService;
import hoopoe.sys.service.MenuService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("菜单管理")
@RestController
@Slf4j
@RequestMapping("/api/v1/menus")
public class MenuController extends BaseController<Menu, MenuService> {
    @Override
    protected Class<Menu> getModelClass() {
        return Menu.class;
    }
}
