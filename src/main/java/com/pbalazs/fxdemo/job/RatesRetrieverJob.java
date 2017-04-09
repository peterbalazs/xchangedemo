package com.pbalazs.fxdemo.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Peter on 4/9/2017.
 */
@Component
public class RatesRetrieverJob {

    @Autowired
    private RatesRetrieverJobAsyncExecutor asyncExecutor;

    /**
     * Run at 4 AM every day
     */
    @Scheduled(cron = "00 00 04 * * ?")
    public void runJob() {
        asyncExecutor.runJob();
    }
}
