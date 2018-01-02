package com.h9.common.db.entity.user;

import javax.persistence.*;

/**
 * @author: George
 * @date: 2017/11/27 16:36
 */

@Entity
@Table(name = "permission", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "parent_id"}),@UniqueConstraint(columnNames = {"access_code"})})
public class Permission implements Comparable<Permission> {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(32) default '' COMMENT '名称'")
    private String name;

    @Column(name = "access_code", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '授权码'")
    private String accessCode;

    @Column(name = "parent_id", columnDefinition = "bigint(20) default null COMMENT '父权限id'")
    private Long parentId;

    @Column(name = "description", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '描述'")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(final Long idToSet) {
        id = idToSet;
    }

    public String getName() {
        return name;
    }

    public void setName(final String nameToSet) {
        name = nameToSet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String descriptionToSet) {
        description = descriptionToSet;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Permission() {
        super();
    }

    public Permission(final String nameToSet) {
        super();
        name = nameToSet;
    }

    public Permission(Long id, String name, String accessCode, Long parentId, String description) {
        this.id = id;
        this.name = name;
        this.accessCode = accessCode;
        this.parentId = parentId;
        this.description = description;
    }

/* @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Permission other = (Permission) obj;
        if (accessCode == null) {
            if (other.accessCode != null)
                return false;
        } else if (!accessCode.equals(other.accessCode))
            return false;
        return true;
    }*/

    @Override
    public String toString() {
        return this.getDescription();
    }

    @Override
    public int compareTo(Permission o) {
        return (int) (this.getId() - o.getId());
    }
}
