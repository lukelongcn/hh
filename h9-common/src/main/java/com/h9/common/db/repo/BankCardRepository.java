package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.UserBank;
import org.springframework.stereotype.Repository;

/**
 * Created by 李圆
 * on 2017/11/2
 */
@Repository
public interface BankCardRepository extends BaseRepository<UserBank> {

    //通过id找到对应的银行卡
    UserBank findById(Long id);
}
