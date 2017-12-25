package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:订单商品
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/28
 * Time: 15:57
 */

@Entity
@Table(name = "order_items")
public class OrderItems extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '商品名称'")
    private String name;

    @Column(name = "image", columnDefinition = "varchar(256) default '' COMMENT '商品图片'")
    private String image;

    @Column(name = "price",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '价格'")
    private BigDecimal price = new BigDecimal(0);

    @Column(name = "money",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '金额'")
    private BigDecimal money = new BigDecimal(0);

    @Column(name = "count",columnDefinition = "int default 1 COMMENT '数量' ")
    private Integer count = 1;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "orders_id",nullable = false,referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT '订单id'",foreignKey =@ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "goods_id",referencedColumnName = "id",foreignKey =@ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @NotFound(action= NotFoundAction.IGNORE)
    private Goods goods;

    @Column(name="didi_card_number")
    private String didiCardNumber;

    public OrderItems(){}

    public OrderItems(String name, String image, BigDecimal price, BigDecimal money, Integer count, Orders orders) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.money = money;
        this.count = count;
        this.orders = orders;
    }

    public OrderItems(Goods goods,Integer count, Orders orders) {
        this.name = goods.getName();
        this.image = goods.getImg();
        this.price = goods.getRealPrice();
        this.money = goods.getRealPrice();
        this.count = count;
        this.orders = orders;
        this.goods = goods;
    }

    public String getDidiCardNumber() {
        return didiCardNumber;
    }

    public void setDidiCardNumber(String didiCardNumber) {
        this.didiCardNumber = didiCardNumber;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }



    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }



}
