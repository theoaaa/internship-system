package org.scau.internshipsystem.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author guest
 * @since 2019-08-16
 */
@Data
@Builder  //builder会生成全参构造函数，覆盖默认的无参构造
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String account;

    private String username;

    private String password;

    private Integer state;

    private String grade;

    private String subject;

    private String email;

    private Integer internshipRef;

    private Integer offerRef;

    private Date registerTime;

    private Date lastLoginTime;
    @TableField(exist =  false)
    private Internship internship;
    @TableField(exist = false)
    private Offer offer;
}
