package com.pbalazs.fxdemo;

import com.pbalazs.fxdemo.job.RatesRetrieverJobAsyncExecutor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by Peter on 4/9/2017.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    ApplicationRunner init(RatesRetrieverJobAsyncExecutor executor) {
        executor.runJob();
        return null;
    }

}
