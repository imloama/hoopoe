package hoopoe.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.imloama.mybatisplus.bootext.base.APIResult;
import hoopoe.core.base.BaseController;
import hoopoe.core.base.PageRequest;
import hoopoe.core.base.Query;
import hoopoe.core.tree.Tree;
import hoopoe.core.tree.TreeUtil;
import hoopoe.sys.model.Menu;
import hoopoe.sys.service.MenuService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/tree")
    public APIResult getMenuTree(@RequestBody PageRequest pageRequest)throws Exception{
        QueryWrapper<Menu> queryWrapper = queryWrapperFromRequest(pageRequest);
        List<Menu> list = this.service.list(queryWrapper);
        Tree<Menu> tree = TreeUtil.build(list);
        return APIResult.ok("success", tree);
    }


    private QueryWrapper<Menu> queryWrapperFromRequest(PageRequest pageRequest) throws Exception {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        List<Query> queries = pageRequest.getQuery();
        if(queries!=null&&!queries.isEmpty()){
            for(int i=0,n=queries.size();i<n;i++){
                queryWrapper = queries.get(i).fill(queryWrapper);
            }
        }
        return queryWrapper;
    }



}
