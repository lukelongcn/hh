package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.community.StickImage;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 李圆 on 2018/1/23
 */
@Repository
public interface StickImageRespository  extends BaseRepository<StickImage> {
    @Query("select s.image from StickImage s where s.stick.id = ?1 order by s.createTime ")
    List<String> findByStickId(Long stickId);

    @Query("select s from StickImage s where s.stick.id = ?1 order by s.createTime ")
    List<StickImage> updateImages(Long id);
}
