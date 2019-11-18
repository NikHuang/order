package com.huang.order.dto;

import lombok.Data;

/**
 * Created by Administrator on 2019/11/18.
 */
@Data
public class PageInfoDto {

    private int currentPage;

    private int pageSize;

    public PageInfoDto() {
    }

    public PageInfoDto(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }
}
