package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.account.CardCoupons;
import com.h9.common.modle.dto.transaction.CardCouponsDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itservice on 2017/11/6.
 */
public interface CardCouponsRepository extends BaseRepository<CardCoupons>{

    /**
     * description: 查询指定类型的滴滴劵库存
     */
    @Query(value = "select count(o.id) from CardCoupons o where o.status = 1 and o.goodsId = ?1 ")
    Object getCount(Long goodId);

    @Query(value = "select * from card_coupons where goods_id = ?1 and status = 1 limit 0,1",nativeQuery = true)
    CardCoupons findByGoodsId(Long goodsId);

    @Query(value = "select gdn from CardCoupons gdn where  gdn.no = ?1")
    CardCoupons findByNo(String number);


    @Query(value = "select * from card_coupons where status = 1 limit 0,1",nativeQuery = true)
    CardCoupons findTopOneUnUse();

    @Query(value = "select o.batchNo from CardCoupons o where o.goodsId = ?1 group by o.batchNo order by o.batchNo desc ")
    List<String> findAllBatchNoByGoodsId(Long goodsId);

    @Query(value = "select o.batch_no from card_coupons o where o.goods_id = ?1 and o.batch_no like ?2% order by o.batch_no desc limit 1",nativeQuery = true)
    String findLastBatchNo(long goodsId,String dayString);

    default Specification<CardCoupons> buildSpecification(CardCouponsDTO cardCouponsDTO) {
        return new Specification<CardCoupons>() {
            @Override
            public Predicate toPredicate(Root<CardCoupons> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                if (cardCouponsDTO.getUserId() != null) {
                    predicateList.add(criteriaBuilder.equal(root.get("userId").as(Long.class),cardCouponsDTO.getUserId()));
                }
                if (StringUtils.isNotBlank(cardCouponsDTO.getNo())) {
                    predicateList.add(criteriaBuilder.equal(root.get("no").as(String.class),cardCouponsDTO.getNo()));
                }
                if (StringUtils.isNotBlank(cardCouponsDTO.getBatchNo())) {
                    predicateList.add(criteriaBuilder.equal(root.get("batchNo").as(String.class),cardCouponsDTO.getBatchNo()));
                }
                predicateList.add(criteriaBuilder.equal(root.get("status").as(Integer.class),cardCouponsDTO.getStatus()));
                predicateList.add(criteriaBuilder.equal(root.get("goodsId").as(Long.class),cardCouponsDTO.getGoodsId()));
                Predicate[] pre = new Predicate[predicateList.size()];
                return criteriaQuery.where(predicateList.toArray(pre)).getRestriction();
            }
        };

    }
}
