package NaverDevelopers.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import NaverDevelopers.domain.dto.MemberDto;
import NaverDevelopers.service.MemberService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {

	@Autowired
	private MemberService service;
	
//-------------------------------------------------------------------

	@GetMapping("/member/join")
	public String join() {
		return "/member/join";
	}
	
	//회원가입버튼 누르면 회원가입 정보 DB에 저장
	@PostMapping("/member/join")
	public String join(MemberDto dto) {
		log.debug("dto:"+dto);
		service.save(dto);
		return "redirect:/member/login";
	}
	
	//join.html의 $.post처리
	@ResponseBody
	@PostMapping("/member/emailCheck") //$.post에서 요청주소가 "/member/emailCheck"이니 $.post랑 연결
	public void emailCheck(String email) throws IOException {//key&value의 "email"이라서 String
		service.Check(email);
	}
	
	//login페이지로 이동
	@GetMapping("/member/login")
	public String login() {
		return "/member/login";
	}
	
	//login처리
	@PostMapping("/member/login")
	public String login(MemberDto dto) {
		service.login(dto);
		return "redirect:/";
	}
	
	//logout처리
	@GetMapping("/member/logout")
	public String logout() {
		service.logout();
		return "redirect:/";
	}
}
