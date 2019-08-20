package org.scau.internshipsystem.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.scau.internshipsystem.common.domain.JsonResult;
import org.scau.internshipsystem.system.entity.Menu;
import org.scau.internshipsystem.system.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author guest
 * @since 2019-08-16
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private IMenuService menuServiceImpl;

     
    @PostMapping("")
    public JsonResult createMenu(@Validated @RequestBody Menu menu) {
        if(menuServiceImpl.count(new LambdaQueryWrapper<Menu>()
                .eq(Menu::getPid, menu.getPid())
                .eq(Menu::getMenuName, menu.getMenuName()))>0)
            return JsonResult.failure("添加失败，菜单已存在");

        if(menu.getPid()!=-1){
            Menu parent = menuServiceImpl.getOne(new LambdaQueryWrapper<Menu>()
                .select(Menu::getType)
                .eq(Menu::getId, menu.getPid()));
            if(parent.getType()==1)
                return JsonResult.failure("添加失败，父菜单类型不能是按钮！");
        }

        if (menuServiceImpl.save(menu)) {
            return JsonResult.success("添加成功");
        }
        return JsonResult.failure("添加失败");
    }

     
    @DeleteMapping("/{id}")
    public JsonResult removeMenu(@PathVariable int id) {
        if (menuServiceImpl.removeMenuByMenuId(id)) {
            return JsonResult.success("删除成功");
        }
        return JsonResult.failure("删除失败");
    }

     
    @GetMapping("")
    public JsonResult getMenuList() {
        return JsonResult.success(menuServiceImpl.getMenutree());
    }

     
    @GetMapping("/{id}")
    public JsonResult getMenuByMenuId(@PathVariable int id) {
        Menu menu = menuServiceImpl.getById(id);
        return JsonResult.success(menu);
    }
    
     
    @PutMapping("")
    public JsonResult updateMenuMessage(@Validated @RequestBody Menu menu){
        if(menuServiceImpl.count(new LambdaQueryWrapper<Menu>()
            .eq(Menu::getPid, menu.getPid())
            .eq(Menu::getMenuName, menu.getMenuName())
            .ne(Menu::getId, menu.getId()))>0)
            return JsonResult.failure("更新失败，菜单已存在");

        if(menu.getPid()!=-1){
            Menu parent = menuServiceImpl.getOne(new LambdaQueryWrapper<Menu>()
                    .select(Menu::getType)
                    .eq(Menu::getId, menu.getPid()));
            if(parent.getType()==1)
                return JsonResult.failure("修改失败，父菜单类型不能是按钮！");
        }

        if(menuServiceImpl.update(menu, new LambdaQueryWrapper<Menu>().eq(Menu::getId, menu.getId()))){
            return JsonResult.success("更新成功");
        }
        return JsonResult.failure("更新失败");
    }
}
