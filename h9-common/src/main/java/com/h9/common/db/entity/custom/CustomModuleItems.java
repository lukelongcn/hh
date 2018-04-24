package com.h9.common.db.entity.custom;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Ln on 2018/4/23.
 */
@Entity
@Table(name = "custom_module_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomModuleItems {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_module_id", referencedColumnName = "id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CustomModule customModule;

    @Column(name = "main_images")
    private String mainImages;

    @Column(name = "custom_images")
    private String customImages;

    @Column(name = "custom_image_count")
    private Integer customImagesCount;

    @Column(name = "texts")
    private String texts;

    @Column(name = "text_count")
    private Integer textCount;

    @Column(name = "del_flag")
    private Integer delFlag = 0;

    public CustomModuleItems(List<String> mainImages, Integer textCount, Integer customImagesCount, CustomModule customModule) {
        this.setMainImages(mainImages);
        this.textCount = textCount;
        this.customImagesCount = customImagesCount;
        this.customModule = customModule;
    }

    public List<String> getMainImages() {
        if (mainImages == null) {
            return new ArrayList<>();
        }
        return JSONObject.parseObject(mainImages, List.class);
    }

    public void setMainImages(List<String> mainImages) {
        if (CollectionUtils.isNotEmpty(mainImages)) {
            this.mainImages = JSONObject.toJSONString(mainImages);
        }
    }

    public List<String> getCustomImages() {
        if (customImages == null) {
            return new ArrayList<>();
        }
        return JSONObject.parseObject(customImages, List.class);
    }

    public void setCustomImages(List<String> customImages) {
        if (CollectionUtils.isNotEmpty(customImages)) {
            this.customImages = JSONObject.toJSONString(mainImages);
        }
    }

    public List<String> getTexts() {
        if (texts == null) {
            return new ArrayList<>();
        }
        return JSONObject.parseObject(texts, List.class);
    }

    public void setTexts(List<String> texts) {
        if (CollectionUtils.isNotEmpty(texts)) {
            this.texts = JSONObject.toJSONString(mainImages);
        }
    }
}
