package hoopoe.users.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.imloama.mybatisplus.bootext.base.BaseModel;
import lombok.Data;

import java.util.Date;

@Data
@TableName("users")
public class User extends BaseModel {


    @TableId
    private int id;
    private String code;//'编号' ,随机生成
    private String name;//'用户登陆账号' ,
    private String pwd;// '登陆密码' ,
    private String email;//'邮箱' ,
    private String nickname;//用户昵称' ,
    private String mobile;//'手机' ,
    private String realname;//'真实姓名' ,
    private Date create_time;//'创建时间' ,
    private String status;//'状态' ,
    private Date last_login_time;//'上次登录时间' ,
    private Short sex;//'性别' ,
    private String avatar;//'头像' ,
    private String idcard;//'身份证' ,
    private String province;//'省' ,
    private String city;//'市' ,
    private String area;//'区' ,
    private String address;//'所在地址' ,
    private String memo;//'备注' ,





    @Override
    public Object getPrimaryKey() {
        return null;
    }
}
