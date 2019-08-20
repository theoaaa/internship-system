package org.scau.internshipsystem.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.scau.internshipsystem.common.domain.JsonResult;
import org.scau.internshipsystem.common.domain.PageQueryRequest;
import org.scau.internshipsystem.common.util.QueryRequestUtil;
import org.scau.internshipsystem.system.entity.Role;
import org.scau.internshipsystem.system.entity.RoleMenuRef;
import org.scau.internshipsystem.system.service.impl.MenuServiceImpl;
import org.scau.internshipsystem.system.service.impl.RoleMenuRefServiceImpl;
import org.scau.internshipsystem.system.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author guest
 * @since 2019-08-16
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleServiceImpl roleService;
    @Autowired
    private MenuServiceImpl menuService;
    @Autowired
    private RoleMenuRefServiceImpl roleMenuRefService;

    @ResponseBody
    @GetMapping("")
    public JsonResult getRoleList(PageQueryRequest pageQueryRequest, String roleName) {
        Page<Role> page = new Page<>();
        QueryRequestUtil.setPageQueryRequest(page, pageQueryRequest);
        IPage<Role> roleList = roleName == null ?
                roleService.page(page) :
                roleService.page(page, new LambdaQueryWrapper<Role>().like(Role::getRoleName, roleName));
        return JsonResult.success(roleList);
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public JsonResult removeRole(@PathVariable Integer id) {
        if (roleService.removeById(id))
            return JsonResult.success("删除成功");
        else
            return JsonResult.failure("删除失败");
    }

    @ResponseBody
    @PostMapping("")
    public JsonResult addRole(@Validated @RequestBody Role role) {
        if (roleService.count(new LambdaQueryWrapper<Role>()
                .eq(Role::getRoleName, role.getRoleName())) > 0)
            return JsonResult.failure("添加失败,该角色已存在");
        if (roleService.save(role)) {
            return JsonResult.success("添加成功");
        }
        return JsonResult.failure("添加失败");
    }

    @ResponseBody
    @GetMapping("/{id}")
    public JsonResult getRoleByRoleId(@PathVariable int id) {
        Role role = roleService.getById(id);
        if (role != null) {
            return JsonResult.success(role);
        } else
            return JsonResult.failure("角色不存在");
    }

    @ResponseBody
    @PutMapping("")
    public JsonResult updateRoleMessage(@Validated @RequestBody Role role) {
        if(roleService.count(new LambdaQueryWrapper<Role>()
            .eq(Role::getRoleName, role.getRoleName())
            .ne(Role::getId, role.getId()))>0)
            return JsonResult.failure("修改失败，角色名已存在");
        boolean res = roleService.update(role, new LambdaQueryWrapper<Role>().eq(Role::getId, role.getId()));
        if (res)
            return JsonResult.success("修改成功");
        else
            return JsonResult.failure("修改失败");
    }

    @ResponseBody
    @PutMapping("/perm/{roleId}")
    public JsonResult updatePermOfRole(@RequestBody ArrayList<Integer> idList, @PathVariable int roleId) {

        roleMenuRefService.remove(new LambdaQueryWrapper<RoleMenuRef>()
                .eq(RoleMenuRef::getRoleId, roleId));
        List<RoleMenuRef> refs = new ArrayList<>();
        idList.forEach(menuId -> {
            RoleMenuRef ref = new RoleMenuRef();
            ref.setRoleId(roleId);
            ref.setMenuId(menuId);
            refs.add(ref);
        });
        if (roleMenuRefService.saveBatch(refs))
            return JsonResult.success("修改成功");
        return JsonResult.failure("修改失败");
    }

    @ResponseBody
    @GetMapping("/perm/{id}")
    public JsonResult getMenuByRoleId(@PathVariable int id){
        return JsonResult.success(menuService.getMenuTreeByRoleId(id));
    }
}
