package com.h9.api.model.vo.community;

import com.h9.common.db.entity.community.Stick;
import com.h9.common.db.entity.community.StickType;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * StickTypeDetailVo:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/4
 * Time: 11:14
 */
@Data
public class StickTypeDetailVo {
    private Long id;
    private String name;
    private String content;
    private String image;
    private Integer stickCount;
    private Integer readCount;
    private Integer answerCount;

    public StickTypeDetailVo() {
    }


    public StickTypeDetailVo(StickType stick) {
        BeanUtils.copyProperties(stick,this);
    }



}
