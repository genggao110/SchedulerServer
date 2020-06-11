package com.scheduler.schedulerserver.thread;

import com.scheduler.schedulerserver.domain.xml.Model;
import com.scheduler.schedulerserver.dto.ServicesMapping;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @Author: wangming
 * @Date: 2019-12-03 17:00
 */
public class AdvanceCallable implements Callable<Model> {

    private AdvanceHandler advanceHandler;

    private ServicesMapping servicesMapping;

    private int index;

    public AdvanceCallable(AdvanceHandler advanceHandler, int index, ServicesMapping servicesMapping) {
        this.advanceHandler = advanceHandler;
        this.index = index;
        this.servicesMapping = servicesMapping;
    }

    @Override
    public Model call() throws Exception {
        return this.advanceHandler.runModel(this.index, servicesMapping);
    }
}
