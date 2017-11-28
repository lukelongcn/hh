package com.transfer.service;

import com.h9.common.base.PageResult;
import com.h9.common.utils.DateUtil;
import com.transfer.SqlUtils;
import com.transfer.db.entity.BounsDetails;
import com.transfer.db.repo.BounsDetailsRepository;
import com.transfer.service.base.BaseService;
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
        return "./bouns.sql";
    }

    @Override
    public PageResult get(int page, int limit) {
        return bounsDetailsRepository.findAll(page,limit);
    }

    @Override
    public void getSql(BounsDetails bounsDetails, BufferedWriter userWtriter) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("insert into `vcoins_flow` (`create_time`, `update_time`," +
                "  `money`,`remarks`, `user_id`, `v_coins_type_id`)");
        stringBuffer.append("value(");
        stringBuffer.append(SqlUtils.concatSql(DateUtil.formatDate(bounsDetails.getBounsTime(), DateUtil.FormatType.SECOND)));
        stringBuffer.append(SqlUtils.concatSql(DateUtil.formatDate(bounsDetails.getBounsTime(), DateUtil.FormatType.SECOND)));
        stringBuffer.append(bounsDetails.getUserBouns()+",");
        if(bounsDetails.getState() == 1){
            stringBuffer.append("红包");
        }
        if(bounsDetails.getToUid()!=null){
            stringBuffer.append(bounsDetails.getUserid()+",");
        }else{
            stringBuffer.append(bounsDetails.getToUid()+",");
        }
        stringBuffer.append(bounsDetails.getState());
        stringBuffer.append(");");
        userWtriter.write(stringBuffer.toString());
        userWtriter.newLine();


    }

    @Override
    public String getTitle() {
        return "资金详情表迁移";
    }
}


