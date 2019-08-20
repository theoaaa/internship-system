package org.scau.internshipsystem.system.controller;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.scau.internshipsystem.common.domain.JsonResult;
import org.scau.internshipsystem.system.entity.Internship;
import org.scau.internshipsystem.system.service.IInternshipService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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

    @GetMapping("")
    public JsonResult getInternship(@RequestParam(value = "page",required = true) Integer page,
                                    @RequestParam(value = "pageSize",required = true) Integer pageSize,
                                    @RequestParam(value = "company",required = false) String company,
                                    @RequestParam(value = "job",required = false) String job,
                                    @RequestParam(value = "number",required = false) Integer number,
                                    @RequestParam(value = "state",required = false) Integer state){
        Page<Internship> pageInternship = new Page<>(page,pageSize);
        IPage<Internship> iPage = internshipService.getInternship(pageInternship,company,job,number,state);
        return JsonResult.success(iPage);
    }

    //获取单条实习信息
    @GetMapping("/{id}")
    public JsonResult getById(@PathVariable(name = "id") Integer id){
        Internship internship = internshipService.getById(id);
        return JsonResult.success(internship);
    }

    @DeleteMapping("/{id}")
    public JsonResult deleteById(@PathVariable(name = "id") Integer id){
        internshipService.deleteById(id);
        return JsonResult.success("删除成功");
    }

    @PostMapping("")
    public JsonResult addRole(@Validated Internship internship){
        internship.setNumber(0);
        internship.setIsDelete(0);
        internship.setIsPublish(0);
        System.out.println(internship);
        internshipService.save(internship);
        return JsonResult.success("添加成功");
    }

    @PutMapping("")
    public JsonResult update(Internship internship){
        internshipService.updateById(internship);
        return JsonResult.success("修改成功");
    }

    //自定义绑定请求参数的Date类型
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor editor = new CustomDateEditor(df, true);
        binder.registerCustomEditor(Date.class, editor);
    }
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
