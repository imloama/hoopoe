package hoopoe.users.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hoopoe.users.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
