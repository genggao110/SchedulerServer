package com.scheduler.schedulerserver.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Author: wangming
 * @Date: 2019-12-03 14:47
 */
@Component
public class LoadTaskObserverRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //开辟一个线程，监听消息队列中的任务
        System.out.println("初始化任务监听线程");
        new Thread(new TaskScheduler(), "TaskObserver").start();
        System.out.println("任务监听线程已经启动");
    }
}
