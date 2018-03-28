package com.h9.admin.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Ln on 2018/3/28.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinBigRichUser {
    private Long id;
    private String phone;
    private String nickName;
    private String money;
    private String activity_number;
}
