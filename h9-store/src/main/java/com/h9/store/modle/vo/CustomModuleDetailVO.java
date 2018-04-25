package com.h9.store.modle.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ln on 2018/4/24.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomModuleDetailVO {
    private List<String> mainImages = new ArrayList<>();
    private int customImageNumber;
    private int customTextNumber;
    private long id;
    private Integer type;
}
