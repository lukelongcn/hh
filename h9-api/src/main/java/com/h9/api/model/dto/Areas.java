package com.h9.api.model.dto;

import com.h9.common.db.entity.China;

import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 李圆 on 2017/11/30
 * 地址管理类
 * */
public class Areas {

    private Long id;

    private String name;

    private List list;

    public Areas(Long id,String name,List list){
         this.id = id;
         this.name = name;
         this.list =list;
    }

    public Areas(Long id,String name) {
        this.id = id;
        this.name = name;
    }

    public Areas(China china) {
        id = china.getId();
        name = china.getName();
        List<China> areas = china.getAreas();
        if (!CollectionUtils.isEmpty(areas)){
            list = areas.stream().map(Areas::new).collect(Collectors.toList());
        }
    }

    public Areas() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
