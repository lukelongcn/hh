package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.config.Article;
import com.h9.common.db.entity.config.ArticleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by itservice on 2017/10/30.
 */
public interface ArticleRepository extends BaseRepository<Article>{

    /**
     * description: 查询当前生效的文章
     */
    @Query(value = "select o from Article o where o.enable = 1 and o.recommend = 1 " +
            " order by o.sort desc,o.createTime desc")
    List<Article> findActiveArticle( );

    @Query("select count(a) from Article a where a.articleType.id=?1 and a.enable<>2")
    Long findCountByArticleType(Long articleType);
    
    @Query("select a from Article a where a.enable<>2 order by a.enable desc ,a.sort desc,a.id desc")
    Page<Article> findAll(Pageable pageable);
    
    @Query("select a from Article a where a.id=?1")
    Article findOne(Long id);

    @Query("select o from Article o where o.enable = 1 and o.articleType = ?1 order by o.sort desc")
    List<Article> findByType(ArticleType type);

    @Query("select a from Article a ")
    List<Article> findTypeList();
}
