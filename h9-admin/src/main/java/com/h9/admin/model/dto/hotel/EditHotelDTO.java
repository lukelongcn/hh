package com.h9.admin.model.dto.hotel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by itservice on 2018/1/4.
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "编辑酒店参数")
public class EditHotelDTO {


    @ApiModelProperty("酒店名")
    @NotNull(message = "请填写酒店名")
    @Length(max = 200,min = 2)
    private String hotelName;


    @ApiModelProperty("酒店电话")
    @NotNull(message = "请填写酒店电话")
    @Length(max = 200,min = 2)
    private String hotelPhone;


    @ApiModelProperty("酒店城市")
    @NotNull(message = "请填写酒店城市")
    @Length(max = 200,min = 2)
    private String city;

    @ApiModelProperty("酒店地址")
    @NotNull(message = "请填写酒店地址")
    @Length(max = 200,min = 2)
    private String detailAddress;

    @ApiModelProperty("评分")
    private Float grade;

    @ApiModelProperty("图片")
    private List<String> images = new ArrayList<>();

    @ApiModelProperty("房间数")
    @NotNull(message = "请输入的房间数")
    @Min(value = -1,message = "房间数必须大于或等于0")
    private Integer roomCount;

    @ApiModelProperty("开始预定的时间")
    @NotNull(message = "请填写开始预定的时间")
    private String startReserveTime;

    @ApiModelProperty("结束预定的时间")
    @NotNull(message = "请填写结束预定的时间")
    private String endReserveTime;

    private Long id;

    @ApiModelProperty("酒店介绍")
    private String hotelInfo = "";

    public void setStartReserveTime(String startReserveTime) throws MissingServletRequestParameterException {
        Pattern pattern = Pattern.compile("[0-9]{2}:[0-9]{2}");
        Matcher matcher = pattern.matcher(startReserveTime);
        if(!matcher.matches()){

            throw new MissingServletRequestParameterException("startReserveTime", "时间格式 08:00");
        }
        this.startReserveTime = startReserveTime;
    }

    public void setEndReserveTime(String endReserveTime) throws MissingServletRequestParameterException {
        Pattern pattern = Pattern.compile("[0-9]{2}:[0-9]{2}");
        Matcher matcher = pattern.matcher(endReserveTime);
        if(!matcher.matches()){
            throw new MissingServletRequestParameterException("startReserveTime", "时间格式 08:00");
        }
        this.endReserveTime = endReserveTime;
    }

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("[0-9]{2}:[0-9]{2}");
        Matcher matcher = pattern.matcher("08:00");
            System.out.println(matcher.matches());
    }
}
