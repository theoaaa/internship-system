package org.scau.internshipsystem.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.scau.internshipsystem.system.entity.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author guest
 * @since 2019-08-16
 */
public interface IMenuService extends IService<Menu> {
    public List<Menu> getMenutree();
    public boolean removeMenuByMenuId(int id);
    public List<Menu> getMenuTreeByRoleId(int roleId);
    public List<Menu> findMenuTreeByUserId(int userId);
}
