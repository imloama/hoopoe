package hoopoe.sys.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import hoopoe.core.base.BaseModel;
import hoopoe.core.excel.convert.TimeConverter;
import hoopoe.core.zfarm.FieldType;
import hoopoe.core.zfarm.RulePattern;
import hoopoe.core.zfarm.annotation.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ZFarm(value="users")
@Data
@TableName("sys_user")
@Excel("用户信息表")
public class User extends BaseModel<User,Long> implements UserDetails {
    /**
     * 账户状态
     */
    public static final Short STATUS_VALID = 0;
    public static final Short STATUS_LOCK = 1;

    /**
     * 性别
     */
    public static final Short SEX_MALE = 0;
    public static final Short SEX_FEMALE = 1;
    public static final Short SEX_UNKNOW = 2;
    // 默认密码
    public static final String DEFAULT_PASSWORD = "hoopoe";

    @ZFarmId
    @TableId
    private Long id;

    private String code;//'编号' ,随机生成

    @ZField(name = "name",label = "用户名", rules = {
            @ZRule(required = true, min = 6, max = 20, errMsg = "用户名长度限制为[6,20]")
    })
    @Size(min = 6, max = 20, message = "用户名长度限制为[6,20]")
    @ExcelField(value = "用户名")
    private String name;//'用户登陆账号' ,


    private String pwd;// '登陆密码' ,

    @ZField(type = FieldType.Password, name = "password",label = "密码", rules = {
            @ZRule(required = true, min = 6, max = 56, errMsg = "密码长度限制为[6,20]")
    }, show = false)
    private transient String password;


    @ZField(type = FieldType.Email, name = "email",label = "邮箱", rules = {
            @ZRule(required = true, pattern = RulePattern.EMAIL, errMsg = "必须为邮箱")
    })
    @Size(max = 50, message = "邮箱限制50字以内")
    @Email(message = "必须为邮箱")
    @ExcelField(value = "邮箱")
    private String email;//'邮箱' ,

    @ZField(name = "nickname", label = "昵称")
    private String nickname;//用户昵称' ,


    @ZField(name = "mobile", label = "手机号")
    @Pattern(regexp = "[1]\\d{10}", message = "必须为手机号")
    @ExcelField(value = "手机号")
    private String mobile;//'手机' ,

    @ZField(name = "realName", label = "真实姓名")
    private String realName;//'真实姓名' ,

    @ZField(type = FieldType.DateTime, name = "createTime", label = "创建时间", edit = false, format = "yyyy-MM-dd HH:mm:ss")
    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private Date createTime;//'创建时间'

    @ZField(type = FieldType.DateTime, name = "modifyTime", label = "修改时间", edit = false, format = "yyyy-MM-dd HH:mm:ss")
    @ExcelField(value = "修改时间", writeConverter = TimeConverter.class)
    private Date modifyTime;

    @ZField(type = FieldType.Select, name = "status", label = "状态", options = {
            @ZOption(key = "0", value = "正常"),
            @ZOption(key = "1", value = "锁定")
    })
    private Short status;//'状态' ,


    private Date lastLoginTime;//'上次登录时间' ,

    @ZField(type = FieldType.Select, name = "sex", label = "性别", options = {
            @ZOption(key = "0", value = "女"),
            @ZOption(key = "1", value = "男"),
            @ZOption(key = "2", value = "保密")
    })
    @NotBlank(message = "必须填")
    @ExcelField(value = "性别", writeConverterExp = "0=女,1=男,2=保密")
    private Short sex;//'性别' ,

    @ZField(type = FieldType.Image, name = "avatar", label = "头像")
    private String avatar;//'头像' ,


    private String idCard;//'身份证' ,
    private String province;//'省' ,
    private String city;//'市' ,
    private String area;//'区' ,
    private String address;//'所在地址' ,
    @Size(max = 100, message = "长度限制")
    @ExcelField(value = "备注")
    private String memo;//'备注' ,

    @ZField(type = FieldType.Ref, name = "deptId", label = "部门", ref = {
            @ZRef(primaryKey = "id", labelKey = "name", value = "depts", parentKey = "parentId")
    })
    private Long deptId;

    @ExcelField(value = "部门")
    private transient String deptName;

    private transient List<Role> roles;
    private transient List<Menu> menus;

    private transient String token;


    public Long getAuthCacheKey() {
        return id;
    }


    @Override
    public Object getPrimaryKey() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.roles!=null){
            return this.roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
        }
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return this.pwd;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.STATUS_VALID == this.status;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.STATUS_VALID == this.status;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.STATUS_VALID == this.status;
    }

    @Override
    public boolean isEnabled() {
        return this.STATUS_VALID == this.status;
    }
}
