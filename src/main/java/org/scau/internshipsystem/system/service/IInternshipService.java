package org.scau.internshipsystem.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.scau.internshipsystem.system.entity.Internship;

import java.util.HashMap;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author guest
 * @since 2019-08-16
 */
public interface IInternshipService extends IService<Internship> {
    boolean chooseInternship(Integer userId,Integer internshipId);
    boolean cancelInternship(Integer userId,Integer internshipId);
}
