package org.scau.internshipsystem.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
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

    IPage<Internship> getInternship(Page page,
                                           @Param("company") String company,
                                           @Param("job") String job,
                                           @Param("number") Integer number,
                                           @Param("state") Integer state);

    @Update("update internship set is_delete=1 where id=#{id}")
    void deleteById(Integer id);
}
