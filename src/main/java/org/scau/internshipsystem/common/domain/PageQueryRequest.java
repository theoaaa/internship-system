package org.scau.internshipsystem.common.domain;

import lombok.Data;

@Data
public class PageQueryRequest {
    private Integer page;
    private Integer pageSize;
}
