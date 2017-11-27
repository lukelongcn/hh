package com.transfer.db.repo;

import com.h9.common.base.BaseRepository;
import com.transfer.db.entity.CardInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Created by itservice on 2017/11/24.
 */
@Repository
public interface CardInfoRepository extends BaseRepository<CardInfo>{


}
