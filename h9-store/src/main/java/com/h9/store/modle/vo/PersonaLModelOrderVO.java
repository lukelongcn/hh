package com.h9.store.modle.vo;

import lombok.Data;

import javax.annotation.Resource;
import java.awt.*;
import java.awt.print.Pageable;
import java.util.List;

/**
 * <p>Title:h9-parent</p>
 * <p>Desription:</p>
 *
 * @author LiYuan
 * @Date 2018/4/25
 */
@Data
public class PersonaLModelOrderVO {
    private List<ModelGoodsVO> modelGoodsVOS;
    private String balance;
}
