package com.transfer.service;

import com.h9.common.base.PageResult;
import com.h9.common.utils.DateUtil;
import com.transfer.SqlUtils;
import com.transfer.Util;
import com.transfer.db.entity.Address;
import com.transfer.db.entity.UserInfo;
import com.transfer.db.repo.TargetAddressReposiroty;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by itservice on 2017/11/15.
 */
@Service
public class AddressService {

    @Resource
    private TargetAddressReposiroty targetAddressReposiroty;

    @Resource
    private com.h9.common.db.repo.AddressReposiroty myaddressReposiroty;

    public void transfernAddress() {

        BufferedWriter buffer = SqlUtils.getBuffer("./address.sql");

        int page = 0;
        int limit = 1000;
        int totalPage = 0;
        PageResult<UserInfo> userInfoPageResult;
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        BufferedWriter userWtriter = SqlUtils. getBuffer("./user.sql");
        BufferedWriter userAccountWtriter = SqlUtils.getBuffer("./user_account.sql");
        BufferedWriter userExtendsWtriter = SqlUtils.getBuffer("./user_extends.sql");
//        do {
//            page = page + 1;
//            userInfoPageResult = targetAddressReposiroty.findAll(page, limit, sort);
//            totalPage = (int) userInfoPageResult.getTotalPage();
//            List<UserInfo> userInfos = userInfoPageResult.getData();
//            for (UserInfo userInfo : userInfos) {
////                covertUser(userInfo);
//                try {
//                    String sql = covertToUser(userInfo);
//                    if(sql!=null){
//                        userWtriter.write(sql);
//                        userWtriter.newLine();
//                    }
//                    String userAccount = covertToUserAccount(userInfo);
//                    if(userAccount!=null){
//                        userAccountWtriter.write(userAccount);
//                        userAccountWtriter.newLine();
//                    }
//                    String userExtends = covertToUserExtends(userInfo);
//                    if(userExtends!=null){
//                        userExtendsWtriter.write(userExtends);
//                        userExtendsWtriter.newLine();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            logger.info("page: "+page+" totalPage: "+totalPage);
//            float rate = (float) page * 100 / (float) totalPage;
//            if (page <= totalPage && userInfoPageResult.getCount() != 0)
//                logger.debugv("用户迁移进度 " + rate + "% " + page + "/" + totalPage);
//        } while (page <= totalPage &&userInfoPageResult.getCount() != 0);
    }

    public String toMyAddressAndSave(Address address){
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("insert into `h9_test`.`address` (`id`,`create_time`, `update_time`, `address`, `city`,`default_address`, " +
                " `distict`, `name`, `phone`, `province`, `provincial_cyty`, `user_id`) ");
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
