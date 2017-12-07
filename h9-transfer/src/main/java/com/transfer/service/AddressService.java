package com.transfer.service;

import com.h9.common.base.PageResult;
import com.transfer.SqlUtils;
import com.transfer.db.entity.Address;
import com.transfer.db.repo.TargetAddressReposiroty;
import org.jboss.logging.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by itservice on 2017/11/15.
 */
@Service
public class AddressService {


     Logger logger = Logger.getLogger(AddressService.class);

    @Resource
    private TargetAddressReposiroty targetAddressReposiroty;


    public void transfernAddress() {

//        BufferedWriter buffer = SqlUtils.getBuffer("./address.sql");

        int page = 0;
        int limit = 1000;
        int totalPage = 0;
        PageResult<Address> userInfoPageResult;
        Sort sort = new Sort(Sort.Direction.ASC, "ID");
        BufferedWriter userWtriter = SqlUtils.getBuffer("./sql/address.sql");
        do {
            page = page + 1;
            userInfoPageResult = targetAddressReposiroty.findAll(page, limit, sort);
            totalPage = (int) userInfoPageResult.getTotalPage();
            List<Address> userInfos = userInfoPageResult.getData();
            for (Address userInfo : userInfos) {
                try {
                    String sql = toMyAddressAndSave(userInfo);
                    if(sql!=null){
                        userWtriter.write(sql);
                        userWtriter.newLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            logger.info("page: "+page+" totalPage: "+totalPage);
            float rate = (float) page * 100 / (float) totalPage;
            if (page <= totalPage && userInfoPageResult.getCount() != 0)
                logger.debugv("地址迁移进度 " + rate + "% " + page + "/" + totalPage);
        } while (page <= totalPage &&userInfoPageResult.getCount() != 0);
        SqlUtils.close(userWtriter);
    }

    public String toMyAddressAndSave(Address address){
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("insert into `address` (`id`,`create_time`, `update_time`, `address`, `city`,`default_address`, " +
                " `distict`, `name`, `phone`, `province`, `provincial_city`, `user_id`) ");
        sqlBuffer.append("value(");
        sqlBuffer.append(address.getID()+",");
        sqlBuffer.append(SqlUtils.concatDate());
        sqlBuffer.append(SqlUtils.concatDate());
        sqlBuffer.append(SqlUtils.concatSql(address.getReceivingaddress()));
        sqlBuffer.append(SqlUtils.concatSql(address.getCity()));
        sqlBuffer.append(address.getADefault()+",");
        sqlBuffer.append(SqlUtils.concatSql(address.getDistrict()));
        sqlBuffer.append(SqlUtils.concatSql(address.getConsignee()));
        sqlBuffer.append(SqlUtils.concatSql(address.getConsigneePhone()));
        sqlBuffer.append(SqlUtils.concatSql(address.getProvince()));
        sqlBuffer.append(SqlUtils.concatSql(address.getProvincialCity()));
        sqlBuffer.append(address.getUserid());
        sqlBuffer.append(");");
        return sqlBuffer.toString();
    }


}
