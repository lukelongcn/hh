package com.transfer.db.repo;


import com.transfer.db.BasicRepository;
import com.transfer.db.entity.Tasting;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: TastingRepository
 * @Description: Tasting 的查询
 * @author: shadow.liu
 * @date: 2016年6月27日 下午3:18:36
 */
@Repository
public interface TastingRepository extends BasicRepository<Tasting> {

//    @Query(value = "select new  com.transfer.db.model.TastingVo(t.code,t.PlanName,t.JPMC,t.BZ,b.CodeId) from Tasting as t LEFT JOIN Bouns b on t.CodeId = b.codemd")
//    Page<TastingVo> findByTasting(Pageable pageable);
//
//    default PageResult<TastingVo> findAllForVo(int page,int limit){
//        Page<TastingVo> tastingVo = findByTasting(pageRequest(page, limit));
//        return new PageResult<>(tastingVo);
//    }




}
