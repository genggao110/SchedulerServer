package com.scheduler.mdl.behavior;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: wangming
 * @Date: 2019-12-30 10:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Behavior {

    List<DatasetItem> relatedDatasets;
    List<State> states;
}
