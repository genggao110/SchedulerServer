package com.scheduler.managerserver.po;

import com.scheduler.managerserver.pojo.environment.HardwareEnvironment;
import com.scheduler.managerserver.pojo.environment.SoftwareEnvironment;
import com.scheduler.managerserver.pojo.support.GeoInfoMeta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @Author: wangming
 * @Date: 2020-04-22 19:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComputerInfo {
    @Id
    String id;
    String agentId;
    String userId;
    String hostName;
    /**
     * host 计算资源的IP地址
     */
    String ip;
    int port;
    /**
     * type 类型数据用以表明该模型容器是属于公网还是局域网内的机器节点
     */
    int type;
    /**
     * status 标识模型容器的状态信息
     */
    boolean status;
    /**
     *  mac 标识模型容器的唯一标识字段
     */
    String mac;
    /**
     * 该字段只有两种值，Windows_NT和Linux，主要作用是快速筛选
     */
    String system;
    /**
     * 操作系统位数，分为x64 和 x86系统架构
     */
    String os_bit;
    double ping_value;
    HardwareEnvironment hardware_info;
    SoftwareEnvironment software_info;
    GeoInfoMeta geoInfo;
    Date date;
    Date updateDate;
}
