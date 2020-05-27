package com.scheduler.managerserver.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scheduler.managerserver.dao.InviteDao;
import com.scheduler.managerserver.dao.ProjectDao;
import com.scheduler.managerserver.dto.ProjectAddDTO;
import com.scheduler.managerserver.po.Invite;
import com.scheduler.managerserver.po.Project;
import com.scheduler.managerserver.po.User;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
public class ProjectService {

    @Autowired
    ProjectDao projectDao;

    @Autowired
    InviteDao inviteDao;

    @Autowired
    UserService userService;

    @Autowired
    PortalUserService portalUserService;

    public Project createProject(ProjectAddDTO project){

        Project newPro = new Project();
        newPro.setOid(UUID.randomUUID().toString());
        newPro.setCreateDate(new Date());
        newPro.setOwner(project.getOwner());
        newPro.setInvitedUsers(project.getInvitedUsers());
        newPro.setInvitingUsers(project.getInvitingUsers());
        newPro.setModel(project.getModel());
        newPro.setModelName(project.getModelName());
        newPro.setTitle(project.getTitle());
        newPro.setDescription(project.getDescription());
        newPro.setStatus(0);

        if(project.getInvitingUsers().size()!=0){
            List<User> users = project.getInvitingUsers();
            for(User user:users){
                createInvite(newPro, user,project.getOwner());
            }
        }

        return projectDao.insert(newPro);

    }

    private void createInvite(Project newPro, User user, String ownerId) {
        Invite invite = new Invite();

        invite.setProject(newPro.getOid());
        invite.setModel(newPro.getModel());
        invite.setModelName(newPro.getModelName());
        invite.setInvite(ownerId);
        invite.setInvited(user.getUserName());
        invite.setOid(UUID.randomUUID().toString());
        invite.setStatus(1);
        invite.setCreateDate(new Date());

        inviteDao.insert(invite);
    }

    public Project getByOid(String oid){
        return projectDao.findFirstByOid(oid);

    }

    public JSONObject getById(String oid){
        Project project = projectDao.findFirstByOid(oid);

        String owner = project.getOwner();

        JSONArray array = portalUserService.getUsersFromPortalByType(owner,"userName");

        JSONObject jsonObject =new JSONObject();

        jsonObject.put("oid",project.getOid());
        jsonObject.put("title",project.getTitle());
        jsonObject.put("description",project.getDescription());
        jsonObject.put("owner",array.get(0));
        jsonObject.put("invitedUsers",project.getInvitedUsers());
        jsonObject.put("invitingUsers",project.getInvitingUsers());
        jsonObject.put("model",project.getModel());
        jsonObject.put("modelName",project.getModelName());
        jsonObject.put("status",project.getStatus());
        jsonObject.put("createDate",project.getCreateDate());
        jsonObject.put("resource",project.getResource());

        return jsonObject;
    }

    public JSONObject getProjectsByUserIdByStatus(String userName, String status, int page, String sortType, int sortAsc){

        Sort sort = Sort.by(sortAsc == 1 ? Sort.Direction.ASC : Sort.Direction.DESC, "createDate");
        Pageable pageable = PageRequest.of(page, 10, sort);
        Page<Project> projects = new Page<Project>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super Project, ? extends U> function) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<Project> getContent() {
                return null;
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<Project> iterator() {
                return null;
            }
        };

        if (status.equals("ready")) {
            projects = projectDao.findByOwnerAndStatus(userName, 1, pageable);
        } else if (status.equals("successful")) {
            projects = projectDao.findByOwnerAndStatus(userName, 0, pageable);
        }
        else
            projects = projectDao.findByOwner(userName, pageable);

        List<Project> pts = projects.getContent();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("count",projects.getTotalElements());
        jsonObject.put("content",pts);

        return jsonObject;
    }

    public JSONObject searchProjects(String searchText, String userName, int page, String sortType, int asc) {

        Sort sort = Sort.by(asc == 1 ? Sort.Direction.ASC : Sort.Direction.DESC, "createDate");

        Pageable pageable = PageRequest.of(page, 10, sort);

        Page<Project> projects = projectDao.findByOwnerAndModelNameContainsIgnoreCase( userName, searchText, pageable);

        JSONObject jObject = new JSONObject();
        jObject.put("count", projects.getTotalElements());
        jObject.put("content", projects.getContent());


        return jObject;

    }

    public void changeInvitingUser(String proId,String invited){
        Project project = projectDao.findFirstByOid(proId);

        List<User> invitingUsers = project.getInvitingUsers();
        User user = new User();

        for(int i=invitingUsers.size()-1;i>=0;i--){
            User user1 = invitingUsers.get(i);
            if(user1.getUserName().equals(invited)){
                user = user1;
                invitingUsers.remove(user1);
            }
        }

        List<User> inviteds = project.getInvitedUsers();
        if(user != null){
            inviteds.add(user);

        }

        projectDao.save(project);


    }
}
