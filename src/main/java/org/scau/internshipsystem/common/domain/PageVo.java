package org.scau.internshipsystem.common.domain;

import lombok.Data;

/**
 * @program: internship-system
 * @description: 分页参数
 * @author: guest
 * @create: 2019-08-17 09:31
 **/
@Data
public class PageVo {
    private Integer page;
    private Integer pageSize;
    public PageVo(){
        page = 1;
        pageSize = 10;
    }
}
