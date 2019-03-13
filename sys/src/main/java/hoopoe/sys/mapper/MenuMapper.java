package hoopoe.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hoopoe.sys.model.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {


    List<Menu> findUserPermissions(String userName);

    List<Menu> findUserMenus(String userName);

    /**
     * 查找当前菜单/按钮关联的用户 ID
     */
    List<String> findUserIdsByMenuId(String menuId);

    /**
     * 递归删除菜单/按钮
     */
    //void deleteMenus(String menuId);

}
