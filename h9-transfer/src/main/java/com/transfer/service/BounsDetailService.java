package com.transfer.service;

import com.h9.common.base.PageResult;
import com.h9.common.utils.DateUtil;
import com.transfer.SqlUtils;
import com.transfer.db.entity.BounsDetails;
import com.transfer.db.repo.BounsDetailsRepository;
import com.transfer.service.base.BaseService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * BounsDetailService:刘敏华 shadow.liu@hey900.com
 * Date: 2017/11/27
 * Time: 15:58
 */
@Component
public class BounsDetailService extends BaseService<BounsDetails> {

    @Resource
    private BounsDetailsRepository bounsDetailsRepository;

    @Override
    public String getPath() {
        return "./sql/bouns.sql";
    }

    @Override
    public PageResult get(int page, int limit) {
        Sort sort = new Sort(Sort.Direction.ASC, "createTime");
        PageResult<BounsDetails> all = bounsDetailsRepository.findAll(page, limit,sort);
        return all;
    }

    @Override
    public void getSql(BounsDetails bounsDetails, BufferedWriter userWtriter) throws IOException {
        if(bounsDetails.getBounsType() == null){
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("insert into `balance_flow` (`create_time`, `update_time`," +
                "  `money`,`remarks`, `user_id`, `flow_type`)");
        stringBuffer.append("value(");
        stringBuffer.append(SqlUtils.concatSql(DateUtil.formatDate(bounsDetails.getCreateTime(), DateUtil.FormatType.SECOND)));
        stringBuffer.append(SqlUtils.concatSql(DateUtil.formatDate(bounsDetails.getCreateTime(), DateUtil.FormatType.SECOND)));
        stringBuffer.append(bounsDetails.getUserBouns()+",");
        if(bounsDetails.getBounsType() == 0){
            stringBuffer.append(SqlUtils.concatSql("转入记录"));
        }else if(bounsDetails.getBounsType() == 2){
            stringBuffer.append(SqlUtils.concatSql("银联退回"));
        }else if(bounsDetails.getBounsType() == 8){
            stringBuffer.append(SqlUtils.concatSql("大转盘"));
        }else if(bounsDetails.getBounsType() == 1){
            stringBuffer.append(SqlUtils.concatSql("提现"));
        }else if(bounsDetails.getBounsType() == 5){
            stringBuffer.append(SqlUtils.concatSql("小品会转出"));
        }else if(bounsDetails.getBounsType() == 6){
            stringBuffer.append(SqlUtils.concatSql("充话费"));
        }else{
            stringBuffer.append(SqlUtils.concatSql(null));
        }
        if(bounsDetails.getToUid()!=null){
            stringBuffer.append(bounsDetails.getToUid()+",");
        }else{
            stringBuffer.append(bounsDetails.getUserid()+",");
        }
        stringBuffer.append(bounsDetails.getBounsType());
        stringBuffer.append(");");
        userWtriter.write(stringBuffer.toString());
        userWtriter.newLine();
    }

    @Override
    public String getTitle() {
        return "资金详情表迁移";
    }
}


