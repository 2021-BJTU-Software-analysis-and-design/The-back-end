package com.xuecheng.order.service.impl;

import com.xuecheng.framework.domain.task.XcTask;
import com.xuecheng.order.dao.XcTaskRepository;
import com.xuecheng.order.service.TaskService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    XcTaskRepository xcTaskRepository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 查询任务列表的实现
     * @param n 查询数量
     * @param updateTime 上次更新时间
     * @return
     */
    @Override
    public List<XcTask> findTaskList(int n, Date updateTime) {
        Pageable pageable = new PageRequest(0, n);
        Page<XcTask> byUpdateTimeBefore = xcTaskRepository.findByUpdateTimeBefore(pageable, updateTime);
        List<XcTask> content = byUpdateTimeBefore.getContent();
        return content;
    }

    /**
     * 发送添加选课消息的实现
     * @param xcTask 消息内容
     * @param ex 交换机
     * @param routingKey 路由key
     */
    @Transactional
    @Override
    public void publishChooseMsg(XcTask xcTask, String ex, String routingKey) {
        //查询任务是否存在
        Optional<XcTask> byId = xcTaskRepository.findById(xcTask.getId());
        if(byId.isPresent()){
            xcTask = byId.get();
            //发送消息到MQ
            rabbitTemplate.convertAndSend(ex, routingKey, xcTask);
            //更细当前任务的时间
            xcTaskRepository.updateTaskTime(xcTask.getId(), new Date());
        }
    }

    /**
     * 使用乐观锁方式校验任务id和版本号是否匹配
     * @param taskId 任务id
     * @param version 任务的版本号
     * @return
     */
    @Transactional
    @Override
    public int getTask(String taskId, int version) {
            int i = xcTaskRepository.updateTaskVersion(taskId, version);
            return i;
    }
}
