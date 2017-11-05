package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.UserBank;

import java.util.List;

/**
 * Created by itservice on 2017/11/4.
 */
public interface UserBankRepository extends BaseRepository<UserBank> {

    List<UserBank> findByUserId(Long userId);
}
