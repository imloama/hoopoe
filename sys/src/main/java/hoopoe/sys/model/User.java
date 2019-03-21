package hoopoe.sys.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import hoopoe.core.base.BaseModel;
import hoopoe.core.excel.convert.TimeConverter;
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

@Data
@TableName("sys_users")
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

    @TableId
    private Long id;
    private String code;//'编号' ,随机生成
    @Size(min = 6, max = 20, message = "用户名长度限制为[6,20]")
    @ExcelField(value = "用户名")
    private String name;//'用户登陆账号' ,
    private String pwd;// '登陆密码' ,
    @Size(max = 50, message = "邮箱限制50字以内")
    @Email(message = "必须为邮箱")
    @ExcelField(value = "邮箱")
    private String email;//'邮箱' ,
    private String nickname;//用户昵称' ,
    @Pattern(regexp = "[1]\\d{10}", message = "必须为手机号")
    @ExcelField(value = "手机号")
    private String mobile;//'手机' ,
    private String realName;//'真实姓名' ,
    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private Date createTime;//'创建时间'
    @ExcelField(value = "修改时间", writeConverter = TimeConverter.class)
    private Date modifyTime;
    private Short status;//'状态' ,
    private Date lastLoginTime;//'上次登录时间' ,
    @NotBlank(message = "必须填")
    @ExcelField(value = "性别", writeConverterExp = "0=男,1=女,2=保密")
    private Short sex;//'性别' ,
    private String avatar;//'头像' ,
    private String idCard;//'身份证' ,
    private String province;//'省' ,
    private String city;//'市' ,
    private String area;//'区' ,
    private String address;//'所在地址' ,
    @Size(max = 100, message = "长度限制")
    @ExcelField(value = "备注")
    private String memo;//'备注' ,

    private Long deptId;
    @ExcelField(value = "部门")
    private transient String deptName;

    private transient List<Role> roles;

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
