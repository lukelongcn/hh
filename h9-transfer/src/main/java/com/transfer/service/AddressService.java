package com.transfer.service;

import com.transfer.db.entity.Address;
import com.transfer.db.repo.TargetAddressReposiroty;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;

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
        int page = 0;
        int size = 100;
        Pageable pageable = new PageRequest(page, size);
        Page<Address> findPage = targetAddressReposiroty.findAll(pageable);

        findPage.getContent().stream().forEach(el -> toMyAddressAndSave(el));

        long count = targetAddressReposiroty.count();
        while (!findPage.isLast()) {
            System.out.println("---->第"+(count/size + 1)+"页，总页数："+count);
            page++;
            Pageable temp = new PageRequest(page, size);
            findPage = targetAddressReposiroty.findAll(temp);
            findPage.getContent().stream().forEach(el -> toMyAddressAndSave(el));
        }
    }

    public void toMyAddressAndSave(Address address){

        com.h9.common.db.entity.Address myAdd = new com.h9.common.db.entity.Address();

        try {
            BeanUtils.copyProperties(myAdd,address);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        myAdd.setOldId(address.getID());


        myaddressReposiroty.save(myAdd);

    }
}
