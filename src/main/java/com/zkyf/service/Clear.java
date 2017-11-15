package com.zkyf.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/11/15.
 */
@Configuration
public class Clear {
    @Resource
    private Croe croe;

    @Scheduled(cron = "0 0 23 * * ?") // 每20秒执行一次
    public void scheduler() {
        croe.clear();
    }
}
