package com.transfer.db.repo;

import com.transfer.db.BasicRepository;
import com.transfer.db.entity.Address;
import com.transfer.db.entity.UserInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by itservice on 2017/11/15.
 */
@Repository
public interface TargetAddressReposiroty extends BasicRepository<Address> {
}
