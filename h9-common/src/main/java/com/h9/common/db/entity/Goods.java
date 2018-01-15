package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Created with IntelliJ IDEA.
 * Description:商品，包括虚拟商品
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/30
 * Time: 11:33
 */

@Entity
@Table(name = "goods")
public class Goods extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(256) default '' COMMENT '充值显示名称'")
    private String name;

    @Column(name = "code", nullable = false, columnDefinition = "varchar(30) default '' COMMENT '商品编码'")
    private String code;

    @Column(name = "real_price",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '售价'")
    private BigDecimal realPrice = new BigDecimal(0);

    @Column(name = "price",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '原价'")
    private BigDecimal price = new BigDecimal(0);

    @Column(name = "cash_back",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '奖励金额'")
    private BigDecimal cashBack = new BigDecimal(0);

    @Column(name = "status",nullable = false,columnDefinition = "tinyint default 1 COMMENT '1 上架 2 下架'")
    private Integer status = 1;

    @Column(name = "description", nullable = false, columnDefinition = "varchar(256) default '' COMMENT '描述'")
    private String description;

    @Column(name = "stock",nullable = false,columnDefinition = "int default 0 COMMENT '库存'")
    private Integer stock = 0;

//    @Column(name = "goods_type",nullable = false,columnDefinition = "int default 1 COMMENT '类别（1，为手机充值 2，为 滴滴卡兑换）'")
    @ManyToOne()
    @JoinColumn(name = "goods_type_id",nullable = false,referencedColumnName="id",columnDefinition = "bigint(20)  COMMENT '商品类型'",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @NotFound(action= NotFoundAction.IGNORE)
    private GoodsType goodsType;

    @Column(name = "img",nullable = false,columnDefinition = "varchar(256) default '' COMMENT '图片url' ")
    private String img;

    @Column(name = "v_coins_rate",columnDefinition = "DECIMAL(10,2) default 0 COMMENT 'V币兑换比例值'")
    private BigDecimal vCoinsRate = new BigDecimal(0);

    @Temporal(TIMESTAMP)
    @Column(name = "start_time", columnDefinition = "datetime COMMENT '上架开始时间'")
    private Date startTime;

    @Temporal(TIMESTAMP)
    @Column(name = "end_time", columnDefinition = "datetime COMMENT '上架结束时间'")
    private Date endTime;



    public Integer getStock() {
        return stock;
    }

    public GoodsType getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
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

    public BigDecimal getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(BigDecimal realPrice) {
        this.realPrice = realPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCashBack() {
        return cashBack;
    }

    public void setCashBack(BigDecimal cashBack) {
        this.cashBack = cashBack;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public BigDecimal getvCoinsRate() {
        return vCoinsRate;
    }

    public void setvCoinsRate(BigDecimal vCoinsRate) {
        this.vCoinsRate = vCoinsRate;
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

    public enum StatusEnum {
        ONSHELF(1,"上架"),
        OFFSHELF(2,"下架");

        StatusEnum(int id,String name){
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
}
