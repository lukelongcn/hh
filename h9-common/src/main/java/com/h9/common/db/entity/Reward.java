package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import java.awt.image.DataBuffer;
import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/28
 * Time: 10:21
 */
@Entity
@Table(name = "reward")
public class Reward extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_id",referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT '活动id'")
    private Activity activity;

    @Column(name = "code", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '奖励条码'")
    private String code;

    @Column(name = "md5_code", nullable = false, columnDefinition = "varchar(32) default '' COMMENT '条码加密'")
    private String md5Code;

    @Column(name = "money",columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '奖励总金额'")
    private BigDecimal money = new BigDecimal(0);


    @Column(name = "reward_url", nullable = false, columnDefinition = "varchar(256) default '' COMMENT '红包路径'")
    private String rewardUrl;

    /***
     * @see StatusEnum
     */
    @Column(name = "status",nullable = false,columnDefinition = "tinyint default 1 COMMENT '1 未领取 2 部分领取 3 已领取完毕 4 已失效'")
    private Integer status = 1;

    @Column(name = "user_id",columnDefinition = "bigint(20) default 0 COMMENT '奖励所属人'")
    private Long userId;

    @Temporal(TIMESTAMP)
    @Column(name = "finish_time", columnDefinition = "datetime COMMENT '结束时间'")
    private Date finishTime;
    
    @Column(name = "partake_count",nullable = false,columnDefinition = "int default 0 COMMENT '参加数量'")
    private int partakeCount = 0;
    
    @Column(name = "factory_status",nullable = false,columnDefinition = "tinyint default -1 COMMENT ' 工厂放回状态'")
    private Integer factoryStatus = -1;

    @Column(name = "start_type",nullable = false,columnDefinition = "tinyint default 1 COMMENT '1 手动开启  2  自动开启'")
    private Integer startType = 1;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id",referencedColumnName="id",columnDefinition = "bigint(20) default null COMMENT ''")
    private Product product;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }


    public String getRewardUrl() {
        return rewardUrl;
    }

    public void setRewardUrl(String rewardUrl) {
        this.rewardUrl = rewardUrl;
    }

    /***
     * @see StatusEnum
     * @param status
     */
    public Integer getStatus() {
        return status;
    }

    /***
     * @see StatusEnum
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getMd5Code() {
        return md5Code;
    }

    public void setMd5Code(String md5Code) {
        this.md5Code = md5Code;
    }

    public  int getPartakeCount() {
        return partakeCount;
    }

    public void setPartakeCount(int partakeCount) {
        this.partakeCount = partakeCount;
    }


    public Integer getFactoryStatus() {
        return factoryStatus;
    }

    public void setFactoryStatus(Integer factoryStatus) {
        this.factoryStatus = factoryStatus;
    }

    public Integer getStartType() {
        return startType;
    }

    public void setStartType(Integer startType) {
        this.startType = startType;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public static enum StatusEnum{

        TO_BEGIN(1,"待领取"),
        PART_START(2,"抢红包中"),
        END(3,"抢红包结束"),
        FAILD(4,"红包已失效");

        private int code;
        private String desc;

        StatusEnum(int code, String desc) {
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




    }
}
