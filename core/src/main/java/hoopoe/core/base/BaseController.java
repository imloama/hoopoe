package hoopoe.core.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.imloama.mybatisplus.bootext.base.APIResult;
import com.github.imloama.mybatisplus.bootext.base.BaseModel;
import com.github.imloama.mybatisplus.bootext.base.BaseService;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @GetMapping("/{id}")
    public APIResult getById(@PathVariable("id") Long id){
        M model = this.service.getById(id);
        return APIResult.ok("success", model);
    }

    @PostMapping("/create")
    public APIResult update(@RequestBody  M model){
        model = this.beforeCreate(model);
        this.service.save(model);
        return APIResult.ok("success");
    }

    protected M beforeCreate(M model){
        return model;
    }

    @PostMapping("/update/{id}")
    public APIResult update(@PathVariable("id") Long id, @RequestBody  M model){
        if(id!=model.getPrimaryKey())return APIResult.fail("参数错误！");
        M old = this.service.getById(id);
        model = this.beforeUpdate(old, model);
        this.service.updateById(model);
        return APIResult.ok("success");
    }

    protected M beforeUpdate(M oldModel,M newModel){
        return newModel;
    }

    @PostMapping("/del/{id}")
    public APIResult delete(@PathVariable("id") Long id){
        this.service.removeById(id);
        return APIResult.ok("success");
    }

    @PostMapping("/page")
    public APIResult page(@RequestBody PageRequest pageRequest){
        Page<M> page = new Page<>();
        page.setCurrent(pageRequest.getPageNum());
        page.setSize(pageRequest.getPageSize());
        if(pageRequest.isAsc()){
            page.setAsc(pageRequest.getOrderby());
        }else{
            page.setDesc(pageRequest.getOrderby());
        }
        QueryWrapper<M> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(pageRequest.getSearch())){
            //TODO
        }
        this.service.page(page, queryWrapper);
        return APIResult.ok("success");
    }




}
