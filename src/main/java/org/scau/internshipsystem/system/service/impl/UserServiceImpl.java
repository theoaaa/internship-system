package org.scau.internshipsystem.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.scau.internshipsystem.common.domain.PageQueryRequest;
import org.scau.internshipsystem.common.domain.PageVo;
import org.scau.internshipsystem.common.util.QueryRequestUtil;
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
    public IPage<User> findUserMessage(PageQueryRequest pageQueryRequest, User user) {
        Page<User> page = new Page<>();
        QueryRequestUtil.setPageQueryRequest(page, pageQueryRequest);
        return this.baseMapper.findUserMessage(page, user);
    }

    @Override
    public User findUserMessageByUserId(int id) {
        return this.baseMapper.findUserMessageByUserId(id);
    }

    @Override
    public User getUserByUsername(String username) {
        User user = this.baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        return user;
    }

    @Override
    public List<String> getMenuCodeByUserId(int userId) {
        return this.baseMapper.getMenuCodeByUserId(userId);
    }

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
