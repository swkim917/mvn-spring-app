package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Member;
import com.example.repository.MemberDao;


@Service
@Transactional
public class MemberService {
    
    public static final int ID_OK_PASSWD_OK = 1;
    public static final int ID_OK_PASSWD_FAIL = 0;
    public static final int ID_FAIL_PASSWD_FAIL = -1;
    
	/*
	 @Autowired - 타입으로 의존관계 주입
	 
	 @Resource - 이름으로 의존관계 주입
	*/
	
	//@Autowired // 타입을 기준으로 빈을 가져와서 의존관계 주입함
	private MemberDao memberDao;
	
	// @Resource - setter 메소드 이름으로 의존관계 주입 (set을 제외한 첫글자가 소문자인 메소드명)
	@Autowired // 매개변수 타입 기준으로 의존관계 주입
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	public void add(Member member) {
		memberDao.add(member);
	}
	
	public int userCheck(String id, String passwd) {
		Member member = memberDao.get(id);
		if (member != null) { // id 일치
			if (member.getPasswd().equals(passwd)) { // passwd 일치
				return ID_OK_PASSWD_OK;
			} else { // passwd 불일치
				return ID_OK_PASSWD_FAIL;
			}
		} else {
			return ID_FAIL_PASSWD_FAIL;
		}
	}
	
	@Transactional(readOnly=true)
	public Member get(String id) {
		return memberDao.get(id);
	}
	
	public void update(Member member) {
		memberDao.update(member);
	}
	
	public void delete(String id) {
		memberDao.delete(id);
	}
	
	public List<Member> getAll() {
		return memberDao.getAll();
	}
	
	public int countById(String id) {
	    return memberDao.countById(id);
	}
	
}
