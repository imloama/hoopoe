package hoopoe.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.imloama.mybatisplus.bootext.base.APIResult;
import hoopoe.core.base.BaseController;
import hoopoe.core.base.PageRequest;
import hoopoe.core.base.Query;
import hoopoe.core.tree.TreeUtil;
import hoopoe.sys.model.Dept;
import hoopoe.sys.service.DeptService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("部门管理")
@RestController
@Slf4j
@RequestMapping("/api/v1/depts")
public class DeptController extends BaseController<Dept, DeptService> {

    @PostMapping("/tree")
    public APIResult all(@RequestBody PageRequest pageRequest)throws Exception{
        QueryWrapper<Dept> queryWrapper = queryWrapperFromRequest(pageRequest);
        List<Dept> list = this.service.list(queryWrapper);
        return APIResult.ok("success", TreeUtil.build(list));
    }


    private QueryWrapper<Dept> queryWrapperFromRequest(PageRequest pageRequest) throws Exception {
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        List<Query> queries = pageRequest.getQuery();
        if(queries!=null&&!queries.isEmpty()){
            for(int i=0,n=queries.size();i<n;i++){
                queryWrapper = queries.get(i).fill(queryWrapper);
            }
        }
        return queryWrapper;
    }




    @Override
    protected Class<Dept> getModelClass() {
        return Dept.class;
    }
}
