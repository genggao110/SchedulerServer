package com.scheduler.managerserver.service;

import com.scheduler.managerserver.dao.ValidateDao;
import com.scheduler.managerserver.po.User;
import com.scheduler.managerserver.po.Validate;
import com.scheduler.webCommons.enums.ResultEnum;
import com.scheduler.webCommons.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Random;

/**
 * @Author: wangming
 * @Date: 2019-12-24 21:10
 */
@Service
public class ValidateService {

    @Autowired
    UserService userService;

    @Autowired
    ValidateDao validateDao;

    @Value("${spring.mail.username}")
    private String mailUserName;

    @Autowired
    JavaMailSender mailSender;

    public Validate getValidateByEmail(String email){
        return validateDao.findValidateByEmail(email);
    }

    public Validate update(Validate validate){
        return validateDao.save(validate);
    }

    public boolean getCode(String email){
        //首先根据邮箱来查找这个用户是否存在
        User user = userService.findUserByEmail(email);
        if(user == null){
            throw new MyException(ResultEnum.NO_OBJECT);
        }else{
            //用户存在下面就去查找验证数据库，看看有没有记录
            Validate validate = validateDao.findValidateByEmail(email);
            //生成验证码
            String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
            if(validate != null){
                //更新数据库记录
                Date currentDate = new Date(System.currentTimeMillis());
                Date modifyDate = new Date(System.currentTimeMillis() + (long) 10*60*1000);
                validate.setVerifyCode(verifyCode);
                validate.setCreate(currentDate);
                validate.setModified(modifyDate);
                validateDao.save(validate);
                //发送邮件
                try {
                    sendEmail(email, verifyCode);
                } catch (MessagingException e) {
                    throw new MyException(ResultEnum.REMOTE_SERVICE_ERROR);
                }
                return true;
            }else{
                //如果不存在该条数据库记录，则新生成
                Validate newRecord = new Validate();
                Validate result = insertNewResetRecord(newRecord,user,verifyCode);
                if(result != null){
                    //发送邮件
                    try {
                        sendEmail(email, verifyCode);
                    } catch (MessagingException e) {
                        throw new MyException(ResultEnum.REMOTE_SERVICE_ERROR);
                    }
                    return true;
                }else{
                    return false;
                }
            }
        }
    }

    public Validate insertNewResetRecord(Validate validate, User user, String verifyCode){
//        validate.setUserId(user.getId());
        validate.setEmail(user.getEmail());
        validate.setType("email");
        validate.setVerifyCode(verifyCode);
        validate.setCreate(new Date(System.currentTimeMillis()));
        //10分钟后过期
        validate.setModified(new Date(System.currentTimeMillis() + (long) 10*60*1000));
        return validateDao.insert(validate);
    }

    private void sendEmail(String email, String verifyCode) throws MessagingException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html><head><title></title></head><body>");
        stringBuilder.append("您好<br/>");
        stringBuilder.append("您的验证码是：").append(verifyCode).append("<br/>");
        stringBuilder.append("您可以复制该验证码至重置密码页面，以验证您的邮箱。<br/>");
        stringBuilder.append("此验证码只能使用一次，在10分钟内有效。验证成功则自动失效。<br/>");
        stringBuilder.append("如果您没有进行上述操作，请忽略此邮件。");

        //发送邮件
        MimeMessage mimeMailMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage,true);
        mimeMessageHelper.setFrom(mailUserName);
        mimeMessageHelper.setTo(email);
        mimeMailMessage.setSubject("重置密码");
        mimeMessageHelper.setText(stringBuilder.toString(),true);
        mailSender.send(mimeMailMessage);
    }
}
