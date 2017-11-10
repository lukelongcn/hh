package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * Description:余额流水类型
 * User:刘敏华 shadow.liu@hey900.com
 * Date: 2017/10/27
 * Time: 17:37
 */

@Entity
@Table(name = "balance_flow_type")
public class BalanceFlowType extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "flow_name", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '流水类型名称'")
    private String flowName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public enum BalanceFlowTypeEnum {
        WITHDRAW(1,"提现"),
        RETURN(2,"银联退回"),
        XIAPPINHUI(3,"小品会"),
        RECHARGE_PHONE_FARE(4,"充话费"),
        DIDI_EXCHANGE(5,"滴滴兑换"),
        ROUNDABOUT(6,"大转盘");

        BalanceFlowTypeEnum(long id,String name){
            this.id = id;
            this.name = name;
        }

        private long id;
        private String name;

        public long getId() {
            return id;
        }

        public void setId(long id) {
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
