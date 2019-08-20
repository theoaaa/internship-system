package org.scau.internshipsystem.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.scau.internshipsystem.system.entity.Internship;
import org.scau.internshipsystem.system.entity.User;
import org.scau.internshipsystem.system.mapper.InternshipMapper;
import org.scau.internshipsystem.system.service.IInternshipService;
import org.scau.internshipsystem.system.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author guest
 * @since 2019-08-16
 */
@Service
@Slf4j
public class InternshipServiceImpl extends ServiceImpl<InternshipMapper, Internship> implements IInternshipService {
    @Resource
    private IUserService userService;
    @Resource
    private InternshipMapper internshipMapper;
    private long tot = 0;
    private long cnt = 0;
    /**
     * 为用户添加实习记录
     * @param userId
     * @return
     */

    @Transactional
    public  boolean chooseInternship(Integer userId,Integer internshipId){
        //确认是否有资格选课
        //User  user = userService.getById(userId);
        User user = userService.getUserForUpdate(userId);
        if(user == null || user.getInternshipRef() != -1){
            log.info("用户id:{} 没有资格进行选择方向操作...",userId);
            return false;
        }
        //确认是否还有余量
        //Internship internship = getById(internshipId);
        Internship internship = internshipMapper.getInternshipForUpdate(internshipId);
        if(internship == null || internship.getQuota() <= internship.getNumber()){
            log.info("用户所选方向已满，无法选课...");
            return false;
        }
        //选课
        update(new UpdateWrapper<Internship>().lambda()
                                .set(Internship::getNumber,internship.getNumber()+1)
                                .eq(Internship::getId,internship.getId()));
        //更新用户记录
        user.setInternshipRef(internshipId);
        userService.updateById(user);
        return true;
    }

//    @RabbitListener(queues = "scau.internship")
//    public void rabbitListen(HashMap<String,Integer> map){
//        long temp = System.currentTimeMillis();
//        chooseInternship(map.get("userId"),map.get("internshipId"));
//        tot += System.currentTimeMillis() - temp;
//        System.out.println(tot/++cnt);
//    }
    /**
     * 取消用户已选方向
     * @param userId
     * @param internshipId
     * @return
     */
    @Transactional
    public boolean cancelInternship(Integer userId,Integer internshipId){
        User user = userService.getById(userId);
        if(user == null || user.getInternshipRef() != internshipId){
            log.error("退选错误，用户不存在或用户没有选该课程...");
            return false;
        }
        Internship internship = getById(internshipId);
        if(internship == null){
            log.error("用户所退选课程不存在...");
            return false;
        }
        //更新实习方向数量
        update(
                new UpdateWrapper<Internship>().lambda()
                .set(Internship::getNumber, internship.getNumber() - 1)
                .eq(Internship::getId, internship.getId())
        );
        //更新个人实习方向信息
        user.setInternshipRef(-1);
        userService.updateById(user);
        return true;
    }
}
