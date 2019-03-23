package hoopoe.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hoopoe.sys.model.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {


    @Select({
            "select * from sys_menu where id in (select rm.menu_id from sys_role_menu rm, sys_user_role ur where rm.role_id=ur.role_id and ur.user_id=#{userId})"
    })
    List<Menu> findByUser(Long userId);


    /**
     * 查找当前菜单/按钮关联的用户 ID
     */
    @Select("select ur.user_id from sys_role_menu rm, sys_user_role ur where rm.role_id=ur.role_id and rm.menu_id=#{menuId}")
    List<String> findUserIdsByMenuId(Long menuId);

}
