package org.scau.internshipsystem.common.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.scau.internshipsystem.common.domain.PageQueryRequest;

public class QueryRequestUtil {
    final static int DEFAULT_PAGE = 1;
    final static int DEFAULT_PAGESIZE = 10;


    public static void setPageQueryRequest(Page page, PageQueryRequest pageQueryRequest) {
        if (pageQueryRequest != null) {
            page.setCurrent(pageQueryRequest.getPage() == null ? DEFAULT_PAGE : pageQueryRequest.getPage());
            page.setSize(pageQueryRequest.getPageSize() == null ? DEFAULT_PAGESIZE : pageQueryRequest.getPageSize());
        }
    }
}
