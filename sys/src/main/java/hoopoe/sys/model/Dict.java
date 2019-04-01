package hoopoe.sys.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import hoopoe.core.base.BaseModel;
import hoopoe.core.zfarm.annotation.ZFarm;
import hoopoe.core.zfarm.annotation.ZFarmId;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ZFarm(apiPrefix = "/api/v1/dicts")
@Data
@ToString
@TableName("sys_dict")
@Excel("字典信息表")
public class Dict extends BaseModel<Dict,Long> {

    private static final long serialVersionUID = 7780820231535870010L;

    @ZFarmId
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "必填")
    @Size(max = 10, message = "长度限制")
    @ExcelField(value = "键")
    private String k;

    @NotBlank(message = "必须")
    @Size(max = 20, message = "长度限制")
    @ExcelField(value = "值")
    private String v;

    @NotBlank(message = "必须")
    @Size(max = 20, message = "长度限制")
    @ExcelField(value = "表名")
    private String tableName;

    @NotBlank(message = "必须")
    @Size(max = 20, message = "长度限制")
    @ExcelField(value = "字段名")
    private String fieldName;

    @Override
    public Long getPrimaryKey() {
        return id;
    }
}