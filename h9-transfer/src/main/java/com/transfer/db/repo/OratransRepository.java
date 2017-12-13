package com.transfer.db.repo;


import com.transfer.db.BasicRepository;
import com.transfer.db.entity.Oratrans;
import com.transfer.db.model.OratransWrap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: OratransRepository
 * @Description: Oratrans 的查询
 * @author: shadow.liu
 * @date: 2016年6月27日 下午3:18:36
 */
@Repository
public interface OratransRepository extends BasicRepository<Oratrans> {

    @Query(value = "select new com.transfer.db.model.OratransWrap(o1,o2) from Oratrans o1,BounsDetails o2 where o2.oratransOId=o1.oratransOId and o1.oratransOId = ?1")
    OratransWrap findByOId(String oid);

    default Page<OratransWrap> findPageQuery(int page,int size){
        return findOratransWrap(new PageRequest(page, size));
    }

    @Query(value = "select new com.transfer.db.model.OratransWrap(o1,o2) from Oratrans o1,BounsDetails o2 where o2.oratransOId=o1.oratransOId")
    Page<OratransWrap> findOratransWrap(Pageable pageable);
}
