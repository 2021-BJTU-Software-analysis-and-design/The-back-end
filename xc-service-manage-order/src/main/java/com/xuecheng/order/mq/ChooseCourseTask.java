package com.xuecheng.order.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ChooseCourseTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChooseCourseTask.class);

//    @Scheduled(fixedDelay = 10000) //上次执行完毕后5秒执行
    @Scheduled(cron = "0/3 * * * * *")
    public void task1(){
        LOGGER.info("============= 测试定时任务1开始 ===============");
        try {
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        LOGGER.info("============= 测试定时任务1结束 ===============");
    }

    @Scheduled(fixedRate = 3000) //上次执行开始时间后3秒执行
    public void task2(){
        LOGGER.info("===============测试定时任务2开始===============");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("===============测试定时任务2结束===============");
    }
}
