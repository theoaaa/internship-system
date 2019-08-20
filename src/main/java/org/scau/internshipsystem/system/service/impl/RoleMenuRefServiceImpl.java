package org.scau.internshipsystem.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.scau.internshipsystem.system.entity.RoleMenuRef;
import org.scau.internshipsystem.system.mapper.RoleMenuRefMapper;
import org.scau.internshipsystem.system.service.IRoleMenuRefService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author guest
 * @since 2019-08-16
 */
@Service
public class RoleMenuRefServiceImpl extends ServiceImpl<RoleMenuRefMapper, RoleMenuRef> implements IRoleMenuRefService {
    public List<Integer> getMenuIdByRoleId(int roleId){
        return this.baseMapper.getMenuIdByRoleId(roleId);
    }
}
