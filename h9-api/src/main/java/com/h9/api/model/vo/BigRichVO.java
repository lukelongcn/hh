package com.h9.api.model.vo;

import com.h9.common.base.PageResult;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>Title:h9-parent</p>
 * <p>Desription:1号大富贵显示界面
 *
 * @author LiYuan
 * @Date 2018/3/28
 */
@Data
public class BigRichVO {
    private Integer lotteryChance;
    private BigDecimal bigRichMoney;
    private PageResult<BigRichRecordVO> recordList;
}
