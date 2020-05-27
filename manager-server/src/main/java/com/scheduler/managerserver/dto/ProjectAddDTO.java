package com.scheduler.managerserver.dto;

import com.scheduler.managerserver.po.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectAddDTO {

    String title;
    String description;
    String owner;
    List<User> invitedUsers;
    List<User> invitingUsers;
    String model;
    String modelName;
}
