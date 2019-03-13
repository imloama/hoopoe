package hoopoe.sys.service;

import com.github.imloama.mybatisplus.bootext.base.BaseServiceImpl;
import hoopoe.sys.mapper.DeptMapper;
import hoopoe.sys.mapper.DictMapper;
import hoopoe.sys.model.Dept;
import hoopoe.sys.model.Dict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DictService extends BaseServiceImpl<DictMapper, Dict> {

}
