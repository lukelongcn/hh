package com.h9.common.db.entity.config;

import com.h9.common.base.BaseEntity;

import javax.persistence.*;

import static java.util.Arrays.*;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author: George
 * @date: 2017/11/28 13:55
 */
@Entity
@Table(name = "version")
public class Version extends BaseEntity {

    @Id
    @SequenceGenerator(name = "h9-apiSeq", sequenceName = "h9-api_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-apiSeq")
    private Long id;

    @Column(name = "version", nullable = true,columnDefinition = "varchar(16) default '' COMMENT '版本'")
    private String version;

    @Column(name = "client_type",  columnDefinition = "tinyint default 1 COMMENT '客户端,1:android,2:ios'")
    private Integer clientType;

    @Column(name = "upgrade_type",  columnDefinition = "tinyint default 0 COMMENT '升级类型,1:不提示升级,2:建议升级,3:强制升级'")
    private Integer upgradeType;

    @Column(name = "description",  columnDefinition = "text  COMMENT '描述'")
    private String description;

    @Column(name = "package_url",  columnDefinition = "varchar(256) default '' COMMENT '包url'")
    private String packageUrl;

    @Column(name = "package_name",  columnDefinition = "varchar(64) default '' COMMENT '包名'")
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

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public enum ClientTypeEnum {
        IOS(2, "IOS"),
        ANDROID(1, "Android");

        private int code;
        private String name;

        ClientTypeEnum(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public static String getNameByCode(int code){
            ClientTypeEnum clientTypeEnum =  stream(values()).filter(o -> o.getCode()==code).limit(1).findAny().orElse(null);
            return clientTypeEnum==null?null:clientTypeEnum.getName();
        }

    }

    public enum UpgradeTypeEnum {
        NO_SUGGESTION(1, "不提示升级"),
        SUGGESTION(2, "建议升级"),
        FORCE(3, "强制升级");

        private int code;
        private String name;

        UpgradeTypeEnum(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public static String getNameByCode(int code){
            UpgradeTypeEnum upgradeTypeEnum = stream(values()).filter(o -> o.getCode()==code).limit(1).findAny().orElse(null);
            return upgradeTypeEnum==null?null:upgradeTypeEnum.getName();
        }
    }

}
