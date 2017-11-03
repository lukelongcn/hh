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
    @Query(value = "select o from Banner o where o.startTime < ?1 and o.endTime> ?1 and o.enable =1  order by o.sort")
    List<Banner> findActiviBanner( Date date);

    Banner findByTitle(String title);

    Banner findByIdNotAndTitle(long id,String title);

    @Query("select o from Banner o")
    Page<Banner> findAllByPage(Pageable page);
}
