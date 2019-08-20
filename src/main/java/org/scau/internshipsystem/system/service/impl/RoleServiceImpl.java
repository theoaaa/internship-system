package org.scau.internshipsystem.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.scau.internshipsystem.system.entity.Role;
import org.scau.internshipsystem.system.mapper.RoleMapper;
import org.scau.internshipsystem.system.service.IRoleService;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
