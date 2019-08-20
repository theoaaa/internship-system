package org.scau.internshipsystem.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scau.internshipsystem.common.util.DateUtil;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author guest
 * @since 2019-08-16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotBlank(message = "account 不能为空")
    private String account;

    @NotBlank(message = "username 不能为空")
    private String username;

    @NotBlank(message = "password 不能为空")
    private String password;

    @NotNull(message = "username 不能为空")
    private Integer state;

    @NotBlank(message = "grade 不能为空")
    private String grade;

    @NotBlank(message = "subject 不能为空")
    private String subject;

    @NotBlank(message = "subject 不能为空")
    private String email;

    @TableField(exist = false)
    private List<Role> roleList;

    private Integer internshipRef;

    private Integer offerRef;

    private String registerTime;

    private String lastLoginTime;

    @TableField(exist =  false)
    private Internship internship;
    @TableField(exist = false)
    private Offer offer;

    public void setRegisterTime(Date registerTime) {
        this.registerTime = DateUtil.formatFullTime(registerTime, DateUtil.FULL_TIME_SPLIT_PATTERN);
    }
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = DateUtil.formatFullTime(lastLoginTime, DateUtil.FULL_TIME_SPLIT_PATTERN);
    }
}
