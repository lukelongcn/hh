package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.UserAccount;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.math.BigDecimal;

/**
 * Created by itservice on 2017/10/28.
 */
public interface UserAccountRepository extends BaseRepository<UserAccount> {

    UserAccount findByUserId(Long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("update UserAccount u set u.balance = ?1 where u.userId = ?2")
    void changeBalance(BigDecimal money,Long userId);
}
