package org.scau.internshipsystem.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: internship-system
 * @description: 邮件实体
 * @author: guest
 * @create: 2019-08-17 13:03
 **/
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mail {
    private String to;
    private String from;
    private String subject;
    private String content;
}
