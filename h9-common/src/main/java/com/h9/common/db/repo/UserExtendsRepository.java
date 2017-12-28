package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.user.UserExtends;

/**
 * Created by itservice on 2017/10/31.
 */
public interface UserExtendsRepository extends BaseRepository<UserExtends> {
    UserExtends findByUserId(Long userId);
}
