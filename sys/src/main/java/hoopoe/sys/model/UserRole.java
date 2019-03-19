package hoopoe.sys.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import hoopoe.core.base.BaseModel;
import lombok.Data;

@Data
@TableName("sys_user_role")
public class UserRole extends BaseModel<UserRole,Long> {

    @TableId
    private Long id;

    private Long userId;
    private Long roleId;

    @Override
    public Long getPrimaryKey() {
        return id;
    }
}
