package hoopoe.core.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.imloama.mybatisplus.bootext.base.APIResult;
import com.github.imloama.mybatisplus.bootext.base.BaseService;
import com.wuwenze.poi.ExcelKit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@Data
public abstract class BaseController<M extends BaseModel<M,Long>,S extends BaseService<M>> {


    @Autowired
    protected S service;

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    protected StringRedisTemplate redisTemplate;

    protected abstract Class<M> getModelClass();

    @GetMapping("/{id}")
    public APIResult getById(@PathVariable("id") Long id)throws Exception{
        M model = this.service.getById(id);
        return APIResult.ok("success", model);
    }

    @PostMapping("/create")
    public APIResult update(@RequestBody  M model)throws Exception{
        model = this.beforeCreate(model);
        this.service.save(model);
        this.afterCreate(model);
        return APIResult.ok("success");
    }

    protected M beforeCreate(M model) throws Exception{
        return model;
    }

    protected void afterCreate(M model)throws Exception {}

    @PostMapping("/update/{id}")
    public APIResult update(@PathVariable("id") Long id, @RequestBody  M model)throws Exception{
        if(id!=model.getPrimaryKey())return APIResult.fail("参数错误！");
        M old = this.service.getById(id);
        model = this.beforeUpdate(old, model);
        this.service.updateById(model);
        return APIResult.ok("success");
    }

    protected M beforeUpdate(M oldModel,M newModel)throws Exception{
        return newModel;
    }

    protected void afterUpdate(M oldModel,M newModel)throws Exception{}

    @GetMapping("/del/{id}")
    public APIResult delete(@PathVariable("id") Long id)throws Exception{
        this.service.removeById(id);
        return APIResult.ok("success");
    }

    // 批量删除
    @PostMapping("/delall")
    public APIResult deleteAll(@RequestBody List<Long> ids)throws Exception{
        if(ids == null || ids.isEmpty())return APIResult.fail("参数不正确！");
        QueryWrapper<M> queryWrapper = new QueryWrapper<>();
        M m = this.getModelClass().newInstance();
        queryWrapper.in(m.getPrimaryKey(), ids);
        this.service.remove(queryWrapper);
        return APIResult.ok("success");
    }

    /**
     * 分页查询
     * @param pageRequest
     * @return
     * @throws Exception
     */
    @PostMapping("/page")
    public APIResult page(@RequestBody PageRequest pageRequest)throws Exception{
        Page<M> page = new Page<>();
        page.setCurrent(pageRequest.getPageNum());
        page.setSize(pageRequest.getPageSize());
        if(pageRequest.isAsc()){
            page.setAsc(pageRequest.getOrderby());
        }else{
            page.setDesc(pageRequest.getOrderby());
        }
        QueryWrapper<M> queryWrapper = queryWrapperFromRequest(pageRequest);
        IPage<M> pageResult = this.service.page(page, queryWrapper);
        return APIResult.ok("success", pageResult);
    }

    private QueryWrapper<M> queryWrapperFromRequest(PageRequest pageRequest) throws Exception {
        QueryWrapper<M> queryWrapper = new QueryWrapper<>();
        // 模糊查询条件
        if(StringUtils.isNotBlank(pageRequest.getSearch())){
            M m = this.getModelClass().newInstance();
            List<String> cols = m.searchColumns();
            if(cols!=null&&!cols.isEmpty()){
                for(int i=0,n=cols.size();i<n;i++){
                    if(i>0){
                        queryWrapper.or();
                    }
                    queryWrapper.like(cols.get(i), pageRequest.getSearch());
                }
            }
        }
        //精确查询条件
        List<Query> queries = pageRequest.getQuery();
        if(queries!=null&&!queries.isEmpty()){
            for(int i=0,n=queries.size();i<n;i++){
                queryWrapper = queries.get(i).fill(queryWrapper);
            }
        }
        return queryWrapper;
    }

    /**
     * 下载excel
     * @param pageRequest
     * @throws Exception
     */
    @GetMapping("/excel")
    public void toExcel(@RequestBody PageRequest pageRequest)throws Exception{
        QueryWrapper<M> queryWrapper = queryWrapperFromRequest(pageRequest);
        List<M> list = this.service.list(queryWrapper);
        ExcelKit.$Export(this.getModelClass(), this.response).downXlsx(list, false);
    }




}
