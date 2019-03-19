package hoopoe.sys.controller;

import hoopoe.core.base.BaseController;
import hoopoe.sys.model.Dict;
import hoopoe.sys.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/depts")
public class DictController extends BaseController<Dict, DictService> {
    @Override
    protected Class<Dict> getModelClass() {
        return Dict.class;
    }
}
