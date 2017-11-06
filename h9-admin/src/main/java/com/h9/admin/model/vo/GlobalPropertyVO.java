package com.h9.admin.model.vo;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: George
 * @date: 2017/11/6 17:43
 */
public class GlobalPropertyVO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "参数类型， 0：文本 1：对象 2:数组")
    private Integer type;

    @ApiModelProperty(value = "参数标识")
    private String code;

    @ApiModelProperty(value = "参数值")
    private List<Map<String,String>> val;

    @ApiModelProperty(value = "说明")
    private String description;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Map<String, String>> getVal() {
        return val;
    }

    public void setVal(String val) {
       /* if(type==0){
            this.val.add(new HashMap<String, String>("val",val));
        }else if(type==1){
            this.val = JSON.parseArray(val,);
        }else{
            List<String> stringList = new ArrayList<>();
            for(Map m:val){
                stringList.add(m.get("val").toString());
            }
            v = JSON.toJSONString(stringList);
        }
        this.val = val;*/
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
