package com.h9.api.model.vo;

import com.h9.common.db.entity.Version;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by itservice on 2017/12/1.
 */
@Data
@Accessors(chain = true)
public class UpdateInfoVO {
    private String updateVersion;
//    private Integer updateVersionNumber;

    private String updateContent;
    /**
     * description: 1:不提示升级,2:建议升级,3:强制升级
     */
    private Integer updateType;
    private String downloadUrl = "";

    public UpdateInfoVO(){}

    public UpdateInfoVO(Version version){
        this.updateContent  = version.getDescription();
        this.updateVersion = version.getVersion();
//        this.updateVersionNumber = version.getVersionNumber();
        this.updateType = version.getUpgradeType();
        this.downloadUrl = version.getPackageUrl();
    }
}
