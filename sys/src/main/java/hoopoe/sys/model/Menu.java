package hoopoe.sys.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import hoopoe.core.base.BaseModel;
import hoopoe.core.tree.ITree;
import hoopoe.core.excel.convert.TimeConverter;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@TableName("sys_menu")
@Excel("菜单信息表")
public class Menu extends BaseModel<Menu,Long> implements ITree {

    private static final long serialVersionUID = 7187628714679791771L;

    public static final String TYPE_MENU = "0";

    public static final String TYPE_BUTTON = "1";

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long parentId;

    @NotBlank(message = "必填项")
    @Size(max = 10, message = "长度限制")
    @ExcelField(value = "名称")
    private String name;

    @Size(max = 50, message = "长度限制")
    @ExcelField(value = "地址")
    private String path;

    @Size(max = 100, message = "长度限制")
    @ExcelField(value = "对应Vue组件")
    private String component;

    @Size(max = 50, message = "长度限制")
    @ExcelField(value = "权限")
    private String perms;

    @ExcelField(value = "图标")
    private String icon;

    @NotBlank(message = "必填项")
    @ExcelField(value = "类型", writeConverterExp = "0=按钮,1=菜单")
    private String type;

    private Double orderBy;

    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private Date createTime;

    @ExcelField(value = "修改时间", writeConverter = TimeConverter.class)
    private Date modifyTime;


    @Override
    public Long getPrimaryKey() {
        return id;
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
        return name;
    }

    @Override
    public String getParentKey() {
        if(this.parentId == null)return null;
        return String.valueOf(parentId);
    }
}