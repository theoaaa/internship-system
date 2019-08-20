package org.scau.internshipsystem.common.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionResult {
    private Integer code;
    private String url;
    private Object msg;
    private boolean result = false;
}
