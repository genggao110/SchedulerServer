package com.scheduler.mdl.description;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wangming
 * @Date: 2019-12-30 09:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalAttribute {
    String local;
    String localName;
    String wiki;
    String keywords;
    String abstracts;
}
