package com.transfer.db.repo;

import com.h9.common.base.PageResult;
import com.transfer.db.BasicRepository;
import com.transfer.db.entity.BounsDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: BounsDetailsRepository
 * @Description: BounsDetails 的查询
 * @author: shadow.liu
 * @date: 2016年6月27日 下午3:18:36
 */
@Repository
public interface BounsDetailsRepository extends BasicRepository<BounsDetails> {

    @Query("select bd from BounsDetails  bd order by bd.createTime ASC ")
     Page findByCreateTime(Pageable pageable);

    default PageResult findAll(int page,int limit){
        return new PageResult(findByCreateTime(pageRequest(page, limit)));
    }

    @Query("select b from BounsDetails  b where b.oratransOId = ?1 and b.bounsType = 1")
    BounsDetails findByOAndBounsOID(String id);

}
