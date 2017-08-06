package com.github.alexpfx.udacity.nanodegree.android.baking_app.executor;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

/**
 * Created by alexandre on 30/07/17.
 */


public class JobExecutor implements Executor {

    private final ThreadPoolExecutor threadPoolExecutor;

    @Inject
    public JobExecutor() {
        threadPoolExecutor = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    }

    @Override
    public void execute(@NonNull Runnable command) {
        threadPoolExecutor.execute(command);
    }
}
