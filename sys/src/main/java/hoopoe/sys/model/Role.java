package hoopoe.sys.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.imloama.mybatisplus.bootext.base.BaseModel;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import hoopoe.core.excel.convert.TimeConverter;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@TableName("sys_role")
@Excel("角色信息表")
public class Role extends BaseModel<User,Long> {

    private static final long serialVersionUID = -1714476694755654924L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "必填项")
    @Size(max = 10, message = "长度限制")
    @ExcelField(value = "角色名称")
    private String name;

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
}