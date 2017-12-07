package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import static java.util.Arrays.stream;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description: 商品类型
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/30
 * Time: 11:35
 */

@Entity
@Table(name = "goods_type", uniqueConstraints = {@UniqueConstraint(columnNames = {"code"})})
public class GoodsType extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '商品分类名称'")
    private String name;

    @Column(name = "status",nullable = false,columnDefinition = "tinyint default 1 COMMENT ' 1 正常  2 禁用'")
    private Integer status = 1;

    @Column(name = "allow_import",nullable = false,columnDefinition = "tinyint default 1 COMMENT ' 是否允许导入数据，1:否，2:是'")
    private Integer allowImport = 1;

    @Column(name = "is_real",nullable = false,columnDefinition = "tinyint default 0 COMMENT ' 是否是实物，0:否，1:是'")
    private Integer isReal;

    /**
     * description:
     * @see GoodsTypeEnum
     */
    @Column(name = "code",nullable = false,columnDefinition = "varchar(50) default '' COMMENT '标识商品类型字段'")
    private String code;
//    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    @JoinColumn(name = "parent_id",nullable = true,referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT ''")
    @Column(name = "parent_id")
    private Long parent;

    public enum GoodsTypeEnum{

        MOBILE_RECHARGE("mobile_recharge","手机卡"),
        DIDI_CARD("didi_card", "滴滴卡"),
//        MATERIAL("material","实物"),
        FOODS("foods", "食物，饮料"),
        EVERYDAY_GOODS("everyday_goods", "日常家居"),
        VB("vb", "V币");

        private String code;
        private String desc;
        GoodsTypeEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }


        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public static GoodsTypeEnum findByCode(String code){
            GoodsTypeEnum[] values = values();
            for(GoodsTypeEnum smsTypeEnum: values){
                if(code.equals(smsTypeEnum.getCode())){
                    return smsTypeEnum;
                }
            }
            return null;
        }
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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

    public Integer getAllowImport() {
        return allowImport;
    }

    public void setAllowImport(Integer allowImport) {
        this.allowImport = allowImport;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public Integer getIsReal() {
        return isReal;
    }

    public void setIsReal(Integer isReal) {
        this.isReal = isReal;
    }

    public enum StatusEnum {
        DISABLED(2,"禁用"),
        ENABLED(1,"正常");

        StatusEnum(int id,String name){
            this.id = id;
            this.name = name;
        }

        private int id;
        private String name;

        public static String getNameById(int id){
            StatusEnum statusEnum = stream(values()).filter(o -> o.getId()==id).limit(1).findAny().orElse(null);
            return statusEnum==null?null:statusEnum.getName();
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

    }
}
