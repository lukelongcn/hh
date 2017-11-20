package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

/**
 * Created by itservice on 2017/11/18.
 */
@Entity
@Table(name = "ofpay_record")
public class OfPayRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "err_msg")
    private String errMsg;

    @Column(name = "retcode")
    private String retcode;

    @Column(name = "orderid")
    private String orderid;

    @Column(name = "cardid")
    private String cardid;

    @Column(name = "cardnum")
    private String cardnum;

    @Column(name = "ordercash")
    private String ordercash;

    @Column(name = "cardname")
    private String cardname;

    @Column(name = "sporder_id")
    private String sporderId;

    @Column(name = "game_userid")
    private String gameUserid;

    @Column(name = "game_state")
    private String gameState;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public String getCardnum() {
        return cardnum;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum;
    }

    public String getOrdercash() {
        return ordercash;
    }

    public void setOrdercash(String ordercash) {
        this.ordercash = ordercash;
    }

    public String getCardname() {
        return cardname;
    }

    public void setCardname(String cardname) {
        this.cardname = cardname;
    }

    public String getSporderId() {
        return sporderId;
    }

    public void setSporderId(String sporderId) {
        this.sporderId = sporderId;
    }

    public String getGameUserid() {
        return gameUserid;
    }

    public void setGameUserid(String gameUserid) {
        this.gameUserid = gameUserid;
    }

    public String getGameState() {
        return gameState;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
    }
}
