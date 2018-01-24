package com.h9.common.db.entity.community;

import com.h9.common.base.BaseEntity;
import com.h9.common.db.entity.order.Address;

import lombok.Data;

import javax.persistence.*;

import static java.util.Arrays.stream;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/12/29
 * Time: 11:14
 */

@Data
@Entity
@Table(name = "stick_type")
public class StickType extends BaseEntity {

    @Id
    @SequenceGenerator(name = "h9-parentSeq", sequenceName = "h9-parent_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-parentSeq")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '分类名称'")
    private String name;

    @Column(name = "content", nullable = false, columnDefinition = "varchar(256) default '' COMMENT '板块介绍'")
    private String content;

    @Column(name = "image", nullable = false, columnDefinition = "varchar(256) default '' COMMENT '板块图片'")
    private String image;

    @Column(name = "stick_count",columnDefinition = "int default 0 COMMENT '贴子数量'")
    private Integer stickCount = 0;

    @Column(name = "limit_state",nullable = false, columnDefinition = "int default 1 COMMENT '限制发帖 1不限制 2限制'")
    private Integer limitState = 1;

    @Column(name = "examine_state",nullable = false, columnDefinition = "int default 1 COMMENT '发帖后台审核 1是 2否'")
    private Integer examineState = 1;

    @Column(name = "comment_state",nullable = false, columnDefinition = "int default 1 COMMENT '评论审核 1是 2否'")
    private Integer commentState = 1;

    @Column(name = "admit_state",nullable = false, columnDefinition = "int default 1 COMMENT '是否允许评论 1是 2否'")
    private Integer admitsState = 1;

    @Column(name = "sort", columnDefinition = "varchar(256) default '' COMMENT '顺序'")
    private String sort;

    @Column(name = "default_sort",nullable = false, columnDefinition = "int default 1 COMMENT '默认排序 1回复数 2浏览数 3最新发表 4最后回复'")
    private Integer defaultSort = 1;

    @Column(name = "state",nullable = false,columnDefinition = "int default 1 COMMENT '帖子评论状态 1使用 2禁用 3删除'")
    private Integer state = 1;


    public enum defaultSortEnum {
        /**
         * 默认排序
         */
        COMMENT_COUNT(1,"回复数"),
        READ_COUNT(2,"浏览数"),
        NEW_STICK(3,"最新发表"),
        LAST_COMMENT(4,"最后回复");

        defaultSortEnum(int id,String name){
            this.id = id;
            this.name = name;
        }

        private int id;
        private String name;

        public static String getNameById(int id){
            StickType.defaultSortEnum defaultSortEnum = stream(values()).filter(o -> o.getId()==id).limit(1).findAny().orElse(null);
            return defaultSortEnum==null?null:defaultSortEnum.getName();
        }

        public int getId() {
            return id;
        }
        public String getName() {
            return name;
        }
    }



}
