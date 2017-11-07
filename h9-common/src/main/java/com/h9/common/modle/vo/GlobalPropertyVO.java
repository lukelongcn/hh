package com.h9.common.modle.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.h9.common.db.entity.GlobalProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.util.*;

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
    private List val;

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "创建时间")
    protected Date createTime;//

    @ApiModelProperty(value = "更新时间")
    protected Date updateTime;//

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
        List list = new ArrayList();
        if(type==0){
            Map map = new HashMap<String, String>();
            map.put("val",val);
            list.add(map);
        }else if(type==1){
          /*  JSONArray jsonArray = JSON.parseObject(val);
            //list = CollectionUtils..arrayToList(jsonArray);
            //Collections.copy(list,jsonArray);
            for(int i=0;i<jsonArray.size();i++){
                list.add(jsonArray.get(i));
                System.out.println(jsonArray.get(i));
            }*/
            JSONObject map = JSON.parseObject(val);
            for(Map.Entry entry: map.entrySet()){
                Map m = new HashMap();
                m.put("key",entry.getKey());
                m.put("code",entry.getValue());
                list.add(m);
            }
        }else{
            //val = val.substring(1,val.length()-1);
            List l = Arrays.asList(val.split(","));
            for(int i=0;i<l.size();i++){
                Map map = new HashMap();
                map.put("val",l.get(i));
                list.add(map);
            }
            //StringUtils
          /*  List<String> stringList = new ArrayList<>();
            for(Map m:val){
                stringList.add(m.get("val").toString());
            }
            v = JSON.toJSONString(stringList);*/
        }
        this.val = list;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public static GlobalPropertyVO toGlobalPropertyVO(GlobalProperty globalProperty){
        GlobalPropertyVO globalPropertyVO = new GlobalPropertyVO();
        BeanUtils.copyProperties(globalProperty,globalPropertyVO);
        return globalPropertyVO;
    }

    public GlobalPropertyVO() {
    }

    public GlobalPropertyVO(GlobalProperty globalProperty) {
        BeanUtils.copyProperties(globalProperty,this);
    }
}
