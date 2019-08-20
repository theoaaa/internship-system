package org.scau.internshipsystem.system.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.scau.internshipsystem.common.util.DateUtil;
import org.scau.internshipsystem.system.entity.Internship;
import org.scau.internshipsystem.system.entity.Offer;
import org.scau.internshipsystem.system.entity.Role;
import org.scau.internshipsystem.system.entity.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
@Data
public class UserMessageParam {

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

    @NotEmpty
    private List<Integer> roleIdList;

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
    public User transformToUser(){
        User user = new User();
        user.setId(id);
        user.setAccount(account);
        user.setUsername(username);
        user.setPassword(password);
        user.setGrade(grade);
        user.setEmail(email);
        user.setState(state);
        user.setSubject(subject);
        return user;
    }
}
