package com.xuecheng.framework.model.response;
import lombok.ToString;

@ToString
public enum CmsCode implements ResultCode {

    CMS_ADDPAGE_EXISTS(false,24001,"页面已存在！"),
    CMS_PAGE_NOT_EXISTS(false,24002,"页面不存在！"),
    CMS_PAGE_FILEID_NOT_EXISTS(false,24002,"页面不存在！"),
    CMS_SIZE_NOT_EXISTS(false,24002,"站点不存在！"),
    CMS_GENRATEHTML_DATAURL_IS_NULL(false,24003,"从页面信息中找不到获取数据的url"),
    CMS_GENRATEHTML_DATA_IS_NULL(false,24004,"根据页面的数据url获取不到数据"),
    CMS_GENRATEHTML_TEMPLATE_IS_NULL(false,24005,"页面模板为空"),
    CMS_GENRATEHTML_HTML_IS_NULL(false,24006,"生成的静态html为空"),
    CMS_GENRATEHTML_SAVE_HTML_ERROR(false,24007,"保存静态html出错"),
    CMS_GENRATEHTML_PERVIEW_IS_NULL(false,24008,"预览页面为空"),
    CMS_PAGE_NAME_NOT_EXISTS(false,24009,"页面名称不存在");


//    CMS_ADDPAGE_PARAM_ERROR(false,24002,"提交的参数有误！");

    //操作结果
    boolean success;

    //操作代码
    int code;

    //提示信息
    String message;

    private CmsCode(boolean success, int code, String message){
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
