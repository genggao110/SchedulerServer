package com.scheduler.managerserver.dto;

import lombok.Data;

/**
 * @Author: wangming
 * @Date: 2020-05-20 17:35
 */
@Data
public class ModelResourceFindDTO extends BaseSplitPageDTO {

    String searchText;
    String type;
}
