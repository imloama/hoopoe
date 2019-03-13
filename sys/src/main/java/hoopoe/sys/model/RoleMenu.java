package hoopoe.sys.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.imloama.mybatisplus.bootext.base.BaseModel;
import lombok.Data;


@TableName("sys_role_menu")
@Data
public class RoleMenu extends BaseModel<RoleMenu,Long> {

    private static final long serialVersionUID = -7573904024872252113L;

    @TableId
    private Long id;

    private Long roleId;

    private Long menuId;

    @Override
    public Long getPrimaryKey() {
        return id;
    }
}