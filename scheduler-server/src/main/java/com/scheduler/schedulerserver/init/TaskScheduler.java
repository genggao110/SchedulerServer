package com.scheduler.schedulerserver.init;

import com.scheduler.schedulerserver.SchedulerServerApplication;
import com.scheduler.schedulerserver.domain.Task;
import com.scheduler.schedulerserver.thread.TaskHandler;
import io.swagger.models.auth.In;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Author: wangming
 * @Date: 2019-12-03 16:05
 */
public class TaskScheduler implements Runnable {

    private static ExecutorService threadPool = Executors.newCachedThreadPool();
    LinkedBlockingDeque<Task> linkedBlockingDeque = SchedulerServerApplication.linkedBlockingDeque;

    @Override
    public void run() {
        System.out.println("准备监听任务队列任务");
        while (true){
            try{
                Task task = linkedBlockingDeque.take();
                System.out.println("成功获取到一个计算任务");
                TaskHandler taskHandler = new TaskHandler(task);
                //从线程池开辟一个线程专门用来处理这个Task
                threadPool.execute(taskHandler);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
