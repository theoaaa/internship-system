package org.scau.internshipsystem.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.scau.internshipsystem.system.entity.User;
import org.scau.internshipsystem.system.param.UserParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author guest
 * @since 2019-08-16
 */
public interface IUserService extends IService<User> {
    Page<User> userInternshipDetail(Page page, UserParam userParam);
    Page<User> userOfferDetail(Page page, UserParam userParam);

    User getUserForUpdate(Integer userId);
}
