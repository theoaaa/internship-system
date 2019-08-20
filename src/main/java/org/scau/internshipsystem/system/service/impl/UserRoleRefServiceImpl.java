package org.scau.internshipsystem.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.scau.internshipsystem.system.entity.UserRoleRef;
import org.scau.internshipsystem.system.mapper.UserRoleRefMapper;
import org.scau.internshipsystem.system.service.IUserRoleRefService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author guest
 * @since 2019-08-16
 */
@Service
public class UserRoleRefServiceImpl extends ServiceImpl<UserRoleRefMapper, UserRoleRef> implements IUserRoleRefService {
    @Override
    public void removeUserRoleRefMessageBuUserId(int userId) {
        baseMapper.delete(new LambdaQueryWrapper<UserRoleRef>().eq(UserRoleRef::getUserId, userId));
    }
}
