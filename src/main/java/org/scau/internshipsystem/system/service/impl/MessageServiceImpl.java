package org.scau.internshipsystem.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.org.apache.regexp.internal.RE;
import org.scau.internshipsystem.system.entity.Message;
import org.scau.internshipsystem.system.entity.User;
import org.scau.internshipsystem.system.mapper.MessageMapper;
import org.scau.internshipsystem.system.service.IMessageService;
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
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {
    @Resource
    private MessageMapper messageMapper;
    @Override
    public List<Message> getMessageById(Integer userId) {
        return messageMapper.selectList(new QueryWrapper<Message>().lambda().eq(Message::getUserId,userId));
    }
}
