package com.pbalazs.fxdemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.Executor;

/**
 * Created by Peter on 4/9/2017.
 */
@Configuration
@EnableScheduling
@EnableAsync
public class AppConfig {
}
