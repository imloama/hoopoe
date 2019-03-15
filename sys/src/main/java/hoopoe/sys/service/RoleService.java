package hoopoe.sys.service;

import com.github.imloama.mybatisplus.bootext.base.BaseServiceImpl;
import hoopoe.sys.mapper.MenuMapper;
import hoopoe.sys.mapper.RoleMapper;
import hoopoe.sys.model.Menu;
import hoopoe.sys.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class RoleService extends BaseServiceImpl<RoleMapper, Role> {

    @Transactional(readOnly = true)
    public List<Role> findByUser(Long userId){
        return this.baseMapper.selectByUserId(userId);
    }

}