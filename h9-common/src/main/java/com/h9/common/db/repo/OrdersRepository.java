package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.base.PageResult;
import com.h9.common.db.entity.lottery.OrdersLotteryActivity;
import com.h9.common.db.entity.order.Orders;
import com.h9.common.modle.dto.transaction.OrderDTO;
import com.h9.common.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by itservice on 2017/11/1.
 */
public interface OrdersRepository extends BaseRepository<Orders> {
    @Query("SELECT o from Orders o where o.user.id=?1 order by o.id desc")
    Page<Orders> findByUser(Long userId, Pageable pageable);

    @Query("SELECT o from Orders o where o.user.id=?1 and o.orderFrom = ?2 order by o.id desc")
    Page<Orders> findByUser(Long userId, Integer orderFrom, Pageable pageable);


    @Query("SELECT o from Orders o where o.user.id=?1 and o.goodsType = ?2 order by o.id desc")
    Page<Orders> findDiDiCardByUser(Long userId, String goodsTypeCode, Pageable pageable);

    default PageResult<Orders> findByUser(Long userId, int page, int limit) {
        Page<Orders> byUser = findByUser(userId, pageRequest(page, limit));
        return new PageResult(byUser);
    }

    @Query("SELECT o from Orders o where o.user.id=?1 and status = ?2 order by o.id desc")
    Page<Orders> findByUser(Long userId, int status, Pageable pageable);

    default PageResult<Orders> findByUser(Long userId, int status, int page, int limit) {
        Page<Orders> byUser = findByUser(userId, status, pageRequest(page, limit));
        return new PageResult(byUser);
    }


    @Async
    @Query("select o from Orders o")
    Future<List<Orders>> findAllAsy();

    default Specification<Orders> buildSpecification(OrderDTO orderDTO) {
        return new Specification<Orders>() {
            @Override
            public Predicate toPredicate(Root<Orders> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                if (orderDTO.getNo() != null) {
                    predicateList.add(criteriaBuilder.equal(root.get("no").as(String.class), orderDTO.getNo()));
                }
                if (StringUtils.isNotBlank(orderDTO.getPhone())) {
                    predicateList.add(criteriaBuilder.equal(root.get("userPhone").as(String.class), orderDTO.getPhone()));
                }
                if (orderDTO.getStartTime() != null) {
                    predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(Date.class), orderDTO.getStartTime()));
                }
                if (orderDTO.getEndTime() != null) {
                    predicateList.add(criteriaBuilder.lessThan(root.get("createTime").as(Date.class), DateUtil.addDays(orderDTO.getEndTime(), 1)));
                }
                if (orderDTO.getStatus() != null && orderDTO.getStatus() != -1) {
                    predicateList.add(criteriaBuilder.equal(root.get("status").as(Integer.class), orderDTO.getStatus()));
                }
                Predicate[] pre = new Predicate[predicateList.size()];
                if (pre != null && pre.length >= 0) {
                    return criteriaQuery.where(predicateList.toArray(pre)).getRestriction();
                } else {
                    return criteriaQuery.getRestriction();
                }
            }
        };
    }


    //    @Query("select sum(o.payMoney) from Orders o where (o.createTime > ?1 and o.createTime < ?2 and ?1 <> null and ?2 <> null) or 1=1")
    @Query(value = "SELECT sum(o.pay_money) FROM orders o ", nativeQuery = true)
    BigDecimal findPayMoneySum();

    @Query(value = "SELECT sum(o.pay_money) FROM orders o WHERE o.create_time > ?1 AND o.create_time < ?2", nativeQuery = true)
    BigDecimal findPayMoneySumAndDate(Date startTime, Date endTime);

    @Query("select sum(o.payMoney) from Orders o where o.payMethond =?1")
    BigDecimal findWXPayMoneySum(Integer payMethod);

    @Query("select sum(o.payMoney) from Orders o where o.payMethond =?1 and o.createTime > ?2 and o.createTime < ?3")
    BigDecimal findWXPayMoneySumAndDate(Integer payMethod, Date startTime, Date endTime);

    @Query("select count(o.id) from Orders o where o.ordersLotteryId =?1")
    Object findByCount(Long id);

    @Query("select o from Orders o where o.ordersLotteryId = ?1")
    Page<Orders> findByordersLotteryId(Long ordersLotteryId, Pageable pageable);

    /**
     * 用戶参与记录
     */
    @Query("select o from Orders o where o.user.id = ?1 and o.ordersLotteryId is not null order by o.createTime ")
    Page<Orders> findByUserId(long userId, Pageable pageable);

    default PageResult<Orders> findByUserId(long userId, Integer page, Integer limit) {
        Page<Orders> list = findByUserId(userId, pageRequest(page, limit));
        return new PageResult<>(list);
    }

    @Modifying
    @Query("update Orders o set o.ordersLotteryId=?4 where o.createTime > ?1 and o.createTime < ?2 " +
            "and o.payStatus = ?3 and o.orderFrom = 2")
    int updateByDateAndStatus(Date start, Date end, Integer status, Long id);

    @Modifying
    @Query("update Orders o set o.ordersLotteryId = null where o.ordersLotteryId=?1")
    @Transactional
    int updateOrdersLotteryId(Long id);


    @Query("select r FROM Orders r where r.createTime > ?1 and r.createTime < ?2 and r.user.id = ?3 and r.status = 1 ")
    List<Orders> findUserfulOrders(Date startTime, Date endTime, long userId);
}
