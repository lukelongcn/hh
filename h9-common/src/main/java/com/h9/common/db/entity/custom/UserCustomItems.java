package com.h9.common.db.entity.custom;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Ln on 2018/4/25.
 */
@Table(name = "user_custom_items")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCustomItems {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "custom_module_items_id")
    private Long customModuleItemsId;

    @Column(name = "customTexts")
    private String customTexts;

    @Column(name = "custom_images")
    private String customImages;


    /**
     * 1 为 瓶身，2为其他
     */
    @Column(name = "type")
    private Integer type;

    public List<String> getCustomImages() {
        if (customImages == null) {
            return new ArrayList<>();
        }
        return JSONObject.parseObject(customImages, List.class);
    }

    public void setCustomImages(List<String> customImages) {
        if (CollectionUtils.isNotEmpty(customImages)) {
            this.customImages = JSONObject.toJSONString(customImages);
        }
    }

    public List<String> getTexts() {
        if (customTexts == null) {
            return new ArrayList<>();
        }
        return JSONObject.parseObject(customTexts, List.class);
    }

    public void setTexts(List<String> texts) {
        if (CollectionUtils.isNotEmpty(texts)) {
            this.customTexts = JSONObject.toJSONString(texts);
        }
    }
}
