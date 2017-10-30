package com.h9.common.db.repo;


import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.SMSLog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by itservice on 2017/10/27.
 */
@Repository
public interface SMSLogReposiroty extends BaseRepository<SMSLog> {

}
