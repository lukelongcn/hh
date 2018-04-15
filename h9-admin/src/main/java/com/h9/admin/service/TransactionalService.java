package com.h9.admin.service;

import com.h9.common.base.BaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用于调用新的事务
 */
@Service
public class TransactionalService {
    @Transactional(propagation = Propagation.REQUIRES_NEW)


    public <T> T findOneNewTrans(BaseRepository<T> rep,Long id) {

        return rep.findOne(id);
    }

}
