package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Created with IntelliJ IDEA.
 * Description: 获奖用户记录
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/5
 * Time: 14:52
 */

@Entity
@Table(name = "lottery_flow",uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","reward_id"}))
public class LotteryFlow extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id",nullable = false,referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT ''",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "reward_id", nullable = false, referencedColumnName = "id",
            columnDefinition = "bigint(20) default 0 COMMENT '奖励id'")
    private Reward reward;

    @Column(name = "money", columnDefinition = "DECIMAL(10,2) default 0.00 COMMENT '奖励领取金额'")
    private BigDecimal money = new BigDecimal(0);

    @OneToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_record_id",nullable = false,referencedColumnName="id",columnDefinition = "bigint(20) default 0 COMMENT ''",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserRecord userRecord;

    @Column(name = "status", nullable = false, columnDefinition = "tinyint default 1 COMMENT '1 未分配奖励 2已分配奖励 3完成'")
    private Integer status = 1;

    /***
     * @see LotteryFlow.UserEnum
     * @param roomUser
     */
    @Column(name = "room_user",nullable = false,columnDefinition = "tinyint default 1 COMMENT ' 2 房间主人 1普通用户'")
    private Integer roomUser = 1;
    
    @Column(name = "remarks", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '备注'")
    private String remarks;

    @Column(name = "description",columnDefinition = "varchar(64) default '' COMMENT '描述'")
    private String desc;

    public Long getId() {
        return id;
    }

    public LotteryFlow() {
    }

    public LotteryFlow(Lottery lottery) {
        BeanUtils.copyProperties(lottery,this,"id");
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public UserRecord getUserRecord() {
        return userRecord;
    }

    public void setUserRecord(UserRecord userRecord) {
        this.userRecord = userRecord;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /***
     * @see LotteryFlow.UserEnum
     * @param roomUser
     */
    public Integer getRoomUser() {
        return roomUser;
    }

    /***
     * @see LotteryFlow.UserEnum
     * @param roomUser
     */
    public void setRoomUser(Integer roomUser) {
        this.roomUser = roomUser;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public enum UserEnum{
        ROOMUSER(2,"房主"),
        ORTHER(1,"普通用户");

        UserEnum(int id,String name){
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
