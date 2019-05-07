package hoopoe.sys.service;

import hoopoe.core.base.BaseServiceImpl;
import hoopoe.sys.mapper.DeptMapper;
import hoopoe.sys.mapper.DictMapper;
import hoopoe.sys.model.Dept;
import hoopoe.sys.model.Dict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class DictService extends BaseServiceImpl<DictMapper, Dict> {


    @Autowired
    private DictRefService dictRefService;

    @Transactional(readOnly = false)
    public List<Dict> selectByTableName(String tableName){
        return this.baseMapper.selectByTableName(tableName);
    }

}
