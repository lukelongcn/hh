package com.h9.api.model.vo;




import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;


/**
 * Created by itservice on 2017/11/27.
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class VbconvertVO {

    private String endTimeTip;

    private String vb;

    private String JiuYuan;

    private String JiuYuanIcon;

}
