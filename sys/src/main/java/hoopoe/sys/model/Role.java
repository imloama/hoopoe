package hoopoe.sys.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import hoopoe.core.base.BaseModel;
import hoopoe.core.excel.convert.TimeConverter;
import hoopoe.core.zfarm.annotation.ZFarm;
import hoopoe.core.zfarm.annotation.ZFarmId;
import hoopoe.core.zfarm.annotation.ZField;
import hoopoe.core.zfarm.annotation.ZRule;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ZFarm(apiPrefix = "/api/v1/roles")
@Data
@TableName("sys_role")
@Excel("角色信息表")
public class Role extends BaseModel<Role,Long> {

    private static final long serialVersionUID = -1714476694755654924L;

    @ZFarmId
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "必填项")
    @Size(max = 10, message = "长度限制")
    @ExcelField(value = "角色编码")
    private String code;

    @ZField(name = "name", label = "角色名称", rules = {
            @ZRule(required = true)
    })
    @NotBlank(message = "必填项")
    @Size(max = 10, message = "长度限制")
    @ExcelField(value = "角色名称")
    private String name;

    @ZField(name = "remark", label = "角色描述")
    @Size(max = 50, message = "长度限制")
    @ExcelField(value = "角色描述")
    private String remark;

    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private Date createTime;

    @ExcelField(value = "修改时间", writeConverter = TimeConverter.class)
    private Date modifyTime;


    @Override
    public Long getPrimaryKey() {
        return id;
    }

    /**
     * 前台修改时，带入要改动的menuIds，从而实现更新
     */
    private transient List<Long> menuIds;

}