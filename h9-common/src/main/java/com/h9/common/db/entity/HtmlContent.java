package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description: 静态页面配置
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/31
 * Time: 10:51
 */

@Entity
@Table(name = "html_content",uniqueConstraints = {@UniqueConstraint(columnNames = "code")})
public class HtmlContent extends BaseEntity {

    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '名称'")
    private String name;

    @Column(name = "code", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '静态页面 标识 在路径最后面'")
    private String code;

    @Column(name = "title", nullable = false, columnDefinition = "varchar(128) default '' COMMENT '标题'")
    private String title;
    
    @Column(name = "content", nullable = false, columnDefinition = "text COMMENT '静态页面内容'")
    private String content;

    @Column(name = "status",nullable = false,columnDefinition = "tinyint default 1 COMMENT '1 启用 0 禁用 2删除 同一个code 只能有一个启用的'")
    private Integer status = 1;
    

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
