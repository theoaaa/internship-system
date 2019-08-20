package org.scau.internshipsystem.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.scau.internshipsystem.system.entity.group.OfferReviewGroup;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("offer")
public class Offer  {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotBlank(message = "Offer company字段不能为空")
    @Length(max = 50, min = 2, message = "Offer company字段长度应在2-50之间")
    @TableField("company")
    private String company;

    @NotBlank(message = "Offer job字段不能为空")
    @Length(max = 30, min = 2, message = "Offer job字段长度应在2-30之间")
    @TableField("job")
    private String job;

    @TableField("image_path")
    private String imagePath;

    @TableField("state")
    private Integer state;

    @TableField(exist = false)
    private Integer userId;
}
