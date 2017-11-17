package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:banner类型
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/27
 * Time: 18:13
 */
@Entity
@Table(name = "article_type")
public class ArticleType extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '文章类型名称'")
    private String name;

    @Column(name = "code",  columnDefinition = "varchar(64) default '' COMMENT '文章类型标识'")
    private String code = "";


    @Column(name = "enable",nullable = false,columnDefinition = "tinyint default 1 COMMENT '是否启用 1启用 0 禁用 2删除'")
    private Integer enable;
    
    @Column(name = "sort",nullable = false,columnDefinition = "tinyint default 1 COMMENT '排序'")
    private Integer sort = 1;
    @Transient
    private Long articleCount;

    public Long getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(Long articleCount) {
        this.articleCount = articleCount;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public ArticleType() {
    }

    public ArticleType(String name, String code, Integer enable) {
        this.name = name;
        this.code = code;
        this.enable = enable;
    }
}
