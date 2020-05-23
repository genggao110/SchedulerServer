package com.scheduler.managerserver.dao;

import com.scheduler.managerserver.po.Validate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author: wangming
 * @Date: 2019-12-19 11:16
 */
public interface ValidateDao extends MongoRepository<Validate, String> {
    Validate findValidateByEmail(String email);
}
