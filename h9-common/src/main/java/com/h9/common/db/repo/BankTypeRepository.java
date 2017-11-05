package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.BankType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 李圆
 * on 2017/11/4
 */
@Repository
public interface BankTypeRepository  extends BaseRepository<BankType> {

   BankType findByBankName(String name);

    @Query("select bankName from BankType")
    List<String> findBankName();
}
