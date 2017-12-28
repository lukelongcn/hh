package com.h9.common.db.entity.user;

import com.h9.common.base.BaseEntity;
import com.h9.common.db.entity.account.BankType;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import java.math.BigDecimal;

import static java.util.Arrays.stream;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/28
 * Time: 9:54
 */

@Entity
@Table(name = "user_bank")
public class UserBank extends BaseEntity {

    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "user_id", nullable = false, columnDefinition = "bigint(20) default 0 COMMENT '用户id'")
    private Long userId;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(32) default '' COMMENT '持卡人名'")
    private String name;

    @Column(name = "no", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '银行卡号'")
    private String no;

    //    @Column(name = "bank_type", nullable = false, columnDefinition = "varchar(32) default '' COMMENT '银行类别'")
    @ManyToOne
    @JoinColumn(name = "bank_type_id", nullable = false, referencedColumnName = "id", columnDefinition = "bigint(20)  COMMENT '银行卡类型'",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private BankType bankType;


    @Column(name = "provice", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '开户省'")
    private String province;

    @Column(name = "city", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '开户城市'")
    private String city;

    @Column(name = "status", nullable = false, columnDefinition = "tinyint default 1 COMMENT '1:正常 2禁用 3解绑'")
    private Integer status = 1;

    @Column(name = "default_select", nullable = false, columnDefinition = "int default 0 COMMENT '默认选择的银行卡 1 默认 0 为不是默认'")
    private Integer defaultSelect;

    /**
     * description: 对应CardInfo 表id
     */
    @Column(name = "card_id")
    private Long cardId;

    @Column(name = "withdraw_money", nullable = false, columnDefinition = "DECIMAL(10,2) default 0 COMMENT '提现金额'")
    private BigDecimal withdrawMoney = new BigDecimal(0);

    @Column(name = "withdraw_count", nullable = false, columnDefinition = "bigint(20) default 0 COMMENT '提现次数'")
    private Long withdrawCount = 0L;

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Integer getDefaultSelect() {
        return defaultSelect;
    }

    public void setDefaultSelect(Integer defaultSelect) {
        this.defaultSelect = defaultSelect;
    }

    public BankType getBankType() {
        return bankType;
    }

    public void setBankType() {
        setBankType();
    }

    public void setBankType(BankType bankType) {
        this.bankType = bankType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getWithdrawMoney() {
        return withdrawMoney;
    }

    public void setWithdrawMoney(BigDecimal withdrawMoney) {
        this.withdrawMoney = withdrawMoney;
    }

    public Long getWithdrawCount() {
        return withdrawCount;
    }

    public void setWithdrawCount(Long withdrawCount) {
        this.withdrawCount = withdrawCount;
    }

    public enum StatusEnum {
        NONE(3,"解绑"),
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
    public enum DefaultSelectEnum {
        NOT_DEFAULT(0,"否"),
        DEFAULT(1,"是");

        DefaultSelectEnum(int id,String name){
            this.id = id;
            this.name = name;
        }

        private int id;
        private String name;

        public static String getNameById(int id){
            DefaultSelectEnum defaultSelectEnum = stream(values()).filter(o -> o.getId()==id).limit(1).findAny().orElse(null);
            return defaultSelectEnum==null?null:defaultSelectEnum.getName();
        }

        public int getId() {
            return id;
        }
        public String getName() {
            return name;
        }
    }

}
