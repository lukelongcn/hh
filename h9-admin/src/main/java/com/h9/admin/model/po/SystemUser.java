package com.h9.admin.model.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


@Entity
@Table(name="system_user")
public class SystemUser{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name", unique = true, nullable = false)
	private String name;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "status",  nullable = false)
	private Integer status;
	
	@Column(name = "creator_id",  nullable = false)
	private Long creatorId;


	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="user_role",joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") })
	private Set<Role> roles;

	@Transient
	@JsonIgnore
    private Set<Permission> permissions;
	public SystemUser() {
		super();
		status = 0;
	}

	public SystemUser(final String nameToSet, final String passwordToSet,
					  final Set<Role> rolesToSet) {
		super();

		name = nameToSet;
		password = passwordToSet;
		roles = rolesToSet;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(final String passwordToSet) {
		password = passwordToSet;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(final Set<Role> rolesToSet) {
		roles = rolesToSet;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Set<Permission> findUserPermissions() {
		permissions = new HashSet<Permission>();
		if(!roles.isEmpty()) {
			Iterator<Role> it = roles.iterator();
			while(it.hasNext()) {
				Role r = it.next();
				Set<Permission> ps = r.getPermissions();
				if (!ps.isEmpty()) {
					Iterator<Permission> pIt = ps.iterator();
					while(pIt.hasNext()) {
						Permission p = pIt.next();
						permissions.add(p);
					}
				}
			}
		}
		return permissions;
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
		final SystemUser other = (SystemUser) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return new StringBuffer().append("id:" + id).append("name:" + name)
				.toString();
	}
}