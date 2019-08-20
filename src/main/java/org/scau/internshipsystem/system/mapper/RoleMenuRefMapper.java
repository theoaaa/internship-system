package org.scau.internshipsystem.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.scau.internshipsystem.system.entity.RoleMenuRef;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author guest
 * @since 2019-08-16
 */
public interface RoleMenuRefMapper extends BaseMapper<RoleMenuRef> {
    @Select("select menu_id from role_menu_ref where role_id=#{roleId}")
    public List<Integer> getMenuIdByRoleId(int roleId);
}
