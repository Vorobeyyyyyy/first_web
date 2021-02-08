package com.vorobyev.fwb.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public enum ConnectionPoolCleaner implements Runnable {
    INSTANCE;

    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void run() {
        ConnectionPool.getInstance().removeUnnecessaryConnections();
    }
}
