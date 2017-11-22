package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by itservice on 2017/10/30.
 */
public interface BannerRepository extends BaseRepository<Banner> {

    /**
     * description: 查询当前生效的banner
     */
//    @Query(value = "select o from Banner o,BannerType bt  where o.startTime < ?1 and o.endTime> ?1 and o.enable =1 and bt.enable = 1 and bt. order by o.sort desc")
    @Query(value = "select * from banner,banner_type where banner.banner_type_id = banner_type.id and banner.start_time < ?1 and banner.end_time >?1 and banner.enable = 1 and banner_type.enable = 1 order by banner.sort desc"
            ,nativeQuery = true)
    List<Banner> findActiviBanner( Date date);

    Banner findByTitle(String title);

    Banner findByIdNotAndTitle(long id,String title);

    @Query("select o from Banner o where o.bannerType.id = ?1  order by o.enable desc , o.sort desc , o.id desc ")
    Page<Banner> findAllByBannerType_Id(long banner_type_id,Pageable page);

    @Query("select o from Banner o where o.bannerType.id = ?1")
    List<Banner> findAllByBannerTypeId(long banner_type_id);
}
