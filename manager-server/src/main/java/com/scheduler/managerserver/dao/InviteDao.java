package com.scheduler.managerserver.dao;

import com.scheduler.managerserver.po.Invite;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InviteDao extends MongoRepository<Invite, String> {

    Invite findByOid(String oid);

    List<Invite> findByInvite(String invite) ;
    List<Invite> findByInvited(String invited);

}
