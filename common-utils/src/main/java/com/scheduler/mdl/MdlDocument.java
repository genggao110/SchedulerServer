package com.scheduler.mdl;

import com.scheduler.mdl.behavior.Behavior;
import com.scheduler.mdl.description.AttributeSet;
import com.scheduler.mdl.environment.Runtime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wangming
 * @Date: 2019-12-30 10:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MdlDocument {

    String name;
    String uid;
    String type;
    AttributeSet attributeSet;
    Behavior behavior;
    Runtime runtime;
}
