package com.myspring.entity;

public class User {

	private Long uid;
	private String name;
	private String sex;
	
	
	public User() {
		super();
	}
	
	public User(Long uid, String name, String sex) {
		super();
		this.uid = uid;
		this.name = name;
		this.sex = sex;
	}

	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	@Override
	public String toString() {
		return "User [uid=" + uid + ", name=" + name + ", sex=" + sex + "]";
	}

	
}
