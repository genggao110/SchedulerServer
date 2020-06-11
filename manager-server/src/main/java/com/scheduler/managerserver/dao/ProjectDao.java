package com.scheduler.managerserver.dao;

import com.scheduler.managerserver.po.Project;
import com.scheduler.mdl.environment.SupportiveResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProjectDao extends MongoRepository<Project, String> {
    List<Project> findByOwner(String userName);

    Page<Project> findByOwner(String userName,Pageable page);

    Project findFirstById(String id);

    Project findFirstByOid(String oid);

    Page<Project> findByOwnerAndStatus(String userName, int Status, Pageable page);

    Page<Project> findByOwnerAndModelNameContainsIgnoreCase(String userName, String title, Pageable page);
}
