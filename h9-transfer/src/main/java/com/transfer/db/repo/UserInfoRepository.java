package com.transfer.db.repo;


import com.h9.common.base.PageResult;
import com.transfer.db.BasicRepository;
import com.transfer.db.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    default PageResult<UserInfo> findAllFromStart(int page, int limit, Sort sort,Long start) {
        Page<UserInfo> pageBean = findByIdGreaterThan(start,pageRequest(page, limit, sort));
        PageResult PageResult = new PageResult(pageBean);
        return PageResult;
    }

    Page<UserInfo> findByIdGreaterThan(Long start,Pageable pageable);

}
