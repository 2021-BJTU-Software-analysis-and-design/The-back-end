package com.xuecheng.framework.domain.cms.request;

import com.xuecheng.framework.model.request.RequestData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class QuerySizeRequest extends RequestData {
    //站点ID
    @ApiModelProperty("站点ID")
    private String siteId;
    //站点名称
    @ApiModelProperty("站点名称")
    private String siteName;
    //站点域
    @ApiModelProperty("站点域")
    private String siteDomain;
    //站点端口
    @ApiModelProperty("站点端口")
    private String sitePort;
    //站点访问地址
    @ApiModelProperty("站点访问地址")
    private String siteWebPath;
    //创建时间
    @ApiModelProperty("创建时间")
    private Date siteCreateTime;
}
