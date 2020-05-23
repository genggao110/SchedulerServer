package com.scheduler.managerserver.pojo.computer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wangming
 * @Date: 2020-04-22 19:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GPU {
    String vram_size;
    String vram_type;
    String gpu_na;
    String vram_speed;
    String vram_width;
}
