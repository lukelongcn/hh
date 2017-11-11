package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.LotteryFlow;
import com.h9.common.db.entity.LotteryFlowRecord;
import com.h9.common.db.entity.User;
import com.h9.common.modle.dto.LotteryFlowActivityDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: George
 * @date: 2017/11/10 14:53
 */
public interface LotteryFlowRecordRepository  extends BaseRepository<LotteryFlowRecord> {
   LotteryFlowRecord findByLotteryFlow_Id(long lotteryFlowId);
}
