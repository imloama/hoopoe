package hoopoe.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hoopoe.sys.model.Dept;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DeptMapper extends BaseMapper<Dept> {

    @Delete({"<script>",
                "delete from sys_dept where id in ",
                "<foreach collection=\"ids\" index=\"\" item=\"id\" open=\"(\" separator=\",\" close=\")\">",
                "#{id}",
                "</foreach>",
            "</script>"}
        )
    int deleteAll(@Param("ids") List<Long> ids);

}
