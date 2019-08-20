package org.scau.internshipsystem.system.controller;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.scau.internshipsystem.common.domain.JsonResult;
import org.scau.internshipsystem.system.entity.Offer;
import org.scau.internshipsystem.system.param.ReviewParam;
import org.scau.internshipsystem.system.service.IOfferService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@RequestMapping("/offer")
public class OfferController  {
    @Resource
    private IOfferService offerService;

    /**
     * 审核offer
     * @param reviewParam
     */
    @PutMapping("/review")
    public JsonResult reviewOffer(@Validated @RequestBody ReviewParam reviewParam){
        Long before = System.currentTimeMillis();
        offerService.reviewOffer(reviewParam);
        System.out.println(System.currentTimeMillis() - before);
        return JsonResult.success("修改成功");
    }

    /**
     * 批量审核offer通过
     * @param jsonObject
     * @return
     */
    @PutMapping("/review/batch")
    public JsonResult batchReviewOffer(@RequestBody JSONObject jsonObject){
        List<ReviewParam> list = JSON.parseArray(
                JSON.toJSONString(jsonObject.get("list")),ReviewParam.class
        );
        offerService.batchReviewOffer(list);
        return JsonResult.success("批量修改成功");
    }

    /**
     * 根据id获取offer详情
     * @param offerId
     * @return
     */
    @GetMapping("/{id}")
    public JsonResult getOfferById(@PathVariable("id") Integer offerId){
        return JsonResult.success(offerService.getById(offerId));
    }

    /**
     * 对未登记的用户添加offer
     * @param offer
     * @return
     */
    @PostMapping("")
    public JsonResult addOffer(@Validated @RequestBody  Offer offer){
        boolean res = offerService.addOffer(offer);
        if(res == false){
            return JsonResult.failure("添加失败");
        }
        return JsonResult.success("保存记录成功");
    }

    /**
     * 对审核未通过的用户修改offer
     * @param offer
     * @return
     */
    @PutMapping("")
    public JsonResult updateOffer(@Validated @RequestBody Offer offer){
        boolean res = offerService.updateOffer(offer);
        if(res == false){
            return JsonResult.failure("修改失败");
        }
        return JsonResult.success("修改成功");
    }



}
