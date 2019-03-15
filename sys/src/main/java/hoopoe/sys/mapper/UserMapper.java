package hoopoe.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hoopoe.sys.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select({
            "select * from sys_user where name=#{name}"
    })
    User selectByName(@Param("name") String name);

}
