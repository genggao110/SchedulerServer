package com.scheduler.schedulerserver;

import com.scheduler.schedulerserver.domain.Task;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.concurrent.LinkedBlockingDeque;

@EnableSwagger2
@SpringBootApplication
public class SchedulerServerApplication {

    //构建全局消息队列，采用LinkedBlockingDeque
    public static LinkedBlockingDeque<Task> linkedBlockingDeque = new LinkedBlockingDeque<>();

    public static void main(String[] args) {
        SpringApplication.run(SchedulerServerApplication.class, args);
    }

}
