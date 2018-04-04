package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.user.UserExtends;
import org.springframework.stereotype.Repository;

/**
 * Created by itservice on 2017/10/31.
 */
@Repository
public interface UserExtendsRepository extends BaseRepository<UserExtends> { ;
    UserExtends findByUserId(Long userId);
}
