package org.scau.internshipsystem.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.scau.internshipsystem.common.util.MenuTreeUtil;
import org.scau.internshipsystem.system.entity.Menu;
import org.scau.internshipsystem.system.mapper.MenuMapper;
import org.scau.internshipsystem.system.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    RoleMenuRefServiceImpl roleMenuRefService;

    @Override
    public List<Menu> getMenutree() {
        List<Menu> menus = this.baseMapper.selectList(Wrappers.emptyWrapper());
        List<Menu> menuTrees = MenuTreeUtil.makeTree(menus);
        return menuTrees;
    }

    @Override
    public boolean removeMenuByMenuId(int id) {
        int delNumber=0;
        if(this.baseMapper.selectCount(new LambdaQueryWrapper<Menu>()
            .eq(Menu::getPid, id))<=0) {
            delNumber = this.baseMapper.deleteById(id);
        }
        return delNumber>0;
    }

    @Override
    public List<Menu> getMenuTreeByRoleId(int roleId) {
        List<Integer> menuIds = roleMenuRefService.getMenuIdByRoleId(roleId);
        List<Menu> menus = null;
        if(menuIds!=null && !menuIds.isEmpty()) {
            menus = this.baseMapper.selectList(new LambdaQueryWrapper<Menu>().in(Menu::getId, menuIds));
        }
        return MenuTreeUtil.makeTree(menus);
    }

    @Override
    public List<Menu> findMenuTreeByUserId(int userId) {
        List<Integer> menuIds = this.baseMapper.findMenuIdsByUserId(userId);
        List<Menu> menus = this.baseMapper.selectList(new LambdaQueryWrapper<Menu>().in(Menu::getId, menuIds));
        return MenuTreeUtil.makeTree(menus);
    }
}
