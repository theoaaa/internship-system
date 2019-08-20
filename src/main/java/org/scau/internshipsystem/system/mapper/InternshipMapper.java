package org.scau.internshipsystem.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.scau.internshipsystem.system.entity.Internship;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author guest
 * @since 2019-08-16
 */
public interface InternshipMapper extends BaseMapper<Internship> {
    Internship getInternshipForUpdate(Integer id);
}
