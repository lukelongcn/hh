package com.h9.common.modle.vo.admin.basis;

import com.h9.common.db.entity.config.Version;
import com.h9.common.modle.vo.admin.BasisVO;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;


/**
 * @author: George
 * @date: 2017/11/28 17:07
 */
public class VersionVO extends BasisVO{

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "版本")
    private String version;

    @ApiModelProperty(value = "客户端",required = true)
    private Integer clientType;

    @ApiModelProperty(value = "客户端描述",required = true)
    private String clientTypeDesc;

    @ApiModelProperty(value = "升级类型,1:不提示升级,2:建议升级,3:强制升级")
    private Integer upgradeType;

    @ApiModelProperty(value = "升级类型描述")
    private String upgradeTypeDesc;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "包url")
    private String packageUrl;

    @ApiModelProperty(value = "包名")
    private String packageName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public String getClientTypeDesc() {
        return clientTypeDesc;
    }

    public void setClientTypeDesc(String clientTypeDesc) {
        this.clientTypeDesc = clientTypeDesc;
    }

    public String getUpgradeTypeDesc() {
        return upgradeTypeDesc;
    }

    public void setUpgradeTypeDesc(String upgradeTypeDesc) {
        this.upgradeTypeDesc = upgradeTypeDesc;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public VersionVO() {
    }

    public VersionVO(Version version) {
        BeanUtils.copyProperties(version,this);
        this.clientTypeDesc = Version.ClientTypeEnum.getNameByCode(version.getClientType());
        this.upgradeTypeDesc = Version.UpgradeTypeEnum.getNameByCode(version.getUpgradeType());
    }
}
