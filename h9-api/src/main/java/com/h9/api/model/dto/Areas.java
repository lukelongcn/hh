package com.h9.api.model.dto;

import java.util.List;

/**
 * Created by 李圆 on 2017/11/30
 * 地址管理类
 * */
public class Areas {

    private String id;

    private String name;

    private List list;

    public Areas(String id,String name,List<Areas> list){
         this.id = id;
         this.name = name;
         this.list =list;
    }

    public Areas(String id,String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
