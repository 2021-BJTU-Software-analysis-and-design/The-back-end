package com.xuecheng.order.mq;

import com.xuecheng.framework.domain.task.XcTask;
import com.xuecheng.order.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Component
public class ChooseCourseTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChooseCourseTask.class);
    @Autowired
    TaskService taskService;

    //每隔1分钟扫描消息表，向mq发送消息
    @Scheduled(fixedDelay = 60000)
    public void sendChoosecourseTask(){
        //取出当前时间1分钟之前的时间
        Calendar calendar =new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(GregorianCalendar.MINUTE,-1);
        Date time = calendar.getTime();
        List<XcTask> taskList = taskService.findTaskList(10, time);
        for(XcTask xcTask: taskList){
            String taskId = xcTask.getId();
            Integer version = xcTask.getVersion();
            Integer res = taskService.getTask(taskId, version);
            //调用乐观锁方法校验任务是否可以执行
            if(res > 0 ){
                //发送消息到MQ
                taskService.publishChooseMsg(xcTask, xcTask.getMqExchange(), xcTask.getMqRoutingkey());
                LOGGER.info("send choose course task id:{}",taskId);
            }
        }
    }

//    @Scheduled(fixedDelay = 10000) //上次执行完毕后5秒执行
//    @Scheduled(cron = "0/3 * * * * *")
//    public void task1(){
//        LOGGER.info("============= 测试定时任务1开始 ===============");
//        try {
//            Thread.sleep(3000);
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
//        LOGGER.info("============= 测试定时任务1结束 ===============");
//    }
//
//    @Scheduled(fixedRate = 3000) //上次执行开始时间后3秒执行
//    public void task2(){
//        LOGGER.info("===============测试定时任务2开始===============");
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        LOGGER.info("===============测试定时任务2结束===============");
//    }
}
