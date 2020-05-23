package com.scheduler.mdl.environment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: wangming
 * @Date: 2019-12-30 10:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Runtime {
    String name;
    String version;
    String baseDir;
    String entry;
    List<HardwareConfigure> hardwareConfigures;
    List<SoftwareConfigure> softwareConfigures;
    List<Assembly> assemblies;
    List<SupportiveResource> supportiveResources;
}
