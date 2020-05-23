package com.scheduler.managerserver.dto.computer;

import com.scheduler.managerserver.pojo.computer.Hardware;
import com.scheduler.managerserver.pojo.computer.Optional;
import com.scheduler.managerserver.pojo.computer.Software;
import lombok.Data;

import java.util.List;

/**
 * @Author: wangming
 * @Date: 2020-05-16 10:46
 */
@Data
public class ComputerInfoDTO {
    String userId;
    String mac;
    String agentId;
    String hostName;
    String ip;
    int port;
    int type;
    double ping_value;
    List<Software> software;
    /**
     * 具体操作系统的划分，例如centos, ubuntu
     */
    String platform;
    String version;
    String os_bit;
    /**
     * 只有两种值，Windows_NT 和 Linux
     */
    String system;
    Hardware hardware;
    Optional dynamicInfo;
}
