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


    public enum ArticleTypeEnum{

        NOTICE("公告",1),
        RECOMMEND("推荐文章",2);

        ArticleTypeEnum(String desc, int code) {
            this.desc = desc;
            this.code = code;
        }

        private String desc;
        private int code;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(64) default '' COMMENT 'banner类型名称'")
    private String name;

    @Column(name = "code", nullable = false, columnDefinition = "varchar(64) default '' COMMENT 'banner类型标识'")
    private String code;


    @Column(name = "enable",nullable = false,columnDefinition = "tinyint default 1 COMMENT '是否启用 1启用 0 禁用'")
    private Integer enable;


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


}
