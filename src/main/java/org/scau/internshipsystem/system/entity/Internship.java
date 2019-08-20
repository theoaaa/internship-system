package org.scau.internshipsystem.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Internship {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String company;

    private String job;

    private String address;

    private Date beginDate;

    private Date endDate;

    private Integer quota;

    private Integer number;

    private Integer isDelete;

    private Integer isPublish;

    private String filepath;

    private String remark;

}
