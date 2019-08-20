package org.scau.internshipsystem.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.scau.internshipsystem.system.entity.User;
import org.scau.internshipsystem.system.param.UserParam;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author guest
 * @since 2019-08-16
 */
public interface UserMapper extends BaseMapper<User> {
    Page<User> getUserInternShipInfoList(Page page, @Param("userParam") UserParam userParam);
    Page<User> getUserOfferList(Page page, @Param("userParam") UserParam userParam);
    User getUserForUpdate(Integer id);
}
