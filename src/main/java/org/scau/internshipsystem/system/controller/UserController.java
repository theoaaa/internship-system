package org.scau.internshipsystem.system.controller;



import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.scau.internshipsystem.common.domain.JsonResult;
import org.scau.internshipsystem.common.domain.PageQueryRequest;
import org.scau.internshipsystem.common.domain.PageVo;
import org.scau.internshipsystem.system.entity.Role;
import org.scau.internshipsystem.system.entity.User;
import org.scau.internshipsystem.system.entity.UserRoleRef;
import org.scau.internshipsystem.system.param.UserParam;
import org.scau.internshipsystem.system.service.IOfferService;
import org.scau.internshipsystem.system.service.IUserService;
import org.scau.internshipsystem.system.service.impl.MenuServiceImpl;
import org.scau.internshipsystem.system.service.impl.UserRoleRefServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author guest
 * @since 2019-08-16
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService userService;
    @Resource
    private IOfferService offerService;

    @Autowired
    MenuServiceImpl menuService;

    @Autowired
    UserRoleRefServiceImpl userRoleRefService;

    @ResponseBody
    @GetMapping("")
    @RequiresPermissions("system111")
    public JsonResult findUserMessage(PageQueryRequest pageQueryRequest, User user, Integer roleId){
        if(roleId != null){
            Role role = new Role();
            role.setId(roleId);
            List<Role> list = new ArrayList<>();
            list.add(role);
            user.setRoleList(list);
        }
        return JsonResult.success(userService.findUserMessage(pageQueryRequest, user));
    }

    @ResponseBody
    @GetMapping("/{id}")
    @RequiresPermissions("system")
    public JsonResult findUserMessageByUserId(@PathVariable int id){
        return JsonResult.success(userService.findUserMessageByUserId(id));
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public JsonResult removeUserByUserId(@PathVariable int id){
        if(userService.removeById(id)){
            userRoleRefService.removeUserRoleRefMessageBuUserId(id);
            return JsonResult.success("删除成功");
        }else
            return JsonResult.failure("删除失败，发生未知异常");
    }

    @ResponseBody
    @PutMapping("")
    public JsonResult updateUserMessage(@Validated @RequestBody User user, HttpServletRequest req){
        String roleIds = req.getParameter("roleIdList");
        if(roleIds == null || roleIds == "") return JsonResult.failure("更新失败，角色列表不能为空");

        if(userService.count(new LambdaQueryWrapper<User>()
                .eq(User::getAccount, user.getAccount())
                .ne(User::getId, user.getId()))>0)
            return JsonResult.failure("更新失败，用户已存在");

        if(userService.update(user, new LambdaUpdateWrapper<User>().eq(User::getId, user.getId()))) {
            userRoleRefService.remove(new LambdaQueryWrapper<UserRoleRef>().eq(UserRoleRef::getUserId, user.getId()));
            List<Integer> roleIdList = JSON.parseObject(roleIds, List.class);
            List<UserRoleRef> userRoleRefList = new ArrayList<>();
            roleIdList.forEach(roleId->{
                UserRoleRef userRoleRef = new UserRoleRef();
                userRoleRef.setRoleId(roleId);
                userRoleRef.setUserId(user.getId());
                userRoleRefList.add(userRoleRef);
            });
            userRoleRefService.saveBatch(userRoleRefList);
            return JsonResult.success("更新成功");
        }
        return JsonResult.failure("更新失败");
    }

    @ResponseBody
    @PostMapping("")
    public JsonResult createUser(@Validated @RequestBody User user, HttpServletRequest req){
        String roleIds = req.getParameter("roleIdList");
        if(roleIds==null || roleIds == ""){
            return JsonResult.failure("添加失败，角色列表不能为空");
        }
        if(userService.count(
                new LambdaQueryWrapper<User>()
                        .eq(User::getAccount, user.getAccount()))>0)
            return JsonResult.failure("添加失败,用户已存在");
        user.setRegisterTime(new Date(System.currentTimeMillis()));
        user.setLastLoginTime(new Date(System.currentTimeMillis()));
        userService.save(user);
        List<Integer> roleIdList = JSON.parseObject(roleIds, List.class);
        List<UserRoleRef> userRoleRefList = new ArrayList<>();
        roleIdList.forEach(roleId->{
            UserRoleRef userRoleRef = new UserRoleRef();
            userRoleRef.setRoleId(roleId);
            userRoleRef.setUserId(user.getId());
            userRoleRefList.add(userRoleRef);
        });
        userRoleRefService.saveBatch(userRoleRefList);
        return JsonResult.success("添加成功");
    }

    @ResponseBody
    @PostMapping("/batch/del")
    public JsonResult removeBatchUser(@RequestBody String idList){
        List<Integer> userIdList = JSON.parseObject(idList, List.class);
        if(userService.removeByIds(userIdList)){
            if(userRoleRefService.remove(new LambdaQueryWrapper<UserRoleRef>()
                    .in(UserRoleRef::getUserId, userIdList)))
                return JsonResult.success("删除成功");
            else
                return JsonResult.success("删除失败");

        }
        return JsonResult.success("删除失败");
    }

    @ResponseBody
    @GetMapping("/perm/{id}")
    public JsonResult findPermMessageOfUser(@PathVariable int id){
        return JsonResult.success(menuService.findMenuTreeByUserId(id));
    }

    @ResponseBody
    @GetMapping("/login")
    public JsonResult login(){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("admin", "123456");
        try {
            subject.login(token);
            return JsonResult.success("登录成功");
        }catch (Exception e){
            return JsonResult.failure("登录失败");
        }
    }
    
    @GetMapping("/internship")
    public JsonResult getUserInternshipDetail(PageVo pageVo, UserParam userParam){
        Page<User> page = userService.userInternshipDetail(
                new Page<User>(pageVo.getPage(),pageVo.getPageSize()), userParam);
        return JsonResult.success(page);
    }

    @GetMapping("/offer")
    public JsonResult getUserOfferDetail(PageVo pageVo, UserParam userParam){
        Page<User> page = userService.userOfferDetail(
                new Page<User>(pageVo.getPage(),pageVo.getPageSize()), userParam);
        return JsonResult.success(page);
    }

    @GetMapping("/offer/{id}")
    public JsonResult getUserPersonOffer(@PathVariable("id") Integer id){
        return JsonResult.success(offerService.getById(id));
    }
}
