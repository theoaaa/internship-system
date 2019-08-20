package org.scau.internshipsystem.system.controller;



import org.scau.internshipsystem.common.domain.JsonResult;
import org.scau.internshipsystem.system.service.IMessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/message")
public class MessageController {
    @Resource
    private IMessageService messageService;

    @GetMapping("/{id}")
    public JsonResult getMessageById(@PathVariable("id") Integer userId){
        return JsonResult.success(messageService.getMessageById(userId));
    }
}
