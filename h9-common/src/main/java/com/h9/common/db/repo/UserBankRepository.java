package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.UserBank;

/**
 * Created by itservice on 2017/11/4.
 */
public interface UserBankRepository extends BaseRepository<UserBank> {

    UserBank findByUserId(Long userId);
}
