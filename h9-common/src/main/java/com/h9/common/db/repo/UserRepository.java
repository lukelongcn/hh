package com.h9.common.db.repo;


import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: UserRepository
 * @Description: User 的查询
 * @author: shadow.liu
 * @date: 2016年6月27日 下午3:18:36
 */
@Repository
public interface UserRepository extends BaseRepository<User> {

    List<User> findByPhone(String phone);
}
