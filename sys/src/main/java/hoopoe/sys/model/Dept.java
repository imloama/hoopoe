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
@TableName("sys_dept")
@Excel("部门信息表")
public class Dept extends BaseModel<Dept,Long> {

    private static final long serialVersionUID = -7790334862410409053L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long parentId;

    @NotBlank(message = "必填")
    @Size(max = 20, message = "长度限制")
    @ExcelField(value = "部门名称")
    private String name;

    private Integer orderBy;

    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private Date createTime;

    @ExcelField(value = "修改时间", writeConverter = TimeConverter.class)
    private Date modifyTime;

    @Override
    public <ID> ID getPrimaryKey() {
        return null;
    }
}