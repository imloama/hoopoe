package hoopoe.sys.controller;

import hoopoe.core.base.BaseController;
import hoopoe.sys.model.Role;
import hoopoe.sys.service.RoleService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("角色管理")
@Slf4j
@RestController
@RequestMapping("/api/v1/roles")
public class RoleController extends BaseController<Role,RoleService> {

    @Autowired
    private RoleService roleService;


    @Override
    protected Class<Role> getModelClass() {
        return Role.class;
    }
}
