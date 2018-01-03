package com.h9.common.db.entity.config;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author: George
 * @date: 2017/11/21 19:06
 */
@Entity
@Table(name = "image")
public class Image extends BaseEntity {

    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "title",  columnDefinition = "varchar(64) default '' COMMENT '标题'")
    private String title;

    @Column(name = "directory",  columnDefinition = "varchar(64) default '' COMMENT '目录'")
    private String directory;

    @Column(name = "url",  columnDefinition = "varchar(256) default '' COMMENT '图片url'")
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
