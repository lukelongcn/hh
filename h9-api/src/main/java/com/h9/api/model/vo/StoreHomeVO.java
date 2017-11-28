package com.h9.api.model.vo;

import com.h9.common.db.entity.Banner;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by itservice on 2017/11/28.
 */
@Data
@Accessors(chain = true)
public class StoreHomeVO {

    private Map<String, List<Banner>> banners = new HashMap<>();

    private String balance = "0";

    private List<GoodsListVO> hotGoods = new ArrayList();

}
