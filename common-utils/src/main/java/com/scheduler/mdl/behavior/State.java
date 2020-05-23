package com.scheduler.mdl.behavior;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: wangming
 * @Date: 2019-12-30 10:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class State {
    String id;
    String name;
    String type;
    String description;
    List<Event> events;
}
