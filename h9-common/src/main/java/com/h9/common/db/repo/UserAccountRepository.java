package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.UserAccount;
import com.h9.common.modle.vo.admin.UserAccountVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.math.BigDecimal;

/**
 * Created by itservice on 2017/10/28.
 */
public interface UserAccountRepository extends BaseRepository<UserAccount> {

    UserAccount findByUserId(Long userId);

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    @Query("update UserAccount u set u.balance = ?1 where u.userId = ?2")
//    void changeBalance(BigDecimal money,Long userId);
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select o from UserAccount  o where o.userId = ?1")
    UserAccount findByUserIdLock(Long userId);
    @Query("select sum(u.vCoins) from UserAccount u")
    BigDecimal getUserVCoins();

    @Query("select new com.h9.common.modle.vo.admin.UserAccountVO(u,ua) from User u,UserAccount ua where u.id = ua.userId order by u.id desc ")
    Page<UserAccountVO> findUserAccountVO(Pageable pageable);
}
