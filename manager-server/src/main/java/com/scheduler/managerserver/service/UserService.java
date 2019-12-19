package com.scheduler.managerserver.service;

import com.alibaba.fastjson.JSONObject;
import com.scheduler.managerserver.dao.UserDao;
import com.scheduler.managerserver.dto.user.AddUserDTO;
import com.scheduler.managerserver.dto.user.SignInDTO;
import com.scheduler.managerserver.po.User;
import com.scheduler.managerserver.service.common.TokenService;
import com.scheduler.webCommons.enums.ResultEnum;
import com.scheduler.webCommons.exception.MyException;
import com.scheduler.webCommons.utils.CopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: wangming
 * @Date: 2019-12-19 11:14
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    TokenService tokenService;

    public void add(AddUserDTO addUserDTO){
        if (userDao.findUserByName(addUserDTO.getName()).orElse(null) != null){
            throw new MyException(ResultEnum.EXIST_OBJECT);
        }
        User user = new User();
        CopyUtils.copyProperties(addUserDTO, user);
        user.setCreateDate(new Date());
        userDao.insert(user);
    }

    public User findUserByName(String name){
        User user  = userDao.findUserByName(name).orElseGet(() -> {
            throw new MyException(ResultEnum.NO_OBJECT);
        });
        return user;
    }

    public User findUserById(String id){
        User user = userDao.findById(id).orElseGet(() -> {
            throw new MyException(ResultEnum.NO_OBJECT);
        });
        return user;
    }

    public JSONObject login(SignInDTO signInDTO){
        User user = userDao.findUserByName(signInDTO.getName()).orElseGet(() -> {
            throw new MyException(ResultEnum.NO_OBJECT);
        });
        JSONObject jsonObject = new JSONObject();
        if(signInDTO.getPassword().equals(user.getPassword())){
            String token = tokenService.getToken(user);
            jsonObject.put("token",token);
        }else{
            throw new MyException(ResultEnum.USER_PASSWORD_NOT_MATCH);
        }
        return jsonObject;
    }
}
