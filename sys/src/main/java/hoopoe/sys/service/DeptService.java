package hoopoe.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import hoopoe.core.base.BaseServiceImpl;
import hoopoe.sys.mapper.DeptMapper;
import hoopoe.sys.model.Dept;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class DeptService extends BaseServiceImpl<DeptMapper, Dept> {

    @Transactional
    public boolean create(Dept dept) {
        Long parentId = dept.getParentId();
        if (parentId == null)
            dept.setParentId(0L);
        dept.setCreateTime(new Date());
        return this.save(dept);
    }
    @Transactional
    public boolean update(Dept dept) {
        dept.setModifyTime(new Date());
        return this.baseMapper.updateById(dept) > 0;
    }

    @Transactional
    public boolean del(List<Long> ids) {
        return this.baseMapper.deleteAll(ids) > 0;
    }

    public List<Dept> listById(Set<Long> ids){
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        return this.list(queryWrapper);
    }

}
