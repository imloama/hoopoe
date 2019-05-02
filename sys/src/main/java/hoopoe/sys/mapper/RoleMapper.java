package hoopoe.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hoopoe.sys.model.Role;
import hoopoe.sys.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select({
            "select * from sys_role where id in (",
            "select role_id from sys_user_role where user_id=#{userId}",
            ") "
    })
    List<Role> selectByUserId(@Param("userId") Long userId);

    @Select("select count(*) from sys_role where code=#{code}")
    int countByCode(@Param("code") String code);
}
