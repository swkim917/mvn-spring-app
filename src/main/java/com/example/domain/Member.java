package com.example.domain;

import java.sql.Timestamp;

public class Member {
	private String id;
	private String passwd;
	private String name;
	private Integer age;
	private String gender;
	private String email;
	private Timestamp reg_date;
	private String address;
	private String tel;
	private String mtel;
	
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPasswd() {
        return passwd;
    }
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Timestamp getReg_date() {
        return reg_date;
    }
    public void setReg_date(Timestamp reg_date) {
        this.reg_date = reg_date;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getMtel() {
        return mtel;
    }
    public void setMtel(String mtel) {
        this.mtel = mtel;
    }
    @Override
    public String toString() {
        return "Member [id=" + id + ", passwd=" + passwd + ", name=" + name + ", age=" + age + ", gender=" + gender
                + ", email=" + email + ", reg_date=" + reg_date + ", address=" + address + ", tel=" + tel + ", mtel="
                + mtel + "]";
    }
    
}






