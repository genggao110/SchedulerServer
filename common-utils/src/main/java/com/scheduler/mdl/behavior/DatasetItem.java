package com.scheduler.mdl.behavior;

import com.alibaba.fastjson.JSONArray;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wangming
 * @Date: 2019-12-30 10:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatasetItem {
    String name;
    String type;
    String description;
    JSONArray dataset;
    String id;
    String parentId;
}
