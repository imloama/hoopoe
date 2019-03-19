package hoopoe.sys.controller;

import hoopoe.core.base.BaseController;
import hoopoe.sys.model.Dict;
import hoopoe.sys.model.Menu;
import hoopoe.sys.service.DictService;
import hoopoe.sys.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/menus")
public class MenuController extends BaseController<Menu, MenuService> {
    @Override
    protected Class<Menu> getModelClass() {
        return Menu.class;
    }
}
