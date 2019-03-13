package hoopoe.sys.service;

import com.github.imloama.mybatisplus.bootext.base.BaseServiceImpl;
import hoopoe.sys.mapper.DeptMapper;
import hoopoe.sys.mapper.UserMapper;
import hoopoe.sys.model.Dept;
import hoopoe.sys.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class DeptService extends BaseServiceImpl<DeptMapper, Dept> {

}
