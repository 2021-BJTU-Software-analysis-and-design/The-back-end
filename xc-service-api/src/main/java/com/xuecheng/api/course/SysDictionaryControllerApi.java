package com.xuecheng.api.course;

import com.xuecheng.framework.domain.system.SysDictionary;
import io.swagger.annotations.Api;

@Api(value = "查询常用的字典")
public interface SysDictionaryControllerApi {
    public SysDictionary findSysDictionary(String dType);
}
