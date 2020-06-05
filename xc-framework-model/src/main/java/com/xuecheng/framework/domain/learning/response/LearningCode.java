package com.xuecheng.framework.domain.learning.response;

import com.xuecheng.framework.model.response.ResultCode;
import lombok.ToString;

/**
 * Created by mrt on 2018/3/5.
 */
@ToString
public enum LearningCode implements ResultCode {
    LEARNING_GET_MEDIA_ERROR(false,24001,"学习中心获取媒资信息错误！"),
    CHOOSECOURSE_COURSEID_ISNULL(false,24002,"添加选课时获取到空的课程ID！"),
    CHOOSECOURSE_USERID_ISNULL(false,24003,"添加选课时获取到空的用户ID！"),
    CHOOSECOURSE_TASKID_ISNULL(false,24004,"添加选课时获取到空的任务ID！");

    //操作代码
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;
    private LearningCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
