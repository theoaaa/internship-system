package org.scau.internshipsystem.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 
 * </p>
 *
 * @author guest
 * @since 2019-08-16
 */
@Data
@TableName("role")
public class Role {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotBlank(message = "角色名不能为空")
    @Length(min = 2, max = 30, message = "角色名长度在2-30之间")
    private String roleName;

    @NotNull(message = "角色状态不能为空")
    private Integer state;

    @TableField(strategy = FieldStrategy.IGNORED)
    private String remark;


}
