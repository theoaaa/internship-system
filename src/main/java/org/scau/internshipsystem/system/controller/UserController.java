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
import org.scau.internshipsystem.system.param.UserMessageParam;
import org.scau.internshipsystem.system.param.UserParam;
import org.scau.internshipsystem.system.service.IMenuService;
import org.scau.internshipsystem.system.service.IOfferService;
import org.scau.internshipsystem.system.service.IUserRoleRefService;
import org.scau.internshipsystem.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    private IUserService userServiceImpl;
    @Resource
    private IOfferService offerServiceImpl;

    @Autowired
    private IMenuService menuServiceImpl;

    @Autowired
    private IUserRoleRefService userRoleRefServiceImpl;

    
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
        return JsonResult.success(userServiceImpl.findUserMessage(pageQueryRequest, user));
    }

    
    @GetMapping("/{id}")
    @RequiresPermissions("system")
    public JsonResult findUserMessageByUserId(@PathVariable int id){
        return JsonResult.success(userServiceImpl.findUserMessageByUserId(id));
    }

    
    @DeleteMapping("/{id}")
    public JsonResult removeUserByUserId(@PathVariable int id){
        if(userServiceImpl.removeById(id)){
            userRoleRefServiceImpl.remove(new LambdaQueryWrapper<UserRoleRef>().eq(UserRoleRef::getUserId, id));
            return JsonResult.success("删除成功");
        }else
            return JsonResult.failure("删除失败，发生未知异常");
    }


    @PutMapping("")
    public JsonResult updateUserMessage(@Validated @RequestBody UserMessageParam userParam){
        if(userServiceImpl.count(new LambdaQueryWrapper<User>()
                .eq(User::getAccount, userParam.getAccount())
                .ne(User::getId, userParam.getId()))>0)
            return JsonResult.failure("更新失败，用户已存在");

        User user = userParam.transformToUser();
        if(userServiceImpl.update(user, new LambdaUpdateWrapper<User>().eq(User::getId, user.getId()))) {
            userRoleRefServiceImpl.remove(new LambdaQueryWrapper<UserRoleRef>().eq(UserRoleRef::getUserId, user.getId()));
            List<UserRoleRef> userRoleRefList = new ArrayList<>();
            userParam.getRoleIdList().forEach(roleId->{
                UserRoleRef userRoleRef = new UserRoleRef();
                userRoleRef.setRoleId(roleId);
                userRoleRef.setUserId(user.getId());
                userRoleRefList.add(userRoleRef);
            });
            userRoleRefServiceImpl.saveBatch(userRoleRefList);
            return JsonResult.success("更新成功");
        }
        return JsonResult.failure("更新失败");
    }

    
    @PostMapping("")
    public JsonResult createUser(@Validated @RequestBody UserMessageParam addUserParam){
        if(userServiceImpl.count(
                new LambdaQueryWrapper<User>()
                        .eq(User::getAccount, addUserParam.getAccount()))>0)
            return JsonResult.failure("添加失败,用户已存在");

        User user = addUserParam.transformToUser();
        user.setRegisterTime(new Date(System.currentTimeMillis()));
        user.setLastLoginTime(new Date(System.currentTimeMillis()));
        userServiceImpl.save(user);

        List<UserRoleRef> userRoleRefList = new ArrayList<>();
        addUserParam.getRoleIdList().forEach(roleId->{
            UserRoleRef userRoleRef = new UserRoleRef();
            userRoleRef.setRoleId(roleId);
            userRoleRef.setUserId(user.getId());
            userRoleRefList.add(userRoleRef);
        });
        userRoleRefServiceImpl.saveBatch(userRoleRefList);
        return JsonResult.success("添加成功");
    }

    
    @PostMapping("/batch/del")
    public JsonResult removeBatchUser(@RequestBody String idList){
        List<Integer> userIdList = JSON.parseObject(idList, List.class);
        if(userServiceImpl.removeByIds(userIdList)){
            if(userRoleRefServiceImpl.remove(new LambdaQueryWrapper<UserRoleRef>()
                    .in(UserRoleRef::getUserId, userIdList)))
                return JsonResult.success("删除成功");
            else
                return JsonResult.success("删除失败");

        }
        return JsonResult.success("删除失败");
    }

    
    @GetMapping("/perm/{id}")
    public JsonResult findPermMessageOfUser(@PathVariable int id){
        return JsonResult.success(menuServiceImpl.findMenuTreeByUserId(id));
    }

    
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
        Page<User> page = userServiceImpl.userInternshipDetail(
                new Page<User>(pageVo.getPage(),pageVo.getPageSize()), userParam);
        return JsonResult.success(page);
    }

    @GetMapping("/offer")
    public JsonResult getUserOfferDetail(PageVo pageVo, UserParam userParam){
        Page<User> page = userServiceImpl.userOfferDetail(
                new Page<User>(pageVo.getPage(),pageVo.getPageSize()), userParam);
        return JsonResult.success(page);
    }

    @GetMapping("/offer/{id}")
    public JsonResult getUserPersonOffer(@PathVariable("id") Integer id){
        return JsonResult.success(offerServiceImpl.getById(id));
    }
}
