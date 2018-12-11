package com.example.web;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domain.Member;
import com.example.service.MemberService;


@Controller
@RequestMapping("/member")
public class MemberController { // POJO기반 컨트롤러
	
	@Autowired
	private MemberService memberService;

	// http://localhost:8181/springanno/app/member/add
	// GET 방식 요청
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String add() {
		return "member/addForm"; // 포워딩방식으로 jsp파일을 사용하라는 정보 리턴
	}
	
	// http://localhost:8181/springanno/app/member/add
	// POST 방식 요청
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(@ModelAttribute Member member) {
		// Member 오브젝트 생성 후 파라미터가 채워져서 매개변수로 전달받음
		member.setReg_date(new Timestamp(System.currentTimeMillis()));
		memberService.add(member);
		return "redirect:/member/login";
	}
	
	
	// http://localhost:8181/springanno/app/member/login
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "member/loginForm";
	}
	
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@RequestParam("id") String id, 
			@RequestParam String passwd,
			HttpServletResponse response,
			HttpSession session) throws Exception {
		
		int check = memberService.userCheck(id, passwd);
		
		if (check != MemberService.ID_OK_PASSWD_OK) { // 아이디, 패스워드가 일치하지 않으면
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			String message = "";
			switch (check) {
			case MemberService.ID_OK_PASSWD_FAIL: message = "패스워드 틀림"; break;
			case MemberService.ID_FAIL_PASSWD_FAIL : message = "아이디 없음"; break;
			}
			
			out.println("<script>alert('" + message + "');"
					+ "history.back();</script>");
			out.close();
			return null;
		}
		
		session.setAttribute("id", id);
		
		return "redirect:/member/main";
	}
	
	@RequestMapping("main")
	public String main() {
		return "member/main";
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/member/login";
	}
	
	@RequestMapping("get")
	public String get(HttpSession session, ModelMap modelMap) {
		String id = (String) session.getAttribute("id");
		// 세션값 없으면 /member/loginForm 으로 이동
		if (id == null) {
			return "redirect:/member/login";
		}
		
		Member member = memberService.get(id);
		
		modelMap.addAttribute("member", member);
		
		return "member/info";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(HttpSession session, ModelMap modelMap) {
		String id = (String) session.getAttribute("id");
		// 세션값 없으면 /member/loginForm 으로 이동
		if (id == null) {
			return "redirect:/member/login";
		}
		
		Member member = memberService.get(id);
		
		modelMap.addAttribute("member", member);
		
		return "member/updateForm";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(HttpSession session,
			@ModelAttribute Member member,
			HttpServletResponse response) throws Exception {
		String id = (String) session.getAttribute("id");
		// 세션값 없으면 /member/loginForm 으로 이동
		if (id == null) {
			return "redirect:/member/login";
		}
		
		int check = memberService.userCheck(member.getId(), member.getPasswd());
		if (check == MemberService.ID_OK_PASSWD_FAIL) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('패스워드 틀림');history.back();</script>");
			out.close();
			return null;
		}
		
		memberService.update(member);
		
		return "redirect:/member/main";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete() {
		return "member/deleteForm";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(HttpSession session,
			@RequestParam String passwd,
			HttpServletResponse response) throws Exception {
		String id = (String) session.getAttribute("id");
		// 세션값 없으면 /member/loginForm 으로 이동
		if (id == null) {
			return "redirect:/member/login";
		}
		
		int check = memberService.userCheck(id, passwd);
		if (check == MemberService.ID_OK_PASSWD_FAIL) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('패스워드 틀림');history.back();</script>");
			out.close();
			return null;
		}
		
		memberService.delete(id);
		session.invalidate(); // 세션값 초기화
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>alert('회원 삭제되었습니다.');"
				+ "location.href='login';</script>");
		out.close();
		return null;
	}
	
	@RequestMapping("list")
	public String list(HttpSession session,
			ModelMap modelMap) {
		String id = (String) session.getAttribute("id");
		// 세션값 없거나 admin이 아니면 /member/main 으로 이동
		if (id == null || !id.equals("admin")) {
			return "redirect:/member/main";
		}
		
		List<Member> members = memberService.getAll();
		
		modelMap.addAttribute("members", members);
		
		return "member/list";
	}
	
	
	@RequestMapping("ajaxCheckDuplicateId")
	@ResponseBody
	public String ajaxCheckDuplicateId(@RequestParam("id") String id) {
	    int count = memberService.countById(id);
	    String str = "";
	    if (count == 0) {
	        str = "<span style='color: green'>사용가능한 아이디 입니다.</span>";
	    } else { // count == 1
	        str = "<span style='color: red'>이미 사용중인 아이디 입니다.</span>";
	    }
	    return str;
	}
	
	
} // MemberController 클래스 끝






