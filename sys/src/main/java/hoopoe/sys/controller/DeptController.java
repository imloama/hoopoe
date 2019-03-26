package hoopoe.sys.controller;

import com.github.imloama.mybatisplus.bootext.base.APIResult;
import hoopoe.core.base.BaseController;
import hoopoe.core.tree.TreeUtil;
import hoopoe.sys.model.Dept;
import hoopoe.sys.service.DeptService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("部门管理")
@RestController
@Slf4j
@RequestMapping("/api/v1/depts")
public class DeptController extends BaseController<Dept, DeptService> {

    @GetMapping("/tree")
    public APIResult all(){
        return APIResult.ok("success", TreeUtil.build(this.service.list()));
    }


    @Override
    protected Class<Dept> getModelClass() {
        return Dept.class;
    }
}
