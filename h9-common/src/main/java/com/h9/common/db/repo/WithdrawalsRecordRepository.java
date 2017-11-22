package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.WithdrawalsRecord;
import com.h9.common.modle.vo.WithdrawRecordVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * Created by itservice on 2017/11/5.
 */
public interface WithdrawalsRecordRepository extends BaseRepository<WithdrawalsRecord> {


    List<WithdrawalsRecord> findByStatusIn(Collection<Integer> statusList);

    List<WithdrawalsRecord> findByStatus(Integer status);

    @Query("select sum(w.money) from WithdrawalsRecord w where w.status=?1")
    BigDecimal getWithdrawalsCount(int status);

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select o from WithdrawalsRecord o where o.id = ?1")
    WithdrawalsRecord findByLockId(long id);

    @Query("select new com.h9.common.modle.vo.WithdrawRecordVO(w) from WithdrawalsRecord  w,User u where w.userId=u.id and u.id=?1 order by w.id")
    Page<WithdrawRecordVO> findByUserId(long userId, Pageable pageable);


    /**
     * description: 查询指定用户当天提现的金额
     */
    @Query(value = "select sum(money) from withdrawals_record where to_days(withdrawals_record.create_time) = TO_DAYS(NOW()) and user_id = ?1 and status = 3",nativeQuery = true)
    Object findByTodayWithdrawMoney(Long userId);

    /**
     * description: 调用 findByTodayWithdrawMoney 方法查询当天提现的金额
     *
     * @return 返回BigDecimal的类型
     */
    default BigDecimal todayWithdrawMoney(Long userId){
        
        Object money = findByTodayWithdrawMoney(userId);
        if(money == null) return new BigDecimal(0);
        return new BigDecimal(String.valueOf(money));
    }
}
