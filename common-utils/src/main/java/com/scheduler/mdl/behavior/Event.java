package com.scheduler.mdl.behavior;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wangming
 * @Date: 2019-12-30 10:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    String id;
    String name;
    String type;
    String description;
    boolean optional;
    Parameter parameter;
}
