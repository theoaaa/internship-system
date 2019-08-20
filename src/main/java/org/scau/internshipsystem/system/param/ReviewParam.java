package org.scau.internshipsystem.system.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @program: internship-system
 * @description: offer审核参数
 * @author: guest
 * @create: 2019-08-17 13:46
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewParam {
    @NotNull(message = "offerId不能为空")
    private Integer offerId;
    private Integer userId;
    @NotBlank(message = "用户邮箱不能为空")
    private String email;
    @NotNull(message = "待修改的状态不能为空")
    @Max(value = 2, message = "状态最大不超过2")
    @Min(value = 0, message = "状态最小不低于0")
    private Integer state;
}
