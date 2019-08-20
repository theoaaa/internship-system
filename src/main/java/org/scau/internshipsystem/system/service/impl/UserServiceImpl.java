package org.scau.internshipsystem.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.scau.internshipsystem.common.domain.PageVo;
import org.scau.internshipsystem.system.entity.User;
import org.scau.internshipsystem.system.mapper.UserMapper;
import org.scau.internshipsystem.system.param.UserParam;
import org.scau.internshipsystem.system.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public Page<User> userInternshipDetail(Page page, UserParam userParam) {
        return userMapper.getUserInternShipInfoList(page,userParam);
    }

    @Override
    public Page<User> userOfferDetail(Page page, UserParam userParam) {
        return userMapper.getUserOfferList(page,userParam);
    }

    @Override
    public User getUserForUpdate(Integer userId) {
        return null;
    }

}
