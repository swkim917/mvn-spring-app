package com.example.domain;

import java.sql.Timestamp;

public class Board {
	private Integer num;
	private String name; 
	private String passwd; 
	private String subject; 
	private String content; 
	private String filename; 
	private Integer re_ref; 
	private Integer re_lev; 
	private Integer re_seq; 
	private Integer readcount; 
	private Timestamp reg_date; 
	private String ip;
	
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Integer getRe_ref() {
		return re_ref;
	}
	public void setRe_ref(Integer re_ref) {
		this.re_ref = re_ref;
	}
	public Integer getRe_lev() {
		return re_lev;
	}
	public void setRe_lev(Integer re_lev) {
		this.re_lev = re_lev;
	}
	public Integer getRe_seq() {
		return re_seq;
	}
	public void setRe_seq(Integer re_seq) {
		this.re_seq = re_seq;
	}
	public Integer getReadcount() {
		return readcount;
	}
	public void setReadcount(Integer readcount) {
		this.readcount = readcount;
	}
	public Timestamp getReg_date() {
		return reg_date;
	}
	public void setReg_date(Timestamp reg_date) {
		this.reg_date = reg_date;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
