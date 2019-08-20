package org.scau.internshipsystem.system.param;

import lombok.Data;
import org.scau.internshipsystem.system.entity.User;

/**
 * @program: internship-system
 * @description: 用户请求参数
 * @author: guest
 * @create: 2019-08-17 09:41
 **/
@Data
public class UserParam extends User {
    private String job;
    private String company;
    private String offerState;
}
