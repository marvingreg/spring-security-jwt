package com.security.application.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.security.application.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
@JsonInclude(Include.NON_NULL)
public class Users {
	
	@Id
	private String id;
	
	@Column(name="FNAME")
	private String fName;
	 
	@Column(name="LNAME")
	private String lName;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="PASSWORD")
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	@Column(name="USERNAME")
	private String username;
	
	@Column(name="ROLE")
	@JsonProperty("role")
	private Role role;
	
	public Users(String id, String fName, String lName, String email, String password, String username, Role role) {
		super();
		this.id = id;
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.password = password;
		this.username = username;
		this.role = role;
	}
	
	public Users(Users user) {
		super();
		this.id = user.getId();
		this.fName = user.getfName();
		this.lName = user.getlName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.username = user.getUsername();
	}
	
	

	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public Users() {
		super();
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
