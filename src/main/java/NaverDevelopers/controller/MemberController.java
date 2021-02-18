package NaverDevelopers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import NaverDevelopers.domain.dto.MemberDto;
import NaverDevelopers.service.MemberService;

@Controller
public class MemberController {

	@Autowired
	private MemberService service;
	
//-------------------------------------------------------------------
	
	//회원가입 페이지로 가주세요
	@GetMapping("/member/join")
	public String join() {
		return"/member/join";
	}
	
	//회원가입버튼누르면 DB에 저장하고 index페이지로 넘어가기
	@PostMapping("/member/join")
	public String doJoin(MemberDto dto) {
		service.doJoin(dto);
		return"redirect:/";
	}
	
}
