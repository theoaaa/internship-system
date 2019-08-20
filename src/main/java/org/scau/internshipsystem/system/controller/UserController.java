package org.scau.internshipsystem.system.controller;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.scau.internshipsystem.common.domain.JsonResult;
import org.scau.internshipsystem.common.domain.PageVo;
import org.scau.internshipsystem.system.entity.User;
import org.scau.internshipsystem.system.param.UserParam;
import org.scau.internshipsystem.system.service.IOfferService;
import org.scau.internshipsystem.system.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
