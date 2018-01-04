package com.h9.admin.model.dto.hotel;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by itservice on 2018/1/4.
 */
@Data
@Accessors(chain = true)
public class EditHotelDTO {

    @NotNull(message = "请填写酒店名")
    @Length(max = 200,min = 2)
    private String hotelName;

    @NotNull(message = "请填写酒店电话")
    @Length(max = 200,min = 2)
    private String hotelPhone;

    @NotNull(message = "请填写酒店城市")
    @Length(max = 200,min = 2)
    private String city;

    @NotNull(message = "请填写酒店名")
    @Length(max = 200,min = 2)
    private String detailAddress;

    private Float grade;

    private String images;

    @NotNull(message = "请输入的房间数")
    @Min(value = -1,message = "房间数必须大于或等于0")
    private Integer roomCount;

    @NotNull(message = "请填写开始预定的时间")
    private String startReserveTime;

    @NotNull(message = "请填写结束预定的时间")
    private String endReserveTime;

    private Long id;

}
