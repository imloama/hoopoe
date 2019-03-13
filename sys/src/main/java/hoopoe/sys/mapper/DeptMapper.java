package hoopoe.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hoopoe.sys.model.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DeptMapper extends BaseMapper<Dept> {

}
