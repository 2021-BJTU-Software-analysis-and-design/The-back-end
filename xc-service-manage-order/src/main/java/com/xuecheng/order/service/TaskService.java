package com.xuecheng.order.service;


import com.xuecheng.framework.domain.task.XcTask;

import java.util.Date;
import java.util.List;

public interface TaskService {
    /**
     * 查询任务列表
     * @param n 查询数量
     * @param updateTime 上次更新时间
     * @return 任务列表
     */
    List<XcTask> findTaskList(int n, Date updateTime);

    /**
     * 发送添加选课消息
     * @param xcTask 消息内容
     * @param ex 交换机
     * @param routingKey 路由key
     */
    void publishChooseMsg(XcTask xcTask,String ex,String routingKey);

    /**
     * 使用乐观锁方式校验任务id和版本号是否匹配
     * @param taskId 任务id
     * @param version 任务的版本号
     * @return 返回结果
     */
    int getTask(String taskId,int version);

    void finishTask(String taskId);
}
