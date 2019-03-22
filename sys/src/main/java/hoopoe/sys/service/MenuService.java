package hoopoe.sys.service;

import com.github.imloama.mybatisplus.bootext.base.BaseServiceImpl;
import hoopoe.sys.mapper.DictMapper;
import hoopoe.sys.mapper.MenuMapper;
import hoopoe.sys.model.Dict;
import hoopoe.sys.model.Menu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class MenuService extends BaseServiceImpl<MenuMapper, Menu> {


    @Transactional(readOnly = true)
    public List<Menu> findByUser(Long userId){
        return this.baseMapper.findByUser(userId);
    }

    @Transactional(readOnly = true)
    public List<String> findUserIdsByMenuId(Long menuId){
        return this.baseMapper.findUserIdsByMenuId(menuId);
    }

}
