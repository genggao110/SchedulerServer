package com.scheduler.managerserver.pojo.environment;

import com.scheduler.managerserver.pojo.computer.Software;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: wangming
 * @Date: 2020-04-22 19:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SoftwareEnvironment {
    String os;
    String osVersion;
    List<Software> softwares;
}
