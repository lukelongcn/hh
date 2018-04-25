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
 * Created by Ln on 2018/4/23.
 *
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
    @JoinColumn(name = "custom_module_id", referencedColumnName = "id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CustomModule customModule;

    @Column(name = "main_images", columnDefinition = "varchar(300) comment '主图'")
    private String mainImages;

    /**
     * 图片框数量
     */
    @Column(name = "custom_image_count")
    private Integer customImagesCount;



    @Column(name = "text_count")
    private Integer textCount = 0;

    @Column(name = "del_flag")
    private Integer delFlag = 0;

    /**
     * 1 为 瓶身，2为其他
     */
    @Column(name = "type")
    private Integer type;

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




}
