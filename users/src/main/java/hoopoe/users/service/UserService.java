package hoopoe.users.service;

import com.github.imloama.mybatisplus.bootext.base.BaseServiceImpl;
import hoopoe.users.mapper.UserMapper;
import hoopoe.users.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class UserService extends BaseServiceImpl<UserMapper, User> {
    public Set<String> getUserRoles(String username) {
        return  null;
    }

    public Set<String> getUserPermissions(String username) {
        return null;
    }

    public User getUser(String username) {
        return null;
    }
}
