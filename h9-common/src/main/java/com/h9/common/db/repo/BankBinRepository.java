package com.h9.common.db.repo;


import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.BankBin;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: BankBinRepository
 * @Description: BankBin 的查询
 * @author: shadow.liu
 * @date: 2016年6月27日 下午3:18:36
 */
@Repository
public interface BankBinRepository extends BaseRepository<BankBin> {

    BankBin findByBankBin(String bankBin);

    BankBin findByBankBinLike(String bankBin);

}
