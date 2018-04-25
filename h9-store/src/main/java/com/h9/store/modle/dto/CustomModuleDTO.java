package com.h9.store.modle.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>Title:h9-parent</p>
 * <p>Desription:私人订制支付</p>
 *
 * @author LiYuan
 * @Date 2018/4/25
 */
@Data
@Accessors(chain = true)
public class CustomModuleDTO {
    @NotEmpty(message = "私人订制信息的ID列表不能为空")
    private List<Long> userCustomItemsId;

    @NotNull(message = "请选择地址")
    private Long addressId;

    @NotNull(message = "请选择您要兑换的数量")
    private Integer count;

    @NotNull(message = "请选择您要兑换的商品")
    private Long goodsId;

    //WX(2, "wx"), WXJS(3, "wxjs")
    @NotNull(message = "请传入支付平台类型，如，'wx'(微信APP）'wxjs'(公众号)")
    private String payPlatform;

    /**
     * description:
     *   @see com.h9.common.db.entity.order.Orders.PayMethodEnum
     */
    @NotNull(message = "请选择支付方式")
    private Integer payMethod;

}
