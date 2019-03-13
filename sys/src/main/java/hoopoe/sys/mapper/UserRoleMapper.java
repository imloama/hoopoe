package hoopoe.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hoopoe.sys.model.User;
import hoopoe.sys.model.UserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
}
