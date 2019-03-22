package hoopoe;

import cn.hutool.crypto.digest.MD5;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserPasswordTest {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        MD5 md5 = new MD5();
        String hex = md5.digestHex("admin");
        String pwd = encoder.encode(hex);
        System.out.println(hex);
        System.out.println(pwd);
    }

}
