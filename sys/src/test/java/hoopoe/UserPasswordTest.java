package hoopoe;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserPasswordTest {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pwd = encoder.encode("admin");
        System.out.println(pwd);
    }

}
