package com.huang.order.framework.context;

import com.huang.order.dto.PageInfoDto;

/**
 * Created by Administrator on 2019/11/18.
 */
public class PageInfoContext {

    private static ThreadLocal<PageInfoDto> pageInfoHolder = new ThreadLocal<PageInfoDto>();

    public static void setPageInfo(PageInfoDto pageInfo){
        pageInfoHolder.set(pageInfo);
    }

    public static PageInfoDto getPageInfo(){
        return pageInfoHolder.get();
    }
}
