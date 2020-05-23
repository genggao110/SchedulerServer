package com.scheduler.managerserver.dao;

import com.scheduler.managerserver.po.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * @Author: wangming
 * @Date: 2019-12-19 11:16
 */
public interface UserDao extends MongoRepository<User,String> {

    Optional<User> findUserByName(String name);
    Optional<User> findUserByNameOrEmail(String name, String email);
    User findUserByEmail(String email);
}
