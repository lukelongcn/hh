package com.h9.api.model.dto;

import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * Created by itservice on 2018/1/25.
 */

@XmlRootElement
public class EventDTO {
    @XmlElement(name = "ToUserName")
    private String ToUserName;

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }
}


