package org.scau.internshipsystem.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.scau.internshipsystem.system.entity.UserRoleRef;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author guest
 * @since 2019-08-16
 */
public interface IUserRoleRefService extends IService<UserRoleRef> {
    public void removeUserRoleRefMessageBuUserId(int userId);
}
