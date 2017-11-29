package com.transfer.service;

import com.h9.common.base.PageResult;
import com.h9.common.utils.DateUtil;
import com.transfer.SqlUtils;
import com.transfer.db.entity.IntegralRecord;
import com.transfer.db.repo.IntegralRecordRepository;
import com.transfer.service.base.BaseService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * IntegralRecord:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/27
 * Time: 14:46
 */
@Component
public class IntegralRecordService extends BaseService<IntegralRecord> {

    @Resource
    private IntegralRecordRepository integralRecordRepository;

    @Override
    public String getPath() {
        return "./record.sql";
    }

    @Override
    public PageResult get(int page, int limit) {
//        Sort sort = new Sort(Sort.Direction.ASC, "RecordID");
        return integralRecordRepository.findAll(page,limit);
    }

    @Override
    public void getSql(IntegralRecord integralRecord, BufferedWriter userWtriter) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("insert into `vcoins_flow` (`id`,`create_time`, `update_time`," +
                "  `money`,`remarks`, `user_id`, `v_coins_type_id`)");
        stringBuffer.append("value(");
        stringBuffer.append(integralRecord.getRecordID()+ ",");
        stringBuffer.append(SqlUtils.concatSql(DateUtil.formatDate(integralRecord.getRecordtime(), DateUtil.FormatType.SECOND)));
        stringBuffer.append(SqlUtils.concatSql(DateUtil.formatDate(integralRecord.getRecordtime(), DateUtil.FormatType.SECOND)));
        stringBuffer.append(integralRecord.getGoodsIntegral()+",");
        stringBuffer.append(SqlUtils.concatSql(integralRecord.getGoodsName()));
        stringBuffer.append(integralRecord.getUserId()+",");
        stringBuffer.append(11);
        stringBuffer.append(");");
        userWtriter.write(stringBuffer.toString());
        userWtriter.newLine();

    }

    @Override
    public String getTitle() {
        return "积分流水迁移进度";
    }
}
