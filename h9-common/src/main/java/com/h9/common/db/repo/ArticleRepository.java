package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by itservice on 2017/10/30.
 */
public interface ArticleRepository extends BaseRepository<Article>{

    /**
     * description: 查询当前生效的文章
     */
    @Query(value = "select o from Article o where o.startTime < ?1 and o.endTime > ?1 and o.enable = 1 order by o.sort")
    List<Article> findActiveArticle(Date date);

    @Query("select count(a) from Article a where a.articleType.id=?1 and a.enable<>2")
    Long findCountByArticleType(Long articleType);
    
    @Query("select a from Article a where a.enable<>2")
    Page<Article> findAll(Pageable pageable);
    
    @Query("select a from Article a where a.enable<>2 and a.id=?1")
    Article findOne(Long id);
}
