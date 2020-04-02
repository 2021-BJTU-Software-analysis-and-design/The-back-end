package com.xuecheng.framework.domain.cms.request;

import com.xuecheng.framework.model.request.RequestData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryTemplateRequest extends RequestData {
    //站点ID
    @ApiModelProperty("站点ID")
    private String siteId;
    //模版ID
    @ApiModelProperty("模版ID")
    private String templateId;
    //模版名称
    @ApiModelProperty("模版名称")
    private String templateName;
    //模版参数
    @ApiModelProperty("模版参数")
    private String templateParameter;
    //模版文件Id
    @ApiModelProperty("模版文件Id")
    private String templateFileId;
}
