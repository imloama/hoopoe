package hoopoe.sys.service;

import com.github.imloama.mybatisplus.bootext.base.BaseServiceImpl;
import hoopoe.sys.mapper.DictMapper;
import hoopoe.sys.mapper.MenuMapper;
import hoopoe.sys.model.Dict;
import hoopoe.sys.model.Menu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MenuService extends BaseServiceImpl<MenuMapper, Menu> {

}
