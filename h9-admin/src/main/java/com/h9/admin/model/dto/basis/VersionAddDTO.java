package com.h9.admin.model.dto.basis;

import com.h9.common.db.entity.Version;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;


/**
 * @author: George
 * @date: 2017/11/28 16:21
 */
public class VersionAddDTO {
    @ApiModelProperty(value = "版本",required = true)
    @NotBlank(message = "版本不能为空")
    private String version;

    @ApiModelProperty(value = "版本号",required = true)
    @NotNull(message = "版本号不能为空")
    private Integer versionNumber;

    @ApiModelProperty(value = "客户端,1:ios,2:android",required = true)
    @NotNull(message = "客户端不能为空")
    private Integer clientType;

    @ApiModelProperty(value = "升级类型,1:不提示升级,2:建议升级,3:强制升级",required = true)
    @NotNull(message = "升级类型不能为空")
    private Integer upgradeType;

    @ApiModelProperty(value = "描述",required = true)
    @NotBlank(message = "描述不能为空")
    private String description;

    @ApiModelProperty(value = "包url",required = true)
    @NotBlank(message = "包url不能为空")
    private String packageUrl;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public Integer getUpgradeType() {
        return upgradeType;
    }

    public void setUpgradeType(Integer upgradeType) {
        this.upgradeType = upgradeType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackageUrl() {
        return packageUrl;
    }

    public void setPackageUrl(String packageUrl) {
        this.packageUrl = packageUrl;
    }

    public Version toVersion() {
        Version version = new Version();
        BeanUtils.copyProperties(this,version);
        return version;
    }
}
