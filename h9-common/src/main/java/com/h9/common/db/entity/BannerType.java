package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;
import com.h9.common.validation.Add;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Created with IntelliJ IDEA.
 * Description:banner类型
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/27
 * Time: 18:13
 */
@Entity
@Table(name = "banner_type")
public class BannerType extends BaseEntity {


    public enum BannerTypeEnum {

        TOP_BANNER(1,"顶部banner"),
        IDEAR_BANNER(3,"创意banner"),
        NAVITION_BANNER(2,"功能导航banner");

        BannerTypeEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        private int code;
        private String desc;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public java.lang.String getDesc() {
            return desc;
        }

        public void setDesc(java.lang.String desc) {
            this.desc = desc;
        }
    }

    @NotNull(message = "id不能为空",groups = {Add.class})
    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @NotEmpty(message = "名称不能为空")
    @Column(name = "name", nullable = false, columnDefinition = "varchar(64) default '' COMMENT 'banner类型名称'")
    private String name;

    @Column(name = "code", nullable = false, columnDefinition = "varchar(64) default '' COMMENT 'banner类型标识'")
    private String code;

    @Column(name = "enable",nullable = false,columnDefinition = "tinyint default 1 COMMENT '是否启用 1启用 0 禁用'")
    private Integer enable;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "start_time",columnDefinition = "datetime COMMENT '开始时间'")
    @Temporal(TIMESTAMP)
    private Date startTime;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "end_time",columnDefinition = "datetime COMMENT '结束时间'")
    @Temporal(TIMESTAMP)
    private Date endTime;


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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
