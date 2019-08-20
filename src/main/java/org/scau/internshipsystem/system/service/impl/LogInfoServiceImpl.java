package org.scau.internshipsystem.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.scau.internshipsystem.system.entity.LogInfo;
import org.scau.internshipsystem.system.mapper.LogInfoMapper;
import org.scau.internshipsystem.system.service.ILogInfoService;
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
public class LogInfoServiceImpl extends ServiceImpl<LogInfoMapper, LogInfo> implements ILogInfoService {

}
