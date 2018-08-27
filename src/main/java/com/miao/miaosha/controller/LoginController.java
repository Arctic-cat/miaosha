package com.miao.miaosha.controller;

import com.miao.common.ServerResponse;
import com.miao.miaosha.pojo.User;
import com.miao.miaosha.service.UserService;
import com.miao.miaosha.service.RedisService;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
@Validated
public class LoginController {
    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;



    @PostMapping("/login")
    @ResponseBody
    public ServerResponse<User> login(@NotBlank @RequestParam String idStr, @RequestParam String password, HttpServletResponse response) {

        Integer id = Integer.valueOf(idStr);

        userService.login(id, password, response);

        return userService.login(id, password, response);
    }


}
