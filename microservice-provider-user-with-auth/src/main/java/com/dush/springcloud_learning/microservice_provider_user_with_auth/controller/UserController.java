package com.dush.springcloud_learning.microservice_provider_user_with_auth.controller;

import com.dush.springcloud_learning.microservice_provider_user_with_auth.repository.UserRepository;
import com.dush.springcloud_learning.microservice_provider_user_with_auth.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by Chopper on 2018/4/23.
 */
@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    private static final Logger LOGGER= LoggerFactory.getLogger(UserController.class);

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id){
        Object principal= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            UserDetails user=(UserDetails)principal;
            Collection<?extends GrantedAuthority> collection=user.getAuthorities();
            for(GrantedAuthority c:collection){
                //打印当前用户信息
                UserController.LOGGER.info("当前用户是{},角色是{}",user.getUsername(),c.getAuthority());
            }
        }else{
            //do other things
        }
        User findOne=this.userRepository.findOne(id);
        return findOne;
    }
}
