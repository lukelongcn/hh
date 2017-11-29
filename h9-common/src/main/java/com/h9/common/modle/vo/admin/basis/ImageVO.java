package com.h9.common.modle.vo.admin.basis;

import com.h9.common.db.entity.Image;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import java.util.Date;

/**
 * @author: George
 * @date: 2017/11/21 19:34
 */
public class ImageVO {

    @ApiModelProperty(value = "id ")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "目录")
    private String directory;

    @ApiModelProperty(value = "图片url")
    private String url;

    @ApiModelProperty(value = "创建时间")
    private Date createTime ;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public ImageVO() {
    }

    public ImageVO(Image image) {
        BeanUtils.copyProperties(image,this);
    }

    public ImageVO toImageVO(Image image){
        ImageVO imageVO = new ImageVO();
        BeanUtils.copyProperties(image,imageVO);
        return  imageVO;
    }

    public static Page<ImageVO> toImageVO(Page<Image> imagePage){
        return  imagePage.map(image ->
            new ImageVO(image)
       );
    }
}
