package com.h9.admin.model.po;

import javax.persistence.*;

@Entity
@Table(name="permission",uniqueConstraints = {@UniqueConstraint(columnNames={"name","parent_id"})})
public class Permission implements Comparable<Permission>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "name",  nullable = false)
	private String name;
	@Column(name= "type", unique = false, nullable = true)
	private String type;//菜单：menu,按钮：button
	@Column(name = "url")
	private String url;
	@Column(name = "access_code")
	private String accessCode;
	@Column(name = "parent_id")
	private Long parentId;//
	@Column(name = "description")
	private String description;
	
	public Permission() {
		super();
	}

	public Permission(final String nameToSet) {
		super();
		name = nameToSet;
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	@Override
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
	}

	@Override
	public String toString() {
		return getName();
	}

	public int compareTo(Permission o) {
		// TODO Auto-generated method stub
		return (int) (this.getId()-o.getId());
	}
}
