package com.h9.api.model.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.jboss.logging.Logger;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * Created by itservice on 2018/1/3.
 */
@Data
@Accessors(chain = true)
public class AddHotelOrderDTO {

    @NotNull(message = "请填写RoomId")
    private Long roomTypeId;

    @NotNull(message = "请填写订房数量")
    private Integer roomCount;

    @NotNull(message = "请填写入住人")
    @Length(max = 200, min = 1,message = "入住人填写过长")
    private String stayRoomer;

    @NotNull(message = "请填写手机号码")
    @Length(max = 20, min = 1)
    private String phone;

    @NotNull(message = "请填写请房间的保留时间")
    @Length(max = 200, min = 1,message = "保留时间填写过长")
    private String keepTime;

    @NotNull(message = "请填写住宿偏好")
    @Length(max = 200, min = 1,message = "住宿偏好填写过长")
    private String roomStyle;

    private String remark;

    @NotNull(message = "请填写入住时间")
    private Date comeRoomTime;

    public void setComeRoomTime(String comeRoomTime) throws MissingServletRequestParameterException {


        Logger logger = Logger.getLogger(this.getClass());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse("2017-09-04");
            this.comeRoomTime = date;
        } catch (ParseException e) {
            logger.info(e.getMessage(), e);
            throw new MissingServletRequestParameterException("comeRoomTime", comeRoomTime);
        }

    }


    @NotNull(message = "请填写离开时间")
    private Date outRoomTime;

    public void setOutRoomTime(String outRoomTime) throws MissingServletRequestParameterException {
        Logger logger = Logger.getLogger(this.getClass());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse("2017-09-04");
            this.outRoomTime = date;
        } catch (ParseException e) {
            logger.info(e.getMessage(), e);
            throw new MissingServletRequestParameterException("outRoomTime", outRoomTime);
        }
    }

    public static void main(String[] args) {
        String s = JSONObject.toJSONString(new AddHotelOrderDTO());
        System.out.println(s);
    }
}
