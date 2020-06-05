package com.xuecheng.order.dao;

import com.xuecheng.framework.domain.task.XcTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface XcTaskRepository extends JpaRepository<XcTask, String> {
    //取出指定时间之前的记录
    Page<XcTask> findByUpdateTimeBefore(Pageable pageable, Date updateTime);

    //更细任务处理的时间
    @Modifying  //更新操作需要使用该注解
    @Query("update XcTask t set t.updateTime = :updateTimeParam where t.id = :idParam")  //自定义更新语句
    int updateTaskTime(@Param("idParam") String id, @Param("updateTimeParam")Date updateTime);

    //使用乐观锁方式校验任务id和版本号是否匹配，匹配则版本号加1,使该任务只能被查询到一次
    @Modifying
    @Query("update XcTask t set t.version = :version+1 where t.id = :id and t.version = :version")
    int updateTaskVersion(@Param(value = "id") String id,@Param(value = "version") int version);
}
