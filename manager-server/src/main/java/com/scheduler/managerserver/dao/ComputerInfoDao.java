package com.scheduler.managerserver.dao;

import com.scheduler.managerserver.po.ComputerInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ComputerInfoDao extends MongoRepository<ComputerInfo,String> {
    ComputerInfo findFirstByUserIdAndMac(String userId, String mac);

    List<ComputerInfo> findAllByUserId(String userName);

    List<ComputerInfo> findAllByStatus(boolean status);
}
