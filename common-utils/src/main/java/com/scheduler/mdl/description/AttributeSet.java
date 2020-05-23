package com.scheduler.mdl.description;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: wangming
 * @Date: 2019-12-30 09:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributeSet {
    List<Category> categories;
    List<LocalAttribute> localAttributes;
}
