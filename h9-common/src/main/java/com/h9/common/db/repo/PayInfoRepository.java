package com.h9.common.db.repo;


import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.PayInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: PayInfoRepository
 * @Description: PayInfo 的查询
 * @author: shadow.liu
 * @date: 2016年6月27日 下午3:18:36
 */
@Repository
public interface PayInfoRepository extends BaseRepository<PayInfo> {


    List<PayInfo> findByOrderIdOrderByIdDesc(Long orderId);

    PayInfo findByOrderIdAndOrderType(Long orderId, Integer orderType);
}
