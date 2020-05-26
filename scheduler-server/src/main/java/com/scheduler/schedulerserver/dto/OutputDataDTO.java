package com.scheduler.schedulerserver.dto;

import com.scheduler.schedulerserver.domain.TemplateInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 新版数据容器上传数据需要数据的模板，构建该DTO实体类，留给后人完善
 * @Author: wangming
 * @Date: 2020-05-25 22:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputDataDTO {
    String statename;
    String event;
    TemplateInfo template;
}
