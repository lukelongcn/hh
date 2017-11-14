package com.transfer.db.repo;


import com.transfer.db.BasicRepository;
import com.transfer.db.entity.UserInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: UserRepository
 * @Description: UserInfo 的查询
 * @author: shadow.liu
 * @date: 2016年6月27日 下午3:18:36
 */
@Repository
public interface UserInfoRepository extends BasicRepository<UserInfo> {


}
