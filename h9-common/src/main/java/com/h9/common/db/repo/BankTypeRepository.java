package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.BankType;
import jdk.nashorn.internal.ir.BaseNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    BankType findByIdNotAndBankName(long id,String name);

    @Query("select o from BankType o  order by o.id desc ")
    Page<BankType> findAllByPage(Pageable page);

    @Query("select o from BankType o where status =1")
    List<BankType> findTypeList();
}
