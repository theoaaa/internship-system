package org.scau.internshipsystem.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.scau.internshipsystem.system.entity.Message;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author guest
 * @since 2019-08-16
 */
public interface IMessageService extends IService<Message> {
    List<Message> getMessageById(Integer userId);
}
