package com.scheduler.managerserver.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.scheduler.managerserver.dao.UserDao;
import com.scheduler.managerserver.dto.user.AddUserDTO;
import com.scheduler.managerserver.dto.user.ResetPasswordDTO;
import com.scheduler.managerserver.dto.user.SignInDTO;
import com.scheduler.managerserver.po.User;
import com.scheduler.managerserver.po.Validate;
import com.scheduler.managerserver.utils.JwtTokenUtil;
import com.scheduler.webCommons.enums.ResultEnum;
import com.scheduler.webCommons.exception.MyException;
import com.scheduler.webCommons.utils.CopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
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
    ValidateService validateService;

    public JSONObject add(AddUserDTO addUserDTO){
        if (userDao.findUserByNameOrEmail(addUserDTO.getName(), addUserDTO.getEmail()).orElse(null) != null){
            throw new MyException(ResultEnum.EXIST_OBJECT);
        }
        User user = new User();
        CopyUtils.copyProperties(addUserDTO, user);
        user.setCreateDate(new Date());
        User newUser = userDao.insert(user);
        JSONObject jsonObject = new JSONObject();
        JSONObject userInfo = new JSONObject();
        userInfo.put("email",newUser.getEmail());
        userInfo.put("id", newUser.getId());
        userInfo.put("name", newUser.getName());
        String token  = generateToken(newUser.getId(),newUser.getName(),newUser.getPassword(),false);
        jsonObject.put(JwtTokenUtil.TOKEN_HEADER,JwtTokenUtil.TOKEN_PREFIX + token);
        jsonObject.put("userInfo",userInfo);
        return jsonObject;
    }

    public User findUserByName(String name){
        //name兼容邮箱和用户名
        User user  = userDao.findUserByNameOrEmail(name, name).orElseGet(() -> {
            throw new MyException(ResultEnum.NO_OBJECT);
        });
        return user;
    }

    public User findUserById(String id){
        User user = userDao.findById(id).orElseThrow(MyException::noObject);
        return user;
    }

    public User findUserByEmail(String email){
        return userDao.findUserByEmail(email);
    }

    public JSONObject login(SignInDTO signInDTO){
        User user = userDao.findUserByNameOrEmail(signInDTO.getName(),signInDTO.getName()).orElseGet(() -> {
            throw new MyException(ResultEnum.NO_OBJECT);
        });
        JSONObject jsonObject = new JSONObject();
        JSONObject userInfo = new JSONObject();
        userInfo.put("email",user.getEmail());
        userInfo.put("id", user.getId());
        userInfo.put("name", user.getName());
        if(signInDTO.getPassword().equals(user.getPassword())){
            String token = generateToken(user.getId(),user.getName(),user.getPassword(),false);
            jsonObject.put(JwtTokenUtil.TOKEN_HEADER,JwtTokenUtil.TOKEN_PREFIX + token);
            jsonObject.put("userInfo",userInfo);
        }else{
            throw new MyException(ResultEnum.USER_PASSWORD_NOT_MATCH);
        }
        return jsonObject;
    }

    private String generateToken(String id, String userName, String password, boolean isRememberMe){
        String token = JwtTokenUtil.createToken(id, userName, password, isRememberMe);
        return token;
    }

    public boolean validateUserName(String userName){
        User user = userDao.findUserByName(userName).orElseGet(() ->  null);
        if(user == null){
            return true;
        }
        return false;
    }

    public JSONObject resetPassword(ResetPasswordDTO resetPasswordDTO){
        //还是得先去判断该用户是否还存在
        JSONObject result = new JSONObject();
        User user = findUserByEmail(resetPasswordDTO.getEmail());
        if(user == null){
            result.put("code",-2);
            result.put("message", "不存在该邮箱用户，请仔细检查一下邮箱");
            return result;
        }else {
            //直接根据邮箱去查找是否存在该记录
            Validate validate = validateService.getValidateByEmail(resetPasswordDTO.getEmail());
            if(validate == null){
                result.put("code",-2);
                result.put("message", "不存在该邮箱用户，请仔细检查一下邮箱");
                return result;
            }else{
                //存在这条记录，判断验证码是否已经过期
                long tokenOutOfTime = validate.getModified().getTime();
                boolean status = resetPasswordDTO.getVerifyCode().equals(validate.getVerifyCode());
                if(System.currentTimeMillis() > tokenOutOfTime || !status){
                    //说明验证码已经过期或者不正确
                    result.put("code",-12);
                    result.put("message", "验证码无效或者已过期，请重新获取验证码");
                    return result;
                }else {
                    //为了严谨，后台再次校验两次密码输入是否一致
                    if(!resetPasswordDTO.getPassword().equals(resetPasswordDTO.getConfirmPassword())){
                        result.put("code",-13);
                        result.put("message", "两次密码不匹配，请检查后重新提交");
                        return result;
                    }else {
                        //修改密码
                        user.setPassword(resetPasswordDTO.getPassword());
                        userDao.save(user);
                        //失效当前验证码
                        validate.setVerifyCode("0");
                        validate.setCreate(new Date(System.currentTimeMillis()));
                        validate.setModified(new Date(System.currentTimeMillis()));
                        validateService.update(validate);
                    }
                }
            }
        }
        result.put("code",0);
        result.put("message", "重设密码成功");
        return result;
    }


}
