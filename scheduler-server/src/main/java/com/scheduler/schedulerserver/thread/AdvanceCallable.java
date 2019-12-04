package com.scheduler.schedulerserver.thread;

import com.scheduler.schedulerserver.domain.xml.Model;

import java.util.concurrent.Callable;

/**
 * @Author: wangming
 * @Date: 2019-12-03 17:00
 */
public class AdvanceCallable implements Callable<Model> {

    private AdvanceHandler advanceHandler;

    private int index;

    public AdvanceCallable(AdvanceHandler advanceHandler, int index) {
        this.advanceHandler = advanceHandler;
        this.index = index;
    }

    @Override
    public Model call() throws Exception {
        return this.advanceHandler.runModel(this.index);
    }
}
