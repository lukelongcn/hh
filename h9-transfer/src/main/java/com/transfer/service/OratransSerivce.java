package com.transfer.service;

import com.h9.common.base.PageResult;
import com.h9.common.db.entity.WithdrawalsRecord;
import com.transfer.SqlUtils;
import com.transfer.db.BasicRepository;
import com.transfer.db.entity.BounsDetails;
import com.transfer.db.entity.Oratrans;
import com.transfer.db.repo.BounsDetailsRepository;
import com.transfer.db.repo.OratransRepository;
import com.transfer.service.base.BaseService;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * OratransSerivce:刘敏华 shadow.liu@hey900.com
 * Date: 2017/12/2
 * Time: 16:12
 */
@Component
public class OratransSerivce extends BaseService<Oratrans> {

    @Resource
    private OratransRepository oratransRepository;

    @Resource
    private BounsDetailsRepository bounsDetailsRepository;



    @Override
    public String getPath() {
        return "./sql/orantrans.sql";
    }

    @Override
    public PageResult get(int page, int limit) {
        return oratransRepository.findAll(page,limit);
    }

    @Override
    public void getSql(Oratrans oratrans, BufferedWriter userWtriter) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("insert into `withdrawals_record` (`create_time`, `update_time`," +
                " `finish_time`, `money`,`order_no`, " +
        " `remarks`, `status`, `user_id`,`bank_name`, `bank_no`, `city`,`name`, `provice`)");
        stringBuffer.append("value(");
        stringBuffer.append(SqlUtils.concatDate(oratrans.getOratransTime()));
        stringBuffer.append(SqlUtils.concatDate(oratrans.getOratransTime()));
        stringBuffer.append(SqlUtils.concatDate(oratrans.getOratransTime()));
        BigDecimal transAmt = oratrans.getTransAmt();
        stringBuffer.append(transAmt.divide(new BigDecimal(100))+",");
        stringBuffer.append(SqlUtils.concatSql(oratrans.getOrderNo()));
        stringBuffer.append(SqlUtils.concatSql("数据迁移"));
        stringBuffer.append(WithdrawalsRecord.statusEnum.FINISH.getCode()+",");
        Long userId = null;
        BounsDetails bounsDetails = bounsDetailsRepository.findByOAndBounsOID(oratrans.getOratransOId());
        if(bounsDetails!=null){
            userId = bounsDetails.getUserid();
        }
        stringBuffer.append(userId+",");
        stringBuffer.append(SqlUtils.concatSql(oratrans.getBakName()));
        stringBuffer.append(SqlUtils.concatSql(oratrans.getCardNo()));
        stringBuffer.append(SqlUtils.concatSql(oratrans.getCity()));
        stringBuffer.append(SqlUtils.concatSql(oratrans.getUsrName()));
        stringBuffer.append(SqlUtils.concatSql(oratrans.getProv(),false));
        stringBuffer.append(")");
        userWtriter.write(stringBuffer.toString());
        userWtriter.newLine();

    }

    @Override
    public String getTitle() {
        return "提现记录迁移进度";
    }


}
