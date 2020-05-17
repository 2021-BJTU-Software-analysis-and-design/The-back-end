package com.xuecheng.manage_media.service;

import com.xuecheng.framework.domain.media.request.QueryMediaFileRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;

public interface MediaFileService {
    /**
     * 查询媒体问价内信息
     * @param page 页码
     * @param size 每页数量
     * @param queryMediaFileRequest 查询条件
     * @return QueryResponseResult
     */
    public QueryResponseResult findList(int page, int size, QueryMediaFileRequest queryMediaFileRequest);
}
