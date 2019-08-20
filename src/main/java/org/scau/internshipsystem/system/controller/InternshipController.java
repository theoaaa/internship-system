package org.scau.internshipsystem.system.controller;



import org.scau.internshipsystem.common.domain.JsonResult;
import org.scau.internshipsystem.system.service.IInternshipService;
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
@RequestMapping("/internship")
public class InternshipController{
    @Resource
    private IInternshipService internshipService;
    /**
     * 选择实习方向
     * @param userId
     * @param internshipId
     * @return
     */
    @GetMapping("/user/1")
    public JsonResult chooseInternship(@RequestParam Integer userId, @RequestParam Integer internshipId){
        boolean res = internshipService.chooseInternship(userId, internshipId);
//        HashMap<String,Integer> map = new HashMap<>();
//        map.put("userId",userId);
//        map.put("internshipId",internshipId);
//        RabbitMQUtil.sendObject(map);
        if(!res){
            return JsonResult.failure("操作失败");
        }
        return JsonResult.success("选择成功");
    }

    /**
     * 退选
     * @param userId
     * @param internshipId
     * @return
     */
    @GetMapping("/user/2")
    public JsonResult deleteInternship(@RequestParam Integer userId, @RequestParam Integer internshipId){
        boolean res = internshipService.cancelInternship(userId,internshipId);
        if(!res){
            return JsonResult.failure("操作失败");
        }
        return JsonResult.success("退选成功");
    }
}
