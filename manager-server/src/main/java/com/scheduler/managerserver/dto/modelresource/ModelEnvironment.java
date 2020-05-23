package com.scheduler.managerserver.dto.modelresource;

import com.scheduler.managerserver.pojo.resource.HardwareInfo;
import com.scheduler.managerserver.pojo.resource.SoftwareInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 模型运行环境与计算资源进行匹配所需字段
 * @Author: wangming
 * @Date: 2020-05-21 19:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelEnvironment {
    /**
     * 模型所处的平台，分为Linux、Windows_NT、WINDOWS_X86、UNIX、MACOS
     */
    String platform;
    List<HardwareInfo> hardware;
    List<SoftwareInfo> software;
}
