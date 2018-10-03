package com.blog.api.controller;

import com.blog.api.repository.UserRepository;
import com.blog.api.repository.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()) == null) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String finalPassword = bCryptPasswordEncoder.encode(user.getPassword());

            user.setPassword(finalPassword);
            userRepository.save(user);
            return "注册成功！";
        }
        return "注册失败";
    }

}
