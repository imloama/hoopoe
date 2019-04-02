package hoopoe.sys.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import hoopoe.core.base.BaseModel;
import hoopoe.core.tree.ITree;
import hoopoe.core.excel.convert.TimeConverter;
import hoopoe.core.zfarm.FieldType;
import hoopoe.core.zfarm.annotation.ZFarm;
import hoopoe.core.zfarm.annotation.ZFarmId;
import hoopoe.core.zfarm.annotation.ZField;
import hoopoe.core.zfarm.annotation.ZRule;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@ZFarm(apiPrefix = "/api/v1/depts", tree = true, parentKey = "parentId")
@Data
@TableName("sys_dept")
@Excel("部门信息表")
public class Dept extends BaseModel<Dept,Long> implements ITree {

    private static final long serialVersionUID = -7790334862410409053L;

    @ZFarmId
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ZField(type = FieldType.Integer, name = "parentId", label = "父级", show = false, showLabel = false)
    private Long parentId;

    @ZField(name = "name", label = "名称", rules = {
            @ZRule(required = true)
    })
    @NotBlank(message = "必填")
    @Size(max = 20, message = "长度限制")
    @ExcelField(value = "部门名称")
    private String name;

    @ZField(name = "orderBy", label = "排序")
    private Integer orderBy;

    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private Date createTime;

    @ExcelField(value = "修改时间", writeConverter = TimeConverter.class)
    private Date modifyTime;

    @Override
    public <ID> ID getPrimaryKey() {
        return null;
    }

    @Override
    public String getKey() {
        return String.valueOf(id);
    }

    @Override
    public String getValue() {
        return String.valueOf(id);
    }

    @Override
    public String getLabel() {
        return this.name;
    }

    @Override
    public String getParentKey() {
        if(this.parentId == null)return null;
        return String.valueOf(this.parentId);
    }
}