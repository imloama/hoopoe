package hoopoe.sys.controller;

import com.github.imloama.mybatisplus.bootext.base.APIResult;
import hoopoe.sys.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class UserController {


    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public APIResult login(String username, String password){


    }


}
