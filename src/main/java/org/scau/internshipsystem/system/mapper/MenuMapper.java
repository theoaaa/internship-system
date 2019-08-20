package org.scau.internshipsystem.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.scau.internshipsystem.system.entity.Menu;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author guest
 * @since 2019-08-16
 */
public interface MenuMapper extends BaseMapper<Menu> {
    @Select("select menu_id from role_menu_ref where role_id in (select role_id from user_role_ref where user_id = #{userId})")
    public List<Integer> findMenuIdsByUserId(int userId);
}
