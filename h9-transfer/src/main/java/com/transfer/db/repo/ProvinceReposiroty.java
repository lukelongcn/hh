package com.transfer.db.repo;

import com.h9.common.base.BaseRepository;
import com.transfer.db.entity.City;
import com.transfer.db.entity.Province;
import org.springframework.stereotype.Repository;

/**
 * Created by itservice on 2017/11/24.
 */
@Repository
public interface ProvinceReposiroty extends BaseRepository<Province>{

    Province findByPid(Long pid);
}
