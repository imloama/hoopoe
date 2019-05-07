package hoopoe.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hoopoe.sys.model.Dict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DictMapper extends BaseMapper<Dict> {

    @Select({
        "SELECT * FROM sys_dict WHERE id in ( SELECT dict_id FROM sys_dict_ref where table_name = #{tableName})",
    })
    List<Dict> selectByTableName(@Param("tableName") String tableName);

}
