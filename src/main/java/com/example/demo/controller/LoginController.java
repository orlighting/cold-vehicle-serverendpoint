package com.example.demo.controller;

import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import com.example.demo.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.util.Objects;

@Controller
public class LoginController {

    @Resource
    private UserMapper userMapper;

    //跨域注解
    @CrossOrigin
    @PostMapping(value = "api/login")
    @ResponseBody
    public Result login(@RequestBody User requestUser){

        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);

        User resUser = userMapper.findByUsername(username,requestUser.getPassword());

        if(Objects.equals(username,resUser.getUsername())&&Objects.equals(requestUser.getPassword(),resUser.getPassword())){

            return new Result(200);
        }
        else {
            String message = "账号密码错误";
            System.out.println("test");
            return new Result(400);
        }

    }
}
