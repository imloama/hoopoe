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
import hoopoe.core.zfarm.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@ZFarm(apiPrefix = "/api/v1/menus", tree = true, parentKey = "parentId")
@Data
@TableName("sys_menu")
@Excel("菜单信息表")
public class Menu extends BaseModel<Menu,Long> implements ITree {

    private static final long serialVersionUID = 7187628714679791771L;

    public static final String TYPE_MENU = "0";

    public static final String TYPE_BUTTON = "1";

    @ZFarmId
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ZField(type = FieldType.Integer, name = "parentId", label = "父菜单", show = false, showLabel = false)
    private Long parentId;

    @ZField(name = "name", label = "名称", rules = {
            @ZRule(required = true)
    })
    @NotBlank(message = "必填项")
    @Size(max = 10, message = "长度限制")
    @ExcelField(value = "名称")
    private String name;

    @ZField(name = "path", label = "地址")
    @Size(max = 50, message = "长度限制")
    @ExcelField(value = "地址")
    private String path;


    @ZField(name = "code", label = "权限")
    @Size(max = 50, message = "长度限制")
    @ExcelField(value = "权限")
    private String code;

    @ZField(name = "icon", label = "图标")
    @ExcelField(value = "图标")
    private String icon;

    @ZField(type = FieldType.Select, name = "type", label = "类型",
        rules = {
            @ZRule(required = true)
        },options = {
            @ZOption(key = "0", value = "按钮"),
            @ZOption(key = "1", value = "菜单")
    })
    @NotBlank(message = "必填项")
    @ExcelField(value = "类型", writeConverterExp = "0=按钮,1=菜单")
    private String type;

    @ZField(name = "orderBy", label = "排序")
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