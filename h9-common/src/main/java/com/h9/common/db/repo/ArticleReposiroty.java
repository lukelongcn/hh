package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.Article;
import org.springframework.data.jpa.repository.Query;
import redis.clients.jedis.BasicRedisPipeline;

import java.util.Date;
import java.util.List;

/**
 * Created by itservice on 2017/10/30.
 */
public interface ArticleReposiroty extends BaseRepository<Article>{

    /**
     * description: 查询当前生效的文章
     */
    @Query(value = "select o from Article o where o.startTime < ?1 and o.endTime > ?1 and o.enable = 1 order by o.sort")
    List<Article> findActiveAriticle(Date date);
}
