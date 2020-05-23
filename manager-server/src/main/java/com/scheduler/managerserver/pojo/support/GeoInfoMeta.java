package com.scheduler.managerserver.pojo.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wangming
 * @Date: 2020-05-16 10:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeoInfoMeta {

    String city;
    String countryCode;
    String latitude;
    String countryName;
    String region;
    String longitude;
}
