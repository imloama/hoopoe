package hoopoe.sys.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import hoopoe.core.base.BaseModel;

@TableName("sys_dict_ref")
public class DictRef extends BaseModel<DictRef,Long> {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 字典表的某个字段
     */
    private Long dictId;

    /**
     * 哪个表有用到这个字典
     */
    private String tableName;

    /**
     * 哪个字段有用到这个字典
     */
    private String fieldName;

    @Override
    public <ID> ID getPrimaryKey() {
        return null;
    }
}
