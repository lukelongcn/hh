package com.transfer.db.repo;


import com.transfer.db.BasicRepository;
import com.transfer.db.entity.Bouns;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: BounsRepository
 * @Description: Bouns 的查询
 * @author: shadow.liu
 * @date: 2016年6月27日 下午3:18:36
 */
@Repository
public interface BounsRepository extends BasicRepository<Bouns> {
    @Query("select b from Bouns b where b.BounsOID=?1")
    Bouns findOne(String oid);
}
