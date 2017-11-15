package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.BannerType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

/**
 * @author: George
 * @date: 2017/11/1 16:57
 */
public interface BannerTypeRepository extends BaseRepository<BannerType> {

    BannerType findByCode(String code);

    BannerType findByIdNotAndCode(long id,String code);

    //BannerType findBy

    @Query("select o from BannerType o order by o.enable desc , o.id desc ")
    Page<BannerType> findAllByPage(Pageable page);
}
