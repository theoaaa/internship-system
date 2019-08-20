package org.scau.internshipsystem.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@TableName("menu")
public class Menu {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotBlank(message = "menuName 不能为空")
    @TableField("menu_name")
    private String menuName;

    @NotNull(message = "pid 不能为空")
    private Integer pid;

    @NotNull(message = "state 不能为空")
    private Integer state;

    @NotBlank(message = "url 不能为空")
    private String url;

    @NotNull(message = "type 不能为空")
    private Integer type;

    @NotBlank(message = "code 不能为空")
    @TableField("`code`")
    private String code;

    @NotNull(message = "order 不能为空")
    @TableField("`order`")
    private Integer order;

    @TableField(exist = false)
    private List<Menu> children;
}
