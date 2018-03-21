package com.h9.common.db.entity.config;

import com.h9.common.base.BaseEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

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
@Data
@Entity
@Table(name = "banner_type")
public class BannerType extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(64) default '' COMMENT 'banner类型名称'")
    private String name;

    @Column(name = "code", nullable = false, columnDefinition = "varchar(64) default '' COMMENT 'banner类型标识'")
    private String code;

    /**
     * description:
     * @see LocaltionEnum
     */
    @Column(name = "location", columnDefinition = "tinyint default 1 COMMENT '显示位置，1:首页，2:酒元商城,3社区首页 ，4帖子详情，5 为旅游健康卡' ")
    private Integer location;

    @Column(name = "enable", nullable = false, columnDefinition = "tinyint default 1 COMMENT '是否启用 1启用 0 禁用'")
    private Integer enable;

    @Column(name = "start_time", nullable = false, columnDefinition = "datetime COMMENT '开始时间'")
    @Temporal(TIMESTAMP)
    private Date startTime;

    @Column(name = "end_time", nullable = false, columnDefinition = "datetime COMMENT '结束时间'")
    @Temporal(TIMESTAMP)
    private Date endTime;

    @Column(name = "sort", nullable = false, columnDefinition = "tinyint default 1 COMMENT '排序'")
    private Integer sort = 1;




    @Transient
    private String locationDesc;

    @Transient
    private Integer bannerCount;


    public BannerType() {
    }

    public BannerType(BannerType bannerType, int bannerCount) {
        BeanUtils.copyProperties(bannerType, this);
        this.bannerCount = bannerCount;
    }

    public BannerType(String name, String code, Integer enable) {
        this.name = name;
        this.code = code;
        this.enable = enable;
    }

    public BannerType(String name, String code, Integer enable, Date startTime, Date endTime) {
        this.name = name;
        this.code = code;
        this.enable = enable;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public enum EnableEnum {
        DISABLED(0, "禁用"),
        ENABLED(1, "启用");

        EnableEnum(int id, String name) {
            this.id = id;
            this.name = name;
        }

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public enum LocaltionEnum {
        HOME(1, "首页"),
        STROE(2, "商店"),
        STICK_HOME(3, "社区首页"),
        STICK_DETAIL(4, "帖子详情"),
        TRAVEL_ALL(5,"旅游+健康"),
        TRAVEL_CHECK(6,"体检"),
        TRAVEL(7,"旅游");

        LocaltionEnum(int id, String name) {
            this.id = id;
            this.name = name;
        }

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static LocaltionEnum findByCode(int code){
            LocaltionEnum[] values = values();
            for(LocaltionEnum enumEl : values){
                if(enumEl.id == code){

                    return enumEl;
                }
            }
            return null;
        }
    }


    public enum TypeEnum {
        Roll("Roll","滚动广告位"),
        NavigationBtn("NavigationBtn","导航按纽"),
        TWO("TWO","双联banner"),;


        TypeEnum(String id, String name) {
            this.id = id;
            this.name = name;
        }

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
