package com.h9.api.model.vo;

import com.h9.common.db.entity.config.Banner;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by itservice on 2018/2/1.
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class TravelPageVO {
    private String layoutStyle;
    private List<Map<String, String>> imgList = new ArrayList<>();


    public TravelPageVO(String layoutStyle, List<Banner> bannerList) {
        this.layoutStyle = layoutStyle;
        bannerList.stream().forEach(banner -> {
            Map<String, String> map = new HashMap<>();
            map.put("id", banner.getId()+"");
            map.put("imgUrl", banner.getIcon());
            map.put("title", banner.getTitle());
            map.put("link", banner.getUrl());
            map.put("fontColor", banner.getFontColor());
            imgList.add(map);
        });
    }
}
