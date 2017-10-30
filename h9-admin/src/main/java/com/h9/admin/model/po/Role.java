package com.h9.admin.model.po;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@Entity
@Table(name="role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "description")
	private String description;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "role_privilege",joinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "priv_id", referencedColumnName = "id") })
	//@JsonManagedReference
	private Set<Permission> permissions;

	public Role() {
		super();
	}

	public Role(final String nameToSet) {
		super();
		name = nameToSet;
	}

	public Role(final String nameToSet, final Set<Permission> permissionsToSet) {
		super();
		name = nameToSet;
		permissions = permissionsToSet;
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

	public void setDescription(String description) {
		this.description = description;
	}


	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(final Set<Permission> permissionsToSet) {
		permissions = permissionsToSet;
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
		final Role other = (Role) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return new StringBuffer().append("id:" + id).append("name:" + name)
				.toString();
	}

}
