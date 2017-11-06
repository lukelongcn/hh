package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.GoodsDIDINumber;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by itservice on 2017/11/6.
 */
public interface GoodsDIDINumberRepository extends BaseRepository<GoodsDIDINumber>{

    @Query(value = "select count(o.id) from GoodsDIDINumber o where o.status = 1")
    Object getCount();
}
