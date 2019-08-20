package org.scau.internshipsystem.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.scau.internshipsystem.system.entity.Mail;
import org.scau.internshipsystem.system.entity.Message;
import org.scau.internshipsystem.system.entity.Offer;
import org.scau.internshipsystem.system.entity.User;
import org.scau.internshipsystem.system.mapper.MessageMapper;
import org.scau.internshipsystem.system.mapper.OfferMapper;
import org.scau.internshipsystem.system.mapper.UserMapper;
import org.scau.internshipsystem.system.param.ReviewParam;
import org.scau.internshipsystem.system.service.IMessageService;
import org.scau.internshipsystem.system.service.IOfferService;
import org.scau.internshipsystem.system.service.IUserService;
import org.scau.internshipsystem.system.service.MailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
public class OfferServiceImpl extends ServiceImpl<OfferMapper, Offer> implements IOfferService {
    @Resource
    private OfferMapper offerMapper;
    @Resource
    private MailService mailService;
    @Resource
    private IMessageService messageService;
    @Resource
    private IUserService userService;
    /**
     * 改变offer审核状态
     * 0： 回撤为待审核状态
     * 1： 审核通过
     * 2： 审核不通过
     * @param reviewParam
     */
    public void reviewOffer(ReviewParam reviewParam){
        update(new UpdateWrapper<Offer>().lambda().set(Offer::getState,reviewParam.getState())
                .eq(Offer::getId, reviewParam.getState()));
        //发送系统消息通知
        messageService.save(
                Message.builder()
                .content("您的offer登记证书审核状态：" + reviewParam.getState())
                .date(new Date())
                .userId(reviewParam.getUserId())
                .build()
        );
        log.info("保存审核结果系统消息成功...");
        //审核不通过则发送邮件通知
        if(StringUtils.isNotEmpty(reviewParam.getEmail()) && reviewParam.getState() == 2){
            boolean res = mailService.sendPlainMessage(Mail
                    .builder()
                    .to(reviewParam.getEmail())
                    .subject("毕业实习offer登记审核结果")
                    .content("您在本系统登记的offer信息未审核通过，请登录系统查看原因并重新修改提交！")
                    .build());
            if(res){
                log.info("审核未通过邮件发送成功...");
            }
            else{
                log.info("审核未通过邮件发送失败...");
            }
        }
    }

    /***
     * 批量通过offer
     * @param list
     */
    public void batchReviewOffer(List<ReviewParam> list){
        List<Integer> offerIdList = Lists.newArrayList();
        list.forEach(r -> offerIdList.add(r.getOfferId()));
        update(new UpdateWrapper<Offer>().lambda().set(Offer::getState, 1)
                .in(Offer::getId,offerIdList));
    }

    public boolean addOffer(Offer offer){
        //检查用户是否存在以及是否有offer记录
        User user = userService.getById(offer.getUserId());
        if(user == null || user.getOfferRef() != -1 || user == null){
            return false;
        }
        //添加记录
        offer.setState(0);
        offerMapper.insert(offer);
        if(offer.getId() != null){
            userService.update(new UpdateWrapper<User>().lambda().eq(User::getId,offer.getUserId()).set(User::getOfferRef,offer.getId()));
        }
        return true;
    }

    public boolean updateOffer(Offer offer){
        //检查Offer是否处于审核未通过状态
        Offer oldOffer = offerMapper.selectById(offer.getId());
        User user = userService.getById(offer.getUserId());
        if(oldOffer == null || oldOffer.getState() != 2){
            return false;
        }
        if(user == null || user.getOfferRef() != offer.getId()){
            return false;
        }
        offer.setState(0);
        offerMapper.updateById(offer);
        return true;
    }
}
