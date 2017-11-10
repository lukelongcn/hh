package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description: 商品类型
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/30
 * Time: 11:35
 */

@Entity
@Table(name = "goods_type")
public class GoodsType extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '商品分类名称'")
    private String name;

    @Column(name = "status",nullable = false,columnDefinition = "tinyint default 1 COMMENT ' 1 启用  2 禁用'")
    private Integer status = 1;

    /**
     * description:
     * @see GoodsTypeEnum
     */
    @Column(name = "code",nullable = false,columnDefinition = "int default 1 COMMENT ''")
    private Integer code;
//    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    @JoinColumn(name = "parent_id",nullable = true,referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT ''")
    @Column(name = "parent_id")
    private Long parent;

    public enum GoodsTypeEnum{

        MOBILE_RECHARGE(1,"手机卡"),
        DIDI_CARD(2, "滴滴卡"),
        MATERIAL(3,"实物");
//        VCOINS(4, "V币");
        private int code;
        private String desc;
        GoodsTypeEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public static GoodsTypeEnum findByCode(int code){
            GoodsTypeEnum[] values = values();
            for(GoodsTypeEnum smsTypeEnum: values){
                if(code == smsTypeEnum.getCode()){
                    return smsTypeEnum;
                }
            }
            return null;
        }
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }
}
