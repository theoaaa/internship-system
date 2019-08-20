package org.scau.internshipsystem.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.scau.internshipsystem.system.entity.Offer;
import org.scau.internshipsystem.system.param.ReviewParam;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author guest
 * @since 2019-08-16
 */
public interface IOfferService extends IService<Offer> {

    void reviewOffer(ReviewParam reviewParam);

    void batchReviewOffer(List<ReviewParam> list);

    boolean addOffer(Offer offer);

    boolean updateOffer(Offer offer);
}
