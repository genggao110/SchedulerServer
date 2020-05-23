package com.scheduler.managerserver.dto.computer;

import com.scheduler.managerserver.pojo.computer.Optional;
import lombok.Data;

/**
 * @Author: wangming
 * @Date: 2020-05-18 21:43
 */
@Data
public class DynamicInfoDTO {
    String userId;
    String mac;
    Optional dynamicInfo;
}
