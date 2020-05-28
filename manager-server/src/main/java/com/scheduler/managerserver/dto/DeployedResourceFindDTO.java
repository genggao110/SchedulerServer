package com.scheduler.managerserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wangming
 * @Date: 2020-05-26 10:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeployedResourceFindDTO extends BaseSplitPageDTO {
    String searchText;
}
