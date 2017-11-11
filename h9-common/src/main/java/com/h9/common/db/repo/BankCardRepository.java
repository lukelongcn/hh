package com.h9.common.db.repo;

import com.h9.admin.model.vo.UserBankVO;
import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.UserBank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 李圆
 * on 2017/11/2
 */
@Repository
public interface BankCardRepository extends BaseRepository<UserBank> {

    //通过id找到对应的银行卡
    UserBank findById(Long id);

    //判断有无对应银行卡存在
    UserBank findByNo(String no);

    List<UserBank> findByUserIdAndStatus(Long userId,Integer status);

    @Query(value = "select * from user_bank where status = 1 and default_select = 1 and user_id = ?1",nativeQuery = true)
    UserBank getDefaultBank(Long userId);

    @Query("select new com.h9.admin.model.vo.UserBankVO(u.id, u.userId, u.name, u.no, u.province,u.city," +
            "(select sum(w.money) from WithdrawalsRecord w where w.status=3 and w.userBank.id=u.id)," +
            "(select count(w) from WithdrawalsRecord w where w.status=3 and w.userBank.id=u.id)," +
            "u.createTime,u.updateTime,u.status) from UserBank u " +
            " where u.userId=?1")
    Page<UserBankVO> findVOByUserId(Long userId, Pageable pageable);

}
