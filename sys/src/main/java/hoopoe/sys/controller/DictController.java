package hoopoe.sys.controller;

import hoopoe.core.base.BaseController;
import hoopoe.sys.model.Dict;
import hoopoe.sys.service.DictService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("字典管理")
@RestController
@Slf4j
@RequestMapping("/api/v1/dicts")
public class DictController extends BaseController<Dict, DictService> {
    @Override
    protected Class<Dict> getModelClass() {
        return Dict.class;
    }
}
