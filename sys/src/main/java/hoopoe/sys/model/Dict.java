package hoopoe.sys.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import hoopoe.core.base.BaseModel;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ToString
@TableName("sys_dict")
@Excel("字典信息表")
public class Dict extends BaseModel<Dict,Long> {

    private static final long serialVersionUID = 7780820231535870010L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "必填")
    @Size(max = 10, message = "长度限制")
    @ExcelField(value = "编码")
    private String code;

    @NotBlank(message = "必填")
    @Size(max = 10, message = "长度限制")
    @ExcelField(value = "名称")
    private String name;

    @NotBlank(message = "必须")
    @Size(max = 20, message = "长度限制")
    @ExcelField(value = "值")
    private String v;


    @ExcelField(value = "备注")
    private String memo;

    @Override
    public Long getPrimaryKey() {
        return id;
    }
}